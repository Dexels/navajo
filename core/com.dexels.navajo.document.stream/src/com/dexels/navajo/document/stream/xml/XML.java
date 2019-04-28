package com.dexels.navajo.document.stream.xml;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.xml.stream.XMLStreamException;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import com.dexels.navajo.document.stream.impl.SaxXmlFeeder;
import com.dexels.navajo.document.stream.io.BaseFlowableOperator;

import io.reactivex.Flowable;
import io.reactivex.FlowableOperator;
import io.reactivex.internal.util.BackpressureHelper;

public class XML {
	
	public static FlowableOperator<Flowable<XMLEvent>, byte[]> parseFlowable(int queueSize) {
		return new BaseFlowableOperator<Flowable<XMLEvent>, byte[]>(queueSize) {
			
			@Override
			public Subscriber<? super byte[]> apply(Subscriber<? super Flowable<XMLEvent>> child) throws Exception {
				return new Subscriber<byte[]>(){
					private final SaxXmlFeeder feeder = new SaxXmlFeeder();

				    @Override
				    public void onError(Throwable t) {
				        error = t;
				        done = true;
				        drain(child);
				    }

				    @Override
				    public void onComplete() {
						feeder.endOfInput();
				        done = true;
				        drain(child);
				    }
					@Override
					public void onNext(byte[] buffer) {
						Flowable<XMLEvent> fromIterable;
						try {
							List<XMLEvent> x = StreamSupport.stream(feeder.parse(buffer).spliterator(), false).filter(elt->elt!=null)
					                .collect(Collectors.toList());
							fromIterable = Flowable.fromIterable(x);
							queue.offer(fromIterable);
							drain(child);
						} catch (XMLStreamException e) {
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
	
}
