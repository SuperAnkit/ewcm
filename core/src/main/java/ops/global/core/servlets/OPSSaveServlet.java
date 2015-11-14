package ops.global.core.servlets;

import java.io.IOException;
import java.io.InputStream;
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

@SlingServlet(paths = { "/bin/save" }, methods = { "POST" }, metatype = true)
public class OPSSaveServlet extends SlingAllMethodsServlet {
	private static final long serialVersionUID = 1;
	private static final String USER_NAME = "user_name";
	private static final String STATE = "state";
	private static final String APP_NO = "application_no";
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
		this.logger.info("+++++++++++++++++++POST LOGGER");
	
			String user_name = request.getParameter("user_name");
			String state = request.getParameter("state");
			String app_no = request.getParameter("application_no");
			ResourceResolver resResolver = null;
		
				try {
					resResolver = this.resourceResolverFactory
							.getAdministrativeResourceResolver(null);
				} catch (LoginException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			
			Session resSession = (Session) resResolver
					.adaptTo((Class) Session.class);
			List<String> tempSet = this.getNodeSet(resSession, user_name, state,
					app_no);
			int counter = 1;
			for (String nodePath : tempSet) {
				this.logger.info("+++++++++++++++++++CHECKING NODE" + nodePath);
				this.logger.info("+++++++++++++++++++COUNTER" + counter);
					Node savedNode;
					try {
						savedNode = resSession.getNodeByUUID(nodePath);
						if (counter == 1) {
							this.newFormNode = nodePath;
							this.logger.info("+++++++++++++++++++COPIED FILE START");
							Node newSavedNode = resSession.getNodeByUUID(this.newFormNode);
							newSavedNode.setProperty("user_name", user_name);
							newSavedNode.setProperty("state", state);
							newSavedNode.save();
							InputStream content = newSavedNode.getProperty("jcr:data")
									.getBinary().getStream();
							this.logger.info("+++++++++++++++++++COPIED FILE STOP");
							String mimeType = "application/octet-stream";
							ValueFactory valueFactory = resSession.getValueFactory();
							Binary contentValue = valueFactory.createBinary(content);
							Node fileNode = newSavedNode.addNode(app_no + ".xml", "nt:file");
							fileNode.addMixin("mix:referenceable");
							Node resNode = fileNode.addNode("jcr:content", "nt:resource");
							resNode.setProperty("jcr:mimeType", mimeType);
							resNode.setProperty("jcr:data", contentValue);
							Calendar lastModified = Calendar.getInstance();
							lastModified.setTimeInMillis(lastModified.getTimeInMillis());
							resNode.setProperty("jcr:lastModified", lastModified);
							newSavedNode.save();
							resNode.save();
							resSession.save();
							response.getWriter().write("++++++++++++++++++++++FROM POST");
					} 
						else {
							this.logger.info("+++++++++++++++++++NODE DELETE START");
							Node toSave = (Node) request.getResourceResolver().getResource(nodePath.substring(0,nodePath.lastIndexOf("/"))).adaptTo((Class) Node.class);
							this.logger.info("+++++++++++++++++++NODE DELEYTE PATH:"
									+ savedNode.getPath());
							this.logger
									.info("+++++++++++++++++++NODE DELEYTE PATH UP LEVEL:"
											+ toSave.getPath());
							savedNode.remove();
							//savedNode.save();
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
					
				
				
				
			
				
		
		
	}
		
	

	private List<String> getNodeSet(Session session, String user_name,
			String status, String app_no) {
		List<String> tempNodeSet = new ArrayList<String>();
		/*String sqlStatement = "SELECT * FROM [sling:Folder] WHERE bApplicationNumber = '"
				+ app_no
				+ "' "
				+ "and [jcr:path] like '/content/usergenerated/content/forms/af/%'"
				+ " ORDER BY [jcr:created] DESC";*/
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