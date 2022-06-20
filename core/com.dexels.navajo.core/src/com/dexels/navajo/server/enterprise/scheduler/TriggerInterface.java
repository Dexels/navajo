/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.server.enterprise.scheduler;

import com.dexels.navajo.script.api.Access;

public interface TriggerInterface extends Listener {

	/**
	 * String representation of the trigger URL.
	 */
	public abstract String getDescription();

	public abstract void activateTrigger();

	/**
	 * Need to be called when trigger is removed, due to task removal, for cleanup purposes.
	 */
	public abstract void removeTrigger();

	public abstract boolean isSingleEvent();

	public abstract void setSingleEvent(boolean b);

	/**
	 * Sets the swap response/request document flag. Used by task for determining
	 * how to call trigger webservice.
	 * @param b
	 */
	public abstract void setSwapInOut(boolean b);

	/**
	 * @return true if swap response/request document flag is set.
	 */
	public abstract boolean swapInOut();

	/**
	 * @return the access object if a webservice caused the trigger.
	 */
	public abstract Access getAccess();

	/**
	 * Set the acces object.
	 * 
	 * @param a the access object.
	 */
	public abstract void setAccess(Access a);

	/**
	 * Gets the task associated with this trigger.
	 * 
	 * @return
	 */
	public abstract TaskInterface getTask();

	/**
	 * Sets the task associated with this trigger.
	 * 
	 * @param t
	 */
	public abstract void setTask(TaskInterface t);

	@Override
	public abstract void setLocal();

	public String getTenant();

}