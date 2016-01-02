package ops.global.core.schedulers;

import java.io.Serializable;
import java.util.Collections;
import java.util.Map;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Session;

import ops.global.core.servlets.OPSConstants;

import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Deactivate;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.commons.osgi.OsgiUtil;
import org.apache.sling.commons.scheduler.Scheduler;
import org.apache.sling.jcr.api.SlingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@SuppressWarnings("deprecation")
@Service(java.lang.Runnable.class)
@Component(
                                metatype = true, 
        immediate = true, 
        label = OpsCleanUpScheduler.OPS_CLEANUPSERVICE_NAME, 
        description = "Periodically deletes mortgage unused XMLs"
                                )

@Properties({
    @Property(
            name = OpsCleanUpScheduler.SCHEDULER_PERIOD,
            label = "Period",
            description = "Execution Interval in Seconds",
            //longValue = OpsCleanUpScheduler.SCHEDULER_PERIOD_DEFAULT
            value = OpsCleanUpScheduler.SCHEDULER_EXPRESSION_DEFAULT
    ),
    @Property(
            name = OpsCleanUpScheduler.SCHEDULER_ENABLED,
            label = "Enabled",
            description = "Enable/Disable the Scheduler",
            boolValue = OpsCleanUpScheduler.SCHEDULER_ENABLED_DEFAULT
    )
})

public class OpsCleanUpScheduler implements Runnable{
                
                @Reference
                protected Scheduler scheduler;
                @Reference
                protected SlingRepository repository;
                @Reference
                private  ResourceResolverFactory resourceResFactory;
                
    public static final String OPS_CLEANUPSERVICE_NAME = "OPS - CleanUp Scheduler";
    public static final String SCHEDULER_ENABLED = "service.enabled";
    public static final boolean SCHEDULER_ENABLED_DEFAULT = true;
    public static final String SCHEDULER_PERIOD = "scheduler.expression";
    public static final String SCHEDULER_EXPRESSION_DEFAULT = "0 0 15 * * ?";
    
                private boolean schedulerEnabled;
                private static final Logger log = LoggerFactory.getLogger(OpsCleanUpScheduler.class);

    @Override
    public void run() {
                log.info("Inside run method OpsCleanUpScheduler");
                                // TODO Auto-generated method stub
                                //ResourceResolver adminRsourceResolver = null;
                Session adminSession = null;
        try {
                log.info("Inside run method OpsCleanUpScheduler before adminRsourceResolver");

                //adminRsourceResolver = resourceResFactory.getAdministrativeResourceResolver(null);
                log.info("Inside run method OpsCleanUpScheduler before adminSession");

                //adminSession = adminRsourceResolver.adaptTo(Session.class);  
                adminSession = repository.loginAdministrative(null);
                log.info("Inside run method OpsCleanUpScheduler after adminSession");

            // remaining code for Deletion here
            
            String nodePath = OPSConstants.HIDDEN_PATH.substring(0, (OPSConstants.HIDDEN_PATH.length()-1));
            log.info("OpsCleanUpScheduler nodepath is"+nodePath);
                                                Node rootNode = adminSession.getNode(nodePath);
                                                NodeIterator nodeIter = rootNode.getNodes();
            log.info("OpsCleanUpScheduler childNodes are"+nodeIter.getSize());
                                                while (nodeIter.hasNext()) {
                            log.info("OpsCleanUpScheduler -- Inside While loop");
                                                                String tempNodePath = nodeIter.nextNode().getPath().trim();
                                                                Node tempNode = adminSession.getNode(tempNodePath);
                                                                log.info("OpsCleanUpScheduler -- Temp Node path" + tempNodePath);
                                                                tempNode.remove();
                                                                rootNode.save();
                                                                adminSession.save();     
                                                } 
            
        }catch(Exception e){
                log.info("Exception has occurred "+e);
                e.printStackTrace();
        }
                }
                
                
                @Activate
    private void activate(final Map<String, Object> config) {

        // ensure non-null configuration properties
        Map<String, Object> properties = config != null ? config : Collections.<String, Object>emptyMap();
        
        this.schedulerEnabled = OsgiUtil.toBoolean(properties.get(SCHEDULER_ENABLED), SCHEDULER_ENABLED_DEFAULT);
        log.info("scheduler infoooo"+this.schedulerEnabled);
        String schedulerExpression = OsgiUtil.toString(properties.get(SCHEDULER_PERIOD), SCHEDULER_EXPRESSION_DEFAULT);
        log.info("scheduler infoooo"+SCHEDULER_EXPRESSION_DEFAULT);

        Map<String, Serializable> schedulerConfig = Collections.emptyMap();
        final Runnable runMe = new OpsCleanUpScheduler();
        try {
                if(this.schedulerEnabled){
                                log.info("inside add periodic job");                          
                                this.scheduler.addJob(OPS_CLEANUPSERVICE_NAME, runMe, schedulerConfig, schedulerExpression, false);
                }
        } catch (Exception e) {
            log.error("Cannot schedule '"+OPS_CLEANUPSERVICE_NAME+"': ", e);   
        }
    }

    @Deactivate
    private void deactivate() {
        this.schedulerEnabled = false;
        scheduler.removeJob(OPS_CLEANUPSERVICE_NAME);
    }
}
