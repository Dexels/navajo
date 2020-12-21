/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.mapping;

public interface AsyncMappableMXBean {
	
	public String getAccessId();
	public String getWebservice();
	public String getUser();
	public long getStartTime();
	public java.util.Date getStartDate();
	public String  getName();
	public boolean getRunning();
	public boolean getIsFinished();
	public boolean getInterrupt();
	public long getLastAccess();
	public void stop();
	public void interrupt();
	public void resume();
	public boolean isResumed();
	public boolean isStopped();
	public String getClassName();
	public String getStackTrace();
	public String getLockClass();
	public String getLockOwner();
	public String getLockName();
	public boolean isWaiting();
	public String getVERSION();
	
}
