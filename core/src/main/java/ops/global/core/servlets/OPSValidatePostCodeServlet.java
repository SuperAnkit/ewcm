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
        	logger.info("REDAER LOG++postCode++++++++++++++++++++++" + postCode );
        	logger.info("REDAER LOG++++++++state++++++++++++++++" + state );
        	logger.info("REDAER LOG+++++++city+++++++++++++++++" + city );

        	GetMethod method = null;
        	String isValid = null;
			String getServiceURL = OPSConstants.WS_VALIDATE_POSTCODE_URL.replace("_postcode", postCode).replace("_city", city).replace("_state", state).replaceAll(" ","%20");
        	// create WS connection
        	method = new GetMethod(getServiceURL);
        	
        	logger.info("REDAER LOG++++++++++++++++++++++++" + getServiceURL );
        	
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
			
			logger.info("REDAER LOG++ RETURN++++++++++++++++++++++" + newresponse.toString() + "------" );
			logger.info("REDAER LOG++ STATUS++++++++++++++++++++++" + statusCode + "------" );
			
			
        	logger.info("REDAER FOR POSTCODE++++++++++++++++++++++" + newresponse.toString());
        	response.setContentType("application/text");
            response.getWriter().write(newresponse.toString());
        	
        }
        	

    }

	

