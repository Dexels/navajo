package com.dexels.navajo.mapping;

import org.junit.Assert;
import org.junit.Test;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;


public class MappingUtilsTest {
	
	@Test
	public void setgetMessageObject1() {
		Navajo n = NavajoFactory.getInstance().createNavajo();
		Property p = n.getProperty("Apenoot/Kip");
		Assert.assertNull(p);
		Message m = MappingUtils.getMessageObject("Apenoot/Kip", null, false, n, false, "", -1);
		p = NavajoFactory.getInstance().createProperty(n, "Kip", Property.STRING_PROPERTY, "Lekker", 0, "", "");
		m.addProperty(p);
		n.write(System.err);
		
		Assert.assertNotNull(n.getProperty("Apenoot/Kip"));
		Assert.assertEquals("Lekker", n.getProperty("Apenoot/Kip").getValue());
		
	}
	
	@Test
	public void testCreateMessage() {
		Navajo n = NavajoFactory.getInstance().createNavajo();
		MappingUtils.getMessageObject("/Apenoot@0/Kibbeling", null, false, n, false, "", -1);
		MappingUtils.getMessageObject("/Apenoot@1/Kibbeling", null, false, n, false, "", -1);
		Assert.assertEquals(2, n.getMessage("Apenoot").getElements().size());
	}

}
