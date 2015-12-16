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


@SlingServlet(paths = "/bin/DDfromStaticModel",
        methods = {"POST"},
        metatype = true)
public class OPSSelectFromStaticModelServlet extends SlingAllMethodsServlet {
    
	private static final long serialVersionUID = 1L;  
	HttpClient client = new HttpClient();
	String toReturnResults = null;
   
        private Logger logger = LoggerFactory.getLogger(OPSSelectFromStaticModelServlet.class);
     
        protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException,
            IOException {
    	        	
        	// fetch all the parameters
        	String entityName = request.getParameter("entity");
        	String isMandatory = request.getParameter("isMandatory");
        	
        	GetMethod method = null;
        	
        	logger.info("VALUE of entityName++" + entityName + "+++");
        	logger.info("VALUE of isMandatory++" + isMandatory + "+++");
        	
        	if (!isMandatory.equals("true")) {
        		logger.info("INTO NOT TRUE LOOP+++++++++++++++++++++++");
        		if (entityName.length() > 0 ) {
            		String getServiceURL = OPSConstants.WS_GET_STATIC_MODEL_URL.replace("entityName", entityName);
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
			} else {
				logger.info("INTO TRUE LOOP+++++++++++++++++++++++");
				if (entityName.length() > 0 ) {
	        		String getServiceURL = OPSConstants.WS_GET_STATIC_MODEL_URL.replace("entityName", entityName);
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
	    			
	    			toReturnResults = newresponse.toString();
				} else {
					toReturnResults = "[]";
				}

			}
        	
        	
        	
        	
	

	
	
	//toReturnResults = newresponse.toString();
	logger.info("FINAL OP LOG++++++++++++++++++++++++" + toReturnResults);

	response.setContentType("application/json");
    response.getWriter().write(toReturnResults.toString());
	

    }

    }