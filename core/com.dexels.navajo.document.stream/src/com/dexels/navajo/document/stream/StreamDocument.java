package com.dexels.navajo.document.stream;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Stack;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.stream.api.Msg;
import com.dexels.navajo.document.stream.api.NavajoHead;
import com.dexels.navajo.document.stream.api.Prop;
import com.dexels.navajo.document.stream.base.BackpressureAdministrator;
import com.dexels.navajo.document.stream.events.Events;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent.NavajoEventTypes;
import com.dexels.navajo.document.stream.xml.BaseFlowableOperator;
import com.dexels.navajo.document.stream.xml.ObservableNavajoParser;
import com.dexels.navajo.document.stream.xml.XMLEvent;
import com.dexels.navajo.document.types.Binary;
import com.dexels.replication.api.ReplicationMessage;

import io.reactivex.Flowable;
import io.reactivex.FlowableOperator;
import io.reactivex.FlowableSubscriber;
import io.reactivex.FlowableTransformer;
import io.reactivex.ObservableOperator;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import rx.Single;
import rx.functions.Func1;

public class StreamDocument {

	
	private final static Logger logger = LoggerFactory.getLogger(StreamDocument.class);

	private StreamDocument() {}

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
						Optional<Navajo> result = collector.processNavajoEvent(v);
						if(result.isPresent()) {
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
	
	
	public static FlowableOperator<Navajo, NavajoStreamEvent> collectFlowable() {
		return new FlowableOperator<Navajo,NavajoStreamEvent>() {

			NavajoStreamCollector collector = new NavajoStreamCollector();
			@Override
			public Subscriber<? super NavajoStreamEvent> apply(Subscriber<? super Navajo> child) throws Exception {
				return new Op(child);
			}
			final class Op implements Subscriber<NavajoStreamEvent>, Subscription {
				final Subscriber<? super Navajo> child;

				Subscription parentSubscription;

				final AtomicBoolean isCancelled = new AtomicBoolean(false);
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
					try {
						Optional<Navajo> result = collector.processNavajoEvent(v);
						if(result.isPresent()) {
							child.onNext(result.get());
						}
					} catch (IOException e) {
						onError(e);
						cancel();
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
					isCancelled.set(true);
					parentSubscription.cancel();
				}
				@Override
				public void request(long n) {
					parentSubscription.request(Long.MAX_VALUE);
				}
			}
		};
	}	
	
	public static FlowableOperator<Binary,String> gatherBinary() {
		return new BaseFlowableOperator<Binary,String>(1) {
		

			@Override
			public Subscriber<? super String> apply(Subscriber<? super Binary> child) throws Exception {
				return new Subscriber<String>(){

					Binary result = null;

					@Override
					public void onComplete() {
						if(result!=null) {
							try {
								result.finishPushContent();
								operatorNext("", r->result, child);
							} catch (IOException e) {
								operatorError(e, child);
							}
//							child.onNext(result);
						}
//						child.onComplete();
						operatorComplete(child);
					}

					@Override
					public void onError(Throwable t) {
						operatorError(t, child);
					}

					@Override
					public void onNext(String e) {
						try {
							result.pushContent(e);
							operatorRequest(1);
						} catch (IOException ex) {
							operatorError(ex, child);
						}	
					}

					@Override
					public void onSubscribe(Subscription s) {
						operatorSubscribe(s, child);
						result = createBinary();
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
				};
			}
		};
	}

	public static FlowableOperator<Binary,String> gatherBinaryOld() {
		return new FlowableOperator<Binary,String>() {


			@Override
			public Subscriber<? super String> apply(Subscriber<? super Binary> child) throws Exception {
				return new Op(child);
			}

			final class Op implements FlowableSubscriber<String> {
				final Subscriber<? super Binary> child;
				Binary result = null;
				private BackpressureAdministrator backpressureAdmin;

				public Op(Subscriber<? super Binary> child) {
					this.child = child;
				}

				@Override
				public void onSubscribe(Subscription s) {
			        this.backpressureAdmin = new BackpressureAdministrator("gatherBinary",Long.MAX_VALUE, s);
					child.onSubscribe(backpressureAdmin);
					backpressureAdmin.initialize();				}

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

	public static FlowableOperator<Flowable<NavajoStreamEvent>, XMLEvent> parse() {
		return new BaseFlowableOperator<Flowable<NavajoStreamEvent>, XMLEvent>(10) {

			@Override
			public Subscriber<? super XMLEvent> apply(Subscriber<? super Flowable<NavajoStreamEvent>> child) throws Exception {
				return new Subscriber<XMLEvent>() {
					ObservableNavajoParser parser = new ObservableNavajoParser();


					@Override
					public void onComplete() {
						operatorComplete(child);
					}

					@Override
					public void onError(Throwable t) {
						operatorError(t, child);
					}

					@Override
					public void onNext(XMLEvent xmlEvent) {
						operatorNext(xmlEvent, x->
							Flowable.fromIterable(parser.parseXmlEvent(x))
						, child);
						
					}

					@Override
					public void onSubscribe(Subscription s) {
						operatorSubscribe(s, child);
						
					}
				};
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
	
	
//	public static FlowableOperator<byte[], NavajoStreamEvent> serialize() {
//		return new BaseFlowableOperator<byte[], NavajoStreamEvent>(100) {
//			@Override
//			public Subscriber<? super NavajoStreamEvent> apply(Subscriber<? super byte[]> child) throws Exception {
//				return new Subscriber<NavajoStreamEvent>() {
//					
//					Subscription subscription = null;
//					private final NavajoStreamSerializer serializer = new NavajoStreamSerializer();
//
//					@Override
//					public void onComplete() {
//						child.onComplete();
//					}
///
//					@Override
//					public void onError(Throwable e) {
//						child.onError(e);
//					}
//
//					@Override
//					public void onNext(NavajoStreamEvent event) {
//						byte[] serialized = serializer.serialize(event);
//						System.err.println("Propagating bytes with size: "+serialized.length);
//						child.onNext(serialized);
//						subscription.request(1);
//					}
//
//					@Override
//					public void onSubscribe(Subscription s) {
//						subscription = s;
//						s.request(1);
//					}};
//			}
//		};
//	}

	public static FlowableOperator<byte[], NavajoStreamEvent> serialize() {
		return new BaseFlowableOperator<byte[],NavajoStreamEvent>(32000) {
			private final NavajoStreamSerializer collector = new NavajoStreamSerializer();

			@Override
			public Subscriber<? super NavajoStreamEvent> apply(Subscriber<? super byte[]> child) throws Exception {
				return new Subscriber<NavajoStreamEvent>() {

					@Override
					public void onComplete() {
						operatorComplete(child);
					}

					@Override
					public void onError(Throwable t) {
						operatorError(t, child);
					}

					@Override
					public void onNext(NavajoStreamEvent v) {
						operatorNext(v, event->{
							byte[] b = collector.serialize(event);
							if(b.length==0) {
								return null;
							} else {
								return b;
							}
						}, child);
					}

					@Override
					public void onSubscribe(Subscription s) {
						operatorSubscribe(s, child);
					}
				};
				
			}
		};
	}

	public static FlowableOperator<NavajoStreamEvent, NavajoStreamEvent> filterMessageIgnore() {
		return new BaseFlowableOperator<NavajoStreamEvent, NavajoStreamEvent>(10) {

			private final Stack<Boolean> ignoreLevel = new Stack<Boolean>();

			@Override
			public Subscriber<? super NavajoStreamEvent> apply(Subscriber<? super NavajoStreamEvent> child)
					throws Exception {
				// TODO Auto-generated method stub
				return new Subscriber<NavajoStreamEvent>() {

					@Override
					public void onComplete() {
						operatorComplete(child);
					}

					@Override
					public void onError(Throwable t) {
						operatorError(t, child);
					}

					@Override
					public void onNext(NavajoStreamEvent event) {
						switch(event.type()) {
							case ARRAY_ELEMENT_STARTED:
							case ARRAY_STARTED:
							case MESSAGE_STARTED:
							case MESSAGE_DEFINITION_STARTED:
								String mode = (String) event.attribute("mode");
								boolean isIgnore = "ignore".equals(mode);
								ignoreLevel.push(isIgnore);
								if(!ignoreLevel.contains(true)) {
									operatorNext(event, e->e, child);
									return;
								}
								break;
								
							case ARRAY_DONE:
							case ARRAY_ELEMENT:
							case MESSAGE_DEFINITION:
							case MESSAGE:

								if(!ignoreLevel.contains(true)) {
									operatorNext(event, e->e, child);
//									child.onNext(event);
									ignoreLevel.pop();
									return;
								}
								ignoreLevel.pop();
								break;
								
							case NAVAJO_DONE:
							case NAVAJO_STARTED:
							case BINARY_STARTED:
							case BINARY_CONTENT:
							case BINARY_DONE:
								operatorNext(event, e->e, child);
								return;
								
							default:
								throw new UnsupportedOperationException("Unknown event found in NAVADOC: "+event.type());

						}
						operatorRequest(1);
						
					}

					@Override
					public void onSubscribe(Subscription s) {
						operatorSubscribe(s, child);
					}
						
				};
			}
		};
		}

	public static FlowableOperator<NavajoStreamEvent,NavajoStreamEvent> setPropertyValue(final String messagePath, String property, Object value) {
		return messageWithPath(messagePath, msg->msg.withValue(property, value),false);
	}
	
	public static FlowableOperator<NavajoStreamEvent,NavajoStreamEvent> messageWithPath(final String messagePath) {
		return messageWithPath(messagePath,m->m,true);
	}

	
	public static FlowableOperator<String,NavajoStreamEvent> observeBinary(final String path) {
		return new BaseFlowableOperator<String,NavajoStreamEvent>(1) {

			@Override
			public Subscriber<? super NavajoStreamEvent> apply(Subscriber<? super String> child) throws Exception {
				return new Subscriber<NavajoStreamEvent>() {
					private final Stack<String> pathStack = new Stack<>();

					@Override
					public void onComplete() {
						operatorComplete(child);
					}

					@Override
					public void onError(Throwable t) {
						operatorError(t, child);
					}

					@Override
					public void onNext(NavajoStreamEvent event) {
						switch(event.type()) {
							case MESSAGE_STARTED:
								pathStack.push(event.path());
								break;
							case ARRAY_ELEMENT_STARTED:
								break;
							case MESSAGE:
								pathStack.pop();
								break;
							case ARRAY_ELEMENT:
								break;
								// TODO Support these?
							case ARRAY_STARTED:
								pathStack.push(event.path());
								break;
							case ARRAY_DONE:
								pathStack.pop();
								break;
							case BINARY_STARTED:
								pathStack.push(event.path());
								break;
							case BINARY_DONE:
								if(matches(path,pathStack)) {
									child.onComplete();
								}
								pathStack.pop();
								break;
							case BINARY_CONTENT:
								if(matches(path,pathStack)) {
									operatorNext(event, f->(String)event.body(), child);
									return;
								}
							default:
								break;
							}
						operatorRequest(1);
//						backpressureAdmin.consumedEvent();
//						backpressureAdmin.requestIfNeeded();						
					}

					@Override
					public void onSubscribe(Subscription s) {
						operatorSubscribe(s, child);
					}
					
					private boolean matches(String path, Stack<String> pathStack) {
						String joined = String.join("/", pathStack);
						return path.equals(joined);
					}
				};
			}
		};
	}
	
	public static FlowableOperator<String,NavajoStreamEvent> observeBinaryold(final String path) {
		return new FlowableOperator<String,NavajoStreamEvent>(){
			@Override
			public Subscriber<? super NavajoStreamEvent> apply(Subscriber<? super String> child) {
				return new Subscriber<NavajoStreamEvent>() {

					private final Stack<String> pathStack = new Stack<>();
					private BackpressureAdministrator backpressureAdmin;
					
					@Override
					public void onSubscribe(Subscription s) {
				        this.backpressureAdmin = new BackpressureAdministrator("observeBinary",Long.MAX_VALUE, s);
						child.onSubscribe(backpressureAdmin);
						backpressureAdmin.initialize();					}
					
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
						switch(event.type()) {
						case MESSAGE_STARTED:
							pathStack.push(event.path());
							break;
						case ARRAY_ELEMENT_STARTED:
							break;
						case MESSAGE:
							pathStack.pop();
							break;
						case ARRAY_ELEMENT:
							break;
							// TODO Support these?
						case ARRAY_STARTED:
							pathStack.push(event.path());
							break;
						case ARRAY_DONE:
							pathStack.pop();
							break;
						case BINARY_STARTED:
							pathStack.push(event.path());
							break;
						case BINARY_DONE:
							if(matches(path,pathStack)) {
								child.onComplete();
							}
							pathStack.pop();
							break;
						case BINARY_CONTENT:
							if(matches(path,pathStack)) {
								child.onNext((String) event.body());
								backpressureAdmin.registerEmission(1);
								backpressureAdmin.requestIfNeeded();
								return;
							}
						default:
							break;
						}
						backpressureAdmin.consumedEvent();
						backpressureAdmin.requestIfNeeded();
					}

					private boolean matches(String path, Stack<String> pathStack) {
						String joined = String.join("/", pathStack);
						return path.equals(joined);
					}
				};
			}};
	}


	public static FlowableOperator<NavajoStreamEvent,NavajoStreamEvent> messageWithPath(final String messagePath, final Func1<Msg,Msg> operation, boolean filterOthers) {
		return new BaseFlowableOperator<NavajoStreamEvent,NavajoStreamEvent>(1) {

			@Override
			public Subscriber<? super NavajoStreamEvent> apply(Subscriber<? super NavajoStreamEvent> child)
					throws Exception {
				return new Subscriber<NavajoStreamEvent>() {

					private final Stack<String> pathStack = new Stack<>();

					@Override
					public void onComplete() {
						operatorComplete(child);
					}

					@Override
					public void onError(Throwable e) {
						operatorError(e, child);
					}

					@Override
					public void onNext(NavajoStreamEvent event) {
						switch(event.type()) {
						case MESSAGE_STARTED:
							pathStack.push(event.path());
//							operatorRequest(1);
							break;
						case ARRAY_ELEMENT_STARTED:
//							operatorRequest(1);
							break;
						case MESSAGE:
							if(matches(messagePath,pathStack)) {
								Msg transformed = operation.call((Msg) event.body());
								operatorNext(event, e->{
									return Events.message(transformed, event.path(), event.attributes());
								}, child);
								return;
//							} else {
//								operatorRequest(1);
							}
							pathStack.pop();
							break;
						case ARRAY_ELEMENT:
							if(matches(messagePath,pathStack)) {
								Msg transformed = operation.call((Msg) event.body());

								operatorNext(event, e->{
									return Events.arrayElement(transformed,event.attributes());
								}, child);
								
//								child.onNext(Events.arrayElement(transformed,event.attributes()));
//								backpressureAdmin.registerEmission(1);
//								backpressureAdmin.requestIfNeeded();
								return;
//							} else {
//								operatorRequest(1);
							}
							break;
							// TODO Support these?
						case ARRAY_STARTED:
							pathStack.push(event.path());
//							operatorRequest(1);
							break;
						case ARRAY_DONE:
							pathStack.pop();
//							operatorRequest(1);
							break;
						default:
							break;
						}
						if(!filterOthers) {
							operatorNext(event, e->e, child);
//							child.onNext(event);
//							backpressureAdmin.registerEmission(1);
//							backpressureAdmin.requestIfNeeded();
						} else {
							operatorRequest(1);
						}
						
					}

					private boolean matches(String path, Stack<String> pathStack) {
						String joined = String.join("/", pathStack);
						return path.equals(joined);
					}
					
					@Override
					public void onSubscribe(Subscription s) {
						operatorSubscribe(s, child);
					}
				};
			}

		};
	}
	
	public static FlowableOperator<String, byte[]> decodeNew(String encodingName) {
		
		final CharsetDecoder charsetDecoder = Charset.forName(encodingName).newDecoder();
		return new BaseFlowableOperator<String,byte[]>(10) {

            private ByteBuffer leftOver = null;
			private BackpressureAdministrator backpressureAdmin;

			@Override
			public Subscriber<? super byte[]> apply(Subscriber<? super String> child) throws Exception {
				return new Subscriber<byte[]>() {

					@Override
					public void onComplete() {
						operatorComplete(child);
					}

					@Override
					public void onError(Throwable t) {
						operatorError(t, child);
					}

					@Override
					public void onNext(byte[] v) {
						operatorNext(v, data->{
							return "";
						}, child);
					}

					
	                public void process(byte[] next, ByteBuffer last, boolean endOfInput) throws CharacterCodingException {
	                    ByteBuffer bb;
	                    if (last != null) {
	                        if (next != null) {
	                            // merge leftover in front of the next bytes
	                            bb = ByteBuffer.allocate(last.remaining() + next.length);
	                            bb.put(last);
	                            bb.put(next);
	                            bb.flip();
	                        }
	                        else { // next == null
	                            bb = last;
	                        }
	                    }
	                    else { // last == null
	                        if (next != null) {
	                            bb = ByteBuffer.wrap(next);
	                        }
	                        else { // next == null
	                            return;
	                        }
	                    }

	                    CharBuffer cb = CharBuffer.allocate((int) (bb.limit() * charsetDecoder.averageCharsPerByte()));
	                    CoderResult cr = charsetDecoder.decode(bb, cb, endOfInput);
	                    cb.flip();

	                    if (cr.isError()) {
//	                        try {
	                            cr.throwException();
//	                        }
//	                        catch (CharacterCodingException e) {
//	                            child.onError(e);
//	                            return false;
//	                        }
	                    }

	                    if (bb.remaining() > 0) {
	                        leftOver = bb;
	                    }
	                    else {
	                        leftOver = null;
	                    }

	                    String string = cb.toString();
	                    if (!string.isEmpty()) {
	                        child.onNext(string);
	                        backpressureAdmin.registerEmission(1);
	                        backpressureAdmin.requestIfNeeded();
	                    } else {
	                        backpressureAdmin.requestIfNeeded();
	                    }
	                    return;
	                }
	                
					@Override
					public void onSubscribe(Subscription s) {
						operatorSubscribe(s, child);
					}
				};
			}
		};
	}
	public static FlowableOperator<String, byte[]> decode(String encodingName) {
		
		final CharsetDecoder charsetDecoder = Charset.forName(encodingName).newDecoder();
		return new FlowableOperator<String,byte[]>() {

			@Override
			public Subscriber<? super byte[]> apply(Subscriber<? super String> child) throws Exception {
				return new Op(child);
			}
			
			final class Op implements FlowableSubscriber<byte[]> {
				final Subscriber<? super String> child;
                private ByteBuffer leftOver = null;
				private BackpressureAdministrator backpressureAdmin;
			
				public Op(Subscriber<? super String> child) {
					this.child = child;
				}

				@Override
				public void onSubscribe(Subscription s) {
			        this.backpressureAdmin = new BackpressureAdministrator("decodeString: "+encodingName,Long.MAX_VALUE, s);
					child.onSubscribe(backpressureAdmin);
					backpressureAdmin.initialize();
				}

                @Override
                public void onComplete() {
                    if (process(null, leftOver, true))
                        child.onComplete();
                }

                @Override
                public void onError(Throwable e) {
                	e.printStackTrace();
                    if (process(null, leftOver, true)) {
                    	child.onError(e);                    	
                    }
                }

                @Override
                public void onNext(byte[] bytes) {
                    process(bytes, leftOver, false);
                }


                public boolean process(byte[] next, ByteBuffer last, boolean endOfInput) {
                    ByteBuffer bb;
                    if (last != null) {
                        if (next != null) {
                            // merge leftover in front of the next bytes
                            bb = ByteBuffer.allocate(last.remaining() + next.length);
                            bb.put(last);
                            bb.put(next);
                            bb.flip();
                        }
                        else { // next == null
                            bb = last;
                        }
                    }
                    else { // last == null
                        if (next != null) {
                            bb = ByteBuffer.wrap(next);
                        }
                        else { // next == null
                            return true;
                        }
                    }

                    CharBuffer cb = CharBuffer.allocate((int) (bb.limit() * charsetDecoder.averageCharsPerByte()));
                    CoderResult cr = charsetDecoder.decode(bb, cb, endOfInput);
                    cb.flip();

                    if (cr.isError()) {
                        try {
                            cr.throwException();
                        }
                        catch (CharacterCodingException e) {
                            child.onError(e);
                            return false;
                        }
                    }

                    if (bb.remaining() > 0) {
                        leftOver = bb;
                    }
                    else {
                        leftOver = null;
                    }

                    String string = cb.toString();
                    if (!string.isEmpty()) {
                        child.onNext(string);
                        backpressureAdmin.registerEmission(1);
                        backpressureAdmin.requestIfNeeded();
                    } else {
                        backpressureAdmin.requestIfNeeded();
                    }

                    return true;
                }
			}
		};
	}

	public static FlowableTransformer<byte[], byte[]> decompress2(String encoding) {
		return new FlowableTransformer<byte[], byte[]>(){

			@Override
			public Publisher<byte[]> apply(Flowable<byte[]> f) {
				if("jzlib".equals(encoding) || "deflate".equals(encoding) || "inflate".equals(encoding)) {
					return f
							.doOnNext(bb->System.err.println("Decompressing: "+encoding+" bytes: "+bytesToHex(bb)))
							.lift(inflate2()).concatMap(e->e);
				}
				// TODO gzip
				return f;
			}};
	}
	
	private final static char[] hexArray = "0123456789ABCDEF".toCharArray();
	public static String bytesToHex(byte[] bytes) {
	    char[] hexChars = new char[bytes.length * 2];
	    for ( int j = 0; j < bytes.length; j++ ) {
	        int v = bytes[j] & 0xFF;
	        hexChars[j * 2] = hexArray[v >>> 4];
	        hexChars[j * 2 + 1] = hexArray[v & 0x0F];
	    }
	    return new String(hexChars);
	}
	
	public static FlowableTransformer<byte[], byte[]> compress(String encoding) {
		return new FlowableTransformer<byte[], byte[]>(){

			@Override
			public Publisher<byte[]> apply(Flowable<byte[]> f) {
				if("jzlib".equals(encoding) || "deflate".equals(encoding) || "inflate".equals(encoding)) {
					return f.lift(deflate2()).concatMap(e->e);
				}
				// TODO gzip
				return f;
			}};
	}
	
//	public static FlowableOperator<byte[], byte[]> compress(String encoding) {
//		if(encoding==null) {
//			return identity();
//		}
//		
//		if("jzlib".equals(encoding) || "deflate".equals(encoding)) {
//			return deflate();
//		}
//		// TODO gzip
//		return identity();
//	}

//	public static FlowableOperator<byte[], byte[]> deflate() {
//		return new FlowableOperator<byte[], byte[]>() {
//			Deflater deflater = new Deflater();
//			private static final int COMPRESSION_BUFFER_SIZE = 16384;
//
//			@Override
//			public Subscriber<? super byte[]> apply(Subscriber<? super byte[]> child) {
//				return new Op(child);
//			}
//
//			final class Op implements FlowableSubscriber<byte[]> {
//				final Subscriber<? super byte[]> child;
//
//				private BackpressureAdministrator backpressureAdmin;
//
//				public Op(Subscriber<? super byte[]> child) {
//					this.child = child;
//				}
//
//				@Override
//				public void onSubscribe(Subscription s) {
//			        this.backpressureAdmin = new BackpressureAdministrator("deflate",1, s);
//					child.onSubscribe(backpressureAdmin);
//					backpressureAdmin.initialize();
//				}
//
//				@Override
//				public void onNext(byte[] in) {
//					deflater.setInput(in);
//					byte[] buffer = new byte[COMPRESSION_BUFFER_SIZE];
//					int read;
//					while(true) {
//						read = deflater.deflate(buffer,0,buffer.length,Deflater.NO_FLUSH);
//						if(read>0) {
//							byte[] copied = Arrays.copyOfRange(buffer, 0, read);
//							child.onNext(copied);
//							backpressureAdmin.registerEmission(1);
//						} else {
//							break;
//						}
//					}
//					
//				}
//
//				@Override
//				public void onError(Throwable e) {
//					child.onError(e);
//				}
//
//				@Override
//				public void onComplete() {
//					deflater.finish();
//					onNext(new byte[]{});
//					child.onComplete();
//				}
//			}
//		};
//	}

	public static FlowableOperator<Flowable<byte[]>, byte[]> deflate2() {
		return new BaseFlowableOperator<Flowable<byte[]>, byte[]>(10) {

			@Override
			public Subscriber<? super byte[]> apply(Subscriber<? super Flowable<byte[]>> child) throws Exception {
				
				
				return new Subscriber<byte[]>() {
					Deflater deflater = new Deflater();
					final int COMPRESSION_BUFFER_SIZE = 16384;

					@Override
					public void onComplete() {
						deflater.finish();
						byte[] buffer = new byte[COMPRESSION_BUFFER_SIZE];
						queue.offer(Flowable.fromIterable(new Iterable<byte[]>(){

								@Override
								public Iterator<byte[]> iterator() {				
									return new Iterator<byte[]>() {
										int read;
										boolean first = true;
										@Override
										public boolean hasNext() {
											read = deflater.deflate(buffer,0,buffer.length,Deflater.FULL_FLUSH);
											boolean needsInput = deflater.needsInput();
											boolean hasMore = (first || needsInput) && read > 0;
											first = false;
											return hasMore;
										}

										@Override
										public byte[] next() {
											return Arrays.copyOfRange(buffer, 0, read);
										}
									};
								}
						}));
						operatorComplete(child);
					}

					@Override
					public void onError(Throwable t) {
						operatorError(t, child);
					}

					@Override
					public void onNext(byte[] dataIn) {
						deflater.setInput(dataIn);
						byte[] buffer = new byte[COMPRESSION_BUFFER_SIZE];
						operatorNext(dataIn, data->{
							return Flowable.fromIterable(new Iterable<byte[]>(){

								@Override
								public Iterator<byte[]> iterator() {				
									return new Iterator<byte[]>() {
										int read;
										boolean first = true;
										@Override
										public boolean hasNext() {
											read = deflater.deflate(buffer,0,buffer.length,Deflater.FULL_FLUSH);
											boolean needsInput = deflater.needsInput();
											boolean hasMore = (first || needsInput) && read > 0;
											first = false;
											return hasMore;
										}

										@Override
										public byte[] next() {
											return Arrays.copyOfRange(buffer, 0, read);
										}
									};
								}
							});
						}, child);
					}

					@Override
					public void onSubscribe(Subscription s) {
						operatorSubscribe(s, child);
					}
				};
			}
		};
	}
	public static FlowableOperator<Flowable<byte[]>, byte[]> inflate2() {
		Inflater inflater = new Inflater();
		return new BaseFlowableOperator<Flowable<byte[]>, byte[]>(10) {

			@Override
			public Subscriber<? super byte[]> apply(Subscriber<? super Flowable<byte[]>> child) throws Exception {
				return new Subscriber<byte[]>(){

					final int COMPRESSION_BUFFER_SIZE = 16384;
					byte[] buffer = new byte[COMPRESSION_BUFFER_SIZE];

					@Override
					public void onComplete() {
						inflater.finished();
						Iterable<byte[]> output = new Iterable<byte[]>(){

							@Override
							public Iterator<byte[]> iterator() {
								return new Iterator<byte[]>(){
									int read;
									boolean first = true;

									@Override
									public boolean hasNext() {
										boolean needsInput = inflater.needsInput();
										
										try {
											System.err.println("Needs input: "+needsInput);
											read = inflater.inflate(buffer);
											boolean hasMore = (first || needsInput) && read > 0;
											first = false;
											return hasMore;
										} catch (DataFormatException e) {
											e.printStackTrace();
											child.onError(e);
											return false;
										}
									}

									@Override
									public byte[] next() {
										return Arrays.copyOfRange(buffer, 0, read);
									}};
							}};
						queue.offer(Flowable.fromIterable(output));						
						operatorComplete(child);
					}

					@Override
					public void onError(Throwable t) {
						operatorError(t,child);
						
					}

					@Override
					public void onNext(byte[] data) {
						System.err.println("Offering bytes: "+data.length);
						inflater.setInput(data);
						Iterable<byte[]> output = new Iterable<byte[]>(){

								@Override
								public Iterator<byte[]> iterator() {
									return new Iterator<byte[]>(){
										int read;
										boolean first = true;

										@Override
										public boolean hasNext() {
											boolean needsInput = inflater.needsInput();
											
											try {
//												System.err.println("NeedsInput: "+needsInput+" first: "+first+" bytes: "+new String(data));
												read = inflater.inflate(buffer);
												boolean hasMore = (first || needsInput) && read > 0;
												first = false;
												return hasMore;
											} catch (DataFormatException e) {
												e.printStackTrace();
												child.onError(e);
												return false;
											}
										}

										@Override
										public byte[] next() {
											return Arrays.copyOfRange(buffer, 0, read);
										}};
								}};
							queue.offer(Flowable.fromIterable(output));
							drain(child);
					}

					@Override
					public void onSubscribe(Subscription s) {
						operatorSubscribe(s, child);
						
					}


				};
			}
		};
				
	}
	
//	public static FlowableOperator<byte[], byte[]> inflate() {
//		return new FlowableOperator<byte[], byte[]>() {
//			Inflater inflater = new Inflater();
//			private static final int COMPRESSION_BUFFER_SIZE = 16384;
//
//			@Override
//			public Subscriber<? super byte[]> apply(Subscriber<? super byte[]> child) {
//				return new Op(child);
//			}
//
//			final class Op implements FlowableSubscriber<byte[]> {
//				final Subscriber<? super byte[]> child;
//				private BackpressureAdministrator backpressureAdmin;
//
//				public Op(Subscriber<? super byte[]> child) {
//					this.child = child;
//				}
//
//				@Override
//				public void onSubscribe(Subscription s) {
//			        this.backpressureAdmin = new BackpressureAdministrator("inflate",1, s);
//					child.onSubscribe(backpressureAdmin);
//					backpressureAdmin.initialize();			
//				}
//
//				@Override
//				public void onNext(byte[] v) {
//					inflater.setInput(v);
//					byte[] buffer = new byte[COMPRESSION_BUFFER_SIZE];
//					int read;
//					try {
//						while(!inflater.needsInput()) {
//							read = inflater.inflate(buffer);
//							if(read>0) {
//								child.onNext(Arrays.copyOfRange(buffer, 0, read));
//							}
//							backpressureAdmin.registerEmission(1);
//						}
//					} catch (DataFormatException e) {
//						child.onError(e);
//					}
//				}
//
//				@Override
//				public void onError(Throwable e) {
//					child.onError(e);
//				}
//
//				@Override
//				public void onComplete() {
//					int remaining = inflater.getRemaining();
//					if(remaining>0) {
//						byte[] rm = new byte[remaining];
//						child.onNext(rm);
//					}
//					child.onComplete();
//				}
//			}
//		};
//	}


	public static Subscriber<byte[]> dumpToFile(final String path) {
		return new Subscriber<byte[]>() {
			
			FileOutputStream out = null;
			private Subscription subscription;
			
			@Override
			public void onComplete() {
				System.err.println("filedump done");
				if(out!=null) {
					try {
						out.flush();
						out.close();
						subscription.cancel();
					} catch (IOException e) {
						logger.error("Error: ", e);
					}
				}
			}

			@Override
			public void onError(Throwable ex) {
				logger.error("Error: ", ex);
				if(out!=null) {
					try {
						out.flush();
						out.close();
						subscription.cancel();
					} catch (IOException e) {
						logger.error("Error: ", e);
					}
				}
			}

			@Override
			public void onNext(byte[] b) {
				System.err.println("filedump: "+b.length);
				try {
					if(out==null) {
						out =  new FileOutputStream(path);
					}
					out.write(b);
					this.subscription.request(1);
				} catch (IOException e) {
					logger.error("Error: ", e);
					subscription.cancel();
				}
				
			}

			@Override
			public void onSubscribe(Subscription s) {
				System.err.println("Subscribing filedump");
				this.subscription = s;
				s.request(1);
			}
		};
	}
	
	public static FlowableTransformer<NavajoStreamEvent, NavajoStreamEvent> inArray(String name) {
		return new FlowableTransformer<NavajoStreamEvent, NavajoStreamEvent>() {

			@Override
			public Flowable<NavajoStreamEvent> apply(Flowable<NavajoStreamEvent> in) {
	        	return in.startWith(Flowable.just(Events.arrayStarted(name,Collections.emptyMap())))
	        	.concatWith(Flowable.just(Events.arrayDone(name)));
			}
		};
	}

	public static FlowableTransformer<ReplicationMessage, NavajoStreamEvent> toArray(String name) {
		return new FlowableTransformer<ReplicationMessage, NavajoStreamEvent>() {

			@Override
			public Flowable<NavajoStreamEvent> apply(Flowable<ReplicationMessage> in) {
				return in.concatMap(msg->StreamDocument.replicationMessageToStreamEvents(name, msg,true))
						.startWith(Flowable.just(Events.arrayStarted(name,Collections.emptyMap())))
						.concatWith(Flowable.just(Events.arrayDone(name)));
			}
		};
	}

	public static FlowableTransformer<NavajoStreamEvent, NavajoStreamEvent> inMessage(String name) {
		return new FlowableTransformer<NavajoStreamEvent, NavajoStreamEvent>() {

			@Override
			public Flowable<NavajoStreamEvent> apply(Flowable<NavajoStreamEvent> in) {
	        	return in.startWith(Flowable.just(Events.messageStarted(name,null)))
	        	.concatWith(Flowable.just(Events.message(Msg.create(), name,null)));
			}
		};
	}

	public static FlowableTransformer<NavajoStreamEvent, NavajoStreamEvent> inNavajo(String name, String username, String password) {
		return new FlowableTransformer<NavajoStreamEvent, NavajoStreamEvent>() {

			@Override
			public Flowable<NavajoStreamEvent> apply(Flowable<NavajoStreamEvent> in) {
	        	return in
	        			.filter(e->e.type()!=NavajoEventTypes.NAVAJO_STARTED)
	        			.filter(e->e.type()!=NavajoEventTypes.NAVAJO_DONE)
	        			.startWith(Flowable.just(Events.started(NavajoHead.createSimple(name, username, password))))
	        			.concatWith(Flowable.just(Events.done()));
			}
		};
	}
	
	public static Flowable<NavajoStreamEvent> replicationMessageToStreamEvents(String name, ReplicationMessage msg,boolean isArrayElement) {
		
		Flowable<NavajoStreamEvent> subm = Flowable.fromIterable(
				msg.subMessageMap()
					.entrySet())
					.concatMap(e-> {
						return replicationMessageToStreamEvents(e.getKey(),e.getValue(),false);
					});
		
		
		Flowable<NavajoStreamEvent> subList = Flowable.fromIterable(
				msg.subMessageListMap()
				.entrySet())
				.concatMap(e->streamReplicationMessageList(e.getKey(),e.getValue()));
		
		if(isArrayElement) {
			return subm.startWith(Events.arrayElementStarted(Collections.emptyMap())).concatWith(subList).concatWith(Flowable.just(Events.arrayElement(replicationToMsg(msg,isArrayElement),Collections.emptyMap())));
		} else {
			return subm.startWith(Events.messageStarted(name, Collections.emptyMap())).concatWith(subList).concatWith(Flowable.just(Events.message(replicationToMsg(msg,isArrayElement), name, Collections.emptyMap())));
		}

	}

	private static Flowable<NavajoStreamEvent> streamReplicationMessageList(String name, List<ReplicationMessage> l) {
		if(l.isEmpty()) {
			return Flowable.empty();
		}
		return Flowable.fromIterable(l)
				.concatMap(msg->replicationMessageToStreamEvents(name,msg,true))
				.startWith(Events.arrayStarted(name, Collections.emptyMap()))
				.concatWith(Flowable.just(Events.arrayDone(name))
					)
		;
	}
	
	public static Msg replicationToMsg(ReplicationMessage rpl, boolean isArrayMessage) {
		Map<String,String> types = rpl.types();
		List<Prop> properties = rpl.values()
			.entrySet()
			.stream()
			.map(e->Prop.create(e.getKey(), e.getValue(), types.get(e.getKey())))
			.collect(Collectors.toList());
		return isArrayMessage ? Msg.createElement(properties) : Msg.create(properties);
	}

	public static ObservableOperator<Binary, byte[]> createBinary() {
		return new ObservableOperator<Binary, byte[]>() {

			@Override
			public Observer<? super byte[]> apply(Observer<? super Binary> out) throws Exception {
				Binary result = new Binary();
				result.startBinaryPush();
				return new Observer<byte[]>() {


					@Override
					public void onComplete() {
						try {
							result.finishPushContent();
						} catch (IOException e) {
							e.printStackTrace();
							out.onError(e);
							return;
						} 
						out.onNext(result);
						out.onComplete();
					}

					@Override
					public void onError(Throwable e) {
						out.onError(e);
					}

					@Override
					public void onNext(byte[] b) {
						result.pushContent(b);
					}

					@Override
					public void onSubscribe(Disposable d) {
						out.onSubscribe(new Disposable() {
							private boolean disposed = false;
							@Override
							public void dispose() {
								disposed = true;
							}

							@Override
							public boolean isDisposed() {
								return disposed;
							}});
					}

				};
			}
};
	}
	public static FlowableOperator<NavajoStreamEvent, NavajoStreamEvent> elementsInPath() {
		return new FlowableOperator<NavajoStreamEvent, NavajoStreamEvent>() {

			@Override
			public Subscriber<? super NavajoStreamEvent> apply(Subscriber<? super NavajoStreamEvent> child) {
				return new Op(child);
			}

			final class Op implements FlowableSubscriber<NavajoStreamEvent>, Subscription {
				final Subscriber<? super NavajoStreamEvent> child;

				Subscription parentSubscription;

				public Op(Subscriber<? super NavajoStreamEvent> child) {
					this.child = child;
				}

				@Override
				public void onSubscribe(Subscription s) {
					this.parentSubscription = s;
					child.onSubscribe(this);
				}

				@Override
				public void onNext(NavajoStreamEvent v) {
					child.onNext(v);
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

	public static Message replicationToMessage(ReplicationMessage msg, String name, boolean isArrayElement) {
		Navajo n = NavajoFactory.getInstance().createNavajo();
		Message m = NavajoFactory.getInstance().createMessage(n, name, isArrayElement ? Message.MSG_TYPE_ARRAY_ELEMENT : Message.MSG_TYPE_SIMPLE);
		List<Property> pp = msg.columnNames()
			.stream()
			.map(e->{
				String type = msg.columnType(e);
				Object value = msg.columnValue(e);
				Property p = NavajoFactory.getInstance().createProperty(n, name, type, "", 0, "", Property.DIR_OUT);
				p.setAnyValue(value);
				return p;
			})
			.collect(Collectors.toList());
		pp.stream().forEach(p->m.addProperty(p));
		msg.subMessageListMap().forEach((msgName,submessages)->{
			Message subArray = NavajoFactory.getInstance().createMessage(n, msgName,  Message.MSG_TYPE_ARRAY_ELEMENT);
			submessages.forEach(repl->{
				subArray.addElement(replicationToMessage(repl, msgName, true));
			});
			m.addMessage(subArray);
		});
		msg.subMessageMap().forEach((msgName,subMessage)->m.addMessage(replicationToMessage(subMessage, msgName, false)));
		return m;
	}
}
