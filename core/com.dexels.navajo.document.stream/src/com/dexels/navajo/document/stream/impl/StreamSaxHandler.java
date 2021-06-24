/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.document.stream.impl;


import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Stack;

import org.apache.commons.lang.StringEscapeUtils;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.base.BaseNode;
import com.dexels.navajo.document.stream.NavajoStreamHandler;
import com.dexels.navajo.document.stream.api.Method;
import com.dexels.navajo.document.stream.api.NavajoHead;
import com.dexels.navajo.document.stream.api.Prop;
import com.dexels.navajo.document.stream.api.Select;
import com.dexels.navajo.document.stream.xml.XmlInputHandler;

public final class StreamSaxHandler implements XmlInputHandler {

    private Stack<List<Prop>> currentPropertiesStack = new Stack<>();
    private Stack<Map<String,String>> attributeStack = new Stack<>();
//    private Method currentMethod = null;
	private final NavajoStreamHandler handler;
	private Map<String, String> transactionAttributes;
	private Map<String, String> piggybackAttriutes;
	private Map<String, String> asyncAttributes;
	private final List<Select> currentSelections = new ArrayList<>();
    private List<Method> methods = new ArrayList<>();
    
//    private final Stack<Msg> msgStack = new Stack<>();
	
	public StreamSaxHandler(NavajoStreamHandler handler) {
		this.handler = handler;
	}

	@Override
	public final int startElement(String tag, Map<String,String> h) {
    	
    	attributeStack.push(h);
        // Unescape all the shit.
        if (tag.equals("tml")) {
            return 0;
        }
        if (tag.equals("message")) {
        	currentPropertiesStack.push(new ArrayList<>());
        	String type = h.get("type");
        	if(Message.MSG_TYPE_ARRAY_ELEMENT.equals(type)) {
            	handler.arrayElementStarted();
        	} else if(Message.MSG_TYPE_ARRAY.equals(type)) {
            	handler.arrayStarted(h);
        	} else if(Message.MSG_TYPE_DEFINITION.equals(type)) {
                handler.messageDefinitionStarted(h);
        	} else {
            	handler.messageStarted(h);
        	}
            return 1;
        }
        if (tag.equals("property")) {
        	String propType = h.get("type");
        	if("binary".equals(propType)) {
        		String lengthString = h.get("length");
        		int length = lengthString == null ? 0 : Integer.parseInt(lengthString);
        		Optional<String> description = Optional.ofNullable(h.get("description"));
        		Optional<String> subtype = Optional.ofNullable(h.get("subtype"));
        		Optional<String> direction = Optional.ofNullable(h.get("direction"));
        		handler.binaryStarted(h.get("name"),length,description,direction,subtype);
        		return 1;
        	}
//            String val = h.get("value");
//            if (val!=null) {
//
//            	Hashtable<String,String> h2 = new Hashtable<String,String>(h);
//    			val = BaseNode.XMLUnescape(val);
//    			h2.put("value", val);
//                currentProperty = Prop.create(h2,currentSelections);
//            } else {
//                currentProperty = Prop.create(h,currentSelections);
//			}
//            currentProperties.add(currentProperty);
//            currentSelections.clear();

        	return 0;
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
            return 0;
        }
        if (tag.equals("method")) {
            parseMethod(h);
            return 0;
        }
        if (tag.equals("header")) {
            return 0;
        }
        if (tag.equals("transaction")) {
            parseTransaction(h);
            return 0;
        }
        if (tag.equals("callback")) {
            parseCallback(h);
            return 0;
        }
        if (tag.equals("methods")) {
            parseMethods(h);
            return 0;
        }
        if (tag.equals(Navajo.OPERATIONS_DEFINITION)) {
        	parseOperations(h);
        	return 0;
        }
        if (tag.equals("client")) {
            return 0;
        }
        if (tag.equals("server")) {
            return 0;
        }
        if (tag.equals("agent")) {
            parseAgent(h);
            return 0;
        }
        if (tag.equals("required")) {
            parseRequired(h);
            return 0;
        }
        if (tag.equals("object")) {
            parseObject(h);
            return 0;
        }
        if (tag.equals("piggyback")) {
            parsePiggyback(h);
            return 0;
        }        
        return 0;
    }
    
    
    /**
	 * @param h parameter callback 
	 */
    private final void parseAgent(Map<String,String> h) {
    }


    private final void parseRequired(Map<String,String> h) {
//        currentMethod.addRequired(h.get("name"));
    }


    private final void parseObject(Map<String,String> h) {
    	
    	this.asyncAttributes = h;
  
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
   
    }

    private final void parseTransaction(Map<String,String> h) {
        this.transactionAttributes = h;
    }

    private final void parsePiggyback(Map<String,String> h) {
        this.piggybackAttriutes = h;
    }
    
    
    private final void parseHeader(Map<String,String> headerAttributes) {
        NavajoHead newHeader = NavajoHead.create(headerAttributes,transactionAttributes,piggybackAttriutes,asyncAttributes);
        handler.navajoStart(newHeader);

    }

    private final void parseMethod(Map<String,String> h) throws NavajoException {
        String name = h.get("name");
        methods.add(new Method(name));
//        currentMethod = NavajoFactory.getInstance().createMethod(currentDocument, name, null);
//        currentDocument.addMethod(currentMethod);
    }

    private final void parseSelection(Map<String,String> h) throws NavajoException {
    	this.currentSelections.add(Select.create(h.get("name"), h.get("value"), (Integer.parseInt(h.get("selected"))>0)));
    }

    @Override
	public final int endElement(String tag) {
    		Map<String,String> attributes = attributeStack.pop();
        if (tag.equals("tml")) {
        	handler.navajoDone(methods);
        	return 1;
         }
        if (tag.equals("message")) {
        	List<Prop> currentProperties = currentPropertiesStack.pop();
        	String type = attributes.get("type");
        	String name = attributes.get("name");
            if(Message.MSG_TYPE_DEFINITION.equals(type)) {
                handler.messageDefinition(attributes,currentProperties);
            } else if (Message.MSG_TYPE_ARRAY.equals(type)) {
            	handler.arrayDone(name);
            } else {
            	if(Message.MSG_TYPE_ARRAY_ELEMENT.equals(type)) {
            		handler.arrayElement(currentProperties);
            	} else {
                    handler.messageDone(attributes,currentProperties);
            	}
            }
            currentProperties.clear();
            return 1;
        }
        if (tag.equals("property")) {
        	Prop currentProperty;
        	String val =  attributes.get("value");
        	if("selection".equals(attributes.get("type"))) {
                currentProperty = Prop.create(attributes,currentSelections);
        	} else if (val!=null) {
            	Hashtable<String,String> h2 = new Hashtable<String,String>(attributes);
    			val = StringEscapeUtils.unescapeXml(val); // BaseNode.XMLUnescape(val);
    			h2.put("value", val);
                currentProperty = Prop.create(h2,currentSelections);
            } else if("binary".equals(attributes.get("type"))) {
            	handler.binaryDone();
            	return 1;
            } else {
                currentProperty = Prop.create(attributes,currentSelections);
			}
        	currentPropertiesStack.peek().add(currentProperty);
//        	currentProperties.add(currentProperty);
        	currentProperty = null;
        	currentSelections.clear();
        	return 0;
        }
        if (tag.equals("header")) {
            parseHeader(attributes);
            return 1;
        }

        if (tag.equals("option")) {
        	
        }
        if (tag.equals("method")) {
        	
        }
        if (tag.equals("transaction")) {
        }
        if (tag.equals("callback")) {
        }
        return 0;
    }


  

    @Override
	public final int startDocument() {
    	return 0;
    }


    @Override
	public final int endDocument() {
    	return 0;
    }

    @Override
	public final int text(String r)  {
    	if(!"".equals(r.trim())) {
        	handler.binaryContent(r);
        	return 1;
    	}
    	return 0;
    }

}