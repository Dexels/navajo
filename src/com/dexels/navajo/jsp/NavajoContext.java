package com.dexels.navajo.jsp;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

import com.dexels.navajo.client.ClientException;
import com.dexels.navajo.client.ClientInterface;
import com.dexels.navajo.client.NavajoClientFactory;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.Property;


public class NavajoContext {

	private ClientInterface myClient;
	private final Map<String, Navajo> myNavajoMap = new HashMap<String, Navajo>();
	private final Map<Navajo, String> myInverseNavajoMap = new HashMap<Navajo, String>();
	private final Stack<Object> myElementStack = new Stack<Object>();

	public NavajoContext() {
	}


	public void reset() {
		myNavajoMap.clear();
		myInverseNavajoMap.clear();
		myElementStack.clear();
	}
	
	public void callService(String service) throws ClientException {
		Navajo n = myClient.doSimpleSend(service);
		myNavajoMap.put(service, n);
		myInverseNavajoMap.put(n, service);
		myElementStack.push(n);
	}
	
	public Map<String,Navajo> getNavajos() {
		return myNavajoMap;
	}

	public String getServiceName(Navajo n) {
		return myInverseNavajoMap.get(n);
	}
	
	public void callService(String service, Navajo input)
			throws ClientException {
		Navajo n = myClient.doSimpleSend(input, service);
			n.getHeader().setRPCName(service);
		myNavajoMap.put(service, n);
		myInverseNavajoMap.put(n, service);
		myElementStack.push(n);
	}

	public boolean hasNavajo(String name) {
		return myNavajoMap.containsKey(name);
	}



	
	public Navajo getNavajo(String name) {

		Navajo navajo = myNavajoMap.get(name);
//		if (navajo == null) {
//			throw new IllegalStateException( "Unknown service: " + name+" known services: "+myNavajoMap.keySet());
//		}
		return navajo;
	}

	public Property getProperty(String service, String path)
			{
		Navajo n = getNavajo(service);
		Property p = n.getProperty(path);
		if (p == null) {
			throw new IllegalStateException( "Unknown property: " + path
					+ " in service " + service);
		}
		return p;
	}

	public Object getPropertyValue(String service, String path)
		 {
		Property p = getProperty(service, path);
		return p.getTypedValue();
	}

	public String getNavajoName() {
		Navajo n = getNavajo();
		if(n==null) {
			return null;
		}
		return myInverseNavajoMap.get(n);
	}
	public Navajo getNavajo() {
		if (myElementStack.isEmpty()) {
			throw new IllegalStateException("No default navajo found. Either supply a name explicitly, or make sure you are within a 'call' tag");
		}		
		Object top = myElementStack.peek();
		if(!(top instanceof Navajo)) {
			return null;
		}
		return (Navajo) top;
	}

	public void popNavajo() {
		myElementStack.pop();
	}

	public void pushNavajo(Navajo m) {
		myElementStack.push(m);
	}

	public Message getMessage() {
		if (myElementStack.isEmpty()) {
			throw new IllegalStateException(
					"No default myMessageStack found. Either supply a name explicitly, or make sure you are within a 'message' tag");
		}
		Object top = myElementStack.peek();
		if(!(top instanceof Message)) {
			return null;
		}
		Message m;
		return (Message) top;
	}
	
	public Property getProperty() {
		if (myElementStack.isEmpty()) {
			throw new IllegalStateException(
					"No default myMessageStack found. Either supply a name explicitly, or make sure you are within a 'message' tag");
		}
		Object top = myElementStack.peek();
		if(!(top instanceof Property)) {
			return null;
		}
		return (Property) top;
	}

	public void popMessage() {
		myElementStack.pop();
	}

	public void pushMessage(Message m) {
		if(m!=null) {
			System.err.println("Pushinh message: "+m.getFullMessageName());
			myElementStack.push(m);			
		}
	}
	
	public void pushProperty(Property property) {
		myElementStack.push(property);
		
	}
	

	public String getDefaultPostman(PageContext pa) {
		HttpServletRequest request = (HttpServletRequest) pa
				.getRequest();
		StringBuffer requestBuffer = new StringBuffer();
		requestBuffer.append(request.getServerName());
		if (request.getServerPort() > 0) {
			requestBuffer.append(":");
			requestBuffer.append(request.getServerPort());
		}
		requestBuffer.append(request.getContextPath());
		requestBuffer.append("/Postman");
		return requestBuffer.toString();
	}
	public void setupClient(String server, String username, String password, PageContext pa) {
		
		 myClient = NavajoClientFactory.getClient();
		if (username == null) {
			username = "demo";
		}
		myClient.setUsername(username);
		if (password == null) {
			password = "demo";
		}
		myClient.setPassword(password);
		if (server == null) {
			server = getDefaultPostman(pa);
		}
		myClient.setServerUrl(server);		
	}
	
	public void debug() {
		
	}
	

	public String getMessagePath() {
		Message m = getMessage();
		if(m==null) {
			return null;
		}
		return createMessagePath(m);
	}
	public String getPropertyPath() {
		Property p = getProperty();
		if(p==null) {
			return null;
		}
		return createPropertyPath(p);
	}
	
	
	
	private String createMessagePath(Message m) {
		String navajoName = getServiceName(m.getRootDoc());
		String msg = m.getFullMessageName();
		return navajoName+"|"+msg;
	}

	
	private String createPropertyPath(Property p) {
		String navajoName = getServiceName(p.getRootDoc());
		String prop;
		try {
			prop = p.getFullPropertyName();
			return navajoName+":"+prop;
		} catch (NavajoException e) {
			e.printStackTrace();
		}

		return null;
	}


	public Property parsePropertyPath(String path) {
		System.err.println("parsing: "+path);
		if(path.indexOf(":")==-1) {
			Navajo n = getNavajo();
			if(n!=null) {
				return n.getProperty(path);
			}
			
		} else {
			String[] elts = path.split(":");
			System.err.println("Getting:"+elts[1]+" from "+elts[0]);
			Navajo n = getNavajo(elts[0]);
			return n.getProperty(elts[1]);
		}
		return null;
	}
	
	public Map<String,Property> getPropertyElement() {
		return new PropertyAccessMap(this);
	}


	public void resolvePost(String name, String value) {
		System.err.println("Resolving: "+name +" value: "+value);
		if(name.indexOf(":")==-1) {
			return;
		}
		String[] keyVal = name.split(":");
		String navajo = keyVal[0];
		String path = keyVal[1];
		Navajo n = getNavajo(navajo);
		Property p = n.getProperty(path);
		p.setValue(value);
	}
	
}
