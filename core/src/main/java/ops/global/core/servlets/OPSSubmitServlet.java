package ops.global.core.servlets;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
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

import com.day.cq.commons.Externalizer;

@SlingServlet(paths = { "/bin/submit " }, methods = { "POST" }, metatype = true)
public class OPSSubmitServlet extends SlingAllMethodsServlet {
	// initializing all the constants
	private static final long serialVersionUID = 1;
	private String newFormNode;
	InputStream content;

	@Reference
	private ResourceResolverFactory resourceResolverFactory;
	private final Logger log;

	public OPSSubmitServlet() {
		this.log = LoggerFactory.getLogger(this.getClass());
	}

	protected void doPost(SlingHttpServletRequest request,
			SlingHttpServletResponse response) throws ServletException,
			IOException {

		// fetching all the parameters
		String user_name = request.getParameter(OPSConstants.USER_NAME);
		String state = request.getParameter(OPSConstants.STATE);
		String app_no = request.getParameter(OPSConstants.APP_NO);
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

		Session resSession = (Session) resResolver
				.adaptTo((Class) Session.class);
		List<String> tempSet = this.getNodeSet(resSession, user_name, state,
				app_no);
		int counter = 1;
		Node verifyNode = null;
		for (String nodePath : tempSet) {
			Node savedNode;
			try {
				savedNode = resSession.getNodeByUUID(nodePath);
				if (counter == 1) { // fetching the latest XML file
					this.newFormNode = nodePath;
					Node newSavedNode = resSession
							.getNodeByUUID(this.newFormNode);
					verifyNode = resSession.getNodeByUUID(this.newFormNode);
					newSavedNode.setProperty(OPSConstants.USER_NAME, user_name);
					newSavedNode.setProperty(OPSConstants.STATE, state);
					newSavedNode.save(); // save the properties for the latest
											// sling:Folder

					// update & create XML file under sling:Folder
					content = newSavedNode.getProperty(JcrConstants.JCR_DATA).getBinary()
							.getStream();
					String mimeType = "application/octet-stream";
					ValueFactory valueFactory = resSession.getValueFactory();
					contentValue = valueFactory.createBinary(content);
					Node fileNode = newSavedNode.addNode(app_no + ".xml", JcrConstants.NT_FILE);
					fileNode.addMixin(JcrConstants.MIX_REFERENCEABLE);
					Node resNode = fileNode.addNode(JcrConstants.JCR_CONTENT, JcrConstants.NT_RESOURCE);
					resNode.setProperty(JcrConstants.JCR_MIMETYPE, mimeType);
					resNode.setProperty(JcrConstants.JCR_DATA, contentValue);
					Calendar lastModified = Calendar.getInstance();
					lastModified
							.setTimeInMillis(lastModified.getTimeInMillis());
					resNode.setProperty(JcrConstants.JCR_LASTMODIFIED, lastModified);
					newSavedNode.save();
					resNode.save();
					resSession.save();

				} else {
					// delete the older XML files and folder
					Node toSave = (Node) request
							.getResourceResolver()
							.getResource(
									nodePath.substring(0,
											nodePath.lastIndexOf("/")))
							.adaptTo((Class) Node.class);
					savedNode.remove();
					toSave.save();
					resSession.save();
				}

			} catch (ItemNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (RepositoryException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			counter++;

		}

		// make ws call to submit data
		try {
			String resArray[] = passSubmittedData(contentValue.getStream(),
					resResolver, user_name);
			response.getWriter().write(resArray[1]);
			int resCode = Integer.parseInt(resArray[0]);
			// revert the sling:folder change back to Draft in case of submit
			// failure
			if (resCode != OPSConstants.RESPONSE_200) {
				verifyNode.setProperty(OPSConstants.STATE,
						OPSConstants.DRAFT_STATUS);
				verifyNode.save();
				resSession.save();
				log.info("Submit failed at BL for application {}", app_no);
			} else {
				log.info("Submit successful at BL for application {}", app_no);
			}

		} catch (RepositoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private String[] passSubmittedData(InputStream content,
			ResourceResolver resResolver, String user_name) throws IOException {
		// TODO Auto-generated method stub

		// Get the URL based on run-mode
		Externalizer externalizer = resResolver.adaptTo(Externalizer.class);
		String webServiceURL = externalizer.externalLink(resResolver,
				"WSRequest", "");

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

		URL URLobj = new URL(OPSConstants.WS_SUBMIT_URL.replace("USER_NAME",
				user_name).replace(OPSConstants.WS_URL, webServiceURL));
		// URLConnection connection = URLobj.openConnection();
		HttpURLConnection connection = (HttpURLConnection) URLobj
				.openConnection();

		String charset = "UTF-8";
		// URLConnection connection = new URL(url).openConnection();
		connection.setDoOutput(true); // Triggers POST.
		connection.setRequestProperty("Accept-Charset", charset);
		connection.setRequestProperty("Content-Type",
				"application/xml;charset=" + charset);

		DataOutputStream wr = new DataOutputStream(connection.getOutputStream());

		wr.write(sb.toString().getBytes());
		wr.flush();
		wr.close();
		StringBuilder response = new StringBuilder();
		BufferedReader buffReader = null;
		String responseline;
		try {

			buffReader = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			while ((responseline = buffReader.readLine()) != null) {
				response.append(responseline);
			}
		} catch (Exception e) {

			buffReader = new BufferedReader(new InputStreamReader(
					connection.getErrorStream()));
			while ((responseline = buffReader.readLine()) != null) {
				response.append(responseline);
			}

		}
		int code = connection.getResponseCode();

		String responseArray[] = new String[2];
		responseArray[0] = Integer.toString(code);
		if (code == OPSConstants.RESPONSE_200) {
			responseArray[1] = "SUCCESS";
		} else {
			responseArray[1] = response.toString();
		}

		return responseArray;

	}

	private List<String> getNodeSet(Session session, String user_name,
			String status, String app_no) {
		List<String> tempNodeSet = new ArrayList<String>();
		String sqlStatement = "SELECT * FROM [sling:Folder] AS s WHERE bApplicationNumber = '"
				+ app_no
				+ "' "
				+ "and ISDESCENDANTNODE(s,'"
				+ OPSConstants.FORMS_FOLDER_PATH
				+ "') "
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