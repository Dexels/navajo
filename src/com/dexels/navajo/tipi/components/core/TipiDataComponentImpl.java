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
	protected String prefix;
	// private String autoLoad = null;
	// private String autoLoadDestination = null;
	protected String myMethod;

	// private String myServer;

	public TipiDataComponentImpl() {
	}

	// /**
	// * @deprecated
	// */
	// public void autoLoadServices(TipiContext context, TipiEvent event) throws
	// TipiException {
	// String autoDest;
	// if (autoLoadDestination == null) {
	// autoDest = "*";
	// }
	// else {
	// autoDest = autoLoadDestination;
	// }
	// if (autoLoad != null && !autoLoad.equals("")) {
	// // System.err.println("Performing servicelist for: "+getPath());
	// performServiceList(autoLoad, autoDest, context, event);
	// }
	// }

	private final void loadServices(String myService) {
		if (myService != null) {
			// myContext.clearTipiAllInstances();
			if (myService.indexOf(';') >= 0) {
				StringTokenizer st = new StringTokenizer(myService, ";");
				while (st.hasMoreTokens()) {
					String t = st.nextToken();
					myServices.add(t);
					myContext.addTipiInstance(t, this);
					// System.err.println("Adding tipi with service:
					// "+myService+" my id: "+myId);
				}
			} else {
				myServices.add(myService);
				myContext.addTipiInstance(myService, this);
				// System.err.println("Adding tipi with service: "+myService+"
				// my id: "+myId);
			}
		}
	}

	// protected Object getComponentValue(String name) {
	// if ("server".equals(name)) {
	// return myServer;
	// }
	// return super.getComponentValue(name);
	// }
	//
	// public String getServer() {
	// return myServer;
	// }

	public void load(XMLElement definition, XMLElement instance, TipiContext context) throws TipiException {
		super.load(definition, instance, context);
		// System.err.println("Loading class: "+instance.getAttribute("id"));
		prefix = (String) instance.getAttribute("prefix");
		// String menubar = (String)instance.getAttribute("menubar");
		loadServices((String) definition.getAttribute("service"));
		// autoLoad = (String) definition.getAttribute("autoload");
		// autoLoadDestination = (String)
		// definition.getAttribute("autoloadDestination");
		String constraint = (String) instance.getAttribute("constraint");
		if (constraint == null) {
			constraint = (String) definition.getAttribute("constraint");
		}
		if (constraint != null) {
			setConstraints(constraint);
		}
		// System.err.println("MY CONSTRIANT::::::::::::::::: " + constraint);
		List<XMLElement> children = null;
		if (instance.getAttribute("class") != null || instance.getAttribute("type") != null) {
			// System.err.println("Instantiating from instance");
			children = instance.getChildren();
		} else {
			// System.err.println("Instantiating from definition");
			children = definition.getChildren();
		}
		for (int i = 0; i < children.size(); i++) {
			XMLElement child = children.get(i);
			if (child.getName().equals("layout") || child.getName().startsWith("l.")) {
				instantiateWithLayout(child);
			} else {
				// System.err.println("Checkin.");
				if (child.getName().equals("tipi-instance") || child.getName().equals("component-instance")
						|| child.getName().equals("component") || child.getName().startsWith("c.")) {
					addComponentInstance(myContext, child, child.getAttribute("constraint"));
				}
			}
		}
	}

	private final void instantiateWithLayout(XMLElement x) throws TipiException {
		TipiLayout tl = myContext.instantiateLayout(x, this);
		if (tl == null) {
			throw new RuntimeException("Trying to instantiate with layout, but the layout == null");
		}
		tl.setComponent(this);
		setLayout(tl);
		tl.createLayout();
		tl.initializeLayout(x);
		if (getContainer() != null) {
			setContainerLayout(tl.getLayout());
		}
		tl.loadLayout(this);
		tl.commitLayout();
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

	// /**
	// * @deprecated
	// */
	//
	// public void performServiceList(String list, String tipiPath, TipiContext
	// context, TipiEvent event) throws TipiException {
	// if (list.indexOf(";") < 0) {
	// try {
	// performService(context, tipiPath, list, false, event, -1, null, null,
	// null, null, null);
	// }
	// catch (TipiBreakException ex) {
	// System.err.println("Error calling autoload service. " + list + "
	// continuing.");
	// }
	// return;
	// }
	// StringTokenizer st = new StringTokenizer(list, ";");
	// while (st.hasMoreTokens()) {
	// try {
	// performService(context, tipiPath, st.nextToken(), false, event, -1, null,
	// null, null, null, null);
	// }
	// catch (TipiBreakException ex) {
	// System.err.println("Error calling autoload service. " + list + "
	// continuing.");
	// }
	// }
	// }

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

	public void setPrefix(String pr) {
		prefix = pr;
	}

	public String getPrefix() {
		return prefix;
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
			if (prefix != null) {
				p = n.getProperty(prefix + "/" + current.getPropertyName());
				current.setProperty(p);
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
			} else {
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
			PropertyComponent current = properties.get(i);
			if (prefix != null) {
				System.err
						.println("LOADING ERRORS: DEPRECATED:::::: WITH Prefix, looking for: " + prefix + "/" + current.getPropertyName());
			} else {
				current.checkForConditionErrors(n.getMessage("ConditionErrors"));
			}
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
				// System.err.println("Adding to params: (id) "+ids.toString());
				// System.err.println("Adding to params: (description)
				// "+descs.toString());
				return performTipiEvent("onGeneratedErrors", param, true);
			}
			// System.err.println("Did not find condition errors?!");
			return performTipiEvent("onGeneratedErrors", null, true);
		} catch (Throwable ex) {
			ex.printStackTrace();
			return false;
		}
	}

	public void refreshLayout() {
		// do nothing.
	}

	public TipiDataComponent getTipiByPath(String path) {
		TipiComponent tc = getTipiComponentByPath(path);
		if (tc == null) {
			System.err.println("Could not find tipi!");
			return null;
		}
		if (!TipiDataComponent.class.isInstance(tc)) {
			System.err.println("Found a component while looking for a tipi, but not a Tipi");
			Thread.dumpStack();
			return null;
		}
		return (TipiDataComponent) tc;
	}

	public void clearProperties() {
		/** @todo Beware. Removed this.... */
		// getContainer().removeAll();
		properties.clear();
	}

	public XMLElement store() {
		XMLElement IamThereforeIcanbeStored = super.store();
		// IamThereforeIcanbeStored.setName("tipi-instance");
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < myServices.size(); i++) {
			sb.append(myServices.get(i));
			if ((i + 1) < myServices.size()) {
				sb.append(";");
			}
		}
		if (myServices.size() > 0) {
			IamThereforeIcanbeStored.setAttribute("service", sb.toString());
		}
		// if (prefix != null) {
		// IamThereforeIcanbeStored.setAttribute("prefix", prefix);
		// }
		return IamThereforeIcanbeStored;
	}

	@Override
	public void tipiLoaded() {
	}

	@Override
	public void childDisposed() {
	}

	public boolean listensTo(String service) {
		return myServices.contains(service);
	}

	public boolean hasProperty(String path) {
		for (int i = 0; i < properties.size(); i++) {
			PropertyComponent current = properties.get(i);
			// System.err.println("Checking hasproperty: " +
			// current.getPropertyName());
			if (path.equals(current.getPropertyName())) {
				return true;
			}
		}
		return false;
	}

	public void disposeComponent() {
		super.disposeComponent();
		myContext.removeTipiInstance(this);
		myNavajo = null;
	}
}
