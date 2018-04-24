package com.dexels.navajo.document.stream;

import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;
import java.util.zip.ZipException;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.stream.io.BaseFlowableOperator;

import io.reactivex.Flowable;
import io.reactivex.FlowableOperator;
import io.reactivex.FlowableTransformer;

public class StreamCompress {

	private enum CompressionType {GZIP,DEFLATE}

	private final static Logger logger = LoggerFactory.getLogger(StreamCompress.class);

	   /**
     * GZIP header magic number.
     */
    private final static int GZIP_MAGIC = 0x8b1f;

    /*
     * File header flags.
     */
//    private final static int FTEXT      = 1;    // Extra text
    private final static int FHCRC      = 2;    // Header CRC
    private final static int FEXTRA     = 4;    // Extra field
    private final static int FNAME      = 8;    // File name
    private final static int FCOMMENT   = 16;   // File comment

    /*
     * Reads GZIP member header and returns the total byte number
     * of this member header.
     */
    private static int readHeader(InputStream this_in,CRC32 crc) throws IOException, EOFException {
    	
        CheckedInputStream in = new CheckedInputStream(this_in, crc);
        crc.reset();
        // Check header magic
        int magic = readUShort(in);
		if (magic != GZIP_MAGIC) {
            throw new ZipException("Not in GZIP format. Expected: 8b1f, was: "+magic);
        }
        // Check compression method
        if (readUByte(in) != 8) {
            throw new ZipException("Unsupported compression method");
        }
        // Read flags
        int flg = readUByte(in);
        // Skip MTIME, XFL, and OS fields
        skipBytes(in, 6);
        int n = 2 + 2 + 6;
        // Skip optional extra field
        if ((flg & FEXTRA) == FEXTRA) {
            int m = magic;
            skipBytes(in, m);
            n += m + 2;
        }
        // Skip optional file name
        if ((flg & FNAME) == FNAME) {
            do {
                n++;
            } while (readUByte(in) != 0);
        }
        // Skip optional file comment
        if ((flg & FCOMMENT) == FCOMMENT) {
            do {
                n++;
            } while (readUByte(in) != 0);
        }
        // Check optional header CRC
        if ((flg & FHCRC) == FHCRC) {
            int v = (int)crc.getValue() & 0xffff;
            if (magic != v) {
                throw new ZipException("Corrupt GZIP header");
            }
            n += 2;
        }
        crc.reset();
        return n;
    }

    private static int readUShort(InputStream in) throws IOException {
        int b = readUByte(in);
        return (readUByte(in) << 8) | b;
    }

    /*
     * Reads unsigned byte.
     */
    private static int readUByte(InputStream in) throws IOException {
        int b = in.read();
        if (b == -1) {
            throw new EOFException();
        }
        if (b < -1 || b > 255) {
            // Report on this.in, not argument in; see read{Header, Trailer}.
            throw new IOException(in.getClass().getName()
                + ".read() returned value out of range -1..255: " + b);
        }
        return b;
    }


    /*
     * Skips bytes of input data blocking until all bytes are skipped.
     * Does not assume that the input stream is capable of seeking.
     */
    private static void skipBytes(InputStream in, int n) throws IOException {
        byte[] tmpbuf = new byte[128];
        while (n > 0) {
            int len = in.read(tmpbuf, 0, n < tmpbuf.length ? n : tmpbuf.length);
            if (len == -1) {
                throw new EOFException();
            }
            n -= len;
        }
    }

    /*
     * Writes GZIP member header.
     *
     */
//    private final static int TRAILER_SIZE = 8;
    
    private static byte[] writeHeader()  {
    	byte[] result = new byte[] {
                      (byte) GZIP_MAGIC,        // Magic number (short)
                      (byte)(GZIP_MAGIC >> 8),  // Magic number (short)
                      Deflater.DEFLATED,        // Compression method (CM)
                      0,                        // Flags (FLG)
                      0,                        // Modification time MTIME (int)
                      0,                        // Modification time MTIME (int)
                      0,                        // Modification time MTIME (int)
                      0,                        // Modification time MTIME (int)
                      0,                        // Extra flags (XFLG)
                      0                         // Operating system (OS)
                  };
    	return result;
    }

    
    private static byte[] writeTrailer(int crcValue, int totalIn) {
    	final ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.putInt(crcValue);
        buffer.putInt(totalIn);
        return buffer.array();
    }

	public static FlowableTransformer<byte[], byte[]> decompress(Optional<String> encodingHint) {
		return new FlowableTransformer<byte[], byte[]>(){

			@Override
			public Publisher<byte[]> apply(Flowable<byte[]> f) {
				if(!encodingHint.isPresent()) {
					return f;
				}
				String encoding = encodingHint.get();
				if("jzlib".equals(encoding) || "deflate".equals(encoding) || "inflate".equals(encoding)) {
					return f.lift(genericDecompress2(CompressionType.DEFLATE, false)).concatMap(e->e);
				} else if("gzip".equals(encoding)) {
					return f.lift(genericDecompress2(CompressionType.GZIP, false)).concatMap(e->e);
				} else {
					return f;
				}
				// TODO gzip
			}};
	}
	
	public static FlowableTransformer<byte[], byte[]> deflate() {
		return compress(Optional.of("deflate"));
	}

	public static FlowableTransformer<byte[], byte[]> gzip() {
		return compress(Optional.of("gzip"));
	}

	public static FlowableTransformer<byte[], byte[]> inflate() {
		return decompress(Optional.of("deflate"));
	}

	public static FlowableTransformer<byte[], byte[]> gunzip() {
		return decompress(Optional.of("gzip"));
	}

	
	
//	public static FlowableOperator<Flowable<byte[]>, byte[]> genericCompress(String type, boolean debug) {
//		final int COMPRESSION_BUFFER_SIZE = 16384;
//		
//		Deflater deflater = type.equals("gzip") ? new Deflater(Deflater.DEFAULT_COMPRESSION, true) : new Deflater();
//
//		return new BaseFlowableOperator<Flowable<byte[]>, byte[]>(10) {
//
//			@Override
//			public Subscriber<? super byte[]> apply(Subscriber<? super Flowable<byte[]>> child) throws Exception {
//				
//				final CRC32 crc = new CRC32();
//				AtomicInteger totalIn = new AtomicInteger();
//				
//				return new Subscriber<byte[]>() {
//
//					private OutputStream debugDump = null;
//					@Override
//					public void onComplete() {
//
//						deflater.finish();
//						
//						byte[] buffer = new byte[COMPRESSION_BUFFER_SIZE];
//						Iterable<byte[]> output = new Iterable<byte[]>(){
//
//								@Override
//								public Iterator<byte[]> iterator() {				
//									return new Iterator<byte[]>() {
//										int read;
//										boolean first = true;
//										@Override
//										public boolean hasNext() {
//											read = deflater.deflate(buffer,0,buffer.length,FLUSH_MODE);
//											boolean needsInput = deflater.needsInput();
//											boolean hasMore = (first || needsInput) && read > 0;
//											first = false;
//											return hasMore;
//										}
//
//										@Override
//										public byte[] next() {
//											return Arrays.copyOfRange(buffer, 0, read);
//										}
//									};
//								}
//						};
//						
//						List<byte[]> items = new ArrayList<>();
//						output.forEach(items::add);
//
//						byte[] suffix = writeTrailer((int) crc.getValue(), deflater.getTotalIn());
//						if("gzip".equals(type)) {
//							queue.offer(Flowable.fromIterable(items).concatWith(Flowable.just(suffix)));
//						} else {
//							queue.offer(Flowable.fromIterable(items));
//						}
//						operatorComplete(child);
//					}
//
//					@Override
//					public void onError(Throwable t) {
//						if(debug) {
//							try {
//								debugDump.close();
//							} catch (IOException e) {
//								e.printStackTrace();
//							}
//						}
//
//						operatorError(t, child);
//					}
//
//					@Override
//					public void onSubscribe(Subscription s) {
//						operatorSubscribe(s, child);
//						if("gzip".equals(type)) {
//							byte[] hdr = writeHeader();
//							totalIn.addAndGet(hdr.length);
//							offer(Flowable.just(hdr));
//						}
//					}
//					
//					@Override
//					public void onNext(byte[] dataIn) {
//						crc.update(dataIn);
//						totalIn.addAndGet(dataIn.length);
//						deflater.setInput(dataIn);
//						byte[] buffer = new byte[COMPRESSION_BUFFER_SIZE];
//						Iterator<byte[]> it =  new Iterator<byte[]>() {
//							int read;
//							boolean first = true;
//							@Override
//							public boolean hasNext() {
//								read = deflater.deflate(buffer,0,buffer.length,FLUSH_MODE);
//								boolean needsInput = deflater.needsInput();
//								boolean hasMore = (first || needsInput) && read > 0;
//								first = false;
//								return hasMore;
//							}
//
//							@Override
//							public byte[] next() {
//								byte[] compressedData = Arrays.copyOfRange(buffer, 0, read);
//								return compressedData;
//							}
//						};
//						List<byte[]> result = new ArrayList<byte[]>();
//						it.forEachRemaining(result::add);
//						queue.offer(Flowable.fromIterable(result));
//						drain(child);
//					}	
//				};
//			}
//		};
//	}
//	
	
	private static FlowableOperator<Flowable<byte[]>, byte[]> genericCompress2(CompressionType type, boolean debug) {
		final int COMPRESSION_BUFFER_SIZE = 4096;
		
		Deflater deflater = type == CompressionType.GZIP ? new Deflater(Deflater.DEFAULT_COMPRESSION, true) : new Deflater();

		return new BaseFlowableOperator<Flowable<byte[]>, byte[]>(10) {

			@Override
			public Subscriber<? super byte[]> apply(Subscriber<? super Flowable<byte[]>> child) throws Exception {
				
				final CRC32 crc = new CRC32();
				AtomicInteger totalIn = new AtomicInteger();
				
				return new Subscriber<byte[]>() {

					private OutputStream debugDump = null;
					@Override
					public void onComplete() {

						deflater.finish();
						
						byte[] buffer = new byte[COMPRESSION_BUFFER_SIZE];
						List<byte[]> output = new ArrayList<>();
						int read = 0;
						do {
//							read = deflater.deflate(buffer,0,buffer.length,Deflater.FULL_FLUSH);
							read = deflater.deflate(buffer);
							byte[] compressedData = Arrays.copyOfRange(buffer, 0, read);
							output.add(compressedData);
						} while(read > 0);

						byte[] suffix = writeTrailer((int) crc.getValue(), deflater.getTotalIn());
						if(type ==CompressionType.GZIP) {
							queue.offer(Flowable.fromIterable(output).concatWith(Flowable.just(suffix)));
						} else {
							queue.offer(Flowable.fromIterable(output));
						}
						operatorComplete(child);
					}

					@Override
					public void onError(Throwable t) {
						if(debug) {
							try {
								debugDump.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}

						operatorError(t, child);
					}

					@Override
					public void onSubscribe(Subscription s) {
						operatorSubscribe(s, child);
						if(type==CompressionType.GZIP) {
							byte[] hdr = writeHeader();
							totalIn.addAndGet(hdr.length);
							offer(Flowable.just(hdr));
						}
					}
					
					@Override
					public void onNext(byte[] dataIn) {
						crc.update(dataIn);
						totalIn.addAndGet(dataIn.length);
						deflater.setInput(dataIn);
						List<byte[]> output = new ArrayList<>();
						byte[] buffer = new byte[COMPRESSION_BUFFER_SIZE];
						int read = 0;
						do {
							read = deflater.deflate(buffer);
							byte[] compressedData = Arrays.copyOfRange(buffer, 0, read);
							output.add(compressedData);
						} while(read > 0);
						queue.offer(Flowable.fromIterable(output));
						drain(child);
					}	
				};
			}
		};
	}

	private static FlowableOperator<Flowable<byte[]>, byte[]> genericDecompress2(CompressionType type, boolean debug) {
		final int COMPRESSION_BUFFER_SIZE = 4096;
		
		Inflater inflater = type == CompressionType.GZIP ? new Inflater(true) : new Inflater();
		CRC32 crc = new CRC32();
		AtomicBoolean first = new AtomicBoolean(true);

		return new BaseFlowableOperator<Flowable<byte[]>, byte[]>(1) {

			
			@Override
			public Subscriber<? super byte[]> apply(Subscriber<? super Flowable<byte[]>> child) throws Exception {
				
//				final CRC32 crc = new CRC32();
//				AtomicInteger totalIn = new AtomicInteger();
				
				return new Subscriber<byte[]>() {

					private OutputStream debugDump = null;
					private final List<byte[]> stashedData = new ArrayList<>();
					@Override
					public void onComplete() {

//						inflater.finish();
						
						byte[] buffer = new byte[COMPRESSION_BUFFER_SIZE];
						List<byte[]> output = new ArrayList<>();
						int read = 0;
						try {
							do {
								read = inflater.inflate(buffer);
								byte[] compressedData = Arrays.copyOfRange(buffer, 0, read);
								output.add(compressedData);
							} while (read > 0);
						} catch (DataFormatException e) {
							e.printStackTrace();
						}

//						byte[] suffix = writeTrailer((int) crc.getValue(), deflater.getTotalIn());
//						if(type ==CompressionType.GZIP) {
//							queue.offer(Flowable.fromIterable(output).concatWith(Flowable.just(suffix)));
//						} else {
							queue.offer(Flowable.fromIterable(output));
//						}
						operatorComplete(child);
					}

					@Override
					public void onError(Throwable t) {
						if(debug) {
							try {
								debugDump.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}

						operatorError(t, child);
					}

					@Override
					public void onSubscribe(Subscription s) {
						operatorSubscribe(s, child);
						if(type==CompressionType.GZIP) {
//							byte[] hdr = writeHeader();
//							totalIn.addAndGet(hdr.length);
//							offer(Flowable.just(hdr));
						}
					}
					
					@Override
					public void onNext(byte[] data) {
						if(first.get() && CompressionType.GZIP == type) {
							byte[] joinedData;
							if(!stashedData.isEmpty()) {
								int totalSize = stashedData.stream().map(b->b.length).collect(Collectors.summingInt(i->i));
								joinedData = new byte[totalSize];
								int count = 0;
								for (byte[] e : stashedData) {
									System.arraycopy(e, 0, joinedData, count, e.length);
									count+=e.length;
								}
							} else {
								joinedData = data;
							}
							ByteArrayInputStream bais = new ByteArrayInputStream(joinedData);
							int offset = 0;
							try {
								offset = readHeader(bais, crc);
								first.set(false);
								if(joinedData.length-offset>0) {
									inflater.setInput(joinedData,offset,joinedData.length-offset);
								} else {
									operatorRequest(1);
									return;
								}
							} catch(EOFException e2) {
//								stashedData.add(data);
								e2.printStackTrace();
								operatorError(e2, child);
//								operatorRequest(1);
								return;
							} catch (IOException e1) {
								e1.printStackTrace();
								operatorError(e1, child);
							}
						} else {
							inflater.setInput(data);
						}
												
						
						
						
						try {
//							inflater.setInput(data);
							List<byte[]> output = new ArrayList<>();
							byte[] buffer = new byte[COMPRESSION_BUFFER_SIZE];
							int read = 0;
							do {
								read = inflater.inflate(buffer);
								byte[] compressedData = Arrays.copyOfRange(buffer, 0, read);
								output.add(compressedData);
							} while (read > 0);
							queue.offer(Flowable.fromIterable(output));
							drain(child);
						} catch (DataFormatException e) {
							e.printStackTrace();
							onError(e);
						}
					}
				};
			}
		};
	}

	
	
	
	public static FlowableTransformer<byte[], byte[]> compress(Optional<String> encodingDefinition) {
		return new FlowableTransformer<byte[], byte[]>(){

			@Override
			public Publisher<byte[]> apply(Flowable<byte[]> f) {
				if(!encodingDefinition.isPresent()) {
					return f;
				}
				String encoding = encodingDefinition.get();
				if("jzlib".equals(encoding) || "deflate".equals(encoding) || "inflate".equals(encoding)) {
					return f.lift(genericCompress2(CompressionType.DEFLATE,false)).concatMap(e->e);
				}
				if("gzip".equals(encoding)) {
					return f.lift(genericCompress2(CompressionType.GZIP, false)).concatMap(e->e);
				}			
				return f;
			}};
	}

}
    
