/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package org.lobobrowser.html.gui;

import org.lobobrowser.html.domimpl.*;

class DocumentNotification {
	public static final int LOOK = 0;
	public static final int POSITION = 1;
	public static final int SIZE = 2;
	public static final int GENERIC = 3;

	public final int type;
	public final NodeImpl node;
	
	public DocumentNotification(int type, NodeImpl node) {
		this.type = type;
		this.node = node;
	}
	
	public String toString() {
		return "DocumentNotification[type=" + this.type + ",node=" + this.node + "]";
	}
}
