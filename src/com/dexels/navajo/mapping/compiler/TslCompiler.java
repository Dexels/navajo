package com.dexels.navajo.mapping.compiler;

/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author Arjen Schoneveld
 * @version $Id$
 */

 /**
  * TODO
  *
  * SYMBOL TABLE BIJHOUDEN VAN ATTRIBUUT WAARDEN UIT MAPPABLE OBJECTEN, DAN DEZE SYMBOL TABLE MEEGEVEN AAN EXPRESSION.EVALUATE(),
  * ZODAT NIET VIA INTROSPECTIE DE ATTRIBUUT WAARDEN HOEVEN TE WORDEN BEPAALD
  *
  * $columnValue('AAP') -> Object o = contextMap.getColumnValue('AAP') -> symbolTable.put("$columnValue('AAP')", o);
  *
  * STACK GEBRUIKEN OP CURRENT OBJECT, CURRENT MAPPABLETREENODE, CURRENT INMESSAGE, CURRENT OUTMESSAGE BIJ TE HOUDEN.
  *
  */
import com.dexels.navajo.document.*;
import com.dexels.navajo.document.jaxpimpl.xml.*;
import com.dexels.navajo.mapping.*;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.parser.Expression;
import com.dexels.navajo.parser.TMLExpressionException;
import com.dexels.navajo.parser.Operand;

import org.apache.jasper.compiler.*;

import java.io.*;
import org.w3c.dom.*;
import java.util.Stack;
import java.util.StringTokenizer;

public class TslCompiler {

  private ClassLoader loader = null;

  private int messageListCounter = 0;
  private int asyncMapCounter = 0;
  private int lengthCounter = 0;
  private int functionCounter = 0;
  private int objectCounter = 0;

  public TslCompiler(ClassLoader loader) {
    this.loader = loader;
    messageListCounter = 0;
    if (loader == null)
      this.loader = this.getClass().getClassLoader();
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
      if (c == '"')
        result.append("\\\"");
      else
        result.append(c);
    }
    return result.toString();
  }

  private String removeNewLinesAndSingleQuotes(String str) {
    StringBuffer result = new StringBuffer(str.length());
    for (int i = 0; i < str.length(); i++) {
      char c = str.charAt(i);
      if (c == '\'')
        result.append("\"");
      else if (c == '\n')
        result.append("\\n");
      else
        result.append(c);

    }
    return result.toString();
  }

  private String removeNewLines(String str) {
    StringBuffer result = new StringBuffer(str.length());
    for (int i = 0; i < str.length(); i++) {
      char c = str.charAt(i);
      if (c == '\n')
        result.append("\\n");
      else
        result.append(c);
    }
    return result.toString();
  }

  /**
   * Purpose: rewrite an expression like
   * $columnValue(1) OR $columnValue('AAP') to (objectName.getColumnValue(1)+"") (IF columnValue returns Object).
   *                                     OR to ((Integer) objectName.getColumnValue(1)).intValue() (IF COLUMN VALUE RETURNS AN INTEGER).
   *                                     OR to ...
   * @param objectName
   * @param call
   * @param c
   * @param attr
   * @param result, return the rewritten statement in the StringBuffer.
   * @return the datatype: java.lang.String, java.lang.Integer, java.lang.Float, java.util.Date, int, boolean, float, etc.
   */
  private String rewriteAttributeCall(String objectName, String call, Class c, String attr, StringBuffer result) {
    //String type = MappingUtils.getFieldType(c, attr);
    return "";
  }


  private String printIdent(int count) {
    StringBuffer identStr = new StringBuffer();
    for (int i = 0; i < count; i++) {
      identStr.append(" ");
    }
    return identStr.toString();
  }

  private  Element getNextElement(Node n) {
    NodeList children = n.getChildNodes();
    for (int i = 0; i < children.getLength(); i++) {
      if (children.item(i) instanceof Element)
        return (Element) children.item(i);
    }
    return null;
  }

  private int countNodes(NodeList l, String name) {
    int count = 0;
    for (int i = 0; i < l.getLength(); i++) {
      if (l.item(i).getNodeName().equals(name))
        count++;
    }
    return count;
  }

  private String removeWhiteSpaces(String s) {
    StringBuffer result = new StringBuffer();
    for (int i = 0; i < s.length(); i++) {
      char c = s.charAt(i);
      if (c != ' ')
        result.append(c);
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
  public String optimizeExpresssion(int ident, String clause, String className, String objectName) {

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

      if (((firstChar > 'a' && firstChar < 'z')) || ((firstChar > 'A') && (firstChar < 'Z'))) {
        functionCall = true;
      }

      if ((functionCall) && (c != '(')) {
        functionNameBuffer.append(c);
      } else if (functionCall && c == '(') {
        functionName = functionNameBuffer.toString();
        functionNameBuffer = new StringBuffer();
      }

      if (c == '$') { // New attribute found
        StringBuffer name = new StringBuffer();
        i++;
        c = clause.charAt(i);
        while (c != '(' && i < clause.length()) {
          name.append(c);
          i++;
          if (i < clause.length())
              c = clause.charAt(i);
        }
        i++;

        StringBuffer params = new StringBuffer();

        if (clause.indexOf("(") != -1) {
          // Determine parameters.
          int endOfParams = 1;

          while (endOfParams > 0) {
            c = clause.charAt(i);
            if (c == '(')
              endOfParams++;
            else if (c == ')')
              endOfParams--;
            else
              params.append(c);
            i++;
          }
        }

        String expr = "";
        if (functionName.equals(""))
          expr = (params.toString().length() > 0 ? "$"+name+"("+params+")" : "$"+name);
        else
          expr = functionName + "(" + (params.toString().length() > 0 ? "$"+name+"("+params+")" : "$"+name) + ")";

        if (removeWhiteSpaces(expr).equals(removeWhiteSpaces(clause))) {
          // Let's evaluate this directly.
          exact = true;
          try {
            StringBuffer objectizedParams = new StringBuffer();
            StringTokenizer allParams = new StringTokenizer(params.toString(), ",");
            while (allParams.hasMoreElements()) {
              String param = allParams.nextToken();
              // Try to evaluate expression (NOTE THAT IF REFERENCES ARE MADE TO EITHER NAVAJO OR MAPPABLE OBJECTS THIS WILL FAIL
              // SINCE THESE OBJECTS ARE NOT KNOWN AT COMPILE TIME!!!!!!!!!!!!!!1
              Operand op = Expression.evaluate(param, null);
              Object v  = op.value;
              if (v instanceof String) {
                objectizedParams.append("\"" + v + "\"");
              } else if (v instanceof Integer) {
                objectizedParams.append("new Integer("+v+")");
              } else if (v instanceof Float) {
                objectizedParams.append("new Float("+v+")");
              } else if (v instanceof Boolean) {
                objectizedParams.append("new Boolean("+v+")");
              }
              if (allParams.hasMoreElements())
                objectizedParams.append(",");
            }
            Class contextClass = Class.forName(className, false, loader);
            String attrType = MappingUtils.getFieldType(contextClass, name.toString());

            // Try to locate class:
            Class fnc = null;
            if (!functionName.equals(""))
              fnc = Class.forName("com.dexels.navajo.functions." + functionName);

            call = objectName + ".get"+(name.charAt(0)+"").toUpperCase()+name.substring(1)+"("+objectizedParams.toString()+")";

            if (attrType.equals("int"))
              call = "new Integer("+call+")";
            else if (attrType.equals("float") || attrType.equals("double"))
              call = "new Double("+call+")";
            else if (attrType.equals("boolean"))
              call = "new Boolean("+call+")";
          } catch (Exception e) {
            exact = false;
          }
        }
      }
    }

    // Try to evaluate clause directly (compile time).
    if ((!exact) && !clause.equals("TODAY") &&  !clause.equals("null") && (clause.indexOf("[") == -1) && (clause.indexOf("$") == -1) && (clause.indexOf("(") == -1) && (clause.indexOf("+") == -1)) {
      try {
        //System.out.println("CLAUSE = " + clause);
        Operand op = Expression.evaluate(clause, null);
        //System.out.println("op = " + op);
        Object v = op.value;
        //System.out.println("op.value = " + v);
        exact = true;
        if (v instanceof String) {
          call = "\"" + v + "\"";
        } else if (v instanceof Integer) {
          call = "new Integer("+v+")";
        } else if (v instanceof Float) {
          call = "new Float("+v+")";
        } else if (v instanceof Boolean) {
          call ="new Boolean("+v+")";
        }
      } catch (NullPointerException ne) {
        exact = false;
      } catch (Throwable e) {
        exact = false;
      }
    }

    if (!exact && clause.equals("null")) {
      call = "null";
      exact = true;
    }

    // Use Expression.evaluate() if expression could not be executed in an optimized way.
    if (!exact) {
       result.append(printIdent(ident) + "op = Expression.evaluate(\""+ replaceQuotes(clause) +"\", inMessage, currentMap, currentInMsg);\n");
       result.append(printIdent(ident) + "sValue = op.value;\n");
    } else { // USE OUR OPTIMIZATION SCHEME.
        //System.out.println("CALL = " + call);
      result.append(printIdent(ident) + "sValue = " + call + ";\n");
      if (!functionName.equals("")) { // Construct Navajo function instance if needed.
        String functionVar = "function"+(functionCounter++);
        result.append(printIdent(ident) + "com.dexels.navajo.functions."+functionName+" " + functionVar +
                                          " = (com.dexels.navajo.functions."+functionName+") getFunction(" +
                                              "\"com.dexels.navajo.functions."+functionName+"\");\n");
        result.append(printIdent(ident) + functionVar + ".reset();\n");
        result.append(printIdent(ident) + functionVar + ".insertOperand(sValue);\n");
        result.append(printIdent(ident) + "sValue = " + functionVar + ".evaluate();\n");
      }
    }

    return result.toString();
  }

  public String expressionNode(int ident, Element exprElmnt, int leftOver, String className, String objectName) throws Exception {
        StringBuffer result = new StringBuffer();
        boolean isStringOperand = false;

        String condition = exprElmnt.getAttribute("condition");
        String value = XMLutils.XMLUnescape(exprElmnt.getAttribute("value"));

        // Check if operand is given as text node between <expression> tags.
        if (value == null || value.equals("")) {
          Node child = exprElmnt.getFirstChild();
          if (child != null) {
              isStringOperand = true;
              value = child.getNodeValue();
          }
          else
            throw new Exception("Error @" + (exprElmnt.getParentNode() + "/" + exprElmnt) + ": <expression> node should either contain a value attribute or a text child node: >" + value + "<");
        }

        if (!condition.equals("")) {
          result.append(printIdent(ident) + "if (Condition.evaluate(\"" + replaceQuotes(condition) + "\", inMessage, currentMap, currentInMsg)) {\n");
          ident += 2;
        }
        if (!isStringOperand) {
          result.append(optimizeExpresssion(ident, value, className, objectName));
        }
        else {
          result.append(printIdent(ident) + "sValue = \"" + removeNewLines(value) + "\";\n");
        }

        result.append(printIdent(ident) + "matchingConditions = true;\n");
        if (!condition.equals("")) {
          ident -= 2;
          result.append(printIdent(ident) + "}\n");
        }

        if (leftOver > 0)
          result.append(printIdent(ident) + " else \n");

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
        if (!condition.equals(""))
          result.append(printIdent(ident)+ "if (Condition.evaluate(\"" + replaceQuotes(condition) + "\", inMessage, null, null)) {\n");
        else
          result.append(printIdent(ident) + "if (true) {\n");
        // Get required messages.
        result.append(printIdent(ident+2) + "com.dexels.navajo.document.Method m = NavajoFactory.getInstance().createMethod(outDoc, \"" +
                                          name + "\", \"\");\n");
        result.append(printIdent(ident+2) + "m.setDescription(\"" + description + "\");\n");
        NodeList required = e.getChildNodes();
        for (int j = 0; j < required.getLength(); j++) {
          if (required.item(j).getNodeName().equals("required")) {
            String reqMsg = ((Element) required.item(j)).getAttribute("message");
            result.append(printIdent(ident+2) + "m.addRequired(\"" + reqMsg + "\");\n");
          }
        }
        result.append(printIdent(ident+2) + "outDoc.addMethod(m);\n");
        result.append(printIdent(ident) +"}\n");
      }
    }

    return result.toString();
  }

  public  String messageNode(int ident, Element n, String className, String objectName) throws Exception {
    StringBuffer result = new StringBuffer();

    String messageName = n.getAttribute("name");
    String condition = n.getAttribute("condition");
    String type = n.getAttribute("type");
    String mode = n.getAttribute("mode");
    String count = n.getAttribute("count");

    //System.out.println("COUNT = " + count);
    type = (type == null) ? "" : type;
    mode = (mode == null) ? "" : mode;
    condition = (condition == null) ? "" : condition;
    count = (count == null || count.equals("")) ? "1" : count;

    boolean isLazy = mode.equals(Message.MSG_MODE_LAZY);

    boolean conditionClause = false;

    // If <message> node is conditional:
    if (!condition.equals("")) {
         conditionClause = true;
         result.append(printIdent(ident) + "if (Condition.evaluate(\"" + replaceQuotes(condition) + "\", inMessage, currentMap, currentInMsg)) { \n");
         ident += 2;
    }

    Element nextElt = getNextElement(n);
    String ref = "";
    String filter = "";
    boolean isArrayAttr = false;
    boolean isSubMapped = false;
    Class contextClass = null;

    // Check if <message> is mapped to an object attribute:
    if (nextElt != null && nextElt.getNodeName().equals("map") && nextElt.getAttribute("ref") != null && !nextElt.getAttribute("ref").equals("")) {
      ref = nextElt.getAttribute("ref");
      filter = nextElt.getAttribute("filter");
      //System.out.println("REF = " + ref);
      //System.out.println("filter = " + filter);
      //System.out.println("Classname = " + className);
      contextClass = Class.forName(className, false, loader);
      String attrType = MappingUtils.getFieldType(contextClass, ref);
      isArrayAttr = MappingUtils.isArrayAttribute(contextClass, ref);
      if (isArrayAttr)
         type = Message.MSG_TYPE_ARRAY;
      isSubMapped = true;
    }
    //System.out.println("isArrayAttr = " + isArrayAttr);

    // Construct Lazy stuff if it's an array message and it has a lazy flag.
    if (isLazy && isArrayAttr) {
      result.append(printIdent(ident) + "lm = access.getLazyMessages();\n");
      result.append(printIdent(ident) + "fullMsgName = \"/\" + ( (currentOutMsg != null ? (currentOutMsg.getFullMessageName() + \"/\") : \"\") + \"" + messageName + "\");\n");
      result.append(printIdent(ident) + "if (lm.isLazy(fullMsgName)) {\n");
      result.append(printIdent(ident+2) + "la = (LazyArray) currentMap.myObject;\n");
      result.append(printIdent(ident+2)+"la.setEndIndex(\"" + ref + "\", lm.getEndIndex(fullMsgName));\n");
      result.append(printIdent(ident+2)+"la.setStartIndex(\"" + ref + "\",lm.getStartIndex(fullMsgName));\n");
      result.append(printIdent(ident) + "}\n");
    }

    // Create the message(s). Multiple messages are created if count > 1.
    result.append(printIdent(ident) + "count = " + (count.equals("1") ? "1" : "((Integer) Expression.evaluate(\""+ count +"\", inMessage, currentMap, currentInMsg).value).intValue()") + ";\n");
    String messageList = "messageList"+(messageListCounter++);
    result.append(printIdent(ident) + "Message [] " + messageList + " = null;\n");
    result.append(printIdent(ident) + messageList + " = MappingUtils.addMessage(outDoc, currentOutMsg, \"" + messageName + "\", \"\", count, \"" + type + "\", \"" + mode + "\");\n");
    result.append(printIdent(ident) + "for (int messageCount"+(ident)+" = 0; messageCount"+(ident)+" < " + messageList + ".length; messageCount"+(ident)+"++) {\n");
    result.append(printIdent(ident+2) + "outMsgStack.push(currentOutMsg);\n");
    result.append(printIdent(ident+2) + "currentOutMsg = " + messageList + "[messageCount"+(ident)+"];\n");

    if (isLazy && isArrayAttr) {
      result.append(printIdent(ident+2) + "if (lm != null && lm.isLazy(fullMsgName)) {\n");
      result.append(printIdent(ident+4) + "currentOutMsg.setLazyTotal(la.getTotalElements(\""+ref+"\"));\n");
      result.append(printIdent(ident+4) + "currentOutMsg.setLazyRemaining(la.getRemainingElements(\""+ref+"\"));\n");
      result.append(printIdent(ident+4) + "currentOutMsg.setArraySize(la.getCurrentElements(\""+ref+"\"));\n");
      result.append(printIdent(ident+4) + "lm = null; fullMsgName = \"\";\n");
      result.append(printIdent(ident+2) + "}\n");
    }

    result.append(printIdent(ident+2) + "access.setCurrentOutMessage(currentOutMsg);\n");

    if (isSubMapped && isArrayAttr) {
      type = Message.MSG_TYPE_ARRAY_ELEMENT;
      String lengthName = "length"+(lengthCounter++);
      result.append(printIdent(ident+2) + "int " + lengthName +" = (((" + className + ") currentMap.myObject).get"+((ref.charAt(0)+"").toUpperCase()+ref.substring(1)) + "() == null ? 0 : ((" + className + ") currentMap.myObject).get"+((ref.charAt(0)+"").toUpperCase()+ref.substring(1)) + "().length);\n");
      result.append(printIdent(ident+2) + "for (int i"+(ident+2)+" = 0; i"+(ident+2)+" < " + lengthName + "; i"+(ident+2)+"++) {\n");
      result.append(printIdent(ident+4) + "outMsgStack.push(currentOutMsg);\n");
      result.append(printIdent(ident+4) + "treeNodeStack.push(currentMap);\n");
      result.append(printIdent(ident+4) + "currentOutMsg = MappingUtils.getMessageObject(\"" + messageName + "\", currentOutMsg, true, outDoc, false, \"\");\n");
      result.append(printIdent(ident+4) + "access.setCurrentOutMessage(currentOutMsg);\n");
      result.append(printIdent(ident+4) + "currentMap = new MappableTreeNode(currentMap, ((" + className + ") currentMap.myObject).get"+((ref.charAt(0)+"").toUpperCase()+ref.substring(1)) + "()[i"+(ident+2)+"]);\n");

      // If filter is specified, evaluate filter first:
      if (!filter.equals("")) {
          result.append(printIdent(ident+4) + "if (Condition.evaluate(\"" + replaceQuotes(filter) + "\", inMessage, currentMap, currentInMsg)) {\n");
          ident += 2;
      }

      String subClassName = MappingUtils.getFieldType(contextClass, ref);
      NodeList children = nextElt.getChildNodes();
      String subObjectName = "mappableObject"+(objectCounter++);
      result.append(printIdent(ident+4) + subClassName + " " + subObjectName + " = (" + subClassName + ") currentMap.myObject;\n");

      for (int i = 0; i < children.getLength(); i++) {
        if (children.item(i) instanceof Element)
            result.append(compile(ident+4, children.item(i), subClassName, subObjectName));
      }

      if (!filter.equals("")) {
        ident -= 2;
        result.append(printIdent(ident+4) + "}\n");
      }

      result.append(printIdent(ident+2) + "currentOutMsg = (Message) outMsgStack.pop();\n");
      result.append(printIdent(ident+2) + "access.setCurrentOutMessage(currentOutMsg);\n");
      result.append(printIdent(ident+2) + "currentMap = (MappableTreeNode) treeNodeStack.pop();\n");
      result.append(printIdent(ident+2) + "} // EOF Array map result from contextMap \n");
    } else if (isSubMapped) {  // Not an array

      result.append(printIdent(ident+2) + "treeNodeStack.push(currentMap);\n");
      result.append(printIdent(ident+2) + "currentMap = new MappableTreeNode(currentMap, ((" + className + ") currentMap.myObject).get"+((ref.charAt(0)+"").toUpperCase()+ref.substring(1)) + "());\n");
      result.append(printIdent(ident+2) + "if (currentMap.myObject != null) {\n");
      result.append(printIdent(ident+4) + "outMsgStack.push(currentOutMsg);\n");
      result.append(printIdent(ident+4) + "currentOutMsg = MappingUtils.getMessageObject(\"" + messageName + "\", currentOutMsg, true, outDoc, false, \"\");\n");
      result.append(printIdent(ident+4) + "access.setCurrentOutMessage(currentOutMsg);\n");

      String subClassName = MappingUtils.getFieldType(contextClass, ref);
      NodeList children = nextElt.getChildNodes();
      for (int i = 0; i < children.getLength(); i++) {
        result.append(compile(ident+4, children.item(i), className, objectName));
      }
      result.append(printIdent(ident+4) + "currentOutMsg = (Message) outMsgStack.pop();\n");
      result.append(printIdent(ident+4) + "access.setCurrentOutMessage(currentOutMsg);\n");
      result.append(printIdent(ident+2) + "}\n");
      result.append(printIdent(ident+2) + "currentMap = (MappableTreeNode) treeNodeStack.pop();\n");
    } else { // Just some new tags under the "message" tag.
      NodeList children = n.getChildNodes();
      for (int i = 0; i < children.getLength(); i++) {
        result.append(compile(ident+2, children.item(i), className, objectName));
      }
    }
    result.append(printIdent(ident) + "currentOutMsg = (Message) outMsgStack.pop();\n");
    result.append(printIdent(ident) + "access.setCurrentOutMessage(currentOutMsg);\n");
    result.append(printIdent(ident) + "} // EOF messageList for \n");

    if (conditionClause) {
      ident -= 2;
      result.append(printIdent(ident) + "} // EOF message condition \n");
    }

    return result.toString();
  }

  public  String propertyNode(int ident, Element n, boolean canBeSubMapped, String className, String objectName) throws Exception {
    StringBuffer result = new StringBuffer();

    String propertyName = n.getAttribute("name");
    String direction = n.getAttribute("direction");
    String type = n.getAttribute("type");
    String length = n.getAttribute("length");
    String value = n.getAttribute("value");
    String description = n.getAttribute("description");
    String cardinality = n.getAttribute("cardinality");
    String condition = n.getAttribute("condition");

    value = (value == null) || (value.equals("")) ? "" : value;
    length = (length == null || length.equals("")) ? "15" : length;
    type = (type == null) ? "" : type;
    description = (description == null) ? "" : description;
    cardinality = (cardinality == null || cardinality.equals("")) ? "1" : cardinality;
    condition = (condition == null) ? "" : condition;

    boolean conditionClause = false;
    if (!condition.equals("")) {
         conditionClause = true;
         result.append(printIdent(ident) + "if (Condition.evaluate(\"" + replaceQuotes(condition) + "\", inMessage, currentMap, currentInMsg)) { \n");
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
        result.append(expressionNode(ident, (Element) children.item(i), --exprCount, className, objectName));
      } else if (children.item(i).getNodeName().equals("option")) {
        isSelection = true;
        String optionName = ((Element) children.item(i)).getAttribute("name");
        String optionValue = ((Element) children.item(i)).getAttribute("value");
        String selectedValue = ((Element) children.item(i)).getAttribute("selected");
        boolean selected = (selectedValue.equals("1"));
        type = "selection";
        optionItems.append("p.addSelection(NavajoFactory.getInstance().createSelection(outDoc, \"" + optionName + "\", \"" + optionValue + "\", " + selected + "));\n");
      } else if (children.item(i).getNodeName().equals("map")) {  // ABout to map a "selection" property!!!
        if (!canBeSubMapped)
          throw new Exception("This property can not be submapped: " + propertyName);
        if (!type.equals("selection"))
          throw new Exception("Only selection properties can be submapped: " + propertyName);
        mapNode = (Element) children.item(i);
        isMapped = true;
        isSelection = true;

      }
    }

    if (!hasChildren || isSelection) {
      result.append(printIdent(ident) + "sValue = new String(\""+value + "\");\n");
      result.append(printIdent(ident) + "type = \"" + type + "\";\n");
    } else {
      result.append(printIdent(ident) + "type = (sValue != null) ? MappingUtils.determineNavajoType(sValue) : \"" + type + "\";\n");
    }

    if (n.getNodeName().equals("property")) {
      result.append(printIdent(ident) + "p = MappingUtils.setProperty(false, currentOutMsg, \"" + propertyName + "\", sValue, type, \"" + direction + "\", \"" + description + "\", " +
                            Integer.parseInt(length) + ", outDoc, inMessage, !matchingConditions);\n");
    } else { // parameter
      result.append(printIdent(ident) + "p = MappingUtils.setProperty(true, parmMessage, \"" + propertyName + "\", sValue, type, \"" + direction + "\", \"" + description + "\", " +
                            Integer.parseInt(length) + ", outDoc, inMessage, !matchingConditions);\n");
    }

    if (isMapped) {
      contextClass = Class.forName(className, false, loader);
      String ref = mapNode.getAttribute("ref");
      result.append(printIdent(ident+2) + "for (int i"+(ident+2)+" = 0; i"+(ident+2)+" < " + objectName + ".get"+((ref.charAt(0)+"").toUpperCase()+ref.substring(1)) + "().length; i"+(ident+2)+"++) {\n");
      result.append(printIdent(ident+4) + "treeNodeStack.push(currentMap);\n");
      result.append(printIdent(ident+4) + "currentMap = new MappableTreeNode(currentMap, " + objectName + ".get"+((ref.charAt(0)+"").toUpperCase()+ref.substring(1)) + "()[i"+(ident+2)+"]);\n");
      result.append(printIdent(ident+4) + "String optionName = \"\";\n");
      result.append(printIdent(ident+4) + "String optionValue = \"\";\n");
      result.append(printIdent(ident+4) + "boolean optionSelected = false;\n");
      children = mapNode.getChildNodes();
      String subClassName = MappingUtils.getFieldType(contextClass, ref);
      String subClassObjectName = "mappableObject" + (objectCounter++);
      result.append(printIdent(ident+4) + subClassName + " " + subClassObjectName + " = (" + subClassName + ") currentMap.myObject;\n");
      for (int i = 0; i < children.getLength(); i++) {
        if (children.item(i).getNodeName().equals("property")) {
          Element elt = (Element) children.item(i);
          String subPropertyName = elt.getAttribute("name");
          if (!(subPropertyName.equals("name") || subPropertyName.equals("value") || subPropertyName.equals("selected"))) {
              throw new Exception("Only 'name' or 'value' named properties expected when submapping a 'selection' property");
          }
          NodeList expressions = elt.getChildNodes();
          int leftOver = countNodes(expressions, "expression");
          //System.out.println("LEFTOVER = " + leftOver + ", CHILD NODES = " + expressions.getLength());

          for (int j = 0; j < expressions.getLength(); j++) {
            //System.out.println("expression.item("+j+") = " + expressions.item(j));
            if ((expressions.item(j) instanceof Element) && expressions.item(j).getNodeName().equals("expression"))
              result.append(expressionNode(ident+4, (Element) expressions.item(j), --leftOver, subClassName, subClassObjectName));
          }
          if (subPropertyName.equals("name")) {
            result.append(printIdent(ident+4) + "optionName = (sValue != null) ? sValue + \"\" : \"\";\n");
          } else if (subPropertyName.equals("value")) {
            result.append(printIdent(ident+4) + "optionValue = (sValue != null) ? sValue + \"\" : \"\";\n");
          } else {
            result.append(printIdent(ident+4) + "optionSelected = (sValue != null) ? ((Boolean) sValue).booleanValue() : false;\n");
          }
        } else if (children.item(i) instanceof Element) {
          throw new Exception("<property> tag expected while sub-mapping a selection property: " + children.item(i).getNodeName());
        }
      }
      result.append(printIdent(ident+4) + "p.addSelection(NavajoFactory.getInstance().createSelection(outDoc, optionName, optionValue, optionSelected));\n");
      result.append(printIdent(ident+4) + "currentMap = (MappableTreeNode) treeNodeStack.pop();\n");
      result.append(printIdent(ident+2) + "} // EOF Array map result to property\n");
    }

    if (isSelection) { // Set selection property stuff.
      result.append(optionItems.toString());
      result.append("p.setCardinality(\"" + cardinality + "\");\n");
    }

    if (conditionClause) {
      ident -= 2;
      result.append(printIdent(ident) + "} // EOF property condition \n");
    }

    return result.toString();

  }

  public  String fieldNode(int ident, Element n, String className, String objectName) throws Exception {

    StringBuffer result = new StringBuffer();

    String attribute = n.getAttribute("name");
    String condition = n.getAttribute("condition");

    condition = (condition == null) ? "" : condition;

    String methodName = "set" + (attribute.charAt(0) + "").toUpperCase()
        + attribute.substring(1, attribute.length());
    NodeList children = n.getChildNodes();

    if (!condition.equals("")) {
      result.append(printIdent(ident) + "if (Condition.evaluate(\"" + replaceQuotes(condition) + "\", inMessage, currentMap, currentInMsg)) { \n");
    } else {
      result.append(printIdent(ident) + "if (true) {\n");
    }
    // Expression nodes.
    boolean isMapped = false;
    Element mapNode = null;

    int exprCount = countNodes(children, "expression");
    for (int i = 0; i < children.getLength(); i++) {
      // Has condition;
      if (children.item(i).getNodeName().equals("expression")) {
        result.append(expressionNode(ident+2, (Element) children.item(i), --exprCount, className, objectName));
      } else if (children.item(i).getNodeName().equals("map")) {
        isMapped = true;
        mapNode = (Element) children.item(i);
      }
    }

    if (!isMapped) {
       String castedValue = "";
        try {
          Class contextClass = Class.forName(className, false, loader);
          String type = MappingUtils.getFieldType(contextClass, attribute);
          if (type.equals("java.lang.String"))
            castedValue = "(String) sValue";
          else if (type.equals("int"))
            castedValue = "((Integer) sValue).intValue()";
          else if (type.equals("double"))
            castedValue = "((Double) sValue).doubleValue()";
          else if (type.equals("java.util.Date"))
            castedValue = "((java.util.Date) sValue)";
          else if (type.equals("boolean"))
            castedValue = "((Boolean) sValue).booleanValue()";
          else if (type.equals("float"))
            castedValue = "((Float sValue).floatValue();";
          else
            castedValue = "sValue";

        } catch (Exception e) {
          e.printStackTrace();
        }
      result.append(printIdent(ident+2) + objectName+ "." + methodName+"(" + castedValue + ");\n");
    } else {  // Field with ref: indicates that a message or set of messages is mapped to attribute (either Array Mappable or singular Mappable)
      String ref = mapNode.getAttribute("ref");
      String filter = mapNode.getAttribute("filter");
      filter = (filter == null) ? "" : filter;
      result.append(printIdent(ident+2) + "// Map message(s) to field\n");
      String messageListName = "messages"+ident;
      result.append(printIdent(ident+2) + "ArrayList "+messageListName+" = MappingUtils.getMessageList(currentInMsg, inMessage, \"" + ref +
                      "\", \"" + filter + "\", currentMap);\n");
      Class contextClass = Class.forName(className, false, loader);
      String type = MappingUtils.getFieldType(contextClass, attribute);
      boolean isArray = MappingUtils.isArrayAttribute(contextClass, attribute);
      //System.out.println("TYPE FOR " + attribute + " IS: " + type + ", ARRAY = " + isArray);
      if (isArray) {
        String subObjectsName = "subObject"+ident;
        String loopCounterName = "j"+ident;
        result.append(printIdent(ident+2) + type+ " [] "+subObjectsName+" = new " + type +"["+messageListName+".size()];\n");
        result.append(printIdent(ident+2) + "for (int "+loopCounterName+" = 0; "+loopCounterName+" < "+messageListName+".size(); "+loopCounterName+"++) {\n");
        // currentInMsg, inMsgStack
        ident += 4;
        result.append(printIdent(ident) + "inMsgStack.push(currentInMsg);\n");
        result.append(printIdent(ident) + "currentInMsg = (Message) "+messageListName+".get("+loopCounterName+");\n");
        result.append(printIdent(ident) + "treeNodeStack.push(currentMap);\n");
        result.append(printIdent(ident) + "currentMap = new MappableTreeNode(currentMap, (Mappable) classLoader.getClass(\"" + type + "\").newInstance());\n");
        result.append(printIdent(ident) + "((Mappable) currentMap.myObject).load(parms, inMessage, access, config);\n");
        result.append(printIdent(ident) + subObjectsName+"["+loopCounterName+"] = ("+type+") currentMap.myObject;\n");
        children = mapNode.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
          result.append(compile(ident+2, children.item(i), type, subObjectsName+"["+loopCounterName+"]"));
        }
        result.append(printIdent(ident) + "currentInMsg = (Message) inMsgStack.pop();\n");
        result.append(printIdent(ident) + "currentMap = (MappableTreeNode) treeNodeStack.pop();\n");
        ident -= 4;
        result.append(printIdent(ident+2) + "} // FOR loop for "+loopCounterName+"\n");
        result.append(printIdent(ident+2) + objectName+ "." + methodName+"("+subObjectsName+");\n");
      }
    }
    result.append(printIdent(ident) + "}\n");
    return result.toString();
  }

  public String breakNode(int ident, Element n) throws Exception {

    StringBuffer result = new StringBuffer();
    String condition  = n.getAttribute("condition");
    if (condition.equals(""))
      result.append(printIdent(ident) + "if (true) {");
    else
      result.append(printIdent(ident) + "if (Condition.evaluate(\"" + replaceQuotes(condition) + "\", inMessage, currentMap, currentInMsg)) { \n");

    result.append(printIdent(ident+2) + "throw new BreakEvent();\n");
    result.append(printIdent(ident) + "}\n");

      return result.toString();
  }

  public String debugNode(int ident, Element n) throws Exception {
    StringBuffer result = new StringBuffer();
    String value = n.getAttribute("value");
    result.append(printIdent(ident) + "op = Expression.evaluate(\""+ replaceQuotes(value) +"\", inMessage, currentMap, currentInMsg);\n");
    result.append(printIdent(ident) + "System.out.println(\"in PROCESSING SCRIPT: \" + access.rpcName + \" DEBUG INFO: \" + op.value);\n");
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

  public  String mapNode(int ident, Element n) throws Exception {

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
         result.append(printIdent(ident) + "if (Condition.evaluate(\"" + replaceQuotes(condition) + "\", inMessage, currentMap, currentInMsg)) { \n");
         ident += 2;
    }

    String className = object;

    if (!name.equals("")) { // We have a potential async mappable object.
      //System.out.println("POTENTIAL MAPPABLE OBJECT " + className);
      Class contextClass = Class.forName(className, false, loader);
      if (contextClass.getSuperclass().getName().equals("com.dexels.navajo.mapping.AsyncMappable")) {
        asyncMap = true;
      } else {
        asyncMap = false;
      }
    }

    if (asyncMap) {
      String aoName = "ao"+asyncMapCounter;
      String headerName = "h"+asyncMapCounter;
      String asyncMapFinishedName = "asyncMapFinished"+asyncMapCounter;
      String callbackRefName = "callbackRef"+asyncMapCounter;
      String interruptTypeName = "interruptType"+asyncMapCounter;
      String asyncMapName = "asyncMap"+asyncMapCounter;
      String asyncStatusName = "asyncStatus"+asyncMapCounter;
      String resumeAsyncName = "resumeAsync"+asyncMapCounter;
      asyncMapCounter++;

      result.append(printIdent(ident) + "boolean " + asyncMapName +" = true;\n");
      result.append(printIdent(ident) + "Header " + headerName + " = inMessage.getHeader();\n");
      result.append(printIdent(ident) + "String " + callbackRefName + " = " + headerName + ".getCallBackPointer(\""+name+"\");\n");
      result.append(printIdent(ident) + "AsyncMappable " + aoName + " = null;\n");
      result.append(printIdent(ident) + "boolean " + asyncMapFinishedName + " = false;\n");
      result.append(printIdent(ident) + "boolean " + resumeAsyncName + " = false;\n");
      result.append(printIdent(ident) + "String " + asyncStatusName + " = \"request\";\n\n");
      result.append(printIdent(ident) + "if (" + callbackRefName + " != null) {\n");
      ident+=2;
      result.append(printIdent(ident) + aoName + " = config.getAsyncStore().getInstance(" + callbackRefName + ");\n");
      result.append(printIdent(ident) + "String " + interruptTypeName + " = " + headerName + ".getCallBackInterupt(\""+name+"\");\n");
      result.append(printIdent(ident) + " if (" + aoName + " == null) {\n " +
          "  throw new UserException( -1, \"Asynchronous object reference instantiation error: no sych instance (perhaps cleaned up?)\");\n}\n");
      result.append(printIdent(ident) + "if ("+interruptTypeName+".equals(\"kill\")) { // Kill thread upon client request.\n" +
                                        "   " +aoName + ".stop();\n" +
                                        "   config.getAsyncStore().removeInstance(" + callbackRefName + ");\n" +
                                        "   return;\n" +
                                        "} else if ("+interruptTypeName+".equals(\"interrupt\")) {\n" +
                                        "   "+aoName+".interrupt();\n " +
                                        "   return;\n" +
                                        "} else if ("+interruptTypeName+".equals(\"resume\")) { " +
                                        "  "+aoName+".resume();\n" +
                                        "}\n");
      ident-=2;
      result.append(printIdent(ident) + "} else { // New instance!\n");
      result.append(printIdent(ident) + aoName + " = (AsyncMappable) classLoader.getClass(\"" + object + "\").newInstance();\n" +
                                        "  // Call load method for async map in advance:\n" +
                                        "  " + aoName + ".load(parms, inMessage, access, config);\n" +
                                        "  " + callbackRefName + " = config.getAsyncStore().addInstance( " + aoName + " );\n" +
                                        "}\n");
      result.append(printIdent(ident) + "treeNodeStack.push(currentMap);\n");
      result.append(printIdent(ident) + "currentMap = new MappableTreeNode(currentMap, "+aoName+");\n");

      result.append(printIdent(ident) + "currentMap.name = \"" + name + "\";\n");
      result.append(printIdent(ident) + "currentMap.ref = " + callbackRefName + ";\n");
      result.append(printIdent(ident) + aoName + ".afterReload(\""+name+"\", "+callbackRefName+");\n");

      result.append(printIdent(ident) + "try {\n");
      ident+=2;

      result.append(printIdent(ident) + asyncMapFinishedName + " = " + aoName + ".isFinished(outDoc, access);\n");
      NodeList response = n.getElementsByTagName("response");
      boolean hasResponseNode = false;
      if (response.getLength() > 1)
        hasResponseNode = true;
      NodeList running = n.getElementsByTagName("running");
      boolean hasRunningNode = false;
      if (response.getLength() > 1)
        hasRunningNode = true;
      NodeList request = n.getElementsByTagName("request");

      boolean whileRunning = ((Element) response.item(0)).getAttribute("while_running").equals("true");
      result.append(printIdent(ident) + "if ("+ asyncMapFinishedName + " || ("+aoName+".isActivated() && " + hasResponseNode + " && " + whileRunning + ")) {\n");
      result.append(printIdent(ident) + "  " +asyncStatusName+ " = \"response\";\n");
      result.append(printIdent(ident) + "  "+aoName+".beforeResponse();\n");
      result.append(printIdent(ident) + "  if ("+aoName+".isActivated() && " + whileRunning + ") {\n");
      result.append(printIdent(ident) + "     "+aoName+".interrupt();\n");
      result.append(printIdent(ident) + "     "+resumeAsyncName+" = true;\n");
      result.append(printIdent(ident) + "  }\n");
      result.append(printIdent(ident) + "} else if (!"+aoName+".isActivated()) {\n");
      result.append(printIdent(ident) + "  "+asyncStatusName+" = \"request\";\n");
      result.append(printIdent(ident) + "} else if (" + hasRunningNode + ") {\n");
      result.append(printIdent(ident) + "  "+asyncStatusName+" = \"running\";\n");
      result.append(printIdent(ident) + "  "+aoName+".interrupt();\n");
      result.append(printIdent(ident) + "  "+resumeAsyncName+" = true;\n");
      result.append(printIdent(ident) + "}\n");

      NodeList children = null;
      result.append(printIdent(ident) + "if ("+asyncStatusName+".equals(\"response\")) {\n");
      children = response.item(0).getChildNodes();
      for (int i = 0; i < children.getLength(); i++) {
        if (children.item(i) instanceof Element)
          result.append(compile(ident+2, children.item(i), className, aoName));
      }
      result.append(printIdent(ident) + "} else if ("+asyncStatusName+".equals(\"request\")) {\n");
      children = request.item(0).getChildNodes();
      for (int i = 0; i < children.getLength(); i++) {
        if (children.item(i) instanceof Element)
          result.append(compile(ident+2, children.item(i), className, aoName));
      }
      result.append(printIdent(ident) + "} else if ("+asyncStatusName+".equals(\"running\")) {\n");
      children = running.item(0).getChildNodes();
      for (int i = 0; i < children.getLength(); i++) {
        if (children.item(i) instanceof Element)
          result.append(compile(ident+2, children.item(i), className, aoName));
      }
      result.append(printIdent(ident) + "}\n");

      result.append(printIdent(ident) + "if ((currentMap.myObject != null)) {\n");
      result.append(printIdent(ident+2) + "if (!"+asyncMapFinishedName+") {\n");
      result.append(printIdent(ident+4) + "if ("+resumeAsyncName+") { "+aoName+".resume(); "+aoName+".afterResponse(); } else { "+aoName+".afterRequest(); "+aoName+".runThread(); }\n");
      result.append(printIdent(ident+2) + "} else {\n");
      result.append(printIdent(ident+4) + "((Mappable) currentMap.myObject).store();\n");
      result.append(printIdent(ident+4) + "config.getAsyncStore().removeInstance(currentMap.ref);\n");
      result.append(printIdent(ident+2) + "}\n");
      result.append(printIdent(ident) + "}\n");

      result.append(printIdent(ident) + "} catch (Exception e"+ident+") {\n");
      result.append(printIdent(ident) + "  //e"+ident+".printStackTrace();\n");
      result.append(printIdent(ident) + " config.getAsyncStore().removeInstance(currentMap.ref);\n");
      result.append(printIdent(ident) + "  ((Mappable) currentMap.myObject).kill();\n");
      result.append(printIdent(ident) + "  throw e"+ident+";\n");
      result.append(printIdent(ident) + "}\n");

      result.append(printIdent(ident) + "currentMap = (MappableTreeNode) treeNodeStack.pop();\n");

    } else {

      result.append(printIdent(ident) + "treeNodeStack.push(currentMap);\n");
      result.append(printIdent(ident) + "currentMap = new MappableTreeNode(currentMap, (Mappable) classLoader.getClass(\"" + object + "\").newInstance());\n");
      String objectName = "mappableObject"+(objectCounter++);
      result.append(printIdent(ident) + className + " " + objectName + " = (" + className + ") currentMap.myObject;\n");
      result.append(printIdent(ident) + objectName + ".load(parms, inMessage, access, config);\n");

      result.append(printIdent(ident) + "try {\n");

      NodeList children = n.getChildNodes();
      for (int i = 0; i < children.getLength(); i++) {
        result.append(compile(ident+2, children.item(i), className, objectName));
      }

      result.append(printIdent(ident) + "} catch (Exception e"+ident+") {\n");
      result.append(printIdent(ident) + "  //e"+ident+".printStackTrace();\n");
      result.append(printIdent(ident) + objectName + ".kill();\n");
      result.append(printIdent(ident) + "  throw e"+ident+";\n");
      result.append(printIdent(ident) + "}\n");
      result.append(printIdent(ident) + objectName + ".store();\n");
      result.append(printIdent(ident) + "currentMap = (MappableTreeNode) treeNodeStack.pop();\n");

    }

    if (conditionClause) {
      ident -= 2;
      result.append(printIdent(ident) + "} // EOF map condition \n");
    }

    return result.toString();
  }

  public  String compile(int ident, Node n, String className, String objectName) throws Exception {
    StringBuffer result = new StringBuffer();

    if (n.getNodeName().equals("map")) {
      result.append(printIdent(ident) + "{ // Starting new mappable object context. \n");
      result.append(mapNode(ident+2, (Element) n));
      result.append(printIdent(ident) + "} // EOF MapContext \n");
    } else if (n.getNodeName().equals("field")) {
      result.append(fieldNode(ident, (Element) n, className, objectName));
    } else if (n.getNodeName().equals("param") || n.getNodeName().equals("property")) {
      result.append(propertyNode(ident, (Element) n, true, className, objectName));
    } else if (n.getNodeName().equals("message")) {
      result.append(messageNode(ident, (Element) n, className, objectName));
    } else if (n.getNodeName().equals("methods")) {
      result.append(methodsNode(ident, (Element) n));
    } else if (n.getNodeName().equals("debug")) {
      result.append(debugNode(ident, (Element) n));
    } else if (n.getNodeName().equals("break")) {
      result.append(breakNode(ident, (Element) n));
    }

    return result.toString();
  }

  public void compileScript(String script, String scriptPath, String workingPath, String packagePath) throws Exception {

    Document tslDoc = null;
    StringBuffer result = new StringBuffer();


    File dir = new File(workingPath);
    if (!dir.exists())
      dir.mkdirs();

    FileWriter fo = new FileWriter(new File(dir, script+".java"));

    tslDoc = XMLDocumentUtils.createDocument(new FileInputStream(scriptPath+"/"+script+".xsl"), false);


    String importDef = (packagePath.equals("") ? "" : "package " + MappingUtils.createPackageName(packagePath) + ";\n\n") +
                       "import com.dexels.navajo.server.*;\n" +
                       "import com.dexels.navajo.mapping.*;\n" +
                       "import com.dexels.navajo.document.*;\n" +
                       "import com.dexels.navajo.parser.*;\n" +
                       "import java.util.ArrayList;\n" +
                       "import java.util.HashMap;\n" +
                       "import java.util.Stack;\n\n\n";
    result.append(importDef);

    String classDef = "public class " + script + " extends CompiledScript {\n\n\n";
    result.append(classDef);

    String methodDef = "public void execute(Parameters parms, Navajo inMessage, Access access, NavajoConfig config) throws Exception { \n\n";
    result.append(methodDef);

    String definitions = "MappableTreeNode currentMap = null;\n" +
                         "Stack treeNodeStack = new Stack();\n" +
                         "Navajo outDoc = access.getOutputDoc();\n" +
                         "Message currentOutMsg = null;\n" +
                         "Stack outMsgStack = new Stack();\n" +
                         "Message currentInMsg = null;\n" +
                         "Stack inMsgStack = new Stack();\n" +
                         "Message parmMessage = null;\n" +
                         "Object sValue = null;\n" +
                         "Operand op = null;\n" +
                         "String type = \"\";\n" +
                         "Property p = null;\n" +
                         "LazyArray la = null;\n" +
                         "LazyMessageImpl lm = null;\n" +
                         "String fullMsgName = \"\";\n" +
                         "boolean matchingConditions = false;\n" +
                         "HashMap evaluatedAttributes = null;\n" +
                         "int count = 1;\n";

    result.append(definitions);

    NodeList children = tslDoc.getFirstChild().getChildNodes();
    for (int i = 0; i < children.getLength(); i++) {
       String str = compile(0, children.item(i), "", "");
       result.append(str);
    }

    result.append("}// EOM\n");

    result.append("}//EOF");

    fo.write(result.toString());
    fo.close();

    //System.out.println(result.toString());
  }

  public void finalize() {
    System.out.println("FINALIZE() METHOD CALL FOR TslCompiler OBJECT " + this);
  }

  private static void compileStandAlone(boolean all, String script, String input, String output) {
    System.out.println("Processing " + script);
    try {
       TslCompiler tslCompiler = new TslCompiler(null);

      if (all) {
          tslCompiler.compileScript(script, input, output, "");
          System.out.println("CREATED JAVA FILE FOR SCRIPT: " + script);
       } else {
          //tslCompiler.compileScript(script, scripts[0].getParentFile().getAbsolutePath(), output, "");
          System.out.println("CREATED JAVA FILE FOR SCRIPT: " + script);
       }

       String classPath = System.getProperty("java.class.path");

       //System.out.println("in NavajoCompiler(): new classPath = " + classPath);

       JavaCompiler compiler = new SunJavaCompiler();

       compiler.setClasspath(classPath);
       compiler.setOutputDir(output);
       compiler.setClassDebugInfo(true);
       compiler.setEncoding("UTF8");
       compiler.setMsgOutput(System.out);
       compiler.compile(output + "/" + script + ".java");

       System.out.println("COMPILED JAVA FILE INTO CLASS FILE");
    } catch (Exception e) {
       e.printStackTrace();
       System.out.println("Could not compile script " + script + ", reason: " + e.getMessage());
       System.exit(1);
    }
  }

  public static void main(String [] args) throws Exception {

    java.util.Date d = (java.util.Date) null;
    System.out.println("d = " + d);
    if (args.length == 0) {
      System.out.println("TslCompiler: Usage: java com.dexels.navajo.mapping.compiler.TslCompiler <scriptDir> <compiledDir> [-all | scriptName]");
      System.exit(1);
    }
    boolean all = args[2].equals("-all");
    if (all)
      System.out.println("SCRIPT DIR = " + args[0]);

    System.out.println("SERVICE = " + args[2]);

    String input = args[0];
    String output = args[1];
    String service = args[2];

    File [] scripts = null;

    if (all) {
      File scriptDir = new File(input);
      scripts = scriptDir.listFiles();
    } else {
      scripts = new File[1];
      scripts[0] = new File(input+"/"+service);
    }

    for (int i = 0; i < scripts.length; i++) {
      if (scripts[i].getName().indexOf(".xsl") != -1) {
        String script = scripts[i].getName().substring(0, scripts[i].getName().indexOf(".xsl"));
        compileStandAlone(all, script, input, output);
      }
    }
  }
}