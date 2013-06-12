import java.util.Map;
import java.util.Set;

import org.junit.Test;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;


public class TestCheckTypes {

	private Navajo createNavajo1() {
		
		Navajo n1 = NavajoFactory.getInstance().createNavajo();
		Message m1 = NavajoFactory.getInstance().createMessage(n1, "Match");
		n1.addMessage(m1);
		Property p1 = NavajoFactory.getInstance().createProperty(n1, "MatchId", Property.INTEGER_PROPERTY, "212", 0, "", "");
		m1.addProperty(p1);
		Property p2 = NavajoFactory.getInstance().createProperty(n1, "MatchName", Property.STRING_PROPERTY, "Ajax-PSV", 0, "", "");
		m1.addProperty(p2);
		Property p3 = NavajoFactory.getInstance().createProperty(n1, "CalendarDate", Property.DATE_PROPERTY, "", 0, "", "");
		m1.addProperty(p3);
		
		// Add array message.
		Message m2 = NavajoFactory.getInstance().createMessage(n1, "Facilities", Message.MSG_TYPE_ARRAY);
		m1.addMessage(m2);
		for ( int i = 0; i < 2; i++) {
			Message c =  NavajoFactory.getInstance().createMessage(n1, "Facilities");
			m2.addMessage(c);
			Property p1c = NavajoFactory.getInstance().createProperty(n1, "FacilityName", Property.STRING_PROPERTY, "Aap"+i, 0, "", "");
			Property p2c = NavajoFactory.getInstance().createProperty(n1, "Size", Property.INTEGER_PROPERTY, i+33+"", 0, "", "");
			c.addProperty(p1c);
			c.addProperty(p2c);
		}
		return n1;
	}
	
	private Navajo createNavajoEntity() {

		Navajo n1 = NavajoFactory.getInstance().createNavajo();
		Message m1 = NavajoFactory.getInstance().createMessage(n1, "Match");
		n1.addMessage(m1);
		Property p1 = NavajoFactory.getInstance().createProperty(n1, "MatchId", Property.INTEGER_PROPERTY, "", 0, "", "");
		m1.addProperty(p1);
		Property p2 = NavajoFactory.getInstance().createProperty(n1, "MatchName", Property.STRING_PROPERTY, "", 0, "", "");
		m1.addProperty(p2);
		// Add definition.
		Message m2 = NavajoFactory.getInstance().createMessage(n1, "Facilities", Message.MSG_TYPE_ARRAY);
		m1.addMessage(m2);
		Message def = NavajoFactory.getInstance().createMessage(n1, "Facilities", Message.MSG_TYPE_DEFINITION);
		Property p1c = NavajoFactory.getInstance().createProperty(n1, "FacilityName", Property.STRING_PROPERTY, "", 0, "", "");
		Property p2c = NavajoFactory.getInstance().createProperty(n1, "Size", Property.STRING_PROPERTY, "", 0, "", "");
		Property p3c = NavajoFactory.getInstance().createProperty(n1, "Address", Property.STRING_PROPERTY, "", 0, "", "");
		def.addProperty(p1c);
		def.addProperty(p2c);
		def.addProperty(p3c);
		m2.setDefinitionMessage(def);
		
		Message extra = NavajoFactory.getInstance().createMessage(n1, "Extra");
		m1.addMessage(extra);
		
		return n1;
	}
	
	@Test
	public void testCheckTypes() {
		
		Navajo n1 = createNavajo1();
		Navajo n2 = createNavajoEntity();
		
		n1.write(System.err);
		
		System.err.println("Definition:");
		
		n2.write(System.err);
		
		Map<String,String> problems = NavajoFactory.getInstance().checkTypes(n1.getMessage("Match"), n2.getMessage("Match"), true);
		
		Set<String> keys = problems.keySet();
		
		for ( String key: keys ) {
			
			System.err.println(key + " -> " + problems.get(key));
		}
	}
}
