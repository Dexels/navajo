import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;

import org.junit.Ignore;
import org.junit.Test;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.adapters.stream.SQL;
import com.dexels.navajo.document.Operand;
import com.dexels.navajo.document.stream.StreamDocument;

public class TestStreamingInput  {
	private final static Logger logger = LoggerFactory.getLogger(TestStreamingInput.class);

	@Test @Ignore
	public void simpleTest() throws IOException {
		File tempFile = File.createTempFile("simpleresponse", ".xml");
		tempFile.deleteOnExit();
		FileOutputStream out = new FileOutputStream(tempFile);

		SQL.query("dummy","sometenant", "SELECT * FROM ORGANIZATION WHERE ORGANIZATIONID = 'BBKV29N'", new Operand[]{})
			.doOnNext(m->System.err.println("Message: "+m))
			.compose(StreamDocument.toMessageEvent("Organizations",true))
			.compose(StreamDocument.inNavajo("dummy",  Optional.empty(),  Optional.empty()))
			.lift(StreamDocument.serialize())
			.subscribe(new Subscriber<byte[]>() {

				@Override
				public void onComplete() {
					try {
						out.flush();
						out.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

				@Override
				public void onError(Throwable arg0) {
					logger.error("Error: ", out);
				}

				@Override
				public void onNext(byte[] b) {
					try {
						out.write(b);
					} catch (IOException e) {
						e.printStackTrace();
					}				
				}

				@Override
				public void onSubscribe(Subscription s) {
					s.request(Long.MAX_VALUE);
				}
			});
	}
	

	@Test @Ignore
	public void testSingleQuery() throws IOException {

		File tempFile = File.createTempFile("singleresponse", ".xml");
		tempFile.deleteOnExit();
		FileOutputStream out = new FileOutputStream(tempFile);

		SQL.query("dummy", "", "SELECT * FROM ORGANIZATION WHERE ORGANIZATIONID = 'BBKV29N'",new Operand[] {})
			.doOnNext(m->System.err.println("Message: "+m))
			.doOnComplete(()->System.err.println("Done!"))
			.compose(StreamDocument.toMessageEvent("Organizations",true))
			.compose(StreamDocument.inNavajo("dummy",  Optional.empty(),  Optional.empty()))
			.lift(StreamDocument.serialize())
			.subscribe(new Subscriber<byte[]>() {

				@Override
				public void onComplete() {
					try {
						System.err.println("Done");
						out.flush();
						out.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

				@Override
				public void onError(Throwable arg0) {
					logger.error("Error: ", out);
				}

				@Override
				public void onNext(byte[] b) {
					try {
						System.err.println("Item: "+new String(b));
						out.write(b);
					} catch (IOException e) {
						e.printStackTrace();
					}				
				}

				@Override
				public void onSubscribe(Subscription s) {
					s.request(Long.MAX_VALUE);
				}
			});
	}
	
}
