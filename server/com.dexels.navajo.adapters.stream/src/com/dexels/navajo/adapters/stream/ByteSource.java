package com.dexels.navajo.adapters.stream;

import java.io.InputStream;

import com.dexels.navajo.document.stream.NavajoStreamOperatorsNew;
import com.github.davidmoten.rx2.Bytes;

import hu.akarnokd.rxjava2.string.StringObservable;


public class ByteSource {
	private static InputStream loadFromAbsoluteClassPath(String resource) {
				return ByteSource.class.getClassLoader().getResourceAsStream(resource);
	}

	public static void main(String[] args) {
		String  a = "pim\r\npam\r\npet";
		String[] bb = a.split(System.getProperty("line.separator"));
		System.err.println("b = "+bb.length);
		Bytes.from(ByteSource.class.getClassLoader().getResourceAsStream("com/dexels/navajo/adapters/stream/sqlmap/example/example.csv"))
//		.doOnNext(aa->System.err.println("Bytes: "+new String(aa)))
		.lift(NavajoStreamOperatorsNew.decode("UTF-8"))
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
