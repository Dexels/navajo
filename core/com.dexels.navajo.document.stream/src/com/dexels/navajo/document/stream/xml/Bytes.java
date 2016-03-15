package com.dexels.navajo.document.stream.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.stream.io.ObservableStreams;

import rx.Observable;

public class Bytes {
	
	private final static Logger logger = LoggerFactory.getLogger(Bytes.class);

	
	public static Observable<byte[]> fromAbsoluteClassPathBuffer(String resource) {
			return ObservableStreams.streamInputStreamWithBufferSize(loadFromAbsoluteClassPath(resource), 1024);
	}
	
	private static InputStream loadFromAbsoluteClassPath(String resource) {
				return Bytes.class.getClassLoader().getResourceAsStream(resource);
	}
	
	public static Observable<byte[]> fromAbsoluteClassPath(String resource) {
		return ObservableStreams.streamInputStreamWithBufferSize(loadFromAbsoluteClassPath(resource), 1024);
}
	public static Observable<byte[]> fromPath(String path) {
		File file = new File(path);
		if(!file.exists()) {
			return Observable.<byte[]>error(new FileNotFoundException("File missing: "+path));
		}
		try {
			InputStream fis = new FileInputStream(file);
			return ObservableStreams.streamInputStreamWithBufferSize(fis, 1024);
		} catch (FileNotFoundException e) {
			logger.error("Error: ", e);
			return Observable.<byte[]>error(e);
		}
	}

}
