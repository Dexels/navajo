
/**
 * Title:        Navajo<p>
 * Description:  <p>
 * Copyright:    Copyright (c) Arjen Schoneveld<p>
 * Company:      Dexels<p>
 * @author Arjen Schoneveld
 * @version $Id$
 */
package com.dexels.navajo.parser;

import java.util.*;
import javax.xml.transform.stream.StreamResult;

import com.dexels.navajo.document.*;
import com.dexels.navajo.util.*;
import com.dexels.navajo.mapping.*;
import com.dexels.navajo.server.*;
import com.dexels.navajo.xml.*;

public class Condition {

  public static boolean evaluate(String clause, Navajo inMessage, Object o, Message parent) throws TMLExpressionException, SystemException
  {
    try {
      if (clause.trim().equals(""))
        return true;
//      java.io.StringBufferInputStream input = new java.io.StringBufferInputStream(clause);
      java.io.StringReader input = new java.io.StringReader(clause);
      TMLParser parser = new TMLParser(input);
      parser.setNavajoDocument(inMessage);
      parser.setMappableObject(o);
      parser.setParentMsg(parent);
      parser.Expression();
      Object aap = parser.jjtree.rootNode().interpret();
      if (aap instanceof Boolean)
        return ((Boolean) aap).booleanValue();
      else
        throw new TMLExpressionException("Expected boolean return value got: " + aap.getClass().getName());
    } catch (TMLExpressionException tmle) {
      throw tmle;
    } catch (ParseException ce) {
      throw new SystemException(SystemException.PARSE_ERROR, "Condition syntax error: " + clause + "\n" +
                "After token " + ce.currentToken.toString() + "\n" + ce.getMessage());
    } catch (Throwable t) {
      throw new TMLExpressionException("Invalid condition: " + clause + ".\nCause: " + t.getMessage());
    }
  }

  public static boolean evaluate(String clause, Navajo inMessage) throws TMLExpressionException, SystemException
  {
      return evaluate(clause, inMessage, null, null);
  }

  public static void main(String args[]) {

    try {
      Navajo doc = new Navajo();

      Message msg = Message.create(doc, "depot0");
      doc.addMessage(msg);
      Property prop = Property.create(doc, "waarde", Property.INTEGER_PROPERTY, "0", 1, "bla", Property.DIR_IN);
      msg.addProperty(prop);
      msg = Message.create(doc, "depot1");
      doc.addMessage(msg);
      prop = Property.create(doc, "waarde", Property.INTEGER_PROPERTY, "95", 1, "bla", Property.DIR_IN);
      msg.addProperty(prop);

      XMLDocumentUtils.toXML(doc.getMessageBuffer(),null,null,new StreamResult(System.out));

      String condition = "CheckRange([/depot.*/waarde],0,10)";
      //String condition = "(arjen = arjen)";

      boolean result = evaluate(condition, doc);
      Util.debugLog("result: " + result);
    } catch (Exception e) {
      Util.debugLog(e.getMessage());
    }
  }

}