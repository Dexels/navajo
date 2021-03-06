/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.document;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.base.BaseNavajoFactoryImpl;
import com.dexels.navajo.document.nanoimpl.CaseSensitiveXMLElement;
import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.document.navascript.tags.MapDefinitionInterrogator;

/**
 * <p>
 * Title: Navajo Product Project
 * </p>
 * <p>
 * Description: This is the official source for the Navajo server
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Dexels BV
 * </p>
 * 
 * @author Arjen Schoneveld
 * @version 1.0
 */

public abstract class NavajoFactory {
	protected static NavajoFactory impl = null;
	protected File tempDir = null;
	protected Map<String, String> defaultSubTypes = new HashMap<>();

	private final Map<String, byte[]> binaryStorage = new HashMap<>();
	private boolean sandboxMode = false;

	private static Object semaphore = new Object();

	private Map<String, Class<?>> toJavaType = new HashMap<>();
	private Map<String, String> toJavaGenericType = new HashMap<>();
	private Map<Class<?>, String> toNavajoType = new HashMap<>();

	private static final Logger logger = LoggerFactory
			.getLogger(NavajoFactory.class);

    private void readTypes() throws ClassNotFoundException, IOException  {
        ClassLoader cl = getClass().getClassLoader();
        if (cl == null) {
            logger.info("Bootstrap classloader detected!");
            cl = ClassLoader.getSystemClassLoader();
        }
        InputStream is = cl.getResourceAsStream("navajotypes.xml");
        CaseSensitiveXMLElement types = new CaseSensitiveXMLElement();
        types.parseFromStream(is);
        is.close();
        Vector<XMLElement> children = types.getChildren();
        for (int i = 0; i < children.size(); i++) {
            XMLElement child = children.get(i);
            String navajotype = (String) child.getAttribute("name");
            String javaclass = (String) child.getAttribute("type");
            String generic = (String) child.getAttribute("generic");
            Class<?> c = Class.forName(javaclass);
            toJavaType.put(navajotype, c);
            toJavaGenericType.put(navajotype, generic);
            toNavajoType.put(c, navajotype);
        }
    }

	public void addNavajoType(String typeId, Class<?> clz) {
		toJavaType.put(typeId, clz);
		toNavajoType.put(clz, typeId);
	}

	public String getNavajoType(Class<?> c) {
		if (c == null) {
			return "empty";
		} else if (toNavajoType.containsKey(c)) {
			return toNavajoType.get(c);
		} else {
			return c.getName();
		}

	}

	public Class<?> getJavaType(String p) {
		return toJavaType.get(p);
	}

	public String getJavaTypeGeneric(String p) {
		return toJavaGenericType.get(p);
	}

	public Set<String> getNavajoTypes() {
		return toJavaType.keySet();
	}

	/**
	 * Get the default NavajoFactory implementation.
	 * 
	 * @return NavajoFactory instance
	 */
	public static NavajoFactory getInstance() {
		if (impl != null) {
			return impl;
		}
		synchronized (semaphore) {
			if (impl == null) {
				boolean sbmode = false;
				String name = null;
				try {
					name = System
							.getProperty("com.dexels.navajo.DocumentImplementation");
				} catch (SecurityException e1) {
					logger.warn("No permission. Using standard document impl.");
					sbmode = true;
				}
				try {
					String cloudMode = System.getProperty("tipi.cloudMode");
					if ("true".equals(cloudMode)) {
						sbmode = true;
					}
				} catch (Throwable e1) {
					logger.error("Error: ", e1);
				}

				if (name == null) {
					impl = new BaseNavajoFactoryImpl();
					impl.sandboxMode = sbmode;
				} else {
					try {
						impl = (NavajoFactory) Class.forName(name)
								.getDeclaredConstructor().newInstance();
						impl.sandboxMode = sbmode;
					} catch (Exception e) {
						logger.error("Error: ", e);
					}
				}

				try {
					if(impl!=null) {
						impl.readTypes();
					}
				} catch (ClassNotFoundException | IOException e) {
					throw new NavajoException(
							"Could not instantiate NavajoDocument Factory, problem reading navajotypes.xml: "
									+ e.getMessage(), e);
				}
			}
		}
		return impl;
	}

	public static void resetImplementation() {
		impl = null;
	}

	public void shutdown() {

	}

	/**
	 * Parses a given string of key=value pairs and returns them in a Map
	 * 
	 * @param subType
	 *            String
	 * @return Map
	 */

	public Map<String, String> parseSubTypes(String subType) {
		Map<String, String> m = new HashMap<>();
		if (subType == null || "".equals(subType)) {
			return m;
		}
		StringTokenizer st = new StringTokenizer(subType, ",");
		while (st.hasMoreTokens()) {
			String next = st.nextToken();
			int i = next.indexOf('=');
			if (i >= 0) {
				String key = next.substring(0, i);
				String value = next.substring(i + 1, next.length());
				m.put(key, value);
			}
		}
		return m;
	}

	/**
	 * Sets the subtype for all the new properties of this type, unless defined
	 * otherwise.
	 * 
	 * @param String
	 *            type
	 * @param String
	 *            subtype todo: Change this into something like
	 *            addDefaultSubType(String type,String key, String value)
	 */
	public void setDefaultSubtypeForType(String type, String subtype) {
		defaultSubTypes.put(type, subtype);
	}

	/**
	 * Get the default subtype for a given type
	 * 
	 * @param type
	 *            String
	 * @return String subtype
	 */
	public String getDefaultSubtypeForType(String type) {
		return defaultSubTypes.get(type);
	}

	private ExpressionEvaluator myExpressionEvaluator = null;

	/**
	 * Get this factory's ExpressionEvaluator object
	 * 
	 * @return ExpressionEvaluator
	 */
	public ExpressionEvaluator getExpressionEvaluator() {
		return myExpressionEvaluator;
	}

	/**
	 * Set this factory's ExpressionEvaluator object
	 * 
	 * @param e
	 *            ExpressionEvaluator
	 */
	public void setExpressionEvaluator(ExpressionEvaluator e) {
		myExpressionEvaluator = e;
	}

	/**
	 * Used to convert classnames to property types. E.g. java.lang.String ->
	 * Property.STRING_PROPERTY It will not return all types. The following
	 * types will never be returned, because they do not map 1-1 on java
	 * classes: MEMO_PROPERTY, DATE_PATTERN_PROPERTY, PASSWORD_PROPERTY,
	 * TIPI_PROPERTY
	 * 
	 * @param String
	 *            className
	 * @return String propertyType
	 */
	public String getPropertyType(String className) {
		if (className.equals("java.lang.String")) {
			return Property.STRING_PROPERTY;
		}
		if (className.equals("java.lang.Integer") || className.equals("int")) {
			return Property.INTEGER_PROPERTY;
		}
		if (className.equals("java.lang.Long") || className.equals("long")) {
			return Property.LONG_PROPERTY;
		}
		if (className.equals("java.util.Date")) {
			return Property.DATE_PROPERTY;
		}
		if (className.equals("java.lang.Float") || className.equals("float")
				|| className.equals("double")) {
			return Property.FLOAT_PROPERTY;
		}
		if (className.equals("com.dexels.navajo.document.types.ClockTime")) {
			return Property.CLOCKTIME_PROPERTY;
		}
		if (className.equals("java.net.URL")) {
			return Property.URL_PROPERTY;
		}
		if (className.equals("java.lang.Boolean")
				|| className.equals("boolean")) {
			return Property.BOOLEAN_PROPERTY;
		}
		if (className.equals("com.dexels.navajo.document.Point")) {
			return Property.STRING_PROPERTY;
		}
		if (className.equals("byte[]")) {
			return Property.BINARY_PROPERTY;
		}
		// as a default...
		return Property.STRING_PROPERTY;
	}

	public void setTempDir(File f) {
		tempDir = f;
	}

	public final File getTempDir() {
		if (tempDir != null) {
			if (!tempDir.exists()) {
				tempDir.mkdirs();
			}
			return tempDir;
		} else {
			return new File(System.getProperty("java.io.tmpdir"));
		}
	}

	/**
	 * Create a NavajoException object with a given message (text)
	 * 
	 * @param message
	 *            String
	 * @return NavajoException
	 */
	public abstract NavajoException createNavajoException(String message);

	/**
	 * Create a NavajoException object with a given Exception object
	 * 
	 * @param e
	 *            Exception
	 * @return NavajoException
	 */
	public abstract NavajoException createNavajoException(Throwable e);

	/**
	 * Create a NavajoException object with a given message AND Exception object
	 * 
	 * @param e
	 *            Exception
	 * @param message
	 *            String
	 * @return NavajoException
	 */
	public abstract NavajoException createNavajoException(String message,
			Throwable e);

	/**
	 * Create a Navjao object from a given InputStream
	 * 
	 * @param stream
	 *            InputStream
	 */
	public abstract Navajo createNavajo(java.io.InputStream stream);

	/**
	 * creates a Navajo object directly from a reader
	 * 
	 * @param r
	 * @return
	 */

	public abstract Navajo createNavajo(Reader r);

	/**
	 * creates a Navajo object directly from a JSON reader
	 * 
	 * @param r
	 * @return
	 */

	public abstract Navajo createNavajoJSON(Reader r);

	/**
	 * Create a Navajo object from a given Object
	 * 
	 * @param representation
	 *            Object
	 * @return Navajo
	 */
	public abstract Navajo createNavajo(Object representation);

	/**
	 * Create an emtpy Navajo object
	 * 
	 * @return Navajo
	 */
	public abstract Navajo createNavajo();

	/**
	 * Create a NavaScript object from a given InputStream
	 * 
	 * @param stream
	 *            InputStream
	 * @return Navajo
	 */
	public abstract Navascript createNavaScript(java.io.InputStream stream);

	
	public abstract Navascript createNavaScript(FileInputStream fis, MapDefinitionInterrogator mapDefinitionInterrogatorImpl);
	
	/**
	 * Create a NavaScript object from a given Object
	 * 
	 * @param representation
	 *            Object
	 * @return Navajo
	 */
	public abstract Navascript createNavaScript(Object representation);

	/**
	 * Create an empty NavaScriptObject
	 * 
	 * @return Navajo
	 */
	public abstract Navascript createNavaScript();

	/**
	 * Create an Header object for a given Navajo
	 * 
	 * @param n
	 *            Navajo
	 * @param rpcName
	 *            String
	 * @param rpcUser
	 *            String
	 * @param rpcPassword
	 *            String
	 * @param expiration_interval
	 *            long
	 * @return Header
	 */
	public abstract Header createHeader(Navajo n, String rpcName,
			String rpcUser, String rpcPassword, long expirationinterval);

	/**
	 * Create a Message object from a given Object
	 * 
	 * @param representation
	 *            Object
	 * @return Message
	 */
	public abstract Message createMessage(Object representation);

	/**
	 * Create a Message object with the given Navajo as parent and with a given
	 * name. NOTE: The Message is NOT added to the supplied Navajo object
	 * 
	 * @param n
	 *            Navajo
	 * @param name
	 *            String
	 * @return Message
	 */
	public abstract Message createMessage(Navajo n, String name);

	/**
	 * Create a Message object of a given type with a given name withe the
	 * supplied Navajo object as his parent. NOTE: The Message is NOT added to
	 * the supplied Navajo object
	 * 
	 * @param tb
	 *            Navajo
	 * @param name
	 *            String
	 * @param type
	 *            String
	 * @return Message
	 */
	public abstract Message createMessage(Navajo tb, String name, String type);

	/**
	 * Create a Property object from a given Object
	 * 
	 * @param representation
	 *            Object
	 * @return Property
	 */
	public abstract Property createProperty(Object representation);

	/**
	 * Create a Property object with the given Navajo as his parent
	 * 
	 * @param tb
	 *            Navajo
	 * @param name
	 *            String
	 * @param cardinality
	 *            String
	 * @param description
	 *            String
	 * @param direction
	 *            String
	 * @throws NavajoException
	 * @return Property
	 */
	public abstract Property createProperty(Navajo tb, String name,
			String cardinality, String description, String direction);

	/**
	 * Create a Property object with the given Navajo as his parent
	 * 
	 * @param tb
	 *            Navajo
	 * @param name
	 *            String
	 * @param type
	 *            String
	 * @param value
	 *            String
	 * @param length
	 *            int
	 * @param description
	 *            String
	 * @param direction
	 *            String
	 * @throws NavajoException
	 * @return Property
	 */
	public abstract Property createProperty(Navajo tb, String name,
			String type, String value, int length, String description,
			String direction);

	/**
	 * Create a Property object with the given Navajo as his parent
	 * 
	 * @param n
	 *            Navajo
	 * @param name
	 *            String
	 * @param type
	 *            String
	 * @param value
	 *            String
	 * @param i
	 *            int
	 * @param desc
	 *            String
	 * @param direction
	 *            String
	 * @param subtype
	 *            String
	 * @throws NavajoException
	 * @return Property
	 */
	public abstract Property createProperty(Navajo n, String name, String type,
			String value, int i, String desc, String direction, String subtype);

	/**
	 * Create an ExpressionTag object
	 * 
	 * @param tb
	 *            Navajo
	 * @param condition
	 *            String
	 * @param value
	 *            String
	 * @throws NavajoException
	 * @return ExpressionTag
	 */
	public abstract ExpressionTag createExpression(Navajo tb, String condition,
			String value);

	/**
	 * Create a FieldTag object
	 * 
	 * @param tb
	 *            Navajo
	 * @param condition
	 *            String
	 * @param name
	 *            String
	 * @throws NavajoException
	 * @return FieldTag
	 */
	public abstract Field createField(Navajo tb, String condition, String name);

	/**
	 * Create a ParamTag object
	 * 
	 * @param tb
	 *            Navajo
	 * @param condition
	 *            String
	 * @param name
	 *            String
	 * @throws NavajoException
	 * @return ParamTag
	 */
	public abstract Param createParam(Navajo tb, String condition,
			String name);

	/**
	 * Create a MapTag object
	 * 
	 * @param tb
	 *            Navajo
	 * @param object
	 *            String
	 * @param condition
	 *            String
	 * @throws NavajoException
	 * @return MapTag
	 */
	public abstract MapAdapter createMapObject(Navajo tb, String object, String condition);

	/**
	 * Create a MapTag reference
	 * 
	 * @param tb
	 *            Navajo
	 * @param ref
	 *            String
	 * @param condition
	 *            String
	 * @param filter
	 *            String
	 * @throws NavajoException
	 * @return MapTag
	 */
	public abstract MapAdapter createMapRef(Navajo tb, String ref, String condition, String filter, MapAdapter parent);

	/**
	 * Create a Selection object
	 * 
	 * @param tb
	 *            Navajo
	 * @param name
	 *            String
	 * @param value
	 *            String
	 * @param selected
	 *            boolean
	 * @return Selection
	 */
	public abstract Selection createSelection(Navajo tb, String name,
			String value, boolean selected);

	/**
	 * Create a Selection object
	 * 
	 * @param tb
	 *            Navajo
	 * @param name
	 *            String
	 * @param value
	 *            String
	 * @param selected
	 *            int, if selected > 0 then selected value is set to true.
	 * @return Selection
	 */
	public abstract Selection createSelection(Navajo tb, String name,
			String value, int selected);

	/**
	 * Create a Selection object
	 * 
	 * @param tb
	 *            Navajo
	 * @param name
	 *            String
	 * @param value
	 *            String
	 * @param selected
	 *            int, if selected is boolean and true, set selected, if false
	 *            unset, if selected is integer > 0 then selected value is set
	 *            to true.
	 * @return Selection
	 */
	public abstract Selection createSelection(Navajo tb, String name,
			String value, Object selected);

	/**
	 * Create a DUMMY Selection object
	 * 
	 * @return Selection
	 */
	public abstract Selection createDummySelection();

	/**
	 * Create a Method object
	 * 
	 * @param tb
	 *            Navajo
	 * @param name
	 *            String
	 * @param server
	 *            String
	 * @return Method
	 */
	public abstract Method createMethod(Navajo tb, String name, String server);

	/**
	 * Create a Point object
	 * 
	 * @param p
	 *            Property
	 * @throws NavajoException
	 * @return Point
	 */
	public abstract Point createPoint(Property p);

	/**
	 * Checks a message using a "definition" message.
	 * 
	 * @param message
	 *            , the message to be checked
	 * @param entityMessage
	 *            , the "definition" message
	 * @param ignoreUndefinedEntities
	 *            , ignores properties/messages in input message that are not in
	 *            entityMessage
	 * @return map with problem properties/messages.
	 */
	public Map<String, String> checkTypes(Message message,
			Message entityMessage, boolean ignoreUndefinedEntities) {

		Map<String, String> problems = new HashMap<>();

		Iterator<Property> inputProperties = message.getAllProperties()
				.iterator();
		while (inputProperties.hasNext()) {
			Property inputP = inputProperties.next();
			Property entityP = entityMessage.getProperty(inputP.getName());
			if (!ignoreUndefinedEntities && entityP == null) {
				problems.put(inputP.getFullPropertyName(), "Unknown property: "
						+ inputP.getName());
			} else if (entityP != null
					&& entityP.getType().equals(Property.SELECTION_PROPERTY)) {
				if (inputP.getValue() != null && !inputP.getValue().equals("")) {
					boolean found = false;
					for (Selection entityS : entityP.getAllSelections()) {
						if (entityS.getValue().equals(inputP.getValue())) {
							found = true;
						}
					}
					if (!found) {
						problems.put(
								inputP.getFullPropertyName(),
								"Invalid selection option: "
										+ inputP.getValue());
					}
				}
			} else if (entityP != null
					&& !inputP.getType().equals(entityP.getType())) {
				problems.put(inputP.getFullPropertyName(), "Invalid type: "
						+ inputP.getType() + ", expected: " + entityP.getType());
			}
		}

		// Other way around: check for missing properties.
		inputProperties = entityMessage.getAllProperties().iterator();
		while (inputProperties.hasNext()) {
			Property entityP = inputProperties.next();
			Property inputP = message.getProperty(entityP.getName());
			if (inputP == null) {
				problems.put(message.getFullMessageName(), "Missing property: "
						+ entityP.getName());
			}
		}

		Iterator<Message> inputMessages = message.getAllMessages().iterator();
		while (inputMessages.hasNext()) {
			Message inputM = inputMessages.next();
			Message entityM = entityMessage.getMessage(inputM.getName());
			if (!ignoreUndefinedEntities && entityM == null) {
				problems.put(inputM.getName(),
						"Unknown message: " + inputM.getName());
			} else if (inputM.isArrayMessage() && entityM.isArrayMessage()
					&& entityM.getDefinitionMessage() != null) {
				Iterator<Message> children = inputM.getElements().iterator();
				while (children.hasNext()) {
					problems.putAll(checkTypes(children.next(),
							entityM.getDefinitionMessage(),
							ignoreUndefinedEntities));
				}
			} else if (!inputM.getType().equals(entityM.getType())) {
				problems.put(inputM.getName(), "Unknown message type: "
						+ inputM.getType() + ". Expected: " + entityM.getType());
			} else {
				problems.putAll(checkTypes(inputM, entityM,
						ignoreUndefinedEntities));
			}
		}

		// Other way around: check for missing messages.
		inputMessages = entityMessage.getAllMessages().iterator();
		while (inputMessages.hasNext()) {
			Message entityM = inputMessages.next();
			Message inputM = message.getMessage(entityM.getName());
			if (inputM == null) {
				problems.put(message.getFullMessageName(), "Missing message: "
						+ entityM.getName());
			}
		}

		return problems;
	}

	public void storeHandle(String name, byte[] data) {
		binaryStorage.put(name, data);
	}

	public byte[] getHandle(String name) {
		return binaryStorage.get(name);
	}

	public boolean isSandboxMode() {
		return sandboxMode;
	}

	public void setSandboxMode(boolean b) {
		sandboxMode = b;
	}

	public void removeHandle(String name) {
		binaryStorage.remove(name);
	}

	public abstract Operation createOperation(Navajo n, String method,
			String service, String entityName, Message extra);

	public abstract Operation createOperation(Navajo n, String method,
			String service, String validationService, String entityName, Message extra);


}
