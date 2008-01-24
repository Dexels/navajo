package com.dexels.navajo.document.jaxpimpl;

import org.w3c.dom.*;

import com.dexels.navajo.document.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public final class ExpressionImpl
    implements ExpressionTag {

  /**
	 * 
	 */
	private static final long serialVersionUID = -7226916693752872142L;
	public final transient Element ref;
 
  public ExpressionImpl(Element e) {
    this.ref = e;
  }

  public static final ExpressionTag create(Navajo tb, String value, String condition)  {

        ExpressionTag p = null;

        Document d = (Document) tb.getMessageBuffer();

        Element n = d.createElement(ExpressionTag.EXPRESSION_DEFINITION);

        p = new ExpressionImpl(n);
        if (condition != null)
          p.setCondition(condition);
        p.setValue(value);

        return p;
    }

  public String getValue() {
    return ref.getAttribute(ExpressionTag.EXPRESSION_VALUE);
  }

  public void setValue(String s) {
    ref.setAttribute(ExpressionTag.EXPRESSION_VALUE, s);
  }

  public String getCondition() {
    return ref.getAttribute(ExpressionTag.EXPRESSION_CONDITION);
  }

  public void setCondition(String s) {
    ref.setAttribute(ExpressionTag.EXPRESSION_CONDITION, s);
  }

  public Object getRef() {
    return this.ref;
  }

  public final int compareTo(ExpressionTag p) {
    return 0;
  }

}