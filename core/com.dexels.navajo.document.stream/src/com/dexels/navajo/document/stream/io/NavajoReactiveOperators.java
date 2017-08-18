package com.dexels.navajo.document.stream.io;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.stream.api.Msg;
import com.dexels.navajo.document.stream.api.NavajoHead;
import com.dexels.navajo.document.stream.events.Events;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent.NavajoEventTypes;
import com.dexels.navajo.document.stream.xml.BackpressureAdministrator;

import io.reactivex.Flowable;
import io.reactivex.FlowableOperator;
import io.reactivex.FlowableSubscriber;
import io.reactivex.FlowableTransformer;

public class NavajoReactiveOperators {

	
	private final static Logger logger = LoggerFactory.getLogger(NavajoReactiveOperators.class);

	public static FlowableOperator<byte[], byte[]> identity() {
		return new FlowableOperator<byte[], byte[]>() {

			@Override
			public Subscriber<? super byte[]> apply(Subscriber<? super byte[]> child) {
				return new Op(child);
			}

			final class Op implements FlowableSubscriber<byte[]> {
				final Subscriber<? super byte[]> child;
				private BackpressureAdministrator backpressureAdmin;

				public Op(Subscriber<? super byte[]> child) {
					this.child = child;
				}

				@Override
				public void onSubscribe(Subscription s) {
			        this.backpressureAdmin = new BackpressureAdministrator("identity",1, s);
					child.onSubscribe(backpressureAdmin);
					backpressureAdmin.initialize();
				
				}

				@Override
				public void onNext(byte[] v) {
					child.onNext(v);
					backpressureAdmin.registerEmission(1);
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
	
	public static FlowableOperator<byte[], byte[]> decompress(String encoding) {
//		logger.info("Starting decompress with encoding: {}",encoding);
		if("jzlib".equals(encoding) || "deflate".equals(encoding) || "inflate".equals(encoding)) {
			return inflate();
		}
		// TODO gzip
		return identity();
	}
	
	public static FlowableOperator<byte[], byte[]> compress(String encoding) {
		if(encoding==null) {
			return identity();
		}
		
		if("jzlib".equals(encoding) || "deflate".equals(encoding)) {
			return deflate();
		}
		// TODO gzip
		return identity();
	}

	public static FlowableOperator<byte[], byte[]> deflate() {
		return new FlowableOperator<byte[], byte[]>() {
			Deflater deflater = new Deflater();
			private static final int COMPRESSION_BUFFER_SIZE = 16384;

			@Override
			public Subscriber<? super byte[]> apply(Subscriber<? super byte[]> child) {
				return new Op(child);
			}

			final class Op implements FlowableSubscriber<byte[]> {
				final Subscriber<? super byte[]> child;

				private BackpressureAdministrator backpressureAdmin;

				public Op(Subscriber<? super byte[]> child) {
					this.child = child;
				}

				@Override
				public void onSubscribe(Subscription s) {
			        this.backpressureAdmin = new BackpressureAdministrator("deflate",1, s);
					child.onSubscribe(backpressureAdmin);
					backpressureAdmin.initialize();
				}

				@Override
				public void onNext(byte[] in) {
					deflater.setInput(in);
					byte[] buffer = new byte[COMPRESSION_BUFFER_SIZE];
					int read;
					while(true) {
						read = deflater.deflate(buffer,0,buffer.length,Deflater.NO_FLUSH);
						if(read>0) {
							byte[] copied = Arrays.copyOfRange(buffer, 0, read);
							child.onNext(copied);
							backpressureAdmin.registerEmission(1);
						} else {
							break;
						}
					}
					
				}

				@Override
				public void onError(Throwable e) {
					child.onError(e);
				}

				@Override
				public void onComplete() {
					deflater.finish();
					onNext(new byte[]{});
					child.onComplete();
				}
			}
		};
	}
	
	public static FlowableOperator<byte[], byte[]> inflate() {
		return new FlowableOperator<byte[], byte[]>() {
			Inflater inflater = new Inflater();
			private static final int COMPRESSION_BUFFER_SIZE = 16384;

			@Override
			public Subscriber<? super byte[]> apply(Subscriber<? super byte[]> child) {
				return new Op(child);
			}

			final class Op implements FlowableSubscriber<byte[]> {
				final Subscriber<? super byte[]> child;
				private BackpressureAdministrator backpressureAdmin;

				public Op(Subscriber<? super byte[]> child) {
					this.child = child;
				}

				@Override
				public void onSubscribe(Subscription s) {
			        this.backpressureAdmin = new BackpressureAdministrator("inflate",1, s);
					child.onSubscribe(backpressureAdmin);
					backpressureAdmin.initialize();			
				}

				@Override
				public void onNext(byte[] v) {
					inflater.setInput(v);
					byte[] buffer = new byte[COMPRESSION_BUFFER_SIZE];
					int read;
					try {
						while(!inflater.needsInput()) {
							read = inflater.inflate(buffer);
							if(read>0) {
								child.onNext(Arrays.copyOfRange(buffer, 0, read));
							}
							backpressureAdmin.registerEmission(1);
						}
					} catch (DataFormatException e) {
						child.onError(e);
					}
				}

				@Override
				public void onError(Throwable e) {
					child.onError(e);
				}

				@Override
				public void onComplete() {
					int remaining = inflater.getRemaining();
					if(remaining>0) {
						byte[] rm = new byte[remaining];
						child.onNext(rm);
					}
					child.onComplete();
				}
			}
		};
	}


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


}
