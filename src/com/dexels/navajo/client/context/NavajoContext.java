package com.dexels.navajo.client.context;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.client.ClientException;
import com.dexels.navajo.client.ClientInterface;
import com.dexels.navajo.client.NavajoClientFactory;
import com.dexels.navajo.document.Header;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;


public class NavajoContext {

	private ClientInterface myClient;
	private final Map<String, Navajo> myNavajoMap = new HashMap<String, Navajo>();
	private final Map<Navajo, String> myInverseNavajoMap = new HashMap<Navajo, String>();
	private final Stack<Object> myElementStack = new Stack<Object>();
	private boolean debugAll;
	
	private static final Logger logger = LoggerFactory.getLogger(NavajoContext.class);

	public NavajoContext() {
		//System.err.println("New navajo context");
	}


	public void reset() {
		myNavajoMap.clear();
		myInverseNavajoMap.clear();
		myElementStack.clear();
	}
	
	public void callService(String service) throws ClientException {
		callService(service,null);
	}
	
	public Map<String,Navajo> getNavajos() {
		return myNavajoMap;
	}

	public String getServiceName(Navajo n) {
		return myInverseNavajoMap.get(n);
	}
	
	// used by the JSP Tester
	public String getEngineInstance() {
		String instanceName = System.getProperty("com.dexels.navajo.server.EngineInstance");
		
		return instanceName;
	}
	
	public void callService(String service, Navajo input)
			throws ClientException {
		if(myClient==null) {
			throw new ClientException(1,-1,"No client has been set up!");
		}
		System.err.println("Calling to server: "+myClient.getServerUrl()+" username: "+myClient.getUsername()+" pass: "+myClient.getPassword()+" hash: "+myClient.hashCode());
		if(input==null) {
			input = NavajoFactory.getInstance().createNavajo();
		}
		Header outHeader = input.getHeader();
		if(outHeader==null) {
			outHeader = NavajoFactory.getInstance().createHeader(input, service, null,null, -1);
			input.addHeader(outHeader);
		}
		
		// TODO Warning should use debugAll
		System.err.println("BTW: debugAll = "+debugAll);
		if(true) {
			outHeader.setHeaderAttribute("fullLog", "true");
		}
		
		long time = System.currentTimeMillis();
		Navajo n = myClient.doSimpleSend(input, service);
		System.err.println("Send complete!");
		n.getHeader().setRPCName(service);
		Navajo old = myNavajoMap.get(service);
		if(old!=null) {
			myInverseNavajoMap.remove(old);
		}
		myNavajoMap.put(service, n);
		myInverseNavajoMap.put(n, service);
		long time2 = System.currentTimeMillis() - time;
		System.err.println("Call took: "+time2+" millis!");
	}

	public boolean hasNavajo(String name) {
		return myNavajoMap.containsKey(name);
	}
	
	public boolean getHasChildren(){
		try{
			Message top = getMessage();
			return top.getMessages().size() > 0;
		}catch(Exception ex){
			return false;
		}
	}
	

	public int getStackSize() {
		return myElementStack.size();
	}


	public void dumpTopElement() {
		Object o = myElementStack.peek();
		if(o instanceof Navajo) {
			Navajo n = (Navajo)o;
			System.err.println("Navajo on top:");
			try {
				n.write(System.err);
			} catch (NavajoException e) {
				e.printStackTrace();
			}
		} else
		if(o instanceof Message) {
			System.err.println("Message on top: "+((Message)o).getFullMessageName());
		} else
		if(o instanceof Property) {
			try {
				System.err.println("Property on top: "+((Property)o).getFullPropertyName());
			} catch (NavajoException e) {
				e.printStackTrace();
			}
			
		} else {
			if(o!=null) {
				System.err.println("Other object:" + o.getClass());
			} else {
				System.err.println("Null object on stack!");
			}
		}
		
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
		return (Navajo) getTopmostElement(Navajo.class);

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
		return (Message) getTopmostElement(Message.class);
	}
	

	public Object peek() {
		if (myElementStack.isEmpty()) {
			throw new IllegalStateException(
					"No default myMessageStack found. Either supply a name explicitly, or make sure you are within a 'message' tag");
		}
		return myElementStack.peek();
	}

	public Property getProperty() {
		if (myElementStack.isEmpty()) {
			throw new IllegalStateException(
					"No default myMessageStack found. Either supply a name explicitly, or make sure you are within a 'message' tag");
		}

		return (Property) getTopmostElement(Property.class);
	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Object getTopmostElement(Class cls) {
		for (int i = myElementStack.size()-1; i>=0; i--) {
			Object e  = myElementStack.get(i); 
			if(cls.isAssignableFrom(e.getClass()) ) {
				return e;
			}
		}
		return null;
	}

	public void popMessage() {
		myElementStack.pop();

	}

	public void pushMessage(Message m) {
		if(m!=null) {
			myElementStack.push(m);			

		}
	}
	
	public void pushProperty(Property property) {
		if(property!=null) {
			myElementStack.push(property);
		} else {
			System.err.println("Warning, attempted to push null property!");
		}
	}
	

	public String getDefaultPostman(String serverName, int serverPort,String contextPath) {
		StringBuffer requestBuffer = new StringBuffer();
		requestBuffer.append(serverName);
		if (serverPort > 0) {
			requestBuffer.append(":");
			requestBuffer.append(serverPort);
		}
		requestBuffer.append(contextPath);
		requestBuffer.append("/Postman");
		return requestBuffer.toString();
	}
	public void setupClient(String server, String username, String password,String requestServerName,int requestServerPort, String requestContextPath) {

		setupClient(server, username, password,requestServerName,requestServerPort,requestContextPath, false);
	}

	public void setupClient(String server, String username, String password) {
		setupClient(server,username,password,null,-1,null);
	}
	
	/**
	 * All request* params are only used when server is not supplied.
	 * @param server
	 * @param username
	 * @param password
	 * @param requestServerName
	 * @param requestServerPort
	 * @param requestContextPath
	 * @param debugAll
	 */
	public void setupClient(String server, String username, String password,String requestServerName,int requestServerPort, String requestContextPath,boolean debugAll) {

		//		Thread.dumpStack();
		System.err.println("Setupclient: "+server+" user: "+username+" pass: "+password);
		NavajoClientFactory.resetClient();
//			NavajoClientFactory.createDefaultClient()
		 myClient = NavajoClientFactory.getClient();
		 myClient.setAllowCompression(false);
		 if (username == null) {
			username = "demo";
		}
		myClient.setUsername(username);
		if (password == null) {
			password = "demo";
		}
		myClient.setPassword(password);
		if (server == null) {
			server = getDefaultPostman(requestServerName,requestServerPort,requestContextPath);
			System.err.println("No server supplied. Creating default server url: "+server);
		}
		myClient.setServerUrl(server);		
		myClient.setRetryAttempts(0);
		this.debugAll = debugAll;
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
		if(path.indexOf(":")==-1) {
			Navajo n = getNavajo();
			if(n!=null) {
				return n.getProperty(path);
			}
			
		} else {
			String[] elts = path.split(":");
			Navajo n = getNavajo(elts[0]);
			return n.getProperty(elts[1]);
		}
		return null;
	}
	
	public Message parseMessagePath(String path) {
		if(path.indexOf(":")==-1) {
			Navajo n = getNavajo();
			if(n!=null) {
				return n.getMessage(path);
			}
		} else {
			String[] elts = path.split(":");
			Navajo n = getNavajo(elts[0]);
			return n.getMessage(elts[1]);
		}
		return null;
	}
	
	
	public Map<String,Property> getPropertyElement() {
		return new PropertyAccessMap(this);
	}

	public Map<String,Message> getMessageElement() {
		return new MessageAccessMap(this);
	}

	public void resolvePost(String name, String value) {
		if(name.indexOf(":")==-1) {
			return;
		}
		String[] keyVal = name.split(":");
		String navajo = keyVal[0];
		String path = keyVal[1];
		Navajo n = getNavajo(navajo);
		Property p = n.getProperty(path);
		if(Property.BOOLEAN_PROPERTY.equals(p.getType()) ) {
			if ("on".equals(value)) {
				p.setAnyValue(true);
			} else {
				p.setAnyValue(false);
			}
		} else {
			p.setValue(value);
		}
	}


	public String dumpStack() {
		StringBuffer sb = new StringBuffer();
		for ( Object a : myElementStack) {
			sb.append("Current object: "+a.getClass()+"\n");
		}
		return sb.toString();
	}


	public void popProperty() {
		myElementStack.pop();
		
	}




	
}
