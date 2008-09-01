package com.dexels.navajo.tipi.internal;

import java.util.*;

import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.studio.*;
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
public class TipiEvent implements TipiExecutable {
	private String myEventName;
	private String myEventService;
	private String mySource;
	// private ArrayList myActions;
//	private Navajo myNavajo;
	private TipiComponent myComponent;
	// private TipiActionBlock myTopActionBlock = null;
	private List<TipiExecutable> myExecutables = new ArrayList<TipiExecutable>();
	
	private Map<String,TipiValue> eventParameterMap = new HashMap<String,TipiValue>();
	private Map<String,String> eventPropertyMap = new HashMap<String, String>();

	private TipiStackElement stackElement = null;
	
	public TipiStackElement getStackElement() {
		return stackElement;
	}

	public void setStackElement(TipiStackElement myStackElement) {
		this.stackElement = myStackElement;
	}

	public TipiEvent() {
	}

	public Object clone() {

		TipiEvent ti = new TipiEvent();
		ti.myEventName = this.myEventName;
		ti.myEventService = this.myEventService;
		ti.mySource = this.mySource;
		ti.myComponent = this.myComponent;
		ti.myExecutables = this.myExecutables;
		ti.eventParameterMap = new HashMap<String,TipiValue>();

		Iterator<String> iter = this.eventParameterMap.keySet().iterator();

		while ( iter.hasNext() ) {
			String key = iter.next();
			TipiValue tv = (TipiValue) this.eventParameterMap.get(key).clone();
			ti.eventParameterMap.put(key, tv);
		}
		
		return ti;
	}
	
	public void init(XMLElement xe) {
		List<XMLElement> v = xe.getChildren();
		for (int i = 0; i < v.size(); i++) {
			XMLElement current = v.get(i);
			if (current.getName().equals("param")) {
				TipiValue tv = new TipiValue(myComponent);
				tv.load(current);
				eventParameterMap.put(tv.getName(), tv);
			}
		}
	}

	public void load(TipiComponent tc, XMLElement elm, TipiContext context) throws TipiException {
		myComponent = tc;
//		if (elm.getName().equals("event")) {
			String stringType = (String) elm.getAttribute("type");
			if(stringType==null) {
				stringType = elm.getName();
			}
			
			myEventName = stringType;
			myEventService = (String) elm.getAttribute("service");
			mySource = (String) elm.getAttribute("listen");

			setStackElement(new TipiStackElement(myEventName+":",elm,getStackElement()));
			
			for (Iterator<String> iterator =  elm.enumerateAttributeNames(); iterator.hasNext();) {
				String n = iterator.next();
				eventPropertyMap .put(n,elm.getStringAttribute(n));
			}
			List<XMLElement> temp = elm.getChildren();
			for (int i = 0; i < temp.size(); i++) {
				XMLElement current = temp.get(i);

				if (current.getName().equals("block")) {
					TipiActionBlock ta = context.instantiateDefaultTipiActionBlock(myComponent);
					ta.load(current, myComponent,this);
					myExecutables.add(ta);
					continue;
				}
					TipiAction ta = context.instantiateTipiAction(current, myComponent,this);
					myExecutables.add(ta);
			}
	}

	public boolean isSync() {
		return false;
	}

	public void appendExecutable(TipiExecutable a) {
		myExecutables.add(a);
	}

	public void removeExecutable(TipiExecutable a) {
		myExecutables.remove(a);
	}

	public void moveExecutableUp(TipiAction action) {
		int index_old = myExecutables.indexOf(action);
		if (index_old > 0) {
			myExecutables.remove(action);
			myExecutables.add(index_old - 1, action);
		}
	}

	public void moveExecutableDown(TipiAction action) {
		int index_old = myExecutables.indexOf(action);
		if (index_old < myExecutables.size() - 1) {
			myExecutables.remove(action);
			myExecutables.add(index_old + 1, action);
		}
	}

	public TipiContext getContext() {
		if (myComponent == null) {
			throw new RuntimeException("Event without component is not allowed");
		}
		return myComponent.getContext();
	}

	public void asyncPerformAction(final TipiEventListener listener, final Map<String,Object> event) {
		TipiEvent localEvent = (TipiEvent) this.clone();
		localEvent.loadEventValues(event);
		// final TipiEvent te = this;
		try {
			//listener.eventStarted(this, event);
			getContext().debugLog("event   ", "enqueueing (in event) async event: " + localEvent);

			myComponent.getContext().performAction(localEvent, listener);
		} catch (Throwable ex) {
			ex.printStackTrace();
		}
	}

	public TipiComponent getComponent() {
		return myComponent;
	}

	public void performAction(TipiEvent te, TipiExecutable parent, int index) throws TipiException, TipiBreakException {
		performAction(myComponent, null);
	}

	public void performAction(TipiEventListener listener) throws TipiBreakException {
		performAction(listener, null);
	}

	public TipiValue getEventParameter(String name) {
		return eventParameterMap.get(name);
	}
	public void addEventParameter(String myKey, TipiValue tv) {
		eventParameterMap.put(myKey,tv);
	}

	public String getBlockParam(String name) {
		return eventPropertyMap.get(name);
	}
	public Set<String> getEventKeySet() {
		return eventParameterMap.keySet();
	}

	private final void loadEventValues(Map<String,Object> m) {
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
	public void performAction(TipiEventListener listener, Map<String,Object> event) throws TipiBreakException {
		TipiEvent localInstance = this;
		if ( event != null ) {
			localInstance = (TipiEvent) this.clone();
			localInstance.loadEventValues(event);
		} 
		
		
		// eventParameterMap.clear();
		// eventParameterMap.putAll(event);

		getContext().debugLog("event   ", "performing event: " + localInstance.getEventName());

		listener.eventStarted(localInstance, event);
		try {
			getContext().performedEvent(myComponent, localInstance);
		} catch (BlockActivityException ex1) {
			// System.err.println("Blocked exception");
			return;
		}
		TipiExecutable last = null;
		try {
			for (int i = 0; i < myExecutables.size(); i++) {
				TipiExecutable current = myExecutables.get(i);
				last = current;
				current.performAction(localInstance, localInstance, i);
				
			}
		} catch (TipiBreakException e) {
			System.err.println("break detected");
			throw e;
		}
		catch (Throwable ex) {
			dumpStack(ex.getMessage());
			getContext().showInternalError("Error performing event: "+getEventName()+" for component: "+getComponent().getPath()+" action: "+last+" : "+ex.getMessage(), ex);
			ex.printStackTrace();
		}
		getContext().debugLog("event   ", "finished event: " + localInstance.getEventName() + " in component" + myComponent.getPath());
		listener.eventFinished(localInstance, event);
	}



	public boolean isTrigger(String name, String service) {
		if (name != null) {
			if (service == null || myEventService == null || myEventService.equals("")) {
				return name.equals(myEventName);
			} else {
				return (service.equals(myEventService) && name.equals(myEventName));
			}
		}
		System.err.println("Name not specified!!");
		return false;
	}

//	public void setNavajo(Navajo n) {
//		myNavajo = n;
//	}

	public String getEventName() {
		return myEventName;
	}

	public String getSource() {
		return mySource;
	}

	public XMLElement store() {
		// throw new RuntimeException("Todo: check and reimplement");
		XMLElement s = new CaseSensitiveXMLElement();
		s.setName(myEventName);
//		s.setAttribute("type", myEventName);
		for (int i = 0; i < myExecutables.size(); i++) {
			TipiExecutable current = myExecutables.get(i);
			s.addChild(current.store());
		}
		return s;
	}

	public int getExecutableChildCount() {
		return myExecutables.size();
	}

	public TipiExecutable getExecutableChild(int index) {
		return myExecutables.get(index);
	}

	public String toString() {
		return "TIpiEvent: " + myEventName + " - " + myEventService + " comp: " + myComponent;
	}

	// This actually has a good reason
	public TipiEvent getEvent() {
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dexels.navajo.tipi.TipiExecutable#setEvent(com.dexels.navajo.tipi.internal.TipiEvent)
	 */
	public void setEvent(TipiEvent e) {
	}

	public Map<String,TipiValue> getParameters() {
		return new HashMap<String,TipiValue>(eventParameterMap);
	}

	public void dumpStack(String message) {
		if(getStackElement()!=null) {
			getStackElement().dumpStack(message);			
		} else {
			System.err.println("Tipi event has no stack element: "+myEventName);
		}
	}

}
