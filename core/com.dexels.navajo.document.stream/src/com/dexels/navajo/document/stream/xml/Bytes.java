package com.dexels.navajo.document.stream.xml;

import java.io.InputStream;

import com.dexels.navajo.document.stream.io.ObservableStreams;

import rx.Observable;

public class Bytes {
	public static Observable<byte[]> fromAbsoluteClassPathBuffer(String resource) {
			return ObservableStreams.streamInputStreamWithBufferSize(loadFromAbsoluteClassPath(resource), 1024);
	}
	
	private static InputStream loadFromAbsoluteClassPath(String resource) {
				return Bytes.class.getClassLoader().getResourceAsStream(resource);
	}
	
	public static Observable<byte[]> fromAbsoluteClassPath(String resource) {
		return ObservableStreams.streamInputStreamWithBufferSize(loadFromAbsoluteClassPath(resource), 1024);
}

}
