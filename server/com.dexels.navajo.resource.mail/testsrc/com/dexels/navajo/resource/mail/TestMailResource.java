package com.dexels.navajo.resource.mail;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.dexels.navajo.adapter.mailmap.AttachementMap;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.resource.mail.impl.ResourceComponent;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.MappableException;
import com.dexels.navajo.script.api.UserException;

public class TestMailResource {

	// TODO find unit test smtp server
	@Before @Ignore
	public void setUp() {
		MailResourceFactory factory = new MailResourceFactory();
		ResourceComponent component = new ResourceComponent();
		Map<String,Object> settings = new HashMap<>();
		settings.put("name", "junit.mail");
		settings.put("host", "smtp.gmail.com");
		settings.put("user", "dexels@gmail.com");
		settings.put("password", getPassword());
		settings.put("encrypt", true);
		component.activate(settings);
		factory.addMailResource(component, settings);
		factory.activate();

		
	}
	
	private String getPassword() {
		return System.getenv().get("test.mail.password");
	}

	@After
	public void tearDown() {
		MailResourceFactory.getInstance().deactivate();	
	}

	@Test
	public void testSimple() throws MappableException {
		ResourceMailAdapter rma = new ResourceMailAdapter();
        try {
            rma.load(new Access());
            rma.setRecipients("dexels@gmail.com");
            rma.setSender("dexels@gmail.com");
            rma.setSubject("Mail at " + new Date());
            rma.setResourceName("junit.mail");
            rma.store();
        } catch (UserException e) {
            Assert.assertEquals("javax.mail.AuthenticationFailedException", e.getCause().toString());
        }
        
	}

	@Test @Ignore
    public void testAttach() throws MappableException, UserException {
        ResourceMailAdapter rma = new ResourceMailAdapter();
        try {
            rma.load(new Access());
            rma.setRecipients("dexels@gmail.com");
            rma.setSender("dexels@gmail.com");
            rma.setSubject("Attachment at " + new Date());
            AttachementMap am = new AttachementMap();
            am.setAttachContentType("image/jpeg");
            am.setAttachContentDisposition("inline");
            am.setAttachFileContent(new Binary(getClass().getResourceAsStream("monkey.jpeg")));
            rma.setAttachment(am);
            rma.setResourceName("junit.mail");
            rma.store();
        } catch (UserException e) {
            Assert.assertEquals("javax.mail.AuthenticationFailedException", e.getCause().toString());
        }
    }
}
