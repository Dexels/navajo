/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.client.impl.apache.test;

import java.util.Calendar;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.client.ClientException;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.Property;

// the script is missing

public class NavajoScriptingTests extends BasicClientTest {

	Navajo input = null;
	
	private static final Logger logger = LoggerFactory.getLogger(NavajoScriptingTests.class);

	@Before
	public void setUp() throws ClientException {
		input = myClient.doSimpleSend(null, "tests/InitProperties");
	}

	@Test @Ignore
	public void testBasic() {
		Assert.assertNotNull(input.getMessage("TestProperties"));
	}

	@Test @Ignore
	public void testDateProperty() {

		// Date
		Message date = input.getMessage("TestProperties/Date");
		Assert.assertNotNull(input);

		Assert.assertEquals(date.getProperty("EmptyTestDateProperty")
				.getValue(), "");
		Assert.assertEquals(date.getProperty("EmptyTestDateProperty")
				.getTypedValue(), null);

		Assert.assertEquals(
				date.getProperty("FullTestDateProperty").getValue(),
				"2008-06-17");
		Calendar c = Calendar.getInstance();
		c.set(2008, 5, 17, 0, 0, 0);
		c.set(Calendar.MILLISECOND, 0);
		Assert.assertEquals(date.getProperty("FullTestDateProperty")
				.getTypedValue(), c.getTime());

		Assert.assertEquals(date.getProperty("WrongTestDateProperty")
				.getValue(), "");
		Assert.assertEquals(date.getProperty("WrongTestDateProperty")
				.getTypedValue(), null);

	}

	@Test @Ignore
	public void testIntegerProperty() {

		// Integer
		Message integer = input.getMessage("TestProperties/Integer");
		Assert.assertNotNull(input);

		Assert.assertEquals(integer.getProperty("EmptyTestIntegerProperty")
				.getValue(), "");
		Assert.assertEquals(integer.getProperty("EmptyTestIntegerProperty")
				.getTypedValue(), null);

		Assert.assertEquals(integer.getProperty("FullTestIntegerProperty")
				.getValue(), "12345");
		Assert.assertEquals(integer.getProperty("FullTestIntegerProperty")
				.getTypedValue(), Integer.valueOf(12345));

		Assert.assertEquals(integer.getProperty("WrongTestIntegerProperty")
				.getValue(), "aap");
		Assert.assertEquals(integer.getProperty("WrongTestIntegerProperty")
				.getTypedValue(), null);
	}

	@Test @Ignore
	public void testBooleanProperty() {

		// Boolean
		Message bool = input.getMessage("TestProperties/Boolean");
		Assert.assertNotNull(input);

		Assert.assertEquals(bool.getProperty("EmptyTestBooleanProperty")
				.getValue(), "");
		Assert.assertEquals(bool.getProperty("EmptyTestBooleanProperty")
				.getTypedValue(), null);

		Assert.assertEquals(bool.getProperty("FullTestBooleanProperty")
				.getValue(), "true");
		Assert.assertEquals(bool.getProperty("FullTestBooleanProperty")
				.getTypedValue(), Boolean.TRUE);

		Assert.assertEquals(bool.getProperty("WrongTestBooleanProperty")
				.getValue(), "");
		Assert.assertEquals(bool.getProperty("WrongTestBooleanProperty")
				.getTypedValue(), null);
	}

	@Test @Ignore
	public void testFloatProperty() {

		// Float
		Message fl = input.getMessage("TestProperties/Float");
		Assert.assertNotNull(input);

		Assert.assertEquals(
				fl.getProperty("EmptyTestFloatProperty").getValue(), "");
		Assert.assertEquals(fl.getProperty("EmptyTestFloatProperty")
				.getTypedValue(), null);

		Assert.assertEquals(fl.getProperty("FullTestFloatProperty").getValue(),
				"10.0");
		Assert.assertEquals(fl.getProperty("FullTestFloatProperty")
				.getTypedValue(), Double.valueOf(10.0));

		Assert.assertEquals(
				fl.getProperty("WrongTestFloatProperty").getValue(), "aap");
		Assert.assertEquals(fl.getProperty("WrongTestFloatProperty")
				.getTypedValue(), null);
	}

	@Test @Ignore
	public void testMoneyProperty() {

		// Money
		Message fl = input.getMessage("TestProperties/Money");
		Assert.assertNotNull(input);

		Assert.assertEquals(
				fl.getProperty("EmptyTestMoneyProperty").getValue(), "");
		Assert.assertEquals(fl.getProperty("EmptyTestMoneyProperty")
				.getTypedValue(), null);

		Assert.assertEquals(fl.getProperty("FullTestMoneyProperty").getValue(),
				"10.0");
		

		Assert.assertEquals(
				fl.getProperty("WrongTestMoneyProperty").getValue(), "aap");
		Assert.assertEquals(fl.getProperty("WrongTestMoneyProperty")
				.getTypedValue(), null);
	}

	@Test @Ignore
	public void testStringProperty() {

		// String
		Message fl = input.getMessage("TestProperties/String");
		Assert.assertNotNull(input);

		Assert.assertEquals(fl.getProperty("EmptyTestStringProperty")
				.getValue(), "");
		Assert.assertEquals(fl.getProperty("EmptyTestStringProperty")
				.getTypedValue(), "");

		Assert.assertEquals(
				fl.getProperty("FullTestStringProperty").getValue(), "noot");
		Assert.assertEquals(fl.getProperty("FullTestStringProperty")
				.getTypedValue(), "noot");

	}

	@Test @Ignore
	public void testClocktimeProperty() {

		// Clocktime
		Message fl = input.getMessage("TestProperties/Clocktime");
		Assert.assertNotNull(input);

		Assert.assertEquals(fl.getProperty("EmptyTestClocktimeProperty")
				.getValue(), "");
		Assert.assertEquals(fl.getProperty("EmptyTestClocktimeProperty")
				.getTypedValue(), null);

		Assert.assertEquals(fl.getProperty("FullTestClocktimeProperty")
				.getValue(), "12:00:00");
	

		Assert.assertEquals(fl.getProperty("WrongTestClocktimeProperty")
				.getValue(), "aap");
		Assert.assertEquals(fl.getProperty("WrongTestClocktimeProperty")
				.getTypedValue(), null);

	}

	@Test @Ignore
	public void testBinaryProperty() {

		// Binary
		Message fl = input.getMessage("TestProperties/Binary");
		Assert.assertNotNull(input);

		Assert.assertEquals(fl.getProperty("EmptyTestBinaryProperty")
				.getValue(), "");
		Assert.assertEquals(fl.getProperty("EmptyTestBinaryProperty")
				.getTypedValue(), null);

		Assert.assertNotSame(fl.getProperty("FullTestBinaryProperty")
				.getValue(), "");
		

	}

	@Test @Ignore
	public void testSelectionProperty() {

		// Selections
		Message fl = input.getMessage("TestProperties/Selections");
		Assert.assertNotNull(input);

		Assert.assertEquals(fl.getProperty("EmptySingleSelection")
				.getAllSelections().size(), 0);
		Assert.assertEquals(fl.getProperty("EmptyMultipleSelection")
				.getAllSelections().size(), 0);

		Assert.assertEquals(fl.getProperty("FilledSingleSelection")
				.getAllSelections().size(), 3);
		Assert.assertEquals(fl.getProperty("FilledSingleSelection")
				.getAllSelectedSelections().size(), 1);
		Assert.assertEquals(fl.getProperty("FilledSingleSelection")
				.getSelected().getValue(), "val1");

		Assert.assertEquals(fl.getProperty("FilledMultipleSelection")
				.getAllSelections().size(), 3);
		Assert.assertEquals(fl.getProperty("FilledMultipleSelection")
				.getAllSelectedSelections().size(), 2);
		Assert.assertEquals(fl.getProperty("FilledMultipleSelection")
				.getSelected().getValue(), "val1");
		Assert.assertEquals(fl.getProperty("FilledMultipleSelection")
				.getAllSelections().get(0).getName(), "name1");
		Assert.assertEquals(fl.getProperty("FilledMultipleSelection")
				.getAllSelections().get(1).getName(), "name2");

		Assert.assertEquals(fl.getProperty("ConstructedSelection")
				.getAllSelections().size(), 4);
		Assert.assertEquals(fl.getProperty("ConstructedSelection")
				.getAllSelectedSelections().size(), 1);
		Assert.assertEquals(fl.getProperty("ConstructedSelection")
				.getSelected().getValue(), "aap");

	}

	@Test @Ignore
	public void testArrayMessage() {

		Message fl = input.getMessage("TestProperties/Array");
		Assert.assertEquals(fl.getType(), Message.MSG_TYPE_ARRAY);
		Assert.assertEquals(fl.getAllMessages().size(), 2);
		Assert.assertEquals(fl.getArraySize(), 2);

		for (int i = 0; i < fl.getArraySize(); i++) {
			Property p = fl.getMessage(i).getProperty("TestProperty");
			Assert.assertNotNull(p);
			if (i == 0) {
				Assert.assertEquals(p.getValue(), "vuur");
			} else if (i == 1) {
				Assert.assertEquals(p.getValue(), "teun");
			}
		}
	}

	private void checkBooleans(Message m) throws Exception {
		List<Property> allProperties = m.getAllProperties();
		for (int j = 0; j < allProperties.size(); j++) {
			Property p = allProperties.get(j);
			if (p.getTypedValue() instanceof Boolean
					&& !((Boolean) p.getTypedValue()).booleanValue()) {
				Assert.fail("Something wrong with: " + p.getFullPropertyName());
			} else {
				logger.info("ok: {}", p.getFullPropertyName());
			}
		}
		List<Message> children = m.getAllMessages();
		for (int j = 0; j < children.size(); j++) {
			checkBooleans(children.get(j));
		}
	}

	@Test @Ignore
	public void testPropertyExpressions() throws Exception {

		Navajo result = myClient.doSimpleSend(input,
				"tests/ProcessTestProperties");
		List<Message> allMessages = result.getAllMessages();
		for (int i = 0; i < allMessages.size(); i++) {
			Message m = allMessages.get(i);
			checkBooleans(m);
		}

	}

	@Test @Ignore
	public void testPropertyTypes() throws Exception {

		myClient.doSimpleSend(input, "tests/ProcessTestProperties");
		Navajo result = myClient.doSimpleSend(input, "tests/ProcessTestFields");

		List<Message> allMessages = result.getAllMessages();
		for (int i = 0; i < allMessages.size(); i++) {
			Message m = allMessages.get(i);
			checkBooleans(m);
		}

	}

	@Test @Ignore
	public void testNavajoMap() throws Exception {

		Navajo result = myClient.doSimpleSend(input, "tests/InitNavajoMapTest");

		List<Message> allMessages = result.getAllMessages();
		for (int i = 0; i < allMessages.size(); i++) {
			Message m = allMessages.get(i);
			checkBooleans(m);
		}

	}

	@Test @Ignore
	public void testNavajoMap2() throws Exception {

		Navajo result = myClient
				.doSimpleSend(input, "tests/InitNavajoMapTest2");

		List<Message> allMessages = result.getAllMessages();
		for (int i = 0; i < allMessages.size(); i++) {
			Message m = allMessages.get(i);
			checkBooleans(m);
		}

	}

	@Test @Ignore
	public void testNavajoMapNewStyle() throws Exception {

		Navajo result = myClient.doSimpleSend(input,
				"tests/InitNavajoMapTestNewStyle");

		List<Message> allMessages = result.getAllMessages();
		for (int i = 0; i < allMessages.size(); i++) {
			Message m = allMessages.get(i);
			checkBooleans(m);
		}

	}

}
