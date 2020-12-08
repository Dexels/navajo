/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.geo.element;

public class GeoChangeException extends Exception {

	private static final long serialVersionUID = 4254758850208590620L;
	public GeoChangeException(String s) {
		super(s);
	}
	public GeoChangeException(Throwable s) {
		super(s);
	}
	public GeoChangeException(String s,Throwable t) {
		super(s,t);
	}
}
