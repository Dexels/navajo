/**
 * Copyright 2014 Jitendra Kotamraju.
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

import rx.Subscriber;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A servlet {@link WriteListener} that pushes Observable events that indicate
 * when HTTP response data can be written
 *
 * @author Jitendra Kotamraju
 */
class ServletWriteListener implements WriteListener {
    private static final Logger LOGGER = Logger.getLogger(ObservableServlet.class.getName());

    private final Subscriber<? super Void> subscriber;
    private final ServletOutputStream out;

    ServletWriteListener(Subscriber<? super Void> subscriber, final ServletOutputStream out) {
        this.subscriber = subscriber;
        this.out = out;
    }

    @Override
    public void onWritePossible() {
        do {
            subscriber.onNext(null);
        // loop until isReady() false, otherwise container will not call onWritePossible()
        } while(!subscriber.isUnsubscribed() && out.isReady());
        // If isReady() false, container will call onWritePossible()
        // when data can be written.
        if (LOGGER.isLoggable(Level.FINE) && !subscriber.isUnsubscribed()) {
            LOGGER.fine("Waiting for container to notify when HTTP response data can be written");
        }
    }

    @Override
    public void onError(Throwable t) {
        if (LOGGER.isLoggable(Level.WARNING)) {
            LOGGER.log(Level.WARNING, "Error while writing HTTP response data", t);
        }
        if (!subscriber.isUnsubscribed()) {
            subscriber.onError(t);
        }
    }

}
