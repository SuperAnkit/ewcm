package ops.global.core.servlets;



import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.ServletException;

import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.commons.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@SlingServlet(paths = "/bin/DDReverseOccupational",
        methods = {"POST"},
        metatype = true)
public class OPSReverseSelectOccupationalServlet extends SlingAllMethodsServlet {
    
	private static final long serialVersionUID = 1L;  
	private static String eOccupationalValue;
	private static int eOccupationalKeyValue;
   
        private Logger logger = LoggerFactory.getLogger(OPSReverseSelectOccupationalServlet.class);
     
        protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException,
            IOException {
    	

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        HashMap<Integer, String> valueMap = new HashMap<Integer, String>();
        valueMap.put(1,"Unknown");
        valueMap.put(44,"Accountant(other)");
        valueMap.put(43,"Accountant-chartered");
        valueMap.put(45,"Architect");
        valueMap.put(46,"Dentist");
        valueMap.put(47,"Doctor,specialist");
        valueMap.put(48,"Engineer");
        valueMap.put(49,"Financial controller");
        valueMap.put(51,"Journalist");
        valueMap.put(50,"Lawyer");
        valueMap.put(52,"Nurse");
        valueMap.put(57,"Other professional");
        valueMap.put(53,"Pharmacist");
        valueMap.put(54,"solicitor");
        valueMap.put(56,"special exec consult");
        valueMap.put(55,"stockbroker");
        valueMap.put(120,"Farm Labourer");
        valueMap.put(119,"Farm Manager");
        valueMap.put(118,"Farm Owner");
        valueMap.put(123,"Homemaker/domestic");
        valueMap.put(122,"Military/armed forces");
        valueMap.put(126,"Other");
        valueMap.put(121,"Police officer");
        valueMap.put(125,"Retired");
        valueMap.put(128,"Small bus. Proprietr");
        valueMap.put(124,"Student");
        valueMap.put(127,"Unemployed");
        valueMap.put(130,"Lower levl mgr/admin");
        valueMap.put(66,"middle level mgr/adm");
        valueMap.put(65,"senior level mgr/adm");
        valueMap.put(72,"Acct/payroll clerk");
        valueMap.put(73,"Bank teller");
        valueMap.put(74,"Bookkeeper");
        valueMap.put(75,"Cashier");
        valueMap.put(76,"Key punch operator");
        valueMap.put(129,"Coral Sea Islands");
        valueMap.put(77,"Office clerk");
        valueMap.put(80,"Other clerical");
        valueMap.put(79,"Postal service workr");
        valueMap.put(78,"Receptn/phone opr");
        valueMap.put(71,"secretary");
        valueMap.put(97,"Baker");
        valueMap.put(88,"Bricklayer");
        valueMap.put(98,"Butcher");
        valueMap.put(89,"carpenter");
        valueMap.put(90,"Electrician");
        valueMap.put(87,"Foreman");
        valueMap.put(91,"Jeweller");
        valueMap.put(92,"Mechanic");
        valueMap.put(99,"Other trades person");
        valueMap.put(93,"painter");
        valueMap.put(94,"plasterer");
        valueMap.put(95,"plumber");
        valueMap.put(96,"tailor");
        valueMap.put(85,"Checkout operator");
        valueMap.put(81,"Insurance brokr/agnt");
        valueMap.put(86,"other sales");
        valueMap.put(82,"real estate agnt/mgr");
        valueMap.put(84,"sales assistant");
        valueMap.put(83,"sales representative");
        valueMap.put(64,"Other teacher");
        valueMap.put(58,"preschool teacher");
        valueMap.put(59,"primary school teacher");
        valueMap.put(63,"remedial/other teacher");
        valueMap.put(60,"secondary teacher");
        valueMap.put(61,"TAFE teacher");
        valueMap.put(62,"tertiary teacher");
        valueMap.put(110,"bar attendant");
        valueMap.put(106,"beautician");
        valueMap.put(115,"bus driver");
        valueMap.put(116,"delivery or courier");
        valueMap.put(109,"dental assistant");
        valueMap.put(107,"hairdresser");
        valueMap.put(108,"nursing aide");
        valueMap.put(117,"other service worker");
        valueMap.put(113,"porter");
        valueMap.put(114,"taxi driver");
        valueMap.put(112,"usher");
        valueMap.put(111,"waiter");
        valueMap.put(68,"computer programmer");
        valueMap.put(69,"data analyst/research");
        valueMap.put(70,"other technical");
        valueMap.put(103,"Builder\"s labourer");
        valueMap.put(101,"factory worker");
        valueMap.put(105,"other worker/laborer");
        valueMap.put(100,"trades person\"s asst");
        valueMap.put(104,"truck driver");
        valueMap.put(102,"warehouseman");


        
			String eOccupational = request.getParameter("eOccupational");
			if (eOccupational.length() > 0) {
				eOccupationalKeyValue = Integer.parseInt(eOccupational);
			} else {
				eOccupationalKeyValue = 1001;
			}
			 
					
			Iterator<Integer> keySetIterator = valueMap.keySet().iterator();
			
			JSONArray eOccupationalValueJsonArray = new JSONArray();

			while(keySetIterator.hasNext()){
			  Integer key = keySetIterator.next();
			  if (key == eOccupationalKeyValue) {
				  eOccupationalValue = valueMap.get(key);
				  eOccupationalValueJsonArray.put(eOccupationalValue);
				  break;
			} 
			  
			}
            
                response.setContentType("application/json");
                response.getWriter().write(eOccupationalValueJsonArray.toString());
         
 
        
    }

    }

