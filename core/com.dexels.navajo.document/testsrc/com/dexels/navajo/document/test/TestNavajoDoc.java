package com.dexels.navajo.document.test;

import org.junit.Assert;
import org.junit.Test;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;

public class TestNavajoDoc {
	@Test
	public void testMarteMessage() {
		// Unit test for ignoring message under an array message and keep the correct
		// index number.
		Navajo n = NavajoFactory.getInstance().createNavajo();
		Message array = NavajoFactory.getInstance().createMessage(n, "Actor");
		n.addMessage(array);
		array.setType(Message.MSG_TYPE_ARRAY);

		// creating children messages

		// child 1
		Message child1 = NavajoFactory.getInstance().createMessage(n, "Actor");
		//child1.setIndex(0);
		array.addElement(child1);

		Property property1_child1 = NavajoFactory.getInstance().createProperty(n, "Id", Property.INTEGER_PROPERTY,
				"1", 0, "", "out");
		
		Property property2_child1 = NavajoFactory.getInstance().createProperty(n, "FirstName", Property.STRING_PROPERTY,
				"Penelope", 0, "", "out");
		
		Property property3_child1 = NavajoFactory.getInstance().createProperty(n, "LastName", Property.STRING_PROPERTY,
				"1", 0, "", "out");
		
		child1.addProperty(property1_child1);
		child1.addProperty(property2_child1);
		child1.addProperty(property3_child1);

		// child 2
		Message child2 = NavajoFactory.getInstance().createMessage(n, "Actor");
		//child2.setIndex(0);
		array.addElement(child2);

		Property property1_child2 = NavajoFactory.getInstance().createProperty(n, "Id", Property.INTEGER_PROPERTY,
				"2", 0, "", "out");
		
		Property property2_child2 = NavajoFactory.getInstance().createProperty(n, "FirstName", Property.STRING_PROPERTY,
				"Nick", 0, "", "out");
		
		Property property3_child2 = NavajoFactory.getInstance().createProperty(n, "LastName", Property.STRING_PROPERTY,
				"Wahlberg", 0, "", "out");
		
		
		child2.addProperty(property1_child2);
		child2.addProperty(property2_child2);
		child2.addProperty(property3_child2);


		// System.out.println("Array message size "+
		// n.getMessage("Array").getArraySize());
	

		
		n.write(System.out);

		//Assert.assertEquals(1, n.getMessage("Array").getArraySize());
		Assert.assertEquals(0, n.getMessage("Actor").getMessage(0).getIndex());
		Assert.assertEquals(1, n.getMessage("Actor").getMessage(1).getIndex());

	}
}
