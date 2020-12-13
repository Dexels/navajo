/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.document.saximpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PushbackReader;
import java.io.Reader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Stack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Navascript;
import com.dexels.navajo.document.base.BaseCheckTagImpl;
import com.dexels.navajo.document.base.BaseExpressionTagImpl;
import com.dexels.navajo.document.base.BaseFieldTagImpl;
import com.dexels.navajo.document.base.BaseNode;
import com.dexels.navajo.document.navascript.tags.Attributes;
import com.dexels.navajo.document.navascript.tags.BreakTag;
import com.dexels.navajo.document.navascript.tags.CheckTag;
import com.dexels.navajo.document.navascript.tags.ExpressionTag;
import com.dexels.navajo.document.navascript.tags.FieldTag;
import com.dexels.navajo.document.navascript.tags.IncludeTag;
import com.dexels.navajo.document.navascript.tags.MapTag;
import com.dexels.navajo.document.navascript.tags.MessageTag;
import com.dexels.navajo.document.navascript.tags.NavascriptTag;
import com.dexels.navajo.document.navascript.tags.ParamTag;
import com.dexels.navajo.document.navascript.tags.PropertyTag;
import com.dexels.navajo.document.navascript.tags.SelectionTag;
import com.dexels.navajo.document.navascript.tags.Tags;
import com.dexels.navajo.document.navascript.tags.ValidationsTag;
import com.dexels.navajo.document.saximpl.qdxml.QDParser;

public class NavascriptSaxHandler extends SaxHandler {

	public NavascriptSaxHandler(QDParser parser) {
		super(parser);
	}

	private NavascriptTag currentDocument=null;
	private Stack<MapTag> currentMap = new Stack<>();
	private Stack<MessageTag> currentMessage = new Stack<>();
	private Stack<FieldTag> currentField = new Stack<>();
	private Stack<BaseNode> currentNode = new Stack<>();
	private ValidationsTag validationsBlock;
	
	private static final Logger logger = LoggerFactory.getLogger(NavascriptSaxHandler.class);
	
	public Navascript getNavascript() {
		return currentDocument;
	}


	@Override
	public final void startElement(String tag, Map<String,String> h) throws Exception {

		if (tag.equals(Tags.NAVASCRIPT)) {
			currentDocument =  new NavascriptTag();
			currentNode.push(currentDocument);
			return;
		}

		BaseNode currentParent = currentNode.lastElement();
		
		if (tag.equals(Tags.INCLUDE)) {
			IncludeTag it = new IncludeTag(currentDocument, (h.get("script")));
			if ( currentParent instanceof MapTag && currentMap.size() > 0) {
				currentMap.lastElement().addInclude(it);
			} else if ( currentParent instanceof MessageTag && currentMessage.size() > 0 ) {
				currentMessage.lastElement().addInclude(it);
			} else {
				currentDocument.addInclude(it);
			} 
			currentNode.push(it);
			return;
		}
		
		if (tag.equals(Tags.VALIDATIONS)) {
			if ( !(currentParent instanceof NavascriptTag) ) {
				throw new Exception("Validation tags can only be specified as top level tags");
			}
			validationsBlock = currentDocument.addValidations();
			currentNode.push(validationsBlock);
			return;
		}
		
		if (tag.equals(Tags.OPTION)) {
			String name = h.get(Attributes.NAME);
			String value = h.get(Attributes.VALUE);
			String selected = h.get(Attributes.SELECTED);
			boolean bSel = (selected != null ? selected.equals("1") : false);
			if ( currentParent instanceof PropertyTag ) {
				SelectionTag st = ((PropertyTag) currentParent).addSelection(name, value, bSel);
				currentNode.push(st);
			} else {
				throw new Exception("Option tags are only allowed after a property tag.");
			}
			return;
		}
		
		if (tag.equals(Tags.CHECK)) {
			String code = h.get("code");
			String desc = h.get(Attributes.DESCRIPTION);
			String condition = h.get(Attributes.CONDITION);
			if ( validationsBlock != null  ) {
				CheckTag ct = validationsBlock.addCheck(code, desc, condition);
				currentNode.push(ct);
			} else {
				throw new Exception("Check tags can only be found under validations tag.");
			}
			return;
		}
		
		if (tag.equals(Tags.BREAK)) {
			BreakTag bt = new BreakTag(currentDocument, h.get(Attributes.CONDITION), h.get("conditionId"), h.get("conditionDescription"));
			if ( currentParent instanceof MapTag && currentMap.size() > 0) {
				currentMap.lastElement().addBreak(bt);
			} else if ( currentParent instanceof MessageTag && currentMessage.size() > 0 ) {
				currentMessage.lastElement().addBreak(bt);
			} else {
				currentDocument.addBreak(bt);
			} 
			currentNode.push(bt);
			return;
		}
		
		if (tag.equals(Tags.MESSAGE) || tag.equals(Tags.ANTIMESSAGE)) {
			MessageTag mt = new MessageTag(currentDocument, h.get(Attributes.NAME),  h.get(Attributes.TYPE));
			mt.setCondition(h.get(Attributes.CONDITION));
			mt.setOrderBy(h.get(Attributes.ORDERBY));
			boolean isAntiMsg = tag.equals(Tags.ANTIMESSAGE);
			mt.setAntiMessage(isAntiMsg);
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
		if (tag.equals(Tags.MAP)) {
			String object = h.get(Attributes.OBJECT);
			String ref = h.get(Attributes.REF);
			String condition = h.get(Attributes.CONDITION);
			MapTag mt = null;
			if ( object != null ) {
				mt = new MapTag(currentDocument, object, h.get(Attributes.CONDITION), true);
			}else {
				// map ref on message
				mt = new MapTag(currentDocument, ref, h.get(Attributes.FILTER), currentMap.lastElement(), true);
			}
			mt.setCondition(condition);
			if ( currentParent instanceof PropertyTag ) { // selection property.
				((PropertyTag) currentParent).addMap(mt);
			} else if ( currentParent instanceof MessageTag && currentMessage.size() > 0 ) {
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
		if ( tag.equals(Tags.FIELD)) {
			String name = h.get(Attributes.NAME);
			FieldTag ft = new FieldTag(currentMap.lastElement(), null, name, true);
			 // FIELD CAN ALSO BE UNDER MESSAGE!!!
			if ( currentParent instanceof MessageTag ) {
				currentMessage.lastElement().addField(ft);
			} else if ( currentParent instanceof MapTag ) {
				currentMap.lastElement().addField(ft);
			} else {
				throw new Exception("Field tags can only be found under message or map tags.");
			}
			currentField.push(ft);
			currentNode.push(ft);
		}
		if (tag.startsWith(Tags.MAP + ".")) { //map.navajo
			String name = tag.split("\\.")[1];
			MapTag mt = new MapTag(currentDocument, name, h.get(Attributes.CONDITION));
			Map<String,String> attributeMap = new HashMap<>();
			for ( String key : h.keySet() ) {
				attributeMap.put(key, h.get(key));
			}
			mt.addAttributes(attributeMap);
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
		
		if (tag.equals(Tags.EXPRESSION)) {
			String condition = h.get(Attributes.CONDITION);
			String value = h.get(Attributes.VALUE);
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
			} else {
				throw new Exception("Expression tags can only be found under following tags: property, field, param");
			}
			currentNode.push(et);
			return;
		}
		if (tag.equals(Tags.PARAM)) {
			ParamTag pt = new ParamTag(currentDocument, h.get(Attributes.CONDITION), h.get(Attributes.NAME));
			if ( currentParent instanceof MessageTag && currentMessage.size() > 0 ) {
				currentMessage.lastElement().addParam(pt);
			} else if ( currentParent instanceof MapTag && currentMap != null ) {
				currentMap.lastElement().addParam(pt);
			} else {
				currentDocument.addParam(pt);
			}
			currentNode.push(pt);
		}
		if (tag.equals(Tags.PROPERTY)) {
			String name = h.get(Attributes.NAME);
			String val = h.get(Attributes.VALUE);
			String type = h.get(Attributes.TYPE);
			String direction = h.get(Attributes.DIRECTION);
			String description = h.get(Attributes.DESCRIPTION);
			String length = h.get(Attributes.LENGTH);
			String cardinality = h.get(Attributes.CARDINALITY);
			int iLen = ( length != null ? Integer.parseInt(length) : 0 );
			PropertyTag pt = new PropertyTag(currentDocument, name, type, val, iLen, description, direction);
			if ( cardinality != null ) {
				pt.setCardinality(cardinality);
			}
			if (val!=null) {
				// Dit kan NOG strakker. Niet alle types hoeven geunescaped worder
				Hashtable<String,String> h2 = new Hashtable<String,String>(h);
				val = BaseNode.XMLUnescape(val);
				h2.put(Attributes.VALUE, val);

			} 
			if ( currentParent instanceof MessageTag && currentMessage.size() > 0 ) {
				currentMessage.lastElement().addProperty(pt);
			} else if ( currentParent instanceof MapTag && currentMap != null ) {
				currentMap.lastElement().addProperty(pt);
			} else {
				throw new Exception("Property tags can only be found under a message or a map tag.");
			}
			currentNode.push(pt);
			return;
		}

		if (currentMap.size() > 0 && tag.startsWith(currentMap.lastElement().getAdapterName()+".")) {  // navajomap.callwebservice
			String fieldName = tag.split("\\.")[1];
			if ( currentParent instanceof MessageTag && h.get(Attributes.VALUE) == null ) { // Mapped field if it is a getter (no value field specified and no expression under the tag)
				// map ref on message
				MapTag mt = new MapTag(currentDocument, fieldName, h.get(Attributes.FILTER), currentMap.lastElement(), false);
				Map<String,String> attributeMap = new HashMap<>();
				for ( String key : h.keySet() ) {
					attributeMap.put(key, h.get(key));
				}
				mt.addAttributes(attributeMap);
				currentMessage.lastElement().addMap(mt);
				currentMap.push(mt);
				currentNode.push(mt);
			} else if ( currentParent instanceof PropertyTag ) { // selection property
				MapTag mt = new MapTag(currentDocument, fieldName, h.get(Attributes.FILTER), currentMap.lastElement(), false);
				Map<String,String> attributeMap = new HashMap<>();
				for ( String key : h.keySet() ) {
					attributeMap.put(key, h.get(key));
				}
				mt.addAttributes(attributeMap);
				((PropertyTag) currentParent).addMap(mt);
				currentMap.push(mt);
				currentNode.push(mt);
			} else { // Normal field
				FieldTag ft = new FieldTag(currentMap.lastElement(), null, fieldName);
				Map<String,String> attributeMap = new HashMap<>();
				for ( String key : h.keySet() ) {
					attributeMap.put(key, h.get(key));
				}
				ft.setAddAttributes(attributeMap);
				// TODO: Get all attributes of this tag and set them in field (navascript field can have any attribute value!
				if ( h.get(Attributes.VALUE) != null ) {
					ExpressionTag et = new ExpressionTag(currentDocument, h.get(Attributes.CONDITION), h.get(Attributes.VALUE));
					ft.addExpression(et);
				}
				currentMap.lastElement().addField(ft);
				currentField.push(ft);
				currentNode.push(ft);
			}
		}

	}

	@Override
	public void endElement(String tag) throws Exception {


		
		if (currentMap.size() > 0 && tag.endsWith("." + currentMap.lastElement().getTagName())) {
			currentMap.pop();
			currentNode.pop();
		} else if (tag.equals(Tags.MESSAGE) || tag.equals(Tags.ANTIMESSAGE)) {
			currentMessage.pop();
			currentNode.pop();
		} else if (tag.equals(Tags.PROPERTY)) {
			currentNode.pop();
		} else if (tag.equals(Tags.EXPRESSION)) {
			currentNode.pop();
		} else if (tag.equals(Tags.PARAM)) {
			currentNode.pop();
		} else if (tag.equals(Tags.MAP)) {
			currentMap.pop();
			currentNode.pop();
		} else if (tag.equals(Tags.FIELD)) {
			currentField.pop();
			currentNode.pop();
		} else if ( currentField.size() > 0 && tag.endsWith("." + currentField.lastElement().getName())) {
			currentField.pop();
			currentNode.pop();
		} else if (tag.equals(Tags.INCLUDE)) {
			currentNode.pop();
		} else if (tag.equals(Tags.CHECK)) {
			currentNode.pop();
		} else if (tag.equals(Tags.VALIDATIONS)) {
			currentNode.pop();
		} else if (tag.equals(Tags.OPTION)) {
			currentNode.pop();
		} else if (tag.equals(Tags.BREAK)) {
			currentNode.pop();
		}
		
	}

	@Override
	public void startDocument() throws Exception {
		logger.info("Start parsing of Navascript filed");
	}

	@Override
	public void endDocument() throws Exception {
		logger.info("End parsing of Navascript filed");
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

		FileInputStream fis = new FileInputStream(new File("/Users/arjenschoneveld/ProcessCountMatchEvents.xml"));
		Navascript ns = NavajoFactory.getInstance().createNavaScript(fis);
		fis.close();

		// Print parsed Navascript:
		//FileWriter fw = new FileWriter(new File("/Users/arjenschoneveld/output.xml")); 
		ns.write(System.err);
		//fw.close();

	}

}
