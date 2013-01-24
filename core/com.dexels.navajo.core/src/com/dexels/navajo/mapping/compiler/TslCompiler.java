package com.dexels.navajo.mapping.compiler;

/**
 * <p>Title: Navajo Product Project</p>"
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author Arjen Schoneveld
 * @version $Id$get
 */

/**
 * TODO IMPLEMENT SUPPORT FOR ARBITRARY JAVA BEANS (NEXT TO MAPPABLE AND ASYNCMAPPABLE OBJECTS.
 *
 * SYMBOL TABLE BIJHOUDEN VAN ATTRIBUUT WAARDEN UIT MAPPABLE OBJECTEN, DAN DEZE SYMBOL TABLE MEEGEVEN AAN EXPRESSION.EVALUATE(),
 * ZODAT NIET VIA INTROSPECTIE DE ATTRIBUUT WAARDEN HOEVEN TE WORDEN BEPAALD
 *
 * $columnValue('AAP') -> Object o = contextMap.getColumnValue('AAP') -> symbolTable.put("$columnValue('AAP')", o);
 *laz
 *
 *
 */
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Constructor;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Stack;
import java.util.StringTokenizer;

import navajocore.Version;

import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Operand;
import com.dexels.navajo.document.jaxpimpl.xml.XMLDocumentUtils;
import com.dexels.navajo.document.jaxpimpl.xml.XMLutils;
import com.dexels.navajo.legacy.compiler.EclipseCompiler;
import com.dexels.navajo.legacy.compiler.JavaCompiler;
import com.dexels.navajo.legacy.compiler.SunJavaCompiler;
import com.dexels.navajo.loader.NavajoClassLoader;
import com.dexels.navajo.mapping.DependentResource;
import com.dexels.navajo.mapping.GenericMultipleDependentResource;
import com.dexels.navajo.mapping.HasDependentResources;
import com.dexels.navajo.mapping.MappingUtils;
import com.dexels.navajo.mapping.bean.DomainObjectMapper;
import com.dexels.navajo.mapping.compiler.meta.AdapterFieldDependency;
import com.dexels.navajo.mapping.compiler.meta.Dependency;
import com.dexels.navajo.mapping.compiler.meta.ExpressionValueDependency;
import com.dexels.navajo.mapping.compiler.meta.IncludeDependency;
import com.dexels.navajo.mapping.compiler.meta.JavaDependency;
import com.dexels.navajo.mapping.compiler.meta.MapMetaData;
import com.dexels.navajo.parser.Expression;
import com.dexels.navajo.parser.TMLExpressionException;
import com.dexels.navajo.server.GenericHandler;
import com.dexels.navajo.server.NavajoIOConfig;
import com.dexels.navajo.server.SystemException;
import com.dexels.navajo.server.UserException;
import com.dexels.navajo.server.internal.LegacyNavajoIOConfig;

public class TslCompiler {

  private ClassLoader loader = null;

  private int messageListCounter = 0;
  private int asyncMapCounter = 0;
  private int lengthCounter = 0;
  private int functionCounter = 0;
  private int objectCounter = 0;
  private int subObjectCounter = 0;
  private int startIndexCounter = 0;
  private int startElementCounter = 0;
  private String scriptPath;
  //private int offsetElementCounter = 0;
  private int methodCounter = 0;
  private ArrayList<StringBuffer> methodClipboard = new ArrayList<StringBuffer>();
  private ArrayList<String> variableClipboard = new ArrayList<String>();
  private StringBuffer dependencies = new StringBuffer();
  private HashSet<String> dependentObjects = new HashSet<String>();
  
  /**
   * Use this as a placeholder for instantiated adapters (for meta data usage).
   */
  private HashMap<Class, DependentResource []> instantiatedAdapters = new HashMap<Class, DependentResource []>();
  
  private Stack<Class> contextClassStack = new Stack<Class>();
  private Class contextClass = null;
  private int included = 0;

  private JavaCompiler compiler;

  private String scriptType = "tsl";
  
  private static String hostname = null;
  
  private static String VERSION = "$Id$";

  private final NavajoIOConfig navajoIOConfig;
  
  
  private final static Logger logger = LoggerFactory.getLogger(TslCompiler.class);

  public TslCompiler(ClassLoader loader) {
	    this.navajoIOConfig = new LegacyNavajoIOConfig();
	    initialize(loader);
  }
  public TslCompiler(ClassLoader loader, NavajoIOConfig config) {
	    this.navajoIOConfig = config;
	    initialize(loader);
  }
private void initialize(ClassLoader loader) {
	this.loader = loader;
	messageListCounter = 0;
	if (loader == null) {
	  this.loader = this.getClass().getClassLoader();
	}
}
  
  public NavajoIOConfig getNavajoIOConfig() {
	  return this.navajoIOConfig;
  }
  
  private String replaceQuotes(String str) {

	  if ( str.startsWith("#")) {
		  str = "(String) userDefinedRules.get(\"" + str.substring(1) + "\")";
		  return str;
	  }   else {
		  StringBuffer result = new StringBuffer(str.length());
		  for (int i = 0; i < str.length(); i++) {
			  char c = str.charAt(i);
			  if (c == '"') {
				  result.append("\\\"");
			  } else if ( c == '\n') {
				  result.append(" ");
			  }
			  else {
				  result.append(c);
			  }
		  }
		  return "\"" + result.toString() + "\"";
	  }
  }

  private String removeNewLines(String str) {
    StringBuffer result = new StringBuffer(str.length());
    for (int i = 0; i < str.length(); i++) {
      char c = str.charAt(i);
      if (c == '\n') {
        result.append("\\n");
      }
      else if (c=='\r'){
    	  // IGNORE CRS
      } else {
        result.append(c);
      }
    }
    return result.toString();
  }

  private String printIdent(int count) {
    StringBuffer identStr = new StringBuffer();
    for (int i = 0; i < count; i++) {
      identStr.append(" ");
    }
    return identStr.toString();
  }

  private Element getNextElement(Node n) {
    NodeList children = n.getChildNodes();
    for (int i = 0; i < children.getLength(); i++) {
      if (children.item(i)instanceof Element) {
        return (Element) children.item(i);
      }
    }
    return null;
  }

  private int countNodes(NodeList l, String name) {
    int count = 0;
    for (int i = 0; i < l.getLength(); i++) {
      if (l.item(i).getNodeName().equals(name)) {
        count++;
      }
    }
    return count;
  }

  private String removeWhiteSpaces(String s) {
    StringBuffer result = new StringBuffer();
    for (int i = 0; i < s.length(); i++) {
      char c = s.charAt(i);
      if (c != ' ') {
        result.append(c);
      }
    }
    return result.toString();
  }

  private final void addDependency(String code, String id) {
	  if (!dependentObjects.contains(id)) {
		  dependencies.append(code);
		  dependentObjects.add(id);
	  }
  }
  
  /**
   * VERY NASTY METHOD.
   * IT TRIES ALL KINDS OF TRICKS TO TRY TO AVOID CALLING THE EXPRESSION.EVALUATE() METHOD IN THE GENERATED JAVA.
   *
   * @param ident
   * @param clause
   * @param className
   * @return
   */
public String optimizeExpresssion(int ident, String clause, String className, String objectName) throws UserException {

    boolean exact = false;
    StringBuffer result = new StringBuffer();
    char firstChar = ' ';
    boolean functionCall = false;
    StringBuffer functionNameBuffer = new StringBuffer();
    String functionName = "";
    String call = "";

    // Try if clause contains only a (Navajo) function and a mappable attribute call.
    for (int i = 0; i < clause.length(); i++) {
      char c = clause.charAt(i);

      if (c != ' ' && firstChar == ' ') {
        firstChar = c;
      }

      if ( ( (firstChar > 'a' && firstChar < 'z')) ||
          ( (firstChar > 'A') && (firstChar < 'Z'))) {
        functionCall = true;
      }

      if ( (functionCall) && (c != '(')) {
        functionNameBuffer.append(c);
      }
      else if (functionCall && c == '(') {
        functionName = functionNameBuffer.toString();
        functionNameBuffer = new StringBuffer();
      }

      if (c == '$') { // New attribute found
        StringBuffer name = new StringBuffer();
        i++;
        c = clause.charAt(i);
        while (c != '(' && i < clause.length() && c != ')') {
          name.append(c);
          i++;
          if (i < clause.length()) {
            c = clause.charAt(i);
          }
        }
        i++;

        StringBuffer params = new StringBuffer();

        if (clause.indexOf("(") != -1) {
          // Determine parameters.
          int endOfParams = 1;

          while (endOfParams > 0 && i < clause.length()) {
            c = clause.charAt(i);
            if (c == '(') {
              endOfParams++;
            }
            else if (c == ')') {
              endOfParams--;
            }
            else {
              params.append(c);
            }
            i++;
          }
        }

        String expr = "";
        if (functionName.equals("")) {
          expr = (params.toString().length() > 0 ?
                  "$" + name + "(" + params + ")" : "$" + name);
        }
        else {
          expr = functionName + "(" +
              (params.toString().length() > 0 ? "$" + name + "(" + params + ")" :
               "$" + name) + ")";

        }
        if (removeWhiteSpaces(expr).equals(removeWhiteSpaces(clause))) {
          // Let's evaluate this directly.
          exact = true;
          Class expressionContextClass = null;

          try {
            StringBuffer objectizedParams = new StringBuffer();
            StringTokenizer allParams = new StringTokenizer(params.toString(),
                ",");
            while (allParams.hasMoreElements()) {
              String param = allParams.nextToken();
              // Try to evaluate expression (NOTE THAT IF REFERENCES ARE MADE TO EITHER NAVAJO OR MAPPABLE OBJECTS THIS WILL FAIL
              // SINCE THESE OBJECTS ARE NOT KNOWN AT COMPILE TIME!!!!!!!!!!!!!!1
              Operand op = Expression.evaluate(param, null);
              Object v = op.value;
              if (v instanceof String) {
                objectizedParams.append("\"" + v + "\"");
              }
              else if (v instanceof Integer) {
                objectizedParams.append("new Integer(" + v + ")");
              }
              else if (v instanceof Long) {
            	  objectizedParams.append("new Long(" + v + ")");
              }
              else if (v instanceof Float) {
                objectizedParams.append("new Float(" + v + ")");
              }
              else if (v instanceof Boolean) {
                objectizedParams.append("new Boolean(" + v + ")");
              }
              else if (v instanceof Double) {
                objectizedParams.append("new Double(" + v + ")");
              }
              else
                throw new UserException(-1, "Unknown type encountered during compile time: " + v.getClass().getName() +  " @clause: " + clause);
              if (allParams.hasMoreElements()) {
                objectizedParams.append(",");
              }
            }

              try {
            	  expressionContextClass = Class.forName(className, false, loader);
              } catch (Exception e) {
              	throw new Exception("Could not find adapter: " + className);
              }

            String attrType = MappingUtils.getFieldType(expressionContextClass, name.toString());

            // Try to locate class:
            if (!functionName.equals("")) {
            	try {
            		Class.forName("com.dexels.navajo.functions." + functionName, false, loader);
            	} catch (Exception e) { throw new Exception("Could not find Navajo function: " + functionName); }

            }
            call = objectName + ".get" + (name.charAt(0) + "").toUpperCase() +
                name.substring(1) + "(" + objectizedParams.toString() + ")";

            if (attrType.equals("int")) {
              call = "new Integer(" + call + ")";
            }
            else if (attrType.equals("float") || attrType.equals("double")) {
              call = "new Double(" + call + ")";
            }
            else if (attrType.equals("boolean")) {
              call = "new Boolean(" + call + ")";
            } else   if (attrType.equals("long")) {
            	call = "new Long(" + call + ")";
            }
          }
          catch (ClassNotFoundException cnfe) {
        	  if (expressionContextClass == null) {
        		  throw new UserException( -1,
        				  "Error in script: Could not find adapter: " + className +
        				  " @clause: " + clause);
        	  }
        	  else {
        		  throw new UserException( -1,
        				  "Error in script: Could not locate function: " + functionName +
        				  " @ clause: " + clause);
        	  }
          }
          catch (Throwable e) {
        	  exact = false;
          }
        }
      }
    }

    // Try to evaluate clause directly (compile time).
    if ( (!exact) && !clause.equals("TODAY") && !clause.equals("null") &&
        (clause.indexOf("[") == -1) && (clause.indexOf("$") == -1) &&
        (clause.indexOf("(") == -1) && (clause.indexOf("+") == -1)) {
      try {
        ////System.out.println("CLAUSE = " + clause);
        Operand op = Expression.evaluate(clause, null);
        ////System.out.println("op = " + op);
        Object v = op.value;
        ////System.out.println("op.value = " + v);
        exact = true;
        if (v instanceof String) {
          call = "\"" + v + "\"";
        }
        else if (v instanceof Integer) {
          call = "new Integer(" + v + ")";
        }
        else if (v instanceof Long) {
        	call = "new Long(" + v + ")";
        }
        else if (v instanceof Float) {
          call = "new Float(" + v + ")";
        }
        else if (v instanceof Boolean) {
          call = "new Boolean(" + v + ")";
        }
        else if (v instanceof Double) {
          call = "new Double(" + v + ")";
        } else
          throw new UserException(-1, "Unknown type encountered during compile time: " + v.getClass().getName() +  " @clause: " + clause);

      }
      catch (NullPointerException ne) {
        exact = false;
      }
      catch (TMLExpressionException pe) {
        exact = false;
        //System.err.println("TMLExpressionException, COULD NOT OPTIMIZE EXPRESSION: " + clause);
      }
      catch (com.dexels.navajo.server.SystemException se) {
        exact = false;
        if ( !clause.startsWith("#")) {
        	throw new UserException(-1, "Could not compile script, Invalid expression: " + clause);
        }
      }
      catch (Throwable e) {
        exact = false;
        //System.err.println("Throwable, COULD NOT OPTIMIZE EXPRESSION: " + clause);
      }
    }

    if (!exact && clause.equals("null")) {
      call = "null";
      exact = true;
    }

    // Use Expression.evaluate() if expression could not be executed in an optimized way.
    if (!exact) {
      result.append(printIdent(ident) + "op = Expression.evaluate(" +
                    replaceQuotes(clause) +
                    ", access.getInDoc(), currentMap, currentInMsg, currentParamMsg, currentSelection, null);\n");
      result.append(printIdent(ident) + "sValue = op.value;\n");
    }
    else { // USE OUR OPTIMIZATION SCHEME.
      ////System.out.println("CALL = " + call);
      result.append(printIdent(ident) + "sValue = " + call + ";\n");
      if (!functionName.equals("")) { // Construct Navajo function instance if needed.
        String functionVar = "function" + (functionCounter++);
        result.append(printIdent(ident) + "com.dexels.navajo.functions." +
                      functionName + " " + functionVar +
                      " = (com.dexels.navajo.functions." + functionName +
                      ") getFunction(" +
                      "\"com.dexels.navajo.functions." + functionName +
                      "\");\n");
        result.append(printIdent(ident) + functionVar + ".reset();\n");
        result.append(printIdent(ident) + functionVar +
                      ".insertOperand(sValue);\n");
        result.append(printIdent(ident) + "sValue = " + functionVar +
                      ".evaluate();\n");
      }
    }

    return result.toString();
  }

  private String getCDATAContent(Node n) {
	  NodeList nl = n.getChildNodes();
	  for (int j = 0; j < nl.getLength(); j++) {
		  if ( nl.item(j).getNodeType() == Node.CDATA_SECTION_NODE ) {
			  return nl.item(j).getNodeValue();
		  }
	  }
	  return null;
  }
  
  /**
   * Extract the value from an expression node.
   * 
   * @param exprElmnt
   * @return
   * @throws Exception
   */
  private Object [] getExpressionValue(Element exprElmnt, Boolean isStringOperand) throws Exception {
	  String value = null;
	 
	  isStringOperand = Boolean.FALSE;
	  
	  Element valueElt = (Element) XMLutils.findNode(exprElmnt, "value");
	  
	  if ( valueElt == null ) {
	    	value = XMLutils.XMLUnescape(exprElmnt.getAttribute("value"));
	    } else {
	    	value = getCDATAContent(valueElt);
	    	if ( value == null ) {
	    		Node child = valueElt.getFirstChild();
	    		value = child.getNodeValue();
	    	}
	    }
	    
	    // Check if operand is given as text node between <expression> tags.
	    if (value == null || value.equals("")) {
	    	Node child = exprElmnt.getFirstChild();
	    	String cdata = getCDATAContent(exprElmnt);
	    	if ( cdata != null ) {
	    		isStringOperand = Boolean.TRUE;
	    		value = cdata;
	    	} else if (child != null) {
	    		isStringOperand = Boolean.TRUE;
	    		value = child.getNodeValue();
	    	}
	    	else {
	    		throw new Exception("Error @" +
	    				(exprElmnt.getParentNode() + "/" + exprElmnt) + ": <expression> node should either contain a value attribute or a text child node: >" +
	    				value + "<");
	    	}
	    } else {
	    	value = value.trim();
	    	value = value.replaceAll("\n", " ");
	    	value = XMLutils.XMLUnescape(value);
	    }
	    
	    return new Object[]{ removeNewLines( value ),isStringOperand};
  }
  
  public String expressionNode(int ident, Element exprElmnt, int leftOver,  String className, String objectName) throws Exception {
	  
    StringBuffer result = new StringBuffer();
    Boolean isStringOperand = false;

    String condition = exprElmnt.getAttribute("condition");
    
    Object [] valueResult = getExpressionValue(exprElmnt, isStringOperand);
	String value = (String) valueResult[0];
	isStringOperand = (Boolean) valueResult[1];
  
	if (!condition.equals("")) {
		result.append(printIdent(ident) + "if (Condition.evaluate(" + replaceQuotes(condition) + ", access.getInDoc(), currentMap, currentInMsg, currentParamMsg))");
	}

	result.append(printIdent(ident) + "{\n");
	ident += 2;

	if (!isStringOperand) {
		result.append(optimizeExpresssion(ident, value, className, objectName));
	}
	else {
		result.append(printIdent(ident) + "sValue = \"" + value + "\";\n");
	}

	// Check dependencies.
	Dependency [] allDeps =  ExpressionValueDependency.getDependencies( value );
	for ( int a = 0; a < allDeps.length; a++ ) {
		addDependency("dependentObjects.add( new ExpressionValueDependency(-1, \"" + allDeps[a].getId() + 
				"\", \"" + 
				allDeps[a].getType() + "\"));\n", 
				"FIELD" + "ExpressionValueDependency" + ";" + allDeps[a].getType() + ";" + 
				allDeps[a].getId());
	}
	
	result.append(printIdent(ident) + "matchingConditions = true;\n");

	ident -= 2;
	result.append(printIdent(ident) + "}\n");

	if (leftOver > 0) {
		result.append(printIdent(ident) + " else \n");

	}
	
	return result.toString();
	
  }

  public String methodsNode(int ident, Element n) {

    StringBuffer result = new StringBuffer();

    // Process children.
    NodeList children = n.getChildNodes();
    for (int i = 0; i < children.getLength(); i++) {
      if (children.item(i).getNodeName().equals("method")) {
        Element e = (Element) children.item(i);
        String name = e.getAttribute("name");
        String condition = e.getAttribute("condition");
        String description = e.getAttribute("description");
        condition = (condition == null) ? "" : condition;
        description = (description == null) ? "" : description;
        if (!condition.equals("")) {
          result.append(printIdent(ident) + "if (Condition.evaluate(" +
                        replaceQuotes(condition) +
                        ", access.getInDoc(), null, null, null)) {\n");
        }
        else {
          result.append(printIdent(ident) + "if (true) {\n");
          // Get required messages.
        }
        result.append(printIdent(ident + 2) + "com.dexels.navajo.document.Method m = NavajoFactory.getInstance().createMethod(access.getOutputDoc(), \"" +
                      name + "\", \"\");\n");
        result.append(printIdent(ident + 2) + "m.setDescription(\"" +
                      description + "\");\n");
        NodeList required = e.getChildNodes();
        for (int j = 0; j < required.getLength(); j++) {
          if (required.item(j).getNodeName().equals("required")) {
            String reqMsg = ( (Element) required.item(j)).getAttribute("message");
            String filter = ( (Element) required.item(j)).getAttribute("filter");
            result.append(printIdent(ident + 2) + "m.addRequired(\"" + reqMsg + "\"" + ", \"" + filter + 
                          "\");\n");
          }
        }
        result.append(printIdent(ident + 2) + "access.getOutputDoc().addMethod(m);\n");
        result.append(printIdent(ident) + "}\n");
      }
    }

    return result.toString();
  }

  @SuppressWarnings("unchecked")
public String messageNode(int ident, Element n, String className, String objectName, List<Dependency> deps) throws Exception {
    StringBuffer result = new StringBuffer();


    String messageName = n.getAttribute("name");
    String condition = n.getAttribute("condition");
    String type = n.getAttribute("type");
    String mode = n.getAttribute("mode");
    String count = n.getAttribute("count");
    String start_index = n.getAttribute("start_index");
    String orderby = n.getAttribute("orderby");
    
    ////System.out.println("COUNT = " + count);
    type = (type == null) ? "" : type;
    mode = (mode == null) ? "" : mode;
    condition = (condition == null) ? "" : condition;
    count = (count == null || count.equals("")) ? "1" : count;
    int startIndex = (start_index == null || start_index.equals("")) ? -1 : Integer.parseInt(start_index);

    boolean conditionClause = false;

    // If <message> node is conditional:
    if (!condition.equals("")) {
      conditionClause = true;
      result.append(printIdent(ident) + "if (Condition.evaluate(" +
                    replaceQuotes(condition) +
                    ", access.getInDoc(), currentMap, currentInMsg, currentParamMsg)) { \n");
      ident += 2;
    }

    Element nextElt = getNextElement(n);
    String ref = "";
    String filter = "";
    String startElement = "";
    String elementOffset = "";

    boolean isArrayAttr = false;
    boolean isSubMapped = false;
    boolean forceArray = false;
    String mapPath = null;
    
    // Check if <message> is mapped to an object attribute:
    if (nextElt != null && nextElt.getNodeName().equals("map") &&
        nextElt.getAttribute("ref") != null &&
        !nextElt.getAttribute("ref").equals("")) {
          String refOriginal = nextElt.getAttribute("ref");
          // Remove leading $ (if present).
          refOriginal = refOriginal.replaceAll("\\$", "");
          
          //System.err.println("refOriginal = " + refOriginal);
          
          if (refOriginal.indexOf("/")!=-1) {
        	  ref = refOriginal.substring(refOriginal.lastIndexOf('/')+1, refOriginal.length());
              mapPath = refOriginal.substring(0,refOriginal.lastIndexOf('/'));
         } else {
        	 ref = refOriginal;
           }
          
          forceArray = type.equals(Message.MSG_TYPE_ARRAY); //( nextElt.getAttribute("forcearray") != null && !nextElt.getAttribute("forcearray").equals("") );
          //System.err.println("forceArray =  " + forceArray);
          filter = nextElt.getAttribute("filter");
          startElement = nextElt.getAttribute("start_element");
          elementOffset = nextElt.getAttribute("element_offset");
          startElement = ((startElement == null || startElement.equals("")) ? "" : startElement);
          elementOffset = ((elementOffset == null || elementOffset.equals("")) ? "" : elementOffset);
        
          try {
        	  if (mapPath!=null) {
        		  contextClass = locateContextClass(mapPath, 0);
        		  className = contextClass.getName();
        	  } else {
        		  contextClass = Class.forName(className, false, loader);
        	  }
          } catch (Exception e) {
        	  throw new Exception("Could not find adapter: " + className);
          }
          
          addDependency("dependentObjects.add( new JavaDependency( -1, \"" + className + "\"));\n", "JAVA"+className);
          
          //System.out.println("in MessageNode(), new contextClass = " + contextClass);
          
          if ( DomainObjectMapper.class.isAssignableFrom(contextClass)) {
        	  //System.err.println("Got a parent DomainObjectMapper...");
        	  isArrayAttr = forceArray;
        	  type = Message.MSG_TYPE_ARRAY;
          } else {
        	  isArrayAttr = MappingUtils.isArrayAttribute(contextClass, ref);
        	  if (isArrayAttr) {
        		  type = Message.MSG_TYPE_ARRAY;
        	  }
          }
          isSubMapped = true;
    }
    ////System.out.println("isArrayAttr = " + isArrayAttr);

    // Create the message(s). Multiple messages are created if count > 1.
    result.append(printIdent(ident) + "count = " +
                  (count.equals("1") ? "1" :
                   "((Integer) Expression.evaluate(\"" + count +
                   "\", access.getInDoc(), currentMap, currentInMsg, currentParamMsg).value).intValue()") +
                  ";\n");
    String messageList = "messageList" + (messageListCounter++);
    result.append(printIdent(ident) + "Message [] " + messageList +
                  " = null;\n");
    
    
    String orderbyExpression =  ("".equals(orderby) ? "\"\"" :
                "(String) Expression.evaluate(" + replaceQuotes(orderby) + ", access.getInDoc(), currentMap, currentInMsg, currentParamMsg).value"
    );
    
    if (n.getNodeName().equals("message")) {
	    result.append(printIdent(ident) + messageList +
	                  " = MappingUtils.addMessage(access.getOutputDoc(), currentOutMsg, \"" +
	                  messageName + "\", \"\", count, \"" + type + "\", \"" + mode +
	                  "\", " + orderbyExpression + ");\n");
	    result.append("");
    } else { // must be parammessage.
    	 
    	 result.append(printIdent(ident) + messageList +
                " = MappingUtils.addMessage(access.getInDoc(), currentParamMsg, \"" +
                messageName + "\", \"\", count, \"" + type + "\", \"" + mode +
                "\");\n");
    }
    
    result.append(printIdent(ident) + "for (int messageCount" + (ident) +
                  " = 0; messageCount" + (ident) + " < " + messageList +
                  ".length; messageCount" + (ident) + "++) {\n if (!kill) {\n");
    
    if (n.getNodeName().equals("message")) {
	    result.append(printIdent(ident + 2) + "outMsgStack.push(currentOutMsg);\n");
	    result.append(printIdent(ident + 2) + "currentOutMsg = " + messageList +
	                  "[messageCount" + (ident) + "];\n");
    } else { // must be parammessage.
    	result.append(printIdent(ident + 2) + "paramMsgStack.push(currentParamMsg);\n");
	    result.append(printIdent(ident + 2) + "currentParamMsg = " + messageList + "[messageCount" + (ident) + "];\n");
    }

    result.append(printIdent(ident + 2) +
                  "access.setCurrentOutMessage(currentOutMsg);\n");

    if (isSubMapped && isArrayAttr) {
      type = Message.MSG_TYPE_ARRAY_ELEMENT;
      String lengthName = "length" + (lengthCounter++);
      
      
      String mappableArrayName = "mappableObject" + (objectCounter++);
      
      boolean isDomainObjectMapper = false;
      
      contextClassStack.push(contextClass);
      String subClassName = null;
      NodeList children = nextElt.getChildNodes();
      //contextClass = null;
      try {
    	subClassName = MappingUtils.getFieldType(contextClass, ref);
      	contextClass = Class.forName(subClassName, false, loader);
      } catch (Exception e) { 
    		isDomainObjectMapper = contextClass.isAssignableFrom(DomainObjectMapper.class);
    		subClassName = "com.dexels.navajo.mapping.bean.DomainObjectMapper";
    		contextClass = com.dexels.navajo.mapping.bean.DomainObjectMapper.class;
        	if ( isDomainObjectMapper ) {
        		type = "java.lang.Object";
        	} else {
        		throw new Exception("Could not find adapter: " + subClassName); 
        	}
      }

      addDependency("dependentObjects.add( new JavaDependency( -1, \"" + subClassName + "\"));\n", "JAVA"+subClassName);

      // Extract ref.... 
      if ( mapPath == null ) {
    	  if ( isDomainObjectMapper ) {
    		  result.append(printIdent(ident + 2) + "try {\n");
    		  result.append(printIdent(ident + 2) +
    				  mappableArrayName + " = com.dexels.navajo.mapping.bean.DomainObjectMapper.createArray( (Object []) ((" + className + ") currentMap.myObject).getDomainObjectAttribute(\"" +
    				   ref + "\", null) ) ;\n");
    		  result.append(printIdent(ident + 2) + "} catch (Exception e) {\n");
    		  result.append(printIdent(ident + 2) + "  String subtype = ((" + className + ") currentMap.myObject).getDomainObjectAttribute(\"" +
   				   ref + "\", null).getClass().getName();\n");
    		  result.append(printIdent(ident + 2) + "  throw new Exception(\" Could not cast " + ref + 
    				  "(type = \" + subtype + \") to an array\");\n");
    		  result.append(printIdent(ident + 2) + "}\n");
    	  } else {
    		  result.append(printIdent(ident + 2) + mappableArrayName +
    				  " = ((" + className + ") currentMap.myObject).get" +
    				  ( (ref.charAt(0) + "").toUpperCase() + ref.substring(1)) + "();\n");
    	  }
      } else {
    	  if ( isDomainObjectMapper ) { 
    		  result.append(printIdent(ident + 2) + "try {\n");
    		  result.append(printIdent(ident + 2) +
    				  mappableArrayName + " = com.dexels.navajo.mapping.bean.DomainObjectMapper.createArray( (Object []) ((" + className + ") findMapByPath( \"" + mapPath + "\")).getDomainObjectAttribute(\"" +
    				   ref + "\", null) ) ;\n");
    		  result.append(printIdent(ident + 2) + "} catch (Exception e) {\n");
    		  result.append(printIdent(ident + 2) + "  String subtype = ((" + className + ") findMapByPath( \"" + mapPath + "\")).getDomainObjectAttribute(\"" +
   				   ref + "\", null).getClass().getName();\n");
    		  result.append(printIdent(ident + 2) + "  throw new Exception(\" Could not cast " + ref + 
    				  "(type = \" + subtype + \") to an array\");\n");
    		  result.append(printIdent(ident + 2) + "}\n");
    	  } else {
    		  result.append(printIdent(ident + 2) + mappableArrayName +
    				  " = ((" + className + ") findMapByPath( \"" + mapPath + "\")).get" +
    				  ( (ref.charAt(0) + "").toUpperCase() + ref.substring(1)) + "();\n");
    	  }
      }

      String mappableArrayDefinition = "Object [] " + mappableArrayName + " = null;\n";
      variableClipboard.add(mappableArrayDefinition);
      
      
      result.append(printIdent(ident + 2) + "int " + lengthName + " = " + 
    		  "(" + mappableArrayName + " == null ? 0 : " + mappableArrayName + ".length);\n");
      
      String startIndexVar = "startIndex"+(startIndexCounter++);

      result.append(printIdent(ident + 2) + "int " + startIndexVar + " = " + startIndex + ";\n");
      String startElementVar = "startWith"+(startElementCounter);
      String offsetElementVar = "offset"+(startElementCounter++);

        // Use a different than 0 as start for for loop.
        // result.append(printIdent(ident) + "count = " +

      result.append(printIdent(ident + 2) + "int " + startElementVar + " = " +
      (startElement.equals("") ? "0" : "((Integer) Expression.evaluate(\"" + startElement + "\", access.getInDoc(), currentMap, currentInMsg, currentParamMsg).value).intValue()") + ";\n");
      result.append(printIdent(ident + 2) + "int " + offsetElementVar + " = " +
      (elementOffset.equals("") ? "1" : "((Integer) Expression.evaluate(\"" + elementOffset + "\", access.getInDoc(), currentMap, currentInMsg, currentParamMsg).value).intValue()") + ";\n");

      result.append(printIdent(ident + 2) + "for (int i" + (ident + 2) +
                    " = " + startElementVar + "; i" + (ident + 2) + " < " + lengthName + "; i" +
                    (ident + 2) + " = i" + (ident + 2) +"+"+ offsetElementVar + ") {\n if (!kill) {\n");

      result.append(printIdent(ident + 4) + "treeNodeStack.push(currentMap);\n");
      result.append(printIdent(ident + 4) +
                    "currentMap = new MappableTreeNode(access, currentMap, " + mappableArrayName + "[i" + (ident + 2) + "], true);\n");
  
      
      // If filter is specified, evaluate filter first:
      if (!filter.equals("")) {
        result.append(printIdent(ident + 4) + "if (Condition.evaluate(" +
                      replaceQuotes(filter) +
                      ", access.getInDoc(), currentMap, currentInMsg, currentParamMsg)) {\n");
        ident += 2;
      }

      if ( n.getNodeName().equals("message")) {
	      result.append(printIdent(ident + 4) +
	                    "outMsgStack.push(currentOutMsg);\n");
	      result.append(printIdent(ident + 4) +
	                    "currentOutMsg = MappingUtils.getMessageObject(\"" +
	                    MappingUtils.getBaseMessageName(messageName) +
	                    "\", currentOutMsg, true, access.getOutputDoc(), false, \"\", " + ((startIndex == -1) ? "-1" : startIndexVar + "++") + ");\n");
	      result.append(printIdent(ident + 4) +
	                    "access.setCurrentOutMessage(currentOutMsg);\n");
      } else { // parammessage.
        result.append(printIdent(ident + 4) +
        "paramMsgStack.push(currentParamMsg);\n");
result.append(printIdent(ident + 4) +
        "currentParamMsg = MappingUtils.getMessageObject(\"" +
        MappingUtils.getBaseMessageName(messageName) +
        "\", currentParamMsg, true, access.getInDoc(), false, \"\", " + ((startIndex == -1) ? "-1" : startIndexVar + "++") + ");\n");
      }
      
      result.append(printIdent(ident) +
      "if ( currentMap.myObject instanceof Mappable ) {  ((Mappable) currentMap.myObject).load(access);}\n");
       
      String subObjectName = "mappableObject" + (objectCounter++);
      result.append(printIdent(ident + 4) + subObjectName +
                    " = (" + subClassName + ") currentMap.myObject;\n");

      String objectDefinition = subClassName + " " + subObjectName + " = null;\n";
      variableClipboard.add(objectDefinition);

      for (int i = 0; i < children.getLength(); i++) {
        if (children.item(i)instanceof Element) {
          result.append(compile(ident + 4, children.item(i), subClassName, subObjectName,deps));
        }
      }

      contextClass = contextClassStack.pop();
      //System.err.println("802: popped: " + contextClass);
      
      result.append(printIdent(ident + 2) + "MappingUtils.callStoreMethod(currentMap.myObject);\n");
      
      if (n.getNodeName().equals("message")) {
	      result.append(printIdent(ident + 2) +
	                    "currentOutMsg = (Message) outMsgStack.pop();\n");
	      result.append(printIdent(ident + 2) +
	                    "access.setCurrentOutMessage(currentOutMsg);\n");
      }  else {
    	result.append(printIdent(ident) +
        "currentParamMsg = (Message) paramMsgStack.pop();\n");
      }

      if (!filter.equals("")) {
        ident -= 2;
        result.append(printIdent(ident + 4) + "}\n");
      }

      result.append(printIdent(ident + 2) +
                    "currentMap.setEndtime();\n" + 
                    "currentMap = (MappableTreeNode) treeNodeStack.pop();\n");
      result.append(printIdent(ident + 2) +
                    "}\n} // EOF Array map result from contextMap \n");
    }
    else if (isSubMapped) { // Not an array

     
       if ( mapPath == null ) {
    	  result.append(printIdent(ident + 2) + "treeNodeStack.push(currentMap);\n");
    	  
    	  if ( className.equals("com.dexels.navajo.mapping.bean.DomainObjectMapper")) {
    		  
     
    		  result.append(printIdent(ident + 2) +
    				  "currentMap = new MappableTreeNode(access, currentMap, new com.dexels.navajo.mapping.bean.DomainObjectMapper( ((" +
    				  className + ") currentMap.myObject).getDomainObjectAttribute(\"" +
    				   ref + "\", null) ), false);\n");
    		
    	  } else {
    		  result.append(printIdent(ident + 2) +
    				  "currentMap = new MappableTreeNode(access, currentMap, ((" +
    				  className + ") currentMap.myObject).get" +
    				  ( (ref.charAt(0) + "").toUpperCase() + ref.substring(1)) +
    		  "(), false);\n");
    	  }
    	  
      } else {
    	  String localObjectName = "mappableObject" + (objectCounter++);
    	  result.append(printIdent(ident + 2) + "Object " + localObjectName + " = findMapByPath( \"" + mapPath + "\");\n");
    	  result.append(printIdent(ident + 2) + "treeNodeStack.push(currentMap);\n");
    	  
    	  if ( className.equals("com.dexels.navajo.mapping.bean.DomainObjectMapper")) {
    		  result.append(printIdent(ident + 2) +
    				  "currentMap = new MappableTreeNode(access, currentMap,new com.dexels.navajo.mapping.bean.DomainObjectMapper( ((" +
    				  className + ")" + localObjectName + ").getDomainObjectAttribute(\"" + 
    				   ref + "\", null) ), false);\n"); 
    	  } else {
    		  result.append(printIdent(ident + 2) +
    				  "currentMap = new MappableTreeNode(access, currentMap, ((" +
    				  className + ")" + localObjectName + ").get" + 
    				  ( (ref.charAt(0) + "").toUpperCase() + ref.substring(1)) +
    		  "(), false);\n");  
    	  }
      }
      
      
      
      result.append(printIdent(ident + 2) +
                    "if (currentMap.myObject != null) {\n");
      result.append(printIdent(ident) +
      "if ( currentMap.myObject instanceof Mappable ) {  ((Mappable) currentMap.myObject).load(access);}\n");
      
      contextClassStack.push(contextClass);
      String subClassName = null;
      
      if ( DomainObjectMapper.class.isAssignableFrom(contextClass) ) {
    	  subClassName = "com.dexels.navajo.mapping.bean.DomainObjectMapper";
    	  contextClass = DomainObjectMapper.class;
      } else {
    	  subClassName = MappingUtils.getFieldType(contextClass, ref);
    	  contextClass = null;
    	  try {
    		  contextClass = Class.forName(subClassName, false, loader);
    	  } catch (Exception e) { throw new Exception("Could not find adapter " + subClassName); }

      }
   
      addDependency("dependentObjects.add( new JavaDependency( -1, \"" + subClassName + "\"));\n", "JAVA"+subClassName);
      
      String subObjectName = "mappableObject" + (objectCounter++);
      result.append(printIdent(ident + 4) + subObjectName +
                    " = (" + subClassName + ") currentMap.myObject;\n");

      String objectDefinition = subClassName + " " + subObjectName + " = null;\n";
      variableClipboard.add(objectDefinition);

      NodeList children = nextElt.getChildNodes();
      for (int i = 0; i < children.getLength(); i++) {
        result.append(compile(ident + 4, children.item(i), subClassName, subObjectName,deps));
      }

      contextClass = contextClassStack.pop();
     
      result.append(printIdent(ident + 2) + "}\n");
      result.append(printIdent(ident + 2) +
                    "currentMap.setEndtime();\n" + 
                    "MappingUtils.callStoreMethod(currentMap.myObject);\n" +
                    "currentMap = (MappableTreeNode) treeNodeStack.pop();\n");
    }
    else { // Just some new tags under the "message" tag.
      NodeList children = n.getChildNodes();
      for (int i = 0; i < children.getLength(); i++) {
        result.append(compile(ident + 2, children.item(i), className,
                              objectName,deps));
      }
    }
    
    if (n.getNodeName().equals("message")) {
	    result.append(printIdent(ident) +
	                  "currentOutMsg = (Message) outMsgStack.pop();\n");
	    result.append(printIdent(ident) +
	                  "access.setCurrentOutMessage(currentOutMsg);\n");
    } else {
    	result.append(printIdent(ident) +
        "currentParamMsg = (Message) paramMsgStack.pop();\n");
    }
    
    
    result.append(printIdent(ident) + "}\n } // EOF messageList for \n");

    if (conditionClause) {
      ident -= 2;
      result.append(printIdent(ident) + "} // EOF message condition \n");
    }
    
    return result.toString();
  }

public String propertyNode(int ident, Element n, boolean canBeSubMapped, String className, String objectName) throws
      Exception {
    StringBuffer result = new StringBuffer();

    String propertyName = n.getAttribute("name");
    String direction = n.getAttribute("direction");
    String type = n.getAttribute("type");
    String subtype = n.getAttribute("subtype");
    String lengthStr = n.getAttribute("length");
    int length = ((lengthStr != null && !lengthStr.equals(""))  ? Integer.parseInt(lengthStr) : -1);
    String value = n.getAttribute("value");
    String description = n.getAttribute("description");
    String cardinality = n.getAttribute("cardinality");
    String condition = n.getAttribute("condition");

    value = (value == null) || (value.equals("")) ? "" : value;
    type = (type == null) ? "" : type;
    subtype = (subtype == null) ? "" : subtype;
    description = (description == null) ? "" : description;
    cardinality = (cardinality == null || cardinality.equals("")) ? "1" :
        cardinality;
    condition = (condition == null) ? "" : condition;

    boolean conditionClause = false;
    if (!condition.equals("")) {
      conditionClause = true;
      result.append(printIdent(ident) + "if (Condition.evaluate(" +
                    replaceQuotes(condition) +
                    ", access.getInDoc(), currentMap, currentInMsg, currentParamMsg)) { \n");
      ident += 2;
    }

    NodeList children = n.getChildNodes();

    boolean hasChildren = false;
    boolean isSelection = false;
    boolean isMapped = false;
    Element mapNode = null;

    StringBuffer optionItems = new StringBuffer();

    int exprCount = countNodes(children, "expression");

    result.append(printIdent(ident) + "matchingConditions = false;\n");
    Class localContextClass = null;
    for (int i = 0; i < children.getLength(); i++) {
      hasChildren = true;
      // Has condition;
      if (children.item(i).getNodeName().equals("expression")) {
        result.append(expressionNode(ident, (Element) children.item(i),
                                     --exprCount, className, objectName));
      } else if (children.item(i).getNodeName().equals("option")) {
        isSelection = true;
        String optionCondition = ( (Element) children.item(i)).getAttribute("condition");
        String optionName = ( (Element) children.item(i)).getAttribute("name");
        String optionValue = ( (Element) children.item(i)).getAttribute("value");
        String selectedValue = ( (Element) children.item(i)).getAttribute("selected");

        type = "selection";
        
        String selected = selectedValue;
        if ( ! (selected.equals("0") || selected.equals("1"))) {
        	selected = "Expression.evaluate(" +
                  replaceQuotes(selectedValue) +
                  ", access.getInDoc(), currentMap, currentInMsg, currentParamMsg, currentSelection, null).value";
        }
        
        // Created condition statement if condition is given!
        String conditional = (optionCondition != null && !optionCondition.equals("")) ?
                             "if (Condition.evaluate(" + replaceQuotes(optionCondition) + ", access.getInDoc(), currentMap, currentInMsg, currentParamMsg))\n" : "";
        optionItems.append(conditional+
            "p.addSelection(NavajoFactory.getInstance().createSelection(access.getOutputDoc(), \"" +
            optionName + "\", \"" + optionValue + "\", " + selected + "));\n");
      }
      else if (children.item(i).getNodeName().equals("map")) { // ABout to map a "selection" property!!!
        if (!canBeSubMapped) {
          throw new Exception("This property can not be submapped: " +
                              propertyName);
        }
        if (!type.equals("selection")) {
          throw new Exception("Only selection properties can be submapped: " +
                              propertyName);
        }
        mapNode = (Element) children.item(i);
        isMapped = true;
        isSelection = true;

      } else if (children.item(i) instanceof Element ) {
        String tagValue = "<" + n.getNodeName() + " name=\"" + propertyName + "\">";
        throw new Exception("Illegal child tag <" + children.item(i).getNodeName() + "> in " + tagValue + " (Check your script) ");
      }
    }

    if (!hasChildren || isSelection) {
      result.append(printIdent(ident) + "sValue = new String(\"" + value +
                    "\");\n");
      result.append(printIdent(ident) + "type = \"" + type + "\";\n");
    }
    else {
      result.append(printIdent(ident) +
          "type = (sValue != null) ? MappingUtils.determineNavajoType(sValue) : \"" +
                    type + "\";\n");
    }

    result.append(printIdent(ident) + "subtype = \"" + subtype + "\";\n");

    if (n.getNodeName().equals("property")) {
      result.append(printIdent(ident) +
                    "p = MappingUtils.setProperty(false, currentOutMsg, \"" +
                    propertyName + "\", sValue, type, subtype, \"" + direction +
                    "\", \"" + description + "\", " +
                    length +
                    ", access.getOutputDoc(), access.getInDoc(), !matchingConditions);\n");
    }
    else { // parameter
      result.append(printIdent(ident) +
                    "MappingUtils.setProperty(true, currentParamMsg, \"" +
                    propertyName + "\", sValue, type, subtype, \"" + direction +
                    "\", \"" + description + "\", " +
                    length +
                    ", access.getOutputDoc(), access.getInDoc(), !matchingConditions);\n");
    }

    if (isMapped) {
      try {
    	  localContextClass = Class.forName(className, false, loader);
      } catch (Exception e) { throw new Exception("Could not find adapter: " + className); }
      
      addDependency("dependentObjects.add( new JavaDependency( -1, \"" + className + "\"));\n", "JAVA"+className);
      if(mapNode==null) {
    	  throw new IllegalStateException("Unexpected null mapNode");
      }
      String ref = mapNode.getAttribute("ref");
      String filter = mapNode.getAttribute("filter");
      
      String mappableArrayName = "mappableObject" + (objectCounter++);
      result.append(printIdent(ident + 2) + mappableArrayName +
    		  " = " + objectName + ".get" + ( (ref.charAt(0) + "").toUpperCase() + ref.substring(1)) + "();\n");

      String mappableArrayDefinition = "Object [] " + mappableArrayName + " = null;\n";
      variableClipboard.add(mappableArrayDefinition);
      
      
      result.append(printIdent(ident + 2) + "for (int i" + (ident + 2) +
                    " = 0; i" + (ident + 2) + " < " + mappableArrayName + ".length; i" + 
                    (ident + 2) + "++) {\n if (!kill) {\n");
      result.append(printIdent(ident + 4) + "treeNodeStack.push(currentMap);\n");
      result.append(printIdent(ident + 4) +
                    "currentMap = new MappableTreeNode(access, currentMap, " +
                    mappableArrayName + "[i" + (ident + 2) + "], true);\n");
      
      // If filter is specified, evaluate filter first (31/1/2007)
      if (!filter.equals("")) {
        result.append(printIdent(ident + 4) + "if (Condition.evaluate(" +
                      replaceQuotes(filter) +
                      ", access.getInDoc(), currentMap, currentInMsg, currentParamMsg)) {\n");
        ident += 2;
      } 
      
      result.append(printIdent(ident + 4) + "String optionName = \"\";\n");
      result.append(printIdent(ident + 4) + "String optionValue = \"\";\n");
      result.append(printIdent(ident + 4) + "boolean optionSelected = false;\n");
      children = mapNode.getChildNodes();
      String subClassName = MappingUtils.getFieldType(localContextClass, ref);
      String subClassObjectName = "mappableObject" + (objectCounter++);
      result.append(printIdent(ident + 4) +
                    subClassObjectName + " = (" + subClassName +
                    ") currentMap.myObject;\n");

      String objectDefinition = subClassName + " " + subClassObjectName + " = null;\n";
      variableClipboard.add(objectDefinition);

      for (int i = 0; i < children.getLength(); i++) {
        if (children.item(i).getNodeName().equals("property")) {
          Element elt = (Element) children.item(i);
          String subPropertyName = elt.getAttribute("name");
          if (! (subPropertyName.equals("name") ||
                 subPropertyName.equals("value") ||
                 subPropertyName.equals("selected"))) {
            throw new Exception("Only 'name' or 'value' named properties expected when submapping a 'selection' property");
          }
          NodeList expressions = elt.getChildNodes();
          int leftOver = countNodes(expressions, "expression");
          ////System.out.println("LEFTOVER = " + leftOver + ", CHILD NODES = " + expressions.getLength());

          for (int j = 0; j < expressions.getLength(); j++) {
            ////System.out.println("expression.item("+j+") = " + expressions.item(j));
            if ( (expressions.item(j)instanceof Element) &&
                expressions.item(j).getNodeName().equals("expression")) {
              result.append(expressionNode(ident + 4,
                                           (Element) expressions.item(j),
                                           --leftOver, subClassName,
                                           subClassObjectName));
            }
          }
          if (subPropertyName.equals("name")) {
            result.append(printIdent(ident + 4) +
                "optionName = (sValue != null) ? sValue + \"\" : \"\";\n");
          }
          else if (subPropertyName.equals("value")) {
            result.append(printIdent(ident + 4) +
                "optionValue = (sValue != null) ? sValue + \"\" : \"\";\n");
          }
          else {
            result.append(printIdent(ident + 4) + "optionSelected = (sValue != null) ? ((Boolean) sValue).booleanValue() : false;\n");
          }
        } else if (children.item(i).getNodeName().equals("debug")) {
          result.append(debugNode(ident, (Element) children.item(i)));
        } else if (children.item(i).getNodeName().equals("param")) {
        	result.append(propertyNode(ident, (Element) children.item(i), false, className, objectName)); 
        }
        else if (children.item(i) instanceof Element) {
          throw new Exception(
              "<property> tag expected while sub-mapping a selection property: " + children.item(i).getNodeName());
        }
      }
      result.append(printIdent(ident + 4) + "p.addSelection(NavajoFactory.getInstance().createSelection(access.getOutputDoc(), optionName, optionValue, optionSelected));\n");
      
      // If filter is specified add closing bracket (31/1/2007)
      if (!filter.equals("")) {
          ident -= 2;
          result.append(printIdent(ident + 4) + "}\n");
      }
      
      result.append(printIdent(ident + 4) +
                    "currentMap.setEndtime();\ncurrentMap = (MappableTreeNode) treeNodeStack.pop();\n");
      result.append(printIdent(ident + 2) +
                    "}\n} // EOF Array map result to property\n");
    }

    if (isSelection) { // Set selection property stuff.
      result.append(optionItems.toString());
    }
    if ( n.getNodeName().equals("property") ) {
    	result.append("p.setCardinality(\"" + cardinality + "\");\n");
    }
    if (conditionClause) {
      ident -= 2;
      result.append(printIdent(ident) + "} // EOF property condition \n");
    }

    return result.toString();

  }

  private final void checkDependentFieldResource(Class localContextClass, String fieldName, ArrayList<String> expressionValues, List<Dependency> deps) {
	  
	  if ( !(HasDependentResources.class.isAssignableFrom(localContextClass) )) {
		  return;
	  }
	  
	  if ( expressionValues == null ) {
		  return;
	  }
	  
	  for ( int all = 0; all < expressionValues.size(); all++ ) {
		  
		  String expressionValue = expressionValues.get(all);
		  
		  DependentResource [] dependentFields = instantiatedAdapters.get(localContextClass);

		  if ( dependentFields == null && HasDependentResources.class.isAssignableFrom(localContextClass) ) {
			  try {
				  HasDependentResources hr = (HasDependentResources) localContextClass.newInstance();
				  dependentFields = hr.getDependentResourceFields();
			  } catch (Throwable t) {
				  logger.error("Dependency detection problem:",t);
			  }
			  instantiatedAdapters.put(localContextClass, dependentFields);
		  }

		  if ( dependentFields == null ) {
			  return;
		  }

		  for (int i = 0; i < dependentFields.length; i++) {
			  if ( fieldName.equals(dependentFields[i].getValue())) {

				  if ( dependentFields[i] instanceof GenericMultipleDependentResource ) {
					  Class<? extends AdapterFieldDependency> depClass = dependentFields[i].getDependencyClass();
					  try {
						  Constructor c = depClass.getConstructor(new Class[]{long.class, String.class, String.class, String.class});
						  AdapterFieldDependency afd = (AdapterFieldDependency) c.newInstance(new Object[]{-1, localContextClass.getName(), dependentFields[i].getType(), expressionValue});
						  deps.add(afd);
						  AdapterFieldDependency [] allDeps = (AdapterFieldDependency []) afd.getMultipleDependencies();
						  for ( int a = 0; a < allDeps.length; a++ ) {
							  addDependency("dependentObjects.add( new " + depClass.getName() + "(-1, \"" + allDeps[a].getJavaClass() + 
									  "\", \"" + 
									  allDeps[a].getType() + "\", \"" +  allDeps[a].getId() + "\"));\n", 
									  "FIELD" + allDeps[a].getJavaClass() + ";" + allDeps[a].getType() + ";" + 
									  fieldName + ";" + allDeps[a].getId());
							  deps.add(allDeps[a]);
						  }
						  
					  } catch (Exception e) {
//						  e.printStackTrace();
					  } 
				  } else {
					  addDependency("dependentObjects.add( new AdapterFieldDependency(-1, \"" + localContextClass.getName() + "\", \"" + 
							  dependentFields[i].getType() + "\", \"" +  expressionValue + "\"));\n", 
							  "FIELD" + localContextClass.getName() + ";" + dependentFields[i].getType() + ";" + 
							  fieldName + ";" + expressionValue);
					  Dependency d = new AdapterFieldDependency(-1, localContextClass.getName(), dependentFields[i].getType(), expressionValue);
					  deps.add(d);
				  }
			  }
		  }
	  }
  }
  
  @SuppressWarnings("unchecked")
public String fieldNode(int ident, Element n, String className,
                          String objectName, List<Dependency> dependencies) throws Exception {

    StringBuffer result = new StringBuffer();

    String attributeOriginal = n.getAttribute("name");
    String condition = n.getAttribute("condition");
    String attribute = null;
    
    String mapPath = null;
    
    if (attributeOriginal.indexOf("/")!=-1) {
        attribute = attributeOriginal.substring(attributeOriginal.lastIndexOf('/')+1, attributeOriginal.length());
        mapPath = attributeOriginal.substring(0,attributeOriginal.lastIndexOf('/'));
   } else {
        attribute = attributeOriginal;
     }

    if (attribute == null || attribute.equals(""))
      throw new Exception("Name attribute is required for field tags");

    condition = (condition == null) ? "" : condition;

    String totalMethodName = "set" + (attribute.charAt(0) + "").toUpperCase()
        + attribute.substring(1, attribute.length());
    
    String methodName = null;
    // TODO Strip methodName
    if (totalMethodName.indexOf("/")!=-1) {
        methodName = totalMethodName.substring(totalMethodName.lastIndexOf('/')+1, totalMethodName.length());
     } else {
        methodName = totalMethodName;
    }
    
    
    
    NodeList children = n.getChildNodes();

    if (!condition.equals("")) {
      result.append(printIdent(ident) + "if (Condition.evaluate(" +
                    replaceQuotes(condition) +
                    ", access.getInDoc(), currentMap, currentInMsg, currentParamMsg)) { \n");
    }
    else {
      result.append(printIdent(ident) + "if (true) {\n");
    }
    // Expression nodes.
    boolean isMapped = false;
    Element mapNode = null;

    int exprCount = countNodes(children, "expression");
    ArrayList<String> exprValues = new ArrayList<String>();
    for (int i = 0; i < children.getLength(); i++) {
      // Has condition;
      if (children.item(i).getNodeName().equals("expression")) {
        result.append(expressionNode(ident + 2, (Element) children.item(i),--exprCount, className, objectName));
        Boolean b = new Boolean(false);
        exprValues.add((String) getExpressionValue(((Element) children.item(i)), b)[0]);
      }
      else if (children.item(i).getNodeName().equals("map")) {
        isMapped = true;
        mapNode = (Element) children.item(i);
      }
    }

    if (!isMapped) {
      String castedValue = "";
      boolean isDomainObjectMapper = false;
      try {
        Class localContextClass = null;
    	
		try {
            if (mapPath!=null) {
            	localContextClass = locateContextClass(mapPath, 0);
            } else {
            	if(Version.osgiActive()) {
            		localContextClass = resolveClassUsingService(className);
            	} else {
                	localContextClass = Class.forName(className, false, loader);
            	}
            }
            
          } catch (Exception e) { throw new Exception("Could not find adapter: " + className,e); }
          
          addDependency("dependentObjects.add( new JavaDependency( -1, \"" + className + "\"));\n", "JAVA"+className);
          dependencies.add(new JavaDependency(-1, className));
         
        String type = null;
       
        try {
//        	logger.info("Attr: "+attribute+" class: "+localContextClass);
        	type = MappingUtils.getFieldType(localContextClass, attribute);
        	checkDependentFieldResource(localContextClass, attribute, exprValues,dependencies);	
        } catch (Exception e) { 
        	isDomainObjectMapper = localContextClass.isAssignableFrom(DomainObjectMapper.class);
        	if ( isDomainObjectMapper ) {
        		type = "java.lang.Object";
        	} else {
        		throw new Exception("Could not find field: " + attribute + " in adapter " + localContextClass.getName(),e);
        	}
        }
        
        if (type.equals("java.lang.String")) {
          castedValue = "(String) sValue";
        } else 
        if (type.equals("com.dexels.navajo.document.types.ClockTime")) {
          castedValue = "(com.dexels.navajo.document.types.ClockTime) sValue";
        }
        else if (type.equals("int")) {
          castedValue = "((Integer) sValue).intValue()";
        }
        else if (type.equals("double")) {
          castedValue = "((Double) sValue).doubleValue()";
        }
        else if (type.equals("java.util.Date")) {
          castedValue = "((java.util.Date) sValue)";
        }
        else if (type.equals("boolean")) {
          castedValue = "((Boolean) sValue).booleanValue()";
        }
        else if (type.equals("float")) { // sValue is never float, internally always Double!
          castedValue = "(new Float(sValue+\"\")).floatValue()";
        }
        else if (type.equals("com.dexels.navajo.document.types.Binary")) {
          castedValue = "((com.dexels.navajo.document.types.Binary) sValue)";
        }
        else if (type.equals("com.dexels.navajo.document.types.Money")) {
          castedValue = "((com.dexels.navajo.document.types.Money) sValue)";
        }
        else if (type.equals("com.dexels.navajo.document.types.Percentage")) {
          castedValue = "((com.dexels.navajo.document.types.Percentage) sValue)";
        }
        else if (type.equals("java.lang.Integer")) {
        	castedValue = "((Integer) sValue)";
        }
        else if (type.equals("java.lang.Long")) {
        	castedValue = "((Long) sValue)";
        }
        else if (type.equals("java.lang.Float")) { // sValue is never float, internally always Double!
        	castedValue = "new Float(sValue+\"\")";
        }
        else if (type.equals("java.lang.Double")) {
        	castedValue = "((Double) sValue)";
        }
        else if (type.equals("java.lang.Boolean")) {
        	castedValue = "((Boolean) sValue)";
        }
        else {
          castedValue = "sValue";
        }
      }
      catch (ClassNotFoundException e) {
        throw new UserException(-1, "Error in script: could not find mappable object: " + className,e);
      }
      
      if (mapPath!=null) {
    	  if ( !isDomainObjectMapper ) {
    		  result.append(printIdent(ident + 2) + "(("+locateContextClass(mapPath, 0).getName()+")findMapByPath(\""+mapPath+"\"))." + methodName + "(" +
    				  castedValue + ");\n");    
    	  } else {
    		  result.append(printIdent(ident + 2) + 
    				  "(("+locateContextClass(mapPath, 0).getName()+
    				  ")findMapByPath(\""+mapPath+"\")).setDomainObjectAttribute(\"" + attribute + "\"," +
    				  castedValue + ");\n"); 
    	  }
      } else {
    	  if ( !isDomainObjectMapper ) {
    		  result.append(printIdent(ident + 2) + objectName + "." + methodName + "(" +
    				  castedValue + ");\n");
    	  } else {
    		  // set  attribute in excluded fields.
    		  // TODO: USE INTROSPECTION METHOD TO CALL METHOD ON PROXIED DOMAIN OBJECT...
    		  result.append(printIdent(ident + 2) + objectName + ".setDomainObjectAttribute(\"" + attribute + "\"," +
    				  castedValue + ");\n");
    	  }
      }
      
    }
    else { // Field with ref: indicates that a message or set of messages is mapped to attribute (either Array Mappable or singular Mappable)
        if(mapNode==null) {
      	  throw new IllegalStateException("Unexpected null mapNode");
        }
      String ref = mapNode.getAttribute("ref");
      boolean isParam = false;
      
      if ( ref.indexOf("[") != -1 ) { // remove square brackets...
    	  ref = ref.replace('[', ' ');
    	  ref = ref.replace(']', ' ');
    	  ref = ref.trim();
      }
      
      if (ref.startsWith("/@")) { // replace @ with __parms__ 'parameter' message indication.
      	ref = ref.replaceAll("@", "__parms__/");
      	isParam = true;
      }
     
      String filter = mapNode.getAttribute("filter");
      filter = (filter == null) ? "" : filter;
      result.append(printIdent(ident + 2) + "// And by the way, my filter is "+filter+"\n");
            
      result.append(printIdent(ident + 2) + "// Map message(s) to field\n");
      String messageListName = "messages" + ident;

      result.append(printIdent(ident + 2) + "ArrayList " + messageListName + " = null;\n");
      result.append(printIdent(ident + 2) + "inSelectionRef = MappingUtils.isSelection(currentInMsg, access.getInDoc(), \"" + ref + "\");\n");
      result.append(printIdent(ident + 2) + "if (!inSelectionRef)\n");
      result.append(printIdent(ident + 4) + messageListName +
          " = MappingUtils.getMessageList(currentInMsg, access.getInDoc(), \"" + ref +
                    "\", \"" + "" + "\", currentMap, currentParamMsg);\n");
      result.append(printIdent(ident + 2) + "else\n");
      result.append(printIdent(ident + 4) + messageListName +
         " = MappingUtils.getSelectedItems(currentInMsg, access.getInDoc(), \"" + ref + "\");\n");

     
      
      contextClassStack.push(contextClass);
      
      /**
       * ADDED:
       */
      Class localContextClass = null;
      try {
    	  if (mapPath!=null) {
    		  localContextClass = locateContextClass(mapPath, 1);
    	  } else {
    		  localContextClass = contextClass;
    	  }

      } catch (Exception e) {throw new Exception("Could not find adapter: " + className,e); }
     
  
      String type = null;
      try {
    	  type = MappingUtils.getFieldType(localContextClass, attribute);
      } catch (Exception e) { throw new Exception("Could not find field: " + attribute + " in adapter " + localContextClass.getName() + ", mappath = " + mapPath); }
     /**
      * END.
      */
      
      //String type = MappingUtils.getFieldType(contextClass, attribute);
      boolean isArray = MappingUtils.isArrayAttribute(localContextClass, attribute);
      
      //System.err.println("TYPE FOR " + attribute + " IS: " + type + ", ARRAY = " + isArray);
      
      try {
    	  contextClass = Class.forName(type, false, loader);
      } catch (Exception e) {
    	  throw new Exception("Could not find adapter: " + type);
      }
      
      addDependency("dependentObjects.add( new JavaDependency( -1, \"" + type + "\"));\n", "JAVA"+type);
      
//      if (!isArray && !MappingUtils.isMappable(contextClass, attribute,loader)) {
//      	throw new TslCompileException(-1, "Not a mappable field: " + attribute, 0, 0);
//      }
      
      if (isArray) {
        String subObjectsName = "subObject" + subObjectCounter;
        String loopCounterName = "j" + subObjectCounter;
        subObjectCounter++;

        String objectDefinition = type + " [] " + subObjectsName + " = null;\n";
        variableClipboard.add(objectDefinition);
        variableClipboard.add("int " + loopCounterName + ";\n");

        result.append(printIdent(ident + 2) + subObjectsName + " = new " + type + "[" + messageListName + ".size()];\n");
        result.append(printIdent(ident + 2) + "for (" +loopCounterName + " = 0; " + loopCounterName + " < " + messageListName + ".size(); " + loopCounterName + "++) {\n if (!kill){\n");
        // currentInMsg, inMsgStack
        ident += 4;
        result.append(printIdent(ident) + "inMsgStack.push(currentInMsg);\n");
        if (isParam) {
        	result.append(printIdent(ident) + "paramMsgStack.push(currentParamMsg);\n");
        }
        result.append(printIdent(ident) + "inSelectionRefStack.push(new Boolean(inSelectionRef));\n");
        
        if (isParam) {
        	result.append(printIdent(ident) + "if (!inSelectionRef)\n");
            result.append(printIdent(ident + 2) + "currentParamMsg = (Message) " + messageListName + ".get(" + loopCounterName + ");\n");
        }
        result.append(printIdent(ident) + "if (!inSelectionRef)\n");
        result.append(printIdent(ident + 2) + "currentInMsg = (Message) " + messageListName + ".get(" + loopCounterName + ");\n");
        result.append(printIdent(ident) + "else\n");
        // currentSelection.
        result.append(printIdent(ident + 2) + "currentSelection = (Selection) " + messageListName + ".get(" + loopCounterName + ");\n");

        // if
        // CONDITION.EVALUATE()!!!!!!!!!!!! {
        // If filter is specified, evaluate filter first:
        if (!filter.equals("")) {
          result.append(printIdent(ident + 4) + "if (inSelectionRef || Condition.evaluate(" + replaceQuotes(filter) + ", access.getInDoc(), currentMap, currentInMsg, currentParamMsg)) {\n");
          ident += 2;
        }


        result.append(printIdent(ident) + "treeNodeStack.push(currentMap);\n");
        result.append(printIdent(ident) + "currentMap = new MappableTreeNode(access, currentMap, classLoader.getClass(\"" +
                      type + "\").newInstance(), false);\n");

        result.append(printIdent(ident) +
            "if ( currentMap.myObject instanceof Mappable ) {  ((Mappable) currentMap.myObject).load(access);}\n");
        result.append(printIdent(ident) + subObjectsName + "[" +
                      loopCounterName + "] = (" + type +
                      ") currentMap.myObject;\n");
        result.append(printIdent(ident) + "try {\n");
        ident = ident+2;

        children = mapNode.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
          result.append(compile(ident + 2, children.item(i), type, subObjectsName + "[" + loopCounterName + "]",dependencies));
        }

        ident = ident-2;
        result.append(printIdent(ident) + "} catch (Exception e" + ident +
                    ") {\n");
        result.append(printIdent(ident + 2) + "MappingUtils.callKillMethod( " + subObjectsName + "[" + loopCounterName + "]);\n");
        result.append(printIdent(ident + 2) + "throw e" + ident + ";\n");

        result.append(printIdent(ident) + "}\n");

        result.append(printIdent(ident) + "MappingUtils.callStoreMethod(" + subObjectsName + "[" + loopCounterName + "]);\n");

        result.append(printIdent(ident) +
                      "currentMap.setEndtime();\ncurrentMap = (MappableTreeNode) treeNodeStack.pop();\n");

        if (!filter.equals("")) {
          ident -= 2;
          result.append(printIdent(ident + 4) + "}\n");
        }

        result.append(printIdent(ident) + "currentInMsg = (Message) inMsgStack.pop();\n");
        if (isParam) {
        	result.append(printIdent(ident) + "currentParamMsg = (Message) paramMsgStack.pop();\n");
        }
        result.append(printIdent(ident) + "inSelectionRef = ((Boolean) inSelectionRefStack.pop()).booleanValue();\n");
        result.append(printIdent(ident) + "currentSelection = null;\n");

        ident -= 4;
        result.append(printIdent(ident + 2) + "}\n} // FOR loop for " +
                      loopCounterName + "\n");
        
        if ( mapPath == null ) {
        	result.append(printIdent(ident + 2) + objectName + "." + methodName +
        			"(" + subObjectsName + ");\n");
        } else {
        	result.append(printIdent(ident + 2) + "((" + locateContextClass(mapPath, 1).getName() + ") findMapByPath(\"" + mapPath + "\"))." + methodName +
        			"(" + subObjectsName + ");\n");
        }
        
      } else { // Not an array type field, but single Mappable object.
      	
      	// Push current mappable object on stack.
        result.append(printIdent(ident) + "treeNodeStack.push(currentMap);\n");     
        
        // Create instance of object.
        result.append(printIdent(ident) + 
        		"currentMap = new MappableTreeNode(access, currentMap, classLoader.getClass(\"" +  type + "\").newInstance(), false);\n");     
        
        // Create local variable to address new object.
        String subObjectsName = "subObject" + subObjectCounter;        
        subObjectCounter++;                
        
        // push currentInMsg, currentParamMsg and inSelectionRef
        ident += 4;
        result.append(printIdent(ident) + "inMsgStack.push(currentInMsg);\n");
        if (isParam) {
        	result.append(printIdent(ident) + "paramMsgStack.push(currentParamMsg);\n");
        }
        result.append(printIdent(ident) + "inSelectionRefStack.push(new Boolean(inSelectionRef));\n");
        
        if (isParam) {
        	result.append(printIdent(ident) + "if (!inSelectionRef)\n");
            result.append(printIdent(ident + 2) + "currentParamMsg = (Message) " + messageListName + ".get(0);\n");
        }
        result.append(printIdent(ident) + "if (!inSelectionRef)\n");
        result.append(printIdent(ident + 2) + "currentInMsg = (Message) " + messageListName + ".get(0);\n");
        result.append(printIdent(ident) + "else\n");
        // currentSelection.
        result.append(printIdent(ident + 2) + "currentSelection = (Selection) " + messageListName + ".get(0);\n");

        // Call load on object.
        result.append(printIdent(ident) +            
        		"if ( currentMap.myObject instanceof Mappable) { ((Mappable) currentMap.myObject).load(access);}\n");     
        // Assign local variable reference.
        result.append(printIdent(ident) + type + " " + 
        		subObjectsName + " = (" + type +                
				") currentMap.myObject;\n");                
        result.append(printIdent(ident) + "try {\n");        
        ident = ident+2;      
        
        // Recursively dive into children.
        children = mapNode.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
          result.append(compile(ident + 2, children.item(i), type, subObjectsName,dependencies ));
        }  
        
        ident = ident-2;        
        result.append(printIdent(ident) + "} catch (Exception e" + 
        		ident +                    ") {\n");        
        result.append(printIdent(ident + 2) + "MappingUtils.callKillMethod( "+ subObjectsName + ");\n");        
        result.append(printIdent(ident + 2) + "throw e" + ident + ";\n");         
        result.append(printIdent(ident) + "}\n");                
        result.append(printIdent(ident) + "MappingUtils.callStoreMethod(" + subObjectsName + ");\n");         
        
        result.append(printIdent(ident) + "currentInMsg = (Message) inMsgStack.pop();\n");
        if (isParam) {
        	result.append(printIdent(ident) + "currentParamMsg = (Message) paramMsgStack.pop();\n");
        }
        result.append(printIdent(ident) + "inSelectionRef = ((Boolean) inSelectionRefStack.pop()).booleanValue();\n");
        result.append(printIdent(ident) + "currentSelection = null;\n");
        result.append(printIdent(ident) +        
        		"currentMap.setEndtime();\ncurrentMap = (MappableTreeNode) treeNodeStack.pop();\n");
        
        if ( mapPath == null ) {
        	result.append(printIdent(ident + 2) + objectName + "." + methodName +
        			"(" + subObjectsName + ");\n");
        } else {
        	result.append(printIdent(ident + 2) + "((" + locateContextClass(mapPath, 1).getName() + ") findMapByPath(\"" + mapPath + "\"))." + methodName +
        			"(" + subObjectsName + ");\n");
        }
        
      }
      contextClass = contextClassStack.pop();
      //System.err.println("JUST POPPED CONTEXTCLASS: " + contextClass);
      //System.err.println(contextClassStack);
    }
    result.append(printIdent(ident) + "}\n");
    return result.toString();
  }

  private Class resolveClassUsingService(String className) {
	  BundleContext bc = Version.getDefaultBundleContext();
	  Collection<ServiceReference<Class>> sr;
	try {
		sr = bc.getServiceReferences(Class.class, "(adapterClass="+className+")");
		  Class result = bc.getService(sr.iterator().next());
		  return result;
	} catch (InvalidSyntaxException e) {
		logger.error("Adapter resolution error: ",e);
		return null;
	}
	  
}
/**
   * Locate a contextclass in the class stack based upon a mappath.
   * Depending on the way this method is called an additional offset to stack index must be supplied...
   * 
   */
  private Class locateContextClass(String mapPath, int offset) {
	  
	   //System.err.println("Count element: "+count);
//	  System.err.println("in locateContextClass(" + mapPath + ")");
//      System.err.println("STACK: "+contextClassStack);
//      System.err.println("STACK: "+contextClass);
      
	  StringTokenizer st = new StringTokenizer(mapPath,"/");
      
      int count = 0;
      while (st.hasMoreTokens()) {
        String element = st.nextToken();
        if (!"..".equals(element)) {
            System.err.println("Huh? : "+element);
        }
        count++;
      }
      if (count==0) {
        return contextClass;
    }
//      System.err.println("Count element: "+count);
//      System.err.println("STACK: "+contextClassStack);
      Class m = contextClassStack.get(contextClassStack.size()-( count ) - offset);
      //System.err.println("Mappable: "+m);
      return m;
}

  public String breakNode(int ident, Element n) throws Exception {

    StringBuffer result = new StringBuffer();
    String condition = n.getAttribute("condition");
    if (condition.equals("")) {
      result.append(printIdent(ident) + "if (true) {");
    }
    else {
      result.append(printIdent(ident) + "if (Condition.evaluate(" +
                    replaceQuotes(condition) +
                    ", access.getInDoc(), currentMap, currentInMsg, currentParamMsg)) { \n");

    }
    result.append(printIdent(ident + 2) + "throw new BreakEvent();\n");
    result.append(printIdent(ident) + "}\n");

    return result.toString();
  }

  public String debugNode(int ident, Element n) throws Exception {
    StringBuffer result = new StringBuffer();
    String value = n.getAttribute("value");
    result.append(printIdent(ident) + "op = Expression.evaluate(" +
                  replaceQuotes(value) +
                  ", access.getInDoc(), currentMap, currentInMsg, currentParamMsg, currentSelection, null);\n");
    result.append(printIdent(ident) + "Access.writeToConsole(access, \"in PROCESSING SCRIPT: \" + access.rpcName + \" DEBUG INFO: \" + op.value + \"\\n\");\n");
    return result.toString();
  }

  public String requestNode(int ident, Element n) throws Exception {
    StringBuffer result = new StringBuffer();
    return result.toString();
  }

  public String responseNode(int ident, Element n) throws Exception {
    StringBuffer result = new StringBuffer();
    return result.toString();
  }

  public String runningNode(int ident, Element n) throws Exception {
    StringBuffer result = new StringBuffer();
    return result.toString();
  }

public String mapNode(int ident, Element n, List<Dependency> deps) throws Exception {


    StringBuffer result = new StringBuffer();

    String object = n.getAttribute("object");
    String condition = n.getAttribute("condition");
    // If name, is specified it could be an AsyncMap.
    String name = n.getAttribute("name");
    boolean asyncMap = false;
    condition = (condition == null) ? "" : condition;

    boolean conditionClause = false;
    if (!condition.equals("")) {
      conditionClause = true;
      result.append(printIdent(ident) + "if (Condition.evaluate(" +
                    replaceQuotes(condition) +
                    ", access.getInDoc(), currentMap, currentInMsg, currentParamMsg)) { \n");
      ident += 2;
    }

    String className = object;
    if (contextClass!=null) {
    	contextClassStack.push(contextClass);
    } 
    try {
    	contextClass = Class.forName(className, false, loader);
    } catch (Exception e) { throw new Exception("Could not find adapter: " + className+" from classloader: "+loader,e); }

    addDependency("dependentObjects.add( new JavaDependency( -1, \"" + className + "\"));\n", "JAVA"+className);
 
    if (!name.equals("")) { // We have a potential async mappable object.
      ////System.out.println("POTENTIAL MAPPABLE OBJECT " + className);
    //  Class contextClass = null;
       if (contextClass.getSuperclass().getName().equals(
          "com.dexels.navajo.mapping.AsyncMappable")) {
        asyncMap = true;

      }
      else {
        asyncMap = false;
      }
    }

    if (asyncMap) {
      String aoName = "ao" + asyncMapCounter;
      String headerName = "h" + asyncMapCounter;
      String asyncMapFinishedName = "asyncMapFinished" + asyncMapCounter;
      String callbackRefName = "callbackRef" + asyncMapCounter;
      String interruptTypeName = "interruptType" + asyncMapCounter;
      String asyncMapName = "asyncMap" + asyncMapCounter;
      String asyncStatusName = "asyncStatus" + asyncMapCounter;
      String resumeAsyncName = "resumeAsync" + asyncMapCounter;
      asyncMapCounter++;

      variableClipboard.add("boolean " + asyncMapName + ";\n");
      variableClipboard.add("Header " + headerName + ";\n");
      variableClipboard.add("String " + callbackRefName + ";\n");
      variableClipboard.add(className + " " + aoName + ";\n");
      variableClipboard.add("boolean " + asyncMapFinishedName + ";\n");
      variableClipboard.add("boolean " + resumeAsyncName + ";\n");
      variableClipboard.add("String " + asyncStatusName + ";\n");
      variableClipboard.add("String " + interruptTypeName + ";\n");

      
      result.append(printIdent(ident) + "if (!DispatcherFactory.getInstance().getNavajoConfig().isAsyncEnabled()) throw new UserException(-1, \"Set enable_async = true in server.xml to use asynchronous objects\");");
      result.append(printIdent(ident) + asyncMapName +" = true;\n");
      result.append(printIdent(ident) + headerName + " = access.getInDoc().getHeader();\n");
      result.append(printIdent(ident) + callbackRefName + " = " + headerName + ".getCallBackPointer(\""+name+"\");\n");
      result.append(printIdent(ident) + aoName + " = null;\n");
      result.append(printIdent(ident) + asyncMapFinishedName + " = false;\n");
      result.append(printIdent(ident) + resumeAsyncName + " = false;\n");
      result.append(printIdent(ident) + asyncStatusName + " = \"request\";\n\n");
      result.append(printIdent(ident) + "if (" + callbackRefName + " != null) {\n");
      ident+=2;
      result.append(printIdent(ident) + aoName + " = (" + className + ") DispatcherFactory.getInstance().getNavajoConfig().getAsyncStore().getInstance(" + callbackRefName + ");\n");
      result.append(printIdent(ident) + interruptTypeName + " = " + headerName + ".getCallBackInterupt(\""+name+"\");\n");

      result.append(printIdent(ident) + " if (" + aoName + " == null) {\n " +
                    "  throw new UserException( -1, \"Asynchronous object reference instantiation error: no sych instance (perhaps cleaned up?)\");\n}\n");
      result.append(printIdent(ident) + "if (" + interruptTypeName +
          ".equals(\"kill\")) { // Kill thread upon client request.\n" +
                    "   " + aoName + ".stop();\n" +
                    "   DispatcherFactory.getInstance().getNavajoConfig().getAsyncStore().removeInstance(" +
                    callbackRefName + ");\n" +
                    "   return;\n" +
                    "} else if ( " + aoName + ".isKilled() ) " + 
                    "{ " + 
                    "     DispatcherFactory.getInstance().getNavajoConfig().getAsyncStore().removeInstance(" + callbackRefName + ");\n" +
                    "     throw new UserException(-1, " + aoName + ".getException().getMessage()," + aoName + ".getException());\n" +
                    "} else if (" + interruptTypeName +
                    ".equals(\"interrupt\")) {\n" +
                    "   " + aoName + ".interrupt();\n " +
                    "   return;\n" +
                    "} else if (" + interruptTypeName +
                    ".equals(\"resume\")) { " +
                    "  " + aoName + ".resume();\n" + "return;\n" +
                    "}\n");
      ident -= 2;
      result.append(printIdent(ident) + "} else { // New instance!\n");

      result.append(printIdent(ident) + aoName + " = (" + className + ") classLoader.getClass(\"" + object + "\").newInstance();\n" +
                                        "  // Call load method for async map in advance:\n" +
                                        "  " + aoName + ".load(access);\n" +
                                        "  " + callbackRefName + " = DispatcherFactory.getInstance().getNavajoConfig().getAsyncStore().addInstance( " + aoName + ", access );\n" +
                                        "}\n");

      result.append(printIdent(ident) + "treeNodeStack.push(currentMap);\n");
      result.append(printIdent(ident) +
                    "currentMap = new MappableTreeNode(access, currentMap, " + aoName +
                    ", false);\n");

      result.append(printIdent(ident) + "currentMap.name = \"" + name + "\";\n");
      result.append(printIdent(ident) + "currentMap.ref = " + callbackRefName +
                    ";\n");
      result.append(printIdent(ident) + aoName + ".afterReload(\"" + name +
                    "\", " + callbackRefName + ");\n");

      result.append(printIdent(ident) + "try {\n");
      ident += 2;

      result.append(printIdent(ident) + asyncMapFinishedName + " = " + aoName +
                    ".isFinished(access.getOutputDoc(), access);\n");
      NodeList response = n.getElementsByTagName("response");
      boolean hasResponseNode = false;

      if (response.getLength() > 0) {
        hasResponseNode = true;
      }
      NodeList running = n.getElementsByTagName("running");
      boolean hasRunningNode = false;

      if (running.getLength() > 0) {
        hasRunningNode = true;
      }
      NodeList request = n.getElementsByTagName("request");


      boolean whileRunning = ((Element) response.item(0)).getAttribute("while_running").equals("true");
      result.append(printIdent(ident) + "if ("+ asyncMapFinishedName + " || ("+aoName+".isActivated() && " + hasResponseNode + " && " + whileRunning + ")) {\n");
      result.append(printIdent(ident) + "  " +asyncStatusName+ " = \"response\";\n");
      result.append(printIdent(ident) + "  "+aoName+".beforeResponse(access);\n");
      result.append(printIdent(ident) + "  if ("+aoName+".isActivated() && " + whileRunning + ") {\n");
      //result.append(printIdent(ident) + "     "+aoName+".interrupt();\n");
      result.append(printIdent(ident) + "     "+resumeAsyncName+" = true;\n");

      result.append(printIdent(ident) + "  }\n");
      result.append(printIdent(ident) + "} else if (!" + aoName +
                    ".isActivated()) {\n");
      result.append(printIdent(ident) + "  " + asyncStatusName +
                    " = \"request\";\n");
      result.append(printIdent(ident) + "} else if (" + hasRunningNode +
                    ") {\n");
      result.append(printIdent(ident) + "  " + asyncStatusName +
                    " = \"running\";\n");
      //result.append(printIdent(ident) + "  " + aoName + ".interrupt();\n");
      result.append(printIdent(ident) + "  " + resumeAsyncName + " = true;\n");
      result.append(printIdent(ident) + "}\n");

      NodeList children = null;
      result.append(printIdent(ident) + "if (" + asyncStatusName +
                    ".equals(\"response\")) {\n");
      children = response.item(0).getChildNodes();
      for (int i = 0; i < children.getLength(); i++) {
        if (children.item(i)instanceof Element) {
          result.append(compile(ident + 2, children.item(i), className, aoName,deps));
        }
      }
      result.append(printIdent(ident) + "} else if (" + asyncStatusName +
                    ".equals(\"request\")) {\n");
      children = request.item(0).getChildNodes();
      for (int i = 0; i < children.getLength(); i++) {
        if (children.item(i)instanceof Element) {
          result.append(compile(ident + 2, children.item(i), className, aoName,deps));
        }
      }
      result.append(printIdent(ident) + "} else if (" + asyncStatusName +
                    ".equals(\"running\")) {\n");
      children = running.item(0).getChildNodes();
      for (int i = 0; i < children.getLength(); i++) {
        if (children.item(i)instanceof Element) {
          result.append(compile(ident + 2, children.item(i), className, aoName,deps));
        }
      }
      result.append(printIdent(ident) + "}\n");

      result.append(printIdent(ident) +
                    "if ((currentMap.myObject != null)) {\n");
      result.append(printIdent(ident + 2) + "if (!" + asyncMapFinishedName +
                    ") {\n");
      result.append(printIdent(ident + 4) + "if (" + resumeAsyncName + ") { " +
                    aoName +
                    ".afterResponse(); } else { " + aoName +
                    ".afterRequest(); " + aoName + ".runThread(); }\n");
      result.append(printIdent(ident + 2) + "} else {\n");
      result.append(printIdent(ident + 4) +
                    "MappingUtils.callStoreMethod(currentMap.myObject);\n");
      result.append(printIdent(ident + 4) +
                    "DispatcherFactory.getInstance().getNavajoConfig().getAsyncStore().removeInstance(currentMap.ref);\n");
      result.append(printIdent(ident + 2) + "}\n");
      result.append(printIdent(ident) + "}\n");

      result.append(printIdent(ident) + "} catch (Exception e" + ident +
                    ") {\n");
      result.append(printIdent(ident) +
                    " MappingUtils.callKillMethod(currentMap.myObject);\n");
      result.append(printIdent(ident) +
                    " DispatcherFactory.getInstance().getNavajoConfig().getAsyncStore().removeInstance(currentMap.ref);\n");

      result.append(printIdent(ident) + "  throw e" + ident + ";\n");
      result.append(printIdent(ident) + "}\n");

      //result.append(printIdent(ident) +
      //              "currentMap.setEndtime();\ncurrentMap = (MappableTreeNode) treeNodeStack.pop();\n");


    }
    else {

      result.append(printIdent(ident) + "treeNodeStack.push(currentMap);\n");
      result.append(printIdent(ident) + "currentMap = new MappableTreeNode(access, currentMap, classLoader.getClass(\"" +
                    object + "\").newInstance(), false);\n");
      String objectName = "mappableObject" + (objectCounter++);
      result.append(printIdent(ident) + objectName + " = (" +
                    className + ") currentMap.myObject;\n");
      boolean objectMappable = false;
      
      try {
		objectMappable = MappingUtils.isObjectMappable(className);
	} catch (UserException e) {
//		logger.debug("Trouble mapping: {} doing a fallback.",className,e);
		objectMappable = MappingUtils.isObjectMappable(className,loader);
	}
	
      if ( objectMappable) {
    	  result.append(printIdent(ident) + objectName + ".load(access);\n");
      }

      String objectDefinition = className + " " + objectName + " = null;\n";
      variableClipboard.add(objectDefinition);

      result.append(printIdent(ident) + "try {\n");

      NodeList children = n.getChildNodes();
      for (int i = 0; i < children.getLength(); i++) {
        result.append(compile(ident + 2, children.item(i), className,
                              objectName,deps));
      }

      result.append(printIdent(ident) + "} catch (Exception e" + ident +
                    ") {\n");
      result.append(printIdent(ident) + "MappingUtils.callKillMethod( " + objectName + ");\n");
      result.append(printIdent(ident) + "  throw e" + ident + ";\n");
      result.append(printIdent(ident) + "}\n");
      result.append(printIdent(ident) + "MappingUtils.callStoreMethod(" + objectName + ");\n");
      result.append(printIdent(ident) +
                    "currentMap.setEndtime();\ncurrentMap = (MappableTreeNode) treeNodeStack.pop();\n");

    }

    if (conditionClause) {
      ident -= 2;
      result.append(printIdent(ident) + "} // EOF map condition \n");
    }
    if (!contextClassStack.isEmpty()) {
        contextClass = contextClassStack.pop();
       // System.err.println("1798: popped: " + contextClass);
    } else {
        contextClass = null;
    }
// TODO NOOT
    return result.toString();
  }

  /**
   * Resolve include nodes in the script:
   *         <include script="[name of script to be included]"/>
   *
   * @param scriptPath
   * @param n
   * @param parent
   * @throws Exception
   */
  private final void includeNode(String scriptPath, Node n, Document parent) throws Exception {

	included++;
	
	if ( included > 1000 ) {
		throw new UserException(-1, "Too many included scripts!!!");
	}
	
    String script = ( (Element) n ).getAttribute("script");
    if (script == null || script.equals("")) {
      throw new UserException(-1, "No script name found in include tag (missing or empty script attribute): " + n);
    }
       
  //  System.err.println("INCLUDING SCRIPT " + script + " @ NODE " + n);

    // Construct scriptName:
    // First try if applicationGroup specific script exists.
    String fileName =  script + "_" + GenericHandler.applicationGroup;
    
    Document includeDoc = null;
    File includedFile = new File(scriptPath + "/" + fileName  + ".xml");
    
    if ( includedFile.exists() ) {
    	includeDoc = XMLDocumentUtils.createDocument(new FileInputStream(includedFile), false);
    } else {
    	fileName = script;
    	includedFile = new File(scriptPath + "/" + fileName  + ".xml");
    	includeDoc = XMLDocumentUtils.createDocument(new FileInputStream(includedFile), false);
    }
    
    // Add dependency.
    addDependency("dependentObjects.add( new IncludeDependency( new Long(\"" + 
    		                 IncludeDependency.getScriptTimeStamp(fileName) + "\"), \"" + script + "\"));\n", "INCLUDE"+script);
  
    if ( includeDoc.getElementsByTagName("tsl").item(0) == null ) { // Maybe it is navascript??
    	String tslResult = MapMetaData.getInstance().parse(scriptPath + "/" + fileName  + ".xml");
    	includeDoc = XMLDocumentUtils.createDocument(new ByteArrayInputStream(tslResult.getBytes()), false);
    }
    
    NodeList content = includeDoc.getElementsByTagName("tsl").item(0).getChildNodes();
    
    Node nextNode = n.getNextSibling();
    while ( nextNode != null && !(nextNode instanceof Element) ) {
      nextNode = nextNode.getNextSibling();
    }
    if ( nextNode == null | !(nextNode instanceof Element) ) {
      nextNode = n;
    }

    //System.err.println("nextNode = " + nextNode + ", n = " + n);
    if(nextNode==null) {
  	  throw new IllegalStateException("Unexpected null nextNode");
    }

    Node parentNode = nextNode.getParentNode();

    for (int i = 0; i < content.getLength(); i++) {
      Node child = content.item(i);
      Node imported = parent.importNode(child.cloneNode(true), true);
      parentNode.insertBefore(imported, nextNode);
    }

    parentNode.removeChild(n);

  }

  public String compile(int ident, Node n, String className, String objectName, List<Dependency> deps) throws
      Exception {
    StringBuffer result = new StringBuffer();
    //System.err.println("in compile(), className = " + className + ", objectName = " + objectName);

    if (n.getNodeName().equals("include") ) {
    	includeNode(scriptPath, n, n.getParentNode().getOwnerDocument());
    } else
    if (n.getNodeName().equals("map")) {
      result.append(printIdent(ident) +
                    "{ // Starting new mappable object context. \n");
      result.append(mapNode(ident + 2, (Element) n,deps));
      result.append(printIdent(ident) + "} // EOF MapContext \n");
    }
    else if (n.getNodeName().equals("field")) {
      result.append(fieldNode(ident, (Element) n, className, objectName,deps));
    }
    else if ((n.getNodeName().equals("param") && !((Element) n).getAttribute("type").equals("array")  ) ||
             n.getNodeName().equals("property")) {
      result.append(propertyNode(ident, (Element) n, true, className, objectName));
    }
    
    else if (n.getNodeName().equals("message") || 
    		(n.getNodeName().equals("param") && ((Element) n).getAttribute("type").equals("array")  )) {
      String methodName = "execute_sub"+(methodCounter++);
      result.append(printIdent(ident) + "if (!kill) { " + methodName + "(access); }\n");

      StringBuffer methodBuffer = new StringBuffer();

      methodBuffer.append(printIdent(ident) + "private final void " + methodName + "(Access access) throws Exception {\n\n");
      ident+=2;
      methodBuffer.append(printIdent(ident) + "if (!kill) {\n");
      methodBuffer.append(messageNode(ident, (Element) n, className, objectName,deps));
      methodBuffer.append(printIdent(ident) + "}\n");
      ident-=2;
      methodBuffer.append("}\n");

      methodClipboard.add(methodBuffer);
      //
    } else if (n.getNodeName().equals("antimessage")) {
    	  
    	  String messageName = ((Element) n).getAttribute("name");
    	  result.append(printIdent(ident) + "if ( currentOutMsg != null && currentOutMsg.getMessage(\"" + messageName + "\") != null ) {\n");
    	  result.append(printIdent(ident+2) + "   currentOutMsg.removeMessage(currentOutMsg.getMessage(\"" + messageName + "\"));\n");
    	  result.append(printIdent(ident) + "} else \n");
    	  result.append(printIdent(ident) + "if (access.getOutputDoc().getMessage(\"" + messageName + "\") != null) { \n");
    	  result.append(printIdent(ident+2) + "access.getOutputDoc().removeMessage(\"" + messageName + "\");\n");   	  
    	  result.append(printIdent(ident) + "}\n");
    }
    else if (n.getNodeName().equals("methods")) {
      result.append(methodsNode(ident, (Element) n));
    }
    else if (n.getNodeName().equals("debug")) {
      result.append(debugNode(ident, (Element) n));
    }
    else if (n.getNodeName().equals("break")) {
      result.append(breakNode(ident, (Element) n));
    }

    return result.toString();
  }

  private final void generateFinalBlock( Document d, StringBuffer generatedCode, List<Dependency> deps) throws Exception {
      generatedCode.append("public final void finalBlock(Access access) throws Exception {\n");

      NodeList list = d.getElementsByTagName("finally");

      if (list != null && list.getLength() > 0) {
        NodeList children = list.item(0).getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
          String str = compile(0, children.item(i), "", "",deps);
          generatedCode.append(str);
        }
      }


      generatedCode.append("}\n");

  }

  /**
   * Check condition/validation rules inside the script.
   * @param f
   * @return
   * @throws Exception
   */
  private final void generateRules( Document d, StringBuffer generatedCode ) throws Exception {

    NodeList list = d.getElementsByTagName("defines");
    for (int i = 0; i < list.getLength(); i++) {
        NodeList rules = list.item(i).getChildNodes();
        for (int j = 0; j < rules.getLength(); j++) {
          if (rules.item(j).getNodeName().equals("define")) {
        	  String name = ((Element) rules.item(j)).getAttribute("name");
        	  String expression = rules.item(j).getFirstChild().getNodeValue();
        	  expression = expression.replace('\n', ' ');
        	  generatedCode.append("userDefinedRules.put(\"" + name + "\",\"" + expression + "\");\n");
          }
        }
    }
  }
  
  /**
  * Check condition/validation rules inside the script.
  * @param f
  * @return
  * @throws Exception
  */
 private final void generateValidations( Document d, StringBuffer generatedCode ) throws Exception {

   boolean hasValidations = false;

   StringBuffer conditionString = new StringBuffer("conditionArray = new String[]{\n");
   StringBuffer ruleString = new StringBuffer("ruleArray = new String[]{\n");
   StringBuffer codeString = new StringBuffer("codeArray = new String[]{\n");
   StringBuffer descriptionString = new StringBuffer("descriptionArray = new String[]{\n");

   NodeList list = d.getElementsByTagName("validations");
   
   //System.err.println("validations nodes: " + list.getLength());
//   boolean valid = true;
//   ArrayList conditions = new ArrayList();
   for (int i = 0; i < list.getLength(); i++) {
     NodeList rules = list.item(i).getChildNodes();
     for (int j = 0; j < rules.getLength(); j++) {
       if (rules.item(j).getNodeName().equals("check")) {
         Element rule = (Element) rules.item(j);
         String code = rule.getAttribute("code");
         String description = rule.getAttribute("description");
         String value = rule.getAttribute("value");
         String condition = rule.getAttribute("condition");
         if (value.equals("")) {
           value = rule.getFirstChild().getNodeValue();
         }
         if (code.equals("")) {
           throw new UserException(-1, "Validation syntax error: code attribute missing or empty");
         }
         if (value.equals("")) {
           throw new UserException(-1, "Validation syntax error: value attribute missing or empty");
         }
         // Check if condition evaluates to true, for evaluating validation ;)
         hasValidations = true;
         conditionString.append("\""+condition.replace('\n', ' ').trim()+"\"");
         ruleString.append("\""+value.replace('\n', ' ').trim()+"\"");
         codeString.append("\""+code.replace('\n', ' ').trim()+"\"");
         descriptionString.append("\""+description.replace('\n', ' ').trim()+"\"");
         if (j != ( rules.getLength() - 2 ) ) { // Add ","
           conditionString.append(",\n");
           ruleString.append(",\n");
           codeString.append(",\n");
           descriptionString.append(",\n");
         }
       }
     }
     if ( i < list.getLength() - 1) {
    	   conditionString.append(",\n");
           ruleString.append(",\n");
           codeString.append(",\n");
           descriptionString.append(",\n");
     }
   }

   conditionString.append("};\n");
   ruleString.append("};\n");
   codeString.append("};\n");
   descriptionString.append("};\n");

   generatedCode.append("public final void setValidations() {\n");
   if (hasValidations) {
     generatedCode.append(conditionString.toString());
     generatedCode.append(ruleString.toString());
     generatedCode.append(codeString.toString());
     generatedCode.append(descriptionString.toString());
   }
   generatedCode.append("}\n\n");

 }


  private final void compileScript(InputStream is, String packagePath, String script, String scriptPath, Writer fo, List<Dependency> deps) throws SystemException{
	  
	  boolean debugInput = false;
	  boolean debugOutput = false;
	  boolean broadcast = false;
	  boolean debugAll = false;
	  
	  this.scriptPath = scriptPath;
	  
	  try {
	      Document tslDoc = null;
	      StringBuffer result = new StringBuffer();

	      
	      tslDoc = XMLDocumentUtils.createDocument(is, false);
	     
	      NodeList tsl = tslDoc.getElementsByTagName("tsl");
	      if (tsl == null || tsl.getLength() != 1 || !(tsl.item(0) instanceof Element)) {
	        throw new SystemException(-1, "Invalid or non existing script file: " + scriptPath + "/" + packagePath + "/" + script + ".xml");
	      }
	      Element tslElt = (Element) tsl.item(0);
	      String debugLevel = tslElt.getAttribute("debug");
	      debugInput = (debugLevel.indexOf("request") != -1);
	      debugOutput = (debugLevel.indexOf("response") != -1);
	      debugAll = (debugLevel.indexOf("true") != -1);
	      String description = tslElt.getAttribute("notes");
	      String author = tslElt.getAttribute("author");
	      
	      broadcast = (tslElt.getAttribute("broadcast").indexOf("true") != -1);
	      String actualPackagePath = packagePath;
	      if (packagePath.equals("")) {
	      	if(Version.osgiActive()) {
	      		actualPackagePath = "defaultPackage";
	      	}
	      }

	      
	      String importDef = (actualPackagePath.equals("") ? "" :
	                          "package " + MappingUtils.createPackageName(actualPackagePath) +
	                          ";\n\n") +
	          "import com.dexels.navajo.server.*;\n" +
	          "import com.dexels.navajo.mapping.*;\n" +
	          "import com.dexels.navajo.document.*;\n" +
	          "import com.dexels.navajo.parser.*;\n" +
	          "import java.util.ArrayList;\n" +
	          "import java.util.HashMap;\n" +
	          "import com.dexels.navajo.server.enterprise.tribe.TribeManagerFactory;\n" +
	          "import com.dexels.navajo.mapping.compiler.meta.IncludeDependency;\n" +
	          "import com.dexels.navajo.mapping.compiler.meta.ExpressionValueDependency;\n" +
	          "import com.dexels.navajo.mapping.compiler.meta.SQLFieldDependency;\n" +
              "import com.dexels.navajo.mapping.compiler.meta.InheritDependency;\n" +
              "import com.dexels.navajo.mapping.compiler.meta.JavaDependency;\n" +
              "import com.dexels.navajo.mapping.compiler.meta.NavajoDependency;\n" +
              "import com.dexels.navajo.mapping.compiler.meta.Dependency;\n" +
              "import com.dexels.navajo.mapping.compiler.meta.AdapterFieldDependency;\n" +
	          "import java.util.Stack;\n\n\n";
	      result.append(importDef);
	      
	      result.append("/**\n");
	      result.append(" * Generated Java code by TSL compiler.\n");
	      result.append(" * " + VERSION+"\n");
	      result.append(" *\n");
//	      result.append(" * Created on: " + new java.util.Date() + "\n");
	      result.append(" * Java version: " + System.getProperty("java.vm.name") + " (" + System.getProperty("java.runtime.version") + ")\n");
	      result.append(" * OS: " + System.getProperty("os.name")  + " " + System.getProperty("os.version") + "\n");
	      //result.append(" * Hostname: " + this.getHostName() + "\n");
	      result.append(" *\n");
	      result.append(" * WARNING NOTICE: DO NOT EDIT THIS FILE UNLESS YOU ARE COMPLETELY AWARE OF WHAT YOU ARE DOING\n");
	      result.append(" *\n");
	      result.append(" */\n\n");

	      String classDef = "public final class " + script +
	          " extends CompiledScript {\n\n\n";

	      result.append(classDef);

	      result.append("private volatile static ArrayList<Dependency> dependentObjects = null;\n\n");
	      
	      // Add constructor.
	      String constructorDef = "  public " + script + "() {\n " +
	                              "        if ( dependentObjects == null ) {\n" +
	                              "             dependentObjects = new ArrayList<Dependency>();\n" +
	                              "             setDependencies();\n" +
	                              "        }\n" +
	                              "  }\n\n";
	      
	      result.append(constructorDef);
	      
	      // First resolve includes.
	      NodeList includes = tslDoc.getElementsByTagName("include");
	      //System.err.println("FOUND " + includes.getLength() + " INCLUDES");
	      Node [] includeArray = new Node[includes.getLength()];
	      for (int i = 0; i < includes.getLength(); i++) {
	        includeArray[i] = includes.item(i);
	      }
	      
	      for (int i = 0; i < includeArray.length; i++) {
	        //System.err.println("ABOUT TO RESOLVE INCLUDE: " + includeArray[i]);
	        includeNode(scriptPath, includeArray[i], tslDoc);
	      }
	      
	      // Generate validation code.
	      generateValidations(tslDoc, result);

	      // Generate final block code.
	      generateFinalBlock(tslDoc, result,deps);

	      String methodDef = "public final void execute(Access access) throws Exception { \n\n";
	      result.append(methodDef);

	      if ( debugAll ) {
	    	  result.append("setDebugAll(true);\n");
	      }
	      if (debugInput) {
	       result.append("System.err.println(\"\\n --------- BEGIN NAVAJO REQUEST ---------\\n\");\n");
	       result.append("access.getInDoc().write(System.err);\n");
	       result.append("System.err.println(\"\\n --------- END NAVAJO REQUEST ---------\\n\");\n");
	     }

//	      result.append("outDoc = access.getOutputDoc();\n");
	      result.append("inDoc = access.getInDoc();\n");

	      // File Rules HashMap
	      generateRules(tslDoc, result);
	      
	      NodeList children = tslDoc.getElementsByTagName("tsl").item(0).getChildNodes();
	      //System.err.println("FOUND " + children.getLength() + " CHILDREN");
	      for (int i = 0; i < children.getLength(); i++) {
	    	
	        String str = compile(0, children.item(i), "", "",deps);
	        result.append(str);
	      }

	      if (debugOutput) {
	        result.append("System.err.println(\"\\n --------- BEGIN NAVAJO RESPONSE ---------\\n\");\n");
	        result.append("access.getOutputDoc().write(System.err);\n");
	        result.append("System.err.println(\"\\n --------- END NAVAJO RESPONSE ---------\\n\");\n");
	      }

	      if ( broadcast) {
	    	  result.append("try { \n");
	    	  result.append("   TribeManagerFactory.getInstance().broadcast(inDoc);\n");
	    	  result.append("} catch (Exception e) { \n");
	    	  result.append("   e.printStackTrace(System.err);\n");
	    	  result.append("}\n");
	      }
	    
	      
	      result.append("}// EOM\n");

	      // Add generated methods.
	      for (int i = 0; i < methodClipboard.size(); i++) {
	        result.append(methodClipboard.get(i).toString() + "\n\n");
	      }

	      // Add generated variables.
	      for (int i = 0; i < variableClipboard.size(); i++) {
	    	  result.append(variableClipboard.get(i));
	      }

	      // Add public void setDependencies() {}
	      if ( !dependencies.toString().equals("") ) {
	    	  result.append("public void setDependencies() {\n");
	    	  result.append(dependencies.toString());
	    	  result.append("}\n\n");
	    	  result.append("public ArrayList<Dependency> getDependentObjects() {\n");
	    	  result.append("   return dependentObjects;\n");
	    	  result.append("}\n\n");
	      }
	      
	      // Add getDescription() and getAuthor()
	      if ( author != null ) {
	    	  result.append("public String getAuthor() {\n");
	    	  result.append("   return \"" + author + "\";\n");
	    	  result.append("}\n\n");
	      }
	      
	      if ( description != null ) {
	    	  result.append("public String getDescription() {\n");
	    	  result.append("   return \"" + description + "\";\n");
	    	  result.append("}\n\n");
	      }
	      
	      result.append("public String getScriptType() {\n");
	      result.append("   return \"" + scriptType + "\";\n");
	      result.append("}\n\n");
	      
	      result.append("}//EOF");

	      fo.write(result.toString());
	      fo.close();
	    } catch (Exception e) {
	      throw new SystemException(-1, "Error while generating Java code for script: " + script, e);
	    } 
  }
    
  public void compileScript(String script, String scriptPath, String workingPath, String packagePath, Writer outputWriter, List<Dependency> deps) throws SystemException {

	    String fullScriptPath = scriptPath + "/" + packagePath + "/" + script + ".xml";
	    
	    ArrayList<String> inheritedScripts = new ArrayList<String>();
	    InputStream is = null;
	    
	    try {
	    	
	    	// Check for metascript.
	    	if ( MapMetaData.isMetaScript(script, scriptPath, packagePath) ) {
	    		scriptType = "navascript";
	    		MapMetaData mmd = MapMetaData.getInstance();
	    		InputStream metais = navajoIOConfig.getScript(packagePath+"/"+script);

	    		String intermed = mmd.parse(fullScriptPath,metais);
	    		metais.close();
				is = new ByteArrayInputStream(intermed.getBytes());
	    	} else {
	    		is = navajoIOConfig.getScript(packagePath+"/"+script);
	    	}
	    	
	    	InputStream sis = navajoIOConfig.getScript(packagePath+"/"+script);
	    	logger.debug("Getting script: "+packagePath+"/"+script);
	    	if ( ScriptInheritance.containsInject(sis)) {
	    		// Inheritance preprocessor before compiling.
	    		InputStream ais = null;
	    		ais = ScriptInheritance.inherit(is, scriptPath, inheritedScripts);
	    		is.close();
	    		is = ais;
	    	}
	    	sis.close();
			
			for (int i = 0; i < inheritedScripts.size(); i++) {
				addDependency("dependentObjects.add( new InheritDependency( new Long(\"" + 
		                 IncludeDependency.getScriptTimeStamp(inheritedScripts.get(i)) + "\"), \"" + 
		                 inheritedScripts.get(i) + "\"));\n", "INHERIT"+inheritedScripts.get(i));
			}
			compileScript(is, packagePath, script, scriptPath, outputWriter,deps);
			
		} catch (Exception e) {
			throw new SystemException(-1, "Error while generating Java code for script: " + script, e);
		} finally {
			if ( is != null ) {
				try {
					is.close();
				} catch (IOException e) {
				}
			}
		}
    
  }

//  private static void compileStandAlone(boolean all, String script,
//                                        String input, String output, String packagePath) {
//    compileStandAlone(all,script,input,output,packagePath,null);
//  }


  private String compileToJava(String script,
          String input, String output, String packagePath, ClassLoader classLoader, NavajoIOConfig navajoIOConfig,List<Dependency> deps) throws Exception {
	  return compileToJava(script, input, output, packagePath, packagePath, classLoader, navajoIOConfig,deps);
  }
  
  /**
   * Only used by OSGi now
   * @param script
   * @param input
   * @param output
   * @param packagePath The package in the Java file. OSGi doesn't handle default packages well, so default package will be translated to 'defaultPackage'
   * @param scriptPackagePath the path of the scriptFile from the scriptRoot
   * @param classLoader
   * @param navajoIOConfig
   * @return
   * @throws Exception
   */
  public String compileToJava(String script,
                                        String input, String output, String packagePath, String scriptPackagePath, ClassLoader classLoader, NavajoIOConfig navajoIOConfig, List<Dependency> deps) throws Exception {
    String javaFile = output + "/" + script + ".java";
   TslCompiler tslCompiler = new TslCompiler(classLoader,navajoIOConfig);
     try {
       String bareScript;

       if (script.indexOf('/')>=0) {
         bareScript = script.substring(script.lastIndexOf('/')+1,script.length());
       } else {
         bareScript = script;
       }
       
//       if(!packagePath.endsWith("/")) {
//    	   packagePath = packagePath + "/";
//       }

       tslCompiler.compileScript(bareScript, input, output,scriptPackagePath,navajoIOConfig.getOutputWriter(output, packagePath, script, ".java"),deps);
       
       return javaFile;
     }
     catch (Throwable ex) {
       logger.error("Error compiling script: "+script,ex);
       //System.err.println("delete javaFile: "+javaFile.toString());
       // Isn't this what 'finally' is for?
       File f = new File(javaFile);
       if (f.exists()) {
		f.delete();
       }
       if (ex instanceof Exception) {
           throw (Exception)ex;
       }
       return null;
    }
  }
  
  private void compileStandAlone(boolean all, String script,
                                        String input, String output, String packagePath, String[] extraclasspath, String configPath, List<Dependency> deps) {
     try {
      TslCompiler tslCompiler = new TslCompiler(null);
        try {
          String bareScript;

          if (script.indexOf('/')>=0) {
            bareScript = script.substring(script.lastIndexOf('/')+1,script.length());
          } else {
            bareScript = script;
          }

          //System.err.println("About to compile script: "+bareScript);
          //System.err.println("Using package path: "+packagePath);
		Writer w = navajoIOConfig.getOutputWriter(output, packagePath, script, ".java");

		tslCompiler.compileScript(bareScript, input, output,packagePath,w,deps);
          
          ////System.out.println("CREATED JAVA FILE FOR SCRIPT: " + script);
        }
        catch (Exception ex) {
          System.err.println("Error compiling script: "+script);
          return;
        }
//      }

        StringBuffer classPath = new StringBuffer();
        classPath.append(System.getProperty("java.class.path"));

      if (extraclasspath!=null) {
        for (int i = 0; i < extraclasspath.length; i++) {
          classPath.append(System.getProperty("path.separator"));
          classPath.append(extraclasspath[i]);
        }
      }

//      System.out.println("in NavajoCompiler(): new classPath = " + classPath);

//      JavaCompiler compiler = new SunJavaCompiler();
//
//      compiler.setClasspath(classPath.toString());
//      compiler.setOutputDir(output);
//      compiler.setClassDebugInfo(true);
//
//      System.err.println("Navajo output: "+output+" ");
//      System.err.println("Script: "+script);
//      compiler.setEncoding("UTF8");
//      compiler.setMsgOutput(System.out);
//      compiler.compile(output + "/" + script + ".java");
//
//      
      JavaCompiler compiler = new SunJavaCompiler();
      //    StringBuffer javaBuffer = new StringBuffer();

      //    System.err.println("JavaBuffer: "+javaBuffer.toString());
      compiler.setClasspath(classPath.toString());
      compiler.setOutputDir(output);
      compiler.setClassDebugInfo(true);
      compiler.setEncoding("UTF8");
      compiler.setMsgOutput(System.out);
      StringWriter myWriter = new StringWriter();
      compiler.setOutputWriter(myWriter);
//      System.err.println("\n\nCLASSPATH: " + classPath.toString());
      compiler.compile(output + "/" + script + ".java");
      
      
      
      //System.out.println("COMPILED JAVA FILE INTO CLASS FILE");
    }
    catch (Exception e) {
    	logger.error("Error: ", e);
    	System.exit(1);
    }
  }

  private ArrayList<String> compileDirectoryToJava(File currentDir, File outputPath, String offsetPath, NavajoClassLoader classLoader, NavajoIOConfig navajoConfig) {
    System.err.println("Entering compiledirectory: " + currentDir + " output: " +
                       outputPath + " offset: " + offsetPath);
    ArrayList<String> files = new ArrayList<String>();
    File[] scripts = null;
    File f = new File(currentDir, offsetPath);
    scripts = f.listFiles();
    if (scripts != null) {
      for (int i = 0; i < scripts.length; i++) {
        File current = scripts[i];
        if (current.isDirectory()) {
          System.err.println("Entering directory: " + current.getName());
          ArrayList<String> subDir = compileDirectoryToJava(currentDir, outputPath,
              offsetPath.equals("") ? current.getName() :
              (offsetPath + "/" + current.getName()),classLoader,navajoConfig);
          files.addAll(subDir);
        }
        else {
          if (current.getName().endsWith(".xml")) {
            String name = current.getName().substring(0,
                current.getName().indexOf("."));
//            System.err.println("Compiling: "+name+" dir: "+ new File(currentDir,offsetPath).toString()+" outdir: "+new File(outputPath,offsetPath));
            System.err.println("Compiling: " + name);
            File outp = new File(outputPath, offsetPath);
            if (!outp.exists()) {
              outp.mkdirs();
            }
            String compileName;
            if (offsetPath.equals("")) {
              compileName = name;
            }
            else {
              compileName = offsetPath + "/" + name;
            }
            String javaFile = null;
            try {
                javaFile = compileToJava(compileName, currentDir.toString(),
                              outputPath.toString(), offsetPath,classLoader,navajoConfig,new ArrayList<Dependency>());
                files.add(javaFile);
            } catch (Exception e) {
               logger.error("Error: ", e);
            }
          }
        }
      }
    }
    return files;
  }

  public void fastCompileDirectory(File currentDir, File outputPath, String offsetPath, String[] extraclasspath, NavajoClassLoader classLoader, NavajoIOConfig navajoConfig) {

    StringBuffer classPath = new StringBuffer();
    classPath.append(System.getProperty("java.class.path"));

    if (extraclasspath != null) {
      for (int i = 0; i < extraclasspath.length; i++) {
        classPath.append(System.getProperty("path.separator"));
        classPath.append(extraclasspath[i]);
      }
    }

    ArrayList<String> javaFiles =  compileDirectoryToJava(currentDir, outputPath, offsetPath,classLoader,navajoConfig);
    System.err.println("javaFiles: "+javaFiles);
    JavaCompiler compiler = new SunJavaCompiler();
//    StringBuffer javaBuffer = new StringBuffer();

//    System.err.println("JavaBuffer: "+javaBuffer.toString());
    compiler.setClasspath(classPath.toString());
    compiler.setOutputDir(outputPath.toString());
    compiler.setClassDebugInfo(true);
    compiler.setEncoding("UTF8");
    compiler.setMsgOutput(System.out);
    System.err.println("\n\nCLASSPATH: "+classPath.toString());
    for (int i = 0; i < javaFiles.size(); i++) {
      compiler.compile(javaFiles.get(i));
      System.err.println("Compiled: "+javaFiles.get(i));
//      javaBuffer.append((String)javaFiles.get(i));
//      javaBuffer.append(" ");
    }


  }

  public static void compileDirectory(File currentDir, File outputPath, String offsetPath, String[] classpath,String configPath) {

	  TslCompiler compiler = new TslCompiler(null, new LegacyNavajoIOConfig());
	List<Dependency> deps = new ArrayList<Dependency>();
	  System.err.println("Entering compiledirectory: "+currentDir+" output: "+outputPath+" offset: "+offsetPath);

    File[] scripts = null;
    File f = new File(currentDir,offsetPath);
    scripts = f.listFiles();
     if (scripts!=null) {
      for (int i = 0; i < scripts.length; i++) {
        File current = scripts[i];
        if (current.isDirectory()) {
          System.err.println("Entering directory: "+current.getName());
          compileDirectory(currentDir, outputPath, offsetPath.equals("") ? current.getName() : (offsetPath + "/"+ current.getName()),classpath, configPath);
        } else {
          if (current.getName().endsWith(".xml")) {
            String name = current.getName().substring(0,current.getName().indexOf("."));
//            System.err.println("Compiling: "+name+" dir: "+ new File(currentDir,offsetPath).toString()+" outdir: "+new File(outputPath,offsetPath));
            System.err.println("Compiling: "+name);
            File outp = new File(outputPath,offsetPath);
            if (!outp.exists()) {
              outp.mkdirs();
            }
            String compileName;
            if (offsetPath.equals("")) {
              compileName = name;
            } else {
              compileName = offsetPath+"/"+name;
            }
            compiler.compileStandAlone(false,compileName,currentDir.toString(),outputPath.toString(),offsetPath,classpath,configPath,deps);
            logger.info("Standalone compile finished. Detected dependencies: "+deps);
          }
        }
      }
    }
}

  public static String getHostName() {

	  if ( hostname != null ) {
		  return hostname; 
	  }

	  synchronized (VERSION) {

		  if ( hostname == null ) {
			  //ArrayList list = new ArrayList();

			  hostname = "unknown host";
//			  long start = System.currentTimeMillis();

			  Enumeration all = null;
			 
			  try {
				  all = java.net.NetworkInterface.getNetworkInterfaces();
			  } catch (SocketException e) {
				  hostname = "generated-host-" + System.currentTimeMillis();
				  return hostname;
			  }

			  while (all.hasMoreElements()) {
				  java.net.NetworkInterface nic = (java.net.NetworkInterface) all.nextElement();
				  Enumeration<InetAddress> ipaddresses = nic.getInetAddresses();
				  while (ipaddresses.hasMoreElements()) {
					  logger.warn("Why are we harassing the network interfaces?");
					  ipaddresses.nextElement();


				  }
			  }
		  }

	  }

	  return hostname;

  }

public static void main(String[] args) throws Exception {
	NavajoIOConfig config = new LegacyNavajoIOConfig();
    System.err.println("today = " + new java.util.Date());
    final String configPath = config.getConfigPath();

   if (args.length == 0) {
     System.out.println("TslCompiler: Usage: java com.dexels.navajo.mapping.compiler.TslCompiler <scriptDir> <compiledDir> [-all | scriptName]");
     System.err.println("NOTE: Startupswitch for extra class path (eg for adding an adaper jar) has not been implemented yet");
     System.exit(1);
   }

   boolean all = args[2].equals("-all");
   if (all) {
     System.err.println("SCRIPT DIR = " + args[0]);
   } else {
     System.err.println("SERVICE = " + args[2]);
   }

   String input = args[0];
   String output = args[1];
   //String service = args[2];

   if (all) {
     File scriptDir = new File(input);
     File outDir = new File(output);
     compileDirectory(scriptDir, outDir, "",null,configPath);
   }
 }

public void initJavaCompiler( String outputPath, ArrayList classpath, Class javaCompilerClass) {
    StringBuffer cpbuffer = new StringBuffer();
          if (classpath != null) {
        for (int i = 0; i < classpath.size(); i++) {
             cpbuffer.append(classpath.get(i));
             if (i<classpath.size()-1) {
                 cpbuffer.append(System.getProperty("path.separator"));
            }
       }
    }
  compiler = new EclipseCompiler();
    compiler.setClasspath(cpbuffer.toString());
    compiler.setOutputDir(outputPath);
    compiler.setClassDebugInfo(true);
    compiler.setEncoding("UTF8");
    compiler.setMsgOutput(System.out);
    compiler.setCompilerClass(javaCompilerClass);

}

public void compileAllTslToJava(ArrayList elements)  throws Exception {
      removeDuplicates(elements);
    
    compiler.compile(elements);
}
public void compileAllTslToJava(ArrayList elements, Class compilerClass)  throws Exception {
    removeDuplicates(elements);
  
  compiler.compile(elements );
}

public void setCompileClassLoader( ClassLoader cl  ) {
    if (compiler!=null) {
        compiler.setCompileClassLoader(cl);
    } else {
        System.err.println("Warning: No java compiler present!");
    }
}

private void removeDuplicates(ArrayList elements) {
    for (int i = elements.size()-1; i >=0; i--) {
        String element = (String)elements.get(i);
        File f = new File(element);
        if (!f.exists()) {
            elements.remove(i);
            continue;
        }
        if (f.length()==0) {
            elements.remove(i);
      }
    }
}


  }