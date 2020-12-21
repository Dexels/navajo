/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.functions.test;

import org.junit.Before;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.functions.util.FunctionFactoryFactory;
import com.dexels.navajo.functions.util.FunctionFactoryInterface;

public abstract class AbstractFunction  {

	protected FunctionFactoryInterface fff;
	protected ClassLoader cl;
	
	@Before
	public void setUp() throws Exception {
		fff = FunctionFactoryFactory.getInstance();
		cl = getClass().getClassLoader();
	}

	protected Navajo createTestNavajo() throws Exception {
		Navajo doc = NavajoFactory.getInstance().createNavajo();
		Message array = NavajoFactory.getInstance().createMessage(doc, "Aap");
		array.setType(Message.MSG_TYPE_ARRAY);
		Message array1 = NavajoFactory.getInstance().createMessage(doc, "Aap");
		array.addElement(array1);
		doc.addMessage(array);
		Property p = NavajoFactory.getInstance().createProperty(doc, "Noot", Property.INTEGER_PROPERTY, "10", 10, "", "in");
		p.setValue(10);
		array1.addProperty(p);
		
		
		Message single = NavajoFactory.getInstance().createMessage(doc, "Single");
		doc.addMessage(single);
		Property p2 = NavajoFactory.getInstance().createProperty(doc, "Selectie", "1", "", "in");
		p2.addSelection(NavajoFactory.getInstance().createSelection(doc, "key", "value", true));
		single.addProperty(p2);
		Property p3 = NavajoFactory.getInstance().createProperty(doc, "Vuur", Property.INTEGER_PROPERTY, "10", 10, "", "out");
		p3.setValue(10);
		single.addProperty(p3);
		
		return doc;
	}
}
