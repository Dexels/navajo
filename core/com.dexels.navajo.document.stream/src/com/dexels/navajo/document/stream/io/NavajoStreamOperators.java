package com.dexels.navajo.document.stream.io;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.stream.api.Msg;
import com.dexels.navajo.document.stream.api.NavajoHead;
import com.dexels.navajo.document.stream.events.Events;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent;

import rx.Observable;
import rx.Observable.Operator;
import rx.Observable.Transformer;
import rx.Subscriber;
import rx.schedulers.Schedulers;

public class NavajoStreamOperators {
	
	
	private static final int COMPRESSION_BUFFER_SIZE = 16384;
//	private static final int COMPRESSION_BUFFER_SIZE = 1024;
	private final static Logger logger = LoggerFactory.getLogger(NavajoStreamOperators.class);

	
	<T> Transformer<T, T> applySchedulers() {  
	    return observable -> observable.subscribeOn(Schedulers.io())
	        .observeOn(Schedulers.newThread());
	}
	
	public static Transformer<NavajoStreamEvent, NavajoStreamEvent> inArray(String name) {
		return new Transformer<NavajoStreamEvent, NavajoStreamEvent>() {

			@Override
			public Observable<NavajoStreamEvent> call(Observable<NavajoStreamEvent> in) {
	        	return in.startWith(Observable.just(Events.arrayStarted(name,Collections.emptyMap())))
	        	.concatWith(Observable.just(Events.arrayDone(name)));
			}
		};
	}
	
	public static Transformer<NavajoStreamEvent, NavajoStreamEvent> inMessage(String name) {
		return new Transformer<NavajoStreamEvent, NavajoStreamEvent>() {

			@Override
			public Observable<NavajoStreamEvent> call(Observable<NavajoStreamEvent> in) {
	        	return in.startWith(Observable.just(Events.messageStarted(name,null)))
	        	.concatWith(Observable.just(Events.message(Msg.create(), name,null)));
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

	
	public static Operator<NavajoStreamEvent, NavajoStreamEvent> elementsInPath(String path) {
		return new Operator<NavajoStreamEvent, NavajoStreamEvent>(){

			@Override
			public Subscriber<? super NavajoStreamEvent> call(Subscriber<? super NavajoStreamEvent> out) {
				return new Subscriber<NavajoStreamEvent>() {

					@Override
					public void onCompleted() {
						out.onCompleted();
					}

					@Override
					public void onError(Throwable e) {
						out.onError(e);
						
					}

					@Override
					public void onNext(NavajoStreamEvent event) {
						out.onNext(event);
						
					}
				};
			}};
		
	}
	public static Operator<byte[], byte[]> decompress(String encoding) {
//		logger.info("Starting decompress with encoding: {}",encoding);
		if("jzlib".equals(encoding) || "deflate".equals(encoding) || "inflate".equals(encoding)) {
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
//		final FileOutputStream fos = new FileOutputStream("/Users/frank/uncompressed.xml");

		return new Operator<byte[], byte[]>(){

			Deflater deflater = new Deflater();
//			int out = 0;
			@Override
			public Subscriber<? super byte[]> call(Subscriber<? super byte[]> sub) {
				return new Subscriber<byte[]>() {

					@Override
					public void onCompleted() {
						deflater.finish();
						onNext(new byte[]{});
						sub.onCompleted();
					}

					@Override
					public void onError(Throwable e) {
						sub.onError(e);
					}

					@Override
					public void onNext(byte[] in) {
						deflater.setInput(in);
						byte[] buffer = new byte[COMPRESSION_BUFFER_SIZE];
						int read;
//						System.err.println("Deflating : "+new String(in));
						while(true) {
							read = deflater.deflate(buffer,0,buffer.length,Deflater.NO_FLUSH);
							if(read>0) {
//								System.err.println("Deflated size: "+read);
//								out += read;
									byte[] copied = Arrays.copyOfRange(buffer, 0, read);
									sub.onNext(copied);
							} else {
								break;
							}
						}
						
					}
				};
			}
		};
    }
	
	public static Subscriber<byte[]> dumpToFile(final String path) {
		return new Subscriber<byte[]>() {
			
			FileOutputStream out = null;
			
			@Override
			public void onCompleted() {
				if(out!=null) {
					try {
						out.flush();
						out.close();
						unsubscribe();
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
						unsubscribe();
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
				} catch (IOException e) {
					logger.error("Error: ", e);
					unsubscribe();
				}
				
			}
		};
	}
    public static Operator<byte[], byte[]> inflate() {
    	System.err.println("Starting to inflate");
//    	FileOutputStream fos = null;
    	try {

		return new Operator<byte[],byte[]>(){

    		final FileOutputStream fos = new FileOutputStream("/Users/frank/dump_"+System.currentTimeMillis()+".xml");
			Inflater inflater = new Inflater();
			@Override
			public Subscriber<? super byte[]> call(Subscriber<? super byte[]> sub) {
				return new Subscriber<byte[]>() {

					@Override
					public void onCompleted() {
//						inflater.end();
						int remaining = inflater.getRemaining();
						if(remaining>0) {
							byte[] rm = new byte[remaining];
//							inflated = inflater.inflate(rm, 0, remaining);
							sub.onNext(rm);
							try {
								fos.write(rm);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						sub.onCompleted();
						try {
							fos.flush();
							fos.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

					@Override
					public void onError(Throwable e) {
						sub.onError(e);
						sub.unsubscribe();
					}

					@Override
					public void onNext(byte[] in) {
						inflater.setInput(in);
						byte[] buffer = new byte[COMPRESSION_BUFFER_SIZE];
						int read;
						try {
							while(!inflater.needsInput()) {
								read = inflater.inflate(buffer);
								if(read>0) {
									if(read == buffer.length) {
										sub.onNext(buffer);
										try {
											fos.write(buffer);
										} catch (IOException e) {
											e.printStackTrace();
										}
									} else {
										sub.onNext(Arrays.copyOfRange(buffer, 0, read));
										try {
											fos.write(Arrays.copyOfRange(buffer, 0, read));
										} catch (IOException e) {
											e.printStackTrace();
										}
									}
								}
							}
						} catch (DataFormatException e) {
							sub.onError(e);
							sub.unsubscribe();
						}
						
					}
				};
			}
		};
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			return null;
		}
    	
    }
	
    
    public static Operator<byte[], byte[]> identityEncodingByteBuffer() {
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
    
    
    public static Operator<byte[], byte[]> identityEncoding() {
  		return new Operator<byte[],byte[]>(){

  			@Override
  			public Subscriber<? super byte[]> call(Subscriber<? super byte[]> sub) {
  				return sub;
  			}
  		};
      }


}
