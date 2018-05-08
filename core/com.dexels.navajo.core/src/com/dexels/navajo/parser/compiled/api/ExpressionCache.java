package com.dexels.navajo.parser.compiled.api;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.Selection;
import com.dexels.navajo.mapping.MappingUtils;
import com.dexels.navajo.parser.TMLExpressionException;
import com.dexels.navajo.parser.compiled.CompiledParser;
import com.dexels.navajo.parser.compiled.ParseException;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.MappableTreeNode;
import com.dexels.navajo.tipilink.TipiLink;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;


public class ExpressionCache {
	private final static Logger logger = LoggerFactory.getLogger(ExpressionCache.class);
    private static final String DEFAULT_CACHE_SPEC = "maximumSize=20000";

	private static ExpressionCache instance;

    private final LoadingCache<String, Optional<ContextExpression>> expressionCache;
    private final LoadingCache<String, Optional<Object>> expressionValueCache;

	private final AtomicLong hitCount = new AtomicLong();
	private final AtomicLong pureHitCount = new AtomicLong();
	private final AtomicLong parsedCount = new AtomicLong();
	
	public ExpressionCache() {
		expressionCache = CacheBuilder.from(DEFAULT_CACHE_SPEC).build(new CacheLoader<String, Optional<ContextExpression>>() {
			public Optional<ContextExpression> load(String key) {
				// Return empty optional and let the application handle it.
				return Optional.empty();
			}
		});
		
		expressionValueCache = CacheBuilder.from(DEFAULT_CACHE_SPEC).build(new CacheLoader<String, Optional<Object>>() {
			public Optional<Object> load(String key) {
				// Return empty optional and let the application handle it.
				return Optional.empty();
			}
		});
		
		try {
	        Timer time = new Timer(); // Instantiate Timer Object

	        time.schedule(new TimerTask() {

				@Override
				public void run() {
					printStats();
				}}, new Date(), TimeUnit.MINUTES.toMillis(5));
		

		} catch (Throwable e) {
			logger.error("Error: ", e);
		}
	}

	public Object evaluate(String expression,Navajo doc, Message parentMsg, Message parentParamMsg, Selection parentSel,
			 MappableTreeNode mapNode, TipiLink tipiLink, Access access, Optional<ImmutableMessage> immutableMessage, Optional<ImmutableMessage> paramMessage) {
		Optional<Object> cachedValue = expressionValueCache.getUnchecked(expression);
		if(cachedValue.isPresent()) {
			pureHitCount.incrementAndGet();
			return cachedValue.get();
		}
		List<String> problems = new ArrayList<>();
		ContextExpression parse = parse(problems,expression);
		if(!problems.isEmpty()) {
			problems.forEach(problem->
				logger.warn("Compile-time type error when compiling expression: {} -> {}",expression,problem)
			);
			
//			throw new TMLExpressionException(problems,expression);
		}
		return parse.apply(doc, parentMsg, parentParamMsg, parentSel, mapNode, tipiLink,access,immutableMessage,paramMessage);
		
	}
	
	public ContextExpression parse(List<String> problems, String expression) {
		return parse(problems, expression,true);
	}
	public ContextExpression parse(List<String> problems, String expression, boolean allowLiteralResolve) {
		Optional<ContextExpression> cachedParsedExpression = expressionCache.getUnchecked(expression);
		if(cachedParsedExpression.isPresent()) {
			hitCount.incrementAndGet();
			return cachedParsedExpression.get();
		}
		CompiledParser cp;
		try {
			StringReader sr = new StringReader(expression);
			cp = new CompiledParser(sr);
			cp.Expression();
	        ContextExpression parsed = cp.getJJTree().rootNode().interpretToLambda(problems,expression);
	        parsedCount.incrementAndGet();
	        if(parsed.isLiteral() && allowLiteralResolve) {
	        		Object result = parsed.apply(null, null, null, null, null, null, null,null,null);
	        		expressionCache.put(expression, Optional.ofNullable(parsed));
	        		if(result!=null) {
		        		expressionValueCache.put(expression,  Optional.of(result));
	        		}
	        		return new ContextExpression() {
						
						@Override
						public boolean isLiteral() {
							return true;
						}
						
						@Override
						public Object apply(Navajo doc, Message parentMsg, Message parentParamMsg, Selection parentSel,
								 MappableTreeNode mapNode, TipiLink tipiLink, Access access, Optional<ImmutableMessage> immutableMessage, Optional<ImmutableMessage> paramMessage) throws TMLExpressionException {
							return result;
						}

						@Override
						public Optional<String> returnType() {
							return Optional.of(MappingUtils.determineNavajoType(result));
						}
						
						@Override
						public String expression() {
							return expression;
						}
					};
	        } else {
	        		expressionCache.put(expression, Optional.ofNullable(parsed));
	        		return parsed;
	        }
	        
		} catch (ParseException e) {
			throw new TMLExpressionException("Error parsing expression: "+expression, e);
		} catch(Throwable e) {
			throw new TMLExpressionException("Unexpected error parsing expression: "+expression, e);
		}

	}
	
	public void printStats() {
		logger.info("Function cache stats. Value hit: {} expression hit: {} parse count: {} cached expression size: {} cached value size: {}",pureHitCount.get(),hitCount.get(), parsedCount.get(),this.expressionCache.size(),this.expressionValueCache.size());
	}
	
	public static ExpressionCache getInstance() {
		if(instance==null) {
			createInstance();
		}
		return instance;
	}
	
	private synchronized static void createInstance() {
		if(instance==null) {
			instance = new ExpressionCache();
		}
		
	}
	
}
