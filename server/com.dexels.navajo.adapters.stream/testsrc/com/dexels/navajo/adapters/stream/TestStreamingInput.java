package com.dexels.navajo.adapters.stream;
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

import com.dexels.navajo.document.stream.StreamDocument;

public class TestStreamingInput  {
	private static final Logger logger = LoggerFactory.getLogger(TestStreamingInput.class);

	@Test @Ignore
	public void simpleTest() throws IOException {
		File tempFile = File.createTempFile("simpleresponse", ".xml");
		tempFile.deleteOnExit();
		FileOutputStream out = new FileOutputStream(tempFile);

		SQL.query("dummy","sometenant", "SELECT * FROM ORGANIZATION WHERE ORGANIZATIONID = 'BBKV29N'")
			.doOnNext(m->logger.info("Message: {}",m))
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
						logger.error("Error: ", e);
					}
				}

				@Override
				public void onError(Throwable e) {
					logger.error("Error: ", e);
				}

				@Override
				public void onNext(byte[] b) {
					try {
						out.write(b);
					} catch (IOException e) {
						logger.error("Error: ", e);
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

		SQL.query("dummy", "", "SELECT * FROM ORGANIZATION WHERE ORGANIZATIONID = 'BBKV29N'")
			.doOnNext(m->logger.info("Message: {}",m))
			.doOnComplete(()->logger.info("Done!"))
			.compose(StreamDocument.toMessageEvent("Organizations",true))
			.compose(StreamDocument.inNavajo("dummy",  Optional.empty(),  Optional.empty()))
			.lift(StreamDocument.serialize())
			.subscribe(new Subscriber<byte[]>() {

				@Override
				public void onComplete() {
					try {
						logger.info("Done");
						out.flush();
						out.close();
					} catch (IOException e) {
						logger.error("Error: ", e);
					}
				}

				@Override
				public void onError(Throwable e) {
					logger.error("Error: ", e);
				}

				@Override
				public void onNext(byte[] b) {
					try {
						logger.info("Item: {}", new String(b));
						out.write(b);
					} catch (IOException e) {
						logger.error("Error: ", e);
					}				
				}

				@Override
				public void onSubscribe(Subscription s) {
					s.request(Long.MAX_VALUE);
				}
			});
	}
	
}
