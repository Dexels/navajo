package com.dexels.navajo.rhino;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.dexels.navajo.document.nanoimpl.CaseSensitiveXMLElement;
import com.dexels.navajo.document.nanoimpl.XMLElement;

public class BasicScriptCompiler implements ScriptCompiler {

	private static final String[] PROPERTY_ATTRIBUTES = new String[]{"type","subtype","direction","description"};
	private long compileStart;

	@Override
	public void compileTsl(Reader is, IndentWriter os) throws IOException {
		compileStart = System.currentTimeMillis();
		XMLElement x = new CaseSensitiveXMLElement();
		x.parseFromReader(is);
		process(x,os);
	}

	public void compileTsl(InputStream is, OutputStream os) throws IOException {
		Writer w = new OutputStreamWriter(os);
		compileTsl(new InputStreamReader(is), new IndentWriter(w));
	}	
	
	private void process(XMLElement current, IndentWriter os) throws IOException {
		
		String condition = current.getStringAttribute("condition");
		
		if(condition!=null) {
			startCondition(current,condition,os);
			return;
		}
		
		if(current.getName().equals("tsl")) {
			os.writeln("// -- Compiled code. Edit at your own peril.");
			for (XMLElement c : current.getChildren()) {
				process(c,os);
			}
			long time = System.currentTimeMillis() - compileStart;
			os.writeln("// compiletime: "+time);
		}

		if(current.getName().equals("methods")) {
			for (XMLElement c : current.getChildren()) {
				process(c,os);
			}
		}
		if(current.getName().equals("method")) {
			processMethod(current,os);
		}
		if(current.getName().equals("param")) {
			processExpressionContainer("addParam",current,createAttributesFromElement(current,PROPERTY_ATTRIBUTES),os);
		}
		if(current.getName().equals("field")) {
			processExpressionContainer("addField",current,null,os);
		}
		if(current.getName().equals("message")) {
			processMessage(current,os);
		}

		if(current.getName().equals("property")) {
			processExpressionContainer("addProperty",current,createAttributesFromElement(current,PROPERTY_ATTRIBUTES),os);
		}

		
		if(current.getName().equals("map")) {
//			processExpressionContainer("addParam",current,os);
			if(current.getStringAttribute("object")!=null) {
				processToplevelMap(current,os);
			}
			if(current.getStringAttribute("ref")!=null) {
				processReferencedMap(current,os);
			}
			
			
		}
		

	}

	private Map<String, String> createAttributesFromElement(XMLElement current,
			String[] propertyAttributes) {
		Map<String,String> attr = new HashMap<String, String>();
		for (String e : propertyAttributes) {
			String cuu = current.getStringAttribute(e);
			if(cuu!=null) {
				attr.put(e, cuu);
			}
		}
		//System.err.println("attr: "+attr);
		return attr;
	}

//	private void processProperty(XMLElement current, IndentWriter os) throws IOException {
//		
//		processExpressionContainer("addProperty",current,attr,os);		
//	}

	private void processMessage(XMLElement current, IndentWriter os) throws IOException {
		String name = current.getStringAttribute("name");
		if("array".equals("type")) {
			os.writeln("addArrayMessage(\""+name+"\",function() {");
		} else {
			os.writeln("addMessage(\""+name+"\",function() {");
		}
		os.in();
		for (XMLElement c : current.getChildren()) {
			process(c,os);
		}
		os.out();
		os.writeln("});");

	}

	private void processToplevelMap(XMLElement current, IndentWriter os) throws IOException {
		os.writeln("callMap(\""+current.getStringAttribute("object")+"\",function() {");
		os.in();
		for (XMLElement c : current.getChildren()) {
			process(c,os);
		}
		os.out();
		os.writeln("});");
	}

	private void processReferencedMap(XMLElement current, IndentWriter os) throws IOException {
		os.writeln("callReferenceMap(\""+current.getStringAttribute("ref")+"\",function() {");
		os.in();
		for (XMLElement c : current.getChildren()) {
			process(c,os);
		}
		os.out();
		os.writeln("});");
	}


	private void processExpressionContainer(String methodName, XMLElement current,Map<String,String> attributes, IndentWriter os) throws IOException {
		String name = current.getStringAttribute("name");
		List<XMLElement> expressions = findExpressions(current);
		String attributeObject = createAttributeObject(attributes);
		if(expressions.size()==0) {
			os.writeln(methodName+"(\""+name+"\",null,"+attributeObject+");");
		} else if(expressions.size()==1) {
			XMLElement expr = expressions.get(0);
			if(expr.getStringAttribute("condition")!=null) {
				throw new IllegalStateException("Single expression with condition not legal");
			}
			os.writeln(methodName+"(" +
					"\""+name+"\",\""+getExpression(expr)+"\","+attributeObject+");");
		} else {
			writeConditionalExpressionContainer(methodName,name,current,expressions,attributeObject, os);
		}
	}

	private void writeConditionalExpressionContainer(String methodName, String setterName,
			XMLElement current, List<XMLElement> expressions,String attributeObject, IndentWriter os) throws IOException {
		boolean first = true;
		for (XMLElement xmlElement : expressions) {
			String condition = xmlElement.getStringAttribute("condition");
//			if(condition==null) {
//				condition = "true";
//			}
			if(condition!=null) {
				if(first) {
					os.writeln("if(evaluateNavajo(\""+condition+"\")==true) {");
				} else {
					os.writeln("else if(evaluateNavajo(\""+condition+"\")==true) {");
				}
			} else {
					os.writeln("else {");
			}
			os.in();
			os.writeln(methodName+"(\""+setterName+"\",\""+getExpression(xmlElement)+"\","+attributeObject+");");
			os.out();
			os.writeln("}");
			first = false;
		}
	}

	private String createAttributeObject(Map<String,String> input) {
		if(input==null) {
			return "null";
		}
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		boolean first = true;
		for (Map.Entry<String, String> element : input.entrySet()) {
			if(!first) {
				sb.append(",");
			} else {
				first = false;
			}
			sb.append(element.getKey()+":\""+element.getValue()+"\"");
		}
		sb.append("}");
		return sb.toString();
	}
	
	private String getExpression(XMLElement expr) {
		String val = expr.getStringAttribute("value");
		if(val!=null) {
			return val;
		}
		return expr.getContent().replaceAll("\n", "\\\\\n");
	}

	private List<XMLElement> findExpressions(XMLElement current) {
		List<XMLElement> result = new ArrayList<XMLElement>();
		for (XMLElement xmlElement : current.getChildren()) {
			if(xmlElement.getName().equals("expression")) {
				result.add(xmlElement);
			}
		}
		return result;
	}

	private void startCondition(XMLElement current,String condition, IndentWriter os) throws IOException {
		os.writeln("if(evaluateNavajo(\""+condition+"\")==true) {");
		current.removeAttribute("condition");
		// not reentrant
		os.in();
		process(current, os);
		os.out();
		os.writeln("}");
	}


	private void processMethod(XMLElement c, IndentWriter os) throws IOException {
		String name = c.getStringAttribute("name");
		StringBuffer sb = new StringBuffer("['");
		
		Vector<XMLElement> required = c.getChildren();
		boolean first = true;
		for (XMLElement ch : required) {
			sb.append(ch.getStringAttribute("message"));
			if(!first) {
				sb.append(",");
			} else {
				first = false;
			}
		}
		sb.append("']");
		os.writeln("addMethod(\""+name+"\","+sb.toString()+");");
	//		addMethod('person/ProcessDeletePerson',['Person']);
	}

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		BasicScriptCompiler bsc = new BasicScriptCompiler();
		StringWriter sw = new StringWriter();
//		FileReader fr = new FileReader("ProcessSearchPersons.xml");
		FileReader fr = new FileReader("ProcessQueryPerson.xml");
		
		bsc.compileTsl(fr, new IndentWriter( sw));
		fr.close();
		System.err.println(sw.toString());
	}

}
