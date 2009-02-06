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
	protected final Set<TipiComponent> propertyComponentSet = new HashSet<TipiComponent>();

	public TipiDataComponentImpl() {
	}

	protected final void loadServices(String myService) {
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
 
	public boolean isServiceRoot() {
		return !myServices.isEmpty();
	}
	

	
	public void load(XMLElement definition, XMLElement instance, TipiContext context) throws TipiException {
		if (definition.equals(instance)) {
			loadServices((String) definition.getAttribute("service"));
		} else {
			setHomeComponent(true);
			if (instance.getAttribute("service") != null) {
				loadServices((String) instance.getAttribute("service"));
			}
			if (definition.getAttribute("service") != null) {
				loadServices((String) definition.getAttribute("service"));
			}
		}
		super.load(definition, instance, context);
		
	}

	public void registerPropertyChild(TipiComponent component) {
		propertyComponentSet.add(component);
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
			myNavajo.addHeader(NavajoFactory.getInstance().createHeader(myNavajo, service, "", "", -1));
				
		}
		context.performTipiMethod(this, myNavajo, tipiPath, service, breakOnError, event, expirationInterval, hostUrl, username, password,
				keystore, keypass);
	}

	public String getCurrentMethod() {
		return myMethod;
	}

	public void loadData(Navajo n, String method) throws TipiException, TipiBreakException {
//		System.err.println("Load data: "+method+" on component: "+getPath());
		myMethod = method;
		if (n == null) {
			throw new TipiException("Loading with null Navajo! ");
		}
		myNavajo = n;
		
		if(true || "true".equals(myContext.getSystemProperty("isNewNavajoLoading"))) {
//			System.err.println("Loading. # of componentS: "+propertyComponentSet.size()+" loading: "+getPath() );
			for (TipiComponent tc : propertyComponentSet) {
				tc.loadPropertiesFromNavajo(n);
			}
			loadProperties(n);
			cascadeLoad(n, method);
			doPerformOnLoad(method, n, true);
			doLayout();
		} else {
			loadProperties(n);
			loadPropertiesFromNavajo(n);
			loadMessages(n);
			cascadeLoad(n, method);
			doPerformOnLoad(method, n, true);
			doLayout();
		}
		
	}

	protected void loadMessages(Navajo n) {
		for (MessageComponent current : messages) {
			Message m = n.getMessage(current.getMessageName());
			if (m != null) {
				current.setMessage(m.getMessage(current.getMessageIndex()));
			}
		}
	}

	/**
	 * @param n
	 */
	protected void loadProperties(Navajo n) {
		for (int i = 0; i < properties.size(); i++) {
			PropertyComponent current = properties.get(i);
			Property p;
			if(current.getPropertyName()==null) {
				System.err.println("Property component found without name: "+current.getClass());
				continue;
			}
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

	/**
	 * Beware, recursive!
	 * @param m
	 */
	protected void loadProperties(Message m) {
		List<PropertyComponent> plist =  getRecursiveProperties();
		for (int i = 0; i < plist.size(); i++) {
			PropertyComponent current = plist.get(i);
			Property p;
			if(current.getPropertyName()==null) {
				System.err.println("Property component found without name: "+current.getClass());
				continue;
			}
			p = m.getProperty(current.getPropertyName());
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
	
	/**
	 * @param n
	 * @param method
	 * @throws TipiException
	 */
	protected void cascadeLoad(Navajo n, String method) throws TipiException {
		/** @TODO Maybe it is not a good idea that it is recursive. */
		if ("true".equals(myContext.getSystemProperty("noCascadedLoading"))) {
			// new style
			//System.err.println("New school mode, ignoring children.");
		} else {
			//System.err.println("Legacy mode, also loading children.");
			for (int i = 0; i < getChildCount(); i++) {
				TipiComponent tcomp = getTipiComponent(i);
				if (TipiDataComponent.class.isInstance(tcomp)) {
					TipiDataComponent current = (TipiDataComponent) tcomp;
					current.loadData(n, method);
				} else{
					tcomp.loadPropertiesFromNavajo(n);
				}
			}
		}
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
			// PropertyComponent current = properties.get(i);
			// current.checkForConditionErrors(n.getMessage("ConditionErrors"));
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
	

	@Override
	public void addComponent(TipiComponent c, int index, TipiContext context,
			Object td) {
		super.addComponent(c, index, context, td);
	}

	@Override
	public Object createContainer() {
		// TODO Auto-generated method stub
		return null;
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

	public void loadArrayData(final Navajo n, String method, String messagePath) throws TipiBreakException {
		if (messagePath == null) {
			return;
		}
		final Message m = n.getMessage(messagePath);
		runSyncInEventThread(new Runnable() {

			public void run() {
				ArrayList<Message> al = m.getAllMessages();
				
				Map<String, Object> staticParams = new HashMap<String, Object>();
				staticParams.put("array", m);
				staticParams.put("size", al.size());
				
				try {
					performTipiEvent("onBegin", staticParams, true);
					int index = 0;
					System.err.println("Mysterymessage:");
					try {
						m.write(System.err);
					} catch (NavajoException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					for (Message message : al) {
						Map<String, Object> eventParams = new HashMap<String, Object>();
						eventParams.put("message", message);
						eventParams.put("array", m);
						eventParams.put("index", index);
						eventParams.put("size", al.size());
						performTipiEvent("onBeforeElement", eventParams, true);
						TipiComponent child = getTipiComponent(""+index);
					
						if(child instanceof MessageComponent) {
							MessageComponent mc = (MessageComponent)child;
							mc.setMessage(message);
						}
						performTipiEvent("onAfterElement", eventParams, true);
						index++;
					}
					System.err.println("Children: "+getChildCount());
					for (int i=0;i<getChildCount();i++) {
						TipiComponent c = getTipiComponent(i);
						System.err.println("Child id: "+c.getId());
					}
					if(al.size()>0) {
						performTipiEvent("onEnd", staticParams, true);
					}
				
					
				} catch (TipiException e) {
					e.printStackTrace();
				}
			}
		});
	}

}
