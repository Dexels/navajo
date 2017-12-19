
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
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
		Assert.assertEquals(7, l);
	}
//
	@Test
	public void testEqual1() {
		Assert.assertEquals(new String(binary1.getDigest().hex()),new String(binary2.getDigest().hex()));
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
    public void testEqual6() throws IOException, URISyntaxException {
	    Binary binaryStreamed = new Binary(getClass().getResourceAsStream("logo.gif"));
	    Path path = Paths.get(getClass().getResource("logo.gif").toURI());
	    byte[] data = Files.readAllBytes(path);
	    Binary binaryByteArray = new Binary(data);

        Assert.assertEquals(binaryStreamed, binaryByteArray);
    }

	@Test
	@Ignore // Ignore until we find a proper fix for this problem...
	public void testMimeDetection1() throws IOException {
		// File-based test - no sandbox mode
		NavajoFactory.getInstance().setSandboxMode(false);
		URL url = this.getClass().getResource("doc1.doc");
		Binary binary_x = new Binary(new File(url.getFile()));
		Assert.assertEquals("application/msword", binary_x.guessContentType());
	}

	@Test
	public void testBinarySerialize() throws IOException, ClassNotFoundException {
		NavajoFactory.getInstance().setSandboxMode(true);
		Binary binary_x = new Binary(getClass().getResourceAsStream("binary1.txt"));
		binary_x.getLength();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(binary_x);
		byte[] bb = baos.toByteArray();
		ObjectInputStream ois =  new ObjectInputStream(new ByteArrayInputStream(bb));
		Binary ooo = (Binary) ois.readObject();
		Assert.assertTrue(ooo.equals(binary_x));
	}

	@Test
	@Ignore // Ignore until we find a proper fix for this problem...
	public void testMimeDetection2() throws IOException {
		// File-based test - no sandbox mode
		NavajoFactory.getInstance().setSandboxMode(false);
		URL url = this.getClass().getResource("binary1.txt");
		Binary binary_x = new Binary(new File(url.getFile()));
		Assert.assertEquals("text/plain", binary_x.guessContentType());
	}

	@Test
	@Ignore // Ignore until we find a proper fix for this problem...
	public void testMimeDetection3() throws IOException {
		// File-based test - no sandbox mode
		NavajoFactory.getInstance().setSandboxMode(false);
		URL url = this.getClass().getResource("doc2.odt");
		Binary binary_x = new Binary(new File(url.getFile()));
		Assert.assertEquals("application/vnd.oasis.opendocument.text", binary_x.guessContentType());
	}

	@Test
	@Ignore // Ignore until we find a proper fix for this problem...
	public void testMimeDetection4() throws IOException {
		// File-based test - no sandbox mode
		NavajoFactory.getInstance().setSandboxMode(false);
		URL url = this.getClass().getResource("doc3.docx");
		Binary binary_x = new Binary(new File(url.getFile()));
		Assert.assertEquals("application/vnd.openxmlformats-officedocument.wordprocessingml.document", binary_x.guessContentType());
	}
	
	@Test
	public void testBinaryFromURL() throws IOException {
		URL u = new URL("https://www.google.com/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png");
		Binary b = new Binary(u,false);
		Assert.assertTrue("",b.getLength()>2000);
	}
	
	@Test
	public void testUnresolvedBinary() throws IOException {
		URL u = new URL("https://www.google.com/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png");
		Binary b = new Binary(u,true);		
		Assert.assertFalse(b.isResolved());
		Assert.assertTrue("",b.getData().length>2000);
		Assert.assertTrue(b.isResolved());
		
	}
	
	@Test
	public void testResolveOnTransport() throws IOException {
		URL u = new URL("https://www.google.com/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png");
		Binary b = new Binary(u,true);		
		Assert.assertFalse(b.isResolved());
		StringWriter sw = new StringWriter();
		b.writeBase64(sw);
		sw.close();
		String result = sw.toString();
		Assert.assertTrue(b.isResolved());
		Assert.assertTrue(result.length()>1000);
	}
	
	@Test
	public void testNonLazyBinary() {
		Binary b1 = new Binary(getClass().getResourceAsStream("binary1.txt"));
		Assert.assertEquals(7,b1.getData().length);
	}

	@Test
	public void testEmptyBinary() {
		Binary b1 = new Binary();
		Assert.assertNull(b1.getData());
	}
	@Test
	public void testLazyFileBinary() throws IOException {
		Binary b1 = new Binary(getClass().getResourceAsStream("binary1.txt"));
		File temp = File.createTempFile("junit", "binary");
		temp.deleteOnExit();
		FileOutputStream fos = new FileOutputStream(temp);
		b1.write(fos);
		fos.close();
		
		Binary b2 = new Binary(temp,true);
		Assert.assertEquals(7,b2.getData().length);
	}
	
	@Test
	public void testBinaryDigest() {
		Binary b1 = new Binary(getClass().getResourceAsStream("binary1.txt"));
		Property p1 = NavajoFactory.getInstance().createProperty(null, "Binary", Property.BINARY_PROPERTY, "", 0, "", Property.DIR_IN);
		Property p2 = NavajoFactory.getInstance().createProperty(null, "Binary2", Property.BINARY_PROPERTY, "", 0, "", Property.DIR_IN);
		p1.setAnyValue(b1);
		Assert.assertEquals(Property.BINARY_PROPERTY,p1.getType());
		p2.setAnyValue(b1.getDigest());
		Assert.assertEquals(Property.BINARY_DIGEST_PROPERTY,p2.getType());
	}

	@Test
	public void testBinaryIterator() {
		NavajoFactory.getInstance().setSandboxMode(false);
		Binary b1 = new Binary(getClass().getResourceAsStream("datasources.xml"));
		int i = 0;
		for (byte[] e : b1.getDataAsIterable(1024)) {
			i+=e.length;
		}
		Assert.assertEquals(b1.getLength(), i);
	}

	@Test
	public void testBinaryIteratorFromLazyURL() throws IOException {
		NavajoFactory.getInstance().setSandboxMode(false);
		URL u = new URL("https://www.google.com/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png");
		Binary b1 = new Binary(u,false);
		
		int i = 0;
		int size = 0;
		for (byte[] e : b1.getDataAsIterable(128)) {
			i++;
			size += e.length;
		}
		System.err.println(">>> "+size);
		Assert.assertEquals(5969, size);
		Assert.assertEquals(93, i);
	}

}
