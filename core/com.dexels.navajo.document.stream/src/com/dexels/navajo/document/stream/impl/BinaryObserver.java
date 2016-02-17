package com.dexels.navajo.document.stream.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.security.DigestOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.dexels.utils.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.types.Binary;

import rx.Observable.Operator;
import rx.Observer;
import rx.Subscriber;

public class BinaryObserver implements Operator<Binary,String>{

	private OutputStream fos;
	private Writer w;
	private transient MessageDigest messageDigest;
	private File dataFile;
	private byte[] digest;
	protected byte[] inMemory;

	
	private final static Logger logger = LoggerFactory.getLogger(BinaryObserver.class);

	public static Operator<Binary,String> collect() {
		return new BinaryObserver();
	}

	public BinaryObserver()  {
        try {
			fos = createTempFileOutputStream();
			w = Base64.newDecoder(fos);
		} catch (Exception e) {
			// TODO deal with this better
			logger.error("Error: ", e);
		}

	}
	@Override
	public Subscriber<? super String> call(Subscriber<? super Binary> outgoing) {
		return new Subscriber<String>() {

			@Override
			public void onCompleted() {
				try {
					w.flush();
					w.close();
					fos.flush();
					fos.close();
					Binary binary;
			    	if(NavajoFactory.getInstance().isSandboxMode()) {
			    		binary = new Binary(BinaryObserver.this.inMemory);
			    	} else {
			    		binary = new Binary(dataFile,true);
			    	}
			    	binary.setDigest(digest);
//			    	binary.setMimeType();
					outgoing.onNext(binary);
			    	outgoing.onCompleted();
				} catch (Throwable e) {
					outgoing.onError(e);
				}
			}

			@Override
			public void onError(Throwable t) {
				// propagate
				outgoing.onError(t);
			}

			@Override
			public void onNext(String line) {
				try {
					w.write(line);
				} catch (IOException e) {
					outgoing.onError(e);
				}
			}
		};
	}
	
	
	private OutputStream createTempFileOutputStream() throws IOException, FileNotFoundException {
		if(messageDigest==null) {
	        MessageDigest md = null;
			try {
				md = MessageDigest.getInstance("MD5");
			} catch (NoSuchAlgorithmException e1) {
				logger.warn("Failed creating messageDigest in binary. Expect problems", e1);
			}
			this.messageDigest = md;
		}
		messageDigest.reset();
    	if(NavajoFactory.getInstance().isSandboxMode()) {
    		ByteArrayOutputStream baos = new ByteArrayOutputStream() {
    			@Override
				public void close() {
    				try {
						super.close();
					} catch (IOException e) {
						logger.error("Error: ", e);
					}
	    			inMemory = toByteArray();
    			}
    		};
    		OutputStream dos = new DigestOutputStream(baos, messageDigest) {

				@Override
				public void close() throws IOException {
					setDigest(messageDigest.digest());
					super.close();
				}
    			
    		};
    		
    		return dos;
    	} else {
            dataFile = File.createTempFile("streamedbinary_object", "navajo", NavajoFactory.getInstance().getTempDir());
            
            FileOutputStream fos = new FileOutputStream(dataFile);
            OutputStream dos = new DigestOutputStream(fos, messageDigest) {

				@Override
				public void close() throws IOException {
					super.close();
					setDigest(messageDigest.digest());
				}
            	
            };
            return dos;
    	}
    }
	protected void setDigest(byte[] digest) {
		this.digest = digest;
	}


}
