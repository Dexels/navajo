package com.dexels.navajo.parser;

public class ASTNegativeNode extends SimpleNode {
  public ASTNegativeNode(int id) {
    super(id);
  }

  public Object interpret() throws TMLExpressionException {

    Object a = this.jjtGetChild(0).interpret();
    if (a instanceof String)
      return new String("-" + (String) a);
    else if (a instanceof Integer)
      return new Integer(0 - ((Integer) a).intValue());
    else if (a instanceof Double)
      return new Double(0 - ((Double) a).doubleValue());
    else
      throw new TMLExpressionException("Illegal type encountered before negation");
  }

}
