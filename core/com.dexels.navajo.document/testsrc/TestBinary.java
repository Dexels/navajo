
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URL;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.NavajoFactory;
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
	private Binary binary1;
	private Binary binary2;
	private Binary binary3;
	private Binary binary4;
	private Binary binary5;

	
	private final static Logger logger = LoggerFactory
			.getLogger(TestBinary.class);
	
	@Before
	public void setUp() throws IOException {
		NavajoFactory.getInstance().setSandboxMode(true);
		fixture.setUp();
		binary1 = new Binary(getClass().getResourceAsStream("binary1.txt"));
		binary2 = new Binary(getClass().getResourceAsStream("binary2.txt"));
		binary3 = new Binary(getClass().getResourceAsStream("binary3.txt"));
		binary4 = new Binary(getClass().getResourceAsStream("binary4.txt"));
		logger.info("Created first");
		StringWriter sw = new StringWriter();
		binary1.writeBase64(sw);
		StringReader sr = new StringReader(sw.toString());
		binary5 = new Binary(sr);
		logger.info("Last...");
		
	}

	@After
	public void tearDown() {
		fixture.tearDown();
	}
	
	@Test
	public void testSandBox() {
		NavajoFactory.getInstance().setSandboxMode(true);
		Binary binary_x = new Binary(getClass().getResourceAsStream("binary1.txt"));
		long l = binary_x.getLength();
		System.err.println(":: "+l);
	}
//
	@Test
	public void testEqual1() {
		Assert.assertEquals(binary1,binary2);
	}

	@Test
	public void testEqual1a() {
		Assert.assertEquals(binary2,binary1);
	}

	@Test
	public void testEqual2() {
		Assert.assertFalse(binary1.equals(binary3));
	}

	@Test
	public void testEqual2a() {
		Assert.assertFalse(binary3.equals(binary1));
	}

	
	@Test
	public void testEqual3() {
		Assert.assertFalse(binary1.equals(binary4));
	}
	
	@Test
	public void testEqual5() {
		Assert.assertEquals(binary1,binary5);
		Assert.assertEquals(binary5,binary1);
	}

	@Test
	public void testMimeDetection1() throws IOException {
		// File-based test - no sandbox mode
		NavajoFactory.getInstance().setSandboxMode(false);
		URL url = this.getClass().getResource("doc1.doc");
		Binary binary_x = new Binary(new File(url.getFile()));
		Assert.assertEquals("application/msword", binary_x.guessContentType());
	}

	@Test
	public void testMimeDetection2() throws IOException {
		// File-based test - no sandbox mode
		NavajoFactory.getInstance().setSandboxMode(false);
		URL url = this.getClass().getResource("binary1.txt");
		Binary binary_x = new Binary(new File(url.getFile()));
		Assert.assertEquals("text/plain", binary_x.guessContentType());
	}

	@Test
	public void testMimeDetection3() throws IOException {
		// File-based test - no sandbox mode
		NavajoFactory.getInstance().setSandboxMode(false);
		URL url = this.getClass().getResource("doc2.odt");
		Binary binary_x = new Binary(new File(url.getFile()));
		Assert.assertEquals("application/vnd.oasis.opendocument.text", binary_x.guessContentType());
	}

	@Test
	public void testMimeDetection4() throws IOException {
		// File-based test - no sandbox mode
		NavajoFactory.getInstance().setSandboxMode(false);
		URL url = this.getClass().getResource("doc3.docx");
		Binary binary_x = new Binary(new File(url.getFile()));
		Assert.assertEquals("application/vnd.openxmlformats-officedocument.wordprocessingml.document", binary_x.guessContentType());
	}
}
