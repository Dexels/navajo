package com.dexels.navajo.adapter;

/**
 * @author  Arjen Schoneveld
 * @version $Id$
 * 
 * This class is used to call a Navajo webservice like the NavajoMap.
 * Difference is that it compares the current Navajo with the Navajo
 * result of the called Navajo webservice. If the two differ a special
 * message "DataHasChanged" is created containing a property "Overwrite".
 * The current Navajo is included in the result, overwriting the Navajo
 * of the called webservice if message names coincide.
 * 
 * The purpose of this adapter is to use it for checking data changes between
 * an original query and the subsequent update.
 */
import com.dexels.navajo.server.*;
import com.dexels.navajo.document.*;
import java.util.ArrayList;

public class VersionedNavajoMap extends NavajoMap {

 public void setDoSend(String method) throws UserException, ConditionErrorException, SystemException, AuthorizationException {

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