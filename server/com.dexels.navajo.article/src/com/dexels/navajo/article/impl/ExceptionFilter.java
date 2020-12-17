/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.article.impl;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.article.APIErrorCode;

public class ExceptionFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionFilter.class);

    @Override
    public void doFilter(ServletRequest rq, ServletResponse rs, FilterChain chain) {
    	HttpServletResponse resp = (HttpServletResponse) rs;
        try {
        	
        	if (!filterCondition((HttpServletRequest) rq)) {
        		chain.doFilter(rq, rs);
                return;
        	}
        	
        	ErrorCatchHttpResponseWrapper wrappedResponse = new ErrorCatchHttpResponseWrapper(resp);
        	chain.doFilter(rq, wrappedResponse);
        	resp.setStatus(200);
        	resp.getWriter().write(wrappedResponse.getBufferContent());
		} catch (IOException | ServletException e) {
			logger.error("Filter has failed {}", e);
			//We should not go in here. All exceptions should be handled in the servlet. As 
			//a backup we report a server error back to the user.
			APIErrorCode internal = APIErrorCode.InternalError;
			try {
				resp.sendError(internal.getHttpStatusCode(), internal.getDescription());
			} catch (Throwable e1) {
				//We've failed to return the error to the user. We cannot do anything anymore.
				logger.error("Failed to deliver error {}", e1);
			}
		} 
    }
    
    private boolean filterCondition(HttpServletRequest request) {
        String userAgent = request.getHeader("user-agent");
        if (userAgent == null) {
            return false;
        }
        userAgent = userAgent.toLowerCase();
        return userAgent.indexOf("msie") != -1 || userAgent.indexOf("trident") != -1;
    }

	@Override
	public void destroy() {
		//Intentionally left empty
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		//Intentionally left empty
	}
}