package com.dexels.navajo.adapters.stream.sqlmap.example;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.dexels.navajo.adapters.stream.sqlmap.example.impl.CSVRowImpl;
import com.dexels.navajo.document.stream.api.Msg;

import rx.Observable;
import rx.Observable.Operator;
import rx.Subscriber;
import rx.functions.Func1;

public class CSV {

    public static Operator<Row, String> rows(final String columnSeparator) {
		return new Operator<Row,String>(){

			@Override
			public Subscriber<? super String> call(Subscriber<? super Row> in) {
				
				final AtomicInteger counter = new AtomicInteger();
				final List<String> columns = new ArrayList<>();

				return new Subscriber<String>() {

					@Override
					public void onCompleted() {
						if(!in.isUnsubscribed()) {
							in.onCompleted();
						}
					}

					@Override
					public void onError(Throwable e) {
						if(!in.isUnsubscribed()) {
							in.onError(e);
							
						}
					}

					@Override
					public void onNext(String line) {
						
						int current = counter.getAndIncrement();
						if (current == 0) {
							columns.addAll(CSV.columnNames(columnSeparator,line));
						} else {
							Row r = CSV.parseLine(columnSeparator,columns, line);
							in.onNext(r);
						}
					}
				};
			}};
    }
	
    public static Observable<Row> fromClassPathWithRow(String resource, Charset charset, String lineSeparator, String columnSeparator) {
    	return  Bytes.fromAbsoluteClassPath(resource)
            	.lift(StringObservable.decode(charset))
            	.lift(StringObservable.split(lineSeparator))
            	.lift(CSV.rows(columnSeparator));
    }
    
    
    public static Observable<Msg> fromClassPath(String resource, Charset charset, String lineSeparator, String columnSeparator) {
    	return fromClassPathWithRow(resource, charset, lineSeparator, columnSeparator).map(r->r.toElement());
    }
	private static Row parseLine(String columnSeparator,List<String> columnNames,String line) {
		return new CSVRowImpl(columnNames, line.split(columnSeparator));
	}
	
	private static List<String> columnNames(String columnSeparator, String line) {
		return Stream.of(line.split(columnSeparator)).collect(Collectors.toList());
	}
}
