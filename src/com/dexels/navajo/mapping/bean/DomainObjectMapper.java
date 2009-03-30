package com.dexels.navajo.mapping.bean;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

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
                throw new MappingException("Could not find method in Mappable object: " + methodName);
            }
            methods.put(name+parameters.hashCode(), m);
       }

      return m;
  }
	
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
				throw new MappingException("Could not find getter for attribute: " + propertyName);
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
			e.printStackTrace(System.err);
			throw new UserException(-1, e.getMessage(), e);
		}
	}

	private void mapObjectToProperties() throws Exception {
		java.lang.reflect.Method[] all = myClass.getMethods();

		Navajo out = myAccess.getOutputDoc();
		Message currentOutMsg = myAccess.getCurrentOutMessage();

		for ( int i = 0; i < all.length; i++ ) {
			
			String attributeName = all[i].getName().substring(3);
			
			if ( all[i].getParameterTypes().length == 0 && 
			     all[i].getName().startsWith("get") && 
			     !all[i].getName().startsWith("getClass") &&
			     !isAnExcludedProperty(attributeName) ) {
				Object result = all[i].invoke(myObject, null);
				
				Property p = NavajoFactory.getInstance().createProperty(out, attributeName, "string", "", 0, "", "", "");
				p.setAnyValue(result);
				p.setDirection(isAnInputProperty(attributeName) ? Property.DIR_IN : Property.DIR_OUT);
				currentOutMsg.addProperty(p);
				
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

	private boolean isAnExcludedProperty(String name) {
		if ( allExcludedProperties.size() == 0 ) {
			return false;
		}
		return allExcludedProperties.contains(name.toLowerCase());
	}
	
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
					System.err.println("Invoked method: " + m.getName() + " with value " + p.getValue());
				}
			}
		} catch (Exception e) {
			throw new UserException(-1, e.getMessage(), e);
		}
	}

	public void setDomainObjectAttribute(String name, Object value) throws Exception {
		setExcludedProperties(name);
		Method m = setMethodReference(myObject.getClass(), name, new Class[]{value.getClass()});
		m.invoke(myObject, value);
	}
	
	public Object getDomainObjectAttribute(String name, Object [] parameters) throws Exception {
		setExcludedProperties(name);
		Method m = getMethodReference(myObject.getClass(), name, parameters);
		return m.invoke(myObject, parameters);
	}
	
	public Object getMyObject() throws Exception {
		return myObject;
	}

	public void setExcludedProperties(String excludedProperties) {
		String [] exclusions = excludedProperties.split(";");
		for ( int i = 0 ; i < exclusions.length; i++ ) {
				allExcludedProperties.add(exclusions[i].toLowerCase());
		}
	}

	public void setInputProperties(String inputProperties) {
		this.inputProperties = inputProperties;
	}

	public DependentResource[] getDependentResourceFields() {
		return new DependentResource[]{new GenericDependentResource("javaobject", "objectName")};
	}
}
