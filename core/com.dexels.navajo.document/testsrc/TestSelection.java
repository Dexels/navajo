
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Selection;

/**
 * This class is used to test the com.dexels.navajo.document.Selection
 * implementation
 * 
 * <p>
 * Title: Navajo Product Project
 * </p>
 * <p>
 * Description: This is the official source for the Navajo server
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Dexels BV
 * </p>
 * 
 * @author Arjen Schoneveld
 * @version 1.0
 */
public class TestSelection {

	private NavajoDocumentTestFicture fixture = new NavajoDocumentTestFicture();
	private Navajo testDoc;

	@Before
	public void setUp() {
		fixture.setUp();
		testDoc = fixture.testDoc;
	}

	@After
	public void tearDown() {
		fixture.tearDown();
	}

	@Test
	public void testSetSelected() {

		Selection selection = NavajoFactory.getInstance().createSelection(
				testDoc, "firstselection", "0", true);
		boolean b1 = true;
		selection.setSelected(b1);
		Assert.assertEquals(selection.isSelected(), true);
	}

	@Test
	public void testCreate() {
		Selection selectionRet = NavajoFactory.getInstance().createSelection(
				testDoc, "firstselection", "0", true);
		Assert.assertEquals(selectionRet.getName(), "firstselection");
		Assert.assertEquals(selectionRet.getValue(), "0");
		Assert.assertEquals(selectionRet.isSelected(), true);
		// Assert.assertNotNull(selectionRet.getRef());
	}

	@Test
	public void testCreateDummy() {
		Selection selectionRet = NavajoFactory.getInstance()
				.createDummySelection();
		Assert.assertEquals("___DUMMY_ELEMENT___", selectionRet.getValue());
	}

	@Test
	public void testGetName() {
		Selection selectionRet = NavajoFactory.getInstance().createSelection(
				testDoc, "firstselection", "0", true);
		Assert.assertEquals(selectionRet.getName(), "firstselection");
	}

	@Test
	public void testGetValue() {
		Selection selectionRet = NavajoFactory.getInstance().createSelection(
				testDoc, "firstselection", "0", true);
		Assert.assertEquals(selectionRet.getValue(), "0");
	}

	@Test
	public void testIsSelected() {
		Selection selectionRet1 = NavajoFactory.getInstance().createSelection(
				testDoc, "firstselection", "0", true);
		Selection selectionRet2 = NavajoFactory.getInstance().createSelection(
				testDoc, "firstselection", "0", false);
		Assert.assertTrue(selectionRet1.isSelected());
		Assert.assertTrue(!selectionRet2.isSelected());
	}

	@Test
	public void testSetName() {
		Selection selectionRet = NavajoFactory.getInstance().createSelection(
				testDoc, "firstselection", "0", true);
		selectionRet.setName("another");
		Assert.assertEquals(selectionRet.getName(), "another");
	}

	@Test
	public void testSetValue() {
		Selection selectionRet = NavajoFactory.getInstance().createSelection(
				testDoc, "firstselection", "0", true);
		selectionRet.setValue("1");
		Assert.assertEquals(selectionRet.getValue(), "1");
	}

	@Test
	public void testIsSelectedWithInteger0() {
		Selection selectionRet = NavajoFactory.getInstance().createSelection(
				testDoc, "firstselection", "0", 1);
		selectionRet.setValue("1");
		Assert.assertTrue(selectionRet.isSelected());
	}

	@Test
	public void testIsSelectedWithInteger1() {
		Selection selectionRet = NavajoFactory.getInstance().createSelection(
				testDoc, "firstselection", "0", 0);
		selectionRet.setValue("1");
		Assert.assertTrue(!selectionRet.isSelected());
	}

	@Test
	public void testIsSelectedWithInteger2() {
		Selection selectionRet = NavajoFactory.getInstance().createSelection(
				testDoc, "firstselection", "0", 11);
		selectionRet.setValue("1");
		Assert.assertTrue(selectionRet.isSelected());
	}

	@Test
	public void testIsSelectedWithObject0() {
		Selection selectionRet = NavajoFactory.getInstance().createSelection(
				testDoc, "firstselection", "0", new Integer(1));
		selectionRet.setValue("1");
		Assert.assertTrue(selectionRet.isSelected());
	}

	@Test
	public void testIsSelectedWithObject1() {
		Selection selectionRet = NavajoFactory.getInstance().createSelection(
				testDoc, "firstselection", "0", new Integer(0));
		selectionRet.setValue("1");
		Assert.assertTrue(!selectionRet.isSelected());
	}

	@Test
	public void testIsSelectedWithObject2() {
		Selection selectionRet = NavajoFactory.getInstance().createSelection(
				testDoc, "firstselection", "0", new Integer(11));
		selectionRet.setValue("1");
		Assert.assertTrue(selectionRet.isSelected());
	}

	@Test
	public void testIsSelectedWithObject3() {
		Selection selectionRet = NavajoFactory.getInstance().createSelection(
				testDoc, "firstselection", "0", new Boolean(true));
		selectionRet.setValue("1");
		Assert.assertTrue(selectionRet.isSelected());
	}

	@Test
	public void testIsSelectedWithObject4() {
		Selection selectionRet = NavajoFactory.getInstance().createSelection(
				testDoc, "firstselection", "0", new Boolean(false));
		selectionRet.setValue("1");
		Assert.assertTrue(!selectionRet.isSelected());
	}
}
