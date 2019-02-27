/* Generated By:JJTree: Do not edit this line. ASTReactivePipe.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=false,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package com.dexels.navajo.parser.compiled;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.expression.api.ContextExpression;
import com.dexels.navajo.expression.api.FunctionClassification;
import com.dexels.navajo.parser.compiled.api.ReactivePipeNode;
import com.dexels.navajo.reactive.api.ReactiveSource;
import com.dexels.navajo.reactive.api.ReactiveTransformer;


public
class ASTReactivePipe extends SimpleNode {
	
	
	private final static Logger logger = LoggerFactory.getLogger(ASTReactivePipe.class);

public int args = 0;
  public ASTReactivePipe(int id) {
    super(id);
  }

@SuppressWarnings("unchecked")
@Override
public ContextExpression interpretToLambda(List<String> problems, String originalExpression, Function<String, FunctionClassification> functionClassifier) {
	ASTPipeline actual = (ASTPipeline) jjtGetChild(0);

	int count = actual.jjtGetNumChildren();
	ReactiveSource sourceNode = (ReactiveSource) actual.jjtGetChild(0).interpretToLambda(problems, "",fn->FunctionClassification.REACTIVE_SOURCE).apply().value;
//	for (int i = 1; i < actual.jjtGetNumChildren(); i++) {
//		ASTFunctionNode sdn = (ASTFunctionNode)actual.jjtGetChild(i);
//	}
	List<Object> pipeElements = new ArrayList<>();

	for (int i = 1; i < count; i++) {
		ContextExpression interpretToLambda = actual.jjtGetChild(i).interpretToLambda(problems, originalExpression,functionClassifier);
		Object result = interpretToLambda.apply().value;
		if(result instanceof Function) {
			Function<StreamScriptContext,Function<DataItem,DataItem>> merger = (Function<StreamScriptContext,Function<DataItem,DataItem>>) result;
			pipeElements.add(merger);

		} else if(result instanceof ReactiveTransformer) {
			ReactiveTransformer transformer = (ReactiveTransformer) result;
			pipeElements.add(transformer);
		} else {
			logger.warn("huh?"+result);
			// something weird
		}
	}
	ReactivePipeNode pipe = new ReactivePipeNode(sourceNode, pipeElements);
	return pipe;
}

public void addSource() {
	args++;
}

public void addTransformer() {
	args++;
}

@Override
public void jjtClose() {
	super.jjtClose();

}



}
/* JavaCC - OriginalChecksum=dd1db8c7a34ea094a180c8dc73739db3 (do not edit this line) */
