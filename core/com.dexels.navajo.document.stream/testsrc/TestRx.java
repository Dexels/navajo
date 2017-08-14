import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.stream.NavajoStreamOperatorsNew;
import com.dexels.navajo.document.stream.io.NavajoReactiveOperators;
import com.dexels.navajo.document.stream.xml.XML2;
import com.dexels.navajo.document.stream.xml.XMLEvent;
import com.github.davidmoten.rx2.Bytes;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class TestRx {

	
	@Test
	public void testXML() {
		Navajo result = Bytes.from(TestRx.class.getClassLoader().getResourceAsStream("tml_with_binary.xml"), 128)
//			.doOnNext(b->System.err.println(">>: "+new String(b)))
			.lift(NavajoReactiveOperators.identity())
			.lift(XML2.parse())
			.lift(NavajoStreamOperatorsNew.parse())
			.doOnNext(e->System.err.println("Event: "+e.toString()))
			.toObservable()
			.lift(NavajoStreamOperatorsNew.collect())
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
			.subscribe(NavajoReactiveOperators.dumpToFile(uncompressed.getAbsolutePath()));
		
		Bytes.from(TestRx.class.getClassLoader().getResourceAsStream("tml_with_binary.xml"),8192)
			.lift(NavajoReactiveOperators.deflate())
			.subscribe(NavajoReactiveOperators.dumpToFile(compressed.getAbsolutePath()));
		

		Assert.assertTrue(uncompressed.exists());
		System.err.println("Compressed: "+compressed.length());
		System.err.println("Uncompressed: "+uncompressed.length());
		Assert.assertTrue(uncompressed.length()>5000);
		Assert.assertTrue(compressed.length()<uncompressed.length());
		
		compressed.delete();
		uncompressed.delete();
	}
	
	
	@Test
	public void testDeflate() throws FileNotFoundException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] original = 
				Bytes.from(TestRx.class.getClassLoader().getResourceAsStream("TestCompression.class"))
				.reduce(baos, (byteout,bytes)->{try {
					byteout.write(bytes);
					} catch (Exception e) {
					} return byteout;})
				.blockingGet()
				.toByteArray();
		ByteArrayOutputStream baos_compressed = new ByteArrayOutputStream();
		byte[] compressed = Bytes.from(TestRx.class.getClassLoader().getResourceAsStream("TestCompression.class"))
				.lift(NavajoReactiveOperators.deflate())
				.reduce(baos_compressed, (byteout,bytes)->{try {
					byteout.write(bytes);
				} catch (Exception e) {
				} return byteout;})
			.blockingGet()
			.toByteArray();
		ByteArrayOutputStream baos_reinflate = new ByteArrayOutputStream();
		byte[] reflated = Flowable.<byte[]>just(compressed)
				.lift(NavajoReactiveOperators.inflate())
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
	public void testBackpressure() {
		
		final int DELAY = 10;
		final int REQUESTCOUNT = 11;
		final Thread me = Thread.currentThread();
		final long started = System.currentTimeMillis();
		AtomicLong count = new AtomicLong();
		Bytes.from(TestRx.class.getClassLoader().getResourceAsStream("tml.xml"), 1)
			.lift(XML2.parse())
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
