package com.dexels.navajo.parser;

public class ASTDatePatternNode extends SimpleNode {
  String val;
  public ASTDatePatternNode(int id) {
    super(id);
    com.dexels.navajo.util.Util.debugLog("in ASTDatePatternNode()");
  }

  public Object interpret() throws TMLExpressionException {
    return DatePattern.parseDatePattern(val);
  }


}
