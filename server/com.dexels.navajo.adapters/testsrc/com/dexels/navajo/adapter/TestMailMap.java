package com.dexels.navajo.adapter;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;

public class TestMailMap {

	@Test @Ignore
	public void test() {

		MailMap m = new MailMap();
		m.setSmtpUser("user");
		m.setSmtpPass("***");
		m.setMailServer("smtp.mail.org");
		m.setSubject("test1");
		m.setSender("sombody@mydomain.com");
		m.setText("boomshakalakka");
		m.setRecipients("somebody@somewhere.com");
		boolean b = m.send();
		assertTrue(b);
	}

}
