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
import org.w3c.css.sac.ConditionFactory;
import org.w3c.dom.css.CSSStyleDeclaration;
import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiComponent;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.css.actions.impl.TipiPropertyHandler;
import com.dexels.navajo.tipi.internal.TipiAction;
import com.dexels.navajo.tipi.internal.TipiEvent;

public class ApplyCss extends TipiAction {

	private static final long serialVersionUID = 5481600392557969470L;
	public ConditionFactory cf;
	public org.akrogen.tkui.css.tipi.engine.CSSTipiEngineImpl aap;
	public org.w3c.dom.css.DocumentCSS noot;
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
		CSSTipiEngineImpl engine = new CSSTipiEngineImpl();
		engine.setErrorHandler(new CSSErrorHandler() {
			@Override
			public void error(Exception e) {
				e.printStackTrace();
			}
		});
		//		String style = "JLabel {uppercase:true}";
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
			engine.applyStyles(component, true);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}
