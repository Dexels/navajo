package com.dexels.navajo.mapping;

/**
 * This class is used to define fields in adapter that have content which contains dependent 
 * external resources, like e.g. database tables, views, mail server, urls, etc.
 * 
 * @author arjen
 *
 */
@SuppressWarnings({"rawtypes","unchecked"})
public class GenericDependentResource implements DependentResource {

	private String type;
	private String value;
	private Class myDependencyClass;
	
	public static final String SERVICE_DEPENDENCY = "script";
	
	public GenericDependentResource(String type, String value, Class depClass) {
		this.type = type;
		this.value = value;
		this.myDependencyClass = depClass;
	}
	
	public String getType() {
		return type;
	}

	public String getValue() {
		return value;
	}

	public Class getDependencyClass() {
		return myDependencyClass;
	}

}
