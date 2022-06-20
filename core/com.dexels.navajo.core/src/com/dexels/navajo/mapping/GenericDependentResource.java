/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.mapping;

/**
 * This class is used to define fields in adapter that have content which contains dependent 
 * external resources, like e.g. database tables, views, mail server, urls, etc.
 * 
 * @author arjen
 *
 */
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
	
	@Override
	public String getType() {
		return type;
	}

	@Override
	public String getValue() {
		return value;
	}

	@Override
	public Class getDependencyClass() {
		return myDependencyClass;
	}

}
