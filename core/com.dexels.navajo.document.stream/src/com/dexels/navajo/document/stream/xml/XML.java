package com.dexels.navajo.document.stream.xml;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.xml.stream.XMLStreamException;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import com.dexels.navajo.document.stream.impl.SaxXmlFeeder;

import io.reactivex.Flowable;
import io.reactivex.FlowableOperator;
import io.reactivex.Observable;
import io.reactivex.ObservableOperator;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
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
	
	
	public static ObservableOperator<Observable<XMLEvent>, byte[]> parse() {
		return new ObservableOperator<Observable<XMLEvent>, byte[]>() {

			@Override
			public Observer<? super byte[]> apply(final Observer<? super Observable<XMLEvent>> child) throws Exception {

				return new Observer<byte[]>(){
					private final SaxXmlFeeder feeder = new SaxXmlFeeder();
					private Subscription subscription;

					@Override
					public void onComplete() {
						feeder.endOfInput();
						child.onComplete();
						System.err.println("Completing!!!!");
					}

					@Override
					public void onError(Throwable e) {
						e.printStackTrace();
						child.onError(e);
						subscription.cancel();
					}

					@Override
					public void onNext(byte[] buffer) {
						Observable<XMLEvent> fromIterable;
						try {
							List<XMLEvent> x = StreamSupport.stream(feeder.parse(buffer).spliterator(), false)
					                .collect(Collectors.toList());
							System.err.println("# of elements: "+x.size());
							for (XMLEvent xmlEvent : x) {
								System.err.println("Element: "+xmlEvent);
							}
							fromIterable = Observable
									.fromIterable(x)
									.doOnComplete(()->System.err.println("BLEB"))
//									.doOnNext(y->System.err.println("Requested: "+y))
									.doOnSubscribe(s->System.err.println("Subscr"));
							child.onNext(fromIterable);
						} catch (XMLStreamException e) {
							child.onError(e);
							return;
						}
					}


					@Override
					public void onSubscribe(Disposable d) {
						System.err.println("SUBSCRIBED!!!");
					}
					

				};
			}


			
		};
	}
}
