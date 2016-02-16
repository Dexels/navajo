import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.ByteBuffer;
import java.util.Collections;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.stream.NavajoDomStreamer;
import com.dexels.navajo.document.stream.NavajoStreamCollector;
import com.dexels.navajo.document.stream.api.NAVADOC;
import com.dexels.navajo.document.stream.io.ObservableStreams;
import com.dexels.navajo.document.stream.xml.Bytes;
import com.dexels.navajo.document.stream.xml.XML;

import rx.Observable;

public class TestNavajoNonBlockingStream {

	private final static Logger logger = LoggerFactory.getLogger(TestNavajoNonBlockingStream.class);

	@Before
	public void setup() {
	}

	@Test 
	public void testDomStreamerAndCollector() throws Exception {

		Navajo baseTml = NavajoFactory.getInstance()
				.createNavajo(getClass().getClassLoader().getResourceAsStream("tiny_tml.xml"));

		NavajoStreamCollector nsc = new NavajoStreamCollector();
		Navajo result = Observable.<Navajo>just(baseTml)		
		.flatMap(navajo -> NavajoDomStreamer.feed(navajo))
		  .flatMap(navajoEvent -> nsc.feed(navajoEvent))
		  .toBlocking()
		  .first();

		StringWriter sw1 = new StringWriter();
		baseTml.write(sw1);
		StringWriter sw2 = new StringWriter();
		result.write(sw2);
		FileWriter fw = new FileWriter("out1.xml");
		fw.write(sw1.toString());
		fw.close();
		FileWriter fw2 = new FileWriter("out2.xml");
		fw2.write(sw2.toString());
		fw2.close();
		Assert.assertEquals(sw1.toString(), sw2.toString());
	}

	@Test
	public void testDomStream() throws Exception {
		final Navajo baseTml = NavajoFactory.getInstance()
				.createNavajo(getClass().getClassLoader().getResourceAsStream("tml.xml"));
		int count = Observable.just(baseTml)
		.flatMap(NavajoDomStreamer::feed)
		.count()
		.toBlocking()
		 .first();
		
		Assert.assertEquals(20, count);
	}
	
	@Test 
	public void testStreamParser() throws Exception {

		int count = Bytes.fromAbsoluteClassPathBuffer("tml_without_binary.xml")
				.doOnCompleted(()->System.err.println("COMP<<<<<<<<<<<"))
				.lift(XML.parse())
				.doOnCompleted(()->System.err.println("COMP"))
				.lift(NAVADOC.parse(Collections.emptyMap()))
				.doOnNext(x->System.err.println("||> "+x.toString()))
				.doOnCompleted(()->System.err.println("COMPLETE!"))
				.count().toBlocking().first();
			Assert.assertEquals(20, count);
	}

	
	@Test 
	public void testStreamParserAndSerializer() throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Bytes.fromAbsoluteClassPath("tml_without_binary.xml")
			.map(b->ByteBuffer.wrap(b))
//		ObservableStreams.streamInputStreamWithBufferSize(getClass().getClassLoader().getResourceAsStream("tml_without_binary.xml"), 10)
			.doOnCompleted(()->System.err.println("DOOOOOONE!!!!!!"))
			.lift(XML.parse())
			.doOnCompleted(()->System.err.println("DONE!!!!!!"))
			.lift(NAVADOC.parse(Collections.emptyMap()))
			.doOnNext(
					a->System.err.println("Next: "+a))
			.lift(NAVADOC.serialize())
				.toBlocking().forEach(b -> {
					try {
						baos.write(b);
					} catch (Exception e) {
					}
				});
		byte[] original = getNavajoData("tml_without_binary.xml");
		FileOutputStream fw = new FileOutputStream("original.xml");
		fw.write(original);
		fw.close();
		FileOutputStream fw2 = new FileOutputStream("parsed.xml");
		fw2.write(baos.toByteArray());
		fw2.close();
		// TODO Seems ok, but make test to compare Navajo o
				Assert.assertArrayEquals(original, baos.toByteArray());
		// Assert.assertEquals(sw1.toString(), sw2.toString());
	}

	@Test 
	public void testStreamParserAndSerializerWithBinary() throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObservableStreams.streamInputStreamWithBufferSize(getClass().getClassLoader().getResourceAsStream("tml_with_binary.xml"), 10)
			.lift(XML.parse())
			.lift(NAVADOC.parse(Collections.emptyMap()))
			.lift(NAVADOC.serialize())
				.toBlocking().forEach(b -> {
					try {
						baos.write(b);
					} catch (Exception e) {
					}
				});
		byte[] original = getNavajoData("tml_with_binary.xml");
		FileOutputStream fw = new FileOutputStream("originalbinary.xml");
		fw.write(original);
		fw.close();
		FileOutputStream fw2 = new FileOutputStream("parsedbinary.xml");
		fw2.write(baos.toByteArray());
		fw2.close();
		Assert.assertTrue(baos.toByteArray().length>5000);
		
		// TODO Seems ok, but make test to compare Navajo o
//				Assert.assertArrayEquals(original, baos.toByteArray());
		// Assert.assertEquals(sw1.toString(), sw2.toString());
	}
	
	@Test 
	public void testStreamParserAndSerializerWithSelection() throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObservableStreams.streamInputStreamWithBufferSize(getClass().getClassLoader().getResourceAsStream("tml_with_selection.xml"), 10)
			.lift(XML.parse())
			.lift(NAVADOC.parse(Collections.emptyMap()))
			.lift(NAVADOC.collect(Collections.emptyMap()))
			.doOnNext(n->n.write(System.err))
			.lift(NAVADOC.stream())
			.lift(NAVADOC.serialize())
				.toBlocking().forEach(b -> {
					try {
						baos.write(b);
					} catch (Exception e) {
					}
				});
		byte[] original = getNavajoData("tml_with_selection.xml");
		FileOutputStream fw = new FileOutputStream("originalselection.xml");
		fw.write(original);
		fw.close();
		FileOutputStream fw2 = new FileOutputStream("parsedselection.xml");
		fw2.write(baos.toByteArray());
		fw2.close();
		// TODO Seems ok, but make test to compare Navajo o
				Assert.assertArrayEquals(original, baos.toByteArray());
		// Assert.assertEquals(sw1.toString(), sw2.toString());
	}
	
	
//	static void streamBytes(InputStream resourceAsStream, int bufferSize, ObservableOutputStream oos) {
//		byte[] buffer = new byte[bufferSize];
//		int read = -1;
//		do {
//			try {
//				read = resourceAsStream.read(buffer, 0, buffer.length);
//				if (read != -1) {
//					// System.err.println("Input: "+new
//					// String(Arrays.copyOfRange(buffer, 0, read)));
//					oos.write(buffer, 0, read);
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		} while (read != -1);
//		try {
//			oos.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//	

	public byte[] getNavajoData(String name) {

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024]; // you can configure the buffer size
		int length;

		try (InputStream in = getClass().getClassLoader().getResourceAsStream(name);) {
			while ((length = in.read(buffer)) != -1)
				out.write(buffer, 0, length);
		} catch (IOException e) {
			logger.error("Error: ", e);
		}

		byte[] result = out.toByteArray();
		return result;
		
		
	}
}
