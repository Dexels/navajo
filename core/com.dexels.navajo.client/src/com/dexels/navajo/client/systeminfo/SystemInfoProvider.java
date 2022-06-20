/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.client.systeminfo;

public interface SystemInfoProvider {

	public abstract int getCpuCount();

	public abstract long getMaxMem();

	public abstract String getOs();

	public abstract String getOsVersion();

	public abstract String getJavaVersion();

	public abstract String getOsArch();

	public abstract void init();

}