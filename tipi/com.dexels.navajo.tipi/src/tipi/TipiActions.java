package tipi;

import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.actions.adapters.BaseActions;

public class TipiActions extends BaseActions {

	private static final long serialVersionUID = -6504263620597967678L;

	public void unloadNavajo(String service) throws TipiException,
			TipiBreakException {
		java.util.Map<String, Object> parameters = new java.util.HashMap<String, Object>();
		parameters.put("service", service);
		performAction("unloadNavajo", parameters);
	}

	public void injectNavajo(String service,
			com.dexels.navajo.document.Navajo navajo) throws TipiException,
			TipiBreakException {
		java.util.Map<String, Object> parameters = new java.util.HashMap<String, Object>();
		parameters.put("service", service);
		parameters.put("navajo", navajo);
		performAction("injectNavajo", parameters);
	}

	public void loadNavajoList(Object context, String service)
			throws TipiException, TipiBreakException {
		java.util.Map<String, Object> parameters = new java.util.HashMap<String, Object>();
		parameters.put("context", context);
		parameters.put("service", service);
		performAction("loadNavajoList", parameters);
	}

	public void animate(com.dexels.navajo.document.Property target, Object value)
			throws TipiException, TipiBreakException {
		java.util.Map<String, Object> parameters = new java.util.HashMap<String, Object>();
		parameters.put("target", target);
		parameters.put("value", value);
		performAction("animate", parameters);
	}

	public void animateAttribute(com.dexels.navajo.tipi.TipiComponent target,
			String attribute, Integer duration, Object value)
			throws TipiException, TipiBreakException {
		java.util.Map<String, Object> parameters = new java.util.HashMap<String, Object>();
		parameters.put("target", target);
		parameters.put("attribute", attribute);
		parameters.put("duration", duration);
		parameters.put("value", value);
		performAction("animateAttribute", parameters);
	}

	public void performMethod(Object tipipath, String method,
			String destination, Boolean fireevent, Boolean breakOnError,
			Integer expirationInterval, String hostUrl, String username,
			String password, String keystore, String keypass, Boolean condense)
			throws TipiException, TipiBreakException {
		java.util.Map<String, Object> parameters = new java.util.HashMap<String, Object>();
		parameters.put("tipipath", tipipath);
		parameters.put("method", method);
		parameters.put("destination", destination);
		parameters.put("fireevent", fireevent);
		parameters.put("breakOnError", breakOnError);
		parameters.put("expirationInterval", expirationInterval);
		parameters.put("hostUrl", hostUrl);
		parameters.put("username", username);
		parameters.put("password", password);
		parameters.put("keystore", keystore);
		parameters.put("keypass", keypass);
		parameters.put("condense", condense);
		performAction("performMethod", parameters);
	}

	public void loadStateNavajo(Object context, String service)
			throws TipiException, TipiBreakException {
		java.util.Map<String, Object> parameters = new java.util.HashMap<String, Object>();
		parameters.put("context", context);
		parameters.put("service", service);
		performAction("loadStateNavajo", parameters);
	}

	public void linkProperty(Object master, Object slave) throws TipiException,
			TipiBreakException {
		java.util.Map<String, Object> parameters = new java.util.HashMap<String, Object>();
		parameters.put("master", master);
		parameters.put("slave", slave);
		performAction("linkProperty", parameters);
	}

	public void declareAlias(String name, String value) throws TipiException,
			TipiBreakException {
		java.util.Map<String, Object> parameters = new java.util.HashMap<String, Object>();
		parameters.put("name", name);
		parameters.put("value", value);
		performAction("declareAlias", parameters);
	}

	public void setSelectedValue(Object property, String value, String name)
			throws TipiException, TipiBreakException {
		java.util.Map<String, Object> parameters = new java.util.HashMap<String, Object>();
		parameters.put("property", property);
		parameters.put("value", value);
		parameters.put("name", name);
		performAction("setSelectedValue", parameters);
	}

	/**
	 * 
	 Breaks the current block.
	 */
	public void breakTipi() throws TipiException, TipiBreakException {
		java.util.Map<String, Object> parameters = null;
		performAction("breakTipi", parameters);
	}

	public void setLength(com.dexels.navajo.document.Property property,
			Integer length) throws TipiException, TipiBreakException {
		java.util.Map<String, Object> parameters = new java.util.HashMap<String, Object>();
		parameters.put("property", property);
		parameters.put("length", length);
		performAction("setLength", parameters);
	}

	public void setStorageInstanceId(String id) throws TipiException,
			TipiBreakException {
		java.util.Map<String, Object> parameters = new java.util.HashMap<String, Object>();
		parameters.put("id", id);
		performAction("setStorageInstanceId", parameters);
	}

	public void showWarning(String text) throws TipiException,
			TipiBreakException {
		java.util.Map<String, Object> parameters = new java.util.HashMap<String, Object>();
		parameters.put("text", text);
		performAction("showWarning", parameters);
	}

	public void setLocale(String locale) throws TipiException,
			TipiBreakException {
		java.util.Map<String, Object> parameters = new java.util.HashMap<String, Object>();
		parameters.put("locale", locale);
		performAction("setLocale", parameters);
	}

	/**
	 * 
	 Removes the loaded tipi definitions from the in memory cache. Mostly used
	 * to commit
	 */
	public void clearCache() throws TipiException, TipiBreakException {
		java.util.Map<String, Object> parameters = null;
		performAction("clearCache", parameters);
	}

	public void execute(String command) throws TipiException,
			TipiBreakException {
		java.util.Map<String, Object> parameters = new java.util.HashMap<String, Object>();
		parameters.put("command", command);
		performAction("execute", parameters);
	}

	public void dumpStack() throws TipiException, TipiBreakException {
		java.util.Map<String, Object> parameters = null;
		performAction("dumpStack", parameters);
	}

	public void loadExtensions(Object context, String service)
			throws TipiException, TipiBreakException {
		java.util.Map<String, Object> parameters = new java.util.HashMap<String, Object>();
		parameters.put("context", context);
		parameters.put("service", service);
		performAction("loadExtensions", parameters);
	}

	public void injectMessage(String service,
			com.dexels.navajo.document.Message message) throws TipiException,
			TipiBreakException {
		java.util.Map<String, Object> parameters = new java.util.HashMap<String, Object>();
		parameters.put("service", service);
		parameters.put("message", message);
		performAction("injectMessage", parameters);
	}

	public void setType(com.dexels.navajo.document.Property property,
			String propertyType) throws TipiException, TipiBreakException {
		java.util.Map<String, Object> parameters = new java.util.HashMap<String, Object>();
		parameters.put("property", property);
		parameters.put("propertyType", propertyType);
		performAction("setType", parameters);
	}

	public void updateExpressions(Object path,
			com.dexels.navajo.document.Navajo navajo) throws TipiException,
			TipiBreakException {
		java.util.Map<String, Object> parameters = new java.util.HashMap<String, Object>();
		parameters.put("path", path);
		parameters.put("navajo", navajo);
		performAction("updateExpressions", parameters);
	}

	/**
	 * 
	 Pause the current event for the supplied number of millis.
	 */
	public void sleep(Integer time) throws TipiException, TipiBreakException {
		java.util.Map<String, Object> parameters = new java.util.HashMap<String, Object>();
		parameters.put("time", time);
		performAction("sleep", parameters);
	}

	public void fireEvent(Object path, String type) throws TipiException,
			TipiBreakException {
		java.util.Map<String, Object> parameters = new java.util.HashMap<String, Object>();
		parameters.put("path", path);
		parameters.put("type", type);
		performAction("fireEvent", parameters);
	}

	public void addPropertyToMessage(String path, Object value,
			com.dexels.navajo.document.Message message) throws TipiException,
			TipiBreakException {
		java.util.Map<String, Object> parameters = new java.util.HashMap<String, Object>();
		parameters.put("path", path);
		parameters.put("value", value);
		parameters.put("message", message);
		performAction("addPropertyToMessage", parameters);
	}

	/**
	 * 
	 Exits the current Tipi application instance. Web based instances will
	 * typically destroy the session, while Swing / SWT versions will exit the
	 * JVM
	 */
	public void exit() throws TipiException, TipiBreakException {
		java.util.Map<String, Object> parameters = null;
		performAction("exit", parameters);
	}

	public void showInfo(String text) throws TipiException, TipiBreakException {
		java.util.Map<String, Object> parameters = new java.util.HashMap<String, Object>();
		parameters.put("text", text);
		performAction("showInfo", parameters);
	}

	public void flushCache(Object context, String service)
			throws TipiException, TipiBreakException {
		java.util.Map<String, Object> parameters = new java.util.HashMap<String, Object>();
		parameters.put("context", context);
		parameters.put("service", service);
		performAction("flushCache", parameters);
	}

	public void insertArrayElement(com.dexels.navajo.document.Message message,
			com.dexels.navajo.document.Message element) throws TipiException,
			TipiBreakException {
		java.util.Map<String, Object> parameters = new java.util.HashMap<String, Object>();
		parameters.put("message", message);
		parameters.put("element", element);
		performAction("insertArrayElement", parameters);
	}

	public void removeMessage(com.dexels.navajo.document.Message message,
			Integer index) throws TipiException, TipiBreakException {
		java.util.Map<String, Object> parameters = new java.util.HashMap<String, Object>();
		parameters.put("message", message);
		parameters.put("index", index);
		performAction("removeMessage", parameters);
	}

	/**
	 * 
	 Breaks the current event.
	 */
	public void breakEvent() throws TipiException, TipiBreakException {
		java.util.Map<String, Object> parameters = null;
		performAction("breakEvent", parameters);
	}

	public void instantiate(String name, String expectType, String id,
			Object location, Boolean force, String constraint)
			throws TipiException, TipiBreakException {
		java.util.Map<String, Object> parameters = new java.util.HashMap<String, Object>();
		parameters.put("name", name);
		parameters.put("expectType", expectType);
		parameters.put("id", id);
		parameters.put("location", location);
		parameters.put("force", force);
		parameters.put("constraint", constraint);
		performAction("instantiate", parameters);
	}

	public void insertMessage(com.dexels.navajo.document.Message message)
			throws TipiException, TipiBreakException {
		java.util.Map<String, Object> parameters = new java.util.HashMap<String, Object>();
		parameters.put("message", message);
		performAction("insertMessage", parameters);
	}

	public void set(Object element, Object value, String propertyType,
			String direction, String description) throws TipiException,
			TipiBreakException {
		java.util.Map<String, Object> parameters = new java.util.HashMap<String, Object>();
		parameters.put("element", element);
		parameters.put("value", value);
		parameters.put("propertyType", propertyType);
		parameters.put("direction", direction);
		parameters.put("description", description);
		performAction("set", parameters);
	}

	public void link(com.dexels.navajo.document.Property master,
			com.dexels.navajo.document.Property slave) throws TipiException,
			TipiBreakException {
		java.util.Map<String, Object> parameters = new java.util.HashMap<String, Object>();
		parameters.put("master", master);
		parameters.put("slave", slave);
		performAction("link", parameters);
	}

	public void attribute(com.dexels.navajo.tipi.TipiComponent path)
			throws TipiException, TipiBreakException {
		java.util.Map<String, Object> parameters = new java.util.HashMap<String, Object>();
		parameters.put("path", path);
		performAction("attribute", parameters);
	}

	public void dumpNavajo(com.dexels.navajo.document.Navajo input,
			String fileName) throws TipiException, TipiBreakException {
		java.util.Map<String, Object> parameters = new java.util.HashMap<String, Object>();
		parameters.put("input", input);
		parameters.put("fileName", fileName);
		performAction("dumpNavajo", parameters);
	}

	public void setUsername(String username) throws TipiException,
			TipiBreakException {
		java.util.Map<String, Object> parameters = new java.util.HashMap<String, Object>();
		parameters.put("username", username);
		performAction("setUsername", parameters);
	}

	public void debug(Object value) throws TipiException, TipiBreakException {
		java.util.Map<String, Object> parameters = new java.util.HashMap<String, Object>();
		parameters.put("value", value);
		performAction("debug", parameters);
	}

	public void run(String engine, Object script) throws TipiException,
			TipiBreakException {
		java.util.Map<String, Object> parameters = new java.util.HashMap<String, Object>();
		parameters.put("engine", engine);
		parameters.put("script", script);
		performAction("run", parameters);
	}

	public void setClientLocale(String locale, String context)
			throws TipiException, TipiBreakException {
		java.util.Map<String, Object> parameters = new java.util.HashMap<String, Object>();
		parameters.put("locale", locale);
		parameters.put("context", context);
		performAction("setClientLocale", parameters);
	}

	public void registerPush(String agentId) throws TipiException,
			TipiBreakException {
		java.util.Map<String, Object> parameters = new java.util.HashMap<String, Object>();
		parameters.put("agentId", agentId);
		performAction("registerPush", parameters);
	}

	public void setPostman(String server, String username, String password)
			throws TipiException, TipiBreakException {
		java.util.Map<String, Object> parameters = new java.util.HashMap<String, Object>();
		parameters.put("server", server);
		parameters.put("username", username);
		parameters.put("password", password);
		performAction("setPostman", parameters);
	}

	public void disposePath(String path) throws TipiException,
			TipiBreakException {
		java.util.Map<String, Object> parameters = new java.util.HashMap<String, Object>();
		parameters.put("path", path);
		performAction("disposePath", parameters);
	}

	public void deleteCache() throws TipiException, TipiBreakException {
		java.util.Map<String, Object> parameters = null;
		performAction("deleteCache", parameters);
	}

	public void setPassword(String password) throws TipiException,
			TipiBreakException {
		java.util.Map<String, Object> parameters = new java.util.HashMap<String, Object>();
		parameters.put("password", password);
		performAction("setPassword", parameters);
	}

	public void setDirection(com.dexels.navajo.document.Property property,
			String direction) throws TipiException, TipiBreakException {
		java.util.Map<String, Object> parameters = new java.util.HashMap<String, Object>();
		parameters.put("property", property);
		parameters.put("direction", direction);
		performAction("setDirection", parameters);
	}

	public void dispose(com.dexels.navajo.tipi.TipiComponent path)
			throws TipiException, TipiBreakException {
		java.util.Map<String, Object> parameters = new java.util.HashMap<String, Object>();
		parameters.put("path", path);
		performAction("dispose", parameters);
	}

	public void setCardinality(com.dexels.navajo.document.Property property,
			String cardinality) throws TipiException, TipiBreakException {
		java.util.Map<String, Object> parameters = new java.util.HashMap<String, Object>();
		parameters.put("property", property);
		parameters.put("cardinality", cardinality);
		performAction("setCardinality", parameters);
	}

	public void removeAllMessages(com.dexels.navajo.document.Message message)
			throws TipiException, TipiBreakException {
		java.util.Map<String, Object> parameters = new java.util.HashMap<String, Object>();
		parameters.put("message", message);
		performAction("removeAllMessages", parameters);
	}

	public void deleteCookies() throws TipiException, TipiBreakException {
		java.util.Map<String, Object> parameters = null;
		performAction("deleteCookies", parameters);
	}

	public void createNavajo(String name) throws TipiException,
			TipiBreakException {
		java.util.Map<String, Object> parameters = new java.util.HashMap<String, Object>();
		parameters.put("name", name);
		performAction("createNavajo", parameters);
	}

	public void setSubLocale(String locale) throws TipiException,
			TipiBreakException {
		java.util.Map<String, Object> parameters = new java.util.HashMap<String, Object>();
		parameters.put("locale", locale);
		performAction("setSubLocale", parameters);
	}

	public void reloadNavajo(Object to, Object from,
			com.dexels.navajo.document.Navajo service) throws TipiException,
			TipiBreakException {
		java.util.Map<String, Object> parameters = new java.util.HashMap<String, Object>();
		parameters.put("to", to);
		parameters.put("from", from);
		parameters.put("service", service);
		performAction("reloadNavajo", parameters);
	}

	public void setValue(Object to, String from) throws TipiException,
			TipiBreakException {
		java.util.Map<String, Object> parameters = new java.util.HashMap<String, Object>();
		parameters.put("to", to);
		parameters.put("from", from);
		performAction("setValue", parameters);
	}

	public void debugNavajo(Object tipipath) throws TipiException,
			TipiBreakException {
		java.util.Map<String, Object> parameters = new java.util.HashMap<String, Object>();
		parameters.put("tipipath", tipipath);
		performAction("debugNavajo", parameters);
	}

	public void instantiateClass(String classTipi, String id, String location,
			Boolean force, String constraint) throws TipiException,
			TipiBreakException {
		java.util.Map<String, Object> parameters = new java.util.HashMap<String, Object>();
		parameters.put("class", classTipi);
		parameters.put("id", id);
		parameters.put("location", location);
		parameters.put("force", force);
		parameters.put("constraint", constraint);
		performAction("instantiateClass", parameters);
	}

	public void loadComponentNavajo(Object context, String service)
			throws TipiException, TipiBreakException {
		java.util.Map<String, Object> parameters = new java.util.HashMap<String, Object>();
		parameters.put("context", context);
		parameters.put("service", service);
		performAction("loadComponentNavajo", parameters);
	}

	public void showError(String text) throws TipiException, TipiBreakException {
		java.util.Map<String, Object> parameters = new java.util.HashMap<String, Object>();
		parameters.put("text", text);
		performAction("showError", parameters);
	}

	public void setCookie(String name, String value) throws TipiException,
			TipiBreakException {
		java.util.Map<String, Object> parameters = new java.util.HashMap<String, Object>();
		parameters.put("name", name);
		parameters.put("value", value);
		performAction("setCookie", parameters);
	}

	public void setDescription(com.dexels.navajo.document.Property property,
			String description) throws TipiException, TipiBreakException {
		java.util.Map<String, Object> parameters = new java.util.HashMap<String, Object>();
		parameters.put("property", property);
		parameters.put("description", description);
		performAction("setDescription", parameters);
	}

	/**
	 * 
	 Exits the current Tipi application instance. Restarts the current tipi
	 * application with the same startup. The context will be rebuilt, only the
	 * vm will remain.
	 */
	public void reboot() throws TipiException, TipiBreakException {
		java.util.Map<String, Object> parameters = null;
		performAction("reboot", parameters);
	}

	public void setHTTPS(Object keystore, String passphrase)
			throws TipiException, TipiBreakException {
		java.util.Map<String, Object> parameters = new java.util.HashMap<String, Object>();
		parameters.put("keystore", keystore);
		parameters.put("passphrase", passphrase);
		performAction("setHTTPS", parameters);
	}

	public void updateAllExpressions(Object path) throws TipiException,
			TipiBreakException {
		java.util.Map<String, Object> parameters = new java.util.HashMap<String, Object>();
		parameters.put("path", path);
		performAction("updateAllExpressions", parameters);
	}

	/**
	 * 
	 Adds a property in a navajo object. Not widely used.
	 */
	public void addProperty(String path, Object value,
			com.dexels.navajo.document.Navajo navajo, String direction,
			String description) throws TipiException, TipiBreakException {
		java.util.Map<String, Object> parameters = new java.util.HashMap<String, Object>();
		parameters.put("path", path);
		parameters.put("value", value);
		parameters.put("navajo", navajo);
		parameters.put("direction", direction);
		parameters.put("description", description);
		performAction("addProperty", parameters);
	}

	public void performTipiMethod(Object path, String name)
			throws TipiException, TipiBreakException {
		java.util.Map<String, Object> parameters = new java.util.HashMap<String, Object>();
		parameters.put("path", path);
		parameters.put("name", name);
		performAction("performTipiMethod", parameters);
	}

	public void disposeChildren(Object path) throws TipiException,
			TipiBreakException {
		java.util.Map<String, Object> parameters = new java.util.HashMap<String, Object>();
		parameters.put("path", path);
		performAction("disposeChildren", parameters);
	}

	public void callService(String service, Boolean cached, String connector,
			String destination, com.dexels.navajo.document.Navajo input,
			Boolean breakOnError) throws TipiException, TipiBreakException {
		java.util.Map<String, Object> parameters = new java.util.HashMap<String, Object>();
		parameters.put("service", service);
		parameters.put("cached", cached);
		parameters.put("connector", connector);
		parameters.put("destination", destination);
		parameters.put("input", input);
		parameters.put("breakOnError", breakOnError);
		performAction("callService", parameters);
	}

	public void setSystemProperty(String name, String value)
			throws TipiException, TipiBreakException {
		java.util.Map<String, Object> parameters = new java.util.HashMap<String, Object>();
		parameters.put("name", name);
		parameters.put("value", value);
		performAction("setSystemProperty", parameters);
	}

}
