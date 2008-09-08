package com.dexels.navajo.server;

import java.util.ArrayList;
import java.util.Calendar;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.Selection;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.document.types.ClockTime;
import com.dexels.navajo.document.types.Money;

import junit.framework.Assert;

public class NavajoScriptingTests extends BasicTest {

	Navajo input = null;
	
	public void setUp() throws Exception {
		super.setUp();
		input = myClient.doSimpleSend("tests/InitProperties");
	}
	
	public void testBasic() throws Exception {
		
		Assert.assertNotNull(input.getMessage("TestProperties"));
		
	}
	
	public void testDateProperty() throws Exception {
		
		
		// Date
		Message date = input.getMessage("TestProperties/Date");
		Assert.assertNotNull(input);
		
		Assert.assertEquals(date.getProperty("EmptyTestDateProperty").getValue(), "");
		Assert.assertEquals(date.getProperty("EmptyTestDateProperty").getTypedValue(), null);
		
		Assert.assertEquals(date.getProperty("FullTestDateProperty").getValue(), "2008-06-17");
		Calendar c = Calendar.getInstance();
		c.set(2008, 5, 17, 0, 0, 0);
		c.set(Calendar.MILLISECOND, 0);
		Assert.assertEquals(date.getProperty("FullTestDateProperty").getTypedValue(), c.getTime());
		
		Assert.assertEquals(date.getProperty("WrongTestDateProperty").getValue(), "");
		Assert.assertEquals(date.getProperty("WrongTestDateProperty").getTypedValue(), null);
		
	}
	
	public void testIntegerProperty() throws Exception {
		
		// Integer
		Message integer = input.getMessage("TestProperties/Integer");
		Assert.assertNotNull(input);

		Assert.assertEquals(integer.getProperty("EmptyTestIntegerProperty").getValue(), "");
		Assert.assertEquals(integer.getProperty("EmptyTestIntegerProperty").getTypedValue(), null);
		
		Assert.assertEquals(integer.getProperty("FullTestIntegerProperty").getValue(), "12345");
		Assert.assertEquals(integer.getProperty("FullTestIntegerProperty").getTypedValue(), Integer.valueOf(12345));
		
		Assert.assertEquals(integer.getProperty("WrongTestIntegerProperty").getValue(), "aap");
		Assert.assertEquals(integer.getProperty("WrongTestIntegerProperty").getTypedValue(), null);
	}
	
	public void testBooleanProperty() throws Exception {
		
		// Boolean
		Message bool = input.getMessage("TestProperties/Boolean");
		Assert.assertNotNull(input);

		Assert.assertEquals(bool.getProperty("EmptyTestBooleanProperty").getValue(), "");
		Assert.assertEquals(bool.getProperty("EmptyTestBooleanProperty").getTypedValue(), null);
		
		Assert.assertEquals(bool.getProperty("FullTestBooleanProperty").getValue(), "true");
		Assert.assertEquals(bool.getProperty("FullTestBooleanProperty").getTypedValue(), Boolean.TRUE);
		
		Assert.assertEquals(bool.getProperty("WrongTestBooleanProperty").getValue(), "");
		Assert.assertEquals(bool.getProperty("WrongTestBooleanProperty").getTypedValue(), null);
	}
	
	public void testFloatProperty() throws Exception {

		// Float
		Message fl = input.getMessage("TestProperties/Float");
		Assert.assertNotNull(input);

		Assert.assertEquals(fl.getProperty("EmptyTestFloatProperty").getValue(), "");
		Assert.assertEquals(fl.getProperty("EmptyTestFloatProperty").getTypedValue(), null);

		Assert.assertEquals(fl.getProperty("FullTestFloatProperty").getValue(), "10.0");
		Assert.assertEquals(fl.getProperty("FullTestFloatProperty").getTypedValue(), new Double(10.0));

		Assert.assertEquals(fl.getProperty("WrongTestFloatProperty").getValue(), "aap");
		Assert.assertEquals(fl.getProperty("WrongTestFloatProperty").getTypedValue(), null);
	}
	
	public void testMoneyProperty() throws Exception {

		// Money
		Message fl = input.getMessage("TestProperties/Money");
		Assert.assertNotNull(input);

		Assert.assertEquals(fl.getProperty("EmptyTestMoneyProperty").getValue(), "");
		Assert.assertEquals(fl.getProperty("EmptyTestMoneyProperty").getTypedValue(), null);

		Assert.assertEquals(fl.getProperty("FullTestMoneyProperty").getValue(), "10.0");
		Assert.assertEquals(fl.getProperty("FullTestMoneyProperty").getTypedValue(), new Money(10.0));

		Assert.assertEquals(fl.getProperty("WrongTestMoneyProperty").getValue(), "aap");
		Assert.assertEquals(fl.getProperty("WrongTestMoneyProperty").getTypedValue(), null);
	}
	
	public void testStringProperty() throws Exception {

		// String
		Message fl = input.getMessage("TestProperties/String");
		Assert.assertNotNull(input);

		Assert.assertEquals(fl.getProperty("EmptyTestStringProperty").getValue(), "");
		Assert.assertEquals(fl.getProperty("EmptyTestStringProperty").getTypedValue(), new String(""));

		Assert.assertEquals(fl.getProperty("FullTestStringProperty").getValue(), "noot");
		Assert.assertEquals(fl.getProperty("FullTestStringProperty").getTypedValue(), new String("noot"));

	}
	
	public void testClocktimeProperty() throws Exception {

		// Clocktime
		Message fl = input.getMessage("TestProperties/Clocktime");
		Assert.assertNotNull(input);

		Assert.assertEquals(fl.getProperty("EmptyTestClocktimeProperty").getValue(), "");
		Assert.assertEquals(fl.getProperty("EmptyTestClocktimeProperty").getTypedValue(), null);

		Assert.assertEquals(fl.getProperty("FullTestClocktimeProperty").getValue(), "12:00:00");
		Assert.assertEquals(fl.getProperty("FullTestClocktimeProperty").getTypedValue(), new ClockTime("12:00:00"));
		
		Assert.assertEquals(fl.getProperty("WrongTestClocktimeProperty").getValue(), "aap");
		Assert.assertEquals(fl.getProperty("WrongTestClocktimeProperty").getTypedValue(), null);

	}
	
	public void testBinaryProperty() throws Exception {

		// Binary
		Message fl = input.getMessage("TestProperties/Binary");
		Assert.assertNotNull(input);

		Assert.assertEquals(fl.getProperty("EmptyTestBinaryProperty").getValue(), "");
		Assert.assertEquals(fl.getProperty("EmptyTestBinaryProperty").getTypedValue(), null);

		Assert.assertNotSame(fl.getProperty("FullTestBinaryProperty").getValue(), "");
		Assert.assertEquals(fl.getProperty("FullTestBinaryProperty").getTypedValue().getClass(), Binary.class);
		
	}
	
	public void testSelectionProperty() throws Exception {

		// Selections
		Message fl = input.getMessage("TestProperties/Selections");
		Assert.assertNotNull(input);

		Assert.assertEquals(fl.getProperty("EmptySingleSelection").getAllSelections().size(), 0);
		Assert.assertEquals(fl.getProperty("EmptyMultipleSelection").getAllSelections().size(), 0);
		
		Assert.assertEquals(fl.getProperty("FilledSingleSelection").getAllSelections().size(), 3);
		Assert.assertEquals(fl.getProperty("FilledSingleSelection").getAllSelectedSelections().size(), 1);
		Assert.assertEquals(fl.getProperty("FilledSingleSelection").getSelected().getValue(), "val1");
		
		Assert.assertEquals(fl.getProperty("FilledMultipleSelection").getAllSelections().size(), 3);
		Assert.assertEquals(fl.getProperty("FilledMultipleSelection").getAllSelectedSelections().size(), 2);
		Assert.assertEquals(fl.getProperty("FilledMultipleSelection").getSelected().getValue(), "val1");
		Assert.assertEquals(((Selection) fl.getProperty("FilledMultipleSelection").getAllSelections().get(0)).getName(), "name1");
		Assert.assertEquals(((Selection) fl.getProperty("FilledMultipleSelection").getAllSelections().get(1)).getName(), "name2");
		
		Assert.assertEquals(fl.getProperty("ConstructedSelection").getAllSelections().size(), 4);
		Assert.assertEquals(fl.getProperty("ConstructedSelection").getAllSelectedSelections().size(), 1);
		Assert.assertEquals(fl.getProperty("ConstructedSelection").getSelected().getValue(), "aap");
		
	}
	
	public void testArrayMessage() throws Exception {
		
		Message fl = input.getMessage("TestProperties/Array");
		Assert.assertEquals(fl.getType(), Message.MSG_TYPE_ARRAY);
		Assert.assertEquals(fl.getAllMessages().size(), 2);
		Assert.assertEquals(fl.getArraySize(), 2);
		
		for (int i = 0; i < fl.getArraySize(); i++) {
			Property p = fl.getMessage(i).getProperty("TestProperty");
			Assert.assertNotNull(p);
			if ( i == 0 ) {
				Assert.assertEquals(p.getValue(), "vuur");
			} else if ( i == 1) {
				Assert.assertEquals(p.getValue(), "teun");
			}
		}
	}
	
	private void checkBooleans(Message m) throws Exception {
		ArrayList<Property> allProperties = m.getAllProperties();
		for (int j = 0; j < allProperties.size(); j++) {
			Property p = allProperties.get(j);
			if ( p.getTypedValue() instanceof Boolean && !((Boolean) p.getTypedValue()).booleanValue() ) {
				Assert.fail("Something wrong with: " + p.getFullPropertyName());
			} else {
				System.err.println("ok: " + p.getFullPropertyName());
			}
		}
		ArrayList<Message> children = m.getAllMessages();
		for (int j = 0; j < children.size(); j++) {
			checkBooleans(children.get(j));
		}
	}
	
	public void testPropertyExpressions() throws Exception {
	
		Navajo result = myClient.doSimpleSend(input, "tests/ProcessTestProperties");
		ArrayList<Message> allMessages = result.getAllMessages();
		for (int i = 0; i < allMessages.size(); i++) {
			Message m = allMessages.get(i);
			checkBooleans(m);
		}
		
	}
	
	public void testPropertyTypes() throws Exception {
		
		Navajo b = myClient.doSimpleSend(input, "tests/ProcessTestProperties");
		Navajo result = myClient.doSimpleSend(input, "tests/ProcessTestFields");
		
		ArrayList<Message> allMessages = result.getAllMessages();
		for (int i = 0; i < allMessages.size(); i++) {
			Message m = allMessages.get(i);
			checkBooleans(m);
		}
		
	}
	
	public void testNavajoMap() throws Exception {

		Navajo result = myClient.doSimpleSend(input, "tests/InitNavajoMapTest");
		
		ArrayList<Message> allMessages = result.getAllMessages();
		for (int i = 0; i < allMessages.size(); i++) {
			Message m = allMessages.get(i);
			checkBooleans(m);
		}

	}

}
