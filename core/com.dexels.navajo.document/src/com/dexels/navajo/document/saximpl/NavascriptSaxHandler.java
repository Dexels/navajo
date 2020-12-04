package com.dexels.navajo.document.saximpl;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Reader;
import java.util.Hashtable;
import java.util.Map;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Navascript;
import com.dexels.navajo.document.base.BaseNode;
import com.dexels.navajo.document.navascript.tags.FieldTag;
import com.dexels.navajo.document.navascript.tags.MapTag;
import com.dexels.navajo.document.navascript.tags.MessageTag;
import com.dexels.navajo.document.navascript.tags.NavascriptTag;
import com.dexels.navajo.document.navascript.tags.ParamTag;
import com.dexels.navajo.document.navascript.tags.PropertyTag;
import com.dexels.navajo.document.saximpl.qdxml.DocHandler;

public class NavascriptSaxHandler implements DocHandler {

	private NavascriptTag currentDocument=null;
	private MapTag currentMap;
	private MessageTag currentMessage;
	private FieldTag currentField;
	private PropertyTag currentProperty;
	private ParamTag currentParam;
	private BaseNode currentNode;
	
	private boolean previousTagIsMessage = false;

	public Navascript getNavascript() {
		return currentDocument;
	}

	@Override
	public final void startElement(String tag, Map<String,String> h) throws Exception {
		//           logger.info("starting element: "+tag+" attrs: "+h);
		//           currentTag = tag;

		// Unescape all the shit.
		if (tag.equals("navascript")) {
			currentDocument =  new NavascriptTag();
			return;
		}
		if (tag.equals("message")) {
			MessageTag mt = new MessageTag(currentDocument, h.get("name"),  h.get("type"));
			System.err.println("currentMessage is: " + currentMessage.getName());
			previousTagIsMessage = true;
			if ( currentMessage == null ) {
				currentDocument.addMessage(mt);
			} else {
				currentMessage.addMessage(mt);
			}
			currentMessage = mt;
			currentNode = mt;
			return;
		}

		if (tag.startsWith("map.")) { //map.navajo
			System.err.println("Found map: " + tag);
			String name = tag.split("\\.")[1];
			MapTag mt = new MapTag(currentDocument, name, h.get("condition"));
			currentMap = mt;
			currentNode = mt;
			return;
		}
		if (tag.equals("include")) {
			//parseMessage(h);
			return;
		}
		if (tag.equals("expression")) {
			String condition = h.get("condition");
			String value = h.get("value");
			System.err.println("expression: [" + condition + "] : " + value);
			return;
		}
		if (tag.equals("param")) {
			String name = h.get("name");
			currentParam = name;
			System.err.println("Found param: " + name);
			
		}
		if (tag.equals("property")) {
			String name = h.get("name");
			System.err.println("Found property: " + name);
			String val = h.get("value");
			if (val!=null) {

				// Dit kan NOG strakker. Niet alle types hoeven geunescaped worder
				Hashtable<String,String> h2 = new Hashtable<String,String>(h);
				val = BaseNode.XMLUnescape(val);
				h2.put("value", val);
				//parseProperty(h2);
			} else {
				//parseProperty(h);

			}
			return;
		}
		if (tag.startsWith(currentMap+".")) {  // navajomap.callwebservice
			String fieldName = tag.split("\\.")[1];
			currentField = fieldName;
			String value = h.get("value");
			System.err.println("In field of map: " + fieldName + " [" + previousTagIsMessage + "]");
			if ( previousTagIsMessage ) { // Mapped field
				// map ref on message
				System.err.println("Now mapping field of a map onto a message: " + currentMessage);
				previousTagIsMessage = false;
			} else { // Normal field
				if ( value != null ) {
					System.err.println("Found value for field: " + value);
				}
			}
		}
		if (tag.equals("option")) {
			String val = h.get("value");
			String name = h.get("name");
			Hashtable<String,String> h2 = new Hashtable<String,String>(h);
			val = BaseNode.XMLUnescape(val);
			name = BaseNode.XMLUnescape(name);
			h2.put("value", val);
			h2.put("name", name);

			//parseSelection(h2);
			return;
		} 

	}

	@Override
	public void endElement(String tag) throws Exception {
		if (tag.equals(currentMap)) {
			System.err.println("Closing tag of: " + currentMap);
		}
	}

	@Override
	public void startDocument() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void endDocument() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void text(Reader r) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public final String quoteStarted(int quoteCharacter, Reader r, String attributeName, String tagName,StringBuilder attributeBuffer) throws IOException {
		int c = 0;
		attributeBuffer.delete(0, attributeBuffer.length());
		while ((c = r.read()) != -1) {
			if (c==quoteCharacter) {
				return attributeBuffer.toString();
			} else {
				attributeBuffer.append((char)c);
			}
		}        
		throw new EOFException("Non terminated quote!");
	}

	public static void main(String [] args) throws Exception {

		FileInputStream fis = new FileInputStream(new File("/Users/arjenschoneveld/navascript.xml"));
		Navascript ns = NavajoFactory.getInstance().createNavaScript(fis);
		fis.close();

	}

}
