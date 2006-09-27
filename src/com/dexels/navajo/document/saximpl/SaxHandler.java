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
import com.dexels.navajo.document.saximpl.qdxml.*;
import com.dexels.navajo.document.types.*;

public final class SaxHandler implements DocHandler {

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

      
    public final void startElement(String tag, Hashtable h) throws Exception {
//        System.err.println("starting element: "+tag+" attrs: "+h);
        currentTag = tag;
        
        // Unescape all the shit.
        Hashtable h2 = new Hashtable();
        for (Iterator iter = h.keySet().iterator(); iter.hasNext();) {
			String key = (String) iter.next();
			String value = (String) h.get(key);
			value = BaseNode.XMLUnescape(value);
			h2.put(key, value);
		}
        h = h2;
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
        if (tag.equals("piggyback")) {
            parsePiggyback(h);
            return;
        }        
        
        //System.err.println("Unknown tag: "+tag+" attrs: "+h);
                 
//        throw new IllegalArgumentException("Unknown tag: "+tag+" attrs: "+h);
        
    }
    
    
    private final void parseAgent(Hashtable h) {
        // TODO Auto-generated method stub
        
    }


    private final void parseRequired(Hashtable h) {
        // TODO Auto-generated method stub
        currentMethod.addRequired((String)h.get("name"));
    }


    private final void parseObject(Hashtable h) {
    	
    	if ( (String) h.get("ref") == null || ((String) h.get("ref")).equals("") ) {
    		return;
    	}
    	
    	System.err.println(h);
    	
    	BaseObjectImpl baseObjectImpl = new BaseObjectImpl(currentDocument);
    	baseObjectImpl.setName((String) h.get("name"));
    	baseObjectImpl.setRef((String) h.get("ref"));
    	baseObjectImpl.setPercReady((int) Double.parseDouble((String) h.get("perc_ready")));
    	baseObjectImpl.setInterrupt((String) h.get("interrupt"));
    	baseObjectImpl.setFinished( h.get("finished").equals("true") );
    	
    	currentHeader.getCallback().addObject(baseObjectImpl);
    	
//    	currentHeader.setCallBack(
//    			(String) h.get("name"),
//    			(String) h.get("ref"),
//    			(int) Double.parseDouble((String) h.get("perc_ready")),
//    			((String) h.get("finished")).equals("true"),
//    			(String) h.get("interrupt")
//    	);
//     
    }


    private final void parseMethods(Hashtable h) {
        // TODO Auto-generated method stub
        
    }


    private final void parseCallback(Hashtable h) {
        // TODO: Should use the navajo factory for these functions
        if (currentHeader==null) {
            throw new IllegalArgumentException("Callback tag outside header tag.");
        }       
    }

    private final void parseTransaction(Hashtable h) {
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

    private final void parsePiggyback(Hashtable h) {
        if (currentHeader==null) {
            throw new IllegalArgumentException("Piggyback tag outside header tag.");
        }
        Map m = new HashMap();
        for (Iterator iter = h.keySet().iterator(); iter.hasNext();) {
			String  key = (String ) iter.next();
			String value = (String )h.get(key);
			m.put(key, value);
		}
        
        
        
        currentHeader.addPiggyBackData(m);        
    }
    
    
    private final void parseHeader(Hashtable h) {
        // TODO: Should use the navajo factory for these functions
//                NavajoFactory.getInstance().createHeader(current, rpcName, rpcUser, rpcPassword, expiration_interval)
        BaseHeaderImpl bhi = new BaseHeaderImpl(currentDocument);
        currentHeader = bhi;
        for (Iterator iter = h.keySet().iterator(); iter.hasNext();) {
			String element = (String) iter.next();
			String value = (String) h.get(element);
			bhi.setAttribute(element, value);
        }
        currentDocument.addHeader(bhi);
        // TODO Auto-generated method stub
        
    }

    private final void parseMethod(Hashtable h) throws NavajoException {
        String name = (String)h.get("name");
        currentMethod = NavajoFactory.getInstance().createMethod(currentDocument, name, null);
        currentDocument.addMethod(currentMethod);
    }

    private final void parseSelection(Hashtable h) throws NavajoException {
    	String name = (String)h.get("name");
    	String value = (String)h.get("value");
    	int selected = Integer.parseInt((String)h.get("selected"));
    	
    	Property definitionProperty = null;
    	
    	BaseMessageImpl arrayParent = (BaseMessageImpl) currentProperty.getParentMessage().getArrayParentMessage();
    	
    	if ( arrayParent != null ) {
    		definitionProperty = arrayParent.getPropertyDefinition(currentProperty.getName());
    	}
    	
    	if ( definitionProperty == null || 
    		 definitionProperty.getSelected().getName().equals(Selection.DUMMY_SELECTION) ||
    		 currentProperty.getParentMessage().getType().equals(Message.MSG_TYPE_DEFINITION) ) {
    		Selection s = NavajoFactory.getInstance().createSelection(currentDocument, name, value, selected!=0);
    		currentProperty.addSelection(s);
    	} else {
    		if ( currentProperty.getSelectionByValue(value) != null && selected == 1) {
    		   ((BasePropertyImpl) currentProperty).setSelectedByValue(value);
    		} else {
    			Selection s = NavajoFactory.getInstance().createSelection(currentDocument, name, value, selected!=0);
    			currentProperty.addSelection(s);
    		}
    	}
    	
    }

    private final void parseProperty(Hashtable h) throws NavajoException {
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
//          System.err.println("ILLEGAL LENGTH IN PROPERTY " + myName + ": " +
//                             sLength);
        }

      
        boolean isListType = (type != null && type.equals(Property.SELECTION_PROPERTY));
       
          if (isListType) {
              currentProperty = NavajoFactory.getInstance().createProperty(currentDocument,myName,cardinality,description,direction);
        } else {
            currentProperty = NavajoFactory.getInstance().createProperty(currentDocument, myName, type, myValue, length, description, direction, subtype);
        }

          Message current = (Message)messageStack.peek();
//          System.err.println("Adding property: "+currentProperty.getName()+" to message: "+current.getFullMessageName());
          current.addProperty(currentProperty);

          definitionProperty = null;

          BaseMessageImpl arrayParent = (BaseMessageImpl) current.getArrayParentMessage();
          //System.err.println("current = " + current.getName() + ", type = " + current.getType());
          if ( arrayParent != null && arrayParent.isArrayMessage() ) {

            definitionProperty = arrayParent.getPropertyDefinition(myName);
            //System.err.println("definitionProperty = " + definitionProperty + ", for name: " + myName);
            
            
            if (definitionProperty != null) {
            	    
              if (description == null || "".equals(description)) {
                description = definitionProperty.getDescription();
              }
              if (direction == null || "".equals(direction)) {
                direction = definitionProperty.getDirection();
              }
              if (type == null || "".equals(type)) {
                type = definitionProperty.getType();
              }
              if (plength == null) {
                length = definitionProperty.getLength();
              }
              if (subType == null) {
                if (definitionProperty.getSubType() != null) {
                	currentProperty.setSubType(definitionProperty.getSubType());
                }
                else {
                  subType = null;
                }
              }
              else {
                if (definitionProperty.getSubType() != null) {
                  /**
                       * Concatenated subtypes. The if the same key of a subtype is present
                   * in both the property and the definition property.
                   */
                	currentProperty.setSubType(definitionProperty.getSubType() + "," + subType);
                }
              }

              if (myValue == null || "".equals(myValue)) {
                myValue = definitionProperty.getValue();
              }
            }
          }

          if (subType == null &&
              NavajoFactory.getInstance().getDefaultSubtypeForType(type) != null) {
        	  currentProperty.setSubType(NavajoFactory.getInstance().getDefaultSubtypeForType(type));
          }
          else {
        	  currentProperty.setSubType(subType);
          }

          if (type == null &&  current.isArrayMessage() ) {
            System.err.println("Found undefined property: " + currentProperty.getName());
          }

          isListType = (type != null && type.equals(Property.SELECTION_PROPERTY));
          
          if (isListType && definitionProperty != null) {
        	  
        	  if (cardinality == null) {
        		  cardinality = definitionProperty.getCardinality();
        	  }
        	  type = Property.SELECTION_PROPERTY;
        	  
        	  ArrayList l = definitionProperty.getAllSelections();
        	  for (int i = 0; i < l.size(); i++) {
        		  BaseSelectionImpl s1 = (BaseSelectionImpl) l.get(i);
        		  BaseSelectionImpl s2 = (BaseSelectionImpl) s1.copy(currentDocument);
        		  currentProperty.addSelection(s2);
        		  //System.err.println("ADDING SELECTION: " + s2);
        	  }
          }
              currentProperty.setType(type);
              currentProperty.setDescription(description);
              currentProperty.setDirection(direction);
              currentProperty.setCardinality(cardinality);
              currentProperty.setLength(length);
//              createProperty(currentDocument,myName,cardinality,description,direction);


      }
    

    private final void parseMessage(Hashtable h) throws NavajoException {
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
        	Message parentMessage = (Message)messageStack.peek();
            if ( !Message.MSG_TYPE_DEFINITION.equals(type) ) {
        		parentMessage.addMessage(m);
        	} else {
        		parentMessage.setDefinitionMessage(m);
                
        	}
        }
          messageStack.push(m);
//        System.err.println("Stack: "+messageStack);
    }


    private void mergeDefinitionMessage() {

        Message currentMessage = (Message)messageStack.peek();
        if (currentMessage==null) {
            return;
        }
        Message parentMessage = currentMessage.getParentMessage();
        if (parentMessage==null) {
            return;
        }
        Message def = parentMessage.getDefinitionMessage();
        if (def==null) {
            return;
        }
        ArrayList props = def.getAllProperties();
        for (int i = 0; i < props.size(); i++) {
            Property src = (Property)props.get(i);
            Property dest = currentMessage.getProperty(src.getName());
            if (dest==null) {
                dest = src.copy(currentDocument);
                currentMessage.addProperty(dest);
            }
        }
    }

//    private void mergeMissingDefinitionProperties() {
//        // TODO Auto-generated method stub
//        
//    }

    public final void endElement(String tag) throws Exception {
//        System.err.println("ending element: "+tag);
        if (tag.equals("tml")) {
         }
        if (tag.equals("message")) {
            mergeDefinitionMessage();
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


  

    public final void startDocument() throws Exception {
          reset();
    }


    public final void endDocument() throws Exception {
        
    }


    public final void text(Reader r) throws Exception {
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
    
    public final Navajo getNavajo() {
        return currentDocument;
    }


    public final String quoteStarted(int quoteCharacter, Reader r, String attributeName, String tagName) throws IOException {
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