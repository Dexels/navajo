package com.dexels.navajo.document.json.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.map.ObjectMapper;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.Selection;
import com.dexels.navajo.document.json.JSONTML;
import com.dexels.navajo.document.types.NavajoType;

/**
 * TODO: Create option to pass Navajo template for setting correct types.
 * 
 * @author arjenschoneveld
 *
 */
public class JSONTMLImpl implements JSONTML {
    
	private JsonFactory jsonFactory = null;
	private ObjectMapper om = null;
	private String topLevelMessageName = null;
	private boolean skipTopLevelMessage = false;
	private boolean typeIsValue = false;
	private Navajo entityTemplate = null;

	private final static String TOP_LEVEL_MSG = "__TOP__";

	public JSONTMLImpl() {
		jsonFactory =  new JsonFactory();
		// Use default typing.
		om = new ObjectMapper().enableDefaultTyping();
	}

	/* (non-Javadoc)
	 * @see com.dexels.navajo.document.json.impl.JSONTML#parse(java.io.InputStream)
	 */
	@Override
	public Navajo parse(InputStream is) throws Exception {
		try {
			JsonParser jp = jsonFactory.createJsonParser(is);
			Navajo n = parse(jp);
			return n;
		} catch (Exception e) {
			throw new Exception("Could not parse JSON inputstream: " + e.getMessage(), e);
		} 
	}
	
	@Override
	public Navajo parse(InputStream is, String topLevelMessageName) throws Exception {
		this.topLevelMessageName = topLevelMessageName;
		return parse(is);
	}

	/* (non-Javadoc)
	 * @see com.dexels.navajo.document.json.impl.JSONTML#parse(java.io.Reader)
	 */
	@Override
	public Navajo parse(Reader r) throws Exception {
		try {
			JsonParser jp = jsonFactory.createJsonParser(r);
			Navajo n = parse(jp);
			return n;
		} catch (Exception e) {
			throw new Exception("Could not parse JSON inputstream: " + e.getMessage());
		} 
	}

	@Override
	public Navajo parse(Reader r, String topLevelMessageName) throws Exception {
		this.topLevelMessageName = topLevelMessageName;
		return parse(r);
	}

	/* (non-Javadoc)
	 * @see com.dexels.navajo.document.json.impl.JSONTML#format(com.dexels.navajo.document.Navajo, java.io.OutputStream)
	 */
	@Override
	public void format(Navajo n, OutputStream os) throws Exception {
	    JsonGenerator jg = null;
		try {
			jg = jsonFactory.createJsonGenerator(os); 

			jg.useDefaultPrettyPrinter();
			format(jg, n);
		} catch (Exception e) {
			throw new Exception("Could not parse JSON inputstream: " + e.getMessage());
		} finally {
		    if (jg != null) {
		        jg.close();
		    }
		}

	}

	@Override
	public void format(Navajo n, OutputStream os, boolean skipTopLevelMessage) throws Exception {
		this.skipTopLevelMessage = skipTopLevelMessage;
		format(n, os);
	}

	/* (non-Javadoc)
	 * @see com.dexels.navajo.document.json.impl.JSONTML#format(com.dexels.navajo.document.Navajo, java.io.Writer)
	 */
	@Override
	public void format(Navajo n, Writer w) throws Exception {
	    JsonGenerator jg = null;
		try {
			jg = jsonFactory.createJsonGenerator(w); 
			jg.useDefaultPrettyPrinter();
			format(jg, n);
		} catch (Exception e) {
			throw new Exception("Could not parse JSON inputstream" ,e);
		} finally {
		    if (jg != null) {
		        jg.close();
		    }
		}
	}

	@Override
	public void format(Navajo n, Writer w, boolean skipTopLevelMessage) throws Exception {
		this.skipTopLevelMessage = skipTopLevelMessage;
		format(n, w);
	}
	
	@Override
	public void formatDefinition(Navajo n, Writer w, boolean skipTopLevelMessage) throws Exception {
		this.skipTopLevelMessage = skipTopLevelMessage;
		this.typeIsValue = true;
		format(n, w);

	}
	

	private void format(JsonGenerator jg, Property p) throws Exception {

		jg.writeFieldName(p.getName());
		if ( p.getType().equals(Property.SELECTION_PROPERTY)) {
			if ( p.getSelected() != null ) {
				om.writeValue(jg, p.getSelected().getValue());
			} else {
				om.writeValue(jg, "null");
			}
		} else {
			if (this.typeIsValue) {
			    String value = p.getType();
			    if (isOptionalKey(p.getKey())) {
			        value += ", optional";
			    }
				om.writeValue(jg, value);
			} else {
				Object value = p.getTypedValue();
				if (p.getType().equals(Property.BINARY_PROPERTY)) {
					value = p.getValue();
				} else if (value instanceof NavajoType) {
				    // Use toString for NavajoTypes
				    value = value.toString();
				}
				om.writeValue(jg, value );

			}
		}

	}
	
	private boolean isOptionalKey(String key) {
        return ( key != null && key.indexOf("optional") != -1 );
    }

	private void format(JsonGenerator jg, Message m, boolean arrayElement) throws Exception {

		if (!arrayElement && !m.getName().equals(TOP_LEVEL_MSG) ) {
			jg.writeFieldName(  m.getName() );
		}
		
		if ( !m.isArrayMessage() && !m.getName().equals(TOP_LEVEL_MSG) ) {
			jg.writeStartObject();
		}

		if ( m.isArrayMessage() ) {
			jg.writeStartArray();
		}

		List<Property> properties = m.getAllProperties();
		for ( Property p : properties) {
			format(jg, p);
		}

		List<Message> messages = new ArrayList<Message>();
		if (m.isArrayMessage()) {
			if (typeIsValue) {
				// Print definition message
				messages.add(m.getDefinitionMessage());
			} else {
				messages.addAll(m.getElements());
			}
		} else { 
			messages = m.getAllMessages();
		}
		
		for ( Message c:  messages) {
			format(jg, c, m.isArrayMessage());
		}
		
		

		if ( m.isArrayMessage() ) {
			jg.writeEndArray();
		} else if ( !m.getName().equals(TOP_LEVEL_MSG) ) {
			jg.writeEndObject();
		}

	}

    private void format(JsonGenerator jg, Navajo n) throws Exception {
        try {
            if (skipTopLevelMessage && n.getMessages().size() == 1) {
                // If skipTopLevelMessage=true, we have one message to write and
                // this is an array message, we have to start our message as an 
                // array rather than object.

                Collection<Message> messages = n.getMessages().values();
                Message msg = messages.iterator().next();
                if (msg.isArrayMessage()) {
                    jg.writeStartArray();
                    for (Message arrayElement : msg.getElements()) {
                        format(jg, arrayElement, true);
                    }
                    jg.writeEndArray();
                    return;
                }
            }

            jg.writeStartObject();
            formatMessages(jg, n.getAllMessages());
            jg.writeEndObject();

        } catch (IOException ioe) {
            throw ioe;
        } catch (Exception e) {
            throw new Exception("Could not format Navajo as JSON stream", e);
        }
    }

    private void formatMessages(JsonGenerator jg, List<Message> messages) throws Exception {
        for (Message m : messages) {
            String origName = TOP_LEVEL_MSG;
            try {
                if (skipTopLevelMessage && !m.isArrayMessage()) {
                    origName = m.getName();
                    m.setName(TOP_LEVEL_MSG);
                }
                format(jg, m, false);
            } finally {
                if (skipTopLevelMessage) {
                    m.setName(origName);
                }
            }
        }
    }

	private void parseProperty(String name, String value, Message p, JsonParser jp) throws Exception {
		if (name == null) {
			// Give property the name of the message
			name = p.getName();
		}
		Property prop = NavajoFactory.getInstance().createProperty(p.getRootDoc(), name, Property.STRING_PROPERTY, value, 0, "", "out");
		p.addProperty(prop);
		if ( entityTemplate != null ) {
			Property ep = entityTemplate.getProperty(prop.getFullPropertyName());
 			if ( ep != null ) {
				if ( ep.getType().equals(Property.SELECTION_PROPERTY)) {
					Selection s = NavajoFactory.getInstance().createSelection(p.getRootDoc(), value, value, true);
					prop.setType(ep.getType());
					prop.addSelection(s);
				} else {
					prop.setType(ep.getType());
					prop.setMethod(ep.getMethod());
				}
 			}
		}

	}

	private void parseArrayMessageElement(Message arrayMessage, JsonParser jp) throws Exception {
		Message m = NavajoFactory.getInstance().createMessage(arrayMessage.getRootDoc(), arrayMessage.getName());
		arrayMessage.addElement(m);
		if (jp.getCurrentToken() == JsonToken.START_OBJECT) {
			// Array with sub message structure
			parse(arrayMessage.getRootDoc(), m, jp);
		} else {
			String name = jp.getCurrentName();
			String value = jp.getText();
			parseProperty(name, value, m, jp);
		}
	}

	private void parseArrayMessage(String name, Navajo n, Message parent, JsonParser jp) throws Exception {
		Message m = NavajoFactory.getInstance().createMessage(n, name);
		m.setType(Message.MSG_TYPE_ARRAY);
		if ( parent != null ) {
			parent.addMessage(m);
		} else {
			n.addMessage(m);
		}
		while ( jp.nextToken() != JsonToken.END_ARRAY ) {
			
			parseArrayMessageElement(m, jp);
		}
	}

	private void parseMessage(String name, Navajo n, Message parent, JsonParser jp) throws Exception {
		Message m = NavajoFactory.getInstance().createMessage(n, name);
		parent.addMessage(m);
		parse(n, m, jp);
	}

    private void parse(Navajo n, Message parent, JsonParser jp) throws Exception {
        if (jp.getCurrentToken() == JsonToken.START_ARRAY) {
            parent.setType("array");
            while (jp.nextToken() != JsonToken.END_ARRAY) {
                Message m = NavajoFactory.getInstance().createMessage(n, topLevelMessageName != null ? topLevelMessageName : "Request");
                parent.addMessage(m);
                parse(n, m, jp);
                
            }
        } else if (jp.getCurrentToken() == JsonToken.START_OBJECT) {
            while (jp.nextToken() != JsonToken.END_OBJECT) {
                String name = jp.getCurrentName();
                if (name != null && jp.getCurrentToken() == JsonToken.FIELD_NAME) {
                    jp.nextToken();
                }
                if (name == null) {
                    name = topLevelMessageName != null ? topLevelMessageName : "Request";
                }

                if (jp.getCurrentToken() == JsonToken.START_OBJECT) {
                    parseMessage(name, n, parent, jp);
                } else if (jp.getCurrentToken() == JsonToken.START_ARRAY) {
                    parseArrayMessage(name, n, parent, jp);
                } else {
                    String value = jp.getText();
                    if (value.equals("null")) {
                        value = null;
                    }
                    parseProperty(name, value, parent, jp);
                }

            }
        } else {
            throw new Exception("Unexpected character - expected { or [ but got: " + jp.getCurrentToken().asString());
        }

    }

	private Navajo parse(JsonParser jp) throws Exception {
		Navajo n = NavajoFactory.getInstance().createNavajo();

        Message parent = NavajoFactory.getInstance().createMessage(n,(topLevelMessageName != null ? topLevelMessageName : "Request"));
        n.addMessage(parent);
        
        while (jp.nextToken() != null) {
            parse(n, parent, jp);
        }
		return n;
	}

	@Override
	public void setEntityTemplate(Navajo m) {
		this.entityTemplate = m;
	}

	@Override
	public void setDateFormat(DateFormat format) {
		if (om == null) {
			return;
		}
		om.setDateFormat(format);
	}
	

}
