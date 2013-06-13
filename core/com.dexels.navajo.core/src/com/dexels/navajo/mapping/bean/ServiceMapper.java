package com.dexels.navajo.mapping.bean;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.Mappable;
import com.dexels.navajo.script.api.MappableException;
import com.dexels.navajo.script.api.UserException;
import com.dexels.navajo.server.DispatcherFactory;

/**
 * ServiceMapper can be used to call singleton service class methods. Domain object arguments are proxied
 * by a DomainObjectMapper object. The result of a method can either be a proxied domain object or a simple result.
 * 
 * @author arjen
 *
 */
public class ServiceMapper implements Mappable {

	protected String serviceClass;
	protected String serviceMethod;
	
	private List<Object> methodParameters = new ArrayList<Object>();
	private Object result;
	
//	private Access myAccess;
	
	/**
	 * Get a instance of the specified service class.
	 * This method can be overwritten in sub classes of ServiceMapper.
	 * 
	 * @return
	 * @throws Exception
	 */
	protected Object getServiceObject() throws Exception {
		Class c = null;
		if ( DispatcherFactory.getInstance() != null ) {
			c = Class.forName(serviceClass, true, DispatcherFactory.getInstance().getNavajoConfig().getClassloader());
		} else {
			c = Class.forName(serviceClass);
		}
		return c.newInstance();
	}
	
	private String listParameterTypes(Class [] params) {
		StringBuffer sb = new StringBuffer();
		for (int j = 0; j < params.length; j++) {
			sb.append(params[j].getName());
		}
		return sb.toString();
	}
	
	private void listAllMethods() throws Exception {
		Method [] am = getServiceObject().getClass().getMethods();
		for (int i = 0; i < am.length; i++) {
			System.err.println(am[i].getName() + ", parameters: " + listParameterTypes(am[i].getParameterTypes()));
		}
	}
	
	/**
	 * Invokes the specified method of the specified service class using the specified parameters.
	 * 
	 * @param b
	 * @throws Exception
	 */
	public void setInvoke(boolean b) throws Exception {
		Object o = getServiceObject();
		Iterator i = methodParameters.iterator();
		Class [] parameterTypes = new Class[methodParameters.size()];
		Object [] objectArray = new Object[methodParameters.size()];
		int index = 0;
		while ( i.hasNext() ) {
			Object dom = i.next();
			if ( dom instanceof DomainObjectMapper ) {
				dom = ((DomainObjectMapper) dom).getMyObject();
			}
			objectArray[index] = dom;
			parameterTypes[index++] = dom.getClass();
		}
		Method m = o.getClass().getMethod(serviceMethod, parameterTypes);
		result = m.invoke(o, objectArray);
	}
	
	/**
	 * Return a simple (non domain-object) result of the invoked method.
	 * 
	 * @return
	 */
	public Object getResult() {
		return result;
	}
	
	/**
	 * Returns a domain object as a result of the service method call.
	 * If result was null. Return an instance of Object.
	 * 
	 * @return
	 * @throws Exception
	 */
	public DomainObjectMapper getDomainObjectResult() throws Exception {
		return new DomainObjectMapper((result != null ? result : new Object()));
	}
	
	/**
	 * Returns an array of domain objects as a result of the service method call.
	 * An array is returned if the method return type of the service is either an array or a list.
	 * 
	 * @return
	 * @throws Exception
	 */
	public DomainObjectMapper [] getDomainObjectResults() throws Exception {
		if ( result == null ) {
			return null;
		}
		Object [] results;
		DomainObjectMapper [] doms = null;
		if ( result.toString().startsWith("[L") ) {
			results = (Object []) result;
			doms = new DomainObjectMapper[results.length];
			for ( int i = 0; i < results.length; i++ ) {
				doms[i] = new DomainObjectMapper(results[i]);
			}
		} else if ( List.class.isAssignableFrom(result.getClass())) {
			List list = (List) result;
			doms = new DomainObjectMapper[list.size()];
			Iterator i = list.iterator();
			int index = 0;
			while ( i.hasNext() ) {
				Object o = i.next();
				doms[index++] = new DomainObjectMapper(o);
			}
		}
		return doms;
	}
	
	/**
	 * Add a simple (non domain object class) parameter (String, Integer, Float, Double, Date, Boolean, etc.)
	 */
	public void setAddParameter(Object o) {
		//System.err.println("addParameter: " + o.getClass());
		methodParameters.add(o);
	}
	
	/**
	 * Add a domain object as a parameter.
	 *  
	 * @param dom
	 */
	public void setAddDomainObject(DomainObjectMapper dom) {
		methodParameters.add(dom);
	}
	
	/**
	 * Set the service class to be used.
	 * 
	 * @param s
	 */
	public void setServiceClass(String s) {
		this.serviceClass = s;
	}
	
	/**
	 * Sets the service class method that needs to be invoked.
	 * 
	 * @param s
	 */
	public void setServiceMethod(String s) {
		this.serviceMethod = s;
	}
	
	/**
	 * Methods from Mappable interface, not yet used.
	 */
	
	public void kill() {
	}

	public void load(Access access) throws MappableException, UserException {
	}

	public void store() throws MappableException, UserException {
	}

	public static void main(String [] args) throws Exception {
		
		ServiceMapper sm = new ServiceMapper();
		sm.setServiceClass("com.dexels.navajo.mapping.bean.TestService");
		sm.setServiceMethod("mergeBeans");
		sm.setAddDomainObject(new DomainObjectMapper(new TestBean()));
		sm.setAddDomainObject(new DomainObjectMapper(new TestBean()));
		sm.setInvoke(true);
		
		
		System.err.println("Next...");
		
		sm = new ServiceMapper();
		sm.setServiceClass("com.dexels.navajo.mapping.bean.TestService");
		sm.setServiceMethod("getABean");
		sm.setAddParameter("Arjen");
		sm.setInvoke(true);
		DomainObjectMapper result = sm.getDomainObjectResult();
		System.err.println("result = " + result.getMyObject());
	
		System.err.println("Again...");
		
		sm = new ServiceMapper();
		sm.setServiceClass("com.dexels.navajo.mapping.bean.TestService");
		sm.setServiceMethod("getTestBeans");
		sm.setAddParameter(new Integer(2));
		sm.setInvoke(true);
		DomainObjectMapper [] results = sm.getDomainObjectResults(); 
	
		for ( int i = 0; i < results.length; i++ ) {
			System.err.println("result[" + i + "]=" + results[i].getMyObject());
		}
		
		System.err.println("Try using Lists...");
		
		sm = new ServiceMapper();
		sm.setServiceClass("com.dexels.navajo.mapping.bean.TestService");
		sm.setServiceMethod("getTestBeansAsList");
		sm.setAddParameter(new Integer(2));
		sm.setInvoke(true);
		results = sm.getDomainObjectResults(); 
	
		for ( int i = 0; i < results.length; i++ ) {
			System.err.println("LIST result[" + i + "]=" + results[i].getMyObject());
		}
		
		System.err.println("Try something simple...");
		
		sm = new ServiceMapper();
		sm.setServiceClass("com.dexels.navajo.mapping.bean.TestService");
		sm.setServiceMethod("getStupid");
		sm.setAddParameter(2);
		sm.setInvoke(true);
		Object or = sm.getResult(); 
		System.err.println("or = " + or);
		
		sm.listAllMethods();
	}
	
} 
