
import junit.framework.*;

import java.io.StringWriter;
import java.util.*;

import com.dexels.navajo.document.*;

public class TestMessage extends TestCase {

  NavajoDocumentTestFicture navajodocumenttestfictureInst = new NavajoDocumentTestFicture(this);
  private Navajo testDoc = null;

  public TestMessage(String s) {
    super(s);
  }

  protected void setUp() {
	System.setProperty("com.dexels.navajo.DocumentImplementation", "com.dexels.navajo.document.base.BaseNavajoFactoryImpl");
	//System.setProperty("com.dexels.navajo.DocumentImplementation", "com.dexels.navajo.document.jaxpimpl.NavajoFactoryImpl");
	    navajodocumenttestfictureInst.setUp();
    testDoc = navajodocumenttestfictureInst.testDoc;
  }

  protected void tearDown() {
    navajodocumenttestfictureInst.tearDown();
    NavajoFactory.resetImplementation();
  }

  public void testAddMessage() {
    Message msg = NavajoFactory.getInstance().createMessage(testDoc, "testmsg");
    Message sub = NavajoFactory.getInstance().createMessage(testDoc, "submsg");
    Message subsub = NavajoFactory.getInstance().createMessage(testDoc, "subsubmsg");
    msg.addMessage(sub);
    sub.addMessage(subsub);

    Message r = msg.getMessage("submsg");
    //Assert.assertNotNull(r.getRef());
    Assert.assertEquals("submsg", r.getName());
    Message r2 = r.getMessage("subsubmsg");
    Assert.assertNotNull(r2);

    Message sub2 = NavajoFactory.getInstance().createMessage(testDoc, "submsg");
    msg.addMessage(sub2);
    r = msg.getMessage("submsg");
    //Assert.assertNotNull(r.getRef());
    //Assert.assertEquals(r.getRef(), sub2.getRef());
    r2 = r.getMessage("subsubmsg");
    Assert.assertNull(r2);
    // Check robustness.
    sub2.addMessage(null);
  }

  public void testAddMessage1() {

    // Same as before only now messages with name are not overwritten!
    Message msg = NavajoFactory.getInstance().createMessage(testDoc, "testmsg");
    Message sub = NavajoFactory.getInstance().createMessage(testDoc, "submsg");
    Message subsub = NavajoFactory.getInstance().createMessage(testDoc, "subsubmsg");
    msg.addMessage(sub, false);
    sub.addMessage(subsub, false);

    Message r = msg.getMessage("submsg");
    //Assert.assertNotNull(r.getRef());
    Assert.assertEquals("submsg", r.getName());
    Message r2 = r.getMessage("subsubmsg");
    Assert.assertNotNull(r2);

    Message sub2 = NavajoFactory.getInstance().createMessage(testDoc, "submsg");
    msg.addMessage(sub2, false);
    r = msg.getMessage("submsg");
    //Assert.assertNotNull(r.getRef());
    //Assert.assertEquals(r.getRef(), sub.getRef());
    r2 = r.getMessage("subsubmsg");
    Assert.assertNotNull(r2);

  }

  public void testAddProperty() throws NavajoException {
      Message m = testDoc.getMessage("testmessage");
      Property p = NavajoFactory.getInstance().createProperty(testDoc, "testprop", Property.STRING_PROPERTY, "navajo rules", 120, "", Property.DIR_OUT);
      m.addProperty(p);
      Property p2 = m.getProperty("testprop");
      Assert.assertEquals("testprop", p2.getName());
      Assert.assertEquals("navajo rules", p2.getValue());
      Property p3 =  NavajoFactory.getInstance().createProperty(testDoc, "testprop", Property.STRING_PROPERTY, "navajo rules big time", 120, "", Property.DIR_OUT);
      m.addProperty(p3);
      p2 = m.getProperty("testprop");
      Assert.assertEquals("navajo rules big time", p2.getValue());
      // Check robustness.
      try {
		m.addProperty(null);
		Assert.fail();
      } catch (NullPointerException e) {
		// ok
	}
  }

  public void testContains() {
    Message m = testDoc.getMessage("testmessage");
    boolean b = m.contains("testprop1");
    Assert.assertTrue(b);
    b = m.contains("testprop2");
    Assert.assertTrue(!b);
  }

  public void testCreate() {
    Message m = NavajoFactory.getInstance().createMessage(testDoc, "bliep");
    Assert.assertNotNull(m);
    Assert.assertEquals("bliep", m.getName());
  }

  public void testGetParentMessage() {
    Message m = testDoc.getMessage("testmessage/testmessage_sub1");
    Assert.assertEquals("testmessage_sub1", m.getName());
    Message p = m.getParentMessage();
    Assert.assertEquals("testmessage", p.getName());
  }

  public void testGetAllMessages() {
    Message m = testDoc.getMessage("testmessage");
    ArrayList all = m.getAllMessages();
    Assert.assertEquals(3, all.size());
    Assert.assertEquals("testmessage_sub1", ((Message) all.get(0)).getName());
    Assert.assertEquals("testmessage_sub2", ((Message) all.get(1)).getName());
    Assert.assertEquals("testmessage_sub3", ((Message) all.get(2)).getName());
    m = testDoc.getMessage("testmessage2");
    all = m.getAllMessages();
    Assert.assertNotNull(all);
    Assert.assertEquals(0, all.size());
  }

  public void testGetAllProperties() {
    Message m = testDoc.getMessage("testmessage");
    ArrayList all = m.getAllProperties();
    Assert.assertEquals(4, all.size());
    Assert.assertEquals("testprop1", ((Property) all.get(0)).getName());
    Assert.assertEquals("stringprop", ((Property) all.get(1)).getName());
    Assert.assertEquals("integerprop", ((Property) all.get(2)).getName());
    Assert.assertEquals("propfloat", ((Property) all.get(3)).getName());
    m = testDoc.getMessage("testmessage2");
    all = m.getAllProperties();
    Assert.assertNotNull(all);
    Assert.assertEquals(0, all.size());
  }

  public void testGetFullMessageName() {
    Message m = testDoc.getMessage("testmessage").getMessage("testmessage_sub1").getMessage("testmessage_sub1_sub1");
    Assert.assertEquals("/testmessage/testmessage_sub1/testmessage_sub1_sub1", m.getFullMessageName());
  }

  public void testGetMessage() {
    Message m = testDoc.getMessage("testmessage");
    Message m2 = m.getMessage("testmessage_sub1");
    Assert.assertNotNull(m2);
    Assert.assertEquals("testmessage_sub1", m2.getName());
    Message m3 = m2.getMessage("../testmessage_sub2");
    Assert.assertEquals("testmessage_sub2", m3.getName());
  }

  public void testGetMessages() throws NavajoException {

    // Regular expression message name match testing.
      Message m = testDoc.getMessage("testmessage");

      ArrayList all = m.getMessages("testmessage_sub.*");
      Assert.assertEquals(3, all.size());
      Assert.assertEquals("testmessage_sub1", ((Message) all.get(0)).getName());
      Assert.assertEquals("testmessage_sub2", ((Message) all.get(1)).getName());
      Assert.assertEquals("testmessage_sub3", ((Message) all.get(2)).getName());

      all = m.getMessages("testmessage_sub");
      Assert.assertEquals(0, all.size());

      all = m.getMessages("testmessage_sub[0-3]");
      Assert.assertEquals(3, all.size());
      Assert.assertEquals("testmessage_sub1", ((Message) all.get(0)).getName());
      Assert.assertEquals("testmessage_sub2", ((Message) all.get(1)).getName());
      Assert.assertEquals("testmessage_sub3", ((Message) all.get(2)).getName());

      all = m.getMessages("testmessage_sub[0-2]");
      Assert.assertEquals(2, all.size());
      Assert.assertEquals("testmessage_sub1", ((Message) all.get(0)).getName());
      Assert.assertEquals("testmessage_sub2", ((Message) all.get(1)).getName());

      all = m.getMessages("test[A-z]*_sub[0-2]");
      Assert.assertEquals(2, all.size());
      Assert.assertEquals("testmessage_sub1", ((Message) all.get(0)).getName());
      Assert.assertEquals("testmessage_sub2", ((Message) all.get(1)).getName());

      all = m.getMessages("test[A-d]*_sub[0-2]");
      Assert.assertEquals(0, all.size());

      // Tests with absolute message offset (starting at root of TML)
      all = m.getMessages("/testmessage_sub[0-2]");
      Assert.assertEquals(0, all.size());

      all = m.getMessages("/testmessage/test[A-z]*_sub[0-2]");
      Assert.assertEquals(2, all.size());
      Assert.assertEquals("testmessage_sub1", ((Message) all.get(0)).getName());
      Assert.assertEquals("testmessage_sub2", ((Message) all.get(1)).getName());

      all = m.getMessages("/testmessage/testmessage_sub.*/testmessage_sub1_sub[2-3]");
      Assert.assertEquals(2, all.size());
      Assert.assertEquals("testmessage_sub1_sub2", ((Message) all.get(0)).getName());
      Assert.assertEquals("testmessage_sub1_sub3", ((Message) all.get(1)).getName());

      all = m.getMessages("/testmessage/testmessage_sub.*/testmessage_sub.*_sub[2-3]");
      Assert.assertEquals(4, all.size());
      Assert.assertEquals("testmessage_sub1_sub2", ((Message) all.get(0)).getName());
      Assert.assertEquals("testmessage_sub1_sub3", ((Message) all.get(1)).getName());
      Assert.assertEquals("testmessage_sub2_sub2", ((Message) all.get(2)).getName());
      Assert.assertEquals("testmessage_sub2_sub3", ((Message) all.get(3)).getName());

      all = m.getMessages("testmessage_sub1/testmessage_sub1_sub.*");
      Assert.assertEquals(4, all.size());
      Assert.assertEquals("testmessage_sub1_sub1", ((Message) all.get(0)).getName());
      Assert.assertEquals("testmessage_sub1_sub2", ((Message) all.get(1)).getName());
      Assert.assertEquals("testmessage_sub1_subje", ((Message) all.get(2)).getName());
      Assert.assertEquals("testmessage_sub1_sub3", ((Message) all.get(3)).getName());

      all = m.getMessages("testmessage_sub1/(testmessage){0,1}_sub1_sub.*");
      Assert.assertEquals(4, all.size());
      Assert.assertEquals("testmessage_sub1_sub1", ((Message) all.get(0)).getName());
      Assert.assertEquals("testmessage_sub1_sub2", ((Message) all.get(1)).getName());
      Assert.assertEquals("testmessage_sub1_subje", ((Message) all.get(2)).getName());
      Assert.assertEquals("testmessage_sub1_sub3", ((Message) all.get(3)).getName());

  }

  public void testGetName() {
    Message m = testDoc.getMessage("testmessage");
    Assert.assertEquals("testmessage", m.getName());
  }

  public void testGetProperties() throws NavajoException {

    System.out.println(testDoc);

      Message m = testDoc.getMessage("testmessage");

      ArrayList all = m.getProperties("[A-z]*prop");
      Assert.assertEquals(2, all.size());
      Assert.assertEquals("stringprop", ((Property) all.get(0)).getName());
      Assert.assertEquals("integerprop", ((Property) all.get(1)).getName());

      all = m.getProperties("[A-z]*propjes");
      Assert.assertEquals(0, all.size());

      all = m.getProperties("testmessage_sub1/testmessage_sub1_sub.*/[A-z]*");
      Assert.assertEquals(1, all.size());
      Assert.assertEquals("proppie", ((Property) all.get(0)).getName());

      m = m.getMessage("testmessage_sub1");
      all = m.getProperties("../[A-z]*prop");
      Assert.assertEquals(2, all.size());
      Assert.assertEquals("stringprop", ((Property) all.get(0)).getName());
      Assert.assertEquals("integerprop", ((Property) all.get(1)).getName());

      m = m.getMessage("testmessage_sub1_sub2");
      all = m.getProperties("../testmessage_sub1_sub1/[A-z]*");
      Assert.assertEquals(1, all.size());
      Assert.assertEquals("proppie", ((Property) all.get(0)).getName());

   
  }

  public void testGetProperty() {
    Message m = testDoc.getMessage("testmessage");
    Property p = m.getProperty("integerprop");
    Assert.assertNotNull(p);
    Assert.assertEquals("integerprop", p.getName());
    p = m.getProperty("integerpropje");
    Assert.assertNull(p);
  }

  public void testRemoveMessage() {
    Message m = testDoc.getMessage("testmessage");
    Message m2 = m.getMessage("testmessage_sub2");
    Assert.assertEquals("testmessage_sub2", m2.getName());
    m.removeMessage(m.getMessage("testmessage_sub2"));
    m2 = m.getMessage("testmessage_sub2");
    Assert.assertNull(m2);
  }

  public void testRemoveProperty() {
     Message m = testDoc.getMessage("testmessage");
     Property p = m.getProperty("propfloat");
     Assert.assertEquals("propfloat", p.getName());
     m.removeProperty(m.getProperty("propfloat"));
     p = m.getProperty("propfloat");
     Assert.assertNull(p);
  }

  public void testArrayMessages() throws Exception {
	 
	  Message m = NavajoFactory.getInstance().createMessage(testDoc, "MyTop");
	  testDoc.addMessage(m);
	  Message a = NavajoFactory.getInstance().createMessage(testDoc, "MyArrayMessage", "array");
	  m.addMessage(a);
	  for (int i = 0; i < 5; i++) {
		  Message a1 = NavajoFactory.getInstance().createMessage(testDoc, "MyArrayMessage");
		  a.addMessage(a1);
		  Property p = NavajoFactory.getInstance().createProperty(testDoc, "MyProp", "string", "noot" + i, 0, "", "in");
		  a1.addProperty(p);
	  }
	  assertEquals("array", testDoc.getMessage("/MyTop/MyArrayMessage").getType());
	  assertEquals(5, testDoc.getMessage("/MyTop/MyArrayMessage").getArraySize());
	  assertNotNull(testDoc.getProperty("/MyTop/MyArrayMessage@1/MyProp"));
	  assertEquals("noot3", testDoc.getProperty("/MyTop/MyArrayMessage@3/MyProp").getValue());
	  
	  ArrayList al = testDoc.getMessages("/MyTop/MyArrayMessage");
	  for (int i = 0; i < al.size(); i++) {
		  assertEquals("noot"+i, ((Message) al.get(i)).getProperty("MyProp").getValue());
		  // MyTop is my parent message(!) parent array is ignored!!
		  assertEquals("MyTop", ((Message) al.get(i)).getParentMessage().getName());
	  }
  }
  
  public void testSetName() throws Exception {
	  Message m = NavajoFactory.getInstance().createMessage(testDoc, "MyTop");
	  testDoc.addMessage(m);
	  m.setName("MyOtherTop");
	  System.err.println(">>>" + testDoc.getMessage("MyTop"));
	  assertNull(testDoc.getMessage("MyTop"));
	  assertNotNull(testDoc.getMessage("MyOtherTop"));
  }

  public void testAddIgnoreMessage() throws Exception {
	  Message m = NavajoFactory.getInstance().createMessage(testDoc, "MyTop");
	  testDoc.addMessage(m);
	  Message m2 = NavajoFactory.getInstance().createMessage(testDoc, "MyIgnoredMessage");
	  m2.setMode("ignore");
	  m.addMessage(m2);
	  assertNotNull(testDoc.getMessage("/MyTop/MyIgnoredMessage"));
	  StringWriter sw = new StringWriter();
	  assertEquals(sw.toString().indexOf("MyIgnoredMessage"), -1);
  }
  
  public void testAddIgnoreArrayMessageElements() throws Exception {
	  Message m = NavajoFactory.getInstance().createMessage(testDoc, "MyTop");
	  testDoc.addMessage(m);
	  Message m2 = NavajoFactory.getInstance().createMessage(testDoc, "MyIgnoredMessage");
	  m2.setMode("ignore");
	  m2.setType(Message.MSG_TYPE_ARRAY);
	  m.addMessage(m2);
	  assertNotNull(testDoc.getMessage("/MyTop/MyIgnoredMessage"));
	  StringWriter sw = new StringWriter();
	  
	  Message m3 =  NavajoFactory.getInstance().createMessage(testDoc, "MyIgnoredMessage");
	  m3.setMode("ignore");
	  m2.addMessage(m3);
	  Property p1 = NavajoFactory.getInstance().createProperty(testDoc, "MyProp", "string", "AAPJES", 0, "", "in", "");
	  m3.addProperty(p1);
	  
	  Message m4 =  NavajoFactory.getInstance().createMessage(testDoc, "MyIgnoredMessage");
	  m4.setMode("ignore");
	  m2.addMessage(m4);
	  Property p2 = NavajoFactory.getInstance().createProperty(testDoc, "MyProp", "string", "NOOTJES", 0, "", "in", "");
	  m4.addProperty(p2);
	  
	  testDoc.write(sw);
	  
	  System.err.println(sw.toString());
	  assertTrue(sw.toString().indexOf("AAPJES") == -1);
	  assertTrue(sw.toString().indexOf("NOOTJES") == -1);
  }

}
