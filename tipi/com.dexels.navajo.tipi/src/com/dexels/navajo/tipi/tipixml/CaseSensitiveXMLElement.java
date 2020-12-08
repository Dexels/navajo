/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.tipixml;

import java.util.Hashtable;

public class CaseSensitiveXMLElement extends XMLElement {
	/**
	 * Serialization serial version ID.
	 */
	static final long serialVersionUID = 6685035139346394777L;

	public CaseSensitiveXMLElement() {
		super(new Hashtable<String, char[]>(), true, false);
	}
}