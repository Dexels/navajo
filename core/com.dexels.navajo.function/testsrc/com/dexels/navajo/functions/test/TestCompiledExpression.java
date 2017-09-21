package com.dexels.navajo.functions.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.parser.TMLExpressionException;
import com.dexels.navajo.parser.compiled.api.CachedExpression;

public class TestCompiledExpression {

	private Navajo input;


	@Before
	public void setup() {
		input = NavajoFactory.getInstance().createNavajo();
		input.addMessage(NavajoFactory.getInstance().createMessage(input,"TestMessage")).addProperty(NavajoFactory.getInstance().createProperty(input, "TestProperty", Property.STRING_PROPERTY, "TestValue", 99, "TestDescription", Property.DIR_OUT));
		Message createMessage = NavajoFactory.getInstance().createMessage(input,"TestArrayMessageMessage",Message.MSG_TYPE_ARRAY);
		input.addMessage(createMessage);
		Message element1 = NavajoFactory.getInstance().createMessage(input, "TestArrayMessageMessage", Message.MSG_TYPE_ARRAY_ELEMENT);
		element1.addProperty(NavajoFactory.getInstance().createProperty(input,"Property",Property.STRING_PROPERTY,"Prop",99,"",Property.DIR_IN));
		Message element2 = NavajoFactory.getInstance().createMessage(input, "TestArrayMessageMessage", Message.MSG_TYPE_ARRAY_ELEMENT);
		Message params = NavajoFactory.getInstance().createMessage(input, "__parms__");
		params.addProperty(NavajoFactory.getInstance().createProperty(input,"Param",Property.STRING_PROPERTY,"SomeParam",99,"",Property.DIR_IN));
		input.addMessage(params);
		//		element2.addProperty(NavajoFactory.getInstance().createProperty(input,"Property",Property.STRING_PROPERTY,"Prop2",99,"",Property.DIR_IN));
		createMessage.addElement(element1);
		createMessage.addElement(element2);
	}

	@Test
	public void parseFunction() throws TMLExpressionException {
		Object o = CachedExpression.getInstance().evaluate("ToUpper('ble')", input, null, null, null, null, null,null,null);
		System.err.println(": "+o);
		Assert.assertEquals("BLE", o);
	}
}
