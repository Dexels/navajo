package com.dexels.navajo.document;

import java.io.*;
import java.util.*;

import com.dexels.navajo.document.base.*;


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
  protected final ArrayList<BinaryProgressListener> myBinaryActivityListeners = new ArrayList<BinaryProgressListener>();

  private final Map<String,byte[]> binaryStorage = new HashMap<String,byte[]>();
  private boolean sandboxMode = false;
  
  private static Object semaphore = new Object();
  
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
				System.err.println("No permission. Using standard document impl.");
				sbmode = true;
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
					  e.printStackTrace();
				  }
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
					  System.err.println("NavajoFactory Warning: Getting instance, but current instance if different. Use resetImplementation.");
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
			  e.printStackTrace();
			  return null;
		  }
	  }
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
        System.err.println("--> WARNING: found '" + next +
            "' subtype, this does not appear to be a key=value pair");
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
  public abstract NavajoException createNavajoException(Exception e);

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
   * Create a LazyMessagePath object
   * @param tb Navajo
   * @param path String
   * @param startIndex int
   * @param endIndex int
   * @param total int
   * @return LazyMessagePath
   */
  public abstract LazyMessagePath createLazyMessagePath(Navajo tb, String path,
      int startIndex, int endIndex, int total);


  /**
   * Create a LazyMessage object
   * @param tb Navajo
   * @param name String
   * @param windowSize int
   * @return LazyMessage
   */
  public abstract LazyMessage createLazyMessage(Navajo tb, String name,
                                                int windowSize);



  /**
   * Fires an binary progress event event to all listeners
   * @param b boolean
   * @param service String
   * @param progress long
   * @param total long
   */
  public void fireBinaryProgress(String service, long progress, long total) {
           for (int i = 0; i < myBinaryActivityListeners.size(); i++) {
      BinaryProgressListener current = myBinaryActivityListeners.get(i);
      current.fireBinaryProgress(service,progress,total);
    }
  }

  /**
   * Add activitylistener
   * @param al ActivityListener
   */
  public final void addBinaryActivityListener(BinaryProgressListener al) {
      myBinaryActivityListeners.add(al);
  }

  /**
   * Remove activitylistener
   * @param al ActivityListener
   */
  public final void removeBinaryActivityListener(BinaryProgressListener al) {
      myBinaryActivityListeners.remove(al);
  }

public void fireBinaryFinished(String message, long expectedLength) {
    for (int i = 0; i < myBinaryActivityListeners.size(); i++) {
        BinaryProgressListener current = myBinaryActivityListeners.get(i);
        current.fireBinaryFinished(message,expectedLength);
      }
   
}

public File createStorageHandle() {
	String handle = "storage_"+Math.random();
	System.err.println("HANDLE: "+handle);
	return new File(handle);
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

public void removeHandle(String name) {
	binaryStorage.remove(name);
}
}
