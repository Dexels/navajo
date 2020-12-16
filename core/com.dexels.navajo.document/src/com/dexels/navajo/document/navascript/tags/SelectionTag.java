/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
 */
package com.dexels.navajo.document.navascript.tags;

import java.io.IOException;
import java.io.OutputStream;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.base.BaseSelectionImpl;

public class SelectionTag extends BaseSelectionImpl implements NS3Compatible {

	public SelectionTag(Navajo n, String name, String value, boolean isSelected) {
		super(n, name, value, isSelected);
	}

	@Override
	public void formatNS3(int indent, OutputStream w) throws IOException {
		w.write((NS3Utils.generateIndent(indent) + "option\n").getBytes());
	}

}
