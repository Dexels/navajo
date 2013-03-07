package com.dexels.navajo.tipi.actions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.TipiComponent;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.internal.TipiAction;
import com.dexels.navajo.tipi.internal.TipiEvent;
import com.dexels.navajo.tipi.internal.TipiMethod;
import com.dexels.navajo.tipi.tipixml.XMLElement;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */
public final class TipiExecuteMethod extends TipiAction {

	private final static Logger logger = LoggerFactory
			.getLogger(TipiExecuteMethod.class);
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 472131866710083014L;

	public final void execute(TipiEvent event)
			throws com.dexels.navajo.tipi.TipiException,
			com.dexels.navajo.tipi.TipiBreakException {
		// Set<String> ss = getParameterNames();

		Object o = getEvaluatedParameterValue("name", event);
		Object p = getEvaluatedParameterValue("rootComponent", event);
		if (o == null) {
			throw new TipiException(
					"TipiExecuteMethod: Name missing ");

		}
		if (!(o instanceof String))
		{
			throw new TipiException(
					"TipiExecuteMethod: Name wrong type");
		}
		if (p != null && !(p instanceof TipiComponent)) {
			throw new TipiException(
					"TipiExecuteMethod: rootComponent wrong type");
		}
		String name = (String) o;
		// is rootComponent defined? if not, use event.getComponent() for now.
		if (getParameter("rootComponent") == null && event != null)
		{
			p = event.getComponent();
		}
		TipiComponent tc = (TipiComponent) p;
		
		// find the method, is it local or global? Local first
		TipiComponent localMethodDefaultRootComponent = null;
		XMLElement localMethodXML = null;
		if (tc != null && tc.getScopeHomeComponent() != null)
		{
			localMethodDefaultRootComponent =  tc.getScopeHomeComponent();
			localMethodXML  = localMethodDefaultRootComponent.getLocalMethod(name);
		}
		// else global
		XMLElement globalMethodXML = null;
		if (tc != null && tc.getContext() != null)
		{
			globalMethodXML = tc.getContext().getGlobalMethod(name);
		}
		
		if (globalMethodXML == null && localMethodXML == null) {
			logger.warn("TipiExecuteMethod: Cannot find local nor global method with name " + name);
			return;
		}
		if (getParameter("rootComponent") == null)
		{ // find default root component
			if (localMethodXML != null)
			{
				tc = localMethodDefaultRootComponent;
			}
			else if (globalMethodXML != null)
			{
				tc = event.getContext().getDefaultTopLevel();
			}
		}
		if (tc != null)
		{
			logger.info("Executing method " + name + " using as rootComponent " + tc.getName() + "/" + tc.getPath());
		}
		else
		{
			logger.info("Executing method " + name + " using as rootComponent null");
		}
		XMLElement methodXML = localMethodXML == null ? globalMethodXML : localMethodXML;
		TipiMethod tipiMethod = new TipiMethod(event.getContext());
		tipiMethod.load(methodXML, tc, null);
		tipiMethod.performAction(event, event.getParent(), event.getExeIndex(this));
	}
}
