/* Generated By:JJTree: Do not edit this line. ASTReactiveScriptNode.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=false,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package com.dexels.navajo.parser.compiled;

import java.util.List;

import com.dexels.navajo.expression.api.ContextExpression;
import com.dexels.navajo.parser.compiled.api.ParseMode;

public
class ASTReactiveScriptNode extends SimpleNode {
  public int args = 0;
  public ASTFunctionNode header = null;
  public ASTReactiveScriptNode(int id) {
    super(id);
  }

@Override
public ContextExpression interpretToLambda(List<String> problems, String originalExpression, ParseMode mode) {
	// TODO support headers;
	
	for (int i = 0; i < jjtGetNumChildren(); i++) {
		ASTReactivePipe pipe = (ASTReactivePipe) jjtGetChild(i);
		pipe.interpretToLambda(problems,originalExpression,ParseMode.DEFAULT);
		
	}
	return null;
}


}
/* JavaCC - OriginalChecksum=1b3774ce274fd31113ba44556c6878a0 (do not edit this line) */
