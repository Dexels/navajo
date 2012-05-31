/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.felix.http.samples.bridge;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.osgi.framework.BundleContext;

public final class ProxyServlet
    extends HttpServlet
{
    private static final long serialVersionUID = 4653802091324863072L;
	private DispatcherTracker tracker;

	
    @Override
    public void init(ServletConfig config)
        throws ServletException
    {
        super.init(config);

        try {
            doInit();
        } catch (ServletException e) {
            throw e;
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    private void doInit()
        throws Exception
    {
        this.tracker = new DispatcherTracker(getBundleContext(), null, getServletConfig());
        this.tracker.open();
    }

    @Override
    protected void service(final HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException
    {
//    	System.err.println("Request: "+req);
//    	System.err.println("Contextpath: "+req.getContextPath());
//    	System.err.println("PathIndo: "+req.getPathInfo());
//    	System.err.println("PathQuert: "+req.getQueryString());
//    	System.err.println("servletPath: "+req.getServletPath());
//    	System.err.println("requri: "+req.getRequestURI());
//    	System.err.println("requrl: "+req.getRequestURL());
//    	
//    	getServletContext().log("SERVICE OF PROXY DETECTED!");

    	HttpServletRequestWrapper hsrw = new HttpServletRequestWrapper(req){

			@Override
			public String getPathInfo() {
				return req.getPathInfo();
			}

			@Override
			public String getServletPath() {
				return super.getServletPath();
			}
    		
    	};
    	HttpServlet dispatcher = this.tracker.getDispatcher();
        if (dispatcher != null) {
            dispatcher.service(req, res);
        } else {
//        	getServletContext().log("OEMPALOEMPA!");
        	
        	res.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
        }
    }

    @Override
    public void destroy()
    {
        this.tracker.close();
        super.destroy();
    }

    private BundleContext getBundleContext()
        throws ServletException
    {
        Object context = getServletContext().getAttribute(BundleContext.class.getName());
        if (context instanceof BundleContext) {
            return (BundleContext)context;
        }

        throw new ServletException("Bundle context attribute [" + BundleContext.class.getName() +
                "] not set in servlet context");
    }
}
