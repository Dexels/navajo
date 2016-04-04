package com.dexels.navajo.document.stream.io;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import rx.Observable;

public class ObservableStreams {

	
	private final static Logger logger = LoggerFactory.getLogger(ObservableStreams.class);

	
	public static Observable<byte[]> streamInputStreamWithBufferSize(InputStream in, int size) {
		ReadableByteChannel channel = Channels.newChannel(in);
		final ByteBuffer bytes = ByteBuffer.allocate(size);

		return streamChannelWithByteBuffer(channel, bytes);
	}

	public static Observable<byte[]> streamChannelWithByteBuffer(ReadableByteChannel channel,
			final ByteBuffer bytes) {
		return Observable.<byte[]>defer(()-> Observable.from(new Iterable<byte[]>(){
			@Override
			public Iterator<byte[]> iterator() {
				return new Iterator<byte[]>() {
					private byte[] nextBuffer = null;

					@Override
					public boolean hasNext() {
						int read=0;
						try {
							if(nextBuffer!=null) {
								return true;
							}
							if(!channel.isOpen()) {
								channel.close();
								return false;
							}
							read = channel.read(bytes);
							if(read<0) {
								channel.close();
								return false;
							}
							loadBuffer(bytes, read);
							return true;
						} catch (IOException e) {
							logger.error("Error: ", e);
							throw new RuntimeException("Error iterating",e);
						} finally {

						}
					}

					private void loadBuffer(final ByteBuffer bytes, int read) {
						byte[] result = new byte[read];
						bytes.flip();
						bytes.get(result);
						setNext(result);
						bytes.flip();
						bytes.clear();
					}

					@Override
					public byte[] next() {
//						nextBuffer.
						byte[] result = nextBuffer;
						setNext(null);
						return result;
					}
					
					private void setNext(byte[] buffer) {
						this.nextBuffer = buffer;
					}
				};

			}}));
	}

	public static Iterator<ByteBuffer> streamToIterator(InputStream in) {
		ReadableByteChannel channel = Channels.newChannel(in);
		final ByteBuffer bytes = ByteBuffer.allocate(1024);
		return new Iterator<ByteBuffer>() {

					
					private ByteBuffer nextBuffer;

					@Override
					public boolean hasNext() {
						int read;
						try {
							read = channel.read(bytes);
//							System.err.println("Read: "+read+" bytes");
							if(read<0) {
								return false;
							}
							byte[] result = new byte[read];
							this.nextBuffer = bytes.get(result);
							bytes.flip();
							return true;
						} catch (IOException e) {
							logger.error("Error: ", e);
							return false;
						}
					}

					@Override
					public ByteBuffer next() {
						return nextBuffer;
					}
					
				};
	}

}
