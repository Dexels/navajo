package com.dexels.navajo.parser;

public class ASTStringConstantNode extends SimpleNode {

  String val;

  public ASTStringConstantNode(int id) {
    super(id);
  }

   public Object interpret()
  {
     com.dexels.navajo.util.Util.debugLog("ASTStringConstantNode()");
     com.dexels.navajo.util.Util.debugLog("val = " + val);

     // Strip quotes.
     return new String(val.substring(1, val.length() - 1));
  }
}
