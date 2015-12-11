package ops.global.core.servlets;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.ServletException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.commons.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@SlingServlet(paths = "/bin/DDstatementcycle",
        methods = {"POST"},
        metatype = true)
public class OPSSelectStatementCycleServlet extends SlingAllMethodsServlet {
    
	private static final long serialVersionUID = 1L;  
	HttpClient client = new HttpClient();
	String toReturnResults = null;
   
        private Logger logger = LoggerFactory.getLogger(OPSSelectSubProductServlet.class);
     
        protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException,
            IOException {
    	        	
        	// fetch all the parameters
        	String ldProductCode = request.getParameter("ldProductCode");
        	
        	GetMethod method = null;
        	
        	logger.info("VALUE of ldProductCode++" + ldProductCode + "+++");
        	logger.info("VALUE of ldProductCode" + ldProductCode.length());
        	
        	if (ldProductCode.length() > 0 ) {
        		String getServiceURL = OPSConstants.WS_GET_STATEMENT_CYCLE_CODE_URL.replace("ldProductCode", ldProductCode);
            	// create WS connection
            	method = new GetMethod(getServiceURL);
            	
            	logger.info("REDAER LOG++++++++++++++++++++++++" + getServiceURL);
            	
            	// Execute the method.
    			int statusCode = client.executeMethod(method);
    			
    			InputStream inputStream = method.getResponseBodyAsStream();

    	  		
    			BufferedReader in = new BufferedReader(
    			        new InputStreamReader(inputStream));
    			String inputLine;
    			StringBuffer newresponse = new StringBuffer();

    			while ((inputLine = in.readLine()) != null) {
    				newresponse.append(inputLine);
    				logger.info("IP LINE++++++++++++++++++++++++" + inputLine);
    			}
    			in.close();
    			
    			toReturnResults = newresponse.toString().replace("[", "[\"-1=Select\",");
			} else {
				toReturnResults = "[\"-1=Select\"]";
			}
        	
        	
        	
	

	
	
	//toReturnResults = newresponse.toString();
	logger.info("REDAER LOG++++++++++++++++++++++++" + toReturnResults);

	response.setContentType("application/json");
    response.getWriter().write(toReturnResults.toString());
	

    }

    }