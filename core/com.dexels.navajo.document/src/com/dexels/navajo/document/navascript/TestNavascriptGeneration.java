/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.document.navascript;

import java.io.FileInputStream;

import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.navascript.tags.NavascriptTag;

public class TestNavascriptGeneration {

	public static void main(String [] args) throws Exception {
		
		FileInputStream fis = new FileInputStream("/Users/arjenschoneveld/ProcessAuthenticate.xml");
		NavascriptTag navascript = (NavascriptTag)  NavajoFactory.getInstance().createNavaScript(fis);
		
		navascript.formatNS3(0, System.err);
		
	}
}
