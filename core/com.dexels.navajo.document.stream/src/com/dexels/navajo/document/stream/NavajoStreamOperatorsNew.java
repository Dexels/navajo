package com.dexels.navajo.document.stream;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;
import java.util.Optional;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicBoolean;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.stream.api.Msg;
import com.dexels.navajo.document.stream.events.Events;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent;
import com.dexels.navajo.document.stream.xml.BackpressureAdministrator;
import com.dexels.navajo.document.stream.xml.ObservableNavajoParser;
import com.dexels.navajo.document.stream.xml.XMLEvent;
import com.dexels.navajo.document.types.Binary;

import io.reactivex.FlowableOperator;
import io.reactivex.FlowableSubscriber;
import io.reactivex.ObservableOperator;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import rx.functions.Func1;

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

	public static FlowableOperator<NavajoStreamEvent, XMLEvent> parse() {
		return new FlowableOperator<NavajoStreamEvent, XMLEvent>() {
			@Override
			public Subscriber<? super XMLEvent> apply(Subscriber<? super NavajoStreamEvent> child) throws Exception {
				return new Op(child);
			}

			
			final class Op implements FlowableSubscriber<XMLEvent> {
				final Subscriber<? super NavajoStreamEvent> child;

				ObservableNavajoParser parser = null;

				private BackpressureAdministrator backpressureAdmin;

				public Op(Subscriber<? super NavajoStreamEvent> child) {
					this.child = child;
					parser = new ObservableNavajoParser(child);
				}

				@Override
				public void onSubscribe(Subscription s) {
			        this.backpressureAdmin = new BackpressureAdministrator("parseNavajoStream",1, s);
					child.onSubscribe(backpressureAdmin);
					backpressureAdmin.initialize();
				}

				@Override
				public void onNext(XMLEvent xmlEvent) {
					int emitted = parser.parseXmlEvent(xmlEvent);
					if(emitted == 0) {
						backpressureAdmin.request(1);
					}
					System.err.println("Emitted: "+emitted);
					backpressureAdmin.registerEmission(emitted);
				}

				@Override
				public void onError(Throwable e) {
					child.onError(e);
				}

				@Override
				public void onComplete() {
					child.onComplete();
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

			
			final class Op implements FlowableSubscriber<NavajoStreamEvent> {
				final Subscriber<? super byte[]> child;
				private BackpressureAdministrator backpressureAdmin;

				public Op(Subscriber<? super byte[]> child) {
					this.child = child;
				}

				@Override
				public void onSubscribe(Subscription s) {
			        this.backpressureAdmin = new BackpressureAdministrator("serializeNavajoStream",Long.MAX_VALUE, s);
					child.onSubscribe(backpressureAdmin);
					backpressureAdmin.initialize();

				}

				@Override
				public void onNext(NavajoStreamEvent v) {
//					child.onNext(v);
					byte[] b = collector.serialize(v);
					if(b.length==0) {
						backpressureAdmin.request(1);
					} else {
						child.onNext(b);
						backpressureAdmin.registerEmission(1);
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
			}
		};
	}
	
	public static FlowableOperator<NavajoStreamEvent, NavajoStreamEvent> filterMessageIgnore() {
		return new FlowableOperator<NavajoStreamEvent, NavajoStreamEvent>() {
			private final Stack<Boolean> ignoreLevel = new Stack<Boolean>();
			
			@Override
			public Subscriber<? super NavajoStreamEvent> apply(Subscriber<? super NavajoStreamEvent> child) throws Exception {
				return new Op(child);
			}
			
			final class Op implements FlowableSubscriber<NavajoStreamEvent> {
				final Subscriber<? super NavajoStreamEvent> child;
				private BackpressureAdministrator backpressureAdmin;

				public Op(Subscriber<? super NavajoStreamEvent> child) {
					this.child = child;
				}

				@Override
				public void onSubscribe(Subscription s) {
			        this.backpressureAdmin = new BackpressureAdministrator("filterMessageIgnore",Long.MAX_VALUE, s);
					child.onSubscribe(backpressureAdmin);
					backpressureAdmin.initialize();
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
							child.onNext(event);
						}
						backpressureAdmin.registerEmission(1);
						break;
						
					case ARRAY_DONE:
					case ARRAY_ELEMENT:
					case MESSAGE_DEFINITION:
					case MESSAGE:
						if(!ignoreLevel.contains(true)) {
							child.onNext(event);
						}
						backpressureAdmin.registerEmission(1);
						ignoreLevel.pop();
						break;
						
					case NAVAJO_DONE:
					case NAVAJO_STARTED:
						child.onNext(event);
						backpressureAdmin.registerEmission(1);
						break;
					default:
						throw new UnsupportedOperationException("Unknown event found in NAVADOC");

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
			};
		};
	}

	public static FlowableOperator<NavajoStreamEvent,NavajoStreamEvent> setPropertyValue(final String messagePath, String property, Object value) {
		return messageWithPath(messagePath, msg->msg.withValue(property, value),false);
	}
	
	public static FlowableOperator<NavajoStreamEvent,NavajoStreamEvent> messageWithPath(final String messagePath) {
		return messageWithPath(messagePath,m->m,true);
	}

	
	
	public static FlowableOperator<String,NavajoStreamEvent> observeBinary(final String path) {
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
		return new FlowableOperator<NavajoStreamEvent,NavajoStreamEvent>(){
			@Override
			public Subscriber<? super NavajoStreamEvent> apply(Subscriber<? super NavajoStreamEvent> child) {
				return new Subscriber<NavajoStreamEvent>() {

					private final Stack<String> pathStack = new Stack<>();
					private BackpressureAdministrator backpressureAdmin;
					
					@Override
					public void onSubscribe(Subscription s) {
				        this.backpressureAdmin = new BackpressureAdministrator("messageWithPath",Long.MAX_VALUE, s);
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
							backpressureAdmin.consumedEvent();
							break;
						case ARRAY_ELEMENT_STARTED:
							backpressureAdmin.consumedEvent();
							break;
						case MESSAGE:
							if(matches(messagePath,pathStack)) {
								Msg transformed = operation.call((Msg) event.body());
								child.onNext(Events.message(transformed, event.path(), event.attributes()));
								backpressureAdmin.registerEmission(1);
								backpressureAdmin.requestIfNeeded();
								return;
							} else {
								backpressureAdmin.consumedEvent();
							}
							pathStack.pop();
							break;
						case ARRAY_ELEMENT:
							if(matches(messagePath,pathStack)) {
								Msg transformed = operation.call((Msg) event.body());
								child.onNext(Events.arrayElement(transformed,event.attributes()));
								backpressureAdmin.registerEmission(1);
								backpressureAdmin.requestIfNeeded();
								return;
							} else {
								backpressureAdmin.consumedEvent();
							}
							break;
							// TODO Support these?
						case ARRAY_STARTED:
							pathStack.push(event.path());
							backpressureAdmin.consumedEvent();
							break;
						case ARRAY_DONE:
							pathStack.pop();
							backpressureAdmin.consumedEvent();
							break;
						default:
							break;
						}
						if(!filterOthers) {
							child.onNext(event);
							backpressureAdmin.registerEmission(1);
							backpressureAdmin.requestIfNeeded();
						}
					}

					private boolean matches(String path, Stack<String> pathStack) {
						String joined = String.join("/", pathStack);
						return path.equals(joined);
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
	
}
