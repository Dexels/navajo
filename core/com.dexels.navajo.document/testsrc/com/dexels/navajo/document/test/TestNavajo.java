/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.document.test;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Method;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.Selection;

public class TestNavajo {
  NavajoDocumentTestFicture navajodocumenttestfictureInst = new NavajoDocumentTestFicture();
  private Navajo testDoc = null;
 
  @Before
  public void setUp() {
    navajodocumenttestfictureInst.setUp();
    testDoc = navajodocumenttestfictureInst.testDoc;
  }

  @After
  public void tearDown() {
    navajodocumenttestfictureInst.tearDown();
  }

  @Test
  public void testAddMessage() {
    Message msg = NavajoFactory.getInstance().createMessage(testDoc, "extramsg");
    testDoc.addMessage(msg);
    Message result = testDoc.getMessage("extramsg");
    Assert.assertNotNull(result);
    Assert.assertEquals(msg.getName(), result.getName());
  }

  @Test
  public void testAddMessage1() {
    // NOT VERY IMPORTANT: OVERWRITING OF MESSAGES SHOULD ALWAYS BE ON.
  }

  @Test
  public void testAddMethod() {
    Method m = NavajoFactory.getInstance().createMethod(testDoc, "mymethod", "Postman");
    testDoc.addMethod(m);
    Method result = testDoc.getMethod("mymethod");
    Assert.assertNotNull(result);
    Assert.assertEquals("mymethod", result.getName());
    Assert.assertEquals("Postman", result.getServer());
  }

  @Test
  public void testAppendDocBuffer() {
    Navajo extra = NavajoFactory.getInstance().createNavajo();
    Message m = NavajoFactory.getInstance().createMessage(extra, "appendedmessage");
    Property p = NavajoFactory.getInstance().createProperty(extra, "propje", Property.STRING_PROPERTY, "", 0, "", "");
    m.addProperty(p);
    extra.addMessage(m);
    Message m2 = NavajoFactory.getInstance().createMessage(extra, "bliep");
    extra.addMessage(m2);
    testDoc.appendDocBuffer(extra.getMessageBuffer());
    Message result = testDoc.getMessage("appendedmessage");
    Assert.assertNotNull(result);
    Assert.assertEquals("appendedmessage", result.getName());
  }

  @Test
  public void testMerge() {
	  // testmessage
	  Navajo extra = NavajoFactory.getInstance().createNavajo();
	  Message m = NavajoFactory.getInstance().createMessage(extra, "testmessage");
	  Property p = NavajoFactory.getInstance().createProperty(extra, "extrapropje", Property.STRING_PROPERTY, "", 0, "", "");
	  Property p2 = NavajoFactory.getInstance().createProperty(extra, "stringprop", Property.STRING_PROPERTY, "SUPERNAVAJO", 0, "", "");
	  m.addProperty(p);
	  m.addProperty(p2);
	  extra.addMessage(m);
	  
	  Message m2 = NavajoFactory.getInstance().createMessage(extra, "testmessage_sub2");
	  m.addMessage(m2);
	  p = NavajoFactory.getInstance().createProperty(extra, "nogeenextrapropje", Property.STRING_PROPERTY, "", 0, "", "");
	  m2.addProperty(p);
	  
	  m = NavajoFactory.getInstance().createMessage(extra, "testmessage_extra");
	  p = NavajoFactory.getInstance().createProperty(extra, "extrapropje", Property.STRING_PROPERTY, "", 0, "", "");
	  m.addProperty(p);
	  extra.addMessage(m);
	  
	  // Before merge assert value of stringprop.
	  Assert.assertEquals("navajo", testDoc.getProperty("/testmessage/stringprop").getValue());
	  
	  testDoc.merge(extra);
	
	  Property assertprop = testDoc.getProperty("/testmessage/extrapropje");
	  Assert.assertNotNull(assertprop);
	  
	  assertprop = testDoc.getProperty("/testmessage/integerprop");
	  Assert.assertNotNull(assertprop);
	  
	  // After merge assert value of stringprop.
	  Assert.assertEquals("SUPERNAVAJO", testDoc.getProperty("/testmessage/stringprop").getValue());
	    
  }
  
  @Test
  public void testClearAllSelections() {
    Property sel1 = NavajoFactory.getInstance().createProperty(testDoc, "sel1", "+", "", Property.DIR_IN);
    Property sel2 = NavajoFactory.getInstance().createProperty(testDoc, "sel2", "+", "", Property.DIR_IN);
    testDoc.getMessage("testmessage").addProperty(sel1);
    testDoc.getMessage("testmessage").getMessage("testmessage_sub1").addProperty(sel2);
    for (int i = 0; i < 5; i++) {
      Selection s = NavajoFactory.getInstance().createSelection(testDoc, "sel"+i, i+"", true);
      sel1.addSelection(s);
      Selection s2 = NavajoFactory.getInstance().createSelection(testDoc, "selectie"+i, i+"", true);
      sel2.addSelection(s2);
    }
    List<Selection> all = null;

    all = testDoc.getMessage("testmessage").getProperty("sel1").getAllSelectedSelections();
    Assert.assertEquals(5, all.size());
    all = testDoc.getMessage("testmessage").getMessage("testmessage_sub1").getProperty("sel2").getAllSelectedSelections();
    Assert.assertEquals(5, all.size());

    testDoc.clearAllSelections();

    all = testDoc.getMessage("testmessage").getProperty("sel1").getAllSelectedSelections();
    Assert.assertEquals(0, all.size());
    all = testDoc.getMessage("testmessage").getProperty("sel1").getAllSelections();
    Assert.assertEquals(5, all.size());
    all = testDoc.getMessage("testmessage").getMessage("testmessage_sub1").getProperty("sel2").getAllSelectedSelections();
    Assert.assertEquals(0, all.size());
    all = testDoc.getMessage("testmessage").getMessage("testmessage_sub1").getProperty("sel2").getAllSelections();
    Assert.assertEquals(5, all.size());
  }

  @Test
  public void testContains() {
  }
  @Test
  public void testCopyMessage() {
  }
  @Test
  public void testCopyMessage1() {
  }
  @Test
  public void testCopyMethod() {
  }
  @Test
  public void testCopyMethod1() {
  }
  @Test
  public void testCreateDocBuffer() {
 }
  @Test
  public void testCreateHeader() {
  }
  @Test
  public void testGetAllMessages() {
  }
  @Test
  public void testGetAllMessages1() {
  }
  @Test
  public void testGetAllMethods() {
  }
  @Test
  public void testGetAllMethods1() {
  }
  @Test
  public void testGetCurrentActions() {
  }
  @Test
  public void testGetCurrentMessages() {
  }
  @Test
  public void testGetErrorDescription() {
  }
  @Test
  public void testGetErrorNumber() {
  }
  @Test
  public void testGetMessage() {
  }
  @Test
  public void testGetMessage1() {
  }
  @Test
  public void testGetMessage2() {
 }
  @Test
  public void testGetMessage3() {
  }
  @Test
  public void testGetMessageBuffer() {
 }
  @Test
  public void testGetMessages() {

  }
  @Test
  public void testGetMethod() {
  }
  @Test
  public void testGetProperties() {

  }
  @Test
  public void testGetProperty() {
  }
  @Test
  public void testGetSelection() {
  }
  @Test
  public void testMain() {
  }
  @Test
  public void testPersistenceKey() {
  }
  @Test
  public void testRemoveMessage() {
  }
  @Test
  public void testRemoveMessage1() {
  }
  @Test
  public void testSetErrorDescription() {
  }
  @Test
  public void testSetErrorNumber() {
  }
  @Test
  public void testToString() {
  }
  @Test
  public void testWriteDocument() {
  }
  @Test
  public void testWriteMessage() {
  }
  
	@Test
	public void testRemoveInternalMessages() throws Exception {
		Navajo doc = NavajoFactory.getInstance().createNavajo();
		Message globals = NavajoFactory.getInstance().createMessage(doc, "__globals__");
		Message parms = NavajoFactory.getInstance().createMessage(doc, "__parms__");
		Message something = NavajoFactory.getInstance().createMessage(doc, "something");
		doc.addMessage(globals);
		doc.addMessage(something);
		doc.addMessage(parms);
		Assert.assertNotNull(doc.getMessage("__parms__"));
		Assert.assertNotNull(doc.getMessage("__globals__"));
		doc.removeInternalMessages();
		Assert.assertNull(doc.getMessage("__parms__"));
		Assert.assertNull(doc.getMessage("__globals__"));
		Assert.assertNotNull(doc.getMessage("something"));
	}
}
