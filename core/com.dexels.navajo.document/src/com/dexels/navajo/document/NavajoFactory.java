package com.dexels.navajo.document;

import java.io.*;
import java.util.*;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.base.*;
import com.dexels.navajo.document.nanoimpl.CaseSensitiveXMLElement;
import com.dexels.navajo.document.nanoimpl.XMLElement;

/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author Arjen Schoneveld
 * @version 1.0
 */

public abstract class NavajoFactory {
  protected static volatile NavajoFactory impl = null;
  protected File tempDir = null;
  protected static final HashMap<String,NavajoFactory> alternativeFactories = new HashMap<String,NavajoFactory>();
  protected Map<String,String> defaultSubTypes = new HashMap<String,String>();

  private final Map<String,byte[]> binaryStorage = new HashMap<String,byte[]>();
  private boolean sandboxMode = false;
  
  private static Object semaphore = new Object();
  
  private HashMap<String, Class<?>> toJavaType = new HashMap<String, Class<?>>();
  private HashMap<Class<?>, String> toNavajoType = new HashMap<Class<?>, String>();
  
  private static final Logger logger = LoggerFactory.getLogger(NavajoFactory.class);

  
  private void readTypes() throws Exception {
	  ClassLoader cl = getClass().getClassLoader();
	  if(cl==null) {
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
		 Class<?> c = Class.forName(javaclass);
		 toJavaType.put(navajotype, c);
		 toNavajoType.put(c, navajotype);
	 }
  }
  
  public void addNavajoType(String typeId, Class<?> clz) {
	  toJavaType.put(typeId, clz);
	  toNavajoType.put(clz, typeId);
  }
  
  public String getNavajoType(Class<?> c) {
	  if ( c == null ) {
		  return "empty";
	  } else
	  if ( toNavajoType.containsKey(c) ) {
		  return toNavajoType.get(c);
	  } else {
		  return c.getName();
	  }
	  
  }
  
  public Class<?> getJavaType(String p) {
	  return toJavaType.get(p);
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
	  if (impl!=null) {
		return impl;
	}
	  synchronized ( semaphore ) {
		  if (impl == null) {
			  boolean sbmode = false;
			  String name = null;
			  try {
				name = System.getProperty(
				  "com.dexels.navajo.DocumentImplementation");
			} catch (SecurityException e1) {
				logger.warn("No permission. Using standard document impl.");
				sbmode = true;
			}
			try {
				// TODO Don't use system properties, really detect GAE
				String cloudMode = System.getProperty("tipi.cloudMode");
				if("true".equals(cloudMode)) {
					sbmode = true;
				}
			} catch (Throwable e1) {
				logger.error("Error: ", e1);
			}
			
			  if (name == null) {
				  name = "com.dexels.navajo.document.base.BaseNavajoFactoryImpl";
				  impl = new BaseNavajoFactoryImpl();
				  impl.sandboxMode = sbmode;
			  } else {
				  try {
					  impl = (NavajoFactory) Class.forName(name).newInstance();
					  impl.sandboxMode = sbmode;
				  }
				  catch (Exception e) {
					  logger.error("Error: ", e);
				  }
			  }
			  
			  try {
				impl.readTypes();
			} catch (Exception e) {
				logger.error("Error reading types ",e);
				throw new RuntimeException("Could not instantiate NavajoDocument Factory, problem reading navajotypes.xml: " + e.getMessage());
			}
		  }
	  }
	  return impl;
  }
  
  public static void resetImplementation() {
      impl = null;
  }

  /**
   * Get a specific NavajoFactory implementation.
   *
   * @param className
   * @return NavajoFactory instance
   */
  public static NavajoFactory getInstance(String className) {
	  
	  synchronized ( semaphore ) {
		  if (alternativeFactories.get(className) != null) {
			  
			  if ( impl != null ) {
				  String cls = impl.getClass().getName();
				  if (!(className.equals(cls))) {
					  logger.warn("NavajoFactory Warning: Getting instance, but current instance if different. Use resetImplementation.");
				  }
			  }
			  return alternativeFactories.get(className);
			  
		  }
		  try {
			  NavajoFactory alt = (NavajoFactory) Class.forName(className).newInstance();
			  alternativeFactories.put(className, alt);
			  return alt;
		  }
		  catch (Exception e) {
			  logger.error("Error: ", e);
			  return null;
		  }
	  }
  }
  
  /**
   * Clean up static references
   */
  public static void terminate() {
	  for ( Entry<String,NavajoFactory> element :alternativeFactories.entrySet()) {
		if(element.getValue()!=null) {
			element.getValue().shutdown();
		}
	}
//	  Might still leak..
	  alternativeFactories.clear();
	//  alternativeFactories = null;
	  if(impl!=null) {
		  impl.shutdown();
	  }
  }

  public void shutdown() {
	  
  }
  /**
   * Parses a given string of key=value pairs and returns them in a Map
   * @param subType String
   * @return Map
   */

  public Map<String,String> parseSubTypes(String subType) {
    Map<String,String> m = new HashMap<String,String>();
    if (subType == null || "".equals(subType)) {
      return m;
    }
    StringTokenizer st = new StringTokenizer(subType, ",");
    while (st.hasMoreTokens()) {
      String next = st.nextToken();
      int i = next.indexOf("=");
      if (i >= 0) {
        String key = next.substring(0, i);
        String value = next.substring(i + 1, next.length());
        m.put(key, value);
      }
      else {
//        logger.info("--> WARNING: found '" + next +
//            "' subtype, this does not appear to be a key=value pair");
      }
    }
    return m;
  }

  /**
   * Sets the subtype for all the new properties of this type,
   * unless defined otherwise.
   * @param String type
   * @param String subtype
   * todo: Change this into something like addDefaultSubType(String type,String key, String value)
   */
  public void setDefaultSubtypeForType(String type, String subtype) {
    defaultSubTypes.put(type, subtype);
  }

  /**
   * Get the default subtype for a given type
   * @param type String
   * @return String subtype
   */
  public String getDefaultSubtypeForType(String type) {
    return defaultSubTypes.get(type);
  }

//    public static final String[] VALID_DATA_TYPES = new String[] {
//    STRING_PROPERTY,INTEGER_PROPERTY,LONG_PROPERTY,DATE_PROPERTY,FLOAT_PROPERTY,MONEY_PROPERTY,CLOCKTIME_PROPERTY,
//    URL_PROPERTY,MEMO_PROPERTY,BOOLEAN_PROPERTY,POINTS_PROPERTY,DATE_PATTERN_PROPERTY,PASSWORD_PROPERTY,
//    TIPI_PROPERTY,BINARY_PROPERTY
//};

  private ExpressionEvaluator myExpressionEvaluator = null;

  /**
   * Get this factory's ExpressionEvaluator object
   * @return ExpressionEvaluator
   */
  public ExpressionEvaluator getExpressionEvaluator() {
    return myExpressionEvaluator;
  }

  /**
   * Set this factory's ExpressionEvaluator object
   * @param e ExpressionEvaluator
   */
  public void setExpressionEvaluator(ExpressionEvaluator e) {
    myExpressionEvaluator = e;
  }

  /**
   * Used to convert classnames to property types. E.g. java.lang.String -> Property.STRING_PROPERTY
   * It will not return all types. The following types will never be returned, because they do not
   * map 1-1 on java classes:
   * MEMO_PROPERTY, DATE_PATTERN_PROPERTY, PASSWORD_PROPERTY, TIPI_PROPERTY
   * @param String className
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
    if (className.equals("java.lang.Float") || className.equals("float") ||
        className.equals("double")) {
      return Property.FLOAT_PROPERTY;
    }
    if (className.equals("com.dexels.navajo.document.types.ClockTime")) {
      return Property.CLOCKTIME_PROPERTY;
    }
    if (className.equals("java.net.URL")) {
      return Property.URL_PROPERTY;
    }
    if (className.equals("java.lang.Boolean") || className.equals("boolean")) {
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
	  if ( tempDir != null ) {
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
   * @param message String
   * @return NavajoException
   */
  public abstract NavajoException createNavajoException(String message);

  /**
   * Create a NavajoException object with a given Exception object
   * @param e Exception
   * @return NavajoException
   */
  public abstract NavajoException createNavajoException(Throwable e);

  /**
   * Create a NavajoException object with a given message AND Exception object
   * @param e Exception
   * @param message String
   * @return NavajoException
   */
  public abstract NavajoException createNavajoException(String message,Throwable e);

  
  /**
   *  Create a Navjao object from a given InputStream
   *  @param stream InputStream
   */
  public abstract Navajo createNavajo(java.io.InputStream stream);

  /**
   * creates a Navajo object directly from a reader
   * @param r
   * @return
   */
  
  public abstract Navajo createNavajo(Reader r);
	 
  /**
   * creates a Navajo object directly from a JSON reader
   * @param r
   * @return
   */
  
  public abstract Navajo createNavajoJSON(Reader r);
  /**
   * Create a Navajo object from a given Object
   * @param representation Object
   * @return Navajo
   */
  public abstract Navajo createNavajo(Object representation);

  /**
   * Create an emtpy Navajo object
   * @return Navajo
   */
  public abstract Navajo createNavajo();

  /**
   * Create a NavaScript object from a given InputStream
   * @param stream InputStream
   * @return Navajo
   */
  public abstract Navajo createNavaScript(java.io.InputStream stream);

  /**
   * Create a NavaScript object from a given Object
   * @param representation Object
   * @return Navajo
   */
  public abstract Navajo createNavaScript(Object representation);

  /**
   * Create an empty NavaScriptObject
   * @return Navajo
   */
  public abstract Navajo createNavaScript();

  /**
   * Create an Header object for a given Navajo
   * @param n Navajo
   * @param rpcName String
   * @param rpcUser String
   * @param rpcPassword String
   * @param expiration_interval long
   * @return Header
   */
  public abstract Header createHeader(Navajo n, String rpcName, String rpcUser,
                                      String rpcPassword,
                                      long expiration_interval);

  /**
   * Create a Message object from a given Object
   * @param representation Object
   * @return Message
   */
  public abstract Message createMessage(Object representation);

  /**
   * Create a Message object with the given Navajo as parent and with a given name.
   * NOTE: The Message is NOT added to the supplied Navajo object
   * @param n Navajo
   * @param name String
   * @return Message
   */
  public abstract Message createMessage(Navajo n, String name);

  /**
   * Create a Message object of a given type with a given name withe the supplied Navajo object as his parent.
   * NOTE: The Message is NOT added to the supplied Navajo object
   * @param tb Navajo
   * @param name String
   * @param type String
   * @return Message
   */
  public abstract Message createMessage(Navajo tb, String name, String type);

  /**
   * Create a Property object from a given Object
   * @param representation Object
   * @return Property
   */
  public abstract Property createProperty(Object representation);

  /**
   * Create a Property object with the given Navajo as his parent
   * @param tb Navajo
   * @param name String
   * @param cardinality String
   * @param description String
   * @param direction String
   * @throws NavajoException
   * @return Property
   */
  public abstract Property createProperty(Navajo tb, String name,
                                          String cardinality,
                                          String description, String direction) throws
      NavajoException;

  /**
   * Create a Property object with the given Navajo as his parent
   * @param tb Navajo
   * @param name String
   * @param type String
   * @param value String
   * @param length int
   * @param description String
   * @param direction String
   * @throws NavajoException
   * @return Property
   */
  public abstract Property createProperty(Navajo tb, String name, String type,
                                          String value, int length,
                                          String description, String direction) throws
      NavajoException;

  /**
   * Create a Property object with the given Navajo as his parent
   * @param n Navajo
   * @param name String
   * @param type String
   * @param value String
   * @param i int
   * @param desc String
   * @param direction String
   * @param subtype String
   * @throws NavajoException
   * @return Property
   */
  public abstract Property createProperty(Navajo n, String name, String type,
                                          String value, int i, String desc,
                                          String direction, String subtype) throws
      NavajoException;

  /**
   * Create an ExpressionTag object
   * @param tb Navajo
   * @param condition String
   * @param value String
   * @throws NavajoException
   * @return ExpressionTag
   */
  public abstract ExpressionTag createExpression(Navajo tb, String condition,
                                                 String value) throws
      NavajoException;

  /**
   * Create a FieldTag object
   * @param tb Navajo
   * @param condition String
   * @param name String
   * @throws NavajoException
   * @return FieldTag
   */
  public abstract FieldTag createField(Navajo tb, String condition, String name) throws
      NavajoException;

  /**
   * Create a ParamTag object
   * @param tb Navajo
   * @param condition String
   * @param name String
   * @throws NavajoException
   * @return ParamTag
   */
  public abstract ParamTag createParam(Navajo tb, String condition, String name) throws
      NavajoException;

  /**
   * Create a MapTag object
   * @param tb Navajo
   * @param object String
   * @param condition String
   * @throws NavajoException
   * @return MapTag
   */
  public abstract MapTag createMapObject(Navajo tb, String object,
                                         String condition) throws
      NavajoException;

  /**
   * Create a MapTag reference
   * @param tb Navajo
   * @param ref String
   * @param condition String
   * @param filter String
   * @throws NavajoException
   * @return MapTag
   */
  public abstract MapTag createMapRef(Navajo tb, String ref, String condition,
                                      String filter) throws NavajoException;

  /**
   * Create a Selection object
   * @param tb Navajo
   * @param name String
   * @param value String
   * @param selected boolean
   * @return Selection
   */
  public abstract Selection createSelection(Navajo tb, String name,
                                            String value, boolean selected);
  

  /**
   * Create a Selection object
   * @param tb Navajo
   * @param name String
   * @param value String
   * @param selected int, if selected > 0 then selected value is set to true.
   * @return Selection
   */
  public abstract Selection createSelection(Navajo tb, String name,
                                            String value, int selected);
  
  /**
   * Create a Selection object
   * @param tb Navajo
   * @param name String
   * @param value String
   * @param selected int, if selected is boolean and true, set selected, if false unset, if selected is integer > 0  then selected value is set to true.
   * @return Selection
   */
  public abstract Selection createSelection(Navajo tb, String name,
                                            String value, Object selected);
  
  /**
   * Create a DUMMY Selection object
   * @return Selection
   */
  public abstract Selection createDummySelection();

  /**
   * Create a Method object
   * @param tb Navajo
   * @param name String
   * @param server String
   * @return Method
   */
  public abstract Method createMethod(Navajo tb, String name, String server);

  /**
   * Create a Point object
   * @param p Property
   * @throws NavajoException
   * @return Point
   */
  public abstract Point createPoint(Property p) throws NavajoException;

  /**
   * Checks a message using a "definition" message.
   * 
   * @param message, the message to be checked
   * @param entityMessage, the "definition" message
   * @param ignoreUndefinedEntities, ignores properties/messages in input message that are not in entityMessage
   * @return map with problem properties/messages.
   */
  public Map<String,String> checkTypes(Message message, Message entityMessage, boolean ignoreUndefinedEntities) {
		 
	  	Map<String,String> problems = new HashMap<String, String>();
	  	
		Iterator<Property> inputProperties = message.getAllProperties().iterator();
		while ( inputProperties.hasNext() ) {
			Property inputP = inputProperties.next();
			Property entityP = entityMessage.getProperty(inputP.getName());
			if ( !ignoreUndefinedEntities && entityP == null ) {
				problems.put(inputP.getFullPropertyName(), "Unknown property: " + inputP.getName());
			} else if ( entityP != null && entityP.getType().equals(Property.SELECTION_PROPERTY) ) {
				if ( inputP.getValue() != null && !inputP.getValue().equals("") ) {
					boolean found = false;
					for (Selection entityS : entityP.getAllSelections()) {
						if ( entityS.getValue().equals(inputP.getValue() ) ) {
							found = true;
						}
					}
					if (!found) {
						problems.put(inputP.getFullPropertyName(), "Invalid selection option: " + inputP.getValue());
					}
				}
			} else if ( entityP != null && !inputP.getType().equals(entityP.getType()) ) {
				problems.put(inputP.getFullPropertyName(), "Invalid type: " + inputP.getType() + ", expected: " + entityP.getType());
			}
		}
		
		// Other way around: check for missing properties.
		inputProperties = entityMessage.getAllProperties().iterator();
		while ( inputProperties.hasNext() ) {
			Property entityP = inputProperties.next();
			Property inputP = message.getProperty(entityP.getName());
			if ( inputP == null ) {
				problems.put(message.getFullMessageName(), "Missing property: " + entityP.getName());
			} 
		}
		
		Iterator<Message> inputMessages = message.getAllMessages().iterator();
		while ( inputMessages.hasNext() ) {
			Message inputM = inputMessages.next();
			Message entityM = entityMessage.getMessage(inputM.getName());
			if ( !ignoreUndefinedEntities && entityM == null ) {
				problems.put(inputM.getName(), "Unknown message: " + inputM.getName());
			} else if (inputM.isArrayMessage() && entityM.isArrayMessage() && entityM.getDefinitionMessage() != null ) {
				Iterator<Message> children = inputM.getElements().iterator();
				while ( children.hasNext() ) {
					problems.putAll(checkTypes(children.next(), entityM.getDefinitionMessage(), ignoreUndefinedEntities));
				}
			} else if ( !inputM.getType().equals(entityM.getType() ) ) {
				problems.put(inputM.getName(), "Unknown message type: " + inputM.getType() + ". Expected: " + entityM.getType());
			} else {
				problems.putAll(checkTypes(inputM, entityM, ignoreUndefinedEntities));
			}
		}
		
		// Other way around: check for missing messages.
		inputMessages = entityMessage.getAllMessages().iterator();
		while ( inputMessages.hasNext() ) {
			Message entityM = inputMessages.next();
			Message inputM = message.getMessage(entityM.getName());
			if ( inputM == null ) {
				problems.put(message.getFullMessageName(), "Missing message: " + entityM.getName());
			} 
		}
		
		return problems;
	}


public void storeHandle(String name, byte[] data) {
	binaryStorage.put(name,data);
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

public static void main(String[] args){
	try{

		System.setProperty("com.dexels.navajo.DocumentImplementation", "com.dexels.navajo.document.base.BaseNavajoFactoryImpl");
		//	NavajoFactory.getInstance().setExpressionEvaluator(new DefaultExpressionEvaluator());
		Navajo n = NavajoFactory.getInstance().createNavajo();
		
		
		Message m2 = NavajoFactory.getInstance().createMessage(n, "navigation", Message.MSG_TYPE_ARRAY);
		Message e1  = NavajoFactory.getInstance().createMessage(n, "navigation");
		Message e2  = NavajoFactory.getInstance().createMessage(n, "navigation");
		
		Property nw1 = NavajoFactory.getInstance().createProperty(n, "nw", "string", "0", 32, "", Property.DIR_OUT);
		Property nw2 = NavajoFactory.getInstance().createProperty(n, "nw", "string", "0", 32, "", Property.DIR_OUT);
		
		Property l1 = NavajoFactory.getInstance().createProperty(n, "label", "string", "My Shell", 32, "", Property.DIR_OUT);
		Property l2 = NavajoFactory.getInstance().createProperty(n, "label", "string", "HR", 32, "", Property.DIR_OUT);
		
		Property u1 = NavajoFactory.getInstance().createProperty(n, "url", "string", "http://www.dexels.com", 32, "", Property.DIR_OUT);
		Property u2 = NavajoFactory.getInstance().createProperty(n, "url", "string", "http://www.shell.com", 32, "", Property.DIR_OUT);
		
		Property f1 = NavajoFactory.getInstance().createProperty(n, "f", "string", "0", 32, "", Property.DIR_OUT);
		Property f2 = NavajoFactory.getInstance().createProperty(n, "f", "string", "0", 32, "", Property.DIR_OUT);
		
		Property p1 = NavajoFactory.getInstance().createProperty(n, "p", "string", "0", 32, "", Property.DIR_OUT);
		Property p2 = NavajoFactory.getInstance().createProperty(n, "p", "string", "1", 32, "", Property.DIR_OUT);
		
		Property i1 = NavajoFactory.getInstance().createProperty(n, "id", "string", "0", 32, "", Property.DIR_OUT);
		Property i2 = NavajoFactory.getInstance().createProperty(n, "id", "string", "1", 32, "", Property.DIR_OUT);
		
		m2.addElement(e1);
		m2.addElement(e2);
		
		e1.addProperty(nw1);
		e2.addProperty(nw2);
		
		e1.addProperty(l1);
		e2.addProperty(l2);
		
		e1.addProperty(u1);
		e2.addProperty(u2);
		
		e1.addProperty(f1);
		e2.addProperty(f2);
		
		e1.addProperty(p1);
		e2.addProperty(p2);
		
		e1.addProperty(i1);
		e2.addProperty(i2);
		
		

		n.addMessage(m2);

		logger.info("=================================== ORIGNAL TML ==============================");
		n.write(System.err);
		logger.info("==============================================================================\n\n");
		StringWriter sw = new StringWriter();
		n.writeJSONTypeless(sw);
		String json = sw.getBuffer().toString();
		// revert to navajo
		logger.info("==============================logger.infoN ============================");
		logger.info(json);
//		logger.info("==============================================================================\n\n");
//		Navajo x = NavajoFactory.getInstance().createNavajoJSON(new StringReader(json));
//		logger.info("================================= RECONSTRUCTED TML ==========================");
//		x.write(System.err);
//		logger.info("==============================================================================\n\n");
	}catch(Exception e){
		logger.error("Error: ", e);
	}
	
}

public abstract Operation createOperation(Navajo n, String method,
		String service, String entityName, Message extra);

}
