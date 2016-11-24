package com.dexels.navajo.client.impl;

import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.UnknownHostException;

import javax.net.ssl.SSLHandshakeException;

import org.apache.http.NoHttpResponseException;

public class NavajoHttpRequestRetryHandler {

    public static boolean retryRequest(Throwable exception, Integer retries) {
        // First determine the type of exception. For some exceptions, we don't bother retrying!

        if (exception == null) {
            throw new IllegalArgumentException("Exception parameter may not be null");
        }
        if (retries == null || retries < 1) {
            return false;
        }

        if (exception instanceof NoHttpResponseException) {
            // Retry if the server dropped connection on us
            return true;
        }
        if (exception instanceof InterruptedIOException) {
            // Timeout
            return true;
        }
        if (exception instanceof UnknownHostException) {
            // Unknown host
            return true;
        }
        if (exception instanceof ConnectException) {
            // Connection refused
            return true;
        }
        if (exception instanceof SSLHandshakeException) {
            // SSL handshake exception
            return false;
        }
        
        // Some other exception occurred. Probably doesn't hurt to retry?
        return true;
    }

}
