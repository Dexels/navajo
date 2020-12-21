/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.document.test;
import org.junit.Test;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Method;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Operation;


public class TestOperation {

	@Test
	public void testOperation() {
		
		NavajoFactory f = NavajoFactory.getInstance();
		
		
		Navajo n = f.createNavajo();
		Message msg = f.createMessage(n, "__Mongo__");
		
		
		Operation o = f.createOperation(n, "PUT", "vla/ProcessInsertPerson", null, "Person", null);
		n.addOperation(o);
		o.setExtraMessage(msg);
		Method m = f.createMethod(n, "vla/ProcessUpdatePerson", null);
		m.addRequired("Apenoot");
		n.addMethod(m);
		
		
		n.write(System.err);
		
	}
}
