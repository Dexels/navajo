package com.dexels.navajo.rhino;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Map;
import java.util.Stack;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContinuationPending;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.adapter.JDBCMap;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Operand;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.Selection;
import com.dexels.navajo.functions.util.FunctionFactoryFactory;
import com.dexels.navajo.jdbc.JDBCFactory;
import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.mapping.MappingException;
import com.dexels.navajo.mapping.MappingUtils;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.DispatcherFactory;
import com.dexels.navajo.server.UserException;

public abstract class ScriptEnvironment implements Serializable {

	private static final long serialVersionUID = 1L;

	private Scriptable globalScope;

	private boolean async = false;

	private Access access;

	private ScriptFinishHandler onFinishHandler;
	protected final Stack<Message> inputMessageStack = new Stack<Message>();
	protected final Stack<Message> paramMessageStack = new Stack<Message>();
	private transient Context currentContext;

	final static Logger logger = LoggerFactory
			.getLogger(Logger.ROOT_LOGGER_NAME);

	public Access getAccess() {
		return access;
	}

	public void dumpRequest() throws NavajoException {
		getAccess().getInDoc().write(System.err);
	}

	public void dumpResponse() throws NavajoException {
		getAccess().getOutputDoc().write(System.err);
	}

	public void setAccess(Access access) {
		this.access = access;
	}

	public void pushInputMessage(Message m) {
		inputMessageStack.push(m);
	}

	public Message popInputMessage() {
		return inputMessageStack.pop();
	}

	public void pushParamMessage(Message m) {
		paramMessageStack.push(m);
	}

	public Message popParamMessage() {
		return paramMessageStack.pop();
	}

	public boolean isAsync() {
		return async;
	}

	public void setAsync(boolean async) {
		this.async = async;
	}

	public void setGlobalScope(Scriptable globalScope) {
		this.globalScope = globalScope;
	}

	public Scriptable getGlobalScope() {
		return globalScope;
	}



	// TODO Add length
	public Property createProperty(String name, Object value,
			Map<String, String> attributes, Navajo rootDoc)
			throws NavajoException {
		Property result = NavajoFactory.getInstance().createProperty(rootDoc,
				name, attributes.get("type"), "", 0,
				attributes.get("description"), attributes.get("direction"));
		result.setAnyValue(value);
		return result;
	}

	public Navajo createNavajo() {
		return NavajoFactory.getInstance().createNavajo();
	}

	public void dump(Navajo n) {
		try {
			if (n == null) {
				System.err.println("[Null navajo]");
			} else {
				n.write(System.err);
			}
		} catch (NavajoException e) {
			e.printStackTrace();
		}
	}

	public void dump(Message m) {
		try {
			if (m == null) {
				System.err.println("[Null message]");
			} else {
				m.write(System.err);
			}
		} catch (NavajoException e) {
			e.printStackTrace();
		}
	}

	public Object getValue(Navajo n, String path) {
		Property p = n.getProperty(path);
		return p.getTypedValue();
	}

	public Object getValue(Message m, String path) {
		Property p = m.getProperty(path);
		return p.getTypedValue();
	}

	public void setValue(Navajo n, String path, Object value) {
		Property p = n.getProperty(path);
		p.setAnyValue(value);
	}

	public Context getCurrentContext() {
		return currentContext;
	}

	public void setCurrentContext(Context currentContext) {
		this.currentContext = currentContext;
	}

//	public Message addMessage(Navajo n, String name) throws NavajoException {
//		Message old = n.getMessage(name);
//
//		if (old != null) {
//			getAccess().setCurrentOutMessage(old);
//			return old;
//		}
//		Message m = NavajoFactory.getInstance().createMessage(n, name);
//		n.addMessage(m);
//		getAccess().setCurrentOutMessage(m);
//
//		return m;
//	}

	public Message addArrayMessage(Navajo n, String name)
			throws NavajoException {
		Message old = n.getMessage(name);
		if (old != null) {
			old.setType(Message.MSG_TYPE_ARRAY);
			getAccess().setCurrentOutMessage(old);
			return old;
		}
		Message m = NavajoFactory.getInstance().createMessage(n, name,
				Message.MSG_TYPE_ARRAY);
		n.addMessage(m);
		getAccess().setCurrentOutMessage(m);
		return m;
	}

//	public Message addMessage(Message parent, String name)
//			throws NavajoException {
//		Message old = parent.getMessage(name);
//		if (old != null) {
//			getAccess().setCurrentOutMessage(old);
//			return old;
//		}
//		Message m = NavajoFactory.getInstance().createMessage(
//				parent.getRootDoc(), name);
//		parent.addMessage(m);
//		getAccess().setCurrentOutMessage(m);
//
//		return m;
//	}

	public Selection addSelection(Property p, String name, Object value, Object selected) throws NavajoException {
		Selection s = NavajoFactory.getInstance().createSelection(
				getAccess().getOutputDoc(), name, "" + value, selected);
		p.addSelection(s);
		if (p.getCardinality() == null) {
			p.setCardinality(Property.CARDINALITY_SINGLE);
		}
		return s;
	}
	
//	public Selection addSelection(Property p, String name, Object value, boolean selected) throws NavajoException {
//		Selection s = NavajoFactory.getInstance().createSelection(
//				getAccess().getOutputDoc(), name, "" + value, selected);
//		p.addSelection(s);
//		if (p.getCardinality() == null) {
//			p.setCardinality(Property.CARDINALITY_SINGLE);
//		}
//		return s;
//	}

	public Message addArrayMessage(Message parent, String name)
			throws NavajoException {
		Message old = parent.getMessage(name);
		if (old != null) {
			getAccess().setCurrentOutMessage(old);
			old.setType(Message.MSG_TYPE_ARRAY);

			return old;
		}
		Message m = NavajoFactory.getInstance().createMessage(
				parent.getRootDoc(), name, Message.MSG_TYPE_ARRAY);
		parent.addMessage(m);
		getAccess().setCurrentOutMessage(m);

		return m;
	}

	public Message addElement(Message parent) throws NavajoException {
		String parentType = parent.getType();
		if (!Message.MSG_TYPE_ARRAY.equals(parentType)) {
			parent.setType(Message.MSG_TYPE_ARRAY);
		}
		Message m = NavajoFactory.getInstance().createMessage(
				parent.getRootDoc(), parent.getName(),
				Message.MSG_TYPE_ARRAY_ELEMENT);
		parent.addMessage(m, parent.getArraySize());
		getAccess().setCurrentOutMessage(m);

		return m;
	}

	public Property addProperty(Message parent, String name, Object value, Map<String,String> attributes)
			throws NavajoException, MappingException {
		String navajoType = null;
		String subtype = null;
		String direction = null;
		String description = null;
		String cardinality = null;
		String literalValue = null;
		int length = 0;
		if(attributes!=null) {
			subtype = attributes.get(Property.PROPERTY_SUBTYPE);
			direction = attributes.get(Property.PROPERTY_DIRECTION);
			description = attributes.get(Property.PROPERTY_DESCRIPTION);
			cardinality = attributes.get(Property.PROPERTY_CARDINALITY);
			literalValue = attributes.get(Property.PROPERTY_VALUE);
			String lengthString = attributes.get(Property.PROPERTY_LENGTH);
			if(lengthString!=null) {
				length = Integer.parseInt(lengthString);
			}
			navajoType = attributes.get(Property.PROPERTY_TYPE);
		}
		if(value==null) {
				//
		} else {
			navajoType = NavajoFactory.getInstance().getNavajoType(value.getClass());
		}
		Property p = MappingUtils.setProperty(false, parent, name, value, navajoType, subtype, direction, description, length, getAccess().getOutputDoc(), getAccess().getInDoc(), false);
		if ( Property.SELECTION_PROPERTY.equals(navajoType) && cardinality != null ) {
			p.setCardinality(cardinality);
		}
		if ( literalValue != null ) {
			p.setValue(literalValue);
		}
		//		Property p = NavajoFactory.getInstance().createProperty(
//				parent.getRootDoc(), name, Property.STRING_PROPERTY, "", 0, "",
//				Property.DIR_INOUT);
//		p.setAnyValue(value);
//		if(value!=null) {
//			p.setType(navajoType);
//		}
//		
//		parent.addProperty(p);
		return p;
	}

	// public Property addSelectionProperty(Message parent, String name, Object
	// value) throws NavajoException {
	// Property p =
	// NavajoFactory.getInstance().createProperty(parent.getRootDoc(), name,
	// Property.STRING_PROPERTY, "", 0, "", Property.DIR_INOUT);
	// p.setAnyValue(value);
	// parent.addProperty(p);
	// return p;
	// }

	public Object navajoEvaluate(String expression, Navajo n)
			throws NavajoException {
		Operand o = NavajoFactory.getInstance().getExpressionEvaluator()
				.evaluate(expression, n);
		if (o == null) {
			return null;
		}
		return o.value;
	}

	public Object navajoEvaluate(String expression) throws NavajoException {
		Operand o = NavajoFactory.getInstance().getExpressionEvaluator()
				.evaluate(expression, access.getInDoc());

		if (o == null) {
			return null;
		}

		return o.value;
	}

	public void storeMap(Object map) throws MappableException, UserException {
		if (map instanceof Mappable) {
			logger.debug("Storing map:"+map);
			((Mappable) map).store();
		} else {
			logger.debug("Not calling store: Not mappable: "+map.getClass()+" map: "+map);
		}
	}
	
	public void killMap(Object map) throws MappableException, UserException {
		if (map instanceof Mappable) {
			logger.debug("Killing map");
			((Mappable) map).kill();
		} else {
			logger.debug("Not calling store: Not mappable");
		}
	}

	public void loadMap(Object map) throws MappableException, UserException {
		if (map instanceof Mappable) {
			((Mappable) map).load(getAccess());
		}
	}


	/*
	 * Note that the ch runner will be called BEFORE the continuation!
	 */
	public void trapContinuation(Object localFunctionResult,
			ContinuationHandler ch) {
		try {
			Context context = Context.enter();
			final ContinuationPending cp = context.captureContinuation();
			final Object c = cp.getContinuation();
			final Object continuation = c;// = reserialize(c);
			ch.setContinuation(continuation);
			ch.setFunctionResult(localFunctionResult);
			ch.setEnv(this);
			// ContinuationHandler nrh = new
			// ContinuationHandler(c,localFunctionResult,this) {
			// @Override
			// public void run() {
			// continueScript(continuation, getFunctionResult());
			// }
			// };
			// cp.setApplicationState(nrh);
			ch.run();
			throw (cp);
			// return;
		} finally {
			Context.exit();
		}

	}

	public void callFinished(String service, Navajo n) {

	}

	// public TmlRunnable createContinueRunnable(final Object pending) {
	//
	// return new ContinuationRunnable(this, pending);
	// }

	public void continueScript(final Object pending, final Object functionResult) {
		try {
			Context context = Context.enter();
			context.resumeContinuation(pending, globalScope, functionResult);
		} catch (ContinuationPending e) {
			return;
		} finally {
			Context.exit();
		}
		finishRun();
	}

	public Object reserialize(Object c) {
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(out);
			oos.writeObject(c);
			oos.flush();
			oos.close();
			byte[] data = out.toByteArray();
			logger.info("Serialized continuation size: " + data.length);
			ObjectInputStream ois = new ObjectInputStream(
					new ByteArrayInputStream(data));
			Object oo = ois.readObject();
			System.err.println("Reserialize successful!");
			return oo;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return c;
	}


	protected void finishRun() {
		// Navajo response = getResponse();
		// try {
		// response.write(System.err);
		// } catch (NavajoException e) {
		// e.printStackTrace();
		// }
		if (onFinishHandler != null) {
			onFinishHandler.run();
		}
		synchronized (this) {
			this.notify();
		}

	}

	public Navajo getResponse() {
		return getAccess().getOutputDoc();
	}

	public void log(String s) {
		logger.info(s);
	}

	public void logException(String s,Object e) {
		Throwable t = (Throwable)e;
		logger.error(s,t);
	}
	
	
	public void dump(String message, Object s) {
		logger.info("Object dump:"+message+" obj: "+s);
	}

	public Navajo getTml(String path) throws FileNotFoundException {
		File f = new File(path);
		if (!f.exists()) {
			return null;
		}
		FileReader fr = new FileReader(f);
		Navajo n = NavajoFactory.getInstance().createNavajo(fr);
		return n;
	}

	public void setFinishClosure(ScriptFinishHandler onFinish) {
		this.onFinishHandler = onFinish;
	}

	public Object createMappable(String className, Navajo input, Navajo output,
			Message currentOutMessage) throws ClassNotFoundException {
		logger.debug("Creating map for class: "+className);
		try {
			
			if("com.dexels.navajo.adapter.SQLMap".equals(className) && JDBCFactory.useOSGi() ) {
				System.err.println("REPLACE!");
				className = "com.dexels.navajo.adapter.JDBCMap";
			}
			ClassLoader cl = DispatcherFactory.getInstance().getNavajoConfig()
					.getClassloader();
			// Note, the ClassLoader is not actually used by OSGi
			Class<?> mclass = FunctionFactoryFactory.getInstance()
					.getAdapterClass(className, cl);
			// Class<?> mclass = Class.forName(className, true, cl);
			Object obj = mclass.newInstance();
			logger.debug("Returning instantiate map: "+obj);
			return obj;
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void scheduleCallback(Function f) {
		System.err.println("Schedulecallback!!!!!");

		f.call(getCurrentContext(), createSubScope(globalScope), null,
				new Object[] {});
	}

	public Scriptable createSubScope(Scriptable parentScope) {
		Scriptable s = new ScriptableObject() {
			/**
			 * 
			 */
			private static final long serialVersionUID = -3991431939619984951L;

			@Override
			public String getClassName() {
				return "Aap";
			}
		};
		s.setParentScope(parentScope);
		ScriptEnvironment subEnvironment = createEnvironment();
		ScriptableObject.putProperty(s, "env",
				Context.javaToJS(subEnvironment, s));
		return s;
	}

	public abstract ScriptEnvironment createEnvironment();

}
