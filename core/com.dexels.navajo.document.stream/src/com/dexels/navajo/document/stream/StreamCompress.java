package com.dexels.navajo.document.stream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;
import java.util.zip.ZipException;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import com.dexels.navajo.document.stream.io.BaseFlowableOperator;

import io.reactivex.Flowable;
import io.reactivex.FlowableOperator;

public class StreamCompress {

	
	private static void dump(byte[] data) {
		try {
			OutputStream unz = new FileOutputStream("unzip",true);
			unz.write(data);
			unz.flush();
			unz.close();
		} catch (IOException e3) {
			e3.printStackTrace();
		}
	}

	public static FlowableOperator<Flowable<byte[]>, byte[]> gunzip() {
		Inflater inflater = new Inflater(true);
		CRC32 crc = new CRC32();
		AtomicBoolean first = new AtomicBoolean(true);

		return new BaseFlowableOperator<Flowable<byte[]>, byte[]>(10) {

			@Override
			public Subscriber<? super byte[]> apply(Subscriber<? super Flowable<byte[]>> child) throws Exception {
				return new Subscriber<byte[]>(){

					final int COMPRESSION_BUFFER_SIZE = 16384;
					byte[] buffer = new byte[COMPRESSION_BUFFER_SIZE];
					private final List<byte[]> stashedData = new ArrayList<>();
					
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
						if(first.get()) {
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
//							System.err.println("# of data: "+data.length);
							ByteArrayInputStream bais = new ByteArrayInputStream(joinedData);
							int offset = 0;
							try {
								offset = readHeader(bais, crc);
								System.err.println("Offset: "+offset);
								first.set(false);
								if(joinedData.length-offset>0) {
									dump(Arrays.copyOfRange(joinedData, offset, joinedData.length));
									inflater.setInput(joinedData,offset,joinedData.length-offset);
								} else {
									operatorRequest(1);
									return;
								}
							} catch(EOFException e2) {
								stashedData.add(data);
								operatorRequest(1);
								return;
							} catch (IOException e1) {
								e1.printStackTrace();
								operatorError(e1, child);
							}
						} else {
							System.err.println("Input length: "+data.length);
							dump(data);

							inflater.setInput(data);
						}
						
						Iterable<byte[]> output = new Iterable<byte[]>(){

								@Override
								public Iterator<byte[]> iterator() {
									return new Iterator<byte[]>(){
										int read;
										boolean first = true;

										@Override
										public boolean hasNext() {
											boolean needsInput = inflater.needsInput();
//											System.err.println("Needs input? "+needsInput);
											if(needsInput) {
												System.err.println("Needs input, so ignoring");
												return false;
											}
											try {
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

	
	
	
	public static FlowableOperator<Flowable<byte[]>, byte[]> gzip() {
		Deflater deflater = new Deflater(Deflater.DEFAULT_COMPRESSION, true);
		final CRC32 crc = new CRC32();
//		deflater.setInput(writeHeader());
		return new BaseFlowableOperator<Flowable<byte[]>, byte[]>(10) {

			@Override
			public Subscriber<? super byte[]> apply(Subscriber<? super Flowable<byte[]>> child) throws Exception {
				
				
				return new Subscriber<byte[]>() {
					final int COMPRESSION_BUFFER_SIZE = 16384;
					byte[] buffer = new byte[COMPRESSION_BUFFER_SIZE];

					@Override
					public void onComplete() {
//						byte[] suffix = writeTrailer((int) crc.getValue(), deflater.getTotalIn());

						deflater.finish();
						
						byte[] buffer = new byte[COMPRESSION_BUFFER_SIZE];
						Flowable<byte[]> flowable = Flowable.fromIterable(new Iterable<byte[]>(){

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
						byte[] finish;
						try {
							finish = finish(deflater, buffer, crc);
							queue.offer(flowable.concatWith(Flowable.just(finish)));
							operatorComplete(child);
						} catch (IOException e) {
							operatorError(e, child);
						}
					}

					@Override
					public void onError(Throwable t) {
						operatorError(t, child);
					}

					@Override
					public void onNext(byte[] dataIn) {
						deflater.setInput(dataIn);
						crc.update(dataIn);
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
						offer(Flowable.just(writeHeader()));
					}
				};
			}
		};
	}
	
	
	   /**
     * GZIP header magic number.
     */
    public final static int GZIP_MAGIC = 0x8b1f;

    /*
     * File header flags.
     */
    private final static int FTEXT      = 1;    // Extra text
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
        if (readUShort(in) != GZIP_MAGIC) {
            throw new ZipException("Not in GZIP format");
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
            int m = readUShort(in);
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
            if (readUShort(in) != v) {
                throw new ZipException("Corrupt GZIP header");
            }
            n += 2;
        }
        crc.reset();
        System.err.println("Header complete");
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
    
    
    static <T> T[] joinArrayGeneric(List<T[]> arrays) {
        int length = 0;
        for (T[] array : arrays) {
            length += array.length;
        }

        //T[] result = new T[length];
        final T[] result = (T[]) Array.newInstance(arrays.get(0).getClass().getComponentType(), length);

        int offset = 0;
        for (T[] array : arrays) {
            System.arraycopy(array, 0, result, offset, array.length);
            offset += array.length;
        }

        return result;
    }
    
    
    
    

    /**
     * Finishes writing compressed data to the output stream without closing
     * the underlying stream. Use this method when applying multiple filters
     * in succession to the same output stream.
     * @exception IOException if an I/O error has occurred
     */
    public static byte[] finish(Deflater def, byte[] buf, CRC32 crc) throws IOException {
    		ByteArrayOutputStream baos = new ByteArrayOutputStream();
        if (!def.finished()) {
            def.finish();
            while (!def.finished()) {
                int len = def.deflate(buf, 0, buf.length);
                if (def.finished() && len <= buf.length - TRAILER_SIZE) {
                    // last deflater buffer. Fit trailer at the end
                    writeTrailer(buf, len,def,crc);
                    len = len + TRAILER_SIZE;
                    baos.write(buf, 0, len);
                    return baos.toByteArray();
                }
                if (len > 0)
                    baos.write(buf, 0, len);
            }
            // if we can't fit the trailer at the end of the last
            // deflater buffer, we write it separately
            byte[] trailer = new byte[TRAILER_SIZE];
            writeTrailer(trailer, 0,def,crc);
            baos.write(trailer);
            return baos.toByteArray();
        }
        return new byte[] {};
    }

    /*
     * Writes GZIP member header.
     *
     */
    private final static int TRAILER_SIZE = 8;
    
    private static byte[] writeHeader()  {
        return new byte[] {
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
    }

    /*
     * Writes GZIP member trailer to a byte array, starting at a given
     * offset.
     */
    private static void writeTrailer(byte[] buf, int offset, Deflater def, CRC32 crc) throws IOException {
        writeInt((int)crc.getValue(), buf, offset); // CRC-32 of uncompr. data
        writeInt(def.getTotalIn(), buf, offset + 4); // Number of uncompr. bytes
    }

    /*
     * Writes integer in Intel byte order to a byte array, starting at a
     * given offset.
     */
    private static void writeInt(int i, byte[] buf, int offset) throws IOException {
        writeShort(i & 0xffff, buf, offset);
        writeShort((i >> 16) & 0xffff, buf, offset + 2);
    }

    /*
     * Writes short integer in Intel byte order to a byte array, starting
     * at a given offset
     */
    private static void writeShort(int s, byte[] buf, int offset) throws IOException {
        buf[offset] = (byte)(s & 0xff);
        buf[offset + 1] = (byte)((s >> 8) & 0xff);
    }


}
    
