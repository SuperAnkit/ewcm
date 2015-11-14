package ops.sightly.components;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.jcr.ItemNotFoundException;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.query.Query;
import javax.jcr.query.QueryManager;
import javax.jcr.query.QueryResult;

import org.apache.jackrabbit.api.security.user.Authorizable;
import org.apache.jackrabbit.api.security.user.Group;
import org.apache.jackrabbit.api.security.user.UserManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.sightly.WCMUse;

public class Search extends WCMUse {

	private final Logger log = LoggerFactory.getLogger(Search.class);
	private static String USER_NAME;
	private static final String MAKER_GROUP = "maker_group";
	private static final String CHECKER_GROUP = "checker_group";
	private static final String READER_GROUP = "reader_group";
	private static final String DRAFT_STATUS = "ops:draft";
	private static final String REVIEW_STATUS = "ops:review";
	private static Set<String> searchNodeSet;
	private String LOAN_APP_NO;
	private String QRY_STRING;
	private boolean formInJCR = false;
	private static final String FP_FORMS_QRY_STRING = "?wcmmode=disabled&dataRef=crx://";
	//private static final String FP_FORMS_PATH = "/content/forms/af/Ops-Forms/BluPrint-Forms/Test2.html";
	private static final String FP_FORMS_PATH = "/content/forms/af/ops/mortgage.html";

	private static final String RESULT_FOUND = "Result found";
	private static final String RESULT_NOT_FOUND = "No Result Found" ;
	private String searchResult = RESULT_NOT_FOUND;

	private List searchResultList = new ArrayList();

	private String FORM_TYPE;

	@Override
	public void activate() throws Exception {
		//LOAN_APP_NO = get("loan_app_no", String.class);
		//FORM_TYPE = get("option_two", String.class);

		
		log.info("++++++++++++++++++++++++++++QRY STRNG" +getRequest().getQueryString().toString()) ;
		QRY_STRING = getRequest().getQueryString().toString();

		LOAN_APP_NO = QRY_STRING.substring(QRY_STRING.indexOf("loan_app_no=") + 13, QRY_STRING.indexOf("&"));
		FORM_TYPE = QRY_STRING.substring(QRY_STRING.indexOf("ftype=") + 7);
		
		
		// get the user
				USER_NAME = getRequest().getUserPrincipal().getName();
				log.info("USERNAME++++++++++++++++++++++++++++++++++" + USER_NAME);
				UserManager userManager = getResourceResolver().adaptTo(
						UserManager.class);
				Authorizable opsBluPrintUser = userManager.getAuthorizable(getRequest()
						.getUserPrincipal());
				//log.info("USERNAME++++++++++++++++++++++++++++++++++" + USER_NAME);
		
		Iterator<Group> groups = opsBluPrintUser.memberOf();
		for (; groups.hasNext();) {
			String groupName = groups.next().getPrincipal().getName();
			log.info("GROUPNAME++++++++++++++++++++++++++++++++++" + groupName);
			
			if (groupName.equals(MAKER_GROUP)) {
				// get user draft forms from JCR
				searchNodeSet = getNodePaths(
						getResourceResolver().adaptTo(Session.class),
						LOAN_APP_NO, DRAFT_STATUS);
				
				for (String nodePath : searchNodeSet) {
				if (nodePath == "") {
					// get from DB
					if (formInJCR) { //found in DB
						searchResult = RESULT_FOUND;

					} else {
						searchResult = RESULT_NOT_FOUND;

					}
				}	
				else {
					searchResult = RESULT_FOUND;
				}
				
				}
				
				log.info("LOG FOR SEARCH RESULT INITIAL++++++++++++++++++++++++" + searchResult);
				

			}
			if (groupName.equals(CHECKER_GROUP)) {
				// get user review forms
				searchNodeSet = getNodePaths(
						getResourceResolver().adaptTo(Session.class),
						LOAN_APP_NO, REVIEW_STATUS);
				
				for (String nodePath : searchNodeSet) {
					log.info("LOOP NODE++++++++++++++++++++++++++++++++++++" + nodePath);
				if (nodePath == "") {
					
						searchResult = RESULT_NOT_FOUND;

					
				}	
				else {
					searchResult = RESULT_FOUND;
				}
				
				}
				
				

			}
			if (groupName.equals(READER_GROUP)) {
				// get details for DB
			}

		}
		
		//prepare list of all results
		for (String set : searchNodeSet) {
			log.info("LOOP NODE FINAL++++++++++++++++++++++++++++++++++++" + set);

			searchResultList.add("<a href='"+ FP_FORMS_PATH + FP_FORMS_QRY_STRING + set +"' class='list-group-item'><span class='glyphicon glyphicon-camera'></span> "+ LOAN_APP_NO +" </a>");
			
			log.info("LOOP NODE FINAL++++++++++++++++++++++++++++++++++++" + "<a href='"+ FP_FORMS_PATH + FP_FORMS_QRY_STRING + set +"' class='list-group-item'><span class='glyphicon glyphicon-camera'></span> "+ LOAN_APP_NO +" </a>");
		    //System.out.println(set);
		}

	}

	private Set<String> getNodePaths(Session session, 
			String loanAppNo, String status)
			throws ItemNotFoundException, RepositoryException {
		// TODO Auto-generated method stub
		Set<String> tempNodeSet = new HashSet<String>();

		String sqlStatement = "SELECT * FROM [sling:Folder] WHERE "
				+ "bApplicationNumber = '"+ LOAN_APP_NO +"' "
				//+ "and user_name = '"+ USER_NAME +"' "
				+ "and state = '"+ status +"' "
				+ "and [jcr:path] like '/content/usergenerated/content/forms/af/%'";
		
		log.info("SQL STMT++++++++++++++++++++++++++++++++++++++++" + sqlStatement);
		
		tempNodeSet = getReferenceNodePaths(session, sqlStatement, tempNodeSet, LOAN_APP_NO);
		
		
		return tempNodeSet;
		
		
	}

		
	public List<String> getSearchResultList() {
		log.info("RETURING RES LIST+++++++++++++++++++++++++++++++++++");
		return searchResultList;
	}
	
	public String getSearchResult() {
		log.info("RETURING SEARCH RESULKT+++++++++++++++++++++++++++++++++++" + searchResult);
		return searchResult;
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
			String sqlStatement, Set<String> tempNodeSet, String loan_no) {
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
				log.info("TEMPPATH++++++++++++++++++++++++++++++++++++" + tempNodePath);
				tempNodeSet.add(tempNodePath + "/" + loan_no + ".xml");
			}

		} catch (RepositoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		log.info("returning from QUERY+++++++++++++++++++++++++++++++++++++++");
		return tempNodeSet;
	}
	
}