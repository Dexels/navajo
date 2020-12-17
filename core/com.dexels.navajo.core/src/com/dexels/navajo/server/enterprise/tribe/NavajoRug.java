/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.server.enterprise.tribe;

import java.io.Serializable;

import com.dexels.navajo.document.Navajo;

/**
 * Interface to wrap Navajo into some container for transportation purposes.
 * 
 * @author arjenschoneveld
 *
 */
public interface NavajoRug extends Serializable {

	public Navajo getNavajo();
	
}
