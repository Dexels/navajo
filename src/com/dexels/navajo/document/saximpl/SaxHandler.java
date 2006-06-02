/*
 * Created on May 3, 2006
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.document.saximpl;


import java.io.*;
import java.util.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.document.base.*;
import com.dexels.navajo.document.nanoimpl.*;
import com.dexels.navajo.document.saximpl.qdxml.*;
import com.dexels.navajo.document.types.*;

public class SaxHandler implements DocHandler {

    private Navajo currentDocument=null;
    private final Stack messageStack = new Stack();
    private final ArrayList binaries = new ArrayList();
    private String currentTag = null;
    private Property currentProperty = null;
    private BaseHeaderImpl currentHeader;
    private BaseCallbackImpl currentCallback = null;
    private Method currentMethod = null;

    public void reset() {
        currentDocument = null;
        messageStack.clear();
        binaries.clear();
        currentTag = null;
    }

      
    public void startElement(String tag, Hashtable h) throws Exception {
//        System.err.println("starting element: "+tag+" attrs: "+h);
        currentTag = tag;
        if (tag.equals("tml")) {
            currentDocument = NavajoFactory.getInstance().createNavajo();
            return;
        }
        if (tag.equals("message")) {
            parseMessage(h);
            return;
        }
        if (tag.equals("property")) {
            parseProperty(h);
            return;
        }
        if (tag.equals("option")) {
            parseSelection(h);
            return;
        }
        if (tag.equals("method")) {
            parseMethod(h);
            return;
        }
        if (tag.equals("header")) {
            parseHeader(h);
            return;
        }
        if (tag.equals("transaction")) {
            parseTransaction(h);
            return;
        }
        if (tag.equals("callback")) {
            parseCallback(h);
            return;
        }
        if (tag.equals("methods")) {
            parseMethods(h);
            return;
        }
        if (tag.equals("client")) {
            return;
        }
        if (tag.equals("server")) {
            return;
        }
        if (tag.equals("agent")) {
            parseAgent(h);
            return;
        }
        if (tag.equals("required")) {
            parseRequired(h);
            return;
        }
        if (tag.equals("object")) {
            parseObject(h);
            return;
        }
        
        System.err.println("Unknown tag: "+tag+" attrs: "+h);
                 
//        throw new IllegalArgumentException("Unknown tag: "+tag+" attrs: "+h);
        
    }
    
    
    private void parseAgent(Hashtable h) {
        // TODO Auto-generated method stub
        
    }


    private void parseRequired(Hashtable h) {
        // TODO Auto-generated method stub
        currentMethod.addRequired((String)h.get("name"));
    }


    private void parseObject(Hashtable h) {
    	
    	if ( (String) h.get("ref") == null || ((String) h.get("ref")).equals("") ) {
    		return;
    	}
    	
    	currentHeader.setCallBack(
    			(String) h.get("name"),
    			(String) h.get("ref"),
    			(int) Double.parseDouble((String) h.get("perc_ready")),
    			((String) h.get("finished")).equals("true"),
    			(String) h.get("interrupt")
    	);
     
    }


    private void parseMethods(Hashtable h) {
        // TODO Auto-generated method stub
        
    }


    private void parseCallback(Hashtable h) {
        // TODO: Should use the navajo factory for these functions
        if (currentHeader==null) {
            throw new IllegalArgumentException("Callback tag outside header tag.");
        }       
    }

    private void parseTransaction(Hashtable h) {
        if (currentHeader==null) {
            throw new IllegalArgumentException("Callback tag outside header tag.");
        }
        BaseTransactionImpl bci = new BaseTransactionImpl(currentDocument);
        bci.setRpc_name((String)h.get("rpc_name"));
        bci.setRpc_usr((String)h.get("rpc_usr"));
        bci.setRpc_pwd((String)h.get("rpc_pwd"));
        bci.setRequestId((String)h.get("requestid"));
        currentHeader.addTransaction(bci);        
    }

    private void parseHeader(Hashtable h) {
        // TODO: Should use the navajo factory for these functions
//                NavajoFactory.getInstance().createHeader(current, rpcName, rpcUser, rpcPassword, expiration_interval)
        BaseHeaderImpl bhi = new BaseHeaderImpl(currentDocument);
        currentHeader = bhi;
        currentDocument.addHeader(bhi);
        // TODO Auto-generated method stub
        
    }

    private void parseMethod(Hashtable h) throws NavajoException {
        String name = (String)h.get("name");
        currentMethod = NavajoFactory.getInstance().createMethod(currentDocument, name, null);
        currentDocument.addMethod(currentMethod);
    }

    private void parseSelection(Hashtable h) throws NavajoException {
        String name = (String)h.get("name");
        String value = (String)h.get("value");
        int selected = Integer.parseInt((String)h.get("selected"));
        Selection s = NavajoFactory.getInstance().createSelection(currentDocument, name, value, selected!=0);
        currentProperty.addSelection(s);
    }

    private void parseProperty(Hashtable h) throws NavajoException {
//        System.err.println("NAME: "+(String)h.get("name"));
        Message parentArrayMessage = null;
        String sLength = null;
        String myName = (String) h.get(Property.PROPERTY_NAME);
        String myValue = (String) h.get(Property.PROPERTY_VALUE);
        String subType = (String) h.get(Property.PROPERTY_SUBTYPE);
        String description = (String) h.get(Property.PROPERTY_DESCRIPTION);
        String direction = (String) h.get(Property.PROPERTY_DIRECTION);
        String type = (String) h.get(Property.PROPERTY_TYPE);
        sLength = (String) h.get(Property.PROPERTY_LENGTH);
        String cardinality = (String) h.get(Property.PROPERTY_CARDINALITY);
         Integer plength = null;
        Property definitionProperty = null;
        String subtype = null;
        int length = 0;
        try {
          if (sLength != null) {
            length = Integer.parseInt(sLength);
            plength = new Integer(length);
          }
        }
        catch (Exception e1) {
          System.err.println("ILLEGAL LENGTH IN PROPERTY " + myName + ": " +
                             sLength);
        }

        definitionProperty = null;


        boolean isListType = (type != null && type.equals(Property.SELECTION_PROPERTY));
        /*       if (isListType) {
          cardinality = (String) h.get(Property.PROPERTY_CARDINALITY);
          if (cardinality == null) {
            cardinality = definitionProperty.getCardinality();
          }
          type = Property.SELECTION_PROPERTY;
          ----------------- HANDLE WITH THE SELECTION
          try {
            if (definitionProperty == null || definitionProperty.getAllSelections().size() == 0) {
              cardinality = (String) e.getAttribute("cardinality");
              for (int i = 0; i < e.countChildren(); i++) {
                XMLElement child = (XMLElement) e.getChildren().elementAt(i);
                SelectionImpl s = (SelectionImpl) NavajoFactory.getInstance().createSelection(myDocRoot, "", "", false);
                s.fromXml(child);
                s.setParent(this);
                this.addSelection(s);
              }
            }
            else { // There is a definition property with defined selections(!)
              ArrayList l = definitionProperty.getAllSelections();
              for (int i = 0; i < l.size(); i++) {
                SelectionImpl s = (SelectionImpl) l.get(i);
                SelectionImpl s2 = (SelectionImpl) s.copy(getRootDoc());
                addSelection(s2);
              }
              for (int j = 0; j < e.countChildren(); j++) {
                XMLElement child = (XMLElement) e.getChildren().elementAt(j);
                String val = (String) child.getAttribute("value");
                //System.err.println(">>>>>>>>>>>>>>>>>>>>>> Attempting to select value: " + val);
                if (val != null) {
                  setSelectedByValue(val);
                }
              }

            }

          }
          catch (NavajoException ex) {
            ex.printStackTrace();
          }
*/
          if (isListType) {
              currentProperty = NavajoFactory.getInstance().createProperty(currentDocument,myName,cardinality,description,direction);
        } else {
            currentProperty = NavajoFactory.getInstance().createProperty(currentDocument, myName, type, myValue, length, description, direction, subtype);
        }

          Message current = (Message)messageStack.peek();
//          System.err.println("Adding property: "+currentProperty.getName()+" to message: "+current.getFullMessageName());
          current.addProperty(currentProperty);
//        }
//        if (type == null) {
//          type = Property.STRING_PROPERTY;
//        }
//        try {
//            setValue(PropertyTypeChecker.getInstance().verify(this, myValue));
//        } catch (PropertyTypeException e1) {
//              e1.printStackTrace();
//        }
      }
    

    private void parseMessage(Hashtable h) throws NavajoException {
        String name = (String)h.get("name");
        String type = (String)h.get("type");
        Message m = null;
        if (type!=null) {
            m = NavajoFactory.getInstance().createMessage(currentDocument, name,type);
        } else {
            m = NavajoFactory.getInstance().createMessage(currentDocument, name);
        }
        if (messageStack.isEmpty()) {
//            System.err.println("Adding to root!");
            currentDocument.addMessage(m);
        } else {
        	// Don't add definition messages.
        	if ( !Message.MSG_TYPE_DEFINITION.equals(type) ) {
        		((Message)messageStack.peek()).addMessage(m);
        	} else {
        		((Message)messageStack.peek()).setDefinitionMessage(m);
        	}
        }
          messageStack.push(m);
//        System.err.println("Stack: "+messageStack);
    }


    public void endElement(String tag) throws Exception {
//        System.err.println("ending element: "+tag);
        if (tag.equals("tml")) {
         }
        if (tag.equals("message")) {
            Message m = (Message)messageStack.pop();
        }
        if (tag.equals("property")) {
//            System.err.println("Property ended");
            currentProperty = null;
        }
        if (tag.equals("option")) {
        }
        if (tag.equals("method")) {
//            currentMethod = null;
        }
        if (tag.equals("header")) {
//            parseHeader(h);
            currentHeader = null;
        }
        if (tag.equals("transaction")) {
//            parseTransaction(h);
        }
        if (tag.equals("callback")) {
            currentCallback = null;
        }       
        if (tag.equals("callback")) {
          }       
        currentTag = null;
    }


    public void startDocument() throws Exception {
          reset();
    }


    public void endDocument() throws Exception {
        
    }


    public void text(Reader r) throws Exception {
        if (currentProperty==null) {
            System.err.println("Huh?");
            Thread.dumpStack();
            return;
//            throw new IllegalArgumentException("Huh?");
        }
        int length = currentProperty.getLength();
        if (Property.BINARY_PROPERTY.equals(currentProperty.getType())) {
            Binary b = new Binary(r,length);
            currentProperty.setValue(b);
        } else {
            throw new IllegalArgumentException("uuuh?");
       }
    }
    
    public Navajo getNavajo() {
        return currentDocument;
    }


    public String quoteStarted(int quoteCharacter, Reader r, String attributeName, String tagName) throws IOException {
        int c = 0;
        StringBuffer sb = new StringBuffer();
        while ((c = r.read()) != -1) {
            if (c==quoteCharacter) {
//                String s = sb.toString();
//                System.err.println(">> "+s);
                return sb.toString();
            } else {
//                System.err.println("parsing char:"+(char)c);
                sb.append((char)c);
//                return sb.toString();
            }
        }        
        throw new EOFException("Non terminated quote!");
    }
    

}
