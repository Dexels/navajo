package com.dexels.navajo.document.stream.impl;


import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.Stack;

import org.dexels.utils.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Method;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.Selection;
import com.dexels.navajo.document.base.BaseHeaderImpl;
import com.dexels.navajo.document.base.BaseMessageImpl;
import com.dexels.navajo.document.base.BaseNode;
import com.dexels.navajo.document.base.BaseObjectImpl;
import com.dexels.navajo.document.base.BasePropertyImpl;
import com.dexels.navajo.document.base.BaseTransactionImpl;
import com.dexels.navajo.document.stream.NavajoStreamHandler;
import com.dexels.navajo.document.stream.xml.XmlInputHandler;
import com.dexels.navajo.document.types.Binary;

public final class StreamSaxHandler implements XmlInputHandler {

    private Navajo currentDocument=null;
    private final Stack<Message> messageStack = new Stack<>();
    private final Stack<String> messageNameStack = new Stack<>();
//    private Message currentMessage = null;

    private BasePropertyImpl currentProperty = null;
    private BaseHeaderImpl currentHeader;
    private Method currentMethod = null;
	private final NavajoStreamHandler handler;
    
	private Writer currentBinaryWriter = null;
	private Binary currentBinary = null; 

	private final Map<String,AtomicInteger> arrayCount = new HashMap<>();

	private final static Logger logger = LoggerFactory
			.getLogger(StreamSaxHandler.class);

	public StreamSaxHandler(NavajoStreamHandler handler) {
		this.handler = handler;
	}
    @Override
	public final void startElement(String tag, Map<String,String> h) {
        // Unescape all the shit.
        if (tag.equals("tml")) {
            currentDocument = NavajoFactory.getInstance().createNavajo();
        	handler.navajoStart();

            return;
        }
        if (tag.equals("message")) {
//        	String path = getPath();
        	// beware refactoring, parseMessage influences getPath
        	Message msg = parseMessage(h);
        	
        	String path = getPath(msg);
        	if(Message.MSG_TYPE_ARRAY_ELEMENT.equals(msg.getType())) {
            	handler.arrayElementStarted(msg, path);
        	} else {
            	handler.messageStarted(msg, path);
        	}
            return;
        }
        if (tag.equals("property")) {
            String val = h.get("value");
            if (val!=null) {

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
    private final void parseAgent(Map<String,String> h) {
    }


    private final void parseRequired(Map<String,String> h) {
        currentMethod.addRequired(h.get("name"));
    }


    private final void parseObject(Map<String,String> h) {
    	
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
    private final void parseMethods(Map<String,String> h) {
        
    }

    private final void parseOperations(Map<String,String> h) {
        
    }

    /**
	 * @param h parameter callback 
	 */
    private final void parseCallback(Map<String,String> h) {
        if (currentHeader==null) {
            throw new IllegalArgumentException("Callback tag outside header tag.");
        }       
    }

    private final void parseTransaction(Map<String,String> h) {
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

    private final void parsePiggyback(Map<String,String> h) {
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
    
    
    private final void parseHeader(Map<String,String> h) {
        BaseHeaderImpl bhi = new BaseHeaderImpl(currentDocument);
        currentHeader = bhi;
        for (Iterator<Entry<String,String>> iter = h.entrySet().iterator(); iter.hasNext();) {
        	Entry<String,String> e = iter.next();
			String element = e.getKey();
			String value = e.getValue();
			bhi.setHeaderAttribute(element, value);
        }
        currentDocument.addHeader(bhi);
        
    }

    private final void parseMethod(Map<String,String> h) throws NavajoException {
        String name = h.get("name");
        currentMethod = NavajoFactory.getInstance().createMethod(currentDocument, name, null);
        currentDocument.addMethod(currentMethod);
    }

    private final void parseSelection(Map<String,String> h) throws NavajoException {
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

    private final Message getCurrentMessage() {
    	return messageStack.peek();
    }
    private final void parseProperty(Map<String,String> h) throws NavajoException {
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
        String bind = h.get(Property.PROPERTY_BIND);
        String method = h.get(Property.PROPERTY_METHOD);
        
//         Integer plength = null;
//        Property definitionProperty = null;
        String subtype = null;
        int length = 0;
        try {
          if (sLength != null) {
            length = Integer.parseInt(sLength);
//            plength = new Integer(length);
          }
        }
        catch (Exception e1) {
        	logger.error("Error: ", e1);
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
          getCurrentMessage().addProperty(currentProperty);
//          currentMessage.write(System.err);

          if (subType == null &&
              NavajoFactory.getInstance().getDefaultSubtypeForType(type) != null) {
        	  currentProperty.setSubType(NavajoFactory.getInstance().getDefaultSubtypeForType(type));
          }
          else {
        	  currentProperty.setSubType(subType);
          }

          if (type == null &&  getCurrentMessage().isArrayMessage() ) {
            logger.info("Found undefined property: " + currentProperty.getName());
          }

          isListType = (type != null && type.equals(Property.SELECTION_PROPERTY));

              currentProperty.setType(type);
              currentProperty.setDescription(description);
              currentProperty.setDirection(direction);
              currentProperty.setCardinality(cardinality);
              currentProperty.setLength(length);
              currentProperty.setExtends(extendsProp);
              currentProperty.setKey(key);
              currentProperty.setReference(reference);
              currentProperty.setBind(bind);
              currentProperty.setMethod(method);
      }
    

    private final Message parseMessage(Map<String,String> h) throws NavajoException {
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
//        currentMessage = m;
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
//        if (messageStack.isEmpty()) {
////            logger.info("Adding to root!");
//            currentDocument.addMessage(m);
//        } else {
//        	// Don't add definition messages.
//        	Message parentMessage = messageStack.peek();
//            if ( !Message.MSG_TYPE_DEFINITION.equals(type) ) {
//        		parentMessage.addMessage(m);
//        	} else {
//        		parentMessage.setDefinitionMessage(m);
//                
//        	}
//        }
          messageStack.push(m);
          if(m.getType().equals(Message.MSG_TYPE_ARRAY)) {
//              messageNameStack.push(name);
              String path = getPath(m);
        	  handler.arrayStarted(m,path);
        	  arrayCount.put(path, new AtomicInteger(0));
//              messageNameStack.push(name);
          } else {
        	  if(m.getType().equals(Message.MSG_TYPE_ARRAY_ELEMENT)) {
                  String parentpath = getPath(m);
                  StringBuilder sb = new StringBuilder(parentpath);
                  if(!parentpath.equals("")) {
                	  sb.append("/");
                  }
                  sb.append(name);
                  AtomicInteger count = arrayCount.get(sb.toString());
                  sb.append("@");
                  
				int index = count.getAndIncrement();
				sb.append(""+index);
				m.setIndex(index);
                  messageNameStack.push(sb.toString());
        	  } else if (m.getType().equals(Message.MSG_TYPE_DEFINITION)) {
                  messageNameStack.push(name+"@definition");
        	  } else {
                  messageNameStack.push(name);
        	  }
              
          }
          return m;
//        logger.info("Stack: "+messageStack);
    }


//    private void mergeDefinitionMessage(Message currentMessage, Message def) {
//        List<Property> props = def.getAllProperties();
//        for (int i = 0; i < props.size(); i++) {
//            Property src = props.get(i);
//            Property dest = currentMessage.getProperty(src.getName());
//            if (dest==null) {
//                dest = src.copy(currentDocument);
//                currentMessage.addProperty(dest);
//            }
//        }
//    }

    @Override
	public final void endElement(String tag) {
        if (tag.equals("tml")) {
        	handler.navajoDone();
         }
        if (tag.equals("message")) {
            Message m = messageStack.pop();
            m.write(System.err);
        	String path = getPath(m);
            if(Message.MSG_TYPE_DEFINITION.equals(m.getType())) {
                messageNameStack.pop();
            	messageStack.peek().setDefinitionMessage(m);
                handler.messageDone(m,path);
            } else if (Message.MSG_TYPE_ARRAY.equals(m.getType())) {
            	handler.arrayDone(m,path);
            } else {
            	if(!messageStack.isEmpty() && Message.MSG_TYPE_ARRAY.equals(messageStack.peek().getType())) {
//            		Message definitionMessage = messageStack.peek().getDefinitionMessage();
//					if(definitionMessage!=null) {
//						mergeDefinitionMessage(m, definitionMessage);
//					}
                    messageNameStack.pop();

            		handler.arrayElement(m,path);
            	} else {
                    messageNameStack.pop();

                    handler.messageDone(m,path);
            	}
            	
            }
        }
        if (tag.equals("property")) {
        	if(currentBinaryWriter!=null) {
        		try {
					currentBinaryWriter.flush();
					currentBinaryWriter.close();
				} catch (IOException e) {
					logger.error("Error closing binarywriter: ",e);
				}
				currentBinaryWriter = null;
        	}
        	if(currentBinary!=null) {
        		try {
					currentBinary.getOutputStream().close();
				} catch (IOException e) {
					logger.error("Error closing binary: ",e);
					}
        		currentBinary = null;
        	}
        	
        	currentProperty = null;
        }
        if (tag.equals("option")) {
        }
        if (tag.equals("method")) {
        }
        if (tag.equals("header")) {
        	handler.header(currentHeader);
            currentHeader = null;
        }
        if (tag.equals("transaction")) {
        }
        if (tag.equals("callback")) {
        }
    }


  

    @Override
	public final void startDocument() {
    }


    @Override
	public final void endDocument() {
    	// the navajoDone event is emitted by the tml end tag
//        handler.navajoDone();
    }

    private String getPath(Message message) {
    	StringBuilder s = new StringBuilder();
    	for (int i = 0; i< messageNameStack.size();i++) {
    		s.append(messageNameStack.get(i));
    		if(i<messageNameStack.size()-1) {
        		s.append("/");
    		}
		}
		if(message.isArrayMessage()) {
			if(s.length()>0) {
				s.append("/");
			}
			s.append(message.getName());
		}

//    	if(Message.MSG_TYPE_ARRAY_ELEMENT.equals(message.getType())) {
//    		if(s.length()!=0) {
//    			s.append("/");
//    			s.append(message.getName());
//    			s.append("@");
//    			s.append(message.getIndex());
//    		}
//    	}
//    	s.deleteCharAt(s.length()-1);
    	return s.toString();
    }

    @Override
	public final void text(String r)  {
        if (currentProperty==null) {
            return;
//            throw new IllegalArgumentException("Huh?");
        }

        if (Property.BINARY_PROPERTY.equals(currentProperty.getType())) {
            if(currentBinary==null) {
        		currentBinary = new Binary();
        		currentBinaryWriter = Base64.newDecoder(currentBinary.getOutputStream());
            }
            String sub = currentProperty.getSubType();
            currentProperty.setValue(currentBinary,false);
            // Preserve the subtype. This will cause the handle to refer to the server
            // handle, not the client side
            currentProperty.setSubType(sub);
            // Set mime type if specified as subtype.
            if ( currentProperty.getSubType("mime") != null ) {
            	currentBinary.setMimeType(currentProperty.getSubType("mime"));
            }
            try {
				currentBinaryWriter.write(r);
			} catch (IOException e) {
				logger.error("Error writing to binary: ",e);
			}
         //  currentProperty.setInitialized();
        } else {
            throw new IllegalArgumentException("uuuh: "+r);
       }
    }
    
    public final Navajo getNavajo() {
        return currentDocument;
    }



}