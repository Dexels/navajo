package com.dexels.navajo.parser;

public class ASTOrNode extends SimpleNode {
  public ASTOrNode(int id) {
    super(id);
  }

  public Object interpret() throws TMLExpressionException
  {
    //System.out.println("in ASTOrNode()");
    Boolean a = (Boolean) jjtGetChild(0).interpret();
    //System.out.println("Got first argument");
    Boolean b = (Boolean) jjtGetChild(1).interpret();
    //System.out.println("Got second argument");

    return new Boolean(a.booleanValue() || b.booleanValue());
  }
}
