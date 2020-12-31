package com.dexels.navajo.compiler.navascript;

import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;
import java.util.Vector;

import com.dexels.navajo.compiler.navascript.parser.EventHandler;
import com.dexels.navajo.compiler.navascript.parser.navascript;
import com.dexels.navajo.document.nanoimpl.CaseSensitiveXMLElement;
import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.document.navascript.tags.BreakTag;
import com.dexels.navajo.document.navascript.tags.ExpressionTag;
import com.dexels.navajo.document.navascript.tags.FieldTag;
import com.dexels.navajo.document.navascript.tags.IncludeTag;
import com.dexels.navajo.document.navascript.tags.MapTag;
import com.dexels.navajo.document.navascript.tags.MessageTag;
import com.dexels.navajo.document.navascript.tags.NS3Compatible;
import com.dexels.navajo.document.navascript.tags.NavascriptTag;
import com.dexels.navajo.document.navascript.tags.ParamTag;
import com.dexels.navajo.document.navascript.tags.PropertyTag;

public class NS3ToNSXML implements EventHandler {

	enum ParsedToken {
		UNKNOWN, TOKEN, Var, VarName, PrimaryExpression, Operator, IntegerLiteral, TmlIdentifier, Conditional;
	}

	enum ParseState {
		START,
		START_VAR, VAR_NAME,
		START_PROPERTY, 
		START_MESSAGE, 
		START_CONDITION, CONDITION_PARSE,
		START_EXPRESSION, EXPRESSION_PARSE, EXPRESSION_START_CONDITION, EXPRESSION_CONDITION_PARSE
	}

	private CharSequence input;
	private String delayedTag;
	public Writer out;
	private boolean indent;
	private boolean hasChildElement;
	private int depth;
	private NavascriptTag navascript;
	private ParsedToken currentToken;
	private ParseState parseState;
	private NS3Compatible currentTag;
	private boolean CONSUME_TOKEN = false;

	public StringWriter xmlString = new StringWriter();

	public static void main(String [] args) throws Exception {
		NS3ToNSXML t = new NS3ToNSXML();

		String fileContent = read("/Users/arjenschoneveld/Downloads/aap.ns");

		t.initialize();
		t.parseNavascript(fileContent);

	}

	public void parseNavascript(String fileContent) throws Exception {

		navascript parser = new navascript(fileContent, this);
		input = fileContent;
		parser.parse_Navascript();

		String result = xmlString.toString();

		XMLElement xe = new CaseSensitiveXMLElement(true);
		xe.parseString(result);

		parseXML(navascript, xe, 0);

		navascript.write(System.err);
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

		} else {
			fragment.consumeToken(content);
		}
		Vector<XMLElement> children = xe.getChildren();
		for ( XMLElement x : children ) {
			consumeContent(fragment, x);
		}
	}

	private void parseConditional(NS3Compatible parent, XMLElement xe) {

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

			if ( name.equals("Expression") ) {
				ExpressionFragment ef = new ExpressionFragment();
				consumeContent(ef, child);
				ExpressionTag et = new ExpressionTag(navascript);
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

	private ParamTag parseVar(NS3Compatible parent, XMLElement currentXML) {
		// Conditional -> VarName -> "=" OR ":" -> ConditionalExpressions

		Vector<XMLElement> children = currentXML.getChildren();

		ParamTag paramTag = new ParamTag(navascript);

		currentXML.setAttribute("PROCESSED", "true");

		for ( XMLElement child : children ) {

			String name = child.getName();

			String content = ( child.getContent() != null && !"".equals(child.getContent()) ?  child.getContent() : null );

			if ( name.equals("VarName") ) {
				paramTag.setName(content);
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

		System.err.println("=================== parseExpression: " + currentXML.getName());

		ExpressionFragment ef = new ExpressionFragment();

		currentXML.setAttribute("PROCESSED", "true");

		Vector<XMLElement> children = currentXML.getChildren();

		for ( XMLElement child : children ) {

			String name = child.getName();
			System.err.println("In parseExpression: " + name);

			if ( name.equals("Expression")) {
				System.err.println("In parseExpression. Found Expression");
				consumeContent(ef, child);
			} 

		}

		System.err.println("=============================== DONE");

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

			if ( name.equals("PropertyDescription")) {
				boolean isLiteral = isLiteral(child);
				if ( isLiteral ) {
					p.setDescription(parseStringConstant(child));
				}
			}

			parsePropertyArguments(p, child);
		}
	}

	private PropertyTag parseProperty(XMLElement currentXML) {

		currentXML.setAttribute("PROCESSED", "true");

		PropertyTag pt = new PropertyTag(navascript);

		Vector<XMLElement> children = currentXML.getChildren();

		for ( XMLElement child : children ) {

			String name = child.getName();
			String content = ( child.getContent() != null && !"".equals(child.getContent()) ?  child.getContent() : null );

			if ( name.equals("Conditional") ) {
				ConditionFragment currentFragment = new ConditionFragment();
				consumeContent(currentFragment, child);
				pt.setCondition(currentFragment.consumedFragment());
				System.err.println("FOUND PROPERTY CONDITIONAL: " + currentFragment.consumedFragment());
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
				ExpressionTag et = new ExpressionTag(navascript);
				et.setConstant(c);
				pt.addExpression(et);
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
				System.err.println("Found MethodName: " + content);
				parent.setFieldName(content);
			}

			if  ( name.equals("KeyValueArguments") ) {
				parseMapOrMethodArguments(parent, child);
			} 
		}

	}
	
	private void parseAdapterField(FieldTag parent, XMLElement currentXML) {

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
				System.err.println("Found FieldName: " + content);
				parent.setFieldName(content);
			}

			if  ( name.equals("ConditionalExpressions") ) {
				List<ExpressionTag> expressions = parseConditionalExpressions(parent, child);
				for ( ExpressionTag et : expressions ) {
					parent.addExpression(et);
				}
			} 
			
			if  ( name.equals("StringConstant") ) {
				String c = content.replaceAll("\"", "");
				System.err.println("FOUND StringConstant: " + c);
				ExpressionTag et = new ExpressionTag(navascript);
				et.setConstant(c);
				parent.addExpression(et);
			} 
		}

	}

	private FieldTag parseMethodOrSetter(MapTag parent, XMLElement currentXML) {
		// if ( name.equals("AdapterMethod" ) ) {

		System.err.println("Found AdapterMethod");
		//MethodName

		FieldTag ft = new FieldTag(parent);
		ft.setParent(parent);

		Vector<XMLElement> children = currentXML.getChildren();

		for ( XMLElement child : children ) {

			String name = child.getName();

			if ( name.equals("Conditional") ) {
				ConditionFragment currentFragment = new ConditionFragment();
				consumeContent(currentFragment, child);
				ft.setCondition(currentFragment.consumedFragment());
				System.err.println("parseAdapterMethod HAS CONDITIONAL: " + currentFragment.consumedFragment());
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

		System.err.println("In parseInnerBody: " + parent);

		List<NS3Compatible> bodyElts = new ArrayList<>();

		Vector<XMLElement> children = currentXML.getChildren();

		for ( XMLElement child : children ) {

			String name = child.getName();
			String content = ( child.getContent() != null && !"".equals(child.getContent()) ?  child.getContent() : null );

			if ( name.equals("Property")) {
				System.err.println("ParseInnerBody. Found Property");
				PropertyTag pt = parseProperty(child);
				bodyElts.add(pt);
			}

			if ( name.equals("Var")) {
				System.err.println("ParseInnerBody. Found Var");
				ParamTag pt = parseVar(parent, child);
				bodyElts.add(pt);
			}

			if ( name.equals("Break") ) {
				System.err.println("ParseInnerBody. Found Break");
				BreakTag mt = parseBreak(parent, child);
				bodyElts.add(mt);
			}
			
			if ( name.equals("Include") ) {
				System.err.println("ParseInnerBody. Found Include");
				IncludeTag mt = parseInclude(parent, child);
				bodyElts.add(mt);
			}

			if ( name.equals("Message")) {
				System.err.println("ParseInnerBody. Found message");
				MessageTag pt = parseMessage(parent, child);
				bodyElts.add(pt);
			}

			if ( name.equals("TopLevelStatement")) {
				System.err.println("ParseInnerBody. Found TopLevelStatement");
				bodyElts.addAll(parseInnerBody(parent, child));
			}

			if (name.equals("MethodOrSetter")) {
				System.err.println("Found MethodOrSetter");
				FieldTag ft = parseMethodOrSetter((MapTag) parent, child);
				bodyElts.add(ft);
			} 

		}

		return bodyElts;
	}

	private void parseMapOrMethodArguments(NS3Compatible parent, XMLElement currentXML) {

		System.err.println("In parseMapOrMethodArguments...");

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

		MapTag mapTag = new MapTag(navascript);


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

			if ( name.equals("Include")) {
				IncludeTag it = parseInclude(parent, child);
				mapTag.addInclude(it);
			} 

			if  ( name.equals("KeyValueArguments") ) {
				parseMapOrMethodArguments(mapTag, child);
			} 

			if ( name.equals("InnerBody")) {
				if (name.equals("InnerBody")) {
					List<NS3Compatible> innerBodyElements = parseInnerBody(mapTag, child);
					for ( NS3Compatible ib : innerBodyElements ) {
						addChildTag(mapTag, ib);
					}
				}
			}
		}

		return mapTag;

	}

	private MessageTag parseMessage(NS3Compatible parent, XMLElement currentXML) throws Exception {

		currentXML.setAttribute("PROCESSED", "true");

		Vector<XMLElement> children = currentXML.getChildren();

		MessageTag msgTag = new MessageTag(navascript);

		System.err.println("In parseMessage. With children: " + children.size());

		for ( XMLElement child : children ) {
			String name = child.getName();
			System.err.println("In parseMessage. Processing child: " + name);

			String content = ( child.getContent() != null && !"".equals(child.getContent()) ?  child.getContent() : null );

			if ( name.equals("Conditional") ) {
				ConditionFragment currentFragment = new ConditionFragment();
				consumeContent(currentFragment, child);
				msgTag.setCondition(currentFragment.consumedFragment());
			}

			if ( name.equals("MsgIdentifier")) {
				msgTag.setName(content);
			}

			if (name.equals("InnerBody")) {
				List<NS3Compatible> innerBodyElements = parseInnerBody(msgTag, child);
				for ( NS3Compatible ib : innerBodyElements ) {
					addChildTag(msgTag, ib);
				}
			}

		}
		return msgTag;


	}

	private IncludeTag parseInclude(NS3Compatible parent, XMLElement currentXML) throws Exception {

		currentXML.setAttribute("PROCESSED", "true");

		IncludeTag it = new IncludeTag(navascript);

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

		BreakTag bt = new BreakTag(navascript);

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

	private void addChildTag(NS3Compatible parent, NS3Compatible child) {

		if ( child instanceof ParamTag ) {
			if ( parent instanceof NavascriptTag) {
				((NavascriptTag) parent).addParam((ParamTag) child);
				System.err.println("Adding param tag to navascript tag");
			}
			if ( parent instanceof MessageTag) {
				((MessageTag) parent).addParam((ParamTag) child);
				System.err.println("Adding param tag to navascript tag");
			}
		}

		if ( child instanceof PropertyTag ) {
			if ( parent instanceof MessageTag) {
				((MessageTag) parent).addProperty((PropertyTag) child);
			}
		}

		if ( child instanceof FieldTag ) {
			if ( parent instanceof MapTag) {
				((MapTag) parent).addProperty((FieldTag) child);
			}
		}

		if ( child instanceof MessageTag ) {
			if ( parent instanceof NavascriptTag) {
				((NavascriptTag) parent).addMessage((MessageTag) child);
			}
			if ( parent instanceof MessageTag) {
				((MessageTag) parent).addMessage((MessageTag) child);
			}
			if ( parent instanceof MapTag) {
				((MapTag) parent).addMessage((MessageTag) child);
			}
		}

		if ( child instanceof MapTag ) {
			if ( parent instanceof NavascriptTag) {
				((NavascriptTag) parent).addMap((MapTag) child);
			}
			if ( parent instanceof MessageTag) {
				((MessageTag) parent).addMap((MapTag) child);
			}
			if ( parent instanceof MapTag) {
				((MapTag) parent).addMap((MapTag) child);
			}
		}

		if ( child instanceof IncludeTag ) {
			if ( parent instanceof NavascriptTag) {
				((NavascriptTag) parent).addInclude((IncludeTag) child);
			}
			if ( parent instanceof MessageTag) {
				((MessageTag) parent).addInclude((IncludeTag) child);
			}
			if ( parent instanceof MapTag) {
				((MapTag) parent).addInclude((IncludeTag) child);
			}
		}

		if ( child instanceof BreakTag ) {
			if ( parent instanceof NavascriptTag) {
				((NavascriptTag) parent).addBreak((BreakTag) child);
			}
			if ( parent instanceof MessageTag) {
				((MessageTag) parent).addBreak((BreakTag) child);
			}
			if ( parent instanceof MapTag) {
				((MapTag) parent).addBreak((BreakTag) child);
			}
		}

	}

	private void parseXML(NS3Compatible parent, XMLElement xe, int level) throws Exception {

		if ( xe.getAttribute("PROCESSED") != null ) {
			return;
		}

		String name = xe.getName();
		String content = ( xe.getContent() != null && !"".equals(xe.getContent()) ?  xe.getContent() : null );

		if ( name.equals("Var") ) {
			System.err.println(level + ": Processing Var...");
			ParamTag pt = parseVar(parent, xe);
			addChildTag(parent, pt);
		} else if ( name.equals("Message") ) {
			System.err.println(level + ": Processing message...");
			MessageTag mt = parseMessage(parent, xe);
			addChildTag(parent, mt);
		} else if ( name.equals("Map") ) {
			System.err.println(level + ": Processing map...");
			MapTag mt = parseMap(parent, xe);
			addChildTag(parent, mt);
		} else if ( name.equals("Break") ) {
			System.err.println(level + ": Processing break...");
			BreakTag mt = parseBreak(parent, xe);
			addChildTag(parent, mt);
		} else if ( name.equals("Include") ) {
			IncludeTag it = parseInclude(parent, xe);
			addChildTag(parent, it);
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
		navascript = new NavascriptTag();
		out = new OutputStreamWriter(System.out, "UTF-8");
	}

	private static String read(String input) throws Exception
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
			if (delayedTag != null)
			{
				StringBuffer sb = new StringBuffer();
				sb.append("<");
				sb.append(delayedTag);
				sb.append(">");
				delayedTag = null;
				writeOutput(sb.toString());
			}
			writeOutput(input.subSequence(begin, end)
					.toString()
					.replace("&", "&amp;")
					.replace("<", "&lt;")
					.replace(">", "&gt;"));
		}
	}

	private ParsedToken transformToToken(String content) {
		if ( content.equals("[Var]"))  {
			return ParsedToken.Var;
		} else if ( content.equals("[TOKEN]")) {
			return ParsedToken.TOKEN;
		} else if ( content.equals("[VarName]")) {
			return ParsedToken.VarName;
		} else if ( content.equals("[PrimaryExpression]")) {
			return ParsedToken.PrimaryExpression;
		} else if ( content.equals("[TmlIdentifier]")) {
			return ParsedToken.TmlIdentifier;
		} else if ( content.equals("[IntegerLiteral]")) {
			return ParsedToken.IntegerLiteral;
		} else if ( content.equals("[Conditional]")) {
			return ParsedToken.Conditional;
		} else {
			return ParsedToken.UNKNOWN;
		}
	}

	public void writeOutput(String content)
	{

		xmlString.write(content);

		//System.err.println("-> " + content + ", current state: " +  parseState );

		/*currentToken = transformToToken(content);

		if ( currentToken == ParsedToken.TOKEN || 
				currentToken == ParsedToken.TmlIdentifier || 
				currentToken == ParsedToken.IntegerLiteral
				) 
		{
			CONSUME_TOKEN = true;
			return;
		}

		if ( currentToken == ParsedToken.VarName) {
			parseState = ParseState.VAR_NAME;
			return;
		}

		if ( currentToken == ParsedToken.Conditional ) {
			System.err.println("Encountered conditional...");
			if ( parseState == ParseState.EXPRESSION_PARSE ) {
				parseState = ParseState.EXPRESSION_START_CONDITION;
				ConditionalExpressionFragment cef = new ConditionalExpressionFragment();
				currentFragment.add(cef);
				// Push ConditionFragment too
				ConditionFragment cf = new ConditionFragment();
				currentFragment.add(cf);
			} else {
				parseState = ParseState.START_CONDITION;
				ConditionFragment cf = new ConditionFragment();
				currentFragment.add(cf);
			}

			return;
		}

		if ( ( parseState == ParseState.START_CONDITION || parseState == ParseState.EXPRESSION_START_CONDITION )  && content.equals("if")) {
			parseState = ( parseState ==  ParseState.START_CONDITION ? ParseState.CONDITION_PARSE : ParseState.EXPRESSION_CONDITION_PARSE );
		} else if ( ( parseState == ParseState.CONDITION_PARSE  || parseState == ParseState.EXPRESSION_CONDITION_PARSE ) && content.equals("then")) {
			ConditionFragment cf = (ConditionFragment) currentFragment.lastElement();
			cf.finalize();
			if ( parseState == ParseState.EXPRESSION_CONDITION_PARSE ) {
				parseState = ParseState.EXPRESSION_PARSE;
				currentFragment.pop(); // ConditionFragment is consumed
				ExpressionFragment ef = (ExpressionFragment) currentFragment.lastElement();
				//ef.addConditionFragment(cf);
				System.err.println("condition: " + cf.consumedFragment() + ", expression: " + ef.consumedFragment());
			}
			CONSUME_TOKEN = false;
		} else if ( parseState == ParseState.VAR_NAME ) {
			ParamFragment pf = new ParamFragment();
			pf.setName(content);
			// Check for preceding ConditionFragment.
			if ( currentFragment.lastElement()  instanceof ConditionFragment ) {
				ConditionFragment cf = (ConditionFragment) currentFragment.lastElement();
				pf.setCondition(cf.consumedFragment());
				currentFragment.pop(); // ConditionFragment is consumed.
			}
			parseState = ParseState.START_EXPRESSION;
			currentFragment.add(pf);
		} else if ( parseState == ParseState.EXPRESSION_PARSE && content.equals(";") ) {
			ExpressionFragment ef = (ExpressionFragment) currentFragment.lastElement();
			parseState = ParseState.START;
			currentFragment.pop();
			NavascriptFragment nf = currentFragment.lastElement();
			System.err.println("NF is " + nf + ", ef is " + ef.consumedFragment());
			if ( nf instanceof ParamFragment ) {
				System.err.println("Var " + ((ParamFragment) nf).getName() + " = " + ef.consumedFragment());
				nf.finalize();
			}
		} else if ( parseState == ParseState.START_EXPRESSION && content.equals("=")) {
			System.err.println("Is NORMAL EXPRESSION");
			ExpressionFragment ef = new ExpressionFragment();
			currentFragment.add(ef);
			parseState = ParseState.EXPRESSION_PARSE;
			// Two Options: single expression OR conditional expression list
		} else if ( currentFragment.size() > 0 && CONSUME_TOKEN ) {
			currentFragment.lastElement().consumeToken(content);
			CONSUME_TOKEN = false;
		}*/

	}

	@Override
	public void setInput(CharSequence input) {
		this.input = input;
	}
}
