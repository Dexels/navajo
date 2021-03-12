/*
 * This file is part of the Navajo Project.
 *
 * It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and
 * at https://www.gnu.org/licenses/agpl-3.0.txt. No part of the Navajo Project, including this file, may be copied,
 * modified, propagated, or distributed except according to the terms contained in the COPYING file.
 */

package com.dexels.navajo.example.listener;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.*;
import com.dexels.navajo.script.api.FatalException;
import com.dexels.navajo.script.api.LocalClient;

public class ExampleServlet extends HttpServlet {

    private static final long serialVersionUID = 7798880709165548039L;

    private static final Logger logger = LoggerFactory.getLogger(ExampleServlet.class);

    private LocalClient localClient;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String serviceName = req.getParameter("service");
        if (serviceName == null) {
            resp.sendError(500, "No service parameter supplied");
            return;
        }

        Navajo n = NavajoFactory.getInstance().createNavajo();
        Header h = NavajoFactory.getInstance().createHeader(n, serviceName, "demo", "demo", -1);
        n.addHeader(h);

        logger.info("Calling service: " + serviceName);
        try {
            long time = System.currentTimeMillis();
            localClient.call(n);
            long diff = System.currentTimeMillis() - time;
            resp.setContentType("text/plain");
            resp.getWriter().write("Service: " + serviceName + " took: " + diff + " millis.");
        } catch (FatalException e) {
            resp.sendError(500, "Error calling service: " + URLEncoder.encode(serviceName, "UTF-8"));
        }
    }

    public void activate() {
        logger.info("Example servlet activated.");
    }

    public void deactivate() {
        logger.info("Example servlet deactivated.");
    }

    public void setClient(LocalClient localClient) {
        this.localClient = localClient;
    }

    public void clearClient(LocalClient localClient) {
        this.localClient = null;
    }

}
