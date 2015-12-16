package ops.sightly.components;

import java.io.IOException;

import javax.jcr.RepositoryException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;



public interface RedirectService {

	public void redirectPath(SlingHttpServletRequest request, SlingHttpServletResponse response) throws RepositoryException, IOException; 
	
}