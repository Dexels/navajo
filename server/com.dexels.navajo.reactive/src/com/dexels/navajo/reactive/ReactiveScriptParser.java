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
import java.util.Stack;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.immutable.api.ImmutableMessage;
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
import com.dexels.navajo.document.stream.StreamDocument;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.mapping.MappingUtils;
import com.dexels.navajo.parser.TMLExpressionException;
import com.dexels.navajo.parser.compiled.TokenMgrError;
import com.dexels.navajo.parser.compiled.api.ContextExpression;
import com.dexels.navajo.parser.compiled.api.ExpressionCache;
import com.dexels.navajo.reactive.api.ParameterValidator;
import com.dexels.navajo.reactive.api.ReactiveMerger;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveParseException;
import com.dexels.navajo.reactive.api.ReactiveSource;
import com.dexels.navajo.reactive.api.ReactiveSourceFactory;
import com.dexels.navajo.reactive.api.ReactiveTransformer;
import com.dexels.navajo.reactive.api.ReactiveTransformerFactory;
import com.dexels.navajo.reactive.api.StandardTransformerMetadata;
import com.dexels.navajo.reactive.api.TransformerMetadata;
import com.dexels.navajo.reactive.mappers.Delete;
import com.dexels.navajo.reactive.mappers.DeleteSubMessage;
import com.dexels.navajo.reactive.mappers.JsonFileAppender;
import com.dexels.navajo.reactive.mappers.Log;
import com.dexels.navajo.reactive.mappers.LogState;
import com.dexels.navajo.reactive.mappers.Rename;
import com.dexels.navajo.reactive.mappers.SetSingle;
import com.dexels.navajo.reactive.mappers.SetSingleKeyValue;
import com.dexels.navajo.reactive.mappers.Store;
import com.dexels.navajo.reactive.mappers.StoreAsSubMessage;
import com.dexels.navajo.reactive.mappers.StoreAsSubMessageList;
import com.dexels.navajo.reactive.mappers.StoreSingle;
import com.dexels.navajo.reactive.mappers.ToSubMessage;
import com.dexels.navajo.reactive.transformer.single.SingleMessageTransformer;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Function3;

public class ReactiveScriptParser {

	
	private final static Logger logger = LoggerFactory.getLogger(ReactiveScriptParser.class);

	private final Map<String,ReactiveSourceFactory> factories = new HashMap<>();
	private final Map<String, ReactiveTransformerFactory> reactiveOperatorFactory = new HashMap<>();
	private final Map<String,ReactiveMerger> reactiveReducer = new HashMap<>();

	private enum TagType {
		TRANSFORMER,
		MAPPER, UNKNOWN
	}
	public ReactiveScriptParser() {
		reactiveReducer.put("set", new SetSingle());
		reactiveReducer.put("setkv", new SetSingleKeyValue());
		reactiveReducer.put("toSubMessage", new ToSubMessage());
		reactiveReducer.put("delete", new Delete());
		reactiveReducer.put("deleteAll", new DeleteSubMessage());
		reactiveReducer.put("rename", new Rename());
		reactiveReducer.put("dump", new JsonFileAppender());
		reactiveReducer.put("log", new Log());
		reactiveReducer.put("logState", new LogState());
		reactiveReducer.put("saveall", new Store());
		reactiveReducer.put("save", new StoreSingle());
		reactiveReducer.put("store", new StoreAsSubMessage());
		reactiveReducer.put("storeList", new StoreAsSubMessageList());
		
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
	
	ReactiveScript parse(String serviceName, InputStream in, String relativePath, Optional<Type> desiredType) throws UnsupportedEncodingException, IOException {
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
		int parallel = x.getIntAttribute("parallel",1);
		String methodsString = x.getStringAttribute("methods","");
		final List<String> methods = methodsString.equals("") ? Collections.emptyList() : Arrays.asList(methodsString.split(","));
		Optional<String> mime = Optional.ofNullable(x.getStringAttribute("mime"));
		List<ReactiveSource> r = parseRoot(x,relativePath,problems,desiredType);
		Type scriptType = null;
		
		boolean hasInputSource = r.stream().anyMatch(e->e.streamInput());
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
		logger.info("Final source type: "+finalType);
		if(!problems.isEmpty()) {
			throw new ReactiveParseException("Parse problems in script: "+serviceName,problems);
		}
		return new ReactiveScript() {
			
			@Override
			public Flowable<DataItem> execute(StreamScriptContext context) {
//				StreamScriptContext resolvedContext = !hasInputSource ? context.resolveInput() : context;
				boolean isTml = finalType.equals(Type.EVENT);

				Flowable<DataItem> flow;
				try {
					flow = parallel > 1 ?
							Flowable.fromIterable(r)
							.concatMapEager(r->r.execute(context, Optional.empty()),parallel,1)
						: 
							Flowable.fromIterable(r)
							.concatMap(r->r.execute(context, Optional.empty()));
					flow = flow.doOnSubscribe(s->context.logEvent("source starting"))
							.doOnComplete(()->context.logEvent("source completed"));
					if(isTml) {
						// Omit password from response
						flow = flow.compose(StreamDocument.inNavajoDataItem(context.getService(),context.getUsername(),"",methods));
					}
					return flow;
				} catch (Throwable e) {
					logger.error("Error parsing: ", e);
					return Flowable.error(e);
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
			public List<ReactiveParseProblem> problems() {
				return problems;
			}

			@Override
			public boolean streamInput() {
				return hasInputSource;
			}

		};
	}

	public ReactiveTransformerFactory getTransformerFactory(String name, Type inferType, List<ReactiveParseProblem> problems, XMLElement xx, String relativePath) {
		ReactiveTransformerFactory base = this.reactiveOperatorFactory.get(name);
		
		Set<Type> baseAllowed = base.inType();
		if(!baseAllowed.contains(inferType)) {
			problems.add(ReactiveParseProblem.of("Mismatched transformer type: "+inferType+" for transformer: "+name+" in mismatched. It only accepts: "+baseAllowed)
					.withTag(xx)
					.withRelativePath(relativePath)
			);
			return null;
		}
		return base;
	}

	// TODO find a smoother way, without exceptions
	private static TagType inferType(XMLElement xml, String tag, ReactiveBuildContext buildContext, List<ReactiveParseProblem> problems) {
		boolean isTransformer = buildContext.transformers.contains(tag);
		if(isTransformer) {
			return TagType.TRANSFORMER;
		}
		boolean isMapper = buildContext.reducers.contains(tag);
		if(isMapper) {
			return TagType.MAPPER;
		}
		problems.add(ReactiveParseProblem.of("Type inference problem, unidentified tag: "+tag).withTag(xml));
		return TagType.UNKNOWN;
	}
	private List<ReactiveSource> parseRoot(XMLElement x, String relativePath,List<ReactiveParseProblem> problems, Optional<Type> desiredType) {
		List<XMLElement> sourceTags = x.getName().startsWith("source.") ? Arrays.asList(new XMLElement[] {x}) : x.getChildren();
		boolean streamInput = sourceTags.stream().anyMatch(xe->xe.getName().startsWith("source.input"));
		List<ReactiveSource> r =sourceTags
				.stream()
				.map(xx->{
					ReactiveBuildContext buildContext = ReactiveBuildContext.of(operatorName->{ 
						ReactiveSourceFactory src = this.factories.get(operatorName);
						if(src==null) {
							String msg = "Missing source for factory: "+operatorName+" available sources: "+factories.keySet();
							ReactiveParseProblem rpp = ReactiveParseProblem.of(msg).withTag(xx).withRelativePath(relativePath);
							problems.add(rpp);
						}
						return src;
					}, (operatorName,type)->{
						// Inject type TODO
//						ReactiveTransformerFactory transformer = this.reactiveOperatorFactory.get(operatorName);
						ReactiveTransformerFactory transformer = getTransformerFactory(operatorName, type, problems, xx,relativePath);
						
						if(transformer==null) {
							String msg = "Missing transformer for factory: "+operatorName;
							ReactiveParseProblem rpp = ReactiveParseProblem.of(msg).withTag(xx).withRelativePath(relativePath);
							problems.add(rpp);
							throw new RuntimeException(msg);
						}
						return transformer;
					}, operatorName->{
						ReactiveMerger mpr = this.reactiveReducer.get(operatorName);
						if(mpr==null) {
							String msg = "Missing mapper for factory: "+operatorName;
							ReactiveParseProblem rpp = ReactiveParseProblem.of(msg).withTag(xx).withRelativePath(relativePath);
							problems.add(rpp);
							throw new RuntimeException(msg);
						}
						return mpr;
					}, reactiveOperatorFactory.keySet(),
	reactiveReducer.keySet()
	, !streamInput);
					try {
						return parseSource(relativePath, xx,problems,buildContext,desiredType);
					} catch (Exception e) {
						throw new RuntimeException("Major source parsing issue.", e);
					}
				})
				.collect(Collectors.toList());
		
		return r;
	}

	private static Operand evaluateCompiledExpression(ContextExpression ctx, StreamScriptContext context,Map<String,Object> params, Optional<ImmutableMessage> immutableMessage, Optional<ImmutableMessage> paramMessage, boolean streamInput)  {
		try {
			Navajo inMessage = streamInput ? null : context.resolvedNavajo();
			Object val =ctx.apply(inMessage, null, null, null, null, null, null,immutableMessage,paramMessage);
			String type = MappingUtils.determineNavajoType(val);
			return new Operand(val, type, "");
		} catch (Throwable e) {
			throw new TMLExpressionException("Materializing expression failed, expression: "+ctx.expression(),e);
		}
	}

	private static Optional<String> evaluateKey(String key) {
		if(key.endsWith(".eval")) {
			return Optional.of(key.substring(0, key.length()-".eval".length()));
		} else if (key.endsWith(":")) {
			return Optional.of(key.substring(0, key.length()-":".length()));
		}
		return Optional.empty();
	}
	
	public static ReactiveParameters parseParamsFromChildren(String relativePath, List<ReactiveParseProblem> problems, Optional<XMLElement> sourceElement, ParameterValidator validator, boolean streamInput) {
		Map<String,Function3<StreamScriptContext,Optional<ImmutableMessage>,ImmutableMessage,Operand>> namedParameters = new HashMap<>();
		List<Function3<StreamScriptContext,Optional<ImmutableMessage>,ImmutableMessage,Operand>> unnamedParameters = new ArrayList<>();
		if(!sourceElement.isPresent()) {
			ReactiveParameters.of(namedParameters, unnamedParameters);
		}
		XMLElement x = sourceElement.orElse(new XMLElement());
		List<XMLElement> children = x.getChildren();
		x.enumerateAttributeNames().forEachRemaining(e->{
			Optional<Map<String, String>> parameterTypes = validator.parameterTypes();
			Optional<String> evaluateKey = evaluateKey(e);
			if(evaluateKey.isPresent()) {
				String name = evaluateKey.get();
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
					}
					
					Function3<StreamScriptContext,Optional<ImmutableMessage>, ImmutableMessage, Operand> value = (context,msg,param)->evaluateCompiledExpression(ce, context, Collections.emptyMap(), msg,Optional.of(param), streamInput);
					namedParameters.put(name, value);
				} catch (TMLExpressionException ex) {
					Throwable cause = ex.getCause();
					int attrStart = x.getAttributeOffset(e);
					int attrEnd = x.getAttributeEndOffset(e);
					int attrLine = x.getAttributeLineNr(e);
					if(cause instanceof TokenMgrError) {
						TokenMgrError te = (TokenMgrError)cause;
						int errorLn = te.errorLine();
						if(errorLn<=0) {
							errorLn = attrLine;
						} else {
							errorLn = attrLine + errorLn;
						}
						int errorCl = te.errorColumn();
						if(errorCl<=0) {
							errorCl = attrStart;
						} else {
							errorCl = attrStart + errorCl;
						}
						
						problems.add(ReactiveParseProblem.of(ex.getMessage()).withCause(ex).withRange(errorLn, errorLn, errorCl, errorCl+1));

						logger.error("TokenManager error found: "+te.getLocalizedMessage(),te);
					} else {
						problems.add(ReactiveParseProblem.of(ex.getMessage()).withCause(ex).withRange(attrLine, attrLine, attrStart, attrEnd));
					}
				}
				// TODO implement default?
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
				Optional<String> evaluateKey = evaluateKey(elementName);
				boolean evaluate = evaluateKey.isPresent();
				String name = possibleParam.getStringAttribute("name");
//				boolean debug = possibleParam.getBooleanAttribute("debug", "true", "false", false);
				String content = possibleParam.getContent();
				if(content==null || "".equals(content)) {
					continue;
				}
				if(name==null) {
					if (evaluate) {
						List<String> probs = new ArrayList<>();
						ContextExpression ce = ExpressionCache.getInstance().parse(probs,content);
						probs.stream().forEach(elt->problems.add(ReactiveParseProblem.of(elt)));
						// TODO should we type check unnamed parameters somehow?
						Function3<StreamScriptContext,Optional<ImmutableMessage>, ImmutableMessage, Operand> value = (context,msg,param)->evaluateCompiledExpression(ce, context, Collections.emptyMap(), msg,Optional.of(param),streamInput);
						
						unnamedParameters.add(value);
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
							
							Function3<StreamScriptContext,Optional<ImmutableMessage>, ImmutableMessage, Operand> value = (context,msg,param)->evaluateCompiledExpression(ce, context, Collections.emptyMap(), msg,Optional.of(param),streamInput);
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
			}
		}
		return ReactiveParameters.of(namedParameters, unnamedParameters);
	}
	
	private static ReactiveSource parseSource(String relativePath, XMLElement x, List<ReactiveParseProblem> problems, ReactiveBuildContext buildContext, Optional<Type> desiredType) throws Exception  {
		String type = x.getName();
		String[] typeSplit = type.split("\\.");
		ReactiveSource src = createSource(relativePath, x,problems,typeSplit[1],buildContext);
		return src;
	}

	public static List<ReactiveTransformer> parseTransformationsFromChildren(Type initialType, String relativePath, List<ReactiveParseProblem> problems, Optional<XMLElement> parent, ReactiveBuildContext buildContext) {
		Stack<Type> typeStack = new Stack<>();
		typeStack.push(initialType);
		return parent.map(p->(List<XMLElement>)p.getChildren())
			.orElse(Collections.emptyList())
			.stream()
			.filter(x->!(x.getName().startsWith("param")) && !(x.getName().startsWith("source")))
			.map(elt->{
				String type = elt.getName();
				return parseTransformerElement(typeStack.peek(),relativePath, problems, elt, type,buildContext,tp->typeStack.push(tp));
				
			}).collect(Collectors.toList());
	}

	private static ReactiveTransformer parseTransformerElement(Type previousType, String relativePath, List<ReactiveParseProblem> problems,  XMLElement xml,String type,ReactiveBuildContext buildContext, Consumer<Type> typeCommit) throws ReactiveParseException {
		try {
			String[] typeParts = type.split("\\.");
			if(typeParts.length == 3) {
				Optional<String> baseType = Optional.of(typeParts[0]);
				String operatorName = typeParts[1];
				Optional<String> newBaseType = Optional.of(typeParts[2]);
				return typeCheckTransformer(previousType, relativePath, problems, xml, typeParts, buildContext, baseType, operatorName, newBaseType,typeCommit);
			} else {
				// a mapper, create an implicit transformer
				if(typeParts.length == 1) {
					TagType inferd = inferType(xml,type, buildContext,problems);
					switch (inferd) {
					case MAPPER:
						List<XMLElement> implicit = Arrays.asList(new XMLElement[]{xml});
						Function<StreamScriptContext,Function<DataItem,DataItem>> joinermapper = ReactiveScriptParser.parseReducerList(relativePath, problems, Optional.of(implicit),buildContext);
						return new SingleMessageTransformer(StandardTransformerMetadata.noParams(new HashSet<>(Arrays.asList(new Type[] {Type.MESSAGE,Type.SINGLEMESSAGE})) , Type.MESSAGE), ReactiveParameters.empty(),joinermapper,Optional.of(xml),relativePath);
					case TRANSFORMER:
//						return typeCheckTransformer(relativePath, problems, xml, typeParts,  Optional.empty(), type, Optional.empty(), useGlobalInput);
						return typeCheckTransformer(previousType, relativePath, problems, xml, typeParts, buildContext, Optional.empty(), type, Optional.empty(),typeCommit);
					case UNKNOWN:
						Function<StreamScriptContext,Function<DataItem,DataItem>> identitymapper = c->i->i;
						return new SingleMessageTransformer(StandardTransformerMetadata.noParams(new HashSet<>(Arrays.asList(new Type[] {Type.MESSAGE,Type.SINGLEMESSAGE})) , Type.MESSAGE), ReactiveParameters.empty(),identitymapper,Optional.of(xml),relativePath);
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

	private static ReactiveTransformer typeCheckTransformer(Type previousType, 
			String relativePath,
			List<ReactiveParseProblem> problems,
			XMLElement xml,
			String[] typeParts,
			ReactiveBuildContext buildContext,
			Optional<String> baseType, String operatorName,
			Optional<String> newBaseType,
			Consumer<Type> typeCommit) throws Exception {
		ReactiveTransformerFactory transformerFactory = buildContext.factorySupplier.apply(operatorName,previousType); 
		
		
		ReactiveParameters parameters = ReactiveScriptParser.parseParamsFromChildren(relativePath, problems,Optional.of(xml),transformerFactory, !buildContext.useGlobalInput);
		ReactiveTransformer transformer = transformerFactory.build(previousType, relativePath, problems, parameters, Optional.of(xml),buildContext);
		TransformerMetadata metadata = transformer.metadata();
		if(metadata==null) {
			problems.add(ReactiveParseProblem.of("Transformer did not return metadata. This isn't allowed.").withTag(xml));
		}
		Set<DataItem.Type> in = metadata.inType();
		Type out = metadata.outType();
		typeCommit.accept(out);
		Type baseParsed = baseType.map(e->DataItem.parseType(e)).orElse(Type.ANY);
		if(!in.contains(baseParsed) && !baseParsed.equals(DataItem.Type.ANY)) {
			throw new ReactiveParseException("Mismatched input for transformer: "+operatorName+" expected: "+in+" but got: "+baseParsed+" at element " +relativePath+" with name: "+String.join(".", typeParts)+" at line: "+xml.getStartLineNr());
		}
		Type newBaseParsed = newBaseType.map(e->DataItem.parseType(e)).orElse(Type.ANY);
		if(!out.equals(newBaseParsed) && !newBaseParsed.equals(DataItem.Type.ANY)) {
			throw new ReactiveParseException("Mismatched output for transformer: "+operatorName+" expected: "+out+" but got: "+newBaseParsed+" at element " +relativePath+" with name: "+String.join(".", typeParts)+" at line: "+xml.getStartLineNr());
		}

		logger.debug("CHAIN: "+in+" / "+baseType+" ---> "+newBaseType+" / "+out);
		return transformer;
	}

	public static Optional<ReactiveSource> findSubSource(String relativePath, XMLElement xml, List<ReactiveParseProblem> problems, ReactiveBuildContext buildContext, Optional<Type> desiredType) throws Exception {
		Optional<XMLElement> xe = xml.getChildren()
				.stream()
				.filter(x->x.getName().startsWith("source."))
				.findFirst();
		if(!xe.isPresent()) {
			throw new RuntimeException("Transformer named: "+xml.getName()+" seenms to expect a source within. There is none."+xml);
		}
		return Optional.of(parseSource(relativePath, xe.get(),problems,buildContext,desiredType));
		
	}

	private static ReactiveSource createSource(String relativePath,
		XMLElement x,
		List<ReactiveParseProblem> problems,
		String type,
		ReactiveBuildContext buildContext
		) throws Exception {

		ReactiveSourceFactory reactiveSourceFactory = buildContext.sourceSupplier.apply(type);
		if(reactiveSourceFactory==null) {
			String msg = "No factory for source type: "+type+" found.";
			problems.add(ReactiveParseProblem.of(msg).withTag(x).withRelativePath(relativePath));
			ReactiveSourceFactory instance = buildContext.sourceSupplier.apply("single");
			if(instance==null) {
				logger.error("Single shouldn't be missing!");
			}
			return instance.build("", "single", problems, Optional.empty(), ReactiveParameters.empty(), Collections.emptyList(), DataItem.Type.MESSAGE, buildContext.reducerSupplier);
//			throw new NullPointerException(msg);
		}
//		reactiveSourceFactory.
		Type t = reactiveSourceFactory.sourceType();
		
		List<ReactiveTransformer> factories = parseTransformationsFromChildren(t,relativePath, problems, Optional.of(x),buildContext);
		factories = inferTypes(t, factories);
		ReactiveParameters params = parseParamsFromChildren(relativePath,problems, Optional.of(x),reactiveSourceFactory,!buildContext.useGlobalInput);

		Type fn = finalType(reactiveSourceFactory, factories,problems);
		ReactiveSource build = reactiveSourceFactory.build(relativePath, type,problems,Optional.of(x),params,factories,fn,buildContext.reducerSupplier);
		logger.debug("Source type: "+reactiveSourceFactory.sourceType());
		for (ReactiveTransformer reactiveTransformer : factories) {
			logger.debug("Transformer type: "+reactiveTransformer.metadata().outType());
		}
		return build;
	}


	private static List<ReactiveTransformer> inferTypes(Type sourceType, List<ReactiveTransformer> fac) {
		List<ReactiveTransformer> result = new ArrayList<>();
		Type current = sourceType;
		for (ReactiveTransformer reactiveTransformer : fac) {
			Set<Type> accepts = reactiveTransformer.metadata().inType();
			Optional<ReactiveTransformer> implicitTransformer = needsImplicitConversion(current, accepts);
			if(implicitTransformer.isPresent()) {
				result.add(implicitTransformer.get());
			}
			result.add(reactiveTransformer);
			current = reactiveTransformer.metadata().outType();
		}
		return result;
	}
	
	private static Optional<ReactiveTransformer> needsImplicitConversion(Type current, Set<Type> accepts) {
		if(accepts.contains(current)) {
			return Optional.empty();
		} else {
			logger.warn("Mismatched types: "+current+" accepted: "+accepts);
			FlowableTransformer<DataItem, DataItem> transformer = accepts.stream().map(e->ImplicitCaster.createTransformer(current, e))
				.filter(e->e.isPresent())
				.findFirst()
				.flatMap(e->e)
				.orElseThrow(()->new ReactiveParseException("Can't seem to implicit cast from "+current+" to any of "+accepts));
			return Optional.of(new ReactiveTransformer() {
				
				@Override
				public Optional<XMLElement> sourceElement() {
					return Optional.empty();
				}
				
				@Override
				public TransformerMetadata metadata() {
					return new TransformerMetadata() {
						
						@Override
						public Optional<List<String>> requiredParameters() {
							return Optional.empty();
						}
						
						@Override
						public Optional<Map<String, String>> parameterTypes() {
							return Optional.empty();
						}
						
						@Override
						public Optional<List<String>> allowedParameters() {
							return Optional.empty();
						}
						
						@Override
						public Type outType() {
							return current;
						}
						
						@Override
						public String name() {
							return "implicit";
						}
						
						@Override
						public Set<Type> inType() {
							return accepts;
						}
					};
				}
				
				@Override
				public FlowableTransformer<DataItem, DataItem> execute(StreamScriptContext context) {
					return transformer;
				}
			})
				
			;
			
		}
	}

	private static Type finalType(ReactiveSourceFactory source, List<ReactiveTransformer> transformers, List<ReactiveParseProblem> problems) {
		Type current = source.sourceType();
		logger.info("Determine source type: "+current);
		int count = 0;
		for (ReactiveTransformer reactiveTransformer : transformers) {
			if(!reactiveTransformer.metadata().inType().contains(current)) {
				
				problems.add(ReactiveParseProblem.of("#"+count+": Type mismatch: Last type in pipeline: "+current+" next part ("+reactiveTransformer.metadata().name()+") expects: "+reactiveTransformer.metadata().inType()).withTag(reactiveTransformer.sourceElement().orElse(new XMLElement())));
			}
			current = reactiveTransformer.metadata().outType();
			count++;
		}
		logger.info("Final type: "+current);
		return current;
	}

	public static Function<StreamScriptContext,Function<DataItem,DataItem>> parseReducerList (String relativePath, List<ReactiveParseProblem> problems, Optional<List<XMLElement>> elements, ReactiveBuildContext buildContext) {
		List<Function<StreamScriptContext,Function<DataItem,DataItem>>> funcList = elements.orElse(Collections.emptyList()).stream()
				.filter(x->!x.getName().startsWith("param"))
				.filter(x->!x.getName().startsWith("source."))
				.filter(x->x.getName().split("\\.").length!=3)
				.map(xml->{
					logger.info("Assuming this is a reducer element: "+xml);
					try {
						ReactiveMerger reducer = buildContext.reducerSupplier.apply(xml.getName());
						ReactiveParameters r = ReactiveScriptParser.parseParamsFromChildren(relativePath, problems, Optional.of(xml),reducer, !buildContext.useGlobalInput);
						return reducer.execute(r,relativePath, Optional.of(xml));
					} catch (Exception e) {
						throw new RuntimeException("I'm genuinely surprised that this happened. Well done.",e);
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
