package com.dexels.navajo.rhino;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.jaxpimpl.xml.XMLutils;
import com.dexels.navajo.document.nanoimpl.CaseSensitiveXMLElement;
import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.document.nanoimpl.XMLParseException;
import com.dexels.navajo.mapping.compiler.meta.MapMetaData;
import com.dexels.navajo.mapping.compiler.meta.MissingParameterException;

public class BasicScriptCompiler implements ScriptCompiler {

	private static final String[] PROPERTY_ATTRIBUTES = new String[] { "type",
			"subtype", "direction", "description", "cardinality", "length",
			"value" };
	private static final String[] MESSAGE_ATTRIBUTES = new String[] { "type",
		"mode" };

	private long compileStart;
	private File includeBase = null;
	private String scriptName = null;

	
	private final static Logger logger = LoggerFactory
			.getLogger(BasicScriptCompiler.class);
	
	private static int fails = 0;
	private static int ignores = 0;
	private static int successes = 0;
	private static Map<String, List<String>> strangeTags = new HashMap<String, List<String>>();

	private final Map<String, String> defines = new HashMap<String, String>();

	private void compileTsl(String scriptName, Reader is, IndentWriter os)
			throws IOException {
		compileStart = System.currentTimeMillis();
		this.scriptName = scriptName;
		defines.clear();
		XMLElement x = new CaseSensitiveXMLElement();
		x.parseFromReader(is);
		process(x, os);

	}

	public void reportFunnyTag(String tag, String script) {
		List<String> s = strangeTags.get(tag);
		if (s == null) {
			s = new LinkedList<String>();
			strangeTags.put(tag, s);
		}
		s.add(script);
	}

	public File getIncludeBase() {
		return includeBase;
	}

	public void setIncludeBase(File includeBase) {
		this.includeBase = includeBase;
	}

	@Override
	public void compileTsl(String scriptName, InputStream is, OutputStream os,
			StringBuffer compilerErrors) throws IOException {
		Writer w = new OutputStreamWriter(os);

		w.write("// Starting javascript.\n");
		w.write("//" + new Date() + ".. and it is a beautiful day\n");
		w.flush();
		StringBuilder sb = new StringBuilder();
		IndentWriter indent = new IndentWriter(sb);
		compileTsl(scriptName, new InputStreamReader(is), indent);
		String result = postProcess(sb.toString());
		w.write(result);
		w.write("// End of compile.\n");
		w.flush();
		w.close();
	}

	private String postProcess(String sb) {

		for (Entry<String, String> def : defines.entrySet()) {
			String name = "#" + def.getKey();

			// hahaha
			String escaped = def.getValue().replaceAll("\\$", "\\$");
			escaped = escaped.trim();
			Pattern p = Pattern.compile(name);
			Matcher m = p.matcher(sb);
			sb = m.replaceAll(escaped);
		}
		return sb;
	}

	private void process(XMLElement current, IndentWriter os)
			throws IOException {
		String condition = current.getStringAttribute("condition");
		if (condition != null && !condition.equals("")) {
			startCondition(current, condition, os);
			return;
		}
		if (current.getName().equals("navascript")) {
			processNavaScript(current, os);
			return;
		}
		// TML is a subset, valid tml means valid tsl
		if (current.getName().equals("tsl") || current.getName().equals("tml")) {
			os.writeln("// -- Compiled code. Edit at your own peril.\n");
			String debug = current.getStringAttribute("debug");
			if (debug != null && debug.indexOf("request") != -1) {
				os.writeln("env.dumpRequest();\n");
			}
			for (XMLElement c : current.getChildren()) {
				process(c, os);
			}
			if (debug != null && debug.indexOf("response") != -1) {
				os.writeln("env.dumpResponse();\n");
			}

			long time = System.currentTimeMillis() - compileStart;
			os.writeln("// compiletime: " + time + "\n");
			return;
		}

		if (current.getName().equals("methods")) {
			for (XMLElement c : current.getChildren()) {
				process(c, os);
			}
			return;
		}

		if (current.getName().equals("method")) {
			processMethod(current, os);
			return;
		}

		if (current.getName().equals("defines")) {
			os.writeln("// start of defines: ");
			os.in();
			for (XMLElement c : current.getChildren()) {
				processDefine(c, os);
			}
			os.out();
			os.writeln("// end of defines");
			return;
		}

		if (current.getName().equals("param")) {
			if (Message.MSG_TYPE_ARRAY.equals(current
					.getStringAttribute("type"))) {
				processParamArrayMessage(current, os);
			} else {
				processExpressionContainer(
						"addParam",
						current,
						createAttributesFromElement(current,
								PROPERTY_ATTRIBUTES), os);
			}
			return;
		}

		if (current.getName().equals("field")) {
			processField(current, os);
			return;
		}
		if (current.getName().equals("message")) {
			processMessage(current, os);
			return;
		}

		if (current.getName().equals("finally")) {
			processFinally(current, os);
			return;
		}

		if (current.getName().equals("property")) {
			List<XMLElement> xe = current.getChildrenByTagName("map");
			if(xe.isEmpty()) {
				processExpressionContainer("addProperty", current,
						createAttributesFromElement(current, PROPERTY_ATTRIBUTES),
						os);
			} else {
				String name = current.getStringAttribute("name");
				processMapSelectionProperty(name,current, createAttributesFromElement(current, PROPERTY_ATTRIBUTES),os);
			}
			return;
		}

		if (current.getName().equals("include")) {
			os.writeln("// start of include: "
					+ current.getStringAttribute("script"));
			XMLElement incl = resolveInclude(current
					.getStringAttribute("script"));
			processInclude(incl, os);
			os.writeln("// end of include: "
					+ current.getStringAttribute("script"));
			return;
		}

		if (current.getName().equals("break")) {
			processBreak(current, os);
			return;
		}

		if (current.getName().equals("check")) {
			processCheck(current, os);
			return;
		}
		if (current.getName().equals("validations")) {
			processValidations(current, os);
			return;
		}

		if (current.getName().equals("comment")) {
			processComment(current, os);
			return;
		}
		if (current.getName().equals("debug")) {
			processDebug(current, os);
			return;
		}

		if (current.getName().equals("map")) {
			String mapClass = current.getStringAttribute("object");
			if (mapClass != null) {
				if (mapClass
						.equals("com.dexels.navajo.adapter.MultipleEmptyMap")) {
					processMultipleMap(current, os);
					return;
				} else {
					processToplevelMap(current, os);
					return;
				}
			} else if (current.getStringAttribute("ref") != null) {
				processReferencedMap(current, os);
				return;
			}
			throw new IllegalArgumentException("Bad map: " + current.toString());
		}

		reportFunnyTag(current.getName(), scriptName);
		throw new IllegalArgumentException("Unknown tag: " + current.getName());

	}

	private void processField(XMLElement current, IndentWriter os)
			throws IOException {
		boolean mapDetected = false;
		List<XMLElement> currentElements = current.getChildren();
		for (XMLElement xmlElement : currentElements) {
			if (xmlElement.getName().equals("map")) {
				mapDetected = true;
			}
		}
		if (mapDetected) {
			processMessageMapping(current, os);
		} else {
			processExpressionContainer("addField", current, null, os);
		}

	}

	private void processMessageMapping(XMLElement current, IndentWriter os)
			throws IOException {
		String refField = current.getStringAttribute("name");
		for (XMLElement e : current.getChildren()) {
			processMessageMapRef(e, refField, os);
		}
	}

	private void processMultipleMap(XMLElement current, IndentWriter os)
			throws IOException {
		for (XMLElement e : current.getChildren()) {
			processMultipleChild(e, os);
		}
	}

	private void processMultipleChild(XMLElement e, IndentWriter os)
			throws IOException {
		if (!e.getName().equals("field")
				|| !e.getStringAttribute("name").equals("emptyMaps")) {
			throw new RuntimeException("Strange multimap: " + e.toString());
		}
		int childSize = e.getChildren().size();
		if (childSize != 1) {
			throw new RuntimeException("Strange number of childen: "
					+ e.toString());
		}
		XMLElement currentRef = e.getFirstChild();
		String parameter = currentRef.getStringAttribute("ref");
		boolean isParam = parameter.startsWith("@")
				|| parameter.startsWith("/@");
		os.writeln("// For each message: (assuming message for now..)");
		String filterAttribute = currentRef.getStringAttribute("filter");
		String methodName = null;
		if (isParam) {
			methodName = "forEachParamMessage";
		} else {
			methodName = "forEachMessage";
		}
		if (filterAttribute == null) {
			os.writeln(methodName + "('" + parameter + "',null, function() {");
		} else {
			os.writeln(methodName + "('" + parameter + "',\"" + filterAttribute
					+ "\", function() {");
		}
		os.in();
		for (XMLElement pp : currentRef.getChildren()) {
			process(pp, os);
		}
		os.out();
		os.writeln("});");
	}

	private void processDefine(XMLElement c, IndentWriter os) {
		String name = c.getStringAttribute("name");
		defines.put(name, c.getContent().trim());
	}

	private void processNavaScript(XMLElement current, IndentWriter os) {
		StringWriter sw = new StringWriter();
		try {
			MapMetaData.getInstance().parse(current, scriptName, sw);
			StringReader sr = new StringReader(sw.toString());
			XMLElement xx = new CaseSensitiveXMLElement();
			xx.parseFromReader(sr);
			process(xx, os);
		} catch (MissingParameterException e) {
			e.printStackTrace();
			try {
				processCompileError(e.getMessage(),
						"MissingParameterException", e, os);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
			try {
				System.err
						.println("Debatable contruction: Generates an error js file. Should be rewritten, I think. Don't know what I was thinking");
				processCompileError(e.getMessage(), "IOException", e, os);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				System.err
						.println("Debatable contruction: Generates an error js file. Should be rewritten, I think. Don't know what I was thinking");
				processCompileError(e.getMessage(), "IOException", e, os);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

	}

	private void processDebug(XMLElement current, IndentWriter os)
			throws IOException {
		String value = current.getStringAttribute("value");
		value = value.replaceAll("\\n", " ");

		os.writeln("debug(evaluateNavajo(\"" + value + "\"));");
	}

	private void processComment(XMLElement current, IndentWriter os)
			throws IOException {
		String value = current.getStringAttribute("value");
		os.writeln("// comment: " + value);
	}

	private void processCompileError(String message, String type, Throwable t,
			IndentWriter os) throws IOException {

		os.writeln("// error: " + message);
		if (t != null) {
			// function addErrorMessage(message, code, body) {
			StringBuffer sb = new StringBuffer();
			writeStacktrace(t, sb);
			os.write(sb.toString());
			os.writeln("addErrorMessage(\"" + message + "\"," + "-1,\""
					+ t.getMessage() + "\");");
		}
	}

	private void writeStacktrace(Throwable t, StringBuffer b) {
		StackTraceElement[] o = t.getStackTrace();
		for (StackTraceElement stackTraceElement : o) {
			b.append("//\tat " + stackTraceElement.getClassName() + "."
					+ stackTraceElement.getMethodName() + "("
					+ stackTraceElement.getFileName() + ":"
					+ stackTraceElement.getLineNumber() + ")\n");
		}
		if (t.getCause() != null) {
			b.append("//Caused by: \n");
			writeStacktrace(t.getCause(), b);
		}
	}

	private void processValidations(XMLElement current, IndentWriter os)
			throws IOException {
		os.writeln("// ---- Validations");
		os.in();
		for (XMLElement c : current.getChildren()) {
			process(c, os);
		}
		os.out();
		os.writeln("breakOnConditionErrors();");
		os.writeln("// ---- End of Validations");

	}

	private void processCheck(XMLElement current, IndentWriter os)
			throws IOException {
		String conditionExpression = current.getContent().trim().replaceAll("\\n", " ").replaceAll("\n", "\\\\\n");
		String unesc = XMLutils.XMLUnescape(conditionExpression);
		int code = current.getIntAttribute("code");
		String description = current.getStringAttribute("description");

		os.writeln("if(!evaluateNavajo(\"" + unesc + "\")==true) {");
		os.in();
		os.writeln("appendConditionError(" + code + "," + description + ");");
		os.out();
		os.writeln("} else {");
		os.in();
		os.writeln("env.log(\"Condition passed!\");");
		os.out();
		os.writeln("}");

	}

	private void processBreak(XMLElement current, IndentWriter os)
			throws IOException {
		os.writeln("throwBreak();");
	}

	/**
	 * incl is the xml element of the top of the included message
	 * 
	 * @param incl
	 * @param os
	 * @throws IOException
	 */
	private void processInclude(XMLElement incl, IndentWriter os)
			throws IOException {
		process(incl, os);
	}

	private Map<String, String> createAttributesFromElement(XMLElement current,
			String[] propertyAttributes) {
		Map<String, String> attr = new HashMap<String, String>();
		for (String e : propertyAttributes) {
			String cuu = current.getStringAttribute(e);
			if (cuu != null) {
				attr.put(e, cuu);
			}
		}
		return attr;
	}

	private void processMessage(XMLElement current, IndentWriter os)
			throws IOException {
		String name = current.getStringAttribute("name");
		Map<String, String> attr = createAttributesFromElement(current, MESSAGE_ATTRIBUTES);
		String attributeObject = createAttributeObject(attr);

		if (Message.MSG_TYPE_ARRAY.equals(current.getStringAttribute("type"))) {
			os.writeln("addArrayMessage(\"" + name + "\",function() {");
		} else {
			os.writeln("addMessage(\"" + name + "\",function() {");
		}
		os.in();
		for (XMLElement c : current.getChildren()) {
			process(c, os);
		}
		os.out();
		os.writeln("}"+ "," + attributeObject + ");");
	}

	private void processParamArrayMessage(XMLElement current, IndentWriter os)
			throws IOException {
		String name = current.getStringAttribute("name");
		os.writeln("addParamArrayMessage(\"" + name + "\",function() {");
		os.in();
		for (XMLElement c : current.getChildren()) {
			process(c, os);
		}
		os.out();
		os.writeln("});");
	}

	private void processFinally(XMLElement current, IndentWriter os)
			throws IOException {
		os.writeln("appandFinalBlock(function() {");
		os.in();
		for (XMLElement c : current.getChildren()) {
			process(c, os);
		}
		os.out();
		os.writeln("});");
	}

	private void processToplevelMap(XMLElement current, IndentWriter os)
			throws IOException {
		String mapClass = current.getStringAttribute("object");
		if (mapClass.equals("com.dexels.navajo.adapter.AsyncProxyMap")) {
			throw new IOException("AsyncProxyMap is deprecated!");
		}

		if (mapClass.equals("com.dexels.navajo.adapter.NavajoMap")) {
			logger.info("Replaced old skool map: DISABLED");
//			mapClass = "com.dexels.navajo.adapter.NavajoMapContinuations";
		}

		os.writeln("callMap(\"" + mapClass + "\",function() {");
		os.in();
		for (XMLElement c : current.getChildren()) {
			process(c, os);
		}
		os.out();
		os.writeln("});");
	}

	private void processReferencedMap(XMLElement current, IndentWriter os)
			throws IOException {
		String filter = current.getStringAttribute("filter");
		if (filter == null) {
			os.writeln("callReferenceMap(\""
					+ current.getStringAttribute("ref") + "\",function() {");
		} else {
			os.writeln("callReferenceMap(\""
					+ current.getStringAttribute("ref") + "\",\"" + filter
					+ "\",function() {");
		}

		os.in();
		for (XMLElement c : current.getChildren()) {
			process(c, os);
		}
		os.out();
		os.writeln("});");
	}

	private void processMessageMapRef(XMLElement e, String refField,
			IndentWriter os) throws IOException {
		String messagePath = e.getStringAttribute("ref");
		String filter = e.getStringAttribute("filter");
		if (filter == null) {
			os.writeln("mapOntoMessage(\"" + refField + "\",\"" + messagePath
					+ "\",function(bean,message) {");
		} else {
			os.writeln("mapOntoMessage(\"" + refField + "\",\"" + messagePath
					+ "\",\"" + filter + "\",function(bean,message) {");
		}
		os.in();
		for (XMLElement c : e.getChildren()) {
			process(c, os);
		}
		os.out();
		os.writeln("});");
		// mapOntoMessage("subBeans","/TestProperties/Array",function(bean,message)
		// {

		// callReferenceMap("subBean", function() {
		// forMessage("/TestProperties/String",function(m){

	}

	private void processExpressionContainer(String methodName,
			XMLElement current, Map<String, String> attributes, IndentWriter os)
			throws IOException {
		String name = current.getStringAttribute("name");
		List<XMLElement> expressions = findExpressions(current);

		String attributeObject = createAttributeObject(attributes);

		List<XMLElement> selections = findSelections(current);
		String prefix = "";
		if (selections.size() > 0) {
			prefix = "prop = ";
		}
		if (expressions.size() == 0) {
			os.writeln(prefix + methodName + "(\"" + name + "\",null,"
					+ attributeObject + ");");
		} else if (expressions.size() == 1) {
			XMLElement expr = expressions.get(0);
			String condition = expr.getStringAttribute("condition");
			if (condition != null && !condition.equals("")) {
				writeConditionalExpressionContainer(methodName, name, current,
						expressions, attributeObject, os);
			}
			os.writeln(prefix + methodName + "(" + "\"" + name + "\","
					+ getExpression(expr) + "," + attributeObject + ");");
		} else {
			writeConditionalExpressionContainer(methodName, name, current,
					expressions, attributeObject, os);
		}
		for (XMLElement s : selections) {
			os.in();
			processSelection(s, os);
			os.out();
		}
		if (selections.size() > 0) {
			os.writeln("prop = null;");
		}
//   	 callReferenceMapSelection("regions",null,function() {
//         addSelection(evaluateNavajo("SingleValueQuery('sportlinkkernel: SELECT name FROM organization WHERE organizationid = ?', $regionName)"),evaluateNavajo("$regionName"),0);
//     })

		List<XMLElement> refs = findMapRefs(current);
		if(refs.size()>0) {
			processPropertyMapRefs(current,os,refs);
		}
	}
	
	
	private void processMapSelectionProperty(String propertyName, XMLElement current,
			Map<String, String> attributes, IndentWriter os) throws IOException {
		String attributeObject = createAttributeObject(attributes);
		XMLElement map = current.getChildByTagName("map");
		String refName = map.getStringAttribute("ref");
		String selectionValue = null;
		String selectionName = null;
		String selectionSelected = null;
		
		List<XMLElement> props = map.getChildrenByTagName("property");
		for (XMLElement currentProp : props) {
			String name = currentProp.getStringAttribute("name");
			if("value".equals(name)) {
				XMLElement ex = currentProp.getChildByTagName("expression");
				selectionValue = getExpression(ex);
			}
			if("name".equals(name)) {
				XMLElement ex = currentProp.getChildByTagName("expression");
				selectionName = getExpression(ex);
			}
			if("selected".equals(name)) {
				XMLElement ex = currentProp.getChildByTagName("expression");
				selectionSelected = getExpression(ex);
			}
		}
		if(selectionSelected==null) {
			selectionSelected = "false";
		}
		os.writeln("addProperty(\"" + propertyName + "\",null,"+ attributeObject + ",function() {");
		os.in();
		os.writeln("callReferenceMapSelection(\""+refName+"\",null,function() {");
		os.in();
		os.writeln("addSelection("+selectionName+","+selectionValue+","+selectionSelected+");");
		os.out();
		os.writeln("});");
		os.out();
		os.writeln( "});");

		// process map property
//        addProperty("Select",null,{direction:"in",type:"selection"},function() {
//            callReferenceMapSelection("regions",null,function() {
//                addSelection(evaluateNavajo("SingleValueQuery('sportlinkkernel: SELECT name FROM organization WHERE organizationid = ?', $regionName)"),"$regionName",0);
//             });   
//            	 
//        });	
				
//	            <property name="Select" type="selection" direction="in">
//                <map ref="regions">
//                    <property name="value">
//                        <expression value="$regionName"/>
//                      </property>
//                    <property name="name">
//                        <expression value="SingleValueQuery('sportlinkkernel: SELECT name FROM organization WHERE organizationid = ?', $regionName)"/>
//                    </property>
//                </map>
//            </property>				
	}

	private void processPropertyMapRefs(XMLElement current, IndentWriter os,
			List<XMLElement> refs) throws IOException {
		if(refs.size()>1) {
			os.write("// Suspicious: Multiple refs? Does that even work?\n");
		}

		for (XMLElement xmlElement : refs) {
			String field = xmlElement.getStringAttribute("ref");
			String name = locateNamePropertyExpression(xmlElement);
			String value = locateValuePropertyExpression(xmlElement);
			String selected = locateSelectedPropertyExpression(xmlElement);
			os.writeln("callReferenceMapSelection(\"" + field + "\",null,function() {");
			os.in();
			os.writeln("addSelection(\"" + name + "\",\""+ value + "\","+selected+");");
			os.out();
			os.write("});");
		}
	}

	private String locateSelectedPropertyExpression(XMLElement xmlElement) {
		String sel = locateElementPropertyExpression("selected",xmlElement,null);
		if(sel==null) {
			return "0";
		}
		return sel;
	}

	private String locateValuePropertyExpression(XMLElement xmlElement) {
		return locateElementPropertyExpression("value",xmlElement,"\"unknown\"");
	}

	private String locateNamePropertyExpression(XMLElement xmlElement) {
		return locateElementPropertyExpression("name",xmlElement,"\"unknown\"");
	}
	
	private String locateElementPropertyExpression(String propertyName,XMLElement xml,String defaultValue) {
		List<XMLElement> xe = xml.getChildrenByTagName("property");
		for (XMLElement xmlElement : xe) {
			if(propertyName.equals(xmlElement.getStringAttribute("name"))) {
				// found.
				return xmlElement.getChildByTagName("expression").getStringAttribute("value");
			}
		}
		return defaultValue;
	}

	private void processSelection(XMLElement s, IndentWriter os)
			throws IOException {
		String condition = s.getStringAttribute("condition");
		if (condition != null && !condition.equals("")) {
			os.writeln("if(evaluateNavajo(\"" + condition.replaceAll("\\n", " ") + "\")==true) {");
			os.in();
		}
		os.writeln("addSelectionToProperty(prop,\"" + s.getStringAttribute("name")
				+ "\",\"" + s.getStringAttribute("value") + "\","
				+ "\"" + ( s.getStringAttribute("selected") != null ? s.getStringAttribute("selected") : "0") + "\");");
		if (condition != null && !condition.equals("")) {
			os.out();
			os.writeln("}");
		}
	}

	private void writeConditionalExpressionContainer(String methodName,
			String setterName, XMLElement current,
			List<XMLElement> expressions, String attributeObject,
			IndentWriter os) throws IOException {
		boolean first = true;
		for (XMLElement xmlElement : expressions) {
			String condition = xmlElement.getStringAttribute("condition");
			if (condition != null && !condition.equals("")) {
				condition = condition.replaceAll("\\n", " ");
				if (first) {
					os.writeln("if(evaluateNavajo(\"" + condition
							+ "\")==true) {");
				} else {
					os.writeln("else if(evaluateNavajo(\"" + condition
							+ "\")==true) {");
				}
			} else {
				os.writeln("else {");
			}
			os.in();
			os.writeln(methodName + "(\"" + setterName + "\","
					+ getExpression(xmlElement) + "," + attributeObject + ");");
			os.out();
			os.writeln("}");
			first = false;
		}
	}

	private String createAttributeObject(Map<String, String> input) {
		if (input == null) {
			return "null";
		}
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		boolean first = true;
		for (Map.Entry<String, String> element : input.entrySet()) {
			if (!first) {
				sb.append(",");
			} else {
				first = false;
			}
			sb.append(element.getKey() + ":\"" + element.getValue() + "\"");
		}
		sb.append("}");
		return sb.toString();
	}

	private String getExpression(XMLElement expr) {
		String val = expr.getStringAttribute("value");
		if (val != null) {
			val = val.replaceAll("\\n", " ");
			return "evaluateNavajo(\"" + val + "\")";
		}
		Vector<XMLElement> children = expr.getChildren();
		if (children.isEmpty()) {
			String contentExpression = expr.getContent().trim()
					.replaceAll("\n", "\\\\\n");
			String unesc = XMLutils.XMLUnescape(contentExpression);
			return "\"" + unesc + "\"";
		}
		XMLElement valueTag = expr.getFirstChild();
		String contentExpression = valueTag.getContent().trim()
				.replaceAll("\n", "\\\\\n");
		String unesc = XMLutils.XMLUnescape(contentExpression);
		return "evaluateNavajo(\"" + unesc + "\")";

	}

	private List<XMLElement> findExpressions(XMLElement current) {
		List<XMLElement> result = new ArrayList<XMLElement>();
		for (XMLElement xmlElement : current.getChildren()) {
			if (xmlElement.getName().equals("expression")) {
				result.add(xmlElement);
			}
		}
		return result;
	}

	private List<XMLElement> findMapRefs(XMLElement current) {
		List<XMLElement> result = new ArrayList<XMLElement>();
		for (XMLElement xmlElement : current.getChildren()) {
			if (xmlElement.getName().equals("map")) {
				result.add(xmlElement);
			}
		}
		return result;
	}
	
	private List<XMLElement> findSelections(XMLElement current) {
		List<XMLElement> result = new ArrayList<XMLElement>();
		for (XMLElement xmlElement : current.getChildren()) {
			if (xmlElement.getName().equals("option")) {
				result.add(xmlElement);
			}
		}
		return result;
	}

	private void startCondition(XMLElement current, String condition,
			IndentWriter os) throws IOException {
		condition = condition.replaceAll("\\n", " ");
		os.writeln("if(evaluateNavajo(\"" + condition + "\")==true) {");
		current.removeAttribute("condition");
		// not reentrant
		os.in();
		process(current, os);
		os.out();
		os.writeln("}");
	}

	private void processMethod(XMLElement c, IndentWriter os)
			throws IOException {
		String name = c.getStringAttribute("name");
		StringBuffer sb = new StringBuffer("['");

		Vector<XMLElement> required = c.getChildren();
		boolean first = true;
		for (XMLElement ch : required) {
			sb.append(ch.getStringAttribute("message"));
			if (!first) {
				sb.append(",");
			} else {
				first = false;
			}
		}
		sb.append("']");
		os.writeln("addMethod(\"" + name + "\"," + sb.toString() + ");");
	}

	public XMLElement resolveInclude(String name) throws XMLParseException,
			IOException {
		if (includeBase == null) {
			throw new IllegalArgumentException("No include base set!");
		}
		File result = new File(includeBase, name + ".xml");
		if (!result.exists()) {
			throw new IllegalArgumentException("Can not include: File: "
					+ result.getAbsolutePath() + " does not exist!");
		}
		FileReader fr = new FileReader(result);
		XMLElement elt = new CaseSensitiveXMLElement();
		elt.parseFromReader(fr);
		
		return elt;
	}

	public static void main(String[] args) throws IOException {
		File output = new File("work");
		File input = new File(
				"/Users/frank/Documents/workspace-indigo/sportlink-serv/navajo-tester/auxilary/scripts");
		BasicScriptCompiler bsc = new BasicScriptCompiler();
		bsc.setIncludeBase(new File(
				"/Users/frank/Documents/workspace-indigo/sportlink-serv/navajo-tester/auxilary/scripts/"));
		massCompile(input, output, bsc);
		logger.info("total fails: " + (fails) + " ignores: " + ignores
				+ " successses: " + successes);
	}

	public static void massCompile(File inputDir, File outputDir,
			BasicScriptCompiler bsc) throws IOException {
		File[] source = inputDir.listFiles();
		for (File file : source) {
			String name = file.getName();
			if (file.isDirectory()) {
				if (!name.equals("CVS")) {
					File newOutput = new File(outputDir, name);
					newOutput.mkdirs();
					massCompile(file, newOutput, bsc);
				}
			} else {
				logger.info("Compiling: " + file.getAbsolutePath());
				if (name.endsWith(".xml")) {
					String currentScriptName = name.substring(0,
							name.indexOf('.'));
					String outName = currentScriptName + ".js";
					File outFile = new File(outputDir, outName);
					FileWriter fw = new FileWriter(outFile);
					FileReader fr = new FileReader(file);
					StringBuilder sb = new StringBuilder();
					try {
						bsc.compileTsl(currentScriptName, fr, new IndentWriter(
								sb));
						String result = bsc.postProcess(sb.toString());
						fw.write(result);
						successes++;
					} catch (Throwable e) {
						logger.error("Compile failed. Script: " + file
								+ " total fails: " + (fails++) + " ignores: "
								+ ignores + " successses: " + successes,e);
					}
					fr.close();
					fw.flush();
					fw.close();
				}
			}
			logger.info("Funny tags: " + strangeTags);
		}
	}
}
