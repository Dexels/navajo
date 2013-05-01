package com.dexels.navajo.tipi.css.actions;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;

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
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.css.actions.impl.TipiPropertyHandler;
import com.dexels.navajo.tipi.internal.TipiAction;
import com.dexels.navajo.tipi.internal.TipiEvent;

public class ApplyCss extends TipiAction implements TipiComponentInstantiatedListener{

	private static final long serialVersionUID = 5481600392557969470L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(ApplyCss.class);
	public org.akrogen.tkui.css.tipi.engine.CSSTipiEngineImpl aap;
	
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
				component.getContext().reloadCssDefinitions(component.getName());
			}
			if (!skipMainCss)
			{
				for (String cssDefinition : component.getContext().getCssDefinitions("main"))
				{
					applyCss(component, cssDefinition, null, event);
				}
			}
			for (String cssDefinition : component.getContext().getCssDefinitions(component.getName()))
			{
				applyCss(component, cssDefinition, null, event);
			}
		}
		else
		{
			applyCss(component, styleString, styleResource, event);
		}
	}
	
	public static CSSTipiEngineImpl initializeEngine(TipiComponent component, String styleString, URL styleResource, final TipiEvent event)
	{
		CSSTipiEngineImpl engine = new CSSTipiEngineImpl();
		engine.setErrorHandler(new CSSErrorHandler() {
			@Override
			public void error(Exception e) {
				logger.error("Error: ",e);
			}
		});
		//		String style = "JLabel {uppercase:true}";
//		long mark = System.currentTimeMillis();
		try {
			engine.registerCSSPropertyHandlerProvider(new ICSSPropertyHandlerProvider() {
				
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
			});
			if(styleResource!=null) {
				InputStream is = styleResource.openStream();
				engine.parseStyleSheet(is);
				is.close();
			}
			if(styleString!=null) {
				engine.parseStyleSheet(new StringReader(styleString));
			}
			return engine;
		} catch (IOException e) {
			logger.error("Error: ",e);
		}
		return null;
	}
		
	public static void applyCss(TipiComponent component, String styleString, URL styleResource, final TipiEvent event)
	{
		CSSTipiEngineImpl engine = initializeEngine(component, styleString, styleResource, event);
		if (engine != null)
		{
//			long afterparse = System.currentTimeMillis();
			try
			{
				engine.applyStyles(engine.getElement(component), true);
			}
			catch(UnsupportedOperationException uoe)
			{
				logger.warn("Registering exception, but continuing: ", uoe);
			}
//			long afterapply = System.currentTimeMillis();
//			logTime((afterparse-mark),(afterapply-afterparse));

			engine.dispose();
		}
	}

	public void doComponentInstantiated(TipiComponent tc) {
		if (tc.getName() != null)
		{
			for (String cssDefinition : tc.getContext().getCssDefinitions("main"))
			{
				applyCss(tc, cssDefinition, null, null);
			}
			for (String cssDefinition : tc.getContext().getCssDefinitions(tc.getName()))
			{
				applyCss(tc, cssDefinition, null, null);
			}
		}
	}
	@Override
	public void componentInstantiated(final TipiComponent tc){
		getContext().runSyncInEventThread(new Runnable(){

			@Override
			public void run() {
				doComponentInstantiated(tc);
				
			}});

		
	}
}
