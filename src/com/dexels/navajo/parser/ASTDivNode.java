package com.dexels.navajo.parser;

public class ASTDivNode extends SimpleNode {
  public ASTDivNode(int id) {
    super(id);
    //System.out.println("in ASTDivNode()");
  }

  public Object interpret() throws TMLExpressionException {

    //System.out.println("in interpret() ASTDivNode");
    Object a = this.jjtGetChild(0).interpret();
    Object b = this.jjtGetChild(1).interpret();
    if (a instanceof String || b instanceof String)
      throw new TMLExpressionException("Division not defined for strings");
    if (a instanceof Integer && b instanceof Integer)
      return new Integer(((Integer) a).intValue() / ((Integer) b).intValue());
    double dA = Utils.getDoubleValue(a);
    double dB = Utils.getDoubleValue(b);
    return new Double(dA / dB);
  }
}
