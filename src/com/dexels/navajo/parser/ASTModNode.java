package com.dexels.navajo.parser;

public class ASTModNode extends SimpleNode {
  public ASTModNode(int id) {
    super(id);
  }

   public Object interpret() throws TMLExpressionException
  {
    //System.out.println("in ASTModNode()");
    Object a = jjtGetChild(0).interpret();
    //System.out.println("Got first argument");
    Object b = jjtGetChild(1).interpret();
    //System.out.println("Got second argument");

    if (!(a instanceof Integer && b instanceof Integer))
      throw new TMLExpressionException("Modulo operator only defined for integers");
    return new Integer(((Integer) a).intValue() % ((Integer) b).intValue());
  }
}
