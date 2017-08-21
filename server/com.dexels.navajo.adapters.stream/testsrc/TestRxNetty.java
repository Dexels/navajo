

import java.io.File;
import java.nio.charset.Charset;

import org.junit.Test;

import com.github.davidmoten.rx.Bytes;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.reactivex.netty.protocol.http.client.HttpClient;
import rx.Observable;
import rx.schedulers.Schedulers;

public class TestRxNetty {

	@Test
	public void testRxNettyIssue596() {
	Observable<ByteBuf> body = Bytes.from(new File("/Users/frank/git/reactive-servlet/rxjava-extras-0.8.0.8.jar"))
			.doOnNext(e->System.err.println("Read some data"))
			.doOnCompleted(()->System.err.println("All data complete"))
			.observeOn(Schedulers.io())
			.map(b->Unpooled.copiedBuffer(b));
		
	
	HttpClient.newClient("localhost",8080).createPost("/reactive-servlet/reactive")
	    .writeContentAndFlushOnEach(body)
	    .map(response->response.getContent().asObservable())
	    .concatMap(e->e)
		.observeOn(Schedulers.io())
	    .doOnNext(e->System.err.println("Something written back"))
	    .doOnCompleted(()->System.err.println("All data received"))
	    .toBlocking()
	    .subscribe();
	}
	
}
