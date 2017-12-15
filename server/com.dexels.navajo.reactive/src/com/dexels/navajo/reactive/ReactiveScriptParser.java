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

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.immutable.factory.ImmutableFactory;
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
import com.dexels.navajo.reactive.api.ReactiveMerger;
import com.dexels.navajo.reactive.api.ReactiveParameterException;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveSource;
import com.dexels.navajo.reactive.api.ReactiveSourceFactory;
import com.dexels.navajo.reactive.api.ReactiveTransformer;
import com.dexels.navajo.reactive.api.ReactiveTransformerFactory;
import com.dexels.navajo.reactive.mappers.Delete;
import com.dexels.navajo.reactive.mappers.JsonFileAppender;
import com.dexels.navajo.reactive.mappers.Rename;
import com.dexels.navajo.reactive.mappers.SetSingle;
import com.dexels.navajo.reactive.mappers.SetSingleKeyValue;
import com.dexels.navajo.reactive.mappers.ToSubMessage;
import com.dexels.navajo.script.api.SystemException;

import io.reactivex.Flowable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.functions.Function3;

public class ReactiveScriptParser {

	
	private final static Logger logger = LoggerFactory.getLogger(ReactiveScriptParser.class);

	private final Map<String,ReactiveSourceFactory> factories = new HashMap<>();
	private final Map<String,ReactiveTransformerFactory> reactiveOperatorFactory = new HashMap<>();
	private final Map<String,ReactiveMerger> reactiveReducer = new HashMap<>();
	private final Map<String,ReactiveMapper> reactiveMapping = new HashMap<>();
	

	public ReactiveScriptParser() {
		reactiveReducer.put("set", new SetSingle());
		reactiveReducer.put("setkv", new SetSingleKeyValue());
		reactiveReducer.put("toSubMessage", new ToSubMessage());
		reactiveMapping.put("delete", new Delete());
		reactiveMapping.put("rename", new Rename());
		reactiveMapping.put("dump", new JsonFileAppender());
		
	}
	
	public void addReactiveSourceFactory(ReactiveSourceFactory factory, String name) {
		factories.put(name, factory);
	}

	public void addReactiveSourceFactory(ReactiveSourceFactory factory, Map<String,Object> settings) {
		factories.put((String) settings.get("name"), factory);
	}

	public void removeReactiveSourceFactory(ReactiveSourceFactory factory, Map<String,Object> settings) {
		String name = (String) settings.get("name");
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
	
	ReactiveScript parse(String serviceName, InputStream in, String relativePath) throws UnsupportedEncodingException, IOException {
		if(in==null) {
			throw new IOException("Missing service: "+serviceName+" identified by script: "+relativePath);
		}
		XMLElement x = new CaseSensitiveXMLElement();
		x.parseFromStream(in);
		List<ReactiveSource> r = parseRoot(x,relativePath);
		Type scriptType = null;
		for (ReactiveSource reactiveSource : r) {
			if(scriptType!=null) {
				if(reactiveSource.finalType()!=scriptType) {
					throw new IOException("Can't parse script for service: "+serviceName+" as there are different source types: "+reactiveSource.finalType()+" and: "+scriptType);
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

	private List<ReactiveSource> parseRoot(XMLElement x, String relativePath) {
		List<ReactiveSource> r =x.getChildren()
				.stream()
				.map(xx->{
					try {
						return parseSource(relativePath, xx,
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
									ReactiveMerger mpr = this.reactiveReducer.get(operatorName);
									if(mpr==null) {
										ReactiveMapper mapper = this.reactiveMapping.get(operatorName);
										if(mapper==null) {
											throw new RuntimeException("Missing mapper for factory: "+operatorName);
										}
										return new ReactiveMerger() {
											
											@Override
											public Function<StreamScriptContext, BiFunction<DataItem, DataItem, DataItem>> execute(String relativePath, XMLElement xml) {
												return context->(item,coitem)->mapper.execute(relativePath, xml).apply(context).apply(item);
											}
										};
										
									}
									return mpr;
								},
								operatorName->{
									ReactiveMapper mpr = this.reactiveMapping.get(operatorName);
									if(mpr==null) {
										throw new RuntimeException("Missing mapper for factory: "+operatorName);
									}
									return mpr;
								}								
							);
					} catch (Exception e) {
						throw new RuntimeException("Major source parsing issue.", e);
					}
				})
				.collect(Collectors.toList());
		
		return r;
	}

	public static ReactiveParameters parseParamsFromChildren(String relativePath, XMLElement x) {
		List<XMLElement> children = x.getChildren();
		Map<String,Function3<StreamScriptContext,Optional<ImmutableMessage>,Optional<ImmutableMessage>,Operand>> namedParameters = new HashMap<>();
		Map<String,	Function3<StreamScriptContext,Optional<ImmutableMessage>, Optional<ImmutableMessage>, Operand>> defaultExpressions = new HashMap<>();
		List<Function3<StreamScriptContext,Optional<ImmutableMessage>,Optional<ImmutableMessage>,Operand>> unnamedParameters = new ArrayList<>();
		x.enumerateAttributeNames().forEachRemaining(e->{
			if(e.endsWith(".eval")) {
				String name = e.substring(0, e.length()-".eval".length());
				Function3<StreamScriptContext,Optional<ImmutableMessage>, Optional<ImmutableMessage>, Operand> value = (context,msg,param)->evaluate((String)x.getStringAttribute(e), context, msg,param,false);
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
				boolean debug = possibleParam.getBooleanAttribute("debug", "true", "false", false);
				String content = possibleParam.getContent();
				String defaultValue = possibleParam.getStringAttribute("default");
				if(content==null || "".equals(content)) {
					continue;
				}
				if(name==null) {
					if(defaultValue!=null) {
						throw new ReactiveParameterException("No default value is allowed for unnamed parameters in file: "+relativePath+" line: "+possibleParam.getStartLineNr());
					}
					if (evaluate) {
						unnamedParameters.add((context,msg,param)->evaluate((String)content, context, msg,param,debug));
					} else {
						unnamedParameters.add((context,msg,param)->new Operand(content,"string",null));
					}
					
				} else {
					if(evaluate) {
						Function3<StreamScriptContext, Optional<ImmutableMessage>,Optional<ImmutableMessage>, Operand> value = (context,msg,param)->evaluate((String)content, context, msg,param,debug);
						namedParameters.put(name, value);
					} else {
						namedParameters.put(name, (context,msg,param)->new Operand(content,"string",null));
					}
				}
			} else {
				logger.info("Ignoring non-parameter: "+possibleParam.getName());
			}
		}
		return ReactiveParameters.of(namedParameters, unnamedParameters,defaultExpressions);
	}
	
	private static ReactiveSource parseSource(String relativePath, XMLElement x, Function<String,ReactiveSourceFactory> sourceFactorySupplier,Function<String,ReactiveTransformerFactory> factorySupplier,Function<String, ReactiveMerger> reducerSupplier,Function<String, ReactiveMapper> mapperSupplier) throws Exception  {
		String type = x.getName();
		logger.info("Type of source: "+type);
		String[] typeSplit = type.split("\\.");
		ReactiveSource src = createSource(relativePath, x,typeSplit[1],sourceFactorySupplier, factorySupplier, reducerSupplier,mapperSupplier);
		return src;
	}

	private static List<ReactiveTransformer> parseTransformationsFromChildren(String relativePath, XMLElement parent, Function<String,ReactiveSourceFactory> sourceFactorySupplier,
			Function<String,ReactiveTransformerFactory> factorySupplier,Function<String, ReactiveMerger> reducerSupplier,Function<String, ReactiveMapper> mapperSupplier) throws Exception {
		List<ReactiveTransformer> result = new ArrayList<>();
		List<XMLElement> children = parent.getChildren();
		for (XMLElement possibleParam : children) {
			String type = possibleParam.getName();
			String[] typeParts = type.split("\\.");
			
			if(!type.startsWith("param") && !type.startsWith("source")) {
				// so it's a transformer
				
				parseTransformerElement(relativePath, result, possibleParam, typeParts,sourceFactorySupplier,factorySupplier,reducerSupplier, mapperSupplier);
			}
		}

		return result;
	}

	private static void parseTransformerElement(String relativePath, List<ReactiveTransformer> result, XMLElement xml,String[] typeParts,Function<String,ReactiveSourceFactory> sourceSupplier, Function<String,ReactiveTransformerFactory> factorySupplier, Function<String, ReactiveMerger> reducerSupplier, Function<String, ReactiveMapper> mapperSupplier) throws Exception {
		if(typeParts.length == 3) {
			String baseType = typeParts[0];
			String operatorName = typeParts[1];
			String newBaseType = typeParts[2];
			ReactiveTransformerFactory transformerFactory = factorySupplier.apply(operatorName); 
			ReactiveTransformer transformer = transformerFactory.build(relativePath, xml,sourceSupplier,factorySupplier,reducerSupplier, mapperSupplier);
			String in = transformer.inType().toString();
			String out = transformer.outType().name().toLowerCase();
			logger.info("CHAIN: "+in+" / "+baseType+" ---> "+newBaseType+" / "+out);
			result.add(transformer);
		}
		
	}

	public static Optional<ReactiveSource> findSubSource(String relativePath, XMLElement possibleParam,Function<String,ReactiveSourceFactory> sourceSupplier,Function<String,ReactiveTransformerFactory> factorySupplier,Function<String, ReactiveMerger> reducerSupplier,Function<String, ReactiveMapper> mapperSupplier) throws Exception {
		Optional<XMLElement> xe = possibleParam.getChildren()
				.stream()
				.filter(x->x.getName().startsWith("source."))
				.findFirst();
		if(!xe.isPresent()) {
			throw new RuntimeException("No such adapter found in transformer: "+possibleParam);
		}
		return Optional.of(parseSource(relativePath, xe.get(),sourceSupplier, factorySupplier,reducerSupplier,mapperSupplier));
		
	}

	private static ReactiveSource createSource(String relativePath,
		XMLElement x, String type,
		Function<String,ReactiveSourceFactory> sourceFactorySupplier,
		Function<String,ReactiveTransformerFactory> factorySupplier,
		Function<String, ReactiveMerger> reducerSupplier,
		Function<String, ReactiveMapper> mapperSupplier) throws Exception {

		List<ReactiveTransformer> factories = parseTransformationsFromChildren(relativePath, x,sourceFactorySupplier, factorySupplier,reducerSupplier, mapperSupplier);
		ReactiveSourceFactory reactiveSourceFactory = sourceFactorySupplier.apply(type);
		if(reactiveSourceFactory==null) {
			throw new NullPointerException("No factory for source type: "+type+" found.");
		}
//		reactiveSourceFactory.
		ReactiveParameters params = parseParamsFromChildren(relativePath, x);

		Type fn = finalType(reactiveSourceFactory, factories);
		ReactiveSource build = reactiveSourceFactory.build(relativePath, type,x,params,factories,fn,reducerSupplier, mapperSupplier);
		logger.info("Source type: "+build.dataType());
		for (ReactiveTransformer reactiveTransformer : factories) {
			logger.info("Transformer type: "+reactiveTransformer.outType());
		}
		return build;
	}


	private static Type finalType(ReactiveSourceFactory source, List<ReactiveTransformer> transformers) {
		Type current = source.sourceType();
		for (ReactiveTransformer reactiveTransformer : transformers) {
			System.err.println("Checking if allowed in types: "+reactiveTransformer.inType()+" contains: "+current.name());
			if(!reactiveTransformer.inType().contains(current)) {
				throw new ClassCastException("Type mismatch: Last type in pipeline: "+current+" next part expects: "+reactiveTransformer.inType());
			}
			current = reactiveTransformer.outType();
		}
		return current;
	}
	
	private static Operand evaluate(String expression, StreamScriptContext context, Optional<ImmutableMessage> m, Optional<ImmutableMessage> param, boolean debug) throws SystemException {
		if(debug) {
			logger.info("Evaluating expression: {}",expression);
			if(m.isPresent()) {
				logger.info("With message: "+m.get().toFlatString(ImmutableFactory.getInstance()));
			}
			if(param.isPresent()) {
				logger.info("With param message: "+param.get().toFlatString(ImmutableFactory.getInstance()));
			}
		}
		Operand result = Expression.evaluate(expression, context.getInput().orElse(null), null, null,null,null,null,null,m,param);
		if(debug) {
			logger.info("Result type: "+result.type+" value: "+result.value);
		}
		return result;
	}

	public static ImmutableMessage empty() {
		return ImmutableFactory.create(Collections.emptyMap(), Collections.emptyMap());
//		return ReplicationFactory.createReplicationMessage(null, System.currentTimeMillis(), ReplicationMessage.Operation.NONE, Collections.emptyList(), Collections.emptyMap(), Collections.emptyMap(),Collections.emptyMap(),Collections.emptyMap(),Optional.of(()->{}));
	}
	

	public static Function<StreamScriptContext,BiFunction<DataItem,DataItem,DataItem>> parseReducerList (String relativePath, List<XMLElement> elements, Function<String, ReactiveMerger> reducerSupplier) {
		List<Function<StreamScriptContext,BiFunction<DataItem,DataItem,DataItem>>> funcList = elements.stream()
				.filter(x->!x.getName().startsWith("param"))
				.filter(x->x.getName().split("\\.").length!=3)
				.map(xml->{
					logger.info("Assuming this is a reducer element: "+xml);
					try {
						ReactiveMerger reducer = reducerSupplier.apply(xml.getName());
						return reducer.execute(relativePath, xml);
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
			}).collect(Collectors.toList());
		return context->(item,optional)->{
			DataItem current = item;
			for (Function<StreamScriptContext, BiFunction<DataItem, DataItem, DataItem>> function : funcList) {
				current = function.apply(context).apply(current, optional);
			}
			return current;
		};
		
	}
	
	public static Function<StreamScriptContext,Function<DataItem,DataItem>> parseMapperList (String relativePath, List<XMLElement> elements, Function<String, ReactiveMapper> mapperSupplier) {

//		Function<StreamScriptContext,BiFunction<DataItem,Optional<DataItem>,DataItem>>
		List<Function<StreamScriptContext,Function<DataItem,DataItem>>> funcList = elements.stream()
				.filter(x->!x.getName().startsWith("param"))
				.filter(x->x.getName().split("\\.").length!=3)
				.map(xml->{
					logger.info("Assuming this is a mapper element: "+xml);
					try {
				return mapperSupplier.apply(xml.getName()).execute(relativePath, xml);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}).collect(Collectors.toList());
	
		return context->(item)->{
			DataItem current = item;
			for (Function<StreamScriptContext, Function<DataItem, DataItem>> function : funcList) {
				current = function.apply(context).apply(current);
			}
			return current;
		};
		
	}


}
