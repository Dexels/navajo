package com.dexels.navajo.tipi.actions;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;

import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptException;

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
	/**
	 * 
	 */
	private static final long serialVersionUID = -1644143195033337886L;

	public void execute(TipiEvent event)
			throws com.dexels.navajo.tipi.TipiException,
			com.dexels.navajo.tipi.TipiBreakException {
		long startup = System.currentTimeMillis();
		String engine = (String) getEvaluatedParameterValue("engine", event);
		ScriptEngine scr = myContext.getScriptingEngine(engine);
		Reader script = null;
		if (scr == null) {
			System.err.println("No engine!");
			return;
		}
		URL scriptPath = (URL) getEvaluatedParameterValue("script", event);

		if (scriptPath != null) {
			try {
				script = new InputStreamReader(scriptPath.openStream());
			} catch (IOException e) {
				e.printStackTrace();
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

		System.err.println("Server spinup: "
				+ (System.currentTimeMillis() - startup));

		// String adapterPath = getContext().getT
		// DispatcherFactory.getInstance().getNavajoConfig().getAdapterPath();
		// String includePath = adapterPath +
		// se.getFactory().getLanguageName()+"/include";
		// se.eval("$LOAD_PATH.push('"+includePath+"');" +
		// "$LOAD_PATH.push('"+DispatcherFactory.getInstance().getNavajoConfig().getScriptPath()+"');");
		// System.err.println("Added include path: "+includePath);
		long start = System.currentTimeMillis();
		try {
			// boolean compilable = scr instanceof Compilable;
			// if(compilable) {
			// Compilable cc = (Compilable)scr;
			// CompiledScript ccc = cc.compile(getText());
			// System.err.println("Compile took: "+(System.currentTimeMillis() -
			// start));
			// start = System.currentTimeMillis();
			// ccc.eval();
			// System.err.println("Run took: "+(System.currentTimeMillis() -
			// start));
			// }
			// System.err.println("Compilabel: "+compilable);

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
				e.printStackTrace();
			}
			System.err.println("Eval took: "
					+ (System.currentTimeMillis() - start));
		} catch (ScriptException e) {
			System.err.println("Scripting engine fail: " + e.getMessage());
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
			System.err.println("No actions for extension: "
					+ t.getProjectName());
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
}
