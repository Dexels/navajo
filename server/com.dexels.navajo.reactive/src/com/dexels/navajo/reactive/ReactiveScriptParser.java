package com.dexels.navajo.reactive;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Vector;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.Operand;
import com.dexels.navajo.document.nanoimpl.CaseSensitiveXMLElement;
import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.document.stream.StreamDocument;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent;
import com.dexels.navajo.parser.Expression;
import com.dexels.navajo.script.api.SystemException;
import com.dexels.replication.api.ReplicationMessage;
import com.dexels.replication.factory.ReplicationFactory;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.functions.Function3;

public class ReactiveScriptParser {

	
	private final static Logger logger = LoggerFactory.getLogger(ReactiveScriptParser.class);

	public ReactiveScriptParser() {
	}

	public ReactiveScript parse(InputStream in) throws UnsupportedEncodingException, IOException {
		XMLElement x = new CaseSensitiveXMLElement();
		x.parseFromStream(in);
		List<ReactiveSource> r =x.getChildren()
				.stream()
				.filter(xe->xe.getName().startsWith("source."))
				.map(elt->parseSource(elt))
				.collect(Collectors.toList());

		String array = x.getStringAttribute("array");
		boolean isArray = array!=null;
		String simple = x.getStringAttribute("simple");
		if(array==null && simple == null) {
			throw new NullPointerException("Should supply either an array or a simple");
		}
		FlowableTransformer<ReplicationMessage, NavajoStreamEvent> transformation = parseTransformation(isArray?array:simple,isArray);
		
		//		apply(ReplicationMessage)
		return new ReactiveScript() {
			
			@Override
			public Flowable<NavajoStreamEvent> execute(StreamScriptContext context, Navajo in, Message current) {
				return Flowable.fromIterable(r)
					.concatMap(r->r.execute(context, Optional.empty()))
					.compose(transformation);
			}
		};
	}
	
	private FlowableTransformer<ReplicationMessage, NavajoStreamEvent> parseTransformation(String name, boolean isArray) {
		if(isArray) {
			return StreamDocument.toArray(name);
		} else {
			return StreamDocument.toMessage(name);
		}
	}

	private ReactiveSource parseSource(XMLElement x) {
		List<XMLElement> children = x.getChildren();
		String type = x.getName();
		
		Map<String,BiFunction<StreamScriptContext,Optional<ReplicationMessage>,Object>> namedParameters = new HashMap<>();
		List<BiFunction<StreamScriptContext,Optional<ReplicationMessage>,Object>> unnamedParameters = new ArrayList<>();
		x.enumerateAttributeNames().forEachRemaining(e->{
			namedParameters.put(e, (context,msg)->x.getStringAttribute(e));
		});
		List<Function<StreamScriptContext,FlowableTransformer<ReplicationMessage, ReplicationMessage>>> maps = new ArrayList<>();
		for (XMLElement possibleParam : children) {
			String elementName = possibleParam.getName();
			if(elementName.equals("param") || elementName.equals("evalparam")) {
				boolean evaluate = elementName.equals("evalparam");
				String name = possibleParam.getStringAttribute("name");
				String content = possibleParam.getContent();
				if(content==null || "".equals(content)) {
					continue;
				}
				if(name==null) {
					if (evaluate) {
						unnamedParameters.add((context,msg)->evaluate((String)content, context, msg).value);
					} else {
						unnamedParameters.add((context,msg)->content);
					}
				} else {
					if(evaluate) {
						namedParameters.put(name, (context,msg)->evaluate((String)content, context, msg).value);
					} else {
						namedParameters.put(name, (context,msg)->content);
					}
				}
			} else {
				maps.add(parseTransformations(possibleParam));
			}
		}
		String[] typeSplit = type.split("\\.");
		System.err.println("# of post processors: "+maps.size());
		ReactiveSource src = createSource(typeSplit[1],namedParameters,unnamedParameters,maps);
		return src;
	}
	
//	private Function<Flowable<ReplicationMessage>, Flowable<ReplicationMessage>> parseTransformations(XMLElement x,StreamScriptContext context) {
	private  Function<StreamScriptContext,FlowableTransformer<ReplicationMessage, ReplicationMessage>> parseTransformations(XMLElement x) {
		String type = x.getName(); //.split("\\.");
		switch (type) {
			case "delete":
				String deleteKey = x.getStringAttribute("key");
				return context->l->l.map(delete(context,deleteKey));
			case "rename":
				String key = x.getStringAttribute("key");
				String to = x.getStringAttribute("to");
				return context->l->l.map(rename(context,key, to));
			case "set":
				String setKey = x.getStringAttribute("key");
				String value = x.getStringAttribute("value");
				return context->l->l.map(setSingle(context,setKey,value));
			case "mergeSingle":
				return createSingleMerge(x);
//				XMLElement reducer = x.getChildByTagName("reduce");
//				List<XMLElement> reductors = reducer.getChildren();
			case "reduce":
//				parseReducer(x).
				
				return reduce(parseReducer(x));
//				parseReducer(x)
//				return context->l->l.reduce(
//						parseReducer(x)
//						);
			default:
				return new Function<StreamScriptContext,FlowableTransformer<ReplicationMessage, ReplicationMessage>>() {

					@Override
					public FlowableTransformer<ReplicationMessage, ReplicationMessage> apply(StreamScriptContext arg0)
							throws Exception {
						return e->e;
					}

					};
		}
	}

	private Function<StreamScriptContext,FlowableTransformer<ReplicationMessage, ReplicationMessage>> createSingleMerge(XMLElement x) {
		Vector<XMLElement> children = x.getChildren();
		if(children.size()!=2) {
			throw new RuntimeException("mergeSingle should have two children");
		}
		XMLElement source = children.get(0);
		ReactiveSource sourceInstance = parseSource(source);
		XMLElement reduce = children.get(1);
		Function3<StreamScriptContext,ReplicationMessage,ReplicationMessage,ReplicationMessage> mergeFunction = xmlSet(reduce);
		
		//eeeek
		return context->flow->flow.concatMap(element->{
			return sourceInstance.execute(context, Optional.of(element)).map(elt->mergeFunction.apply(context,element, elt));
		});
	}


	private ReactiveSource createSource(String type, Map<String, BiFunction<StreamScriptContext,Optional<ReplicationMessage>, Object>> namedParameters,
			List<BiFunction<StreamScriptContext,Optional<ReplicationMessage>, Object>> unnamedParameters, List<Function<StreamScriptContext,FlowableTransformer<ReplicationMessage, ReplicationMessage>>> rest) {
		switch (type) {
			case "sql":
				return new SQLReactiveSource(namedParameters, unnamedParameters,rest);
			default:
				break;
		}
		throw new RuntimeException("Unknown source type: "+type);
	}
	
	private Function3<StreamScriptContext,ReplicationMessage,ReplicationMessage,ReplicationMessage> xmlSet(XMLElement xe) {
		switch (xe.getName()) {
			case "set":
				String key = xe.getStringAttribute("key");
				String value = xe.getStringAttribute("value");
				String to = xe.getStringAttribute("to");
				return set(key,value,to);
			case "copyAll":
				String copyTo = xe.getStringAttribute("to");
				return copyAll(copyTo);
				
		}
		logger.info("Found an unknown mutator: {}",xe.getName());
		return (c,x,y)->x;
	}
	private Function3<StreamScriptContext,ReplicationMessage,ReplicationMessage,ReplicationMessage> set(String... params) {
		return (context,reduc,item)->{
			try {
				String keyExpression = params[0];
				String valueExpression = params[1];
				String key = (String)evaluate(keyExpression,context,Optional.of(item)).value;
				Operand valueOperand = evaluate(valueExpression,context, Optional.of(item));
				Object value = valueOperand.value;
				reduc.with(key,value,valueOperand.type);
			} catch (Throwable e) {
				logger.error("Error: ", e);
			}
			return reduc;
		};
	}
	
	private Function3<StreamScriptContext,ReplicationMessage,ReplicationMessage,ReplicationMessage> copyAll(String... params) {
		return (context,reduc,item)->{
			try {
				String to = params[0];
				Operand toOperand = evaluate(to,context, Optional.of(item));
				Object value = toOperand.value;
				return reduc.withSubMessage((String)value, item);
			} catch (Throwable e) {
				logger.error("Error: ", e);
			}
			return reduc;
		};
	}

	private Function<ReplicationMessage,ReplicationMessage> setSingle(StreamScriptContext context, String... params)  {
		return (item)->{
			try {
				String keyExpression = params[0];
				String valueExpression = params[1];
				String key = (String)evaluate(keyExpression,context,Optional.of(item)).value;
				Operand valueOperand = evaluate(valueExpression,context, Optional.of(item));
				Object value = valueOperand.value;
				return item.with(key,value,valueOperand.type);
			} catch (Throwable e) {
				logger.error("Error: ", e);
			}
			return item;
		};
	}

	private Function3<StreamScriptContext,ReplicationMessage,ReplicationMessage,ReplicationMessage> setReduce(String... params)  {
		//----------
		return (context,acc,item)->{
			try {
				String keyExpression = params[0];
				String valueExpression = params[1];
				String destinationKey = params[2];
//				System.err.println("BiFunction: "+keyExpression+" value: "+valueExpression+" to: "+destinationKey);
				String key = (String)evaluate(keyExpression,context,Optional.of(item)).value;
				String destinationKeyEvaluated = (String)evaluate(destinationKey,context,Optional.of(item)).value;
				Operand valueOperand = evaluate(valueExpression, context, Optional.of(item));
				Object value = valueOperand.value;
				return acc.with(destinationKeyEvaluated,value,valueOperand.type);
			} catch (Throwable e) {
				logger.error("Error: ", e);
			}
			return item;
		};
	}
	
	public Function<ReplicationMessage,ReplicationMessage> rename(StreamScriptContext context, String key, String to)  {
		return in->{
			Object value = in.columnValue(key);
			String type = in.columnType(key);
			return in.without(key)
					.with(key, value, type);
		};
	}
	
	
	private Function<ReplicationMessage,ReplicationMessage> delete(StreamScriptContext context, String key) {
		System.err.println("Delete keys: "+key);
		return in->in.without(Arrays.asList(key.split(",")));
	}
	

	private Operand evaluate(String expression, StreamScriptContext context, Optional<ReplicationMessage> m) throws SystemException {
//		System.err.println("Evaluating: "+valueExpression);
		return Expression.evaluate(expression, context.getInput().orElse(null), null, null,null,null,null,null,m);
	}
	
	
	private Function<StreamScriptContext,BiFunction<ReplicationMessage,ReplicationMessage,ReplicationMessage>> parseReducer(XMLElement x) {
			List<Function3<StreamScriptContext,ReplicationMessage,ReplicationMessage,ReplicationMessage>> functions = x.getChildren().stream().map(mutator->{
				String key = mutator.getStringAttribute("key");
				String value = mutator.getStringAttribute("value");
				String to = mutator.getStringAttribute("to");
				return setReduce(key,value,to);
			}).collect(Collectors.toList());
			

			return context->new BiFunction<ReplicationMessage,ReplicationMessage,ReplicationMessage>(){

				@Override
				public ReplicationMessage apply(ReplicationMessage acc, ReplicationMessage item) throws Exception {
					ReplicationMessage current = acc;

					for (Function3<StreamScriptContext,ReplicationMessage,ReplicationMessage,ReplicationMessage> biFunction : functions) {
						current = biFunction.apply(context,current, item);
					}
					return current;
				}};
	}
	
	public Function<StreamScriptContext,FlowableTransformer<ReplicationMessage, ReplicationMessage>> reduce(Function<StreamScriptContext,BiFunction<ReplicationMessage,ReplicationMessage,ReplicationMessage>> func) {
		return context->new FlowableTransformer<ReplicationMessage, ReplicationMessage>() {

			@Override
			public Flowable<ReplicationMessage> apply(Flowable<ReplicationMessage> in) {
	        	BiFunction<ReplicationMessage, ReplicationMessage, ReplicationMessage> apply;
				try {
					apply = func.apply(context);
					return in.reduce(empty(),apply)
		        			.toFlowable();
				} catch (Exception e) {
					e.printStackTrace();
				}
				return in;
			}
		};
	}

	private static ReplicationMessage empty() {
		return ReplicationFactory.createReplicationMessage(null, System.currentTimeMillis(), ReplicationMessage.Operation.NONE, Collections.emptyList(), Collections.emptyMap(), Collections.emptyMap(),Collections.emptyMap(),Collections.emptyMap(),Optional.of(()->{}));
	}
	
}
