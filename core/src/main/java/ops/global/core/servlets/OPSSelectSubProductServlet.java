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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.commons.Externalizer;

@SlingServlet(paths = "/bin/DDsubproduct", methods = { "POST" }, metatype = true)
public class OPSSelectSubProductServlet extends SlingAllMethodsServlet {

	private static final long serialVersionUID = 1L;
	HttpClient client = new HttpClient();

	private Logger log = LoggerFactory
			.getLogger(OPSSelectSubProductServlet.class);

	protected void doPost(SlingHttpServletRequest request,
			SlingHttpServletResponse response) throws ServletException,
			IOException {

		// fetch all the parameters
		String ldPurposeCategory = request.getParameter("ldPurposeCategory");
		String ldProductCode = request.getParameter("ldProductCode");

		GetMethod method = null;

		// Get the URL based on run-mode
		Externalizer externalizer = request.getResourceResolver().adaptTo(
				Externalizer.class);
		String webServiceURL = externalizer.externalLink(
				request.getResourceResolver(), "WSRequest", "");

		String getServiceURL = OPSConstants.WS_GET_SUBPRODUCT_CODE_URL
				.replace("ldProductCode", ldProductCode)
				.replace("ldPurposeCategory", ldPurposeCategory)
				.replace(OPSConstants.WS_URL, webServiceURL);

		// create WS connection
		method = new GetMethod(getServiceURL);

		// Execute the method.
		int statusCode = client.executeMethod(method);

		try {

			InputStream inputStream = method.getResponseBodyAsStream();

			BufferedReader in = new BufferedReader(new InputStreamReader(
					inputStream));
			String inputLine;
			StringBuffer newresponse = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				newresponse.append(inputLine);
			}
			in.close();

			String toReturnResults = null;
			toReturnResults = newresponse.toString();

			response.setContentType("application/json");
			response.getWriter().write(toReturnResults.toString());

		} catch (Exception e) {
			e.printStackTrace();

		}

	}

}
