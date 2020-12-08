/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.server;

import com.dexels.navajo.document.Navajo;

/**
 * This interface can be used to implement a class that can be used to emit
 * events right after the execution of a webservice and BEFORE the execution of the afterwebservice event.
 * 
 * @author arjen
 *
 */
public interface AfterWebServiceEmitter {

	public void emit(Navajo response);
	
}
