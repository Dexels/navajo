package com.dexels.navajo.jsp;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

import com.dexels.navajo.client.ClientException;
import com.dexels.navajo.client.ClientInterface;
import com.dexels.navajo.client.NavajoClient;
import com.dexels.navajo.client.NavajoClientFactory;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.base.BaseNode;
import com.dexels.navajo.jsp.output.NavajoOutputWriter;
import com.dexels.navajo.jsp.output.OutputWriter;

public class NavajoContext {

	private ClientInterface myClient;
	private final Map<String, Navajo> myNavajoMap = new HashMap<String, Navajo>();
	private final Stack<Object> myElementStack = new Stack<Object>();
//	private final PageContext myPageContext;
	private final OutputWriter outputPlugin = new NavajoOutputWriter();
	private Property currentProperty = null;
	

	public Property getProperty() {
		return currentProperty;
	}
	public void setProperty(Property currentProperty) {
		this.currentProperty = currentProperty;
	}
	public NavajoContext() {
	}
	public NavajoContext(PageContext pageContext, String server,
			String username, String password) {
//		myPageContext = pageContext;
		setupClient(server, username, password,pageContext);
//		myPageContext.setAttribute("navajo", myNavajoMap);
	}

	public void callService(String service) throws ClientException {
		Navajo n = myClient.doSimpleSend(service);
		myNavajoMap.put(service, n);
		myElementStack.push(n);
	}
	
	public Map<String,Navajo> getNavajos() {
		return myNavajoMap;
	}

	public void callService(String service, Navajo input)
			throws ClientException {
		Navajo n = myClient.doSimpleSend(input, service);
		myNavajoMap.put(service, n);
		myElementStack.push(n);
	}

	public boolean hasNavajo(String name) {
		return myNavajoMap.containsKey(name);
	}


	
	public Navajo getNavajo(String name) throws ClientException {

		Navajo navajo = myNavajoMap.get(name);
		if (navajo == null) {
			throw new ClientException(1, 1, "Unknown service: " + name);
		}
		return navajo;
	}

	public Property getProperty(String service, String path)
			throws ClientException {
		Navajo n = getNavajo(service);
		Property p = n.getProperty(path);
		if (p == null) {
			throw new ClientException(1, 1, "Unknown property: " + path
					+ " in service " + service);
		}
		return p;
	}

	public Object getPropertyValue(String service, String path)
			throws ClientException {
		Property p = getProperty(service, path);
		return p.getTypedValue();
	}

	public Navajo getNavajo() {
		System.err.println("Getting!");
		if (myElementStack.isEmpty()) {
			throw new IllegalStateException(
					"No default navajo found. Either supply a name explicitly, or make sure you are within a 'call' tag");
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
		return (Message) top;
	}

	public void popMessage() {
		myElementStack.pop();
	}

	public void pushMessage(Message m) {
		myElementStack.push(m);
	}
	
	public OutputWriter getOutputWriter() {
		return outputPlugin;
	}
	
	public void writeService(Navajo service, PageContext pa) throws IOException {
		outputPlugin.writeService(service, pa.getOut());
	}
	
	public void writeMessage(Message message, PageContext pa) throws IOException {

		outputPlugin.writeMessage(message, pa.getOut());
	}

	private void writeMessageTable(Message message, PageContext pa) throws IOException {
		outputPlugin.writeTable(message, pa.getOut());
	}

	public void writeMessageRow(Message message, PageContext pa) throws IOException {
		outputPlugin.writeTableRow(message, pa.getOut());
	}

	public String getMessageRow(Message message, PageContext pa) throws IOException {
		StringWriter sw = new StringWriter();
		outputPlugin.writeTableRow(message, pa.getOut());
		return sw.toString();
	}

	public void writeProperty(Property property, PageContext pa) throws IOException {
		pa.getOut().write(property.getValue());
		pa.getOut().write(" ");
		outputPlugin.writeProperty(property, pa.getOut());
	}

	public String getProperty(Property property) throws IOException {
		StringWriter sw = new StringWriter();
		outputPlugin.writeProperty(property, sw);
		return sw.toString();
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
	
}
