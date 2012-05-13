package com.dexels.navajo.rhino;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import org.xml.sax.SAXException;

import com.dexels.navajo.document.ExpressionEvaluator;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Method;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Operand;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.Selection;
import com.dexels.navajo.mapping.MappableTreeNode;
import com.dexels.navajo.mapping.MappingException;
import com.dexels.navajo.mapping.MappingUtils;
import com.dexels.navajo.parser.DefaultExpressionEvaluator;
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

	private Message currentParamMessage = null;

	public StackScriptEnvironment() {
		// System.err.println("New navajo context");
		if (NavajoFactory.getInstance().getExpressionEvaluator() == null) {
			NavajoFactory.getInstance().setExpressionEvaluator(
					new DefaultExpressionEvaluator());
		}
	}

	public StackScriptEnvironment(StackScriptEnvironment original) {
		myNavajoMap.putAll(original.myNavajoMap);
		myInverseNavajoMap.putAll(original.myInverseNavajoMap);
		myElementStack.addAll(original.myElementStack);
		treeNodeStack.addAll(original.treeNodeStack);
		currentParamMessage = original.currentParamMessage;
	}

	public void blockDebug() {
		System.err.println("So something insignificant");
	}

	public Object navajoEvaluate(String expression) throws NavajoException {
		// if (getCurrentTreeNode()==null) {
		// System.err.println("Evaluating navajo: "+expression+" without tree");
		// } else {
		// System.err.println("Evaluating navajo: "+expression+" with top: "+getCurrentTreeNode().getMyMap().getClass());
		//
		// }
		Operand o = null;
		Navajo inDoc = getAccess().getInDoc();
		Message top = null;
		if (!inputMessageStack.isEmpty()) {
			top = inputMessageStack.peek();
		}
		// try {
		ExpressionEvaluator expressionEvaluator = NavajoFactory.getInstance()
				.getExpressionEvaluator();
		o = expressionEvaluator.evaluate(expression, inDoc,
				getCurrentTreeNode(), top, getTopParamStackMessage());
		// } catch (Throwable e) {
		// log("Error evaluating expression: "+expression);
		// e.printStackTrace(getLogger());
		// }
		if (o == null) {
			return null;
		}
		// System.err.println(":: "+expression+" evaluated to: "+o.value);
		return o.value;
	}

	private Message getTopParamStackMessage() throws NavajoException {
		if (paramMessageStack.isEmpty()) {
			return getTopParamMessage();
		}
		return paramMessageStack.peek();
	}

	private Message getTopParamMessage() throws NavajoException {
		for (int i = myElementStack.size() - 1; i >= 0; i--) {
			Object o = myElementStack.get(i);
			if (o instanceof Message) {
				if (isParamMessage((Message) o)) {
					return (Message) o;
				}

			}
		}
		return getParamMessage();
	}

	private boolean isParamMessage(Message m) {
		Message c = m;
		Message paramRoot = getAccess().getInDoc().getMessage(
				Message.MSG_PARAMETERS_BLOCK);
		do {
			c = c.getParentMessage();
			if (c == paramRoot) {
				return true;
			}
		} while (c != null);
		return false;
	}

	public void addField(String fieldName, Object value)
			throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		MappableTreeNode treeTop = treeNodeStack.peek();
		if (treeTop == null) {
			log("Can not set field, no map detected");
			return;
		}
		Object map = treeTop.getMyMap();
		String fieldSetter = "set" + fieldName.substring(0, 1).toUpperCase()
				+ fieldName.substring(1);
		// System.err.println("Assumming setter name: "+fieldSetter);
		Class<? extends Object> mapClass = map.getClass();
//		Class[] mapClassList = mapClass.getClasses();

		Class<?> t = mapClass;

		int emergencyCounter = 0;
		while (t != null && !t.equals(Object.class) && emergencyCounter < 10) {
			// System.err.println("Attempting class: "+t);
			boolean found = setValueForClass(value, map, fieldSetter, t);
			if (found) {
				return;
			}

			t = mapClass.getSuperclass();
			emergencyCounter++;
		}
		// for (Class t : mapClassList) {
		// }

		log("WARNING SETTER FOR FIELD: " + fieldName + " failed");
	}

	private boolean setValueForClass(Object value, Object map,
			String fieldSetter, Class<? extends Object> mapClass)
			throws IllegalAccessException, InvocationTargetException {
		java.lang.reflect.Method[] methods = mapClass.getMethods();
		for (java.lang.reflect.Method method : methods) {
			if (method.getName().equals(fieldSetter)) {
				// log("found qualified setter (based on name");
				Class<?>[] params = method.getParameterTypes();
				if (params.length == 1) {
					// log("Single parameter. Looking good.");
					// method.invoke(map, value);
					Class<?> prm = params[0];
					if (value == null) {
						// no further detective work possible
						method.invoke(map, value);
						return true;
					} else {

						//boolean a = prm.isAssignableFrom(value.getClass());

						Object v;
						if (prm.equals(Float.class)) {
//							System.err.println("Float conversion performed");
							v = Float.valueOf(value.toString());
						} else if (prm.equals(float.class)) {
							v = Float.valueOf(value.toString()).floatValue();
						} else {
							v = value;
						}
						method.invoke(map, v);
						return true;
					}
				} else {
					log("Ignoring multiparam setter");
				}
			}
		}
		return false;
	}

	public void pushMapReference(String fieldName)
			throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		MappableTreeNode treeTop = treeNodeStack.peek();
		if (treeTop == null) {
			log("Can not set field, no map detected");
			return;
		}
		// Object map = treeTop.getMyMap();

		Object map = createMapRef(fieldName);
		pushMappableTreeNode(map);
	}

	public boolean isArrayMapRef(String fieldName)
			throws IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {
		String fieldGetter = "get" + fieldName.substring(0, 1).toUpperCase()
				+ fieldName.substring(1);
		Object map = getCurrentTreeNode().getMyMap();
		System.err.println("Getting field: " + fieldGetter
				+ " from object type: " + map.getClass().getName());
		// debugTreeNodeStack();
		java.lang.reflect.Method[] methods = map.getClass().getMethods();
		for (java.lang.reflect.Method method : methods) {
			// System.err.println("Examining method: "+method.toString()+" in class: "+map.getClass());
			if (method.getName().equals(fieldGetter)) {
				// log("found qualified getter");
//				int paramCount = method.getParameterTypes().length;
				// log("Params in ref: "+paramCount);
				return method.getReturnType().isArray();
			}
		}
		throw new NoSuchMethodException(fieldGetter + "in object: " + map);
	}

	public void debugTreeNodeStack(String message) {
		log("DEBUG TREE: " + message);
		if (treeNodeStack.isEmpty()) {
			log(">>> EMPTY");
		} else {
			for (MappableTreeNode m : treeNodeStack) {
				System.err.println("M: " + m.getMapName());
			}
		}
	}

	// includes a stack push!
	public Object createMapRef(String fieldName) throws IllegalAccessException,
			InvocationTargetException {
		// It's also possible that there is no mapRef yet, and we need to create one!
		String fieldGetter = "get" + fieldName.substring(0, 1).toUpperCase()
				+ fieldName.substring(1);
		Object map = getCurrentTreeNode().getMyMap();
		java.lang.reflect.Method[] methods = map.getClass().getMethods();
		for (java.lang.reflect.Method method : methods) {
			if (method.getName().equals(fieldGetter)) {
//				int paramCount = method.getParameterTypes().length;
				Object mapref = method.invoke(map, new Object[] {});
				boolean isArray = method.getReturnType().isArray();
				if(mapref==null) {
					// so: no 
				} else {
					if (isArray ) {
						 log("Element# "+ ((Object[])mapref).length+" for getter: "+fieldGetter+" map: "+map);
					}
					pushMappableTreeNode(mapref, isArray);
				}
				return mapref;
			}
		}
		log("No getter found?!");
		return null;
	}

	public void popMapReference() {
		popMappableTreeNode();
	}

	public void setAccess(Access access) {
		super.setAccess(access);
		myElementStack.push(access.getOutputDoc());

	}

	public void pushMappableTreeNode(Object o) {
		pushMappableTreeNode(o, false);
	}

	public Object pushMappableTreeNode(Object o, boolean isArray) {
		// log("pushMappable>>> "+o);
		MappableTreeNode top = null;
		if (!treeNodeStack.isEmpty()) {
			top = treeNodeStack.peek();
		}
		MappableTreeNode mtn = new MappableTreeNode(getAccess(), top, o,
				isArray);
		treeNodeStack.push(mtn);
		return mtn.getMyMap();
	}

	public Message getParamMessage(String name) {
		Navajo n = super.getAccess().getOutputDoc();
		if (name.startsWith("/@")) {
			name = name.substring(2);
		}
		Message params = n.getMessage(Message.MSG_PARAMETERS_BLOCK);
		if (params == null) {
			return null;
		}
		return params.getMessage(name);
	}

	public MappableTreeNode getCurrentTreeNode() {
		if (!treeNodeStack.isEmpty()) {
			return treeNodeStack.peek();
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

	public Map<String, Navajo> getNavajos() {
		return myNavajoMap;
	}

	public String getServiceName(Navajo n) {
		return myInverseNavajoMap.get(n);
	}

	public void callFinished(String service, Navajo n) {
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
		if (o instanceof Navajo) {
			Navajo n = (Navajo) o;
			System.err.println("Navajo on top:");
			try {
				n.write(System.err);
			} catch (NavajoException e) {
				e.printStackTrace();
			}
		} else if (o instanceof Message) {
			System.err.println("Message on top: "
					+ ((Message) o).getFullMessageName());
		} else if (o instanceof Property) {
			try {
				System.err.println("Property on top: "
						+ ((Property) o).getFullPropertyName());
			} catch (NavajoException e) {
				e.printStackTrace();
			}

		} else {
			if (o != null) {
				System.err.println("Other object:" + o.getClass());
			} else {
				System.err.println("Null object on stack!");
			}
		}

	}

	public Navajo getNavajo(String name) {

		Navajo navajo = myNavajoMap.get(name);
		// if (navajo == null) {
		// throw new IllegalStateException( "Unknown service: " +
		// name+" known services: "+myNavajoMap.keySet());
		// }
		return navajo;
	}

	public Property getProperty(String service, String path) {
		Navajo n = getNavajo(service);
		Property p = n.getProperty(path);
		if (p == null) {
			throw new IllegalStateException("Unknown property: " + path
					+ " in service " + service);
		}
		return p;
	}

	public Object getPropertyValue(String service, String path) {
		Property p = getProperty(service, path);
		return p.getTypedValue();
	}

	public String getNavajoName() {
		Navajo n = getNavajo();
		if (n == null) {
			return null;
		}
		return myInverseNavajoMap.get(n);
	}

	public Navajo getNavajo() {
		if (myElementStack.isEmpty()) {
			throw new IllegalStateException(
					"No default navajo found. Either supply a name explicitly, or make sure you are within a 'call' tag");
		}
		return (Navajo) getTopmostElement(Navajo.class);

	}

	public void popNavajo() {
		System.err.println("navajo");

		myElementStack.pop();

	}

	public void popElement() {
	
		myElementStack.pop();

	}
	public void pushNavajo(Navajo m) {
		System.err.println("pushnavajo");

		myElementStack.push(m);
	}

	public Message getMessage() {
		if (myElementStack.isEmpty()) {
			System.err.println("Empty stack!");
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

	private Object getTopmostElement(Class<?>[] cls) {
		for (int i = myElementStack.size() - 1; i >= 0; i--) {
			Object e = myElementStack.get(i);
			for (Class<?> clz : cls) {
				if (clz.isAssignableFrom(e.getClass())) {
					return e;
				}
			}
		}
		return null;
	}

	private Object getTopmostElement(@SuppressWarnings("rawtypes") Class cls) {
		return getTopmostElement(new Class[] { cls });
	}

	public void popMessage() {
		myElementStack.pop();
		getAccess().setCurrentOutMessage(getMessage());
	}
	
	// no stack activity
	public Message getInputMessage(String path) throws NavajoException {
		String path2 = path.replaceAll("@", Message.MSG_PARAMETERS_BLOCK + "/");
		Message result = getAccess().getInDoc().getMessage(path2);
		if (result == null) {
			System.err.println("Can't find message: " + path2);
			System.err.println("In doc:");
			getAccess().getInDoc().write(System.err);

			getAccess().getOutputDoc().write(System.err);
		}
		result.write(System.err);
		return result;
	}

	public void pushMessage(Message m) {
		getAccess().setCurrentOutMessage(m);
		if (m != null) {
			pushElement(m);
		}
	}

	public void pushProperty(Property p) {
		if (p != null) {
			pushElement(p);
		}
	}
	
	public void pushElement(Object item) {
		myElementStack.push(item);
	}



	public void debug() {
	}

	public String getMessagePath() {
		Message m = getMessage();
		if (m == null) {
			return null;
		}
		return createMessagePath(m);
	}

	public String getPropertyPath() {
		Property p = getProperty();
		if (p == null) {
			return null;
		}
		return createPropertyPath(p);
	}

	private String createMessagePath(Message m) {
		String navajoName = getServiceName(m.getRootDoc());
		String msg = m.getFullMessageName();
		return navajoName + "|" + msg;
	}

	private String createPropertyPath(Property p) {
		String navajoName = getServiceName(p.getRootDoc());
		String prop;
		try {
			prop = p.getFullPropertyName();
			return navajoName + ":" + prop;
		} catch (NavajoException e) {
			e.printStackTrace();
		}

		return null;
	}

	public Property parsePropertyPath(String path) {
		if (path.indexOf(":") == -1) {
			Navajo n = getNavajo();
			if (n != null) {
				return n.getProperty(path);
			}

		} else {
			String[] elts = path.split(":");
			Navajo n = getNavajo(elts[0]);
			return n.getProperty(elts[1]);
		}
		return null;
	}

	public Map<String, Property> getPropertyElement() {
		return new PropertyAccessMap(this);
	}

	public void resolvePost(String name, String value) {
		if (name.indexOf(":") == -1) {
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
		if (myElementStack.isEmpty()) {
			sb.append("Empty element stack!");
		} else {
			sb.append("Stacksize: " + myElementStack.size());
		}
		for (Object a : myElementStack) {
			sb.append("Current object: " + a.getClass() + "\n");
		}
		return sb.toString();
	}

	public void popProperty() {
		myElementStack.pop();

	}

	public void setValue(String path, Object value) {
		Object oo = getTopmostElement(new Class[] { Message.class, Navajo.class });
		if (Message.class.isAssignableFrom(oo.getClass())) {
			Property p = ((Message) oo).getProperty(path);
			p.setAnyValue(value);
		}
		if (Navajo.class.isAssignableFrom(oo.getClass())) {
			Property p = ((Navajo) oo).getProperty(path);
			p.setAnyValue(value);
		}
		System.err.println("Odd stack problem");
	}

	public void setValue(Object value) {
		getProperty().setAnyValue(value);
	}

	public Message addMessage(String name,Map<String, String> attributes) throws NavajoException, IOException, SAXException, MappingException {
		Object oo = getTopmostElement(new Class[] { Message.class, Navajo.class });
		if (Message.class.isAssignableFrom(oo.getClass())) {
			Message parent = (Message) oo;
			Message[] mm = MappingUtils.addMessage(parent.getRootDoc(), parent, name, "", 1, attributes.get(Message.MSG_TYPE), attributes.get(Message.MSG_MODE), attributes.get(Message.MSG_ORDERBY));
			if(mm.length==0) {
				throw new MappingException("I've just created a message, but it isn't there.");
			}
			pushMessage(mm[0]);
			return mm[0];
//			Message resolvedParent = MappingUtils.getParentMessage(parent, name);
//			if(resolvedParent==null) {
//				logger.warn("Null resolved parent in addmessage.");
//			}
//			boolean isArray = parent.isArrayMessage();
//			MessageMappingUtils.getMessageObject(name, resolvedParent, true, parent.getRootDoc(), isArray, mode, -1)
//			if (isArray) {
//				return addElement();
//			}
//			Message m = super.addMessage(parent, name);
			
//			return m;
		}
		if (Navajo.class.isAssignableFrom(oo.getClass())) {
			Navajo n = (Navajo)oo;
			Message[] mm = MappingUtils.addMessage(n, null, name, "", 1, attributes.get(Message.MSG_TYPE), attributes.get(Message.MSG_MODE), attributes.get(Message.MSG_ORDERBY));
			if(mm.length==0) {
				throw new MappingException("I've just created a message, but it isn't there.");
			}
			pushMessage(mm[0]);
			return mm[0];
//			Message m = super.addMessage((Navajo) oo, name);
//			pushMessage(m);
//			return m;

		}

		return null;
	}

	public Message addArrayMessage(String name) throws NavajoException {
		Object oo = getTopmostElement(new Class[] { Message.class, Navajo.class });
		if (Message.class.isAssignableFrom(oo.getClass())) {
			Message m = super.addArrayMessage((Message) oo, name);
			pushMessage(m);
			return m;
		}
		if (Navajo.class.isAssignableFrom(oo.getClass())) {
			Message m = super.addArrayMessage((Navajo) oo, name);
			pushMessage(m);
			return m;

		}
		return null;
	}

	public Message addParamArrayMessage(String name) throws NavajoException {
		Navajo out = getAccess().getOutputDoc();
		Message params = getTopParamMessage();
		Message result = NavajoFactory.getInstance().createMessage(out, name,
				Message.MSG_TYPE_ARRAY);
		params.addMessage(result);

		result.write(System.err);
		currentParamMessage = result;
		pushMessage(result);
		return result;
	}

	public Message addElement() throws NavajoException {
		// System.err.println("######## TOPMOST: "+getMessage().getFullMessageName());
		// getMessage().write(System.err);
		Message e = super.addElement(getMessage());
		// System.err.println("Element added: "+getMessage().getFullMessageName());
		// getMessage().write(System.err);
		pushElement(e);
		return e;
	}

	public Property addProperty(String name, Object value, Map<String,String> attributes)
			throws NavajoException, MappingException {
		// System.err.println("Adding property: "+name+" to message: "+getMessage().getName());
		if (getMessage() == null) {
			log("No message, can not add property!");
			log(dumpStack());
		}
		Property p = super.addProperty(getMessage(), name, value,attributes);
		// pushProperty(p);
		return p;
	}

	public Property addParam(String name, Object value,
			Map<String, String> attributes) throws NavajoException {
//		int length = 0;
//		String str = attributes.get("length");
//		if (str != null) {
//			length = Integer.valueOf(str);
//		}

		String type = attributes.get("type");
		if (type == null) {
			type = Property.STRING_PROPERTY;
		}
		
		if(name.startsWith("/")) {
			Message param = getTopParamMessage();
			name = name.substring(1);
			Property pp = getParam(name, param);
			if (pp == null) {
				pp = createProperty(name, value, attributes, getAccess()
						.getInDoc());
				param.addProperty(pp);
			} else {
				pp.setAnyValue(value);
			}
			return pp;
			
		}
		try {
			Message param = getTopParamStackMessage();
			Property pp = getParam(name, param);
			if (pp == null) {
				pp = createProperty(name, value, attributes, getAccess()
						.getInDoc());
				param.addProperty(pp);
			} else {
				pp.setAnyValue(value);
			}
			return pp;
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		// return p;
		return null;
	}

	private Property getParam(String name, Message param)
			throws NavajoException {
		if (param == null) {
			param = getParamMessage();
		}
		Property pp = param.getProperty(name);

		return pp;
	}

	private Message getParamMessage() throws NavajoException {
		Message par = currentParamMessage;
		// if(par!=null) {
		// return par;
		// }
		par = getAccess().getInDoc().getMessage(Message.MSG_PARAMETERS_BLOCK);
		if (par == null) {
			par = NavajoFactory.getInstance().createMessage(
					getAccess().getInDoc(), Message.MSG_PARAMETERS_BLOCK,
					Message.MSG_TYPE_SIMPLE);
			getAccess().getInDoc().addMessage(par);
		}
		return par;
	}

	public Selection addSelection(String name, String value, int selected)
			throws NavajoException {
		return super.addSelection(getProperty(), name, value, selected);
	}
	public Selection addSelectionToProperty(Property p, String name, Object value,
			Integer selected) throws NavajoException {
		return super.addSelection(p, name, value, selected);
	}
	public Method addMethod(String name) throws NavajoException {
		// return super.addProperty(getMessage(), name, value);
		Method m = NavajoFactory.getInstance().createMethod(
				getAccess().getOutputDoc(), name, null);
		getAccess().getOutputDoc().addMethod(m);
		// System.err.println("Adding method with name: "+name);
		// trapContinuation(m, new ContinuationHandler() {
		//
		// @Override
		// public void run() {
		//
		// System.err.println("Finished sleeping.");
		// new Thread(){
		// public void run() {
		// try {
		// Thread.sleep(5000);
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// }
		// resumeScript();
		// }
		// }.start();
		// }
		// });
		return m;
	}

	public ScriptEnvironment createEnvironment() {
		return new StackScriptEnvironment(this);
	}

}
