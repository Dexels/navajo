/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
 */
package com.dexels.navajo.document.saximpl;

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

import com.dexels.navajo.document.Navascript;
import com.dexels.navajo.document.base.BaseCheckTagImpl;
import com.dexels.navajo.document.base.BaseExpressionTagImpl;
import com.dexels.navajo.document.base.BaseFieldTagImpl;
import com.dexels.navajo.document.base.BaseNode;
import com.dexels.navajo.document.navascript.tags.Attributes;
import com.dexels.navajo.document.navascript.tags.BlockTag;
import com.dexels.navajo.document.navascript.tags.BreakTag;
import com.dexels.navajo.document.navascript.tags.CheckTag;
import com.dexels.navajo.document.navascript.tags.CommentBlock;
import com.dexels.navajo.document.navascript.tags.DebugTag;
import com.dexels.navajo.document.navascript.tags.DefineTag;
import com.dexels.navajo.document.navascript.tags.DefinesTag;
import com.dexels.navajo.document.navascript.tags.ExpressionTag;
import com.dexels.navajo.document.navascript.tags.FieldTag;
import com.dexels.navajo.document.navascript.tags.FinallyTag;
import com.dexels.navajo.document.navascript.tags.IncludeTag;
import com.dexels.navajo.document.navascript.tags.LogTag;
import com.dexels.navajo.document.navascript.tags.MapDefinitionInterrogator;
import com.dexels.navajo.document.navascript.tags.MapTag;
import com.dexels.navajo.document.navascript.tags.MessageTag;
import com.dexels.navajo.document.navascript.tags.MethodTag;
import com.dexels.navajo.document.navascript.tags.MethodsTag;
import com.dexels.navajo.document.navascript.tags.NS3Compatible;
import com.dexels.navajo.document.navascript.tags.NavascriptTag;
import com.dexels.navajo.document.navascript.tags.ParamTag;
import com.dexels.navajo.document.navascript.tags.PropertyTag;
import com.dexels.navajo.document.navascript.tags.SelectionTag;
import com.dexels.navajo.document.navascript.tags.SynchronizedTag;
import com.dexels.navajo.document.navascript.tags.Tags;
import com.dexels.navajo.document.navascript.tags.ValidationsTag;
import com.dexels.navajo.document.navascript.tags.ValueTag;
import com.dexels.navajo.document.saximpl.qdxml.QDParser;

public class NavascriptSaxHandler extends SaxHandler {

	public NavascriptSaxHandler(QDParser parser) {
		super(parser);
	}

	private NavascriptTag currentDocument=null;
	private DefinesTag currentDefines = null;
	private MethodsTag currentMethods = null;
	private Stack<MapTag> currentMap = new Stack<>();
	private Stack<MessageTag> currentMessage = new Stack<>();
	private Stack<FieldTag> currentField = new Stack<>();
	private Stack<BaseNode> currentNode = new Stack<>();
	private ValidationsTag validationsBlock;
	private MapDefinitionInterrogator mapChecker;
	
	private static final Logger logger = LoggerFactory.getLogger(NavascriptSaxHandler.class);

	public Navascript getNavascript() {
		return currentDocument;
	}


	public void setMapChecker(MapDefinitionInterrogator mapDefinitionInterrogator) {
		mapChecker = mapDefinitionInterrogator;
	}
	
	@Override
	public final void startElement(String tag, Map<String,String> h) throws Exception {

		if (tag.equals(Tags.NAVASCRIPT) || tag.equals("tsl")) {
			currentDocument =  new NavascriptTag();
			currentNode.push(currentDocument);
			return;
		}
		

		BaseNode currentParent = currentNode.lastElement();
		
		if (tag.equals(Tags.FINALLY)) {
			FinallyTag ft = new FinallyTag(currentDocument);
			currentDocument.addFinally(ft);

			currentNode.push(ft);
		}

		if (tag.equals(Tags.METHODS)) {
			MethodsTag mt = new MethodsTag(currentDocument);
			currentDocument.addMethods(mt);
			currentNode.push(mt);
			currentMethods = mt;
		}
		
		if (tag.equals(Tags.METHOD)) {
			MethodTag m = new MethodTag(currentDocument);
			m.setScriptName(h.get("name"));
			if ( currentMethods == null ) {
				logger.error("Missing methods tag for this method " + h.get("name"));
				return;
			}
			currentNode.push(m);
			currentMethods.addMethod(m);
		}
		
		if (tag.equals(Tags.DEFINES)) {
			DefinesTag dt = new DefinesTag(currentDocument);
			currentDocument.addDefines(dt);
			currentNode.push(dt);
			currentDefines = dt;
		}

		if (tag.equals(Tags.DEFINE)) {
			DefineTag dt = new DefineTag(currentDocument);
			String name = h.get("name");
			dt.setName(name);
			if ( currentDefines != null ) {
				currentDefines.addDefine(dt);
			}
			currentNode.push(dt);
		}

		if ( tag.equals(Tags.DEBUG)) {
			DebugTag dt = new DebugTag(currentDocument, h.get("value"));
			dt.setCondition(h.get("condition"));
			if ( currentParent instanceof MapTag && currentMap.size() > 0) {
				currentMap.lastElement().addDebug(dt);
			} else if ( currentParent instanceof MessageTag && currentMessage.size() > 0 ) {
				currentMessage.lastElement().addDebug(dt);
			} else if ( currentParent instanceof BlockTag ) {
				((BlockTag) currentParent).add(dt);
			} else if ( currentParent instanceof FinallyTag ) {
				((FinallyTag) currentParent).add(dt);
			} else if ( currentParent instanceof SynchronizedTag ) {
				((SynchronizedTag) currentParent).add(dt);
			} else if ( currentParent instanceof BlockTag ) {
				((BlockTag) currentParent).addDebug(dt);
			} else if ( currentParent instanceof NavascriptTag ) {
				currentDocument.addDebug(dt);
			} else {
				throw new Exception("Did not expect debug tag under this parent: " + currentParent.getTagName());
			}
			currentNode.push(dt);
			return;
		}
		
		if ( tag.equals(Tags.LOG)) {
			LogTag dt = new LogTag(currentDocument, h.get("value"));
			dt.setCondition(h.get("condition"));
			if ( currentParent instanceof MapTag && currentMap.size() > 0) {
				currentMap.lastElement().addDebug(dt);
			} else if ( currentParent instanceof MessageTag && currentMessage.size() > 0 ) {
				currentMessage.lastElement().addDebug(dt);
			} else if ( currentParent instanceof BlockTag ) {
				((BlockTag) currentParent).add(dt);
			} else if ( currentParent instanceof FinallyTag ) {
				((FinallyTag) currentParent).add(dt);
			} else if ( currentParent instanceof SynchronizedTag ) {
				((SynchronizedTag) currentParent).add(dt);
			} else if ( currentParent instanceof BlockTag ) {
				((BlockTag) currentParent).addDebug(dt);
			} else if ( currentParent instanceof NavascriptTag ) {
				currentDocument.addDebug(dt);
			} else {
				throw new Exception("Did not expect log tag under this parent: " + currentParent.getTagName());
			}
			currentNode.push(dt);
			return;
		}
		
		if (tag.equals(Tags.INCLUDE)) {
			IncludeTag it = new IncludeTag(currentDocument, (h.get("script")));
			it.setCondition(h.get("condition"));
			if ( currentParent instanceof MapTag && currentMap.size() > 0) {
				currentMap.lastElement().addInclude(it);
			} else if ( currentParent instanceof MessageTag && currentMessage.size() > 0 ) {
				currentMessage.lastElement().addInclude(it);
			} else if ( currentParent instanceof BlockTag ) {
				((BlockTag) currentParent).add(it);
			} else if ( currentParent instanceof FinallyTag ) {
				((FinallyTag) currentParent).add(it);
			} else if ( currentParent instanceof SynchronizedTag ) {
				((SynchronizedTag) currentParent).add(it);
			} else if ( currentParent instanceof NavascriptTag ) {
				currentDocument.addInclude(it);
			} else {
				
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
			} else if ( currentParent instanceof BlockTag ) {
				((BlockTag) currentParent).add(bt);
			} else if ( currentParent instanceof FinallyTag ) {
				((FinallyTag) currentParent).add(bt);
			} else if ( currentParent instanceof SynchronizedTag ) {
				((SynchronizedTag) currentParent).add(bt);
			} else if ( currentParent instanceof NavascriptTag ){
				currentDocument.addBreak(bt);
			} else {
				throw new Exception("Did not expect break tag under this parent: " + currentParent.getTagName());
			}
			currentNode.push(bt);
			return;
		}

		if (tag.equals(Tags.SYNCHRONIZED)) {
			SynchronizedTag st = new SynchronizedTag(currentDocument);
			st.setContext(h.get(Attributes.CONTEXT));
			st.setKey(h.get(Attributes.KEY));
			st.setTimeout(h.get(Attributes.TIMEOUT));
			st.setBreakOnNoLock(h.get(Attributes.BREAKON_NOLOCK));
			if ( currentParent instanceof MapTag && currentMap.size() > 0) {
				currentMap.lastElement().addSynchronized(st);
			} else if ( currentParent instanceof MessageTag && currentMessage.size() > 0 ) {
				currentMessage.lastElement().addSynchronized(st);
			} else if ( currentParent instanceof BlockTag) {
				((BlockTag) currentParent).addSynchronized(st);
			} else if ( currentParent instanceof FinallyTag ) {
				((FinallyTag) currentParent).add(st);
			} else if ( currentParent instanceof NavascriptTag ) {
				((NavascriptTag) currentParent).addSynchronized(st);
			} else {
				throw new Exception("Did not excpect synchronized tag under this parent: " + currentParent.getTagName());
			}
			currentNode.push(st);
		}
		
		if (tag.equals(Tags.BLOCK)) {
			BlockTag bt = new BlockTag(currentDocument);
			bt.setCondition(h.get(Attributes.CONDITION));
			if ( currentParent instanceof MapTag && currentMap.size() > 0) {
				currentMap.lastElement().addBlock(bt);
			} else if ( currentParent instanceof MessageTag && currentMessage.size() > 0 ) {
				currentMessage.lastElement().addBlock(bt);
			} else if ( currentParent instanceof BlockTag) {
				((BlockTag) currentParent).addBlock(bt);
			} else if ( currentParent instanceof FinallyTag ) {
				((FinallyTag) currentParent).add(bt);
			} else if ( currentParent instanceof NavascriptTag ) {
				((NavascriptTag) currentParent).addBlock(bt);
			} else if ( currentParent instanceof SynchronizedTag ) {
				((SynchronizedTag) currentParent).add(bt);
			} else {
				throw new Exception("Did not expect block tag under this parent: " + currentParent.getTagName());
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
			} else if ( currentParent instanceof BlockTag ) {
				((BlockTag) currentParent).add(mt);
			} else if ( currentParent instanceof FinallyTag ) {
				((FinallyTag) currentParent).add(mt); 
			} else if ( currentParent instanceof SynchronizedTag ) {
				((SynchronizedTag) currentParent).add(mt);
			} else if ( currentParent instanceof NavascriptTag ){
				currentDocument.addMessage(mt);
			} else {
				throw new Exception("Did not expect message tag under this parent: " + currentParent.getTagName());
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
				mt = new MapTag(currentDocument, ref, h.get(Attributes.FILTER), ( currentMap.size() > 0 ? currentMap.lastElement() :  null ), true);
				if ( ref.startsWith("[")) {
					mt.setMappedMessage(true);
				}
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
			} else if ( currentParent instanceof ParamTag ) {
				((ParamTag) currentParent).addMap(mt);
				((ParamTag) currentParent).setType("array");
			} else if ( currentParent instanceof BlockTag ) {
				((BlockTag) currentParent).add(mt);
			} else if ( currentParent instanceof FinallyTag ) {
				((FinallyTag) currentParent).add(mt); 
			} else if ( currentParent instanceof SynchronizedTag ) {
				((SynchronizedTag) currentParent).add(mt);
			} else if ( currentParent instanceof NavascriptTag ){
				currentDocument.addMap(mt);
			} else {
				throw new Exception("Did not expect map tag under this parent: " + currentParent.getTagName());
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
			} else if ( currentParent instanceof BlockTag ) {
				((BlockTag) currentParent).add(ft);
			} else {
				throw new Exception("Field tags can only be found under message or map tags.");
			}
			currentField.push(ft);
			currentNode.push(ft);
		}
		if (tag.startsWith(Tags.MAP + ".")) { //map.navajo
			String name = tag.split("\\.")[1].trim();
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
			} else if ( currentParent instanceof BlockTag ) {
				((BlockTag) currentParent).add(mt);
			} else if ( currentParent instanceof FinallyTag ) {
				((FinallyTag) currentParent).add(mt); 
			} else if ( currentParent instanceof SynchronizedTag ) {
				((SynchronizedTag) currentParent).add(mt); 
			} else if (currentParent instanceof NavascriptTag ) {
				currentDocument.addMap(mt);
			} else {
				StringBuffer sb = new StringBuffer();
				sb.append("Did not expect map[" + name + "] tag under this parent: " + currentParent.getTagName() + 
						", currentMessage: " + currentMessage.size());
				if ( currentParent instanceof MessageTag ) {
					sb.append("\nParent is a message with name: " + ((MessageTag) currentParent).getName());
				}
				throw new Exception(sb.toString());
			}
			currentMap.push(mt);
			currentNode.push(mt);
			return;
		}

		if ( tag.equals(Tags.VALUE) ) {
			String condition = h.get(Attributes.CONDITION);
			ValueTag vt = new ValueTag(currentDocument);
			vt.setCondition(condition);
			if ( currentParent instanceof FieldTag ) { // This FieldTag will be a normal "setter" field.
				ExpressionTag et = new ExpressionTag(currentDocument);
				et.addValueTag(vt);
				((FieldTag) currentParent).setOldSkool(true);
				((FieldTag) currentParent).setSetter(true);
				((FieldTag) currentParent).addExpression(et);
			} else {
				((ExpressionTag) currentParent).addValueTag(vt);
			}
			currentNode.push(vt);
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
			String mode = h.get(Attributes.MODE);
			String value = h.get(Attributes.VALUE);
			pt.setType(h.get(Attributes.TYPE));
			if ( value != null && !"".equals(value)) { // String Constant as value.
				ExpressionTag et = new ExpressionTag(currentDocument);
				et.setConstant(value);
				pt.addExpression(et);
			}
			pt.setMode(mode);
			if ( currentParent instanceof MessageTag && currentMessage.size() > 0 ) {
				currentMessage.lastElement().addParam(pt);
			} else if ( currentParent instanceof MapTag && currentMap != null ) {
				currentMap.lastElement().addParam(pt);
			} else if ( currentParent instanceof BlockTag ) {
				((BlockTag) currentParent).add(pt);
			} else if ( currentParent instanceof FinallyTag ) {
				((FinallyTag) currentParent).add(pt); 
			} else if ( currentParent instanceof SynchronizedTag ) {
				((SynchronizedTag) currentParent).add(pt);
			} else if ( currentParent instanceof NavascriptTag ){
				currentDocument.addParam(pt);
			} else if ( currentParent instanceof ParamTag ){
				((ParamTag) currentParent).addParam(pt);
			}
			else {
				throw new Exception("Did not expect param tag under this parent: " + currentParent.getTagName());
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
			String condition = h.get(Attributes.CONDITION);
			int iLen = ( length != null ? Integer.parseInt(length) : 0 );
			PropertyTag pt = new PropertyTag(currentDocument, name, type, val, iLen, description, direction);
			pt.setCondition(condition);

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
			} else if ( currentParent instanceof BlockTag ) {
				((BlockTag) currentParent).add(pt);
			} else {
				throw new Exception("Property tags can only be found under a message, map or block tag.");
			}
			currentNode.push(pt);
			return;
		}

		if (currentMap.size() > 0 && tag.startsWith(currentMap.lastElement().getAdapterName()+".")) {  // navajomap.callwebservice
			String fieldName = tag.split("\\.")[1];
			String adapterName = tag.split("\\.")[0];
			
			boolean isField = mapChecker.isField(adapterName, fieldName);

			if ( currentParent instanceof MessageTag && isField && h.get(Attributes.VALUE) == null ) { // Mapped field if it is a getter (no value field specified and no expression under the tag)
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
			} else if ( currentParent instanceof ParamTag ) { // mapped param
				MapTag mt = new MapTag(currentDocument, fieldName, h.get(Attributes.FILTER), currentMap.lastElement(), false);
				Map<String,String> attributeMap = new HashMap<>();
				for ( String key : h.keySet() ) {
					attributeMap.put(key, h.get(key));
				}
				mt.addAttributes(attributeMap);
				((ParamTag) currentParent).addMap(mt);
				((ParamTag) currentParent).setType("array");
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
				if ( currentParent instanceof MessageTag ) {
					((MessageTag) currentParent).addField(ft);
				} else if ( currentParent instanceof BlockTag) {
					((BlockTag) currentParent).add(ft);
				} else if ( currentParent instanceof FinallyTag ) {
					((FinallyTag) currentParent).add(ft); 
				} else if ( currentParent instanceof MapTag ){
					((MapTag) currentParent).addField(ft); 
				} else {
					throw new Exception("Cannot place tag " + tag + " (setter/operation) under this tag: " + currentParent);
				}
				currentField.push(ft);
				currentNode.push(ft);
			}
		}

	}

	@Override
	public void endElement(String tag) throws Exception {
		
		if (currentMap.size() > 0 && tag.endsWith(currentMap.lastElement().getTagName())) {
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
		} else if (tag.startsWith(Tags.MAP)) {
			currentMap.pop();
			currentNode.pop();
		} else if (tag.equals(Tags.FIELD)) {
			currentField.pop();
			currentNode.pop();
		} else if ( currentField.size() > 0 && tag.endsWith(currentField.lastElement().getName())) {
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
		} else if ( tag.equals(Tags.VALUE)) {
			currentNode.pop();
		} else if ( tag.equals(Tags.DEFINES) ) {
			currentNode.pop();
		} else if ( tag.equals(Tags.DEFINE) ) {
			currentNode.pop();
		} else if ( tag.equals(Tags.BLOCK) ) {
			currentNode.pop();
		} else if ( tag.equals(Tags.FINALLY)) {
			currentNode.pop();
		} else if ( tag.equals(Tags.SYNCHRONIZED) ) {
			currentNode.pop();
		} else if ( tag.equals(Tags.DEBUG)) {
			currentNode.pop();
		} else if ( tag.equals(Tags.LOG)) {
			currentNode.pop();
		} else if ( tag.equals(Tags.METHODS)) {
			currentNode.pop();
		} else if ( tag.equals(Tags.METHOD)) {
			currentNode.pop();
		}

	}

	@Override
	public void startDocument() throws Exception {
		logger.debug("Start parsing of Navascript filed");
	}

	@Override
	public void endDocument() throws Exception {
		logger.debug("End parsing of Navascript filed");
	}

	@Override
	public void addComment(String c) {
		CommentBlock cb = new CommentBlock();
		cb.setComment(c);
		if ( currentNode != null && currentNode.size() > 0 ) {
			NS3Compatible cn = (NS3Compatible) currentNode.lastElement();
			cn.addComment(cb);
		}
	}
	
	@Override
	public void addCData(String s) {
		if ( currentNode != null && currentNode.size() > 0 ) {
			NS3Compatible cn = (NS3Compatible) currentNode.lastElement();
			if ( cn instanceof ValueTag ) {
				((ValueTag) cn).setValue(s);
			}
			if ( cn instanceof ExpressionTag ) {
				((ExpressionTag) cn).setValue(s);
			}
			if ( cn instanceof FieldTag ) {
				((FieldTag) cn).setValue(s);
			}
		}
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
		} else if ( n instanceof ValueTag ) {
			((ValueTag) n).setValue(text);
		} else if ( n instanceof DefineTag ) {
			((DefineTag) n).setExpression(text);
		} else {
			logger.info("Cannot place text under node, it must be a field tag. Correct.");
			if ( currentNode.lastElement() instanceof MapTag ) {
				currentMap.pop(); 
			} else {
				throw new Exception("Expected a MapTag to be corrected for a FieldTag not a " + currentNode.lastElement() + " tag.");
			}
			currentNode.pop();
			FieldTag ft = new FieldTag(currentMap.lastElement(), null, ((MapTag) n).getRefAttribute());
			ft.setConstant(text);
			BaseNode p = currentNode.lastElement();
			if ( p instanceof MessageTag ) {
				MessageTag mt = (MessageTag) p;
				mt.removeLastChild();
				mt.addField(ft);
			}
			currentNode.push(ft);
			currentField.push(ft);
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

}
