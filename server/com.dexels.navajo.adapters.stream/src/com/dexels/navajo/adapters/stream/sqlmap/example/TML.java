package com.dexels.navajo.adapters.stream.sqlmap.example;

import java.io.InputStream;
import java.util.Arrays;

import rx.Observable;

public class TML {
	
	public static Observable<byte[]> fromClassPath(String resource)  {
		return Observable.<byte[]>create(subscriber->{
			InputStream is = null;
			if(resource.startsWith("/")) {
				is = TML.class.getClassLoader().getResourceAsStream(resource.substring(1));
			} else {
				is = TML.class.getResourceAsStream(resource);
			}
			// could be more streaming, but I'm assuming the cp resources are nearby
			int nRead;
			byte[] data = new byte[16384];

			try {
				while ((nRead = is.read(data, 0, data.length)) != -1) {
				  subscriber.onNext(Arrays.copyOfRange(data,0,nRead));
				}
			} catch (Exception e) {
				subscriber.onError(e);
			}
		});
	}}
