/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.mapping;

public interface HasDependentResources {

	/**
	 * Return a string array of all fields that are used for dependencies on external resources, e.g.
	 * a mail server, a database, another web service, an external web service, an external url, etc.
	 * @return
	 */
	public DependentResource []  getDependentResourceFields();
	
}
