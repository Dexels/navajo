import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.Collections;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.stream.NavajoDomStreamer;
import com.dexels.navajo.document.stream.NavajoStreamCollector;
import com.dexels.navajo.document.stream.NavajoStreamOperatorsNew;
import com.dexels.navajo.document.stream.api.NAVADOC;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent.NavajoEventTypes;
import com.dexels.navajo.document.stream.xml.XML;
import com.dexels.navajo.document.stream.xml.XML2;
import com.github.davidmoten.rx2.Bytes;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Observable;


public class TestNavajoNonBlockingStreamReactive {

	private final static Logger logger = LoggerFactory.getLogger(TestNavajoNonBlockingStreamReactive.class);

	@Test 
	public void testDomStreamerAndCollector() throws Exception {

		Navajo baseTml = NavajoFactory.getInstance()
				.createNavajo(getClass().getClassLoader().getResourceAsStream("tiny_tml.xml"));

		Navajo result = Observable.just(baseTml)	
			.lift(NavajoStreamOperatorsNew.domStream())
			.lift(NavajoStreamOperatorsNew.collect())
//			.blockingLast();
			.blockingFirst();

		StringWriter sw1 = new StringWriter();
		baseTml.write(sw1);
		StringWriter sw2 = new StringWriter();
		result.write(sw2);
		Assert.assertEquals(sw1.toString(), sw2.toString());
	}

	@Test
	public void testDomStream() throws Exception {
		final Navajo baseTml = NavajoFactory.getInstance().createNavajo(getClass().getClassLoader().getResourceAsStream("tml.xml"));
		long count = Flowable.just(baseTml)
				.flatMap(NavajoDomStreamer::feedFlowable)
				.count()
				.blockingGet();
		Assert.assertEquals(23, count);
	}
	
	@Test 
	public void testStreamParser() throws Exception {
		long count = Bytes.from(TestRx.class.getClassLoader().getResourceAsStream("tml_without_binary.xml"), 8192)
				.lift(XML2.parse())
				.lift(NavajoStreamOperatorsNew.parse())
				.count()
				.blockingGet();
			Assert.assertEquals(20, count);
	}

	
	@Test 
	public void testStreamParserAndSerializer() throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Bytes.from(TestRx.class.getClassLoader().getResourceAsStream("tml_without_binary.xml"),8192)
			.lift(XML2.parse())
			.lift(NavajoStreamOperatorsNew.parse())
			.doOnNext(System.err::println)
			.lift(NavajoStreamOperatorsNew.serialize())
			.blockingForEach(b -> {
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
	public void testStreamParserAndSerializerWithBinaryUsingTml() throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		final Navajo baseTml = NavajoFactory.getInstance().createNavajo(getClass().getClassLoader().getResourceAsStream("tml_with_binary.xml"));
		Observable.just(baseTml)
			.lift(NavajoStreamOperatorsNew.domStream())
			.lift(NavajoStreamOperatorsNew.serializeObservable())
			.doOnNext(b -> {
				try {
					baos.write(b);
				} catch (Exception e) {
				}
			})
			.toFlowable(BackpressureStrategy.BUFFER)
			.lift(XML2.parse())
			.lift(NavajoStreamOperatorsNew.parse())
			.filter(event->NavajoEventTypes.MESSAGE==event.type())
			.filter(event->"SecureImage".equals(event.path()))
			.doOnNext(event->System.err.println(event.path()))
			.map(event->event.body())
			.blockingFirst();
		byte[] byteArray = baos.toByteArray();
		System.err.println("Output: "+new String(byteArray));
		Assert.assertTrue(byteArray.length>5000);
	}
	@Test 
	public void testStreamParserAndSerializerWithSelection() throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Bytes.from(TestRx.class.getClassLoader().getResourceAsStream("tml_with_selection.xml"),8192)
			.lift(XML2.parse())
			.lift(NavajoStreamOperatorsNew.parse())
			.toObservable()
			.lift(NavajoStreamOperatorsNew.collect())
			.doOnNext(n->n.write(System.err))
			.lift(NavajoStreamOperatorsNew.domStream())
			.lift(NavajoStreamOperatorsNew.serializeObservable())
				.blockingForEach(b -> {
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
		Bytes.from(TestRx.class.getClassLoader().getResourceAsStream("tml_with_date.xml"),8192)
			.lift(XML2.parse())
			.lift(NavajoStreamOperatorsNew.parse())
			.doOnNext(n->System.err.println("><>>>1 "+n))
			.toObservable()
			.lift(NavajoStreamOperatorsNew.collect())
			.doOnNext(n->System.err.println("><>>>2 "+n))
			.lift(NavajoStreamOperatorsNew.domStream())
			.doOnNext(n->System.err.println("><>>>3 "+n))
			.toFlowable(BackpressureStrategy.BUFFER)
			.lift(NavajoStreamOperatorsNew.serialize())
				.blockingForEach(b -> {
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

		Navajo navajo = Bytes.from(TestRx.class.getClassLoader().getResourceAsStream("tiny_tml.xml"),8192)
		.lift(XML2.parse())
		.lift(NavajoStreamOperatorsNew.parse())
		.doOnNext(n->System.err.println("><>>>1 "+n))
		.toObservable()
		.lift(NavajoStreamOperatorsNew.collect())
		.doOnNext(n->System.err.println("><>>>2 "+n))
		.blockingFirst();
		String rpc = navajo.getHeader().getRPCName();
		System.err.println("RPC: "+rpc);
		Assert.assertEquals("Tiny", rpc);
	}

	@Test
	public void testDomStreamerWithHeader() {
		final Navajo navajo = NavajoFactory.getInstance().createNavajo(getClass().getClassLoader().getResourceAsStream("tiny_tml.xml"));
		
		Observable.just(navajo)
		.lift(NavajoStreamOperatorsNew.domStream())
		.toFlowable(BackpressureStrategy.BUFFER)
		.lift(NavajoStreamOperatorsNew.serialize())
		.lift(XML2.parse())
		.lift(NavajoStreamOperatorsNew.parse())
		.doOnNext(n->System.err.println("><>>>1 "+n))
		.toObservable()
		.lift(NavajoStreamOperatorsNew.collect())
		.blockingFirst();
		
		String rpc = navajo.getHeader().getRPCName();
		System.err.println("RPC: "+rpc);
		Assert.assertEquals("Tiny", rpc);
	}

	
	@Test 
	public void testStreamParserAndSerializerWithIgnoreMessage() throws Exception {
		Navajo navajo = Bytes.from(TestRx.class.getClassLoader().getResourceAsStream("tiny_tml_with_ignore.xml"),8192)
			.lift(XML2.parse())
			.lift(NavajoStreamOperatorsNew.parse())
			.lift(NavajoStreamOperatorsNew.filterMessageIgnore())
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


}
