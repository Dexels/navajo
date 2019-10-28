/* Generated By:JJTree: Do not edit this line. ASTReactiveScriptNode.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=false,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package com.dexels.navajo.parser.compiled;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.Operand;
import com.dexels.navajo.document.Selection;
import com.dexels.navajo.expression.api.ContextExpression;
import com.dexels.navajo.expression.api.FunctionClassification;
import com.dexels.navajo.expression.api.TipiLink;
import com.dexels.navajo.parser.NamedExpression;
import com.dexels.navajo.parser.compiled.api.ReactivePipeNode;
import com.dexels.navajo.reactive.api.Reactive;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.MappableTreeNode;

public class ASTReactiveScriptNode extends SimpleNode {
  public int args = 0;
  public int headers = 0;
  public boolean hasHeader = false;
  public ASTFunctionNode header = null;
  private final Map<String,Operand> headerMap = new HashMap<>();
  ASTReactiveScriptNode(int id) {
    super(id);
  }

@Override
public ContextExpression interpretToLambda(List<String> problems, String originalExpression, Function<String, FunctionClassification> functionClassifier, Function<String,Optional<Node>> mapResolver) {
	int start = hasHeader ? headers : 0;
	
	if(hasHeader) {
		for (int i = 0; i < headers; i++) {
			ASTKeyValueNode hdr = (ASTKeyValueNode) jjtGetChild(i);	
			NamedExpression ne = (NamedExpression) hdr.interpretToLambda(problems, originalExpression, functionClassifier,mapResolver);
			String key = ne.name;
			headerMap.put(key, ne.apply());
		}
	}
	int count = jjtGetNumChildren();
	List<Node> unnamedPipes = new ArrayList<>();
	Map<String,Node> namedPipes = new HashMap<>();
	for (int i = start; i < count; i++) {
		Node child = jjtGetChild(i);
//		ASTReactivePipe pipe = null;
		if(child instanceof ASTPipeDefinition) {
			unnamedPipes.add((ASTPipeDefinition) child);
		} else if (child instanceof ASTKeyValueNode) {
			ASTKeyValueNode kvNode = (ASTKeyValueNode)child;
			String streamName = kvNode.val;
			Node namedPipe = kvNode.jjtGetChild(0);
			namedPipes.put(streamName, namedPipe);			
		}
	}
	List<ReactivePipeNode> pipes = unnamedPipes.stream()
			.map(p->(ReactivePipeNode)p.interpretToLambda(problems, originalExpression, functionClassifier,name->Optional.ofNullable(namedPipes.get(name))))
			.collect(Collectors.toList());

	return new ContextExpression() {
		
		@Override
		public Optional<String> returnType() {
			return Optional.of(Reactive.ReactiveItemType.REACTIVE_SCRIPT.toString());
		}
		
		@Override
		public boolean isLiteral() {
			return true;
		}
		
		@Override
		public String expression() {
			return "";
		}
		
		@Override
		public Operand apply(Navajo doc, Message parentMsg, Message parentParamMsg, Selection parentSel,
				MappableTreeNode mapNode, TipiLink tipiLink, Access access, Optional<ImmutableMessage> immutableMessage,
				Optional<ImmutableMessage> paramMessage) {
			return Operand.ofCustom(pipes, Reactive.ReactiveItemType.REACTIVE_SCRIPT.toString());
		}
	};
}

	public List<String> methods() {
		Operand methodStr = this.headerMap.get("methods");
		if(methodStr==null) {
			return Collections.emptyList();
		}
		return Arrays.asList(((String)methodStr.value).split(","));
		
	}

	public Optional<String> mime() {
		Optional<Operand> mime = Optional.ofNullable(this.headerMap.get("mime"));
		return mime.map(e->(String)e.value);
	}

}
/* JavaCC - OriginalChecksum=1b3774ce274fd31113ba44556c6878a0 (do not edit this line) */
