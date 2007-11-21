
import junit.framework.*;
import java.util.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.document.base.BaseMessageImpl;
import com.dexels.navajo.document.base.BaseNavajoImpl;
import com.dexels.navajo.document.base.BasePropertyImpl;
import com.dexels.navajo.document.base.BaseSelectionImpl;

public class TestProperty extends TestCase {
	NavajoDocumentTestFicture navajodocumenttestfictureInst = new NavajoDocumentTestFicture(this);
	private Navajo testDoc;

	public TestProperty(String s) {
		super(s);
	}

	protected void setUp() {
		navajodocumenttestfictureInst.setUp();
		testDoc = navajodocumenttestfictureInst.testDoc;
	}

	protected void tearDown() {
		navajodocumenttestfictureInst.tearDown();
	}

	public void testSetAnyValue() throws Exception {
		Navajo n = NavajoFactory.getInstance().createNavajo();
		Property p1 = NavajoFactory.getInstance().createProperty(n, "Aap", "", "", "");
		
		// String
		p1.setAnyValue("Apenoot");
		assertEquals("string", p1.getType());
		// Integer
		p1.setAnyValue(new Integer(50));
		assertEquals("integer", p1.getType());
		// Double
		p1.setAnyValue(new Double(50));
		assertEquals("float", p1.getType());
		// Float
		p1.setAnyValue(new Float(50));
		assertEquals("float", p1.getType());
		// Date
		p1.setAnyValue(new java.util.Date());
		assertEquals("date", p1.getType());
		// Long
		p1.setAnyValue(new Long(10));
		assertEquals("long", p1.getType());
		// Boolean
		p1.setAnyValue(Boolean.TRUE);
		assertEquals("boolean", p1.getType());
	}
	
	public void testAddSelection() {

		try {
			Property testSelectionProp = NavajoFactory.getInstance().createProperty(testDoc, "testselectionproperty", "+", "", Property.DIR_IN);
			Selection s1 = NavajoFactory.getInstance().createSelection(testDoc, "firstselection", "0", true);
			testSelectionProp.addSelection(s1);
			Selection s2 = testSelectionProp.getSelection("firstselection");
			Assert.assertNotNull(s2);
			Assert.assertEquals(s2.isSelected(), s1.isSelected());
			Assert.assertEquals(s2.getName(), s1.getName());
			Assert.assertEquals(s2.getValue(), s1.getValue());
			// Check whether selection with same name is correctly replaced.
			Selection s3 = NavajoFactory.getInstance().createSelection(testDoc, "firstselection", "1", false);
			testSelectionProp.addSelection(s3);
			s2 = testSelectionProp.getSelection("firstselection");
			Assert.assertNotNull(s2);
			Assert.assertEquals(s2.isSelected(), s3.isSelected());
			Assert.assertEquals(s2.getName(), s3.getName());
			Assert.assertEquals(s2.getValue(), s3.getValue());
		}
		catch(Exception e) {
			System.err.println("Exception thrown:  "+e);
		}
	}
	public void testAddSelectionWithoutReplace() {

		try {
			Property testSelectionProp = NavajoFactory.getInstance().createProperty(testDoc, "testselectionproperty", "+", "", Property.DIR_IN);
			Selection s1 = NavajoFactory.getInstance().createSelection(testDoc, "firstselection", "0", true);
			testSelectionProp.addSelectionWithoutReplace(s1);
			Selection s2 = testSelectionProp.getSelection("firstselection");
			Assert.assertNotNull(s2);
			Assert.assertEquals(s2.isSelected(), s1.isSelected());
			Assert.assertEquals(s2.getName(), s1.getName());
			Assert.assertEquals(s2.getValue(), s1.getValue());
			Selection s3 = NavajoFactory.getInstance().createSelection(testDoc, "firstselection", "1", false);
			testSelectionProp.addSelectionWithoutReplace(s3);
			int count = 0;
			ArrayList all = testSelectionProp.getAllSelections();
			for (int i = 0; i < all.size(); i++) {
				if (((Selection)all.get(i)).getName().equals("firstselection"))
					count++;
			}
			Assert.assertEquals(2, count);
		}
		catch(Exception e) {
			System.err.println("Exception thrown:  "+e);
		}
	}
	public void testClearSelections() {
		try {
			Property testSelectionProp = NavajoFactory.getInstance().createProperty(testDoc, "testselectionproperty", "+", "", Property.DIR_IN);
			Selection s1 = NavajoFactory.getInstance().createSelection(testDoc, "firstselection", "0", true);
			Selection s2 = NavajoFactory.getInstance().createSelection(testDoc, "secondselection", "1", false);
			Selection s3 = NavajoFactory.getInstance().createSelection(testDoc, "thirdselection", "2", false);
			testSelectionProp.addSelection(s1);
			testSelectionProp.addSelection(s2);
			testSelectionProp.addSelection(s3);
			testSelectionProp.clearSelections();
			Iterator iter = testSelectionProp.getAllSelections().iterator();
			while (iter.hasNext()) {
				Selection s = (Selection) iter.next();
				Assert.assertTrue(!s.isSelected());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void testCreate() {
		try {
			// Normal property.
			Property testProp = NavajoFactory.getInstance().createProperty(testDoc, "myprop", Property.STRING_PROPERTY, "78", 89, "mydesc", Property.DIR_IN);
			Assert.assertNotNull(testProp);
			Assert.assertNotNull(testProp.getRef());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void testCreate1() {
		try {
			// Selection property.
			Property testProp = NavajoFactory.getInstance().createProperty(testDoc, "myprop", "+", "", Property.DIR_IN);
			Assert.assertNotNull(testProp);
			Assert.assertNotNull(testProp.getRef());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void testGetAllSelectedSelections() {
		try {
			Property testSelectionProp = NavajoFactory.getInstance().createProperty(testDoc, "testselectionproperty", "+", "", Property.DIR_IN);
			Selection s1 = NavajoFactory.getInstance().createSelection(testDoc, "firstselection", "0", true);
			Selection s2 = NavajoFactory.getInstance().createSelection(testDoc, "secondselection", "1", false);
			Selection s3 = NavajoFactory.getInstance().createSelection(testDoc, "thirdselection", "2", true);
			testSelectionProp.addSelection(s1);
			testSelectionProp.addSelection(s2);
			testSelectionProp.addSelection(s3);
			Iterator iter = testSelectionProp.getAllSelectedSelections().iterator();
			int count = 0;
			while (iter.hasNext()) {
				Selection s = (Selection) iter.next();
				count++;
				Assert.assertTrue((s.getName().equals("firstselection") || s.getName().equals("thirdselection")));
				Assert.assertTrue(!s.getName().equals("secondselection"));
			}
			Assert.assertEquals(2, count);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void testGetAllSelections() {
		try {
			Property testSelectionProp = NavajoFactory.getInstance().createProperty(testDoc, "testselectionproperty", "+", "", Property.DIR_IN);
			Selection s1 = NavajoFactory.getInstance().createSelection(testDoc, "firstselection", "0", true);
			Selection s2 = NavajoFactory.getInstance().createSelection(testDoc, "secondselection", "1", false);
			Selection s3 = NavajoFactory.getInstance().createSelection(testDoc, "thirdselection", "2", true);
			testSelectionProp.addSelection(s1);
			testSelectionProp.addSelection(s2);
			testSelectionProp.addSelection(s3);
			Iterator iter = testSelectionProp.getAllSelections().iterator();
			int count = 3;
			HashSet set = new HashSet();
			while (iter.hasNext()) {
				Selection s = (Selection) iter.next();
				set.add(s.getName());
				if ((s.getName().equals("firstselection") || s.getName().equals("thirdselection") || s.getName().equals("secondselection")))
					count--;
			}
			Assert.assertEquals(3, set.size());
			Assert.assertEquals(0, count);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void testGetCardinality() {
		try {
			Property testSelectionProp = NavajoFactory.getInstance().createProperty(testDoc, "testselectionproperty", "+", "", Property.DIR_IN);
			Assert.assertEquals("+", testSelectionProp.getCardinality());
			testSelectionProp = NavajoFactory.getInstance().createProperty(testDoc, "testselectionproperty", "1", "", Property.DIR_IN);
			Assert.assertEquals("1", testSelectionProp.getCardinality());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void testGetDescription() {
		try {
			Property testSelectionProp = NavajoFactory.getInstance().createProperty(testDoc, "testselectionproperty", "+", "mydesc", Property.DIR_IN);
			Assert.assertEquals("mydesc", testSelectionProp.getDescription());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void testGetDirection() {
		try {
			Property testSelectionProp = NavajoFactory.getInstance().createProperty(testDoc, "testselectionproperty", "+", "mydesc", Property.DIR_IN);
			Assert.assertEquals(Property.DIR_IN, testSelectionProp.getDirection());
			testSelectionProp = NavajoFactory.getInstance().createProperty(testDoc, "testselectionproperty", "+", "mydesc", Property.DIR_OUT);
			Assert.assertEquals(Property.DIR_OUT, testSelectionProp.getDirection());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void testGetFullPropertyName() {
		try {
			Property testProp = NavajoFactory.getInstance().createProperty(testDoc, "myprop", Property.STRING_PROPERTY, "78", 89, "mydesc", Property.DIR_IN);
			testDoc.getMessage("testmessage").addMessage(NavajoFactory.getInstance().createMessage(testDoc, "anothermsg")).addProperty(testProp);
			Assert.assertEquals("/testmessage/anothermsg/myprop", testProp.getFullPropertyName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void testGetLength() {
		try {
			Property testProp = NavajoFactory.getInstance().createProperty(testDoc, "myprop", Property.STRING_PROPERTY, "78", 89, "mydesc", Property.DIR_IN);
			Assert.assertEquals(89, testProp.getLength());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void testGetName() {
		try {
			Property testProp = NavajoFactory.getInstance().createProperty(testDoc, "myprop", Property.STRING_PROPERTY, "78", 89, "mydesc", Property.DIR_IN);
			Assert.assertEquals("myprop", testProp.getName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void testGetPoints() {
		testSetPoints();
	}

	public void testGetSelection() {
		try {
			Property testSelectionProp = NavajoFactory.getInstance().createProperty(testDoc, "testselectionproperty", "+", "mydesc", Property.DIR_IN);
			Selection s = NavajoFactory.getInstance().createSelection(testDoc, "myselection", "0", true);
			testSelectionProp.addSelection(s);
			Selection s1 = testSelectionProp.getSelection("myselection");
			Assert.assertNotNull(s1);
			Assert.assertEquals(s1.getName(), s.getName());
			Assert.assertEquals(s1.isSelected(), s.isSelected());
			Assert.assertEquals(s1.getValue(), s.getValue());
			s1 = testSelectionProp.getSelection("myselection2");
			Assert.assertNotNull(s1);
			Assert.assertEquals(Selection.DUMMY_SELECTION, s1.getName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void testGetSelectionByValue() {
		try {
			Property testSelectionProp = NavajoFactory.getInstance().createProperty(testDoc, "testselectionproperty", "+", "mydesc", Property.DIR_IN);
			Selection s = NavajoFactory.getInstance().createSelection(testDoc, "myselection", "0", true);
			testSelectionProp.addSelection(s);
			Selection s1 = testSelectionProp.getSelectionByValue("0");
			Assert.assertNotNull(s1);
			Assert.assertEquals(s1.getName(), s.getName());
			Assert.assertEquals(s1.isSelected(), s.isSelected());
			Assert.assertEquals(s1.getValue(), s.getValue());
			s1 = testSelectionProp.getSelectionByValue("1");
			Assert.assertNotNull(s1);
			Assert.assertEquals(Selection.DUMMY_SELECTION, s1.getName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void testGetType() {
		try {
			Property testProp = NavajoFactory.getInstance().createProperty(testDoc, "myprop", Property.STRING_PROPERTY, "78", 89, "mydesc", Property.DIR_IN);
			Assert.assertEquals(Property.STRING_PROPERTY, testProp.getType());
			testProp = NavajoFactory.getInstance().createProperty(testDoc, "myprop", Property.INTEGER_PROPERTY, "78", 89, "mydesc", Property.DIR_IN);
			Assert.assertEquals(Property.INTEGER_PROPERTY, testProp.getType());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void testGetValue() {
		try {
			Property testProp = NavajoFactory.getInstance().createProperty(testDoc, "myprop", Property.STRING_PROPERTY, "78", 89, "mydesc", Property.DIR_IN);
			Assert.assertEquals("78", testProp.getValue());
			testProp = NavajoFactory.getInstance().createProperty(testDoc, "myprop", Property.INTEGER_PROPERTY, "88", 89, "mydesc", Property.DIR_IN);
			Assert.assertEquals("88", testProp.getValue());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void testSetCardinality() {
		try {
			Property testSelectionProp = NavajoFactory.getInstance().createProperty(testDoc, "testselectionproperty", "+", "mydesc", Property.DIR_IN);
			testSelectionProp.setCardinality("1");
			Assert.assertEquals("1", testSelectionProp.getCardinality());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void testSetDescription() {
		try {
			Property testProp = NavajoFactory.getInstance().createProperty(testDoc, "myprop", Property.STRING_PROPERTY, "78", 89, "mydesc", Property.DIR_IN);
			testProp.setDescription("mynewdesc");
			Assert.assertEquals("mynewdesc", testProp.getDescription());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void testSetDirection() {
		try {
			Property testProp = NavajoFactory.getInstance().createProperty(testDoc, "myprop", Property.STRING_PROPERTY, "78", 89, "mydesc", Property.DIR_IN);
			testProp.setDirection(Property.DIR_OUT);
			Assert.assertEquals(Property.DIR_OUT, testProp.getDirection());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void testSetLength() {
		try {
			Property testProp = NavajoFactory.getInstance().createProperty(testDoc, "myprop", Property.STRING_PROPERTY, "78", 89, "mydesc", Property.DIR_IN);
			testProp.setLength(798);
			Assert.assertEquals(798, testProp.getLength());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void testSetName() {
		try {
			Property testProp = NavajoFactory.getInstance().createProperty(testDoc, "myprop", Property.STRING_PROPERTY, "78", 89, "mydesc", Property.DIR_IN);
			testProp.setName("mypropje");
			Assert.assertEquals("mypropje", testProp.getName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void testSetPoints() {
		// TODO.
		try {
			Property p = NavajoFactory.getInstance().createProperty(testDoc, "mypoints", Property.POINTS_PROPERTY, "", 0, "", Property.DIR_OUT);
			Vector [] points = new Vector[5];
			for (int i = 0; i < points.length; i++) {
				Vector v = new Vector();
				v.add(new String(i+""));
				v.add(new String((i*i)+""));
				points[i] = v;
			}
			p.setPoints(points);

			Vector [] result = p.getPoints();
			Assert.assertEquals(points.length, result.length);
			for (int i = 0; i < result.length; i++) {
				Vector rv = result[i];
				String rx1 = result[i].get(0).toString();
				String rx2 = result[i].get(1).toString();
				String x1 = points[i].get(0).toString();
				String x2 = points[i].get(1).toString();
				Assert.assertEquals(x1, rx1);
				Assert.assertEquals(x2, rx2);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void testSetSelected() {
		try {
			Property testSelectionProp1 = NavajoFactory.getInstance().createProperty(testDoc, "testselectionproperty", "1", "mydesc", Property.DIR_IN);
			Property testSelectionProp2 = NavajoFactory.getInstance().createProperty(testDoc, "testselectionproperty", "+", "mydesc", Property.DIR_IN);
			Selection s1 = NavajoFactory.getInstance().createSelection(testDoc, "myselection1", "0", false);
			Selection s2 = NavajoFactory.getInstance().createSelection(testDoc, "myselection2", "1", false);
			Selection s3 = NavajoFactory.getInstance().createSelection(testDoc, "myselection3", "2", true);
			Selection s4 = NavajoFactory.getInstance().createSelection(testDoc, "myselection4", "3", false);
			testSelectionProp1.addSelection(s1);
			testSelectionProp1.addSelection(s2);
			testSelectionProp1.addSelection(s3);
			testSelectionProp1.addSelection(s4);
			testSelectionProp1.setSelected("0");
			Assert.assertEquals(true, s1.isSelected());
			Assert.assertTrue(!s3.isSelected());

			s1 = NavajoFactory.getInstance().createSelection(testDoc, "myselection1", "0", false);
			s2 = NavajoFactory.getInstance().createSelection(testDoc, "myselection2", "1", false);
			s3 = NavajoFactory.getInstance().createSelection(testDoc, "myselection3", "2", true);
			s4 = NavajoFactory.getInstance().createSelection(testDoc, "myselection4", "3", false);
			testSelectionProp2.addSelection(s1);
			testSelectionProp2.addSelection(s2);
			testSelectionProp2.addSelection(s3);
			testSelectionProp2.addSelection(s4);
			testSelectionProp2.setSelected("0");
			Assert.assertEquals(true, s1.isSelected());
			Assert.assertTrue(s3.isSelected());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void testSetSelected1() {
		try {
			Property testSelectionProp1 = NavajoFactory.getInstance().createProperty(testDoc, "testselectionproperty", "+", "mydesc", Property.DIR_IN);
			Selection s1 = NavajoFactory.getInstance().createSelection(testDoc, "myselection1", "0", false);
			Selection s2 = NavajoFactory.getInstance().createSelection(testDoc, "myselection2", "1", false);
			Selection s3 = NavajoFactory.getInstance().createSelection(testDoc, "myselection3", "2", false);
			Selection s4 = NavajoFactory.getInstance().createSelection(testDoc, "myselection4", "3", false);
			testSelectionProp1.addSelection(s1);
			testSelectionProp1.addSelection(s2);
			testSelectionProp1.addSelection(s3);
			testSelectionProp1.addSelection(s4);
			testSelectionProp1.setSelected(new String [] {"0", "3"});
			Assert.assertTrue(s1.isSelected());
			Assert.assertTrue(s4.isSelected());
			Assert.assertTrue(!s2.isSelected());
			Assert.assertTrue(!s3.isSelected());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void testSetSelected2() {
		try {
			Property testSelectionProp1 = NavajoFactory.getInstance().createProperty(testDoc, "testselectionproperty", "+", "mydesc", Property.DIR_IN);
			Selection s1 = NavajoFactory.getInstance().createSelection(testDoc, "myselection1", "0", false);
			Selection s2 = NavajoFactory.getInstance().createSelection(testDoc, "myselection2", "1", false);
			Selection s3 = NavajoFactory.getInstance().createSelection(testDoc, "myselection3", "2", false);
			Selection s4 = NavajoFactory.getInstance().createSelection(testDoc, "myselection4", "3", false);
			testSelectionProp1.addSelection(s1);
			testSelectionProp1.addSelection(s2);
			testSelectionProp1.addSelection(s3);
			testSelectionProp1.addSelection(s4);
			ArrayList l = new ArrayList();
			l.add("0");
			l.add("3");
			testSelectionProp1.setSelected(l);
			Assert.assertTrue(s1.isSelected());
			Assert.assertTrue(s4.isSelected());
			Assert.assertTrue(!s2.isSelected());
			Assert.assertTrue(!s3.isSelected());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void testSetType() {
		try {
			Property testProp = NavajoFactory.getInstance().createProperty(testDoc, "myprop", Property.STRING_PROPERTY, "78", 89, "mydesc", Property.DIR_IN);
			testProp.setType(Property.INTEGER_PROPERTY);
			Assert.assertEquals(Property.INTEGER_PROPERTY, testProp.getType());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void testSetValue() {
		try {
			Property testProp = NavajoFactory.getInstance().createProperty(testDoc, "myprop", Property.STRING_PROPERTY, "78", 89, "mydesc", Property.DIR_IN);
			testProp.setValue("OK!");
			Assert.assertEquals("OK!", testProp.getValue());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void testEqualProperties() throws Exception {
		BaseNavajoImpl n = new BaseNavajoImpl();
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
		p1.addSelection(new BaseSelectionImpl((Navajo) n, "opt1", "1", false));
		p1.addSelection(new BaseSelectionImpl((Navajo) n, "opt2", "2", true));
		p1.addSelection(new BaseSelectionImpl((Navajo) n, "opt3", "3", false));
		
		p2.setType("selection");
		p2.setCardinality("1");
		p2.addSelection(new BaseSelectionImpl((Navajo) n, "opt1", "1", false));
		p2.addSelection(new BaseSelectionImpl((Navajo) n, "opt2", "2", true));
		p2.addSelection(new BaseSelectionImpl((Navajo) n, "opt3", "3", false));
		assertTrue(p1.isEqual(p2));
		
		p2.setSelected(new BaseSelectionImpl((Navajo) n, "opt2", "2", true), false);
		p2.setSelected(new BaseSelectionImpl((Navajo) n, "opt2", "3", true), true);
		assertFalse(p1.isEqual(p2));
		
		p1.setSelected(new BaseSelectionImpl((Navajo) n, "opt2", "2", true), false);
		p1.setSelected(new BaseSelectionImpl((Navajo) n, "opt2", "3", true), true);
		assertTrue(p1.isEqual(p2));
		
		// Date
		p1.setType("date");
		p1.removeAllSelections();
		p2.setType("date");
		p2.removeAllSelections();
		
		p1.setAnyValue(new java.util.Date());
		p2.setAnyValue(new java.util.Date());
		assertTrue(p1.isEqual(p2));
		
		p2.setAnyValue(new java.util.Date((long) 32131332));
		assertFalse(p1.isEqual(p2));
		
	}
	
	public void testSelections() throws Exception {
		BaseNavajoImpl n = new BaseNavajoImpl();
		BaseMessageImpl m = new BaseMessageImpl(n, "Aap");
		BasePropertyImpl p1 = new BasePropertyImpl(n, "Noot");
		p1.setType("selection");
		p1.setCardinality("1");
		p1.addSelection(new BaseSelectionImpl((Navajo) n, "opt1", "1", false));
		p1.addSelection(new BaseSelectionImpl((Navajo) n, "opt2", "2", false));
		p1.addSelection(new BaseSelectionImpl((Navajo) n, "opt3", "3", false));
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
		ArrayList<BaseSelectionImpl> all = p1.getAllSelectedSelections();
		for (int i = 0; i < all.size(); i++) {
			assertTrue(all.get(i).getValue().equals("1") || all.get(i).getValue().equals("2"));
			assertFalse(all.get(i).getValue().equals("3"));
		}
	}
	
	public void testToString() {
		try {
			Property testProp = NavajoFactory.getInstance().createProperty(testDoc, "myprop", Property.STRING_PROPERTY, "78", 89, "mydesc", Property.DIR_IN);
			Assert.assertEquals("78", testProp.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
