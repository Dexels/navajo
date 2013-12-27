import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.StringReader;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;


import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.Selection;
import com.dexels.navajo.document.base.BaseMessageImpl;
import com.dexels.navajo.document.base.BaseNavajoImpl;
import com.dexels.navajo.document.base.BasePropertyImpl;
import com.dexels.navajo.document.base.BaseSelectionImpl;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.document.types.ClockTime;
import com.dexels.navajo.document.types.Money;
import com.dexels.navajo.document.types.Percentage;
import com.dexels.navajo.document.types.StopwatchTime;

public class TestProperty {

	private final static Logger logger = LoggerFactory
			.getLogger(TestProperty.class);
	NavajoDocumentTestFicture navajodocumenttestfictureInst = new NavajoDocumentTestFicture();
	private Navajo testDoc;

	@Before
	public void setUp() {
		navajodocumenttestfictureInst.setUp();
		testDoc = navajodocumenttestfictureInst.testDoc;
	}

	@After
	public void tearDown() {
		navajodocumenttestfictureInst.tearDown();
	}

	@Test
	public void tesSetAnyValue() throws Exception {
		Navajo n = NavajoFactory.getInstance().createNavajo();
		Property p1 = NavajoFactory.getInstance().createProperty(n, "Aap", "",
				"", "");

		// String
		p1.setAnyValue("Apenoot");
		assertEquals("string", p1.getType());
		assertEquals("Apenoot", p1.getValue());
		assertTrue(p1.getTypedValue().equals("Apenoot"));

		// Integer
		p1.setAnyValue(new Integer(50));
		assertEquals("integer", p1.getType());
		assertEquals("50", p1.getValue());
		assertTrue(p1.getTypedValue().equals(new Integer(50)));

		// Double
		p1.setAnyValue(new Double(50));
		assertEquals("float", p1.getType());
		assertEquals("50.0", p1.getValue());
		assertTrue(p1.getTypedValue().equals(new Double(50)));

		// Float
		p1.setAnyValue(new Float(50));
		assertEquals("float", p1.getType());
		assertEquals("50.0", p1.getValue());
		assertTrue(p1.getTypedValue().equals(new Double(50)));

		// Date
		Date d = new java.util.Date();
		p1.setAnyValue(d);
		String expectedFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SS")
				.format(d);
		assertEquals("date", p1.getType());
		assertEquals(expectedFormat, p1.getValue());
		assertTrue(p1.getTypedValue().equals(d));

		// Long
		p1.setAnyValue(new Long(10));
		assertEquals("long", p1.getType());
		assertEquals("10", p1.getValue());
		assertTrue(p1.getTypedValue().equals(new Long(10)));

		// Boolean
		p1.setAnyValue(Boolean.TRUE);
		assertEquals("boolean", p1.getType());
		assertEquals("true", p1.getValue());
		assertTrue(p1.getTypedValue().equals(Boolean.TRUE));

		// Binary
		Binary b = new Binary("Mooie array".getBytes());
		p1.setAnyValue(b);
		assertEquals("binary", p1.getType());
		Binary b1 = (Binary) p1.getTypedValue();
		String expected = new String(b1.getData());
		assertEquals("Mooie array", expected);

		// Money
		p1.setAnyValue(new Money(5000));
		assertEquals("money", p1.getType());

		assertEquals("5000.00", p1.getValue());
		assertTrue(p1.getTypedValue().equals(new Money(5000)));

		// ClockTime
		Date d1 = new java.util.Date();
		ClockTime ct = new ClockTime(d1);
		String expectedFormat2 = ct.toString();
		p1.setAnyValue(ct);
		assertEquals("clocktime", p1.getType());
		assertEquals(expectedFormat2, p1.getValue());
		assertTrue(p1.getTypedValue().equals(new ClockTime(d1)));

		// StopwatchTime
		Date d2 = new java.util.Date();
		String format = new SimpleDateFormat("HH:mm:ss:SSS").format(d2);
		StopwatchTime swt = new StopwatchTime(format);
		p1.setAnyValue(swt);
		assertEquals("stopwatchtime", p1.getType());
		logger.info("FORM: " + format + " val: " + p1.getValue());
		assertEquals(format, p1.getValue());
		assertTrue(p1.getTypedValue().equals(new StopwatchTime(format)));

		// Percentage
		Percentage p = new Percentage(50);
		p1.setAnyValue(p);
		assertEquals("percentage", p1.getType());
		assertEquals("50.0", p1.getValue());
		assertTrue(p1.getTypedValue().equals(new Percentage(50)));

	}

	@Test
	public void tesAddSelection() throws NavajoException {

		Property testSelectionProp = NavajoFactory.getInstance()
				.createProperty(testDoc, "testselectionproperty", "+", "",
						Property.DIR_IN);
		Selection s1 = NavajoFactory.getInstance().createSelection(testDoc,
				"firstselection", "0", true);
		testSelectionProp.addSelection(s1);
		Selection s2 = testSelectionProp.getSelection("firstselection");
		Assert.assertNotNull(s2);
		Assert.assertEquals(s2.isSelected(), s1.isSelected());
		Assert.assertEquals(s2.getName(), s1.getName());
		Assert.assertEquals(s2.getValue(), s1.getValue());
		// ArrayList selections =
		// ((ArrayList)testSelectionProp.getTypedValue());
		Selection s3 = NavajoFactory.getInstance().createSelection(testDoc,
				"firstselection", "1", false);
		testSelectionProp.addSelection(s3);
		s2 = testSelectionProp.getSelection("firstselection");
		Assert.assertNotNull(s2);
		Assert.assertEquals(s2.isSelected(), s3.isSelected());
		Assert.assertEquals(s2.getName(), s3.getName());
		Assert.assertEquals(s2.getValue(), s3.getValue());
	}

	@Test
	public void tesAddSelectionWithoutReplace() throws NavajoException {

		Property testSelectionProp = NavajoFactory.getInstance()
				.createProperty(testDoc, "testselectionproperty", "+", "",
						Property.DIR_IN);
		Selection s1 = NavajoFactory.getInstance().createSelection(testDoc,
				"firstselection", "0", true);
		testSelectionProp.addSelectionWithoutReplace(s1);
		Selection s2 = testSelectionProp.getSelection("firstselection");
		Assert.assertNotNull(s2);
		Assert.assertEquals(s2.isSelected(), s1.isSelected());
		Assert.assertEquals(s2.getName(), s1.getName());
		Assert.assertEquals(s2.getValue(), s1.getValue());
		Selection s3 = NavajoFactory.getInstance().createSelection(testDoc,
				"firstselection", "1", false);
		testSelectionProp.addSelectionWithoutReplace(s3);
		int count = 0;
		List<Selection> all = testSelectionProp.getAllSelections();
		for (int i = 0; i < all.size(); i++) {
			if ((all.get(i)).getName().equals("firstselection"))
				count++;
		}
		Assert.assertEquals(2, count);

	}

	@Test
	public void tesClearSelections() throws NavajoException {
		Property testSelectionProp = NavajoFactory.getInstance()
				.createProperty(testDoc, "testselectionproperty", "+", "",
						Property.DIR_IN);
		Selection s1 = NavajoFactory.getInstance().createSelection(testDoc,
				"firstselection", "0", true);
		Selection s2 = NavajoFactory.getInstance().createSelection(testDoc,
				"secondselection", "1", false);
		Selection s3 = NavajoFactory.getInstance().createSelection(testDoc,
				"thirdselection", "2", false);
		testSelectionProp.addSelection(s1);
		testSelectionProp.addSelection(s2);
		testSelectionProp.addSelection(s3);
		testSelectionProp.clearSelections();
		Iterator<Selection> iter = testSelectionProp.getAllSelections()
				.iterator();
		while (iter.hasNext()) {
			Selection s = iter.next();
			Assert.assertTrue(!s.isSelected());
		}
	}

	@Test
	public void tesCreate() throws NavajoException {
		Property testProp = NavajoFactory.getInstance().createProperty(testDoc,
				"myprop", Property.STRING_PROPERTY, "78", 89, "mydesc",
				Property.DIR_IN);
		Assert.assertNotNull(testProp);
		Assert.assertEquals(testProp.getName(), "myprop");
	}

	@Test
	public void tesCreate1() throws NavajoException {
		// Selection property.
		Property testProp = NavajoFactory.getInstance().createProperty(testDoc,
				"myprop", "+", "", Property.DIR_IN);
		Assert.assertNotNull(testProp);
		Assert.assertNotNull(testProp.getName());

	}

	@Test
	public void tesGetAllSelectedSelections() throws NavajoException {
		Property testSelectionProp = NavajoFactory.getInstance()
				.createProperty(testDoc, "testselectionproperty", "+", "",
						Property.DIR_IN);
		Selection s1 = NavajoFactory.getInstance().createSelection(testDoc,
				"firstselection", "0", true);
		Selection s2 = NavajoFactory.getInstance().createSelection(testDoc,
				"secondselection", "1", false);
		Selection s3 = NavajoFactory.getInstance().createSelection(testDoc,
				"thirdselection", "2", true);
		testSelectionProp.addSelection(s1);
		testSelectionProp.addSelection(s2);
		testSelectionProp.addSelection(s3);
		Iterator<Selection> iter = testSelectionProp.getAllSelectedSelections()
				.iterator();
		int count = 0;
		while (iter.hasNext()) {
			Selection s = iter.next();
			count++;
			Assert.assertTrue((s.getName().equals("firstselection") || s
					.getName().equals("thirdselection")));
			Assert.assertTrue(!s.getName().equals("secondselection"));
		}
		Assert.assertEquals(2, count);

	}

	@Test
	public void tesGetAllSelections() throws NavajoException {
		Property testSelectionProp = NavajoFactory.getInstance()
				.createProperty(testDoc, "testselectionproperty", "+", "",
						Property.DIR_IN);
		Selection s1 = NavajoFactory.getInstance().createSelection(testDoc,
				"firstselection", "0", true);
		Selection s2 = NavajoFactory.getInstance().createSelection(testDoc,
				"secondselection", "1", false);
		Selection s3 = NavajoFactory.getInstance().createSelection(testDoc,
				"thirdselection", "2", true);
		testSelectionProp.addSelection(s1);
		testSelectionProp.addSelection(s2);
		testSelectionProp.addSelection(s3);
		Iterator<Selection> iter = testSelectionProp.getAllSelections()
				.iterator();
		int count = 3;
		Set<String> set = new HashSet<String>();
		while (iter.hasNext()) {
			Selection s = iter.next();
			set.add(s.getName());
			if ((s.getName().equals("firstselection")
					|| s.getName().equals("thirdselection") || s.getName()
					.equals("secondselection")))
				count--;
		}
		Assert.assertEquals(3, set.size());
		Assert.assertEquals(0, count);

	}

	@Test
	public void tesGetCardinality() throws NavajoException {
		Property testSelectionProp = NavajoFactory.getInstance()
				.createProperty(testDoc, "testselectionproperty", "+", "",
						Property.DIR_IN);
		Assert.assertEquals("+", testSelectionProp.getCardinality());
		testSelectionProp = NavajoFactory.getInstance().createProperty(testDoc,
				"testselectionproperty", "1", "", Property.DIR_IN);
		Assert.assertEquals("1", testSelectionProp.getCardinality());

	}

	@Test
	public void tesGetDescription() throws NavajoException {
		Property testSelectionProp = NavajoFactory.getInstance()
				.createProperty(testDoc, "testselectionproperty", "+",
						"mydesc", Property.DIR_IN);
		Assert.assertEquals("mydesc", testSelectionProp.getDescription());

	}

	@Test
	public void tesGetDirection() throws NavajoException {
		Property testSelectionProp = NavajoFactory.getInstance()
				.createProperty(testDoc, "testselectionproperty", "+",
						"mydesc", Property.DIR_IN);
		Assert.assertEquals(Property.DIR_IN, testSelectionProp.getDirection());
		testSelectionProp = NavajoFactory.getInstance().createProperty(testDoc,
				"testselectionproperty", "+", "mydesc", Property.DIR_OUT);
		Assert.assertEquals(Property.DIR_OUT, testSelectionProp.getDirection());
	}

	@Test
	public void tesGetFullPropertyName() throws NavajoException {
		Property testProp = NavajoFactory.getInstance().createProperty(testDoc,
				"myprop", Property.STRING_PROPERTY, "78", 89, "mydesc",
				Property.DIR_IN);
		testDoc.getMessage("testmessage")
				.addMessage(
						NavajoFactory.getInstance().createMessage(testDoc,
								"anothermsg")).addProperty(testProp);
		Assert.assertEquals("/testmessage/anothermsg/myprop",
				testProp.getFullPropertyName());
	}

	@Test
	public void tesGetLength() throws NavajoException {
		Property testProp = NavajoFactory.getInstance().createProperty(testDoc,
				"myprop", Property.STRING_PROPERTY, "78", 89, "mydesc",
				Property.DIR_IN);
		Assert.assertEquals(89, testProp.getLength());
	}

	@Test
	public void tesGetName() throws NavajoException {
		Property testProp = NavajoFactory.getInstance().createProperty(testDoc,
				"myprop", Property.STRING_PROPERTY, "78", 89, "mydesc",
				Property.DIR_IN);
		Assert.assertEquals("myprop", testProp.getName());
	}

	@Test
	public void tesGetSelection() throws NavajoException {
		Property testSelectionProp = NavajoFactory.getInstance()
				.createProperty(testDoc, "testselectionproperty", "+",
						"mydesc", Property.DIR_IN);
		Selection s = NavajoFactory.getInstance().createSelection(testDoc,
				"myselection", "0", true);
		testSelectionProp.addSelection(s);
		Selection s1 = testSelectionProp.getSelection("myselection");
		Assert.assertNotNull(s1);
		Assert.assertEquals(s1.getName(), s.getName());
		Assert.assertEquals(s1.isSelected(), s.isSelected());
		Assert.assertEquals(s1.getValue(), s.getValue());
		s1 = testSelectionProp.getSelection("myselection2");
		Assert.assertNotNull(s1);
		Assert.assertEquals(Selection.DUMMY_SELECTION, s1.getName());

	}

	@Test
	public void tesGetSelectionByValue() throws NavajoException {
		Property testSelectionProp = NavajoFactory.getInstance()
				.createProperty(testDoc, "testselectionproperty", "+",
						"mydesc", Property.DIR_IN);
		Selection s = NavajoFactory.getInstance().createSelection(testDoc,
				"myselection", "0", true);
		testSelectionProp.addSelection(s);
		Selection s1 = testSelectionProp.getSelectionByValue("0");
		Assert.assertNotNull(s1);
		Assert.assertEquals(s1.getName(), s.getName());
		Assert.assertEquals(s1.isSelected(), s.isSelected());
		Assert.assertEquals(s1.getValue(), s.getValue());
		s1 = testSelectionProp.getSelectionByValue("1");
		Assert.assertNotNull(s1);
		Assert.assertEquals(Selection.DUMMY_SELECTION, s1.getName());
	}

	@Test
	public void tesGetType() throws NavajoException {
		Property testProp = NavajoFactory.getInstance().createProperty(testDoc,
				"myprop", Property.STRING_PROPERTY, "78", 89, "mydesc",
				Property.DIR_IN);
		Assert.assertEquals(Property.STRING_PROPERTY, testProp.getType());
		testProp = NavajoFactory.getInstance().createProperty(testDoc,
				"myprop", Property.INTEGER_PROPERTY, "78", 89, "mydesc",
				Property.DIR_IN);
		Assert.assertEquals(Property.INTEGER_PROPERTY, testProp.getType());
	}

	@Test
	public void tesGetValue() throws NavajoException {
		Property testProp = NavajoFactory.getInstance().createProperty(testDoc,
				"myprop", Property.STRING_PROPERTY, "78", 89, "mydesc",
				Property.DIR_IN);
		Assert.assertEquals("78", testProp.getValue());
		testProp = NavajoFactory.getInstance().createProperty(testDoc,
				"myprop", Property.INTEGER_PROPERTY, "88", 89, "mydesc",
				Property.DIR_IN);
		Assert.assertEquals("88", testProp.getValue());
	}

	@Test
	public void tesSetCardinality() throws NavajoException {
		Property testSelectionProp = NavajoFactory.getInstance()
				.createProperty(testDoc, "testselectionproperty", "+",
						"mydesc", Property.DIR_IN);
		testSelectionProp.setCardinality("1");
		Assert.assertEquals("1", testSelectionProp.getCardinality());
	}

	@Test
	public void tesSetDescription() throws NavajoException {
		Property testProp = NavajoFactory.getInstance().createProperty(testDoc,
				"myprop", Property.STRING_PROPERTY, "78", 89, "mydesc",
				Property.DIR_IN);
		testProp.setDescription("mynewdesc");
		Assert.assertEquals("mynewdesc", testProp.getDescription());

	}

	@Test
	public void tesSetDirection() throws NavajoException {
		Property testProp = NavajoFactory.getInstance().createProperty(testDoc,
				"myprop", Property.STRING_PROPERTY, "78", 89, "mydesc",
				Property.DIR_IN);
		testProp.setDirection(Property.DIR_OUT);
		Assert.assertEquals(Property.DIR_OUT, testProp.getDirection());

	}

	@Test
	public void tesSetLength() throws NavajoException {
		Property testProp = NavajoFactory.getInstance().createProperty(testDoc,
				"myprop", Property.STRING_PROPERTY, "78", 89, "mydesc",
				Property.DIR_IN);
		testProp.setLength(798);
		Assert.assertEquals(798, testProp.getLength());

	}

	@Test
	public void tesSetName() throws NavajoException {
		Property testProp = NavajoFactory.getInstance().createProperty(testDoc,
				"myprop", Property.STRING_PROPERTY, "78", 89, "mydesc",
				Property.DIR_IN);
		testProp.setName("mypropje");
		Assert.assertEquals("mypropje", testProp.getName());
	}

	@Test
	public void tesSetSelected() throws NavajoException {
		Property testSelectionProp1 = NavajoFactory.getInstance()
				.createProperty(testDoc, "testselectionproperty", "1",
						"mydesc", Property.DIR_IN);
		Property testSelectionProp2 = NavajoFactory.getInstance()
				.createProperty(testDoc, "testselectionproperty", "+",
						"mydesc", Property.DIR_IN);
		Selection s1 = NavajoFactory.getInstance().createSelection(testDoc,
				"myselection1", "0", false);
		Selection s2 = NavajoFactory.getInstance().createSelection(testDoc,
				"myselection2", "1", false);
		Selection s3 = NavajoFactory.getInstance().createSelection(testDoc,
				"myselection3", "2", true);
		Selection s4 = NavajoFactory.getInstance().createSelection(testDoc,
				"myselection4", "3", false);
		testSelectionProp1.addSelection(s1);
		testSelectionProp1.addSelection(s2);
		testSelectionProp1.addSelection(s3);
		testSelectionProp1.addSelection(s4);
		testSelectionProp1.setSelected("0");
		Assert.assertEquals(true, s1.isSelected());
		Assert.assertTrue(!s3.isSelected());

		s1 = NavajoFactory.getInstance().createSelection(testDoc,
				"myselection1", "0", false);
		s2 = NavajoFactory.getInstance().createSelection(testDoc,
				"myselection2", "1", false);
		s3 = NavajoFactory.getInstance().createSelection(testDoc,
				"myselection3", "2", true);
		s4 = NavajoFactory.getInstance().createSelection(testDoc,
				"myselection4", "3", false);
		testSelectionProp2.addSelection(s1);
		testSelectionProp2.addSelection(s2);
		testSelectionProp2.addSelection(s3);
		testSelectionProp2.addSelection(s4);
		testSelectionProp2.setSelected("0");
		Assert.assertEquals(true, s1.isSelected());
		Assert.assertTrue(s3.isSelected());

	}

	@Test
	public void tesSetSelected1() throws NavajoException {
		Property testSelectionProp1 = NavajoFactory.getInstance()
				.createProperty(testDoc, "testselectionproperty", "+",
						"mydesc", Property.DIR_IN);
		Selection s1 = NavajoFactory.getInstance().createSelection(testDoc,
				"myselection1", "0", false);
		Selection s2 = NavajoFactory.getInstance().createSelection(testDoc,
				"myselection2", "1", false);
		Selection s3 = NavajoFactory.getInstance().createSelection(testDoc,
				"myselection3", "2", false);
		Selection s4 = NavajoFactory.getInstance().createSelection(testDoc,
				"myselection4", "3", false);
		testSelectionProp1.addSelection(s1);
		testSelectionProp1.addSelection(s2);
		testSelectionProp1.addSelection(s3);
		testSelectionProp1.addSelection(s4);
		testSelectionProp1.setSelected(new String[] { "0", "3" });
		Assert.assertTrue(s1.isSelected());
		Assert.assertTrue(s4.isSelected());
		Assert.assertTrue(!s2.isSelected());
		Assert.assertTrue(!s3.isSelected());

	}

	@Test
	public void tesSetSelected2() throws NavajoException {
		Property testSelectionProp1 = NavajoFactory.getInstance()
				.createProperty(testDoc, "testselectionproperty", "+",
						"mydesc", Property.DIR_IN);
		Selection s1 = NavajoFactory.getInstance().createSelection(testDoc,
				"myselection1", "0", false);
		Selection s2 = NavajoFactory.getInstance().createSelection(testDoc,
				"myselection2", "1", false);
		Selection s3 = NavajoFactory.getInstance().createSelection(testDoc,
				"myselection3", "2", false);
		Selection s4 = NavajoFactory.getInstance().createSelection(testDoc,
				"myselection4", "3", false);
		testSelectionProp1.addSelection(s1);
		testSelectionProp1.addSelection(s2);
		testSelectionProp1.addSelection(s3);
		testSelectionProp1.addSelection(s4);
		ArrayList<String> l = new ArrayList<String>();
		l.add("0");
		l.add("3");
		testSelectionProp1.setSelected(l);
		Assert.assertTrue(s1.isSelected());
		Assert.assertTrue(s4.isSelected());
		Assert.assertTrue(!s2.isSelected());
		Assert.assertTrue(!s3.isSelected());

	}

	@Test
	public void tesSetType() throws NavajoException {
		Property testProp = NavajoFactory.getInstance().createProperty(testDoc,
				"myprop", Property.STRING_PROPERTY, "78", 89, "mydesc",
				Property.DIR_IN);
		testProp.setType(Property.INTEGER_PROPERTY);
		Assert.assertEquals(Property.INTEGER_PROPERTY, testProp.getType());
	}

	@Test
	public void tesSetValue() throws NavajoException {
		Property testProp = NavajoFactory.getInstance().createProperty(testDoc,
				"myprop", Property.STRING_PROPERTY, "78", 89, "mydesc",
				Property.DIR_IN);
		testProp.setValue("OK!");
		Assert.assertEquals("OK!", testProp.getValue());
	}

	@Test
	public void tesEqualProperties() throws Exception {
		BaseNavajoImpl n = new BaseNavajoImpl(NavajoFactory.getInstance());
		BasePropertyImpl p1 = new BasePropertyImpl(n, "Noot");
		BasePropertyImpl p2 = new BasePropertyImpl(n, "Noot");

		// Strings
		p1.setAnyValue("Aap");
		p2.setAnyValue("Aap");
		assertTrue(p1.isEqual(p2));

		p2.setAnyValue("Kip");
		assertFalse(p1.isEqual(p2));

		// Selections
		p1.setType("selection");
		p1.setCardinality("1");
		p1.addSelection(new BaseSelectionImpl(n, "opt1", "1", false));
		p1.addSelection(new BaseSelectionImpl(n, "opt2", "2", true));
		p1.addSelection(new BaseSelectionImpl(n, "opt3", "3", false));

		p2.setType("selection");
		p2.setCardinality("1");
		p2.addSelection(new BaseSelectionImpl(n, "opt1", "1", false));
		p2.addSelection(new BaseSelectionImpl(n, "opt2", "2", true));
		p2.addSelection(new BaseSelectionImpl(n, "opt3", "3", false));
		assertTrue(p1.isEqual(p2));

		p2.setSelected(new BaseSelectionImpl(n, "opt2", "2", true), false);
		p2.setSelected(new BaseSelectionImpl(n, "opt2", "3", true), true);
		assertFalse(p1.isEqual(p2));

		p1.setSelected(new BaseSelectionImpl(n, "opt2", "2", true), false);
		p1.setSelected(new BaseSelectionImpl(n, "opt2", "3", true), true);
		assertTrue(p1.isEqual(p2));

		// Date
		p1.setType("date");
		p1.removeAllSelections();
		p2.setType("date");
		p2.removeAllSelections();

		// these could be non-equal, right?
		p1.setAnyValue(new java.util.Date());
		p2.setAnyValue(new java.util.Date());
		assertTrue(p1.isEqual(p2));

		p2.setAnyValue(new java.util.Date(32131332L));
		assertFalse(p1.isEqual(p2));

	}

	@SuppressWarnings("deprecation")
	@Test
	public void tesSelections() throws Exception {
		BaseNavajoImpl n = new BaseNavajoImpl(NavajoFactory.getInstance());
		BasePropertyImpl p1 = new BasePropertyImpl(n, "Noot");
		p1.setType("selection");
		p1.setCardinality("1");
		p1.addSelection(new BaseSelectionImpl(n, "opt1", "1", false));
		p1.addSelection(new BaseSelectionImpl(n, "opt2", "2", false));
		p1.addSelection(new BaseSelectionImpl(n, "opt3", "3", false));
		assertEquals("___DUMMY_ELEMENT___", p1.getSelected().getValue());

		// Cardinality 1
		p1.setSelected("1");
		assertEquals("1", p1.getSelected().getValue());
		assertFalse("2".equals(p1.getSelected().getValue()));
		assertFalse("3".equals(p1.getSelected().getValue()));
		p1.setSelected("2");
		assertEquals("2", p1.getSelected().getValue());
		assertFalse("1".equals(p1.getSelected().getValue()));
		assertFalse("3".equals(p1.getSelected().getValue()));
		p1.setSelected("3");
		assertEquals("3", p1.getSelected().getValue());
		assertFalse("1".equals(p1.getSelected().getValue()));
		assertFalse("2".equals(p1.getSelected().getValue()));

		// Cardinality +
		p1.setCardinality("+");
		p1.clearSelections();
		assertFalse("1".equals(p1.getSelected().getValue()));
		assertFalse("2".equals(p1.getSelected().getValue()));
		assertFalse("3".equals(p1.getSelected().getValue()));
		p1.setSelected("1");
		p1.setSelected("2");
		ArrayList<Selection> all = p1.getAllSelectedSelections();
		for (int i = 0; i < all.size(); i++) {
			assertTrue(all.get(i).getValue().equals("1")
					|| all.get(i).getValue().equals("2"));
			assertFalse(all.get(i).getValue().equals("3"));
		}
	}

	@Test
	public void tesToString() throws NavajoException {
		Property testProp = NavajoFactory.getInstance().createProperty(testDoc,
				"myprop", Property.STRING_PROPERTY, "78", 89, "mydesc",
				Property.DIR_IN);
		Assert.assertEquals("78", testProp.toString());

	}

	@Test
	public void tesNullValues() throws NavajoException {
		Property testProp = NavajoFactory.getInstance().createProperty(testDoc,
				"myprop", Property.STRING_PROPERTY, null, 89, "mydesc",
				Property.DIR_IN);
		Assert.assertNull(testProp.getValue());
		Assert.assertNull(testProp.getTypedValue());
	}

	@Test
	public void tesSetValueWithString() throws NavajoException {

		Property p = NavajoFactory.getInstance().createProperty(testDoc,
				"myprop", Property.BOOLEAN_PROPERTY, "false", 89, "mydesc",
				Property.DIR_IN);
		Assert.assertEquals(Property.BOOLEAN_PROPERTY, p.getType());
		p.setValue("true");
		Assert.assertEquals(Property.BOOLEAN_PROPERTY, p.getType());

	}

	@Test
	public void tesNullEquals() throws NavajoException {

		for (int i = 0; i < Property.VALID_DATA_TYPES.length; i++) {
			Property p = NavajoFactory.getInstance().createProperty(testDoc,
					"myprop", Property.VALID_DATA_TYPES[i], null, 89, "mydesc",
					Property.DIR_IN);
			Property q = NavajoFactory.getInstance().createProperty(testDoc,
					"myprop", Property.VALID_DATA_TYPES[i], null, 89, "mydesc",
					Property.DIR_IN);
			Assert.assertEquals(p.getTypedValue(), q.getTypedValue());
		}

	}

	@Test
	public void tesNullMoneyEquals() {
		Money m = new Money();
		Money mm = new Money();
		Assert.assertEquals(m, mm);
	}

	@Test
	public void tesSelectionEqualsUpdateFix() throws Exception {
		BaseNavajoImpl n = new BaseNavajoImpl(NavajoFactory.getInstance());
		BasePropertyImpl p1 = new BasePropertyImpl(n, "Noot");
		p1.setType("selection");
		p1.setCardinality("1");
		p1.addSelection(new BaseSelectionImpl(n, "opt1", "1", false));
		p1.addSelection(new BaseSelectionImpl(n, "opt2", "2", true));
		p1.addPropertyChangeListener(new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent e) {
				logger.info("Old: " + e.getOldValue());
				logger.info("New: " + e.getNewValue());
				// no real change.
				Assert.fail();
			}
		});
		p1.setSelected(p1.getSelection("opt2"));

	}

	@Test
	public void tesMoneyFormat() {
		Money m = new Money(10);
		logger.info("Country: " + Locale.getDefault().getCountry());
		logger.info("m: " + m.toTmlString() + " :: " + m.editingString());
		Assert.assertEquals(m.toTmlString(), "10.00");
		Assert.assertEquals(m.editingString(), "10");

		m = new Money("10.000");
		Assert.assertEquals(m.doubleValue(), 10000d,0.1);
		m = new Money("10.000,00");
		Assert.assertEquals(m.doubleValue(), 10000d,0.1);
		m = new Money("5,00");
		Assert.assertEquals(m.doubleValue(), 5d,0.1);

	}

	@Test
	public void tesMoneyProperty() throws NavajoException {
		BaseNavajoImpl n = new BaseNavajoImpl(NavajoFactory.getInstance());
		BaseMessageImpl m = new BaseMessageImpl(n, "Aap");
		n.addMessage(m);
		BasePropertyImpl p1 = new BasePropertyImpl(n, "Noot");
		m.addProperty(p1);
		p1.setType(Property.MONEY_PROPERTY);
		p1.setValue("10.30");
		StringWriter sw = new StringWriter();
		n.write(sw);
		StringReader sr = new StringReader(sw.toString());
		Navajo n2 = NavajoFactory.getInstance().createNavajo(sr);
		Property p2 = n2.getProperty("Aap/Noot");
		// n2.write(System.err);
		Money mon = (Money) p2.getTypedValue();

		logger.info("m: " + mon.toTmlString() + " :: " + mon.editingString()
				+ " :: " + mon.toString());
		Assert.assertEquals(mon.toTmlString(), "10.30");
		Assert.assertEquals(mon.doubleValue(), 10.30d,0.1);

	}

	@Test
	public void tesMoneyParseTml() {

		double d = 10.30;
		Money m = new Money("10.30");
		// Strange test, but does no harm
		logger.info(":: " + m.doubleValue());
		Assert.assertNotSame(d, m.doubleValue());

	}

	@Test
	public void tesMoneyParsePresentation() {

		double d = 1300.50;
		Money m = new Money("1.300,50");
		logger.info(":: " + m.doubleValue());
		Assert.assertEquals(d, m.doubleValue(),0.1);

	}

	@Test
	public void tesExpression() throws NavajoException {
		BaseNavajoImpl n = new BaseNavajoImpl(NavajoFactory.getInstance());
		BaseMessageImpl m = new BaseMessageImpl(n, "Aap");
		n.addMessage(m);
		BasePropertyImpl p1 = new BasePropertyImpl(n, "Noot");
		m.addProperty(p1);
		p1.setType(Property.EXPRESSION_PROPERTY);
		String illegalExpression = "123";
		p1.setValue(illegalExpression);
		String res = p1.getValue();
		assertEquals(illegalExpression, res);
		// Can't really test, need an expression evaluator, which has a dep to
		// Navajo
		// TODO: Move test to navajo
		// assertEquals(illegalExpression, p1.getTypedValue());

	}

	@Test
	public void testDateTime() {
		Navajo n = NavajoFactory.getInstance().createNavajo(getClass().getClassLoader().getResourceAsStream("iphone.xml"));
		Property ios = n.getMessage("NewMatchEvent").getProperty("Ios");
		Property correct = n.getMessage("NewMatchEvent").getProperty("Correct");
		Object correctValue = correct.getTypedValue();
		Object iosValue = ios.getTypedValue();
		assertTrue(correctValue!=null);
		assertTrue(iosValue!=null);
		assertEquals(Date.class, correctValue.getClass());
		assertEquals(Date.class, iosValue.getClass());
		Date correctDate = (Date)correctValue;
		Date iosDate = (Date)iosValue;
		Calendar c = Calendar.getInstance();
		c.setTime(correctDate);
		assertFalse(c.get(Calendar.HOUR_OF_DAY)==0);

		Calendar iosCalendar = Calendar.getInstance();
		iosCalendar.setTime(iosDate);
		assertFalse(iosCalendar.get(Calendar.HOUR_OF_DAY)==0);

		
		n.write(System.err);
	}
}
