package ops.global.core.servlets;

import aQute.bnd.annotation.ProviderType;

/**
 * Defines additional keys that are available for servlets
 */
@ProviderType
public final class OPSConstants {

    private OPSConstants() {

    }
    
    // Session Token to be passed with each request
	public static final int SESSION_TOKEN = 401064664;
    
	// Web-service URL for submitting the application data to DB for maker groups
    public static final String WS_SUBMIT_URL = "http://wasau301mel0050.globaltest.anz.com:9080/OBPHL/rest/application/saveXMLRequest?status=DEProg&user_name=USER_NAME&session_token=" + SESSION_TOKEN;
    
	// Web-service URL for submitting the application data to DB for checker groups
    public static final String WS_APPROVE_URL = "http://wasau301mel0050.globaltest.anz.com:9080/OBPHL/rest/application/saveXMLRequest?status=DEComp&user_name=USER_NAME&session_token=" + SESSION_TOKEN;
    
    // Web-service URL for fetching the application data from DB for reader groups in JSON format
    public static final String WS_GET_READER_URL = "http://wasau301mel0050.globaltest.anz.com:9080/OBPHL/rest/application/get";
    
	// Web-service URL for fetching the application data from DB for maker groups in XML format
    public static final String WS_GET_APP_URL = "http://wasau301mel0050.globaltest.anz.com:9080/OBPHL/rest/application/getXMLRequest";

    // Web-service URL for fetching the subproduct code data from DB for selected purpose and productcode
    public static final String WS_GET_SUBPRODUCT_CODE_URL = "http://wasau301mel0050.globaltest.anz.com:9080/OBPHL/rest/application/getSubProductCode?PurposeID=ldPurposeCategory&ProductCodeID=ldProductCode";

    // Web-service URL for fetching the subproduct code static data from DB for auto-populated subproduct code option
    public static final String WS_GET_SUBPRODUCT_CODE_VALUE_URL = "http://wasau301mel0050.globaltest.anz.com:9080/OBPHL/rest/application/getSubProductCodeValue?subProductCodeID=ldSubProductCode";

    // Web-service URL for fetching the statement cycle code static data from DB for selected subproduct code
    public static final String WS_GET_STATEMENT_CYCLE_CODE_URL = "http://wasau301mel0050.globaltest.anz.com:9080/OBPHL/rest/application/getStatementCycleByProductCode?ProductCodeID=ldProductCode";

    // Web-service URL for fetching the statement cycle code static data from DB for auto-populated statement cycle code option
    public static final String WS_GET_STATEMENT_CYCLE_CODE_VALUE_URL = "http://wasau301mel0050.globaltest.anz.com:9080/OBPHL/rest/application/getStatementCycleValue?statementCycleID=ldDDAStatementCycle";

    // CRX path for auto-generated form XML
    public static final String FORMS_FOLDER_PATH = "/content/usergenerated/content/forms/af/ops/";

    // QueryString to create pre-fill URL for the forms
    public static final String FP_FORMS_QRY_STRING = "?wcmmode=disabled&dataRef=crx://";
    
    // CRX path for the mortgage form
    public static final String FP_FORMS_PATH = "/content/forms/af/ops/mortgage.html";
    
    // CRX path for reader panel 
	public static final String FP_READER_FORMS_PATH = "/content/ops/readerpage.html";
	
	// CRX path for searched forms
	public static final String HIDDEN_PATH = "/content/usergenerated/hidden/";
	
	// Constant to display search result
	public static final String RESULT_FOUND = "Result found";
	
	// Constant to display search result
	public static final String RESULT_NOT_FOUND = "No Result Found";
	
	// Constant for response code 204
	public static final int RESPONSE_204 = 204;
	
	// Constant for response code 200
	public static final int RESPONSE_200 = 200;
	
	// Constant for maker group name
	public static final String MAKER_GROUP = "maker_group";
	
	// Constant for checker group name
	public static final String CHECKER_GROUP = "checker_group";
	
	// Constant for reader group name
	public static final String READER_GROUP = "reader_group";
	
	// Constant for draft status name
	public static final String DRAFT_STATUS = "ops:draft";
	
	// Constant for review status name
	public static final String REVIEW_STATUS = "ops:review";
	
	// Constant for user name
	public static final String USER_NAME = "user_name";
	
	// Constant for user group
	public static final String USER_GRP = "user_group";
	
	// Constant for the given search keyword
	public static final String SEARCH_KEYWORD = "frmSearch_txt";
	
	// Constant for the selected application type in search
	public static final String DOC_TYPE = "docType";
	
	// Constant for the application state
	public static final String STATE = "state";
	
	// Constant for the application number
	public static final String APP_NO = "application_no";
	
	// Constant for the application number searched
	public static final String SEARCHED_APP_NO = "loanNum";
	
	// Constant for creating param string to search application number in DB for reader group
	public static final String READER_PARAM = "{\"applicationNumber\":\""
			+ SEARCHED_APP_NO + "\", \"userName\":\"\", \"sessionToken\":\""+SESSION_TOKEN+"\", \"stage\":\"" + DOC_TYPE + "\"}";
	
	// Constant for creating param string to search application number in DB for maker group 
	public static final String SEARCH_XML_PARAM = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><aemRequestWrapper><afData/><applicationNumber>"+SEARCH_KEYWORD+"</applicationNumber><sessionToken>"+SESSION_TOKEN+"</sessionToken><stage>"+DOC_TYPE+"</stage></aemRequestWrapper>";
	

}
