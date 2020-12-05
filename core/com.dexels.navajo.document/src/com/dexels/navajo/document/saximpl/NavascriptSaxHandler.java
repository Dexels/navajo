package com.dexels.navajo.document.saximpl;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PushbackReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Hashtable;
import java.util.Map;
import java.util.Stack;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Navascript;
import com.dexels.navajo.document.base.BaseExpressionTagImpl;
import com.dexels.navajo.document.base.BaseFieldTagImpl;
import com.dexels.navajo.document.base.BaseNode;
import com.dexels.navajo.document.navascript.tags.ExpressionTag;
import com.dexels.navajo.document.navascript.tags.FieldTag;
import com.dexels.navajo.document.navascript.tags.MapTag;
import com.dexels.navajo.document.navascript.tags.MessageTag;
import com.dexels.navajo.document.navascript.tags.NavascriptTag;
import com.dexels.navajo.document.navascript.tags.ParamTag;
import com.dexels.navajo.document.navascript.tags.PropertyTag;
import com.dexels.navajo.document.saximpl.qdxml.DocHandler;
import com.dexels.navajo.document.saximpl.qdxml.QDParser;

public class NavascriptSaxHandler implements DocHandler {

	private NavascriptTag currentDocument=null;
	private Stack<MapTag> currentMap = new Stack<>();
	private Stack<MessageTag> currentMessage = new Stack<>();
	private Stack<FieldTag> currentField = new Stack<>();
	private Stack<BaseNode> currentNode = new Stack<>();


	public Navascript getNavascript() {
		return currentDocument;
	}


	@Override
	public final void startElement(String tag, Map<String,String> h) throws Exception {

		System.err.println(">>>>>>>>>>>>>>>>>>>>>> In startElement: " + tag);


		if (tag.equals("navascript")) {
			currentDocument =  new NavascriptTag();
			currentNode.push(currentDocument);
			return;
		}

		BaseNode currentParent = currentNode.lastElement();

		if (tag.equals("message")) {
			MessageTag mt = new MessageTag(currentDocument, h.get("name"),  h.get("type"));
			if ( h.get("mode") != null ) {
				mt.setMode(h.get("mode") + "_"); // postfix mode to prevent ignore message. Strip character later
			}
			if ( currentParent instanceof MapTag && currentMap.size() > 0) {
				currentMap.lastElement().addMessage(mt);
			} else if ( currentParent instanceof MessageTag && currentMessage.size() > 0 ) {
				currentMessage.lastElement().addMessage(mt);
			} else {
				currentDocument.addMessage(mt);
			} 
			currentMessage.push(mt);
			currentNode.push(mt);
			return;
		}
		if (tag.equals("map")) {
			String object = h.get("object");
			String ref = h.get("ref");
			MapTag mt = null;
			if ( object != null ) {
				mt = new MapTag(currentDocument, object, h.get("condition"), true);
			}else {
				// map ref on message
				mt = new MapTag(currentDocument, ref, h.get("filter"), currentMap.lastElement(), true);
			}
			System.err.println("in map tag: " + ref + ", currentParent: " +  ( currentParent != null ? currentParent.getClass() : ""));
			if ( currentParent instanceof MessageTag && currentMessage.size() > 0 ) {
				currentMessage.lastElement().addMap(mt);
			} else if ( currentParent instanceof MapTag && currentMap.size() > 0) {
				currentMap.lastElement().addMap(mt);
			} else if ( currentParent instanceof FieldTag && currentField.size() > 0){
				currentField.lastElement().addMap(mt);
			} else {
				currentDocument.addMap(mt);
			}
			currentMap.push(mt);
			currentNode.push(mt);
			System.err.println("In old skool map: " + object + ", currentMap is " + currentMap);
			return;
		}
		if ( tag.equals("field")) {
			String name = h.get("name");
			System.err.println("in field tag. currentMap is: " + currentMap);
			FieldTag ft = new FieldTag(currentMap.lastElement(), null, name, true);
			System.err.println("SETTING FT TO: " + ft.getClass());
			currentMap.lastElement().addField(ft);
			currentField.push(ft);
			currentNode.push(ft);
			System.err.println("SETTING CURRENTPARENT TO: " + currentParent.getClass());
		}
		if (tag.startsWith("map.")) { //map.navajo
			System.err.println("Found map: " + tag);
			String name = tag.split("\\.")[1];
			MapTag mt = new MapTag(currentDocument, name, h.get("condition"));
			if ( currentParent instanceof MessageTag && currentMessage.size() > 0 ) {
				currentMessage.lastElement().addMap(mt);
			} else if ( currentParent instanceof MapTag && currentMap != null ) {
				currentMap.lastElement().addMap(mt);
			} else {
				currentDocument.addMap(mt);
			}
			currentMap.push(mt);
			currentNode.push(mt);
			return;
		}
		//		if (tag.equals("include")) {
		//			//parseMessage(h);
		//			return;
		//		}
		if (tag.equals("expression")) {
			String condition = h.get("condition");
			String value = h.get("value");
			System.err.println("expression: [" + condition + "] : " + value);
			ExpressionTag et = new ExpressionTag(currentDocument, condition, value);
			if ( currentParent instanceof PropertyTag ) {
				((PropertyTag) currentParent).addExpression(et);
			} else if ( currentParent instanceof FieldTag) {
				((FieldTag) currentParent).addExpression(et);
			} else if ( currentParent instanceof ParamTag ) {
				((ParamTag) currentParent).addExpression(et);
			}
			currentNode.push(et);
			return;
		}
		if (tag.equals("param")) {
			ParamTag pt = new ParamTag(currentDocument, h.get("condition"), h.get("name"));
			String name = pt.getName();
			System.err.println("Found param: " + name + ", currentParent: " + currentParent);
			if ( currentParent instanceof MessageTag && currentMessage.size() > 0 ) {
				currentMessage.lastElement().addParam(pt);
			} else if ( currentParent instanceof MapTag && currentMap != null ) {
				currentMap.lastElement().addParam(pt);
			} else {
				currentDocument.addParam(pt);
			}
			currentNode.push(pt);
		}
		if (tag.equals("property")) {
			String name = h.get("name");
			System.err.println("Found property: " + name);
			String val = h.get("value");
			String type = h.get("type");
			PropertyTag pt = new PropertyTag(currentDocument, name, type, val, 0, "", "");
			if (val!=null) {
				// Dit kan NOG strakker. Niet alle types hoeven geunescaped worder
				Hashtable<String,String> h2 = new Hashtable<String,String>(h);
				val = BaseNode.XMLUnescape(val);
				h2.put("value", val);

			} 
			if ( currentParent instanceof MessageTag && currentMessage.size() > 0 ) {
				currentMessage.lastElement().addProperty(pt);
			} else if ( currentParent instanceof MapTag && currentMap != null ) {
				currentMap.lastElement().addProperty(pt);
			}
			currentNode.push(pt);
			return;
		}

		if (currentMap.size() > 0 && tag.startsWith(currentMap.lastElement().getAdapterName()+".")) {  // navajomap.callwebservice
			String fieldName = tag.split("\\.")[1];
			if ( currentParent instanceof MessageTag ) { // Mapped field
				// map ref on message
				MapTag mt = new MapTag(currentDocument, fieldName, h.get("filter"), currentMap.lastElement(), false);
				currentMessage.lastElement().addMap(mt);
				System.err.println("Now mapping field of a map onto a message: " + currentMessage);
				currentMap.push(mt);
				currentNode.push(mt);
			} else { // Normal field
				FieldTag ft = new FieldTag(currentMap.lastElement(), null, fieldName);
				if ( h.get("value") != null ) {
					ExpressionTag et = new ExpressionTag(currentDocument, h.get("condition"), h.get("value"));
					ft.addExpression(et);
				}
				currentMap.lastElement().addField(ft);
				currentField.push(ft);
				currentNode.push(ft);
			}
		}
		//		if (tag.equals("option")) {
		//			String val = h.get("value");
		//			String name = h.get("name");
		//			Hashtable<String,String> h2 = new Hashtable<String,String>(h);
		//			val = BaseNode.XMLUnescape(val);
		//			name = BaseNode.XMLUnescape(name);
		//			h2.put("value", val);
		//			h2.put("name", name);
		//
		//			//parseSelection(h2);
		//			return;
		//		} 

	}

	@Override
	public void endElement(String tag) throws Exception {


		if (currentMap.size() > 0 && tag.endsWith("." + currentMap.lastElement().getTagName())) {
			System.err.println("POPPING MAP (0)........." + tag);
			currentMap.pop();
			currentNode.pop();
		}
		if (tag.equals("message")) {
			System.err.println("POPPING MESSAGE: " + tag);
			currentMessage.pop();
			currentNode.pop();
		}
		if (tag.equals("property")) {
			System.err.println("POPPING PROPERTY: " + tag);
			currentNode.pop();
		}
		if (tag.equals("expression")) {
			System.err.println("POPPING PROPERTY: " + tag);
			currentNode.pop();
		}
		if (tag.equals("param")) {
			System.err.println("POPPING PARAM: " + tag);
			currentNode.pop();
		}
		if (tag.equals("map")) {
			System.err.println("POPPING MAP (1)........." + tag);
			currentMap.pop();
			currentNode.pop();
		}
		if (tag.equals("field")) {
			System.err.println("POPPING FIELD: " + tag);
			currentField.pop();
			currentNode.pop();
		}
		if ( currentField.size() > 0 && tag.endsWith("." + currentField.lastElement().getName())) {
			System.err.println("POPPING FIELD: " + tag);
			currentField.pop();
			currentNode.pop();
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
		StringWriter sw = new StringWriter();
		copyBufferedBase64Resource(sw, (PushbackReader) r);
		String text = sw.toString();
		System.err.println("**************************** In text....." + text);
		BaseNode n = currentNode.lastElement();
		System.err.println("Current node is: " + n);
		if ( n instanceof BaseExpressionTagImpl ) {
			((BaseExpressionTagImpl) n).setConstant(text);
		} else if ( n instanceof BaseFieldTagImpl ) {
			((BaseFieldTagImpl) n).setConstant(text);
		}
	}

	private void copyBufferedBase64Resource(StringWriter out, PushbackReader in) throws IOException {
		int read;
		char[] buffer = new char[QDParser.PUSHBACK_SIZE];
		while ((read = in.read(buffer, 0 ,buffer.length)) > -1) {
			int ii = getIndexOf(buffer, '<');
			if (ii==-1) {
				System.err.println("SHOULD NOT COME HERE!");
				out.write(buffer,0,read);
			} else {
				out.write(buffer, 0, ii);
				System.err.println("Unread: " + Character.toString(ii) + " from " + Character.toString(buffer[ii]) + " to " + Character.toString(buffer[read-ii]));
				in.unread(buffer, ii-1, read-ii);
				break;
			}

		}

		out.flush();
	}

	private int getIndexOf(char[] buffer, char c) {
		for (int i = 0; i < buffer.length; i++) {
			if (c==buffer[i]) {
				return i;
			}
		}
		return -1;
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

		FileInputStream fis = new FileInputStream(new File("/Users/arjenschoneveld/ProcessUpdateDocuments.xml"));
		Navascript ns = NavajoFactory.getInstance().createNavaScript(fis);
		fis.close();

		// Print parsed Navascript:
		ns.write(System.err);

	}

}
