package ops.global.core.servlets;

import javax.jcr.query.Query;
import javax.jcr.query.QueryManager;
import javax.jcr.query.QueryResult;
import javax.jcr.NodeIterator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import javax.jcr.Binary;
import javax.jcr.ItemNotFoundException;
import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.UnsupportedRepositoryOperationException;
import javax.jcr.ValueFactory;
import javax.servlet.ServletException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
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

@SlingServlet(paths = "/bin/opsSearch", methods = { "POST" }, metatype = true)
public class OPSSearchServlet extends SlingAllMethodsServlet {

	private static final long serialVersionUID = 1L;
	private static final String USER_NAME = "user_name";
	private static final String USER_GRP = "user_group";
	private static final String SEARCH_KEYWORD = "frmSearch_txt";
	private static final String DOC_TYPE = "docType";
	private static final String MAKER_GROUP = "maker_group";
	private static final String CHECKER_GROUP = "checker_group";
	private static final String READER_GROUP = "reader_group";
	private static final String DRAFT_STATUS = "ops:draft";
	private static final String REVIEW_STATUS = "ops:review";
	private static Set<String> searchNodeSet;
	private static final String FP_FORMS_QRY_STRING = "?wcmmode=disabled&dataRef=crx://";
	private static final String FP_FORMS_PATH = "/content/forms/af/ops/mortgage.html";
	private static final String FP_READER_FORMS_PATH = "/content/ops/readerpage.html";
	private static final String HIDDEN_PATH = "/content/usergenerated/hidden/";
	private static final String RESULT_FOUND = "Result found";
	private static final String RESULT_NOT_FOUND = "No Result Found";
	private String searchResult = RESULT_NOT_FOUND;
	private boolean formInJCR = false;
	private String search_keyword;
	private String document_type;
	HttpClient client = new HttpClient();
	private String toReturnResults = null;
	private String newFormNode;
	int failedResponse = 204;

	GetMethod method = null;

	@Reference
	private ResourceResolverFactory resourceResolverFactory;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	protected void doPost(SlingHttpServletRequest request,
			SlingHttpServletResponse response) throws ServletException,
			IOException {
		logger.info("+++++++++++++++++++INTO OPS SEARCH");

		String user_name = request.getParameter(USER_NAME);
		String user_group = request.getParameter(USER_GRP);
		search_keyword = request.getParameter(SEARCH_KEYWORD);
		document_type = request.getParameter(DOC_TYPE);
		logger.info("+++++++++++++++++++GOT ALL PARA");

		logger.info("+++++++++++++++++++GOT KWRD" + search_keyword);
		logger.info("+++++++++++++++++++GOT TYPE" + document_type);
		logger.info("+++++++++++++++++++GOT GROUP" + user_group);
		logger.info("+++++++++++++++++++GOT NAME" + user_name);

		ResourceResolver resResolver = null;
		try {
			resResolver = resourceResolverFactory
					.getAdministrativeResourceResolver(null);
			// create a session object
			Session resSession = resResolver.adaptTo(Session.class);

			if (user_group.equals(MAKER_GROUP)) {
				// get user draft forms from JCR
				try {
					searchNodeSet = getNodePaths(resSession, search_keyword,
							DRAFT_STATUS);
				} catch (ItemNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (RepositoryException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				logger.info("NEW MSG++++++++++++++++++++++"
						+ searchNodeSet.isEmpty());
				if (searchNodeSet.isEmpty()) {

					logger.info("IN THE DB SEARCH SEC");
					// get from DB
					//String dbContent = getXMLDataFromDB(search_keyword,
					//		document_type, resResolver);
					InputStream iPStream = getXMLDataFromDB(search_keyword,
									document_type, resResolver);
					
					//logger.info("+++++++++++++++++++XML Content" + dbContent + "++++");

					
					if (iPStream != null) { // found in DB
						searchResult = RESULT_FOUND;
						



						Node savedNode;
						try {
							savedNode = resSession.getNode(HIDDEN_PATH);

							if (!savedNode.hasNode(search_keyword + ".xml")) { // check
																				// if
																				// the
																				// node
																				// already
																				// exists
								// all the processing
								createHiddenFile(resSession, iPStream, savedNode);

								logger.info("+++++++++++++++++++COPIED FILE STOP");
							} else {
								// delete the current node
								String nodePath = HIDDEN_PATH + search_keyword + ".xml";
								
								logger.info("+++++++++++++++++++NODE DELETE START" + nodePath);
								
								//Node toRemove = request.getResourceResolver().getResource(nodePath).adaptTo(Node.class);
							   	Node toRemove = resSession.getNode(nodePath);

								logger.info("+++++++++++++++++++NODE DELEYTE PATH:"
										+ savedNode.getPath());
								logger.info("+++++++++++++++++++NODE DELEYTE PATH UP LEVEL:"
										+ toRemove.getPath());
								toRemove.remove();
								// toDelete.save();
								savedNode.save();
								resSession.save();
								logger.info("+++++++++++++++++++NODE DELEYTED");
								createHiddenFile(resSession, iPStream, savedNode);
							}

						} catch (ItemNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (RepositoryException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						logger.info("+++++++++++++++++++COPIED FILE START");
						// //////////////////////

						toReturnResults = "<a href='"
								+ FP_FORMS_PATH
								+ FP_FORMS_QRY_STRING
								+ HIDDEN_PATH
								+ search_keyword
								+ ".xml' class='list-group-item'><span class='glyphicon'></span> "
								+ search_keyword + " </a>";

					} else {
						toReturnResults = null;
						searchResult = RESULT_NOT_FOUND;
						logger.info("NOT NULL IN ELSE");
					}

				} else {
					toReturnResults = null;
					for (String nodePath : searchNodeSet) {
						logger.info("IN THE DB SEARCH SEC NODE PTH" + nodePath
								+ "+++");
						if (nodePath == "") {
						} else {
							searchResult = RESULT_FOUND;
							// prepare list of all results
							for (String set : searchNodeSet) {
								logger.info("LOOP NODE FINAL++++++++++++++++++++++++++++++++++++"
										+ set);

								// searchResultList.add("<a href='"+
								// FP_FORMS_PATH + FP_FORMS_QRY_STRING + set
								// +"' class='list-group-item'><span class='glyphicon glyphicon-camera'></span> "+
								// search_keyword +" </a>");
								toReturnResults = "<a href='"
										+ FP_FORMS_PATH
										+ FP_FORMS_QRY_STRING
										+ set
										+ "' class='list-group-item'><span class='glyphicon'></span> "
										+ search_keyword + " </a>";
								logger.info("LOOP NODE FINAL++++++++++++++++++++++++++++++++++++"
										+ toReturnResults);
								// System.out.println(set);
							}
						}

					}

				}

				logger.info("LOG FOR SEARCH RESULT INITIAL++++++++++++++++++++++++"
						+ searchResult);

			}
			if (user_group.equals(CHECKER_GROUP)) {
				// get user review forms
				try {
					searchNodeSet = getNodePaths(resSession, search_keyword,
							REVIEW_STATUS);
				} catch (ItemNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (RepositoryException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				for (String nodePath : searchNodeSet) {
					logger.info("LOOP NODE++++++++++++++++++++++++++++++++++++"
							+ nodePath);
					if (nodePath == "") {

						searchResult = RESULT_NOT_FOUND;

					} else {
						searchResult = RESULT_FOUND;
					}

				}

				toReturnResults = null;
				// prepare list of all results
				for (String set : searchNodeSet) {
					logger.info("LOOP NODE FINAL++++++++++++++++++++++++++++++++++++"
							+ set);


					toReturnResults = "<a href='"
							+ FP_FORMS_PATH
							+ FP_FORMS_QRY_STRING
							+ set
							+ "' class='list-group-item'><span class='glyphicon'></span> "
							+ search_keyword + " </a>";
					logger.info("LOOP NODE FINAL++++++++++++++++++++++++++++++++++++"
							+ toReturnResults);
					// System.out.println(set);
				}

			}
			if (user_group.equals(READER_GROUP)) {
				toReturnResults = null;

				logger.info("+++++++++++++++++++INTO READR LOOP");
				// get details for DB

				String newresponse = getApplicationDataFromDB(search_keyword,
						document_type, resResolver);

				if (!newresponse.toString().contains("\"applicationID\":null")) {

					toReturnResults = "<a href='"
							+ FP_READER_FORMS_PATH
							+ "#?loanNum="
							+ search_keyword
							+ "&docType="
							+ document_type
							+ "' class='list-group-item'><span class='glyphicon'></span> "
							+ search_keyword + " </a>";
					logger.info("LOOP NODE FINAL++++++++++++++++++++++++++++++++++++"
							+ toReturnResults);
					// System.out.println(set);
				}
				// logger.info("LOOP NODE FINAL++++++++++++++++++++++++++++++++++++"
				// + set);

				// print result
				logger.info("RES++++++++++++++++++++++:"
						+ newresponse.toString());

			}

			logger.info("FINAL LOGGER+++++++++" + toReturnResults);

			if (toReturnResults != null) {
				response.getWriter().write(toReturnResults);
			} else {
				response.getWriter().write("");
			}

			// response.getWriter().write("<a href='"+ FP_FORMS_PATH +
			// FP_FORMS_QRY_STRING + search_keyword
			// +"' class='list-group-item'><span class='glyphicon glyphicon-camera'></span> "+
			// search_keyword +" </a>");;

		} catch (LoginException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}



	private InputStream getXMLDataFromDB(String search_keyword,
			String document_type, ResourceResolver resResolver) throws IOException {
		// TODO Auto-generated method stub
		String param = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><aemRequestWrapper><afData/><applicationNumber>"+search_keyword+"</applicationNumber><sessionToken>401064664</sessionToken><stage>"+document_type+"</stage></aemRequestWrapper>";
    			
		System.out.println("PARAM "+param);
		
    	logger.info("+++++++++++++++++++INTO READR LOOP PARAM" + param);
    	
    
    	Externalizer externalizer = resResolver.adaptTo(
				Externalizer.class);
		String getServiceURL = externalizer.externalLink(resResolver, "getXMLRequest","");
		logger.info("EXT ++++++" + getServiceURL);
    	URL URLobj = new URL(getServiceURL);
    	
		//URLConnection connection = URLobj.openConnection();
		HttpURLConnection connection = (HttpURLConnection)URLobj.openConnection();
    	String charset = "UTF-8"; 
    	  //URLConnection connection = new URL(url).openConnection();
    	  connection.setDoOutput(true); // Triggers POST.
    	  connection.setRequestProperty("Accept-Charset", charset);
    	  connection.setRequestProperty("Content-Type", "application/xml;charset=" + charset);

    	  DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
    	  	wr.writeBytes(param);
  			wr.flush();
  			wr.close();

  		
		/*BufferedReader in = new BufferedReader(
		        new InputStreamReader(connection.getInputStream()));
		String inputLine;
		
		
	
		
		
		StringBuffer newresponse = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			newresponse.append(inputLine);
		}
		in.close();*/
		//logger.info("GET DATA+++++++++" + newresponse.toString());
		
  			int code = connection.getResponseCode();
  			logger.info("RES CODE" + code);
		
		if (code != failedResponse ) {
			return connection.getInputStream();
		} else {
			return null;
		}
		
		
		
		}
		
	

	private void createHiddenFile(Session resSession, InputStream iPStream,
			Node savedNode) throws UnsupportedRepositoryOperationException,
			RepositoryException, FileNotFoundException {
		// TODO Auto-generated method stub
		logger.info("INSIDE HIDDEN MEHOTD ++++" );
		String newmimeType = "application/octet-stream";
		// Node node = session.getNode(parentNode);
		

		
		//FileInputStream fileInputStream = new FileInputStream("C:\\AEM\\Temp\\test.txt");

		
		//actual code
		logger.info("CONNT VALUE DATA ++++before+++++");

	   	Node node = resSession.getNode("/content/usergenerated/hidden");
		ValueFactory newvalueFactory = resSession.getValueFactory();
		Binary newcontentValue = newvalueFactory.createBinary(iPStream);
		logger.info("CONNT VALUE DATA ++++AFTER+++++");
		Node tempFileNode = node.addNode(search_keyword + ".xml",
				"nt:file");
		logger.info("CONNT VALUE DATA ++++1 just before+++++");

		tempFileNode.addMixin("mix:referenceable");
		logger.info("CONNT VALUE DATA ++++2 just before+++++");

		Node tempResNode = tempFileNode.addNode("jcr:content", "nt:resource");
		logger.info("CONNT VALUE DATA ++++3just before+++++");

		tempResNode.setProperty("jcr:mimeType", newmimeType);
		logger.info("CONNT VALUE DATA ++++just before+++++");

		tempResNode.setProperty("jcr:data", newcontentValue);
		logger.info("CONNT VALUE DATA ++++" + newcontentValue.toString() + "+++++");
		Calendar tempLastModified = Calendar.getInstance();
		tempLastModified.setTimeInMillis(tempLastModified.getTimeInMillis());
		tempResNode.setProperty("jcr:lastModified", tempLastModified);
		savedNode.save();
		tempResNode.save();
		node.save();
		resSession.save();

	}

	private String getApplicationDataFromDB(String search_keyword,
			String document_type, ResourceResolver resResolver)
			throws IOException {
		// TODO Auto-generated method stub
		String param = "{\"applicationNumber\":\""
				+ search_keyword
				+ "\", \"userName\":\"\", \"sessionToken\":\"401064664\", \"stage\":\""
				+ document_type + "\"}";

		logger.info("+++++++++++++++++++INTO READR LOOP PARAM" + param);

		Externalizer externalizer = resResolver.adaptTo(Externalizer.class);
		String getServiceURL = externalizer.externalLink(resResolver,
				"readerGet", "");
		logger.info("EXT ++++++" + getServiceURL);
		URL URLobj = new URL(getServiceURL);

		URLConnection connection = URLobj.openConnection();

		String charset = "UTF-8";
		// URLConnection connection = new URL(url).openConnection();
		connection.setDoOutput(true); // Triggers POST.
		connection.setRequestProperty("Accept-Charset", charset);
		connection.setRequestProperty("Content-Type",
				"application/json;charset=" + charset);

		DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
		wr.writeBytes(param);
		wr.flush();
		wr.close();

		BufferedReader in = new BufferedReader(new InputStreamReader(
				connection.getInputStream()));
		String inputLine;
		StringBuffer newresponse = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			newresponse.append(inputLine);
		}
		in.close();

		return newresponse.toString();
	}

	private Set<String> getNodePaths(Session session, String loanAppNo,
			String status) throws ItemNotFoundException, RepositoryException {
		// TODO Auto-generated method stub
		Set<String> tempNodeSet = new HashSet<String>();

		String sqlStatement = "SELECT * FROM [sling:Folder] AS s WHERE "
				+ "bApplicationNumber = '"
				+ search_keyword
				+ "' "
				// + "and user_name = '"+ USER_NAME +"' "
				+ "and state = '"
				+ status
				+ "' "
				+ "and ISDESCENDANTNODE(s,'/content/usergenerated/content/forms/af/ops/')";

		logger.info("SQL STMT++++++++++++++++++++++++++++++++++++++++"
				+ sqlStatement);

		tempNodeSet = getReferenceNodePaths(session, sqlStatement, tempNodeSet,
				search_keyword);

		return tempNodeSet;

	}

	/***
	 * Gets all the pages where the node is referenced
	 *
	 * @param session
	 *            , sqlStatement, nodeSet
	 * @param loan_no
	 * @return nodeSet with all Page references
	 */
	private Set<String> getReferenceNodePaths(Session session,
			String sqlStatement, Set<String> tempNodeSet, String search_keyword) {
		// TODO Auto-generated method stub
		QueryManager queryManager = null;
		Query query = null;

		try {
			queryManager = session.getWorkspace().getQueryManager();
		} catch (RepositoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			query = queryManager.createQuery(sqlStatement, "JCR-SQL2");
			// Execute the query and get the results ...
			QueryResult result = null;
			result = query.execute();

			// Iterate over the nodes in the results ...
			NodeIterator nodeIter = result.getNodes();

			while (nodeIter.hasNext()) {

				String tempNodePath = nodeIter.nextNode().getPath().trim();
				logger.info("TEMPPATH++++++++++++++++++++++++++++++++++++"
						+ tempNodePath);
				tempNodeSet.add(tempNodePath + "/" + search_keyword + ".xml");
			}

		} catch (RepositoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		logger.info("returning from QUERY+++++++++++++++++++++++++++++++++++++++");
		return tempNodeSet;
	}

}
