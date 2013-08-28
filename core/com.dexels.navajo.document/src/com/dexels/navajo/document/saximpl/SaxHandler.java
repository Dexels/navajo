/*
 * Created on May 3, 2006
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.document.saximpl;


import java.io.*;
import java.util.*;
import java.util.Map.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.*;
import com.dexels.navajo.document.base.*;
import com.dexels.navajo.document.saximpl.qdxml.*;
import com.dexels.navajo.document.types.*;

public final class SaxHandler implements DocHandler {

    private Navajo currentDocument=null;
    private final Stack<Message> messageStack = new Stack<Message>();
    private BasePropertyImpl currentProperty = null;
    private BaseHeaderImpl currentHeader;
    private Method currentMethod = null;
    
	private final static Logger logger = LoggerFactory
			.getLogger(SaxHandler.class);
    public void reset() {
        currentDocument = null;
        messageStack.clear();
//        binaries.clear();
//        currentTag = null;
    }

      
    public final void startElement(String tag, Hashtable<String,String> h) throws Exception {
//        logger.info("starting element: "+tag+" attrs: "+h);
//        currentTag = tag;
        
        // Unescape all the shit.
        if (tag.equals("tml")) {
            currentDocument = NavajoFactory.getInstance().createNavajo();
            return;
        }
        if (tag.equals("message")) {
            parseMessage(h);
            return;
        }
        if (tag.equals("property")) {
            String val = h.get("value");
            if (val!=null) {

            	// Dit kan NOG strakker. Niet alle types hoeven geunescaped worder
            	Hashtable<String,String> h2 = new Hashtable<String,String>(h);
    			val = BaseNode.XMLUnescape(val);
    			h2.put("value", val);
                parseProperty(h2);
			} else {
                parseProperty(h);
				
			}
         return;
        }
        if (tag.equals("option")) {
            String val = h.get("value");
            String name = h.get("name");
            Hashtable<String,String> h2 = new Hashtable<String,String>(h);
    		val = BaseNode.XMLUnescape(val);
    		name = BaseNode.XMLUnescape(name);
    		h2.put("value", val);
    		h2.put("name", name);
    			
    			parseSelection(h2);
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
        if (tag.equals(Navajo.OPERATIONS_DEFINITION)) {
        	parseOperations(h);
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
        
        //logger.info("Unknown tag: "+tag+" attrs: "+h);
                 
//        throw new IllegalArgumentException("Unknown tag: "+tag+" attrs: "+h);
        
    }
    
    
    /**
	 * @param h parameter callback 
	 */
    private final void parseAgent(Hashtable<String,String> h) {
        // TODO Auto-generated method stub
        
    }


    private final void parseRequired(Hashtable<String,String> h) {
        // TODO Auto-generated method stub
        currentMethod.addRequired(h.get("name"));
    }


    private final void parseObject(Hashtable<String,String> h) {
    	
    	if ( h.get("ref") == null || (h.get("ref")).equals("") ) {
    		return;
    	}
    	
    //	logger.info(h);
    	
    	BaseObjectImpl baseObjectImpl = new BaseObjectImpl(currentDocument);
    	baseObjectImpl.setName(h.get("name"));
    	baseObjectImpl.setRef(h.get("ref"));
    	baseObjectImpl.setPercReady((int) Double.parseDouble(h.get("perc_ready")));
    	baseObjectImpl.setInterrupt(h.get("interrupt"));
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


    /**
	 * @param h parameter callback 
	 */
    private final void parseMethods(Hashtable<String,String> h) {
        // TODO Auto-generated method stub
        
    }

    private final void parseOperations(Hashtable<String,String> h) {
        // TODO Auto-generated method stub
        
    }

    /**
	 * @param h parameter callback 
	 */
    private final void parseCallback(Hashtable<String,String> h) {
        // TODO: Should use the navajo factory for these functions
        if (currentHeader==null) {
            throw new IllegalArgumentException("Callback tag outside header tag.");
        }       
    }

    private final void parseTransaction(Hashtable<String,String> h) {
        if (currentHeader==null) {
            throw new IllegalArgumentException("Callback tag outside header tag.");
        }
        BaseTransactionImpl bci = new BaseTransactionImpl(currentDocument);
        bci.setRpc_name(h.get("rpc_name"));
        bci.setRpc_usr(h.get("rpc_usr"));
        bci.setRpc_pwd(h.get("rpc_pwd"));
        bci.setRpc_schedule(h.get("rpc_schedule"));
        bci.setRequestId(h.get("requestid"));
        currentHeader.addTransaction(bci);        
    }

    private final void parsePiggyback(Hashtable<String,String> h) {
        if (currentHeader==null) {
            throw new IllegalArgumentException("Piggyback tag outside header tag.");
        }
        Map<String,String> m = new HashMap<String,String>();
        for (Iterator<Entry<String,String>> iter = h.entrySet().iterator(); iter.hasNext();) {
        	Entry<String,String> e = iter.next();
			String  key = e.getKey();
			String value = e.getValue();
			m.put(key, value);
		}
        
        
        
        currentHeader.addPiggyBackData(m);        
    }
    
    
    private final void parseHeader(Hashtable<String,String> h) {
        // TODO: Should use the navajo factory for these functions
//                NavajoFactory.getInstance().createHeader(current, rpcName, rpcUser, rpcPassword, expiration_interval)
        BaseHeaderImpl bhi = new BaseHeaderImpl(currentDocument);
        currentHeader = bhi;
        for (Iterator<Entry<String,String>> iter = h.entrySet().iterator(); iter.hasNext();) {
        	Entry<String,String> e = iter.next();
			String element = e.getKey();
			String value = e.getValue();
			bhi.setHeaderAttribute(element, value);
        }
        currentDocument.addHeader(bhi);
        // TODO Auto-generated method stub
        
    }

    private final void parseMethod(Hashtable<String,String> h) throws NavajoException {
        String name = h.get("name");
        currentMethod = NavajoFactory.getInstance().createMethod(currentDocument, name, null);
        currentDocument.addMethod(currentMethod);
    }

    private final void parseSelection(Hashtable<String,String> h) throws NavajoException {
    	String name = h.get("name");
    	String value = h.get("value");
    	int selected = Integer.parseInt(h.get("selected"));
    	
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
    		   currentProperty.setSelectedByValue(value);
    		} else {
    			Selection s = NavajoFactory.getInstance().createSelection(currentDocument, name, value, selected!=0);
    			currentProperty.addSelection(s);
    		}
    	}
    	
    }

    private final void parseProperty(Hashtable<String,String> h) throws NavajoException {
//        logger.info("NAME: "+(String)h.get("name"));
        String sLength = null;
        String myName = h.get(Property.PROPERTY_NAME);
        String myValue = h.get(Property.PROPERTY_VALUE);
        String subType = h.get(Property.PROPERTY_SUBTYPE);
        String description = h.get(Property.PROPERTY_DESCRIPTION);
        String direction = h.get(Property.PROPERTY_DIRECTION);
        String type = h.get(Property.PROPERTY_TYPE);
        sLength = h.get(Property.PROPERTY_LENGTH);
        String cardinality = h.get(Property.PROPERTY_CARDINALITY);
        
        String extendsProp = h.get(Property.PROPERTY_EXTENDS);
        String reference = h.get(Property.PROPERTY_REFERENCE);
        String key = h.get(Property.PROPERTY_KEY);
        
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
//          logger.info("ILLEGAL LENGTH IN PROPERTY " + myName + ": " +
//                             sLength);
        }
        if(myName==null) {
        	throw NavajoFactory.getInstance().createNavajoException("Can not parse property without a name ");
        }
      
        boolean isListType = (type != null && type.equals(Property.SELECTION_PROPERTY));
       
          if (isListType) {
              currentProperty = (BasePropertyImpl) NavajoFactory.getInstance().createProperty(currentDocument,myName,cardinality,description,direction);
        } else {
            currentProperty = (BasePropertyImpl) NavajoFactory.getInstance().createProperty(currentDocument, myName, type, myValue, length, description, direction, subtype);
        }

          Message current = messageStack.peek();
//          logger.info("Adding property: "+currentProperty.getName()+" to message: "+current.getFullMessageName());
          current.addProperty(currentProperty);


          BaseMessageImpl arrayParent = (BaseMessageImpl) current.getArrayParentMessage();
          //logger.info("current = " + current.getName() + ", type = " + current.getType());
          if ( arrayParent != null && arrayParent.isArrayMessage() ) {

            definitionProperty = arrayParent.getPropertyDefinition(myName);
            //logger.info("definitionProperty = " + definitionProperty + ", for name: " + myName);
            
            
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

//              if (myValue == null || "".equals(myValue)) {
//                myValue = definitionProperty.getValue();
//              }
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
            logger.info("Found undefined property: " + currentProperty.getName());
          }

          isListType = (type != null && type.equals(Property.SELECTION_PROPERTY));
          
          if (isListType && definitionProperty != null) {
        	  
        	  if (cardinality == null) {
        		  cardinality = definitionProperty.getCardinality();
        	  }
        	  type = Property.SELECTION_PROPERTY;
        	  
        	  ArrayList<Selection> l = definitionProperty.getAllSelections();
        	  for (int i = 0; i < l.size(); i++) {
        		  BaseSelectionImpl s1 = (BaseSelectionImpl) l.get(i);
        		  BaseSelectionImpl s2 = (BaseSelectionImpl) s1.copy(currentDocument);
        		  currentProperty.addSelection(s2);
        		  //logger.info("ADDING SELECTION: " + s2);
        	  }
          }
              currentProperty.setType(type);
              currentProperty.setDescription(description);
              currentProperty.setDirection(direction);
              currentProperty.setCardinality(cardinality);
              currentProperty.setLength(length);
              currentProperty.setExtends(extendsProp);
              currentProperty.setKey(key);
              currentProperty.setReference(reference);
//              createProperty(currentDocument,myName,cardinality,description,direction);


      }
    

    private final void parseMessage(Hashtable<String,String> h) throws NavajoException {
        String name = h.get("name");
        String type = h.get("type");
        String orderby = h.get("orderby");
        String scope = h.get(Message.MSG_SCOPE);
        String extendsMsg = h.get(Message.MSG_EXTENDS);
        String eTag = h.get(Message.MSG_ETAG);
        
        Message m = null;
        if (type!=null) {
            m = NavajoFactory.getInstance().createMessage(currentDocument, name,type);
        } else {
            m = NavajoFactory.getInstance().createMessage(currentDocument, name);
        }
        if(orderby != null && !"".equals(orderby)){
          m.setOrderBy(orderby);
        }
        if (scope != null && !"".equals(scope) ) {
        	m.setScope(scope);
        }
        if (extendsMsg != null && !"".equals(extendsMsg) ) {
        	m.setExtends(extendsMsg);
        }
        if (eTag != null && !"".equals(eTag) ) {
        	m.setEtag(eTag);
        }
        if (messageStack.isEmpty()) {
//            logger.info("Adding to root!");
            currentDocument.addMessage(m);
        } else {
        	// Don't add definition messages.
        	Message parentMessage = messageStack.peek();
            if ( !Message.MSG_TYPE_DEFINITION.equals(type) ) {
        		parentMessage.addMessage(m);
        	} else {
        		parentMessage.setDefinitionMessage(m);
                
        	}
        }
          messageStack.push(m);
//        logger.info("Stack: "+messageStack);
    }


    private void mergeDefinitionMessage() {

        Message currentMessage = messageStack.peek();
        if (currentMessage==null) {
            return;
        }
        // TODO: Shouldn't this be getArrayParentMessage?
        Message parentMessage = currentMessage.getParentMessage();
        if (parentMessage==null) {
            return;
        }
        Message def = parentMessage.getDefinitionMessage();
        if (def==null) {
            return;
        }
        ArrayList<Property> props = def.getAllProperties();
        for (int i = 0; i < props.size(); i++) {
            Property src = props.get(i);
            Property dest = currentMessage.getProperty(src.getName());
            if (dest==null) {
                dest = src.copy(currentDocument);
                currentMessage.addProperty(dest);
            }
        }
    }

    public final void endElement(String tag) throws Exception {
        if (tag.equals("tml")) {
         }
        if (tag.equals("message")) {
            mergeDefinitionMessage();
            messageStack.pop();
        }
        if (tag.equals("property")) {
            currentProperty = null;
        }
        if (tag.equals("option")) {
        }
        if (tag.equals("method")) {
        }
        if (tag.equals("header")) {
            currentHeader = null;
        }
        if (tag.equals("transaction")) {
        }
        if (tag.equals("callback")) {
        }
    }


  

    public final void startDocument() throws Exception {
          reset();
    }


    public final void endDocument() throws Exception {
        
    }


    public final void text(Reader r) throws Exception {
        if (currentProperty==null) {
            return;
//            throw new IllegalArgumentException("Huh?");
        }
        if (Property.BINARY_PROPERTY.equals(currentProperty.getType())) {
            Binary b = new Binary(r);
            String sub = currentProperty.getSubType();
            currentProperty.setValue(b,false);
            // Preserve the subtype. This will cause the handle to refer to the server
            // handle, not the client side
            currentProperty.setSubType(sub);
            // Set mime type if specified as subtype.
            if ( currentProperty.getSubType("mime") != null ) {
            	b.setMimeType(currentProperty.getSubType("mime"));
            }
         //  currentProperty.setInitialized();
        } else {
            throw new IllegalArgumentException("uuuh?");
       }
    }
    
    public final Navajo getNavajo() {
        return currentDocument;
    }


    public final String quoteStarted(int quoteCharacter, Reader r, String attributeName, String tagName,StringBuffer attributeBuffer) throws IOException {
        int c = 0;
        attributeBuffer.delete(0, attributeBuffer.length());
        while ((c = r.read()) != -1) {
            if (c==quoteCharacter) {
//                String s = sb.toString();
//                logger.info(">> "+s);
                return attributeBuffer.toString();
            } else {
//                logger.info("parsing char:"+(char)c);
            	attributeBuffer.append((char)c);
//                return sb.toString();
            }
        }        
        throw new EOFException("Non terminated quote!");
    }
    

}