

import org.junit.Ignore;
import org.junit.Test;

public class TestRxNetty {

	@Test @Ignore
	public void testRxNettyIssue596() {
//	Flowable<ByteBuf> body = Bytes.from(new File("/Users/frank/git/reactive-servlet/rxjava-extras-0.8.0.8.jar"))
//			.doOnNext(e->System.err.println("Read some data"))
//			.doOnComplete(()->System.err.println("All data complete"))
//			.subscribeOn(Schedulers.io())
//			.observeOn(Schedulers.computation())
//			.map(b->Unpooled.copiedBuffer(b));
//		
//	
//	HttpClient.newClient("localhost",8080).createPost("/reactive-servlet/reactive")
//	    .writeContentAndFlushOnEach(RxJavaInterop.toV1Observable(body))
//	    
//		.subscribeOn(rx.schedulers.Schedulers.io())
//		.observeOn(rx.schedulers.Schedulers.computation())
//	    .map(response->response.getContent().asObservable())
//	    .concatMap(e->e)
//	    .doOnNext(e->System.err.println("Something written back"))
//	    .doOnCompleted(()->System.err.println("All data received"))
//	    .toBlocking()
//	    .subscribe();
	
	}
	
}
