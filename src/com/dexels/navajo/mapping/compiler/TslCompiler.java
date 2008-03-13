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
import com.dexels.navajo.document.*;
import com.dexels.navajo.document.jaxpimpl.xml.*;
import com.dexels.navajo.mapping.*;
import com.dexels.navajo.mapping.compiler.meta.MapMetaData;
import com.dexels.navajo.server.UserException;
import com.dexels.navajo.server.SystemException;

import org.apache.jasper.compiler.*;

import java.io.*;

import org.w3c.dom.*;
import java.util.Stack;
import java.util.StringTokenizer;
import com.dexels.navajo.parser.Expression;
import com.dexels.navajo.parser.TMLExpressionException;
import java.net.*;
import java.util.ArrayList;
import java.util.Enumeration;
import com.dexels.navajo.loader.*;


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
  @SuppressWarnings("unchecked")
  private Stack<Class> contextClassStack = new Stack<Class>();
  @SuppressWarnings("unchecked")
  private Class contextClass = null;

  private static String hostname = null;
  
  private static String VERSION = "$Id$";

  public TslCompiler(ClassLoader loader) {
    this.loader = loader;
    messageListCounter = 0;
    if (loader == null) {
      this.loader = this.getClass().getClassLoader();
    }
    //Stack s = new Stack();
    //o s.pop();
    //s.push(o);
  }

  //private String className = "";

  // "aap" -> \"aap\"
  private String replaceQuotes(String str) {

    StringBuffer result = new StringBuffer(str.length());
    for (int i = 0; i < str.length(); i++) {
      char c = str.charAt(i);
      if (c == '"') {
        result.append("\\\"");
      }
      else {
        result.append(c);
      }
    }
    return result.toString();
  }

  private String removeNewLines(String str) {
    StringBuffer result = new StringBuffer(str.length());
    for (int i = 0; i < str.length(); i++) {
      char c = str.charAt(i);
      if (c == '\n') {
        result.append("\\n");
      }
      else {
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

  /**
   * VERY NASTY METHOD.
   * IT TRIES ALL KINDS OF TRICKS TO TRY TO AVOID CALLING THE EXPRESSION.EVALUATE() METHOD IN THE GENERATED JAVA.
   *
   * @param ident
   * @param clause
   * @param className
   * @return
   */
  @SuppressWarnings("unchecked")
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
          Class contextClass = null;

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

              contextClass = null;
              try {
              	contextClass = Class.forName(className, false, loader);
              } catch (Exception e) {
              	throw new Exception("Could not find adapeter: " + className);
              }

            String attrType = MappingUtils.getFieldType(contextClass, name.toString());

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
            if (contextClass == null) {
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
          catch (Exception e) {
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
        throw new UserException(-1, "Could not compile script, Invalid expression: " + clause);
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
      result.append(printIdent(ident) + "op = Expression.evaluate(\"" +
                    replaceQuotes(clause) +
                    "\", inMessage, currentMap, currentInMsg, currentParamMsg, currentSelection, null);\n");
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
  
  public String expressionNode(int ident, Element exprElmnt, int leftOver,  String className, String objectName) throws
      Exception {
    StringBuffer result = new StringBuffer();
    boolean isStringOperand = false;

    String condition = exprElmnt.getAttribute("condition");
    String value = null;
    Element valueElt = (Element) XMLutils.findNode(exprElmnt, "value");

    if ( valueElt == null ) {
    	value = XMLutils.XMLUnescape(exprElmnt.getAttribute("value"));
    } else {
    	value = getCDATAContent(valueElt);
    	if ( value == null ) {
    		Node child = valueElt.getFirstChild();
    		value = child.getNodeValue();
    	}
    	value = value.trim();
    	value = value.replaceAll("\n", " ");
    	value = XMLutils.XMLUnescape(value);
    }

    // Check if operand is given as text node between <expression> tags.
    if (value == null || value.equals("")) {
    	Node child = exprElmnt.getFirstChild();
    	String cdata = getCDATAContent(exprElmnt);
    	if ( cdata != null ) {
    		isStringOperand = true;
    		value = cdata;
    	} else if (child != null) {
    		isStringOperand = true;
    		value = child.getNodeValue();
    	}
    	else {
    		throw new Exception("Error @" +
    				(exprElmnt.getParentNode() + "/" + exprElmnt) + ": <expression> node should either contain a value attribute or a text child node: >" +
    				value + "<");
    	}
    }

        if (!condition.equals("")) {
          result.append(printIdent(ident) + "if (Condition.evaluate(\"" + replaceQuotes(condition) + "\", inMessage, currentMap, currentInMsg, currentParamMsg))");
        }

        result.append(printIdent(ident) + "{\n");
        ident += 2;

        if (!isStringOperand) {
          result.append(optimizeExpresssion(ident, value, className, objectName));
        }
        else {
          result.append(printIdent(ident) + "sValue = \"" + removeNewLines(value) + "\";\n");
          //result.append(printIdent(ident) + "sValue = \"" + value + "\";\n");
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
          result.append(printIdent(ident) + "if (Condition.evaluate(\"" +
                        replaceQuotes(condition) +
                        "\", inMessage, null, null, null)) {\n");
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
public String messageNode(int ident, Element n, String className, String objectName) throws Exception {
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

    boolean isLazy = mode.equals(Message.MSG_MODE_LAZY);

    boolean conditionClause = false;

    // If <message> node is conditional:
    if (!condition.equals("")) {
      conditionClause = true;
      result.append(printIdent(ident) + "if (Condition.evaluate(\"" +
                    replaceQuotes(condition) +
                    "\", inMessage, currentMap, currentInMsg, currentParamMsg)) { \n");
      ident += 2;
    }

    Element nextElt = getNextElement(n);
    String ref = "";
    String filter = "";
    String startElement = "";
    String elementOffset = "";

    boolean isArrayAttr = false;
    boolean isSubMapped = false;
    String mapPath = null;
    
    // Check if <message> is mapped to an object attribute:
    if (nextElt != null && nextElt.getNodeName().equals("map") &&
        nextElt.getAttribute("ref") != null &&
        !nextElt.getAttribute("ref").equals("")) {
          String refOriginal = nextElt.getAttribute("ref");
          
          if (refOriginal.indexOf("/")!=-1) {
        	  ref = refOriginal.substring(refOriginal.lastIndexOf('/')+1, refOriginal.length());
              mapPath = refOriginal.substring(0,refOriginal.lastIndexOf('/'));
         } else {
        	 ref = refOriginal;
           }
          
          filter = nextElt.getAttribute("filter");
          startElement = nextElt.getAttribute("start_element");
          elementOffset = nextElt.getAttribute("element_offset");
          startElement = ((startElement == null || startElement.equals("")) ? "" : startElement);
          elementOffset = ((elementOffset == null || elementOffset.equals("")) ? "" : elementOffset);
          //System.out.println("in MessageNode(), REF = " + ref);
          ////System.out.println("filter = " + filter);
          //System.out.println("in MessageNode(), current contextClass = " + contextClass);
          if (contextClass==null) {
              //System.err.println("NO CONTEXT CLASS (YET)");
            } else {
                //System.err.println("LINE 587: PUSHING CLASS: "+contextClass);
                contextClassStack.push(contextClass);
           }
          contextClass = null;
        
          try {
        	  if (mapPath!=null) {
        		  contextClass = locateContextClass(mapPath);
        		  className = contextClass.getName();
        	  } else {
        		  contextClass = Class.forName(className, false, loader);
        	  }
          } catch (Exception e) {
        	  throw new Exception("Could not find adapter: " + className);
          }
          
          //System.out.println("in MessageNode(), new contextClass = " + contextClass);
          isArrayAttr = MappingUtils.isArrayAttribute(contextClass, ref);
          if (isArrayAttr) {
            type = Message.MSG_TYPE_ARRAY;
          }
          isSubMapped = true;
    }
    ////System.out.println("isArrayAttr = " + isArrayAttr);

    // Construct Lazy stuff if it's an array message and it has a lazy flag.
    if (isLazy && isArrayAttr) {
      result.append(printIdent(ident) + "lm = access.getLazyMessages();\n");
      result.append(printIdent(ident) + "fullMsgName = \"/\" + ( (currentOutMsg != null ? (currentOutMsg.getFullMessageName() + \"/\") : \"\") + \"" +
                    messageName + "\");\n");
      result.append(printIdent(ident) + "if (lm.isLazy(fullMsgName)) {\n");
      result.append(printIdent(ident + 2) +
                    "la = (LazyArray) currentMap.myObject;\n");
      result.append(printIdent(ident + 2) + "la.setEndIndex(\"" + ref +
                    "\", lm.getEndIndex(fullMsgName));\n");
      result.append(printIdent(ident + 2) + "la.setStartIndex(\"" + ref +
                    "\",lm.getStartIndex(fullMsgName));\n");
      result.append(printIdent(ident + 2) + "la.setTotalElements(\"" + ref +
                    "\",lm.getTotalElements(fullMsgName));\n");
      result.append(printIdent(ident) + "}\n");
    }

    // Create the message(s). Multiple messages are created if count > 1.
    result.append(printIdent(ident) + "count = " +
                  (count.equals("1") ? "1" :
                   "((Integer) Expression.evaluate(\"" + count +
                   "\", inMessage, currentMap, currentInMsg, currentParamMsg).value).intValue()") +
                  ";\n");
    String messageList = "messageList" + (messageListCounter++);
    result.append(printIdent(ident) + "Message [] " + messageList +
                  " = null;\n");
    
    
    String orderbyExpression =  ("".equals(orderby) ? "\"\"" :
                "(String) Expression.evaluate(\"" + orderby + "\", inMessage, currentMap, currentInMsg, currentParamMsg).value"
    );
    
    if (n.getNodeName().equals("message")) {
	    result.append(printIdent(ident) + messageList +
	                  " = MappingUtils.addMessage(access.getOutputDoc(), currentOutMsg, \"" +
	                  messageName + "\", \"\", count, \"" + type + "\", \"" + mode +
	                  "\", " + orderbyExpression + ");\n");
	    result.append("");
    } else { // must be parammessage.
    	 
    	 result.append(printIdent(ident) + messageList +
                " = MappingUtils.addMessage(inMessage, currentParamMsg, \"" +
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

    if (isLazy && isArrayAttr) {
      result.append(printIdent(ident + 2) +
                    "if (lm != null && lm.isLazy(fullMsgName)) {\n");
      result.append(printIdent(ident + 4) +
                    "currentOutMsg.setLazyTotal(la.getTotalElements(\"" + ref +
                    "\"));\n");
      result.append(printIdent(ident + 4) +
                    "currentOutMsg.setLazyRemaining(la.getRemainingElements(\"" +
                    ref + "\"));\n");
      result.append(printIdent(ident + 4) +
                    "currentOutMsg.setArraySize(la.getCurrentElements(\"" + ref +
                    "\"));\n");
      result.append(printIdent(ident + 4) + "lm = null; fullMsgName = \"\";\n");
      result.append(printIdent(ident + 2) + "}\n");
    }

    result.append(printIdent(ident + 2) +
                  "access.setCurrentOutMessage(currentOutMsg);\n");

    if (isSubMapped && isArrayAttr) {
      type = Message.MSG_TYPE_ARRAY_ELEMENT;
      String lengthName = "length" + (lengthCounter++);
      
      
      String mappableArrayName = "mappableObject" + (objectCounter++);
      
      if ( mapPath == null ) {
    	  result.append(printIdent(ident + 2) + mappableArrayName +
    			  " = ((" + className + ") currentMap.myObject).get" +
    			  ( (ref.charAt(0) + "").toUpperCase() + ref.substring(1)) + "();\n");
      } else {
    	  result.append(printIdent(ident + 2) + mappableArrayName +
    			  " = ((" + className + ") findMapByPath( \"" + mapPath + "\")).get" +
    			  ( (ref.charAt(0) + "").toUpperCase() + ref.substring(1)) + "();\n");
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
      (startElement.equals("") ? "0" : "((Integer) Expression.evaluate(\"" + startElement + "\", inMessage, currentMap, currentInMsg, currentParamMsg).value).intValue()") + ";\n");
      result.append(printIdent(ident + 2) + "int " + offsetElementVar + " = " +
      (elementOffset.equals("") ? "1" : "((Integer) Expression.evaluate(\"" + elementOffset + "\", inMessage, currentMap, currentInMsg, currentParamMsg).value).intValue()") + ";\n");

      result.append(printIdent(ident + 2) + "for (int i" + (ident + 2) +
                    " = " + startElementVar + "; i" + (ident + 2) + " < " + lengthName + "; i" +
                    (ident + 2) + " = i" + (ident + 2) +"+"+ offsetElementVar + ") {\n if (!kill) {\n");

      result.append(printIdent(ident + 4) + "treeNodeStack.push(currentMap);\n");
      result.append(printIdent(ident + 4) +
                    "currentMap = new MappableTreeNode(access, currentMap, " + mappableArrayName + "[i" + (ident + 2) + "], true);\n");

      // If filter is specified, evaluate filter first:
      if (!filter.equals("")) {
        result.append(printIdent(ident + 4) + "if (Condition.evaluate(\"" +
                      replaceQuotes(filter) +
                      "\", inMessage, currentMap, currentInMsg, currentParamMsg)) {\n");
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
        "\", currentParamMsg, true, inMessage, false, \"\", " + ((startIndex == -1) ? "-1" : startIndexVar + "++") + ");\n");
      }

      contextClassStack.push(contextClass);
      String subClassName = MappingUtils.getFieldType(contextClass, ref);
      NodeList children = nextElt.getChildNodes();
      contextClass = null;
      try {
      	contextClass = Class.forName(subClassName, false, loader);
      } catch (Exception e) { throw new Exception("Could not find adapter: " + subClassName); }

      String subObjectName = "mappableObject" + (objectCounter++);
      result.append(printIdent(ident + 4) + subObjectName +
                    " = (" + subClassName + ") currentMap.myObject;\n");

      String objectDefinition = subClassName + " " + subObjectName + " = null;\n";
      variableClipboard.add(objectDefinition);

      for (int i = 0; i < children.getLength(); i++) {
        if (children.item(i)instanceof Element) {
          result.append(compile(ident + 4, children.item(i), subClassName, subObjectName));
        }
      }

      contextClass = (Class) contextClassStack.pop();

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
                    "currentMap.setEndtime();\ncurrentMap = (MappableTreeNode) treeNodeStack.pop();\n");
      result.append(printIdent(ident + 2) +
                    "}\n} // EOF Array map result from contextMap \n");
    }
    else if (isSubMapped) { // Not an array

      result.append(printIdent(ident + 2) + "treeNodeStack.push(currentMap);\n");
      result.append(printIdent(ident + 2) +
                    "currentMap = new MappableTreeNode(access, currentMap, ((" +
                    className + ") currentMap.myObject).get" +
                    ( (ref.charAt(0) + "").toUpperCase() + ref.substring(1)) +
                    "(), false);\n");
      result.append(printIdent(ident + 2) +
                    "if (currentMap.myObject != null) {\n");
      //result.append(printIdent(ident + 4) +
      //              "outMsgStack.push(currentOutMsg);\n");
      //result.append(printIdent(ident + 4) +
      //              "currentOutMsg = MappingUtils.getMessageObject(\"" +
      //              messageName +
      //              "\", currentOutMsg, true, access.getOutputDoc(), false, \"\", -1);\n");
      //result.append(printIdent(ident + 4) +
      //              "access.setCurrentOutMessage(currentOutMsg);\n");

      contextClassStack.push(contextClass);
      String subClassName = MappingUtils.getFieldType(contextClass, ref);
      contextClass = null;
      try {
      	contextClass = Class.forName(subClassName, false, loader);
      } catch (Exception e) { throw new Exception("Could not find adapter " + subClassName); }

      String subObjectName = "mappableObject" + (objectCounter++);
      result.append(printIdent(ident + 4) + subObjectName +
                    " = (" + subClassName + ") currentMap.myObject;\n");

      String objectDefinition = subClassName + " " + subObjectName + " = null;\n";
      variableClipboard.add(objectDefinition);

      NodeList children = nextElt.getChildNodes();
      for (int i = 0; i < children.getLength(); i++) {
        result.append(compile(ident + 4, children.item(i), subClassName, subObjectName));
      }

      contextClass = (Class) contextClassStack.pop();

      //result.append(printIdent(ident + 4) +
      //              "currentOutMsg = (Message) outMsgStack.pop();\n");
      //result.append(printIdent(ident + 4) +
      //              "access.setCurrentOutMessage(currentOutMsg);\n");
      result.append(printIdent(ident + 2) + "}\n");
      result.append(printIdent(ident + 2) +
                    "currentMap.setEndtime();\ncurrentMap = (MappableTreeNode) treeNodeStack.pop();\n");
    }
    else { // Just some new tags under the "message" tag.
      NodeList children = n.getChildNodes();
      for (int i = 0; i < children.getLength(); i++) {
        result.append(compile(ident + 2, children.item(i), className,
                              objectName));
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

    if (isSubMapped) {
      contextClass = (Class) contextClassStack.pop();
    }

    return result.toString();
  }

  @SuppressWarnings("unchecked")
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
      result.append(printIdent(ident) + "if (Condition.evaluate(\"" +
                    replaceQuotes(condition) +
                    "\", inMessage, currentMap, currentInMsg, currentParamMsg)) { \n");
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
    Class contextClass = null;
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
        boolean selected = (selectedValue.equals("1"));
        type = "selection";
        // Created condition statement if condition is given!
        String conditional = (optionCondition != null && !optionCondition.equals("")) ?
                             "if (Condition.evaluate(\"" + replaceQuotes(optionCondition) + "\", inMessage, currentMap, currentInMsg, currentParamMsg))\n" : "";
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
                    ", access.getOutputDoc(), inMessage, !matchingConditions);\n");
    }
    else { // parameter
      result.append(printIdent(ident) +
                    "p = MappingUtils.setProperty(true, currentParamMsg, \"" +
                    propertyName + "\", sValue, type, subtype, \"" + direction +
                    "\", \"" + description + "\", " +
                    length +
                    ", access.getOutputDoc(), inMessage, !matchingConditions);\n");
    }

    if (isMapped) {
      contextClass = null;
      try {
      contextClass = Class.forName(className, false, loader);
      } catch (Exception e) { throw new Exception("Could not find adapter: " + className); }
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
        result.append(printIdent(ident + 4) + "if (Condition.evaluate(\"" +
                      replaceQuotes(filter) +
                      "\", inMessage, currentMap, currentInMsg, currentParamMsg)) {\n");
        ident += 2;
      } 
      
      result.append(printIdent(ident + 4) + "String optionName = \"\";\n");
      result.append(printIdent(ident + 4) + "String optionValue = \"\";\n");
      result.append(printIdent(ident + 4) + "boolean optionSelected = false;\n");
      children = mapNode.getChildNodes();
      String subClassName = MappingUtils.getFieldType(contextClass, ref);
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
    if ( cardinality != null) {
      result.append("p.setCardinality(\"" + cardinality + "\");\n");
    }

    if (conditionClause) {
      ident -= 2;
      result.append(printIdent(ident) + "} // EOF property condition \n");
    }

    return result.toString();

  }

  @SuppressWarnings("unchecked")
public String fieldNode(int ident, Element n, String className,
                          String objectName) throws Exception {

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
      result.append(printIdent(ident) + "if (Condition.evaluate(\"" +
                    replaceQuotes(condition) +
                    "\", inMessage, currentMap, currentInMsg, currentParamMsg)) { \n");
    }
    else {
      result.append(printIdent(ident) + "if (true) {\n");
    }
    // Expression nodes.
    boolean isMapped = false;
    Element mapNode = null;

    int exprCount = countNodes(children, "expression");
    for (int i = 0; i < children.getLength(); i++) {
      // Has condition;
      if (children.item(i).getNodeName().equals("expression")) {
        result.append(expressionNode(ident + 2, (Element) children.item(i),
                                     --exprCount, className, objectName));
      }
      else if (children.item(i).getNodeName().equals("map")) {
        isMapped = true;
        mapNode = (Element) children.item(i);
      }
    }

    if (!isMapped) {
      String castedValue = "";
      try {
        Class contextClass = null;
		try {
            if (mapPath!=null) {
                contextClass = locateContextClass(mapPath);
            } else {
                contextClass = Class.forName(className, false, loader);
            }
           // contextClassStack.push(contextClass);
          } catch (Exception e) { throw new Exception("Could not find adapter: " + className); }
        String type = null;
        try {
        	type = MappingUtils.getFieldType(contextClass, attribute, methodName);
        } catch (Exception e) { throw new Exception("Could not find field: " + attribute + " in adapter " + contextClass.getName()); }
        if (type.equals("java.lang.String")) {
          castedValue = "(String) sValue";
        } else if (type.equals("com.dexels.navajo.document.types.ClockTime")) {
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
        else if (type.equals("float")) {
          castedValue = "((Double) sValue).doubleValue()";
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
        else {
          castedValue = "sValue";
        }
      }
      catch (ClassNotFoundException e) {
        e.printStackTrace();
        throw new UserException(-1, "Error in script: could not find mappable object: " + className);
      }
      
      if (mapPath!=null) {
          result.append(printIdent(ident + 2) + "(("+locateContextClass(mapPath).getName()+")findMapByPath(\""+mapPath+"\"))." + methodName + "(" +
                  castedValue + ");\n");        
      } else {
          result.append(printIdent(ident + 2) + objectName + "." + methodName + "(" +
                  castedValue + ");\n");        
      }
    }
    else { // Field with ref: indicates that a message or set of messages is mapped to attribute (either Array Mappable or singular Mappable)
      String ref = mapNode.getAttribute("ref");
      boolean isParam = false;
      if (ref.startsWith("/@")) {
      	ref = ref.replaceAll("@", "__parms__/");
      	isParam = true;
      }
      String filter = mapNode.getAttribute("filter");
      filter = (filter == null) ? "" : filter;
      result.append(printIdent(ident + 2) + "// And by the way, my filter is "+filter+"\n");
            
      result.append(printIdent(ident + 2) + "// Map message(s) to field\n");
      String messageListName = "messages" + ident;

      result.append(printIdent(ident + 2) + "ArrayList " + messageListName + " = null;\n");
      result.append(printIdent(ident + 2) + "inSelectionRef = MappingUtils.isSelection(currentInMsg, inMessage, \"" + ref + "\");\n");
      result.append(printIdent(ident + 2) + "if (!inSelectionRef)\n");
      result.append(printIdent(ident + 4) + messageListName +
          " = MappingUtils.getMessageList(currentInMsg, inMessage, \"" + ref +
                    "\", \"" + "" + "\", currentMap, currentParamMsg);\n");
      result.append(printIdent(ident + 2) + "else\n");
      result.append(printIdent(ident + 4) + messageListName +
         " = MappingUtils.getSelectedItems(currentInMsg, inMessage, \"" + ref + "\");\n");

      Class contextClass = null;
      try {
      	contextClass = Class.forName(className, false, loader);
      } catch (Exception e) {
    	  throw new Exception("Could not find adapter: " + className);
      }
      String type = MappingUtils.getFieldType(contextClass, attribute, methodName);
      boolean isArray = MappingUtils.isArrayAttribute(contextClass, attribute);
      
      ////System.out.println("TYPE FOR " + attribute + " IS: " + type + ", ARRAY = " + isArray);
      
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
          result.append(printIdent(ident + 4) + "if (inSelectionRef || Condition.evaluate(\"" + replaceQuotes(filter) + "\", inMessage, currentMap, currentInMsg, currentParamMsg)) {\n");
          ident += 2;
        }


        result.append(printIdent(ident) + "treeNodeStack.push(currentMap);\n");
        result.append(printIdent(ident) + "currentMap = new MappableTreeNode(access, currentMap, classLoader.getClass(\"" +
                      type + "\").newInstance(), false);\n");

        result.append(printIdent(ident) +
            "if ( currentMap.myObject instanceof Mappable ) {  ((Mappable) currentMap.myObject).load(parms, inMessage, access, config);}\n");
        result.append(printIdent(ident) + subObjectsName + "[" +
                      loopCounterName + "] = (" + type +
                      ") currentMap.myObject;\n");
        result.append(printIdent(ident) + "try {\n");
        ident = ident+2;

        children = mapNode.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
          result.append(compile(ident + 2, children.item(i), type, subObjectsName + "[" + loopCounterName + "]"));
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
        result.append(printIdent(ident + 2) + objectName + "." + methodName +
                      "(" + subObjectsName + ");\n");
        
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
        		"if ( currentMap.myObject instanceof Mappable) { ((Mappable) currentMap.myObject).load(parms, inMessage, access, config);}\n");     
        // Assign local variable reference.
        result.append(printIdent(ident) + type + " " + 
        		subObjectsName + " = (" + type +                
				") currentMap.myObject;\n");                
        result.append(printIdent(ident) + "try {\n");        
        ident = ident+2;      
        
        // Recursively dive into children.
        children = mapNode.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
          result.append(compile(ident + 2, children.item(i), type, subObjectsName ));
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
        result.append(printIdent(ident + 2) + objectName + "." + methodName +
                "(" + subObjectsName + ");\n");
      	
      }
    }
    result.append(printIdent(ident) + "}\n");
    return result.toString();
  }

  @SuppressWarnings("unchecked")
  private Class locateContextClass(String mapPath) {
//	  System.err.println("finaMapByPath: "+mapPath);
      StringTokenizer st = new StringTokenizer(mapPath,"/");
      //System.err.println("STACK: "+contextClassStack);
      //System.err.println("CONTEXT"+contextClass);
      
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
      //System.err.println("STACK: "+contextClassStack);
      Class m = (Class)contextClassStack.get(contextClassStack.size()-( count+1));
//      System.err.println("Mappable: "+m);
      return m;
}

  public String breakNode(int ident, Element n) throws Exception {

    StringBuffer result = new StringBuffer();
    String condition = n.getAttribute("condition");
    if (condition.equals("")) {
      result.append(printIdent(ident) + "if (true) {");
    }
    else {
      result.append(printIdent(ident) + "if (Condition.evaluate(\"" +
                    replaceQuotes(condition) +
                    "\", inMessage, currentMap, currentInMsg, currentParamMsg)) { \n");

    }
    result.append(printIdent(ident + 2) + "throw new BreakEvent();\n");
    result.append(printIdent(ident) + "}\n");

    return result.toString();
  }

  public String debugNode(int ident, Element n) throws Exception {
    StringBuffer result = new StringBuffer();
    String value = n.getAttribute("value");
    result.append(printIdent(ident) + "op = Expression.evaluate(\"" +
                  replaceQuotes(value) +
                  "\", inMessage, currentMap, currentInMsg, currentParamMsg, currentSelection, null);\n");
    result.append(printIdent(ident) + "System.err.println(\"in PROCESSING SCRIPT: \" + access.rpcName + \" DEBUG INFO: \" + op.value);\n");
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

  @SuppressWarnings("unchecked")
public String mapNode(int ident, Element n) throws Exception {


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
      result.append(printIdent(ident) + "if (Condition.evaluate(\"" +
                    replaceQuotes(condition) +
                    "\", inMessage, currentMap, currentInMsg, currentParamMsg)) { \n");
      ident += 2;
    }

    String className = object;
    if (contextClass!=null) {
        contextClassStack.push(contextClass);
    } 
     try {
         contextClass = Class.forName(className, false, loader);
           } catch (Exception e) { throw new Exception("Could not find adapter: " + className); }

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

      result.append(printIdent(ident) + "if (!config.isAsyncEnabled()) throw new UserException(-1, \"Set enable_async = true in server.xml to use asynchronous objects\");");
      result.append(printIdent(ident) + asyncMapName +" = true;\n");
      result.append(printIdent(ident) + headerName + " = inMessage.getHeader();\n");
      result.append(printIdent(ident) + callbackRefName + " = " + headerName + ".getCallBackPointer(\""+name+"\");\n");
      result.append(printIdent(ident) + aoName + " = null;\n");
      result.append(printIdent(ident) + asyncMapFinishedName + " = false;\n");
      result.append(printIdent(ident) + resumeAsyncName + " = false;\n");
      result.append(printIdent(ident) + asyncStatusName + " = \"request\";\n\n");
      result.append(printIdent(ident) + "if (" + callbackRefName + " != null) {\n");
      ident+=2;
      result.append(printIdent(ident) + aoName + " = (" + className + ") config.getAsyncStore().getInstance(" + callbackRefName + ");\n");
      result.append(printIdent(ident) + interruptTypeName + " = " + headerName + ".getCallBackInterupt(\""+name+"\");\n");

      result.append(printIdent(ident) + " if (" + aoName + " == null) {\n " +
                    "  throw new UserException( -1, \"Asynchronous object reference instantiation error: no sych instance (perhaps cleaned up?)\");\n}\n");
      result.append(printIdent(ident) + "if (" + interruptTypeName +
          ".equals(\"kill\")) { // Kill thread upon client request.\n" +
                    "   " + aoName + ".stop();\n" +
                    "   config.getAsyncStore().removeInstance(" +
                    callbackRefName + ");\n" +
                    "   return;\n" +
                    "} else if ( " + aoName + ".isKilled() ) " + 
                    "{ " + 
                    "     config.getAsyncStore().removeInstance(" + callbackRefName + ");\n" +
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
                                        "  " + aoName + ".load(parms, inMessage, access, config);\n" +
                                        "  " + callbackRefName + " = config.getAsyncStore().addInstance( " + aoName + ", access );\n" +
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
      result.append(printIdent(ident) + "  "+aoName+".beforeResponse(parms, inMessage, access, config);\n");
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
          result.append(compile(ident + 2, children.item(i), className, aoName));
        }
      }
      result.append(printIdent(ident) + "} else if (" + asyncStatusName +
                    ".equals(\"request\")) {\n");
      children = request.item(0).getChildNodes();
      for (int i = 0; i < children.getLength(); i++) {
        if (children.item(i)instanceof Element) {
          result.append(compile(ident + 2, children.item(i), className, aoName));
        }
      }
      result.append(printIdent(ident) + "} else if (" + asyncStatusName +
                    ".equals(\"running\")) {\n");
      children = running.item(0).getChildNodes();
      for (int i = 0; i < children.getLength(); i++) {
        if (children.item(i)instanceof Element) {
          result.append(compile(ident + 2, children.item(i), className, aoName));
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
                    "config.getAsyncStore().removeInstance(currentMap.ref);\n");
      result.append(printIdent(ident + 2) + "}\n");
      result.append(printIdent(ident) + "}\n");

      result.append(printIdent(ident) + "} catch (Exception e" + ident +
                    ") {\n");
      result.append(printIdent(ident) +
                    " MappingUtils.callKillMethod(currentMap.myObject);\n");
      result.append(printIdent(ident) +
                    " config.getAsyncStore().removeInstance(currentMap.ref);\n");

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
      if ( MappingUtils.isObjectMappable(className)) {
    	  result.append(printIdent(ident) + objectName + ".load(parms, inMessage, access, config);\n");
      }

      String objectDefinition = className + " " + objectName + " = null;\n";
      variableClipboard.add(objectDefinition);

      result.append(printIdent(ident) + "try {\n");

      NodeList children = n.getChildNodes();
      for (int i = 0; i < children.getLength(); i++) {
        result.append(compile(ident + 2, children.item(i), className,
                              objectName));
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
        contextClass = (Class)contextClassStack.pop();
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

    String script = ( (Element) n ).getAttribute("script");
    if (script == null || script.equals("")) {
      throw new UserException(-1, "No script name found in include tag (missing or empty script attribute): " + n);
    }
    
  //  System.err.println("INCLUDING SCRIPT " + script + " @ NODE " + n);

    Document includeDoc = XMLDocumentUtils.createDocument(new FileInputStream(scriptPath + "/" + script + ".xml"), false);

    NodeList content = includeDoc.getElementsByTagName("tsl").item(0).getChildNodes();
    Node nextNode = n.getNextSibling();
    while ( nextNode != null && !(nextNode instanceof Element) ) {
      nextNode = nextNode.getNextSibling();
    }
    if ( nextNode == null | !(nextNode instanceof Element) ) {
      nextNode = n;
    }

    //System.err.println("nextNode = " + nextNode + ", n = " + n);

    Node parentNode = nextNode.getParentNode();

    for (int i = 0; i < content.getLength(); i++) {
      Node child = content.item(i);
      Node imported = parent.importNode(child.cloneNode(true), true);
      parentNode.insertBefore(imported, nextNode);
    }

    parentNode.removeChild(n);

//    System.err.println("After include");
//    String result = XMLDocumentUtils.toString(parent);
//    System.err.println("result:");
//    System.err.println(result);
  }

  public String compile(int ident, Node n, String className, String objectName) throws
      Exception {
    StringBuffer result = new StringBuffer();
    //System.err.println("in compile(), className = " + className + ", objectName = " + objectName);

    if (n.getNodeName().equals("include") ) {
    	includeNode(scriptPath, n, n.getParentNode().getOwnerDocument());
    } else
    if (n.getNodeName().equals("map")) {
      result.append(printIdent(ident) +
                    "{ // Starting new mappable object context. \n");
      result.append(mapNode(ident + 2, (Element) n));
      result.append(printIdent(ident) + "} // EOF MapContext \n");
    }
    else if (n.getNodeName().equals("field")) {
      result.append(fieldNode(ident, (Element) n, className, objectName));
    }
    else if ((n.getNodeName().equals("param") && !((Element) n).getAttribute("type").equals("array")  ) ||
             n.getNodeName().equals("property")) {
      result.append(propertyNode(ident, (Element) n, true, className, objectName));
    }
    
    else if (n.getNodeName().equals("message") || 
    		(n.getNodeName().equals("param") && ((Element) n).getAttribute("type").equals("array")  )) {
      String methodName = "execute_sub"+(methodCounter++);
      result.append(printIdent(ident) + "if (!kill) { " + methodName + "(parms, inMessage, access, config); }\n");

      StringBuffer methodBuffer = new StringBuffer();

      methodBuffer.append(printIdent(ident) + "private final void " + methodName + "(Parameters parms, Navajo inMessage, Access access, NavajoConfig config) throws Exception {\n\n");
      ident+=2;
      methodBuffer.append(printIdent(ident) + "if (!kill) {\n");
      methodBuffer.append(messageNode(ident, (Element) n, className, objectName));
      methodBuffer.append(printIdent(ident) + "}\n");
      ident-=2;
      methodBuffer.append("}\n");

      methodClipboard.add(methodBuffer);
      //
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

  private final void generateFinalBlock( Document d, StringBuffer generatedCode ) throws Exception {
      generatedCode.append("public final void finalBlock(Parameters parms, Navajo inMessage, Access access, NavajoConfig config) throws Exception {\n");

      NodeList list = d.getElementsByTagName("finally");

      if (list != null && list.getLength() > 0) {
        NodeList children = list.item(0).getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
          String str = compile(0, children.item(i), "", "");
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
 private final void generateValidations( Document d, StringBuffer generatedCode ) throws Exception {

   boolean hasValidations = false;

   StringBuffer conditionString = new StringBuffer("conditionArray = new String[]{\n");
   StringBuffer ruleString = new StringBuffer("ruleArray = new String[]{\n");
   StringBuffer codeString = new StringBuffer("codeArray = new String[]{\n");

   NodeList list = d.getElementsByTagName("validations");
//   boolean valid = true;
//   ArrayList conditions = new ArrayList();
   for (int i = 0; i < list.getLength(); i++) {
     NodeList rules = list.item(i).getChildNodes();
     for (int j = 0; j < rules.getLength(); j++) {
       if (rules.item(j).getNodeName().equals("check")) {
         Element rule = (Element) rules.item(j);
         String code = rule.getAttribute("code");
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
         if (j != ( rules.getLength() - 2 ) ) { // Add ","
           conditionString.append(",\n");
           ruleString.append(",\n");
           codeString.append(",\n");
         }
       }
     }
   }

   conditionString.append("};\n");
   ruleString.append("};\n");
   codeString.append("};\n");

   generatedCode.append("public final void setValidations() {\n");
   if (hasValidations) {
     generatedCode.append(conditionString.toString());
     generatedCode.append(ruleString.toString());
     generatedCode.append(codeString.toString());
   }
   generatedCode.append("}\n\n");

 }


  private final void compileScript(InputStream is, String packagePath, String script, String scriptPath, String workingPath) throws SystemException{
	  
	  boolean debugInput = false;
	  boolean debugOutput = false;
	  boolean broadcast = false;
	  
	  this.scriptPath = scriptPath;
	  
	  try {
	      Document tslDoc = null;
	      StringBuffer result = new StringBuffer();
	      
	      File dir = new File(workingPath);
	      if (!dir.exists()) {
	        dir.mkdirs();
	      }
	      
	      File javaFile = new File(dir,packagePath+"/"+script+".java");
	      javaFile.getParentFile().mkdirs();
	      //System.err.println("Will create file: "+javaFile.toString());

	      FileWriter fo = new FileWriter(javaFile);
	      tslDoc = XMLDocumentUtils.createDocument(is, false);
	     
	      NodeList tsl = tslDoc.getElementsByTagName("tsl");
	      if (tsl == null || tsl.getLength() != 1 || !(tsl.item(0) instanceof Element)) {
	        throw new SystemException(-1, "Invalid or non existing script file: " + scriptPath + "/" + packagePath + "/" + script + ".xml");
	      }
	      Element tslElt = (Element) tsl.item(0);
	      String debugLevel = tslElt.getAttribute("debug");
	      debugInput = (debugLevel.indexOf("request") != -1);
	      debugOutput = (debugLevel.indexOf("response") != -1);
	      
	      broadcast = (tslElt.getAttribute("broadcast").indexOf("true") != -1);

	      String importDef = (packagePath.equals("") ? "" :
	                          "package " + MappingUtils.createPackageName(packagePath) +
	                          ";\n\n") +
	          "import com.dexels.navajo.server.*;\n" +
	          "import com.dexels.navajo.mapping.*;\n" +
	          "import com.dexels.navajo.document.*;\n" +
	          "import com.dexels.navajo.parser.*;\n" +
	          "import java.util.ArrayList;\n" +
	          "import java.util.HashMap;\n" +
	          "import com.dexels.navajo.server.enterprise.tribe.TribeManagerFactory;\n" +
	          "import java.util.Stack;\n\n\n";
	      result.append(importDef);

	      result.append("/**\n");
	      result.append(" * Generated Java code by TSL compiler.\n");
	      result.append(" * " + VERSION+"\n");
	      result.append(" *\n");
//	      result.append(" * Created on: " + new java.util.Date() + "\n");
	      result.append(" * Java version: " + System.getProperty("java.vm.name") + " (" + System.getProperty("java.runtime.version") + ")\n");
	      result.append(" * OS: " + System.getProperty("os.name")  + " " + System.getProperty("os.version") + "\n");
	      result.append(" * Hostname: " + this.getHostName() + "\n");
	      result.append(" *\n");
	      result.append(" * WARNING NOTICE: DO NOT EDIT THIS FILE UNLESS YOU ARE COMPLETELY AWARE OF WHAT YOU ARE DOING\n");
	      result.append(" *\n");
	      result.append(" */\n\n");

	      String classDef = "public final class " + script +
	          " extends CompiledScript {\n\n\n";

	      result.append(classDef);
	      // Generate validation code.
	      generateValidations(tslDoc, result);

	      // Generate final block code.
	      generateFinalBlock(tslDoc, result);

	      String methodDef = "public final void execute(Parameters parms, Navajo inMessage, Access access, NavajoConfig config) throws Exception { \n\n";
	      result.append(methodDef);

	      if (debugInput) {
	       result.append("System.err.println(\"\\n --------- BEGIN NAVAJO REQUEST ---------\\n\");\n");
	       result.append("inMessage.write(System.err);\n");
	       result.append("System.err.println(\"\\n --------- END NAVAJO REQUEST ---------\\n\");\n");
	     }

//	      result.append("outDoc = access.getOutputDoc();\n");
	      result.append("inDoc = inMessage;\n");

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

	      NodeList children = tslDoc.getElementsByTagName("tsl").item(0).getChildNodes();
	      //System.err.println("FOUND " + children.getLength() + " CHILDREN");
	      for (int i = 0; i < children.getLength(); i++) {
	    	
	        String str = compile(0, children.item(i), "", "");
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

	      result.append("}//EOF");

	      fo.write(result.toString());
	      fo.close();
	    } catch (Exception e) {
	      e.printStackTrace();
	      throw new SystemException(-1, "Error while generating Java code for script: " + script + ". Message: " + e.getMessage(), e);
	    } 
  }
  
  public void compileScript(String script, String scriptPath, String workingPath, String packagePath) throws SystemException {

	    try {
			if (!MapMetaData.isMetaScript(script, scriptPath, packagePath)) {
				compileScript(new FileInputStream(scriptPath + "/" + packagePath + "/" + script + ".xml"), 
						      packagePath, script, scriptPath, workingPath);
			} else {
				MapMetaData mmd = MapMetaData.getInstance();
				String intermed = mmd.parse(scriptPath + "/" + packagePath + "/" + script + ".xml");
				compileScript(new ByteArrayInputStream(intermed.getBytes()), 
						     packagePath, script, scriptPath, workingPath );
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemException(-1, "Error while generating Java code for script: " + script + ". Message: " + e.getMessage(), e);
		}
    
  }

//  private static void compileStandAlone(boolean all, String script,
//                                        String input, String output, String packagePath) {
//    compileStandAlone(all,script,input,output,packagePath,null);
//  }


  public static String compileToJava(String script,
                                        String input, String output, String packagePath, NavajoClassLoader classLoader) throws Exception {
     // File dir = new File(output);
    String javaFile = output + "/" + script + ".java";
     //ArrayList javaList = new ArrayList();
   TslCompiler tslCompiler = new TslCompiler(classLoader);
     try {
       String bareScript;

       if (script.indexOf('/')>=0) {
         bareScript = script.substring(script.lastIndexOf('/')+1,script.length());
       } else {
         bareScript = script;
       }
       tslCompiler.compileScript(bareScript, input, output,packagePath);
        return javaFile;
     }
     catch (Throwable ex) {
       System.err.println("Error compiling script: "+script);
       //System.err.println("delete javaFile: "+javaFile.toString());
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
  
  private static void compileStandAlone(boolean all, String script,
                                        String input, String output, String packagePath, String[] extraclasspath) {
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
          tslCompiler.compileScript(bareScript, input, output,packagePath);
          
          ////System.out.println("CREATED JAVA FILE FOR SCRIPT: " + script);
        }
        catch (Exception ex) {
          System.err.println("Error compiling script: "+script);
          ex.printStackTrace();
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
      
      System.err.println("Compilertext: "+myWriter.toString());
      
      
      //System.out.println("COMPILED JAVA FILE INTO CLASS FILE");
    }
    catch (Exception e) {
      e.printStackTrace();
      //System.out.println("Could not compile script " + script + ", reason: " +
      //                   e.getMessage());
      System.exit(1);
    }
  }

  public static ArrayList<String> compileDirectoryToJava(File currentDir, File outputPath, String offsetPath, NavajoClassLoader classLoader) {
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
              (offsetPath + "/" + current.getName()),classLoader);
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
                              outputPath.toString(), offsetPath,classLoader);
                files.add(javaFile);
            } catch (Exception e) {
               
                e.printStackTrace();
            }
          }
        }
      }
    }
    return files;
  }

  public static void fastCompileDirectory(File currentDir, File outputPath, String offsetPath, String[] extraclasspath, NavajoClassLoader classLoader) {

    StringBuffer classPath = new StringBuffer();
    classPath.append(System.getProperty("java.class.path"));

    if (extraclasspath != null) {
      for (int i = 0; i < extraclasspath.length; i++) {
        classPath.append(System.getProperty("path.separator"));
        classPath.append(extraclasspath[i]);
      }
    }

    ArrayList<String> javaFiles =  compileDirectoryToJava(currentDir, outputPath, offsetPath,classLoader);
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
      compiler.compile((String)javaFiles.get(i));
      System.err.println("Compiled: "+javaFiles.get(i));
//      javaBuffer.append((String)javaFiles.get(i));
//      javaBuffer.append(" ");
    }


  }

  public static void compileDirectory(File currentDir, File outputPath, String offsetPath, String[] classpath) {
    System.err.println("Entering compiledirectory: "+currentDir+" output: "+outputPath+" offset: "+offsetPath);

    File[] scripts = null;
    File f = new File(currentDir,offsetPath);
    scripts = f.listFiles();
     if (scripts!=null) {
      for (int i = 0; i < scripts.length; i++) {
        File current = scripts[i];
        if (current.isDirectory()) {
          System.err.println("Entering directory: "+current.getName());
          compileDirectory(currentDir, outputPath, offsetPath.equals("") ? current.getName() : (offsetPath + "/"+ current.getName()),classpath);
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
            compileStandAlone(false,compileName,currentDir.toString(),outputPath.toString(),offsetPath,classpath);
          }
        }
      }
    }
}

  @SuppressWarnings("unchecked")
private String getHostName() throws SocketException {
	  
	 if ( hostname != null ) {
		 return hostname; 
	 }
	 
	 synchronized (VERSION) {

		 if ( hostname == null ) {
			 //ArrayList list = new ArrayList();
			 
			 hostname = "unknown host";
			 long start = System.currentTimeMillis();

			 Enumeration all = java.net.NetworkInterface.getNetworkInterfaces();

			 while (all.hasMoreElements()) {
				 java.net.NetworkInterface nic = (java.net.NetworkInterface) all.nextElement();
				 Enumeration ipaddresses = nic.getInetAddresses();
				 while (ipaddresses.hasMoreElements()) {

					 start = System.currentTimeMillis();

					 InetAddress ip = (InetAddress) ipaddresses.nextElement();

					 System.err.println("\t\tCanonical hostname: " + ip.getCanonicalHostName());
					 System.err.println("\t\tHost address: " + ip.getHostAddress());
					 System.err.println("\t\tisMCGlobal: " + ip.isMCGlobal());
					 System.err.println("\t\tisLinkLocalAddress: " + ip.isLinkLocalAddress());
					 System.err.println("\t\t"+ip.toString());
					 System.err.println("Getting hostname took: " + ( System.currentTimeMillis() - start ) ) ;
					 
					 hostname =  ip.getCanonicalHostName();
					
				 }
			 }
		 }
		
	 }
	 
     return hostname;

    }

public static void main(String[] args) throws Exception {

    System.err.println("today = " + new java.util.Date());
   
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
     compileDirectory(scriptDir, outDir, "",null);
   }
 }

  }