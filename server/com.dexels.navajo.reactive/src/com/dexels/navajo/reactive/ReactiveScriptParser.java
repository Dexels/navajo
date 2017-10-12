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
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Operand;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.nanoimpl.CaseSensitiveXMLElement;
import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.StreamDocument;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent;
import com.dexels.navajo.parser.Expression;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveScript;
import com.dexels.navajo.reactive.api.ReactiveSource;
import com.dexels.navajo.reactive.api.ReactiveSourceFactory;
import com.dexels.navajo.reactive.api.ReactiveTransformer;
import com.dexels.navajo.reactive.api.ReactiveTransformerFactory;
import com.dexels.navajo.reactive.source.mongo.MongoReactiveSourceFactory;
import com.dexels.navajo.reactive.source.sql.SQLReactiveSourceFactory;
import com.dexels.navajo.reactive.transformer.csv.CSVTransformerFactory;
import com.dexels.navajo.reactive.transformer.filestore.FileStoreTransformerFactory;
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

	private Map<String,ReactiveSourceFactory> factories = new HashMap<>();
	private Map<String,ReactiveTransformerFactory> reactiveOperatorFactory = new HashMap<>();
	
	public ReactiveScriptParser() {
		registerReactiveFactory("sql", new SQLReactiveSourceFactory());
		registerReactiveFactory("mongo", new MongoReactiveSourceFactory());
		registerReactiveTransformerFactory("csv", new CSVTransformerFactory());
		registerReactiveTransformerFactory("filestore", new FileStoreTransformerFactory());
	}
	
	private void registerReactiveFactory(String name, ReactiveSourceFactory factory) {
		factories.put(name, factory);
	}

	private void registerReactiveTransformerFactory(String name, ReactiveTransformerFactory factory) {
		reactiveOperatorFactory.put(name, factory);
	}

	
	public ReactiveScript parse(String serviceName, InputStream in) throws UnsupportedEncodingException, IOException {
		XMLElement x = new CaseSensitiveXMLElement();
		x.parseFromStream(in);
		List<ReactiveSource> r = parseRoot(x);

		return new ReactiveScript() {
			
			@Override
			public Flowable<NavajoStreamEvent> execute(StreamScriptContext context) {
				return Flowable.fromIterable(r)
					.doOnNext(s->System.err.println("Source detected"))
					.concatMap(r->r.execute(context, Optional.empty())
					.compose(parseTransformation("ChangeMe", true)))
					.compose(StreamDocument.inNavajo(context.service, context.username, ""));
			}
		};
	}
	
	private FlowableTransformer<DataItem, NavajoStreamEvent> parseTransformation(String name, boolean isArray) {
		if(isArray) {
			return flow->flow.map(item->item.message()).compose(StreamDocument.toArray(name));
		} else {
			return flow->flow.map(item->item.message()).compose(StreamDocument.toMessage(name));
		}
	}

	private List<ReactiveSource> parseRoot(XMLElement x) {
		List<ReactiveSource> r =x.getChildren()
				.stream()
//				.filter(xe->xe.getName().startsWith("source."))
				.map(this::parseSource)
				.collect(Collectors.toList());		
		
		return r;
	}

//	private ReactiveSource parseElement(XMLElement x) {
//		
//	}
//	
	private ReactiveParameters parseParamsFromChildren(XMLElement x) {
		List<XMLElement> children = x.getChildren();
		Map<String,BiFunction<StreamScriptContext,Optional<ReplicationMessage>,Operand>> namedParameters = new HashMap<>();
		List<BiFunction<StreamScriptContext,Optional<ReplicationMessage>,Operand>> unnamedParameters = new ArrayList<>();
		x.enumerateAttributeNames().forEachRemaining(e->{
			namedParameters.put(e, (context,msg)->new Operand(x.getStringAttribute(e),Property.STRING_PROPERTY,null));
		});
		for (XMLElement possibleParam : children) {
			String elementName = possibleParam.getName();
			if(elementName.startsWith("param")) {
				boolean evaluate = elementName.endsWith("eval");
				String name = possibleParam.getStringAttribute("name");
				String content = possibleParam.getContent();
				if(content==null || "".equals(content)) {
					continue;
				}
				if(name==null) {
					if (evaluate) {
						unnamedParameters.add((context,msg)->evaluate((String)content, context, msg));
					} else {
						unnamedParameters.add((context,msg)->new Operand(content,"string",null));
					}
				} else {
					if(evaluate) {
						BiFunction<StreamScriptContext, Optional<ReplicationMessage>, Operand> value = (context,msg)->evaluate((String)content, context, msg);
						namedParameters.put(name, value);
					} else {
						namedParameters.put(name, (context,msg)->new Operand(content,"string",null));
					}
				}
			} else {
				logger.info("Ignoring non-parameter: "+possibleParam.getName());
			}
		}
		return ReactiveParameters.of(namedParameters, unnamedParameters);
	}

	private ReactiveSource parseSource(XMLElement x) {
		String type = x.getName();
		String[] typeSplit = type.split("\\.");
		ReactiveParameters params = parseParamsFromChildren(x);
		List<ReactiveTransformer> factories = parseTransformationsFromChildren(x);
		ReactiveSource src = createSource(typeSplit[1],params,factories);
		return src;
	}

	private List<ReactiveTransformer> parseTransformationsFromChildren(XMLElement parent) {
		List<ReactiveTransformer> result = new ArrayList<>();
		List<XMLElement> children = parent.getChildren();
		for (XMLElement possibleParam : children) {
			String type = possibleParam.getName();
			String[] typeParts = type.split("\\.");
			
			if(!type.startsWith("param") && !type.startsWith("source")) {
				if(typeParts.length == 3) {
					String baseType = typeParts[0];
					String operatorName = typeParts[1];
					String newBaseType = typeParts[2];
					ReactiveTransformerFactory typeFactory = this.reactiveOperatorFactory.get(operatorName);
					ReactiveParameters transformParams = parseParamsFromChildren(possibleParam);
					System.err.println("PARSING OPERATOR: "+operatorName);
					result.add(typeFactory.build(transformParams));
				}
			}
		}

		return result;
//		switch (type) {
//			case "delete":
//				String deleteKey = x.getStringAttribute("key");
//				return context->l->l.map(delete(context,deleteKey)); 
//			case "rename":
//				String key = x.getStringAttribute("key");
//				String to = x.getStringAttribute("to");
//				return context->l->l.map(rename(context,key, to));
//			case "set":
//				String setKey = x.getStringAttribute("key");
//				String evalkey = x.getStringAttribute("key.eval");
//				String value = x.getStringAttribute("value");
////				String valueeval = x.getStringAttribute("value.eval");
//				String finalvalue = value==null?value = x.getContent():value;
//				boolean eval = evalkey!=null;
////				boolean evalvalue = valueeval!=null;
//				return context->l->l.map(setSingle(context,eval?evalkey:setKey,eval,finalvalue));
//			case "mergeSingle":
//				
//				return createSingleMerge(x);
//			case "reduce":
//				
//				return reduce(parseReducer(x));
//
//			default:
//				return ex->ex;
//		}
	}

	private Function<StreamScriptContext,FlowableTransformer<DataItem,DataItem>> createSingleMerge(XMLElement x) {
		Vector<XMLElement> children = x.getChildren();
		if(children.size()!=2) {
			throw new RuntimeException("mergeSingle should have two children");
		}
		XMLElement source = children.get(0);
		ReactiveSource sourceInstance = parseSource(source);
		XMLElement reduce = children.get(1);
		Function3<StreamScriptContext,DataItem,DataItem,DataItem> mergeFunction = xmlSet(reduce);
		//eeeek
//		return context->flow->flow.cast(ReplicationMessage.class)
		return context->flow->flow.concatMap(element->sourceInstance.execute(context, Optional.of(element.message())).map(elt->mergeFunction.apply(context,element, elt)));
	}


	private ReactiveSource createSource(String type,
			ReactiveParameters params,
			List<ReactiveTransformer> transformations) {
			ReactiveSourceFactory reactiveSourceFactory = factories.get(type);
			return reactiveSourceFactory.build(params,transformations);
	}
	
	private Function3<StreamScriptContext,DataItem,DataItem,DataItem> xmlSet(XMLElement xe) {
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
	private Function3<StreamScriptContext,DataItem,DataItem,DataItem> set(String... params) {
		return (context,reduc,item)->{
			try {
				String keyExpression = params[0];
				String valueExpression = params[1];
				ReplicationMessage message = item.message();
				String key = (String)evaluate(keyExpression,context,Optional.of(message)).value;
				Operand valueOperand = evaluate(valueExpression,context, Optional.of(message));
				Object value = valueOperand.value;
				DataItem.of(reduc.message().with(key,value,valueOperand.type));
			} catch (Throwable e) {
				logger.error("Error: ", e);
			}
			return reduc;
		};
	}
	
	private Function3<StreamScriptContext,DataItem,DataItem,DataItem> copyAll(String... params) {
		return (context,reduc,item)->{
			try {
				String to = params[0];
				Operand toOperand = evaluate(to,context, Optional.of(item.message()));
				Object value = toOperand.value;
				return DataItem.of(reduc.message().withSubMessage((String)value, item.message()));
			} catch (Throwable e) {
				logger.error("Error: ", e);
			}
			return reduc;
		};
	}

	private Function<DataItem,DataItem> setSingle(StreamScriptContext context, String keySetter, boolean evaluate, String valueExpression)  {
		return (elt)->{
			try {
				ReplicationMessage item = elt.message();
				String key = evaluate ? (String)evaluate(keySetter,context,Optional.of(item)).value : keySetter;
				Operand valueOperand = evaluate(valueExpression,context, Optional.of(item));
				Object value = valueOperand.value;
				return DataItem.of(item.with(key,value,valueOperand.type));
			} catch (Throwable e) {
				logger.error("Error: ", e);
			}
			return elt;
		};
	}

	private Function3<StreamScriptContext,DataItem,DataItem,DataItem> setReduce(String... params)  {
		return (context,acc,item)->{
			try {
//				String keyExpression = params[0];
				String valueExpression = params[1];
				String destinationKey = params[2];
//				System.err.println("BiFunction: "+keyExpression+" value: "+valueExpression+" to: "+destinationKey);
//				String key = (String)evaluate(keyExpression,context,Optional.of(item)).value;
				String destinationKeyEvaluated = (String)evaluate(destinationKey,context,Optional.of(item.message())).value;
				Operand valueOperand = evaluate(valueExpression, context, Optional.of(item.message()));
				Object value = valueOperand.value;
				return DataItem.of(acc.message().with(destinationKeyEvaluated,value,valueOperand.type));
			} catch (Throwable e) {
				logger.error("Error: ", e);
			}
			return item;
		};
	}
	
	public Function<DataItem,DataItem> rename(StreamScriptContext context, String key, String to)  {
		return item->{
			ReplicationMessage msg = item.message();
			Object value = msg.columnValue(key);
			String type = msg.columnType(key);
			return DataItem.of(msg.without(key)
					.with(key, value, type));
		};
	}
	
	
	private Function<DataItem,DataItem> delete(StreamScriptContext context, String key) {
		return in->DataItem.of( in.message().without(Arrays.asList(key.split(","))));
	}
	

	private Operand evaluate(String expression, StreamScriptContext context, Optional<ReplicationMessage> m) throws SystemException {
		return Expression.evaluate(expression, context.getInput().orElse(null), null, null,null,null,null,null,m);
	}
	
	
	private Function<StreamScriptContext,BiFunction<DataItem,DataItem,DataItem>> parseReducer(XMLElement x) {
			List<Function3<StreamScriptContext,DataItem,DataItem,DataItem>> functions = x.getChildren().stream().map(mutator->{
				String key = mutator.getStringAttribute("key");
				String value = mutator.getStringAttribute("value");
				String to = mutator.getStringAttribute("to");
				return setReduce(key,value,to);
			}).collect(Collectors.toList());
			

			return context->new BiFunction<DataItem,DataItem,DataItem>(){

				@Override
				public DataItem apply(DataItem acc, DataItem item) throws Exception {
					DataItem current = acc;

					for (Function3<StreamScriptContext,DataItem,DataItem,DataItem> biFunction : functions) {
						current = biFunction.apply(context,current, item);
					}
					return current;
				}
			};
	}
	
	public Function<StreamScriptContext,FlowableTransformer<DataItem, DataItem>> reduce(Function<StreamScriptContext,BiFunction<DataItem,DataItem,DataItem>> func) {
		return context->new FlowableTransformer<DataItem, DataItem>() {

			@Override
			public Flowable<DataItem> apply(Flowable<DataItem> in) {
	        	BiFunction<DataItem, DataItem, DataItem> apply;
				try {
					apply = func.apply(context);
					return in.reduce(DataItem.of(empty()),apply)
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
