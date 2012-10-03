
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Selection;
import com.dexels.navajo.document.types.Binary;

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
public class TestBinary {

	private NavajoDocumentTestFicture fixture = new NavajoDocumentTestFicture();
	private Navajo testDoc;
	private Binary binary1;
	private Binary binary2;
	private Binary binary3;
	private Binary binary4;

	
	private final static Logger logger = LoggerFactory
			.getLogger(TestBinary.class);
	
	@Before
	public void setUp() {
		binary1 = new Binary(getClass().getResourceAsStream("binary1.txt"));
		binary2 = new Binary(getClass().getResourceAsStream("binary2.txt"));
		binary3 = new Binary(getClass().getResourceAsStream("binary3.txt"));
		binary4 = new Binary(getClass().getResourceAsStream("binary4.txt"));
//		logger.info("Digestlength: "+Binary.bytesToHex(binary1.getDigest()));
		fixture.setUp();
		testDoc = fixture.testDoc;
	}

	@After
	public void tearDown() {
		fixture.tearDown();
	}

	@Test
	public void testEqual1() {
		Assert.assertEquals(binary1,binary2);
	}

	@Test
	public void testEqual2() {
		Assert.assertFalse(binary1.equals(binary3));
	}

	@Test
	public void testEqual3() {
		Assert.assertFalse(binary1.equals(binary4));
	}

	
}
