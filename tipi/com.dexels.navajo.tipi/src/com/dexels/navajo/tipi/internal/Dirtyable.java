/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.internal;

public interface Dirtyable {
	
	/**
	 * Indicates if the object has been modified.
	 * @return {@code TRUE} if the object has been modified.
	 */
	public Boolean isDirty();

	/**
	 * Sets the dirty state of an object.
	 * @param b A {@code Boolean} that indicates whether the state is dirty or not.
	 */
	public void setDirty(Boolean b);
}
