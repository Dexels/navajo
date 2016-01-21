import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Header;
import com.dexels.navajo.document.stream.NavajoStreamSerializer;
import com.dexels.navajo.document.stream.ObservableOutputStream;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent.NavajoEventTypes;
import com.dexels.navajo.document.stream.xml.ObservableNavajoParser;
import com.dexels.navajo.document.stream.xml.ObservableXmlFeeder;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.observables.ConnectableObservable;
import rx.subjects.PublishSubject;

public class TestScriptingExample {
	
	
	private final static Logger logger = LoggerFactory.getLogger(TestScriptingExample.class);

	
	@Test
	public void testHeaderParsing() throws Exception {
		ObservableXmlFeeder oxf = new ObservableXmlFeeder();
		ObservableNavajoParser onp = new ObservableNavajoParser(Collections.emptyMap());
		ConnectableObservable<NavajoStreamEvent> published = Observable.<byte[]> create(
			subscriber -> {
				try(InputStream inputStream = getClass().getClassLoader().getResourceAsStream("tml_without_binary.xml")) {
					TestNavajoNonBlockingStream.streamBytes(inputStream, 1, new ObservableOutputStream(subscriber, 1));
				} catch (Exception e) {
					logger.error("Error: ", e);
				}
			})
				.flatMap(s -> oxf.feed(s))
				.flatMap(xml -> onp.feed(xml))
				.publish();
//	s	Ob
//		Observable<NavajoStreamEvent> shared = obs.refCount();
		OutputStream fos = new FileOutputStream("/Users/frank/output.xml");
		PublishSubject<NavajoStreamEvent> output = createOutput(fos);
		authorize(published.filter(e->e.type()==NavajoEventTypes.HEADER),published,output);
//		processInstance(published);
		Subscription p = published.connect();
		p.unsubscribe();
		int count = published.count().toBlocking().first();
		System.err.println("count: "+count);
//				obs.subscribe(e->System.err.println("YY: "+e));
		
	}


	private void authorize(Observable<NavajoStreamEvent> headerEvent, Observable<NavajoStreamEvent> publishedStream,Observer<NavajoStreamEvent> output) {
		new Authorizer(headerEvent,publishedStream,output);
	}


	private PublishSubject<NavajoStreamEvent> createOutput(OutputStream out) {
		PublishSubject<NavajoStreamEvent> subject = PublishSubject.<NavajoStreamEvent>create();
		NavajoStreamSerializer nss = new NavajoStreamSerializer();
		subject.flatMap(nsevent->nss.feed(nsevent)).subscribe(b->{try {
			out.write(b);
		} catch (Exception e) {
			logger.error("Error: ", e);
		}},t->logger.error("Error: ", t),()->{
			try {
				out.flush(); 
				out.close();
			} catch (Exception e) {
				logger.error("Error: ", e);
			}
		
		});
		
		return subject;
		
	}
}
