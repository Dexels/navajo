package com.dexels.navajo.document.stream.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;

import javax.xml.stream.XMLStreamException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.stream.NavajoDomStreamer;
import com.dexels.navajo.document.stream.NavajoStreamCollector;
import com.dexels.navajo.document.stream.NavajoStreamSerializer;
import com.dexels.navajo.document.stream.ObservableOutputStream;
import com.dexels.navajo.document.stream.xml.ObservableNavajoParser;
import com.dexels.navajo.document.stream.xml.ObservableXmlFeeder;

import rx.Observable;
import rx.Subscriber;;

public class TestAsync3 {

	private final static Logger logger = LoggerFactory.getLogger(TestAsync3.class);
	private static Subscriber<? super Navajo> sub;
	public static void main(String[] args) throws XMLStreamException, IOException, InterruptedException {
		Navajo baseTml = NavajoFactory.getInstance().createNavajo(TestAsync3.class.getClassLoader().getResourceAsStream("tml.xml"));
		
		NavajoStreamCollector nsc = new NavajoStreamCollector();
		NavajoDomStreamer domStreamer = new NavajoDomStreamer();
		Observable.<Navajo>create(subscribe->{
			sub = subscribe;
		})
			.flatMap(navajo -> domStreamer.feed(navajo))
			.flatMap(navajoEvent-> nsc.feed(navajoEvent))
			.subscribe(ar -> { System.err.println(ar); ar.write(System.err);
//				if(ar.getBody()!=null && ar.getBody() instanceof Message) {
//					Message m = (Message)ar.getBody();
//					m.write(System.err);
//				}
			});
//		oos.getObservable();
//			streamBytes(TestAsync3.class.getClassLoader().getResourceAsStream("tml.xml"), 10, 0, sub);
		sub.onNext(baseTml);
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
