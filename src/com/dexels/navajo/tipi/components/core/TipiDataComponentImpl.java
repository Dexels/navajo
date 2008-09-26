package com.dexels.navajo.tipi.components.core;

import java.util.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.internal.*;
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
public abstract class TipiDataComponentImpl extends TipiComponentImpl implements TipiDataComponent {
	private final List<String> myServices = new ArrayList<String>();
	protected String myMethod;

	public TipiDataComponentImpl() {
	}



	private final void loadServices(String myService) {
		if (myService != null) {
			if (myService.indexOf(';') >= 0) {
				StringTokenizer st = new StringTokenizer(myService, ";");
				while (st.hasMoreTokens()) {
					String t = st.nextToken();
					myServices.add(t);
					myContext.addTipiInstance(t, this);
				}
			} else {
				myServices.add(myService);
				myContext.addTipiInstance(myService, this);
			}
		}
	}


	public void load(XMLElement definition, XMLElement instance, TipiContext context) throws TipiException {
		super.load(definition, instance, context);

		loadServices((String) definition.getAttribute("service"));

	}



	public String getName() {
		return myName;
	}

	public Object getValue(String name) {
		if (".".equals(name)) {
			return getNavajo();
		}
		return super.getValue(name);
	}

	public void replaceLayout(TipiLayout tl) {
		List<TipiComponent> elementList = new ArrayList<TipiComponent>();
		for (int i = 0; i < getChildCount(); i++) {
			TipiComponent current = getTipiComponent(i);
			if (current.isVisibleElement()) {
				removeFromContainer(current.getContainer());
			}
			elementList.add(current);
		}
		setLayout(tl);
		setContainerLayout(tl.getLayout());
		for (int i = 0; i < elementList.size(); i++) {
			TipiComponent current = elementList.get(i);
			Object o = tl.createDefaultConstraint(i);
			current.setConstraints(o);
			addToContainer(current.getContainer(), o);
		}
	}

	public List<String> getServices() {
		return myServices;
	}

	public void addService(String service) {
		myServices.add(service);
	}

	public void removeService(String service) {
		myServices.remove(service);
	}

	/**
	 * @deprecated
	 */
	@Deprecated
	public void performService(TipiContext context, String tipiPath, String service, boolean breakOnError, TipiEvent event,
			long expirationInterval, String hostUrl, String username, String password, String keystore, String keypass)
			throws TipiException, TipiBreakException {
		tipiPath = "*";
		if (myNavajo == null) {
			myNavajo = NavajoFactory.getInstance().createNavajo();
		}
		context.performTipiMethod(this, myNavajo, tipiPath, service, breakOnError, event, expirationInterval, hostUrl, username, password,
				keystore, keypass);
	}
	public String getCurrentMethod() {
		return myMethod;
	}

	public void loadData(Navajo n, String method) throws TipiException, TipiBreakException {
		myMethod = method;
		if (n == null) {
			throw new TipiException("Loading with null Navajo! ");
		}

		for (int i = 0; i < properties.size(); i++) {
			PropertyComponent current = properties.get(i);
			Property p;
				p = n.getProperty(current.getPropertyName());
				if (p != null) {
					try {
						getContext().debugLog("data    ",
								"delivering property: " + p.getFullPropertyName() + " to tipi: " + ((TipiComponent) current).getId());
					} catch (NavajoException ex) {
						ex.printStackTrace();
					}
				} else {
					getContext().debugLog("data    ", "delivering null property to tipi: " + ((TipiComponent) current).getId());
				}
				if (p != null) {
					current.setProperty(p);
				}
		}
		myNavajo = n;
		/** @TODO Maybe it is not a good idea that it is recursive. */
		for (int i = 0; i < getChildCount(); i++) {
			TipiComponent tcomp = getTipiComponent(i);
			if (TipiDataComponent.class.isInstance(tcomp)) {
				TipiDataComponent current = (TipiDataComponent) tcomp;
				current.loadData(n, method);
			}
		}
		// hmmmmmmmmm
		doPerformOnLoad(method, n, true);
		doLayout();
	}

	protected void doPerformOnLoad(String method, Navajo n, boolean sync) throws TipiException {
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("service", method);
		m.put("navajo", n);
		performTipiEvent("onLoad", m, sync);
	}

	protected void doLayout() {
	}

	public boolean loadErrors(Navajo n, String method) {
		for (int i = 0; i < properties.size(); i++) {
//			PropertyComponent current = properties.get(i);
//			current.checkForConditionErrors(n.getMessage("ConditionErrors"));
		}
		for (int i = 0; i < getChildCount(); i++) {
			TipiComponent tcomp = getTipiComponent(i);
			if (TipiDataComponent.class.isInstance(tcomp)) {
				TipiDataComponent current = (TipiDataComponent) tcomp;
				current.loadErrors(n, method);
			}
		}
		try {
			Message m = n.getMessage("ConditionErrors");
			if (m != null && m.getArraySize() > 0) {
				StringBuffer ids = new StringBuffer();
				StringBuffer descs = new StringBuffer();
				for (int i = 0; i < m.getArraySize(); i++) {
					Message current = m.getMessage(i);
					Property id = current.getProperty("Id");
					Property desc = current.getProperty("Description");
					if (id != null && desc != null) {
						ids.append(id.getValue());
						if (i != m.getArraySize() - 1) {
							ids.append(",");
						}
						descs.append(desc.getValue());
						if (i != m.getArraySize() - 1) {
							descs.append(",");
						}
					}
				}
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("id", ids.toString());
				param.put("description", descs.toString());
				param.put("service", method);
				return performTipiEvent("onGeneratedErrors", param, true);
			}
			return performTipiEvent("onGeneratedErrors", null, true);
		} catch (Throwable ex) {
			ex.printStackTrace();
			return false;
		}
	}

	public void refreshLayout() {
		// do nothing.
	}


	public void clearProperties() {
		properties.clear();
	}


	public void disposeComponent() {
		super.disposeComponent();
		myContext.removeTipiInstance(this);
		myNavajo = null;
	}
}
