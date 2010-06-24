package com.dexels.navajo.rhino;


import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;


import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Method;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Operand;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.mapping.MappableTreeNode;
import com.dexels.navajo.server.Access;


public class StackScriptEnvironment extends ScriptEnvironment {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final Map<String, Navajo> myNavajoMap = new HashMap<String, Navajo>();
	private final Map<Navajo, String> myInverseNavajoMap = new HashMap<Navajo, String>();
	private final Stack<Object> myElementStack = new Stack<Object>();
	private final Stack<MappableTreeNode> treeNodeStack = new Stack<MappableTreeNode>();

	public StackScriptEnvironment() {
		//System.err.println("New navajo context");
	}

	public Object navajoEvaluate(String expression) throws NavajoException {
		
		Operand o = NavajoFactory.getInstance().getExpressionEvaluator().evaluate(expression, getAccess().getInDoc(),getCurrentTreeNode(), getMessage());
		if(o==null) {
			return null;
		}
		System.err.println(":: "+expression+" evaluated to: "+o.value);
		return o.value;
	}

	
	public void setAccess(Access access) {
		super.setAccess(access);
		myElementStack.push(access.getOutputDoc());
		
	}

	public void pushMappableTreeNode(Object o) {
		MappableTreeNode top = null;
		if(!treeNodeStack.isEmpty()) {
			top = treeNodeStack .peek();

		}
		// TODO Add support for arrays!
		MappableTreeNode mtn = new MappableTreeNode(getAccess(), top, o, false);
		treeNodeStack.push(mtn);
	}


	public MappableTreeNode getCurrentTreeNode() {
		if(!treeNodeStack.isEmpty()) {
			return treeNodeStack .peek();
		}
		return null;
	}	

	public MappableTreeNode popMappableTreeNode() {
		return treeNodeStack.pop();
	}	
	
	public void reset() {
		myNavajoMap.clear();
		myInverseNavajoMap.clear();
		myElementStack.clear();
	}
	

	
	public Map<String,Navajo> getNavajos() {
		return myNavajoMap;
	}

	public String getServiceName(Navajo n) {
		return myInverseNavajoMap.get(n);
	}
	
	protected void callFinished(String service, Navajo n) {
		myNavajoMap.put(service, n);
		myInverseNavajoMap.put(n, service);
		myElementStack.push(n);
		System.err.println("Call finished");
	}


	public boolean hasNavajo(String name) {
		return myNavajoMap.containsKey(name);
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
			return null;
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


	@SuppressWarnings("unchecked")
	private Object getTopmostElement(Class[] cls) {
		for (int i = myElementStack.size()-1; i>=0; i--) {
			Object e  = myElementStack.get(i); 
			for (Class clz : cls) {
				if(clz.isAssignableFrom(e.getClass()) ) {
					return e;
				}
			}
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	private Object getTopmostElement(Class cls) {
		return getTopmostElement(new Class[]{cls});
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
	
	public Map<String,Property> getPropertyElement() {
		return new PropertyAccessMap(this);
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
		p.setValue(value);
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

	public void setValue(String path, Object value) {
		Object oo = getTopmostElement(new Class[]{Message.class,Navajo.class});
		if(Message.class.isAssignableFrom(oo.getClass())) {
			Property p = ((Message)oo).getProperty(path);
			p.setAnyValue(value);
		}
		if(Navajo.class.isAssignableFrom(oo.getClass())) {
			Property p = ((Navajo)oo).getProperty(path);
			p.setAnyValue(value);
		}
		System.err.println("Odd stack problem");
	}

	public void setValue(Object value) {
		getProperty().setAnyValue(value);
	}	
	
	public Message addMessage(String name) throws NavajoException {
		Object oo = getTopmostElement(new Class[]{Message.class,Navajo.class});
		if(Message.class.isAssignableFrom(oo.getClass())) {
			
			return super.addMessage((Message)oo, name);
		}
		if(Navajo.class.isAssignableFrom(oo.getClass())) {
			System.err.println("Navajo detected");
			return super.addMessage((Navajo)oo, name);

		}
		return null;
	}
	public Message addArrayMessage(String name) throws NavajoException {
		Object oo = getTopmostElement(new Class[]{Message.class,Navajo.class});
		if(Message.class.isAssignableFrom(oo.getClass())) {
			return super.addArrayMessage((Message)oo, name);
		}
		if(Navajo.class.isAssignableFrom(oo.getClass())) {
			return super.addArrayMessage((Navajo)oo, name);

		}
		return null;
	}
	
	public Message addElement() throws NavajoException {
		return super.addElement(getMessage());
	}
	
	public Property addProperty(String name, Object value) throws NavajoException {
		return super.addProperty(getMessage(), name, value);
	}

	public Method addMethod(String name) throws NavajoException {
//		return super.addProperty(getMessage(), name, value);
		Method m = NavajoFactory.getInstance().createMethod(getAccess().getOutputDoc(), name,null );
		getAccess().getOutputDoc().addMethod(m);
		System.err.println("Adding method with name: "+name);
		trapContinuation(m, new ContinuationHandler() {
			
			@Override
			public void run() {
				
				System.err.println("Finished sleeping.");
				new Thread(){
					public void run() {
						try {
							Thread.sleep(5000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						resumeScript();
					}
				}.start();
			}
		});
		return m;
	}

}

