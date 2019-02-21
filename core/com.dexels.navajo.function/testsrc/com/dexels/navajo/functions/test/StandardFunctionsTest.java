package com.dexels.navajo.functions.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.dexels.utils.Base64;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.dexels.navajo.document.Header;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Operand;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.document.types.ClockTime;
import com.dexels.navajo.document.types.Memo;
import com.dexels.navajo.document.types.Money;
import com.dexels.navajo.document.types.Percentage;
import com.dexels.navajo.document.types.StopwatchTime;
import com.dexels.navajo.expression.api.FunctionInterface;
import com.dexels.navajo.expression.api.TMLExpressionException;
import com.dexels.navajo.functions.CapString;
import com.dexels.navajo.functions.CheckFloat;
import com.dexels.navajo.functions.CheckInteger;
import com.dexels.navajo.functions.CreateExpression;
import com.dexels.navajo.functions.DateTime;
import com.dexels.navajo.functions.ValidatePhoneNumber;
import com.dexels.navajo.functions.WeekDay;
import com.dexels.navajo.functions.util.FunctionFactoryFactory;
import com.dexels.navajo.functions.util.FunctionFactoryInterface;
import com.dexels.navajo.parser.Expression;
import com.dexels.navajo.server.DispatcherFactory;
import com.dexels.navajo.server.test.TestDispatcher;
import com.dexels.navajo.server.test.TestNavajoConfig;

@SuppressWarnings("unused")
public class StandardFunctionsTest {

	FunctionFactoryInterface fff;
	ClassLoader cl;

	@Before
	public void setUp() throws Exception {
		fff = FunctionFactoryFactory.getInstance();
		cl = getClass().getClassLoader();
	}

	private Navajo createTestNavajo() throws Exception {
		Navajo doc = NavajoFactory.getInstance().createNavajo();
		Message array = NavajoFactory.getInstance().createMessage(doc, "Aap");
		array.setType(Message.MSG_TYPE_ARRAY);
		Message array1 = NavajoFactory.getInstance().createMessage(doc, "Aap");
		array.addElement(array1);
		doc.addMessage(array);
		Property p = NavajoFactory.getInstance().createProperty(doc, "Noot",
				Property.INTEGER_PROPERTY, "10", 10, "", "in");
		array1.addProperty(p);

		Message single = NavajoFactory.getInstance().createMessage(doc,
				"Single");
		doc.addMessage(single);
		Property p2 = NavajoFactory.getInstance().createProperty(doc,
				"Selectie", "1", "", "in");
		p2.addSelection(NavajoFactory.getInstance().createSelection(doc, "key",
				"value", true));
		single.addProperty(p2);
		Property p3 = NavajoFactory.getInstance().createProperty(doc, "Vuur",
				Property.INTEGER_PROPERTY, "10", 10, "", "out");
		single.addProperty(p3);

		return doc;
	}

	@Test
	public void testFindElement() throws TMLExpressionException {
		Navajo doc = NavajoFactory.getInstance().createNavajo();
		Message array = NavajoFactory.getInstance().createMessage(doc, "Aap");
		array.setType(Message.MSG_TYPE_ARRAY);
		doc.addMessage(array);

		Message array1 = NavajoFactory.getInstance().createMessage(doc, "Aap");
		array.addElement(array1);
		Property p1 = NavajoFactory.getInstance().createProperty(doc, "Noot",
				Property.STRING_PROPERTY, "pim", 10, "", "in");
		array1.addProperty(p1);
		Message array2 = NavajoFactory.getInstance().createMessage(doc, "Aap");
		array.addElement(array2);
		Property p2 = NavajoFactory.getInstance().createProperty(doc, "Noot",
				Property.STRING_PROPERTY, "pam", 10, "", "in");
		array2.addProperty(p2);
		Message array3 = NavajoFactory.getInstance().createMessage(doc, "Aap");
		array.addElement(array3);
		Property p3 = NavajoFactory.getInstance().createProperty(doc, "Noot",
				Property.STRING_PROPERTY, "pet", 10, "", "in");
		array3.addProperty(p3);
		
		
		FunctionInterface fi = fff.getInstance(cl, "FindElement");
		doc.write(System.err);
		fi.reset();
		fi.insertStringOperand("Noot");
		fi.insertStringOperand("pam");
		fi.insertMessageOperand(array);
		Message result = (Message) fi.evaluate();
		assertEquals(array2, result);
		fi.reset();
		fi.setCurrentMessage(array3);
		fi.insertStringOperand("Noot");
		fi.insertStringOperand("pam");
		assertEquals(array2, result);
	}
	@Test
	public void testAbs() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "Abs");

		// Test Integer.
		fi.reset();
		fi.insertIntegerOperand(Integer.valueOf(-10));
		Object o = fi.evaluateWithTypeChecking();
		assertEquals(o.getClass(), Integer.class);
		assertEquals(((Integer) o).intValue(), 10);

		// Test Double.
		fi.reset();
		fi.insertFloatOperand(Double.valueOf(-10));
		o = fi.evaluateWithTypeChecking();
		assertEquals(o.getClass(), Double.class);
		assertEquals(((Double) o).intValue(), 10);

		// Test bogus.
		boolean bogus = false;
		fi.reset();
		fi.insertStringOperand("-10");
		try {
			o = fi.evaluateWithTypeChecking();
		} catch (TMLExpressionException e) {
			bogus = true;
		}
		assertTrue(bogus);

		// Test null.
		fi.reset();
		fi.insertOperand(Operand.NULL);
		o = fi.evaluateWithTypeChecking();
		assertNull(o);

		// Test empty parameters.
		boolean empty = false;
		fi.reset();
		try {
			o = fi.evaluateWithTypeChecking();
		} catch (TMLExpressionException e) {
			empty = true;
		}
		assertTrue(empty);
	}

	public static void main(String[] args) throws Exception {
		StandardFunctionsTest t = new StandardFunctionsTest();
		t.setUp();
		t.testAbs();
	}

	@Test
	public void testZipArchive() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "ZipArchive");
		fi.reset();
		fi.insertStringOperand("aap");
		Object o = fi.evaluateWithTypeChecking();
		assertNull(o);

	}

	@Test
	public void testZip() throws Exception {

		Binary b = new Binary(new byte[] { 1, 1, 1 });
		FunctionInterface fi = fff.getInstance(cl, "Zip");
		fi.reset();
		fi.insertBinaryOperand(b);
		fi.insertStringOperand("aap");
		Object o = fi.evaluateWithTypeChecking();
		assertNotNull(o);
		assertEquals(o.getClass(), Binary.class);
	}

	@Test
	public void testXmlUnescape() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "XmlUnescape");
		fi.reset();
		fi.insertStringOperand("<><>");
		Object o = fi.evaluate();
		assertNotNull(o);
		assertEquals(o.getClass(), String.class);

		boolean exception = false;
		try {
			fi.reset();
			fi.insertIntegerOperand(Integer.valueOf(10));
			fi.evaluateWithTypeChecking();
		} catch (Exception e) {
			exception = true;
		}

		assertTrue(exception);
	}

	@Test
	public void testXmlEscape() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "XmlEscape");
		fi.reset();
		fi.insertStringOperand("aap");
		Object o = fi.evaluateWithTypeChecking();
		assertNotNull(o);
		assertEquals(o.getClass(), String.class);

	}

	@Test
	public void testWeekday() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "WeekDay");
		fi.reset();
		fi.insertDateOperand(new java.util.Date());
		Object o = fi.evaluateWithTypeChecking();
		assertNotNull(o);
		assertEquals(o.getClass(), String.class);

		fi.reset();
		Object o2 = fi.evaluateWithTypeChecking();
		assertNotNull(o);
		assertEquals(o.getClass(), String.class);

		assertEquals(o.toString(), o2.toString());

	}

	@Test
	public void testWait() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "Wait");
		fi.reset();
		fi.insertIntegerOperand(Integer.valueOf(10));
		Object o = fi.evaluateWithTypeChecking();
		assertNull(o);

	}

	@Test
	public void testUnicode() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "Unicode");
		fi.reset();
		fi.insertStringOperand("10");
		Object o = fi.evaluateWithTypeChecking();
		assertNotNull(o);

	}

	@Test
	public void testURLEncode() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "URLEncode");
		fi.reset();
		fi.insertStringOperand("10");
		Object o = fi.evaluateWithTypeChecking();
		assertNotNull(o);
		assertEquals(o.getClass(), String.class);

	}

	@Test
	public void testTrim() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "Trim");
		fi.reset();
		fi.insertStringOperand(" aap ");
		Object o = fi.evaluateWithTypeChecking();
		assertNotNull(o);
		assertEquals("aap", o.toString());

	}

	@Test
	public void testToUpper() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "ToUpper");
		fi.reset();
		fi.insertStringOperand("aap");
		Object o = fi.evaluateWithTypeChecking();
		assertNotNull(o);
		assertEquals("AAP", o.toString());

	}

	@Test
	public void testToString() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "ToString");
		fi.reset();
		fi.insertStringOperand("aap");
		Object o = fi.evaluateWithTypeChecking();
		assertNotNull(o);
		assertEquals("aap", o.toString());

		fi.reset();
		fi.insertIntegerOperand(Integer.valueOf(10));
		o = fi.evaluateWithTypeChecking();
		assertNotNull(o);
		assertEquals("10", o.toString());

		fi.reset();
		fi.insertFloatOperand(Float.valueOf(10));
		o = fi.evaluateWithTypeChecking();
		assertNotNull(o);
		assertEquals("10.0", o.toString());

	}

	@Test
	public void testToStopwatchTime() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "ToStopwatchTime");
		fi.reset();
		fi.insertDateOperand(new java.util.Date());
		Object o = fi.evaluateWithTypeChecking();
		assertNotNull(o);
		assertEquals(StopwatchTime.class, o.getClass());

	}

	@Test
	public void testToSecureImage() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "ToSecureImage");
		fi.reset();
		fi.insertStringOperand("SECURE");
		Object o = fi.evaluateWithTypeChecking();
		assertNotNull(o);
		assertEquals(Binary.class, o.getClass());

		fi.reset();
		fi.insertOperand(Operand.NULL);
		o = fi.evaluateWithTypeChecking();
		assertNull(o);

		System.err.println(System.currentTimeMillis());
		long l = (Long.parseLong("1234567890000") - System
				.currentTimeMillis());
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MILLISECOND, (int) l);
		System.err.println(SimpleDateFormat.getInstance().format(c.getTime()));

	}

	@Test
	public void testToPercentage() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "ToPercentage");
		fi.reset();
		fi.insertStringOperand("80");
		Object o = fi.evaluateWithTypeChecking();
		assertNotNull(o);
		assertEquals(Percentage.class, o.getClass());

		fi.reset();
		fi.insertIntegerOperand(Integer.valueOf(80));
		o = fi.evaluateWithTypeChecking();
		assertNotNull(o);
		assertEquals(Percentage.class, o.getClass());

		fi.reset();
		fi.insertFloatOperand(Double.valueOf(80));
		o = fi.evaluateWithTypeChecking();
		assertNotNull(o);
		assertEquals(Percentage.class, o.getClass());

	}

	@Test
	public void testToMoney() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "ToMoney");
		fi.reset();
		fi.insertStringOperand("80");
		Object o = fi.evaluateWithTypeChecking();
		assertNotNull(o);
		assertEquals(Money.class, o.getClass());

		fi.reset();
		fi.insertIntegerOperand(Integer.valueOf(80));
		o = fi.evaluateWithTypeChecking();
		assertNotNull(o);
		assertEquals(Money.class, o.getClass());

		fi.reset();
		fi.insertFloatOperand(Double.valueOf(80));
		o = fi.evaluateWithTypeChecking();
		assertNotNull(o);
		assertEquals(Money.class, o.getClass());

	}

	@Test
	public void testToMilliseconds() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "ToMilliseconds");
		fi.reset();
		fi.insertStopwatchOperand(new StopwatchTime(20));
		Object o = fi.evaluateWithTypeChecking();
		assertNotNull(o);
		assertEquals(Integer.class, o.getClass());

		fi.reset();
		fi.insertClockTimeOperand(new ClockTime(new java.util.Date()));
		o = fi.evaluateWithTypeChecking();
		assertNotNull(o);
		assertEquals(Long.class, o.getClass());

	}

	@Test
	public void testToMemo() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "ToMemo");
		fi.reset();
		fi.insertStringOperand("20");
		Object o = fi.evaluateWithTypeChecking();
		assertNotNull(o);
		assertEquals(Memo.class, o.getClass());

	}

	@Test
	public void testToLower() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "ToLower");
		fi.reset();
		fi.insertStringOperand("AAP");
		Object o = fi.evaluateWithTypeChecking();
		assertNotNull(o);
		assertEquals("aap", o.toString());

	}

	@Test
	public void testToInteger() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "ToInteger");
		fi.reset();
		fi.insertStringOperand("10");
		Object o = fi.evaluateWithTypeChecking();
		assertNotNull(o);
		assertEquals("10", o.toString());
		assertEquals(Integer.class, o.getClass());

	}

	@Test
	public void testToDouble() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "ToDouble");
		fi.reset();
		fi.insertStringOperand("10.0");
		Object o = fi.evaluateWithTypeChecking();
		assertNotNull(o);
		assertEquals("10.0", o.toString());
		assertEquals(Double.class, o.getClass());

	}

	@Test
	public void testToClockTime() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "ToClockTime");
		fi.reset();
		fi.insertStringOperand("10:00");
		Object o = fi.evaluateWithTypeChecking();
		assertNotNull(o);
		assertEquals("10:00:00", o.toString());
		assertEquals(ClockTime.class, o.getClass());

	}

	@Test
	public void testToBinaryFromUrl() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "ToBinaryFromUrl");
		fi.reset();
		fi.insertStringOperand("http://www.google.com/google.jpg");
		Object o = fi.evaluateWithTypeChecking();
		assertNotNull(o);
		assertEquals(Binary.class, o.getClass());

	}


	@Test
	public void testToBinary() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "ToBinary");
		fi.reset();
		fi.insertStringOperand("aap");
		Object o = fi.evaluateWithTypeChecking();

		assertNotNull(o);

	}

	@Test
	public void testSumProperties() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "SumProperties");
		fi.setInMessage(createTestNavajo());

		fi.reset();
		fi.insertStringOperand("Aap");
		fi.insertStringOperand("Noot");

		Object o = fi.evaluateWithTypeChecking();

		assertNotNull(o);
		assertEquals(Integer.class, o.getClass());
		assertEquals("10", o.toString());

	}

	@Test
	public void testSumMessage() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "SumMessage");
		Navajo doc = createTestNavajo();
		fi.setInMessage(doc);

		fi.reset();
		fi.insertMessageOperand(doc.getMessage("Aap"));
		fi.insertStringOperand("Noot");

		Object o = fi.evaluateWithTypeChecking();

		assertNotNull(o);
		assertEquals(Integer.class, o.getClass());
		assertEquals("10", o.toString());

	}

	@Test
	public void testSumExpressions() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "SumExpressions");
		Navajo doc = createTestNavajo();
		fi.setInMessage(doc);

		fi.reset();
		fi.insertStringOperand("Aap");
		fi.insertStringOperand("[Noot]+10");

		Object o = fi.evaluateWithTypeChecking();

		assertNotNull(o);
		assertEquals(Integer.class, o.getClass());
		assertEquals("20", o.toString());

	}

	@Test
	public void testSum() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "Sum");

		ArrayList<Integer> list = new ArrayList<Integer>();
		list.add(Integer.valueOf(10));
		list.add(Integer.valueOf(10));

		fi.reset();
		fi.insertOperand(Operand.ofList(list));

		Object o = fi.evaluateWithTypeChecking();

		assertNotNull(o);
		assertEquals(Integer.class, o.getClass());
		assertEquals("20", o.toString());

	}

	@Test
	public void testStringPadding() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "StringPadding");
		fi.reset();
		fi.insertStringOperand("aap");
		fi.insertIntegerOperand(Integer.valueOf(10));
		fi.insertStringOperand("*");

		Object o = fi.evaluateWithTypeChecking();

		assertNotNull(o);
		assertEquals(String.class, o.getClass());
		assertEquals("aap*******", o.toString());

	}

	@Test
	public void testStringPadding2() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "StringPadding");
		fi.reset();
		fi.insertStringOperand("aap");
		fi.insertIntegerOperand(Integer.valueOf(10));
		fi.insertStringOperand("*");
		fi.insertBooleanOperand(true);

		Object o = fi.evaluateWithTypeChecking();

		assertNotNull(o);
		assertEquals(String.class, o.getClass());
		assertEquals("*******aap", o.toString());

	}

	@Test
	public void testStringPadding3() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "StringPadding");
		fi.reset();
		fi.insertStringOperand("aap");
		fi.insertIntegerOperand(Integer.valueOf(10));

		Object o = fi.evaluateWithTypeChecking();

		assertNotNull(o);
		assertEquals(String.class, o.getClass());
		assertEquals("aap       ", o.toString());

	}

	@Test
	public void testStringStringFunction() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "StringFunction");
		fi.reset();
		fi.insertStringOperand("indexOf");
		fi.insertStringOperand("Apenoot");
		fi.insertStringOperand("n");

		Object o = fi.evaluateWithTypeChecking();

		assertNotNull(o);
		assertEquals(Integer.class, o.getClass());

		fi.reset();
		fi.insertStringOperand("substring");
		fi.insertStringOperand("Apenoot");
		fi.insertIntegerOperand(Integer.valueOf(0));
		fi.insertIntegerOperand(Integer.valueOf(4));

		o = fi.evaluateWithTypeChecking();

		assertNotNull(o);
		assertEquals(String.class, o.getClass());

	}

	@Test
	public void testStringField() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "StringField");
		fi.reset();
		fi.insertStringOperand("aap,noot,mies");
		fi.insertStringOperand(",");
		fi.insertIntegerOperand(Integer.valueOf(2));

		Object o = fi.evaluateWithTypeChecking();

		assertNotNull(o);
		assertEquals(String.class, o.getClass());

		assertEquals("noot", o.toString());

	}

	@Test
	public void testStringDistance() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "StringDistance");
		fi.reset();
		fi.insertStringOperand("AAP");
		fi.insertStringOperand("NOOT");

		Object o = fi.evaluateWithTypeChecking();

		assertNotNull(o);
		assertEquals(Integer.class, o.getClass());

	}

	@Test
	public void testSize() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "Size");
		fi.reset();
		fi.insertStringOperand("NOOT");

		Object o = fi.evaluateWithTypeChecking();

		assertNotNull(o);
		assertEquals(Integer.class, o.getClass());
		assertEquals("4", o.toString());

		fi.reset();
		fi.insertMessageOperand(createTestNavajo().getMessage("Aap"));

		o = fi.evaluateWithTypeChecking();

		assertNotNull(o);
		assertEquals(Integer.class, o.getClass());
		assertEquals("1", o.toString());

		ArrayList<Integer> list = new ArrayList<Integer>();
		list.add(Integer.valueOf(10));
		list.add(Integer.valueOf(10));
		fi.reset();
		fi.insertListOperand(list);

		o = fi.evaluateWithTypeChecking();

		assertNotNull(o);
		assertEquals(Integer.class, o.getClass());
		assertEquals("2", o.toString());

	}

	@Test
	public void testSetAllProperties() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "SetAllProperties");
		fi.reset();
		fi.setInMessage(createTestNavajo());
		fi.insertMessageOperand(createTestNavajo().getMessage("Aap"));
		fi.insertStringOperand("Noot");
		fi.insertIntegerOperand(Integer.valueOf(2));

		Object o = fi.evaluateWithTypeChecking();

		assertNull(o);

	}

	@Test
	public void testScaleImageMin() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "ScaleImageMin");
		fi.reset();
		fi.insertOperand(Operand.NULL);
		fi.insertIntegerOperand(10);
		fi.insertIntegerOperand(10);

		Object o = fi.evaluateWithTypeChecking();

		assertNull(o);

	}

	@Test
	public void testScaleImageFree() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "ScaleImageFree");
		fi.reset();
		fi.insertOperand(Operand.NULL);
		fi.insertIntegerOperand(10);
		fi.insertIntegerOperand(10);

		Object o = fi.evaluateWithTypeChecking();

		assertNull(o);

	}

	@Test
	public void testRound() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "Round");
		fi.reset();
		fi.insertFloatOperand(Double.valueOf(10.5));
		fi.insertIntegerOperand(0);

		Object o = fi.evaluateWithTypeChecking();

		assertNotNull(o);
		assertEquals(Double.class, o.getClass());
	}

	@Test
	public void testRandomString() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "RandomString");
		fi.reset();
		fi.insertIntegerOperand(10);

		Object o = fi.evaluateWithTypeChecking();

		assertNotNull(o);
		assertEquals(String.class, o.getClass());
	}

	@Test
	public void testRandomString2() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "RandomString");
		fi.reset();
		fi.insertIntegerOperand(10);
		fi.insertStringOperand("abcdef");

		Object o = fi.evaluateWithTypeChecking();

		assertNotNull(o);
		assertEquals(String.class, o.getClass());
		assertEquals(o.toString().indexOf("g"), -1);
	}

	@Test
	public void testRandomInt() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "RandomInt");
		fi.reset();
		fi.insertIntegerOperand(10);
		fi.insertIntegerOperand(11);

		Object o = fi.evaluateWithTypeChecking();

		assertNotNull(o);
		assertEquals(Integer.class, o.getClass());
		assertEquals("10", o.toString());
	}

	@Test
	public void testRandom() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "Random");
		fi.reset();

		Object o = fi.evaluateWithTypeChecking();

		assertNotNull(o);
		assertEquals(Integer.class, o.getClass());

	}

	@Test
	public void testParseStringList() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "ParseStringList");
		fi.reset();
		fi.insertStringOperand("aap,noot,mies");
		fi.insertStringOperand(",");

		Object o = fi.evaluateWithTypeChecking();

		assertNotNull(o);
		assertTrue(List.class.isInstance(o));

	}

	@Test
	public void testParseSelection() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "ParseSelection");
		fi.reset();
		fi.insertStringOperand("aap,noot,mies");

		Object o = fi.evaluateWithTypeChecking();

		assertNotNull(o);
		assertTrue(o.getClass().isArray());

	}

	@Test
	public void testParseDate() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "ParseDate");
		fi.reset();
		fi.insertStringOperand("2008-08-28");
		fi.insertStringOperand("yyyy-MM-dd");

		Operand o = fi.evaluateWithTypeCheckingOperand();
		assertEquals(Property.DATE_PROPERTY,o.type);
		assertNotNull(o.value);
		assertEquals(java.util.Date.class, o.value.getClass());

	}

	@Test
	public void testParameterList() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "ParameterList");
		fi.reset();
		fi.insertIntegerOperand(5);

		Object o = fi.evaluateWithTypeChecking();

		assertNotNull(o);
		assertEquals(String.class, o.getClass());

	}

	@Test
	public void testOffsetDate() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "OffsetDate");
		fi.reset();
		fi.insertDateOperand(new java.util.Date());
		fi.insertIntegerOperand(10);
		fi.insertIntegerOperand(6);
		fi.insertIntegerOperand(17);
		fi.insertIntegerOperand(12);
		fi.insertIntegerOperand(0);
		fi.insertIntegerOperand(0);

		Object o = fi.evaluateWithTypeChecking();

		assertNotNull(o);
		assertEquals(java.util.Date.class, o.getClass());

	}

	@Test
	public void testNow() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "Now");
		fi.reset();

		Object o = fi.evaluateWithTypeChecking();

		assertNotNull(o);
		assertEquals(String.class, o.getClass());

	}

	@Test
	public void testNextMonth() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "NextMonth");
		fi.reset();
		fi.insertIntegerOperand(0);

		Object o = fi.evaluateWithTypeChecking();

		assertNotNull(o);
		assertEquals(java.util.Date.class, o.getClass());

	}

	@Test
	public void testMin() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "Min");
		fi.reset();
		fi.insertIntegerOperand(20);
		fi.insertIntegerOperand(10);

		Object o = fi.evaluateWithTypeChecking();

		assertNotNull(o);
		assertEquals(Integer.class, o.getClass());
		assertEquals("10", o.toString());
	}

	@Test
	public void testMax() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "Max");
		fi.reset();
		fi.insertIntegerOperand(20);
		fi.insertIntegerOperand(10);

		Object o = fi.evaluateWithTypeChecking();

		assertNotNull(o);
		assertEquals(Integer.class, o.getClass());
		assertEquals("20", o.toString());
	}

	@Test
	public void testMergeNavajo() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "MergeNavajo");
		fi.reset();
		fi.insertNavajoOperand(createTestNavajo());
		fi.insertNavajoOperand(createTestNavajo());

		Object o = fi.evaluateWithTypeChecking();

		assertNotNull(o);
		assertTrue(Navajo.class.isInstance(o));

	}

	@Test
	public void testIsServiceCached() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "IsServiceCached");
		fi.reset();
		fi.insertStringOperand("aap");
		fi.insertStringOperand("noot");

		try {
			Object o = fi.evaluateWithTypeChecking();

			assertNotNull(o);
		} catch (NullPointerException n) {

		}

	}

	@Test
	public void testIsNull() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "IsNull");
		fi.reset();
		fi.insertOperand(Operand.NULL);

		Object o = fi.evaluateWithTypeChecking();

		assertNotNull(o);
		assertEquals("true", o.toString());

	}

	@Test
	public void testInMonthTurnInterval() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "InMonthTurnInterval");
		fi.reset();
		fi.insertDateOperand(new java.util.Date());
		fi.insertIntegerOperand(5);
		fi.insertBooleanOperand(true);

		Object o = fi.evaluateWithTypeChecking();

		assertNotNull(o);
		assertEquals(Boolean.class, o.getClass());

	}

	@Test
	public void testGetWeekDayDate() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "GetWeekDayDate");
		fi.reset();
		fi.insertStringOperand("SUN");
		fi.insertStringOperand("forward");
		fi.insertDateOperand(new java.util.Date());

		Object o = fi.evaluateWithTypeChecking();

		assertNotNull(o);
		assertEquals(java.util.Date.class, o.getClass());

	}

	@Test
	public void testGetWeekDayDate2() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "GetWeekDayDate");
		fi.reset();
		fi.insertStringOperand("SUN");
		fi.insertStringOperand("forward");
		// fi.insertOperand(new java.util.Date());

		Object o = fi.evaluateWithTypeChecking();

		assertNotNull(o);
		assertEquals(java.util.Date.class, o.getClass());

	}

	@Test
	public void testGetUrlTime() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "GetUrlTime");
		fi.reset();
		fi.insertStringOperand("http://www.dexels.com/index.php");

		Object o = fi.evaluateWithTypeChecking();

		assertNotNull(o);

	}

	@Test
	public void testGetUrlModificationTime() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "GetUrlModificationTime");
		fi.reset();
		fi.insertStringOperand("http://www.dexels.com/index.php");

		Object o = fi.evaluateWithTypeChecking();

		assertNotNull(o);

	}

	@Test
	public void testGetUrlMimeType() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "GetUrlMimeType");
		fi.reset();
		fi.insertStringOperand("http://www.dexels.com/index.php");

		Object o = fi.evaluateWithTypeChecking();

		assertNotNull(o);

	}

	@Test
	public void testGetSelectedValue() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "GetSelectedValue");
		fi.reset();
		Navajo doc = createTestNavajo();
		fi.insertSelectionListOperand(doc.getProperty("/Single/Selectie")
				.getAllSelectedSelections());

		Object o = fi.evaluateWithTypeChecking();

		assertNotNull(o);
		assertEquals("value", o.toString());
	}

	@Test
	public void testGetSelectedName() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "GetSelectedName");
		fi.reset();
		Navajo doc = createTestNavajo();
		fi.insertSelectionListOperand(doc.getProperty("/Single/Selectie")
				.getAllSelectedSelections());

		Object o = fi.evaluateWithTypeChecking();

		assertNotNull(o);
		assertEquals("key", o.toString());
	}

	@Test
	public void testGetPropertyValue() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "GetPropertyValue");
		fi.reset();
		fi.setInMessage(createTestNavajo());
		Navajo doc = createTestNavajo();
		fi.insertMessageOperand(doc.getMessage("Single"));
		fi.insertStringOperand("Selectie");

		Object o = fi.evaluateWithTypeChecking();

		assertNotNull(o);
		assertEquals("value", o.toString());
	}

	@Test
	public void testGetPropertyType() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "GetPropertyType");
		fi.reset();
		fi.setInMessage(createTestNavajo());
		Navajo doc = createTestNavajo();
		fi.insertStringOperand("/Single/Selectie");

		Object o = fi.evaluateWithTypeChecking();

		assertNotNull(o);
		assertEquals("selection", o.toString());
	}

	@Test
	public void testGetPropertyDirection1() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "GetPropertyDirection");
		fi.reset();
		fi.setInMessage(createTestNavajo());
		Navajo doc = createTestNavajo();
		fi.insertStringOperand("/Single/Selectie");

		Object o = fi.evaluateWithTypeChecking();

		assertNotNull(o);
		assertEquals("in", o.toString());

	}

	@Test
	public void testGetPropertyDirection2() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "GetPropertyDirection");
		fi.reset();
		fi.setInMessage(createTestNavajo());
		Navajo doc = createTestNavajo();
		fi.insertStringOperand("/Single/Vuur");

		Object o = fi.evaluateWithTypeChecking();

		assertNotNull(o);
		assertEquals("out", o.toString());

	}

	@Test
	public void testGetPropertySubType() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "GetPropertySubType");
		fi.reset();
		fi.setInMessage(createTestNavajo());
		Navajo doc = createTestNavajo();
		fi.insertStringOperand("/Single/Selectie");
		fi.insertStringOperand("testsub");

		Object o = fi.evaluateWithTypeChecking();

		assertNull(o);

	}

	@Test
	public void testGetProperty() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "GetProperty");
		fi.reset();
		fi.setInMessage(createTestNavajo());
		Navajo doc = createTestNavajo();
		fi.insertMessageOperand(doc.getMessage("Single"));
		fi.insertStringOperand("Selectie");

		Object o = fi.evaluateWithTypeChecking();

		assertNotNull(o);
		assertTrue(Property.class.isInstance(o));
	}

	@Test
	public void testGetMimeType() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "GetMimeType");
		fi.reset();
		fi.insertOperand(Operand.NULL);

		Object o = fi.evaluateWithTypeChecking();

		assertNull(o);
	}

	@Test
	public void testGetMessage() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "GetMessage");
		Navajo doc = createTestNavajo();
		fi.setInMessage(doc);

		fi.reset();
		fi.insertMessageOperand(doc.getMessage("Aap"));
		fi.insertIntegerOperand(0);

		Object o = fi.evaluateWithTypeChecking();

		assertNotNull(o);
	}

	@Test
	public void testGetLogoImage() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "GetLogoImage");
		Navajo doc = createTestNavajo();
		fi.setInMessage(doc);

		fi.reset();
		fi.insertStringOperand("Logo");

		Object o = fi.evaluateWithTypeChecking();

		assertNotNull(o);
		assertEquals(Binary.class, o.getClass());
	}

	@Test
	public void testGetInitials() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "GetInitials");
		Navajo doc = createTestNavajo();
		fi.setInMessage(doc);

		fi.reset();
		fi.insertStringOperand("aap noot mies");

		Object o = fi.evaluateWithTypeChecking();

		assertNotNull(o);
		assertEquals(String.class, o.getClass());
	}

	@Test
	public void testGetFileExtension() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "GetFileExtension");
		Navajo doc = createTestNavajo();
		fi.setInMessage(doc);

		fi.reset();
		fi.insertOperand(Operand.NULL);

		Object o = fi.evaluateWithTypeChecking();

		assertNull(o);

	}

	@Test
	public void testGetDescription() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "GetDescription");
		fi.reset();
		fi.setInMessage(createTestNavajo());
		Navajo doc = createTestNavajo();
		fi.insertPropertyOperand(doc.getProperty("/Single/Selectie"));

		Object o = fi.evaluateWithTypeChecking();

		assertNotNull(o);
		assertEquals("", o.toString());
	}

	@Test
	public void testGetCurrentMessage() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "GetCurrentMessage");
		fi.reset();
		fi.setInMessage(createTestNavajo());
		Navajo doc = createTestNavajo();

		Object o = fi.evaluateWithTypeChecking();

		assertNull(o);

	}

	@Test
	public void testGetCents() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "GetCents");
		fi.reset();
		fi.setInMessage(createTestNavajo());
		Navajo doc = createTestNavajo();
		fi.insertMoneyOperand(new Money(100.50));

		Object o = fi.evaluateWithTypeChecking();

		assertNotNull(o);

	}

	@Test
	public void testFormatStringList() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "FormatStringList");
		fi.reset();
		ArrayList<String> list = new ArrayList<String>();
		list.add("10");
		list.add("20");
		fi.insertListOperand(list);
		fi.insertStringOperand("&");

		Object o = fi.evaluateWithTypeChecking();

		assertNotNull(o);
		assertEquals("10&20", o.toString());

	}

	@Test
	public void testFormatDecimal() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "FormatDecimal");
		fi.reset();

		fi.insertFloatOperand(10.5);
		fi.insertStringOperand("##");

		Object o = fi.evaluateWithTypeChecking();

		assertNotNull(o);
		assertEquals("10", o.toString());
	}

	@Test
	public void testFormatDate() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "FormatDate");
		fi.reset();

		fi.insertDateOperand(new java.util.Date());
		fi.insertStringOperand("yyyy-MM-dd");

		Object o = fi.evaluateWithTypeChecking();

		assertNotNull(o);

	}

	@Test
	public void testForAll() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "ForAll");
		fi.reset();
		fi.setInMessage(createTestNavajo());
		fi.insertStringOperand("/Aap");
		fi.insertStringOperand("[Noot] != 20");

		Object o = fi.evaluateWithTypeChecking();

		assertNotNull(o);

	}

	@Test
	public void testFileSize() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "FileSize");
		fi.reset();

		fi.insertOperand(Operand.NULL);

		Object o = fi.evaluateWithTypeChecking();

		assertNotNull(o);

	}

	@Test
	public void testFileExists() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "FileExists");
		fi.reset();

		fi.insertStringOperand("noot");

		Object o = fi.evaluateWithTypeChecking();

		assertNotNull(o);
		assertEquals("false", o.toString());
	}

	@Test
	public void testFile() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "File");
		fi.reset();
		fi.insertStringOperand("aap");

		Object o = fi.evaluateWithTypeChecking();
		assertNull(o);

	}

	@Test
	public void testExistsProperty() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "ExistsProperty");
		fi.reset();
		fi.setInMessage(createTestNavajo());
		fi.insertStringOperand("/Single/Selectie");

		Object o = fi.evaluateWithTypeChecking();
		assertNotNull(o);
		assertEquals("true", o.toString());
	}

	@Test
	public void testExists() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "Exists");
		fi.reset();
		fi.setInMessage(createTestNavajo());
		fi.insertMessageOperand(createTestNavajo().getMessage("Aap"));
		fi.insertStringOperand("true");

		Object o = fi.evaluateWithTypeChecking();
		assertNotNull(o);
		assertEquals("true", o.toString());
	}


	@Test
	public void testEvaluateExpression() throws Exception {

		DispatcherFactory df = new DispatcherFactory(new TestDispatcher(
				new TestNavajoConfig()));

		FunctionInterface fi = fff.getInstance(cl, "EvaluateExpression");
		fi.reset();
		Navajo doc = createTestNavajo();
		Header h = NavajoFactory.getInstance().createHeader(doc, "aap", "noot",
				"mies", -1);
		doc.addHeader(h);

		fi.setInMessage(doc);

		fi.insertStringOperand("true");

		Object o = fi.evaluateWithTypeChecking();
		assertNotNull(o);

	}

	@Test
	public void testEuro() throws Exception {

		DispatcherFactory df = new DispatcherFactory(new TestDispatcher(
				new TestNavajoConfig()));

		FunctionInterface fi = fff.getInstance(cl, "Euro");
		fi.reset();

		Object o = fi.evaluateWithTypeChecking();
		assertNotNull(o);

	}

	@Test
	public void testEqualsPattern() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "EqualsPattern");
		fi.reset();
		fi.insertStringOperand("apenoot");
		fi.insertStringOperand("[A-z]*");

		Object o = fi.evaluateWithTypeChecking();
		assertNotNull(o);
		assertEquals(Boolean.class, o.getClass());
		assertEquals("true", o.toString());
	}

	@Test
	public void testEqualsIgnoreCase() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "EqualsIgnoreCase");
		fi.reset();
		fi.insertStringOperand("apenoot");
		fi.insertStringOperand("APeNOoT");

		Object o = fi.evaluateWithTypeChecking();
		assertNotNull(o);
		assertEquals(Boolean.class, o.getClass());
		assertEquals("true", o.toString());
	}


	@Test
	public void testDecimalChar() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "DecimalChar");
		fi.reset();
		fi.insertIntegerOperand(10046);

		Object o = fi.evaluateWithTypeChecking();
		assertNotNull(o);
		assertEquals(String.class, o.getClass());

	}

	@Test
	public void testDateSubtract() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "DateSubtract");
		fi.reset();
		fi.insertDateOperand(new java.util.Date());
		fi.insertDateOperand(new java.util.Date());

		Object o = fi.evaluateWithTypeChecking();
		assertNotNull(o);
		assertEquals(java.util.Date.class, o.getClass());

	}

	@Test
	public void testDateField() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "DateField");
		fi.reset();
		fi.insertDateOperand(new java.util.Date());
		fi.insertStringOperand("YEAR");

		Object o = fi.evaluateWithTypeChecking();
		assertNotNull(o);
		assertEquals(Integer.class, o.getClass());

	}

	@Test
	public void testDateAdd() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "DateAdd");
		fi.reset();
		fi.insertDateOperand(new java.util.Date());
		fi.insertIntegerOperand(2);
		fi.insertStringOperand("YEAR");

		Object o = fi.evaluateWithTypeChecking();
		assertNotNull(o);
		assertEquals(java.util.Date.class, o.getClass());

	}

	@Test
	public void testDate() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "Date");
		fi.reset();
		fi.insertStringOperand("2008-01-01");

		Object o = fi.evaluateWithTypeChecking();
		assertNotNull(o);
		assertEquals(java.util.Date.class, o.getClass());

	}

	@Test
	public void testCurrentTimeMillis() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "CurrentTimeMillis");
		fi.reset();

		Object o = fi.evaluateWithTypeChecking();
		assertNotNull(o);
		assertEquals(String.class, o.getClass());

	}

	@Test
	public void testCreateExpression() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "CreateExpression");
		fi.reset();
		fi.insertStringOperand("aap");

		Object o = fi.evaluateWithTypeChecking();
		assertNotNull(o);
		assertEquals(String.class, o.getClass());

	}

	@Test
	public void testContains() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "Contains");
		fi.reset();
		ArrayList<String> list = new ArrayList<String>();
		list.add("10");
		list.add("20");
		fi.insertListOperand(list);
		fi.insertStringOperand("10");

		Object o = fi.evaluateWithTypeChecking();
		assertNotNull(o);
		assertEquals(Boolean.class, o.getClass());

	}

	@Test
	public void testCheckUrl() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "CheckUrl");
		fi.reset();
		fi.insertStringOperand("http://www.dexels.com");

		Object o = fi.evaluateWithTypeChecking();
		assertNotNull(o);
		assertEquals(Boolean.class, o.getClass());

	}

	@Test
	public void testCheckUniqueness() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "CheckUniqueness");
		fi.reset();
		fi.setInMessage(createTestNavajo());
		fi.insertStringOperand("Aap");
		fi.insertStringOperand("Noot");

		Object o = fi.evaluateWithTypeChecking();
		assertNotNull(o);
		assertEquals(Boolean.class, o.getClass());

	}

	@Test
	public void testCheckRange() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "CheckRange");

		ArrayList<Integer> list = new ArrayList<Integer>();
		list.add(Integer.valueOf(10));
		list.add(Integer.valueOf(10));

		fi.reset();
		fi.setInMessage(createTestNavajo());
		fi.insertListOperand(list);
		fi.insertIntegerOperand(30);

		Object o = fi.evaluateWithTypeChecking();
		assertNotNull(o);
		assertEquals(Boolean.class, o.getClass());

	}

	@Test
	public void testCheckInteger() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "CheckInteger");

		fi.reset();

		fi.insertIntegerOperand(30);

		Object o = fi.evaluateWithTypeChecking();
		assertNotNull(o);
		assertEquals(Boolean.class, o.getClass());

	}

	@Test
	public void testCheckFloat() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "CheckFloat");

		fi.reset();

		fi.insertFloatOperand(30.0);

		Object o = fi.evaluateWithTypeChecking();
		assertNotNull(o);
		assertEquals(Boolean.class, o.getClass());

	}

	@Test
	public void testCheckEmail() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "CheckEmail");

		fi.reset();

		fi.insertStringOperand("arjen@dexels.com");

		Object o = fi.evaluateWithTypeChecking();
		assertNotNull(o);
		assertEquals(Boolean.class, o.getClass());

	}

	@Test
	public void testCheckDate() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "CheckDate");

		fi.reset();

		fi.insertDateOperand(new java.util.Date());

		Object o = fi.evaluateWithTypeChecking();
		assertNotNull(o);
		assertEquals(Boolean.class, o.getClass());

	}

	@Test
	public void testBase64Encode() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "Base64Encode");
		byte[] bytes = "tralala".getBytes();
		String b = Base64.encode(bytes);
		fi.reset();

		fi.insertStringOperand(b);

		Object o = fi.evaluateWithTypeChecking();
		Binary oo = (Binary) o;
		byte[] returned = oo.getData();
		assertTrue(Arrays.equals(returned, bytes));
	}

	@Test
	public void testArraySelection() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "ArraySelection");

		fi.reset();
		fi.setInMessage(createTestNavajo());
		fi.insertStringOperand("Aap");
		fi.insertStringOperand("Noot");
		fi.insertStringOperand("10");

		try {
			Object o = fi.evaluateWithTypeChecking();
		} catch (Exception e) {
		}
	}

	@Test
	public void testAppendArray() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "AppendArray");

		fi.reset();
		fi.setInMessage(createTestNavajo());
		fi.insertMessageOperand(createTestNavajo().getMessage("Aap"));
		fi.insertMessageOperand(createTestNavajo().getMessage("Aap"));

		try {
			Object o = fi.evaluateWithTypeChecking();
		} catch (Exception e) {
		}
	}

	@Test
	public void testAge() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "Age");
		fi.reset();
		fi.setInMessage(createTestNavajo());
		fi.insertDateOperand(new java.util.Date());

		Object o = fi.evaluateWithTypeChecking();
		assertNotNull(o);
		assertEquals(Integer.class, o.getClass());
	}

	@Test
	public void testTrunc() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "Trunc");
		fi.reset();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date d1 = sdf.parse("2009-12-01 12:34");
		Date d2 = sdf.parse("2009-12-01 14:54");
		fi.insertDateOperand(d1);
		Date d3 = (Date) fi.evaluate();

		fi.reset();
		fi.insertDateOperand(d2);
		Date d4 = (Date) fi.evaluate();

		assertEquals(sdf.format(d3), sdf.format(d4));

	}

	@Test
	public void testNavajoRequestToString() throws Exception {
		Navajo n = createTestNavajo();
		FunctionInterface fi = fff.getInstance(cl, "NavajoRequestToString");
		fi.setInMessage(n);
		fi.reset();
		Object o = fi.evaluate();
		assertTrue(((String) o).indexOf("Aap") != -1);
	}

	@Test
	public void testIsEmpty() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "IsEmpty");

		// Empty String.
		fi.reset();
		fi.insertStringOperand("");
		Object o = fi.evaluateWithTypeChecking();
		assertNotNull(o);
		assertEquals(Boolean.TRUE, o);

		// Non Empty String.
		fi.reset();
		fi.insertStringOperand("aap");
		o = fi.evaluateWithTypeChecking();
		assertNotNull(o);
		assertEquals(Boolean.FALSE, o);

		// Null value.
		fi.reset();
		fi.insertOperand(Operand.NULL);
		o = fi.evaluateWithTypeChecking();
		assertNotNull(o);
		assertEquals(Boolean.TRUE, o);

		// Empty list
		fi.reset();
		fi.insertListOperand(new ArrayList<Object>());
		o = fi.evaluateWithTypeChecking();
		assertNotNull(o);
		assertEquals(Boolean.TRUE, o);

		// Non Empty list.
		fi.reset();
		boolean thing = new ArrayList<String>().add("noot");
		fi.insertBooleanOperand(thing);
		o = fi.evaluateWithTypeChecking();
		assertNotNull(o);
		assertEquals(Boolean.FALSE, o);

		// Empty Binary.
		fi.reset();
		fi.insertBinaryOperand(new Binary());
		o = fi.evaluateWithTypeChecking();
		assertNotNull(o);
		assertEquals(Boolean.TRUE, o);

		// Non Empty Binary.
		fi.reset();
		fi.insertBinaryOperand(new Binary("aap".getBytes()));
		o = fi.evaluateWithTypeChecking();
		assertNotNull(o);
		assertEquals(Boolean.FALSE, o);

		// Non empty Clocktime.
		fi.reset();
		fi.insertClockTimeOperand(new ClockTime(new java.util.Date()));
		o = fi.evaluateWithTypeChecking();
		assertNotNull(o);
		assertEquals(Boolean.FALSE, o);
	}

	@Test
	public void testPhone() throws Exception {
	    ValidatePhoneNumber e1 = new ValidatePhoneNumber();
	    e1.reset();
	    e1.insertStringOperand("+31 (0)20 490 4977");
	    ValidatePhoneNumber e2 = new ValidatePhoneNumber();
	    e2.reset();
	    e2.insertStringOperand("020-4904977");
        System.out.println(e1.getStringOperand(0) + " - " + e1.evaluate());
        Assert.assertTrue((Boolean)e1.evaluate());
        System.out.println(e2.getStringOperand(0) + " - " + e2.evaluate());
        Assert.assertTrue((Boolean)e2.evaluate());
	}

	@Test
	public void testWeekDay() throws Exception {

		    // Tests.
		    WeekDay wd = new WeekDay();

		    wd.reset();
		    wd.insertOperand(Operand.NULL);
//		    System.out.println("result = " + wd.evaluate().toString());
		    Assert.assertEquals("THU", wd.evaluate().toString());
		    wd.reset();
		    wd.insertStringOperand("2013-07-16");
//		    System.out.println("result = " + wd.evaluate().toString());
		    Assert.assertEquals("TUE", wd.evaluate().toString());

		    wd.reset();
		    wd.insertDateOperand(new java.util.Date(System.currentTimeMillis()));
//		    System.out.println("result = " + wd.evaluate().toString());
		    Assert.assertEquals("THU", wd.evaluate().toString());

		  }

	@Test
	public  void testCapString() throws TMLExpressionException {
		CapString t = new CapString();
		t.reset();
		t.insertStringOperand("012345678901234567890");
		t.insertIntegerOperand(6);
		String res = (String) t.evaluate();
		System.err.println(">" + res + "<");
		Assert.assertEquals("012345", res);
	}
	
	@Test
	public void testCheckFloatExtended() throws Exception {
		CheckFloat cf = new CheckFloat();

		cf.reset();
		cf.insertIntegerOperand(Integer.valueOf(1234));
		Object result = cf.evaluate();
		System.err.println("result = " + result);
		Assert.assertTrue((boolean) result);
		cf.reset();
		cf.insertFloatOperand(Double.valueOf(5.67));
		result = cf.evaluate();
		System.err.println("result = " + result);
		Assert.assertTrue((boolean) result);

		cf.reset();
		cf.insertFloatOperand(Double.valueOf(+7.98E4));
		result = cf.evaluate();
		System.err.println("result = " + result);
		Assert.assertTrue((boolean) result);

		cf.reset();
		cf.insertFloatOperand(Double.valueOf(-5.43E-2));
		result = cf.evaluate();
		System.err.println("result = " + result);
		Assert.assertTrue((boolean) result);

		cf.reset();
		cf.insertStringOperand("aap");
		result = cf.evaluate();
		System.err.println("result = " + result);
		Assert.assertFalse((boolean) result);

		cf.reset();
		cf.insertOperand(Operand.NULL);
		result = cf.evaluate();
		System.err.println("result = " + result);
		Assert.assertFalse((boolean) result);
	}
	
	@Test
	  public void testCheckIntegerExtended() throws Exception {
		    CheckInteger ci = new CheckInteger();
		    ci.reset();
		    ci.insertStringOperand("aap");
		    Object result = ci.evaluate();
		    System.err.println("result = " + result);
		Assert.assertFalse((boolean) result);
		    ci.reset();
		    ci.insertOperand(Operand.NULL);
		    result = ci.evaluate();
		    System.err.println("result = " + result);
		    Assert.assertFalse((boolean) result);
		  }
	
	@Test
	  public void testCheckUniquenessExtended() throws Exception {
		    String expression = "Hallo \n Hoe is het nou?";
		    CreateExpression ce = new CreateExpression();
		    ce.reset();
		    ce.insertStringOperand(expression);
		    String result = (String) ce.evaluate();
		    System.err.println("result:");
		    System.err.println(result);
		    Operand o = Expression.evaluate(result, null);
		    System.err.println("Evaluated to: ");
		    System.err.println(o.value);
		    Assert.assertEquals(2, ((String)o.value).split("\n").length);
		  }
	
	@Test(expected=TMLExpressionException.class)
	public void testDateTimeInvalidArgument() {
		DateTime dateTime = new DateTime();
		dateTime.reset();
		dateTime.insertIntegerOperand(1);
		dateTime.evaluate();
	}

	@Test(expected=TMLExpressionException.class)
	public void testDateTimeMoreInvalidArgument() {
		DateTime dateTime = new DateTime();
		dateTime.reset();
		dateTime.insertStringOperand("yyyy/MM/dd HH:mm:ss");
		dateTime.insertStringOperand("yyyy/MM/dd HH:mm:ss");
		dateTime.evaluate();
	}

	@Test(expected=TMLExpressionException.class)
	public void testDateTimeAnotherInvalidArgument() {
		DateTime dateTime = new DateTime();
		dateTime.reset();
		dateTime.reset();
		dateTime.insertStringOperand("cc");
		dateTime.evaluate();
	}
	@Test
	public void testDateTime() {
		// Test using default parameter
		DateTime dateTime = new DateTime();
		System.out.println("--------- Testing default pattern ---------");
		dateTime.reset();
		String res = dateTime.evaluate();
		System.err.println("res1: " + res);

		// Test using valid pattern
		System.out.println("--------- Testing valid pattern yyyy/MM/dd HH:mm:ss---------");
		dateTime.reset();
		dateTime.insertStringOperand("\"yyyy/MM/dd HH:mm:ss\"");
		String res2 = dateTime.evaluate();
		System.err.println("res2: " + res2);

		// Test using valid pattern
		System.out.println("--------- Testing valid pattern dd-mm-yyyy ---------");
		dateTime.reset();
		dateTime.insertStringOperand("\"dd-mm-yyyy\"");
		String res3 = dateTime.evaluate();
		System.err.println("res3: " + res3);

		// Test using valid pattern
		System.out.println("--------- Testing valid pattern HH:mm:ss ---------");
		dateTime.reset();
		dateTime.insertStringOperand("\"HH:mm:ss\"");
		String res4 = dateTime.evaluate();
		System.err.println("res4: " + res4);


	}
}