package com.dexels.navajo.adapters.stream;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.adapters.stream.impl.CSVRowImpl;

import io.reactivex.ObservableOperator;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class CSV {

	private CSV() {
		// no instances
	}
	private static final Logger logger = LoggerFactory.getLogger(CSV.class);

    public static ObservableOperator<Row, String> rows(final String columnSeparator) {
		return new ObservableOperator<Row, String>() {

			@Override
			public Observer<? super String> apply(Observer<? super Row> child) throws Exception {
				return new Observer<String>() {
					final AtomicInteger counter = new AtomicInteger();
					final List<String> columns = new ArrayList<>();
					private Disposable disposable;

					@Override
					public void onComplete() {
						child.onComplete();
					}

					@Override
					public void onError(Throwable e) {
						logger.error("Error: ", e);
						child.onError(e);
					}

					@Override
					public void onNext(String line) {
						int current = counter.getAndIncrement();
						if (current == 0) {
							columns.addAll(CSV.columnNames(columnSeparator,line));
						} else {
							Row r = CSV.parseLine(columnSeparator,columns, line);
							if(!disposable.isDisposed()) {
								child.onNext(r);
							}
						}						
					}

					@Override
					public void onSubscribe(Disposable d) {
						disposable = d;
					}
				};
			}
		};
    }


	private static Row parseLine(String columnSeparator,List<String> columnNames,String line) {
		return new CSVRowImpl(columnNames, line.split(columnSeparator));
	}
	
	private static List<String> columnNames(String columnSeparator, String line) {
		return Stream.of(line.split(columnSeparator)).collect(Collectors.toList());
	}
}
