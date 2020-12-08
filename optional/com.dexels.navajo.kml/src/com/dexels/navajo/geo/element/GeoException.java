/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.geo.element;

public class GeoException extends Exception {
	
private static final long serialVersionUID = -6931691935372047818L;
	public GeoException(String s) {
		super(s);
	}
	public GeoException(Throwable s) {
		super(s);
	}
	public GeoException(String s,Throwable t) {
		super(s,t);
	}
}
