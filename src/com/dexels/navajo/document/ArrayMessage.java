package com.dexels.navajo.document;

import org.w3c.dom.*;
import gnu.regexp.*;
import java.util.*;
import com.dexels.navajo.util.Util;

/**
 * This class implements a so called array message.
 * An array message contains one or more submessages that can be regarded as array "elements" of
 * the array message.
 *
 * An ArrayMessage can be "lazy", such that its elements can be addresses in a lazy manner (creating
 * as many elements as requested). Each Message element of an ArrayMessage should have a unique
 * "index" attribute.
 *
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author Arjen Schoneveld
 * @version 1.0
 */

public class ArrayMessage extends Message {

  /**
    * Public constants.
    */

  public static final String MSG_LAZY = "is_lazy";

  private int startIndex;
  private int endIndex;
  private int totalElements;

  public ArrayMessage(Element e) {
    super(e);
    totalElements = 0;
  }

   /**
     * Create a message.
     */
  public static ArrayMessage createArray(Navajo tb, String name) {

        ArrayMessage p = null;

        Document d = tb.getMessageBuffer();
        Element n = (Element) d.createElement(Message.MSG_DEFINITION);

        p = new ArrayMessage(n);
        p.setName(name);
        p.setType(Message.MSG_TYPE_ARRAY);

        return p;
  }

  /**
   * Create an element.
   *
   * @param m
   * @return
   */

   public Message createElement() {

        Message m = null;

        Document d = this.ref.getOwnerDocument();
        Element n = (Element) d.createElement(Message.MSG_DEFINITION);

        m = new Message(n);
        m.setName(this.getName());

        return m;
   }

   /**
   * Add a message element to array message.
   *
   * @param parm1
   * @return
   */
  public Message addMessage(Message m) {

      m.setName(this.getName());
      m.setIndex(totalElements);

      if (m == null)
        return null;

      ref.appendChild(m.ref);
      m.setIndex(this.totalElements);
      this.totalElements++;

      return m;
  }

  /**
   * Get a specific message element from array message.
   *
   * @param i
   * @return
   */
  public Message get(int index) {
    NodeList list = ref.getChildNodes();
    for (int i = 0; i < list.getLength(); i++) {
      if (list.item(i).getNodeName().equals(Message.MSG_DEFINITION)) {
        Element e = (Element) list.item(i);
        if (e.getAttribute(Message.MSG_INDEX).equals(index+""))
          return new Message(e);
      }
    }
    return null;
  }

  public ArrayList getAllElements() {
    return this.getAllMessages();
  }

  public static ArrayMessage toArrayMessage(Message m) {
      int size = m.getAllMessages().size();
      ArrayMessage am = new ArrayMessage(m.ref);
      am.totalElements = size;
      am.setType(Message.MSG_TYPE_ARRAY);
      return am;
  }

   /**
     * Return all messages that match a given regular expression. Regular expression may include sub-messages and even
     * absolute message references starting at the root level.
     */
    public ArrayList getMessages(String regularExpression) throws NavajoException {

        ArrayList messages = new ArrayList();
        ArrayList sub = null;
        ArrayList sub2 = null;

        if (regularExpression.startsWith(Navajo.PARENT_MESSAGE+Navajo.MESSAGE_SEPARATOR)) {
          regularExpression = regularExpression.substring((Navajo.PARENT_MESSAGE+Navajo.MESSAGE_SEPARATOR).length());
          return getParentMessage().getMessages(regularExpression);
        } else
        if (regularExpression.startsWith(Navajo.MESSAGE_SEPARATOR)) { // We have an absolute offset
            Util.debugLog("in Message: getMessages(): " + regularExpression);
            Navajo d = new Navajo(this.ref.getOwnerDocument());
            return d.getMessages(regularExpression);
        } else // Contains submessages.
          if (regularExpression.indexOf(Navajo.MESSAGE_SEPARATOR) != -1) // contains a path, descent it first
        {
            StringTokenizer tok = new StringTokenizer(regularExpression, Navajo.MESSAGE_SEPARATOR);
            Message m = null;

            while (tok.hasMoreElements()) {
                String msgName = tok.nextToken();

                if (sub == null) { // First message in path.
                    sub = getMessages(msgName);
                } else {// Subsequent submessages in path.
                    messages = new ArrayList();
                    for (int i = 0; i < sub.size(); i++) {
                        m = (Message) sub.get(i);
                        sub2 = m.getMessages(msgName);
                        messages.addAll(sub2);
                    }
                    sub = messages;
                }
            }
            return sub;
        }  else {
            ArrayList msgList = getAllMessages();
            ArrayList result = new ArrayList();

            StringTokenizer els = new StringTokenizer(regularExpression, "[]");

            regularExpression = els.nextToken();
            String index = "";
            if (els.hasMoreTokens()) {
                  index = els.nextToken();
            }

            if (index.equals("*"))
                  result = msgList;
                else {
                  for (int i = 0; i < msgList.size(); i++) {
                      Message m = (Message) msgList.get(i);
                      if ((m.getIndex()+"").equals(index))
                          result.add(msgList.get(i));
                  }
            }

            return result;
        }
    }

  public static void main(String args[]) throws Exception {
      Navajo d = new Navajo();
      Message top = Message.create(d, "top");
      d.addMessage(top);

      ArrayMessage am = ArrayMessage.createArray(d, "array");
      top.addMessage(am);
      Message el0 = am.createElement();
      Message el1 = am.createElement();
      Message el2 = am.createElement();
      am.addMessage(el0);
      am.addMessage(el1);
      am.addMessage(el2);
      System.out.println(d.toString());

      ArrayList l = am.getMessages("/top/array[*]");
      System.out.println("array = " + am.isArrayMessage());
      for (int i = 0; i < l.size(); i++) {
        Message el = (Message) l.get(i);
        System.out.println("el = " + el.getName() + "(" + el.getIndex() + ", array = " + el.isArrayMessage() + ")");
      }

  }
}