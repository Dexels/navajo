package com.dexels.navajo.mapping.bean;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.mapping.DependentResource;
import com.dexels.navajo.mapping.GenericDependentResource;
import com.dexels.navajo.mapping.HasDependentResources;
import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.mapping.MappingException;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.UserException;

/**
 * This is a special proxy class to wrap/proxy simple POJOs (domain objects). 
 * A bean style (with setters/getters) POJO is expected.
 * It is assumed that sub POJOs (either single or multiple) are also proxied as DomainObjectMapper objects.
 * 
 * @author arjen
 *
 */
public class DomainObjectMapper implements Mappable, HasDependentResources {

	private String objectName;
	private String messageName;
	private String attributeName;
	private String excludedProperties;
	private String inputProperties;
	private Class myClass;
	private Object myObject;
	
	private Navajo myNavajo;
	private Message currentMsg = null;
	private Access myAccess = null;
	
	private HashMap<String,Method> methods = new HashMap<String,Method>();
	private boolean setting = true;
	private HashSet<String> allExcludedProperties = new HashSet<String>();
	
	/**
	 * Empty constructor for creating domain objects (for setting).
	 */
	public DomainObjectMapper() {
	}
	
	/**
	 * Constructor for wrapping existing domain objects (for getting).
	 * 
	 * @param o
	 */
	public DomainObjectMapper(Object o) throws Exception {
		setting = false;
		myObject = o;
	}
	
	/**
	 * Creates an DomainObjectMapper array from a simple POJO array.
	 * 
	 * @param dom
	 * @return
	 * @throws Exception
	 */
	public final static DomainObjectMapper [] createArray(Object [] pojos) throws Exception {
		DomainObjectMapper [] array = new DomainObjectMapper[pojos.length];
		for (int i = 0; i < pojos.length; i++) {
			array[i] = new DomainObjectMapper(pojos[i]);
		}
		return array;
	}
	
	
	 /**
	  * Gets the appropriate 'setter' for an attribute name with parameters signature.
	  * 
	  * @param myClass the class to introspect
	  * @param name name of the attribute
	  * @param parameters type signature for method parameters
	  * @return
	  * @throws MappingException
	  */
	 public final Method setMethodReference(Class myClass, String name, Class [] parameters) throws MappingException {

         java.lang.reflect.Method m = (Method) methods.get(name+parameters.hashCode());

         if (m == null) {
               String methodName = "set" + (name.charAt(0) + "").toUpperCase()
                    + name.substring(1, name.length());

                Class c = this.myObject.getClass();

                java.lang.reflect.Method[] all = c.getMethods();
                for (int i = 0; i < all.length; i++) {
                  if (all[i].getName().equalsIgnoreCase(methodName)) {
                    m = all[i];
                    Class[] inputParameters = m.getParameterTypes();
                    if (inputParameters.length == parameters.length) {
                      for (int j = 0; j < inputParameters.length; j++) {
                        Class myParm = parameters[j];
                        if (inputParameters[j].isAssignableFrom(myParm)) {
                          i = all.length + 1;
                          j = inputParameters.length + 1;
                          break;
                        }
                      }
                    }
                  }
                }
            if (m == null) {
            	throw new MappingException("Could not find setter in class " + myClass.getCanonicalName() + " for attribute: " + name);
            }
            methods.put(name+parameters.hashCode(), m);
       }

      return m;
  }
	
	/**
	 * Returns a method associated with a propertyName getter
	 * 
	 * @param myClass the class to introspect
	 * @param propertyName the attribute to find the getter
	 * @param arguments object array to be passed as method parameters
	 * @return
	 * @throws MappingException
	 */
	private final Method getMethodReference(Class myClass, String propertyName, Object [] arguments) throws MappingException {

		StringBuffer key = new StringBuffer();
		key.append(propertyName);

		// Determine method unique method key:
		Class [] classArray = null;

		if (arguments != null) {
			// Get method with arguments.
			classArray = new Class[arguments.length];
			for (int i = 0; i < arguments.length; i++) {
				classArray[i] = arguments[i].getClass();
				key.append(arguments[i].getClass().getName());
			}
		}
        
		java.lang.reflect.Method m = (Method) methods.get(key.toString());

		if (m == null) {
		
			java.lang.reflect.Method[] all = myClass.getMethods();
			for (int i = 0; i < all.length; i++) {
				if (all[i].getName().equalsIgnoreCase("get"+propertyName) ) {
					m = all[i];
					if ( classArray == null || m.getParameterTypes().equals(classArray)) {
						methods.put(key.toString(), m);
						break;
					}
				}
			}
			if (m == null) {
				throw new MappingException("Could not find getter in class " + myClass.getCanonicalName() + " for attribute: " + propertyName);
			}
		}

		return m;
	}
	
	private Object createObject() throws Exception {
		if ( objectName == null ) {
			throw new Exception("DomainObjectMapper failure. No objectName specified.");
		}
		if ( myObject == null ) {
			myClass = Class.forName(objectName);
			myObject = myClass.newInstance();
		}
		return myObject;
	}
	
	public void kill() {
	}

	public void load(Access access) throws MappableException, UserException {
		myNavajo = access.getInDoc();
		currentMsg = access.getCompiledScript().currentInMsg;
		myAccess = access;
	}

	public void store() throws MappableException, UserException {
		try {
			if ( setting ) {
				mapAllPropertiesToObject();
			} else {
				myClass = myObject.getClass();
				mapObjectToProperties();
			}
		} catch (Exception e) {
			throw new UserException(-1, e.getMessage(), e);
		}
	}

	/**
	 * Automatically maps all 'getters' to a property.
	 * 
	 * Only add property if:
	 *  1. attribute 'getter' does not expect any arguments.
	 *  2. attribute has a 'getter'.
	 *  3. corresponding property does not already exist in message.
	 *  4. corresponding property is not in exclusion list.
	 * @throws Exception
	 */
	private void mapObjectToProperties() throws Exception {
		java.lang.reflect.Method[] all = myClass.getMethods();

		Navajo out = myAccess.getOutputDoc();
		Message currentOutMsg = myAccess.getCurrentOutMessage();

		for ( int i = 0; i < all.length; i++ ) {
			
			String attributeName = all[i].getName().substring(3);
			
			if ( all[i].getParameterTypes().length == 0 && 
			     all[i].getName().startsWith("get") && 
			     !all[i].getName().startsWith("getClass") &&
			     currentOutMsg.getProperty(attributeName) == null &&
			     !isAnExcludedProperty(attributeName) ) {
				
				Object result = all[i].invoke(myObject, null);
				
				if ( result == null || !( result.toString().startsWith("[L") || List.class.isAssignableFrom(result.getClass()) ) ) {
					Property p = NavajoFactory.getInstance().createProperty(out, attributeName, "string", "", 0, "", "", "");
					p.setAnyValue(result);
					p.setDirection(isAnInputProperty(attributeName) ? Property.DIR_IN : Property.DIR_OUT);
					currentOutMsg.addProperty(p);
				}
				
			}
		}
	}

	public void setObjectName(String objectName) throws Exception {
		this.objectName = objectName;
		createObject();
	}

	public void setMessageName(String messageName) {
		this.messageName = messageName;
	}

	public void setAttributeName(String attribute) {
		this.attributeName = attribute;
	}
	
	public void setAttributeValue(Object value) throws UserException {
		
		if ( attributeName == null ) {
			throw new UserException(-1, "Set attribute name before setting its value.");
		}
		try {
			myObject = createObject();
			Method m = setMethodReference(myClass, attributeName, new Class[]{value.getClass()});
			m.invoke(myObject, value);
		} catch (Exception e) {
			throw new UserException(-1, e.getMessage(), e);
		}
		
	}

	/**
	 * Checks whether a property needs to exluded from automatic mapping.
	 * 
	 * @param name
	 * @return
	 */
	private boolean isAnExcludedProperty(String name) {
		if ( allExcludedProperties.size() == 0 ) {
			return false;
		}
		return allExcludedProperties.contains(name.toLowerCase());
	}
	
	/**
	 * Checks whether a property needs to be an input property.
	 * 
	 * @param name
	 * @return
	 */
	private boolean isAnInputProperty(String name) {
		if ( inputProperties == null ) {
			return false;
		}
		String [] inputs = inputProperties.split(";");
		for ( int i = 0 ; i < inputs.length; i++ ) {
			if ( inputs[i].equalsIgnoreCase(name) ) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Automatically maps all properties to appropriate 'setters'.
	 * 
	 * @throws Exception
	 */
	private void mapAllPropertiesToObject() throws Exception {
		createObject();
		Message mapMsg = ( messageName != null ? myNavajo.getMessage(messageName) :  currentMsg );
		if ( mapMsg == null ) {
			if ( messageName != null ) {
				throw new UserException(-1, "Could not find message " + messageName + " in request");
			} else {
				throw new UserException(-1, "No mappable message specified.");
			}
		}
		
		try {
			
			ArrayList<Property> allProperties = mapMsg.getAllProperties();
			for ( int i = 0; i < allProperties.size(); i++ ) {
				Property p = allProperties.get(i);
				if ( !isAnExcludedProperty(p.getName()) ) {
					Method m = setMethodReference(myClass, p.getName(), new Class[]{p.getTypedValue().getClass()});
					m.invoke(myObject, p.getValue());
					//System.err.println("Invoked method: " + m.getName() + " with value " + p.getValue());
				}
			}
		} catch (Exception e) {
			throw new UserException(-1, e.getMessage(), e);
		}
	}

	/**
	 * Calls a 'setter' for a given attribute and a given value.
	 * 
	 * @param name name of the attribute
	 * @param value value to be set
	 * @throws Exception
	 */
	public void setDomainObjectAttribute(String name, Object value) throws Exception {
		setExcludedProperties(name);
		Method m = setMethodReference(myObject.getClass(), name, new Class[]{value.getClass()});
		m.invoke(myObject, value);
	}
	
	/**
	 * Performs a 'getter' on a domainobject attribute. If result is a list, transform it to an array.
	 * This method does not automatically proxy POJOs as a domain object. 
	 * This should be done by the calling class (CompiledScript objects).
	 * 
	 * @param name
	 * @param parameters
	 * @return
	 * @throws Exception
	 */
	public Object getDomainObjectAttribute(String name, Object [] parameters) throws Exception {
		//setExcludedProperties(name);
		Method m = getMethodReference(myObject.getClass(), name, parameters);
		Object result = m.invoke(myObject, parameters);
		if ( List.class.isAssignableFrom(result.getClass()) ) {
			// Is list, cast to array.
			List list = (List) result;
			Object [] pojos = new Object[list.size()];
			Iterator i = list.iterator();
			int index = 0;
			while ( i.hasNext() ) {
				pojos[index++] = i.next();
			}
			return pojos;
		}
		return result;
	}
	
	/**
	 * Returns the proxied POJO.
	 * 
	 * @return
	 * @throws Exception
	 */
	public Object getMyObject() throws Exception {
		return myObject;
	}

	/**
	 * Adds a ;-seperated list of property/attribute names to exclude (both for getting and setting).
	 * Method can be called multiple times.
	 * 
	 * @param excludedProperties
	 */
	public void setExcludedProperties(String excludedProperties) {
		String [] exclusions = excludedProperties.split(";");
		for ( int i = 0 ; i < exclusions.length; i++ ) {
				allExcludedProperties.add(exclusions[i].toLowerCase());
		}
	}

	/**
	 * Sets a ;-seperated list of properties that will be direction="in".
	 * Can only be called once.
	 * 
	 * @param inputProperties
	 */
	public void setInputProperties(String inputProperties) {
		this.inputProperties = inputProperties;
	}

	public DependentResource[] getDependentResourceFields() {
		return new DependentResource[]{new GenericDependentResource("javaobject", "objectName")};
	}
}
