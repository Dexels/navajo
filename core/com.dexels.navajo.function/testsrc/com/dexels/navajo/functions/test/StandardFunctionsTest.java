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
import org.junit.Before;
import org.junit.Test;

import com.dexels.navajo.document.Header;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.document.types.ClockTime;
import com.dexels.navajo.document.types.Memo;
import com.dexels.navajo.document.types.Money;
import com.dexels.navajo.document.types.Percentage;
import com.dexels.navajo.document.types.StopwatchTime;
import com.dexels.navajo.functions.util.FunctionFactoryFactory;
import com.dexels.navajo.functions.util.FunctionFactoryInterface;
import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.parser.TMLExpressionException;
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
	public void testAbs() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "Abs");

		// Test Integer.
		fi.reset();
		fi.insertOperand(new Integer(-10));
		Object o = fi.evaluateWithTypeChecking();
		assertEquals(o.getClass(), Integer.class);
		assertEquals(((Integer) o).intValue(), 10);

		// Test Double.
		fi.reset();
		fi.insertOperand(new Double(-10));
		o = fi.evaluateWithTypeChecking();
		assertEquals(o.getClass(), Double.class);
		assertEquals(((Double) o).intValue(), 10);

		// Test bogus.
		boolean bogus = false;
		fi.reset();
		fi.insertOperand(new String("-10"));
		try {
			o = fi.evaluateWithTypeChecking();
		} catch (TMLExpressionException e) {
			bogus = true;
		}
		assertTrue(bogus);

		// Test null.
		fi.reset();
		fi.insertOperand(null);
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
		fi.insertOperand("aap");
		Object o = fi.evaluateWithTypeChecking();
		assertNull(o);

	}

	@Test
	public void testZip() throws Exception {

		Binary b = new Binary(new byte[] { 1, 1, 1 });
		FunctionInterface fi = fff.getInstance(cl, "Zip");
		fi.reset();
		fi.insertOperand(b);
		fi.insertOperand("aap");
		Object o = fi.evaluateWithTypeChecking();
		assertNotNull(o);
		assertEquals(o.getClass(), Binary.class);
	}

	@Test
	public void testXmlUnescape() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "XmlUnescape");
		fi.reset();
		fi.insertOperand("<><>");
		Object o = fi.evaluate();
		assertNotNull(o);
		assertEquals(o.getClass(), String.class);

		boolean exception = false;
		try {
			fi.reset();
			fi.insertOperand(new Integer(10));
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
		fi.insertOperand("aap");
		Object o = fi.evaluateWithTypeChecking();
		assertNotNull(o);
		assertEquals(o.getClass(), String.class);

	}

	@Test
	public void testWeekday() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "WeekDay");
		fi.reset();
		fi.insertOperand(new java.util.Date());
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
		fi.insertOperand(new Integer(10));
		Object o = fi.evaluateWithTypeChecking();
		assertNull(o);

	}

	@Test
	public void testUnicode() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "Unicode");
		fi.reset();
		fi.insertOperand("10");
		Object o = fi.evaluateWithTypeChecking();
		assertNotNull(o);

	}

	@Test
	public void testURLEncode() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "URLEncode");
		fi.reset();
		fi.insertOperand("10");
		Object o = fi.evaluateWithTypeChecking();
		assertNotNull(o);
		assertEquals(o.getClass(), String.class);

	}

	@Test
	public void testTrim() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "Trim");
		fi.reset();
		fi.insertOperand(" aap ");
		Object o = fi.evaluateWithTypeChecking();
		assertNotNull(o);
		assertEquals("aap", o.toString());

	}

	@Test
	public void testToUpper() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "ToUpper");
		fi.reset();
		fi.insertOperand("aap");
		Object o = fi.evaluateWithTypeChecking();
		assertNotNull(o);
		assertEquals("AAP", o.toString());

	}

	@Test
	public void testToString() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "ToString");
		fi.reset();
		fi.insertOperand("aap");
		Object o = fi.evaluateWithTypeChecking();
		assertNotNull(o);
		assertEquals("aap", o.toString());

		fi.reset();
		fi.insertOperand(new Integer(10));
		o = fi.evaluateWithTypeChecking();
		assertNotNull(o);
		assertEquals("10", o.toString());

		fi.reset();
		fi.insertOperand(new Float(10));
		o = fi.evaluateWithTypeChecking();
		assertNotNull(o);
		assertEquals("10.0", o.toString());

	}

	@Test
	public void testToStopwatchTime() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "ToStopwatchTime");
		fi.reset();
		fi.insertOperand(new java.util.Date());
		Object o = fi.evaluateWithTypeChecking();
		assertNotNull(o);
		assertEquals(StopwatchTime.class, o.getClass());

	}

	@Test
	public void testToSecureImage() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "ToSecureImage");
		fi.reset();
		fi.insertOperand("SECURE");
		Object o = fi.evaluateWithTypeChecking();
		assertNotNull(o);
		assertEquals(Binary.class, o.getClass());

		fi.reset();
		fi.insertOperand(null);
		o = fi.evaluateWithTypeChecking();
		assertNull(o);

		System.err.println(System.currentTimeMillis());
		long l = (new Long("1234567890000").longValue() - System
				.currentTimeMillis());
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MILLISECOND, (int) l);
		System.err.println(SimpleDateFormat.getInstance().format(c.getTime()));

	}

	@Test
	public void testToPercentage() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "ToPercentage");
		fi.reset();
		fi.insertOperand("80");
		Object o = fi.evaluateWithTypeChecking();
		assertNotNull(o);
		assertEquals(Percentage.class, o.getClass());

		fi.reset();
		fi.insertOperand(new Integer(80));
		o = fi.evaluateWithTypeChecking();
		assertNotNull(o);
		assertEquals(Percentage.class, o.getClass());

		fi.reset();
		fi.insertOperand(new Double(80));
		o = fi.evaluateWithTypeChecking();
		assertNotNull(o);
		assertEquals(Percentage.class, o.getClass());

	}

	@Test
	public void testToMoney() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "ToMoney");
		fi.reset();
		fi.insertOperand("80");
		Object o = fi.evaluateWithTypeChecking();
		assertNotNull(o);
		assertEquals(Money.class, o.getClass());

		fi.reset();
		fi.insertOperand(new Integer(80));
		o = fi.evaluateWithTypeChecking();
		assertNotNull(o);
		assertEquals(Money.class, o.getClass());

		fi.reset();
		fi.insertOperand(new Double(80));
		o = fi.evaluateWithTypeChecking();
		assertNotNull(o);
		assertEquals(Money.class, o.getClass());

	}

	@Test
	public void testToMilliseconds() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "ToMilliseconds");
		fi.reset();
		fi.insertOperand(new StopwatchTime(20));
		Object o = fi.evaluateWithTypeChecking();
		assertNotNull(o);
		assertEquals(Integer.class, o.getClass());

		fi.reset();
		fi.insertOperand(new ClockTime(new java.util.Date()));
		o = fi.evaluateWithTypeChecking();
		assertNotNull(o);
		assertEquals(Long.class, o.getClass());

	}

	@Test
	public void testToMemo() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "ToMemo");
		fi.reset();
		fi.insertOperand(new String("20"));
		Object o = fi.evaluateWithTypeChecking();
		assertNotNull(o);
		assertEquals(Memo.class, o.getClass());

	}

	@Test
	public void testToLower() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "ToLower");
		fi.reset();
		fi.insertOperand("AAP");
		Object o = fi.evaluateWithTypeChecking();
		assertNotNull(o);
		assertEquals("aap", o.toString());

	}

	@Test
	public void testToInteger() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "ToInteger");
		fi.reset();
		fi.insertOperand("10");
		Object o = fi.evaluateWithTypeChecking();
		assertNotNull(o);
		assertEquals("10", o.toString());
		assertEquals(Integer.class, o.getClass());

	}

	@Test
	public void testToDouble() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "ToDouble");
		fi.reset();
		fi.insertOperand("10.0");
		Object o = fi.evaluateWithTypeChecking();
		assertNotNull(o);
		assertEquals("10.0", o.toString());
		assertEquals(Double.class, o.getClass());

	}

	@Test
	public void testToClockTime() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "ToClockTime");
		fi.reset();
		fi.insertOperand("10:00");
		Object o = fi.evaluateWithTypeChecking();
		assertNotNull(o);
		assertEquals("10:00:00", o.toString());
		assertEquals(ClockTime.class, o.getClass());

	}

	@Test
	public void testToBinaryFromUrl() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "ToBinaryFromUrl");
		fi.reset();
		fi.insertOperand("http://www.google.com/google.jpg");
		Object o = fi.evaluateWithTypeChecking();
		assertNotNull(o);
		assertEquals(Binary.class, o.getClass());

	}

	@Test
	public void testToBinaryFromPath() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "ToBinaryFromPath");
		fi.reset();
		fi.insertOperand("/aeap");
		Object o = fi.evaluateWithTypeChecking();

		assertNotNull(o);

	}

	@Test
	public void testToBinary() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "ToBinary");
		fi.reset();
		fi.insertOperand("aap");
		Object o = fi.evaluateWithTypeChecking();

		assertNotNull(o);

	}

	@Test
	public void testSumProperties() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "SumProperties");
		fi.setInMessage(createTestNavajo());

		fi.reset();
		fi.insertOperand("Aap");
		fi.insertOperand("Noot");

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
		fi.insertOperand(doc.getMessage("Aap"));
		fi.insertOperand("Noot");

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
		fi.insertOperand("Aap");
		fi.insertOperand("[Noot]+10");

		Object o = fi.evaluateWithTypeChecking();

		assertNotNull(o);
		assertEquals(Integer.class, o.getClass());
		assertEquals("20", o.toString());

	}

	@Test
	public void testSum() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "Sum");

		ArrayList<Integer> list = new ArrayList<Integer>();
		list.add(new Integer(10));
		list.add(new Integer(10));

		fi.reset();
		fi.insertOperand(list);

		Object o = fi.evaluateWithTypeChecking();

		assertNotNull(o);
		assertEquals(Integer.class, o.getClass());
		assertEquals("20", o.toString());

	}

	@Test
	public void testStringPadding() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "StringPadding");
		fi.reset();
		fi.insertOperand("aap");
		fi.insertOperand(new Integer(10));
		fi.insertOperand("*");

		Object o = fi.evaluateWithTypeChecking();

		assertNotNull(o);
		assertEquals(String.class, o.getClass());
		assertEquals("aap*******", o.toString());

	}

	@Test
	public void testStringPadding2() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "StringPadding");
		fi.reset();
		fi.insertOperand("aap");
		fi.insertOperand(new Integer(10));
		fi.insertOperand("*");
		fi.insertOperand(true);

		Object o = fi.evaluateWithTypeChecking();

		assertNotNull(o);
		assertEquals(String.class, o.getClass());
		assertEquals("*******aap", o.toString());

	}

	@Test
	public void testStringPadding3() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "StringPadding");
		fi.reset();
		fi.insertOperand("aap");
		fi.insertOperand(new Integer(10));

		Object o = fi.evaluateWithTypeChecking();

		assertNotNull(o);
		assertEquals(String.class, o.getClass());
		assertEquals("aap       ", o.toString());

	}

	@Test
	public void testStringStringFunction() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "StringFunction");
		fi.reset();
		fi.insertOperand("indexOf");
		fi.insertOperand("Apenoot");
		fi.insertOperand("n");

		Object o = fi.evaluateWithTypeChecking();

		assertNotNull(o);
		assertEquals(Integer.class, o.getClass());

		fi.reset();
		fi.insertOperand("substring");
		fi.insertOperand("Apenoot");
		fi.insertOperand(new Integer(0));
		fi.insertOperand(new Integer(4));

		o = fi.evaluateWithTypeChecking();

		assertNotNull(o);
		assertEquals(String.class, o.getClass());

	}

	@Test
	public void testStringField() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "StringField");
		fi.reset();
		fi.insertOperand("aap,noot,mies");
		fi.insertOperand(",");
		fi.insertOperand(new Integer(2));

		Object o = fi.evaluateWithTypeChecking();

		assertNotNull(o);
		assertEquals(String.class, o.getClass());

		assertEquals("noot", o.toString());

	}

	@Test
	public void testStringDistance() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "StringDistance");
		fi.reset();
		fi.insertOperand("AAP");
		fi.insertOperand("NOOT");

		Object o = fi.evaluateWithTypeChecking();

		assertNotNull(o);
		assertEquals(Integer.class, o.getClass());

	}

	@Test
	public void testSize() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "Size");
		fi.reset();
		fi.insertOperand("NOOT");

		Object o = fi.evaluateWithTypeChecking();

		assertNotNull(o);
		assertEquals(Integer.class, o.getClass());
		assertEquals("4", o.toString());

		fi.reset();
		fi.insertOperand(createTestNavajo().getMessage("Aap"));

		o = fi.evaluateWithTypeChecking();

		assertNotNull(o);
		assertEquals(Integer.class, o.getClass());
		assertEquals("1", o.toString());

		ArrayList<Integer> list = new ArrayList<Integer>();
		list.add(new Integer(10));
		list.add(new Integer(10));
		fi.reset();
		fi.insertOperand(list);

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
		fi.insertOperand(createTestNavajo().getMessage("Aap"));
		fi.insertOperand("Noot");
		fi.insertOperand(new Integer(2));

		Object o = fi.evaluateWithTypeChecking();

		assertNull(o);

	}

	@Test
	public void testScaleImageMin() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "ScaleImageMin");
		fi.reset();
		fi.insertOperand(null);
		fi.insertOperand(10);
		fi.insertOperand(10);

		Object o = fi.evaluateWithTypeChecking();

		assertNull(o);

	}

	@Test
	public void testScaleImageFree() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "ScaleImageFree");
		fi.reset();
		fi.insertOperand(null);
		fi.insertOperand(10);
		fi.insertOperand(10);

		Object o = fi.evaluateWithTypeChecking();

		assertNull(o);

	}

	@Test
	public void testRound() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "Round");
		fi.reset();
		fi.insertOperand(new Double(10.5));
		fi.insertOperand(0);

		Object o = fi.evaluateWithTypeChecking();

		assertNotNull(o);
		assertEquals(Double.class, o.getClass());
	}

	@Test
	public void testRandomString() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "RandomString");
		fi.reset();
		fi.insertOperand(10);

		Object o = fi.evaluateWithTypeChecking();

		assertNotNull(o);
		assertEquals(String.class, o.getClass());
	}

	@Test
	public void testRandomString2() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "RandomString");
		fi.reset();
		fi.insertOperand(10);
		fi.insertOperand("abcdef");

		Object o = fi.evaluateWithTypeChecking();

		assertNotNull(o);
		assertEquals(String.class, o.getClass());
		assertEquals(o.toString().indexOf("g"), -1);
	}

	@Test
	public void testRandomInt() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "RandomInt");
		fi.reset();
		fi.insertOperand(10);
		fi.insertOperand(11);

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
		fi.insertOperand("aap,noot,mies");
		fi.insertOperand(",");

		Object o = fi.evaluateWithTypeChecking();

		assertNotNull(o);
		assertTrue(List.class.isInstance(o));

	}

	@Test
	public void testParseSelection() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "ParseSelection");
		fi.reset();
		fi.insertOperand("aap,noot,mies");

		Object o = fi.evaluateWithTypeChecking();

		assertNotNull(o);
		assertTrue(o.getClass().isArray());

	}

	@Test
	public void testParseDate() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "ParseDate");
		fi.reset();
		fi.insertOperand("2008-08-28");
		fi.insertOperand("yyyy-MM-dd");

		Object o = fi.evaluateWithTypeChecking();

		assertNotNull(o);
		assertEquals(java.util.Date.class, o.getClass());

	}

	@Test
	public void testParameterList() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "ParameterList");
		fi.reset();
		fi.insertOperand(5);

		Object o = fi.evaluateWithTypeChecking();

		assertNotNull(o);
		assertEquals(String.class, o.getClass());

	}

	@Test
	public void testOffsetDate() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "OffsetDate");
		fi.reset();
		fi.insertOperand(new java.util.Date());
		fi.insertOperand(10);
		fi.insertOperand(6);
		fi.insertOperand(17);
		fi.insertOperand(12);
		fi.insertOperand(0);
		fi.insertOperand(0);

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
		fi.insertOperand(0);

		Object o = fi.evaluateWithTypeChecking();

		assertNotNull(o);
		assertEquals(java.util.Date.class, o.getClass());

	}

	@Test
	public void testMin() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "Min");
		fi.reset();
		fi.insertOperand(20);
		fi.insertOperand(10);

		Object o = fi.evaluateWithTypeChecking();

		assertNotNull(o);
		assertEquals(Integer.class, o.getClass());
		assertEquals("10", o.toString());
	}

	@Test
	public void testMax() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "Max");
		fi.reset();
		fi.insertOperand(20);
		fi.insertOperand(10);

		Object o = fi.evaluateWithTypeChecking();

		assertNotNull(o);
		assertEquals(Integer.class, o.getClass());
		assertEquals("20", o.toString());
	}

	@Test
	public void testMergeNavajo() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "MergeNavajo");
		fi.reset();
		fi.insertOperand(createTestNavajo());
		fi.insertOperand(createTestNavajo());

		Object o = fi.evaluateWithTypeChecking();

		assertNotNull(o);
		assertTrue(Navajo.class.isInstance(o));

	}

	@Test
	public void testIsServiceCached() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "IsServiceCached");
		fi.reset();
		fi.insertOperand("aap");
		fi.insertOperand("noot");

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
		fi.insertOperand(null);

		Object o = fi.evaluateWithTypeChecking();

		assertNotNull(o);
		assertEquals("true", o.toString());

	}

	@Test
	public void testInMonthTurnInterval() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "InMonthTurnInterval");
		fi.reset();
		fi.insertOperand(new java.util.Date());
		fi.insertOperand(5);
		fi.insertOperand(true);

		Object o = fi.evaluateWithTypeChecking();

		assertNotNull(o);
		assertEquals(Boolean.class, o.getClass());

	}

	@Test
	public void testGetWeekDayDate() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "GetWeekDayDate");
		fi.reset();
		fi.insertOperand("SUN");
		fi.insertOperand("forward");
		fi.insertOperand(new java.util.Date());

		Object o = fi.evaluateWithTypeChecking();

		assertNotNull(o);
		assertEquals(java.util.Date.class, o.getClass());

	}

	@Test
	public void testGetWeekDayDate2() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "GetWeekDayDate");
		fi.reset();
		fi.insertOperand("SUN");
		fi.insertOperand("forward");
		// fi.insertOperand(new java.util.Date());

		Object o = fi.evaluateWithTypeChecking();

		assertNotNull(o);
		assertEquals(java.util.Date.class, o.getClass());

	}

	@Test
	public void testGetUrlTime() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "GetUrlTime");
		fi.reset();
		fi.insertOperand("http://www.dexels.com/index.php");

		Object o = fi.evaluateWithTypeChecking();

		assertNotNull(o);

	}

	@Test
	public void testGetUrlModificationTime() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "GetUrlModificationTime");
		fi.reset();
		fi.insertOperand("http://www.dexels.com/index.php");

		Object o = fi.evaluateWithTypeChecking();

		assertNotNull(o);

	}

	@Test
	public void testGetUrlMimeType() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "GetUrlMimeType");
		fi.reset();
		fi.insertOperand("http://www.dexels.com/index.php");

		Object o = fi.evaluateWithTypeChecking();

		assertNotNull(o);

	}

	@Test
	public void testGetSelectedValue() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "GetSelectedValue");
		fi.reset();
		Navajo doc = createTestNavajo();
		fi.insertOperand(doc.getProperty("/Single/Selectie")
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
		fi.insertOperand(doc.getProperty("/Single/Selectie")
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
		fi.insertOperand(doc.getMessage("Single"));
		fi.insertOperand("Selectie");

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
		fi.insertOperand("/Single/Selectie");

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
		fi.insertOperand("/Single/Selectie");

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
		fi.insertOperand("/Single/Vuur");

		Object o = fi.evaluateWithTypeChecking();

		assertNotNull(o);
		assertEquals("out", o.toString());

	}

	@Test
	public void testGetPropertyAttribute1() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "GetPropertyAttribute");
		fi.reset();
		fi.setInMessage(createTestNavajo());
		Navajo doc = createTestNavajo();
		fi.insertOperand("/Single/Vuur");
		fi.insertOperand("direction");

		Object o = fi.evaluateWithTypeChecking();

		assertNotNull(o);
		assertEquals("out", o.toString());

	}

	@Test
	public void testGetPropertyAttribute2() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "GetPropertyAttribute");
		fi.reset();
		fi.setInMessage(createTestNavajo());
		Navajo doc = createTestNavajo();
		fi.insertOperand("/Single/Vuur");
		fi.insertOperand("type");

		Object o = fi.evaluateWithTypeChecking();

		assertNotNull(o);
		assertEquals("integer", o.toString());

	}

	@Test
	public void testGetPropertySubType() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "GetPropertySubType");
		fi.reset();
		fi.setInMessage(createTestNavajo());
		Navajo doc = createTestNavajo();
		fi.insertOperand("/Single/Selectie");
		fi.insertOperand("testsub");

		Object o = fi.evaluateWithTypeChecking();

		assertNull(o);

	}

	@Test
	public void testGetProperty() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "GetProperty");
		fi.reset();
		fi.setInMessage(createTestNavajo());
		Navajo doc = createTestNavajo();
		fi.insertOperand(doc.getMessage("Single"));
		fi.insertOperand("Selectie");

		Object o = fi.evaluateWithTypeChecking();

		assertNotNull(o);
		assertTrue(Property.class.isInstance(o));
	}

	@Test
	public void testGetMimeType() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "GetMimeType");
		fi.reset();
		fi.insertOperand(null);

		Object o = fi.evaluateWithTypeChecking();

		assertNull(o);
	}

	@Test
	public void testGetMessage() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "GetMessage");
		Navajo doc = createTestNavajo();
		fi.setInMessage(doc);

		fi.reset();
		fi.insertOperand(doc.getMessage("Aap"));
		fi.insertOperand(0);

		Object o = fi.evaluateWithTypeChecking();

		assertNotNull(o);
	}

	@Test
	public void testGetLogoImage() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "GetLogoImage");
		Navajo doc = createTestNavajo();
		fi.setInMessage(doc);

		fi.reset();
		fi.insertOperand("Logo");

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
		fi.insertOperand("aap noot mies");

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
		fi.insertOperand(null);

		Object o = fi.evaluateWithTypeChecking();

		assertNull(o);

	}

	@Test
	public void testGetDescription() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "GetDescription");
		fi.reset();
		fi.setInMessage(createTestNavajo());
		Navajo doc = createTestNavajo();
		fi.insertOperand(doc.getProperty("/Single/Selectie"));

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
		fi.insertOperand(new Money(100.50));

		Object o = fi.evaluateWithTypeChecking();

		assertNotNull(o);

	}

	@Test
	public void testFormatStringList() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "FormatStringList");
		fi.reset();
		ArrayList<String> list = new ArrayList<String>();
		list.add(new String("10"));
		list.add(new String("20"));
		fi.insertOperand(list);
		fi.insertOperand("&");

		Object o = fi.evaluateWithTypeChecking();

		assertNotNull(o);
		assertEquals("10&20", o.toString());

	}

	@Test
	public void testFormatDecimal() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "FormatDecimal");
		fi.reset();

		fi.insertOperand(10.5);
		fi.insertOperand("##");

		Object o = fi.evaluateWithTypeChecking();

		assertNotNull(o);
		assertEquals("10", o.toString());
	}

	@Test
	public void testFormatDate() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "FormatDate");
		fi.reset();

		fi.insertOperand(new java.util.Date());
		fi.insertOperand("yyyy-MM-dd");

		Object o = fi.evaluateWithTypeChecking();

		assertNotNull(o);

	}

	@Test
	public void testForAll() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "ForAll");
		fi.reset();
		fi.setInMessage(createTestNavajo());
		fi.insertOperand("/Aap");
		fi.insertOperand("[Noot] != 20");

		Object o = fi.evaluateWithTypeChecking();

		assertNotNull(o);

	}

	@Test
	public void testFileSize() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "FileSize");
		fi.reset();

		fi.insertOperand(null);

		Object o = fi.evaluateWithTypeChecking();

		assertNotNull(o);

	}

	@Test
	public void testFileExists() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "FileExists");
		fi.reset();

		fi.insertOperand("noot");

		Object o = fi.evaluateWithTypeChecking();

		assertNotNull(o);
		assertEquals("false", o.toString());
	}

	@Test
	public void testFile() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "File");
		fi.reset();
		fi.insertOperand("aap");

		Object o = fi.evaluateWithTypeChecking();
		assertNull(o);

	}

	@Test
	public void testExistsProperty() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "ExistsProperty");
		fi.reset();
		fi.setInMessage(createTestNavajo());
		fi.insertOperand("/Single/Selectie");

		Object o = fi.evaluateWithTypeChecking();
		assertNotNull(o);
		assertEquals("true", o.toString());
	}

	@Test
	public void testExists() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "Exists");
		fi.reset();
		fi.setInMessage(createTestNavajo());
		fi.insertOperand(createTestNavajo().getMessage("Aap"));
		fi.insertOperand("true");

		Object o = fi.evaluateWithTypeChecking();
		assertNotNull(o);
		assertEquals("true", o.toString());
	}

	@Test
	public void testExecuteScript() throws Exception {

		DispatcherFactory df = new DispatcherFactory(new TestDispatcher(
				new TestNavajoConfig()));

		FunctionInterface fi = fff.getInstance(cl, "ExecuteScript");
		fi.reset();
		Navajo doc = createTestNavajo();
		Header h = NavajoFactory.getInstance().createHeader(doc, "aap", "noot",
				"mies", -1);
		doc.addHeader(h);

		fi.setInMessage(doc);

		fi.insertOperand("<tsl/>");

		try {
			Object o = fi.evaluateWithTypeChecking();
		} catch (Exception cnfe) {
		}

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

		fi.insertOperand("true");

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
		fi.insertOperand("apenoot");
		fi.insertOperand("[A-z]*");

		Object o = fi.evaluateWithTypeChecking();
		assertNotNull(o);
		assertEquals(Boolean.class, o.getClass());
		assertEquals("true", o.toString());
	}

	@Test
	public void testEqualsIgnoreCase() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "EqualsIgnoreCase");
		fi.reset();
		fi.insertOperand("apenoot");
		fi.insertOperand("APeNOoT");

		Object o = fi.evaluateWithTypeChecking();
		assertNotNull(o);
		assertEquals(Boolean.class, o.getClass());
		assertEquals("true", o.toString());
	}


	@Test
	public void testDecimalChar() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "DecimalChar");
		fi.reset();
		fi.insertOperand(10046);

		Object o = fi.evaluateWithTypeChecking();
		assertNotNull(o);
		assertEquals(String.class, o.getClass());

	}

	@Test
	public void testDateSubstract() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "DateSubstract");
		fi.reset();
		fi.insertOperand(new java.util.Date());
		fi.insertOperand(new java.util.Date());

		Object o = fi.evaluateWithTypeChecking();
		assertNotNull(o);
		assertEquals(java.util.Date.class, o.getClass());

	}

	@Test
	public void testDateField() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "DateField");
		fi.reset();
		fi.insertOperand(new java.util.Date());
		fi.insertOperand("YEAR");

		Object o = fi.evaluateWithTypeChecking();
		assertNotNull(o);
		assertEquals(Integer.class, o.getClass());

	}

	@Test
	public void testDateAdd() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "DateAdd");
		fi.reset();
		fi.insertOperand(new java.util.Date());
		fi.insertOperand(2);
		fi.insertOperand("YEAR");

		Object o = fi.evaluateWithTypeChecking();
		assertNotNull(o);
		assertEquals(java.util.Date.class, o.getClass());

	}

	@Test
	public void testDate() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "Date");
		fi.reset();
		fi.insertOperand("2008-01-01");

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
		fi.insertOperand("aap");

		Object o = fi.evaluateWithTypeChecking();
		assertNotNull(o);
		assertEquals(String.class, o.getClass());

	}

	@Test
	public void testContains() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "Contains");
		fi.reset();
		ArrayList<String> list = new ArrayList<String>();
		list.add(new String("10"));
		list.add(new String("20"));
		fi.insertOperand(list);
		fi.insertOperand("10");

		Object o = fi.evaluateWithTypeChecking();
		assertNotNull(o);
		assertEquals(Boolean.class, o.getClass());

	}

	@Test
	public void testCheckUrl() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "CheckUrl");
		fi.reset();
		fi.insertOperand("http://www.dexels.com");

		Object o = fi.evaluateWithTypeChecking();
		assertNotNull(o);
		assertEquals(Boolean.class, o.getClass());

	}

	@Test
	public void testCheckUniqueness() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "CheckUniqueness");
		fi.reset();
		fi.setInMessage(createTestNavajo());
		fi.insertOperand("Aap");
		fi.insertOperand("Noot");

		Object o = fi.evaluateWithTypeChecking();
		assertNotNull(o);
		assertEquals(Boolean.class, o.getClass());

	}

	@Test
	public void testCheckRange() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "CheckRange");

		ArrayList<Integer> list = new ArrayList<Integer>();
		list.add(new Integer(10));
		list.add(new Integer(10));

		fi.reset();
		fi.setInMessage(createTestNavajo());
		fi.insertOperand(list);
		fi.insertOperand(30);

		Object o = fi.evaluateWithTypeChecking();
		assertNotNull(o);
		assertEquals(Boolean.class, o.getClass());

	}

	@Test
	public void testCheckInteger() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "CheckInteger");

		fi.reset();

		fi.insertOperand(30);

		Object o = fi.evaluateWithTypeChecking();
		assertNotNull(o);
		assertEquals(Boolean.class, o.getClass());

	}

	@Test
	public void testCheckFloat() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "CheckFloat");

		fi.reset();

		fi.insertOperand(30.0);

		Object o = fi.evaluateWithTypeChecking();
		assertNotNull(o);
		assertEquals(Boolean.class, o.getClass());

	}

	@Test
	public void testCheckEmail() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "CheckEmail");

		fi.reset();

		fi.insertOperand("arjen@dexels.com");

		Object o = fi.evaluateWithTypeChecking();
		assertNotNull(o);
		assertEquals(Boolean.class, o.getClass());

	}

	@Test
	public void testCheckDate() throws Exception {

		FunctionInterface fi = fff.getInstance(cl, "CheckDate");

		fi.reset();

		fi.insertOperand(new java.util.Date());

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

		fi.insertOperand(b);

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
		fi.insertOperand("Aap");
		fi.insertOperand("Noot");
		fi.insertOperand("10");

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
		fi.insertOperand(createTestNavajo().getMessage("Aap"));
		fi.insertOperand(createTestNavajo().getMessage("Aap"));

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
		fi.insertOperand(new java.util.Date());

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
		fi.insertOperand(d1);
		Date d3 = (Date) fi.evaluate();

		fi.reset();
		fi.insertOperand(d2);
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
		fi.insertOperand(new String(""));
		Object o = fi.evaluateWithTypeChecking();
		assertNotNull(o);
		assertEquals(Boolean.TRUE, o);

		// Non Empty String.
		fi.reset();
		fi.insertOperand(new String("aap"));
		o = fi.evaluateWithTypeChecking();
		assertNotNull(o);
		assertEquals(Boolean.FALSE, o);

		// Null value.
		fi.reset();
		fi.insertOperand(null);
		o = fi.evaluateWithTypeChecking();
		assertNotNull(o);
		assertEquals(Boolean.TRUE, o);

		// Empty list
		fi.reset();
		fi.insertOperand(new ArrayList<Object>());
		o = fi.evaluateWithTypeChecking();
		assertNotNull(o);
		assertEquals(Boolean.TRUE, o);

		// Non Empty list.
		fi.reset();
		fi.insertOperand(new ArrayList<String>().add(new String("noot")));
		o = fi.evaluateWithTypeChecking();
		assertNotNull(o);
		assertEquals(Boolean.FALSE, o);

		// Empty Binary.
		fi.reset();
		fi.insertOperand(new Binary());
		o = fi.evaluateWithTypeChecking();
		assertNotNull(o);
		assertEquals(Boolean.TRUE, o);

		// Non Empty Binary.
		fi.reset();
		fi.insertOperand(new Binary("aap".getBytes()));
		o = fi.evaluateWithTypeChecking();
		assertNotNull(o);
		assertEquals(Boolean.FALSE, o);

		// Non empty Clocktime.
		fi.reset();
		fi.insertOperand(new ClockTime(new java.util.Date()));
		o = fi.evaluateWithTypeChecking();
		assertNotNull(o);
		assertEquals(Boolean.FALSE, o);
	}

}