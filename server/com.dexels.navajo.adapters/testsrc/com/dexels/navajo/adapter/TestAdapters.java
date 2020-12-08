/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.adapter;

import java.io.File;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.dexels.navajo.adapter.filemap.FileLineMap;
import com.dexels.navajo.adapter.messagemap.ResultMessage;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.MappableException;
import com.dexels.navajo.script.api.UserException;

public class TestAdapters {

	@Test
	public void testFileMap() throws IOException, MappableException, UserException {
		FileMap fm = new FileMap();
		FileLineMap[] flm = new FileLineMap[2];
		flm[0] = new FileLineMap();
		flm[0].setLine("apenoot");
		flm[1] = new FileLineMap();
		flm[1].setLine("kibbeling");
		fm.setLines(flm);
		File f = File.createTempFile("test", ".txt");
		fm.setFileName(f.getAbsolutePath());
		fm.store();
		Assert.assertEquals(18, f.length());
		f.deleteOnExit();
	}
	
	
	// TODO Add asserts
	@Test
	public void testMessageMap() throws UserException, MappableException  {
		Navajo out = NavajoFactory.getInstance().createNavajo();
		Message msg1 = NavajoFactory.getInstance().createMessage(out, "message1");
		msg1.setType("array");
		Message msg2 = NavajoFactory.getInstance().createMessage(out, "message2");
		msg2.setType("array");
		out.addMessage(msg1);
		out.addMessage(msg2);

		for (int i = 0; i < 4; i++) {
			Message m1 = NavajoFactory.getInstance().createMessage(out, "message1");
			Message m2 = NavajoFactory.getInstance().createMessage(out, "message2");
			msg1.addMessage(m1);
			msg2.addMessage(m2);

			Property p;

			p = NavajoFactory.getInstance().createProperty(out, "propje1", Property.STRING_PROPERTY, ""+3*i, 0, "", "");
			m1.addProperty(p);
			p = NavajoFactory.getInstance().createProperty(out, "propje2", Property.STRING_PROPERTY, ""+8*i, 0, "", "");
			m1.addProperty(p);
			p = NavajoFactory.getInstance().createProperty(out, "propje3", Property.STRING_PROPERTY, "propjes"+23*i, 0, "", "");
			m1.addProperty(p);

			p = NavajoFactory.getInstance().createProperty(out, "blieblab", Property.STRING_PROPERTY, ""+3*i, 0, "", "");
			m2.addProperty(p);
			p = NavajoFactory.getInstance().createProperty(out, "apenoot2", Property.STRING_PROPERTY, "apenoot"+8*i, 0, "", "");
			m2.addProperty(p);
			p = NavajoFactory.getInstance().createProperty(out, "apfelkorn", Property.STRING_PROPERTY, "apfelkorn"+23*i, 0, "", "");
			m2.addProperty(p);

		}

		Property p;
		Message m1 = NavajoFactory.getInstance().createMessage(out, "message1");
		msg1.addMessage(m1);
		p = NavajoFactory.getInstance().createProperty(out, "propje1", Property.STRING_PROPERTY, "343", 0, "", "");
		m1.addProperty(p);
		p = NavajoFactory.getInstance().createProperty(out, "propje2", Property.STRING_PROPERTY, "12321", 0, "", "");
		m1.addProperty(p);
		p = NavajoFactory.getInstance().createProperty(out, "propje3", Property.STRING_PROPERTY, "propjes2321", 0, "", "");
		m1.addProperty(p);

		// Additional m2.
		Message m2 = NavajoFactory.getInstance().createMessage(out, "message2");
		msg2.addMessage(m2);
		p = NavajoFactory.getInstance().createProperty(out, "blieblab", Property.STRING_PROPERTY, "0", 0, "", "");
		m2.addProperty(p);
		p = NavajoFactory.getInstance().createProperty(out, "apenoot2", Property.STRING_PROPERTY, "12321", 0, "", "");
		m2.addProperty(p);
		p = NavajoFactory.getInstance().createProperty(out, "apfelkorn", Property.STRING_PROPERTY, "propjes2321", 0, "", "");
		m2.addProperty(p);

		Access a = new Access();
		a.setOutputDoc(out);

		MessageMap mm = new MessageMap();
		mm.load(a);
		mm.setSuppressProperties("propje3");
		mm.setJoinMessage1("message1");
		mm.setJoinMessage2("message2");
		mm.setJoinCondition("propje1=blieblab");
		mm.setJoinType("inner");

		Message resultMessage = NavajoFactory.getInstance().createMessage(out, "ResultingMessage");
		resultMessage.setType("array");
		out.addMessage(resultMessage);

		a.setCurrentOutMessage(resultMessage);

		ResultMessage [] result = mm.getResultMessage();
		for (int i = 0; i < result.length; i++) {
			result[i].load(a);
			result[i].store();
		}
		a.setCurrentOutMessage(null);
		mm.store();
	}

}
