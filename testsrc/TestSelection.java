

import junit.framework.Assert;
import junit.framework.TestCase;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Selection;

/**
 * This class is used to test the com.dexels.navajo.document.Selection implementation
 *
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author Arjen Schoneveld
 * @version 1.0
 */
public class TestSelection extends TestCase {

	private NavajoDocumentTestFicture fixture = new NavajoDocumentTestFicture(this);
	private Navajo testDoc;

	public TestSelection(String s) {
		super(s);
	}

	protected void setUp() {
		fixture.setUp();
		testDoc = fixture.testDoc;
	}

	protected void tearDown() {
		fixture.tearDown();
	}

	public void testSetSelected() {

		Selection selection = NavajoFactory.getInstance().createSelection(testDoc, "firstselection", "0", true);
		boolean b1=  true;
		selection.setSelected(b1);
		Assert.assertEquals(selection.isSelected(), true);
	}
	public void testCreate() {
		Selection selectionRet = NavajoFactory.getInstance().createSelection(testDoc, "firstselection", "0", true);
		Assert.assertEquals(selectionRet.getName(), "firstselection");
		Assert.assertEquals(selectionRet.getValue(), "0");
		Assert.assertEquals(selectionRet.isSelected(), true);
		//Assert.assertNotNull(selectionRet.getRef());
	}
	public void testCreateDummy() {
		Selection selectionRet = NavajoFactory.getInstance().createDummySelection();
		assertEquals("___DUMMY_ELEMENT___", selectionRet.getValue());
	}
	public void testGetName() {
		Selection selectionRet = NavajoFactory.getInstance().createSelection(testDoc, "firstselection", "0", true);
		Assert.assertEquals(selectionRet.getName(), "firstselection");
	}
	public void testGetValue() {
		Selection selectionRet = NavajoFactory.getInstance().createSelection(testDoc, "firstselection", "0", true);
		Assert.assertEquals(selectionRet.getValue(), "0");
	}
	public void testIsSelected() {
		Selection selectionRet1 = NavajoFactory.getInstance().createSelection(testDoc, "firstselection", "0", true);
		Selection selectionRet2 = NavajoFactory.getInstance().createSelection(testDoc, "firstselection", "0", false);
		Assert.assertTrue(selectionRet1.isSelected());
		Assert.assertTrue(!selectionRet2.isSelected());
	}
	public void testSetName() {
		Selection selectionRet = NavajoFactory.getInstance().createSelection(testDoc, "firstselection", "0", true);
		selectionRet.setName("another");
		Assert.assertEquals(selectionRet.getName(), "another");
	}
	public void testSetValue() {
		Selection selectionRet = NavajoFactory.getInstance().createSelection(testDoc, "firstselection", "0", true);
		selectionRet.setValue("1");
		Assert.assertEquals(selectionRet.getValue(), "1");
	}
	public void testIsSelectedWithInteger0() {
		Selection selectionRet = NavajoFactory.getInstance().createSelection(testDoc, "firstselection", "0", 1);
		selectionRet.setValue("1");
		Assert.assertTrue(selectionRet.isSelected());
	}
	public void testIsSelectedWithInteger1() {
		Selection selectionRet = NavajoFactory.getInstance().createSelection(testDoc, "firstselection", "0", 0);
		selectionRet.setValue("1");
		Assert.assertTrue(!selectionRet.isSelected());
	}
	public void testIsSelectedWithInteger2() {
		Selection selectionRet = NavajoFactory.getInstance().createSelection(testDoc, "firstselection", "0", 11);
		selectionRet.setValue("1");
		Assert.assertTrue(selectionRet.isSelected());
	}
	
	public void testIsSelectedWithObject0() {
		Selection selectionRet = NavajoFactory.getInstance().createSelection(testDoc, "firstselection", "0", new Integer(1));
		selectionRet.setValue("1");
		Assert.assertTrue(selectionRet.isSelected());
	}
	public void testIsSelectedWithObject1() {
		Selection selectionRet = NavajoFactory.getInstance().createSelection(testDoc, "firstselection", "0", new Integer(0));
		selectionRet.setValue("1");
		Assert.assertTrue(!selectionRet.isSelected());
	}
	public void testIsSelectedWithObject2() {
		Selection selectionRet = NavajoFactory.getInstance().createSelection(testDoc, "firstselection", "0", new Integer(11));
		selectionRet.setValue("1");
		Assert.assertTrue(selectionRet.isSelected());
	}
	public void testIsSelectedWithObject3() {
		Selection selectionRet = NavajoFactory.getInstance().createSelection(testDoc, "firstselection", "0", new Boolean(true));
		selectionRet.setValue("1");
		Assert.assertTrue(selectionRet.isSelected());
	}
	public void testIsSelectedWithObject4() {
		Selection selectionRet = NavajoFactory.getInstance().createSelection(testDoc, "firstselection", "0", new Boolean(false));
		selectionRet.setValue("1");
		Assert.assertTrue(!selectionRet.isSelected());
	}
}
