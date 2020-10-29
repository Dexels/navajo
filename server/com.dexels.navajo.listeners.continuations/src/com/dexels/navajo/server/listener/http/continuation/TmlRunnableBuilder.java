package com.dexels.navajo.server.listener.http.continuation;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.script.api.AsyncRequest;
import com.dexels.navajo.script.api.LocalClient;
import com.dexels.navajo.script.api.TmlRunnable;
import com.dexels.navajo.server.listener.http.TmlHttpServlet;
import com.dexels.navajo.server.listener.http.impl.BaseRequestImpl;

public class TmlRunnableBuilder {

    public static TmlRunnable prepareRunnable(final HttpServletRequest req,
            HttpServletResponse resp, LocalClient localClient, String instance, long timeout)
            throws IOException {

        // Prevent the double creation of an asynchronous request handler for this request.
        TmlContinuationRunner tmlRunner = (TmlContinuationRunner) req.getAttribute("tmlRunner");
        if (tmlRunner != null) {
            return null;
        }

        AsyncRequest request = constructRequest(req, resp, instance);
        TmlContinuationRunner runner = new TmlContinuationRunner(request, localClient, timeout);
        if (runner.isAborted()) {
            return null;
        }

        req.setAttribute("tmlRunner", runner);
        runner.setAttribute("tester", req.getHeader("X-Navajo-Tester") != null);
        runner.suspendContinuation(resp);

        return runner;
    }

    private static AsyncRequest constructRequest(final HttpServletRequest req,
            HttpServletResponse resp, String instance) throws IOException {

        Object certObject = req.getAttribute("javax.servlet.request.X509Certificate");
        String contentEncoding = req.getHeader("Content-Encoding");
        String acceptEncoding = req.getHeader("Accept-Encoding");
        AsyncRequest request = null;
        if ("POST".equals(req.getMethod())) {
            request = new BaseRequestImpl(req, resp, acceptEncoding, contentEncoding, certObject,
                    instance);
        } else {
            Navajo in = TmlHttpServlet.constructFromRequest(req);
            request = new BaseRequestImpl(in, req, resp, instance);
        }

        return request;
	}

}
