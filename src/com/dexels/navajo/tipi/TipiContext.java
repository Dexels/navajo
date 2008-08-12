package com.dexels.navajo.tipi;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.Map.*;

import javax.imageio.spi.*;

import tipi.*;

import com.dexels.navajo.client.*;
import com.dexels.navajo.document.*;
import com.dexels.navajo.document.types.*;
import com.dexels.navajo.parser.*;
import com.dexels.navajo.tipi.actions.*;
import com.dexels.navajo.tipi.components.core.*;
import com.dexels.navajo.tipi.connectors.*;
import com.dexels.navajo.tipi.extension.*;
import com.dexels.navajo.tipi.internal.*;
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
public abstract class TipiContext implements ActivityController, TypeFormatter {

	
	
    public abstract void runSyncInEventThread(Runnable r);
    public abstract void runAsyncInEventThread(Runnable r);

    public abstract void setCookie(String key, String value);
    public abstract String getCookie(String key);
    
	/**
	 * Maps a service to a list of datacomponents. Components register here by
	 * having a service tag
	 */
	protected final Map<String, List<TipiDataComponent>> tipiInstanceMap = new HashMap<String, List<TipiDataComponent>>();

	/**
	 * Containes a pre-parsed map of component definitions, is used less and
	 * less in favor of lazy loading
	 */
	protected final Map<String, XMLElement> tipiComponentMap = new HashMap<String, XMLElement>();

	/**
	 * Maps component types to their actual class. Could be refactored to be
	 * done on demand
	 */
	protected final Map<String, Class<?>> tipiClassMap = new HashMap<String, Class<?>>();

	/*
	 * Maps component type definitions
	 */
	protected final Map<String, XMLElement> tipiClassDefMap = new HashMap<String, XMLElement>();
	// protected final Map tipiActionDefMap = new HashMap();

	private boolean contextShutdown = false;
	protected boolean fakeJars = false;
	protected final Map<String, String> lazyMap = new HashMap<String, String>();
	protected final List<String> includeList = new ArrayList<String>();
	protected TipiErrorHandler eHandler;
	protected String errorHandler;

	/**
	 * Lists the toplevel components in the current implementation
	 */
	protected final ArrayList<Object> rootPaneList = new ArrayList<Object>();
	private final TipiActionManager myActionManager = new TipiActionManager();

	protected final List<TipiActivityListener> myActivityListeners = new ArrayList<TipiActivityListener>();
	private final List<ActivityController> activityListenerList = new ArrayList<ActivityController>();

	private final List<TipiNavajoListener> navajoListenerList = new ArrayList<TipiNavajoListener>();
	private final List<TipiNavajoListener> eventListenerList = new ArrayList<TipiNavajoListener>();

	protected final HashMap clientConfigMap = new HashMap();

	protected final Map<String, Navajo> navajoMap = new HashMap<String, Navajo>();

	protected TipiThreadPool myThreadPool;
	protected TipiComponent topScreen = null;
	// protected List myThreadsToServer = new ArrayList();
	// protected int maxToServer = 1;
	protected int poolSize = 4;
	// protected boolean singleThread = true;
	// private String currentDefinition = null;
	private final Map<String, TipiTypeParser> parserInstanceMap = new HashMap<String, TipiTypeParser>();
	// private final Map resourceReferenceMap = new HashMap();
	//
	// private final List resourceReferenceList = new ArrayList();

	protected TipiStorageManager myStorageManager = null;

	// private final List packageList = new ArrayList();
	// private final Map packageMap = new HashMap();
	// private final List packageReferenceList = new ArrayList();
	// private final Map packageReferenceMap = new HashMap();

	protected DescriptionProvider myDescriptionProvider = null;

	protected final Map<String,Object> globalMap = new HashMap<String,Object>();

	protected final long startTime = System.currentTimeMillis();

	protected File resourceBaseDirectory = null;
	protected File tipiBaseDirectory = null;

	protected boolean allowLazyIncludes = true;
//	private boolean isSwitching = false;
	private ClassLoader tipiClassLoader = null;
	private ClassLoader resourceClassLoader = null;
	private final List<ShutdownListener> shutdownListeners = new ArrayList<ShutdownListener>();

	private TipiResourceLoader tipiResourceLoader;
	private TipiResourceLoader genericResourceLoader;

	private final Map<String, String> systemPropertyMap = new HashMap<String, String>();
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
	private final Map<Property,List<Property>> propertyLinkRegistry = new HashMap<Property,List<Property>>();

	private final Map<String, TipiConnector> tipiConnectorMap = new HashMap<String, TipiConnector>();
	private TipiConnector defaultConnector;

	private final Map<String, String> argumentMap = new HashMap<String, String>();

	
	private boolean hasDebugger;
	
	private final Map<String, List<PropertyChangeListener>> propertyBindMap = new HashMap<String, List<PropertyChangeListener>>();

	private String resourceCodeBase;
	private String tipiCodeBase;

	private TipiContext myParentContext;

	private final List<TipiDefinitionListener> tipiDefinitionListeners = new LinkedList<TipiDefinitionListener>();

	public TipiContext(TipiContext parent) {
		this();
		myParentContext = parent;
	}
	public TipiContext() {
		Iterator<TipiExtension> tt = ServiceRegistry.lookupProviders(TipiExtension.class);
		initializeExtensions(tt);
//		getClassLoader().getResources
	
//		System.err.println("FORCING FAKE MODE!!! BEWARE!!!!!!!!!!");
		if (coreExtensionList.isEmpty()) {
			System.err.println("Beware: no extensions. Running without jars? Entering fake mode...");
			fakeJars = true;
			fakeExtensions();
		}
		System.err.println("extensions loaded");
		NavajoFactory.getInstance().setExpressionEvaluator(new DefaultExpressionEvaluator());
		tipiResourceLoader = new ClassPathResourceLoader();
		setStorageManager(new TipiNullStorageManager());
		 try {
			Class c = Class.forName("com.dexels.navajo.tipi.tools.TipiSocketDebugger");
			hasDebugger = true;
			System.err.println("debugger loaded");
		} catch (Throwable e) {
			System.err.println("Starting without development environment");
			hasDebugger = false;
		}


	}

	private void fakeExtensions() {
			coreExtensionList.add(new TipiCore());
			fakeExtension(mainExtensionList,"tipi.TipiSwingExtension");
			fakeExtension(optionalExtensionList,"tipi.TipiDevelopTools");
			fakeExtension(optionalExtensionList,"tipi.TipiJabberExtension");
			fakeExtension(optionalExtensionList,"tipi.TipiRssExtension");
			fakeExtension(optionalExtensionList,"tipi.TipiMailExtension");
			fakeExtension(optionalExtensionList,"tipi.TipiNativeSwingExtension");
			fakeExtension(optionalExtensionList,"tipi.TipiSubstanceExtension");
			fakeExtension(optionalExtensionList,"tipi.TipiDevelopTools");
			fakeExtension(optionalExtensionList,"tipi.TipiGoogleExtension");
			fakeExtension(optionalExtensionList,"tipi.TipiYoutubeExtension");
			fakeExtension(optionalExtensionList,"tipi.TipiFlickrExtension");
			fakeExtension(optionalExtensionList,"tipi.TipiSwingXExtension");
			fakeExtension(optionalExtensionList,"tipi.TipiGeoSwingExtension");
			fakeExtension(optionalExtensionList,"tipi.TipiBatikExtension");
			fakeExtension(optionalExtensionList,"tipi.NavajoRichTipiExtension");
			fakeExtension(optionalExtensionList,"tipi.TipiCalendarExtension");
			

				// initialize again
			appendIncludes(coreExtensionList, includeList);
			appendIncludes(mainExtensionList, includeList);
			appendIncludes(optionalExtensionList, includeList);
			processRequiredIncludes();
	}

	private void fakeExtension(List<TipiExtension> extensionList, String extensionName) {
		try {
			System.err.println("Attempting to load extension: "+extensionName);
			TipiExtension tipiExtension = (TipiExtension) Class.forName(extensionName).newInstance();
			checkExtension(tipiExtension,extensionList);
			extensionList.add(tipiExtension);
			tipiExtension.initialize(this);
		} catch (Exception e) {
			System.err.println("Could not load extension: "+extensionName+" message: "+e.getMessage());
			if (e instanceof ClassNotFoundException) {
				System.err.println("Just not found.");
			} else {
				System.err.println("Something else: ");
				e.printStackTrace();
			}

		}	
	}

	private void initializeExtensions(Iterator<TipiExtension> tt) {
		coreExtensionList = new ArrayList<TipiExtension>();
		mainExtensionList = new ArrayList<TipiExtension>();
		optionalExtensionList = new ArrayList<TipiExtension>();
		// List<String> includeList = new ArrayList<String>();
		int count = 0;
		while (tt.hasNext()) {
			TipiExtension element = tt.next();
			extensionMap.put(element.getId(), element);
			System.err.println("Description: " + element.getDescription());
			count++;
			if (element.requiresMainImplementation() == null) {
				coreExtensionList.add(element);
				continue;
			}
			if (element.isMainImplementation()) {
				mainExtensionList.add(element);
			} else {
				optionalExtensionList.add(element);
			}
			// initialize the extension (whatever it may do)
			element.initialize(this);
		}
		System.err.println("Total # of extensions: "+count);
		List<TipiExtension> allExtensions = new LinkedList<TipiExtension>();
		allExtensions.addAll(mainExtensionList);
		allExtensions.addAll(coreExtensionList);
		allExtensions.addAll(optionalExtensionList);
		checkExtensions(allExtensions);
		
		appendIncludes(coreExtensionList, includeList);
		appendIncludes(mainExtensionList, includeList);
		appendIncludes(optionalExtensionList, includeList);
		for (String element : includeList) {
			System.err.println("Include: " + element);
		}
			processRequiredIncludes();

	}

	private void checkExtensions(List<TipiExtension> e) {
		for (TipiExtension tipiExtension : e) {
			checkExtension(tipiExtension,e);
		}
	}

	private void checkExtension(TipiExtension tipiExtension,List<TipiExtension> allExtension) {
		System.err.println("Checking: "+tipiExtension.getId()+" : "+tipiExtension.getDescription());
		String main = tipiExtension.requiresMainImplementation();
		List<String> extensions = tipiExtension.getRequiredExtensions();
		System.err.println("NEEDS: "+main+" ext: "+extensions);
	}

	
	private void appendIncludes(List<TipiExtension> extensionList, List<String> includes) {
		for (TipiExtension tipiExtension : extensionList) {
			if (tipiExtension.getIncludes() == null) {
				System.err.println("Warning: extension: " + tipiExtension.getId() + " - " + tipiExtension.getDescription()
						+ " has no includes!");
				return;
			}
			String[] ss = tipiExtension.getIncludes();
			for (int i = 0; i < ss.length; i++) {
				includes.add(ss[i]);
			}
		}

	}

	protected void clearLogFile() {
	}

	// public void setResourceClassLoader(ClassLoader c) {
	// resourceClassLoader = c;
	// }

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
	}

	public void handleException(Exception e) {
		if (eHandler != null) {
			eHandler.showError(e);
		}
	}

	public int getPoolSize() {
		return poolSize;
	}

	public void setClassLoader(ClassLoader cl) {
		tipiClassLoader = cl;
	}

	public ClassLoader getClassLoader() {
		if (tipiClassLoader != null) {
			return tipiClassLoader;
		} else {
			return getClass().getClassLoader();
		}
	}

	public Object getGlobalValue(String name) {
		return globalMap.get(name);
	}

	public void setGlobalValue(String name, Object value) {
		globalMap.put(name, value);
	}

	public abstract void setSplash(Object s);

	// public void setToplevel(RootPaneContainer tl) {
	// myTopLevel = tl;
	// }
	public void parseFile(File location, boolean studioMode, String dir) throws IOException, XMLParseException, TipiException {
		parseStream(new FileInputStream(location), dir, studioMode);
	}

	public void parseURL(URL location, boolean studioMode) throws IOException, XMLParseException, TipiException {
		try {
			parseStream(location.openStream(), location.toString(), studioMode);
		} catch (IOException e) {
			throw new TipiException("Can not resolve URL: " + location + " or other IO error.", e);
		}
	}

	public void parseURL(URL location, boolean studioMode, String definitionName) throws IOException, XMLParseException, TipiException {
		parseStream(location.openStream(), location.toString(), definitionName, studioMode);
	}

	public Map<String, XMLElement> getTipiClassDefMap() {
		return tipiClassDefMap;
	}

	// public Map getTipiDefinitionMap() {
	// return tipiComponentMap;
	// }

	protected void clearResources() {
		tipiInstanceMap.clear();
		tipiComponentMap.clear();
		tipiClassMap.clear();
		tipiClassDefMap.clear();
		clearTopScreen();
		includeList.clear();
	
		eHandler = null;
		errorHandler = null;
		rootPaneList.clear();
		Runtime runtimeObject = Runtime.getRuntime();
		runtimeObject.traceInstructions(false);
		runtimeObject.traceMethodCalls(false);
		runtimeObject.runFinalization();
		runtimeObject.gc();
	}

	public void clearLazyDefinitionCache() {
		System.err.println("Flushing lazy definition cache!");
		Set<String> iterSet = new HashSet<String>(tipiComponentMap.keySet());
		for (Iterator<String> iter = iterSet.iterator(); iter.hasNext();) {
			String definitionName = iter.next();
			XMLElement def = tipiComponentMap.get(definitionName);
			String lazyLocation = lazyMap.get(definitionName);
//			if (lazyLocation != null && def != null) {
			System.err.println("Removing: "+definitionName);
				tipiComponentMap.remove(definitionName);
//			} else {
				
//			}
		}
	}

	public abstract void clearTopScreen();



	protected final void setSystemProperty(String name, String value, boolean overwrite) {
		systemPropertyMap.put(name, value);

		try {
			if (System.getProperty(name) != null) {
				if (overwrite) {
					System.setProperty(name, value);
				}
			} else {
				System.setProperty(name, value);
			}
		} catch (SecurityException e) {
			// System.err.println("No system propery access allowed.");
		}
	}

	public final void setSystemPropertyLocal(String name, String value) {
		systemPropertyMap.put(name, value);
	}

	public final void setSystemProperty(String name, String value) {
		setSystemProperty(name, value, true);
	}

	public final String getSystemProperty(String name) {
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
			// System.err.println("No system propery access allowed.");
		}
		return value;

	}

	protected void createClient(XMLElement config) throws TipiException {
		String name = config.getStringAttribute("name");
		String impl = (String) attemptGenericEvaluate(config.getStringAttribute("impl", "'indirect'"));
		setSystemProperty("tipi.client.impl", impl, false);
		String cfg = (String) attemptGenericEvaluate(config.getStringAttribute("config", "'server.xml'"));
		setSystemProperty("tipi.client.config", cfg, false);
		Object secure = attemptGenericEvaluate(config.getStringAttribute("secure", "false"));
		boolean secureBoolean = false;
		if (secure != null && secure instanceof Boolean) {
			setSystemProperty("tipi.client.secure", "" + secure, false);
			secureBoolean = ((Boolean) secure).booleanValue();
		}

		String locale = (String) attemptGenericEvaluate(config.getStringAttribute("locale", "'en'"));
		setSystemProperty("tipi.client.locale", cfg, false);

		String sublocale = (String) attemptGenericEvaluate(config.getStringAttribute("sublocale", "''"));
		setSystemProperty("tipi.client.sublocale", cfg, false);

		Object keystore = attemptGenericEvaluate(config.getStringAttribute("keystore", ""));

		String storepass = (String) attemptGenericEvaluate(config.getStringAttribute("storepass", ""));
		setSystemProperty("tipi.client.storepass", storepass, false);
		navajoServer = (String) attemptGenericEvaluate(config.getStringAttribute("server", ""));
		setSystemProperty("tipi.client.server", navajoServer, false);
		navajoUsername = (String) attemptGenericEvaluate(config.getStringAttribute("username", ""));
		setSystemProperty("tipi.client.username", navajoUsername, false);
		navajoPassword = (String) attemptGenericEvaluate(config.getStringAttribute("password", ""));
		setSystemProperty("tipi.client.password", navajoPassword, false);

		if (!impl.equals("direct")) {
			if (impl.equals("socket")) {
				NavajoClientFactory.resetClient();
				NavajoClientFactory.createClient("com.dexels.navajo.client.NavajoSocketClient", null, null);
			} else {
				NavajoClientFactory.resetClient();
				NavajoClientFactory.createDefaultClient();
			}
			NavajoClientFactory.getClient().setServerUrl(navajoServer);
			NavajoClientFactory.getClient().setUsername(navajoUsername);
			NavajoClientFactory.getClient().setPassword(navajoPassword);
			if (name != null) {
				clientConfigMap.put(name, NavajoClientFactory.getClient());
			}
		} else {
			NavajoClientFactory.resetClient();
			// deprecated
			throw new UnsupportedOperationException("Sorry, I deprecated the direct client for tipi usage");
			// NavajoClientFactory.createClient("com.dexels.navajo.client.impl.DirectClientImpl",
			// getClass().getClassLoader().getResource(cfg));
		}

		NavajoClientFactory.getClient().setLocaleCode(locale);
		NavajoClientFactory.getClient().setSubLocaleCode(sublocale);
		if (secureBoolean) {
			if (storepass != null && keystore != null) {
				try {
					if (keystore instanceof URL) {
						NavajoClientFactory.getClient().setSecure(((URL) keystore).openStream(), storepass, true);

					} else {
						String keys = "" + keystore;
						NavajoClientFactory.getClient().setSecure(keys, storepass, true);
					}
				} catch (Exception ex) {
					ex.printStackTrace();
					throw new TipiException("Could not locate keystore: " + keystore);
				}
			}
		}
	}

	public void parseStream(InputStream in, String sourceName, boolean studioMode) throws IOException, XMLParseException, TipiException {
		parseStream(in, sourceName, sourceName, studioMode);
	}

	public void parseStream(InputStream in) throws IOException, XMLParseException, TipiException {
		XMLElement doc = new CaseSensitiveXMLElement();
		InputStreamReader isr = new InputStreamReader(in, "UTF-8");
		doc.parseFromReader(isr);
		isr.close();
		parseXMLElement(doc);
	}

	public void parseStream(InputStream in, String sourceName, String definitionName, boolean studioMode) throws IOException,
			XMLParseException, TipiException {
		XMLElement doc = new CaseSensitiveXMLElement();
		InputStreamReader isr = new InputStreamReader(in, "UTF-8");
		doc.parseFromReader(isr);
		isr.close();
		parseXMLElement(doc);
		// Class initClass = (Class) tipiClassMap.get(definitionName);
		// try {
		// if (initClass != null) {
		// TipiInitInterface tii = (TipiInitInterface) initClass.newInstance();
		// tii.init(this);
		// }
		// }
		// catch (IllegalAccessException ex) {
		// ex.printStackTrace();
		// }
		// catch (InstantiationException ex) {
		// ex.printStackTrace();
		// }
		// catch (ClassCastException ex) {
		// ex.printStackTrace();
		// }
		// System.err.println(tipiComponentMap);

		switchToDefinition(definitionName);
		if (errorHandler != null) {
			try {
				Class<TipiErrorHandler> c = (Class<TipiErrorHandler>) getTipiClass(errorHandler);
				if (c != null) {
					eHandler = c.newInstance();
					eHandler.setContext(this);
				}
			} catch (Exception e) {
				System.err.println("Error instantiating TipiErrorHandler!");
				e.printStackTrace();
			}
		}
	}

	protected void parseXMLElement(XMLElement elm) throws TipiException {
		String elmName = elm.getName();
		setSplashInfo("Loading user interface");
		if (!elmName.equals("tid")) {
			throw new TipiException("TID Rootnode not found!, found " + elmName + " instead.");
		}
		errorHandler = (String) elm.getAttribute("errorhandler", null);
		List<XMLElement> children = elm.getChildren();
		for (int i = 0; i < children.size(); i++) {
			XMLElement child = children.get(i);
			parseChild(child);
		}
	}

	/**
	 * Parses a toplevel tipi file (every child is a child of tid)
	 * 
	 * @param child
	 * @param dir
	 * @throws TipiException
	 */
	protected void parseChild(XMLElement child) throws TipiException {

		String childName = child.getName();
		if (childName.equals("client-config")) {
			// if (!"__ignore".equals(dir)) {
			createClient(child);
			// }
		}
		// if (childName.equals("tipi-config")) {
		// configureTipi(child);
		// return;
		// }
		if (childName.equals("component") || childName.equals("tipi") || childName.equals("definition")) {
			parseDefinition(child);
			return;
		}

		if (childName.equals("tipiclass")) {
			addTipiClassDefinition(child);
			return;
		}
		if (childName.equals("tipiaction")) {
			addActionDefinition(child);
			return;
		}
		if (childName.equals("tipi-include")) {
			// if (!"__ignore".equals(dir)) {
			parseLibrary(child);
			// }
			return;
		}
		if (childName.equals("tipi-parser")) {
			parseParser(child);
			return;
		}
		// if (childName.equals("tipi-resource")) {
		// parseResource(child);
		// return;
		// }
		// if (childName.equals("tipi-package")) {
		// parsePackage(child);
		// return;
		// }

		if (childName.equals("tipi-storage")) {
			parseStorage(child);
			return;
		}
		if (childName.startsWith("d.")) {
			if(child.getAttribute("name")==null) {
				throw new TipiException("Tipi definition should have a name attribute:"+child.toString());
			}
			parseDefinition(child);
			return;
		}

	}

	private void parseStorage(XMLElement child) {
		String type = child.getStringAttribute("type");
		if ("asp".equals(type)) {
			String instanceId = child.getStringAttribute("instanceId");
			String scriptPrefix = child.getStringAttribute("scriptPrefix");
			setStorageManager(new TipiGeneralAspManager(scriptPrefix, instanceId));
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
		}
		if ("null".equals(type)) {
			setStorageManager(new TipiNullStorageManager());
		}

	}

	public void parseDefinition(XMLElement child) {
		String childName = child.getName();
		if (childName.startsWith("d.")) {
			String name = child.getStringAttribute("name");
			child.setAttribute("name", name);
			addComponentDefinition(child);

		}
		if (childName.equals("tipi") || childName.equals("component") || childName.equals("definition")) {
			// THIS... IS.. SILLY!
			// if (getTipiDefinition(childName) != null) {
			// System.err.println(">>>>>>>>>>>>>>>>> SKIPPING ALREADY DEFINED: "
			// + childName);
			// } else {
			testDefinition(child);
			addComponentDefinition(child);

			// }
		}
	}


	
	private final void testDefinition(XMLElement xe) {
		if (xe.getAttribute("name") == null) {
			throw new RuntimeException("Tipi/component definition found without name at: " + xe.getLineNr());
		}
		if (xe.getAttribute("class") == null && xe.getAttribute("type") == null) {
			throw new RuntimeException("Tipi/component definition found without class at: " + xe.getLineNr());
		}
	}

	public InputStream getTipiResourceStream(String location) throws IOException {
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

		if (u == null) {
			// System.err.println("getResourceURL: "+location+" not found in
			// classpath, continuing");
		} else {
			return u;
		}

		if (genericResourceLoader != null) {
			try {
				return genericResourceLoader.getResourceURL(location);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public URL getTipiResourceURL(String location) {
		return getTipiResourceURL(location, resourceClassLoader);
	}

	public URL getTipiResourceURL(String location, ClassLoader cl) {
		if (cl == null) {
			cl = getClass().getClassLoader();
		}
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
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public final void parseLibraryFromClassPath(String location) {
		parseLibrary(location, false, null, false);
	}

	private final void parseLibrary(XMLElement lib) throws TipiException {

		String location = (String) lib.getAttribute("location");
		String lazy = (String) lib.getAttribute("lazy");
		boolean isLazy = "true".equals(lazy);
		String componentName = (String) lib.getAttribute("definition");
		if (componentName == null && isLazy) {
			throw new IllegalArgumentException("Lazy include, but no definition found. Location: " + location);
		}
		if (isLazy && getTipiDefinition(componentName) != null) {
			// already present.
			System.err.println("Not reparsing lazy: " + componentName);
			return;
		}
		parseLibrary(location, true, componentName, isLazy);
	}

	public void processRequiredIncludes() {
		for (String element : includeList) {
			try {
				parseLibrary(element, false, null, false);
			} catch (UnsupportedClassVersionError e) {
				System.err.println("Error parsing extension: " + element + " wrong java version! "+e.getCause());
				
				throw new UnsupportedClassVersionError(e.getMessage());
			}
	}
		// Thread.dumpStack();
		// for (Iterator iter = includeList.iterator(); iter.hasNext();) {
		// String element = (String) iter.next();
		// // System.err.println("Parsing element: "+element);
		// parseLibrary(element, false, null, null, false);
		// }
	}

	private final void parseLibrary(String location, boolean addToInclude, String definition, boolean isLazy) {
		if (isLazy) {
			if (definition == null) {
				throw new IllegalArgumentException("Lazy include, but no definition found. Location: " + location);
			}
			lazyMap.put(definition, location);
			return;
		}
		try {
			if (location != null) {
				InputStream in = resolveInclude(location);
				if (in == null) {
					return;
				}
				XMLElement doc = new CaseSensitiveXMLElement();
				try {
					InputStreamReader isr = new InputStreamReader(in, "UTF-8");
					doc.parseFromReader(isr);
					isr.close();
				}
				/** @todo Throw these exceptions */
				catch (XMLParseException ex) {
					showInternalError("XML parse exception while parsing file: " + location + " at line: " + ex.getLineNr(),ex);
					ex.printStackTrace();
					return;
				} catch (IOException ex) {
					System.err.println("IO exception while parsing file: " + location);
					ex.printStackTrace();
					return;
				}
				parseXMLElement(doc);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private InputStream resolveInclude(String location) {
		// first, try to resolve the include by checking the classpath:
		try {
			InputStream in = getTipiResourceStream(location);
			return in;
		} catch (IOException e) {
			// classload failed. Continuing.
		}
		return null;
	}

	public TipiActionBlock instantiateTipiActionBlock(XMLElement definition, TipiComponent parent) {
		TipiActionBlock c = createTipiActionBlockCondition();
		c.load(definition, parent);
		return c;
	}

	public TipiActionBlock instantiateDefaultTipiActionBlock(TipiComponent parent)  {
		TipiActionBlock c = createTipiActionBlockCondition();
		return c;
	}

	public TipiAction instantiateTipiAction(XMLElement definition, TipiComponent parent) throws TipiException {
		String type = (String) definition.getAttribute("type");
		if (type == null) {
			type = definition.getName();
		}
		if (type == null) {
			throw new TipiException("Undefined action type in: " + definition.toString());
		}
		return myActionManager.instantiateAction(definition, parent);
	}

	public TipiActionManager getActionManager() {
		return myActionManager;
	}

	public TipiLayout instantiateLayout(XMLElement instance, TipiComponent cc) throws TipiException {

		String type = null;
		if (instance.getName().equals("layout")) {
			type = (String) instance.getAttribute("type");
		} else {
			if (instance.getName().startsWith("l.")) {
				type = instance.getName().substring(2, instance.getName().length());
			} else {
				System.err.println("WARNING, STRANGE LAYOUT TAG: " + instance);
			}
		}
		TipiLayout tl = (TipiLayout) instantiateClass(type, null, instance);
		if (tl == null) {
			System.err.println("Null layout!!!!!!!!!!!!");
			return null;
		}
		XMLElement xx = getTipiClassDefMap().get(type);
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

	protected TipiComponent instantiateComponentByDefinition(XMLElement definition, XMLElement instance, TipiEvent event) throws TipiException {
		String clas = definition.getStringAttribute("class");
		if (clas == null) {
			clas = definition.getStringAttribute("type");
		}
		if (definition.getName().startsWith("d.")) {
			clas = definition.getName().substring(2, definition.getName().length());

		}
		String name = instance.getStringAttribute("name");
		if (name == null) {
			showInternalError("Error instantiating component: " + clas + ". No name supplied. instance: " + instance);
		}
		if (!clas.equals("")) {
			TipiComponent tc = (TipiComponent) instantiateClass(clas, name, instance);
			// System.err.println("Instantiating component by definition:
			// "+clas);
			XMLElement classDef = tipiClassDefMap.get(clas);

			/**
			 * @todo think these two can be removed, because they are invoked in
			 *       the instantiateClass method
			 */
			tc.loadEventsDefinition(this, definition, classDef);
			tc.loadMethodDefinitions(this, definition, classDef);
			// -----------------------------
			tc.loadStartValues(definition,event);
			boolean se = definition.getAttribute("studioelement") != null;
			tc.setStudioElement(se);
			tc.commitToUi();
			return tc;
		} else {
			throw new TipiException("Problems instantiating TipiComponent class: " + definition.toString());
		}
	}

	public TipiComponent reloadComponent(TipiComponent comp, XMLElement definition, XMLElement instance, TipiEvent event) throws TipiException {
		String clas = definition.getStringAttribute("class", "");
		if (clas == null || "".equals(clas)) {
			clas = definition.getStringAttribute("type", "");
		}
		if (!clas.equals("")) {
			comp.load(definition, instance, this);
			XMLElement classDef = tipiClassDefMap.get(clas);
			comp.loadEventsDefinition(this, definition, classDef);
			comp.loadMethodDefinitions(this, definition, classDef);
			comp.loadStartValues(definition,event);
			boolean se = definition.getAttribute("studioelement") != null;
			comp.setStudioElement(se);
			return comp;
		} else {
			throw new TipiException("Problems reloading TipiComponent class: " + definition.toString());
		}
	}

	public TipiComponent instantiateComponent(XMLElement instance, TipiEvent event,TipiInstantiateTipi t) throws TipiException {
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
				throw new TipiException("Definition based instance, but no definition found. Definition: " + name);
			}
			tc = instantiateComponentByDefinition(xx, instance,event);

		} else {
			// Class provided. Not instantiating from a definition, name is
			// irrelevant.
			tc = (TipiComponent) instantiateClass(clas, null, instance);
		}
		
		
		
		if (t==null) {
			tc.loadStartValues(instance,event);
		} else {
			Set<String> paramNames = t.getParameterNames();
			for (String param : paramNames) {
				if(param.equals("id") || param.equals("name") ||param.equals("class") || param.equals("location")|| param.equals("force")  ) {
					continue;
				}
				Object o = t.getEvaluatedParameterValue(param, event);
				if(param.equals("constraint")) {
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

	public void disposeTipiComponent(TipiComponent comp) {
		if (comp.getTipiParent() == null) {
			showInternalError("Can not dispose tipi: It has no parent!");
			return;
		}
		TipiComponent parent = comp.getTipiParent();
		Message pp = parent.getStateMessage();
		final Message stateMessage = comp.getStateMessage();
		pp.removeMessage(stateMessage);
		
		Runnable r = new Runnable(){

			public void run() {
				try {
					unlink(stateMessage);
				} catch (NavajoException e) {
					e.printStackTrace();
				}
					
			}};
			try {
				execute(r);
			} catch (TipiException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		parent.removeChild(comp);
		Message m = stateMessage;

//		pp.removeMessage(m);
		if (comp instanceof TipiDataComponent) {
			removeTipiInstance(comp);
		} else {
		}
		if (comp instanceof TipiConnector) {
			TipiConnector rr = (TipiConnector)comp;
			tipiConnectorMap.remove(rr.getConnectorId());
			if(defaultConnector==comp) {
				defaultConnector= null;
			}
		} else {
		}
		killComponent(comp);
		fireTipiStructureChanged(parent);
	}

	private Object instantiateClass(String className, String defname, XMLElement instance) throws TipiException {
		 
		XMLElement tipiDefinition = null;
		Class<?> c = getTipiClass(className);
		// AAAAAAP
		if (defname != null ) {
			tipiDefinition = getComponentDefinition(defname);
		}
		XMLElement classDef = tipiClassDefMap.get(className);
		if(classDef==null) {
			throw new TipiException("Error loading class def: "+className);
		}
		String componentType = classDef.getStringAttribute("type");
		if (c == null) {
			throw new TipiException("Error retrieving class definition. Looking for class: " + defname + ", classname: " + className);
		}
		Object o;
		try {
			o = c.newInstance();
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new TipiException("Error instantiating class:" + className + "/" + defname+" class: "+c.getName()
					+ ". Class may not have a public default contructor, or be abstract, or an interface");
		}
		if (TipiComponent.class.isInstance(o)) {
			TipiComponent tc = (TipiComponent) o;
			tc.setContext(this);
			if(tc.getId()==null) {
				tc.setId(generateComponentId(null,tc));
			}
			// tc.setContainer(tc.createContainer());
			tc.setPropertyComponent(classDef.getBooleanAttribute("propertycomponent", "true", "false", false));
//			tc.setStudioElement(instance.getBooleanAttribute("studioelement", "true", "false", false));
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
			
			if("connector".equals(componentType)) {
				boolean isDefaultConnector = instance.getBooleanAttribute("default", "true", "false", false);
				if(!(tc instanceof TipiConnector)) {
					showInternalError("Error: Component: "+className+" is registered as a component, but it does not implement TipiConnector!");
				} else {
					registerConnector((TipiConnector)tc);
					if(isDefaultConnector) {
						this.defaultConnector = (TipiConnector) tc;
					}
				}
			}
			
			return tc;
		} else {
			// System.err.println("Not a TIPICOMPONENT!!");
		}
		if (TipiLayout.class.isInstance(o)) {
			TipiLayout tl = (TipiLayout) o;
			tl.setContext(this);
			return tl;
		}
		throw new TipiException("INSTANTIATING UNKOWN SORT OF CLASS THING.");
	}

	public Class<?> getTipiClass(String name)  {
		Class<?> cc = tipiClassMap.get(name);
		if(cc!=null) {
			return cc;
		}
		XMLElement xe = tipiClassDefMap.get(name);
		if(xe==null) {
			return null;
		}
		String pack = (String) xe.getAttribute("package");
		String clas = (String) xe.getAttribute("class");
		String fullDef = pack + "." + clas;
//		System.err.println("Adding: "+fullDef);
		try {
			cc = Class.forName(fullDef, true, getClassLoader());
			tipiClassMap.put(name, cc);
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}
		return cc;
	}

	private final void addTipiClassDefinition(XMLElement xe) {
		String pack = (String) xe.getAttribute("package");
		String name = (String) xe.getAttribute("name");
		String clas = (String) xe.getAttribute("class");
		String fullDef = pack + "." + clas;
		setSplashInfo("Adding: " + fullDef);
//		System.err.println("Adding: "+fullDef);
//		try {
//			Class<?> c = Class.forName(fullDef, true, getClassLoader());
//			tipiClassMap.put(name, c);
			tipiClassDefMap.put(name, xe);
//		} catch (ClassNotFoundException ex) {
//			ex.printStackTrace();
			// throw new TipiException("Trouble loading class. Name: " + clas +
			// " in package: " + pack);
//		}
	}

	public Iterator<String> getTipiClassDefIterator() {
		return tipiClassDefMap.keySet().iterator();
	}

	public void addActionDefinition(XMLElement xe) throws TipiException {
		myActionManager.addAction(xe, this);
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
		Iterator<List<TipiDataComponent>> c = tipiInstanceMap.values().iterator();
		while (c.hasNext()) {
			List<TipiDataComponent> current = c.next();
			// BEWARE of the backward order
			for (int i = current.size() - 1; i >= 0; i--) {
				TipiComponent element = current.get(i);
				if(element==instance) {
					current.remove(i);
				}
//				if (instance.getPath().equals(element.getPath())) {
//					current.remove(i);
//					continue;
//				}
//				if (element.getPath().startsWith(instance.getPath() + "/")) {
//					current.remove(i);
//				}
			}
		}
	}

	/**
	 * Returns a cached tipi definition. If it is not cached, it will return null.
	 * Generally, you'll want to use getComponentDefinition(name), which will do
	 * whatever it can to locate the source.
	 * @param name
	 * @return
	 */
	public XMLElement getTipiDefinition(String name)  {
		XMLElement xe = tipiComponentMap.get(name);
		return xe;
	}

	/**
	 * Lists all instantiated components that listen to the specified service
	 * @param service
	 * @return
	 */
	public List<TipiDataComponent> getTipiInstancesByService(String service)  {
		// List<TipiDataComponent> x= tipiInstanceMap.get(service);
		return tipiInstanceMap.get(service);
	}

	public List<String> getListeningServices() {
		return new ArrayList<String>(tipiInstanceMap.keySet());
	}

	public XMLElement getComponentDefinition(String componentName)  {
		// if(lazyMap.containsKey(componentName)) {
		// String location = (String) lazyMap.get(componentName);
		// } 
		XMLElement xe = getTipiDefinition(componentName);
		if (xe != null) {
			return xe;
		}
		String location = lazyMap.get(componentName);
		if (location == null) {

			String fullName = null;
			if (componentName.indexOf(".") != -1) {
				fullName = componentName.replace(".", "/");
			} else {
				fullName = componentName;
			}
			String total = null;
			if(fullName.endsWith(".xml")) {
				total = fullName;
			} else {
				total = fullName + ".xml";
			}
			parseLibrary(total, true, componentName, false);
			xe = getTipiDefinition(componentName);
			fireDefinitionLoaded(componentName, xe);
			return xe;

			// return null;
		} else {
			parseLibrary(location, true, componentName, false);
			xe = getTipiDefinition(componentName);
			fireDefinitionLoaded(componentName, xe);
			return xe;
			
		}
	}

	protected void addComponentDefinition(XMLElement elm) {
		String defname = (String) elm.getAttribute("name");
		setSplashInfo("Loading: " + defname);
		if(defname.equals("init")) {
			
		}
		tipiComponentMap.put(defname, elm);
		
		if(!hasDebugger) {
			// debug mode, don't cache at all
			
//			tipiComponentMap.put(defname, elm);
			
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

	public void closeAll() {
		tipiComponentMap.clear();
		tipiInstanceMap.clear();
		tipiClassMap.clear();
		tipiClassDefMap.clear();
		includeList.clear();
	}

	public void switchToDefinition(String name) throws TipiException {
		clearTopScreen();
		setSplashInfo("Starting application: "+name);
		XMLElement componentDefinition = null; //
		componentDefinition = getComponentDefinition(name);
		// fallback to init:
		if(componentDefinition==null) {
			componentDefinition = getComponentDefinition("init");
		}
		if (componentDefinition == null) {
			showInternalError("TipiDefinition not found. Available definitions: " + tipiComponentMap.keySet());
			throw new TipiException("Fatal tipi error: Can not switch. Unknown definition: " + name);
		}
		componentDefinition.setAttribute("id", "init");
		TipiComponent tc = instantiateComponent(componentDefinition,null,null);
		tc.commitToUi();

		
		
		try {
			TipiExtension t = extensionMap.get("develop");
			if (t != null) {
				TipiComponent dev = instantiateComponent(getComponentDefinition("develop"),null,null);
				tc.addComponent(dev, this, null);
			}
		} catch (Throwable e) {
			e.printStackTrace();
			System.err.println("Error instantiating debug component. Continuing");
		}

		getDefaultTopLevel().addComponent(tc, this, null);
		getDefaultTopLevel().addToContainer(tc.getContainer(), null);
		try {
			getStateNavajo().addMessage(tc.getStateMessage());
		} catch (NavajoException e) {
			e.printStackTrace();
		}
		setSplashVisible(false);
		fireTipiStructureChanged(tc);
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
	public Navajo doSimpleSend(Navajo n, String service, ConditionErrorHandler ch, long expirtationInterval, String hosturl,
			String username, String password, boolean breakOnError) throws TipiBreakException {
		return doSimpleSend(n, service, ch, expirtationInterval, hosturl, username, password, null, null, breakOnError);
	}

	/**
	 * @deprecated
	 */

	@Deprecated
	public Navajo doSimpleSend(Navajo n, String service, ConditionErrorHandler ch, String clientName, boolean breakOnError)
			throws TipiBreakException {
		return doSimpleSend(n, service, ch, -1, null, null, null, null, null, breakOnError);
	}

	/**
	 * @deprecated
	 */
	@Deprecated
	public Navajo doSimpleSend(Navajo n, String service, ConditionErrorHandler ch, long expirtationInterval, String hosturl,
			String username, String password, String keystore, String keypass, boolean breakOnError) throws TipiBreakException {
		return doSimpleSend(n, service, ch, expirtationInterval, hosturl, username, password, null, null, breakOnError, null);
	}

	public void setHTTPS(String passphrase, Binary keystore) throws ClientException {
		NavajoClientFactory.getClient().setSecure(keystore.getDataAsStream(), passphrase, true);
	}

	/**
	 * @deprecated
	 */
	@Deprecated
	private Navajo doSimpleSend(Navajo n, String service, ConditionErrorHandler ch, long expirtationInterval, String hosturl,
			String username, String password, String keystore, String keypass, boolean breakOnError, String clientName)
			throws TipiBreakException {
		Navajo reply = null;
		if (myThreadPool == null) {
			myThreadPool = new TipiThreadPool(this, getPoolSize());
		}
		try {
			if (hosturl != null && !"".equals(hosturl)) {
				if ("direct".equals(NavajoClientFactory.getClient().getClientName())) {
					ClientInterface ci = NavajoClientFactory.createDefaultClient();
					ci.setServerUrl(hosturl);
					// System.err.println("Specifically sending to: "+hosturl);
					ci.setUsername(username);
					ci.setPassword(password);
					if (keystore != null && keypass != null && !"".equals(keystore)) {
						// System.err.println("Setting secure. Keystore:
						// "+keystore+" keypass: "+keypass);
						ci.setSecure(keystore, keypass, true);
					}

					reply = ci.doSimpleSend(n, service, ch, expirtationInterval);
					debugLog("data", "simpleSend to host (diverted from directclient): " + hosturl + " username: " + username
							+ " password: " + password + " method: " + service);
				} else {
					System.err.println("CONNECTING TO: " + navajoServer);
					hosturl = navajoServer;
					username = navajoUsername;
					password = navajoPassword;

					String url = NavajoClientFactory.getClient().getServerUrl();
					NavajoClientFactory.getClient().setServerUrl(hosturl);
					NavajoClientFactory.getClient().setUsername(username);
					NavajoClientFactory.getClient().setPassword(password);
					reply = NavajoClientFactory.getClient().doSimpleSend(n, service, ch, expirtationInterval);
					NavajoClientFactory.getClient().setServerUrl(url);
					debugLog("data", "simpleSend to host: " + hosturl + " username: " + username + " password: " + password + " method: "
							+ service);
				}

			} else {
				reply = NavajoClientFactory.getClient().doSimpleSend(n, service, ch, expirtationInterval);
				debugLog("data", "simpleSend client: " + NavajoClientFactory.getClient().getClientName() + " method: " + service);
			}
		} catch (Throwable ex) {
			ex.printStackTrace();
			if (eHandler != null && Exception.class.isInstance(ex)) {
				debugLog("data", "send error occurred:" + ex.getMessage() + " method: " + service);
				eHandler.showError((Exception) ex);
				if (breakOnError) {
					throw new TipiBreakException(TipiBreakException.BREAK_EVENT);
				}
			}
			ex.printStackTrace();
		}
		if (reply == null) {
			reply = NavajoFactory.getInstance().createNavajo();
		}

		return reply;
	}

	public void performTipiMethod(TipiDataComponent t, Navajo n, String tipiDestinationPath, String method, boolean breakOnError,
			TipiEvent event, long expirationInterval, String hosturl, String username, String password) throws 	TipiBreakException {
		performTipiMethod(t, n, tipiDestinationPath, method, breakOnError, event, expirationInterval, hosturl, username, password, null,
				null);
	}

	public void performTipiMethod(TipiDataComponent t, Navajo n, String tipiDestinationPath, String method, boolean breakOnError,
			TipiEvent event, long expirationInterval, String hosturl, String username, String password, String keystore, String keypass)
			throws TipiBreakException {

		ConditionErrorHandler ch = t;
		fireNavajoSent(n, method);
		Navajo reply = doSimpleSend(n, method, ch, expirationInterval, hosturl, username, password, keystore, keypass, breakOnError);
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
		if(h==null) {
			h = NavajoFactory.getInstance().createHeader(navajo, method, "unknown","unknown", -1);
			navajo.addHeader(h);
		}
		navajoMap.put(method, navajo);
	}

	public void loadNavajo(Navajo reply, String method) throws TipiBreakException {
		Header h = reply.getHeader();
		if(h==null) {
			h = NavajoFactory.getInstance().createHeader(reply, method, "unknown","unknown", -1);
			reply.addHeader(h);
		}
		loadNavajo(reply, method, "*", null, false);
		Navajo compNavajo = null;
		if(hasDebugger && !"NavajoListNavajo".equals(method)) {
			try {
				compNavajo = createNavajoListNavajo();
				loadNavajo(compNavajo, "NavajoListNavajo");
				System.err.println("Firing navajo: "+method);

			} catch (NavajoException e) {
				e.printStackTrace();
			}
			
		}
		if(compNavajo!=null) {
			fireNavajoReceived(compNavajo, "NavajoListNavajo");
		}
	}

	public void loadNavajo(Navajo reply, String method, String tipiDestinationPath, TipiEvent event, boolean breakOnError)
			throws TipiBreakException {
		if (reply != null) {
			if (eHandler != null) {
				if (eHandler.hasErrors(reply)) {
					System.err.println("Errors detected. ");
					boolean hasUserDefinedErrorHandler = false;
						List<TipiDataComponent> tipis = getTipiInstancesByService(method);
						if (tipis != null) {
							for (int i = 0; i < tipis.size(); i++) {
								TipiDataComponent current = tipis.get(i);

								if (current.hasPath(tipiDestinationPath, event)) {
									boolean hasHandler = false;
									debugLog("data    ", "delivering error from method: " + method + " to tipi: " + current.getId());
									hasHandler = current.loadErrors(reply, method);
									if (hasHandler) {
										hasUserDefinedErrorHandler = true;
									}
								}
							}
						}
					if (!hasUserDefinedErrorHandler) {
						eHandler.showError();
					}
					if (breakOnError) {
						throw new TipiBreakException(TipiBreakException.WEBSERVICE_BREAK);
					}
					return;
				}
			} else {
//				System.err.println("No error handler!");
			}
			try {
				loadTipiMethod(reply, method);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} else {
			System.err.println("Trying to load null navajo.");
		}

	}

	public void parseStudio() throws XMLParseException  {
		// do nothing
	}

	protected void storeTemplateNavajo(String method, Navajo reply) {
		// do nothing
	}

	protected void fireNavajoLoaded(String method, Navajo reply) {
		// do nothing
	}

	protected void loadTipiMethod(Navajo reply, String method) throws TipiException, NavajoException {

		Navajo oldNavajo = getNavajo(method);
	
		if(oldNavajo!=null) {
			unloadNavajo(oldNavajo,method);
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
			debugLog("data    ", "delivering data from method: " + method + " to tipi: " + t.getId());
			try {
				t.loadData(reply, method);
			} catch (TipiBreakException e) {
				System.err.println("Data refused by component");
			}
			if (t.getContainer() != null) {
				t.tipiLoaded();
			}
		}
		
		fireNavajoReceived(reply, method);
	}

	private void unloadNavajo(Navajo reply, String method) throws NavajoException {
		unlink(reply);
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

	public Operand evaluate(String expr, TipiComponent tc, TipiEvent event, Message currentMessage) {

		return evaluate(expr, tc, event, tc.getNearestNavajo(), currentMessage);
	}

	public Operand evaluate(String expr, TipiComponent tc, TipiEvent event, Navajo n, Message currentMessage) {
		Operand o = null;
		if (expr == null) {
			return null;
		}
		try {
			synchronized (tc) {
				tc.setCurrentEvent(event);
				o = Expression.evaluate(expr, n, null, currentMessage, null, tc);
				if (o == null) {
					System.err.println("Expression evaluated to null operand!");
					return null;
				}
			}
		} catch (Exception ex) {
			System.err.println("Not happy while evaluating expression: " + expr + " message: " + ex.getMessage());
			ex.printStackTrace();
			return o;
		} catch (Error ex) {
			System.err.println("Not happy while evaluating expression: " + expr + " message: " + ex.getMessage());
			ex.printStackTrace();
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

	public Object evaluateExpression(String expression, TipiComponent tc, TipiEvent event) throws Exception {
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
					throw new TipiException("Null protocol?! evaluating expression: "+expression);
				}
				rest = path.substring(protocol.length() + 2);
				obj = parse(tc, protocol, rest, event);
				// if (true) {
				return obj;
				// }
			}
		} else {
			System.err
					.println("Trying to evaluate an expression that is not a tipiexpression.\n I.e. It is not in placed in curly brackets: "
							+ expression);
			Thread.dumpStack();
			return expression;
		}
		return obj;
	}


	@SuppressWarnings("unchecked")
	private final void parseParser(XMLElement xe) {
		String name = xe.getStringAttribute("name");
		String parserClass = xe.getStringAttribute("parser");
		String classType = xe.getStringAttribute("type");
		Class<TipiTypeParser> pClass = null;
		try {
			pClass = (Class<TipiTypeParser>) Class.forName(parserClass, true, getClassLoader());
		} catch (ClassNotFoundException ex) {
			System.err.println("Error loading class for parser: " + parserClass);
			return;
		}
		TipiTypeParser ttp = null;
		try {
			ttp = pClass.newInstance();
		} catch (IllegalAccessException ex1) {
			System.err.println("Error instantiating class for parser: " + parserClass);
			ex1.printStackTrace();
			return;
		} catch (InstantiationException ex1) {
			System.err.println("Error instantiating class for parser: " + parserClass);
			ex1.printStackTrace();
			return;
		}
		ttp.setContext(this);
		try {
			Class<?> cc = Class.forName(classType, true, getClassLoader());
			ttp.setReturnType(cc);
		} catch (ClassNotFoundException ex) {
			System.err.println("Error verifying return type class for parser: " + classType);
			return;
		}
		parserInstanceMap.put(name, ttp);
	}

	public Object parse(TipiComponent source, String name, String expression, TipiEvent te) {
		TipiTypeParser ttp = parserInstanceMap.get(name);
		if (ttp == null) {
			System.err.println("Unknown type: " + name);
			return null;
		}
		Object o = ttp.parse(source, expression, te);
		Class<?> c = ttp.getReturnType();
		if (o != null && !c.isInstance(o)) {
			throw new IllegalArgumentException("Wrong type returned. Expected: " + c + "\nfound: " + o.getClass()
					+ "\nWas parsing expression: " + expression + "\nUsing parser: " + name);
		}
		return o;
	}

	public boolean isValidType(String name) {
		return parserInstanceMap.containsKey(name);
	}

	public String toString(TipiComponent source, String name, Object o) {
		TipiTypeParser ttp = parserInstanceMap.get(name);
		if (ttp == null) {
			System.err.println("Unknown type: " + name);
			return null;
		}
		Class<?> c = ttp.getReturnType();
		if (o == null) {
			return null;
		}
		if (!c.isInstance(o)) {
			throw new IllegalArgumentException("Wrong type: Need type: " + name + " (being of class: " + c.toString() + ") but found: "
					+ o.getClass());
		}
		return ttp.toString(o, source);
	}

	private boolean exists(TipiComponent source, String path) {
		if (source != null) {
			try {
				Object p = source.evaluateExpression(path);
				return p != null;
			} catch (Exception ex) {
				ex.printStackTrace();
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
			root.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
			root.setAttribute("xsi:noNamespaceSchemaLocation", "tipiscript.xsd");
			root.setAttribute("errorhandler", "error");
			Set<String> s = new TreeSet<String>(includeList);
			Iterator<String> iter = s.iterator();
			while (iter.hasNext()) {
				// for (int j = 0; j < s.size(); j++) {
				String location = iter.next();
				XMLElement inc = new CaseSensitiveXMLElement();
				inc.setName("tipi-include");
				inc.setAttribute("location", location);
				root.addChild(inc);
			}
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
			e.printStackTrace();
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

	public void storeComponentTree(String name) {
		try {
			FileWriter fw = new FileWriter(name);
			getComponentTree().write(fw);
			fw.flush();
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// EOF
	public void addActivityListener(ActivityController al) {
		activityListenerList.add(al);
	}

	public void removeActivityListener(ActivityController al) {
		activityListenerList.remove(al);
	}

	private boolean isStudioElement(TipiComponent tc) {
		if (tc.isStudioElement()) {
			return true;
		}
		return tc.getPath().indexOf("//studio") != -1;
	}

	public void performedEvent(TipiComponent tc, TipiEvent e) throws BlockActivityException {
		boolean blocked = false;
		for (ActivityController current : activityListenerList) {
			try {
				current.performedEvent(tc, e);
			} catch (BlockActivityException ex) {
				blocked = true;
			}
		}
		if (blocked && !isStudioElement(tc)) {
			throw new BlockActivityException();
		}
	}

	public void performedBlock(TipiComponent tc, TipiActionBlock tab, String expression, String exprPath, boolean passed, TipiEvent te)
			throws BlockActivityException {
		boolean blocked = false;

		for (ActivityController current : activityListenerList) {
			try {
				current.performedBlock(tc, tab, expression, exprPath, passed, te);
			} catch (BlockActivityException ex) {
				blocked = true;
			}
			if (blocked && !isStudioElement(tc)) {
				throw new BlockActivityException();
			}
		}
	}

	public void performedAction(TipiComponent tc, TipiAction ta, TipiEvent te) throws BlockActivityException {
		boolean blocked = false;
		for (ActivityController current : activityListenerList) {
			try {
				current.performedAction(tc, ta, te);
			} catch (BlockActivityException ex) {
				blocked = true;
			}
		}
		if (blocked && !isStudioElement(tc)) {
			throw new BlockActivityException();
		}
	}

	// note that the TipiException will only be thrown in sync mode (== poolsize
	// 0)
	// UPDATE: NOT ANYMORE
	public void performAction(final TipiEvent te, TipiEventListener listener) {
		debugLog("event   ", "enqueueing async event: " + te.getEventName());
		if (myThreadPool == null) {
			myThreadPool = new TipiThreadPool(this, getPoolSize());
		}
		try {
			myThreadPool.performAction(te, listener);
		} catch (TipiException e) {
			e.printStackTrace();
			showInternalError("Error performing action: "+te.getEventName()+" on component: "+te.getComponent().getPath(), e);
		} catch (TipiBreakException e) {
			e.printStackTrace();
			showInternalError("Error performing action: "+te.getEventName()+" on component: "+te.getComponent().getPath(), e);
		} catch (Throwable e) {
			e.printStackTrace();
			showInternalError("Severe error performing action: "+te.getEventName()+" on component: "+te.getComponent().getPath(), e);
			
		}
	}

	public void threadStarted(Thread workThread) {
	}

	public void threadEnded(Thread workThread) {
	}

	public void loadServerSettingsFromProperties() {
		String impl = System.getProperty("tipi.client.impl");
		if ("direct".equals(impl)) {
			System.err.println("********* FOR NOW: Only supports indirect client *******");
		}
		String navajoServer = System.getProperty("tipi.client.server");
		String navajoUsername = System.getProperty("tipi.client.username");
		String navajoPassword = System.getProperty("tipi.client.password");
		NavajoClientFactory.getClient().setUsername(navajoUsername);
		NavajoClientFactory.getClient().setPassword(navajoPassword);
		NavajoClientFactory.getClient().setServerUrl(navajoServer);
	}

	public void addTipiActivityListener(TipiActivityListener listener) {
		myActivityListeners.add(listener);
	}

	public void removeTipiActivityListener(TipiActivityListener listener) {
		myActivityListeners.remove(listener);
	}

	/**
	 * @return
	 */
	public List<String> getRequiredIncludes() {
		List<String> s = new ArrayList<String>();
		s.add("com/dexels/navajo/tipi/classdef.xml");
		s.add("com/dexels/navajo/tipi/actions/actiondef.xml");
		return s;
	}

	/**
	 * slightly redundant
	 */
	public void parseRequiredIncludes() {
		for (String s : getRequiredIncludes()) {
			includeList.add(s);
		}
	}

	public void enqueueExecutable(TipiExecutable te) throws TipiException, TipiBreakException {
		myThreadPool.enqueueExecutable(te);
	}

	public TipiStorageManager getStorageManager() {
		return myStorageManager;

	}

	public void setStorageManager(TipiStorageManager tsm) {
		if (tsm == null) {
			throw new IllegalArgumentException("setStorageManager: Can not be null");
		}
		myStorageManager = tsm;
	}

	public Navajo retrieveDocument(String id) {
		if (getStorageManager() != null) {
			try {
				return getStorageManager().getStorageDocument(id);
			} catch (TipiException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public void storeDocument(String id, Navajo n) {
		if (getStorageManager() != null) {
			try {
				getStorageManager().setStorageDocument(id, n);
			} catch (TipiException e) {
				e.printStackTrace();
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
		printIndent(indent, "End of debug component: " + c.getId() + " class: " + c.getClass());

	}

	protected static void printIndent(int indent, String text) {
		for (int i = 0; i < indent; i++) {
			System.err.print(" ");
		}
		System.err.println(text);
	}

	public void exit() {
		System.exit(0);
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
			return;
		}
		if (myThreadPool != null) {
			myThreadPool.shutdown();
		}
		for (ShutdownListener s : shutdownListeners) {
			s.contextShutdown();
		}
		contextShutdown = true;
	}

	public DescriptionProvider getDescriptionProvider() {
		return myDescriptionProvider;
	}

	public void initRemoteDescriptionProvider(String context, String locale) throws NavajoException, ClientException {
		Navajo n = NavajoFactory.getInstance().createNavajo();
		Message m = NavajoFactory.getInstance().createMessage(n, "Description");
		n.addMessage(m);
		Property w = NavajoFactory.getInstance().createProperty(n, "Context", Property.STRING_PROPERTY, context, 99, "", Property.DIR_IN);
		m.addProperty(w);
		Property l = NavajoFactory.getInstance().createProperty(n, "Locale", Property.STRING_PROPERTY, locale, 99, "", Property.DIR_IN);
		m.addProperty(l);
		// n.write(System.err);
		Navajo res = NavajoClientFactory.getClient().doSimpleSend(n, "navajo/description/ProcessGetContextResources");

		// res.write(System.err);
		Message descr = res.getMessage("Descriptions");
		myDescriptionProvider = new RemoteDescriptionProvider(this);
		// myDescriptionProvider.init(locale, context);
		((RemoteDescriptionProvider) myDescriptionProvider).setMessage(descr);
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
		String url = NavajoClientFactory.getClient().getServerUrl();
		URL u;
		try {
			u = new URL(url + "?GetBinary=true&handle=" + b.getHandle());
			return u;
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public abstract void showInfo(final String text, final String title);
	public abstract void showQuestion(final String text, final String title, String[] options) throws TipiBreakException;

	public String generateComponentId(TipiComponent parent, TipiComponent component) {
//		String generated = "RandomId" + Math.random();
//		if (parent == null) {
//			return generated;
//		}
//		TipiComponent present = parent.getTipiComponent(generated);
//		if (present != null) {
//			// try again
//			return generateComponentId(parent,component);
//		}
//		return generated;
		return component.getClass().getName()+"@"+component.hashCode();
		
	}

//	public String getGenericResourceLoader() {
//		return resourceCodeBase;
//	}
//	
	public void setGenericResourceLoader(String resourceCodeBase) throws MalformedURLException {
		this.resourceCodeBase = resourceCodeBase;
		if (resourceCodeBase != null) {
			if (resourceCodeBase.indexOf("http:/") != -1 || resourceCodeBase.indexOf("file:/") != -1) {
				setGenericResourceLoader(new HttpResourceLoader(new URL(resourceCodeBase)));
			} else {
				File res = new File(resourceCodeBase);
				setGenericResourceLoader(new FileResourceLoader(res));
			}
		} else {
			File res = new File("resource");
			setGenericResourceLoader(new FileResourceLoader(res));
		}
	}
//	public String getTipiResourceLoader() {
//		return tipiCodeBase;
//	}

	public void setTipiResourceLoader(String tipiCodeBase) throws MalformedURLException {
		this.tipiCodeBase = tipiCodeBase;
		if (tipiCodeBase != null) {
			if (tipiCodeBase.indexOf("http:/") != -1 || tipiCodeBase.indexOf("file:/") != -1) {
				setTipiResourceLoader(new HttpResourceLoader(new URL(tipiCodeBase)));
			} else {
				setTipiResourceLoader(new FileResourceLoader(new File(tipiCodeBase)));
			}
		} else {
			// nothing supplied. Use a file loader with fallback to classloader.
			setTipiResourceLoader(new FileResourceLoader(new File("tipi")));
		}
	}

	public OutputStream writeTipiResource(String resourceName) throws IOException {
		System.err.println("Writing tipiResource: "+resourceName);
		return tipiResourceLoader.writeResource(resourceName);
	}

	public List<File> getAllTipiResources() throws IOException {
		return tipiResourceLoader.getAllResources();
	}

	public void processProperties(Map<String,String> properties) throws MalformedURLException {
		for (Iterator<String> iter = properties.keySet().iterator(); iter.hasNext();) {
			String element = iter.next();
			String value = properties.get(element);
			setSystemProperty(element, value);
		}
		String tipiCodeBase = properties.get("tipiCodeBase");
		String resourceCodeBase = properties.get("resourceCodeBase");
		System.err.println("Tipi code base: "+tipiCodeBase);
			setTipiResourceLoader(tipiCodeBase);
			setGenericResourceLoader(resourceCodeBase);
	}

	public void fireTipiContextEvent(TipiComponent source, String type, Map<String,Object> event, boolean sync) {
		// link to
		for (TipiEventReporter te : tipiEventReporterList) {
			te.tipiEventReported(source, type, event, sync);
		}

	}

	public void fireTipiStructureChanged(TipiComponent tc) {
		// do nothing
	}
	public void addDefinitionListener(TipiDefinitionListener te) {
		tipiDefinitionListeners .add(te);
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
			Header h = NavajoFactory.getInstance().createHeader(stateNavajo, "StateNavajo", "unknown", "unknown", -1);
			stateNavajo.addHeader(h);
		}
		return stateNavajo;
	}

	public Navajo createComponentNavajo() throws NavajoException {

		Navajo n = NavajoFactory.getInstance().createNavajo();
		Message tipiClasses = NavajoFactory.getInstance().createMessage(n, "TipiClass", Message.MSG_TYPE_ARRAY);
		n.addMessage(tipiClasses);
		for (String s : tipiClassDefMap.keySet()) {
			XMLElement xx = tipiClassDefMap.get(s);
			String type = xx.getStringAttribute("type");
			if (xx.getName().equals("tipiclass") && ("tipi".equals(type) || "component".equals(type))) {
				Message element = NavajoFactory.getInstance().createMessage(n, "TipiClass", Message.MSG_TYPE_ARRAY_ELEMENT);
				tipiClasses.addMessage(element);
				Property nameProp = NavajoFactory.getInstance().createProperty(n, "Name", Property.STRING_PROPERTY,
						xx.getStringAttribute("name"), 0, "", Property.DIR_IN);
				element.addProperty(nameProp);
				Property packageProp = NavajoFactory.getInstance().createProperty(n, "Package", Property.STRING_PROPERTY,
						xx.getStringAttribute("package"), 0, "", Property.DIR_IN);
				element.addProperty(packageProp);
				Property classProp = NavajoFactory.getInstance().createProperty(n, "Class", Property.STRING_PROPERTY,
						xx.getStringAttribute("class"), 0, "", Property.DIR_IN);
				element.addProperty(classProp);
				Property moduleProp = NavajoFactory.getInstance().createProperty(n, "Module", Property.STRING_PROPERTY,
						xx.getStringAttribute("module"), 0, "", Property.DIR_IN);
				element.addProperty(moduleProp);
			}
		}

		return n;
	}

	public Navajo createExtensionNavajo() throws NavajoException {
		//Overview/InjectionName
		Navajo n = NavajoFactory.getInstance().createNavajo();
		Message overview = NavajoFactory.getInstance().createMessage(n, "Overview");
		n.addMessage(overview);
		Property overviewProperty = NavajoFactory.getInstance().createProperty(n, "Overview", Property.CARDINALITY_MULTIPLE, "",
				Property.DIR_OUT);
		overview.addProperty(overviewProperty);
	
		for (TipiExtension te : coreExtensionList) {
			Selection s = NavajoFactory.getInstance().createSelection(n, te.getDescription(), te.getId(), false);
			overviewProperty.addSelection(s);
		}
		
		
		Property connectors = NavajoFactory.getInstance().createProperty(n, "DevelopConnector", Property.CARDINALITY_SINGLE, "",Property.DIR_IN);
		overview.addProperty(connectors);
		Property injection = NavajoFactory.getInstance().createProperty(n, "InjectionName", Property.STRING_PROPERTY, "",0,"Injection name",Property.DIR_IN);
		overview.addProperty(injection);
		for (String conString: tipiConnectorMap.keySet()) {
			Selection s = NavajoFactory.getInstance().createSelection(n,conString, conString, conString.equals(getDefaultConnector().getConnectorId()));
			connectors.addSelection(s);
		}
		for (TipiExtension te : coreExtensionList) {
			Selection s = NavajoFactory.getInstance().createSelection(n, te.getDescription(), te.getId(), false);
			overviewProperty.addSelection(s);
		}
		
		for (TipiExtension te : mainExtensionList) {
			Selection s = NavajoFactory.getInstance().createSelection(n, te.getDescription(), te.getId(), false);
			overviewProperty.addSelection(s);
		}
		for (TipiExtension te : optionalExtensionList) {
			Selection s = NavajoFactory.getInstance().createSelection(n, te.getDescription(), te.getId(), false);
			overviewProperty.addSelection(s);
		}
		ExtensionManager.addExtensionMessage(this,n, coreExtensionList, "Core");
		ExtensionManager.addExtensionMessage(this,n, mainExtensionList, "Main");
		ExtensionManager.addExtensionMessage(this,n, optionalExtensionList, "Options");

		ExtensionManager.addConnectorMessage(this,n, tipiConnectorMap.keySet());
		
		return n;
	}

	public void link(final Property master, final Property slave)  {

		if(master==slave) {
			try {
				System.err.println("F@#$ing hell! You are linking a property to itself! "+master.getFullPropertyName());
			} catch (NavajoException e) {
				e.printStackTrace();
			}
		}
		copyPropertyValue(master, slave);

		doLink(master, slave);
		doLink(slave, master);
	}


	public void unlink(Navajo n) throws NavajoException {
		for(Message current : n.getAllMessages()) {
			unlink(current);
		}
	}	

	public void unlink(Message m) throws NavajoException {
		for (Property p : m.getAllProperties()) {
				unlink(p);
		}	
		for(Message current : m.getAllMessages()) {
			unlink(current);
		}
	}

	
	public void unlink(Property master)  {
		Navajo rootDoc = master.getRootDoc();
		String service = rootDoc.getHeader().getRPCName();
		List<PropertyChangeListener> pref;
		try {
			pref = propertyBindMap.get(service+":"+master.getFullPropertyName());
		} catch (NavajoException e) {
			e.printStackTrace();
			return;
		} 
		propertyBindMap.remove(master);
		if(pref!=null) {
			for (PropertyChangeListener propertyChangeListener : pref) {
				master.removePropertyChangeListener(propertyChangeListener);
			}
			pref.clear();
		}
		
		//		propertyBindMap.clear();
		List<Property> p =  propertyLinkRegistry.get(master);
		propertyLinkRegistry.remove(master);
		if(p!=null) {
			for (Property property : p) {
				unlink(property);
				List<Property> q =  propertyLinkRegistry.get(property);
//				
				if(q!=null) {
					q.remove(master);
					if(q.isEmpty()) {
						propertyLinkRegistry.remove(property);
					}
				}
			}
		}
	}

	private void doLink(final Property master, final Property slave) {

		PropertyChangeListener propertyChangeListener = new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent e) {
				Property master = (Property) e.getSource();
				copyPropertyValue(master, slave);
			}
			
			public String toString() {
				try {
					Navajo rootDoc = slave.getRootDoc();
					Header header = rootDoc.getHeader();
				// TODO Prevent null headers!
					String navajo = header.getRPCName();
					return navajo+":"+slave.getFullPropertyName();
				} catch (NavajoException e) {
					e.printStackTrace();
					return e.getMessage();
				}
			}

			public boolean equals(Object a) {
				// TODO THis is maybe a bit... too short through the bend
				// Properties with the same path may be still different (belonging to another navajo)
				if(a instanceof PropertyChangeListener) {
					return a.toString().equals(toString());
				}
				return super.equals(a);
			}
			
			
		};
		master.addPropertyChangeListener(propertyChangeListener);
		registerPropertyLink(master,slave,propertyChangeListener);
 
	}

	
	protected void registerPropertyLink(Property master, Property slave, PropertyChangeListener pp) {
		List<Property> p =  propertyLinkRegistry.get(master);
		if(p==null) {
			p = new LinkedList<Property>();
			propertyLinkRegistry.put(master, p);
		}
		p.add(slave);

		List<PropertyChangeListener> xx = propertyBindMap.get(master);
		Navajo rootDoc = master.getRootDoc();
		String service = rootDoc.getHeader().getRPCName();
		if(xx==null) {
			xx = new LinkedList<PropertyChangeListener>();
			try {
				propertyBindMap.put(service+":"+master.getFullPropertyName(), xx);
			} catch (NavajoException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		// TODO beware, equality depends on equal property paths
		if(!xx.contains(pp)) {
			xx.add(pp);
		}
	}

	private int getTotalPropertyLinks() {
		int total = 0;
		for (Iterator<Entry<Property,List<Property>>> iterator = propertyLinkRegistry.entrySet().iterator(); iterator.hasNext();) {
			Entry<Property,List<Property>> e = iterator.next();
			total += e.getValue().size();
			
		}
		return total;
	}

	// TODO: Add support for other attributes? type,cardinality,description,length,direction
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
				if(slave.getType().equals(Property.EXPRESSION_PROPERTY)) {
					System.err.println("Ignoring link for expression property");
				} else {
					System.err.println("Link: " + slaveValue + " to: " + masterValue);
					System.err.println("slaveClass: "+slaveValue.getClass()+" masterClass: "+masterValue.getClass());
					try {
						System.err.println("slaveHash: "+slave.getFullPropertyName()+" masterHash: "+master.getFullPropertyName());
					} catch (NavajoException e) {
						e.printStackTrace();
					}
				
					slave.setAnyValue(masterValue);
				}
			}
		}
	}

	public void animateProperty(Property p, int duration, Object target) {
		p.setAnyValue(target);
	}

	public String formatObject(Object o, Class<?> c) {
		System.err.println("Gormat: "+o);
		Thread.dumpStack();
		return "hopla";
	}

	public Navajo createNavajoListNavajo() throws NavajoException {
		Navajo n = NavajoFactory.getInstance().createNavajo();
		Message tipiClasses = NavajoFactory.getInstance().createMessage(n, "Navajo", Message.MSG_TYPE_ARRAY);
		n.addMessage(tipiClasses);
		for (String s : navajoMap.keySet()) {

			if(!s.equals("StateNavajo") && !s.equals("NavajoView")) {
				Message element = NavajoFactory.getInstance().createMessage(n, "Name", Message.MSG_TYPE_ARRAY_ELEMENT);
				tipiClasses.addMessage(element);
				Property nameProp = NavajoFactory.getInstance().createProperty(n, "Name", Property.STRING_PROPERTY, s, 0, "", Property.DIR_OUT);
				element.addProperty(nameProp);
			}

		}
		
		return n;
	}

	public void doActions(TipiEvent te, TipiComponent comp, TipiExecutable executableParent, List<TipiExecutable> exe) throws TipiBreakException {
		try {
			int i = 0;
			for (TipiExecutable current : exe) {
				current.performAction(te, executableParent, i++);
			}

		} catch (TipiException ex) {
			ex.printStackTrace();
		}
	}
	
	
	/**
	 * Parses an connector instance
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
		if(defaultConnector==null) {
			defaultConnector = new HttpNavajoConnector();
			defaultConnector.setContext(this);
		}
			
		return defaultConnector;
	}

	private void registerConnector(TipiConnector tc) {
		tipiConnectorMap.put(tc.getConnectorId(), tc);
	}

	public boolean hasErrors(Navajo result) {
		if(result==null) {
			return false;
		}
		if(result.getMessage("error")!=null) {
			return true;
		}
		if(result.getMessage("ConditionErrors")!=null) {
			return true;
		}
		return false;
	}
	
	public void execute(Runnable e) throws TipiException {
		TipiAnonymousAction taa = new TipiAnonymousAction(e);
		myThreadPool.enqueueExecutable(taa);
	}
	
	public void injectNavajo(String service, Navajo n) throws TipiBreakException {

			if(n.getHeader()==null) {
				Header h =NavajoFactory.getInstance().createHeader(n, service, "unknown","unknown", -1);
				n.addHeader(h);
			}
			addNavajo(service, n);
			loadNavajo(n, service);
	}

	protected void addArgument(String name, String value) {
		argumentMap.put(name, value);
	}
	
	public String getArgument(String expression) {
		return argumentMap.get(expression);
	}
	
	public TipiContext getParentContext() {
		return myParentContext;
	}
	
	public void showInternalError(String errorString, Throwable t) {
		System.err.println("Internal error: "+errorString);
		if(t!=null) {
			t.printStackTrace();
		}
	}

	public final void showInternalError(String errorString) {
		showInternalError(errorString,null);
	}
}