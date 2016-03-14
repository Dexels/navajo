package com.dexels.navajo.adapters.stream;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.util.Arrays;

import rx.Observable;

public class HTTP {
	private static final int BUFFERSIZE = 8192;

	
	// TODO Rewrite using non blocking HTTP Client
	public static Observable<byte[]> get(String getUrl) {
		return Observable.create(subscriber->{
			try {
				System.err.println("GET THREAD: "+Thread.currentThread().getName());
				URL url = new URL(getUrl);
				URLConnection conn = url.openConnection();
				InputStream is = conn.getInputStream();
				int nRead;
				byte[] data = new byte[BUFFERSIZE];

				while ((nRead = is.read(data, 0, data.length)) != -1) {
					subscriber.onNext(Arrays.copyOfRange(data,0,nRead));
				}
				subscriber.onCompleted();
			} catch (Throwable e) {
				subscriber.onError(e);
			}
		});
	}
	
	
	public static Observable<ByteBuffer> getToByteBuffer(String getUrl) {
		return get(getUrl).map(b->ByteBuffer.wrap(b));
	}

	
}
