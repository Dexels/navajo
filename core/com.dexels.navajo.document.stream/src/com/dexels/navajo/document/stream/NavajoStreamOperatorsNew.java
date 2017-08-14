package com.dexels.navajo.document.stream;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent;
import com.dexels.navajo.document.stream.xml.ObservableNavajoParser;
import com.dexels.navajo.document.stream.xml.XMLEvent;
import com.dexels.navajo.document.types.Binary;

import io.reactivex.FlowableOperator;
import io.reactivex.FlowableSubscriber;
import io.reactivex.ObservableOperator;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.util.BackpressureHelper;

public class NavajoStreamOperatorsNew {

	
	private final static Logger logger = LoggerFactory.getLogger(NavajoStreamOperatorsNew.class);

	private NavajoStreamOperatorsNew() {}

	public static ObservableOperator<NavajoStreamEvent, Navajo> domStream() {
		return new ObservableOperator<NavajoStreamEvent,Navajo>() {

			@Override
			public Observer<? super Navajo> apply(Observer<? super NavajoStreamEvent> child) throws Exception {
					return new Op(child);
			}
				final class Op implements Observer<Navajo>, Disposable {
					final Observer<? super NavajoStreamEvent> child;

					Disposable parentSubscription;

					final AtomicBoolean isDisposed = new AtomicBoolean(false);
					public Op(Observer<? super NavajoStreamEvent> child) {
						this.child = child;
					}
					@Override
					public void onSubscribe(Disposable s) {
						this.parentSubscription = s;
						child.onSubscribe(this);
					}

					@Override
					public void onNext(Navajo n) {
						for (NavajoStreamEvent event : NavajoDomStreamer.processNavajo(n)) {
							child.onNext(event);
						}
					}

					@Override
					public void onError(Throwable e) {
						child.onError(e);
					}

					@Override
					public void onComplete() {
						child.onComplete();
					}

					@Override
					public void dispose() {
						isDisposed.set(true);
						parentSubscription.dispose();
						
					}
					@Override
					public boolean isDisposed() {
						return isDisposed.get();
					}
				}
			};
	}

	public static ObservableOperator<Navajo, NavajoStreamEvent> collect() {
		return new ObservableOperator<Navajo,NavajoStreamEvent>() {

			NavajoStreamCollector collector = new NavajoStreamCollector();
			@Override
			public Observer<? super NavajoStreamEvent> apply(Observer<? super Navajo> child) throws Exception {
				return new Op(child);
			}
			final class Op implements Observer<NavajoStreamEvent>, Disposable {
				final Observer<? super Navajo> child;

				Disposable parentSubscription;

				final AtomicBoolean isDisposed = new AtomicBoolean(false);
				public Op(Observer<? super Navajo> child) {
					this.child = child;
				}
				@Override
				public void onSubscribe(Disposable s) {
					this.parentSubscription = s;
					child.onSubscribe(this);
				}

				@Override
				public void onNext(NavajoStreamEvent v) {
					try {
						System.err.println("Event: "+v);
						Optional<Navajo> result = collector.processNavajoEvent(v);
						if(result.isPresent()) {
							System.err.println("Result: "+result.get());
							child.onNext(result.get());
						}
					} catch (IOException e) {
						onError(e);
						dispose();
					}
				}

				@Override
				public void onError(Throwable e) {
					child.onError(e);
				}

				@Override
				public void onComplete() {
					child.onComplete();
				}

				@Override
				public void dispose() {
					isDisposed.set(true);
					parentSubscription.dispose();
					
				}
				@Override
				public boolean isDisposed() {
					return isDisposed.get();
				}
			}
		};
	}
	
	public static FlowableOperator<Binary,String> gatherBinary() {
		return new FlowableOperator<Binary,String>() {


			@Override
			public Subscriber<? super String> apply(Subscriber<? super Binary> child) throws Exception {
				return new Op(child);
			}

			final class Op implements FlowableSubscriber<String>, Subscription {
				final Subscriber<? super Binary> child;
				Binary result = null;

				Subscription parentSubscription;

				public Op(Subscriber<? super Binary> child) {
					this.child = child;
				}

				@Override
				public void onSubscribe(Subscription s) {
					this.parentSubscription = s;
					child.onSubscribe(this);
					s.request(Long.MAX_VALUE);
				}

				@Override
				public void onNext(String s) {
					if(result==null) {
						result = createBinary();
					}
					try {
						result.pushContent(s);
					} catch (IOException e) {
						child.onError(e);
					}			
				}

				@Override
				public void onError(Throwable e) {
					child.onError(e);
				}

				@Override
				public void onComplete() {
					try {
						if(result!=null) {
							result.finishPushContent();
							child.onNext(result);
						}
						child.onComplete();
					} catch (IOException e) {
						child.onError(e);
					}
				}

				@Override
				public void cancel() {
					parentSubscription.cancel();
				}

				@Override
				public void request(long n) {
					parentSubscription.request(n);
				}
				
				private Binary createBinary() {
					Binary result = new Binary();
					try {
						result.startPushRead();
					} catch (IOException e1) {
						logger.error("Error: ", e1);
					}
					return result;
				}
			}
		};
	}

	public static FlowableOperator<NavajoStreamEvent, XMLEvent> parse() {
		return new FlowableOperator<NavajoStreamEvent, XMLEvent>() {
			@Override
			public Subscriber<? super XMLEvent> apply(Subscriber<? super NavajoStreamEvent> child) throws Exception {
				return new Op(child);
			}

			
			final class Op implements FlowableSubscriber<XMLEvent>, Subscription {
				final Subscriber<? super NavajoStreamEvent> child;
				final AtomicLong requested = new AtomicLong();
				long emitted = 0;

				ObservableNavajoParser parser = null;

				Subscription parentSubscription;

				public Op(Subscriber<? super NavajoStreamEvent> child) {
					this.child = child;
					parser = new ObservableNavajoParser(child);
				}

				@Override
				public void onSubscribe(Subscription s) {
					this.parentSubscription = s;
					child.onSubscribe(this);
				}

				@Override
				public void onNext(XMLEvent xmlEvent) {
					int emitted = parser.parseXmlEvent(xmlEvent);
					if(emitted == 0) {
						parentSubscription.request(1);
					}
				}

				@Override
				public void onError(Throwable e) {
					child.onError(e);
				}

				@Override
				public void onComplete() {
					child.onComplete();
				}

				@Override
				public void cancel() {
					parentSubscription.cancel();
				}
				@Override
				public void request(long n) {
					BackpressureHelper.add(requested, n);
					if(n==Long.MAX_VALUE) {
						parentSubscription.request(Long.MAX_VALUE);
					}
					requestIfNeeded();
				}
				
				public long amountToRequest(long emitted,long requested) {
//					System.err.println("Request: emitted: "+emitted+" requested: "+requested);
					if(requested == Long.MAX_VALUE) {
						return 0;
					}
					if(requested > emitted) {
						return requested - emitted;
					}
					return 0;
				}
				
				private void requestIfNeeded() {
					// locking incorrect I think
					long req = amountToRequest(emitted, requested.get());
					if(req>0) {
						parentSubscription.request(req);
					}
				}
			}
		};
	}
	
	public static ObservableOperator<byte[], NavajoStreamEvent> serializeObservable() {
		return new ObservableOperator<byte[], NavajoStreamEvent>() {
			private final NavajoStreamSerializer collector = new NavajoStreamSerializer();

			@Override
			public Observer<? super NavajoStreamEvent> apply(Observer<? super byte[]> child) throws Exception {
				return new Op(child);
			}
			
			final class Op implements Observer<NavajoStreamEvent>, Disposable {

				Disposable parentDisposable;
				final Observer<? super byte[]> child;
				
				public Op(Observer<? super byte[]> child) {
					this.child = child;
				}

				@Override
				public void onComplete() {
					child.onComplete();
				}

				@Override
				public void onError(Throwable e) {
					child.onError(e);
				}

				@Override
				public void onNext(NavajoStreamEvent event) {
					byte[] b = collector.serialize(event);
					if(b.length==0) {
					} else {
						child.onNext(b);
					}				
				}
				

				@Override
				public void onSubscribe(Disposable d) {
					this.parentDisposable = d;
					child.onSubscribe(this);
				}

				@Override
				public void dispose() {
					parentDisposable.dispose();
				}

				@Override
				public boolean isDisposed() {
					return false;
				}
			}
		};
	}
	public static FlowableOperator<byte[], NavajoStreamEvent> serialize() {
		return new FlowableOperator<byte[],NavajoStreamEvent>() {
			private final NavajoStreamSerializer collector = new NavajoStreamSerializer();


			@Override
			public Subscriber<? super NavajoStreamEvent> apply(Subscriber<? super byte[]> child) throws Exception {
				return new Op(child);
			}

			
			final class Op implements FlowableSubscriber<NavajoStreamEvent>, Subscription {
				final Subscriber<? super byte[]> child;
				final AtomicLong requested = new AtomicLong();
				long emitted = 0;

				Subscription parentSubscription;

				public Op(Subscriber<? super byte[]> child) {
					this.child = child;
				}

				@Override
				public void onSubscribe(Subscription s) {
					this.parentSubscription = s;
					child.onSubscribe(this);
				}

				@Override
				public void onNext(NavajoStreamEvent v) {
//					child.onNext(v);
					byte[] b = collector.serialize(v);
					if(b.length==0) {
						parentSubscription.request(1);
					} else {
						child.onNext(b);
						emitted++;
					}
				}

				@Override
				public void onError(Throwable e) {
					child.onError(e);
				}

				@Override
				public void onComplete() {
					child.onComplete();
				}

				@Override
				public void cancel() {
					parentSubscription.cancel();
				}

				@Override
				public void request(long n) {
					BackpressureHelper.add(requested, n);
					if(n==Long.MAX_VALUE) {
						parentSubscription.request(Long.MAX_VALUE);
					}
					requestIfNeeded();
				}
				
				public long amountToRequest(long emitted,long requested) {
//					System.err.println("Request: emitted: "+emitted+" requested: "+requested);
					if(requested == Long.MAX_VALUE) {
						return 0;
					}
					if(requested > emitted) {
						return requested - emitted;
					}
					return 0;
				}
				
				private void requestIfNeeded() {
					// locking incorrect I think
					long req = amountToRequest(emitted, requested.get());
					if(req>0) {
						parentSubscription.request(req);
					}
				}
			}
		};
	}

	
	public static FlowableOperator<Navajo, NavajoStreamEvent> identity() {
		return new FlowableOperator<Navajo,NavajoStreamEvent>() {


			@Override
			public Subscriber<? super NavajoStreamEvent> apply(Subscriber<? super Navajo> child) throws Exception {
				return new Op(child);
			}

			
			final class Op implements FlowableSubscriber<NavajoStreamEvent>, Subscription {
				final Subscriber<? super Navajo> child;

				Subscription parentSubscription;

				public Op(Subscriber<? super Navajo> child) {
					this.child = child;
				}

				@Override
				public void onSubscribe(Subscription s) {
					this.parentSubscription = s;
					child.onSubscribe(this);
				}

				@Override
				public void onNext(NavajoStreamEvent v) {
//					child.onNext(v);
				}

				@Override
				public void onError(Throwable e) {
					child.onError(e);
				}

				@Override
				public void onComplete() {
					child.onComplete();
				}

				@Override
				public void cancel() {
					parentSubscription.cancel();
				}

				@Override
				public void request(long n) {
					parentSubscription.request(n);
				}
			}
		};
	}
}
