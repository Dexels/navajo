package com.dexels.navajo.test;
import com.dexels.navajo.document.*;
import com.dexels.navajo.client.*;
/**
 * Title:        Navajo
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:      Dexels
 * @author Arjen Schoneveld en Martin Bergman
 * @version 1.0
 */

public class Test {

  public Test() {
  }

   public static void main(String args[]) throws Exception {

      Navajo doc = new Navajo();
      Message msg = Message.create(doc, "invoer");
      doc.addMessage(msg);
      Property prop1 = Property.create(doc, "tussenpersoon", Property.STRING_PROPERTY, "233", 0, "", Property.DIR_IN);
      msg.addProperty(prop1);
      Property prop2 = Property.create(doc, "productId", Property.STRING_PROPERTY, "-1", 0, "", Property.DIR_IN);
      msg.addProperty(prop2);
      Property prop3 = Property.create(doc, "jaar", "1", "", Property.DIR_IN);
      Selection sel = Selection.create(doc, "2002", "2002", true);
      prop3.addSelection(sel);
      msg.addProperty(prop3);

      int COUNT = 20;

      NavajoClient client = new NavajoClient(NavajoClient.HTTP_PROTOCOL);

      client.doSimpleSend(doc, "localhost/thispas/servlet/Postman", "afsluitprovisie_get", "DBV", "DBV");

      long start = System.currentTimeMillis();
      for (int i = 0; i < COUNT; i++)
        client.doSimpleSend(doc, "localhost/thispas/servlet/Postman", "afsluitprovisie_get", "DBV", "DBV");
        //client.doSimpleSend(doc, "localhost/thispas/servlet/Postman", "provisie", "DBV", "DBV");

      long end = System.currentTimeMillis();
      double total = (end - start)/1000.0;
      System.out.println("total time = " + total);
      System.out.println("average time = " + total/(double) COUNT);

  }
}