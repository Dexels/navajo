/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.mapping;

import com.dexels.navajo.mapping.compiler.meta.AdapterFieldDependency;

/**
 * Interface to define dependent resource templates.
 * Used in HasDependentResources objects to specify what bean attributes contain dependent resources (like databases, urls, etc.)
 * 
 * @author arjen
 *
 */
public interface DependentResource {

	/**
	 * Return the type of dependent resource.
	 * 
	 * @return
	 */
	public String getType();
	
	/**
	 * value return the name of the field that contains a references to dependent resource(s).
	 * 
	 * @return
	 */
	public String getValue();
	
	public Class<? extends AdapterFieldDependency> getDependencyClass();
	
	
}
