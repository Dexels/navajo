package com.dexels.navajo.tipi.css.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

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

public class CssComponentResponderImpl implements
		TipiComponentInstantiatedListener, CssApplier {

	private final static Logger logger = LoggerFactory
			.getLogger(CssComponentResponderImpl.class);

	private final TipiContext context;

	private static CssComponentResponderImpl instance = null;

	private final Map<String, CSSTipiEngineImpl> engineCache = new HashMap<String, CSSTipiEngineImpl>();

	public static CssComponentResponderImpl getInstance() {
		return instance;
	}

	public CssComponentResponderImpl(TipiContext tc) {
		this.context = tc;
		instance = this;
	}

	public void doComponentInstantiated(TipiComponent tc) {
		if (tc.getName() != null) {
			for (String cssDefinition : tc.getContext().getCssDefinitions(
					"main")) {
				applyCss(tc, cssDefinition, null, null);
			}
			for (String cssDefinition : tc.getContext().getCssDefinitions(
					getHomeDefinitionName(tc))) {
				applyCss(tc, cssDefinition, null, null);
			}
		}
	}

	@Override
	public void applyCss(TipiComponent component, String styleString,
			URL styleResource, final TipiEvent event) {
		CSSTipiEngineImpl engine = getEngine(component, styleString,styleResource, event);
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

	private CSSTipiEngineImpl getEngine(TipiComponent component,
			String styleString, URL styleResource, final TipiEvent event) {

		// CSSTipiEngineImpl engine = engineCache.get(styleString);
		// if(engine!=null) {
		// return engine;
		// }
		CSSTipiEngineImpl engine = initializeEngine(component, styleString,
				styleResource, event);
		engineCache.put(styleString, engine);
		return engine;
	}

	public CSSTipiEngineImpl initializeEngine(TipiComponent component,
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

				@SuppressWarnings("rawtypes")
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
}
