package com.dexels.navajo.parser;

public class ASTIntegerConstantNode extends SimpleNode {

  int val;

  public ASTIntegerConstantNode( int id) {
    super(id);
  }

  public Object interpret()
  {
     com.dexels.navajo.util.Util.debugLog("ASTIntegerConstant()");
     com.dexels.navajo.util.Util.debugLog("val = " + val);

     return new Integer(val);
  }
}
