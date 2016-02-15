package com.dexels.navajo.adapters.stream.sqlmap.example;

import java.io.InputStream;
import java.nio.ByteBuffer;

import com.dexels.navajo.document.stream.io.ObservableStreams;

import rx.Observable;

public class Bytes {
	public static Observable<ByteBuffer> fromAbsoluteClassPathBuffer(String resource) {
			return ObservableStreams.streamInputStreamWithBufferSize(loadFromAbsoluteClassPath(resource), 1024);
	}
	
	private static InputStream loadFromAbsoluteClassPath(String resource) {
				return Bytes.class.getClassLoader().getResourceAsStream(resource);
	}
	
	public static Observable<byte[]> fromAbsoluteClassPath(String resource) {
		return ObservableStreams.streamInputStreamWithBufferSize(loadFromAbsoluteClassPath(resource), 1024).map(b->b.array());
}

	public static void main(String[] args) {
		String  a = "pim\r\npam\r\npet";
		String[] bb = a.split(System.getProperty("line.separator"));
		System.err.println("b = "+bb.length);
		Bytes.fromAbsoluteClassPath("com/dexels/navajo/adapters/stream/sqlmap/example/example.csv")
//		.doOnNext(aa->System.err.println("Bytes: "+new String(aa)))
		.lift(StringObservable.decode("UTF-8"))
//		.lift(StringObservable.split(System.getProperty("line.separator")))
		.lift(StringObservable.split("[\r]+"))
		.lift(CSV.rows(","))
		.map(r->r.get("city"))
		.cast(String.class)
		.toBlocking()
		.subscribe(s->System.err.println("Line: "+s))
		;
	System.err.println(">>> "+System.getProperty("line.separator"));
	}
}
