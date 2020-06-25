package com.dexels.navajo.document.stream.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.security.DigestOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.dexels.utils.Base64;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.types.Binary;

import io.reactivex.FlowableOperator;

public class BinaryObserver implements FlowableOperator<Binary,String>{

	private OutputStream fos;
	private Writer w;
	private transient MessageDigest messageDigest;
	private File dataFile;
	private byte[] digest;
	protected byte[] inMemory;
	int count = 0;


	private final static Logger logger = LoggerFactory.getLogger(BinaryObserver.class);

	public static FlowableOperator<Binary,String> collect() {
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
	public Subscriber<? super String> apply(Subscriber<? super Binary> outgoing) {
		return new Subscriber<String>() {

			@Override
			public void onComplete() {
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
					outgoing.onNext(binary);
			    	outgoing.onComplete();
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
				count++;
				try {
					w.write(line);
				} catch (IOException e) {
					outgoing.onError(e);
				}
			}

			@Override
			public void onSubscribe(Subscription s) {
			}
		};
	}


	private OutputStream createTempFileOutputStream() throws IOException {
		if(messageDigest==null) {
	        MessageDigest md = null;
			try {
				md = MessageDigest.getInstance("MD5");
			} catch (NoSuchAlgorithmException e1) {
				logger.warn("Failed creating messageDigest in binary. Expect problems", e1);
			}
			this.messageDigest = md;
		} else {
			messageDigest.reset();
		}
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
    		return new DigestOutputStream(baos, messageDigest) {

				@Override
				public void close() throws IOException {
					setDigest(messageDigest.digest());
					super.close();
				}

    		};
    	} else {
            dataFile = File.createTempFile("streamedbinary_object", "navajo", NavajoFactory.getInstance().getTempDir());

            FileOutputStream fosStream = new FileOutputStream(dataFile);
            return new DigestOutputStream(fosStream, messageDigest) {

				@Override
				public void close() throws IOException {
					super.close();
					setDigest(messageDigest.digest());
				}

            };
    	}
    }
	protected void setDigest(byte[] digest) {
		this.digest = digest;
	}


}
