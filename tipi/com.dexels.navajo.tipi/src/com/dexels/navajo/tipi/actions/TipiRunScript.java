package com.dexels.navajo.tipi.actions;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;

import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tipi.TipiExtension;

import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.actions.adapters.BaseActions;
import com.dexels.navajo.tipi.actions.adapters.Evaluator;
import com.dexels.navajo.tipi.components.core.TipiComponentImpl;
import com.dexels.navajo.tipi.components.core.adapter.BaseAdapter;
import com.dexels.navajo.tipi.internal.TipiAction;
import com.dexels.navajo.tipi.internal.TipiEvent;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

/** @todo Refactor, move to NavajoSwingTipi */
public class TipiRunScript extends TipiAction {
	private static final long serialVersionUID = -1644143195033337886L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiRunScript.class);
	
	public void execute(TipiEvent event)
			throws com.dexels.navajo.tipi.TipiException,
			com.dexels.navajo.tipi.TipiBreakException {
		long startup = System.currentTimeMillis();
		String engine = (String) getEvaluatedParameterValue("engine", event);
		ScriptEngine scr = myContext.getScriptingEngine(engine);
		Reader script = null;
		if (scr == null) {
			logger.warn("No engine for tipi scripting!");
			return;
		}
		URL scriptPath = (URL) getEvaluatedParameterValue("script", event);

		if (scriptPath != null) {
			try {
				script = new InputStreamReader(scriptPath.openStream());
			} catch (IOException e) {
				logger.error("Error: ",e);
			}
		}
		scr.getBindings(ScriptContext.ENGINE_SCOPE).clear();

		for (TipiExtension t : getComponent().getContext().getExtensions()) {
			String actionClass = "tipi." + t.getProjectName() + "Actions";
			appendActions(actionClass, event, scr, t, false);
			String functionClass = "tipi." + t.getProjectName() + "Functions";
			appendActions(functionClass, event, scr, t, true);
		}

		scr.put("eval", new Evaluator(this));
		// scr.put("globals",getComponent().getContext().getGlobalMap());
		scr.put("tipiContext", getComponent().getContext());
		scr.put("component",
				((TipiComponentImpl) getComponent()).createAdapter(this, event));
		TipiComponentImpl homeComponent = (TipiComponentImpl) getComponent()
				.getHomeComponent();
		if (homeComponent != null) {
			scr.put("home", homeComponent.createAdapter(this, event));
		}
		TipiComponentImpl defaultTopLevel = (TipiComponentImpl) getComponent()
				.getContext().getDefaultTopLevel();
		TipiComponentImpl frame = (TipiComponentImpl) defaultTopLevel
				.getTipiComponent("init");
		BaseAdapter root = frame.createAdapter(this, event);
		scr.put("root", root);
		scr.put("params", event.getEvaluatedParameters());

		logger.info("Server spinup: "
				+ (System.currentTimeMillis() - startup));
		long start = System.currentTimeMillis();
		try {
			if (script != null) {
				scr.eval(script);
			} else {
				scr.eval(getText());
			}
			try {
				if (script != null) {
					script.close();

				}
			} catch (IOException e) {
				logger.error("Error: ",e);
			}
			logger.debug("Eval took: "
					+ (System.currentTimeMillis() - start));
		} catch (ScriptException e) {
			logger.error("Scripting engine fail: " + e.getMessage());
			throw new TipiException("Scripting engine fail: " + e.getMessage(),
					e);
		}
	}

	private void appendActions(String actionClass, TipiEvent event,
			ScriptEngine scr, TipiExtension t, boolean functions) {
		BaseActions b = null;
		try {
			Class<?> c = Class.forName(actionClass);
			b = (BaseActions) c.newInstance();
			b.setComponent(getComponent());
			b.setEvent(event);
			b.setInvocation(this);
			scr.put(t.getProjectName().toLowerCase()
					+ (functions ? "function" : ""), b);

		} catch (ClassNotFoundException e) {
			logger.warn("No actions for extension: "
					+ t.getProjectName());
		} catch (InstantiationException e) {
			logger.error("Error: ", e);
		} catch (IllegalAccessException e) {
			logger.error("Error: ", e);
		}
	}
}
