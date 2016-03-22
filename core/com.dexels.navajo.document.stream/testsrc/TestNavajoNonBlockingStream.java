import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Collections;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.stream.NavajoDomStreamer;
import com.dexels.navajo.document.stream.NavajoStreamCollector;
import com.dexels.navajo.document.stream.api.NAVADOC;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent.NavajoEventTypes;
import com.dexels.navajo.document.stream.impl.SaxXmlFeeder;
import com.dexels.navajo.document.stream.xml.Bytes;
import com.dexels.navajo.document.stream.xml.XML;
import com.dexels.navajo.document.stream.xml.XMLEvent;
import com.dexels.navajo.document.stream.xml.XMLEvent.XmlEventTypes;

import rx.Observable;

public class TestNavajoNonBlockingStream {

	private final static Logger logger = LoggerFactory.getLogger(TestNavajoNonBlockingStream.class);

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
		Assert.assertEquals(sw1.toString(), sw2.toString());
	}

	@Test
	public void testDomStream() throws Exception {
		final Navajo baseTml = NavajoFactory.getInstance().createNavajo(getClass().getClassLoader().getResourceAsStream("tml.xml"));
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
				.lift(XML.parse())
				.lift(NAVADOC.parse(Collections.emptyMap()))
				.count()
				.toBlocking()
				.first();
			Assert.assertEquals(20, count);
	}

	
	@Test 
	public void testStreamParserAndSerializer() throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Bytes.fromAbsoluteClassPath("tml_without_binary.xml")
			.lift(XML.parse())
			.lift(NAVADOC.parse(Collections.emptyMap()))
			.doOnNext(System.err::println)
			.lift(NAVADOC.serialize())
			.toBlocking().forEach(b -> {
				try {
					baos.write(b);
				} catch (Exception e) {
			}
		});
		byte[] original = getNavajoData("tml_without_binary.xml");
		// TODO Seems ok, but make test to compare Navajo o
		Assert.assertArrayEquals(original, baos.toByteArray());
	}

	@Test 
	public void testStreamParserAndSerializerWithBinary() throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Bytes.fromAbsoluteClassPath("tml_with_binary.xml")
			.lift(XML.parse())
			.lift(NAVADOC.parse(Collections.emptyMap()))
			.lift(NAVADOC.serialize())
				.toBlocking().forEach(b -> {
					try {
						baos.write(b);
					} catch (Exception e) {
					}
				});
		Assert.assertTrue(baos.toByteArray().length>5000);
	}
	
	@Test 
	public void testStreamParserAndSerializerWithBinaryUsingTml() throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		final Navajo baseTml = NavajoFactory.getInstance().createNavajo(getClass().getClassLoader().getResourceAsStream("tml_with_binary.xml"));
		Observable.just(baseTml)
			.lift(NAVADOC.stream())
			.lift(NAVADOC.serialize())
			.doOnNext(b -> {
				try {
					baos.write(b);
				} catch (Exception e) {
				}
			})
			.lift(XML.parse())
			.lift(NAVADOC.parse(Collections.emptyMap()))
			.filter(event->NavajoEventTypes.MESSAGE==event.type())
			.filter(event->"SecureImage".equals(event.path()))
			.doOnNext(event->System.err.println(event.path()))
			.map(event->event.body())
			.toBlocking()
			.first()
			;
		Assert.assertTrue(baos.toByteArray().length>5000);
	}
	@Test 
	public void testStreamParserAndSerializerWithSelection() throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Bytes.fromAbsoluteClassPath("tml_with_selection.xml")
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
		Assert.assertArrayEquals(original, baos.toByteArray());
	}
	
	
	@Test 
	public void testStreamParserAndSerializerWithDate() throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Bytes.fromAbsoluteClassPath("tml_with_date.xml")
			.lift(XML.parse())
			.lift(NAVADOC.parse(Collections.emptyMap()))
			.doOnNext(n->System.err.println("><>>>1 "+n))
			.lift(NAVADOC.collect(Collections.emptyMap()))
			.doOnNext(n->System.err.println("><>>>2 "+n))
			.lift(NAVADOC.stream())
			.doOnNext(n->System.err.println("><>>>3 "+n))
			.lift(NAVADOC.serialize())
				.toBlocking().forEach(b -> {
					try {
						baos.write(b);
					} catch (Exception e) {
					}
				});
		byte[] original = getNavajoData("tml_with_date.xml");
		Assert.assertArrayEquals(original, baos.toByteArray());
	}

	@Test
	public void testHeader() {
		Navajo navajo = Bytes.fromAbsoluteClassPath("tiny_tml.xml")
		.lift(XML.parse())
		.lift(NAVADOC.parse(Collections.emptyMap()))
		.doOnNext(n->System.err.println("><>>>1 "+n))
		.lift(NAVADOC.collect(Collections.emptyMap()))
		.doOnNext(n->System.err.println("><>>>2 "+n))
		.toBlocking()
		.first();
		String rpc = navajo.getHeader().getRPCName();
		System.err.println("RPC: "+rpc);
		Assert.assertEquals("Tiny", rpc);
	}

	@Test
	public void testDomStreamerWithHeader() {
		final Navajo navajo = NavajoFactory.getInstance().createNavajo(getClass().getClassLoader().getResourceAsStream("tiny_tml.xml"));
		
		Observable.just(navajo)
		.lift(NAVADOC.stream())
		.lift(NAVADOC.serialize())
		.lift(XML.parse())
		.lift(NAVADOC.parse(Collections.emptyMap()))
		.doOnNext(n->System.err.println("><>>>1 "+n))
		.lift(NAVADOC.collect(Collections.emptyMap()))
		.toBlocking()
		.first();
		
		String rpc = navajo.getHeader().getRPCName();
		System.err.println("RPC: "+rpc);
		Assert.assertEquals("Tiny", rpc);
	}

	
	@Test 
	public void testStreamParserAndSerializerWithIgnoreMessage() throws Exception {
		Navajo navajo =
		Bytes.fromAbsoluteClassPath("tiny_tml_with_ignore.xml")
			.lift(XML.parse())
			.lift(NAVADOC.parse(Collections.emptyMap()))
			.doOnNext(n->System.err.println("><>>>1 "+n))
			.lift(NAVADOC.filterMessageIgnore())
			.doOnNext(n->System.err.println("><>>>q "+n))
			.lift(NAVADOC.collect(Collections.emptyMap()))
			.toBlocking().first();

		Message ignored = navajo.getMessage("Message");
		Assert.assertNull(ignored);
	}

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

	@Test @Ignore
	public void testXmlFeeder() throws IOException {
		File f = new File("/Users/frank/output3.xml");
		SaxXmlFeeder sxf = new SaxXmlFeeder();
		try(FileInputStream fis = new FileInputStream(f)) {
			byte[] buffer = new byte[1024];
			int read = -1;
			boolean ready = false;
			while (!ready) {
				try {
					read = fis.read(buffer);
					if ( read > -1 ) {
						Iterable<XMLEvent> ee = sxf.parse(Arrays.copyOfRange(buffer, 0, read));
						for (XMLEvent xmlEvent : ee) {
							if(xmlEvent.getType()!=XmlEventTypes.TEXT) {
								System.err.println("Event "+xmlEvent);
							}
						}
					}
				} catch (IOException e) {
				}
				if ( read <= -1) {
					ready = true;
				}
			}
			sxf.endOfInput();
		}
				
		
	}

}
