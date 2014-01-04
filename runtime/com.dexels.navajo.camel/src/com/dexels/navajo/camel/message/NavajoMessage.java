package com.dexels.navajo.camel.message;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.camel.Message;
import org.apache.camel.impl.DefaultMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.Property;

public class NavajoMessage extends DefaultMessage implements Message {

	
	private static final String SERVICE_HEADER_NAME = "service";
	private final static Logger logger = LoggerFactory
			.getLogger(NavajoMessage.class);
	
//	@Override
//	public void addAttachment(String arg0, DataHandler arg1) {
//
//	}

	@Override
	public Message copy() {
		NavajoMessage m = new NavajoMessage();
		Navajo n = (Navajo) getBody();
		m.setBody(n.copy());
		return m;
	}

	@Override
	public void copyFrom(Message m) {
		Navajo n = (Navajo) m.getBody();
		setBody(n.copy());
	}

//	@Override
//	public DataHandler getAttachment(String arg0) {
//		return null;
//	}
//
//	@Override
//	public Set<String> getAttachmentNames() {
//		return null;
//	}
//
//	@Override
//	public Map<String, DataHandler> getAttachments() {
//		return null;
//	}

//	@Override
//	public Object getBody() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public <T> T getBody(Class<T> arg0) {
//		// TODO Auto-generated method stub
//		return null;
//	}

//	@Override
//	public Exchange getExchange() {
//		// TODO Auto-generated method stub
//		return null;
//	}

	@Override
	public Object getHeader(String header) {
		Object res = super.getHeader(header);
		if(res!=null) {
			return res;
		}
		Navajo n = (Navajo) getBody();
		return n.getHeader().getHeaderAttribute(header);
	}

	@Override
	public Object getHeader(String header, Object defaultValue) {
		Object res = getHeader(header);
		if(res!=null) {
			return res;
		}
		Navajo n = (Navajo) getBody();
		res =  n.getHeader().getHeaderAttribute(header);
		return res==null?defaultValue:res;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getHeader(String header, Class<T> cc) {
		return (T) getHeader(header);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getHeader(String header, Object defaultValue, Class<T> cc) {
		return (T) getHeader(header,defaultValue);
	}

	@Override
	public Map<String, Object> getHeaders() {
		Map<String, Object> res = super.getHeaders();
		if(res==null) {
			res = new HashMap<String, Object>();
		}
		Navajo n = (Navajo) getBody();
		Map<String, String> rr =  n.getHeader().getHeaderAttributes();
		if(rr!=null) {
			res.putAll(rr);
		}
		return res;
	}

	@Override
	public void setHeader(String name, Object value) {
		super.setHeader(name, value);
		if(name.equals(SERVICE_HEADER_NAME)) {
			Navajo n = (Navajo) getBody();
			n.getHeader().setRPCName((String) value);
		}
		if(name.indexOf("/")!=-1) {
			Navajo n = (Navajo) getBody();
			// property detected
			Property p = n.getProperty(name);
			if(p!=null) {
				p.setAnyValue(value);
			} else {
				logger.warn("Property {} not found, ignoring set header",name);
			}
			
		}
	}

	@Override
	public void setHeaders(Map<String, Object> headers) {
		for (Entry<String,Object> e : headers.entrySet()) {
			setHeader(e.getKey(), e.getValue());
		}

	}

	
//	@Override
//	public Object getMandatoryBody() throws InvalidPayloadException {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public <T> T getMandatoryBody(Class<T> arg0) throws InvalidPayloadException {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public String getMessageId() {
//		// TODO Auto-generated method stub
//		return null;
//	}

//	@Override
//	public boolean hasAttachments() {
//		// TODO Auto-generated method stub
//		return false;
//	}

	@Override
	public boolean hasHeaders() {
		boolean has = super.hasHeaders();
		if(has) {
			return true;
		}
		Navajo n = (Navajo) getBody();
		Map<String, String> rr =  n.getHeader().getHeaderAttributes();
		if(rr!=null && rr.size()>0) {
			return true;
		}
		return false;
	}

//	@Override
//	public boolean isFault() {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public void removeAttachment(String arg0) {
//		// TODO Auto-generated method stub
//
//	}

//	@Override
//	public Object removeHeader(String header) {
//		return super.removeHeader(header);
//	}
//
//	@Override
//	public boolean removeHeaders(String pattern) {
//		return super.removeHeaders(pattern);
//	}
//
//	@Override
//	public boolean removeHeaders(String arg0, String... arg1) {
//		return super.removeHeaders(arg0, arg1);
//	}

//	@Override
//	public void setAttachments(Map<String, DataHandler> arg0) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void setBody(Object arg0) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public <T> void setBody(Object arg0, Class<T> arg1) {
//		// TODO Auto-generated method stub
//
//	}

//	@Override
//	public void setFault(boolean arg0) {
//		// TODO Auto-generated method stub
//
//	}


//	@Override
//	public void setMessageId(String arg0) {
//		// TODO Auto-generated method stub
//
//	}
//

}
