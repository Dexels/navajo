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
