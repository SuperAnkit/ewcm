package ops.global.core.servlets;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.jcr.Binary;
import javax.jcr.ItemNotFoundException;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.ValueFactory;
import javax.jcr.query.Query;
import javax.jcr.query.QueryManager;
import javax.jcr.query.QueryResult;
import javax.servlet.ServletException;

import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.commons.Externalizer;

@SlingServlet(paths = { "/bin/approve " }, methods = { "POST" }, metatype = true)

/* Servlet to approve the form and pass the submitted data to the database using WS
 * 
 * 
 * 
 */


public class OPSApproveServlet extends SlingAllMethodsServlet {
	
	// Initializing all the constants
	private static final long serialVersionUID = 1;
	private static final String USER_NAME = "user_name";
	private static final String STATE = "state";
	private static final String APP_NO = "application_no";
	private String newFormNode;
	@Reference
	private ResourceResolverFactory resourceResolverFactory;
	private final Logger logger;
	InputStream content;

	public OPSApproveServlet() {
		this.logger = LoggerFactory.getLogger(this.getClass());
	}

	protected void doPost(SlingHttpServletRequest request,
			SlingHttpServletResponse response) throws ServletException,
			IOException {
		this.logger.info("+++++++++++++++++++POST LOGGER");
		
		// fetch all the parameters
		String user_name = request.getParameter("user_name");
		String state = request.getParameter("state");
		String app_no = request.getParameter("application_no");
		ResourceResolver resResolver = null;
		InputStream content = null;
		Binary contentValue = null;
	
			try {
				resResolver = this.resourceResolverFactory
						.getAdministrativeResourceResolver(null);
			} catch (LoginException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			
		Session resSession = (Session) resResolver.adaptTo((Class) Session.class);
		
		// get all the Nodes for the given applicationNumber
		List<String> tempSet = this.getNodeSet(resSession, user_name, state,
				app_no);
		int counter = 1; // use counter to get jcr:data from latest node
		for (String nodePath : tempSet) {
			this.logger.info("+++++++++++++++++++CHECKING NODE" + nodePath);
			this.logger.info("+++++++++++++++++++COUNTER" + counter);
				Node savedNode;
				try {
					savedNode = resSession.getNodeByUUID(nodePath);
					if (counter == 1) {
						// get the jcr:data and delete the approved node
						this.newFormNode = nodePath;
						this.logger.info("+++++++++++++++++++COPIED FILE START");
						Node newSavedNode = resSession.getNodeByUUID(this.newFormNode);
						content = newSavedNode.getProperty("jcr:data")
								.getBinary().getStream();
						ValueFactory valueFactory = resSession.getValueFactory();
						contentValue = valueFactory.createBinary(content);
						this.logger.info("+++++++++++++++++++COPIED FILE STOP");
						Node toSave = (Node) request.getResourceResolver().getResource(nodePath.substring(0,nodePath.lastIndexOf("/"))).adaptTo((Class) Node.class);
						this.logger.info("+++++++++++++++++++NODE DELEYTE PATH:"
								+ savedNode.getPath());
						this.logger
								.info("+++++++++++++++++++NODE DELEYTE PATH UP LEVEL:"
										+ toSave.getPath());
						savedNode.remove();
						toSave.save();
						resSession.save();
						this.logger.info("+++++++++++++++++++NODE DELEYTED");
				        
				} 
					else {
						// delete the older version of the nodes for given applicationNumber
						this.logger.info("+++++++++++++++++++NODE DELETE START");
						Node toSave = (Node) request.getResourceResolver().getResource(nodePath.substring(0,nodePath.lastIndexOf("/"))).adaptTo((Class) Node.class);
						this.logger.info("+++++++++++++++++++NODE DELEYTE PATH:"
								+ savedNode.getPath());
						this.logger
								.info("+++++++++++++++++++NODE DELEYTE PATH UP LEVEL:"
										+ toSave.getPath());
						savedNode.remove();
						toSave.save();
						resSession.save();
						this.logger.info("+++++++++++++++++++NODE DELEYTED");
					} 	
				
				}catch (ItemNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (RepositoryException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				counter++;

				}
				
			
			
		//make WS call to submit data
        try {
			passSubmittedData(contentValue.getStream(), resResolver);
		} catch (RepositoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
			
	
	

        
    
}

private void passSubmittedData(InputStream content, ResourceResolver resResolver) throws IOException {
	
	//String param = "{\"applicationNumber\":\"23\", \"userName\":\"\", \"sessionToken\":\"401064664\", \"stage\":\"NEW_APPLICATION\"}";
	
	//convert Inputstream into string
	BufferedReader br = null;
	StringBuilder sb = new StringBuilder();

	String line;
	try {

		br = new BufferedReader(new InputStreamReader(content));
		while ((line = br.readLine()) != null) {
			sb.append(line);
		}

	} catch (IOException e) {
		e.printStackTrace();
	} finally {
		if (br != null) {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

		
	// get the WS URL from the externalizer
	Externalizer externalizer = resResolver.adaptTo(Externalizer.class);
	String getServiceURL = "http://wasau301mel0050.globaltest.anz.com:9080/OBPHL/rest/application/saveXMLRequest?status=DEComp";
	
	//String getServiceURL = externalizer.externalLink(resResolver, "saveXMLRequest","") + "?status=DEComp";
	logger.info("EXT ++++++" + getServiceURL + "++++");
	logger.info("EXT ++++++" + content.toString() + "++++");
	//logger.info("EXT ++++++" + content.toString());
	
	
	
	logger.info("Rquest XML : " + sb.toString());
	logger.info("Rquest XML sfter");
	
	// create connection
	URL URLobj = new URL(getServiceURL);
    URLConnection connection = URLobj.openConnection();
	
	String charset = "UTF-8"; 
	  connection.setDoOutput(true); // Triggers POST.
	  connection.setRequestProperty("Accept-Charset", charset);
	  connection.setRequestProperty("Content-Type", "application/xml;charset=" + charset);

	  DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
	  
	  		wr.write(sb.toString().getBytes()); // write the content data into the DataOutPutStream of the connection
			wr.flush();
			wr.close();

		String responseCode = connection.getContent().toString();
		logger.info("INSIDE GET LKOGGER responseCode+++++++++++" + responseCode);
	BufferedReader in = new BufferedReader(
	        new InputStreamReader(connection.getInputStream()));
	String inputLine;
	StringBuffer newresponse = new StringBuffer();

	while ((inputLine = in.readLine()) != null) {
		newresponse.append(inputLine);
	}
	in.close();
	
}

private List<String> getNodeSet(Session session, String user_name,
		String status, String app_no) {
	List<String> tempNodeSet = new ArrayList<String>();
	// create SQl statement for fetching all current AEM nodes for given applicationNumber
	String sqlStatement = "SELECT * FROM [sling:Folder] AS s WHERE bApplicationNumber = '"
			+ app_no
			+ "' "
			+ "and ISDESCENDANTNODE(s,'/content/usergenerated/content/forms/af/ops/') "
			+ "ORDER BY [jcr:created] DESC";
	this.logger
			.info("SQL++++++++++++++++++++++++++++++++++" + sqlStatement);
	tempNodeSet = this.getCurrentPaths(session, sqlStatement, tempNodeSet);
	return tempNodeSet;
}

private List<String> getCurrentPaths(Session session, String sqlStatement,
		List<String> tempNodeSet) {
	QueryManager queryManager = null;
	Query query = null;
	try {
		queryManager = session.getWorkspace().getQueryManager();
	} catch (RepositoryException e) {
		e.printStackTrace();
	}
	try {
		query = queryManager.createQuery(sqlStatement, "JCR-SQL2");
		QueryResult result = null;
		result = query.execute();
		this.logger.info("THE RESULT ++++++++++++++++++++++++"
				+ result.toString());
		NodeIterator nodeIter = result.getNodes();
		this.logger.info("CHECK ++++++++++++++" + nodeIter.getSize());
		while (nodeIter.hasNext()) {
			String tempNodePath = nodeIter.nextNode().getPath().trim();
			this.logger.info("INTO LOOP++++++++++++++++++++++++++");
			tempNodeSet.add(tempNodePath);
			this.logger
					.info("NODES FOUND------------------------------------------------------------------------"
							+ tempNodePath + "--------------------");
		}
	} catch (RepositoryException e) {
		e.printStackTrace();
	}
	return tempNodeSet;
}

protected void bindResourceResolverFactory(
		ResourceResolverFactory resourceResolverFactory) {
	this.resourceResolverFactory = resourceResolverFactory;
}

protected void unbindResourceResolverFactory(
		ResourceResolverFactory resourceResolverFactory) {
	if (this.resourceResolverFactory == resourceResolverFactory) {
		this.resourceResolverFactory = null;
	}
}
}