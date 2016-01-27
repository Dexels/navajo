package com.dexels.navajo.adapters.stream.sqlmap.example;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import rx.Observable;

public class HTTP {
	private static final int BUFFERSIZE = 8192;

	private static Executor httpPool = Executors.newFixedThreadPool(5);
	
	public static Observable<byte[]> get(String getUrl) {
		return Observable.create(subscriber->{
			Runnable r = new Runnable(){

				@Override
				public void run() {
					try {
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
				}
			};
//			httpPool.execute(r);
			r.run();
		});
		
	}
}
