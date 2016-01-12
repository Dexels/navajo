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
package org.jitu.rx.servlet;

import rx.Observer;
import rx.Subscriber;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A servlet {@link ReadListener} that pushes HTTP request data to an {@link Observer}
 *
 * @author Jitendra Kotamraju
 */
class ServletReadListener implements ReadListener {
    private static final Logger LOGGER = Logger.getLogger(ServletReadListener.class.getName());

    private final Subscriber<? super ByteBuffer> subscriber;
    private final ServletInputStream in;

    ServletReadListener(ServletInputStream in, Subscriber<? super ByteBuffer> subscriber) {
        this.in = in;
        this.subscriber = subscriber;
    }

    @Override
    public void onDataAvailable() throws IOException {
        do {
            byte[] buf = new byte[4096];
            int len = in.read(buf);
            if (len != -1) {
                subscriber.onNext(ByteBuffer.wrap(buf, 0, len));
            }
        // loop until isReady() false, otherwise container will not call onDataAvailable()
        } while(!subscriber.isUnsubscribed() && !in.isFinished() && in.isReady());
        // If isReady() false, container will call onDataAvailable()
        // when data is available.
        if (LOGGER.isLoggable(Level.FINE) && !subscriber.isUnsubscribed()) {
            LOGGER.fine("Waiting for container to notify when there is HTTP request data");
        }
    }

    @Override
    public void onAllDataRead() {
        if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER.fine("Read all the data from ServletInputStream");
        }
        if (!subscriber.isUnsubscribed()) {
            subscriber.onCompleted();
        }
    }

    @Override
    public void onError(Throwable t) {
        if (LOGGER.isLoggable(Level.WARNING)) {
            LOGGER.log(Level.WARNING, "Error while reading the data from ServletInputStream", t);
        }
        if (!subscriber.isUnsubscribed()) {
            subscriber.onError(t);
        }
    }

}
