package com.dexels.navajo.document.jaxpimpl;

import com.dexels.navajo.document.ParamTag;
import com.dexels.navajo.document.ExpressionTag;
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

public class ParamImpl implements ParamTag {
  public Element ref;
   private Navajo myRootDoc = null;

   public ParamImpl(Element e) {
     this.ref = e;
   }

   public static final ParamTag create(Navajo tb, String name, String condition) throws NavajoException {

         ParamTag p = null;

         Document d = (Document) tb.getMessageBuffer();

         Element n = (Element) d.createElement(ParamTag.PARAM_DEFINITION);

         p = new ParamImpl(n);
         if (condition != null)
           p.setCondition(condition);
         p.setName(name);

         return p;
     }

  public void addExpression(ExpressionTag e) {
    ref.appendChild((Node) e.getRef());
  }

   public String getName() {
     return ref.getAttribute(ParamTag.PARAM_NAME);
   }

   public void setName(String s) {
     ref.setAttribute(ParamTag.PARAM_NAME, s);
   }

   public String getCondition() {
     return ref.getAttribute(ParamTag.PARAM_CONDITION);
   }

   public void setCondition(String s) {
     ref.setAttribute(ParamTag.PARAM_CONDITION, s);
   }

   public Object getRef() {
     return this.ref;
   }

   public final int compareTo(Object p) {
     return -1;
   }

   public String getComment() {
     return ref.getAttribute(ParamTag.PARAM_COMMENT);
   }

   public void setComment(String s) {
      ref.setAttribute(ParamTag.PARAM_COMMENT, s);
   }

}