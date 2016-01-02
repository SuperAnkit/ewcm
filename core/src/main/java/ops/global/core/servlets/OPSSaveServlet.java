package ops.global.core.servlets;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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
import org.apache.jackrabbit.JcrConstants;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ops.global.core.servlets.OPSConstants;

@SlingServlet(paths = { "/bin/save" }, methods = { "POST" }, metatype = true)
public class OPSSaveServlet extends SlingAllMethodsServlet {
	
	// initializing all the constants
	private static final long serialVersionUID = 1;
	private String newFormNode;
	@Reference
	private ResourceResolverFactory resourceResolverFactory;
	private final Logger logger;

	public OPSSaveServlet() {
		this.logger = LoggerFactory.getLogger(this.getClass());
	}

	protected void doPost(SlingHttpServletRequest request,
			SlingHttpServletResponse response) throws ServletException,
			IOException {
	
		// fetching all the parameters
			String user_name = request.getParameter(OPSConstants.USER_NAME);
			String state = request.getParameter(OPSConstants.STATE);
			String app_no = request.getParameter(OPSConstants.APP_NO);
			ResourceResolver resResolver = null;
		
				try {
					resResolver = this.resourceResolverFactory
							.getAdministrativeResourceResolver(null);
				} catch (LoginException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			
			Session resSession = resResolver.adaptTo(Session.class);
			
			// fetch all the nodes with the given applicationNumber
			List<String> tempSet = this.getNodeSet(resSession, user_name, state,
					app_no);
			int counter = 1; // set counter to get the latest node
			for (String nodePath : tempSet) {
					Node savedNode;
					try {
						savedNode = resSession.getNodeByUUID(nodePath);
						if (counter == 1) {
							this.newFormNode = nodePath;
							Node newSavedNode = resSession.getNodeByUUID(this.newFormNode);
							// update the user_name, state properties of the node
							newSavedNode.setProperty(OPSConstants.USER_NAME, user_name);
							newSavedNode.setProperty(OPSConstants.STATE, state);
							newSavedNode.save();
							InputStream content = newSavedNode.getProperty(JcrConstants.JCR_DATA)
									.getBinary().getStream();
							String mimeType = "application/octet-stream";
							ValueFactory valueFactory = resSession.getValueFactory();
							Binary contentValue = valueFactory.createBinary(content);
							
							// create new node to store XML data
							Node fileNode = newSavedNode.addNode(app_no + ".xml", JcrConstants.NT_FILE);
							fileNode.addMixin(JcrConstants.MIX_REFERENCEABLE);
							Node resNode = fileNode.addNode(JcrConstants.JCR_CONTENT, JcrConstants.NT_RESOURCE);
							resNode.setProperty(JcrConstants.JCR_MIMETYPE, mimeType);
							resNode.setProperty(JcrConstants.JCR_DATA, contentValue);
							Calendar lastModified = Calendar.getInstance();
							lastModified.setTimeInMillis(lastModified.getTimeInMillis());
							resNode.setProperty(JcrConstants.JCR_LASTMODIFIED, lastModified);
							newSavedNode.save();
							resNode.save();
							resSession.save();
					} 
						else {
							// delete the older versions of the node
							Node toSave = (Node) request.getResourceResolver().getResource(nodePath.substring(0,nodePath.lastIndexOf("/"))).adaptTo((Class) Node.class);
							savedNode.remove();
							//savedNode.save();
							toSave.save();
							resSession.save();
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
			
			logger.info("Save as Draft successful for application {}", app_no);
	}
		
	

	private List<String> getNodeSet(Session session, String user_name,
			String status, String app_no) {
		List<String> tempNodeSet = new ArrayList<String>();
		
		// sql statement to get all the AEM nodes for given applicationNumber
		String sqlStatement = "SELECT * FROM [sling:Folder] AS s WHERE bApplicationNumber = '"
				+ app_no
				+ "' "
				+ "and ISDESCENDANTNODE(s,'"+ OPSConstants.FORMS_FOLDER_PATH +"') "
				+ "ORDER BY [jcr:created] DESC";
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
			NodeIterator nodeIter = result.getNodes();
			while (nodeIter.hasNext()) {
				String tempNodePath = nodeIter.nextNode().getPath().trim();
				tempNodeSet.add(tempNodePath);
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