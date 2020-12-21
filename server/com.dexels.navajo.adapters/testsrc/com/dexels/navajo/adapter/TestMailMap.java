/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.adapter;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;

public class TestMailMap {

	@Test @Ignore
	public void test() {

		MailMap m = new MailMap();
//		m.setSmtpUser("Secretaris-BBJS05L");
//		m.setSmtpPass("***");
		m.setMailServer("localhost");
		m.setSubject("test2");
		m.setSender("somebody@club.knkv.nl");
		m.setText("boomshakalakka");
		m.setRecipients("NCX26G8@club.knkv.nl");
		boolean b = m.send();
		assertTrue(b);
	}

}
