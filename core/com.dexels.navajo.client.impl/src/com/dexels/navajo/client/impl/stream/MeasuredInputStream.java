package com.dexels.navajo.client.impl.stream;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.client.stream.TransferDataListener;

public class MeasuredInputStream extends FilterInputStream {
	
	
	private final static Logger logger = LoggerFactory
			.getLogger(MeasuredInputStream.class);
	private volatile long totalNumBytesRead;
	private String label;
	private TransferDataListener transferDataListener;
	private long started;
	private AtomicBoolean closed = new AtomicBoolean(false);
	
    @Override
	public void close() throws IOException {
    	logger.debug("Transfer: "+label+" complete. Used bytes: "+totalNumBytesRead);
    	if(transferDataListener!=null && closed.compareAndSet(false, true)) {
    		transferDataListener.transferCompleted(label,totalNumBytesRead,(System.currentTimeMillis() - started));
    	}
    	totalNumBytesRead = 0;
    	super.close();
	}


    public MeasuredInputStream(TransferDataListener tl, String label, InputStream in) {
        super(in);
        this.started = System.currentTimeMillis();
        this.transferDataListener = tl;
        this.label = label;

    }

    public long getTotalNumBytesRead() {
        return totalNumBytesRead;
    }


    @Override
    public int read() throws IOException {
        int b = super.read();
        updateProgress(1);
        return b;
    }

    @Override
    public int read(byte[] b) throws IOException {
        return (int)updateProgress(super.read(b));
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        final int read;
		try {
			read = super.read(b, off, len);
			return (int)updateProgress(read);
		} catch (IOException e) {
			if(transferDataListener!=null) {
				transferDataListener.transferFailed(label, (System.currentTimeMillis() - started));
			}
			throw e;
		}
    }

    @Override
    public long skip(long n) throws IOException {
        return updateProgress(super.skip(n));
    }

    @Override
    public void mark(int readlimit) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void reset() throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean markSupported() {
        return false;
    }

    private long updateProgress(long numBytesRead) {
        if (numBytesRead > 0) {
            this.totalNumBytesRead += numBytesRead;
        }

        return numBytesRead;
    }
}