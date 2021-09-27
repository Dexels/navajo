package com.dexels.navajo.mapping.compiler.navascript;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.nanoimpl.CaseSensitiveXMLElement;
import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.document.navascript.tags.BlockTag;
import com.dexels.navajo.document.navascript.tags.BreakTag;
import com.dexels.navajo.document.navascript.tags.CheckTag;
import com.dexels.navajo.document.navascript.tags.DebugTag;
import com.dexels.navajo.document.navascript.tags.DefineTag;
import com.dexels.navajo.document.navascript.tags.DefinesTag;
import com.dexels.navajo.document.navascript.tags.ExpressionTag;
import com.dexels.navajo.document.navascript.tags.FieldTag;
import com.dexels.navajo.document.navascript.tags.FinallyTag;
import com.dexels.navajo.document.navascript.tags.IncludeTag;
import com.dexels.navajo.document.navascript.tags.LogTag;
import com.dexels.navajo.document.navascript.tags.LoopTag;
import com.dexels.navajo.document.navascript.tags.MapTag;
import com.dexels.navajo.document.navascript.tags.MessageTag;
import com.dexels.navajo.document.navascript.tags.MethodTag;
import com.dexels.navajo.document.navascript.tags.MethodsTag;
import com.dexels.navajo.document.navascript.tags.NS3Compatible;
import com.dexels.navajo.document.navascript.tags.NavascriptTag;
import com.dexels.navajo.document.navascript.tags.ParamTag;
import com.dexels.navajo.document.navascript.tags.PropertyTag;
import com.dexels.navajo.document.navascript.tags.SynchronizedTag;
import com.dexels.navajo.document.navascript.tags.ValidationsTag;
import com.dexels.navajo.mapping.compiler.navascript.parser.EventHandler;
import com.dexels.navajo.mapping.compiler.navascript.parser.navascript;

public class NS3ToNSXML implements EventHandler {

	private CharSequence input;
	private String delayedTag;
	public Writer out;
	private boolean hasChildElement;
	private int depth;
	private NavascriptTag myNavascript;
	private DefinesTag myDefines = null;

	public StringWriter xmlString = new StringWriter();

	private static final Logger logger = LoggerFactory.getLogger(NS3ToNSXML.class);

	public static void main(String [] args) throws Exception {
		NS3ToNSXML t = new NS3ToNSXML();

		String fileContent = t.read("/Users/arjenschoneveld/Loop.ns");

		t.initialize();

		BufferedReader bisr = new BufferedReader(new InputStreamReader(t.parseNavascript(fileContent)));

		String line = null;
		while ( ( line = bisr.readLine()) != null ) {
			System.out.println(line);
		}

		bisr.close();

	}

	public InputStream parseNavascript(String fileContent) throws Exception {

		navascript parser = new navascript(fileContent, this);
		input = fileContent;
		parser.parse_Navascript();

		String result = xmlString.toString();

		System.err.println(result);

		XMLElement xe = new CaseSensitiveXMLElement(true);

		xe.parseString(result);

		parseXML(myNavascript, xe, 0);

		PipedInputStream in = new PipedInputStream();
		PipedOutputStream out = new PipedOutputStream(in);

		new Thread() {
			public void run() {
				myNavascript.write(out);
				try {
					out.close();
				} catch (IOException e) {
					logger.error(e.getLocalizedMessage(), e);
				}
			}
		}.start();

		return in;
	}

	private void consumeContent(NavascriptFragment fragment, XMLElement xe) {

		String content = ( xe.getContent() != null && !"".equals(xe.getContent()) ?  xe.getContent() : null );
		xe.setAttribute("PROCESSED", "TRUE");
		if ( content == null ) {
			// do nothing
		} else if ( content.equals("if") ) {
			// do nothing
		} else if ( content.equals("then") ) {
			// end of condition
			fragment.finalize();
		} else if ( content.equals("else") ) {

		}
		else {
			fragment.consumeToken(content);
		}
		Vector<XMLElement> children = xe.getChildren();
		for ( XMLElement x : children ) {
			consumeContent(fragment, x);
		}
	}

	/**
	 * 
	 * parent is ParamTag or PropertyTag or FieldTag
	 * 
	 * Conditional
	 * Expression
	 * Conditional
	 * TOKEN else
	 * Expression
	 * 
	 * @param parent
	 * @param xe
	 */
	private List<ExpressionTag> parseConditionalExpressions(NS3Compatible parent, XMLElement currentXML) {

		List<ExpressionTag> expressions = new ArrayList<>();

		currentXML.setAttribute("PROCESSED", "true");	

		Vector<XMLElement> children = currentXML.getChildren();

		ConditionFragment conditionFragment = null;

		for ( XMLElement child : children ) {

			String name = child.getName();
			String content = ( child.getContent() != null && !"".equals(child.getContent()) ?  child.getContent() : null );

			if ( name.equals("Conditional") ) {
				conditionFragment = new ConditionFragment();
				consumeContent(conditionFragment, child);
			}

			if ( name.equals("StringConstant") ) {
				String constant = content.replaceAll("\"", "");
				ExpressionTag et = new ExpressionTag(myNavascript);
				if ( conditionFragment != null ) {
					et.setCondition(conditionFragment.consumedFragment());
				}
				et.setConstant(constant);
				expressions.add(et);
				conditionFragment = null;
			}

			if ( name.equals("Expression") ) {
				ExpressionFragment ef = new ExpressionFragment();
				consumeContent(ef, child);
				ExpressionTag et = new ExpressionTag(myNavascript);
				if ( conditionFragment != null ) {
					et.setCondition(conditionFragment.consumedFragment());
				}
				et.setValue(ef.consumedFragment());
				expressions.add(et);
				conditionFragment = null;
			}

			if ( name.equals("TOKEN") && content.trim().equals("else") ) {
				conditionFragment = null;
			}

		}

		return expressions;

	}

	private MapTag findClosestMapTag(NS3Compatible tag, String methodOrSetter) throws Exception {

		if ( tag instanceof MapTag ) {
			return (MapTag) tag;
		}

		if ( tag.getParentTag() == null ) {
			throw new Exception("Could not find map for: " + methodOrSetter);
		} else {
			return findClosestMapTag(tag.getParentTag(), methodOrSetter);
		}

	}

	private ParamTag parseVarArrayElement(ParamTag parent, XMLElement currentXML) throws Exception {

		ParamTag paramElt = new ParamTag(myNavascript);

		paramElt.setType(Message.MSG_TYPE_ARRAY_ELEMENT);

		Vector<XMLElement> children = currentXML.getChildren();

		for ( XMLElement child : children ) {

			String name = child.getName();

			if ( name.equals("Var")) {
				ParamTag pt = parseVar(paramElt, child);
				paramElt.addParam(pt);
			}

		}

		return paramElt;
	}

	private ParamTag parseVar(NS3Compatible parent, XMLElement currentXML) throws Exception {
		// Conditional -> VarName -> "=" OR ":" -> ConditionalExpressions

		Vector<XMLElement> children = currentXML.getChildren();

		ParamTag paramTag = new ParamTag(myNavascript);
		paramTag.addParent(parent);

		currentXML.setAttribute("PROCESSED", "true");

		for ( XMLElement child : children ) {

			String name = child.getName();

			String content = ( child.getContent() != null && !"".equals(child.getContent()) ?  child.getContent() : null );

			if ( name.equals("VarName") ) {
				paramTag.setName(content);
			}

			if (name.equals("VarArguments") ) {
				parseVarArguments(paramTag, child);
			}

			if ( name.equals("Conditional") ) {
				ConditionFragment currentFragment = new ConditionFragment();
				consumeContent(currentFragment, child);
				paramTag.setCondition(currentFragment.consumedFragment());
			}

			if ( name.equals("ConditionalExpressions") ) {
				List<ExpressionTag> expressions = parseConditionalExpressions(paramTag, child);
				for ( ExpressionTag et : expressions ) {
					paramTag.addExpression(et);
				}
			}

			if ( name.equals("MappedArrayField")) { // <map ref="$"
				MapTag maf = parseMappedArrayField((MapTag) parent, child);
				paramTag.addMap(maf);
			}

			if  ( name.equals("MappedArrayMessage") ) {
				MapTag mt = parsedMappedArrayMessage(parent, child);
				paramTag.addMap(mt);
			}

			if ( name.equals("VarArray")) {
				paramTag.setType(Message.MSG_TYPE_ARRAY);
				Vector<XMLElement> paramChildren = child.getChildren();
				for ( XMLElement paramChild : paramChildren ) {

					if ( paramChild.getName().equals("VarArrayElement")) {
						ParamTag paramElt = parseVarArrayElement(paramTag, paramChild);
						paramElt.setName(paramTag.getName());
						paramTag.addParam(paramElt);
					}
				}
			}

		}

		return paramTag;

	}

	private String parseStringConstant(XMLElement currentXML) { 

		currentXML.setAttribute("PROCESSED", "true");

		Vector<XMLElement> children = currentXML.getChildren();

		for ( XMLElement child : children ) {

			String name = child.getName();
			String content = ( child.getContent() != null && !"".equals(child.getContent()) ?  child.getContent() : null );

			if ( name.equals("LiteralOrExpression")) {
				return parseStringConstant(child);
			}

			if ( name.equals("StringConstant")) {
				return content.replaceAll("\"", "");
			} 

		}

		return "";
	}

	private String parseExpression(XMLElement currentXML) {


		ExpressionFragment ef = new ExpressionFragment();

		currentXML.setAttribute("PROCESSED", "true");

		Vector<XMLElement> children = currentXML.getChildren();

		for ( XMLElement child : children ) {

			String name = child.getName();

			if ( name.equals("Expression")) {
				consumeContent(ef, child);
			} 

		}

		return ef.consumedFragment();
	}

	private boolean isLiteral(XMLElement currentXML) {

		Vector<XMLElement> children = currentXML.getChildren();

		for ( XMLElement child : children ) {

			String name = child.getName();

			if ( name.equals("LiteralOrExpression") ) {
				return isLiteral(child);
			}

			if ( name.equals("StringConstant") ) {
				return true;
			}

		}

		return false;
	}

	private void parseVarArguments(ParamTag p, XMLElement currentXML) {
		currentXML.setAttribute("PROCESSED", "true");

		Vector<XMLElement> children = currentXML.getChildren();

		for ( XMLElement child : children ) {

			String name = child.getName();
			String content = ( child.getContent() != null && !"".equals(child.getContent()) ?  child.getContent() : null );

			if ( name.equals("MessageMode")) {
				p.setMode(content);
			}

			if ( name.equals("MessageType")) {
				p.setType(content);
			}

			parseVarArguments(p, child);
		}
	}


	private void parsePropertyArguments(PropertyTag p, XMLElement currentXML) {

		currentXML.setAttribute("PROCESSED", "true");

		Vector<XMLElement> children = currentXML.getChildren();

		for ( XMLElement child : children ) {

			String name = child.getName();
			String content = ( child.getContent() != null && !"".equals(child.getContent()) ?  child.getContent() : null );

			if ( name.equals("PropertyTypeValue")) {
				p.setType(content);
			}

			if ( name.equals("PropertyDirectionValue")) {
				p.setDirection(content);
			}

			if ( name.equals("PropertyCardinalityValue")) {
				p.setCardinality(content);
			}

			if ( name.equals("PropertyDescription")) {
				boolean isLiteral = isLiteral(child);
				if ( isLiteral ) {
					p.setDescription(parseStringConstant(child));
				}
			}

			parsePropertyArguments(p, child);
		}
	}

	private void parseOptionElement(ParamTag ft, XMLElement currentXML) {

		Vector<XMLElement> children = currentXML.getChildren();

		ParamTag pt = null;

		for ( XMLElement child : children ) {

			String name = child.getName();
			String content = ( child.getContent() != null && !"".equals(child.getContent()) ?  child.getContent() : null );

			if ( name.equals("TOKEN") && ( content.equals("name") || content.equals("value") || content.equals("selected") ) ) {
				pt = new ParamTag(myNavascript);
				pt.setName(content);
				ft.addParam(pt);
			} 

			if ( name.equals("ConditionalExpressions") ) {
				List<ExpressionTag> expressions = parseConditionalExpressions(pt, child);
				for ( ExpressionTag et : expressions ) {
					pt.addExpression(et);
				}
			}
		}

	}

	// Option
	private void parseSelectionArrayElement(ParamTag ft, XMLElement currentXML) throws Exception {

		Vector<XMLElement> children = currentXML.getChildren();

		for ( XMLElement child : children ) {

			String name = child.getName();

			if ( name.equals("Option") ) {
				parseOptionElement(ft, child);
			} else {
				parseSelectionArrayElement(ft, child);
			}

		}

	}

	private void parseSelectionArrayElements(ParamTag paramtag, MapTag selectionMap, XMLElement currentXML) throws Exception {

		Vector<XMLElement> children = currentXML.getChildren();

		for ( XMLElement child : children ) {

			String name = child.getName();

			if ( name.equals("SelectionArrayElement")) {
				ParamTag pt = new ParamTag(myNavascript);
				pt.setType(Message.MSG_TYPE_ARRAY_ELEMENT);
				pt.setName(paramtag.getName());
				paramtag.addParam(pt);

				parseSelectionArrayElement(pt, child);
			}
		}

	}

	private String randomParamName(String base) {

		Random r = new Random(System.currentTimeMillis());

		return base + r.nextInt();
	}

	private NS3Compatible parseProperty(NS3Compatible parent, XMLElement currentXML) throws Exception {

		currentXML.setAttribute("PROCESSED", "true");

		PropertyTag pt = new PropertyTag(myNavascript);
		pt.addParent(parent);

		Vector<XMLElement> children = currentXML.getChildren();

		for ( XMLElement child : children ) {

			String name = child.getName();
			String content = ( child.getContent() != null && !"".equals(child.getContent()) ?  child.getContent() : null );

			if ( name.equals("TOKEN") ) {
				if ( content.equals("value") ) {
					pt.setName("value");
				} 
				if ( content.equals("name") ) {
					pt.setName("name");
				} 
				if ( content.equals("selected") ) {
					pt.setName("selected");
				} 
			}

			if ( name.equals("Conditional") ) {
				ConditionFragment currentFragment = new ConditionFragment();
				consumeContent(currentFragment, child);
				pt.setCondition(currentFragment.consumedFragment());
			}

			if (name.equals("PropertyName") ) {
				pt.setName(content);
			}

			if (name.equals("PropertyArguments") ) {
				parsePropertyArguments(pt, child);
			}

			if ( name.equals("ConditionalExpressions") ) {
				List<ExpressionTag> expressions = parseConditionalExpressions(pt, child);
				for ( ExpressionTag et : expressions ) {
					pt.addExpression(et);
				}
			}

			if  ( name.equals("StringConstant") ) {
				String c = content.replaceAll("\"", "");
				ExpressionTag et = new ExpressionTag(myNavascript);
				et.setConstant(c);
				pt.addExpression(et);
			} 

			if ( name.equals("MappedArrayFieldSelection")) {
				MapTag maf = parseMappedArrayField(parent, child);
				pt.addMap(maf);
			}

			if ( name.equals("MappedArrayMessageSelection")) {
				MapTag maf = parsedMappedArrayMessage(parent, child);
				pt.addMap(maf);
			}

			// There is an array of selection options defined for this selection property.
			// Use SelectionMap in combination with a param array message to support this construction in navascript XML.
			if ( name.equals("SelectionArray")) {  
				// Create param array
				ParamTag paramtag = new ParamTag(myNavascript);
				paramtag.setType(Message.MSG_TYPE_ARRAY);
				paramtag.setName(randomParamName( pt.getName() + "_selections"));
				myNavascript.addParam(paramtag);
				// Create com.dexels.navajo.adapter.SelectionMap
				MapTag mt = new MapTag(myNavascript);
				mt.setObject("com.dexels.navajo.adapter.SelectionMap");
				mt.setOldStyleMap(true);

				FieldTag fieldOptions = new FieldTag(mt);
				fieldOptions.setFieldName("options");
				fieldOptions.setOldSkool(true);
				MapTag mappedOptions = new MapTag(myNavascript);
				fieldOptions.addMap(mappedOptions);
				mappedOptions.setOldStyleMap(true);
				mappedOptions.setRefAttribute("[/@" + paramtag.getName() + "]");
				// Add name, value, selected
				FieldTag nameField = new FieldTag(mappedOptions);
				nameField.setOldSkool(true);
				nameField.setFieldName("optionName");
				mappedOptions.addField(nameField);
				nameField.addExpression(null, "[name]");

				FieldTag valueField = new FieldTag(mappedOptions);
				valueField.setOldSkool(true);
				valueField.setFieldName("optionValue");
				mappedOptions.addField(valueField);
				valueField.addExpression(null, "[value]");

				FieldTag selectedField = new FieldTag(mappedOptions);
				selectedField.setOldSkool(true);
				selectedField.setFieldName("optionSelected");
				mappedOptions.addField(selectedField);
				selectedField.addExpression(null, "[selected]");

				// add options field to map
				mt.addField(fieldOptions);

				// add property to selectionmap
				mt.addProperty(pt);
				MapTag refOptions = new MapTag(myNavascript);
				refOptions.setOldStyleMap(true);
				refOptions.setRefAttribute("options");
				pt.addMap(refOptions);

				PropertyTag nameProp = new PropertyTag(myNavascript);
				nameProp.setName("name");
				nameProp.addExpression(null, "$optionName");
				refOptions.addProperty(nameProp);


				PropertyTag valueProp = new PropertyTag(myNavascript);
				valueProp.setName("value");
				valueProp.addExpression(null, "$optionValue");
				refOptions.addProperty(valueProp);

				PropertyTag selectedProp = new PropertyTag(myNavascript);
				selectedProp.setName("selected");
				selectedProp.addExpression(null, "$optionSelected");
				refOptions.addProperty(selectedProp);

				// Fetch the array elements to construct a param array message to store them.
				parseSelectionArrayElements(paramtag, mt, child);

				// Return the map instead of the property.
				return mt;
			}
		}

		return pt;
	}

	private void parseAdapterMethod(FieldTag parent, XMLElement currentXML) {

		//MethodName
		Vector<XMLElement> children = currentXML.getChildren();

		for ( XMLElement child : children ) {

			String name = child.getName();
			String content = ( child.getContent() != null && !"".equals(child.getContent()) ?  child.getContent() : null );

			if ( name.equals("MethodName") ) {
				parent.setFieldName(content);
			}

			if  ( name.equals("KeyValueArguments") ) {
				parseMapOrMethodArguments(parent, child);
			} 
		}

	}

	private void parseAdapterField(FieldTag parent, XMLElement currentXML) throws Exception {

		//MethodName
		Vector<XMLElement> children = currentXML.getChildren();

		parent.setOldSkool(true);

		for ( XMLElement child : children ) {

			String name = child.getName();
			String content = ( child.getContent() != null && !"".equals(child.getContent()) ?  child.getContent() : null );

			if ( name.equals("Conditional") ) {
				ConditionFragment currentFragment = new ConditionFragment();
				consumeContent(currentFragment, child);
				parent.setCondition(currentFragment.consumedFragment());
			}

			if ( name.equals("FieldName") ) {
				parent.setFieldName(content);
			}

			if  ( name.equals("ConditionalExpressions") ) {
				List<ExpressionTag> expressions = parseConditionalExpressions(parent, child);
				for ( ExpressionTag et : expressions ) {
					parent.addExpression(et);
				}
			} 

			if ( name.equals("MappedArrayField")) { // <map ref="$"
				MapTag maf = parseMappedArrayField((MapTag) parent.getParent(), child);
				parent.addMap(maf);
			}

			if  ( name.equals("MappedArrayMessage") ) {
				MapTag mt = parsedMappedArrayMessage(parent, child);
				parent.addMap(mt);
			}

			if  ( name.equals("StringConstant") ) {
				String c = content.replaceAll("\"", "");
				ExpressionTag et = new ExpressionTag(myNavascript);
				et.setConstant(c);
				parent.addExpression(et);
			} 
		}

	}

	private MapTag parsedMappedArrayMessage(NS3Compatible parent, XMLElement currentXML) throws Exception {

		MapTag mapTag = new MapTag(myNavascript);
		mapTag.addParent(parent);

		Vector<XMLElement> children = currentXML.getChildren();

		mapTag.setOldStyleMap(true);
		if ( parent != null && parent instanceof FieldTag ) {
			mapTag.setName(((FieldTag) parent).getParent().getObject());
		}

		boolean hasFilter = false;

		for ( XMLElement child : children ) {

			String name = child.getName();
			String content = ( child.getContent() != null && !"".equals(child.getContent()) ?  child.getContent() : null );

			if ( name.equals("MsgIdentifier" )) {
				mapTag.setRefAttribute("[" + content + "]");
			}

			if ( name.equals("TOKEN") && content.equals("filter") ) {
				hasFilter = true;
			}

			if ( hasFilter && name.equals("Expression") ) {
				ExpressionFragment ef = new ExpressionFragment();
				consumeContent(ef, child);
				mapTag.setFilter(ef.consumedFragment());
			}

			if (name.equals("InnerBody") || name.equals("InnerBodySelection") ) {
				List<NS3Compatible> innerBodyElements = parseInnerBody(mapTag, child);
				for ( NS3Compatible ib : innerBodyElements ) {
					addChildTag(mapTag, ib);
				}
			}
		}

		return mapTag;

	}

	private FieldTag parseMethodOrSetter(MapTag parent, XMLElement currentXML) throws Exception {

		FieldTag ft = new FieldTag(parent);
		ft.setParent(parent);
		ft.addParent(parent);

		Vector<XMLElement> children = currentXML.getChildren();

		for ( XMLElement child : children ) {

			String name = child.getName();

			if ( name.equals("Conditional") ) {
				ConditionFragment currentFragment = new ConditionFragment();
				consumeContent(currentFragment, child);
				ft.setCondition(currentFragment.consumedFragment());
			}

			if ( name.equals("AdapterMethod") ) {
				parseAdapterMethod(ft, child);
			}

			if ( name.equals("SetterField"))  {
				parseAdapterField(ft, child);
			}
		}

		return ft;
	}

	private List<NS3Compatible> parseInnerBody(NS3Compatible parent, XMLElement currentXML) throws Exception {

		currentXML.setAttribute("PROCESSED", "true");

		List<NS3Compatible> bodyElts = new ArrayList<>();

		Vector<XMLElement> children = currentXML.getChildren();

		for ( XMLElement child : children ) {

			String name = child.getName();
			String content = ( child.getContent() != null && !"".equals(child.getContent()) ?  child.getContent() : null );

			if ( name.equals("Print") ) {
				DebugTag pt = parsePrint(parent, child);
				bodyElts.add(pt);
			}

			if ( name.equals("Log") ) {
				LogTag pt = parseLog(parent, child);
				bodyElts.add(pt);
			}

			if ( name.equals("Property") ) {
				NS3Compatible pt = parseProperty(parent, child);
				bodyElts.add(pt);
			}

			if ( name.equals("Option") ) {
				// Find type of option: name, value or selected
				NS3Compatible pt = parseProperty(parent, child);
				bodyElts.add(pt);
			}

			if ( name.equals("Map")) {
				MapTag mt = parseMap(parent, child);
				bodyElts.add(mt);
			}

			if ( name.equals("Var")) {
				ParamTag pt = parseVar(parent, child);
				bodyElts.add(pt);
			}

			if ( name.equals("Break") ) {
				BreakTag mt = parseBreak(parent, child);
				bodyElts.add(mt);
			}

			if ( name.equals("Include") ) {
				IncludeTag mt = parseInclude(parent, child);
				bodyElts.add(mt);
			}

			if ( name.equals("Message")) {
				MessageTag pt = parseMessage(parent, child);
				bodyElts.add(pt);
			}

			if ( name.equals("Synchronized")) {
				SynchronizedTag st = parseSynchronizedBlock(parent, child);
				bodyElts.add(st);
			}

			if ( name.equals("ConditionalEmptyMessage")) {
				BlockTag pt = parseConditionalBlock(parent, child, true);
				bodyElts.add(pt);
			}

			if ( name.equals("TopLevelStatement")) {
				bodyElts.addAll(parseInnerBody(parent, child));
			}

			if (name.equals("MethodOrSetter")) {
				MapTag parentMap = findClosestMapTag(parent, "");
				FieldTag ft = parseMethodOrSetter(parentMap, child);
				bodyElts.add(ft);
			} 

			if (name.equals("Loop")) {
				LoopTag mt = parseLoop(parent, child);
				bodyElts.add(mt);
			}

		}

		return bodyElts;
	}

	private LoopTag parseLoop(NS3Compatible parent, XMLElement currentXML) throws Exception {

		// Define arraymessage adapter
		LoopTag mt = new LoopTag(myNavascript);
		//mt.setName("arraymessage");
		mt.addParent(parent);

		// Define map for mapped array message of array field
		MapTag ref = new MapTag(myNavascript);
		ref.setOldStyleMap(true);
		mt.addMap(ref);

		Vector<XMLElement> children = currentXML.getChildren();
		boolean hasFilter = false;

		for ( XMLElement child : children ) {

			String name = child.getName();
			String content = ( child.getContent() != null && !"".equals(child.getContent()) ?  child.getContent() : null );

			if ( name.equals("Conditional")) {
				ConditionFragment currentFragment = new ConditionFragment();
				consumeContent(currentFragment, child);
				mt.setCondition(currentFragment.consumedFragment());
			}

			if ( name.equals("MsgIdentifier") ) {
				ref.setRefAttribute("[" + content + "]");
			}


			if ( name.equals("MappableIdentifier")) {
				String fieldRef = parseMappableIdentifier(child);
				ref.setRefAttribute(fieldRef);
			}

			if ( name.equals("TOKEN") && content.equals("filter") ) {
				hasFilter = true;
			}

			if ( hasFilter && name.equals("Expression") ) {
				ExpressionFragment ef = new ExpressionFragment();
				consumeContent(ef, child);
				ref.setFilter(ef.consumedFragment());
			}

			if (name.equals("InnerBody") || name.equals("InnerBodySelection") ) {
				List<NS3Compatible> innerBodyElements = parseInnerBody(ref, child);
				for ( NS3Compatible ib : innerBodyElements ) {
					addChildTag(ref, ib);
				}
			}

		}

		return mt;
	}

	private void parseMapOrMethodArguments(NS3Compatible parent, XMLElement currentXML) {

		Vector<XMLElement> children = currentXML.getChildren();

		String key = null;
		for ( XMLElement child : children ) {
			String name = child.getName();
			String content = ( child.getContent() != null && !"".equals(child.getContent()) ?  child.getContent() : null );

			if ( name.equals("ParamKeyName")  ) {
				key = content;
			}

			if ( name.equals("LiteralOrExpression") ) {
				String c = null;
				if ( isLiteral(child)) {
					c = parseStringConstant(child);
				} else {
					c =  parseExpression(child);
				}
				if ( key != null ) {
					if ( parent instanceof MapTag ) {
						((MapTag) parent).addAttributeNameValue(key, c);
					}
					if ( parent instanceof FieldTag ) {
						((FieldTag) parent).addAttributeNameValue(key, c);
					}
				}
				key = null;
			}
		}

	}

	private MapTag parseMap(NS3Compatible parent, XMLElement currentXML) throws Exception {

		currentXML.setAttribute("PROCESSED", "true");

		MapTag mapTag = new MapTag(myNavascript);
		mapTag.addParent(parent);

		Vector<XMLElement> children = currentXML.getChildren();

		for ( XMLElement child : children ) {
			String name = child.getName();
			String content = ( child.getContent() != null && !"".equals(child.getContent()) ?  child.getContent() : null );

			if ( name.equals("Conditional") ) {
				ConditionFragment currentFragment = new ConditionFragment();
				consumeContent(currentFragment, child);
				mapTag.setCondition(currentFragment.consumedFragment());
			} 

			if ( name.equals("AdapterName")) {
				mapTag.setName(content);
			} 

			if ( name.equals("ClassName")) { // old style map
				mapTag.setOldStyleMap(true);
				mapTag.setObject(content);
			} 

			if ( name.equals("Include")) {
				IncludeTag it = parseInclude(parent, child);
				mapTag.addInclude(it);
			} 

			if  ( name.equals("KeyValueArguments") ) {
				parseMapOrMethodArguments(mapTag, child);
			} 

			if (name.equals("InnerBody")) {
				List<NS3Compatible> innerBodyElements = parseInnerBody(mapTag, child);
				for ( NS3Compatible ib : innerBodyElements ) {
					addChildTag(mapTag, ib);
				}
			}
		}

		return mapTag;

	}

	/**
	 * Parse
	 * <Identifier><Arguments>
	 * 
	 * @param currentXML
	 * @return
	 */
	private String parseMappableIdentifier(XMLElement currentXML) {

		StringBuffer result = new StringBuffer();

		Vector<XMLElement> children = currentXML.getChildren();

		for ( XMLElement child : children ) {
			String name = child.getName();
			String content = ( child.getContent() != null && !"".equals(child.getContent()) ?  child.getContent() : null );

			if ( name.equals("Identifier")) {
				result.append("$"+content);
			}

			if ( name.equals("Arguments")) {
				ExpressionFragment ef = new ExpressionFragment();
				consumeContent(ef, child);
				result.append(ef.consumedFragment());
			}

		}

		return result.toString();

	}

	private MapTag parseMappedArrayField(NS3Compatible parent, XMLElement currentXML) throws Exception {

		currentXML.setAttribute("PROCESSED", "true");

		MapTag ft = new MapTag(myNavascript);
		ft.addParent(parent);

		if ( parent != null && parent instanceof MapTag ) {
			ft.setParent((MapTag) parent);
		}
		ft.setOldStyleMap(true);

		Vector<XMLElement> children = currentXML.getChildren();

		boolean hasFilter = false;

		for ( XMLElement child : children ) {
			String name = child.getName();

			String content = ( child.getContent() != null && !"".equals(child.getContent()) ?  child.getContent() : null );

			if ( name.equals("MappableIdentifier")) {
				String fieldRef = parseMappableIdentifier(child);
				ft.setRefAttribute(fieldRef);
			}

			if ( name.equals("FieldName")) {
				ft.setRefAttribute(content);
			}

			if ( name.equals("TOKEN") && content.equals("filter") ) {
				hasFilter = true;
			}

			if ( hasFilter && name.equals("Expression") ) {
				ExpressionFragment ef = new ExpressionFragment();
				consumeContent(ef, child);
				ft.setFilter(ef.consumedFragment());
			}

			if (name.equals("InnerBody") || name.equals("InnerBodySelection") ) {
				List<NS3Compatible> innerBodyElements = parseInnerBody(ft, child);
				for ( NS3Compatible ib : innerBodyElements ) {
					addChildTag(ft, ib);
				}
			}
		}

		return ft;
	}

	private BlockTag parseConditionalBlock(NS3Compatible parent, XMLElement currentXML, boolean isEmptyMessage) throws Exception {

		BlockTag bt = new BlockTag(myNavascript);
		bt.addParent(parent);

		Vector<XMLElement> children = currentXML.getChildren();

		for ( XMLElement child : children ) {
			String name = child.getName();
			String content = ( child.getContent() != null && !"".equals(child.getContent()) ?  child.getContent() : null );

			if ( name.equals("Conditional") ) {
				ConditionFragment currentFragment = new ConditionFragment();
				consumeContent(currentFragment, child);
				bt.setCondition(currentFragment.consumedFragment());
			}

			if (name.equals("InnerBody") ) {
				List<NS3Compatible> innerBodyElements = parseInnerBody(bt, child);
				for ( NS3Compatible ib : innerBodyElements ) {
					addChildTag(bt, ib);
				}
			}
		}

		return bt;

	}

	private void parseSynchronizedArguments(String key, SynchronizedTag p, XMLElement currentXML) {

		currentXML.setAttribute("PROCESSED", "true");

		Vector<XMLElement> children = currentXML.getChildren();

		for ( XMLElement child : children ) {

			String name = child.getName();
			String content = ( child.getContent() != null && !"".equals(child.getContent()) ?  child.getContent() : null );

			if ( name.equals("SContextType")) {
				p.setContext(content);
			}

			if ( name.equals("SKey")) {
				key = "key";
			}

			if ( name.equals("STimeout") ) {
				key = "timeout";
			}

			if ( name.equals("SBreakOnNoLock") ) {
				key = "breakOnNoLock";
			}

			if ( name.equals("LiteralOrExpression") || name.equals("Expression") ) {
				String c = null;
				if ( name.equals("Expression")) {
					ExpressionFragment ef = new ExpressionFragment();
					consumeContent(ef, child);
					c = ef.consumedFragment();
				} else {
					if ( isLiteral(child)) {
						c = parseStringConstant(child);
					} else {
						c =  parseExpression(child);
					}
				}
				if ( key != null ) {
					if ( key.equals("key")) {
						p.setKey(c);
					}
					if ( key.equals("timeout")) {
						p.setTimeout(c);
					}
					if ( key.equals("breakOnNoLock") ) {
						p.setBreakOnNoLock(c);
					}
				}
				key = null;
			}

			parseSynchronizedArguments(key, p, child);
		}
	}

	private SynchronizedTag parseSynchronizedBlock(NS3Compatible parent, XMLElement xe) throws Exception {

		SynchronizedTag st = new SynchronizedTag(myNavascript);
		st.addParent(parent);

		Vector<XMLElement> children = xe.getChildren();

		for ( XMLElement child : children ) {

			String name = child.getName();

			if ( name.equals("SynchronizedArguments")) {
				parseSynchronizedArguments(null, st, child);
			}

			if ( name.equals("TopLevelStatement")) {
				List<NS3Compatible> innerBodyElements = parseInnerBody(st, child);
				for ( NS3Compatible ib : innerBodyElements ) {
					addChildTag(st, ib);
				}
			}

		}

		return st;
	}

	private void parseMessageArguments(MessageTag p, XMLElement currentXML) {

		currentXML.setAttribute("PROCESSED", "true");

		Vector<XMLElement> children = currentXML.getChildren();

		for ( XMLElement child : children ) {

			String name = child.getName();
			String content = ( child.getContent() != null && !"".equals(child.getContent()) ?  child.getContent() : null );

			if ( name.equals("MessageMode")) {
				p.setMode(content+"_"); // Add _ to prevent ignore messages from not being rendered.
			}

			if ( name.equals("MessageType")) {
				p.setType(content);
			}

			parseMessageArguments(p, child);
		}
	}

	private MessageTag parseMessage(NS3Compatible parent, XMLElement currentXML) throws Exception {

		currentXML.setAttribute("PROCESSED", "true");

		Vector<XMLElement> children = currentXML.getChildren();

		MessageTag msgTag = new MessageTag(myNavascript);
		msgTag.addParent(parent);

		for ( XMLElement child : children ) {
			String name = child.getName();

			String content = ( child.getContent() != null && !"".equals(child.getContent()) ?  child.getContent() : null );

			if ( name.equals("Conditional") ) {
				ConditionFragment currentFragment = new ConditionFragment();
				consumeContent(currentFragment, child);
				msgTag.setCondition(currentFragment.consumedFragment());
			}

			if  ( name.equals("MappedArrayMessage") ) { // <map ref="[]"
				MapTag mt = parsedMappedArrayMessage(null, child);
				msgTag.addMap(mt);
			}

			if ( name.equals("MappedArrayField")) { // <map ref="$"
				MapTag maf = parseMappedArrayField((MapTag) parent, child);
				msgTag.addMap(maf);
			}

			if ( name.equals("MsgIdentifier")) {
				msgTag.setName(content);
			}

			if ( name.equals("MessageArguments")) {
				parseMessageArguments(msgTag, child);
			}

			if (name.equals("InnerBody") ) {
				List<NS3Compatible> innerBodyElements = parseInnerBody(msgTag, child);
				for ( NS3Compatible ib : innerBodyElements ) {
					addChildTag(msgTag, ib);
				}
			}

			if ( name.equals("MessageArray") ) {
				msgTag.setType(Message.MSG_TYPE_ARRAY);
				Vector<XMLElement> messageChildren = child.getChildren();
				int count = 0;
				for ( XMLElement messageChild : messageChildren ) {

					if ( messageChild.getName().equals("MessageArrayElement")) {
						MessageTag messageElt = parseMessage(parent, messageChild);
						messageElt.setType(Message.MSG_TYPE_ARRAY_ELEMENT);
						messageElt.setName(msgTag.getName());
						messageElt.setIndex(count);
						msgTag.addMessage(messageElt);
						count++;
					}
				}
			}

		}

		return msgTag;


	}

	private IncludeTag parseInclude(NS3Compatible parent, XMLElement currentXML) throws Exception {

		currentXML.setAttribute("PROCESSED", "true");

		IncludeTag it = new IncludeTag(myNavascript);
		it.addParent(parent);

		Vector<XMLElement> children = currentXML.getChildren();

		for ( XMLElement child : children ) {
			String name = child.getName();
			String content = ( child.getContent() != null && !"".equals(child.getContent()) ?  child.getContent() : null );

			if ( name.equals("Conditional") ) {
				ConditionFragment currentFragment = new ConditionFragment();
				consumeContent(currentFragment, child);
				it.setCondition(currentFragment.consumedFragment());
			}

			if ( name.equals("ScriptIdentifier") ) {
				it.setScript(content);
			}
		}


		//Include

		return it;
	}

	private DebugTag parsePrint(NS3Compatible parent, XMLElement currentXML) throws Exception {

		currentXML.setAttribute("PROCESSED", "true");

		DebugTag dt = new DebugTag(myNavascript);
		dt.addParent(parent);

		Vector<XMLElement> children = currentXML.getChildren();

		for ( XMLElement child : children ) {

			String name = child.getName();

			if ( name.equals("Conditional")) {
				ConditionFragment currentFragment = new ConditionFragment();
				consumeContent(currentFragment, child);
				dt.setCondition(currentFragment.consumedFragment());
			}

			if ( name.equals("Expression")) {
				ExpressionFragment ef = new ExpressionFragment();
				consumeContent(ef, child);
				dt.setValue(ef.consumedFragment());
			}
		}

		return dt;

	}

	private LogTag parseLog(NS3Compatible parent, XMLElement currentXML) throws Exception {

		currentXML.setAttribute("PROCESSED", "true");

		LogTag dt = new LogTag(myNavascript);
		dt.addParent(parent);

		Vector<XMLElement> children = currentXML.getChildren();

		for ( XMLElement child : children ) {

			String name = child.getName();

			if ( name.equals("Conditional")) {
				ConditionFragment currentFragment = new ConditionFragment();
				consumeContent(currentFragment, child);
				dt.setCondition(currentFragment.consumedFragment());
			}

			if ( name.equals("Expression")) {
				ExpressionFragment ef = new ExpressionFragment();
				consumeContent(ef, child);
				dt.setValue(ef.consumedFragment());
			}
		}

		return dt;

	}

	private void parseBreakParameters(BreakTag p, XMLElement currentXML) {

		currentXML.setAttribute("PROCESSED", "true");

		Vector<XMLElement> children = currentXML.getChildren();

		String parameter = null;

		for ( XMLElement child : children ) {

			String name = child.getName();
			String content = ( child.getContent() != null && !"".equals(child.getContent()) ?  child.getContent() : null );

			if ( name.equals("BreakParameter")) {
				parseBreakParameters(p, child);
			}

			if ( name.equals("TOKEN") && content.trim().equals("code") )  {
				parameter = "code";
			}

			if ( name.equals("TOKEN") && content.trim().equals("description") )  {
				parameter = "description";
			}

			if ( name.equals("LiteralOrExpression") ) {
				String c = null;
				if ( isLiteral(child)) {
					c = parseStringConstant(child);
				} else {
					c =  parseExpression(child);
				}
				if ( parameter != null ) {
					if ( parameter.equals("code") ) {
						p.setConditionId(c);
					} else if ( parameter.equals("description")) {
						p.setConditionDescription(c);
					}
				}
				parameter = null;
			}
		}
	}

	private BreakTag parseBreak(NS3Compatible parent, XMLElement currentXML) throws Exception {

		currentXML.setAttribute("PROCESSED", "true");

		BreakTag bt = new BreakTag(myNavascript);
		bt.addParent(parent);

		Vector<XMLElement> children = currentXML.getChildren();

		for ( XMLElement child : children ) {
			String name = child.getName();
			String content = ( child.getContent() != null && !"".equals(child.getContent()) ?  child.getContent() : null );

			if ( name.equals("Conditional") ) {
				ConditionFragment currentFragment = new ConditionFragment();
				consumeContent(currentFragment, child);
				bt.setCondition(currentFragment.consumedFragment());
			}

			if ( name.equals("BreakParameters")) {
				parseBreakParameters(bt, child);
			}
		}

		return bt;
	}

	private void parseCheckAttributes(CheckTag ct, XMLElement xe) {
		Vector<XMLElement> children = xe.getChildren();

		String param = null;

		for ( XMLElement child : children ) {

			String name = child.getName();
			String content = ( child.getContent() != null && !"".equals(child.getContent()) ?  child.getContent() : null );

			if ( name.endsWith("Conditional")) {
				ConditionFragment conditionFragment = new ConditionFragment();
				consumeContent(conditionFragment, child);
				ct.setCondition(conditionFragment.consumedFragment());
			}

			if ( name.equals("CheckAttributes")) {
				parseCheckAttributes(ct, child);
			}

			if ( name.equals("CheckAttribute")) {
				parseCheckAttributes(ct, child);
			}

			if ( name.equals("TOKEN") ) {
				param = content;
			}

			if ( name.equals("LiteralOrExpression")) {
				String c = null;
				if ( isLiteral(child)) {
					c = parseStringConstant(child);
				} else {
					c =  parseExpression(child);
				}
				if ( param.equals("code") ) {
					ct.setCode(c);
				}
				if ( param.equals("description") ) {
					ct.setDescription(c);
				}
			}

			if ( name.equals("Expression")) {
				ExpressionFragment ef = new ExpressionFragment();
				consumeContent(ef, child);
				ct.setRule(ef.consumedFragment());
			}
		}
	}

	private ValidationsTag parseValidations(NS3Compatible parent, XMLElement xe) {

		ValidationsTag vt = new ValidationsTag(myNavascript);
		vt.addParent(parent);

		Vector<XMLElement> children = xe.getChildren();

		CheckTag ct = null;

		for ( XMLElement child : children ) {

			String name = child.getName();
			String content = ( child.getContent() != null && !"".equals(child.getContent()) ?  child.getContent() : null );

			if ( name.equals("Checks")) {
				return parseValidations(parent, child);
			}

			if ( name.equals("Check")) {
				ct = new CheckTag(myNavascript);
				vt.addCheck(ct);
				parseCheckAttributes(ct, child);
			}

		}

		return vt;

	}

	private FinallyTag parseFinally(NS3Compatible parent, XMLElement xe) throws Exception {
		FinallyTag ft = new FinallyTag(myNavascript);
		ft.addParent(parent);

		Vector<XMLElement> children = xe.getChildren();

		for ( XMLElement child : children ) {

			String name = child.getName();


			if ( name.equals("TopLevelStatement")) {
				List<NS3Compatible> innerBodyElements = parseInnerBody(ft, child);
				for ( NS3Compatible ib : innerBodyElements ) {
					addChildTag(ft, ib);
				}
			}

		}
		return ft;
	}

	private MethodsTag parseMethods(NS3Compatible parent, XMLElement xe) throws Exception {

		MethodsTag mt = new MethodsTag(myNavascript);
		mt.addParent(parent);

		Vector<XMLElement> children = xe.getChildren();

		for ( XMLElement child : children ) {

			String name = child.getName();

			if ( name.equals("DefinedMethod")) {

				for ( XMLElement c : child.getChildren() ) {

					String cn = c.getName();
					String content = ( c.getContent() != null && !"".equals(c.getContent()) ?  c.getContent() : null );

					if ( cn.equals("ScriptIdentifier")) {
						MethodTag m = new MethodTag(myNavascript);
						m.setScriptName(content);
						mt.addMethod(m);
					}

				}
			}
		}

		return mt;

	}

	private void addDefine(DefineTag dt) {
		if ( myDefines == null ) {
			myDefines = new DefinesTag(myNavascript);
			myNavascript.addDefines(myDefines);
		}
		myDefines.addDefine(dt);
	}

	private DefineTag parseDefine(NS3Compatible parent, XMLElement xe) {
		DefineTag dt = new DefineTag(myNavascript);
		dt.addParent(parent);

		Vector<XMLElement> children = xe.getChildren();

		for ( XMLElement child : children ) {

			String name = child.getName();
			String content = ( child.getContent() != null && !"".equals(child.getContent()) ?  child.getContent() : null );

			if ( name.equals("Identifier") ) {
				dt.setName(content);
			}

			if ( name.equals("Expression") ) {
				ExpressionFragment ef = new ExpressionFragment();
				consumeContent(ef, child);
				dt.setExpression(ef.consumedFragment());
			}
		}

		return dt;
	}


	private void addChildTag(NS3Compatible parent, NS3Compatible child) {

		child.addParent(parent);

		if ( child instanceof MethodsTag ) {
			if ( parent instanceof NavascriptTag) {
				((NavascriptTag) parent).addMethods((MethodsTag) child);
			} else {
				logger.error("Cannot add {} under {} tag", child.getTagName(), parent.getTagName());
			}	
		}

		if ( child instanceof FinallyTag ) {
			if ( parent instanceof NavascriptTag) {
				((NavascriptTag) parent).addFinally((FinallyTag) child);
			} else {
				logger.error("Cannot add {} under {} tag", child.getTagName(), parent.getTagName());
			}
		}

		if ( child instanceof BlockTag ) {
			if ( parent instanceof NavascriptTag) {
				((NavascriptTag) parent).addBlock((BlockTag) child);
			} else if ( parent instanceof MessageTag) {
				((MessageTag) parent).addBlock((BlockTag) child);
			} else if ( parent instanceof MapTag) {
				((MapTag) parent).addBlock((BlockTag) child);
			} else if ( parent instanceof BlockTag) {
				((BlockTag) parent).addBlock((BlockTag) child);
			} else if ( parent instanceof FinallyTag) {
				((FinallyTag) parent).add((BlockTag) child);
			} else if ( parent instanceof SynchronizedTag) {
				((SynchronizedTag) parent).add((BlockTag) child);
			} else {
				logger.error("Cannot add {} under {} tag", child.getTagName(), parent.getTagName());
			}
		}

		if ( child instanceof DebugTag ) {
			if ( parent instanceof NavascriptTag) {
				((NavascriptTag) parent).addDebug((DebugTag) child);
			} else if ( parent instanceof MessageTag) {
				((MessageTag) parent).addDebug((DebugTag) child);
			} else if ( parent instanceof MapTag) {
				((MapTag) parent).addDebug((DebugTag) child);
			} else if ( parent instanceof BlockTag) {
				((BlockTag) parent).addDebug((DebugTag) child);
			} else if ( parent instanceof FinallyTag) {
				((FinallyTag) parent).add((DebugTag) child);
			} else if ( parent instanceof SynchronizedTag) {
				((SynchronizedTag) parent).add((DebugTag) child);
			} else {
				logger.error("Cannot add {} under {} tag", child.getTagName(), parent.getTagName());
			}
		}

		if ( child instanceof SynchronizedTag ) {
			if ( parent instanceof NavascriptTag) {
				((NavascriptTag) parent).addSynchronized((SynchronizedTag) child);
			} else if ( parent instanceof MessageTag) {
				((MessageTag) parent).addSynchronized((SynchronizedTag) child);
			} else if ( parent instanceof MapTag) {
				((MapTag) parent).addSynchronized((SynchronizedTag) child);
			} else if ( parent instanceof BlockTag) {
				((BlockTag) parent).add((SynchronizedTag) child);
			} else if ( parent instanceof FinallyTag) {
				((FinallyTag) parent).add((SynchronizedTag) child);
			} else {
				logger.error("Cannot add {} under {} tag", child.getTagName(), parent.getTagName());
			}
		}


		if ( child instanceof ParamTag ) {
			if ( parent instanceof NavascriptTag) {
				((NavascriptTag) parent).addParam((ParamTag) child);
			} else if ( parent instanceof MessageTag) {
				((MessageTag) parent).addParam((ParamTag) child);
			} else if ( parent instanceof MapTag) {
				((MapTag) parent).addParam((ParamTag) child);
			} else if ( parent instanceof BlockTag) {
				((BlockTag) parent).add((ParamTag) child);
			} else if ( parent instanceof FinallyTag) {
				((FinallyTag) parent).add((ParamTag) child);
			} else if ( parent instanceof SynchronizedTag) {
				((SynchronizedTag) parent).add((ParamTag) child);
			} else {
				logger.error("Cannot add {} under {} tag", child.getTagName(), parent.getTagName());
			}
		}

		if ( child instanceof PropertyTag ) {
			if ( parent instanceof MessageTag) {
				((MessageTag) parent).addProperty((PropertyTag) child);
			} else if ( parent instanceof MapTag) {
				((MapTag) parent).addProperty((PropertyTag) child);
			} else if ( parent instanceof BlockTag) {
				((BlockTag) parent).add((PropertyTag) child);
			} else {
				logger.error("Cannot add {} under {} tag", child.getTagName(), parent.getTagName());
			}
		}

		if ( child instanceof FieldTag ) {
			if ( parent instanceof MapTag) {
				((MapTag) parent).addField((FieldTag) child);
			} else if ( parent instanceof MessageTag) {
				((MessageTag) parent).addField((FieldTag) child);
			} else if ( parent instanceof BlockTag) {
				((BlockTag) parent).add((FieldTag) child);
			} else {
				logger.error("Cannot add {} under {} tag", child.getTagName(), parent.getTagName());
			}
		}

		if ( child instanceof MessageTag ) {
			if ( parent instanceof NavascriptTag) {
				((NavascriptTag) parent).addMessage((MessageTag) child);
			} else if ( parent instanceof MessageTag) {
				((MessageTag) parent).addMessage((MessageTag) child);
			} else if ( parent instanceof MapTag) {
				((MapTag) parent).addMessage((MessageTag) child);
			} else if ( parent instanceof BlockTag) {
				((BlockTag) parent).add((MessageTag) child);
			} else if ( parent instanceof FinallyTag) {
				((FinallyTag) parent).add((MessageTag) child);
			} else if ( parent instanceof SynchronizedTag) {
				((SynchronizedTag) parent).add((MessageTag) child);
			} else {
				logger.error("Cannot add {} under {} tag", child.getTagName(), parent.getTagName());
			}
		}
		
		if ( child instanceof LoopTag ) {
			if ( parent instanceof NavascriptTag) {
				((NavascriptTag) parent).addLoop((LoopTag) child);
			} else if ( parent instanceof MessageTag) {
				((MessageTag) parent).addLoop((LoopTag) child);
			} else if ( parent instanceof MapTag) {
				((MapTag) parent).addLoop((LoopTag) child);
			} else if ( parent instanceof BlockTag) {
				((BlockTag) parent).add((LoopTag) child);
			} else if ( parent instanceof FinallyTag) {
				((FinallyTag) parent).add((LoopTag) child);
			} else if ( parent instanceof SynchronizedTag) {
				((SynchronizedTag) parent).add((LoopTag) child);
			} else {
				logger.error("Cannot add {} under {} tag", child.getTagName(), parent.getTagName());
			}
		}

		if ( child instanceof MapTag ) {
			if ( parent instanceof NavascriptTag) {
				((NavascriptTag) parent).addMap((MapTag) child);
			} else if ( parent instanceof MessageTag) {
				((MessageTag) parent).addMap((MapTag) child);
			} else if ( parent instanceof MapTag) {
				((MapTag) parent).addMap((MapTag) child);
			} else if ( parent instanceof BlockTag) {
				((BlockTag) parent).add((MapTag) child);
			} else if ( parent instanceof FinallyTag) {
				((FinallyTag) parent).add((MapTag) child);
			} else if ( parent instanceof SynchronizedTag) {
				((SynchronizedTag) parent).add((MapTag) child);
			} else {
				logger.error("Cannot add {} under {} tag", child.getTagName(), parent.getTagName());
			}
		}

		if ( child instanceof IncludeTag ) {
			if ( parent instanceof NavascriptTag) {
				((NavascriptTag) parent).addInclude((IncludeTag) child);
			} else if ( parent instanceof MessageTag) {
				((MessageTag) parent).addInclude((IncludeTag) child);
			} else if ( parent instanceof MapTag) {
				((MapTag) parent).addInclude((IncludeTag) child);
			} else if ( parent instanceof BlockTag) {
				((BlockTag) parent).add((IncludeTag) child);
			} else if ( parent instanceof FinallyTag) {
				((FinallyTag) parent).add((IncludeTag) child);
			} else if ( parent instanceof SynchronizedTag) {
				((SynchronizedTag) parent).add((IncludeTag) child);
			} else {
				logger.error("Cannot add {} under {} tag", child.getTagName(), parent.getTagName());
			}
		}

		if ( child instanceof BreakTag ) {
			if ( parent instanceof NavascriptTag) {
				((NavascriptTag) parent).addBreak((BreakTag) child);
			} else if ( parent instanceof MessageTag) {
				((MessageTag) parent).addBreak((BreakTag) child);
			} else if ( parent instanceof MapTag) {
				((MapTag) parent).addBreak((BreakTag) child);
			} else if ( parent instanceof BlockTag) {
				((BlockTag) parent).add((BreakTag) child);
			} else if ( parent instanceof FinallyTag) {
				((FinallyTag) parent).add((BreakTag) child);
			} else if ( parent instanceof SynchronizedTag) {
				((SynchronizedTag) parent).add((BreakTag) child);
			} else {
				logger.error("Cannot add {} under {} tag", child.getTagName(), parent.getTagName());
			}
		}

	}

	private void parseXML(NS3Compatible parent, XMLElement xe, int level) throws Exception {

		if ( xe.getAttribute("PROCESSED") != null ) {
			return;
		}

		String name = xe.getName();
		String content = ( xe.getContent() != null && !"".equals(xe.getContent()) ?  xe.getContent() : null );

		if ( name.equals("Validations")) {
			ValidationsTag vt = parseValidations(parent, xe);
			myNavascript.addValidations(vt);
		} else if ( name.equals("Var") ) {
			ParamTag pt = parseVar(parent, xe);
			addChildTag(parent, pt);
		} else if ( name.equals("Message") ) {
			MessageTag mt = parseMessage(parent, xe);
			addChildTag(parent, mt);
		} else if ( name.equals("ConditionalEmptyMessage")) {
			BlockTag mt = parseConditionalBlock(parent, xe, true);
			addChildTag(parent, mt);
		} else if (name.equals("Loop")) {
			LoopTag mt = parseLoop(parent, xe);
			addChildTag(parent, mt);
		} else if ( name.equals("Synchronized")) {
			SynchronizedTag st = parseSynchronizedBlock(parent, xe);
			addChildTag(parent, st);
		} else if ( name.equals("Map") ) {
			MapTag mt = parseMap(parent, xe);
			addChildTag(parent, mt);
		} else if ( name.equals("Break") ) {
			BreakTag mt = parseBreak(parent, xe);
			addChildTag(parent, mt);
		} else if ( name.equals("Include") ) {
			IncludeTag it = parseInclude(parent, xe);
			addChildTag(parent, it);
		} else if ( name.equals("Define") ) {
			DefineTag dt = parseDefine(parent, xe);
			addDefine(dt);
		} else if ( name.equals("Print") ) {
			DebugTag dt = parsePrint(parent, xe);
			addChildTag(parent, dt);
		} else if ( name.equals("Log")) {
			LogTag dt = parseLog(parent, xe);
			addChildTag(parent, dt);
		} else if ( name.equals("Finally")) {
			FinallyTag ft = parseFinally(parent, xe);
			addChildTag(parent, ft);
		} else if ( name.equals("Methods")) {
			MethodsTag mt = parseMethods(parent, xe);
			addChildTag(parent, mt);
		} else {
			Vector<XMLElement> children = xe.getChildren();
			for ( XMLElement x : children ) {
				if ( x.getAttribute("PROCESSED") == null ) {
					parseXML(parent, x, level + 1);
				}
			}
		}

	}

	public void initialize() throws UnsupportedEncodingException {
		myNavascript = new NavascriptTag();
		out = new OutputStreamWriter(System.out, "UTF-8");
	}

	public String read(String input) throws Exception
	{
		if (input.startsWith("{") && input.endsWith("}"))
		{
			return input.substring(1, input.length() - 1);
		}
		else
		{
			byte buffer[] = new byte[(int) new java.io.File(input).length()];
			java.io.FileInputStream stream = new java.io.FileInputStream(input);
			stream.read(buffer);
			stream.close();
			String content = new String(buffer, System.getProperty("file.encoding"));
			return content.length() > 0 && content.charAt(0) == '\uFEFF'
					? content.substring(1)
							: content;
		}
	}

	@Override
	public void reset(CharSequence string)
	{
		writeOutput("<?xml version=\"1.0\" encoding=\"UTF-8\"?" + ">");
		input = string;
		delayedTag = null;
		hasChildElement = false;
		depth = 0;
	}

	public void startNonterminal(String name, int begin)
	{
		if (delayedTag != null)
		{
			StringBuffer sb = new StringBuffer();
			sb.append("<");
			sb.append(delayedTag);
			sb.append(">");
			writeOutput(sb.toString());
		}
		delayedTag = name;
		hasChildElement = false;
		++depth;
	}


	public void endNonterminal(String name, int end)
	{
		--depth;
		if (delayedTag != null)
		{
			delayedTag = null;
			StringBuffer sb = new StringBuffer();
			sb.append("<");
			sb.append(name);
			sb.append("/>");
			writeOutput(sb.toString());
		}
		else
		{
			StringBuffer sb = new StringBuffer();
			sb.append("</");
			sb.append(name);
			sb.append(">");
			writeOutput(sb.toString());
		}
		hasChildElement = true;
	}

	@Override
	public void terminal(String name, int begin, int end)
	{				
		if (name.charAt(0) == '\'')
		{
			name = "TOKEN";
		}		
		startNonterminal(name, begin);
		characters(begin, end);
		endNonterminal(name, end);
	}


	@Override
	public void whitespace(int begin, int end)
	{
		characters(begin, end);
	}

	private void characters(int begin, int end)
	{
		if (begin < end)
		{
			boolean ignoreComment = false;
			if (delayedTag != null)
			{
				StringBuffer sb = new StringBuffer();
				sb.append("<");
				sb.append(delayedTag);
				sb.append(">");
				// ignore comment in characters between StringConstant and StringLiteral tags
				ignoreComment = "StringConstant".equals(delayedTag) || "StringLiteral".equals(delayedTag);
				delayedTag = null;
				writeOutput(sb.toString());
			}
			
			writeOutput(input.subSequence(begin, end)
					.toString()
					.replace("&", "&amp;")
					.replace("<", "&lt;")
					.replace(">", "&gt;"), ignoreComment);
		}
	}

	public String formatComment(String content) {

		StringBuffer result = new StringBuffer();

		String strip = content.replaceAll("//", "").replaceAll("\\/\\*", "").replaceAll("\\*\\/", "");

		result.append("<!--");
		result.append(strip);
		result.append("-->");

		return result.toString();
	}

	public void writeOutput(String content) {
		writeOutput(content, false);
	}

	public void writeOutput(String content, boolean ignoreComment)
	{		
		if ( !ignoreComment && ( content.indexOf("//") != -1 || content.indexOf("/*") != -1 ) ) {
			xmlString.write(formatComment(content));
		} else {
			xmlString.write(content);
		}

	}


}
