package com.dexels.navajo.adapters.stream.sqlmap.example;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;

import com.dexels.navajo.document.stream.events.NavajoStreamEvent;
import com.dexels.navajo.document.stream.xml.ObservableNavajoParser;
import com.dexels.navajo.document.stream.xml.ObservableXmlFeeder;

import rx.Observable;

public class TML {
	
	private static final int BUFFERSIZE = 8192;

	public static Observable<NavajoStreamEvent> fromClassPath(String resource)  {
		ObservableXmlFeeder oxf = new ObservableXmlFeeder();
		ObservableNavajoParser onp = new ObservableNavajoParser(Collections.emptyMap());


		
		return Observable.<byte[]>create(subscriber->{
			InputStream is = null;
			if(resource.startsWith("/")) {
				is = TML.class.getClassLoader().getResourceAsStream(resource.substring(1));
			} else {
				is = TML.class.getResourceAsStream(resource);
			}
			// could be more streaming, but I'm assuming the cp resources are nearby
			int nRead;
			byte[] data = new byte[BUFFERSIZE];

			try {
				while ((nRead = is.read(data, 0, data.length)) != -1) {
				  subscriber.onNext(Arrays.copyOfRange(data,0,nRead));
				}
			} catch (Exception e) {
				subscriber.onError(e);
			}
		})		.flatMap(f->oxf.feed(f))
				.flatMap(f->onp.feed(f))

		;
	}}
