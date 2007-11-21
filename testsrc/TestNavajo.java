

import junit.framework.*;
import org.w3c.dom.*;
import java.util.*;

import com.dexels.navajo.document.*;

public class TestNavajo extends TestCase {
  NavajoDocumentTestFicture navajodocumenttestfictureInst = new NavajoDocumentTestFicture(this);
  private Navajo testDoc = null;

  public TestNavajo(String s) {
    super(s);
  }

  protected void setUp() {
    navajodocumenttestfictureInst.setUp();
    testDoc = navajodocumenttestfictureInst.testDoc;
  }

  protected void tearDown() {
    navajodocumenttestfictureInst.tearDown();
  }

  public void testAddMessage() throws NavajoException {
    Message msg = NavajoFactory.getInstance().createMessage(testDoc, "extramsg");
    testDoc.addMessage(msg);
    Message result = testDoc.getMessage("extramsg");
    Assert.assertNotNull(result);
    Assert.assertEquals(msg.getName(), result.getName());
  }

  public void testAddMessage1() {
    // NOT VERY IMPORTANT: OVERWRITING OF MESSAGES SHOULD ALWAYS BE ON.
  }

  public void testAddMethod() throws NavajoException {
    Method m = NavajoFactory.getInstance().createMethod(testDoc, "mymethod", "Postman");
    testDoc.addMethod(m);
    Method result = testDoc.getMethod("mymethod");
    Assert.assertNotNull(result);
    Assert.assertEquals("mymethod", result.getName());
    Assert.assertEquals("Postman", result.getServer());
  }

  public void testAppendDocBuffer() throws NavajoException, java.io.IOException {
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

  public void testClearAllSelections() throws NavajoException {
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
    ArrayList all = null;

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

  public void testContains() {
  }
  public void testCopyMessage() {
  }
  public void testCopyMessage1() {
  }
  public void testCopyMethod() {
  }
  public void testCopyMethod1() {
  }
  public void testCreateDocBuffer() {
 }
  public void testCreateHeader() {
  }
  public void testGetAllMessages() {
  }
  public void testGetAllMessages1() {
  }
  public void testGetAllMethods() {
  }
  public void testGetAllMethods1() {
  }
  public void testGetCurrentActions() {
  }
  public void testGetCurrentMessages() {
  }
  public void testGetErrorDescription() {
  }
  public void testGetErrorNumber() {
  }
  public void testGetMessage() {
  }
  public void testGetMessage1() {
  }
  public void testGetMessage2() {
 }
  public void testGetMessage3() {
  }
  public void testGetMessageBuffer() {
 }
  public void testGetMessages() {

  }
  public void testGetMethod() {
  }
  public void testGetProperties() {

  }
  public void testGetProperty() {
  }
  public void testGetSelection() {
  }
  public void testMain() {
  }
  public void testPersistenceKey() {
  }
  public void testRemoveMessage() {
  }
  public void testRemoveMessage1() {
  }
  public void testSetErrorDescription() {
  }
  public void testSetErrorNumber() {
  }
  public void testToString() {
  }
  public void testWriteDocument() {
  }
  public void testWriteMessage() {
  }
}
