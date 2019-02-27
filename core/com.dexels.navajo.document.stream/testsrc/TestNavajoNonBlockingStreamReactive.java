import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.stream.NavajoDomStreamer;
import com.dexels.navajo.document.stream.StreamDocument;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent.NavajoEventTypes;
import com.dexels.navajo.document.stream.xml.XML;
import com.github.davidmoten.rx2.Bytes;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Single;


public class TestNavajoNonBlockingStreamReactive {

	private final static Logger logger = LoggerFactory.getLogger(TestNavajoNonBlockingStreamReactive.class);

	@Test 
	public void testDomStreamerAndCollector() throws Exception {

		Navajo baseTml = NavajoFactory.getInstance()
				.createNavajo(getClass().getClassLoader().getResourceAsStream("tiny_tml.xml"));

		Navajo result = Single.just(baseTml)	
			.compose(StreamDocument.domStreamTransformer())
			.toObservable()
			.concatMap(e->e)
			.compose(StreamDocument.domStreamCollector())
//			.concatMap(e->e)
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
				.lift(XML.parseFlowable(5))
				.flatMap(e->e)
				.lift(StreamDocument.parse())
				.concatMap(e->e)
				.count()
				.blockingGet();
			Assert.assertEquals(20, count);
	}

	
	@Test 
	public void testStreamParserAndSerializer() throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Bytes.from(TestRx.class.getClassLoader().getResourceAsStream("tml_without_binary.xml"),8192)
			.lift(XML.parseFlowable(5))
			.concatMap(e->e)
			.lift(StreamDocument.parse())
			.concatMap(e->e)
			.doOnNext(System.err::println)
			.lift(StreamDocument.serialize())
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
		Single.just(baseTml)
			.compose(StreamDocument.domStreamTransformer())
			.toObservable()
			.concatMap(e->e)
			.lift(StreamDocument.serializeObservable())
			.doOnNext(b -> {
				try {
					baos.write(b);
				} catch (Exception e) {
				}
			})
			.toFlowable(BackpressureStrategy.BUFFER)
			.lift(XML.parseFlowable(5))
			.concatMap(e->e)
			.lift(StreamDocument.parse())
			.concatMap(e->e)
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
			.lift(XML.parseFlowable(5))
			.flatMap(e->e)
			.lift(StreamDocument.parse())
			.concatMap(e->e)
			.toObservable()
			.doOnNext(n->{
				System.err.println("N: "+n);
				
			}
					)
			.compose(StreamDocument.domStreamCollector())
			.firstOrError()
//			.concatMap(e->e)
			.doOnSuccess(e->e.write(System.err))
			.compose(StreamDocument.domStreamTransformer())
			.toObservable()
			.concatMap(e->e)
			.lift(StreamDocument.serializeObservable())
				.blockingForEach(b -> {
					try {
						baos.write(b);
					} catch (Exception e) {
					}
				});
		byte[] original = getNavajoData("tml_with_selection.xml");
		byte[] result = baos.toByteArray();
		System.err.println("original: "+new String(original));
		System.err.println("result: "+new String(result));
		Assert.assertArrayEquals(original, result);
	}
	
	
	@Test 
	public void testStreamParserAndSerializerWithDate() throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Bytes.from(TestRx.class.getClassLoader().getResourceAsStream("tml_with_date.xml"),8192)
			.lift(XML.parseFlowable(5))
			.flatMap(e->e)
			.lift(StreamDocument.parse())
			.concatMap(e->e)
			.doOnNext(n->System.err.println("><>>>1 "+n))
			.toObservable()
			.compose(StreamDocument.domStreamCollector())
//			.concatMap(e->e)
			.doOnNext(n->System.err.println("><>>>2 "+n))
			.firstOrError()
			.compose(StreamDocument.domStreamTransformer())
			.toObservable()
			.concatMap(e->e)
//			.doOnSuccess(n->System.err.println("><>>>3 "+n))
			.toFlowable(BackpressureStrategy.BUFFER)
			.lift(StreamDocument.serialize())
			.blockingForEach(b -> {
					try {
						baos.write(b);
					} catch (Exception e) {
				}});
		byte[] original = getNavajoData("tml_with_date.xml");
		Assert.assertArrayEquals(original, baos.toByteArray());
	}

	@Test
	public void testHeader() {

		Navajo navajo = Bytes.from(TestRx.class.getClassLoader().getResourceAsStream("tiny_tml.xml"),8192)
		.lift(XML.parseFlowable(5))
		.flatMap(e->e)
		.lift(StreamDocument.parse())
		.concatMap(e->e)
		.doOnNext(n->System.err.println("><>>>1 "+n))
		.toObservable()
		.compose(StreamDocument.domStreamCollector())
//		.concatMap(e->e)
		.doOnNext(n->System.err.println("><>>>2 "+n))
		.blockingFirst();
		String rpc = navajo.getHeader().getRPCName();
		System.err.println("RPC: "+rpc);
		Assert.assertEquals("Tiny", rpc);
	}

	@Test
	public void testDomStreamerWithHeader() {
		final Navajo navajo = NavajoFactory.getInstance().createNavajo(getClass().getClassLoader().getResourceAsStream("tiny_tml.xml"));
		
		Single.just(navajo)
		.compose(StreamDocument.domStreamTransformer())
		.toObservable()
		.concatMap(e->e)
		.toFlowable(BackpressureStrategy.BUFFER)
		.lift(StreamDocument.serialize())
		.lift(XML.parseFlowable(5))
		.flatMap(e->e)
		.lift(StreamDocument.parse())
		.concatMap(e->e)
		.doOnNext(n->System.err.println("><>>>1 "+n))
		.toObservable()
		.compose(StreamDocument.domStreamCollector())
		.blockingFirst();
		
		String rpc = navajo.getHeader().getRPCName();
		System.err.println("RPC: "+rpc);
		Assert.assertEquals("Tiny", rpc);
	}

	
	@Test 
	public void testStreamParserAndSerializerWithIgnoreMessage() throws Exception {
		try {
			Navajo navajo = Bytes.from(TestRx.class.getClassLoader().getResourceAsStream("tiny_tml_with_ignore.xml"),8192)
				.lift(XML.parseFlowable(5))
				.concatMap(e->e)
				.lift(StreamDocument.parse())
				.concatMap(e->e)
				.lift(StreamDocument.filterMessageIgnore())
				.toObservable()
				.compose(StreamDocument.domStreamCollector())
//				.concatMap(e->e)
				.blockingFirst();

			Message ignored = navajo.getMessage("Message");
			System.err.println("Msg: "+ignored);
			Assert.assertNull(ignored);
		} catch (Exception e) {
			e.printStackTrace();
		}
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
