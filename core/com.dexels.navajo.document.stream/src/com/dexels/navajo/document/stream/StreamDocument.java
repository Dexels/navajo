package com.dexels.navajo.document.stream;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;
import java.util.AbstractMap;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.immutable.factory.ImmutableFactory;
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
import com.dexels.navajo.document.stream.io.BaseFlowableOperator;
import com.dexels.navajo.document.stream.xml.ObservableNavajoParser;
import com.dexels.navajo.document.stream.xml.XMLEvent;
import com.dexels.navajo.document.types.Binary;

import io.reactivex.Flowable;
import io.reactivex.FlowableOperator;
import io.reactivex.FlowableSubscriber;
import io.reactivex.FlowableTransformer;
import io.reactivex.ObservableOperator;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class StreamDocument {

	
	private final static Logger logger = LoggerFactory.getLogger(StreamDocument.class);

	private StreamDocument() {}

	public static Flowable<NavajoStreamEvent> streamMessage(Message message) {
		return NavajoDomStreamer.streamMessage(message);
	}
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
						List<NavajoStreamEvent> eventList = NavajoDomStreamer.processNavajo(n);
						for (NavajoStreamEvent event : eventList) {
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
						}
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

	public static FlowableOperator<byte[], NavajoStreamEvent> serialize() {
		return new BaseFlowableOperator<byte[],NavajoStreamEvent>(2) {
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
	public static FlowableOperator<NavajoStreamEvent,NavajoStreamEvent> messageWithPath(final String messagePath, final Function<Msg,Msg> operation, boolean filterOthers) {
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
								Msg transformed;
								try {
									transformed = operation.apply((Msg) event.body());
									operatorNext(event, e->{
										return Events.message(transformed, event.path(), event.attributes());
									}, child);
								} catch (Exception e1) {
									logger.error("Unexpected error: ", e1);
								}
								return;
//							} else {
//								operatorRequest(1);
							}
							pathStack.pop();
							break;
						case ARRAY_ELEMENT:
							if(matches(messagePath,pathStack)) {
								Msg transformed;
								try {
									transformed = operation.apply((Msg) event.body());
									operatorNext(event, e->{
										return Events.arrayElement(transformed,event.attributes());
									}, child);
								} catch (Exception e1) {
									logger.error("Very unexpected exception: ", e1);
									e1.printStackTrace();
								}

								
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
	public static void removeFile(final String path) {
		File f = new File(path);
		if(f.exists()) {
			f.delete();
		}
 	}
	
	/**
	 * Not very fast, as stream gets opened and closed all the time
	 * @param path
	 * @return
	 */
	public static Consumer<byte[]> appendToFile(final String path) {
		return b->{
			File appendTo = new File(path);
			
			if(appendTo.isAbsolute() && !appendTo.getParentFile().exists()) {
				appendTo.getParentFile().mkdirs();
			}
			try(FileOutputStream out = new FileOutputStream(appendTo,true)) {
				out.write(b);
			} catch (IOException e) {
				logger.error("Error dumping data to file: ", path);
			}
			
		};
	}
	
	public static Subscriber<byte[]> dumpToFile(final String path) {
		return new Subscriber<byte[]>() {
			
			FileOutputStream out = null;
			private Subscription subscription;
			
			@Override
			public void onComplete() {
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

	public static FlowableTransformer<ImmutableMessage, NavajoStreamEvent> toMessageEvent(String name, boolean isArray) {
		return new FlowableTransformer<ImmutableMessage, NavajoStreamEvent>() {

			@Override
			public Flowable<NavajoStreamEvent> apply(Flowable<ImmutableMessage> in) {
				if(!isArray) {
					in = in.take(1);
				}
				Flowable<NavajoStreamEvent> events = in.concatMap(msg->StreamDocument.replicationMessageToStreamEvents(name, msg,isArray));
				if(!isArray) {
					return events;
				}
				return events
						.startWith(Flowable.just(Events.arrayStarted(name,Collections.emptyMap())))
						.concatWith(Flowable.just(Events.arrayDone(name)));
			}
		};
	}

	public static FlowableTransformer<ImmutableMessage, NavajoStreamEvent> toMessage(String name) {
		return new FlowableTransformer<ImmutableMessage, NavajoStreamEvent>() {

			@Override
			public Flowable<NavajoStreamEvent> apply(Flowable<ImmutableMessage> in) {
				return in.concatMap(msg->StreamDocument.replicationMessageToStreamEvents(name, msg,false));
			}
		};
	}

	public static FlowableTransformer<NavajoStreamEvent, NavajoStreamEvent> inNavajo(String name, Optional<String> username, Optional<String> password) {
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
	
	public static Flowable<NavajoStreamEvent> replicationMessageToStreamEvents(String name, ImmutableMessage msg,boolean isArrayElement) {
		
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

	private static Flowable<NavajoStreamEvent> streamReplicationMessageList(String name, List<ImmutableMessage> l) {
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
	
	public static Msg replicationToMsg(ImmutableMessage rpl, boolean isArrayMessage) {
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

	public static Message replicationToMessage(ImmutableMessage msg, String name, boolean isArrayElement) {
		Navajo n = NavajoFactory.getInstance().createNavajo();
		Message m = NavajoFactory.getInstance().createMessage(n, name, isArrayElement ? Message.MSG_TYPE_ARRAY_ELEMENT : Message.MSG_TYPE_SIMPLE);
		List<Property> pp = msg.columnNames()
			.stream()
			.map(e->{
				String type = msg.columnType(e);
				Object value = msg.columnValue(e);
				Property p = NavajoFactory.getInstance().createProperty(n, e, type, "", 0, "", Property.DIR_OUT);
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
	
	private static Map.Entry<String,List<ImmutableMessage>> arrayMessageToReplicationList(Message msg) {
		return new AbstractMap.SimpleEntry<String,List<ImmutableMessage>>(msg.getName(), msg.getAllMessages().stream()
			.map(StreamDocument::messageToReplication)
			.collect(Collectors.toList()));
	}
	private static Map.Entry<String,ImmutableMessage> messageToReplicationEntry(Message msg) {
		return new AbstractMap.SimpleEntry<String,ImmutableMessage>(msg.getName(), messageToReplication(msg));
	}	
	public static ImmutableMessage messageToReplication(Message msg) {
		Map<String,Object> values = new HashMap<>();
		Map<String,String> types = new HashMap<>();
		msg.getProperties().forEach((name,prop)->{
//			values.put(name, p)
			String type = prop.getType();
			Object value = prop.getTypedValue();
			values.put(name, value);
			types.put(name, type);
		});
		Map<String,List<ImmutableMessage>> arrayMessages = msg.getAllMessages()
				.stream()
				.filter(m->m.isArrayMessage())
				.map(StreamDocument::arrayMessageToReplicationList)
				.collect(Collectors.toMap(e->e.getKey(), e->e.getValue()));
		Map<String,ImmutableMessage> subMessageMap =  msg.getAllMessages().stream()
				.filter(m->!m.isArrayMessage())
				.map(StreamDocument::messageToReplicationEntry)
				.collect(Collectors.toMap(e->e.getKey(), e->e.getValue()));
//		List<Message> simpleMessages = msg.getAllMessages().stream().filter(m->!m.isArrayMessage()).collect(Collectors.toList());
		return ImmutableFactory.create(values, types)
				.withAllSubMessageLists(arrayMessages)
				.withAllSubMessage(subMessageMap);
	}
}
