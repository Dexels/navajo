import java.io.IOException;
import java.io.InputStream;

import org.junit.Assert;
import org.junit.Test;

import com.dexels.immutable.factory.ImmutableFactory;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.stream.StreamDocument;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Observable;

public class TestGatherEvents {

	@Test
	public void testMethods() throws IOException {
		try (InputStream is = TestGatherEvents.class.getResourceAsStream("tml_events.xml")) {
			Navajo n = NavajoFactory.getInstance().createNavajo(is);
//			List<NavajoStreamEvent> l =  NavajoDomStreamer.feed(n).toList().blockingGet();
			long l =  Observable.just(n)
					.lift(StreamDocument.domStream())
					.toFlowable(BackpressureStrategy.BUFFER)
					.lift(StreamDocument.collectEventsToImmutable())
					.doOnNext(e->ImmutableFactory.createParser().describe(e))
					.count()
					.blockingGet();
			
			Assert.assertEquals(10, l);
		}
	}
}
