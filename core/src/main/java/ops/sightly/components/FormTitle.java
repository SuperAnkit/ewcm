package ops.sightly.components;

import java.util.ArrayList;
import java.util.List;
import javax.jcr.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.sightly.WCMUse;
import com.day.cq.wcm.api.PageManager;

public class FormTitle extends WCMUse {

	private final Logger log = LoggerFactory.getLogger(FormTitle.class);
	private static final String PROP_FORM_ROOT = "formFolders";
	private String formRootPath;
	private List formNodeSet = new ArrayList();
	private List formSet = new ArrayList();
	private List formTitleSet = new ArrayList();

	@Override
	public void activate() throws Exception {
		
		String[] formRootPathValues = getProperties().get(PROP_FORM_ROOT, String[].class);
		log.info("NEW MSG ++++++++++++++++++++++++++++");
            for (String formRootPath : formRootPathValues) {
            	log.info("FOORMMMMMMMMMMMMMMMMMMMMMMMMM VALUES+++++++++++++++++++++++++++++++" + formRootPath + "+++++++++++++++++++++++++=");
            	Node tempNode = getResourceResolver().getResource(formRootPath).adaptTo(Node.class);
            	String nodeType = tempNode.getPrimaryNodeType().getName();
            	if (nodeType.equals("cq:Page")) {
            		log.info("-----------------------------------------INSIDE CONDN");
            		formNodeSet.add(formRootPath + ".html");
            		formTitleSet.add(getResourceResolver().adaptTo(PageManager.class).getPage(formRootPath).getTitle());
				}
            	
        }
            
            for (int i = 0; i < formNodeSet.size(); i++) {
            	
    			String tempLinkSet = "<a href='"+ formNodeSet.get(i)+"' class = 'btn btn-primary btn-lg' role = 'button'><span class='glyphicon glyphicon-camera'></span> "+ formTitleSet.get(i) +" </a>";
    			formSet.add(tempLinkSet);
    			log.info("START------------------------------------------------------------------------" + tempLinkSet + "---------------------END");
    		}
			
	}


	public List getFormPaths() {
		log.info("FORM PATHS+++++++++++++++++++++++++++++++" + formNodeSet);
		return formNodeSet;
	}
	
	public List getFormSets() {
		log.info("FORM TILES+++++++++++++++++++++++++++++++" + formTitleSet);
		return formSet;
	}
	
	
	
}