package ops.sightly.components;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.jcr.ItemNotFoundException;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.Value;
import javax.jcr.query.Query;
import javax.jcr.query.QueryManager;
import javax.jcr.query.QueryResult;

import org.apache.jackrabbit.api.security.user.Authorizable;
import org.apache.jackrabbit.api.security.user.Group;
import org.apache.jackrabbit.api.security.user.UserManager;
import org.apache.sling.api.resource.ResourceResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.sightly.WCMUse;

public class TabularFormDisplay extends WCMUse {

	// Initializing all the constants
	private final Logger log = LoggerFactory.getLogger(TabularFormDisplay.class);
	private static String USER_NAME;
	private static final String MAKER_GROUP = "maker_group";
	private static final String CHECKER_GROUP = "checker_group";
	private static final String READER_GROUP = "reader_group";
	private static final String FP_FORMS_QRY_STRING = "?wcmmode=disabled&dataRef=crx://";
	private static final String FP_FORMS_PATH = "/content/forms/af/ops/mortgage.html";
	private static final String MY_FORMS = "my_forms_tab";
	private static final String ALL_FORMS = "all_forms_tab";
	private String draftDisplay;
	private String reviewDisplay;
	private String workItemDisplay;
	private static final String DRAFT_STATUS = "ops:draft";
	private static final String REVIEW_STATUS = "ops:review";
	private static final String BLOCK_VALUE = "DisplayBlock";
	private static final String NONE_VALUE = "DisplayNone";
	
	private static final String FORM_USER_LASTNAME = "profile/familyName";
    private static final String FORM_USER_FIRSTNAME = "profile/givenName";
    
	private List draftFormLinks = new ArrayList(); 
	private List reviewFormLinks = new ArrayList(); 
	private List formLinks = new ArrayList();
	private List draftFormTitles = new ArrayList();
	private List reviewFormTitles = new ArrayList();
	private List formTitles = new ArrayList();

	private List draftModifiedDateSet = new ArrayList();
	private List reviewModifiedDateSet = new ArrayList();
	private List formModifiedDateSet = new ArrayList();
	
	private List draftUserInfoSet = new ArrayList();
	private List reviewUserInfoSet = new ArrayList();
	private List formUserInfoSet = new ArrayList();
	
	private List draftLinkSet = new ArrayList();
	private List reviewLinkSet = new ArrayList();
	private List workingItemsLinkSet = new ArrayList();

	

	



		@Override
	public void activate() throws Exception {

			//get the user
			USER_NAME = getRequest().getUserPrincipal().getName();
			UserManager userManager = getResourceResolver().adaptTo(UserManager.class);
	        Authorizable opsBluPrintUser = userManager.getAuthorizable(getRequest().getUserPrincipal());

	        // get all the memberships for the user
			Iterator<Group> groups = opsBluPrintUser.memberOf();
			for (;groups.hasNext();) {
	        		String groupName = groups.next().getPrincipal().getName();
	        		
	        		Set<String> draftNodeSet = new HashSet<String>();
	        		Set<String> reviewNodeSet = new HashSet<String>();

					if (groupName.equals(MAKER_GROUP)) {	
						log.info("GROUPNAME++++++++++++++++++++++++++++++++++" + groupName);
	        			//get user draft Nodes
						 Set<String> tempDraftSet = getNodeSet(
								getResourceResolver().adaptTo(Session.class),
								USER_NAME, DRAFT_STATUS, MY_FORMS);
						 //get all user workitems
						 workingItemsLinkSet = getSavedNodeSet(
									getResourceResolver().adaptTo(Session.class),
									USER_NAME, getResourceResolver());
						 
						 
	        			// get all the node links
	        			draftFormLinks = getNodePaths(getResourceResolver().adaptTo(Session.class), tempDraftSet);
	        			// get all the titles for the nodes
	        			draftFormTitles = getNodeTitles(getResourceResolver().adaptTo(Session.class), tempDraftSet);
	        			// get all the modified date details
	        			draftModifiedDateSet = getNodeModifiedDates(getResourceResolver().adaptTo(Session.class), tempDraftSet);
	        			// get the user details for the nodes
	        			draftUserInfoSet = getNodeUserInfo(getResourceResolver().adaptTo(Session.class), tempDraftSet, getResourceResolver());
	        			
	        			//create set of all the links, titles, dates and user info
	        			draftLinkSet = getLinkSet(draftFormLinks, draftFormTitles, draftModifiedDateSet, draftUserInfoSet);
	        			
	        			log.info("GETTIG REVEW DET++++++++++++++++++++++++++++++++++" + groupName);
	        			
	        			// get all draft Nodes
						 Set<String> tempReviewtSet = getNodeSet(
									getResourceResolver().adaptTo(Session.class),
									USER_NAME, DRAFT_STATUS, ALL_FORMS);
	        			log.info("STARTING REVEW DET++++++++++++++++++++++++++++++++++" );
	        			// get all the node links
	        			reviewFormLinks = getNodePaths(getResourceResolver().adaptTo(Session.class), tempReviewtSet);
	        			// get all the titles for the nodes
	        			reviewFormTitles = getNodeTitles(getResourceResolver().adaptTo(Session.class), tempReviewtSet);
	        			// get all the modified date details
	        			reviewModifiedDateSet = getNodeModifiedDates(getResourceResolver().adaptTo(Session.class), tempReviewtSet);
	        			// get the user details for the nodes
	        			reviewUserInfoSet = getNodeUserInfo(getResourceResolver().adaptTo(Session.class), tempReviewtSet, getResourceResolver());
	        			
	        			//create set of all the links, titles, dates and user info
	        			reviewLinkSet = getLinkSet(reviewFormLinks, reviewFormTitles, reviewModifiedDateSet, reviewUserInfoSet);

	        			draftDisplay = BLOCK_VALUE;
	        			reviewDisplay = BLOCK_VALUE;
	        			workItemDisplay = BLOCK_VALUE;
					}
	        		if (groupName.equals(CHECKER_GROUP)) {
	        			log.info("GROUPNAME++++++++++++++++++++++++++++++++++" + groupName);
	        			//get user review-draft Nodes
						 Set<String> tempDraftSet = getNodeSet(
								getResourceResolver().adaptTo(Session.class),
								USER_NAME, REVIEW_STATUS, MY_FORMS);
						//get all review-draft Nodes
						 Set<String> tempReviewtSet = getNodeSet(
									getResourceResolver().adaptTo(Session.class),
									USER_NAME, REVIEW_STATUS, ALL_FORMS);
						 //get user workitems nodes
						 workingItemsLinkSet = getSavedNodeSet(
									getResourceResolver().adaptTo(Session.class),
									USER_NAME, getResourceResolver());
	        			
		        			// get all the node links
	        			draftFormLinks = getNodePaths(getResourceResolver().adaptTo(Session.class), tempDraftSet);
	        			// get all the titles for the nodes
	        			draftFormTitles = getNodeTitles(getResourceResolver().adaptTo(Session.class), tempDraftSet);
	        			// get all the modified date details
	        			draftModifiedDateSet = getNodeModifiedDates(getResourceResolver().adaptTo(Session.class), tempDraftSet);
	        			draftUserInfoSet = getNodeUserInfo(getResourceResolver().adaptTo(Session.class), tempDraftSet, getResourceResolver());
	        			
	        			//create set of all the links, titles, dates and user info
	        			draftLinkSet = getLinkSet(draftFormLinks, draftFormTitles, draftModifiedDateSet, draftUserInfoSet);
	        			
	        			// get all the node links
	        			reviewFormLinks = getNodePaths(getResourceResolver().adaptTo(Session.class), tempReviewtSet);
	        			// get all the titles for the nodes
	        			reviewFormTitles = getNodeTitles(getResourceResolver().adaptTo(Session.class), tempReviewtSet);
	        			// get all the modified date details
	        			reviewModifiedDateSet = getNodeModifiedDates(getResourceResolver().adaptTo(Session.class), tempReviewtSet);
	        			// get the user details for the nodes
	        			reviewUserInfoSet = getNodeUserInfo(getResourceResolver().adaptTo(Session.class), tempReviewtSet, getResourceResolver());
	        			
	        			//create set of all the links, titles, dates and user info
	        			reviewLinkSet = getLinkSet(reviewFormLinks, reviewFormTitles, reviewModifiedDateSet, reviewUserInfoSet);

	        			draftDisplay = BLOCK_VALUE;
	        			reviewDisplay = BLOCK_VALUE;
	        			workItemDisplay = BLOCK_VALUE;

					}
	        		if (groupName.equals(READER_GROUP)) {
	        			log.info("GROUPNAME++++++++++++++++++++++++++++++++++" + groupName);
						//get details for DB and no tab visible for reader
	        			draftDisplay = reviewDisplay = workItemDisplay = NONE_VALUE;

					}



			}

		}





	

	private List<String> getSavedNodeSet(Session session, String user_name, ResourceResolver resourceResolver) throws PathNotFoundException, RepositoryException {
			// TODO Auto-generated method stub
		
		List<String> tempWorkingItemsLinkSet = new ArrayList();
		
		
			//get the root node for the user
			String rootPath = "/content/forms/fp/" + user_name + "/drafts/metadata";
			if (session.nodeExists(rootPath)) {
				Node rootNode = session.getNode(rootPath);
				//check if the form is mortgage form	
				NodeIterator allSavedNodesIter = rootNode.getNodes("mortgage*");
				//get all the underneath nodes
				while (allSavedNodesIter.hasNext()) {

					// get the path
					String tempWorkingItemNodePath = allSavedNodesIter.nextNode().getPath().trim();
					log.info("TEMPPATH++++++++++++++++++++++++++++++++++++"
							+ tempWorkingItemNodePath);
					
					// get the user
					String tempWorkingItemUserName = getUserName(user_name, resourceResolver);
					
					//get the formTitle
					String tempWorkingItemTitle = tempWorkingItemNodePath.substring(tempWorkingItemNodePath.indexOf("mortgage_") + 9, tempWorkingItemNodePath.lastIndexOf("_"));
					
					//generate the URL
					String workingItemURL = "<a href='"+ tempWorkingItemNodePath + ".html" + "' class='list-group-item'><span class='glyphicon'></span> "+ tempWorkingItemTitle +"<span class='badge'> "+ tempWorkingItemUserName +" </span></a>";
					
					//add to the list
					tempWorkingItemsLinkSet.add(workingItemURL);
					
					
					
				}
			
			return tempWorkingItemsLinkSet;
			
			} else {
				return new ArrayList();

			}
			

	}





	private List getNodeUserInfo(Session session, Set<String> nodeSet, ResourceResolver resResolver) throws ItemNotFoundException, RepositoryException {
			// TODO Auto-generated method stub
		for (String set : nodeSet) {
			Node node = session.getNodeByUUID(set);
			String user_name = node.getProperty("user_name").getValue().toString();
			
			formUserInfoSet.add(getUserName(user_name, resResolver));
		}
		
			
			return formUserInfoSet;
		}

	/***
     * Get User Name by concatenating first and lastname
     *
     * @param user_name
     * @param resourceResolver
     * @return String with the Username
     */
    private String getUserName(String user_name,
                               ResourceResolver resourceResolver) throws RepositoryException {
        UserManager userManager = resourceResolver.adaptTo(UserManager.class);
        Authorizable authApprover = userManager.getAuthorizable(user_name);
        String lastName = null;
        String firstName = null;
        String userName = null;
        if (authApprover.hasProperty(FORM_USER_LASTNAME)) {
            Value[] lastNameVal = authApprover.getProperty(FORM_USER_LASTNAME);
            lastName = lastNameVal[0].getString();
        }
        if (authApprover.hasProperty(FORM_USER_FIRSTNAME)) {
            Value[] firstNameVal = authApprover.getProperty(FORM_USER_FIRSTNAME);
            firstName = firstNameVal[0].getString();
        }
        if (firstName == null) {
            userName = lastName.trim();
        } else {
            userName = (firstName + " " + lastName).trim();
        }
        return userName;
    }



	private List getLinkSet(List formLink, List formTitles, List modifiedDateSet, List userInfoSet ) {
			// TODO Auto-generated method stub
		
		List linkSet = new ArrayList();
		for (int i = 0; i < formLink.size(); i++) {
        	
			String tempLinkSet = "<a href='"+ FP_FORMS_PATH + FP_FORMS_QRY_STRING + formLink.get(i) + "/"+ formTitles.get(i) + ".xml" +"' class='list-group-item'><span class='glyphicon'></span> "+ formTitles.get(i) +"<span class='badge'> "+ userInfoSet.get(i) +" </span></a>";
			linkSet.add(tempLinkSet);
			//log.info("START------------------------------------------------------------------------" + tempLinkSet + "---------------------END");
		}
		
		for (int i = 0; i < linkSet.size(); i++) {
			 ;
			 //log.info("ACTUAL START------------------------------------------------------------------------" + linkSet.get(i) + "---------------------ACTUAL END");
		}
		
			return linkSet;
		}





	private List getNodeModifiedDates(Session session,
				Set<String> nodeSet) throws ItemNotFoundException, RepositoryException {
			// TODO Auto-generated method stub
		
		for (String set : nodeSet) {
			Node node = session.getNodeByUUID(set);
			
			/** Today's date */
		    Date today = new Date();
			//formModifiedDateSet.add(node.getProperty("jcr:lastModified").getValue());
		    long timeDiff = today.getTime() - (node.getProperty("jcr:created").getDate().getTime().getTime());
			formModifiedDateSet.add(node.getProperty("jcr:created").getValue());
		    //System.out.println(set);
		}
		
			
			return formModifiedDateSet;
		}





	private List getNodeTitles(Session session, 
				Set<String> nodeSet) throws ItemNotFoundException, RepositoryException {
			// TODO Auto-generated method stub
		

		for (String set : nodeSet) {
			Node node = session.getNodeByUUID(set);
			
			formTitles.add(node.getProperty("bApplicationNumber").getValue());
		    //System.out.println(set);
		}
		
		
		
			return formTitles;
		}
		





	private List getNodePaths(Session session, Set<String> nodeSet) throws ItemNotFoundException, RepositoryException {
			// TODO Auto-generated method stub
		
		
		for (String set : nodeSet) {
			formLinks.add(set);
		    //System.out.println(set);
		}

					return formLinks;
		}

	private Set<String> getNodeSet(Session session, 
			String user_name, String status, String tabValue)
			throws ItemNotFoundException, RepositoryException {
		// TODO Auto-generated method stub
		Set<String> tempNodeSet = new HashSet<String>();

		String sqlStatement;
		if (tabValue.equals(MY_FORMS)) {
			sqlStatement = "SELECT * FROM [sling:Folder] AS s WHERE "
					//+ "loan_no = '"+ LOAN_APP_NO +"' "
					+ "user_name = '"+ USER_NAME +"' "
					+ "and state = '"+ status +"' "
					+ "and ISDESCENDANTNODE(s,'/content/usergenerated/content/forms/af/ops/')";
		} else {
			sqlStatement = "SELECT * FROM [sling:Folder] As s WHERE "
					//+ "loan_no = '"+ LOAN_APP_NO +"' "
					+ "user_name <> '"+ USER_NAME +"' "
					+ "and state = '"+ status +"' "
					+ "and ISDESCENDANTNODE(s,'/content/usergenerated/content/forms/af/ops/')";
		}
		log.info("SQL++++++++++++++++++++++++++++++++++" + sqlStatement);
		
		tempNodeSet = getReferenceNodePaths(session, sqlStatement, tempNodeSet);
		
		
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
			String sqlStatement, Set<String> tempNodeSet) {
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
				tempNodeSet.add(tempNodePath);
				 log.info("NODES FOUND------------------------------------------------------------------------" + tempNodePath + "--------------------");

			}

		} catch (RepositoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return tempNodeSet;
	}
	
	public String getWorkItemDisplay() {
		return workItemDisplay;
	}
	
	public String getDraftDisplay() {
		return draftDisplay;
	}

	public String getReviewDisplay() {
		return reviewDisplay;
	}

	public List getWorkingItemsLinkSet() {
		for (int i = 0; i < workingItemsLinkSet.size(); i++) {
			 ;
			 log.info("DRAFT START------------------------------------------------------------------------" + workingItemsLinkSet.get(i) + "---------------------ACTUAL END");
		}
		return workingItemsLinkSet;
	}
	
	public List getDraftLinkSet() {
		for (int i = 0; i < draftLinkSet.size(); i++) {
			 ;
			 log.info("DRAFT START------------------------------------------------------------------------" + draftLinkSet.get(i) + "---------------------ACTUAL END");
		}
		return draftLinkSet;
	}
	
	public List getReviewLinkSet() {
		for (int i = 0; i < reviewLinkSet.size(); i++) {
			 ;
			 log.info("REVIEW START------------------------------------------------------------------------" + reviewLinkSet.get(i) + "---------------------ACTUAL END");
		}
		return reviewLinkSet;
	}
	
}