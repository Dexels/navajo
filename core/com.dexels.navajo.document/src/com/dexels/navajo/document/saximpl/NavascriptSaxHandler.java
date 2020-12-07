package com.dexels.navajo.document.saximpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PushbackReader;
import java.io.Reader;
import java.io.StringWriter;
import java.util.Hashtable;
import java.util.Map;
import java.util.Stack;

import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Navascript;
import com.dexels.navajo.document.base.BaseCheckTagImpl;
import com.dexels.navajo.document.base.BaseExpressionTagImpl;
import com.dexels.navajo.document.base.BaseFieldTagImpl;
import com.dexels.navajo.document.base.BaseNode;
import com.dexels.navajo.document.navascript.tags.CheckTag;
import com.dexels.navajo.document.navascript.tags.ExpressionTag;
import com.dexels.navajo.document.navascript.tags.FieldTag;
import com.dexels.navajo.document.navascript.tags.IncludeTag;
import com.dexels.navajo.document.navascript.tags.MapTag;
import com.dexels.navajo.document.navascript.tags.MessageTag;
import com.dexels.navajo.document.navascript.tags.NavascriptTag;
import com.dexels.navajo.document.navascript.tags.ParamTag;
import com.dexels.navajo.document.navascript.tags.PropertyTag;
import com.dexels.navajo.document.navascript.tags.ValidationsTag;

public class NavascriptSaxHandler extends SaxHandler {

	private NavascriptTag currentDocument=null;
	private Stack<MapTag> currentMap = new Stack<>();
	private Stack<MessageTag> currentMessage = new Stack<>();
	private Stack<FieldTag> currentField = new Stack<>();
	private Stack<BaseNode> currentNode = new Stack<>();
	private ValidationsTag validationsBlock;
	
	public Navascript getNavascript() {
		return currentDocument;
	}


	@Override
	public final void startElement(String tag, Map<String,String> h) throws Exception {

		if (tag.equals("navascript")) {
			currentDocument =  new NavascriptTag();
			currentNode.push(currentDocument);
			return;
		}

		BaseNode currentParent = currentNode.lastElement();
		
		if (tag.equals("include")) {
			IncludeTag it = currentDocument.addInclude(h.get("script"));
			currentNode.push(it);
			return;
		}
		
		if (tag.equals("validations")) {
			validationsBlock = currentDocument.addValidations();
			currentNode.push(validationsBlock);
			return;
		}
		
		if (tag.equals("check")) {
			String code = h.get("code");
			String desc = h.get("description");
			if ( validationsBlock != null  ) {
				CheckTag ct = validationsBlock.addCheck(code, desc);
				currentNode.push(ct);
			} else {
				// log error
			}
			return;
		}
		
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
			return;
		}
		if ( tag.equals("field")) {
			String name = h.get("name");
			FieldTag ft = new FieldTag(currentMap.lastElement(), null, name, true);
			 // FIELD CAN ALSO BE UNDER MESSAGE!!!
			if ( currentParent instanceof MessageTag ) {
				currentMessage.lastElement().addField(ft);
			} else if ( currentParent instanceof MapTag ) {
				currentMap.lastElement().addField(ft);
			}
			currentField.push(ft);
			currentNode.push(ft);
		}
		if (tag.startsWith("map.")) { //map.navajo
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
		
		if (tag.equals("expression")) {
			String condition = h.get("condition");
			String value = h.get("value");
			ExpressionTag et = new ExpressionTag(currentDocument, condition, value);
			if ( currentParent instanceof PropertyTag ) {
				((PropertyTag) currentParent).addExpression(et);
			} else if ( currentParent instanceof FieldTag) {
				((FieldTag) currentParent).addExpression(et);
			} else if ( currentParent instanceof ParamTag ) {
				((ParamTag) currentParent).addExpression(et);
			} else if ( currentParent instanceof MapTag )  { // Oops. this cannot happen. Should have been a FieldTag. Fix this.
				MapTag fixThis = currentMap.pop();
				
				currentNode.pop();
				
				FieldTag ft = new FieldTag(currentMap.lastElement(), null, fixThis.getRefAttribute());
				currentNode.push(ft);
				currentField.push(ft);
				currentMessage.lastElement().removeMap(fixThis);
				currentMessage.lastElement().addField(ft);
				ft.addExpression(et);
			}
			currentNode.push(et);
			return;
		}
		if (tag.equals("param")) {
			ParamTag pt = new ParamTag(currentDocument, h.get("condition"), h.get("name"));
			String name = pt.getName();
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
			if ( currentParent instanceof MessageTag && h.get("value") == null ) { // Mapped field if it is a getter (no value field specified and no expression under the tag)
				// map ref on message
				MapTag mt = new MapTag(currentDocument, fieldName, h.get("filter"), currentMap.lastElement(), false);
				currentMessage.lastElement().addMap(mt);
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
			currentMap.pop();
			currentNode.pop();
		}
		if (tag.equals("message")) {
			currentMessage.pop();
			currentNode.pop();
		}
		if (tag.equals("property")) {
			currentNode.pop();
		}
		if (tag.equals("expression")) {
			currentNode.pop();
		}
		if (tag.equals("param")) {
			currentNode.pop();
		}
		if (tag.equals("map")) {
			currentMap.pop();
			currentNode.pop();
		}
		if (tag.equals("field")) {
			currentField.pop();
			currentNode.pop();
		}
		if ( currentField.size() > 0 && tag.endsWith("." + currentField.lastElement().getName())) {
			currentField.pop();
			currentNode.pop();
		}
		if (tag.equals("include")) {
			currentNode.pop();
		}
		if (tag.equals("check")) {
			currentNode.pop();
		}
		if (tag.equals("validations")) {
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
		copyTextBuffer(sw, (PushbackReader) r);
		String text = sw.toString();
		BaseNode n = currentNode.lastElement();
		if ( n instanceof BaseCheckTagImpl ) {
			((BaseCheckTagImpl) n).setRule(text);
		} else if ( n instanceof BaseExpressionTagImpl ) {
			((BaseExpressionTagImpl) n).setConstant(text);
		} else if ( n instanceof BaseFieldTagImpl ) {
			((BaseFieldTagImpl) n).setConstant(text);
		}
	}

	private void copyTextBuffer(StringWriter out, PushbackReader in) throws IOException {
		int read;
		int prevRead = -1;
		while ((read = in.read() ) > -1) {
			if ( (char) read != '<') {
				out.write(read);
			} else {
				in.unread(read);
				if ( prevRead != -1 ) {
					in.unread(prevRead);
				}
				break;
			}
			prevRead = read;
		}
		out.flush();
	}

	public static void main(String [] args) throws Exception {

		FileInputStream fis = new FileInputStream(new File("/Users/arjenschoneveld/noot.xml"));
		Navascript ns = NavajoFactory.getInstance().createNavaScript(fis);
		fis.close();

		// Print parsed Navascript:
		//FileWriter fw = new FileWriter(new File("/Users/arjenschoneveld/output.xml")); 
		ns.write(System.err);
		//fw.close();

	}

}
