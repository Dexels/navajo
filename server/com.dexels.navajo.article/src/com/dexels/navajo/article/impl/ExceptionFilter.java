package com.dexels.navajo.article.impl;

import java.io.IOException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.codehaus.jackson.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ExceptionFilter implements Filter{

private static final Logger logger = LoggerFactory.getLogger(ExceptionFilter.class);
private String filterString;

@Override
public void doFilter(ServletRequest rq, ServletResponse rs, FilterChain chain) throws IOException, ServletException {
	HttpServletResponse resp = (HttpServletResponse) rs;
	
	if(!filterCondition((HttpServletRequest) rq)) {
        chain.doFilter(rq, rs);
        return;
	}
	ErrorCatchHttpResponseWrapper wrappedResponse = new ErrorCatchHttpResponseWrapper(resp);
	try {
        chain.doFilter(rq, wrappedResponse);
        int status = wrappedResponse.getStatus();
		logger.info("Status: "+status);
        if(status>=400) {
        	dealWithException(null,wrappedResponse, resp);
        	return;
        }
        resp.getWriter().write(wrappedResponse.getBufferContent());
        // this calls the servlet which is where your exceptions will bubble up from
    } catch (Throwable t) {
        // deal with exception, then do redirect to custom jsp page
        logger.warn("Catching exception",t);
        dealWithException(t,wrappedResponse, resp); // you could have a service to track counts of exceptions / log them to DB etc
    }
}

public void activate(Map<String,Object> settings) {
	this.filterString = (String)settings.get("filterstring");
	System.out.println("FilterString " + this.filterString);
	
}

private boolean filterCondition(HttpServletRequest request) {
	String userAgent = request.getHeader("user-agent");
	if(userAgent==null) {
		return false;
	}
	userAgent = userAgent.toLowerCase();
	return userAgent.indexOf("msie")!=-1 || userAgent.indexOf("trident")!=-1;
	//	return userAgent.indexOf(filterString)!=-1;
}

private void dealWithException(Throwable t,ErrorCatchHttpResponseWrapper wrapped, HttpServletResponse rs) throws IOException {
	ObjectMapper objectMapper = new ObjectMapper();
	ObjectNode object = objectMapper.createObjectNode();
	object.put("error", true);
	if(t!=null) {
		while(t.getCause()!=null) {
			t = t.getCause();
		}
		object.put("errorcode", 500);
		object.put("description", t.getMessage());
	} else {
		object.put("errorcode", wrapped.getStatus());
		object.put("description", wrapped.getErrorMessage());
	}
	ObjectWriter writer = objectMapper.writer().withDefaultPrettyPrinter();
	rs.setContentType("application/json");
	rs.setStatus(200);
	writer.writeValue(rs.getWriter(), object);
	

}

@Override
public void init(FilterConfig arg0) throws ServletException {
 }

@Override
public void destroy() {
	// TODO Auto-generated method stub
	
}

}