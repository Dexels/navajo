package com.dexels.navajo.expression.compiled;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Operand;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.parser.Expression;
import com.dexels.navajo.parser.TMLExpressionException;
import com.dexels.navajo.parser.compiled.api.CachedExpressionEvaluator;
import com.dexels.navajo.parser.compiled.api.ContextExpression;
import com.dexels.navajo.parser.compiled.api.ExpressionCache;
import com.dexels.navajo.parser.compiled.api.ParseMode;
import com.dexels.navajo.script.api.SystemException;

public class TestExpressionPropertiesCompiled {

	private Navajo testDoc;
	private Message topMessage;
	private Property one;
	private Property two;
	private Property three;
	private Property five;

	@Before
	public void setup() {
//		NavajoFactory.getInstance().setExpressionEvaluator(new DefaultExpressionEvaluator());
		NavajoFactory.getInstance().setExpressionEvaluator(new CachedExpressionEvaluator());
		
		testDoc = NavajoFactory.getInstance().createNavajo();
		topMessage = NavajoFactory.getInstance().createMessage(testDoc, "MyTop");
		testDoc.addMessage(topMessage);
		one = NavajoFactory.getInstance().createProperty(testDoc,
				"One", Property.INTEGER_PROPERTY, "1", 0, "", Property.DIR_OUT);
		two = NavajoFactory.getInstance().createProperty(testDoc,
				"Two", Property.EXPRESSION_PROPERTY, "[One]+1", 0, "", Property.DIR_OUT);
		three = NavajoFactory.getInstance().createProperty(testDoc,
				"Three", Property.EXPRESSION_PROPERTY, "2+1", 0, "", Property.DIR_OUT);
		five = NavajoFactory.getInstance().createProperty(testDoc,
				"Five", Property.EXPRESSION_PROPERTY, "[Two]+[Three]", 0, "", Property.DIR_OUT);
		topMessage.addProperty(one);
		topMessage.addProperty(two);
		topMessage.addProperty(three);
		topMessage.addProperty(five);
	}
	
	@Test
	public void testCompiled() throws TMLExpressionException, SystemException {
		Operand result = Expression.evaluate("[Two]", testDoc,null,topMessage);
		Assert.assertEquals(2, result.value);
		Integer twoValue = (Integer) two.getTypedValue();
		Assert.assertEquals(2, twoValue.intValue());
		one.setAnyValue(2);
		Integer fiveValue =  (Integer) five.getTypedValue();
		Assert.assertEquals(5, fiveValue.intValue());
		List<Property> change = testDoc.refreshExpression();
		twoValue = (Integer) two.getTypedValue();
		Assert.assertEquals(3, twoValue.intValue());
		System.err.println("Change: "+change.size());
		result = Expression.evaluate("[Two]", testDoc,null,topMessage);
		Assert.assertEquals(3, result.value);
		fiveValue =  (Integer) five.getTypedValue();
		Assert.assertEquals(6, fiveValue.intValue());
		
	}

}
