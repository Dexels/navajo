package com.dexels.navajo.document.jaxpimpl;

import com.dexels.navajo.document.FieldTag;
import com.dexels.navajo.document.*;
import org.w3c.dom.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public final class FieldImpl implements FieldTag {

  public Element ref;
   //private Navajo myRootDoc = null;

   public FieldImpl(Element e) {
     this.ref = e;
   }

   public static final FieldTag create(Navajo tb, String name, String condition) throws NavajoException {

         FieldTag p = null;

         Document d = (Document) tb.getMessageBuffer();

         Element n = (Element) d.createElement(FieldTag.FIELD_DEFINITION);

         p = new FieldImpl(n);
         if (condition != null)
           p.setCondition(condition);
         p.setName(name);

         return p;
     }

  public void addExpression(ExpressionTag e) {
    ref.appendChild((Node) e.getRef());
  }

   public String getName() {
     return ref.getAttribute(FieldTag.FIELD_NAME);
   }

   public void setName(String s) {
     ref.setAttribute(FieldTag.FIELD_NAME, s);
   }

   public String getCondition() {
     return ref.getAttribute(FieldTag.FIELD_CONDITION);
   }

   public void setCondition(String s) {
     ref.setAttribute(FieldTag.FIELD_CONDITION, s);
   }

   public Object getRef() {
     return this.ref;
   }

   public final int compareTo(Object p) {
     return -1;
   }

   public String getComment() {
     return ref.getAttribute(FieldTag.FIELD_COMMENT);
   }

   public void setComment(String s) {
      ref.setAttribute(FieldTag.FIELD_COMMENT, s);
   }


}