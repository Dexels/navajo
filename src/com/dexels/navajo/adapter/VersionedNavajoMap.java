package com.dexels.navajo.adapter;

import com.dexels.navajo.server.*;
import com.dexels.navajo.mapping.*;
import com.dexels.navajo.document.*;
import java.util.ArrayList;

public class VersionedNavajoMap extends NavajoMap {

 public void setDoSend(String method) throws UserException, ConditionErrorException, SystemException {

     super.setDoSend(method);

     System.err.println("in VersionedNavajoMap store()");
     if (!this.isEqual) {

       System.err.println("ABOUT TO CONSTRUCT MESSAGES!");
       try {
         Navajo currentOutDoc = super.access.getOutputDoc();

         Message confirm = NavajoFactory.getInstance().createMessage(
             currentOutDoc, "DataHasChanged");
         currentOutDoc.addMessage(confirm);
         Property b = NavajoFactory.getInstance().createProperty(currentOutDoc,
             "Overwrite", Property.BOOLEAN_PROPERTY, "false", 1, "",
             Property.DIR_IN);
         confirm.addProperty(b);

         ArrayList allMethods = ((com.dexels.navajo.document.jaxpimpl.NavajoImpl) inMessage).getAllMethods(inMessage.getMessageBuffer());
         for (int i = 0; i < allMethods.size(); i++) {
           Method m = (Method) allMethods.get(i);
           System.err.println("Adding method: " + m.getName());
           Method a = NavajoFactory.getInstance().createMethod(currentOutDoc,
               m.getName(), "");
           ArrayList required = m.getRequiredMessages();
           for (int j = 0; j < required.size(); j++) {
             String name = (String) required.get(j);
              System.err.println("Adding required message: " + name);
             a.addRequired(name);
           }
           currentOutDoc.addMethod(a);
         }

         ArrayList allMessages = inMessage.getAllMessages();
         for (int i = 0; i < allMessages.size(); i++) {

           Message m = (Message) allMessages.get(i);
            System.err.println("Adding message: " + m.getName());
           Message a = inMessage.copyMessage(m, currentOutDoc);
           currentOutDoc.addMessage(a);
         }
       }
       catch (NavajoException ne) {
         throw new UserException( -1, ne.getMessage(), ne);
       }
     }
  }

  public static void main(String [] args) throws Exception {
     Navajo n = NavajoFactory.getInstance().createNavajo(new java.io.FileInputStream("/home/arjen/@@.tml"));
     n.write(System.err);
     ArrayList l = ((com.dexels.navajo.document.jaxpimpl.NavajoImpl) n).getAllMethods(n.getMessageBuffer());
     System.err.println("l = " + l.size());
     System.exit(1);
  }
}