package com.dexels.navajo.tipi.css.actions;

import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiComponent;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.css.CssFactory;
import com.dexels.navajo.tipi.internal.TipiAction;
import com.dexels.navajo.tipi.internal.TipiEvent;

public class ApplyCss extends TipiAction {

	private static final long serialVersionUID = 5481600392557969470L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(ApplyCss.class);
	public org.akrogen.tkui.css.tipi.engine.CSSTipiEngineImpl aap;
	
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
		URL styleResource = null; //(URL) getEvaluatedParameterValue("styleSheet", event);
		
		
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
					CssFactory.getInstance().applyCss(component, cssDefinition, null, event);
				}
			}
			for (String cssDefinition : component.getContext().getCssDefinitions(getHomeDefinitionName(component)))
			{
				CssFactory.getInstance().applyCss(component, cssDefinition, null, event);
			}
		}
		else
		{
			CssFactory.getInstance().applyCss(component, styleString, styleResource, event);
		}
	}

	
	private String getHomeDefinitionName(TipiComponent tc)
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

}
