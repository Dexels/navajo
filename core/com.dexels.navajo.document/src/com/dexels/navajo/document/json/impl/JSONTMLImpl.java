package com.dexels.navajo.document.json.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
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
	
	public Navajo parse(Reader r, String topLevelMessageName) throws Exception {
		this.topLevelMessageName = topLevelMessageName;
		return parse(r);
	}
	
	/* (non-Javadoc)
	 * @see com.dexels.navajo.document.json.impl.JSONTML#format(com.dexels.navajo.document.Navajo, java.io.OutputStream)
	 */
	@Override
	public void format(Navajo n, OutputStream os) throws Exception {
		try {
			
		
			//ObjectMapper mapper = new ObjectMapper().enableDefaultTyping();
			
			JsonGenerator jg = jsonFactory.createJsonGenerator(os); 
			
			jg.useDefaultPrettyPrinter();
			format(jg, n);
			jg.close();
		} catch (Exception e) {
			throw new Exception("Could not parse JSON inputstream: " + e.getMessage());
		} 

	}
	
	/* (non-Javadoc)
	 * @see com.dexels.navajo.document.json.impl.JSONTML#format(com.dexels.navajo.document.Navajo, java.io.Writer)
	 */
	@Override
	public void format(Navajo n, Writer w) throws Exception {
		try {
			JsonGenerator jg = jsonFactory.createJsonGenerator(w); 
			jg.useDefaultPrettyPrinter();
			format(jg, n);
			jg.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Could not parse JSON inputstream: " + e.getMessage());
		} 
	}
	
	public void format(Navajo n, Writer w, boolean skipTopLevelMessage) throws Exception {
		this.skipTopLevelMessage = skipTopLevelMessage;
		format(n, w);
	}
	
	private void format(JsonGenerator jg, Property p) throws Exception {

		jg.writeFieldName(p.getName());
		om.writeValue(jg, p.getTypedValue());
		//jg.writeObject(p.getTypedValue());

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

		List<Message> messages = ( m.isArrayMessage() ? m.getElements() : m.getAllMessages() );
		for ( Message c: messages) {
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
		jg.writeStartObject();
		List<Message> messages = n.getAllMessages();
		for ( Message m: messages) {
			if ( skipTopLevelMessage ) {
				m.setName(TOP_LEVEL_MSG);
			}
			format(jg, m, false);
		}
		jg.writeEndObject();
		} catch (IOException ioe) {
			throw ioe;
		} catch (Exception e) {
			e.printStackTrace(System.err);
			throw new Exception("Could not format Navajo as JSON stream: " + e.getMessage());
		}
	}

	private void parseProperty(String name, String value, Message p, JsonParser jp) throws Exception {
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
				}
			} else {
				System.err.println("Could not find property in template: " + prop.getFullPropertyName());
			}
		}
		
	}

	private void parseArrayMessageElement(Message arrayMessage, JsonParser jp) throws Exception {
		Message m = NavajoFactory.getInstance().createMessage(arrayMessage.getRootDoc(), arrayMessage.getName());
		arrayMessage.addElement(m);
		parse(arrayMessage.getRootDoc(), m, jp);
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
		if ( parent != null ) {
			parent.addMessage(m);
		} else {
			n.addMessage(m);
		}
		parse(n, m, jp);
	}

	private void parse(Navajo n, Message parent, JsonParser jp) throws Exception {

		while ( jp.nextToken() != JsonToken.END_OBJECT ) {
			String name = jp.getCurrentName();
			if ( name != null && jp.getCurrentToken() == JsonToken.FIELD_NAME ) {
				jp.nextToken();
			}
			if ( jp.getCurrentToken() == JsonToken.START_OBJECT ) {
				parseMessage(name, n, parent, jp);
			} else if ( jp.getCurrentToken() == JsonToken.START_ARRAY ) {
				parseArrayMessage(name, n, parent, jp);
			} else {
				String value = jp.getText();
				if ( parent == null ) {
					System.err.println("Could not find message, creating dummy");
					parent = NavajoFactory.getInstance().createMessage(n, (topLevelMessageName != null ? topLevelMessageName : "Request" ) );
					n.addMessage(parent);
				}
				parseProperty(name, value, parent, jp);
			}
		}
	}

	private Navajo parse(JsonParser jp) throws Exception {
		Navajo n = NavajoFactory.getInstance().createNavajo();
		while ( jp.nextToken() != null ) {
			parse(n, null, jp);
		}
		return n;
	}

	@Override
	public void setEntityTemplate(Navajo m) {
		this.entityTemplate = m;
	}
	

}
