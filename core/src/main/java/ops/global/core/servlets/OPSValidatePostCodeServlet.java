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

import com.day.cq.commons.Externalizer;


@SlingServlet(paths = "/bin/validatePostcode",
        methods = {"POST"},
        metatype = true)
public class OPSValidatePostCodeServlet extends SlingAllMethodsServlet {
    
	private static final long serialVersionUID = 1L;  
	HttpClient client = new HttpClient();
   
        private Logger log = LoggerFactory.getLogger(OPSValidatePostCodeServlet.class);
     
        protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException,
            IOException {
    	
        	// fetch all the parameters
        	String postCode = request.getParameter("postcode");
        	String state = request.getParameter("state");
        	String city = request.getParameter("city");
 
        	GetMethod method = null;
        	
        	//Get the URL based on run-mode
    		Externalizer externalizer = request.getResourceResolver().adaptTo(Externalizer.class);
    		String webServiceURL = externalizer.externalLink(request.getResourceResolver(),"WSRequest","");
        	
        	
			String getServiceURL = OPSConstants.WS_VALIDATE_POSTCODE_URL.replace("_postcode", postCode).replace("_city", city).replace("_state", state).replaceAll(" ","%20").replace(OPSConstants.WS_URL, webServiceURL);
        	// create WS connection
        	method = new GetMethod(getServiceURL);
        	        	
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
			
        	response.setContentType("application/text");
            response.getWriter().write(newresponse.toString());
        	
        }
        	

    }

	

