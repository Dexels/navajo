/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.resource.http;

public class HttpElement {

	public final String name;
	public final String type;
	public final long size;
	public final String hash;
	
	public HttpElement(String name, String type, long size, String hash) {
		this.name = name;
		this.type = type;
		this.size = size;
		this.hash = hash;
	}

}
