/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.document.stream.json;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.stream.io.BaseFlowableOperator;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.reactivex.Flowable;
import io.reactivex.FlowableOperator;
import io.reactivex.FlowableTransformer;
import io.reactivex.Observable;
import io.reactivex.ObservableOperator;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.util.BackpressureHelper;

public class JSON {

	
	private final static Logger logger = LoggerFactory.getLogger(JSON.class);
	private final static ObjectMapper objectMapper = new ObjectMapper();
	private JSON() {}

	public static JsonNode parseObjectNodes(byte[] input) throws IOException {
		return objectMapper.reader().readTree(new ByteArrayInputStream(input));
	}
	
	public static FlowableTransformer<byte[], byte[]> collectBytesToSingle() {
		return flow->flow.reduce(new ByteArrayOutputStream(), (baos,b)->{baos.write(b); return baos;})
				.map(b->b.toByteArray()).toFlowable()
				;
	}
	public static FlowableOperator<Flowable<JSONEvent>, byte[]> parseFlowable(int queueSize) {
		return new BaseFlowableOperator<Flowable<JSONEvent>, byte[]>(queueSize) {

			@Override
			public Subscriber<? super byte[]> apply(Subscriber<? super Flowable<JSONEvent>> child) throws Exception {
				return new Subscriber<byte[]>() {
					JSONToIterable parser = new JSONToIterable(new JsonFactory());

					@Override
					public void onComplete() {
						Flowable<JSONEvent> fromIterable = Flowable.fromIterable(parser.endOfInput());
						queue.offer(fromIterable);
				        done = true;
				        drain(child);						
					}

					@Override
					public void onError(Throwable t) {
				        error = t;
				        done = true;
				        drain(child);						
					}

					@Override
					public void onNext(byte[] data) {
						Flowable<JSONEvent> fromIterable;
						try {
							fromIterable = Flowable.fromIterable(parser.feed(data));
							queue.offer(fromIterable);
							drain(child);
						} catch (IOException e) {
							child.onError(e);
							return;
						}						
					}

					@Override
					public void onSubscribe(Subscription s) {
						subscription = s;
						child.onSubscribe(new Subscription() {
							
							@Override
							public void request(long n) {
						        BackpressureHelper.add(requested, n);
						        drain(child);
							}
							
							@Override
							public void cancel() {
						        cancelled = true;
						        s.cancel();								
							}
						});
						s.request(1);						
					}
				};
			}

		};
	}
	public static ObservableOperator<Observable<JSONEvent>, byte[]> parseObservable() {
		return new ObservableOperator<Observable<JSONEvent>, byte[]>() {

			@Override
			public Observer<? super byte[]> apply(Observer<? super Observable<JSONEvent>> in) throws Exception {
				return new Observer<byte[]>() {
					
					JSONToIterable parser = new JSONToIterable(new JsonFactory());
					private Disposable disposable;
					@Override
					public void onComplete() {
						if(disposable.isDisposed()) {
							return;
						}
//						Observable<JSONEvent> e = Observable.fromIterable(parser.endOfInput());
//						disposable.dispose();
//						in.onNext(e);
						in.onComplete();
					}

					@Override
					public void onError(Throwable t) {
						logger.error("Error: ", t);
						if(disposable.isDisposed()) {
							return;
						}
						in.onError(t);
					}

					@Override
					public void onNext(byte[] data) {
						if(disposable.isDisposed()) {
							return;
						}
						try {
							Observable<JSONEvent> e = Observable.fromIterable(parser.feed(data));
							in.onNext(e);
						} catch (IOException e) {
							in.onError(e);
						}
					}

					@Override
					public void onSubscribe(Disposable d) {
						this.disposable = d;
					}
				};
			}
		};
		
	}
}
