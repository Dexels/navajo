package com.dexels.navajo.document;

import org.w3c.dom.*;
import java.util.*;

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
   * Add a message element to array message.
   *
   * @param parm1
   * @return
   */
  public Message addMessage(Message m, int index) {

      if (m == null)
        return null;

      Message dummy = this.getMessage(m.getName());

      if ((dummy != null) && (index != -1) && (m.getIndex() == index)) {
        ref.removeChild(dummy.ref);
        this.totalElements--;
      }

      ref.appendChild(m.ref);
      m.setIndex(index);
      this.totalElements++;
      return m;
  }

   /**
   * Add a message element to array message.
   *
   * @param parm1
   * @return
   */
  public Message addMessage(Message m) {
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

  public static void main(String args[]) throws Exception {
      Navajo d = new Navajo();
      ArrayMessage am = ArrayMessage.createArray(d, "array");
      d.addMessage(am);
      Message el0 = Message.create(d, "element");
      Message el1 = Message.create(d, "element");
      Message el2 = Message.create(d, "element");
      am.addMessage(el0);
      am.addMessage(el1);
      am.addMessage(el2);
      System.out.println(d.toString());

      ArrayList l = am.getAllElements();
      System.out.println("array = " + am.isArrayMessage());
      for (int i = 0; i < l.size(); i++) {
        Message el = (Message) l.get(i);
        System.out.println("el = " + el.getName() + "(" + el.getIndex() + ", array = " + el.isArrayMessage() + ")");
      }

  }
}