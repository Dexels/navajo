package com.dexels.navajo.client.context;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.Property;


public abstract class NavajoContext implements ClientContext {

	private final Map<String, Navajo> myNavajoMap = new HashMap<String, Navajo>();
	private final Map<Navajo, String> myInverseNavajoMap = new HashMap<Navajo, String>();
	private final Stack<Object> myElementStack = new Stack<Object>();
	private static final Logger logger = LoggerFactory.getLogger(NavajoContext.class);
	private String username = null;
	private String password = null;
	
	public NavajoContext() {
	}


	/* (non-Javadoc)
	 * @see com.dexels.navajo.client.context.ClientContext#reset()
	 */
	@Override
	public void reset() {
		myNavajoMap.clear();
		myInverseNavajoMap.clear();
		myElementStack.clear();
	}
	
	
	/* (non-Javadoc)
	 * @see com.dexels.navajo.client.context.ClientContext#getNavajos()
	 */
	@Override
	public Map<String,Navajo> getNavajos() {
		return myNavajoMap;
	}

	/* (non-Javadoc)
	 * @see com.dexels.navajo.client.context.ClientContext#getServiceName(com.dexels.navajo.document.Navajo)
	 */
	@Override
	public String getServiceName(Navajo n) {
		return myInverseNavajoMap.get(n);
	}
	
	// used by the JSP Tester
	public String getEngineInstance() {
		String instanceName = System.getProperty("com.dexels.navajo.server.EngineInstance");
		
		return instanceName;
	}
	

	/* (non-Javadoc)
	 * @see com.dexels.navajo.client.context.ClientContext#hasNavajo(java.lang.String)
	 */
	@Override
	public boolean hasNavajo(String name) {
		return myNavajoMap.containsKey(name);
	}
	
	public void putNavajo(String service, Navajo n) {
		Navajo old = myNavajoMap.get(service);
		if(old!=null) {
			myInverseNavajoMap.remove(old);
		}
		myNavajoMap.put(service, n);
		myInverseNavajoMap.put(n, service);
		
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
			logger.info("Navajo on top:");
			try {
				n.write(System.err);
			} catch (NavajoException e) {
				logger.error("Error: ", e);
			}
		} else
		if(o instanceof Message) {
			logger.info("Message on top: "+((Message)o).getFullMessageName());
		} else
		if(o instanceof Property) {
			try {
				logger.info("Property on top: "+((Property)o).getFullPropertyName());
			} catch (NavajoException e) {
				logger.error("Error: ", e);
			}
			
		} else {
			if(o!=null) {
				logger.info("Other object:" + o.getClass());
			} else {
				logger.info("Null object on stack!");
			}
		}
		
	}
	
	/* (non-Javadoc)
	 * @see com.dexels.navajo.client.context.ClientContext#getNavajo(java.lang.String)
	 */
	@Override
	public Navajo getNavajo(String name) {

		Navajo navajo = myNavajoMap.get(name);
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
			logger.info("Warning, attempted to push null property!");
		}
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
			logger.error("Error: ", e);
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

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	public String getUsername() {
		return username;
	}


	public String getPassword() {
		return password;
	}



	
}
