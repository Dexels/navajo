package com.dexels.navajo.adapter;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.adapter.xmlmap.TagMap;
import com.dexels.navajo.document.nanoimpl.CaseSensitiveXMLElement;
import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.Mappable;
import com.dexels.navajo.script.api.MappableException;
import com.dexels.navajo.script.api.UserException;

public class XMLMap extends TagMap implements Mappable {

    private static final Logger logger = LoggerFactory.getLogger(XMLMap.class);

	public String start = null;
	public Binary content = null;
	public String stringContent = null;
	public boolean debug = false;

	@Override
	public void load(Access access) throws MappableException, UserException {}

	@Override
	public void store() throws MappableException, UserException {}

	@Override
	public void kill() {}

	public void setDebug(boolean b) {
		debug = b;
	}

	public void setStart(String name) {
		this.name = name;
	}

	public String getString() {
		if ( this.name != null ) {
			return getString( 0, this.indent );
		} else {
			return null;
		}
	}

	@Override
	public void setChild(TagMap t) throws UserException {
		if ( this.name != null ) {
			super.setChild(t);
		} else {
			throw new UserException(-1, "No start tag defined.");
		}
	}

	public Binary getContent() {
		String r = getString();
        byte[] bytes = r.getBytes(StandardCharsets.UTF_8);
        Binary b = new Binary(bytes);
        if ( debug ) {
            logger.debug(new String(bytes));
        }

		return b;
	}

	private void parseXML(XMLElement e) throws UserException {

		String startName = e.getName();
		this.setName(startName);

		// parse attributes
		Iterator<String> attrib_enum = e.enumerateAttributeNames();
		while ( attrib_enum.hasNext()) {
			String key = attrib_enum.next();
			String value = e.getStringAttribute(key);
			if ( this.attributes == null ) {
				this.attributes = new HashMap<String,String> ();
			}
			this.attributes.put( key, value );
		}

		// parse children.
		Vector<XMLElement> v = e.getChildren();
		for (int i = 0; i < v.size(); i++) {
			XMLElement child = v.get(i);

			TagMap childTag = TagMap.parseXMLElement(child, this.compact );
            childTag.setCompact( this.compact );

			this.setChild(childTag);
		}

		// Check for text node.
		if ( e.getContent() != null && !e.getContent().equals("")) {
			this.setText(e.getContent());
		}
	}

	private void parseString(String s) throws UserException {

		XMLElement xe = new CaseSensitiveXMLElement(true);
		try {
			xe.parseFromReader(new StringReader(s));
		}
		catch (IOException ex) {
			throw new UserException("Error parsing:", ex);
		}
		this.name = xe.getName();

		parseXML(xe);
	}

	public void setContent(Binary b) throws UserException {
		String str = new String(b.getData());
		setStringContent(str);
	}

	public void setStringContent(String s) throws UserException {
		parseString(s);
	}

	public String getStart() {
		return start;
	}

	public String getStringContent() {
		return stringContent;
	}

	public static void main(String [] args) throws Exception {
		XMLMap xml = new XMLMap();
		xml.setStart("xml");
		TagMap district = new TagMap();
		district.setName("district");
		xml.setChild(district);
		district.setName("DISTRIKTJE");
		district.setName("WERKT DIT NOG STEEDS");
		logger.info("child = {}", xml.getChildTag("district", 0));

		TagMap n = new TagMap();
		n.setName("apenoot");
		n.setText("Achterlijke");
		district.setChild(n);

		Binary b = xml.getContent();

		xml.setChildName("district");
		TagMap kind = xml.getChild();
		logger.info("kind = {}", kind.getName());
		StringWriter sw = new StringWriter();
		b.writeBase64(sw);
		logger.info("Result: {}",sw);
	}
}
