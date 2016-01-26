package com.dexels.navajo.adapters.stream.sqlmap.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.dexels.navajo.adapters.stream.sqlmap.example.impl.CSVRowImpl;
import com.dexels.navajo.listeners.stream.core.OutputSubscriber;

import rx.Observable;
import rx.functions.Action1;

public class CSV {
	public static void syncFromClassPath(String resource, OutputSubscriber output, Action1<Row> onRow)  {
		try {
			try(BufferedReader br = loadFromClassPath(resource)) {
				List<String> columnNames = columnNames(br.readLine());
				br.lines().map(line->parseLine(columnNames,line)).forEach(
					row->onRow.call(row)
				);
			}
		} catch (Exception e) {
			output.onError(e);
		}
	}
	
	public static Observable<Row> fromClassPath(String resource)  {
		return Observable.<Row>create(subscriber->{
			try {
				try(BufferedReader br = loadFromClassPath(resource)) {
					List<String> columnNames = columnNames(br.readLine());
					br.lines().map(line->parseLine(columnNames,line)).forEach(
							row->{
								if(!subscriber.isUnsubscribed()) {
									subscriber.onNext(row);
								}
								
							}
					);
					subscriber.onCompleted();
				}
			} catch (Exception e) {
				subscriber.onError(e);
			} finally {
				
			}
			
		});
	}
	
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
	
	public static void main(String[] args) throws UnsupportedEncodingException, IOException {
		CSV.syncFromClassPath("example.csv",null,e->{
			System.err.println("Name: "+e.get("permalink"));
		});
	}
}
