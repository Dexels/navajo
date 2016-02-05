package com.dexels.navajo.adapters.stream.sqlmap.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.adapters.stream.sqlmap.example.impl.CSVRowImpl;

import rx.Observable;

public class CSV {

	
	private final static Logger logger = LoggerFactory.getLogger(CSV.class);

	
	public static Observable<Row> fromClassPath(String resource) {
		return Observable.<Row> defer(() -> Observable.from(new Iterable<Row>() {

			
			@Override
			public Iterator<Row> iterator() {
				
				try {
					final BufferedReader br = loadFromClassPath(resource);
					final List<String> columns = CSV.columnNames(br.readLine());

				return new Iterator<Row>() {
//					private static List<String> columnNames(String line) {
//						return Stream.of(line.split(",")).collect(Collectors.toList());
//					}
					private String next;
					@Override
					public boolean hasNext() {
						try {
							next = br.readLine();
							return next!=null;
						} catch (IOException e) {
							logger.error("Error: ", e);
							return false;
						}
					}

					@Override
					public Row next() {
						return CSV.parseLine(columns, this.next);
					}
				};
				} catch (IOException e) {
					logger.error("Error: ", e);
				}
				return null;

			}
		})

		);
	}
//	public static Observable<Row> fromClassPath(String resource)  {
//		return Observable.<Row>create(subscriber->{
//			try {
//				try(BufferedReader br = loadFromClassPath(resource)) {
//					List<String> columnNames = columnNames(br.readLine());
//					br.lines().map(line->parseLine(columnNames,line)).forEach(
//						row->{
//							if(!subscriber.isUnsubscribed()) {
//								subscriber.onNext(row);
//							}
//							
//						}
//					);
//					subscriber.onCompleted();
//				}
//			} catch (Exception e) {
//				subscriber.onError(e);
//			}
//		});
//	}
	
	private static Row parseLine(List<String> columnNames,String line) {
		return new CSVRowImpl(columnNames, line.split(","));
	}
	
	private static List<String> columnNames(String line) {
		return Stream.of(line.split(",")).collect(Collectors.toList());
	}
	
	private static BufferedReader loadFromClassPath(String resource) throws UnsupportedEncodingException {
		return new BufferedReader(
				new InputStreamReader(
						resource.startsWith("/")?
								CSV.class.getClassLoader().getResourceAsStream(resource.substring(1)) : 
								CSV.class.getResourceAsStream(resource), "UTF-8"
				));
	}
}
