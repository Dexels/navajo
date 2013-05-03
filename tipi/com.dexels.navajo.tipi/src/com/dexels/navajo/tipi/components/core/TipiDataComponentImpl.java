package com.dexels.navajo.tipi.components.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiComponent;
import com.dexels.navajo.tipi.TipiContext;
import com.dexels.navajo.tipi.TipiDataComponent;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.internal.MessageComponent;
import com.dexels.navajo.tipi.internal.PropertyComponent;
import com.dexels.navajo.tipi.internal.TipiEvent;
import com.dexels.navajo.tipi.internal.TipiLayout;
import com.dexels.navajo.tipi.tipixml.XMLElement;

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
public abstract class TipiDataComponentImpl extends TipiComponentImpl implements
		TipiDataComponent {
	private static final long serialVersionUID = -8051817615319907555L;
	private final List<String> myServices = new ArrayList<String>();
	protected String myMethod;
	protected String mySettingsService = null;
	protected final Set<TipiComponent> propertyComponentSet = new HashSet<TipiComponent>();
	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiDataComponentImpl.class);
	
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

	public void load(XMLElement definition, XMLElement instance,
			TipiContext context) throws TipiException {
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

	public void loadStartValues(XMLElement element, TipiEvent event) {
		super.loadStartValues(element, event);
		// see if a value called settingsService has just been set to the component
		try
		{
			mySettingsService = (String) this.getValue("settingsService");
		}
		catch(UnsupportedOperationException uoe)
		{
			// nothing wrong, it is just not available for this class.
		}
		if (mySettingsService != null)
		{
			loadServices(mySettingsService);
			Navajo settings = this.getContext().getNavajo(mySettingsService);
			if (settings != null)
			{
				try
				{
					loadData(settings, mySettingsService);
				}
				catch(TipiException te)
				{
					logger.error("Something going wrong loading the settings data for component " + this + " with settings " + settings, te);
				}
			}
		}
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
	public void performService(TipiContext context, String tipiPath,
			String service, boolean breakOnError, TipiEvent event,
			long expirationInterval, String hostUrl, String username,
			String password, String keystore, String keypass)
			throws TipiException, TipiBreakException {
		tipiPath = "*";
		if (myNavajo == null) {
			myNavajo = NavajoFactory.getInstance().createNavajo();
			myNavajo.addHeader(NavajoFactory.getInstance().createHeader(
					myNavajo, service, "", "", -1));

		}
		context.performTipiMethod(this, myNavajo, tipiPath, service,
				breakOnError, event, expirationInterval, hostUrl, username,
				password, keystore, keypass);
	}

	public String getCurrentMethod() {
		return myMethod;
	}

	public void loadData(Navajo n, String method) throws TipiException,
			TipiBreakException {
		myMethod = method;
		if (n == null) {
			throw new TipiException("Loading with null Navajo! ");
		}
		myNavajo = n;
		if (method!=null && method.equals(mySettingsService))
		{
			extractUserSettings(n);
		}
		else
		{

			// );
			for (TipiComponent tc : propertyComponentSet) {
				tc.loadPropertiesFromNavajo(n);
			}
			loadProperties(n);
			cascadeLoad(n, method);
			doPerformOnLoad(method, n, true);
		}
		doLayout();

	}

	private void extractUserSettings(Navajo n) {
		// find the x, y, w and h values and set them as values to this component.
		Message userSettings = n.getMessage("UserSettings");
		for (int i=0; i<userSettings.getArraySize();i++)
		{
			Message singleComponentUserSettings = userSettings.getMessage(i);
			if (singleComponentUserSettings != null && singleComponentUserSettings.getProperty("ObjectName").getValue().equals(myName) && singleComponentUserSettings.getProperty("ObjectType").getValue().equalsIgnoreCase(this.getClass().getName()))
			{
				Integer propertyX = 0;
				Integer propertyY = 0;
				Integer propertyW = 0;
				Integer propertyH = 0;
				if (singleComponentUserSettings.getProperty("PropertyY") != null && singleComponentUserSettings.getProperty("PropertyY").getTypedValue() != null)
				{
					propertyY = (Integer) singleComponentUserSettings.getProperty("PropertyY").getTypedValue();
				}
				if (singleComponentUserSettings.getProperty("PropertyW") != null && singleComponentUserSettings.getProperty("PropertyW").getTypedValue() != null)
				{
					propertyW = (Integer) singleComponentUserSettings.getProperty("PropertyW").getTypedValue();
				}
				if (singleComponentUserSettings.getProperty("PropertyH") != null && singleComponentUserSettings.getProperty("PropertyH").getTypedValue() != null)
				{
					propertyH = (Integer) singleComponentUserSettings.getProperty("PropertyH").getTypedValue();
				}
				if (singleComponentUserSettings.getProperty("PropertyX") != null && singleComponentUserSettings.getProperty("PropertyX").getTypedValue() != null)
				{
					propertyX = (Integer) singleComponentUserSettings.getProperty("PropertyX").getTypedValue();
				}
				// set the values
				this.setValue("x", propertyX);
				this.setValue("y", propertyY);
				if (propertyW > 0)
				{
					this.setValue("w", propertyW);
				}
				if (propertyH > 0)
				{
					this.setValue("h", propertyH);
				}
			}
		}
	}

	private void saveUserSettings() {
		if (mySettingsService != null)
		{
			Navajo n = this.getContext().getNavajo(mySettingsService);
			if (n != null)
			{
				Message userSettings = n.getMessage("UserSettings");
				Message thisComponentUserSettings = null;
				for (int i=0; i<userSettings.getArraySize();i++)
				{
					Message singleComponentUserSettings = userSettings.getMessage(i);
					if (singleComponentUserSettings != null && singleComponentUserSettings.getProperty("ObjectName").getValue().equals(myName) && singleComponentUserSettings.getProperty("ObjectType").getValue().equalsIgnoreCase(this.getClass().getName()))
					{
						thisComponentUserSettings = singleComponentUserSettings;
					}
				}
				if (thisComponentUserSettings == null)
				{ // this component doesn't have an entry in the arraylist so we need to add it.
					thisComponentUserSettings = NavajoFactory.getInstance().createMessage(n, "UserSettings", Message.MSG_TYPE_ARRAY_ELEMENT);
					userSettings.addElement(thisComponentUserSettings);

					// set objectname & objecttype properties
					Property p = NavajoFactory.getInstance().createProperty(n, "ObjectName", "String",
							null, 0, "ObjectName", Property.DIR_IN);
					thisComponentUserSettings.addProperty(p);
					p.setAnyValue(myName);
					
					p = NavajoFactory.getInstance().createProperty(n, "ObjectType", "String",
							null, 0, "ObjectType", Property.DIR_IN);
					thisComponentUserSettings.addProperty(p);
					p.setAnyValue(this.getClass().getName());
				}
				
				// save the settings
				String name = "PropertyX";
				Property p = null;
				if (thisComponentUserSettings.getProperty(name) != null)
				{
					p = thisComponentUserSettings.getProperty(name);
				}
				else
				{ // add the property
					p = NavajoFactory.getInstance().createProperty(n, name, "Integer",
							null, 0, name, Property.DIR_IN);
					thisComponentUserSettings.addProperty(p);
				}
				p.setAnyValue(this.getValue("x"));
				//y
				name = "PropertyY";
				p = null;
				if (thisComponentUserSettings.getProperty(name) != null)
				{
					p = thisComponentUserSettings.getProperty(name);
				}
				else
				{ // add the property
					p = NavajoFactory.getInstance().createProperty(n, name, "Integer",
							null, 0, name, Property.DIR_IN);
					thisComponentUserSettings.addProperty(p);
				}
				p.setAnyValue(this.getValue("y"));
				//width
				name = "PropertyW";
				p = null;
				if (thisComponentUserSettings.getProperty(name) != null)
				{
					p = thisComponentUserSettings.getProperty(name);
				}
				else
				{ // add the property
					p = NavajoFactory.getInstance().createProperty(n, name, "Integer",
							null, 0, name, Property.DIR_IN);
					thisComponentUserSettings.addProperty(p);
				}
				p.setAnyValue(this.getValue("w"));
				//height
				name = "PropertyH";
				p = null;
				if (thisComponentUserSettings.getProperty(name) != null)
				{
					p = thisComponentUserSettings.getProperty(name);
				}
				else
				{ // add the property
					p = NavajoFactory.getInstance().createProperty(n, name, "Integer",
							null, 0, name, Property.DIR_IN);
					thisComponentUserSettings.addProperty(p);
				}
				p.setAnyValue(this.getValue("h"));

			}
			else
			{
				logger.error("Couldn't save user settings because the expected navajo (" + mySettingsService + ") was not available.");
			}
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
			if (current.getPropertyName() == null) {
				logger.warn("Property component found without name: "
						+ current.getClass());
				continue;
			}
			p = n.getProperty(current.getPropertyName());
			if (p != null) {
				try {
					getContext().debugLog(
							"data    ",
							"delivering property: " + p.getFullPropertyName()
									+ " to tipi: "
									+ ((TipiComponent) current).getId());
				} catch (NavajoException ex) {
					logger.error("Error: ",ex);
				}
			} else {
				getContext().debugLog(
						"data    ",
						"delivering null property to tipi: "
								+ ((TipiComponent) current).getId());
			}
			if (p != null) {
				current.setProperty(p);
			}
		}
	}

	/**
	 * Beware, recursive!
	 * 
	 * @param m
	 */
	protected void loadProperties(Message m) {
		List<PropertyComponent> plist = getRecursiveProperties();
		for (int i = 0; i < plist.size(); i++) {
			PropertyComponent current = plist.get(i);
			Property p;
			if (current.getPropertyName() == null) {
				logger.warn("Property component found without name: "
						+ current.getClass());
				continue;
			}
			p = m.getProperty(current.getPropertyName());
			if (p != null) {
				try {
					getContext().debugLog(
							"data    ",
							"delivering property: " + p.getFullPropertyName()
									+ " to tipi: "
									+ ((TipiComponent) current).getId());
				} catch (NavajoException ex) {
					logger.error("Error: ",ex);
				}
			} else {
				getContext().debugLog(
						"data    ",
						"delivering null property to tipi: "
								+ ((TipiComponent) current).getId());
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
		} else {
			for (int i = 0; i < getChildCount(); i++) {
				TipiComponent tcomp = getTipiComponent(i);
				if (TipiDataComponent.class.isInstance(tcomp)) {
					TipiDataComponent current = (TipiDataComponent) tcomp;
					current.loadData(n, method);
				} else {
					tcomp.loadPropertiesFromNavajo(n);
				}
			}
		}
	}

	protected void doPerformOnLoad(String method, Navajo n, boolean sync)
			throws TipiException {
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
			logger.error("Error: ",ex);
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
		return null;
	}

	public void refreshLayout() {
		// do nothing.
	}

	public void clearProperties() {

		properties.clear();
	}

	public void disposeComponent() {
		saveUserSettings();
		super.disposeComponent();
		myContext.removeTipiInstance(this);
		myNavajo = null;
	}

	public void loadArrayData(final Navajo n,String messagePath)
			throws TipiBreakException {
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

					for (Message message : al) {
						Map<String, Object> eventParams = new HashMap<String, Object>();
						eventParams.put("message", message);
						eventParams.put("array", m);
						eventParams.put("index", index);
						eventParams.put("size", al.size());
						performTipiEvent("onBeforeElement", eventParams, true);
						TipiComponent child = getTipiComponent("" + index);

						if (child instanceof MessageComponent) {
							MessageComponent mc = (MessageComponent) child;
							mc.setMessage(message);
						}
						performTipiEvent("onAfterElement", eventParams, true);
						index++;
					}

					if (al.size() > 0) {
						performTipiEvent("onEnd", staticParams, true);
					}

				} catch (TipiException e) {
					logger.error("Error: ",e);
				}
			}
		});
	}

}
