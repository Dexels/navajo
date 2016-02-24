package com.dexels.navajo.document.stream.impl;


import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Method;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.base.BaseNode;
import com.dexels.navajo.document.stream.NavajoStreamHandler;
import com.dexels.navajo.document.stream.api.NavajoHead;
import com.dexels.navajo.document.stream.api.Prop;
import com.dexels.navajo.document.stream.api.Select;
import com.dexels.navajo.document.stream.xml.XmlInputHandler;
import com.dexels.navajo.document.types.Binary;

import rx.Observable;
import rx.Observable.OnSubscribe;
import rx.Subscriber;

public final class StreamSaxHandler implements XmlInputHandler {

//    private Navajo currentDocument=null;
//    private final Stack<String> messageNameStack = new Stack<>();
    private List<Prop> currentProperties = new ArrayList<>();
    private Stack<Map<String,String>> attributeStack = new Stack<>();
    
//    private Message currentMessage = null;
//    private Prop currentProperty = null;
    private Method currentMethod = null;
	private final NavajoStreamHandler handler;
    
//	private Writer currentBinaryWriter = null;
//	private Binary currentBinary = null; 

//	private final Map<String,AtomicInteger> arrayCount = new HashMap<>();
	private Map<String, String> transactionAttributes;
	private Map<String, String> piggybackAttriutes;
	private Map<String, String> asyncAttributes;
	private final List<Select> currentSelections = new ArrayList<>();
	private Binary currentBinary;
//	private Writer currentBinaryWriter;
//	private boolean binaryStarted;
	private final static Logger logger = LoggerFactory
			.getLogger(StreamSaxHandler.class);

	private Subscriber<? super String> binarySink;
	
	public StreamSaxHandler(NavajoStreamHandler handler) {
		this.handler = handler;
	}
    @Override
	public final void startElement(String tag, Map<String,String> h) {
    	
    	attributeStack.push(h);
        // Unescape all the shit.
        if (tag.equals("tml")) {
//            currentDocument = NavajoFactory.getInstance().createNavajo();

            return;
        }
        if (tag.equals("message")) {
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
            return;
        }
        if (tag.equals("property")) {
        	String propType = h.get("type");
        	if("binary".equals(propType)) {
        		Observable.<String>create(new OnSubscribe<String>() {

					@Override
					public void call(Subscriber<? super String> s) {
						binarySink = s;
					}
				}).lift(BinaryObserver.collect()).subscribe(binary->{
//					currentProperty = currentProperty.withValue(binary);
	               currentBinary = binary;
				});
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
//        BaseTransactionImpl bci = new BaseTransactionImpl(currentDocument);
//        bci.setRpc_name(h.get("rpc_name"));
//        bci.setRpc_usr(h.get("rpc_usr"));
//        bci.setRpc_pwd(h.get("rpc_pwd"));
//        bci.setRpc_schedule(h.get("rpc_schedule"));
//        bci.setRequestId(h.get("requestid"));
//        currentHeader.addTransaction(bci);        
    }

    private final void parsePiggyback(Map<String,String> h) {
        this.piggybackAttriutes = h;
    }
    
    
    private final void parseHeader(Map<String,String> headerAttributes) {
        NavajoHead newHeader = NavajoHead.create(headerAttributes,transactionAttributes,piggybackAttriutes,asyncAttributes);
        handler.navajoStart(newHeader);

    }

    private final void parseMethod(Map<String,String> h) throws NavajoException {
//        String name = h.get("name");
//        currentMethod = NavajoFactory.getInstance().createMethod(currentDocument, name, null);
//        currentDocument.addMethod(currentMethod);
    }

    private final void parseSelection(Map<String,String> h) throws NavajoException {
    	this.currentSelections.add(Select.create(h.get("name"), h.get("value"), (Integer.parseInt(h.get("selected"))>0)));
    }

    @Override
	public final void endElement(String tag) {
    	Map<String,String> attributes = attributeStack.pop();
        if (tag.equals("tml")) {
        	handler.navajoDone();
         }
        if (tag.equals("message")) {
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
//            messageNameStack.pop();
        }
        if (tag.equals("property")) {
        	Prop currentProperty;
        	String val = attributes.get("value");
        	if("selection".equals(attributes.get("type"))) {
                currentProperty = Prop.create(attributes,currentSelections);
        	} else if (val!=null) {
            	Hashtable<String,String> h2 = new Hashtable<String,String>(attributes);
    			val = BaseNode.XMLUnescape(val);
    			h2.put("value", val);
                currentProperty = Prop.create(h2,currentSelections);
            } else if(binarySink!=null) {
            	binarySink.onCompleted();
            	if(currentBinary!=null) {
                    currentProperty = Prop.create(attributes,currentBinary);
            		currentBinary = null;
            	} else {
            		logger.error("Null currentBinary where not expected");
            		throw new RuntimeException("Null currentBinary where not expected");
            	}
            } else {
                currentProperty = Prop.create(attributes,currentSelections);
			}
//            String sub = currentProperty.subtype();
//            currentProperty.withValue(currentBinary); //.value(currentBinary);
//            // Preserve the subtype. This will cause the handle to refer to the server
//            // handle, not the client side
//            currentProperty.subtype(sub);
//            // Set mime type if specified as subtype.
//            if ( currentProperty.subtypes("mime") != null ) {
//            	currentBinary.setMimeType(currentProperty.subtypes("mime"));
//            }        	
        	
        	currentProperties.add(currentProperty);
        	currentProperty = null;
        	currentSelections.clear();
        	binarySink = null;
        }
        if (tag.equals("option")) {
//        	parseSelection(attributes);
//        	currentProperty.addSelect(s);
        	
        }
        if (tag.equals("method")) {
        	
        }
        if (tag.equals("header")) {
            parseHeader(attributes);
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

    @Override
	public final void text(String r)  {
//        if (currentProperty==null) {
//            return;
//            throw new IllegalArgumentException("Huh?");
//        }

        if (this.binarySink!=null) {
        	this.binarySink.onNext(r);
         //  currentProperty.setInitialized();
        } else {
        	if(!"".equals(r.trim())) {
                throw new IllegalArgumentException("uuuh: "+r);
        	}
       }
    }

}