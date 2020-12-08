/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
/*
 * Created on Feb 10, 2006
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.tipi;

import com.dexels.navajo.document.Navajo;

public interface TipiStorageManager {

	public void setContext(TipiContext tc);

	public Navajo getStorageDocument(String id) throws TipiException;

	public void setStorageDocument(String id, Navajo n) throws TipiException;

	public void setInstanceId(String id);

}
