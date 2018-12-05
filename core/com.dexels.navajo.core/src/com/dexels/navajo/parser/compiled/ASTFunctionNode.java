/* Generated By:JJTree&JavaCC: Do not edit this line. ASTFunctionNode.java */
package com.dexels.navajo.parser.compiled;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.config.runtime.RuntimeConfig;
import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.Selection;
import com.dexels.navajo.expression.api.ContextExpression;
import com.dexels.navajo.expression.api.FunctionInterface;
import com.dexels.navajo.expression.api.TMLExpressionException;
import com.dexels.navajo.expression.api.TipiLink;
import com.dexels.navajo.functions.util.FunctionFactoryFactory;
import com.dexels.navajo.functions.util.OSGiFunctionFactoryFactory;
import com.dexels.navajo.parser.NamedExpression;
import com.dexels.navajo.parser.compiled.api.ParseMode;
import com.dexels.navajo.parser.compiled.api.ReactiveParseItem;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.MappableTreeNode;
import com.dexels.navajo.server.DispatcherFactory;
import com.dexels.navajo.version.AbstractVersion;


public final class ASTFunctionNode extends SimpleNode {

	
	private final static Logger typechecklogger = LoggerFactory.getLogger("navajo.typecheck");

	String functionName;
	int args = 0;
	
	public ASTFunctionNode(int id) {
		super(id);
	}
	
	private FunctionInterface getFunction() {
		ClassLoader cl = null;
		if ( DispatcherFactory.getInstance() == null ) {
			cl = getClass().getClassLoader();
		} else  {
			cl = DispatcherFactory.getInstance().getNavajoConfig().getClassloader();
		} 

		FunctionInterface f = AbstractVersion.osgiActive() ? OSGiFunctionFactoryFactory.getFunctionInterface(functionName) :
			FunctionFactoryFactory.getInstance().getInstance(cl, functionName);
		return f;
	}
	
	@Override
	public ContextExpression interpretToLambda(List<String> problems,String expression, ParseMode mode) {


		List<ContextExpression> l = new LinkedList<>();
		// TODO make lazy?
		Map<String,ContextExpression> named = new HashMap<>();

		for (int i = 0; i <jjtGetNumChildren(); i++) {
			Node sn = jjtGetChild(i);
			ContextExpression cn = sn.interpretToLambda(problems, expression,mode);
			if(cn instanceof NamedExpression) {
				NamedExpression ne = (NamedExpression)cn;
				named.put(ne.name, ne.expression);
			} else {
				l.add(cn);
			}
		}

		switch (mode) {
			
			case REACTIVE_HEADER:
				
				break;
			case REACTIVE_SOURCE:
				return new ReactiveParseItem(functionName, ReactiveParseItem.ReactiveItemType.SOURCE, named, l, expression);
//				System.err.println("Creating reactive source: "+this.functionName);
//				return resolveReactiveSource(l, named, problems, expression);
			case REACTIVE_TRANSFORMER:
				return new ReactiveParseItem(functionName, ReactiveParseItem.ReactiveItemType.TRANSFORMER, named, l, expression);
	
			case DEFAULT:
				default:
		}
		return resolveNormalFunction(l, named, problems, expression);

	}
	
	private ContextExpression resolveNormalFunction(List<ContextExpression> l, Map<String, ContextExpression> named,
			List<String> problems, String expression) {
		FunctionInterface typeCheckInstance = getFunction();
		if(typeCheckInstance==null) {
			throw new NullPointerException("Function: "+functionName+" can not be resolved!");
		}

		try {
			List<String> typeProblems = typeCheckInstance.typeCheck(l,expression);
			if(!typeProblems.isEmpty()) {
				if(RuntimeConfig.STRICT_TYPECHECK.getValue()!=null) {
					problems.addAll(typeProblems);
				}
			}
		} catch (Throwable e2) {
			typechecklogger.error("Typechecker itself failed when parsing: "+expression+" function definition: "+typeCheckInstance+" Error: ", e2);
		}
		
		return new ContextExpression() {
			
			@Override
			public boolean isLiteral() {
				// TODO also check named params
				return typeCheckInstance.isPure() && l.stream().allMatch(e->e.isLiteral());
			}
			
			@Override
			public Object apply(Navajo doc, Message parentMsg, Message parentParamMsg, Selection parentSel,
					 MappableTreeNode mapNode, TipiLink tipiLink, Access access, Optional<ImmutableMessage> immutableMessage, Optional<ImmutableMessage> paramMessage) throws TMLExpressionException {
				FunctionInterface f = getFunction();
				Map<String,Object> resolvedNamed = named.entrySet().stream().collect(Collectors.toMap(e->e.getKey(),e->e.getValue().apply(doc, parentMsg, parentParamMsg, parentSel, mapNode,tipiLink, access,immutableMessage,paramMessage)));
				f.setInMessage(doc);
				f.setNamedParameter(resolvedNamed);
				f.setCurrentMessage(parentMsg);
				f.setAccess(access);
				f.reset();
				l.stream()
					.map(e->{
						try {
							return e.apply(doc, parentMsg, parentParamMsg, parentSel, mapNode,tipiLink, access,immutableMessage,paramMessage);
						} catch (TMLExpressionException e1) {
							throw new RuntimeException("Error parsing parameters for function: "+functionName, e1);
						}
					})
					.forEach(e->f.insertOperand(e));
				return f.evaluateWithTypeChecking();
			}

			@Override
			public Optional<String> returnType() {
				return typeCheckInstance.getReturnType();
			}
			@Override
			public String expression() {
				return expression;
			}
		};
	}

}
