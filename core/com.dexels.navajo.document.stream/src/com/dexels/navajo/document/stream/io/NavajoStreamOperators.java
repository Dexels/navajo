package com.dexels.navajo.document.stream.io;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.stream.api.NavajoHead;
import com.dexels.navajo.document.stream.events.Events;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent;

import rx.Observable;
import rx.Observable.Operator;
import rx.Observable.Transformer;
import rx.Subscriber;
import rx.schedulers.Schedulers;

public class NavajoStreamOperators {
	
	
	private final static Logger logger = LoggerFactory.getLogger(NavajoStreamOperators.class);

	
	<T> Transformer<T, T> applySchedulers() {  
	    return observable -> observable.subscribeOn(Schedulers.io())
	        .observeOn(Schedulers.newThread());
	}
	
	public static Transformer<NavajoStreamEvent, NavajoStreamEvent> inArray(String name) {
		return new Transformer<NavajoStreamEvent, NavajoStreamEvent>() {

			@Override
			public Observable<NavajoStreamEvent> call(Observable<NavajoStreamEvent> in) {
	        	return in.startWith(Observable.just(Events.arrayStarted(name)))
	        	.concatWith(Observable.just(Events.arrayDone(name)));
			}
		};
	}
	
	public static Transformer<NavajoStreamEvent, NavajoStreamEvent> inMessage(String name) {
		return new Transformer<NavajoStreamEvent, NavajoStreamEvent>() {

			@Override
			public Observable<NavajoStreamEvent> call(Observable<NavajoStreamEvent> in) {
	        	return in.startWith(Observable.just(Events.messageStarted(name,null)))
	        	.concatWith(Observable.just(Events.message(Collections.emptyList(), name,null)));
			}
		};
	}

	public static Transformer<NavajoStreamEvent, NavajoStreamEvent> inNavajo(String name, String username, String password) {
		return new Transformer<NavajoStreamEvent, NavajoStreamEvent>() {

			@Override
			public Observable<NavajoStreamEvent> call(Observable<NavajoStreamEvent> in) {
	        	return in.startWith(Observable.just(Events.started(NavajoHead.createSimple(name, username, password))))
	        	.concatWith(Observable.just(Events.done()));
			}
		};
	}

	
	public static Operator<ByteBuffer, ByteBuffer> decompress(Map<String, Object> attributes) {
		String encoding = (String) attributes.get("Content-Encoding");
		if("jzlib".equals(encoding) || "deflate".equals(encoding)) {
			return inflate();
		}
		// TODO gzip
		return identityEncodingByteBuffer();
	}
	
	public static Operator<byte[], byte[]> compress(String encoding) {
		if(encoding==null) {
			return identityEncoding();
		}
		
		if("jzlib".equals(encoding) || "deflate".equals(encoding)) {
			try {
				return deflate();
			} catch (FileNotFoundException e) {
				logger.error("Error: ", e);
			}
		}
		// TODO gzip
		return identityEncoding();
	}
	
	public static Operator<byte[], byte[]> collect() {
		return new Operator<byte[], byte[]>(){
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			@Override
			public Subscriber<? super byte[]> call(Subscriber<? super byte[]> sub) {
				return new Subscriber<byte[]>() {

					@Override
					public void onCompleted() {
						sub.onNext(baos.toByteArray());
						sub.onCompleted();
					}

					@Override
					public void onError(Throwable t) {
						sub.onError(t);
					}

					@Override
					public void onNext(byte[] b) {
						try {
							baos.write(b);
						} catch (IOException e) {
							sub.onError(e);
						}
					}
				};
			}};
	}
	
	
	public static Operator<byte[], byte[]> deflate() throws FileNotFoundException {
		final FileOutputStream fos = new FileOutputStream("/Users/frank/uncompressed.xml");

		return new Operator<byte[], byte[]>(){

			Deflater deflater = new Deflater();
			int out = 0;
			@Override
			public Subscriber<? super byte[]> call(Subscriber<? super byte[]> sub) {
				return new Subscriber<byte[]>() {

					@Override
					public void onCompleted() {
						deflater.finish();
//						deflater.end();
						// TODO check for remaining?
						onNext(new byte[]{});
						System.err.println("Deflate completed. Total: "+deflater.getTotalIn()+" out: "+deflater.getTotalOut() + " -> "+out);
						try {
							fos.flush();
							fos.close();
						} catch (IOException e) {
							logger.error("Error: ", e);
						}
						sub.onCompleted();
					}

					@Override
					public void onError(Throwable e) {
						sub.onError(e);
					}

					@Override
					public void onNext(byte[] in) {
						deflater.setInput(in);
						byte[] buffer = new byte[1024];
						int read;
//						System.err.println("Deflating : "+new String(in));
						while(!deflater.needsInput()) {
							read = deflater.deflate(buffer,0,buffer.length,Deflater.SYNC_FLUSH);
//							System.err.println("Deflated size: "+read);
							out += read;
							if(read>0) {
								if(read == buffer.length) {
									sub.onNext(buffer);
									try {
										fos.write(buffer);
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								} else {
									byte[] copied = Arrays.copyOfRange(buffer, 0, read);
									try {
										fos.write(copied);
									} catch (IOException e) {
										logger.error("Error: ", e);
									}
									sub.onNext(copied);
								}
							}
						}
						
					}
				};
			}
		};
    }
	

	
//
//	public static Operator<byte[], byte[]> deflate_jzlib() throws FileNotFoundException {
//		final FileOutputStream fos = new FileOutputStream("/Users/frank/uncompressed.xml");
//
//		return new Operator<byte[], byte[]>(){
//
//			com.jcraft.jzlib.Deflater deflater = new com.jcraft.jzlib.Deflater();
//			int out = 0;
//			@Override
//			public Subscriber<? super byte[]> call(Subscriber<? super byte[]> sub) {
//				return new Subscriber<byte[]>() {
//
//					@Override
//					public void onCompleted() {
//						deflater.finish();
////						deflater.
////						deflater.end();
//						// TODO check for remaining?
//						onNext(new byte[]{});
//						System.err.println("Deflate completed. Total: "+deflater.getTotalIn()+" out: "+deflater.getTotalOut() + " -> "+out);
//						try {
//							fos.flush();
//							fos.close();
//						} catch (IOException e) {
//							logger.error("Error: ", e);
//						}
//						sub.onCompleted();
//					}
//
//					@Override
//					public void onError(Throwable e) {
//						sub.onError(e);
//					}
//
//					@Override
//					public void onNext(byte[] in) {
//						deflater.setInput(in);
//						byte[] buffer = new byte[1024];
//						int read;
//						while(!deflater.needsInput()) {
//							read = deflater.deflate(buffer,0,buffer.length,Deflater.SYNC_FLUSH);
//							out += read;
//							if(read>0) {
//								if(read == buffer.length) {
//									sub.onNext(buffer);
//									try {
//										fos.write(buffer);
//									} catch (IOException e) {
//										// TODO Auto-generated catch block
//										e.printStackTrace();
//									}
//								} else {
//									byte[] copied = Arrays.copyOfRange(buffer, 0, read);
//									try {
//										fos.write(copied);
//									} catch (IOException e) {
//										logger.error("Error: ", e);
//									}
//									sub.onNext(copied);
//								}
//							}
//						}
//						
//					}
//				};
//			}
//		};
//    }
	
    public static Operator<ByteBuffer, ByteBuffer> inflate() {
		return new Operator<ByteBuffer,ByteBuffer>(){

			Inflater inflater = new Inflater();
			@Override
			public Subscriber<? super ByteBuffer> call(Subscriber<? super ByteBuffer> sub) {
				return new Subscriber<ByteBuffer>() {

					@Override
					public void onCompleted() {
//						inflater.getRemaining()
						inflater.end();
						int remaining = inflater.getRemaining();
						if(remaining>0) {
							byte[] rm = new byte[remaining];
							sub.onNext(ByteBuffer.wrap(rm));
						}
						sub.onCompleted();
					}

					@Override
					public void onError(Throwable e) {
						sub.onError(e);
					}

					@Override
					public void onNext(ByteBuffer in) {
						inflater.setInput(in.array());
						byte[] buffer = new byte[1024];
						int read;
						try {
							while(!inflater.needsInput()) {
								read = inflater.inflate(buffer);
								if(read>0) {
									if(read == buffer.length) {
										sub.onNext(ByteBuffer.wrap(buffer));
									} else {
										sub.onNext(ByteBuffer.wrap(Arrays.copyOfRange(buffer, 0, read)));
									}
								}
							}
						} catch (DataFormatException e) {
							logger.error("Error: ", e);
							sub.onError(e);
						}
						
					}
				};
			}
		};
    }
	
    
    public static Operator<ByteBuffer, ByteBuffer> identityEncodingByteBuffer() {
  		return new Operator<ByteBuffer,ByteBuffer>(){

  			@Override
  			public Subscriber<? super ByteBuffer> call(Subscriber<? super ByteBuffer> sub) {
  				return new Subscriber<ByteBuffer>() {

  					@Override
  					public void onCompleted() {
  						sub.onCompleted();
  					}

  					@Override
  					public void onError(Throwable e) {
  						sub.onError(e);
  					}

  					@Override
  					public void onNext(ByteBuffer in) {
  						sub.onNext(in);
  					}
  				};
  			}
  		};
      }
    
    
    public static Operator<byte[], byte[]> identityEncoding() {
  		return new Operator<byte[],byte[]>(){

  			@Override
  			public Subscriber<? super byte[]> call(Subscriber<? super byte[]> sub) {
  				return new Subscriber<byte[]>() {

  					@Override
  					public void onCompleted() {
  						sub.onCompleted();
  					}

  					@Override
  					public void onError(Throwable e) {
  						sub.onError(e);
  					}

  					@Override
  					public void onNext(byte[] in) {
  						sub.onNext(in);
  					}
  				};
  			}
  		};
      }


}
