package com.dexels.navajo.tipi.internal;

import java.util.*;

import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.tipixml.*;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */
public class TipiEvent extends TipiAbstractExecutable implements TipiExecutable {
	private String myEventName;
	
	private Runnable afterEvent = null;
	
	private Map<String, TipiValue> eventParameterMap = new HashMap<String, TipiValue>();

	public TipiEvent() {
	}

	public Object clone() {

		TipiEvent ti = new TipiEvent();
		ti.myEventName = this.myEventName;
		ti.setComponent(getComponent());
		ti.setExecutables(this.getExecutables());
		ti.eventParameterMap = new HashMap<String, TipiValue>();
		ti.setStackElement(getStackElement());
		ti.setAfterEvent(getAfterEvent());
		ti.setExpression(getExpression());
		Iterator<String> iter = this.eventParameterMap.keySet().iterator();

		while (iter.hasNext()) {
			String key = iter.next();
			TipiValue tv = (TipiValue) this.eventParameterMap.get(key).clone();
			ti.eventParameterMap.put(key, tv);
		}
		return ti;
	}

	/**
	 * event-specific vars:
	 * @param name
	 * @return
	 */
	public TipiValue getEventParameter(String name) {
		return eventParameterMap.get(name);
	}

	public void addEventParameter(String myKey, TipiValue tv) {
		eventParameterMap.put(myKey, tv);
	}

	public Set<String> getEventKeySet() {
		return eventParameterMap.keySet();
	}
	

	/**
	 * Initializes the eventtype from the classdef.
	 * @param xe
	 */
	public void init(XMLElement xe) {
		List<XMLElement> v = xe.getChildren();
		for (int i = 0; i < v.size(); i++) {
			XMLElement current = v.get(i);
			if (current.getName().equals("param")) {
				TipiValue tv = new TipiValue(getComponent());
				tv.load(current);
				eventParameterMap.put(tv.getName(), tv);
			}
		}
	}

	public void load(TipiComponent tc, XMLElement elm, TipiContext context) throws TipiException {
		setComponent(tc);
		String stringType = (String) elm.getAttribute("type");
		if (stringType == null) {
			stringType = elm.getName();
		}
		// check other event attributes
		for (Iterator<String> iterator = elm.enumerateAttributeNames(); iterator.hasNext();) {
			String n = iterator.next();
			if (!n.equals("expression") && !n.equals("condition")) {
				setBlockParam(n, elm.getStringAttribute(n));
			}
		}
		
		String condition = (String) elm.getAttribute("condition");
		if(condition!=null) {
			setExpression(condition);
		}
		
		
		myEventName = stringType;
		setStackElement(new TipiStackElement(myEventName + ":", elm, getStackElement()));
		for (Iterator<String> iterator = elm.enumerateAttributeNames(); iterator.hasNext();) {
			String n = iterator.next();
//			eventPropertyMap.put(n, elm.getStringAttribute(n));
			setBlockParam(n,  elm.getStringAttribute(n));
		}
		List<XMLElement> temp = elm.getChildren();
		for (int i = 0; i < temp.size(); i++) {
			XMLElement current = temp.get(i);

			if (current.getName().equals("block")) {
				TipiActionBlock ta = context.instantiateDefaultTipiActionBlock(getComponent());
				ta.load(current, getComponent(), this);
				appendTipiExecutable(ta);
				continue;
			}
			parseActions(context, current);
		}
	}

	/**
	 * @param context
	 * @param current
	 * @throws TipiException
	 */

	public boolean isSync() {
		return false;
	}



	public TipiContext getContext() {
		if (getComponent() == null) {
			throw new RuntimeException("Event without component is not allowed");
		}
		return getComponent().getContext();
	}

	public void asyncPerformAction(final TipiEventListener listener, TipiExecutable parentExecutable, final Map<String, Object> event, Runnable afterEventParam) {

		TipiEvent localEvent = (TipiEvent) this.clone();
		localEvent.loadEventValues(event);
//		if(!localEvent.checkCondition(localEvent)) {
//			afterEventParam.run();
//			return;
//		}
		try {
			localEvent.setAfterEvent(afterEventParam);
			getContext().debugLog("event   ", "enqueueing (in event) async event: " + localEvent);
			getComponent().getContext().performAction(localEvent, parentExecutable, listener);
		} catch (Throwable ex) {
			ex.printStackTrace();
		}
	}


	public void performAction(TipiEvent te, TipiExecutable parent, int index) throws TipiException, TipiBreakException {
		performAction(getComponent(), parent, null);
	}



	private final void loadEventValues(Map<String, Object> m) {
		if (m == null) {
			return;
		}
		Iterator<String> it = m.keySet().iterator();
		while (it.hasNext()) {
			String s = it.next();
			TipiValue tv = getEventParameter(s);
			if (tv != null) {
				tv.setValue(m.get(s));
			}
		}
	}

	// Sync, in current thread
	public void performAction(TipiEventListener listener, TipiExecutable executableParent, Map<String, Object> event)
			throws TipiBreakException {
		TipiEvent localInstance = this;
		if (event != null) {
			localInstance = (TipiEvent) this.clone();
			localInstance.loadEventValues(event);
		}
		if(!localInstance.checkCondition(localInstance)) {
			return;
		}

		Thread currentThread = Thread.currentThread();
		final Stack<TipiExecutable> s = getContext().getThreadPool().getThreadStack(currentThread);
		TipiExecutable parentEvent = null;

		if (s != null && !s.isEmpty()) {
			parentEvent = s.peek();
			s.push(this);
		}
		getContext().getThreadPool().pushCurrentEvent(this);
		TipiStackElement te = getStackElement();
		if (te != null) {
			if (parentEvent != null) {
				te.setRootCause(parentEvent.getStackElement());
			}
		}
		getContext().debugLog("event   ", "performing event: " + localInstance.getEventName());

		listener.eventStarted(localInstance, event);

		TipiExecutable last = null;
		try {
			System.err.println("Executing event: "+getEventName());
			for (int i = 0; i < getExecutables().size(); i++) {
				TipiExecutable current = getExecutables().get(i);
				System.err.println("Executing subevent: "+current.toString());
				last = current;
				current.performAction(localInstance, executableParent, i);

			}
		} catch (TipiBreakException e) {
			System.err.println("break detected");
			throw e;
		} catch (Throwable ex) {
			dumpStack(ex.getMessage());
			getContext().showInternalError(
					"Error performing event: " + getEventName() + " for component: " + getComponent().getPath() + " action: " + last
							+ " : " + ex.getMessage(), ex);
			ex.printStackTrace();
		}
		getContext().debugLog("event   ", "finished event: " + localInstance.getEventName() + " in component" + getComponent().getPath());
		listener.eventFinished(localInstance, event);
	}

	public boolean isTrigger(String name) {
		return name.equals(myEventName);
	}

	public String getEventName() {
		return myEventName;
	}


	public String toString() {
		return "TIpiEvent: " + myEventName + " - "  + " comp: " + getComponent();
	}

	// This actually has a good reason
	public TipiEvent getEvent() {
		return this;
	}


	public Map<String, TipiValue> getParameters() {
		return new HashMap<String, TipiValue>(eventParameterMap);
	}


	public Runnable getAfterEvent() {
		return afterEvent;
	}

	public void setAfterEvent(Runnable afterEvent) {
		this.afterEvent = afterEvent;
	}

}
