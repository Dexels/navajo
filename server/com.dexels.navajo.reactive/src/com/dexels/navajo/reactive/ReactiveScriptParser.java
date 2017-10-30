package com.dexels.navajo.reactive;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Operand;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.nanoimpl.CaseSensitiveXMLElement;
import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.DataItem.Type;
import com.dexels.navajo.document.stream.ReactiveScript;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.parser.Expression;
import com.dexels.navajo.reactive.api.ReactiveMapper;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveSource;
import com.dexels.navajo.reactive.api.ReactiveSourceFactory;
import com.dexels.navajo.reactive.api.ReactiveTransformer;
import com.dexels.navajo.reactive.api.ReactiveTransformerFactory;
import com.dexels.navajo.reactive.mappers.CopyMessage;
import com.dexels.navajo.reactive.mappers.Delete;
import com.dexels.navajo.reactive.mappers.Rename;
import com.dexels.navajo.reactive.mappers.SetSingle;
import com.dexels.navajo.reactive.mappers.SetSingleKeyValue;
import com.dexels.navajo.script.api.SystemException;
import com.dexels.replication.api.ReplicationMessage;
import com.dexels.replication.factory.ReplicationFactory;

import io.reactivex.Flowable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.functions.Function3;

public class ReactiveScriptParser {

	
	private final static Logger logger = LoggerFactory.getLogger(ReactiveScriptParser.class);

	private final Map<String,ReactiveSourceFactory> factories = new HashMap<>();
	private final Map<String,ReactiveTransformerFactory> reactiveOperatorFactory = new HashMap<>();
	private final Map<String,ReactiveMapper> reactiveMapper = new HashMap<>();
	

	public ReactiveScriptParser() {
		reactiveMapper.put("set", new SetSingle());
		reactiveMapper.put("setkv", new SetSingleKeyValue());
		reactiveMapper.put("toSubMessage", new CopyMessage());
		reactiveMapper.put("delete", new Delete());
		reactiveMapper.put("rename", new Rename());
	}
	
	public void addReactiveSourceFactory(ReactiveSourceFactory factory, String name) {
		factories.put(name, factory);
	}

	public void addReactiveSourceFactory(ReactiveSourceFactory factory, Map<String,Object> settings) {
		factories.put((String) settings.get("name"), factory);
	}

	public void removeReactiveSourceFactory(ReactiveSourceFactory factory, Map<String,Object> settings) {
		String name = (String) settings.remove("name");
		factories.remove(name);
	}

	public void addReactiveTransformerFactory(ReactiveTransformerFactory factory, String name) {
		reactiveOperatorFactory.put(name, factory);
	}
	
	public void addReactiveTransformerFactory(ReactiveTransformerFactory factory, Map<String,Object> settings) {
		reactiveOperatorFactory.put((String) settings.get("name"), factory);
	}

	public void removeReactiveTransformerFactory(ReactiveTransformerFactory factory, Map<String,Object> settings) {
		reactiveOperatorFactory.remove((String) settings.get("name"));
	}
	
	public ReactiveScript parse(String serviceName, InputStream in) throws UnsupportedEncodingException, IOException {
		XMLElement x = new CaseSensitiveXMLElement();
		x.parseFromStream(in);
		List<ReactiveSource> r = parseRoot(x);
		Type scriptType = null;
		for (ReactiveSource reactiveSource : r) {
			if(scriptType!=null) {
				if(reactiveSource.dataType()!=scriptType) {
					throw new IOException("Can't parse script for service: "+serviceName+" as there are different source types.");
				}
			} else {
				scriptType = reactiveSource.finalType();
			}
			;
		}
		
		final Type finalType = scriptType;
		
		return new ReactiveScript() {
			
			@Override
			public Flowable<DataItem> execute(StreamScriptContext context) {
				return Flowable.fromIterable(r)
					.concatMap(r->r.execute(context, Optional.empty()));
			}

			@Override
			public Type dataType() {
				return finalType;
			}
		};
	}

	private List<ReactiveSource> parseRoot(XMLElement x) {
		List<ReactiveSource> r =x.getChildren()
				.stream()
				.map(xx->{
					try {
						return parseSource(xx,
								operatorName->{ 
									ReactiveSourceFactory src = this.factories.get(operatorName);
									if(src==null) {
										throw new RuntimeException("Missing source for factory: "+operatorName);
									}
									return src;
								},
								operatorName->{
									ReactiveTransformerFactory transformer = this.reactiveOperatorFactory.get(operatorName);
									if(transformer==null) {
										throw new RuntimeException("Missing transformer for factory: "+operatorName);
									}
									return transformer;
								},
								operatorName->{
									ReactiveMapper mpr = this.reactiveMapper.get(operatorName);
									if(mpr==null) {
										throw new RuntimeException("Missing mapper for factory: "+operatorName);
									}
									return mpr;
								}
								
							);
					} catch (Exception e) {
						throw new RuntimeException("Major source parsing issue. This is not going to end well.", e);
					}
				})
				.collect(Collectors.toList());
		
		return r;
	}

	public static ReactiveParameters parseParamsFromChildren(XMLElement x) {
		List<XMLElement> children = x.getChildren();
		Map<String,Function3<StreamScriptContext,Optional<ReplicationMessage>,Optional<ReplicationMessage>,Operand>> namedParameters = new HashMap<>();
		List<Function3<StreamScriptContext,Optional<ReplicationMessage>,Optional<ReplicationMessage>,Operand>> unnamedParameters = new ArrayList<>();
		x.enumerateAttributeNames().forEachRemaining(e->{
			if(e.endsWith(".eval")) {
				String name = e.substring(0, e.length()-".eval".length());
				Function3<StreamScriptContext,Optional<ReplicationMessage>, Optional<ReplicationMessage>, Operand> value = (context,msg,param)->evaluate((String)x.getStringAttribute(e), context, msg,param);
				namedParameters.put(name, value);
			} else {
				namedParameters.put(e, (context,msg,param)->new Operand(x.getStringAttribute(e),Property.STRING_PROPERTY,null));
				
			}
		}
		);
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
						unnamedParameters.add((context,msg,param)->evaluate((String)content, context, msg,param));
					} else {
						unnamedParameters.add((context,msg,param)->new Operand(content,"string",null));
					}
				} else {
					if(evaluate) {
						Function3<StreamScriptContext, Optional<ReplicationMessage>,Optional<ReplicationMessage>, Operand> value = (context,msg,param)->evaluate((String)content, context, msg,param);
						namedParameters.put(name, value);
					} else {
						namedParameters.put(name, (context,msg,param)->new Operand(content,"string",null));
					}
				}
			} else {
				logger.info("Ignoring non-parameter: "+possibleParam.getName());
			}
		}
		return ReactiveParameters.of(namedParameters, unnamedParameters);
	}
	
	private static ReactiveSource parseSource(XMLElement x, Function<String,ReactiveSourceFactory> sourceFactorySupplier,Function<String,ReactiveTransformerFactory> factorySupplier,Function<String, ReactiveMapper> mapperSupplier) throws Exception  {
		String type = x.getName();
		System.err.println("Type of source: "+type);
		String[] typeSplit = type.split("\\.");
		ReactiveParameters params = parseParamsFromChildren(x);
		ReactiveSource src = createSource(x,typeSplit[1],params,sourceFactorySupplier, factorySupplier, mapperSupplier);
		return src;
	}

	private static List<ReactiveTransformer> parseTransformationsFromChildren(XMLElement parent, Function<String,ReactiveSourceFactory> sourceFactorySupplier,Function<String,ReactiveTransformerFactory> factorySupplier, Function<String, ReactiveMapper> mapperSupplier) throws Exception {
		List<ReactiveTransformer> result = new ArrayList<>();
		List<XMLElement> children = parent.getChildren();
		for (XMLElement possibleParam : children) {
			String type = possibleParam.getName();
			String[] typeParts = type.split("\\.");
			
			if(!type.startsWith("param") && !type.startsWith("source")) {
				// so it's a transformer
				
				parseTransformerElement(result, possibleParam, typeParts,sourceFactorySupplier,factorySupplier,mapperSupplier);
			}
		}

		return result;
	}

	private static void parseTransformerElement(List<ReactiveTransformer> result, XMLElement xml,String[] typeParts,Function<String,ReactiveSourceFactory> sourceSupplier, Function<String,ReactiveTransformerFactory> factorySupplier, Function<String, ReactiveMapper> mapperSupplier) throws Exception {
		if(typeParts.length == 3) {
			String baseType = typeParts[0];
			String operatorName = typeParts[1];
			String newBaseType = typeParts[2];
			ReactiveTransformerFactory transformerFactory = factorySupplier.apply(operatorName); 
			ReactiveTransformer transformer = transformerFactory.build(xml,sourceSupplier,factorySupplier,mapperSupplier);
			String in = transformer.inType().name().toLowerCase();
			String out = transformer.outType().name().toLowerCase();
			System.err.println("CHAIN: "+in+" / "+baseType+" ---> "+newBaseType+" / "+out);
			result.add(transformer);
		}
		
	}

	public static Optional<ReactiveSource> findSubSource(XMLElement possibleParam,Function<String,ReactiveSourceFactory> sourceSupplier,Function<String,ReactiveTransformerFactory> factorySupplier,Function<String, ReactiveMapper> mapperSupplier) throws Exception {
		Optional<XMLElement> xe = possibleParam.getChildren()
				.stream()
				.filter(x->x.getName().startsWith("source."))
				.findFirst();
		if(!xe.isPresent()) {
			return Optional.empty();
		}
		return Optional.of(parseSource(xe.get(),sourceSupplier, factorySupplier,mapperSupplier));
		
	}

	private static ReactiveSource createSource(XMLElement x, String type, ReactiveParameters params,
		Function<String,ReactiveSourceFactory> sourceFactorySupplier,
		Function<String,ReactiveTransformerFactory> factorySupplier, Function<String, ReactiveMapper> mapperSupplier) throws Exception {

		List<ReactiveTransformer> factories = parseTransformationsFromChildren(x,sourceFactorySupplier, factorySupplier,mapperSupplier);
		ReactiveSourceFactory reactiveSourceFactory = sourceFactorySupplier.apply(type);
//		reactiveSourceFactory.
		Type fn = finalType(reactiveSourceFactory, factories);
		ReactiveSource build = reactiveSourceFactory.build(type,x,params,factories,fn,mapperSupplier);
		System.err.println("Source type: "+build.dataType());
		for (ReactiveTransformer reactiveTransformer : factories) {
			System.err.println("Transformer type: "+reactiveTransformer.outType());
		}
		return build;
	}


	private static Type finalType(ReactiveSourceFactory source, List<ReactiveTransformer> transformers) {
		Type current = source.sourceType();
		for (ReactiveTransformer reactiveTransformer : transformers) {
			if(current != reactiveTransformer.inType()) {
				throw new ClassCastException("Type mismatch: Last type in pipeline: "+current+" next part expects: "+reactiveTransformer.inType());
			}
			current = reactiveTransformer.outType();
		}
		return current;
	}
	
	private static Operand evaluate(String expression, StreamScriptContext context, Optional<ReplicationMessage> m, Optional<ReplicationMessage> param) throws SystemException {
		return Expression.evaluate(expression, context.getInput().orElse(null), null, null,null,null,null,null,m,param);
	}

	public static ReplicationMessage empty() {
		return ReplicationFactory.createReplicationMessage(null, System.currentTimeMillis(), ReplicationMessage.Operation.NONE, Collections.emptyList(), Collections.emptyMap(), Collections.emptyMap(),Collections.emptyMap(),Collections.emptyMap(),Optional.of(()->{}));
	}
	

	public static Function<StreamScriptContext,BiFunction<DataItem,Optional<DataItem>,DataItem>> parseMapperList (List<XMLElement> elements, Function<String, ReactiveMapper> mapperSupplier) {

//		Function<StreamScriptContext,BiFunction<DataItem,Optional<DataItem>,DataItem>>
		List<Function<StreamScriptContext,BiFunction<DataItem,Optional<DataItem>,DataItem>>> funcList = elements.stream()
				.filter(x->!x.getName().startsWith("param"))
				.filter(x->x.getName().split("\\.").length!=3)
				.map(xml->{
					System.err.println("Assuming this is a mapper element: "+xml);
					try {
				return mapperSupplier.apply(xml.getName()).execute(xml);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}).collect(Collectors.toList());
	
		return context->(item,optional)->{
			DataItem current = item;
			for (Function<StreamScriptContext, BiFunction<DataItem, Optional<DataItem>, DataItem>> function : funcList) {
				current = function.apply(context).apply(current, optional);
			}
			return current;
		};
		
	}


}
