package com.dexels.navajo.document.stream.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;

import javax.xml.stream.XMLStreamException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.stream.NavajoDomStreamer;
import com.dexels.navajo.document.stream.NavajoStreamCollector;
import com.dexels.navajo.document.stream.NavajoStreamSerializer;
import com.dexels.navajo.document.stream.ObservableOutputStream;
import com.dexels.navajo.document.stream.xml.ObservableNavajoParser;
import com.dexels.navajo.document.stream.xml.ObservableXmlFeeder;

import rx.Observable;
import rx.Subscriber;;

public class TestAsync2 {

	private final static Logger logger = LoggerFactory.getLogger(TestAsync2.class);
	private static Subscriber<? super byte[]> sub;

	public static void main(String[] args) throws XMLStreamException, IOException, InterruptedException {
		Observable.<String>just("aap", "noot","mies").doOnEach(a->System.err.println(a.getKind()+" -> "+a.toString()))
		.map(s->s+"ba").flatMap(f-> Observable.from(f.split("o"))).doOnCompleted(()->System.err.println("complete"))
			.subscribe(element->System.err.println("result: "+element),e->e.printStackTrace(),()->System.err.println("done!"));
	}
	public static void main2(String[] args) throws XMLStreamException, IOException, InterruptedException {

//		ObservableOutputStream oos = null;
		
		ObservableXmlFeeder oxf = new ObservableXmlFeeder();
		ObservableNavajoParser onp = new ObservableNavajoParser(Collections.emptyMap());
		NavajoStreamCollector nsc = new NavajoStreamCollector();
		NavajoDomStreamer domStreamer = new NavajoDomStreamer();
		NavajoStreamSerializer serializer = new NavajoStreamSerializer();
		Observable.<byte[]>create(subscribe->{
			sub = subscribe;
		})
			.flatMap(bytes -> oxf.feed(bytes))
			// .doOnNext(b->System.err.println("XMLElement: "+b))
			.flatMap(xmlEvents -> onp.feed(xmlEvents))
//			.doOnNext(b -> System.err.println("Navajo events: " + b))
			.flatMap(navajoEvents->nsc.feed(navajoEvents))
			.flatMap(navajo->domStreamer.feed(navajo))
			.flatMap(navajoEvents->serializer.feed(navajoEvents))
//			.doOnNext(b -> System.err.println("Bytes: " + new String(b)))
				// .doOnNext(navajo->navajo.write(System.err))
				.subscribe(ar -> new String(ar));
//		oos.getObservable();
			streamBytes(TestAsync2.class.getClassLoader().getResourceAsStream("tml.xml"), 10, 0, sub);

	}
	
	private static void streamBytes(InputStream resourceAsStream, int bufferSize, int sleep, Subscriber<? super byte[]> sub) {
		ObservableOutputStream oos = new ObservableOutputStream(sub,5);
		byte[] buffer = new byte[bufferSize];
		new Thread() {

			@Override
			public void run() {
				int read = 0;
				do {
					try {
						read = resourceAsStream.read(buffer, 0, buffer.length);
						if (sleep > 0) {
							Thread.sleep(sleep);
						}
						if (read != -1) {
							// System.err.println("Input: "+new
							// String(Arrays.copyOfRange(buffer, 0, read)));
							oos.write(buffer, 0, read);
						} else {
							oos.close();
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				} while (read != -1);
				try {
					oos.close();
				} catch (IOException e) {
					logger.error("Error: ", e);
				}
			}
		}.start();

	}

}
