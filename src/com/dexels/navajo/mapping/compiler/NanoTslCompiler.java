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
 * TODO
 * 
 * IMPLEMENT SUPPORT FOR ARBITRARY JAVA BEANS (NEXT TO MAPPABLE AND
 * ASYNCMAPPABLE OBJECTS.
 * 
 * SYMBOL TABLE BIJHOUDEN VAN ATTRIBUUT WAARDEN UIT MAPPABLE OBJECTEN, DAN DEZE
 * SYMBOL TABLE MEEGEVEN AAN EXPRESSION.EVALUATE(), ZODAT NIET VIA INTROSPECTIE
 * DE ATTRIBUUT WAARDEN HOEVEN TE WORDEN BEPAALD
 * 
 * $columnValue('AAP') -> Object o = contextMap.getColumnValue('AAP') ->
 * symbolTable.put("$columnValue('AAP')", o); laz
 * 
 *  
 */
import com.dexels.navajo.document.*;
//import com.dexels.navajo.document.jaxpimpl.xml.*;
import com.dexels.navajo.document.nanoimpl.*;
import com.dexels.navajo.mapping.*;
import com.dexels.navajo.mapping.compiler.meta.*;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.UserException;
import com.dexels.navajo.server.SystemException;

import org.apache.jasper.compiler.*;

import java.io.*;
//import org.w3c.dom.*;
import java.util.*;
import java.util.Stack;
import java.util.StringTokenizer;

import com.dexels.navajo.document.types.ClockTime;
import com.dexels.navajo.parser.Expression;
import com.dexels.navajo.parser.TMLExpressionException;
import java.net.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.net.NetworkInterface;
import com.dexels.navajo.loader.*;

public class NanoTslCompiler {

    private final ClassLoader loader;

    private int messageListCounter = 0;

    private int asyncMapCounter = 0;

    private int lengthCounter = 0;

    private int functionCounter = 0;

    private int objectCounter = 0;

    private int subObjectCounter = 0;

    private int startIndexCounter = 0;

    private int startElementCounter = 0;

    private int offsetElementCounter = 0;

    private int methodCounter = 0;

    private final ArrayList methodClipboard = new ArrayList();

    private final ArrayList variableClipboard = new ArrayList();

    private final Stack contextClassStack = new Stack();

    private Class contextClass = null;

    private static String VERSION = "$Id$";

    public final static String XML_ESCAPE_DELIMITERS = "&'<>\"\n";

    private final ArrayList metaDataListeners = new ArrayList();
    
    private String currentScript = null;

    private JavaCompiler compiler;

    public NanoTslCompiler(ClassLoader loader) {

        messageListCounter = 0;
        if (loader == null) {
            this.loader = this.getClass().getClassLoader();
        } else {
            this.loader = loader;
            
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
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }

    private String removeNewLinesAndSingleQuotes(String str) {
        StringBuffer result = new StringBuffer(str.length());
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c == '\'') {
                result.append("\"");
            } else if (c == '\n') {
                result.append("\\n");
            } else {
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
            } else {
                if (c == '\r') {
                    // ignore
                } else {
                    result.append(c);
                }
            }
        }
        return result.toString();
    }

    /**
     * Purpose: rewrite an expression like $columnValue(1) OR
     * $columnValue('AAP') to (objectName.getColumnValue(1)+"") (IF columnValue
     * returns Object). OR to ((Integer)
     * objectName.getColumnValue(1)).intValue() (IF COLUMN VALUE RETURNS AN
     * INTEGER). OR to ...
     * 
     * @param objectName
     * @param call
     * @param c
     * @param attr
     * @param result,
     *            return the rewritten statement in the StringBuffer.
     * @return the datatype: java.lang.String, java.lang.Integer,
     *         java.lang.Float, java.util.Date, int, boolean, float, etc.
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

    // It takes its first child, right?! Don't know the point of this method
    private XMLElement getNextElement(XMLElement n) {
        //      System.err.println("getNextElement node is experimental and should
        // not be trusted!");
        return n.getFirstChild();
        //    NodeList children = n.getChildNodes();
        //    for (int i = 0; i < children.getLength(); i++) {
        //      if (children.item(i)instanceof Element) {
        //        return (Element) children.item(i);
        //      }
        //    }
        //    return null;
    }

    private int countNodes(Vector l, String name) {
        int count = 0;
        for (int i = 0; i < l.size(); i++) {
            if (((XMLElement) l.get(i)).getName().equals(name)) {
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
     * VERY NASTY METHOD. IT TRIES ALL KINDS OF TRICKS TO TRY TO AVOID CALLING
     * THE EXPRESSION.EVALUATE() METHOD IN THE GENERATED JAVA.
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

        // Try if clause contains only a (Navajo) function and a mappable
        // attribute call.
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
                        } else if (c == ')') {
                            endOfParams--;
                        } else {
                            params.append(c);
                        }
                        i++;
                    }
                }

                String expr = "";
                if (functionName.equals("")) {
                    expr = (params.toString().length() > 0 ? "$" + name + "(" + params + ")" : "$" + name);
                } else {
                    expr = functionName + "(" + (params.toString().length() > 0 ? "$" + name + "(" + params + ")" : "$" + name) + ")";

                }
                if (removeWhiteSpaces(expr).equals(removeWhiteSpaces(clause))) {
                    // Let's evaluate this directly.
                    exact = true;
                    Class contextClass = null;

                    try {
                        StringBuffer objectizedParams = new StringBuffer();
                        StringTokenizer allParams = new StringTokenizer(params.toString(), ",");
                        while (allParams.hasMoreElements()) {
                            String param = allParams.nextToken();
                            // Try to evaluate expression (NOTE THAT IF
                            // REFERENCES ARE MADE TO EITHER NAVAJO OR MAPPABLE
                            // OBJECTS THIS WILL FAIL
                            // SINCE THESE OBJECTS ARE NOT KNOWN AT COMPILE
                            // TIME!!!!!!!!!!!!!!1
                            Operand op = Expression.evaluate(param, null);
                            Object v = op.value;
                            if (v instanceof String) {
                                objectizedParams.append("\"" + v + "\"");
                            } else if (v instanceof Integer) {
                                objectizedParams.append("new Integer(" + v + ")");
                            } else if (v instanceof Float) {
                                objectizedParams.append("new Float(" + v + ")");
                            } else if (v instanceof Boolean) {
                                objectizedParams.append("new Boolean(" + v + ")");
                            } else if (v instanceof Double) {
                                objectizedParams.append("new Double(" + v + ")");
                            } else
                                throw new UserException(-1, "Unknown type encountered during compile time: " + v.getClass().getName() + " @clause: "
                                        + clause);
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
                        Class fnc = null;
                        if (!functionName.equals("")) {
                            try {
                                fnc = Class.forName("com.dexels.navajo.functions." + functionName, false, loader);
                            } catch (Exception e) {
                                throw new Exception("Could not find Navajo function: " + functionName);
                            }

                        }
                        call = objectName + ".get" + (name.charAt(0) + "").toUpperCase() + name.substring(1) + "(" + objectizedParams.toString()
                                + ")";

                        if (attrType.equals("int")) {
                            call = "new Integer(" + call + ")";
                        } else if (attrType.equals("float") || attrType.equals("double")) {
                            call = "new Double(" + call + ")";
                        } else if (attrType.equals("boolean")) {
                            call = "new Boolean(" + call + ")";
                        }
                    } catch (ClassNotFoundException cnfe) {
                        if (contextClass == null) {
                            throw new UserException(-1, "Error in script: Could not find adapter: " + className + " @clause: " + clause);
                        } else {
                            throw new UserException(-1, "Error in script: Could not locate function: " + functionName + " @ clause: " + clause);
                        }
                    } catch (Exception e) {
                        exact = false;
                    }
                }
            }
        }

        // Try to evaluate clause directly (compile time).
        if ((!exact) && !clause.equals("TODAY") && !clause.equals("null") && (clause.indexOf("[") == -1) && (clause.indexOf("$") == -1)
                && (clause.indexOf("(") == -1) && (clause.indexOf("+") == -1)) {
            try {
                ////System.out.println("CLAUSE = " + clause);
                Operand op = Expression.evaluate(clause, null);
                ////System.out.println("op = " + op);
                Object v = op.value;
                ////System.out.println("op.value = " + v);
                exact = true;
                if (v instanceof String) {
                    call = "\"" + v + "\"";
                } else if (v instanceof Integer) {
                    call = "new Integer(" + v + ")";
                } else if (v instanceof Float) {
                    call = "new Float(" + v + ")";
                } else if (v instanceof Boolean) {
                    call = "new Boolean(" + v + ")";
                } else if (v instanceof Double) {
                    call = "new Double(" + v + ")";
                } else
                    throw new UserException(-1, "Unknown type encountered during compile time: " + v.getClass().getName() + " @clause: " + clause);

            } catch (NullPointerException ne) {
                exact = false;
            } catch (TMLExpressionException pe) {
                exact = false;
                //System.err.println("TMLExpressionException, COULD NOT
                // OPTIMIZE EXPRESSION: " + clause);
            } catch (com.dexels.navajo.server.SystemException se) {
                exact = false;
                throw new UserException(-1, "Could not compile script, Invalid expression: " + clause);
            } catch (Throwable e) {
                exact = false;
                //System.err.println("Throwable, COULD NOT OPTIMIZE EXPRESSION:
                // " + clause);
            }
        }

        if (!exact && clause.equals("null")) {
            call = "null";
            exact = true;
        }

        // Use Expression.evaluate() if expression could not be executed in an
        // optimized way.
        if (!exact) {
            result.append(printIdent(ident) + "op = Expression.evaluate(\"" + replaceQuotes(clause)
                    + "\", inMessage, currentMap, currentInMsg, currentParamMsg, currentSelection, null);\n");
            result.append(printIdent(ident) + "sValue = op.value;\n");
        } else { // USE OUR OPTIMIZATION SCHEME.
            ////System.out.println("CALL = " + call);
            result.append(printIdent(ident) + "sValue = " + call + ";\n");
            if (!functionName.equals("")) { // Construct Navajo function
                                            // instance if needed.
                String functionVar = "function" + (functionCounter++);
                result.append(printIdent(ident) + "com.dexels.navajo.functions." + functionName + " " + functionVar
                        + " = (com.dexels.navajo.functions." + functionName + ") getFunction(" + "\"com.dexels.navajo.functions." + functionName
                        + "\");\n");
                result.append(printIdent(ident) + functionVar + ".reset();\n");
                result.append(printIdent(ident) + functionVar + ".insertOperand(sValue);\n");
                result.append(printIdent(ident) + "sValue = " + functionVar + ".evaluate();\n");
            }
        }

        return result.toString();
    }

    public String expressionNode(int ident, XMLElement exprElmnt, int leftOver, String className, String objectName) throws Exception {
        StringBuffer result = new StringBuffer();
        boolean isStringOperand = false;

        String condition = exprElmnt.getNonNullStringAttribute("condition");
        String value = XMLUnescape(exprElmnt.getNonNullStringAttribute("value"));

        // Check if operand is given as text node between <expression> tags.
        if (value == null || value.equals("")) {
            //      XMLElement child = exprElmnt.getFirstChild();
            value = exprElmnt.getContent();
            if (value != null) {
                isStringOperand = true;
                //        value = child.getContent();
            } else {
                throw new TslCompileException(TslCompileException.TSL_MISSING_VALUE, "Error @" + (exprElmnt.getParent() + "/" + exprElmnt)
                        + ": <expression> node should either contain a value attribute or a text child node: >" + value + "<", exprElmnt
                        .getStartOffset(), exprElmnt.getOffset());
            }
        }

        //    System.err.println("PARSING: "+exprElmnt.toString());
        //    System.err.println("isStringO: "+isStringOperand+" value: "+value);
        //    System.err.println(">> "+removeNewLines(value)+" <<");
        if (!condition.equals("")) {
            result.append(printIdent(ident) + "if (Condition.evaluate(\"" + replaceQuotes(condition)
                    + "\", inMessage, currentMap, currentInMsg, currentParamMsg))");
        }

        result.append(printIdent(ident) + "{\n");
        ident += 2;

        if (!isStringOperand) {
            result.append(optimizeExpresssion(ident, removeNewLines(value), className, objectName));
        } else {

            // TODO:::::::::::::: SHOULDN'T THERE BE AN OPTIMIZE EXPRESSION HERE
            // AS WELL?

            result.append(printIdent(ident) + "sValue = \"" + removeNewLines(value) + "\";\n");
            //result.append(printIdent(ident) + "sValue = \"" + value +
            // "\";\n");
        }

        result.append(printIdent(ident) + "matchingConditions = true;\n");

        ident -= 2;
        result.append(printIdent(ident) + "}\n");

        if (leftOver > 0) {
            result.append(printIdent(ident) + " else \n");

        }
        return result.toString();
    }

    public String methodsNode(int ident, XMLElement n) throws TslCompileException {

        StringBuffer result = new StringBuffer();

        // Process children.
        Vector children = n.getChildren();
        for (int i = 0; i < children.size(); i++) {
            if (((XMLElement) children.get(i)).getName().equals("method")) {
                XMLElement e = (XMLElement) children.get(i);
                String name = e.getNonNullStringAttribute("name");
                if ("".equals(name)) {
                    throw new TslCompileException(TslCompileException.TSL_MISSING_VALUE,"Method nodes should have a name attribute!",e);
                }
                String condition = e.getNonNullStringAttribute("condition");
                String description = e.getNonNullStringAttribute("description");
                condition = (condition == null) ? "" : condition;
                description = (description == null) ? "" : description;
                if (!condition.equals("")) {
                    result.append(printIdent(ident) + "if (Condition.evaluate(\"" + replaceQuotes(condition)
                            + "\", inMessage, null, null, null)) {\n");
                } else {
                    result.append(printIdent(ident) + "if (true) {\n");
                    // Get required messages.
                }
                result.append(printIdent(ident + 2) + "com.dexels.navajo.document.Method m = NavajoFactory.getInstance().createMethod(outDoc, \""
                        + name + "\", \"\");\n");
                result.append(printIdent(ident + 2) + "m.setDescription(\"" + description + "\");\n");
                Vector required = e.getChildren();
                Vector requireNames = new Vector();
                for (int j = 0; j < required.size(); j++) {
                    if (((XMLElement) required.get(j)).getName().equals("required")) {
                        String reqMsg = ((XMLElement) required.get(j)).getNonNullStringAttribute("message");
                        result.append(printIdent(ident + 2) + "m.addRequired(\"" + reqMsg + "\");\n");
                    } else {
                        throw new TslCompileException(TslCompileException.TSL_INAPPROPRIATE_NODE,"Unknown type of node under methods",(XMLElement) required.get(j));
                    }
                }
                String[] req = new String[requireNames.size()];
                for (int j = 0; j < req.length; j++) {
                    req[j] = (String)requireNames.get(j);
                }
                addScriptCalls(currentScript, name,req);

                result.append(printIdent(ident + 2) + "outDoc.addMethod(m);\n");
                result.append(printIdent(ident) + "}\n");
            }
        }

        return result.toString();
    }

    public String messageNode(int ident, XMLElement n, String className, String objectName) throws Exception {
        StringBuffer result = new StringBuffer();

        String messageName = n.getNonNullStringAttribute("name");
        String condition = n.getNonNullStringAttribute("condition");
        String type = n.getNonNullStringAttribute("type");
        String mode = n.getNonNullStringAttribute("mode");
        String count = n.getNonNullStringAttribute("count");
        String start_index = n.getNonNullStringAttribute("start_index");

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
            result.append(printIdent(ident) + "if (Condition.evaluate(\"" + replaceQuotes(condition)
                    + "\", inMessage, currentMap, currentInMsg, currentParamMsg)) { \n");
            ident += 2;
        }

        XMLElement nextElt = getNextElement(n);
        String ref = "";
        String filter = "";
        String startElement = "";
        String elementOffset = "";

        boolean isArrayAttr = false;
        boolean isSubMapped = false;
        boolean isParam = false;
        //Class contextClass = null;

        // Check if <message> is mapped to an object attribute:
        if (nextElt != null && nextElt.getName().equals("map") && nextElt.getNonNullStringAttribute("ref") != null
                && !nextElt.getNonNullStringAttribute("ref").equals("")) {
            ref = nextElt.getNonNullStringAttribute("ref");
            filter = nextElt.getNonNullStringAttribute("filter");
            startElement = nextElt.getNonNullStringAttribute("start_element");
            elementOffset = nextElt.getNonNullStringAttribute("element_offset");
            startElement = ((startElement == null || startElement.equals("")) ? "" : startElement);
            elementOffset = ((elementOffset == null || elementOffset.equals("")) ? "" : elementOffset);
            //System.out.println("in MessageNode(), REF = " + ref);
            ////System.out.println("filter = " + filter);
            //System.out.println("in MessageNode(), current contextClass = " +
            // contextClass);
            contextClassStack.push(contextClass);
            contextClass = null;
            try {
                contextClass = Class.forName(className, false, loader);
            } catch (Exception e) {
                throw new TslCompileException(TslCompileException.TSL_UNKNOWN_MAP, "Could not find adapter: " + className, nextElt);
            }
            //System.out.println("in MessageNode(), new contextClass = " +
            // contextClass);
            String attrType = MappingUtils.getFieldType(contextClass, ref);
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
            result.append(printIdent(ident)
                    + "fullMsgName = \"/\" + ( (currentOutMsg != null ? (currentOutMsg.getFullMessageName() + \"/\") : \"\") + \"" + messageName
                    + "\");\n");
            result.append(printIdent(ident) + "if (lm.isLazy(fullMsgName)) {\n");
            result.append(printIdent(ident + 2) + "la = (LazyArray) currentMap.myObject;\n");
            result.append(printIdent(ident + 2) + "la.setEndIndex(\"" + ref + "\", lm.getEndIndex(fullMsgName));\n");
            result.append(printIdent(ident + 2) + "la.setStartIndex(\"" + ref + "\",lm.getStartIndex(fullMsgName));\n");
            result.append(printIdent(ident + 2) + "la.setTotalElements(\"" + ref + "\",lm.getTotalElements(fullMsgName));\n");
            result.append(printIdent(ident) + "}\n");
        }

        // Create the message(s). Multiple messages are created if count > 1.
        result.append(printIdent(ident)
                + "count = "
                + (count.equals("1") ? "1" : "((Integer) Expression.evaluate(\"" + count
                        + "\", inMessage, currentMap, currentInMsg, currentParamMsg).value).intValue()") + ";\n");
        String messageList = "messageList" + (messageListCounter++);
        result.append(printIdent(ident) + "Message [] " + messageList + " = null;\n");

        if (n.getName().equals("message")) {
            result.append(printIdent(ident) + messageList + " = MappingUtils.addMessage(outDoc, currentOutMsg, \"" + messageName
                    + "\", \"\", count, \"" + type + "\", \"" + mode + "\");\n");
        } else { // must be parammessage.

            result.append(printIdent(ident) + messageList + " = MappingUtils.addMessage(inMessage, currentParamMsg, \"" + messageName
                    + "\", \"\", count, \"" + type + "\", \"" + mode + "\");\n");
        }

        result.append(printIdent(ident) + "for (int messageCount" + (ident) + " = 0; messageCount" + (ident) + " < " + messageList
                + ".length; messageCount" + (ident) + "++) {\n if (!kill) {\n");

        if (n.getName().equals("message")) {
            result.append(printIdent(ident + 2) + "outMsgStack.push(currentOutMsg);\n");
            result.append(printIdent(ident + 2) + "currentOutMsg = " + messageList + "[messageCount" + (ident) + "];\n");
        } else { // must be parammessage.
            result.append(printIdent(ident + 2) + "paramMsgStack.push(currentParamMsg);\n");
            result.append(printIdent(ident + 2) + "currentParamMsg = " + messageList + "[messageCount" + (ident) + "];\n");
        }

        if (isLazy && isArrayAttr) {
            result.append(printIdent(ident + 2) + "if (lm != null && lm.isLazy(fullMsgName)) {\n");
            result.append(printIdent(ident + 4) + "currentOutMsg.setLazyTotal(la.getTotalElements(\"" + ref + "\"));\n");
            result.append(printIdent(ident + 4) + "currentOutMsg.setLazyRemaining(la.getRemainingElements(\"" + ref + "\"));\n");
            result.append(printIdent(ident + 4) + "currentOutMsg.setArraySize(la.getCurrentElements(\"" + ref + "\"));\n");
            result.append(printIdent(ident + 4) + "lm = null; fullMsgName = \"\";\n");
            result.append(printIdent(ident + 2) + "}\n");
        }

        result.append(printIdent(ident + 2) + "access.setCurrentOutMessage(currentOutMsg);\n");

        if (isSubMapped && isArrayAttr) {
            type = Message.MSG_TYPE_ARRAY_ELEMENT;
            
            String lengthName = "length" + (lengthCounter++);
            
            /**
             * Changes 24/10
             */
            String mappableArrayName = "mappableObject" + (objectCounter++);
            result.append(printIdent(ident + 2) + mappableArrayName +
          		  " = ((" + className + ") currentMap.myObject).get" +
                    ( (ref.charAt(0) + "").toUpperCase() + ref.substring(1)) + "();\n");

            String mappableArrayDefinition = "Mappable [] " + mappableArrayName + " = null;\n";
            variableClipboard.add(mappableArrayDefinition);
       
            result.append(printIdent(ident + 2) + "int " + lengthName + " = " + 
          		  "(" + mappableArrayName + " == null ? 0 : " + mappableArrayName + ".length);\n");
            /**
             * End changes 24/10
             */
            
            String startIndexVar = "startIndex" + (startIndexCounter++);

            result.append(printIdent(ident + 2) + "int " + startIndexVar + " = " + startIndex + ";\n");
            String startElementVar = "startWith" + (startElementCounter);
            String offsetElementVar = "offset" + (startElementCounter++);

            // Use a different than 0 as start for for loop.
            // result.append(printIdent(ident) + "count = " +

            result.append(printIdent(ident + 2)
                    + "int "
                    + startElementVar
                    + " = "
                    + (startElement.equals("") ? "0" : "((Integer) Expression.evaluate(\"" + startElement
                            + "\", inMessage, currentMap, currentInMsg, currentParamMsg).value).intValue()") + ";\n");
            result.append(printIdent(ident + 2)
                    + "int "
                    + offsetElementVar
                    + " = "
                    + (elementOffset.equals("") ? "1" : "((Integer) Expression.evaluate(\"" + elementOffset
                            + "\", inMessage, currentMap, currentInMsg, currentParamMsg).value).intValue()") + ";\n");

            result.append(printIdent(ident + 2) + "for (int i" + (ident + 2) + " = " + startElementVar + "; i" + (ident + 2) + " < " + lengthName
                    + "; i" + (ident + 2) + " = i" + (ident + 2) + "+" + offsetElementVar + ") {\n if (!kill) {\n");

            result.append(printIdent(ident + 4) + "treeNodeStack.push(currentMap);\n");
            /**
             * Changes 24/10
             */
            result.append(printIdent(ident + 4) +
                    "currentMap = new MappableTreeNode(currentMap, " + mappableArrayName + "[i" + (ident + 2) + "]);\n");
            /**
             * End changes 24/10
             */

            // If filter is specified, evaluate filter first:
            if (!filter.equals("")) {
                result.append(printIdent(ident + 4) + "if (Condition.evaluate(\"" + replaceQuotes(filter)
                        + "\", inMessage, currentMap, currentInMsg, currentParamMsg)) {\n");
                ident += 2;
            }

            if (n.getName().equals("message")) {
                result.append(printIdent(ident + 4) + "outMsgStack.push(currentOutMsg);\n");
                result.append(printIdent(ident + 4) + "currentOutMsg = MappingUtils.getMessageObject(\"" + messageName
                        + "\", currentOutMsg, true, outDoc, false, \"\", " + ((startIndex == -1) ? "-1" : startIndexVar + "++") + ");\n");
                result.append(printIdent(ident + 4) + "access.setCurrentOutMessage(currentOutMsg);\n");
            } else { // parammessage.
                result.append(printIdent(ident + 4) + "paramMsgStack.push(currentParamMsg);\n");
                result.append(printIdent(ident + 4) + "currentParamMsg = MappingUtils.getMessageObject(\"" + messageName
                        + "\", currentParamMsg, true, inMessage, false, \"\", " + ((startIndex == -1) ? "-1" : startIndexVar + "++") + ");\n");
            }

            contextClassStack.push(contextClass);
            String subClassName = MappingUtils.getFieldType(contextClass, ref);
            Vector children = nextElt.getChildren();
            contextClass = null;
            try {
                contextClass = Class.forName(subClassName, false, loader);
            } catch (Exception e) {
                throw new TslCompileException(TslCompileException.TSL_UNKNOWN_MAP, "Could not find adapter: " + subClassName, nextElt);
            }

            String subObjectName = "mappableObject" + (objectCounter++);
            result.append(printIdent(ident + 4) + subObjectName + " = (" + subClassName + ") currentMap.myObject;\n");

            String objectDefinition = subClassName + " " + subObjectName + " = null;\n";
            variableClipboard.add(objectDefinition);

            for (int i = 0; i < children.size(); i++) {
                if (children.get(i) instanceof XMLElement) {
                    result.append(compile(ident + 4, ((XMLElement) children.get(i)), subClassName, subObjectName));
                }
            }

            contextClass = (Class) contextClassStack.pop();

            if (n.getName().equals("message")) {
                result.append(printIdent(ident + 2) + "currentOutMsg = (Message) outMsgStack.pop();\n");
                result.append(printIdent(ident + 2) + "access.setCurrentOutMessage(currentOutMsg);\n");
            } else {
                result.append(printIdent(ident) + "currentParamMsg = (Message) paramMsgStack.pop();\n");
            }

            if (!filter.equals("")) {
                ident -= 2;
                result.append(printIdent(ident + 4) + "}\n");
            }

            result.append(printIdent(ident + 2) + "currentMap.setEndtime();\ncurrentMap = (MappableTreeNode) treeNodeStack.pop();\n");
            result.append(printIdent(ident + 2) + "}\n} // EOF Array map result from contextMap \n");
        } else if (isSubMapped) { // Not an array

            result.append(printIdent(ident + 2) + "treeNodeStack.push(currentMap);\n");
            result.append(printIdent(ident + 2) + "currentMap = new MappableTreeNode(currentMap, ((" + className + ") currentMap.myObject).get"
                    + ((ref.charAt(0) + "").toUpperCase() + ref.substring(1)) + "());\n");
            result.append(printIdent(ident + 2) + "if (currentMap.myObject != null) {\n");
            //result.append(printIdent(ident + 4) +
            //              "outMsgStack.push(currentOutMsg);\n");
            //result.append(printIdent(ident + 4) +
            //              "currentOutMsg = MappingUtils.getMessageObject(\"" +
            //              messageName +
            //              "\", currentOutMsg, true, outDoc, false, \"\", -1);\n");
            //result.append(printIdent(ident + 4) +
            //              "access.setCurrentOutMessage(currentOutMsg);\n");

            contextClassStack.push(contextClass);
            String subClassName = MappingUtils.getFieldType(contextClass, ref);
            contextClass = null;
            try {
                contextClass = Class.forName(subClassName, false, loader);
            } catch (Exception e) {
                throw new TslCompileException(TslCompileException.TSL_UNKNOWN_MAP, "Could not find adapter " + subClassName, nextElt);
            }

            String subObjectName = "mappableObject" + (objectCounter++);
            result.append(printIdent(ident + 4) + subObjectName + " = (" + subClassName + ") currentMap.myObject;\n");

            String objectDefinition = subClassName + " " + subObjectName + " = null;\n";
            variableClipboard.add(objectDefinition);

            Vector children = nextElt.getChildren();
            for (int i = 0; i < children.size(); i++) {
                result.append(compile(ident + 4, ((XMLElement) children.get(i)), subClassName, subObjectName));
            }

            contextClass = (Class) contextClassStack.pop();

            //result.append(printIdent(ident + 4) +
            //              "currentOutMsg = (Message) outMsgStack.pop();\n");
            //result.append(printIdent(ident + 4) +
            //              "access.setCurrentOutMessage(currentOutMsg);\n");
            result.append(printIdent(ident + 2) + "}\n");
            result.append(printIdent(ident + 2) + "currentMap.setEndtime();\ncurrentMap = (MappableTreeNode) treeNodeStack.pop();\n");
        } else { // Just some new tags under the "message" tag.
            Vector children = n.getChildren();
            for (int i = 0; i < children.size(); i++) {
                result.append(compile(ident + 2, ((XMLElement) children.get(i)), className, objectName));
            }
        }

        if (n.getName().equals("message")) {
            result.append(printIdent(ident) + "currentOutMsg = (Message) outMsgStack.pop();\n");
            result.append(printIdent(ident) + "access.setCurrentOutMessage(currentOutMsg);\n");
        } else {
            result.append(printIdent(ident) + "currentParamMsg = (Message) paramMsgStack.pop();\n");
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

    public String propertyNode(int ident, XMLElement n, boolean canBeSubMapped, String className, String objectName) throws Exception {
        StringBuffer result = new StringBuffer();

        String propertyName = n.getNonNullStringAttribute("name");
        String direction = n.getNonNullStringAttribute("direction");
        String type = n.getNonNullStringAttribute("type");
        String subtype = n.getNonNullStringAttribute("subtype");
        String lengthStr = n.getNonNullStringAttribute("length");
        int length = ((lengthStr != null && !lengthStr.equals("")) ? Integer.parseInt(lengthStr) : -1);
        String value = n.getNonNullStringAttribute("value");
        String description = n.getNonNullStringAttribute("description");
        String cardinality = n.getNonNullStringAttribute("cardinality");
        String condition = n.getNonNullStringAttribute("condition");

        value = (value == null) || (value.equals("")) ? "" : value;
        type = (type == null) ? "" : type;
        subtype = (subtype == null) ? "" : subtype;
        description = (description == null) ? "" : description;
        cardinality = (cardinality == null || cardinality.equals("")) ? "1" : cardinality;
        condition = (condition == null) ? "" : condition;

        boolean conditionClause = false;
        if (!condition.equals("")) {
            conditionClause = true;
            result.append(printIdent(ident) + "if (Condition.evaluate(\"" + replaceQuotes(condition)
                    + "\", inMessage, currentMap, currentInMsg, currentParamMsg)) { \n");
            ident += 2;
        }

        Vector children = n.getChildren();

        boolean hasChildren = false;
        boolean isSelection = false;
        boolean isMapped = false;
        XMLElement mapNode = null;

        StringBuffer optionItems = new StringBuffer();

        int exprCount = countNodes(children, "expression");

        result.append(printIdent(ident) + "matchingConditions = false;\n");
        Class contextClass = null;
        for (int i = 0; i < children.size(); i++) {
            hasChildren = true;
            // Has condition;
            if (((XMLElement) children.get(i)).getName().equals("expression")) {
                result.append(expressionNode(ident, (XMLElement) children.get(i), --exprCount, className, objectName));
            } else if (((XMLElement) children.get(i)).getName().equals("option")) {
                isSelection = true;
                String optionCondition = ((XMLElement) children.get(i)).getNonNullStringAttribute("condition");
                String optionName = ((XMLElement) children.get(i)).getNonNullStringAttribute("name");
                String optionValue = ((XMLElement) children.get(i)).getNonNullStringAttribute("value");
                String selectedValue = ((XMLElement) children.get(i)).getNonNullStringAttribute("selected");
                boolean selected = (selectedValue.equals("1"));
                type = "selection";
                // Created condition statement if condition is given!
                String conditional = (optionCondition != null && !optionCondition.equals("")) ? "if (Condition.evaluate(\""
                        + replaceQuotes(optionCondition) + "\", inMessage, currentMap, currentInMsg, currentParamMsg))\n" : "";
                optionItems.append(conditional + "p.addSelection(NavajoFactory.getInstance().createSelection(outDoc, \"" + optionName + "\", \""
                        + optionValue + "\", " + selected + "));\n");
            } else if (((XMLElement) children.get(i)).getName().equals("map")) { // ABout
                                                                                 // to
                                                                                 // map
                                                                                 // a
                                                                                 // "selection"
                                                                                 // property!!!
                if (!canBeSubMapped) {
                    throw new TslCompileException(TslCompileException.SUB_MAP_ERROR, "This property can not be submapped: " + propertyName, n);
                }
                if (!type.equals("selection")) {
                    throw new TslCompileException(TslCompileException.SUB_MAP_ERROR, "Only selection properties can be submapped: " + propertyName, n);
                }
                mapNode = (XMLElement) children.get(i);
                isMapped = true;
                isSelection = true;

            } else if (children.get(i) instanceof XMLElement) {
                String tagValue = "<" + n.getName() + " name=\"" + propertyName + "\">";
                throw new TslCompileException(TslCompileException.SUB_MAP_ERROR, "Illegal child tag <" + ((XMLElement) children.get(i)).getName()
                        + "> in " + tagValue + " (Check your script) ", n);
            }
        }

        if (!hasChildren || isSelection) {
            result.append(printIdent(ident) + "sValue = new String(\"" + value + "\");\n");
            result.append(printIdent(ident) + "type = \"" + type + "\";\n");
        } else {
            result.append(printIdent(ident) + "type = (sValue != null) ? MappingUtils.determineNavajoType(sValue) : \"" + type + "\";\n");
        }

        result.append(printIdent(ident) + "subtype = \"" + subtype + "\";\n");

        if (n.getName().equals("property")) {
            result.append(printIdent(ident) + "p = MappingUtils.setProperty(false, currentOutMsg, \"" + propertyName
                    + "\", sValue, type, subtype, \"" + direction + "\", \"" + description + "\", " + length
                    + ", outDoc, inMessage, !matchingConditions);\n");
        } else { // parameter
            result.append(printIdent(ident) + "p = MappingUtils.setProperty(true, currentParamMsg, \"" + propertyName
                    + "\", sValue, type, subtype, \"" + direction + "\", \"" + description + "\", " + length
                    + ", outDoc, inMessage, !matchingConditions);\n");
        }

        if (isMapped) {
            contextClass = null;
            try {
                contextClass = Class.forName(className, false, loader);
            } catch (Exception e) {
                throw new TslCompileException(TslCompileException.TSL_UNKNOWN_MAP, "Could not find adapter: " + className, n);
            }
            String ref = mapNode.getNonNullStringAttribute("ref");
            
            /**
             * Changes 24/10
             */
            String mappableArrayName = "mappableObject" + (objectCounter++);
            result.append(printIdent(ident + 2) + mappableArrayName +
          		  " = " + objectName + ".get" + ( (ref.charAt(0) + "").toUpperCase() + ref.substring(1)) + "();\n");

            String mappableArrayDefinition = "Mappable [] " + mappableArrayName + " = null;\n";
            variableClipboard.add(mappableArrayDefinition);
            /**
             * End changes 24/10
             */
            
           
            /**
             * Changes 24/10
             */
            result.append(printIdent(ident + 2) + "for (int i" + (ident + 2) +
                    " = 0; i" + (ident + 2) + " < " + mappableArrayName + ".length; i" + 
                    (ident + 2) + "++) {\n if (!kill) {\n");
            /**
             * End changes 24/10
             */
            
            result.append(printIdent(ident + 4) + "treeNodeStack.push(currentMap);\n");
            
            /**
             * Changes 24/10
             */
            result.append(printIdent(ident + 4) +
                    "currentMap = new MappableTreeNode(currentMap, " +
                    mappableArrayName + "[i" + (ident + 2) + "]);\n");
            /**
             * End changes 24/10
             */
            
            result.append(printIdent(ident + 4) + "String optionName = \"\";\n");
            result.append(printIdent(ident + 4) + "String optionValue = \"\";\n");
            result.append(printIdent(ident + 4) + "boolean optionSelected = false;\n");
            children = mapNode.getChildren();
            String subClassName = MappingUtils.getFieldType(contextClass, ref);
            String subClassObjectName = "mappableObject" + (objectCounter++);
            result.append(printIdent(ident + 4) + subClassObjectName + " = (" + subClassName + ") currentMap.myObject;\n");

            String objectDefinition = subClassName + " " + subClassObjectName + " = null;\n";
            variableClipboard.add(objectDefinition);

            for (int i = 0; i < children.size(); i++) {
                if (((XMLElement) children.get(i)).getName().equals("property")) {
                    XMLElement elt = (XMLElement) children.get(i);
                    String subPropertyName = elt.getNonNullStringAttribute("name");
                    if (!(subPropertyName.equals("name") || subPropertyName.equals("value") || subPropertyName.equals("selected"))) {
                        throw new TslCompileException(TslCompileException.SUB_MAP_ERROR,
                                "Only 'name' or 'value' named properties expected when submapping a 'selection' property", elt);
                    }
                    Vector expressions = elt.getChildren();
                    int leftOver = countNodes(expressions, "expression");
                    ////System.out.println("LEFTOVER = " + leftOver + ", CHILD
                    // NODES = " + expressions.getLength());

                    for (int j = 0; j < expressions.size(); j++) {
                        ////System.out.println("expression.item("+j+") = " +
                        // expressions.item(j));
                        if ((expressions.get(j) instanceof XMLElement) && ((XMLElement) expressions.get(j)).getName().equals("expression")) {
                            result.append(expressionNode(ident + 4, (XMLElement) expressions.get(j), --leftOver, subClassName, subClassObjectName));
                        }
                    }
                    if (subPropertyName.equals("name")) {
                        result.append(printIdent(ident + 4) + "optionName = (sValue != null) ? sValue + \"\" : \"\";\n");
                    } else if (subPropertyName.equals("value")) {
                        result.append(printIdent(ident + 4) + "optionValue = (sValue != null) ? sValue + \"\" : \"\";\n");
                    } else {
                        result.append(printIdent(ident + 4) + "optionSelected = (sValue != null) ? ((Boolean) sValue).booleanValue() : false;\n");
                    }
                } else if (((XMLElement) children.get(i)).getName().equals("debug")) {
                    result.append(debugNode(ident, (XMLElement) children.get(i)));
                } else if (children.get(i) instanceof XMLElement) {
                    throw new TslCompileException(TslCompileException.SUB_MAP_ERROR,
                            "<property> tag expected while sub-mapping a selection property: " + ((XMLElement) children.get(i)).getName(),
                            (XMLElement) children.get(i));
                }
            }
            result.append(printIdent(ident + 4)
                    + "p.addSelection(NavajoFactory.getInstance().createSelection(outDoc, optionName, optionValue, optionSelected));\n");
            result.append(printIdent(ident + 4) + "currentMap.setEndtime();\ncurrentMap = (MappableTreeNode) treeNodeStack.pop();\n");
            result.append(printIdent(ident + 2) + "}\n} // EOF Array map result to property\n");
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

    public String fieldNode(int ident, XMLElement n, String className, String objectName) throws Exception {

        StringBuffer result = new StringBuffer();

        String attribute = n.getNonNullStringAttribute("name");
        String condition = n.getNonNullStringAttribute("condition");

        if (attribute == null || attribute.equals(""))
            throw new TslCompileException(TslCompileException.TSL_MISSING_FIELD_NAME, "Name attribute is required for field tags", n);

        condition = (condition == null) ? "" : condition;

        String methodName = "set" + (attribute.charAt(0) + "").toUpperCase() + attribute.substring(1, attribute.length());
        Vector children = n.getChildren();

        if (!condition.equals("")) {
            result.append(printIdent(ident) + "if (Condition.evaluate(\"" + replaceQuotes(condition)
                    + "\", inMessage, currentMap, currentInMsg, currentParamMsg)) { \n");
        } else {
            result.append(printIdent(ident) + "if (true) {\n");
        }
        // Expression nodes.
        boolean isMapped = false;
        XMLElement mapNode = null;

        int exprCount = countNodes(children, "expression");
        for (int i = 0; i < children.size(); i++) {
            // Has condition;
            if (((XMLElement) children.get(i)).getName().equals("expression")) {
                result.append(expressionNode(ident + 2, (XMLElement) children.get(i), --exprCount, className, objectName));
            } else if (((XMLElement) children.get(i)).getName().equals("map")) {
                isMapped = true;
                mapNode = (XMLElement) children.get(i);
            }
        }

        if (!isMapped) {
            String castedValue = "";
            //      try {
            Class contextClass = null;
            try {
                contextClass = Class.forName(className, false, loader);
            } catch (Exception e) {
                throw new TslCompileException(TslCompileException.TSL_UNKNOWN_MAP, "Could not find adapter: " + className, n.getParent()
                        .getStartOffset(), n.getOffset());
            }
            String type = null;
            try {
                type = MappingUtils.getFieldType(contextClass, attribute);
            } catch (Exception e) {
                throw new TslCompileException(TslCompileException.TSL_UNKNOWN_FIELD, "Could not find field: " + attribute + " in adapter "
                        + className, n.getStartOffset(), n.getOffset());
            }
            if (type.equals("java.lang.String")) {
                castedValue = "(String) sValue";
            } else if (type.equals("com.dexels.navajo.document.types.ClockTime")) {
                castedValue = "(com.dexels.navajo.document.types.ClockTime) sValue";
            } else if (type.equals("int")) {
                castedValue = "((Integer) sValue).intValue()";
            } else if (type.equals("double")) {
                castedValue = "((Double) sValue).doubleValue()";
            } else if (type.equals("java.util.Date")) {
                castedValue = "((java.util.Date) sValue)";
            } else if (type.equals("boolean")) {
                castedValue = "((Boolean) sValue).booleanValue()";
            } else if (type.equals("float")) {
                castedValue = "((Double) sValue).doubleValue()";
            } else if (type.equals("com.dexels.navajo.document.types.Binary")) {
                castedValue = "((com.dexels.navajo.document.types.Binary) sValue)";
            } else if (type.equals("com.dexels.navajo.document.types.Money")) {
                castedValue = "((com.dexels.navajo.document.types.Money) sValue)";
            } else if (type.equals("com.dexels.navajo.document.types.Percentage")) {
                castedValue = "((com.dexels.navajo.document.types.Percentage) sValue)";
            } else {
                castedValue = "sValue";
            }
            //      }
            //      catch (ClassNotFoundException e) {
            //        e.printStackTrace();
            //        throw new UserException(-1, "Error in script: could not find
            // mappable object: " + className);
            //      }
            result.append(printIdent(ident + 2) + objectName + "." + methodName + "(" + castedValue + ");\n");
        } else { // Field with ref: indicates that a message or set of messages
                 // is mapped to attribute (either Array Mappable or singular
                 // Mappable)
            String ref = mapNode.getNonNullStringAttribute("ref");
            boolean isParam = false;
            if (ref.startsWith("/@")) {
                ref = ref.replaceAll("@", "__parms__/");
                isParam = true;
            }
            String filter = mapNode.getNonNullStringAttribute("filter");
            filter = (filter == null) ? "" : filter;
            result.append(printIdent(ident + 2) + "// Map message(s) to field\n");
            String messageListName = "messages" + ident;

            result.append(printIdent(ident + 2) + "ArrayList " + messageListName + " = null;\n");
            result.append(printIdent(ident + 2) + "inSelectionRef = MappingUtils.isSelection(currentInMsg, inMessage, \"" + ref + "\");\n");
            result.append(printIdent(ident + 2) + "if (!inSelectionRef)\n");
            result.append(printIdent(ident + 4) + messageListName + " = MappingUtils.getMessageList(currentInMsg, inMessage, \"" + ref + "\", \""
                    + "" + "\", currentMap, currentParamMsg);\n");
            result.append(printIdent(ident + 2) + "else\n");
            result.append(printIdent(ident + 4) + messageListName + " = MappingUtils.getSelectedItems(currentInMsg, inMessage, \"" + ref + "\");\n");

            Class contextClass = null;
            try {
                contextClass = Class.forName(className, false, loader);
            } catch (Exception e) {
                throw new TslCompileException(TslCompileException.TSL_UNKNOWN_MAP, "Could not find adapter: " + className, n);
            }
            String type = MappingUtils.getFieldType(contextClass, attribute);
            boolean isArray = MappingUtils.isArrayAttribute(contextClass, attribute);
            ////System.out.println("TYPE FOR " + attribute + " IS: " + type +
            // ", ARRAY = " + isArray);      
            ////System.out.println("TYPE FOR " + attribute + " IS: " + type + ", ARRAY = " + isArray);
            
            if (!isArray && !MappingUtils.isMappable(contextClass, attribute)) {
            	throw new TslCompileException(-1, "Not a mappable field: " + attribute, mapNode);
            }
            
            if (isArray) {
                String subObjectsName = "subObject" + subObjectCounter;
                String loopCounterName = "j" + subObjectCounter;
                subObjectCounter++;

                String objectDefinition = type + " [] " + subObjectsName + " = null;\n";
                variableClipboard.add(objectDefinition);
                variableClipboard.add("int " + loopCounterName + ";\n");

                result.append(printIdent(ident + 2) + subObjectsName + " = new " + type + "[" + messageListName + ".size()];\n");
                result.append(printIdent(ident + 2) + "for (" + loopCounterName + " = 0; " + loopCounterName + " < " + messageListName + ".size(); "
                        + loopCounterName + "++) {\n if (!kill){\n");
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
                    result.append(printIdent(ident + 4) + "if (inSelectionRef || Condition.evaluate(\"" + replaceQuotes(filter)
                            + "\", inMessage, currentMap, currentInMsg, currentParamMsg)) {\n");
                    ident += 2;
                }

                result.append(printIdent(ident) + "treeNodeStack.push(currentMap);\n");
                result.append(printIdent(ident) + "currentMap = new MappableTreeNode(currentMap, (Mappable) classLoader.getClass(\"" + type
                        + "\").newInstance());\n");

                result.append(printIdent(ident) + "((Mappable) currentMap.myObject).load(parms, inMessage, access, config);\n");
                result.append(printIdent(ident) + subObjectsName + "[" + loopCounterName + "] = (" + type + ") currentMap.myObject;\n");
                result.append(printIdent(ident) + "try {\n");
                ident = ident + 2;

                children = mapNode.getChildren();
                for (int i = 0; i < children.size(); i++) {
                    result.append(compile(ident + 2, (XMLElement) children.get(i), type, subObjectsName + "[" + loopCounterName + "]"));
                }

                ident = ident - 2;
                result.append(printIdent(ident) + "} catch (Exception e" + ident + ") {\n");
                result.append(printIdent(ident + 2) + subObjectsName + "[" + loopCounterName + "].kill();\n");
                result.append(printIdent(ident + 2) + "throw e" + ident + ";\n");

                result.append(printIdent(ident) + "}\n");

                result.append(printIdent(ident) + subObjectsName + "[" + loopCounterName + "].store();\n");

                result.append(printIdent(ident) + "currentMap.setEndtime();\ncurrentMap = (MappableTreeNode) treeNodeStack.pop();\n");

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
                result.append(printIdent(ident + 2) + "}\n} // FOR loop for " + loopCounterName + "\n");
                result.append(printIdent(ident + 2) + objectName + "." + methodName + "(" + subObjectsName + ");\n");
            } else { // Not an array type field, but single Mappable object.
              	
              	// Push current mappable object on stack.
                result.append(printIdent(ident) + "treeNodeStack.push(currentMap);\n");     
                
                // Create instance of object.
                result.append(printIdent(ident) + 
                		"currentMap = new MappableTreeNode(currentMap, (Mappable) classLoader.getClass(\"" +  type + "\").newInstance());\n");     
                
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
                		"((Mappable) currentMap.myObject).load(parms, inMessage, access, config);\n");     
                // Assign local variable reference.
                result.append(printIdent(ident) + type + " " + 
                		subObjectsName + " = (" + type +                
        				") currentMap.myObject;\n");                
                result.append(printIdent(ident) + "try {\n");        
                ident = ident+2;      
                
                // Recursively dive into children.
                children = mapNode.getChildren();
                for (int i = 0; i < children.size(); i++) {
                  result.append(compile(ident + 2,(XMLElement) children.get(i), type, subObjectsName ));
                }  
                
                ident = ident-2;        
                result.append(printIdent(ident) + "} catch (Exception e" + 
                		ident +                    ") {\n");        
                result.append(printIdent(ident + 2) + subObjectsName + ".kill();\n");        
                result.append(printIdent(ident + 2) + "throw e" + ident + ";\n");         
                result.append(printIdent(ident) + "}\n");                
                result.append(printIdent(ident) + subObjectsName + ".store();\n");         
                
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

    public String breakNode(int ident, XMLElement n) throws Exception {

        StringBuffer result = new StringBuffer();
        String condition = n.getNonNullStringAttribute("condition");
        if (condition.equals("")) {
            result.append(printIdent(ident) + "if (true) {");
        } else {
            result.append(printIdent(ident) + "if (Condition.evaluate(\"" + replaceQuotes(condition)
                    + "\", inMessage, currentMap, currentInMsg, currentParamMsg)) { \n");

        }
        result.append(printIdent(ident + 2) + "throw new BreakEvent();\n");
        result.append(printIdent(ident) + "}\n");

        return result.toString();
    }

    public String debugNode(int ident, XMLElement n) throws Exception {
        StringBuffer result = new StringBuffer();
        String value = n.getNonNullStringAttribute("value");
        result.append(printIdent(ident) + "op = Expression.evaluate(\"" + replaceQuotes(value)
                + "\", inMessage, currentMap, currentInMsg, currentParamMsg, currentSelection, null);\n");
        result.append(printIdent(ident) + "System.out.println(\"in PROCESSING SCRIPT: \" + access.rpcName + \" DEBUG INFO: \" + op.value);\n");
        return result.toString();
    }

    public String requestNode(int ident, XMLElement n) throws Exception {
        StringBuffer result = new StringBuffer();
        return result.toString();
    }

    public String responseNode(int ident, XMLElement n) throws Exception {
        StringBuffer result = new StringBuffer();
        return result.toString();
    }

    public String runningNode(int ident, XMLElement n) throws Exception {
        StringBuffer result = new StringBuffer();
        return result.toString();
    }

    public String mapNode(int ident, XMLElement n) throws Exception {

        StringBuffer result = new StringBuffer();

        String object = n.getNonNullStringAttribute("object");
        String condition = n.getNonNullStringAttribute("condition");
        // If name, is specified it could be an AsyncMap.
        String name = n.getNonNullStringAttribute("name");
        boolean asyncMap = false;
        condition = (condition == null) ? "" : condition;

        boolean conditionClause = false;
        if (!condition.equals("")) {
            conditionClause = true;
            result.append(printIdent(ident) + "if (Condition.evaluate(\"" + replaceQuotes(condition)
                    + "\", inMessage, currentMap, currentInMsg, currentParamMsg)) { \n");
            ident += 2;
        }

        String className = object;
        String reff = n.getNonNullStringAttribute("ref");
//        System.err.println("Ref found: "+n.toString());
        if (!"".equals(className)) {
            addScriptUsesAdapter(currentScript, className);
        } else {
            System.err.println("whee!");
            addScriptUsesAdapter(currentScript, "Referenced adapter"+n.getNonNullStringAttribute("ref"));
        }

        if (!name.equals("")) { // We have a potential async mappable object.
            ////System.out.println("POTENTIAL MAPPABLE OBJECT " + className);
            Class contextClass = null;
            try {
                contextClass = Class.forName(className, false, loader);
            } catch (Exception e) {
                throw new TslCompileException(TslCompileException.TSL_UNKNOWN_MAP, "Could not find adapter: " + className, n);
            }
            if (contextClass.getSuperclass().getName().equals("com.dexels.navajo.mapping.AsyncMappable")) {
                asyncMap = true;

            } else {
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

            result
                    .append(printIdent(ident)
                            + "if (!config.isAsyncEnabled()) throw new UserException(-1, \"Set enable_async = true in server.xml to use asynchronous objects\");");
            result.append(printIdent(ident) + asyncMapName + " = true;\n");
            result.append(printIdent(ident) + headerName + " = inMessage.getHeader();\n");
            result.append(printIdent(ident) + callbackRefName + " = " + headerName + ".getCallBackPointer(\"" + name + "\");\n");
            result.append(printIdent(ident) + aoName + " = null;\n");
            result.append(printIdent(ident) + asyncMapFinishedName + " = false;\n");
            result.append(printIdent(ident) + resumeAsyncName + " = false;\n");
            result.append(printIdent(ident) + asyncStatusName + " = \"request\";\n\n");
            result.append(printIdent(ident) + "if (" + callbackRefName + " != null) {\n");
            ident += 2;
            result.append(printIdent(ident) + aoName + " = (" + className + ") config.getAsyncStore().getInstance(" + callbackRefName + ");\n");
            result.append(printIdent(ident) + interruptTypeName + " = " + headerName + ".getCallBackInterupt(\"" + name + "\");\n");

            result
                    .append(printIdent(ident)
                            + " if ("
                            + aoName
                            + " == null) {\n "
                            + "  throw new UserException( -1, \"Asynchronous object reference instantiation error: no sych instance (perhaps cleaned up?)\");\n}\n");
            result.append(printIdent(ident) + "if (" + interruptTypeName + ".equals(\"kill\")) { // Kill thread upon client request.\n" + "   "
                    + aoName + ".stop();\n" + "   config.getAsyncStore().removeInstance(" + callbackRefName + ");\n" + "   return;\n" + "} else if ("
                    + interruptTypeName + ".equals(\"interrupt\")) {\n" + "   " + aoName + ".interrupt();\n " + "   return;\n" + "} else if ("
                    + interruptTypeName + ".equals(\"resume\")) { " + "  " + aoName + ".resume();\n" + "return;\n" + "}\n");
            ident -= 2;
            result.append(printIdent(ident) + "} else { // New instance!\n");

            result.append(printIdent(ident) + aoName + " = (" + className + ") classLoader.getClass(\"" + object + "\").newInstance();\n"
                    + "  // Call load method for async map in advance:\n" + "  " + aoName + ".load(parms, inMessage, access, config);\n" + "  "
                    + callbackRefName + " = config.getAsyncStore().addInstance( " + aoName + ", access );\n" + "}\n");

            result.append(printIdent(ident) + "treeNodeStack.push(currentMap);\n");
            result.append(printIdent(ident) + "currentMap = new MappableTreeNode(currentMap, " + aoName + ");\n");

            result.append(printIdent(ident) + "currentMap.name = \"" + name + "\";\n");
            result.append(printIdent(ident) + "currentMap.ref = " + callbackRefName + ";\n");
            result.append(printIdent(ident) + aoName + ".afterReload(\"" + name + "\", " + callbackRefName + ");\n");

            result.append(printIdent(ident) + "try {\n");
            ident += 2;

            result.append(printIdent(ident) + asyncMapFinishedName + " = " + aoName + ".isFinished(outDoc, access);\n");
            Vector response = n.getElementsByTagName("response");
            boolean hasResponseNode = false;

            if (response.size() > 0) {
                hasResponseNode = true;
            }
            Vector running = n.getElementsByTagName("running");
            boolean hasRunningNode = false;

            if (running.size() > 0) {
                hasRunningNode = true;
            }
            Vector request = n.getElementsByTagName("request");

            boolean whileRunning = ((XMLElement) response.get(0)).getNonNullStringAttribute("while_running").equals("true");
            result.append(printIdent(ident) + "if (" + asyncMapFinishedName + " || (" + aoName + ".isActivated() && " + hasResponseNode + " && "
                    + whileRunning + ")) {\n");
            result.append(printIdent(ident) + "  " + asyncStatusName + " = \"response\";\n");
            result.append(printIdent(ident) + "  " + aoName + ".beforeResponse(parms, inMessage, access, config);\n");
            result.append(printIdent(ident) + "  if (" + aoName + ".isActivated() && " + whileRunning + ") {\n");
            //result.append(printIdent(ident) + " "+aoName+".interrupt();\n");
            result.append(printIdent(ident) + "     " + resumeAsyncName + " = true;\n");

            result.append(printIdent(ident) + "  }\n");
            result.append(printIdent(ident) + "} else if (!" + aoName + ".isActivated()) {\n");
            result.append(printIdent(ident) + "  " + asyncStatusName + " = \"request\";\n");
            result.append(printIdent(ident) + "} else if (" + hasRunningNode + ") {\n");
            result.append(printIdent(ident) + "  " + asyncStatusName + " = \"running\";\n");
            //result.append(printIdent(ident) + " " + aoName +
            // ".interrupt();\n");
            result.append(printIdent(ident) + "  " + resumeAsyncName + " = true;\n");
            result.append(printIdent(ident) + "}\n");

            Vector children = null;
            result.append(printIdent(ident) + "if (" + asyncStatusName + ".equals(\"response\")) {\n");
            children = ((XMLElement) response.get(0)).getChildren();
            for (int i = 0; i < children.size(); i++) {
                if (children.get(i) instanceof XMLElement) {
                    result.append(compile(ident + 2, (XMLElement) children.get(i), className, aoName));
                }
            }
            result.append(printIdent(ident) + "} else if (" + asyncStatusName + ".equals(\"request\")) {\n");
            children = ((XMLElement) request.get(0)).getChildren();
            for (int i = 0; i < children.size(); i++) {
                if (children.get(i) instanceof XMLElement) {
                    result.append(compile(ident + 2, ((XMLElement) children.get(i)), className, aoName));
                }
            }
            result.append(printIdent(ident) + "} else if (" + asyncStatusName + ".equals(\"running\")) {\n");
            children = ((XMLElement) running.get(0)).getChildren();
            for (int i = 0; i < children.size(); i++) {
                if (children.get(i) instanceof XMLElement) {
                    result.append(compile(ident + 2, (XMLElement) children.get(i), className, aoName));
                }
            }
            result.append(printIdent(ident) + "}\n");

            result.append(printIdent(ident) + "if ((currentMap.myObject != null)) {\n");
            result.append(printIdent(ident + 2) + "if (!" + asyncMapFinishedName + ") {\n");
            result.append(printIdent(ident + 4) + "if (" + resumeAsyncName + ") { " + aoName + ".afterResponse(); } else { " + aoName
                    + ".afterRequest(); " + aoName + ".runThread(); }\n");
            result.append(printIdent(ident + 2) + "} else {\n");
            result.append(printIdent(ident + 4) + "((Mappable) currentMap.myObject).store();\n");
            result.append(printIdent(ident + 4) + "config.getAsyncStore().removeInstance(currentMap.ref);\n");
            result.append(printIdent(ident + 2) + "}\n");
            result.append(printIdent(ident) + "}\n");

            result.append(printIdent(ident) + "} catch (Exception e" + ident + ") {\n");
            result.append(printIdent(ident) + "  ((Mappable) currentMap.myObject).kill();\n");
            result.append(printIdent(ident) + " config.getAsyncStore().removeInstance(currentMap.ref);\n");

            result.append(printIdent(ident) + "  throw e" + ident + ";\n");
            result.append(printIdent(ident) + "}\n");

            //result.append(printIdent(ident) +
            //              "currentMap.setEndtime();\ncurrentMap = (MappableTreeNode)
            // treeNodeStack.pop();\n");

        } else {

            result.append(printIdent(ident) + "treeNodeStack.push(currentMap);\n");
            result.append(printIdent(ident) + "currentMap = new MappableTreeNode(currentMap, (Mappable) classLoader.getClass(\"" + object
                    + "\").newInstance());\n");
            String objectName = "mappableObject" + (objectCounter++);
            result.append(printIdent(ident) + objectName + " = (" + className + ") currentMap.myObject;\n");
            result.append(printIdent(ident) + objectName + ".load(parms, inMessage, access, config);\n");

            String objectDefinition = className + " " + objectName + " = null;\n";
            variableClipboard.add(objectDefinition);

            result.append(printIdent(ident) + "try {\n");

            Vector children = n.getChildren();
            for (int i = 0; i < children.size(); i++) {
                result.append(compile(ident + 2, (XMLElement) children.get(i), className, objectName));
            }

            result.append(printIdent(ident) + "} catch (Exception e" + ident + ") {\n");
            result.append(printIdent(ident) + objectName + ".kill();\n");
            result.append(printIdent(ident) + "  throw e" + ident + ";\n");
            result.append(printIdent(ident) + "}\n");
            result.append(printIdent(ident) + objectName + ".store();\n");
            result.append(printIdent(ident) + "currentMap.setEndtime();\ncurrentMap = (MappableTreeNode) treeNodeStack.pop();\n");

        }

        if (conditionClause) {
            ident -= 2;
            result.append(printIdent(ident) + "} // EOF map condition \n");
        }

        return result.toString();
    }

    /**
     * Resolve include nodes in the script: <include script="[name of script to
     * be included]"/>
     * 
     * @param scriptPath
     * @param n
     * @param parent
     * @throws Exception
     */
    private final void includeNode(String scriptPath, XMLElement n, XMLElement parent) throws Exception {

        String script = ((XMLElement) n).getNonNullStringAttribute("script");
        if (script == null || script.equals("")) {
            throw new TslCompileException(TslCompileException.VALIDATION_NO_SCRIPT_INCLUDE,
                    "No script name found in include tag (missing or empty script attribute)", n);
        }
        addScriptIncludes(currentScript, script);
//        System.err.println("INCLUDING SCRIPT " + script + " @ NODE " + n);
        XMLElement nextNode = n.getNextSibling();
        while (nextNode != null && !(nextNode instanceof XMLElement)) {
            nextNode = nextNode.getNextSibling();
        }
        if (nextNode == null | !(nextNode instanceof XMLElement)) {
            nextNode = n;
        }

        //    Document includeDoc = XMLDocumentUtils.createDocument(new
        // FileInputStream(scriptPath + "/" + script + ".xml"), false);
        FileInputStream fii = null;

        XMLElement includeDoc;
        try {
            fii = new FileInputStream(scriptPath + "/" + script + ".xml");
            includeDoc = parseXMLElement(fii);
        } finally {
            if (fii != null) {
                fii.close();
            }
        }

        Vector content = includeDoc.getChildren();

        //System.err.println("nextNode = " + nextNode + ", n = " + n);

        XMLElement parentNode = nextNode.getParent();

        //    System.err.println("Compiling includes. Dont really trust the
        // mechanism.");
        for (int i = 0; i < content.size(); i++) {
            XMLElement child = (XMLElement) content.get(i);
            //      Node imported = parent.importNode(child.cloneNode(true), true);
            parentNode.insertBefore(child, nextNode);
        }

        parentNode.removeChild(n);

//        System.err.println("After include");
        //String result = XMLDocumentUtils.toString(parent);
        //System.err.println("result:");
        //System.err.println(result);
//        System.err.println("AFTER INCLUDE: " + parentNode.toString());
    }

    public String compile(int ident, XMLElement n, String className, String objectName) throws Exception {
        StringBuffer result = new StringBuffer();
        //    System.err.println("in compile(), className = " + className + ",
        // objectName = " + objectName);
        //    if (n==null) {
        //        System.err.println("WARNING: Received null xmlElement");
        //    } else {
        //        System.err.println("Received xmlelement with name: "+n.getName());
        //    }
        if (n.getName().equals("map")) {
            result.append(printIdent(ident) + "{ // Starting new mappable object context. \n");
            result.append(mapNode(ident + 2, (XMLElement) n));
            result.append(printIdent(ident) + "} // EOF MapContext \n");
        } else if (n.getName().equals("field")) {
            result.append(fieldNode(ident, (XMLElement) n, className, objectName));
        } else if ((n.getName().equals("param") && !((XMLElement) n).getNonNullStringAttribute("type").equals("array"))
                || n.getName().equals("property")) {
            result.append(propertyNode(ident, (XMLElement) n, true, className, objectName));
        }

        else if (n.getName().equals("message") || (n.getName().equals("param") && ((XMLElement) n).getNonNullStringAttribute("type").equals("array"))) {
            String methodName = "execute_sub" + (methodCounter++);
            result.append(printIdent(ident) + "if (!kill) { " + methodName + "(parms, inMessage, access, config); }\n");

            StringBuffer methodBuffer = new StringBuffer();

            methodBuffer.append(printIdent(ident) + "private final void " + methodName
                    + "(Parameters parms, Navajo inMessage, Access access, NavajoConfig config) throws Exception {\n\n");
            ident += 2;
            methodBuffer.append(printIdent(ident) + "if (!kill) {\n");
            methodBuffer.append(messageNode(ident, (XMLElement) n, className, objectName));
            methodBuffer.append(printIdent(ident) + "}\n");
            ident -= 2;
            methodBuffer.append("}\n");

            methodClipboard.add(methodBuffer);
            //
        } else if (n.getName().equals("methods")) {
            result.append(methodsNode(ident, (XMLElement) n));
        } else if (n.getName().equals("debug")) {
            result.append(debugNode(ident, (XMLElement) n));
        } else if (n.getName().equals("break")) {
            result.append(breakNode(ident, (XMLElement) n));
        }

        return result.toString();
    }

    private final void generateFinalBlock(XMLElement d, StringBuffer generatedCode) throws Exception {
        generatedCode
                .append("public final void finalBlock(Parameters parms, Navajo inMessage, Access access, NavajoConfig config) throws Exception {\n");

        Vector list = d.getElementsByTagName("finally");

        if (list != null && list.size() > 0) {
            Vector children = ((XMLElement) list.get(0)).getChildren();
            for (int i = 0; i < children.size(); i++) {
                String str = compile(0, (XMLElement) children.get(i), "", "");
                generatedCode.append(str);
            }
        }

        generatedCode.append("}\n");

    }

    /**
     * Check condition/validation rules inside the script.
     * 
     * @param f
     * @return
     * @throws Exception
     */
    private final void generateValidations(XMLElement d, StringBuffer generatedCode) throws Exception {

        boolean hasValidations = false;

        StringBuffer conditionString = new StringBuffer("conditionArray = new String[]{\n");
        StringBuffer ruleString = new StringBuffer("ruleArray = new String[]{\n");
        StringBuffer codeString = new StringBuffer("codeArray = new String[]{\n");

        Vector list = d.getElementsByTagName("validations");
        boolean valid = true;
        ArrayList conditions = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            Vector rules = ((XMLElement) list.get(i)).getChildren();
            for (int j = 0; j < rules.size(); j++) {
                XMLElement rule = (XMLElement) rules.get(j);
                if (rule.isCalled("check")) {
                    String code = rule.getNonNullStringAttribute("code");
                    String value = rule.getNonNullStringAttribute("value");
                    String condition = rule.getNonNullStringAttribute("condition");
                    if (value.equals("")) {
                        value = removeNewLines(rule.getContent());
                    }
                    if (rule.equals("")) {
                        throw new TslCompileException(TslCompileException.VALIDATION_NO_CODE,
                                "Validation syntax error: code attribute missing or empty", rule);
                    }
                    if (value.equals("")) {
                        throw new TslCompileException(TslCompileException.VALIDATION_NO_VALUE,
                                "Validation syntax error: value attribute missing or empty", rule);
                    }
                    // Check if condition evaluates to true, for evaluating
                    // validation ;)
                    hasValidations = true;
                    conditionString.append("\"" + condition.replace('\n', ' ').trim() + "\"");
                    ruleString.append("\"" + value.replace('\n', ' ').trim() + "\"");
                    codeString.append("\"" + code.replace('\n', ' ').trim() + "\"");
                    //         System.err.println(" Appended nr: "+j+" size:
                    // "+rules.size());
                    if (j < rules.size() - 1) { // Add ","
                    //       if (j != ( rules.size() - 2 ) ) { // Add ","
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
    

    public int compileScript(String script, String scriptPath, String workingPath, String packagePath) throws SystemException, TslCompileException {

        boolean debugInput = false;
        boolean debugOutput = false;
        StringBuffer result = new StringBuffer();

        XMLElement tslDoc = null;
        FileWriter fo = null;
        
        reuse();
        if (packagePath==null || "".equals(packagePath)) {
            currentScript = script;
        } else {
            currentScript = packagePath +"/"+script;
        }
        
        //
        // Added:
        //
        removeScriptMetadata(currentScript);
        try {
            File dir = new File(workingPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File javaFile = new File(dir, packagePath + "/" + script + ".java");
            javaFile.getParentFile().mkdirs();
            //System.err.println("Will create file: "+javaFile.toString());

            fo = new FileWriter(javaFile);
            //      tslDoc = XMLDocumentUtils.createDocument(new
            // FileInputStream(scriptPath + "/" + packagePath + "/" + script +
            // ".xml"), false);
            long cc = System.currentTimeMillis();
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(scriptPath + "/" + packagePath + "/" + script + ".xml");
                tslDoc = parseXMLElement(fis);
            } finally {
                if (fis != null) {
                    fis.close();

                }
            }
            //       System.err.println("Parsing: "+script+" took:
            // "+(System.currentTimeMillis()-cc)+" millis.");
            //      cc = System.currentTimeMillis();
            //      tslDoc.parseFromReader(new InputStreamReader(new
            // FileInputStream(scriptPath + "/" + packagePath + "/" + script +
            // ".xml")));
            //           XMLDocumentUtils.createDocument(new FileInputStream(scriptPath +
            // "/" + packagePath + "/" + script + ".xml"), false);

            //      NodeList tsl = tslDoc.getElementsByTagName("tsl");
            //      if (tsl == null || tsl.getLength() != 1 || !(tsl.item(0)
            // instanceof Element)) {
            //        throw new SystemException(-1, "Invalid or non existing script
            // file: " + scriptPath + "/" + packagePath + "/" + script +
            // ".xml");
            //      }
            if (tslDoc == null || tslDoc.getName() == null || !tslDoc.getName().equals("tsl")) {
                throw new SystemException(-1, "Invalid or non existing script file: " + scriptPath + "/" + packagePath + "/" + script + ".xml");
            }
            //      Element tslElt = (Element) tsl.item(0);
            String debugLevel = tslDoc.getNonNullStringAttribute("debug");
            debugInput = (debugLevel.indexOf("request") != -1);
            debugOutput = (debugLevel.indexOf("response") != -1);

            String importDef = (packagePath.equals("") ? "" : "package " + MappingUtils.createPackageName(packagePath) + ";\n\n")
                    + "import com.dexels.navajo.server.*;\n" + "import com.dexels.navajo.mapping.*;\n" + "import com.dexels.navajo.document.*;\n"
                    + "import com.dexels.navajo.parser.*;\n" + "import java.util.ArrayList;\n" + "import java.util.HashMap;\n"
                    + "import java.util.Stack;\n\n\n";
            result.append(importDef);

            result.append("/**\n");
            result.append(" * Generated Java code by TSL compiler.\n");
            result.append(" * " + this.VERSION + "\n");
            result.append(" *\n");
            result.append(" * Created on: " + new java.util.Date() + "\n");
            result.append(" * Java version: " + System.getProperty("java.vm.name") + " (" + System.getProperty("java.runtime.version") + ")\n");
            result.append(" * OS: " + System.getProperty("os.name") + " " + System.getProperty("os.version") + "\n");
            result.append(" * Hostname: " + this.getHostName() + "\n");
            result.append(" *\n");
            result.append(" * WARNING NOTICE: DO NOT EDIT THIS FILE UNLESS YOU ARE COMPLETELY AWARE OF WHAT YOU ARE DOING\n");
            result.append(" *\n");
            result.append(" */\n\n");

            String classDef = "public final class " + script + " extends CompiledScript {\n\n\n";

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

            result.append("outDoc = access.getOutputDoc();\n");
            result.append("inDoc = inMessage;\n");

            // First resolve includes.
            Vector includes = tslDoc.getElementsByTagName("include");
            //System.err.println("FOUND " + includes.getLength() + "
            // INCLUDES");
            XMLElement[] includeArray = new XMLElement[includes.size()];
            for (int i = 0; i < includes.size(); i++) {
                includeArray[i] = (XMLElement) includes.get(i);
            }

            for (int i = 0; i < includeArray.length; i++) {
                //System.err.println("ABOUT TO RESOLVE INCLUDE: " +
                // includeArray[i]);
                includeNode(scriptPath, includeArray[i], tslDoc);
            }

            Vector children = tslDoc.getChildren();
            //System.err.println("FOUND " + children.getLength() + "
            // CHILDREN");
            for (int i = 0; i < children.size(); i++) {
                String str = compile(0, (XMLElement) children.get(i), "", "");
                result.append(str);
            }

            if (debugOutput) {
                result.append("System.err.println(\"\\n --------- BEGIN NAVAJO RESPONSE ---------\\n\");\n");
                result.append("outDoc.write(System.err);\n");
                result.append("System.err.println(\"\\n --------- END NAVAJO RESPONSE ---------\\n\");\n");
            }

            result.append("}// EOM\n");

            // Add generated methods.
            for (int i = 0; i < methodClipboard.size(); i++) {
                StringBuffer methodBuffer = (StringBuffer) methodClipboard.get(i);
                result.append(methodBuffer.toString());
                result.append("\n\n");
            }

            // Add generated variables.
            for (int i = 0; i < variableClipboard.size(); i++) {
                String objectDefinition = (String) variableClipboard.get(i);
                result.append(objectDefinition);
            }

            result.append("}//EOF");

            fo.write(result.toString());
            //      tslDoc.disposeAll();
                  System.err.println("Compiling: "+script+" took:"+(System.currentTimeMillis()-cc)+" millis. # of lines: "+tslDoc.getLineNr());
//                  long diff = System.currentTimeMillis()-cc;
//                  if (diff!=null) {
//                      return tslDoc.getLineNr() / diff;
//                } else {
//                    return Double.POSITIVE_INFINITY;
//                }
                  return tslDoc.getLineNr();
        } catch (Exception e) {
            e.printStackTrace();
            if (e instanceof TslCompileException) {
                throw (TslCompileException) e;
            }
            if (e instanceof XMLParseException) {
                XMLParseException xe = (XMLParseException) e;
                throw new TslCompileException(TslCompileException.TSL_PARSE_EXCEPTION, "XML Parse exception: " + xe.getMessage(), xe.getSource());
                //          throw (TslCompileException)e;
            }

            throw new SystemException(-1, "Error while generating Java code for script: " + script + ". Message: " + e.getMessage(), e);
        } finally {
            if (fo != null) {
                try {
                    // Make damn sure the outstream is closed
                    fo.close();
                    currentScript = null;

                } catch (IOException e1) {
                    e1.printStackTrace();
                }

            }

        }

        ////System.out.println(result.toString());
    }

    //  public void finalize() {
    //System.out.println("FINALIZE() METHOD CALL FOR NanoTslCompiler OBJECT " +
    // this);
    //  }

    private static void compileStandAlone(boolean all, String script, String input, String output, String packagePath) {
        compileStandAlone(all, script, input, output, packagePath, null);
    }

    public static String compileToJava(String script, String input, String output, String packagePath, NavajoClassLoader classLoader)
            throws Exception {
        File dir = new File(output);
        String javaFile = output + "/" + script + ".java";
        ArrayList javaList = new ArrayList();
        NanoTslCompiler tslCompiler = new NanoTslCompiler(classLoader);
        tslCompiler.compileTsl(script, input, output, packagePath, false);
        return javaFile;
    }

    public int compileTsl(String script, String input, String output, String packagePath,  boolean createMetaData)
            throws Exception {
        try {
            String bareScript;
            
            if (script.indexOf('/') >= 0) {
                bareScript = script.substring(script.lastIndexOf('/') + 1, script.length());
            } else {
                bareScript = script;
            }
            return compileScript(bareScript, input, output, packagePath);
        } catch (Throwable ex) {
            String javaFile = output + "/" + script + ".java";
            System.err.println("Error compiling script: " + script + " ex: " + ex.getMessage() + " cl: " + ex.getClass());
            System.err.println("delete javaFile: " + javaFile.toString());
            File f = new File(javaFile);
            if (f.exists()) {
                f.delete();
            }
            if (ex instanceof Exception) {
                throw (Exception) ex;
            }
            return -1;
        }

    }


    private static void compileStandAlone(boolean all, String script, String input, String output, String packagePath, String[] extraclasspath) {
        try {
            NanoTslCompiler tslCompiler = new NanoTslCompiler(null);
            try {
                String bareScript;

                if (script.indexOf('/') >= 0) {
                    bareScript = script.substring(script.lastIndexOf('/') + 1, script.length());
                } else {
                    bareScript = script;
                }

                //System.err.println("About to compile script: "+bareScript);
                //System.err.println("Using package path: "+packagePath);
                tslCompiler.compileScript(bareScript, input, output, packagePath);
                File dir = new File(output);

                ////System.out.println("CREATED JAVA FILE FOR SCRIPT: " +
                // script);
            } catch (Exception ex) {
                System.err.println("Error compiling script: " + script);
                ex.printStackTrace();
                return;
            }
            //      }

            StringBuffer classPath = new StringBuffer();
            classPath.append(System.getProperty("java.class.path"));

            if (extraclasspath != null) {
                for (int i = 0; i < extraclasspath.length; i++) {
                    classPath.append(System.getProperty("path.separator"));
                    classPath.append(extraclasspath[i]);
                }
            }

            ////System.out.println("in NavajoCompiler(): new classPath = " +
            // classPath);

            JavaCompiler compiler = new SunJavaCompiler();

            compiler.setClasspath(classPath.toString());
            compiler.setOutputDir(output);
            compiler.setClassDebugInfo(true);
            compiler.setEncoding("UTF8");
            compiler.setMsgOutput(System.out);
            compiler.compile(output + "/" + script + ".java");

            //System.out.println("COMPILED JAVA FILE INTO CLASS FILE");
        } catch (Exception e) {
            e.printStackTrace();
            //System.out.println("Could not compile script " + script + ",
            // reason: " +
            //                   e.getMessage());
            System.exit(1);
        }
    }
    
    public void initJavaCompiler( String outputPath, ArrayList classpath) {
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
       
 
    }
    
    public void compileTslToJava(String script, String input, String output, String packagePath)  throws Exception {
        long cc = System.currentTimeMillis();
        	System.err.println("compileTslToJava: "+script+" inp: "+input+" out: "+output+" packpath: "+packagePath);
 
        StringWriter sw = new StringWriter();
        compiler.setOutputWriter(sw);
        compiler.compile(output + "/" + script + ".java");
        System.err.println("Compile took: "+(System.currentTimeMillis()-cc)+" millis.");
        System.err.println("Output: "+sw.toString());
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

    public static ArrayList compileDirectoryToJava(File currentDir, File outputPath, String offsetPath, NavajoClassLoader classLoader) {
        System.err.println("Entering compiledirectory: " + currentDir + " output: " + outputPath + " offset: " + offsetPath);
        ArrayList files = new ArrayList();
        File[] scripts = null;
        File f = new File(currentDir, offsetPath);
        scripts = f.listFiles();
        if (scripts != null) {
            for (int i = 0; i < scripts.length; i++) {
                File current = scripts[i];
                if (current.isDirectory()) {
                    System.err.println("Entering directory: " + current.getName());
                    ArrayList subDir = compileDirectoryToJava(currentDir, outputPath, offsetPath.equals("") ? current.getName()
                            : (offsetPath + "/" + current.getName()), classLoader);
                    files.addAll(subDir);
                } else {
                    if (current.getName().endsWith(".xml")) {
                        String name = current.getName().substring(0, current.getName().indexOf("."));
                        //            System.err.println("Compiling: "+name+" dir: "+ new
                        // File(currentDir,offsetPath).toString()+" outdir:
                        // "+new File(outputPath,offsetPath));
                        System.err.println("Compiling: " + name);
                        File outp = new File(outputPath, offsetPath);
                        if (!outp.exists()) {
                            outp.mkdirs();
                        }
                        String compileName;
                        if (offsetPath.equals("")) {
                            compileName = name;
                        } else {
                            compileName = offsetPath + "/" + name;
                        }
                        String javaFile = null;
                        try {
                            javaFile = compileToJava(compileName, currentDir.toString(), outputPath.toString(), offsetPath, classLoader);
                            files.add(javaFile);
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return files;
    }

    public static void fastCompileDirectory(File currentDir, File outputPath, String offsetPath, String[] extraclasspath,
            NavajoClassLoader classLoader) {

        StringBuffer classPath = new StringBuffer();
        classPath.append(System.getProperty("java.class.path"));

        if (extraclasspath != null) {
            for (int i = 0; i < extraclasspath.length; i++) {
                classPath.append(System.getProperty("path.separator"));
                classPath.append(extraclasspath[i]);
            }
        }

        ArrayList javaFiles = compileDirectoryToJava(currentDir, outputPath, offsetPath, classLoader);
        System.err.println("javaFiles: " + javaFiles);
        JavaCompiler compiler = new SunJavaCompiler();
        //    StringBuffer javaBuffer = new StringBuffer();

        //    System.err.println("JavaBuffer: "+javaBuffer.toString());
        compiler.setClasspath(classPath.toString());
        compiler.setOutputDir(outputPath.toString());
        compiler.setClassDebugInfo(true);
        compiler.setEncoding("UTF8");
        compiler.setMsgOutput(System.out);
        StringWriter myWriter = new StringWriter();
        compiler.setOutputWriter(myWriter);
        System.err.println("\n\nCLASSPATH: " + classPath.toString());
        for (int i = 0; i < javaFiles.size(); i++) {
            compiler.compile((String) javaFiles.get(i));
            System.err.println("Compiled: " + javaFiles.get(i));
            //      javaBuffer.append((String)javaFiles.get(i));
            //      javaBuffer.append(" ");
            System.err.println("output: "+myWriter.toString());
        }

    }

    public static void compileDirectory(File currentDir, File outputPath, String offsetPath, String[] classpath) {
        System.err.println("Entering compiledirectory: " + currentDir + " output: " + outputPath + " offset: " + offsetPath);

        File[] scripts = null;
        File f = new File(currentDir, offsetPath);
        scripts = f.listFiles();
        if (scripts != null) {
            for (int i = 0; i < scripts.length; i++) {
                File current = scripts[i];
                if (current.isDirectory()) {
                    System.err.println("Entering directory: " + current.getName());
                    compileDirectory(currentDir, outputPath, offsetPath.equals("") ? current.getName() : (offsetPath + "/" + current.getName()),
                            classpath);
                } else {
                    if (current.getName().endsWith(".xml")) {
                        String name = current.getName().substring(0, current.getName().indexOf("."));
                        //            System.err.println("Compiling: "+name+" dir: "+ new
                        // File(currentDir,offsetPath).toString()+" outdir:
                        // "+new File(outputPath,offsetPath));
                        System.err.println("Compiling: " + name);
                        File outp = new File(outputPath, offsetPath);
                        if (!outp.exists()) {
                            outp.mkdirs();
                        }
                        String compileName;
                        if (offsetPath.equals("")) {
                            compileName = name;
                        } else {
                            compileName = offsetPath + "/" + name;
                        }
                        compileStandAlone(false, compileName, currentDir.toString(), outputPath.toString(), offsetPath, classpath);
                    }
                }
            }
        }

        //    for (int i = 0; i < scripts.length; i++) {
        //      if (scripts[i].getName().indexOf(".xsl") != -1) {
        //        String script = scripts[i].getName().substring(0,
        //            scripts[i].getName().indexOf(".xsl"));
        //        compileStandAlone(all, script, input, output);
        //      }
        //    }
        //
        //    scripts = new File[1];
        //    scripts[0] = new File(input + "/" + service);

    }

    private String getHostName() throws SocketException {
        ArrayList list = new ArrayList();
        Enumeration all = java.net.NetworkInterface.getNetworkInterfaces();

        while (all.hasMoreElements()) {
            java.net.NetworkInterface nic = (java.net.NetworkInterface) all.nextElement();
            Enumeration ipaddresses = nic.getInetAddresses();
            while (ipaddresses.hasMoreElements()) {
                InetAddress ip = (InetAddress) ipaddresses.nextElement();
                return ip.getCanonicalHostName();
                //         System.err.println("\t\tCanonical hostname: " +
                // ip.getCanonicalHostName());
                //         System.err.println("\t\tHost address: " +
                // ip.getHostAddress());
                //         System.err.println("\t\tisMCGlobal: " + ip.isMCGlobal());
                //         System.err.println("\t\tisLinkLocalAddress: " +
                // ip.isLinkLocalAddress());
                //         System.err.println("\t\t"+ip.toString());

            }
        }
        return "unkown host";

    }

    private XMLElement parseXMLElement(InputStream is) throws IOException {
        XMLElement xe = new CaseSensitiveXMLElement();
        xe.parseFromReader(new InputStreamReader(is));
        return xe;
    }

    public static void main(String[] args) throws Exception {

        System.err.println("today = " + new java.util.Date());
        java.util.Date d = (java.util.Date) null;

        if (args.length == 0) {
            System.out
                    .println("NanoTslCompiler: Usage: java com.dexels.navajo.mapping.compiler.NanoTslCompiler <scriptDir> <compiledDir> [-all | scriptName]");
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
        String service = args[2];

        if (all) {
            File scriptDir = new File(input);
            File outDir = new File(output);
            compileDirectory(scriptDir, outDir, "", null);
        }
    }

    /**
     * Replace all occurrences of the characters &, ', ", < and > by the escaped
     * characters &amp;, &quot;, &apos;, &lt; and &gt;
     */
    public static String XMLEscape(String s) {
        if ((s == null) || (s.length() == 0)) {
            return s;
        }

        StringTokenizer tokenizer = new StringTokenizer(s, XML_ESCAPE_DELIMITERS, true);
        StringBuffer result = new StringBuffer();

        while (tokenizer.hasMoreElements()) {
            String substring = tokenizer.nextToken();

            if (substring.length() == 1) {
                switch (substring.charAt(0)) {

                //case '&' :
                //    result.append("&amp;");
                //    break;

                //case '\'' :
                //    result.append("&apos;");
                //    break;

                case ';':
                    result.append("\\;");
                    break;

                case '<':
                    result.append("&lt;");
                    break;

                case '>':
                    result.append("&gt;");
                    break;

                case '\"':
                    result.append("&quot;");
                    break;

                case '\n':
                    result.append("\\n");
                    break;

                default:
                    result.append(substring);
                }
            } else {
                result.append(substring);
            }
        }

        return result.toString();
    }

    /**
     * Replace all occurrences of the escaped characters &amp;, &quot;, &apos;,
     * &lt; and &gt; by the unescaped characters &, ', ", < and >.
     */
    public static String XMLUnescape(String s) {
        if ((s == null) || (s.length() == 0)) {
            return s;
        }

        int offset;
        int next;
        String result;

        // filter out all escaped ampersands
        offset = 0;
        result = "";

        while ((next = s.indexOf("&amp;", offset)) >= 0) {
            result += s.substring(offset, next) + "&";
            offset = next + "&amp;".length();
        }

        result += s.substring(offset, s.length()); // characters after last &
        s = result;

        // filter out all escaped double quotes
        offset = 0;
        result = "";

        while ((next = s.indexOf("&quot;", offset)) >= 0) {
            result += s.substring(offset, next) + "\"";
            offset = next + "&quot;".length();
        }

        result += s.substring(offset, s.length()); // characters after last "
        s = result;

        // filter out all escaped single quotes
        offset = 0;
        result = "";

        while ((next = s.indexOf("&apos;", offset)) >= 0) {
            result += s.substring(offset, next) + "\'";
            offset = next + "&apos;".length();
        }

        result += s.substring(offset, s.length()); // characters after last "
        s = result;

        // filter out all escaped less than characters
        offset = 0;
        result = "";

        while ((next = s.indexOf("&lt;", offset)) >= 0) {
            result += s.substring(offset, next) + "<";
            offset = next + "&lt;".length();
        }

        result += s.substring(offset, s.length()); // characters after last <
        s = result;

        // filter out all escaped greater than characters
        offset = 0;
        result = "";

        while ((next = s.indexOf("&gt;", offset)) >= 0) {
            result += s.substring(offset, next) + ">";
            offset = next + "&gt;".length();
        }

        result += s.substring(offset, s.length()); // characters after last >
        s = result;

        // filter out all escaped newlines
        offset = 0;
        result = "";

        while ((next = s.indexOf("\\n", offset)) >= 0) {
            result += s.substring(offset, next) + "\n";
            offset = next + "\\n".length();
        }

        result += s.substring(offset, s.length()); // characters after last
                                                   // newline

        // filter out all escaped ;'s
        offset = 0;
        result = "";

        while ((next = s.indexOf("\\;", offset)) >= 0) {
            result += s.substring(offset, next) + ";";
            offset = next + "\\;".length();
        }

        result += s.substring(offset, s.length()); // characters after last
                                                   // newline

        return result;
    }
    
    public void addScriptCalls(String source, String destination, String[] requires) {
         for (Iterator iter = metaDataListeners.iterator(); iter.hasNext();) {
            MetaDataListener element = (MetaDataListener) iter.next();
            element.scriptCalls(source, destination,requires);
        }
    }
    public void addScriptIncludes(String source, String destination) {
        for (Iterator iter = metaDataListeners.iterator(); iter.hasNext();) {
            MetaDataListener element = (MetaDataListener) iter.next();
            element.scriptIncludes(source, destination);
        }
    }
    public void addScriptUsesAdapter(String source, String adaptername) {
         for (Iterator iter = metaDataListeners.iterator(); iter.hasNext();) {
            MetaDataListener element = (MetaDataListener) iter.next();
            element.scriptUsesAdapter(source, adaptername);
        }
    }
    public void addScriptUsesField(String source, String adaptername, String fieldName) {
         for (Iterator iter = metaDataListeners.iterator(); iter.hasNext();) {
            MetaDataListener element = (MetaDataListener) iter.next();
            element.scriptUsesField(source, adaptername, fieldName);
        }
    }
    public void removeScriptMetadata(String script) {
        for (Iterator iter = metaDataListeners.iterator(); iter.hasNext();) {
           MetaDataListener element = (MetaDataListener) iter.next();
           element.removeScriptMetadata(script);
       }
   }
    
    public void resetMetaData() {
        for (Iterator iter = metaDataListeners.iterator(); iter.hasNext();) {
            MetaDataListener element = (MetaDataListener) iter.next();
            element.resetMetaData();
        }
    }

    public void addMetaDataListener(MetaDataListener listener) {
//        System.err.println("Adding script meta data listener...");
        metaDataListeners.add(listener);
    }

    public void removeMetaDataListener(MetaDataListener listener) {
        metaDataListeners.remove(listener);
    }

    public void reuse() {
        messageListCounter = 0;
        asyncMapCounter = 0;
        lengthCounter = 0;
        functionCounter = 0;
        objectCounter = 0;
        subObjectCounter = 0;
        startIndexCounter = 0;
        startElementCounter = 0;
        offsetElementCounter = 0;
        methodCounter = 0;
        methodClipboard.clear();
        variableClipboard.clear();
        contextClassStack.clear();
        contextClass = null;
        currentScript = null;
    }
}
