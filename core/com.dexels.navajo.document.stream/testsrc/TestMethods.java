import java.io.IOException;
import java.io.InputStream;

import org.junit.Assert;
import org.junit.Test;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.stream.StreamDocument;

import io.reactivex.Single;

public class TestMethods {

	@Test
	public void testMethods() throws IOException {
		try (InputStream is = TestMethods.class.getResourceAsStream("tml_with_method.xml")) {
			Navajo n = NavajoFactory.getInstance().createNavajo(is);
//			List<NavajoStreamEvent> l =  NavajoDomStreamer.feed(n).toList().blockingGet();
			Navajo nav =  Single.just(n)
			.compose(StreamDocument.domStreamTransformer())
			.toObservable()
			.concatMap(e->e)
//			.doOnNext(e->System.err.println("Event: "+e))
//			.toFlowable(BackpressureStrategy.BUFFER)
			.compose(StreamDocument.domStreamCollector())
			.firstElement().blockingGet();
			
			nav.write(System.err);
			com.dexels.navajo.document.Method m = nav.getMethod("somemethod");
			Assert.assertNotNull(m);
			
		}
	}
}
