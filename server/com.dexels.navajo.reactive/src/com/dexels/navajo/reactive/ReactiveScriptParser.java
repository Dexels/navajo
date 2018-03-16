package com.dexels.navajo.reactive;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.immutable.factory.ImmutableFactory;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.Operand;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.nanoimpl.CaseSensitiveXMLElement;
import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.document.nanoimpl.XMLParseException;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.DataItem.Type;
import com.dexels.navajo.document.stream.ReactiveParseProblem;
import com.dexels.navajo.document.stream.ReactiveScript;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.mapping.MappingUtils;
import com.dexels.navajo.parser.Expression;
import com.dexels.navajo.parser.TMLExpressionException;
import com.dexels.navajo.parser.compiled.api.ContextExpression;
import com.dexels.navajo.parser.compiled.api.ExpressionCache;
import com.dexels.navajo.reactive.api.ParameterValidator;
import com.dexels.navajo.reactive.api.ReactiveMerger;
import com.dexels.navajo.reactive.api.ReactiveParameterException;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveParseException;
import com.dexels.navajo.reactive.api.ReactiveSource;
import com.dexels.navajo.reactive.api.ReactiveSourceFactory;
import com.dexels.navajo.reactive.api.ReactiveTransformer;
import com.dexels.navajo.reactive.api.ReactiveTransformerFactory;
import com.dexels.navajo.reactive.api.StandardTransformerMetadata;
import com.dexels.navajo.reactive.mappers.Delete;
import com.dexels.navajo.reactive.mappers.DeleteSubMessage;
import com.dexels.navajo.reactive.mappers.JsonFileAppender;
import com.dexels.navajo.reactive.mappers.Rename;
import com.dexels.navajo.reactive.mappers.SetSingle;
import com.dexels.navajo.reactive.mappers.SetSingleKeyValue;
import com.dexels.navajo.reactive.mappers.Store;
import com.dexels.navajo.reactive.mappers.StoreAsSubMessage;
import com.dexels.navajo.reactive.mappers.StoreSingle;
import com.dexels.navajo.reactive.mappers.ToSubMessage;
import com.dexels.navajo.reactive.transformer.single.SingleMessageTransformer;
import com.dexels.navajo.script.api.SystemException;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Function3;

public class ReactiveScriptParser {

	
	private final static Logger logger = LoggerFactory.getLogger(ReactiveScriptParser.class);

	private final Map<String,ReactiveSourceFactory> factories = new HashMap<>();
	private final Map<String,ReactiveTransformerFactory> reactiveOperatorFactory = new HashMap<>();
	private final Map<String,ReactiveMerger> reactiveReducer = new HashMap<>();

	private enum TagType {
		TRANSFORMER,
		MAPPER
	}
	public ReactiveScriptParser() {
		reactiveReducer.put("set", new SetSingle());
		reactiveReducer.put("setkv", new SetSingleKeyValue());
		reactiveReducer.put("toSubMessage", new ToSubMessage());
		reactiveReducer.put("delete", new Delete());
		reactiveReducer.put("deleteAll", new DeleteSubMessage());
		reactiveReducer.put("rename", new Rename());
		reactiveReducer.put("dump", new JsonFileAppender());
		reactiveReducer.put("saveall", new Store());
		reactiveReducer.put("save", new StoreSingle());
		reactiveReducer.put("store", new StoreAsSubMessage());
		
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
		List<ReactiveParseProblem> problems = new ArrayList<>();
		if(in==null) {
			throw new IOException("Missing service: "+serviceName+" identified by script: "+relativePath);
		}
		XMLElement x = new CaseSensitiveXMLElement();
		try {
			x.parseFromStream(in);
		} catch (XMLParseException e) {
			ReactiveParseProblem rpp = ReactiveParseProblem.of("XML parse issue").withLocation(e.getLineNr(),e.getOffset());
			problems.add(rpp);
			e.printStackTrace();
		}
//		parseParamsFromChildren("", sourceElement)
		int parallel = x.getIntAttribute("parallel",1);
		final Optional<String> streamMessage = Optional.ofNullable(x.getStringAttribute("streamMessage"));

		Optional<String> mime = Optional.ofNullable(x.getStringAttribute("mime"));
		List<ReactiveSource> r = parseRoot(x,relativePath,problems);
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
		
		if(!problems.isEmpty()) {
			throw new ReactiveParseException("Parse problems in script: "+serviceName,problems);
		}
		return new ReactiveScript() {
			
			@Override
			public Flowable<DataItem> execute(StreamScriptContext context) {
				StreamScriptContext resolvedContext = streamMessage.isPresent() ? context.resolveInput() : context;
				if(parallel > 1) {
					return Flowable.fromIterable(r)
							.concatMapEager(r->r.execute(resolvedContext, Optional.empty()),parallel,1);
				} else {
					return Flowable.fromIterable(r)
							.concatMap(r->r.execute(resolvedContext, Optional.empty()));
				}
			}

			@Override
			public Type dataType() {
				return finalType;
			}

			@Override
			public Optional<String> binaryMimeType() {
				return mime;
			}

			@Override
			public Optional<String> streamMessage() {
				return streamMessage;
			}

			@Override
			public List<ReactiveParseProblem> problems() {
				return problems;
			}
		};
	}

	// TODO find a smoother way, without exceptions
	private static TagType inferType(String tag, Function<String,ReactiveTransformerFactory> transformerSupplier,Function<String, ReactiveMerger> reducerSupplier) {
		try {
			ReactiveTransformerFactory rf = transformerSupplier.apply(tag);
			return TagType.TRANSFORMER;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return TagType.MAPPER;
	}
	private List<ReactiveSource> parseRoot(XMLElement x, String relativePath,List<ReactiveParseProblem> problems) {
		List<XMLElement> sourceTags = x.getName().startsWith("source.") ? Arrays.asList(new XMLElement[] {x}) : x.getChildren();
		List<ReactiveSource> r =sourceTags
				.stream()
				.map(xx->{
					try {
						return parseSource(relativePath, xx,
								problems,
								operatorName->{ 
									ReactiveSourceFactory src = this.factories.get(operatorName);
									if(src==null) {
										String msg = "Missing source for factory: "+operatorName;
										ReactiveParseProblem rpp = ReactiveParseProblem.of(msg).withTag(xx).withRelativePath(relativePath);
										problems.add(rpp);
										throw new RuntimeException(msg);
									}
									return src;
								},
								operatorName->{
									ReactiveTransformerFactory transformer = this.reactiveOperatorFactory.get(operatorName);
									if(transformer==null) {
										String msg = "Missing transformer for factory: "+operatorName;
										ReactiveParseProblem rpp = ReactiveParseProblem.of(msg).withTag(xx).withRelativePath(relativePath);
										problems.add(rpp);
										throw new RuntimeException(msg);
									}
									return transformer;
								},
								operatorName->{
									ReactiveMerger mpr = this.reactiveReducer.get(operatorName);
									if(mpr==null) {
										String msg = "Missing mapper for factory: "+operatorName;
										ReactiveParseProblem rpp = ReactiveParseProblem.of(msg).withTag(xx).withRelativePath(relativePath);
										problems.add(rpp);
										throw new RuntimeException(msg);
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

	public static ReactiveParameters parseParamsFromChildren(String relativePath, List<ReactiveParseProblem> problems, Optional<XMLElement> sourceElement, ParameterValidator validator) {
		Map<String,Function3<StreamScriptContext,Optional<ImmutableMessage>,ImmutableMessage,Operand>> namedParameters = new HashMap<>();
		List<Function3<StreamScriptContext,Optional<ImmutableMessage>,ImmutableMessage,Operand>> unnamedParameters = new ArrayList<>();
		if(!sourceElement.isPresent()) {
			ReactiveParameters.of(namedParameters, unnamedParameters);
		}
		XMLElement x = sourceElement.orElse(new XMLElement());
		List<XMLElement> children = x.getChildren();
		x.enumerateAttributeNames().forEachRemaining(e->{
			Optional<Map<String, String>> parameterTypes = validator.parameterTypes();
			if(e.endsWith(".eval")) {
				String name = e.substring(0, e.length()-".eval".length());
				if(validator.allowedParameters().isPresent()) {
					List<String> val = validator.allowedParameters().get();
					if(!val.contains(name)) {
						int offstart = x.getAttributeOffset(e);
						int offend = x.getAttributeEndOffset(e);
						int offline = x.getAttributeLineNr(e);
						problems.add(ReactiveParseProblem.of("Parameter: "+name+" is not allowed in element: "+relativePath).withRange(offline, offline, offstart, offend));
					}
				}
				try {
					List<String> probs = new ArrayList<>();
					ContextExpression ce = ExpressionCache.getInstance().parse(probs,x.getStringAttribute(e));
					probs.stream().forEach(elt->problems.add(ReactiveParseProblem.of(elt)));
					
					if(ce.returnType().isPresent() && parameterTypes.isPresent()) {
						String ret = ce.returnType().get();
						String type = parameterTypes.get().get(name);
						if(type!=null) {
							if(!type.equals(ret)) {
								int offstart = x.getAttributeOffset(e);
								int offend = x.getAttributeEndOffset(e);
								int offline = x.getAttributeLineNr(e);
								problems.add(ReactiveParseProblem.of("Parameter type mismatch for parameter: "+name+" the expression: "+x.getStringAttribute(e)+" evaluates to: "+ret+" while the parameter should be: "+type).withRange(offline, offline, offstart, offend));
							}
						}
//						validator.
					}
					
					Function3<StreamScriptContext,Optional<ImmutableMessage>, ImmutableMessage, Operand> value = (context,msg,param)->evaluateCompiledExpression(ce, context, Collections.emptyMap(), msg,Optional.of(param));
					namedParameters.put(name, value);
				} catch (TMLExpressionException ex) {
					int attrStart = x.getAttributeOffset(e);
					int attrEnd = x.getAttributeEndOffset(e);
					int attrLine = x.getAttributeLineNr(e);
					problems.add(ReactiveParseProblem.of(ex.getMessage()).withRange(attrLine, attrLine, attrStart, attrEnd));
				}
				// todo implement default
			} else if(e.endsWith(".default")) {
				// deprecated?
				String name = e.substring(0, e.length()-".default".length());
				Function3<StreamScriptContext,Optional<ImmutableMessage>, ImmutableMessage, Operand> value = (context,msg,param)->evaluate((String)x.getStringAttribute(e), context, msg,param,false);
				namedParameters.put(name, value);
			} else {
				if(parameterTypes.isPresent()) {
					String type = parameterTypes.get().get(e);

					if(type!=null) {
						if(!type.equals(Property.STRING_PROPERTY)) {
							int offstart = x.getAttributeOffset(e);
							int offend = x.getAttributeEndOffset(e);
							int offline = x.getAttributeLineNr(e);
							problems.add(ReactiveParseProblem.of("Parameter type mismatch for parameter: "+e+" the string literal: "+x.getStringAttribute(e)+" while the parameter expects to be : "+type).withRange(offline, offline, offstart, offend));
						}
					}
				}
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
					
						try {
							List<String> probs = new ArrayList<>();
							ContextExpression ce = ExpressionCache.getInstance().parse(probs,content);
							probs.stream().forEach(elt->problems.add(ReactiveParseProblem.of(elt)));
							if(ce.returnType().isPresent() && validator.parameterTypes().isPresent()) {
								String ret = ce.returnType().get();
								String type = validator.parameterTypes().get().get(name);
								if(type!=null) {
									if(!type.equals(ret)) {
										int offstart = possibleParam.getStartOffset();
										int offend = 0;
										int offStartline = x.getLineNr();
										int offEndline = x.getLineNr();
										problems.add(ReactiveParseProblem.of("Parameter type mismatch for parameter: "+name+" the expression: "+content+" evaluates to: "+ret+" while the parameter should be: "+type).withRange(offStartline, offEndline, offstart, offend));
									}
								}
//								validator.
							}
							
							Function3<StreamScriptContext,Optional<ImmutableMessage>, ImmutableMessage, Operand> value = (context,msg,param)->evaluateCompiledExpression(ce, context, Collections.emptyMap(), msg,Optional.of(param));
							namedParameters.put(name, value);
						} catch (TMLExpressionException ex) {
//							int attrStart = x.getAttributeOffset(e);
//							int attrEnd = x.getAttributeEndOffset(e);
//							int attrLine = x.getAttributeLineNr(e);
							// TODO fix coordinates
							problems.add(ReactiveParseProblem.of(ex.getMessage()));
						}
//						Function3<StreamScriptContext, Optional<ImmutableMessage>,ImmutableMessage, Operand> value = (context,msg,param)->evaluate((String)content, context, msg,param,debug);
//						namedParameters.put(name, value);
					} else {
						if(validator.parameterTypes().isPresent()) {
							String type = validator.parameterTypes().get().get(name);
							if(type!=null) {
								if(!type.equals(Property.STRING_PROPERTY)) {
									int offstart = possibleParam.getStartOffset();
									int offend = 0;
									int offStartline = x.getLineNr();
									int offEndline = x.getLineNr()+1;
									problems.add(ReactiveParseProblem.of("Parameter type mismatch for parameter: "+name+" the string literal: "+content+" while the parameter should be: "+type).withRange(offStartline, offEndline, offstart, offend));
								}
							}
//							validator.
						}
						
						
						namedParameters.put(name, (context,msg,param)->new Operand(content,"string",null));
					}
				}
			} else {
				logger.info("Ignoring non-parameter: "+possibleParam.getName());
			}
		}
		return ReactiveParameters.of(namedParameters, unnamedParameters);
	}
	
	private static ReactiveSource parseSource(String relativePath, XMLElement x, List<ReactiveParseProblem> problems, Function<String,ReactiveSourceFactory> sourceFactorySupplier,Function<String,ReactiveTransformerFactory> factorySupplier,Function<String, ReactiveMerger> reducerSupplier) throws Exception  {
		String type = x.getName();
		logger.info("Type of source: "+type);
		String[] typeSplit = type.split("\\.");
		ReactiveSource src = createSource(relativePath, x,problems,typeSplit[1],sourceFactorySupplier, factorySupplier, reducerSupplier);
		return src;
	}

	private static List<ReactiveTransformer> parseTransformationsFromChildren(String relativePath, List<ReactiveParseProblem> problems, Optional<XMLElement> parent, Function<String,ReactiveSourceFactory> sourceFactorySupplier,
			Function<String,ReactiveTransformerFactory> factorySupplier,Function<String, ReactiveMerger> reducerSupplier) throws Exception {
		return parent.map(p->(List<XMLElement>)p.getChildren())
			.orElse(Collections.emptyList())
			.stream()
			.filter(x->!(x.getName().startsWith("param")) && !(x.getName().startsWith("source")))
			.map(possibleParam->{
				String type = possibleParam.getName();
				return parseTransformerElement(relativePath, problems, possibleParam, type,sourceFactorySupplier,factorySupplier,reducerSupplier);
				
			}).collect(Collectors.toList());
	}

	private static ReactiveTransformer parseTransformerElement(String relativePath, List<ReactiveParseProblem> problems,  XMLElement xml,String type,Function<String,ReactiveSourceFactory> sourceSupplier, Function<String,ReactiveTransformerFactory> factorySupplier, Function<String, ReactiveMerger> reducerSupplier) throws ReactiveParseException {
		try {
			String[] typeParts = type.split("\\.");
			if(typeParts.length == 3) {
				Optional<String> baseType = Optional.of(typeParts[0]);
				String operatorName = typeParts[1];
				Optional<String> newBaseType = Optional.of(typeParts[2]);
				return typeCheckTransformer(relativePath, problems, xml, typeParts, sourceSupplier, factorySupplier,
						reducerSupplier, baseType, operatorName, newBaseType);
			} else {
				// a mapper, create an implicit transformer
				if(typeParts.length == 1) {
					TagType inferd = inferType(type, factorySupplier, reducerSupplier);
					switch (inferd) {
					case MAPPER:
						List<XMLElement> implicit = Arrays.asList(new XMLElement[]{xml});
						Function<StreamScriptContext,Function<DataItem,DataItem>> joinermapper = ReactiveScriptParser.parseReducerList(relativePath, problems, Optional.of(implicit), reducerSupplier);
						return new SingleMessageTransformer(StandardTransformerMetadata.noParams(new HashSet<>(Arrays.asList(new Type[] {Type.MESSAGE,Type.SINGLEMESSAGE})) , Type.MESSAGE), ReactiveParameters.empty(),joinermapper,Optional.of(xml),relativePath);
					case TRANSFORMER:
						return typeCheckTransformer(relativePath, problems, xml, typeParts, sourceSupplier, factorySupplier,
								reducerSupplier, Optional.empty(), type, Optional.empty());
					default:
						break;
					}

				}
				
				throw new ReactiveParseException("Illegal element at path: "+relativePath+" with name: "+type+" at line: "+xml.getStartLineNr());
			}
		} catch (Exception e) {
			throw new ReactiveParseException("Unknown exception. element at path: "+relativePath+" with name: "+String.join(".", type)+" at line: "+xml.getStartLineNr(),e);
		}
		
	}

	private static ReactiveTransformer typeCheckTransformer(String relativePath, List<ReactiveParseProblem> problems, XMLElement xml, String[] typeParts,
			Function<String, ReactiveSourceFactory> sourceSupplier,
			Function<String, ReactiveTransformerFactory> factorySupplier,
			Function<String, ReactiveMerger> reducerSupplier, Optional<String> baseType, String operatorName,
			Optional<String> newBaseType) throws Exception {
		ReactiveTransformerFactory transformerFactory = factorySupplier.apply(operatorName); 
		ReactiveParameters parameters = ReactiveScriptParser.parseParamsFromChildren(relativePath, problems,Optional.of(xml),transformerFactory);

		ReactiveTransformer transformer = transformerFactory.build(relativePath, problems, parameters, Optional.of(xml),sourceSupplier,factorySupplier,reducerSupplier);
		Set<DataItem.Type> in = transformer.metadata().inType();
		Type out = transformer.metadata().outType();
		Type baseParsed = baseType.map(e->DataItem.parseType(e)).orElse(Type.ANY);
		if(!in.contains(baseParsed) && !baseParsed.equals(DataItem.Type.ANY)) {
			throw new ReactiveParseException("Mismatched input for transformer: "+operatorName+" expected: "+in+" but got: "+baseParsed+" at element " +relativePath+" with name: "+String.join(".", typeParts)+" at line: "+xml.getStartLineNr());
		}
		Type newBaseParsed = newBaseType.map(e->DataItem.parseType(e)).orElse(Type.ANY);
		if(!out.equals(newBaseParsed) && !newBaseParsed.equals(DataItem.Type.ANY)) {
			throw new ReactiveParseException("Mismatched output for transformer: "+operatorName+" expected: "+out+" but got: "+newBaseParsed+" at element " +relativePath+" with name: "+String.join(".", typeParts)+" at line: "+xml.getStartLineNr());
		}

		logger.info("CHAIN: "+in+" / "+baseType+" ---> "+newBaseType+" / "+out);
		return transformer;
	}

	public static Optional<ReactiveSource> findSubSource(String relativePath, XMLElement xml, List<ReactiveParseProblem> problems, Function<String,ReactiveSourceFactory> sourceSupplier,Function<String,ReactiveTransformerFactory> factorySupplier,Function<String, ReactiveMerger> reducerSupplier) throws Exception {
		Optional<XMLElement> xe = xml.getChildren()
				.stream()
				.filter(x->x.getName().startsWith("source."))
				.findFirst();
		if(!xe.isPresent()) {
			throw new RuntimeException("Transformer named: "+xml.getName()+" seenms to expect a source within. There is none."+xml);
		}
		return Optional.of(parseSource(relativePath, xe.get(),problems, sourceSupplier, factorySupplier,reducerSupplier));
		
	}

	private static ReactiveSource createSource(String relativePath,
		XMLElement x,
		List<ReactiveParseProblem> problems,
		String type,
		Function<String,ReactiveSourceFactory> sourceFactorySupplier,
		Function<String,ReactiveTransformerFactory> factorySupplier,
		Function<String, ReactiveMerger> reducerSupplier) throws Exception {

		List<ReactiveTransformer> factories = parseTransformationsFromChildren(relativePath, problems, Optional.of(x),sourceFactorySupplier, factorySupplier,reducerSupplier);
		ReactiveSourceFactory reactiveSourceFactory = sourceFactorySupplier.apply(type);
		if(reactiveSourceFactory==null) {
			String msg = "No factory for source type: "+type+" found.";
			problems.add(ReactiveParseProblem.of(msg).withTag(x).withRelativePath(relativePath));
			throw new NullPointerException(msg);
		}
//		reactiveSourceFactory.
		ReactiveParameters params = parseParamsFromChildren(relativePath,problems, Optional.of(x),reactiveSourceFactory);

		Type fn = finalType(reactiveSourceFactory, factories,problems);
		ReactiveSource build = reactiveSourceFactory.build(relativePath, type,problems,Optional.of(x),params,factories,fn,reducerSupplier);
		logger.info("Source type: "+reactiveSourceFactory.sourceType());
		for (ReactiveTransformer reactiveTransformer : factories) {
			logger.info("Transformer type: "+reactiveTransformer.metadata().outType());
		}
		return build;
	}


	private static Type finalType(ReactiveSourceFactory source, List<ReactiveTransformer> transformers, List<ReactiveParseProblem> problems) {
		Type current = source.sourceType();
		for (ReactiveTransformer reactiveTransformer : transformers) {
			System.err.println("Checking if allowed in types: "+reactiveTransformer.metadata().inType()+" contains: "+current.name()+" for transformer: "+reactiveTransformer.getClass().getName());
			if(!reactiveTransformer.metadata().inType().contains(current)) {
				throw new ClassCastException("Type mismatch: Last type in pipeline: "+current+" next part expects: "+reactiveTransformer.metadata().inType());
			}
			current = reactiveTransformer.metadata().outType();
		}
		return current;
	}
	
	// TODO default eval
	private static Operand evaluate(String expression, StreamScriptContext context, Optional<ImmutableMessage> m, ImmutableMessage param, boolean debug) throws SystemException {
		if(debug) {
			logger.info("Evaluating expression: {}",expression);
			if(m.isPresent()) {
				logger.info("With message: "+m.get().toFlatString(ImmutableFactory.getInstance()));
			}
			logger.info("With param message: "+param.toFlatString(ImmutableFactory.getInstance()));
		}
		Operand result = Expression.evaluate(expression, context.getInput().orElse(null), null, null,null,null,null,null,m,Optional.of(param));
		if(debug) {
			logger.info("Result type: "+result.type+" value: "+result.value);
		}
		return result;
	}
	
	private static Operand evaluateCompiledExpression(ContextExpression ctx, StreamScriptContext context,Map<String,Object> params, Optional<ImmutableMessage> immutableMessage, Optional<ImmutableMessage> paramMessage)  {
		Navajo inMessage = context.getInput().orElse(null);
		Object val =ctx.apply(inMessage, null, null, null, null, null, null,immutableMessage,paramMessage);
		String type = MappingUtils.determineNavajoType(val);
		return new Operand(val, type, "");
	}


	public static ImmutableMessage empty() {
		return ImmutableFactory.create(Collections.emptyMap(), Collections.emptyMap());
	}
	

	public static Function<StreamScriptContext,Function<DataItem,DataItem>> parseReducerList (String relativePath, List<ReactiveParseProblem> problems, Optional<List<XMLElement>> elements, Function<String, ReactiveMerger> reducerSupplier) {
		List<Function<StreamScriptContext,Function<DataItem,DataItem>>> funcList = elements.orElse(Collections.emptyList()).stream()
				.filter(x->!x.getName().startsWith("param"))
				.filter(x->!x.getName().startsWith("source."))
				.filter(x->x.getName().split("\\.").length!=3)
				.map(xml->{
					logger.info("Assuming this is a reducer element: "+xml);
					try {
						ReactiveMerger reducer = reducerSupplier.apply(xml.getName());
						ReactiveParameters r = ReactiveScriptParser.parseParamsFromChildren(relativePath, problems, Optional.of(xml),reducer);
						return reducer.execute(r,relativePath, Optional.of(xml));
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
