package com.dexels.navajo.tipi.css.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import org.akrogen.tkui.css.core.dom.properties.ICSSPropertyHandler;
import org.akrogen.tkui.css.core.dom.properties.ICSSPropertyHandlerProvider;
import org.akrogen.tkui.css.core.engine.CSSEngine;
import org.akrogen.tkui.css.core.engine.CSSErrorHandler;
import org.akrogen.tkui.css.tipi.engine.CSSTipiEngineImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.css.CSSStyleDeclaration;

import com.dexels.navajo.tipi.TipiComponent;
import com.dexels.navajo.tipi.TipiComponentInstantiatedListener;
import com.dexels.navajo.tipi.TipiContext;
import com.dexels.navajo.tipi.css.CssApplier;
import com.dexels.navajo.tipi.css.actions.impl.TipiPropertyHandler;
import com.dexels.navajo.tipi.internal.TipiEvent;
import com.dexels.navajo.tipi.locale.LocaleListener;

public class CssComponentResponderImpl implements
		TipiComponentInstantiatedListener, CssApplier {

	private final static Logger logger = LoggerFactory
			.getLogger(CssComponentResponderImpl.class);

	private final TipiContext context;


	private final Map<String, CSSEngine> engineCache = new HashMap<String, CSSEngine>();

	/**
	 * Contains the cache of CSS definitions per tipi component.
	 */
	private final Map<String, List<String>> tipiCssMap = new HashMap<String, List<String>>();

	public CssComponentResponderImpl(TipiContext tc) {
		this.context = tc;
		tc.getApplicationInstance().addLocaleListener(new LocaleListener(){

			@Override
			public void localeChanged(TipiContext context, String language,
					String region) {
				reloadCssDefinitions();
				// TODO Auto-generated method stub
				
			}});
	}

	private void doComponentInstantiated(TipiComponent tc) {
		List<String> definitions = new ArrayList<String>();
		definitions.add("main");
		String home = getHomeDefinitionName(tc);
		if(home!=null) {
			definitions.add(home);
		}
		
		// ----
		for (String def : definitions) {
			for (String defstring : getCssDefinitions(def)) {
				applyCss(tc, defstring, null, null);
			}
		}
//		CSSEngine e = getCssDefinitionEngine(definitions);
//		applyStyleToPreparedEngine((CSSTipiEngineImpl) e, tc, null);

//		for (String def : definitions) {
//			CSSEngine e = getCssDefinitionEngine(def);
//			applyStyleToPreparedEngine((CSSTipiEngineImpl) e, tc, null);
//		}
		

		//		if (tc.getName() != null) {
//			for (String cssDefinition : getCssDefinitions(
//					"main")) {
//				applyCss(tc, cssDefinition, null, null);
//				
//			}
//			for (String cssDefinition : getCssDefinitions(
//					getHomeDefinitionName(tc))) {
//				applyCss(tc, cssDefinition, null, null);
//			}
//		}
	}

	
	@Override
	public List<String> getCssDefinitions(String definition)
	{
		logger.info("Getting definition: "+definition);
		// perhaps not yet cached? Turn this off for now because it is probably a big performance drain. Do return an empty list though (prevent NPE further on)

		if (!tipiCssMap.containsKey(definition))
		{	
			try
			{
				loadCssDefinition(definition, resolveInclude(definition));
			}
			catch(IOException ioe)
			{
				logger.error("Something going wrong loading CSS definitions for component " + definition);
				tipiCssMap.put(definition, new ArrayList<String>());
			}
		}
		return tipiCssMap.get(definition);
	}

	@SuppressWarnings("unused")
	private CSSEngine getCssDefinitionEngine(List<String> definitions)
	{
//		CSSEngine cached = engineCache.get(definitions.toString());
//		if(cached !=null) {
//			return cached;
//		}
		List<String> cssDefList = new ArrayList<String>();
		for (String definition : definitions) {
			// perhaps not yet cached? Turn this off for now because it is probably a big performance drain. Do return an empty list though (prevent NPE further on)
			if (!tipiCssMap.containsKey(definition))
			{
				try
				{
					loadCssDefinition(definition, resolveInclude(definition));
				}
				catch(IOException ioe)
				{
					logger.error("Something going wrong loading CSS definitions for component " + definition);
					tipiCssMap.put(definition, new ArrayList<String>());
				}
			}
			
			final List<String> founddefinitions = tipiCssMap.get(definition);
			cssDefList.addAll(founddefinitions);
			
		}
		System.err.println("# of definitions: "+cssDefList.size());
		for (String string : cssDefList) {
			System.err.println(">>>>>>>>>>>\n>>>>>>>>>>>>\n"+string);
		}
		CSSEngine engine = prepareEngine(cssDefList);
		engineCache.put(definitions.toString(), engine);

		return engine;
	//		initializeEngine(component, styleString, styleResource, event)
//		return definitions;
	}
	private String resolveInclude(String definition) {
		return this.context.resolveInclude(definition);
	}
	
	private List<String> loadCssResources(String baseLocation)
	{
		List<String> cssResources = new ArrayList<String>();

		String result = resolveCssResource(baseLocation + ".css");
		if (result != null && !result.isEmpty())
		{
			cssResources.add(result);
		}
		result = resolveCssResource(baseLocation + "_" + context.getApplicationInstance().getLocaleCode() + ".css");
		if (result != null && !result.isEmpty())
		{
			cssResources.add(result);
		}
		if (context.getApplicationInstance().getSubLocaleCode() != null && !context.getApplicationInstance().getSubLocaleCode().isEmpty())
		{
			result = resolveCssResource(baseLocation + "_" + context.getApplicationInstance().getLocaleCode() + "_" + context.getApplicationInstance().getSubLocaleCode().toLowerCase() + ".css");
			if (result != null && !result.isEmpty())
			{
				cssResources.add(result);
			}
		}
		return cssResources;
	}
	
	private String resolveCssResource(String location) 
	{
//		URL resource = getResourceURL(location);
//		InputStream iss = resource.openStream();
		InputStream iss;
		try {
			iss = context.getGenericResourceStream(location);
		} catch (IOException e1) {
			logger.debug("CSS location not found: "+location);
			return null;
		}

		if (iss != null)
		{
			final Scanner scanner = new Scanner(iss);
			java.util.Scanner s = scanner.useDelimiter("\\A");
			String result = s.hasNext() ? s.next() : "" ;
			try {
				iss.close();
				s.close();
				scanner.close();
			} catch (IOException e) {
				logger.error("Error closing resource, ", e);
			}
		    return result;
		}
		return null;
	}
	
	
	private void loadCssDefinition(String definition, String location) throws IOException
	{
		logger.info("Finding CSS files for definition: " + definition + " with location " + location);
		logger.info("Current locale is " + context.getApplicationInstance().getLocaleCode() + " and current subLocale is " + context.getApplicationInstance().getSubLocaleCode());
		// first try in the same dir as the definition file (ie location)
		String strippedLocation = null;
		if (location != null)
		{
			if(location.lastIndexOf("/") != -1) {
				strippedLocation = location.substring(0, location.lastIndexOf("/") + 1) + definition;
			} else {
				if(location.indexOf('.')!=-1) {
					strippedLocation = location.substring(0,location.lastIndexOf('.'));
				} else {
					strippedLocation = location;
				}
			}
		}
		List<String> cssResources = null; 
		if (strippedLocation != null)
		{
			cssResources = loadCssResources(strippedLocation);

			// then try in the dir we create by replacing tipi by resource/css. Eg, definition is tipi/committee/committeeCommitteeWindow.xml, then we look for resource/css/committee/committeeCommitteeWindow*.css
			// actually location already assumes we start from tipi/ so it is not a replace.
			if (cssResources.isEmpty())
			{
				cssResources = loadCssResources("css/" + strippedLocation);
			}
		}
		// finally try in the base resource/css dir
		if (cssResources == null || cssResources.isEmpty())
		{
			cssResources = loadCssResources("css/" + definition);
			if (cssResources.isEmpty())
			{
				logger.info("Didn't find any CSS resources for definition: " + definition);
			}
		}

		tipiCssMap.put(definition, cssResources);
		// If the "main" definition hasn't been loaded yet, do so now (only if we're not trying to load the main definition. This prevents a possible infinite loop.
		if (definition != null && !definition.equals("main") && !tipiCssMap.containsKey("main"))
		{
			loadCssDefinition("main", null);
		}
	}
	
	/**
	 * Only clears the cache
	 */
	public void clearCssDefinitions()
	{
		Set<String> iterSet = new HashSet<String>(tipiCssMap.keySet());
		for (Iterator<String> iter = iterSet.iterator(); iter.hasNext();) {
			String definitionName = iter.next();
			tipiCssMap.remove(definitionName);
		}
	}
	
	/**
	 * Reloads all current Css definitions. This should happen only when locale and/or sublocale have changed.
	 */
	public void reloadCssDefinitions()
	{
		// it is possible that reloadCssDefinitions results in the addition of "main" to the keySet. This situation results in an NPE without the following precaution
		String[] defKeys = tipiCssMap.keySet().toArray(new String[tipiCssMap.keySet().size()]);
		for (String definition : defKeys)
		{
			reloadCssDefinitions(definition);
		}
		reapplyCss(context.getDefaultTopLevel());
	}
	/**
	 * Reloads all current Css definitions. This should happen only when locale and/or sublocale have changed.
	 */
	@Override
	public void reloadCssDefinitions(String definition)
	{
		try
		{
			loadCssDefinition(definition, context.resolveInclude(definition));
		}
		catch(IOException ioe)
		{
			logger.error("Something going wrong reloading CSS definitions for definition " + definition);
		}
	}	
	
	
	@Override
	public void applyCss(TipiComponent component, String styleString,
			URL styleResource, final TipiEvent event) {
		CSSEngine engine = getEngine(component, styleString,styleResource, event);
		if (engine != null) {
			// long afterparse = System.currentTimeMillis();
			try {
				engine.applyStyles(engine.getElement(component), true);
			} catch (Throwable uoe) {
				logger.warn("Registering exception, but continuing: ", uoe);
			}
			// long afterapply = System.currentTimeMillis();
			// logTime((afterparse-mark),(afterapply-afterparse));

			// engine.dispose();
		}
	}

	private CSSEngine getEngine(TipiComponent component,
			String styleString, URL styleResource, final TipiEvent event) {

//		 CSSTipiEngineImpl engine = engineCache.get(styleString);
//		 if(engine!=null) {
//		 return engine;
//		 }
		CSSTipiEngineImpl engine = initializeEngine(component, styleString,
				styleResource, event);
		engineCache.put(styleString, engine);
		return engine;
	}

	private CSSTipiEngineImpl initializeEngine(TipiComponent component,
			String styleString, URL styleResource, final TipiEvent event) {

		CSSTipiEngineImpl engine = new CSSTipiEngineImpl();
		engine.setErrorHandler(new CSSErrorHandler() {
			@Override
			public void error(Exception e) {
				logger.error("CSS Error: ", e);
			}
		});
		// String style = "JLabel {uppercase:true}";
		// long mark = System.currentTimeMillis();
		try {
			engine.registerCSSPropertyHandlerProvider(new ICSSPropertyHandlerProvider() {

				@Override
				public CSSStyleDeclaration getDefaultCSSStyleDeclaration(
						CSSEngine engine, Object element,
						CSSStyleDeclaration newStyle, String pseudoE)
						throws Exception {
					return null;
				}

				@Override
				public Collection getCSSPropertyHandlers(String property)
						throws Exception {
					Collection<ICSSPropertyHandler> c = new ArrayList<ICSSPropertyHandler>();
					c.add(new TipiPropertyHandler(event));
					return c;
				}
			});
			if (styleResource != null) {
				InputStream is = styleResource.openStream();
				engine.parseStyleSheet(is);
				is.close();
			}
			if (styleString != null) {
				engine.parseStyleSheet(new StringReader(styleString));
			}
			return engine;
		} catch (IOException e) {
			logger.error("Error: ", e);
		}
		return null;
	}

	private CSSTipiEngineImpl prepareEngine(List<String> styleList) {
		CSSTipiEngineImpl engine = new CSSTipiEngineImpl();
		engine.setErrorHandler(new CSSErrorHandler() {
			@Override
			public void error(Exception e) {
				logger.error("CSS Error: ", e);
			}
		});
		// String style = "JLabel {uppercase:true}";
		// long mark = System.currentTimeMillis();
		try {

			for (String styleString : styleList) {
				engine.parseStyleSheet(new StringReader(styleString));
			}
			return engine;
		} catch (IOException e) {
			logger.error("Error: ", e);
		}
		return null;
	}

	@SuppressWarnings("unused")
	private void applyStyleToPreparedEngine(CSSTipiEngineImpl engine, TipiComponent component,final TipiEvent event) {
		final ICSSPropertyHandlerProvider handlerProvider = new ICSSPropertyHandlerProvider() {

			@Override
			public CSSStyleDeclaration getDefaultCSSStyleDeclaration(
					CSSEngine engine, Object element,
					CSSStyleDeclaration newStyle, String pseudoE)
					throws Exception {
				return null;
			}

			@Override
			public Collection getCSSPropertyHandlers(String property)
					throws Exception {
				Collection<ICSSPropertyHandler> c = new ArrayList<ICSSPropertyHandler>();
				c.add(new TipiPropertyHandler(event));
				return c;
			}
		};
		engine.registerCSSPropertyHandlerProvider(handlerProvider);
		
		// apply:
		try {
			engine.applyStyles(engine.getElement(component), true);
		} catch (Throwable uoe) {
			logger.warn("Registering exception, but continuing: ", uoe);
		}
		
		//engine.unregisterCSSPropertyHandlerProvider(handlerProvider);
	}
	
	@Override
	public void componentInstantiated(final TipiComponent tc) {
		try {
			// Only apply if this component wants CSS.
			Object applyCss = tc.getValue("applyCss");
			if (applyCss != null && applyCss.toString().equals("true")) {
				context.runSyncInEventThread(new Runnable() {
					@Override
					public void run() {
						doComponentInstantiated(tc);

					}
				});
			}
		} catch (UnsupportedOperationException uoe) {
			// nothing wrong, applyCss is not a possible value for this
			// TipiComponent
		}

	}

	private String getHomeDefinitionName(TipiComponent tc) {
		if (tc == null) {
			return null;
		} else if (tc.getHomeComponent() != null) {
			return tc.getHomeComponent().getName();
		} else {
			return tc.getName();
		}
	}
	private void reapplyCss(TipiComponent tc)
	{
		if (tc.isHomeComponent())
		{
			// slight hack, will result in CSS reapplying. 
			// If we are going to use componentInstantiated for something else, perhaps a differentiation mechanism should be made.
			context.fireComponentInstantiated(tc);
		}
		for (TipiComponent child : tc.getChildren())
		{
			reapplyCss(child);
		}
	}
}
