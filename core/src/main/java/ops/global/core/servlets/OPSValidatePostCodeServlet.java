package ops.global.core.servlets;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.ServletException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.commons.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@SlingServlet(paths = "/bin/validatePostcode",
        methods = {"POST"},
        metatype = true)
public class OPSValidatePostCodeServlet extends SlingAllMethodsServlet {
    
	private static final long serialVersionUID = 1L;  
	HttpClient client = new HttpClient();
   
        private Logger logger = LoggerFactory.getLogger(OPSValidatePostCodeServlet.class);
     
        protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException,
            IOException {
    	
        	// fetch all the parameters
        	String postCode = request.getParameter("postcode");
        	String state = request.getParameter("state");
        	String city = request.getParameter("city");
        	String isMandatory = request.getParameter("isMandatory");
        	

        	String toReturnResults = null;
        	
        	if (isMandatory.equals("true")) {
        		//always validate
        		//call ws for validating the postcode
        		toReturnResults = validatePostCode(postCode, city, state);
        		
        	} else {
        		//return true if no value provided and perform validation only if postcode provided
        		if (!postCode.equals(null)) {
					//call ws for validating the postcode
        			toReturnResults = validatePostCode(postCode, city, state);
				} else {
					toReturnResults = "true";
				}

			}
        	
        	logger.info("REDAER FOR POSTCODE++++++++++++++++++++++" + toReturnResults);
        	response.setContentType("application/text");
            response.getWriter().write(toReturnResults);
        	
        	
        	

    }

		private String validatePostCode(String postCode, String city,
				String state) throws HttpException, IOException {
			// TODO Auto-generated method stub
			
        	GetMethod method = null;
			String getServiceURL = OPSConstants.WS_VALIDATE_POSTCODE_URL.replace("_postcode", postCode).replace("_city", city).replace("_state", state);
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
			}
			in.close();
			return newresponse.toString();
		}

    }

