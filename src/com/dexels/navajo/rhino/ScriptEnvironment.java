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

import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContinuationPending;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

import com.dexels.navajo.client.AsyncClient;
import com.dexels.navajo.client.AsyncClientFactory;
import com.dexels.navajo.client.NavajoResponseHandler;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Operand;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.mapping.MappableTreeNode;
import com.dexels.navajo.server.Access;

public class ScriptEnvironment implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Scriptable globalScope;

	private boolean async = false;

	private Access access;

	private boolean holdScript;

	private ScriptFinishHandler onFinishHandler;
	
	public Access getAccess() {
		return access;
	}


	public void setAccess(Access access) {
		this.access = access;
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


	public AsyncClient getClient() {
		return AsyncClientFactory.getInstance();
	}


	public Navajo createNavajo() {
		return NavajoFactory.getInstance().createNavajo();
	}
	
	public void dump(Navajo n) {
		try {
			if (n==null) {
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
			if (m==null) {
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

	public Message addMessage(Navajo n, String name) throws NavajoException {
		Message m = NavajoFactory.getInstance().createMessage(n, name);
		n.addMessage(m);
		return m;
	}
	public Message addArrayMessage(Navajo n, String name) throws NavajoException {
		Message m = NavajoFactory.getInstance().createMessage(n, name,Message.MSG_TYPE_ARRAY);
		n.addMessage(m);
		return m;
	}

	
	public Message addMessage(Message parent, String name) throws NavajoException {
		Message m = NavajoFactory.getInstance().createMessage(parent.getRootDoc(), name);
		parent.addMessage(m);
		return m;
	}
	
	public Message addArrayMessage(Message parent, String name) throws NavajoException {
		Message m = NavajoFactory.getInstance().createMessage(parent.getRootDoc(), name,Message.MSG_TYPE_ARRAY);
		parent.addMessage(m);
		return m;
	}
	
	public Message addElement(Message parent) throws NavajoException {
		Message m = NavajoFactory.getInstance().createMessage(parent.getRootDoc(), parent.getName(),Message.MSG_TYPE_ARRAY_ELEMENT);
		parent.addMessage(m,parent.getArraySize());
		return m;
	}
	public Property addProperty(Message parent, String name, Object value) throws NavajoException {
		Property p = NavajoFactory.getInstance().createProperty(parent.getRootDoc(), name, Property.STRING_PROPERTY, "", 0, "", Property.DIR_INOUT);
		p.setAnyValue(value);
		parent.addProperty(p);
		return p;
	}
	

	
	public Object navajoEvaluate(String expression, Navajo n) throws NavajoException {
		Operand o = NavajoFactory.getInstance().getExpressionEvaluator().evaluate(expression, n);
		if(o==null) {
			return null;
		}
		return o.value;
	}
	

	public Object navajoEvaluate(String expression) throws NavajoException {
		Operand o = NavajoFactory.getInstance().getExpressionEvaluator().evaluate(expression, access.getInDoc());
		
		if(o==null) {
			return null;
		}

		return o.value;
	}
	
	
	public void callService(final String service, Navajo n) throws IOException, NavajoException,ContinuationPending {
		
		try {
			Context context = Context.enter();
			final ContinuationPending cp = context.captureContinuation();
			final Object c = cp.getContinuation();
			
			final Object continuation = c;// = reserialize(c);
			NavajoResponseHandler nrh = new NavajoResponseHandler() {
				@Override
				public void onResponse(Navajo n) {
//					System.err.println("Result received");
					callFinished(service,n);
					continueScript(continuation, n);
				}
			};
			getClient().callService(n, service, nrh);
//			System.err.println("Freezing!");
			throw (cp);
		} finally {
			Context.exit();
		}
	}
	
	public void trapContinuation(Object localFunctionResult, ContinuationHandler ch) {
		try {
			Context context = Context.enter();
			final ContinuationPending cp = context.captureContinuation();
			final Object c = cp.getContinuation();
			
			final Object continuation = c;// = reserialize(c);
			ch.setContinuation(continuation);
			ch.setFunctionResult(localFunctionResult);
			ch.setEnv(this);
			//			ContinuationHandler nrh = new ContinuationHandler(c,localFunctionResult,this) {
//				@Override
//				public void run() {
//					continueScript(continuation, getFunctionResult());
//				}
//			};
//			cp.setApplicationState(nrh);
			ch.run();
			throw (cp);
//			return; 
		} finally {
			Context.exit();
		}

	}
	  
	protected void callFinished(String service, Navajo n) {
		
	}


	public void continueScript(final Object pending, final Object functionResult) {
//		Thread.dumpStack();
//		new Thread() {
//			public void run() {
				try {
//					callStack.push("noot");
					Context context = Context.enter();
					
//					System.err.println("Resuming");
				 	  context.resumeContinuation(pending, globalScope, functionResult);
			      } catch (ContinuationPending e) {
			    	  return;
			      } finally {
					Context.exit();
			      }
//			      System.err.println("Finishing script run!");
//			      System.err.println("Stack: "+callStack);
			      finishRun();
//, Context.javaToJS(new ScriptEnvironment(), globalScope)			  	  
			 //     globalScope.get
//			}
//		}.start();
	}


	private Object reserialize(Object c) {
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(out);
			oos.writeObject(c);
			oos.flush();
			oos.close();
			byte[] data = out.toByteArray();
			System.err.println("Serialized continuation size: "+data.length);
			ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
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

	public void callService(String service) throws IOException, NavajoException,ContinuationPending {
		Navajo input = NavajoFactory.getInstance().createNavajo();
		callService(service,input);
	}
	
	protected void finishRun() {
		Navajo response = getResponse();
		  try {
			response.write(System.err);
		} catch (NavajoException e) {
			e.printStackTrace();
		}
		if(onFinishHandler!=null) {
			onFinishHandler.run();
		}
		synchronized (this) {
			this.notify();
		}

	}


	public Navajo getResponse() {
		Navajo response = (Navajo) Context.jsToJava( ScriptableObject.getProperty(globalScope, "output"),Navajo.class);
		return response;
	}
	
	
	public void log(String s) {
		System.err.println("Log: "+s);
	}

	public Navajo getTml(String path) throws FileNotFoundException {
		File f= new File(path);
		if(!f.exists()) {
			return null;
		}
		FileReader fr = new FileReader(f);
		Navajo n = NavajoFactory.getInstance().createNavajo(fr);
		return n;
	}


	public void setFinishClosure(ScriptFinishHandler onFinish) {
		this.onFinishHandler = onFinish;
	}

}
