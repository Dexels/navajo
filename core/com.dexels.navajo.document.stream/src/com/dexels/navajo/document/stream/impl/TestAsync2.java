package com.dexels.navajo.document.stream.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.stream.XMLStreamException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.stream.NavajoStreamCollector;;

public class TestAsync2 {

	private final static Logger logger = LoggerFactory.getLogger(TestAsync2.class);

	public static void main(String[] args) throws XMLStreamException, IOException, InterruptedException {

		ObservableOutputStream oos = new ObservableOutputStream();
		ObservableXmlFeeder oxf = new ObservableXmlFeeder();
		ObservableNavajoParser onp = new ObservableNavajoParser();
		NavajoStreamCollector nsc = new NavajoStreamCollector();
		oos.getObservable()
//				 .doOnNext(b->System.err.println("Bytes: "+new String(b)))
				// .doOnTerminate(()->System.err.println("Terminate!"))
				.flatMap(bytes -> oxf.feed(bytes))
				// .doOnNext(b->System.err.println("XMLElement: "+b))
				.flatMap(xmlEvents -> onp.feed(xmlEvents)).doOnNext(b -> System.err.println("Navajo events: " + b))
				.flatMap(navajoEvents -> nsc.feed(navajoEvents))
				// .doOnNext(navajo->navajo.write(System.err))
				.subscribe(ar -> System.err.println("Print: " + ar));
		oos.getObservable().connect();
		streamBytes(TestAsync2.class.getClassLoader().getResourceAsStream("tml.xml"), 1, 0, oos);
	}

	private static void streamBytes(InputStream resourceAsStream, int bufferSize, int sleep, OutputStream oos) {
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
