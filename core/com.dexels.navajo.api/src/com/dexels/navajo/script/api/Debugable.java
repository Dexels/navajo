/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.script.api;

/**
 * Adapters that implement the Debugable interface support automatic enabling of debug flags when
 * full debug is set for webservice.
 *  
 * @author arjen
 *
 */
public interface Debugable {

	public void setDebug(boolean b);
	public boolean getDebug();
	
}
