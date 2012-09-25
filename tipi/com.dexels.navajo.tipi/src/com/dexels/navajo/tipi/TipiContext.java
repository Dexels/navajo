package com.dexels.navajo.tipi;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Serializable;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.StringTokenizer;

import javax.imageio.spi.ServiceRegistry;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import navajo.ExtensionDefinition;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tipi.TipiApplicationInstance;
import tipi.TipiExtension;
import tipipackage.ITipiExtensionContainer;

import com.dexels.navajo.client.ClientException;
import com.dexels.navajo.client.ClientInterface;
import com.dexels.navajo.client.ConditionErrorHandler;
import com.dexels.navajo.client.NavajoClientFactory;
import com.dexels.navajo.document.Header;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Operand;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.notifier.SerializablePropertyChangeListener;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.functions.util.FunctionDefinition;
import com.dexels.navajo.functions.util.FunctionFactoryFactory;
import com.dexels.navajo.parser.DefaultExpressionEvaluator;
import com.dexels.navajo.parser.Expression;
import com.dexels.navajo.tipi.actionmanager.IActionManager;
import com.dexels.navajo.tipi.actionmanager.TipiActionManager;
import com.dexels.navajo.tipi.actions.TipiInstantiateTipi;
import com.dexels.navajo.tipi.classdef.ClassManager;
import com.dexels.navajo.tipi.classdef.IClassManager;
import com.dexels.navajo.tipi.components.core.ShutdownListener;
import com.dexels.navajo.tipi.components.core.ThreadActivityListener;
import com.dexels.navajo.tipi.components.core.TipiComponentImpl;
import com.dexels.navajo.tipi.components.core.TipiThread;
import com.dexels.navajo.tipi.components.core.TipiThreadPool;
import com.dexels.navajo.tipi.connectors.HttpNavajoConnector;
import com.dexels.navajo.tipi.connectors.TipiConnector;
import com.dexels.navajo.tipi.internal.BaseTipiErrorHandler;
import com.dexels.navajo.tipi.internal.ClassPathResourceLoader;
import com.dexels.navajo.tipi.internal.DescriptionProvider;
import com.dexels.navajo.tipi.internal.FileResourceLoader;
import com.dexels.navajo.tipi.internal.HttpResourceLoader;
import com.dexels.navajo.tipi.internal.RemoteDescriptionProvider;
import com.dexels.navajo.tipi.internal.TipiAction;
import com.dexels.navajo.tipi.internal.TipiActionBlock;
import com.dexels.navajo.tipi.internal.TipiAnonymousAction;
import com.dexels.navajo.tipi.internal.TipiEvent;
import com.dexels.navajo.tipi.internal.TipiFileStorageManager;
import com.dexels.navajo.tipi.internal.TipiGeneralAspManager;
import com.dexels.navajo.tipi.internal.TipiLayout;
import com.dexels.navajo.tipi.internal.TipiNullStorageManager;
import com.dexels.navajo.tipi.internal.TipiResourceLoader;
import com.dexels.navajo.tipi.internal.cookie.CookieManager;
import com.dexels.navajo.tipi.tipixml.CaseSensitiveXMLElement;
import com.dexels.navajo.tipi.tipixml.XMLElement;
import com.dexels.navajo.tipi.tipixml.XMLParseException;
import com.dexels.navajo.tipi.validation.TipiValidationDecorator;

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
public abstract class TipiContext implements ITipiExtensionContainer, Serializable {

	
	private static final Logger logger = LoggerFactory.getLogger(TipiContext.class);
	private static final long serialVersionUID = 2077449402941300665L;

	public abstract void runSyncInEventThread(Runnable r);

	public abstract void runAsyncInEventThread(Runnable r);

	/**
	 * Maps a service to a list of datacomponents. Components register here by
	 * having a service tag
	 */
	private final Map<String, List<TipiDataComponent>> tipiInstanceMap = new HashMap<String, List<TipiDataComponent>>();

	/**
	 * Containes a pre-parsed map of component definitions, is used less and
	 * less in favor of lazy loading
	 */
	private final Map<String, XMLElement> tipiComponentMap = new HashMap<String, XMLElement>();

	/**
	 * Maps component types to their actual class. Could be refactored to be
	 * done on demand
	 */
	// protected final Map<String, Class<?>> tipiClassMap = new HashMap<String,
	// Class<?>>();

	/*
	 * Maps component type definitions
	 */
	// protected final Map tipiActionDefMap = new HashMap();

	private boolean contextShutdown = false;
	protected boolean fakeJars = false;
	private final Map<String, String> lazyMap = new HashMap<String, String>();
	// protected final List<String> includeList = new ArrayList<String>();

	private final List<ThreadActivityListener> threadStateListenerList = new ArrayList<ThreadActivityListener>();

	protected TipiErrorHandler eHandler;
	private String errorHandler;

	/**
	 * Lists the toplevel components in the current implementation
	 */
	// protected final ArrayList<Object> rootPaneList = new ArrayList<Object>();
	private IActionManager myActionManager = new TipiActionManager();

	protected final List<TipiActivityListener> myActivityListeners = new ArrayList<TipiActivityListener>();
	private final List<TipiNavajoListener> navajoListenerList = new ArrayList<TipiNavajoListener>();

	private CookieManager myCookieManager;

	protected final Map<String, Navajo> navajoMap = new HashMap<String, Navajo>();

	protected TipiThreadPool myThreadPool;
	protected TipiComponent topScreen = null;
	protected int poolSize = 6;
//	private final Map<String, TipiTypeParser> parserInstanceMap = new HashMap<String, TipiTypeParser>();
	protected TipiStorageManager myStorageManager = null;
	protected IClassManager classManager = new ClassManager(this);
	protected final Stack<DescriptionProvider> descriptionProviderStack = new Stack<DescriptionProvider>();
	protected final Map<String, Object> globalMap = new HashMap<String, Object>();
	protected final long startTime = System.currentTimeMillis();
	private final List<ShutdownListener> shutdownListeners = new ArrayList<ShutdownListener>();
	private TipiResourceLoader tipiResourceLoader;
	private TipiResourceLoader genericResourceLoader;

	// for now... TODO something more elegant
	public final Map<String, String> systemPropertyMap = new HashMap<String, String>();

	private Object myToplevelContainer;
	private String navajoServer;
	private String navajoUsername;
	private String navajoPassword;
	private List<TipiEventReporter> tipiEventReporterList = new ArrayList<TipiEventReporter>();
	private Navajo stateNavajo;
	private List<TipiExtension> coreExtensionList;
	private List<TipiExtension> mainExtensionList;
	private List<TipiExtension> optionalExtensionList;
	private Map<String, TipiExtension> extensionMap = new HashMap<String, TipiExtension>();
	private final Map<Property, List<Property>> propertyLinkRegistry = new HashMap<Property, List<Property>>();

	private final Map<String, TipiConnector> tipiConnectorMap = new HashMap<String, TipiConnector>();
	private TipiConnector defaultConnector;
	private boolean hasDebugger;
	private final Map<String, List<PropertyChangeListener>> propertyBindMap = new HashMap<String, List<PropertyChangeListener>>();

	private TipiContext myParentContext;

	private final List<TipiDefinitionListener> tipiDefinitionListeners = new LinkedList<TipiDefinitionListener>();

	private TipiValidationDecorator tipiValidationManager;
	// Temp construct, until the connector pattern is fully functional
	// TODO, fix and remove
	protected ClientInterface clientInterface;

	protected final TipiApplicationInstance myApplication;

	private transient ScriptEngineManager scriptManager;

//	private boolean osgiMode;

	public TipiContext(TipiApplicationInstance myApplication, TipiContext parent) {
		this.myApplication = myApplication;
		List<TipiExtension> extensionList = getExtensionFromServiceEnumeration();
		
		initializeContext(extensionList, parent);
		FunctionFactoryFactory.getInstance().addFunctionResolver(classManager);
	}
	
	public TipiContext(TipiApplicationInstance myApplication, List<TipiExtension> extensionList) {
		this.myApplication = myApplication;
		initializeContext(extensionList, null);
		FunctionFactoryFactory.getInstance().addFunctionResolver(classManager);
	}

	protected List<TipiExtension> getExtensionFromServiceEnumeration() {
		List<TipiExtension> extensionList = listExtensions();
		return extensionList;
	}

	
	
	private List<TipiExtension> listExtensions() {
		List<TipiExtension> extensionList = new LinkedList<TipiExtension>();
		try {
			Iterator<TipiExtension> iter = ServiceRegistry
					.lookupProviders(TipiExtension.class);
			while (iter.hasNext()) {
				TipiExtension tipiExtension = iter.next();
				extensionList.add(tipiExtension);
//				tipiExtension.loadDescriptor();
			}
		} catch (Throwable e) {
			logger.error("Error: ",e);
		}
		return extensionList;
	}

	public TipiContext(TipiApplicationInstance myApplication,
			List<TipiExtension> preload, TipiContext parent) {
		this.myApplication = myApplication;

		// this();
		initializeContext(preload, parent);
		FunctionFactoryFactory.getInstance().addFunctionResolver(classManager);
	}

	private void initializeContext(List<TipiExtension> preload, TipiContext parent) {

		myParentContext = parent;
		initializeExtensions(preload.iterator());
		clientInterface = NavajoClientFactory.createDefaultClient();

		if (myThreadPool == null) {
			myThreadPool = new TipiThreadPool(this, getPoolSize());
		}
		NavajoFactory.getInstance().setExpressionEvaluator(
				new DefaultExpressionEvaluator());
		tipiResourceLoader = new ClassPathResourceLoader();
		setStorageManager(new TipiNullStorageManager());
		try {
			Class<? extends TipiContextListener> cc = (Class<? extends TipiContextListener>) Class
					.forName("com.dexels.navajo.tipi.tools.TipiEventDumpDebugger");
			TipiContextListener tcl = cc.newInstance();
			tcl.setContext(this);
			hasDebugger = true;
		} catch (Throwable e) {
			hasDebugger = false;
		}
	}

	public TipiApplicationInstance getApplicationInstance() {
		return myApplication;
	}

	public final IClassManager getClassManager() {
		return classManager;
	}

	protected void addMainExtension(TipiExtension te) {
		mainExtensionList.add(te);
	}
	private void initializeExtensions(Iterator<TipiExtension> tt) {
		coreExtensionList = new ArrayList<TipiExtension>();
		mainExtensionList = new ArrayList<TipiExtension>();
		optionalExtensionList = new ArrayList<TipiExtension>();
		while (tt.hasNext()) {
			TipiExtension element = tt.next();
			extensionMap.put(element.getId(), element);
			if (element.requiresMainImplementation() == null
					&& !element.isMainImplementation()) {
				coreExtensionList.add(element);
				continue;
			}
			if (element.isMainImplementation()) {
				mainExtensionList.add(element);
			} else {
				optionalExtensionList.add(element);
			}
			element.initialize(this);
		}
		List<TipiExtension> allExtensions = new LinkedList<TipiExtension>();
		allExtensions.addAll(mainExtensionList);
		allExtensions.addAll(coreExtensionList);
		allExtensions.addAll(optionalExtensionList);
		appendIncludes(allExtensions);
	}

	public void setTipiInstallationFolder(File install) {
		logger.info("Using install: "+install.getAbsolutePath());
		File tipi = new File(install,"tipi");
		File resource = new File(install,"resource");
		setTipiResourceLoader(new FileResourceLoader(tipi));
		setGenericResourceLoader(new FileResourceLoader(resource));
	}


	
	public void addOptionalInclude(TipiExtension te) {
		optionalExtensionList.add(te);
		processRequiredIncludes(te);

	}

	private void appendIncludes(List<TipiExtension> extensionList) {
		for (TipiExtension tipiExtension : extensionList) {
			processRequiredIncludes(tipiExtension);
		}
	}

	public List<TipiExtension> getExtensions() {
		// want them sorted
		List<TipiExtension> result = new ArrayList<TipiExtension>();
		result.addAll(coreExtensionList);
		result.addAll(mainExtensionList);
		result.addAll(optionalExtensionList);

		return result;

	}

	protected void clearLogFile() {
	}

	public void getTipiValidationDecorator(TipiValidationDecorator tv) {
		tipiValidationManager = tv;
	}

	public TipiValidationDecorator getTipiValidationDecorator() {
		return tipiValidationManager;
	}

	public void setTipiResourceLoader(TipiResourceLoader tr) {
		tipiResourceLoader = tr;
	}

	public void setGenericResourceLoader(TipiResourceLoader tr) {
		genericResourceLoader = tr;
	}

	public TipiResourceLoader getTipiResourceLoader() {
		return tipiResourceLoader;
	}

	public TipiResourceLoader getGenericResourceLoader() {
		return genericResourceLoader;
	}

	public void addNavajoListener(TipiNavajoListener tnl) {
		navajoListenerList.add(tnl);
	}

	public void removeNavajoListener(TipiNavajoListener tnl) {
		navajoListenerList.remove(tnl);
	}

	public void fireNavajoReceived(Navajo n, String service) {
		for (TipiNavajoListener element : navajoListenerList) {
			element.navajoReceived(n, service);
		}
	}

	public void fireNavajoSent(Navajo n, String service) {
		for (TipiNavajoListener element : navajoListenerList) {
			element.navajoSent(n, service);
		}
	}

	// public void setResourceBaseDirectory(File f) {
	// resourceBaseDirectory = f;
	// }
	//
	// public void setTipiBaseDirectory(File f) {
	// tipiBaseDirectory = f;
	// }

	public void debugLog(String category, String event) {
		logger.debug("category: "+category+" event: "+event);
	}

	public void handleException(Exception e) {
		showInternalError("Error", e);
	}

	public int getPoolSize() {
		return poolSize;
	}



	public ClassLoader getClassLoader() {
			return getClass().getClassLoader();
	}

	public Object getGlobalValue(String name) {
		return globalMap.get(name);
	}

	public void setGlobalValue(String name, Object value) {
		globalMap.put(name, value);
	}

	// public Map<String,Object> getGlobalMap() {
	// return new HashMap<String, Object>(globalMap);
	// }

	public abstract void setSplash(Object s);

	// public void setToplevel(RootPaneContainer tl) {
	// myTopLevel = tl;
	// }

	// public void parseFile(File location, boolean studioMode, String dir)
	// throws IOException, XMLParseException, TipiException {
	// parseStream(new FileInputStream(location), dir, studioMode);
	// }

	// public void parseURL(URL location, boolean studioMode) throws
	// IOException, XMLParseException, TipiException {
	// try {
	// parseStream(location.openStream(), location.toString(), studioMode);
	// } catch (IOException e) {
	// throw new TipiException("Can not resolve URL: " + location +
	// " or other IO error.", e);
	// }
	// }

	// public void parseURL(URL location, boolean studioMode, String
	// definitionName) throws IOException, XMLParseException, TipiException {
	// parseStream(location.openStream(), location.toString(), definitionName,
	// studioMode);
	// switchToDefinition(definitionName);
	//
	// }

//	public Map<String, XMLElement> getTipiClassDefMap() {
//		return getClassManager().getClassMap();
//	}

	// public Map getTipiDefinitionMap() {
	// return tipiComponentMap;
	// }

	protected void clearResources() {
		tipiInstanceMap.clear();
		tipiComponentMap.clear();
		// tipiClassMap.clear();
//		getClassManager().clearClassMap();
		clearTopScreen();

		eHandler = null;
		errorHandler = null;
		// rootPaneList.clear();
		Runtime runtimeObject = Runtime.getRuntime();
		runtimeObject.traceInstructions(false);
		runtimeObject.traceMethodCalls(false);
		runtimeObject.runFinalization();
		runtimeObject.gc();
	}

	public void clearLazyDefinitionCache() {
		logger.info("Flushing lazy definition cache!");
		Set<String> iterSet = new HashSet<String>(tipiComponentMap.keySet());
		for (Iterator<String> iter = iterSet.iterator(); iter.hasNext();) {
			String definitionName = iter.next();
			tipiComponentMap.remove(definitionName);
		}
	}

	public abstract void clearTopScreen();


	public void setSystemProperty(String name, String value) {
		systemPropertyMap.put(name, value);
	}

	public String getSystemProperty(String name) {

		String value = systemPropertyMap.get(name);
		String sysVal = null;
		if (value != null) {
			return value;
		}
		try {
			sysVal = System.getProperty(name);
			systemPropertyMap.put(name, sysVal);
			value = sysVal;
		} catch (SecurityException e) {
		}
		return value;

	}

	private void createClient(XMLElement config) {
		String impl = (String) attemptGenericEvaluate(config
				.getStringAttribute("impl", "'indirect'"));
		setSystemProperty("tipi.client.impl", impl);
		String cfg = (String) attemptGenericEvaluate(config.getStringAttribute(
				"config", "'server.xml'"));
		setSystemProperty("tipi.client.config", cfg);

		String locale = (String) attemptGenericEvaluate(config
				.getStringAttribute("locale", "'en'"));
		setSystemProperty("tipi.client.locale", cfg);

		String sublocale = (String) attemptGenericEvaluate(config
				.getStringAttribute("sublocale", "''"));
		setSystemProperty("tipi.client.sublocale", cfg);
		navajoServer = (String) attemptGenericEvaluate(config
				.getStringAttribute("server", ""));
		setSystemProperty("tipi.client.server", navajoServer);
		navajoUsername = (String) attemptGenericEvaluate(config
				.getStringAttribute("username", ""));
		setSystemProperty("tipi.client.username", navajoUsername);
		navajoPassword = (String) attemptGenericEvaluate(config
				.getStringAttribute("password", ""));
		setSystemProperty("tipi.client.password", navajoPassword);

		logger.info("Connecting to server: " + navajoServer);
		if (!impl.equals("direct")) {
			if (impl.equals("socket")) {
				throw new UnsupportedOperationException(
						"Sorry, I deprecated the direct client for tipi usage");
			}
			getClient().setServerUrl(navajoServer);
			getClient().setUsername(navajoUsername);
			getClient().setPassword(navajoPassword);

		} else {
			throw new UnsupportedOperationException(
					"Sorry, I deprecated the direct client for tipi usage");
		}

		getClient().setLocaleCode(locale);
		getClient().setSubLocaleCode(sublocale);
	}

	public void parseStream(InputStream in, ExtensionDefinition ed)
			throws IOException, XMLParseException, TipiException {
		XMLElement doc = new CaseSensitiveXMLElement();
		long stamp = System.currentTimeMillis();

		InputStreamReader isr = new InputStreamReader(in, "UTF-8");
		doc.parseFromReader(isr);
		doc.setTitle("Unknown");
		stamp = System.currentTimeMillis() - stamp;

		isr.close();
		parseXMLElement(doc, ed);
	}

	protected void parseXMLElement(XMLElement elm, ExtensionDefinition ed)
			throws TipiException {
		String elmName = elm.getName();
		// TODO Remove this hard dependency on NavajoFunctions, or promote the
		// StandardFunctionDefinitions class to Navajo core
		// ExtensionDefinition ed = new StandardFunctionDefinitions();
		String extension = elm.getStringAttribute("extension");
		setSplashInfo("Loading user interface");
		if (!elmName.equals("tid") && !elmName.equals("functiondef")) {
			throw new TipiException("TID Rootnode not found!, found " + elmName
					+ " instead.");
		}
		errorHandler = (String) elm.getAttribute("errorhandler", null);
		List<XMLElement> children = elm.getChildren();
		for (int i = 0; i < children.size(); i++) {
			XMLElement child = children.get(i);
			parseChild(child, extension, ed);
		}
	}

	/**
	 * Parses a toplevel tipi file (every child is a child of tid)
	 * 
	 * @param child
	 * @param dir
	 * @throws TipiException
	 */
	private void parseChild(XMLElement child, String extension,
			ExtensionDefinition ed) throws TipiException {

		String childName = child.getName();
		if (childName.equals("client-config")) {
			createClient(child);
			return;
		}
		if (childName.equals("component") || childName.equals("tipi")
				|| childName.equals("definition")) {
			parseDefinition(child);
			return;
		}

		if (childName.equals("tipiclass")) {
			try {
				getClassManager().addTipiClassDefinition(child, ed);
			} catch (ClassNotFoundException e) {
				throw new TipiException("Class loading problem while getting classdef: "+child,e);
			} catch (InstantiationException e) {
				throw new TipiException("Class instantiation problem while getting classdef: "+child,e);
			} catch (IllegalAccessException e) {
				throw new TipiException("Class access problem while getting classdef: "+child,e);
			}
			if (extension != null) {
				child.setAttribute("extension", extension);
			}
			return;
		}
		if (childName.equals("tipiaction")) {
			try {
				getActionManager().addAction(child, ed);
			} catch (ClassNotFoundException e) {
				throw new TipiException("Error loading action: "+child,e);
			}
			return;
		}
		if (childName.equals("tipi-include")) {
			parseLibrary(child, ed);
			return;
		}
		if (childName.equals("tipi-parser")) {
			getClassManager().parseParser(child,ed);
			return;
		}

		if (childName.equals("tml")) {
			String service = child.getStringAttribute("service");
			if (service == null) {
				throw new TipiException(
						"Inline tml needs a 'service' attribute");
			}
			StringWriter sw = new StringWriter();
			try {
				child.write(sw);
				StringReader sr = new StringReader(sw.toString());
				Navajo n = NavajoFactory.getInstance().createNavajo(sr);
				addNavajo(service, n);
			} catch (IOException e) {
				logger.error("Error: ",e);
			}

			return;
		}

		if (childName.equals("tipi-storage")) {
			parseStorage(child);
			return;
		}
		if (childName.startsWith("d.")) {
			if (child.getAttribute("name") == null) {
				throw new TipiException(
						"Tipi definition should have a name attribute:"
								+ child.toString());
			}
			parseDefinition(child);
			return;
		}
		if (childName.equals("function")) {
			parseFunction(child, ed);
			return;
		}

		throw new TipiException("Wtf? What is this tag: " + childName);
	}

	private void parseFunction(XMLElement f, ExtensionDefinition ed)  {

		XMLElement description = f.getChildByTagName("description");
		String desc = description == null ? "" : description.getContent();
		XMLElement input = f.getChildByTagName("input");
		String inp = input == null ? "" : input.getContent();
		XMLElement result = f.getChildByTagName("result");
		String res = result == null ? "" : result.getContent();
		String name = f.getStringAttribute("name");

		FunctionDefinition fd = new FunctionDefinition(
				f.getStringAttribute("class"), desc, inp, res);
		// TODO Realign tipixml / nanoxml implementations
		// fd.setXmlElement(f);
		getClassManager().addFunctionDefinition(name, fd);
	}

	private void parseStorage(XMLElement child) {
		String type = child.getStringAttribute("type");
		if ("asp".equals(type)) {
			String instanceId = child.getStringAttribute("instanceId");
			String scriptPrefix = child.getStringAttribute("scriptPrefix");
			TipiGeneralAspManager tsm = new TipiGeneralAspManager(scriptPrefix);
			tsm.setInstanceId(instanceId);
			tsm.setContext(this);
			setStorageManager(tsm);
			return;
		}
		if ("file".equals(type)) {
			String basePath = child.getStringAttribute("dir");
			String instanceId = child.getStringAttribute("instanceId");

			File baseDir = null;
			if (basePath != null) {
				baseDir = new File(basePath);
				if (instanceId != null) {
					baseDir = new File(baseDir, instanceId);
				}
			}
			setStorageManager(new TipiFileStorageManager(baseDir));
			return;
		}
		if ("null".equals(type)) {
			setStorageManager(new TipiNullStorageManager());
			return;
		}
		try {
			Class<? extends TipiStorageManager> c = (Class<? extends TipiStorageManager>) Class
					.forName(type);
			TipiStorageManager tsm = c.newInstance();
			setStorageManager(tsm);
		} catch (ClassNotFoundException e) {
			logger.error("Error: ",e);
		} catch (InstantiationException e) {
			logger.error("Error: ",e);
		} catch (IllegalAccessException e) {
			logger.error("Error: ",e);
		}

	}

	private void parseDefinition(XMLElement child) {
		String childName = child.getName();
		if (childName.startsWith("d.")) {
			String name = child.getStringAttribute("name");
			child.setAttribute("name", name);
			addComponentDefinition(child);

		}
		if (childName.equals("tipi") || childName.equals("component")
				|| childName.equals("definition")) {
			testDefinition(child);
			addComponentDefinition(child);
		}
	}

	private final void testDefinition(XMLElement xe) {
		if (xe.getAttribute("name") == null) {
			throw new RuntimeException(
					"Tipi/component definition found without name at: "
							+ xe.getLineNr());
		}
		if (xe.getAttribute("class") == null && xe.getAttribute("type") == null) {
			throw new RuntimeException(
					"Tipi/component definition found without class at: "
							+ xe.getLineNr());
		}
	}

	public InputStream getTipiResourceStream(String location)
			throws IOException {
		logger.debug("Getting tipi file: "+location+" loader: "+tipiResourceLoader);
		if (tipiResourceLoader != null) {
			return tipiResourceLoader.getResourceStream(location);
		} else {
			URL u = getTipiResourceURL(location);
			if (u == null) {
				throw new IOException("Location not found: " + location);
			}
			return u.openStream();
		}
	}

	public URL getResourceURL(String location) {
		// Try the classloader first, the
		URL u = getClass().getClassLoader().getResource(location);

		if (u != null) {
			return u;
		}

		if (genericResourceLoader != null) {
			try {
				URL resourceURL = genericResourceLoader
						.getResourceURL(location);
				if (resourceURL != null) {
					return resourceURL;
				}
			} catch (IOException e) {
				logger.error("Error: ",e);
			}
		}

		if (tipiResourceLoader != null) {
			try {
				return tipiResourceLoader.getResourceURL(location);
			} catch (IOException e) {
				logger.error("Error: ",e);
			}
		}
		return null;
	}


	private URL getTipiResourceURL(String location) {
		ClassLoader cl = getClass().getClassLoader();
		URL u = cl.getResource(location);
		if (u == null) {
		} else {
			return u;
		}
		// Now, it was not found in the classpath. if there is a
		// tipiResourceLoader, then it means
		// we are looking for a resource we are not certain where we can find
		// it.
		// If we supply a tipiResourceLoader, this means that we will NEVER look
		// in the local file
		// system.
		if (tipiResourceLoader != null) {
			try {
				return tipiResourceLoader.getResourceURL(location);
			} catch (IOException e) {
				logger.error("Error: ",e);
			}
		}
		return null;
	}

	// public final void parseLibraryFromClassPath(String location) {
	// parseLibrary(location, false, null, false);
	// }

	private final void parseLibrary(XMLElement lib,
			ExtensionDefinition tipiExtension) throws TipiException {

		String location = (String) lib.getAttribute("location");
		String lazy = (String) lib.getAttribute("lazy");
		boolean isLazy = "true".equals(lazy);
		String componentName = (String) lib.getAttribute("definition");
		if (componentName == null && isLazy) {
			throw new TipiException(
					"Lazy include, but no definition found. Location: "
							+ location);
		}
		if (isLazy && getTipiDefinition(componentName) != null) {
			// already present.
			logger.info("Not reparsing lazy: " + componentName);
			return;
		}
		parseLibrary(location, true, componentName, isLazy, tipiExtension);
	}

	public void processRequiredIncludes(TipiExtension tipiExtension) {
		logger.info("Adding extension: "+tipiExtension.getId());
		List<String> includes = new LinkedList<String>();
		String[] ss = tipiExtension.getIncludes();
		if (ss == null || ss.length == 0) {
			logger.warn("Warning: extension: " + tipiExtension.getId()
					+ " - " + tipiExtension.getDescription()+" class: ("+tipiExtension.getClass()+")"
					+ " has no includes!");
			return;
		}
//		Thread.dumpStack();
		for (int i = 0; i < ss.length; i++) {
			logger.info("Adding include: " + ss[i]);
			includes.add(ss[i]);
		}

		for (String element : includes) {
			try {
				parseLibrary(element, false, element, false, tipiExtension);
			} catch (UnsupportedClassVersionError e) {
				throw new UnsupportedClassVersionError(
						"Error parsing extension: " + element
								+ " wrong java version! " + e.getCause()
								+ " - " + e.getMessage());
			}
		}

	}

	private final void parseLibrary(String location, boolean addToInclude,
			String definition, boolean isLazy, ExtensionDefinition tipiExtension) {
		String extension = null;

		if (tipiExtension != null) {
			String projectName = tipiExtension.getProjectName();
			if(projectName==null) {
				extension = "unknown";
			} else {
				extension = projectName.toLowerCase();
			}
		}
		if (isLazy) {
			if (definition == null) {
				throw new IllegalArgumentException(
						"Lazy include, but no definition found. Location: "
								+ location);
			}
			lazyMap.put(definition, location);
			return;
		}
		try {
			if (location != null) {
				InputStream in = resolveInclude(location, tipiExtension);
				if (in == null) {
					logger.warn("Missing include at location: "+location+" and extension: "+tipiExtension);
					return;
				}
				XMLElement doc = new CaseSensitiveXMLElement();
				try {
					long stamp = System.currentTimeMillis();
					InputStreamReader isr = new InputStreamReader(in, "UTF-8");
					doc.parseFromReader(isr);
					doc.setTitle(definition);
					stamp = System.currentTimeMillis() - stamp;
					isr.close();
				}
				/** @todo Throw these exceptions */
				catch (XMLParseException ex) {
					showInternalError(
							"XML parse exception while parsing file: "
									+ location + " at line: " + ex.getLineNr(),
							ex);
					logger.error("Error: ",ex);
					return;
				} catch (IOException ex) {
					logger.error("Error: ",ex);
					return;
				}
				if (extension != null) {
					doc.setAttribute("extension", extension);
				}
				parseXMLElement(doc, tipiExtension);
			}
		} catch (Throwable e) {
			logger.error("Error: ",e);
		}
	}

	private InputStream resolveInclude(String location,
			ExtensionDefinition tipiExtension) {
		// first, try to resolve the include by checking the classpath:
		if (tipiExtension != null) {
			InputStream iss = tipiExtension.getClass().getClassLoader()
					.getResourceAsStream(location);
			if (iss != null) {
				return iss;
			}
		}
		InputStream iss = getClass().getClassLoader().getResourceAsStream(
				location);
		if (iss != null) {
			logger.info("FALLBACK: Using TipiContext classloader to locate include.");
			return iss;
		}
		try {
			InputStream in = getTipiResourceStream(location);
			return in;
		} catch (IOException e) {
			logger.warn("Error resolving tipi include", e);
			// classload failed. Continuing.
		}
		return null;
	}

	public TipiActionBlock instantiateTipiActionBlock(XMLElement definition,
			TipiComponent parent, TipiExecutable parentExe)
			 {
		TipiActionBlock c = createTipiActionBlockCondition();
		c.load(definition, parent, parentExe);
		return c;
	}

	public TipiActionBlock instantiateDefaultTipiActionBlock() {
		TipiActionBlock c = createTipiActionBlockCondition();
		return c;
	}

	public TipiAction instantiateTipiAction(XMLElement definition,
			TipiComponent parent, TipiExecutable parentExe)
			throws TipiException {
		String type = (String) definition.getAttribute("type");
		if (type == null) {
			type = definition.getName();
		}
		if (type == null) {
			throw new TipiException("Undefined action type in: "
					+ definition.toString());
		}
		return myActionManager.instantiateAction(definition, parent, parentExe);
	}

	public IActionManager getActionManager() {
		return myActionManager;
	}

	public void setActionManager(IActionManager man) {
		this.myActionManager = man;
	}
	public TipiLayout instantiateLayout(XMLElement instance, TipiComponent cc)
			throws TipiException {

		String type = null;
		if (instance.getName().equals("layout")) {
			type = (String) instance.getAttribute("type");
		} else {
			if (instance.getName().startsWith("l.")) {
				type = instance.getName().substring(2,
						instance.getName().length());
			} else {
				logger.warn("WARNING, STRANGE LAYOUT TAG: " + instance);
			}
		}
		TipiLayout tl = (TipiLayout) instantiateClass(type, null, instance, cc);
		if (tl == null) {
			return null;
		}
		XMLElement xx = getClassManager().getAssembledClassDef(type);
		tl.setComponent(cc);
		tl.setName(type);
		tl.setClassDef(xx);
		tl.initializeLayout(instance);
		tl.loadClassDef();
		return tl;
	}

	// /**
	// * @deprecated
	// */
	// public TipiLayout instantiateLayout(XMLElement instance) throws
	// TipiException {
	// return instantiateLayout(instance,null);
	// }

	protected TipiComponent instantiateComponentByDefinition(
			XMLElement definition, XMLElement instance, TipiEvent event,
			TipiComponent parent) throws TipiException {
		String clas = definition.getStringAttribute("class");
		if (clas == null) {
			clas = definition.getStringAttribute("type");
		}
		if (definition.getName().startsWith("d.")) {
			clas = definition.getName().substring(2,
					definition.getName().length());

		}
		String name = instance.getStringAttribute("name");
		if (name == null) {
			showInternalError("Error instantiating component: " + clas
					+ ". No name supplied. instance: " + instance);
		}
		if (!clas.equals("")) {
			TipiComponent tc = (TipiComponent) instantiateClass(clas, name,
					instance, parent);
			XMLElement classDef = getClassManager().getAssembledClassDef(clas);

			/**
			 * @todo think these two can be removed, because they are invoked in
			 *       the instantiateClass method
			 */
			tc.loadEventsDefinition(this, definition, classDef);
			tc.loadMethodDefinitions(this, definition, classDef);
			// -----------------------------
			tc.loadStartValues(definition, event);

			tc.commitToUi();
			return tc;
		} else {
			throw new TipiException(
					"Problems instantiating TipiComponent class: "
							+ definition.toString());
		}
	}

	/**
	 * TODO Support parentExtension: call setParentExtension when creating a
	 * component, that way we know who its instantiating extension is.
	 * 
	 * @param instance
	 * @param event
	 * @param t
	 * @param parent
	 * @return
	 * @throws TipiException
	 */

	public TipiComponent instantiateComponent(XMLElement instance,
			TipiEvent event, TipiInstantiateTipi t, TipiComponent parent)
			throws TipiException {
		String name = (String) instance.getAttribute("name");
		String tagName = instance.getName();
		String clas = instance.getStringAttribute("class");
		if (tagName.startsWith("c.")) {
			clas = instance.getName().substring(2, tagName.length());
		}
		if (tagName.startsWith("d.")) {
			clas = instance.getName().substring(2, tagName.length());
		}

		if (clas == null) {
			clas = instance.getStringAttribute("type", "");
		}

		TipiComponent tc = null;
		if (clas.equals("") && name != null && !"".equals(name)) {
			// No class provided. Must be instantiating from a definition.
			XMLElement xx = getComponentDefinition(name);
			if (xx == null) {
				throw new TipiException(
						"Definition based instance, but no definition found. Definition: "
								+ name);
			}
			tc = instantiateComponentByDefinition(xx, instance, event, parent);
		} else {
			// Class provided. Not instantiating from a definition, name is
			// irrelevant.
			tc = (TipiComponent) instantiateClass(clas, null, instance, parent);
		}
		tc.setClassName(clas);
		// tc.setParent(parent);

		if (t == null) {
			tc.loadStartValues(instance, event);
		} else {
			Set<String> paramNames = t.getParameterNames();
			for (String param : paramNames) {
				if (param.equals("id") || param.equals("name")
						|| param.equals("class") || param.equals("location")
						|| param.equals("expectType") || param.equals("force")) {
					continue;
				}
				Object o = t.getEvaluatedParameterValue(param, event);
				if (param.equals("constraint") || param.equals("constraints")) {
					tc.setConstraints(o);
				} else {
					tc.setValue(param, o);

				}

			}
		}

		tc.componentInstantiated();

		return tc;
	}

	private final void killComponent(TipiComponent comp) {
		comp.disposeComponent();
	}

	/**
	 * 
	 * @param  comp
	 */
	public void disposeTipiComponent(TipiComponent comp) {
		if (comp.getTipiParent() == null) {
			showInternalError("Can not dispose tipi: It has no parent!");
			return;
		}
		TipiComponent parent = comp.getTipiParent();
		Message pp = parent.getStateMessage();
		final Message stateMessage = comp.getStateMessage();
		pp.removeMessage(stateMessage);

		Runnable r = new Runnable() {

			public void run() {
				try {
					unlink(getStateNavajo(), stateMessage);
				} catch (NavajoException e) {
					logger.error("Error: ",e);
				}

			}
		};
		try {
			execute(r);
		} catch (TipiException e) {
			logger.error("Error: ",e);
		}
		parent.removeChild(comp);
		// Message m = stateMessage;

		// pp.removeMessage(m);
		if (comp instanceof TipiDataComponent) {
			removeTipiInstance(comp);
		} else {
		}
		if (comp instanceof TipiConnector) {
			TipiConnector rr = (TipiConnector) comp;
			tipiConnectorMap.remove(rr.getConnectorId());
			if (defaultConnector == comp) {
				defaultConnector = null;
			}
		} else {
		}
		killComponent(comp);
		fireTipiStructureChanged(parent);
	}

	private Object instantiateClass(String className, String defname,
			XMLElement instance, TipiComponent parent) throws TipiException {

		XMLElement tipiDefinition = null;
		XMLElement classDef = getClassManager().getAssembledClassDef(className);
		Class<?> c = getClassManager().getTipiClass(classDef);
		// AAAAAAP
		if (defname != null) {
			tipiDefinition = getComponentDefinition(defname);
		}

		String componentType = classDef.getStringAttribute("type");
		if (c == null) {
			throw new TipiException(
					"Error retrieving class definition. Looking for class: "
							+ defname + ", classname: " + className);
		}
		Object o;
		try {
			o = c.newInstance();
		} catch (Exception ex) {
			logger.error("Error: ",ex);
			throw new TipiException(
					"Error instantiating class:"
							+ className
							+ "/"
							+ defname
							+ " class: "
							+ c.getName()
							+ ". Class may not have a public default contructor, or be abstract, or an interface");
		}
		if (TipiComponent.class.isInstance(o)) {
			TipiComponent tc = (TipiComponent) o;
			tc.setContext(this);
			tc.setParent(parent);
			tc.setClassName(defname);
			if(parent!=null) {
				tc.setParentContainer(parent.getContainer());
			}
			if (tc.getId() == null) {
				tc.setId(generateComponentId(null, tc));
			}
			tc.setPropertyComponent(classDef.getBooleanAttribute("propertycomponent", "true", "false", false));
			tc.initContainer();
			tc.instantiateComponent(instance, classDef);
			if (tipiDefinition != null) {
				tc.load(tipiDefinition, instance, this);
			} else {
				tc.load(instance, instance, this);
			}
			// Moved from the previous else clause
			tc.loadEventsDefinition(this, instance, classDef);
			tc.loadMethodDefinitions(this, instance, classDef);

			if ("connector".equals(componentType)) {
				boolean isDefaultConnector = instance.getBooleanAttribute(
						"default", "true", "false", false);
				if (!(tc instanceof TipiConnector)) {
					showInternalError("Error: Component: "+ className + " is registered as a component, but it does not implement TipiConnector!");
				} else {
					registerConnector((TipiConnector) tc);
					if (isDefaultConnector) {
						this.defaultConnector = (TipiConnector) tc;
					}
				}
			}

			return tc;
		}
		if (TipiLayout.class.isInstance(o)) {
			TipiLayout tl = (TipiLayout) o;
			tl.setContext(this);
			return tl;
		}
		throw new TipiException("INSTANTIATING UNKOWN SORT OF CLASS THING.");
	}

	public void addTipiInstance(String service, TipiDataComponent instance) {
		if (tipiInstanceMap.containsKey(service)) {
			List<TipiDataComponent> al = tipiInstanceMap.get(service);
			al.add(instance);
		} else {
			List<TipiDataComponent> al = new ArrayList<TipiDataComponent>();
			al.add(instance);
			tipiInstanceMap.put(service, al);
		}
	}

	public void removeTipiInstance(TipiComponent instance) {
		Iterator<List<TipiDataComponent>> c = tipiInstanceMap.values()
				.iterator();
		while (c.hasNext()) {
			List<TipiDataComponent> current = c.next();
			// BEWARE of the backward order
			for (int i = current.size() - 1; i >= 0; i--) {
				TipiComponent element = current.get(i);
				if (element == instance) {
					current.remove(i);
				}
				// if (instance.getPath().equals(element.getPath())) {
				// current.remove(i);
				// continue;
				// }
				// if (element.getPath().startsWith(instance.getPath() + "/")) {
				// current.remove(i);
				// }
			}
		}
	}

	/**
	 * Returns a cached tipi definition. If it is not cached, it will return
	 * null. Generally, you'll want to use getComponentDefinition(name), which
	 * will do whatever it can to locate the source.
	 * 
	 * @param name
	 * @return
	 */
	public XMLElement getTipiDefinition(String name) {
		XMLElement xe = tipiComponentMap.get(name);
		return xe;
	}

	/**
	 * Lists all instantiated components that listen to the specified service
	 * 
	 * @param service
	 * @return
	 */
	public List<TipiDataComponent> getTipiInstancesByService(String service) {
		// List<TipiDataComponent> x= tipiInstanceMap.get(service);
		return tipiInstanceMap.get(service);
	}

	public List<String> getListeningServices() {
		return new ArrayList<String>(tipiInstanceMap.keySet());
	}

	public XMLElement getComponentDefinition(String componentName) {
		// if(lazyMap.containsKey(componentName)) {
		// String location = (String) lazyMap.get(componentName);
		// }
		XMLElement xe = getTipiDefinition(componentName);
		if (xe != null) {
			return xe;
		}
		String location = lazyMap.get(componentName);
		if (location == null) {

			// String fullName = null;
			// if (componentName.indexOf(".") != -1) {
			// fullName = componentName.replace(".", "/");
			// } else {
			// fullName = componentName;
			// }
			String total = null;
			if (componentName.endsWith(".xml")) {
				total = componentName;
			} else {
				total = componentName + ".xml";
			}
			String actualName = total.substring(total.lastIndexOf('/') + 1,
					total.lastIndexOf('.'));
			parseLibrary(total, true, actualName, false, null);
			xe = getTipiDefinition(actualName);
			fireDefinitionLoaded(actualName, xe);
			return xe;

			// return null;
		} else {
			parseLibrary(location, true, componentName, false, null);
			xe = getTipiDefinition(componentName);
			fireDefinitionLoaded(componentName, xe);
			return xe;

		}
	}

	protected void addComponentDefinition(XMLElement elm) {
		String defname = (String) elm.getAttribute("name");
		setSplashInfo("Loading: " + defname);
		if (defname.equals("init")) {

		}
		tipiComponentMap.put(defname, elm);

		if (!hasDebugger) {
			// debug mode, don't cache at all

			// tipiComponentMap.put(defname, elm);

		}
		// tipiMap.put(defname, elm);
	}

	public void addTipiDefinition(XMLElement elm) {
		addComponentDefinition(elm);
	}

	private TipiActionBlock createTipiActionBlockCondition() {
		return new TipiActionBlock(this);
	}

	// public ArrayList getScreens() {
	// return screenList;
	// }

	public Object getTopLevel() {
		if (myToplevelContainer != null) {
			return myToplevelContainer;
		}
		return getDefaultTopLevel().getContainer();
	}

	public void setTopLevelContainer(Object o) {
		myToplevelContainer = o;
	}

	public TipiComponent getDefaultTopLevel() {
		return topScreen;
	}

	public void setDefaultTopLevel(TipiComponent tc) {
		topScreen = tc;
	}



	public void switchToDefinition(String name) throws TipiException {
		clearTopScreen();
		setSplashInfo("Starting application: " + name);
		XMLElement componentDefinition = null; //
		componentDefinition = getComponentDefinition(name);
		// fallback to init:
		if (componentDefinition == null) {
			componentDefinition = getComponentDefinition("init");
		}
		if (componentDefinition == null) {
			showInternalError("TipiDefinition not found. Available definitions: "
					+ tipiComponentMap.keySet());
			throw new TipiException(
					"Fatal tipi error: Can not switch. Unknown definition: "
							+ name);
		}
		componentDefinition.setAttribute("id", "init");
		TipiComponent tc = instantiateComponent(componentDefinition, null,
				null, null);
		tc.commitToUi();

		try {
			TipiExtension t = extensionMap.get("develop");
			if (t != null) {
				TipiComponent dev = instantiateComponent(
						getComponentDefinition("develop"), null, null, null);
				tc.addComponent(dev, this, null);
			}
		} catch (Throwable e) {
			logger.error("Error instantiating debug component. Continuing",e);
		}

		getDefaultTopLevel().addComponent(tc, this, null);
		getDefaultTopLevel().addToContainer(tc.getContainer(), null);
		try {
			getStateNavajo().addMessage(tc.getStateMessage());
		} catch (NavajoException e) {
			logger.error("Error: ",e);
		}
		setSplashVisible(false);
		fireTipiStructureChanged(tc);

		if (errorHandler != null) {
			logger.warn("Error handlers are deprecated remove 'error' attribute: It is ignored.");
		}
	}

	public abstract void setSplashVisible(boolean b);

	public abstract void setSplashInfo(String s);

	public TipiComponent getTipiComponentByPath(String path) {
		if (path.indexOf("/") == 0) {
			path = path.substring(1);
		}
		return getDefaultTopLevel().getTipiComponentByPath(path);
	}

	public TipiDataComponent getTipiByPath(String path) {
		TipiComponent tc = getTipiComponentByPath(path);
		if (!TipiDataComponent.class.isInstance(tc)) {
			return null;
		}
		return (TipiDataComponent) tc;
	}


	/**
	 * @deprecated
	 */
	@Deprecated
	private Navajo doSimpleSend(Navajo n, String service,
			ConditionErrorHandler ch, long expirtationInterval, String hosturl,
			String username, String password, String keystore, String keypass,
			boolean breakOnError) throws TipiBreakException {
		return doSimpleSend(n, service, ch, expirtationInterval, hosturl,
				username, password, breakOnError);
	}

	
	/**
	 * @deprecated
	 */
	@Deprecated
	private Navajo doSimpleSend(Navajo n, String service,
			ConditionErrorHandler ch, long expirtationInterval, String hosturl,
			String username, String password, boolean breakOnError) throws TipiBreakException {
		Navajo reply = null;
		try {
			if (hosturl != null && !"".equals(hosturl)) {
					hosturl = navajoServer;
					username = navajoUsername;
					password = navajoPassword;
					String url = getClient().getServerUrl();
					getClient().setServerUrl(hosturl);
					getClient().setUsername(username);
					getClient().setPassword(password);
					reply = getClient().doSimpleSend(n, service, ch,
							expirtationInterval);
					getClient().setServerUrl(url);
					debugLog("data", "simpleSend to host: " + hosturl
							+ " username: " + username + " method: " + service);
			} else {
				reply = getClient().doSimpleSend(n, service, ch,
						expirtationInterval);
				debugLog("data", "simpleSend client: "
						+ getClient().getClientName() + " method: " + service);
			}
		} catch (Throwable ex) {
			logger.error("Sending problem:",ex);
			if (eHandler != null && Exception.class.isInstance(ex)) {
				debugLog("data", "send error occurred:" + ex.getMessage()
						+ " method: " + service);
				showInternalError("Probleem:", ex);
				if (breakOnError) {
					throw new TipiBreakException(TipiBreakException.USER_BREAK);
				}
			}
		}
		if (reply == null) {
			reply = NavajoFactory.getInstance().createNavajo();
			reply.addHeader(NavajoFactory.getInstance().createHeader(reply,
					service, "", "", -1));

		}

		return reply;
	}


	@Deprecated
	public void performTipiMethod(TipiDataComponent t, Navajo n,
			String tipiDestinationPath, String method, boolean breakOnError,
			TipiEvent event, long expirationInterval, String hosturl,
			String username, String password, String keystore, String keypass)
			throws TipiBreakException {

		fireNavajoSent(n, method);
		Navajo reply = doSimpleSend(n, method, null, expirationInterval,
				hosturl, username, password, keystore, keypass, breakOnError);
		fireNavajoReceived(reply, method);

		addNavajo(method, reply);
		loadNavajo(reply, method, tipiDestinationPath, event, breakOnError);
	}

	public Navajo getNavajo(String method) {
		return navajoMap.get(method);
	}

	public Set<String> getNavajoNames() {
		return navajoMap.keySet();
	}

	public void removeNavajo(String method) {
		navajoMap.remove(method);
	}

	public void addNavajo(String method, Navajo navajo) {
		Header h = navajo.getHeader();
		if (h == null) {
			h = NavajoFactory.getInstance().createHeader(navajo, method,
					"unknown", "unknown", -1);
			navajo.addHeader(h);
		}
		navajoMap.put(method, navajo);
	}

	public void loadNavajo(Navajo reply, String method)
			throws TipiBreakException {
		Header h = reply.getHeader();
		if (h == null) {
			h = NavajoFactory.getInstance().createHeader(reply, method,
					"unknown", "unknown", -1);
			reply.addHeader(h);
		}
		loadNavajo(reply, method, "*", null, false);
		Navajo compNavajo = null;
		if (hasDebugger && !"NavajoListNavajo".equals(method)) {
			try {
				compNavajo = createNavajoListNavajo();
				loadNavajo(compNavajo, "NavajoListNavajo");
				logger.debug("Firing navajo: " + method);

			} catch (NavajoException e) {
				logger.error("Error: ",e);
			}

		}
		if (compNavajo != null) {
			fireNavajoReceived(compNavajo, "NavajoListNavajo");
		}

		assert (reply.getHeader() != null);
	}

	public void loadNavajo(Navajo reply, String method,
			String tipiDestinationPath, TipiEvent event, boolean breakOnError)
			throws TipiBreakException {
		if (reply != null) {
			// TODO Put this in a more elegant place
			// TODO No remove completely. Don't like it.
			if (eHandler == null) {
				eHandler = new BaseTipiErrorHandler();
				eHandler.setContext(this);
				eHandler.initResource();

			}
			String errorMessage = eHandler.hasErrors(reply);
			if (errorMessage != null) {
				logger.error("Errors detected. ");
				boolean hasUserDefinedErrorHandler = false;
				List<TipiDataComponent> tipis = getTipiInstancesByService(method);
				if (tipis != null) {
					for (int i = 0; i < tipis.size(); i++) {
						TipiDataComponent current = tipis.get(i);

						if (current.hasPath(tipiDestinationPath, event)) {
							boolean hasHandler = false;
							debugLog("data    ",
									"delivering error from method: " + method
											+ " to tipi: " + current.getId());
							hasHandler = current.loadErrors(reply, method);
							if (hasHandler) {
								hasUserDefinedErrorHandler = true;
							}
						}
					}
				}
				if (!hasUserDefinedErrorHandler) {
					logger.error("Delivering usererror: \n"
							+ errorMessage);
					showWarning(errorMessage, "Invoerfout");
				}
				if (breakOnError) {
					throw new TipiBreakException(
							TipiBreakException.WEBSERVICE_BREAK);
				}
				return;
			}
			try {
				loadTipiMethod(reply, method);
			} catch (Exception ex) {
				logger.error("Error: ",ex);
			}
		} else {
			logger.error("Trying to load null navajo.");
		}

	}

	public void parseStudio() throws XMLParseException {
		// do nothing
	}

	protected void storeTemplateNavajo(String method, Navajo reply) {
		// do nothing
	}

	protected void fireNavajoLoaded(String method, Navajo reply) {
		// do nothing
	}

	protected void loadTipiMethod(Navajo reply, String method)
			throws TipiException, NavajoException {

		Navajo oldNavajo = getNavajo(method);
		if (oldNavajo != null && oldNavajo != reply) {
			unloadNavajo(oldNavajo, method);
		}
		addNavajo(method, reply);

		List<TipiDataComponent> tipiList;
		tipiList = getTipiInstancesByService(method);
		if (tipiList == null) {
			fireNavajoReceived(reply, method);
			return;
		}
		for (int i = 0; i < tipiList.size(); i++) {
			TipiDataComponent t = tipiList.get(i);
			debugLog("data    ", "delivering data from method: " + method
					+ " to tipi: " + t.getId());
			try {
				t.loadData(reply, method);
			} catch (TipiBreakException e) {
				logger.warn("Data refused by component");
			}
		}

		fireNavajoReceived(reply, method);
	}

	private void unloadNavajo(Navajo reply, String method)
			throws NavajoException {
		if (reply.getHeader() == null) {
			logger.error("WTF!!!!! WHY DOESN'T IT HAVE A HEADER?!");
		} else {
			unlink(reply);

		}
		removeNavajo(method);
	}

	protected Object attemptGenericEvaluate(String expression) {
		Operand o = null;
		try {
			o = evaluate(expression, getDefaultTopLevel(), null);
		} catch (Throwable ex) {
			return expression;
		}
		if (o == null) {
			return expression;
		}
		if (o.value == null) {
			return expression;
		}
		return o.value;
	}

	public Operand evaluate(String expr, TipiComponent tc, TipiEvent event) {
		return evaluate(expr, tc, event, null);
	}

	public Operand evaluate(String expr, TipiComponent tc, TipiEvent event,
			Message currentMessage) {

		Navajo nearestNavajo = null;
		if (tc != null) {
			nearestNavajo = tc.getNearestNavajo();
		}
		return evaluate(expr, tc, event, nearestNavajo, currentMessage);
	}

	public Operand evaluate(String expr, TipiComponent tc, TipiEvent event,
			Navajo n, Message currentMessage) {
		Operand o = null;
		if (expr == null) {
			return null;
		}
		try {
			Object sync = event;
			if (sync == null) {
				sync = tc;
			}
			// synchronized (sync) {
			tc.setCurrentEvent(event);
			o = Expression.evaluate(expr, n, null, currentMessage, null, tc);
			if (o == null) {
				logger.debug("Expression evaluated to null operand!");
				return null;
			}
			// }
		} catch (Exception ex) {
			logger.error("Not happy while evaluating expression: " + expr, ex);
			return o;
		} catch (Error ex) {
			logger.error("Not happy while evaluating expression: " + expr,ex);
			return o;
		}
		if (o.type.equals(Property.STRING_PROPERTY)) {
			if (o.value != null) {
				String s = (String) o.value;
				if (s.length() > 1) {
					if (s.charAt(0) == '\'' && s.charAt(s.length() - 1) == '\'') {
						o.value = s.substring(1, s.length() - 2);
					}
				}
			}
		}
		return o;
	}

	public Object evaluateExpression(String expression, TipiComponent tc,
			TipiEvent event) throws Exception {
		Object obj = null;
		if (expression.startsWith("{") && expression.endsWith("}")) {
			String path = expression.substring(1, expression.length() - 1);
			// Bad bad and evil. The exist operator should be outside the
			// curlies.
			// That would require some extensions to the expression mechanism.
			// The current construction is a bit stupid, but it kind of works
			// for now.
			if (path.startsWith("?")) {
				obj = new Boolean(exists(tc, path.substring(1)));
			} else if (path.startsWith("!?")) {
				obj = new Boolean(!exists(tc, path.substring(2)));
			} else {
				StringTokenizer str = new StringTokenizer(path, ":/");
				String protocol = null;
				String rest = null;
				if (str.hasMoreTokens()) {
					protocol = str.nextToken();
				} else {
					throw new TipiException(
							"Null protocol?! evaluating expression: "
									+ expression);
				}
				rest = path.substring(protocol.length() + 2);
				obj = getClassManager().parse(tc, protocol, rest, event);
				// if (true) {
				return obj;
				// }
			}
		} else {
			logger.warn("Trying to evaluate an expression that is not a tipiexpression.\n I.e. It is not in placed in curly brackets: "
							+ expression);
			Thread.dumpStack();
			return expression;
		}
		return obj;
	}

	private boolean exists(TipiComponent source, String path) {
		if (source != null) {
			try {
				Object p = source.evaluateExpression(path);
				return p != null;
			} catch (Exception ex) {
				logger.error("Error: ",ex);
				return false;
			}
		} else {
			Object p = evaluate(path, null, null);
			return p != null;
		}
	}

	public void setWaiting(boolean b) {
	}

	public void setActiveThreads(int i) {
		for (TipiActivityListener tal : myActivityListeners) {
			tal.setActiveThreads(i);
		}
	}

	public boolean isDefined(TipiComponent comp) {
		if (comp != null) {
			if (getTipiDefinition(comp.getName()) != null) {
				return true;
			} else {
				return false;
			}
		} else {
			return true;
		}
	}

	public XMLElement getComponentTree() {
		try {
			XMLElement root = new CaseSensitiveXMLElement();
			root.setName("tid");
			root.setAttribute("xmlns:xsi",
					"http://www.w3.org/2001/XMLSchema-instance");
			root.setAttribute("xsi:noNamespaceSchemaLocation", "tipiscript.xsd");
			root.setAttribute("errorhandler", "error");
			// Set<String> s = new TreeSet<String>(includeList);
			// Iterator<String> iter = s.iterator();
			// while (iter.hasNext()) {
			// // for (int j = 0; j < s.size(); j++) {
			// String location = iter.next();
			// XMLElement inc = new CaseSensitiveXMLElement();
			// inc.setName("tipi-include");
			// inc.setAttribute("location", location);
			// root.addChild(inc);
			// }
			// if (clientConfig != null) {
			// root.addChild(clientConfig);
			// }
			Iterator<String> it = tipiComponentMap.keySet().iterator();
			while (it.hasNext()) {
				String name = it.next();
				XMLElement current = tipiComponentMap.get(name);
				boolean se = current.getAttribute("studioelement") != null;
				if (!se) {
					root.addChild(current);
				}
			}
			return root;
		} catch (Exception e) {
			logger.error("Error: ",e);
			return null;
		}
	}

	public void deleteDefinition(String definition) {
		tipiComponentMap.remove(definition);
	}

	public void replaceDefinition(XMLElement xe) {
		String name = xe.getStringAttribute("name");
		tipiComponentMap.put(name, xe);
	}


	public void performAction(final TipiEvent te,
			TipiExecutable parentExecutable, TipiEventListener listener) {

		// te.setRootCause(parentExecutable);
		debugLog("event   ", "enqueueing async event: " + te.getEventName());
		if (myThreadPool == null) {
			myThreadPool = new TipiThreadPool(this, getPoolSize());
		}
		try {
			if (parentExecutable != null) {
				te.getStackElement().setRootCause(
						parentExecutable.getStackElement());
			}
			myThreadPool.performAction(te, listener);
		} catch (TipiException e) {
			logger.error("Error: ",e);
			showInternalError("Error performing action: " + te.getEventName()
					+ " on component: " + te.getComponent().getPath(), e);
		} catch (TipiBreakException e) {
			logger.error("Error: ",e);
			showInternalError("Error performing action: " + te.getEventName()
					+ " on component: " + te.getComponent().getPath(), e);
		} catch (Throwable e) {
			logger.error("Error: ",e);
			showInternalError(
					"Severe error performing action: " + te.getEventName()
							+ " on component: " + te.getComponent().getPath(),
					e);

		}
	}

	public void threadStarted(Thread workThread) {
	}

	public void threadEnded(Thread workThread) {
	}

	public void loadServerSettingsFromProperties() {
		String impl = System.getProperty("tipi.client.impl");
		if ("direct".equals(impl)) {
			logger.info("********* FOR NOW: Only supports indirect client *******");
		}
		String navajoServerProperty = System.getProperty("tipi.client.server");
		String navajoUsernameProperty = System
				.getProperty("tipi.client.username");
		String navajoPasswordProperty = System
				.getProperty("tipi.client.password");
		navajoServer = navajoServerProperty;
		navajoPassword = navajoPasswordProperty;
		navajoUsername = navajoUsernameProperty;
		getClient().setUsername(navajoUsernameProperty);
		getClient().setPassword(navajoPasswordProperty);
		getClient().setServerUrl(navajoServerProperty);
	}

	public void addTipiActivityListener(TipiActivityListener listener) {
		myActivityListeners.add(listener);
	}

	public void removeTipiActivityListener(TipiActivityListener listener) {
		myActivityListeners.remove(listener);
	}

	public void enqueueExecutable(TipiExecutable te) throws TipiException,
			TipiBreakException {
		myThreadPool.enqueueExecutable(te);
	}

	public TipiStorageManager getStorageManager() {
		return myStorageManager;

	}

	public void setStorageManager(TipiStorageManager tsm) {
		if (tsm == null) {
			throw new IllegalArgumentException(
					"setStorageManager: Can not be null");
		}
		myStorageManager = tsm;
		myStorageManager.setContext(this);
	}

	public Navajo retrieveDocument(String id) {
		if (getStorageManager() != null) {
			try {
				return getStorageManager().getStorageDocument(id);
			} catch (TipiException e) {
				logger.error("Error: ",e);
			}
		}
		return null;
	}

	public void storeDocument(String id, Navajo n) {
		if (getStorageManager() != null) {
			try {
				getStorageManager().setStorageDocument(id, n);
			} catch (TipiException e) {
				logger.error("Error: ",e);
			}
		}
	}

	public void debugTipiComponentTree(TipiComponent c, int indent) {
		printIndent(indent, "Debugging component" + c.toString());
		printIndent(indent, "Childlist" + ((TipiComponentImpl) c).childDump());

		for (int i = 0; i < c.getChildCount(); i++) {
			TipiComponent ccc = c.getTipiComponent(i);
			debugTipiComponentTree(ccc, indent + 3);
		}
		printIndent(indent, "End of debug component: " + c.getId() + " class: "
				+ c.getClass());

	}

	protected static void printIndent(int indent, String text) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < indent; i++) {
			sb.append(" ");
		}
		logger.info(sb.toString()+ text);
	}

	public void exit() {
	}

	public void addShutdownListener(ShutdownListener sl) {
		shutdownListeners.add(sl);
	}

	public void removeShutdownListener(ShutdownListener sl) {
		shutdownListeners.remove(sl);
	}

	public void shutdown() {
		// prevent multishutdown
		if (contextShutdown) {
			logger.warn("Already shutting down. Please stand by.");
			return;
		}

		if (myThreadPool != null) {
			logger.info("Shutting down threadpool. # of poolthreads: "+getPoolSize());
			myThreadPool.shutdown();
		}
		
		for (ShutdownListener s : shutdownListeners) {
			s.contextShutdown();
		}
		contextShutdown = true;
		if(getPoolSize()>0) {
			Thread shutdownThread = new Thread("TipiShutdown") {
				public void run() {
					myThreadPool.waitForAllThreads();
					logger.info("done.");

				}
			};
			logger.info("Blocking until all threads are gone...");
			shutdownThread.start();
		}



	}

	public DescriptionProvider getDescriptionProvider() {
		if (descriptionProviderStack.isEmpty()) {
			return null;
		}
		return descriptionProviderStack.peek();
	}

	private void pushDescriptionProvider(DescriptionProvider descriptionProvider) {
		descriptionProviderStack.push(descriptionProvider);
	}

	// TODO Needs refactoring
	public void initRemoteDescriptionProvider(String context, String locale)
			throws NavajoException, ClientException {
		Navajo n = NavajoFactory.getInstance().createNavajo();
		Message m = NavajoFactory.getInstance().createMessage(n, "Description");
		n.addMessage(m);
		Property w = NavajoFactory.getInstance().createProperty(n, "Context",
				Property.STRING_PROPERTY, context, 99, "", Property.DIR_IN);
		m.addProperty(w);
		Property l = NavajoFactory.getInstance().createProperty(n, "Locale",
				Property.STRING_PROPERTY, locale, 99, "", Property.DIR_IN);
		m.addProperty(l);
		Navajo res = getClient().doSimpleSend(n,
				"navajo/description/ProcessGetContextResources");

		Message descr = res.getMessage("Descriptions");
		DescriptionProvider descriptionProvider = new RemoteDescriptionProvider(
				this);
		pushDescriptionProvider(descriptionProvider);
		// myDescriptionProvider.init(locale, context);
		((RemoteDescriptionProvider) descriptionProvider).setMessage(descr);
	}

	public String XMLUnescape(String s) {
		if ((s == null) || (s.length() == 0)) {
			return s;
		}

		int offset;
		int next;
		String result;

		// filter out all escaped ampersands
		offset = 0;
		result = "";

		while ((next = s.indexOf("&amp;", offset)) >= 0) {
			result += s.substring(offset, next) + "&";
			offset = next + "&amp;".length();
		}

		result += s.substring(offset, s.length()); // characters after last &
		s = result;

		// filter out all escaped double quotes
		offset = 0;
		result = "";

		while ((next = s.indexOf("&quot;", offset)) >= 0) {
			result += s.substring(offset, next) + "\"";
			offset = next + "&quot;".length();
		}

		result += s.substring(offset, s.length()); // characters after last "
		s = result;

		// filter out all escaped single quotes
		offset = 0;
		result = "";

		while ((next = s.indexOf("&apos;", offset)) >= 0) {
			result += s.substring(offset, next) + "\'";
			offset = next + "&apos;".length();
		}

		result += s.substring(offset, s.length()); // characters after last "
		s = result;

		// filter out all escaped less than characters
		offset = 0;
		result = "";

		while ((next = s.indexOf("&lt;", offset)) >= 0) {
			result += s.substring(offset, next) + "<";
			offset = next + "&lt;".length();
		}

		result += s.substring(offset, s.length()); // characters after last <
		s = result;

		// filter out all escaped greater than characters
		offset = 0;
		result = "";

		while ((next = s.indexOf("&gt;", offset)) >= 0) {
			result += s.substring(offset, next) + ">";
			offset = next + "&gt;".length();
		}

		result += s.substring(offset, s.length()); // characters after last >
		s = result;

		// filter out all escaped newlines
		offset = 0;
		result = "";

		while ((next = s.indexOf("\\n", offset)) >= 0) {
			result += s.substring(offset, next) + "\n";
			offset = next + "\\n".length();
		}

		result += s.substring(offset, s.length()); // characters after last
		// newline

		// filter out all escaped ;'s
		offset = 0;
		result = "";

		while ((next = s.indexOf("\\;", offset)) >= 0) {
			result += s.substring(offset, next) + ";";
			offset = next + "\\;".length();
		}

		result += s.substring(offset, s.length()); // characters after last
		// newline

		return result;
	}

	public URL getBinaryUrl(Binary b) {
		String url = getClient().getServerUrl();
		URL u;
		try {
			u = new URL(url + "?GetBinary=true&handle=" + b.getHandle());
			return u;
		} catch (MalformedURLException e) {
			logger.error("Error: ",e);
			return null;
		}
	}

	/**
	 * Shows an error popup, can be overridden separately
	 * 
	 * @param text
	 * @param title
	 */
	public void showError(final String text, final String title) {
		showInfo(text, title);
	}

	/**
	 * Shows an warning popup, can be overridden separately
	 * 
	 * @param text
	 * @param title
	 */
	public void showWarning(String text, String title) {
		showInfo(text, title);
	}

	public abstract void showInfo(final String text, final String title);

	public abstract void showQuestion(final String text, final String title,
			String[] options) throws TipiBreakException;

	public String generateComponentId(TipiComponent parent,
			TipiComponent component) {
		if (component == null || component == parent) {
			return component.getClass().getName() + "@" + Math.random();
		}
		return component.getClass().getName() + "@" + component.hashCode();

	}

	public void setGenericResourceLoader(String resourceCodeBase)
			throws MalformedURLException {
		if (resourceCodeBase != null) {
			if (resourceCodeBase.indexOf("http:/") != -1
					|| resourceCodeBase.indexOf("file:/") != -1) {
				setGenericResourceLoader(new HttpResourceLoader(
						resourceCodeBase));
			} else {
				File res = new File(resourceCodeBase);
				setGenericResourceLoader(new FileResourceLoader(res));
			}
		} else {
			// BEWARE: The trailing slash is important!
			setGenericResourceLoader(createDefaultResourceLoader("resource/",
					useCache()));
		}
	}

	public void setTipiResourceLoader(String tipiCodeBase)
			throws MalformedURLException {
		if (tipiCodeBase != null) {
			if (tipiCodeBase.indexOf("http:/") != -1
					|| tipiCodeBase.indexOf("file:/") != -1) {
				setTipiResourceLoader(new HttpResourceLoader(tipiCodeBase));
			} else {
				setTipiResourceLoader(new FileResourceLoader(new File(
						tipiCodeBase)));
			}
		} else {
			// nothing supplied. Use a file loader with fallback to classloader.
			// BEWARE: The trailing slash is important!

			setTipiResourceLoader(createDefaultResourceLoader("tipi/",
					useCache()));
		}
	}

	public boolean useCache() {
		return false;
	}

	// private TipiResourceLoader createHttpResourceLoader(String codebase)
	// throws MalformedURLException {
	// if (getCacheDir()==null ) {
	// return new HttpResourceLoader(codebase);
	//
	// } else {
	// return new CachedHttpResourceLoader(getCacheDir(),new URL(codebase));
	// }
	// }
	//
	// private File getCacheDir() {
	// return new File("/Users/frank/tipicache");
	// }

	/**
	 * @return
	 */
	protected TipiResourceLoader createDefaultResourceLoader(String loaderType,
			boolean cache) {
		return new FileResourceLoader(new File(loaderType));
	}

	public OutputStream writeTipiResource(String resourceName)
			throws IOException {
		logger.info("Writing tipiResource: " + resourceName);
		return tipiResourceLoader.writeResource(resourceName);
	}

	public List<File> getAllTipiResources() throws IOException {
		return tipiResourceLoader.getAllResources();
	}

	public void processProperties(Map<String, String> properties)
			throws MalformedURLException {
		for (Iterator<String> iter = properties.keySet().iterator(); iter
				.hasNext();) {
			String element = iter.next();
			String value = properties.get(element);

			if (element.startsWith("-D")) {
				element = element.substring(2);
			}
			setSystemProperty(element, value);
		}
		String tipiCodeBase = properties.get("tipiCodeBase");
		String resourceCodeBase = properties.get("resourceCodeBase");
		setTipiResourceLoader(tipiCodeBase);
		setGenericResourceLoader(resourceCodeBase);

		eHandler = new BaseTipiErrorHandler();
		eHandler.setContext(this);
		eHandler.initResource();

//		try {
//			Class<?> c = Class
//					.forName("com.dexels.navajo.tipi.tools.TipiXSDBuilder");
//			TipiContextAdapter tca = (TipiContextAdapter) c.newInstance();
//			tca.execute(this);
//			logger.info("xsd builder loaded");
//		} catch (Throwable e) {
//			logger.error("Error loading XSD?",e);
//		}

	}

	public void fireTipiContextEvent(TipiComponent source, String type,
			Map<String, Object> event, boolean sync) {
		// link to
		for (TipiEventReporter te : tipiEventReporterList) {
			te.tipiEventReported(source, type, event, sync);
		}

	}

	public void fireTipiStructureChanged(TipiComponent tc) {
		// do nothing
	}

	public void addDefinitionListener(TipiDefinitionListener te) {
		tipiDefinitionListeners.add(te);
	}

	public void removeDefinitionListener(TipiDefinitionListener te) {
		tipiDefinitionListeners.remove(te);
	}

	public void fireDefinitionLoaded(String name, XMLElement definition) {
		for (TipiDefinitionListener tdl : tipiDefinitionListeners) {
			tdl.definitionLoaded(name, definition);
		}
	}

	public void addTipiEventReporter(TipiEventReporter te) {
		tipiEventReporterList.add(te);
	}

	public void removeTipiEventReporter(TipiEventReporter te) {
		tipiEventReporterList.remove(te);
	}

	public Navajo getStateNavajo() {
		if (stateNavajo == null) {
			stateNavajo = NavajoFactory.getInstance().createNavajo();
			Header h = NavajoFactory.getInstance().createHeader(stateNavajo,
					"StateNavajo", "unknown", "unknown", -1);
			stateNavajo.addHeader(h);
		}
		return stateNavajo;
	}

	public Navajo createComponentNavajo() throws NavajoException, TipiException {

		Navajo n = NavajoFactory.getInstance().createNavajo();
		Message tipiClasses = NavajoFactory.getInstance().createMessage(n,
				"TipiClass", Message.MSG_TYPE_ARRAY);
		n.addMessage(tipiClasses);
		for (String s : getClassManager().getClassNameSet()) {
			XMLElement xx = getClassManager().getAssembledClassDef(s);
			String type = xx.getStringAttribute("type");
			if (xx.getName().equals("tipiclass")
					&& ("tipi".equals(type) || "component".equals(type))) {
				Message element = NavajoFactory.getInstance().createMessage(n,
						"TipiClass", Message.MSG_TYPE_ARRAY_ELEMENT);
				tipiClasses.addMessage(element);
				Property nameProp = NavajoFactory.getInstance().createProperty(
						n, "Name", Property.STRING_PROPERTY,
						xx.getStringAttribute("name"), 0, "", Property.DIR_IN);
				element.addProperty(nameProp);
				Property packageProp = NavajoFactory.getInstance()
						.createProperty(n, "Package", Property.STRING_PROPERTY,
								xx.getStringAttribute("package"), 0, "",
								Property.DIR_IN);
				element.addProperty(packageProp);
				Property classProp = NavajoFactory.getInstance()
						.createProperty(n, "Class", Property.STRING_PROPERTY,
								xx.getStringAttribute("class"), 0, "",
								Property.DIR_IN);
				element.addProperty(classProp);
				Property moduleProp = NavajoFactory.getInstance()
						.createProperty(n, "Module", Property.STRING_PROPERTY,
								xx.getStringAttribute("module"), 0, "",
								Property.DIR_IN);
				element.addProperty(moduleProp);
			}
		}

		return n;
	}

	
	public void link(final Property master, final Property slave) {

		if (master == slave) {
			try {
				logger.info("F@#$ing hell! You are linking a property to itself! "
								+ master.getFullPropertyName());
			} catch (NavajoException e) {
				logger.error("Error: ",e);
			}
		}
		copyPropertyValue(master, slave);

		doLink(master, slave);
		doLink(slave, master);
	}

	public void unlink(Navajo n) throws NavajoException {
		for (Message current : n.getAllMessages()) {
			unlink(n, current);
		}
	}

	public void unlink(Navajo n, Message m) throws NavajoException {
		for (Property p : m.getAllProperties()) {
			unlink(n, p);
		}
		for (Message current : m.getAllMessages()) {
			unlink(n, current);
		}
	}

	public void unlink(Navajo rootDoc, Property master) {
		if (rootDoc == null) {

			return;
		}
		// BasePropertyImpl ppp = (BasePropertyImpl) master;

		String service = rootDoc.getHeader().getRPCName();
		List<PropertyChangeListener> pref;
		try {
			pref = propertyBindMap.get(service + ":"
					+ master.getFullPropertyName());
		} catch (NavajoException e) {
			logger.error("Error: ",e);
			return;
		}
		propertyBindMap.remove(master);
		if (pref != null) {
			for (PropertyChangeListener propertyChangeListener : pref) {
				master.removePropertyChangeListener(propertyChangeListener);
			}
			pref.clear();
		}

		// propertyBindMap.clear();
		List<Property> p = propertyLinkRegistry.get(master);
		propertyLinkRegistry.remove(master);
		if (p != null) {
			for (Property property : p) {
				unlink(rootDoc, property);
				List<Property> q = propertyLinkRegistry.get(property);
				//
				if (q != null) {
					q.remove(master);
					if (q.isEmpty()) {
						propertyLinkRegistry.remove(property);
					}
				}
			}
		}
	}

	private void doLink(final Property master, final Property slave) {

		PropertyChangeListener propertyChangeListener = new SerializablePropertyChangeListener() {
			private static final long serialVersionUID = -43753977302262498L;

			public void propertyChange(PropertyChangeEvent e) {
				Property masterSource = (Property) e.getSource();
				copyPropertyValue(masterSource, slave);
			}

			public String toString() {
				try {
					Navajo rootDoc = slave.getRootDoc();
					Header header = rootDoc.getHeader();
					// TODO Prevent null headers!
					String navajo = header.getRPCName();
					return navajo + ":" + slave.getFullPropertyName();
				} catch (NavajoException e) {
					logger.error("Error: ",e);
					return e.getMessage();
				}
			}

			public boolean equals(Object a) {
				// TODO THis is maybe a bit... too short through the bend
				// Properties with the same path may be still different
				// (belonging to another navajo)
				if (a instanceof PropertyChangeListener) {
					return a.toString().equals(toString());
				}
				return super.equals(a);
			}

		};
		master.addPropertyChangeListener(propertyChangeListener);
		registerPropertyLink(master, slave, propertyChangeListener);

	}

	protected void registerPropertyLink(Property master, Property slave,
			PropertyChangeListener pp) {
		List<Property> p = propertyLinkRegistry.get(master);
		if (p == null) {
			p = new LinkedList<Property>();
			propertyLinkRegistry.put(master, p);
		}
		p.add(slave);

		List<PropertyChangeListener> xx = propertyBindMap.get(master);
		Navajo rootDoc = master.getRootDoc();
		String service = rootDoc.getHeader().getRPCName();
		if (xx == null) {
			xx = new LinkedList<PropertyChangeListener>();
			try {
				propertyBindMap.put(
						service + ":" + master.getFullPropertyName(), xx);
			} catch (NavajoException e1) {
				e1.printStackTrace();
			}
		}
		// TODO beware, equality depends on equal property paths
		if (!xx.contains(pp)) {
			xx.add(pp);
		}
	}

	// TODO: Add support for other attributes?
	// type,cardinality,description,length,direction
	private void copyPropertyValue(final Property master, Property slave) {
		Object masterValue = master.getTypedValue();
		Object slaveValue = slave.getTypedValue();
		if (slaveValue != null && masterValue == null) {
			slave.setAnyValue(null);
		}
		if (slaveValue == null && masterValue != null) {
			slave.setAnyValue(masterValue);
		}
		if (slaveValue != null && masterValue != null) {
			if (!slaveValue.equals(masterValue)) {
				if (slave.getType().equals(Property.EXPRESSION_PROPERTY)) {
					logger.info("Ignoring link for expression property");
				} else {
					logger.info("Link: " + slaveValue + " to: "
							+ masterValue);
					slave.setAnyValue(masterValue);
				}
			}
		}
	}

	public void animateProperty(Property p, int duration, Object target) {
		p.setAnyValue(target);
	}

	public Navajo createNavajoListNavajo() throws NavajoException {
		Navajo n = NavajoFactory.getInstance().createNavajo();
		n.addHeader(NavajoFactory.getInstance().createHeader(n,
				"NavajoListNavajo", "", "", -1));
		Message tipiClasses = NavajoFactory.getInstance().createMessage(n,
				"Navajo", Message.MSG_TYPE_ARRAY);
		n.addHeader(NavajoFactory.getInstance().createHeader(n,
				"NavajoListNavajo", "", "", -1));
		n.addMessage(tipiClasses);
		for (String s : navajoMap.keySet()) {

			if (!s.equals("StateNavajo") && !s.equals("NavajoView")) {
				Message element = NavajoFactory.getInstance().createMessage(n,
						"Name", Message.MSG_TYPE_ARRAY_ELEMENT);
				tipiClasses.addMessage(element);
				Property nameProp = NavajoFactory.getInstance().createProperty(
						n, "Name", Property.STRING_PROPERTY, s, 0, "",
						Property.DIR_OUT);
				element.addProperty(nameProp);
			}

		}

		return n;
	}

	public void doActions(TipiEvent te, TipiComponent comp,
			TipiExecutable executableParent, List<TipiExecutable> exe)
			throws TipiBreakException, TipiSuspendException {
		try {
			int i = 0;
			for (TipiExecutable current : exe) {
				executableParent.setExecutionIndex(i);
				current.performAction(te, executableParent, i);
				i++;
			}

		} catch (TipiException ex) {
			logger.error("Error: ",ex);
		}
	}

//	public void continueActions(TipiEvent te, TipiComponent comp,
//			TipiExecutable executableParent, List<TipiExecutable> exe)
//			throws TipiBreakException, TipiSuspendException {
//		try {
//			int start = executableParent.getExecutionIndex();
//			for (int i = start; i<exe.size(); i++) {
//				executableParent.setExecutionIndex(i);
//				TipiExecutable current = exe.get(i);
//				current.continueAction(te, executableParent, i);
//				i++;
//			}
//		} catch (TipiException ex) {
//			logger.error("Error: ",ex);
//		}
//	}
	/**
	 * Parses an connector instance
	 * 
	 * @param child
	 * @throws TipiException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */

	public TipiConnector getConnector(String id) {
		TipiConnector tipiConnector = tipiConnectorMap.get(id);
		return tipiConnector;
	}

	public TipiConnector getDefaultConnector() {
		if (defaultConnector == null) {
			defaultConnector = new HttpNavajoConnector();
			defaultConnector.setContext(this);
		}

		return defaultConnector;
	}

	private void registerConnector(TipiConnector tc) {
		tipiConnectorMap.put(tc.getConnectorId(), tc);
	}

	public boolean hasErrors(Navajo result) {
		if (result == null) {
			return false;
		}
		if (result.getMessage("error") != null) {
			return true;
		}
		if (result.getMessage("ConditionErrors") != null) {
			return true;
		}
		if (result.getMessage("AuthenticationError") != null) {
			return true;
		}
		
		return false;
	}

	public void execute(Runnable e) throws TipiException {
		TipiAnonymousAction taa = new TipiAnonymousAction(e);
		myThreadPool.enqueueExecutable(taa);
	}

	public void injectNavajo(String service, Navajo n)
			throws TipiBreakException {

		Navajo old = getNavajo(service);
		if (old != null && old != n) {
			try {
				unloadNavajo(old, service);
			} catch (NavajoException e) {
				logger.error("Error: ",e);
			}
		}
		if (n.getHeader() != null) {
			n.removeHeader();
		}

		if (n.getHeader() == null) {
			Header h = NavajoFactory.getInstance().createHeader(n, service,
					"unknown", "unknown", -1);
			n.addHeader(h);
		}
		addNavajo(service, n);
		loadNavajo(n, service);
	}

	public TipiContext getParentContext() {
		return myParentContext;
	}

	public void showInternalError(String errorString, Throwable t) {
		if (t != null) {
			t.printStackTrace();
		}
	}

	public final void showInternalError(String errorString) {
		showInternalError(errorString, null);
	}

	public String createExpressionUrl(String expression) throws TipiException {
		throw new TipiException("Not implemented in this implementation");
	}

	public void fireThreadStateEvent(Map<TipiThread, String> threadStateMap,
			TipiThread tt, String state, int queueSize) {
		for (ThreadActivityListener al : threadStateListenerList) {
			al.threadActivity(threadStateMap, tt, state, queueSize);
		}

	}

	public void setThreadState(String state) {
		myThreadPool.setThreadState(state);
	}

	public void addThreadStateListener(ThreadActivityListener ta) {
		threadStateListenerList.add(ta);
	}

	public void removeThreadStateListener(ThreadActivityListener ta) {
		threadStateListenerList.remove(ta);
	}

	public CookieManager getCookieManager() {
		return myCookieManager;
	}

	public void setCookieManager(CookieManager m) {
		myCookieManager = m;
	}

	public final void setCookie(String key, String value) {
		if (myCookieManager == null) {
			return;
		}
		myCookieManager.setCookie(key, value);
	}

	public final String getCookie(String key) {
		if (myCookieManager == null) {
			return null;
		}
		return myCookieManager.getCookie(key);
	}

	public TipiThreadPool getThreadPool() {
		return myThreadPool;
	}

	public String getDescription(String expression) {
		return "I am a monkey";
	}

	public ClientInterface getClient() {
		return clientInterface;
	}

	public void showFatalStartupError(String message) {
		logger.error("Error starting up: " + message);
	}

	public ScriptEngine getScriptingEngine(String engine) {
		if (scriptManager == null) {
			scriptManager = new javax.script.ScriptEngineManager();
		}
		return scriptManager.getEngineByName(engine);
	}
	
//	public void setBundleContext(BundleContext context) {
//		this.bundleContext  = context;
//	}
	public void setClassManager(IClassManager classManager) {
		this.classManager = classManager;
		
	}


//	public void setOSGiMode(boolean b) {
//		this.osgiMode = b;
//	}

}