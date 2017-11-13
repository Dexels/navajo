import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.stream.StreamCompress;
import com.dexels.navajo.document.stream.StreamDocument;
import com.dexels.navajo.document.stream.xml.XML;
import com.dexels.navajo.document.stream.xml.XMLEvent;
import com.github.davidmoten.rx2.Bytes;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class TestRx {
	
	@Test 
	public void testFromIterableCompletes() {
		List<String> l = Arrays.asList(new String[]{"a","b","c"});
		Flowable<String> a = Flowable.fromIterable(l);
		List<String> l2 = Arrays.asList(new String[]{"d","e","f"});
		Flowable<String> a2 = Flowable.fromIterable(l2);
		
		Flowable<Flowable<String>> joined = Flowable.just(a, a2);
		
		joined.flatMap(e->e).doOnComplete(()->System.err.println("Flowable completed"))
		.blockingForEach(xml->System.err.println("XML: "+xml));
//		.subscribe(e->System.err.println("item: "+e));
//		String a = "<a><ble></ble><aba>";
//		String b = "tralala</aba></a>";
	}

	@Test
	public void simplerXML() {
		String[] parts = new String[]{"<a><ble></ble><aba>","tralala</aba></a>"};
		Flowable.fromArray(parts)
			.map(x->x.getBytes())
			.lift(XML.parseFlowable(10))
			.doOnComplete(()->System.err.println("Pre-flatmap complete"))
			.flatMap(e->e)
			.doOnComplete(()->System.err.println("Post-flatmap complete"))
			.blockingForEach(xml->System.err.println("XML: "+xml));
//			.subscribe(event->System.err.println("Event: "+event));
	}
	
	@Test 
	public void simplerXMLObservable() {
		String[] parts = new String[]{"<a><ble></ble><aba>","tralala</aba></a>"};
		Observable.fromArray(parts)
			.map(x->x.getBytes())
			.lift(XML.parse())
			.doOnComplete(()->System.err.println("Pre-flatmap complete"))
			.flatMap(e->e)
			.doOnComplete(()->System.err.println("Post-flatmap complete"))
			.subscribe(event->System.err.println("Event: "+event));
	}

	@Test 
	public void simpleXML() throws InterruptedException {
		Bytes.from(TestRx.class.getClassLoader().getResourceAsStream("tml_with_binary.xml"), 128)
			.lift(XML.parseFlowable(10))
			.doOnComplete(()->System.err.println("Pre-flatmap complete"))
			.flatMap(e->e)
			.doOnComplete(()->System.err.println("Post-flatmap complete"))
			.doOnNext(e->System.err.println("Element encountered"))
//			.subscribe(event->System.err.println("Event: "+event));
			.blockingForEach(xml->System.err.println("XML: "+xml));
	}	

//	@Test
//	public void gettingDesperate() {
//		Flowable
//	}
	
	
	@Test 
	public void testXMLSimple() {
		Bytes.from(TestRx.class.getClassLoader().getResourceAsStream("tml_with_binary.xml"), 128)
			.lift(XML.parseFlowable(10))
			.doOnNext(e->System.err.println("Element encountered"))
			.concatMap(e->e)
			.lift(StreamDocument.parse())
			.concatMap(e->e)
			.blockingForEach(e->System.err.println(e));
	}
			
	@Test 
	public void testXML() {
		Navajo result = Bytes.from(TestRx.class.getClassLoader().getResourceAsStream("tml_with_binary.xml"), 128)
			.lift(XML.parseFlowable(10))
			.doOnNext(e->System.err.println("Element encountered"))
			.concatMap(e->e)
			.lift(StreamDocument.parse())
			.concatMap(e->e)
			.doOnNext(e->System.err.println("Event: "+e.toString()))
			.toObservable()
			.lift(StreamDocument.collect())
			.firstOrError().blockingGet();
		System.err.println("Result: "+result.toString());
	}
	
	
	
	@Test 
	public void repro() throws IOException {
		
		File compressed = File.createTempFile("dump", ".xml.deflated");
		File uncompressed = File.createTempFile("dump", ".xml");
		
		InputStream resourceAsStream = TestRx.class.getClassLoader().getResourceAsStream("tml_with_binary.xml");
		Bytes.from(resourceAsStream,8192)
			.doOnSubscribe(e->System.err.println("Ataaaa"))
			.doOnNext(b->System.err.println("Bytes: "+b.length))
			.subscribe(StreamDocument.dumpToFile(uncompressed.getAbsolutePath()));
		
		System.err.println("Uncompressed file at: "+uncompressed.getAbsolutePath());
		Bytes.from(TestRx.class.getClassLoader().getResourceAsStream("tml_with_binary.xml"),8192)
			.lift(StreamDocument.deflate2())
			.concatMap(e->e)
			.subscribe(StreamDocument.dumpToFile(compressed.getAbsolutePath()));
		System.err.println("Compressed file at: "+compressed.getAbsolutePath());
		

		//1,560 
		Assert.assertTrue(uncompressed.exists());
		System.err.println("Compressed: "+compressed.length());
		System.err.println("Uncompressed: "+uncompressed.length());
		Assert.assertTrue(uncompressed.length()==5258);
		Assert.assertTrue(compressed.length()<uncompressed.length());
//		compressed.delete();
//		uncompressed.delete();
	}
	
	@Test 
	public void testDeflate() throws FileNotFoundException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] original = 
				Bytes.from(TestRx.class.getClassLoader().getResourceAsStream("TestBinaries.class"))
				.reduce(baos, (byteout,bytes)->{try {
					byteout.write(bytes);
					} catch (Exception e) {
					} return byteout;})
				.blockingGet()
				.toByteArray();
		ByteArrayOutputStream baos_compressed = new ByteArrayOutputStream();
		
		byte[] compressed = Bytes.from(TestRx.class.getClassLoader().getResourceAsStream("TestBinaries.class"))
				.lift(StreamDocument.deflate2())
				.doOnError(e->e.printStackTrace())
				.concatMap(e->e)
				.reduce(baos_compressed, (byteout,bytes)->{byteout.write(bytes); return byteout;})
			.blockingGet()
			.toByteArray();
		ByteArrayOutputStream baos_reinflate = new ByteArrayOutputStream();
		byte[] reflated = Flowable.<byte[]>just(compressed)
				.lift(StreamCompress.inflate2())
				.concatMap(r->r)
				.reduce(baos_reinflate, (byteout,bytes)->{try {
					System.err.println("Bytes decompressed: "+bytes.length);
					byteout.write(bytes);
				} catch (Exception e) {
				} return byteout;})
			.blockingGet()
			.toByteArray();
		System.err.println("original: "+original.length);
		System.err.println("deflated: "+compressed.length);

		System.err.println("reinflated: "+reflated.length);
		Assert.assertArrayEquals(original, reflated);
		
	}
	
	
	
	@Test 
	public void testGzip() throws FileNotFoundException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] original = 
				Bytes.from(TestRx.class.getClassLoader().getResourceAsStream("TestBinaries.class"))
				.reduce(baos, (byteout,bytes)->{try {
					byteout.write(bytes);
					} catch (Exception e) {
					} return byteout;})
				.blockingGet()
				.toByteArray();
		ByteArrayOutputStream baos_compressed = new ByteArrayOutputStream();
		
		byte[] compressed = Bytes.from(TestRx.class.getClassLoader().getResourceAsStream("TestBinaries.class"))
				.lift(StreamCompress.gzip())
				.doOnError(e->e.printStackTrace())
				.concatMap(e->e)
				.reduce(baos_compressed, (byteout,bytes)->{byteout.write(bytes); return byteout;})
			.blockingGet()
			.toByteArray();
		ByteArrayOutputStream baos_reinflate = new ByteArrayOutputStream();
		byte[] reflated = Flowable.<byte[]>just(compressed)
				.lift(StreamCompress.gunzip())
				.concatMap(r->r)
				.reduce(baos_reinflate, (byteout,bytes)->{try {
					System.err.println("Bytes decompressed: "+bytes.length);
					byteout.write(bytes);
				} catch (Exception e) {
				} return byteout;})
			.blockingGet()
			.toByteArray();
		System.err.println("original: "+original.length);
		System.err.println("gzipped: "+compressed.length);

		System.err.println("reinflated: "+reflated.length);
		Assert.assertArrayEquals(original, reflated);
	}

	@Test
	public void testGunzipTrustedFile() throws FileNotFoundException, IOException {
		File gunzipped = new File("gunzipped");
		try(FileOutputStream fos = new FileOutputStream(gunzipped)) {
			Bytes.from(new File("gzippedpage.gz"))
			.lift(StreamCompress.gunzip())
			.concatMap(e->e)
			.blockingForEach(bytes->fos.write(bytes));
		}
	}
	
	@Test @Ignore
	//TODO, can't get it to work
	public void testGunzipTrustedFileInSingleBytes() throws FileNotFoundException, IOException {
		File gunzipped = new File("gunzipped");
		List<byte[]> events = new ArrayList<>();
		try(FileOutputStream fos = new FileOutputStream(gunzipped)) {
			try(FileInputStream fis = new FileInputStream("gzippedpage.gz")) {
				int read = 0;
				do {
					byte[] buffer = new byte[1];
					read = fis.read(buffer);
					events.add(Arrays.copyOf(buffer, 1));
				} while (read>0);
				System.err.println("# of events: "+events.size());
				Flowable.fromIterable(events)
					.lift(StreamCompress.gunzip())
					.concatMap(e->e)
					.doOnNext(e->System.err.println("Uncompressed bytes: "+e.length))
					.blockingForEach(bytes->fos.write(bytes));
			}
		}
	}
	
	@Test 
	public void testBackpressure() {
		
		final int DELAY = 1;
		final int REQUESTCOUNT = 1;
		final Thread me = Thread.currentThread();
		final long started = System.currentTimeMillis();
		AtomicLong count = new AtomicLong();
		Bytes.from(TestRx.class.getClassLoader().getResourceAsStream("tml.xml"), 1)
			.lift(XML.parseFlowable(1))
			.flatMap(e->e)
			.subscribe(new Subscriber<XMLEvent>(){
				Disposable timer;
				@Override
				public void onComplete() {
					long complete = System.currentTimeMillis();
					System.err.println("Total run took: "+(complete-started)+" for "+count.get()+" elements. Should be little more than: "+(DELAY*count.get()/REQUESTCOUNT));
					timer.dispose();
					me.interrupt();
				}

				@Override
				public void onError(Throwable e) {
					timer.dispose();
					e.printStackTrace();
					me.interrupt();

				}

				@Override
				public void onSubscribe(Subscription s) {
					s.request(1);
					timer = Observable.interval(DELAY, TimeUnit.MILLISECONDS)
						.subscribe(l->s.request(REQUESTCOUNT));
				}

				@Override
				public void onNext(XMLEvent x) {
					count.incrementAndGet();
				}
			});
	
		try {
			Thread.sleep(500000);
		} catch (InterruptedException e1) {
		}
	}
}
