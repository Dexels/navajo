
import com.dexels.navajo.document.*;

public class NavajoDocumentTestFicture extends java.lang.Object {

  public Navajo testDoc;

  public NavajoDocumentTestFicture(Object obj) {
  }

  public void setUp() {
    try {
      testDoc = NavajoFactory.getInstance().createNavajo();

      Message msg = NavajoFactory.getInstance().createMessage(testDoc, "testmessage");
      Property prop = NavajoFactory.getInstance().createProperty(testDoc, "testprop1", "+", "", Property.DIR_IN);
      msg.addProperty(prop);
      Property prop2 = NavajoFactory.getInstance().createProperty(testDoc, "stringprop", Property.STRING_PROPERTY, "navajo", 10, "", Property.DIR_OUT);
      msg.addProperty(prop2);
      Property prop3 = NavajoFactory.getInstance().createProperty(testDoc, "integerprop", Property.INTEGER_PROPERTY, "navajo", 10, "", Property.DIR_OUT);
      msg.addProperty(prop3);
      Property prop4 = NavajoFactory.getInstance().createProperty(testDoc, "propfloat", Property.FLOAT_PROPERTY, "navajo", 10, "", Property.DIR_OUT);
      msg.addProperty(prop4);

      testDoc.addMessage(msg);
      Message submsg1 =  NavajoFactory.getInstance().createMessage(testDoc, "testmessage_sub1");
      msg.addMessage(submsg1);
      Message subsubmsg1 = NavajoFactory.getInstance().createMessage(testDoc, "testmessage_sub1_sub1");
      submsg1.addMessage(subsubmsg1);
      Property prop5 = NavajoFactory.getInstance().createProperty(testDoc, "proppie", Property.STRING_PROPERTY, "", 0, "", Property.DIR_INOUT);
      subsubmsg1.addProperty(prop5);

      Message subsubmsg2 = NavajoFactory.getInstance().createMessage(testDoc, "testmessage_sub1_sub2");
      submsg1.addMessage(subsubmsg2);
      Message subsubmsgje = NavajoFactory.getInstance().createMessage(testDoc, "testmessage_sub1_subje");
      submsg1.addMessage(subsubmsgje);
      Message subsubmsg3 = NavajoFactory.getInstance().createMessage(testDoc, "testmessage_sub1_sub3");
      submsg1.addMessage(subsubmsg3);

      Message submsg2 = NavajoFactory.getInstance().createMessage(testDoc, "testmessage_sub2");
      msg.addMessage(submsg2);
      Message sub2submsg1 = NavajoFactory.getInstance().createMessage(testDoc, "testmessage_sub2_sub1");
      submsg2.addMessage(sub2submsg1);
      Message sub2submsg2 = NavajoFactory.getInstance().createMessage(testDoc, "testmessage_sub2_sub2");
      submsg2.addMessage(sub2submsg2);
      Message sub2submsgje = NavajoFactory.getInstance().createMessage(testDoc, "testmessage_sub2_subje");
      submsg2.addMessage(sub2submsgje);
      Message sub2submsg3 = NavajoFactory.getInstance().createMessage(testDoc, "testmessage_sub2_sub3");
      submsg2.addMessage(sub2submsg3);

      Message submsg3 = NavajoFactory.getInstance().createMessage(testDoc, "testmessage_sub3");
      msg.addMessage(submsg3);

      Message msg2 = NavajoFactory.getInstance().createMessage(testDoc, "testmessage2");
      testDoc.addMessage(msg2);

      Message msg3 = NavajoFactory.getInstance().createMessage(testDoc, "testmessage3");
      testDoc.addMessage(msg3);

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void tearDown() {
  }
  
  public static void main(String [] args) throws Exception {
	  NavajoDocumentTestFicture o = new NavajoDocumentTestFicture(null);
	  o.setUp();
	  o.testDoc.write(System.err);
  }

}
