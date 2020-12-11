/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.document.navascript.tags;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.base.BaseIncludeTagImpl;

public class IncludeTag extends BaseIncludeTagImpl implements NS3Compatible {

	public IncludeTag(Navajo n,String s) {
		super(n, s);
	}
	
	/**
	 * include constants/Globals
	 * 
	 * @param w
	 */
	@Override
	public void writeNS3(int indent, OutputStream w) throws IOException {
		String r = NS3Utils.generateIndent(indent) + "include " + getScript() + "\n\n";
		w.write(r.getBytes());
	}

}
