package com.dexels.navajo.parser;

public class ASTFloatConstantNode extends SimpleNode {

  double val;

  public ASTFloatConstantNode(int id) {
    super(id);
  }

   public Object interpret()
  {
     com.dexels.navajo.util.Util.debugLog("ASTFloatConstantNode()");
     com.dexels.navajo.util.Util.debugLog("val = " + val);
     return new Double(val);
  }

}
