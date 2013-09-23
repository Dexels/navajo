package com.dexels.navajo.tipi.css.actions;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.akrogen.tkui.css.core.dom.properties.ICSSPropertyHandler;
import org.akrogen.tkui.css.core.dom.properties.ICSSPropertyHandlerProvider;
import org.akrogen.tkui.css.core.engine.CSSEngine;
import org.akrogen.tkui.css.core.engine.CSSErrorHandler;
import org.akrogen.tkui.css.tipi.engine.CSSTipiEngineImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.css.CSSStyleDeclaration;

import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiComponent;
import com.dexels.navajo.tipi.TipiComponentInstantiatedListener;
import com.dexels.navajo.tipi.TipiComponentTransformer;
import com.dexels.navajo.tipi.TipiContext;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.css.actions.impl.TipiPropertyHandler;
import com.dexels.navajo.tipi.internal.TipiAction;
import com.dexels.navajo.tipi.internal.TipiEvent;

public class CopyOfApplyCss extends TipiAction implements TipiComponentInstantiatedListener{

	private static final long serialVersionUID = 5481600392557969470L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(CopyOfApplyCss.class);
	public org.akrogen.tkui.css.tipi.engine.CSSTipiEngineImpl aap;
	
	private static int withString = 0;
	private static int withOutString = 0;
	
	@Override
	protected void execute(final TipiEvent event) throws TipiBreakException, TipiException {
		getContext().runSyncInEventThread(new Runnable(){

			@Override
			public void run() {
				doExecute(event);
				
			}});

		
	}
	private void doExecute(final TipiEvent event) {
		String styleString = (String) getEvaluatedParameterValue("style", event);
		TipiComponent component = (TipiComponent) getEvaluatedParameterValue("component", event);
		URL styleResource = (URL) getEvaluatedParameterValue("styleSheet", event);
		if (styleString==null) {
			withOutString++;
		} else {
			withString++;
		}
		logger.warn("CSS: With : "+withString+" without: "+withOutString);
		if (styleString == null && styleResource == null)
		{
			Boolean forceReloadCssDefinition = (Boolean) getEvaluatedParameterValue("forceReloadCssDefinition", event);
			if (forceReloadCssDefinition == null)
			{
				forceReloadCssDefinition = false;
			}
			Boolean skipMainCss = (Boolean) getEvaluatedParameterValue("skipMainCss", event);
			if (skipMainCss == null)
			{
				skipMainCss = false;
			}
			if (forceReloadCssDefinition)
			{
				component.getContext().reloadCssDefinitions("main");
				component.getContext().reloadCssDefinitions(getHomeDefinitionName(component));
			}
			if (!skipMainCss)
			{
				for (String cssDefinition : component.getContext().getCssDefinitions("main"))
				{
					applyCss(component, cssDefinition, null, event);
				}
			}
			for (String cssDefinition : component.getContext().getCssDefinitions(getHomeDefinitionName(component)))
			{
				applyCss(component, cssDefinition, null, event);
			}
		}
		else
		{
			applyCss(component, styleString, styleResource, event);
		}
	}
	
	public static CSSTipiEngineImpl initializeEngine(URL styleResource) {
		CSSTipiEngineImpl engine = new CSSTipiEngineImpl();
		engine.setErrorHandler(new CSSErrorHandler() {
			@Override
			public void error(Exception e) {
				logger.error("CSS Error: ",e);
			}
		});
		try {
			if(styleResource!=null) {
				InputStream is = styleResource.openStream();
				engine.parseStyleSheet(is);
				is.close();
			}
			return engine;
		} catch (IOException e) {
			logger.error("Error: ",e);
		}
		return null;
	}

	public static CSSTipiEngineImpl initializeEngine(String style, URL styleResource, final TipiEvent event) {
		List<String> styles = new ArrayList<String>();
		return initializeEngine(styles, styleResource, event);
	}
	public static CSSTipiEngineImpl initializeEngine(List<String> styles, URL styleResource, final TipiEvent event) {
		CSSTipiEngineImpl engine = new CSSTipiEngineImpl();
		engine.setErrorHandler(new CSSErrorHandler() {
			@Override
			public void error(Exception e) {
				logger.error("CSS Error: ",e);
			}
		});
		//		String style = "JLabel {uppercase:true}";
//		long mark = System.currentTimeMillis();
		try {
			engine.registerCSSPropertyHandlerProvider(createPropertyHandler(event));
			if(styleResource!=null) {
				InputStream is = styleResource.openStream();
				engine.parseStyleSheet(is);
				is.close();
			}
			if(styles!=null) {
				for (String styleString : styles) {
					engine.parseStyleSheet(new StringReader(styleString));
				}
			}
			return engine;
		} catch (IOException e) {
			logger.error("Error: ",e);
		}
		return null;
	}
	
	
	private static ICSSPropertyHandlerProvider createPropertyHandler(
			final TipiEvent event) {
		return new ICSSPropertyHandlerProvider() {
			
			@Override
			public CSSStyleDeclaration getDefaultCSSStyleDeclaration(CSSEngine engine,
					Object element, CSSStyleDeclaration newStyle, String pseudoE)
					throws Exception {
				return null;
			}
			
			@SuppressWarnings("rawtypes")
			@Override
			public Collection getCSSPropertyHandlers(String property) throws Exception {
				Collection<ICSSPropertyHandler> c = new ArrayList<ICSSPropertyHandler>();
				c.add(new TipiPropertyHandler(event));
				return c;
			}
		};
	}

	public static void applyCss(TipiComponent component, String styleString) {
		CSSTipiEngineImpl engine = initializeEngine(styleString, null, null);
		if (engine != null) {
			try {
				engine.applyStyles(engine.getElement(component), true);
			} catch (Throwable uoe) {
				logger.warn("Registering exception, but continuing: ", uoe);
			}
			engine.dispose();
		}
	}
	
	public static void applyCss(TipiComponent component, String styleString, URL styleResource, final TipiEvent event) {
		CSSTipiEngineImpl engine = initializeEngine(styleString, styleResource, event);
		if (engine != null)
		{
//			long afterparse = System.currentTimeMillis();
			try
			{
				engine.applyStyles(engine.getElement(component), true);
			}
			catch(Throwable uoe)
			{
				logger.warn("Registering exception, but continuing: ", uoe);
			}
			engine.dispose();
		}
	}

	public static void applyCss(TipiComponent component, URL styleResource, final TipiEvent event) {
		CSSTipiEngineImpl engine = initializeEngine(styleResource);
		if (engine != null)
		{
			ICSSPropertyHandlerProvider provider = createPropertyHandler(event);
			engine.registerCSSPropertyHandlerProvider(provider);
			try
			{
				engine.applyStyles(engine.getElement(component), true);
			}
			catch(Throwable uoe)
			{
				logger.warn("Registering exception, but continuing: ", uoe);
			}
			engine.unregisterCSSPropertyHandlerProvider(provider);
			engine.dispose();
		}
	}
	
	public static String getHomeDefinitionName(TipiComponent tc)
	{
		if (tc == null)
		{
			return null;
		}
		else if(tc.getHomeComponent() != null)
		{
			return tc.getHomeComponent().getName();
		}
		else
		{
			return tc.getName();
		}
	}

	public void doComponentInstantiated(TipiComponent tc) {
		if (tc.getName() != null)
		{
			final String homeDefinitionName = getHomeDefinitionName(tc);
//			for (String cssDefinition : tc.getContext().getCssDefinitions("main"))
//			{
//				applyCss(tc, cssDefinition);
//			}
//			for (String cssDefinition : tc.getContext().getCssDefinitions(homeDefinitionName))
//			{
//				applyCss(tc, cssDefinition);
//			}
			try {
				TipiComponentTransformer tct = createDefaultEngine(tc.getContext(), homeDefinitionName);
				tct.transform(tc);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private TipiComponentTransformer createDefaultEngine(TipiContext tipiContext, String homeDefinitionName) throws IOException {
//		TipiComponentTransformer tct = tc.getContext().getTipiComponentTransformer(homeDefinitionName);
//		if(tct!=null) {
//			return tct;
//		}
		List<String> cssDefinitions = new ArrayList<String>();
		for (String cssDefinition : tipiContext.getCssDefinitions("main"))
		{
			cssDefinitions.add(cssDefinition);
		}
		for (String cssDefinition : tipiContext.getCssDefinitions(homeDefinitionName))
		{
			cssDefinitions.add(cssDefinition);
		}
		
		
		final CSSTipiEngineImpl engine = new CSSTipiEngineImpl();
		engine.setErrorHandler(new CSSErrorHandler() {
			@Override
			public void error(Exception e) {
				logger.error("CSS Error: ",e);
			}
		});
		for (String def : cssDefinitions) {
			engine.parseStyleDeclaration(def);
		}
		TipiComponentTransformer newTransformer = new TipiComponentTransformer() {
			
			@Override
			public void transform(TipiComponent component) {
				engine.applyStyles(component, true);
			}
		};
		tipiContext.addTipiComponentTransformer(homeDefinitionName, newTransformer);
		return newTransformer;
	}
	@Override
	public void componentInstantiated(final TipiComponent tc){
		try
		{
			//Only apply if this component wants CSS.
			Object applyCss = tc.getValue("applyCss");
			if (applyCss != null && applyCss.toString().equals("true"))
			{
				getContext().runSyncInEventThread(new Runnable(){
					
					@Override
					public void run() {
						doComponentInstantiated(tc);
						
					}});
			}
		}
		catch(UnsupportedOperationException uoe)
		{
			// nothing wrong, applyCss is not a possible value for this TipiComponent
		}
		
	}
}
