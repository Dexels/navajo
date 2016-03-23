/**
 * Copyright 2013-2014 Jitendra Kotamraju.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dexels.navajo.listeners.stream;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import rx.Subscriber;

class ServletReadListener implements ReadListener {
	private static final int BUFFER_SIZE = 4096;

	private final static Logger logger = LoggerFactory.getLogger(ServletReadListener.class);

    private final Subscriber<? super byte[]> subscriber;
    private final ServletInputStream in;

    ServletReadListener(ServletInputStream in, Subscriber<? super byte[]> subscriber) {
        this.in = in;
        this.subscriber = subscriber;
    }

    @Override
    public void onDataAvailable() throws IOException {
//    	System.err.println("Read Data available!");
    	if(subscriber.isUnsubscribed()) {
    		in.close();
    		return;
    	}

    	while(true) {
            boolean ready = checkReady();
//            System.err.println("READ Ready: "+ready);
            if(!ready) {
            	break;
            }
            if(in.isFinished()) {
//            	System.err.println("READ Finished data!");
            	break;
            }
            byte[] buf = new byte[BUFFER_SIZE];
            int len = in.read(buf);
            if (len != -1) {
//            	logger.debug("Read data: {}",len);
                subscriber.onNext(Arrays.copyOf(buf, len));
            }

        }
    }

	private boolean checkReady() {
//		System.err.println("READ Ready called");
		return in.isReady();
	}

    @Override
    public void onAllDataRead() {
        logger.debug("Read all the data from ServletInputStream");
        logger.debug("Doing one last read:");
        try {
			onDataAvailable();
		} catch (IOException e) {
			logger.error("Error: ", e);
		}
        if (!subscriber.isUnsubscribed()) {
        	System.err.println("Completed");
            subscriber.onCompleted();
        }
    }

    @Override
    public void onError(Throwable t) {
        logger.error("Error: ", t);
        if (!subscriber.isUnsubscribed()) {
            subscriber.onError(t);
        }
    }

}
