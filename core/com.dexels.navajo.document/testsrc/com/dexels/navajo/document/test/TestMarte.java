package com.dexels.navajo.document.test;

import org.junit.Assert;
import org.junit.Test;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;

public class TestMarte {
	@Test
	public void testMarteMessage() {
		// Unit test for ignoring message under an array message and keep the correct
		// index number.
		Navajo n = NavajoFactory.getInstance().createNavajo();
		Message array = NavajoFactory.getInstance().createMessage(n, "Array");
		n.addMessage(array);
		array.setType(Message.MSG_TYPE_ARRAY);

		// creating children messages

		// child 1
		Message child1 = NavajoFactory.getInstance().createMessage(n, "Marte");
		child1.setMode(Message.MSG_MODE_IGNORE);
		array.addElement(child1);

		Property property_child1 = NavajoFactory.getInstance().createProperty(n, "Waarde", Property.STRING_PROPERTY,
				"marte", 0, "", "out");
		child1.addProperty(property_child1);

		// child 2
		Message child2 = NavajoFactory.getInstance().createMessage(n, "Carlo");
		child2.setMode(Message.MSG_MODE_IGNORE);
		array.addElement(child2);

		Property property_child2 = NavajoFactory.getInstance().createProperty(n, "Waarde", Property.STRING_PROPERTY,
				"carlo", 0, "", "out");
		child2.addProperty(property_child2);

		// child 3
		Message child3 = NavajoFactory.getInstance().createMessage(n, "Martin");
		array.addElement(child3);

		Property property_child3 = NavajoFactory.getInstance().createProperty(n, "Waarde", Property.STRING_PROPERTY,
				"martin", 0, "", "out");
		child3.addProperty(property_child3);
		
		

		// System.out.println("Array message size "+
		// n.getMessage("Array").getArraySize());

		n.write(System.out);

		Assert.assertEquals(1, n.getMessage("Array").getArraySize());
		Assert.assertEquals("martin", n.getMessage("Array").getMessage(0).getProperty("Waarde").getValue());
		Assert.assertEquals(0, n.getMessage("Array").getMessage(0).getIndex());

	}
}
