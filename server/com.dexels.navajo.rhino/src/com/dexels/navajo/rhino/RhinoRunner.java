package com.dexels.navajo.rhino;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContinuationPending;
import org.mozilla.javascript.JavaScriptException;
import org.mozilla.javascript.ScriptableObject;
import org.mozilla.javascript.WrappedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Header;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.rhino.flow.BreakError;
import com.dexels.navajo.rhino.flow.ConditionError;
import com.dexels.navajo.script.api.FatalException;
import com.dexels.navajo.script.api.NavajoDoneException;
import com.dexels.navajo.script.api.SchedulerRegistry;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.DispatcherFactory;
import com.dexels.navajo.server.SystemException;

public class RhinoRunner {
	// private ScriptableObject globalScope;
	// private Context cx;
	// private Navajo response = null;

	private boolean localMode = false;

	private static Logger logger = LoggerFactory.getLogger(RhinoRunner.class);

	public static void main(String[] args) throws NavajoException, ClassNotFoundException,
			NavajoDoneException {
		Class.forName("com.dexels.navajo.functions.ToBinary", true,
				RhinoRunner.class.getClassLoader());

//		Navajo input = NavajoFactory.getInstance().createNavajo();


//		runScript("sleepmap", input, false);

		SchedulerRegistry.getScheduler().shutdownScheduler();

	}

//	private static Navajo runScript(String scriptName, Navajo input,
//			boolean compile) throws FileNotFoundException, IOException,
//			NavajoException, NavajoDoneException {
//		RhinoRunner t = new RhinoRunner();
//		t.localMode = true;
//		Access a = new Access();
//		a.rpcName = scriptName;
//
//		// a.rpcName = "InitTestPropertyTypes";
//		a.setInDoc(input);
//		NavajoFactory.getInstance().setExpressionEvaluator(
//				new DefaultExpressionEvaluator());
//		new DispatcherFactory(new Dispatcher(new TestNavajoConfig()));
//		// t.run(a);
//		if (compile) {
//			BasicScriptCompiler bsc = new BasicScriptCompiler();
//			FileOutputStream os = new FileOutputStream(a.getRpcName() + ".js");
//			bsc.compileTsl("ArrayMapTest", new FileInputStream("testscript/"
//					+ a.getRpcName() + ".xml"), os, new StringBuffer());
//			os.flush();
//			os.close();
//		}
//		Navajo output = NavajoFactory.getInstance().createNavajo();
//		a.setOutputDoc(output);
//		Navajo n;
//		try {
//			n = t.runBlockingScript(new FileInputStream(new File(a.getRpcName()
//					+ ".js")), a);
//			return n;
//		} catch (NavajoDoneException e) {
//			try {
//				Thread.sleep(10000);
//			} catch (InterruptedException e1) {
//				logger.warn("Interrupted");
//			}
//			return null;
//		}
//	}

	public Navajo runBlockingScript(InputStream script, final Access a)
			throws IOException, NavajoException, NavajoDoneException {

		ScriptEnvironment se;
		try {
			se = runScript(script, a, new ScriptFinishHandler() {

				@Override
				public void run() {
					// response = getScriptEnvironment().getResponse();
					// a.setOutputDoc(response);
					if (getScriptEnvironment().isAsync()) {
						synchronized (getScriptEnvironment()) {
							// TODO do I need to skip this if the thread has
							// been freed? There is no point notifying, but I
							// _think_ it is harmless.
							System.err.println("Notifying for scriptFinished");
							getScriptEnvironment().notify();
						}
					}
				}
			});
		} catch (NavajoDoneException e1) {
			System.err
					.println("Thread indicates it is freed. The navajo is still running, but it doesn't need this thread.");
			// so no point in waiting.
			throw e1;
		}

		// if the script is not 'async' (meaning no continuations or something
		// happened)
		// do not wait then, as the script is already done.
		if (se.isAsync()) {
			synchronized (se) {
				try {
					logger.debug("Waiting for scriptFinish");
					se.wait();
				} catch (InterruptedException e) {
					logger.info("Interrupted:", e);
				}
			}
		}
		return a.getOutputDoc();

	}

	// public static Scriptable getGlobalScope() {
	// if (globalScriptScope == null) {
	// globalScriptScope = Context.
	// }
	// }

	public ScriptEnvironment runScript(InputStream script, Access a,
			ScriptFinishHandler onFinish) throws IOException,
			NavajoDoneException {
		Reader fileReader = new InputStreamReader(script);
		Reader includeReader = getIncludeReader("include.js");
		Reader includeCompiledReader = getIncludeReader("includeCompiled.js");
		Context cx = Context.enter();
		Header h = a.getOutputDoc().getHeader();
		if (h == null) {
			h = NavajoFactory.getInstance().createHeader(a.getOutputDoc(),
					a.getRpcName(), a.getRpcUser(), "", -1);
			a.getOutputDoc().addHeader(h);
		}
		h.setHeaderAttribute("language",
				"js version:" + cx.getImplementationVersion());
		h.setHeaderAttribute("threadName", Thread.currentThread().getName());

		ScriptEnvironment scriptEnvironment = new StackScriptEnvironment();
		a.setScriptEnvironment(scriptEnvironment);
		scriptEnvironment.setCurrentContext(cx);
		onFinish.setScriptEnvironment(scriptEnvironment);
		scriptEnvironment.setFinishClosure(onFinish);
		ScriptableObject globalScope = null;
		try {
			globalScope = (ScriptableObject) NavajoScopeManager.getInstance()
					.getScope();
			ConditionError conditionError = new com.dexels.navajo.rhino.flow.ConditionError();

			a.setInDoc(a.getInDoc());
			scriptEnvironment.setAccess(a);
			scriptEnvironment.setGlobalScope(globalScope);

			ScriptableObject.putProperty(globalScope, "input",
					Context.javaToJS(a.getInDoc(), globalScope));
			ScriptableObject.putProperty(globalScope, "output",
					Context.javaToJS(a.getOutputDoc(), globalScope));
			ScriptableObject.putProperty(globalScope, "access",
					Context.javaToJS(a, globalScope));
			ScriptableObject.putProperty(globalScope, "env",
					Context.javaToJS(scriptEnvironment, globalScope));
			ScriptableObject.putProperty(globalScope, "conditionErrors",
					Context.javaToJS(conditionError, globalScope));

			cx.setOptimizationLevel(-1); // must use interpreter mode
//			Script includeCompiledRun = cx.compileReader(includeCompiledReader,
//					"includeCompiled.js", 1, null);
//			Script includeRun = cx.compileReader(includeReader, "include.js",
//					1, null);
//			Script scriptrun = cx.compileReader(fileReader, a.getRpcName()
//					+ ".js", 1, null);
			
			
			
//			cx.executeScriptWithContinuations(includeRun, globalScope);
	
			try {
				/**
				 * TODO: NEXT TWO evaluateReader() steps consume most of the time for simple scripts.
				 * find another way of including includeCompiled.js only once in generated script.
				 * Why is include.js needed??
				 */
				cx.evaluateReader(globalScope, includeCompiledReader, "includeCompiled.js", 1, null);
				cx.evaluateReader(globalScope, includeReader, "include.js", 1, null);
				cx.evaluateReader(globalScope, fileReader, a.getRpcName()+ ".js", 1, null);
//			cx.executeScriptWithContinuations(scriptrun, globalScope);
			} catch (JavaScriptException e) {
				
				if ( conditionError.getConditionErrors() != null ) {
					a.setOutputDoc(conditionError.getConditionErrors());
				} else {
					Object o = e.getValue();
					logger.error("ScriptStack: \n{}",e.getScriptStackTrace());
					//				o = Context.jsToJava(o, Object.class);
					System.err.println("o: " + o);
					if(o instanceof Throwable) {
						logger.error("Exception:", (Throwable)o);
					}
					logger.error("Other exception:", e);
					if (o instanceof RuntimeException) {
						RuntimeException t = (RuntimeException) o;
						throw t;
					} 
					generateErrorMessage("Break Error detected: " + e.getMessage(), e,a);
				}
			}
			// this only happens when the entire scripts runs without
			// continuations.
			scriptEnvironment.finishRun();
			NavajoScopeManager.getInstance().releaseScope(globalScope);
			return scriptEnvironment;
		} catch (ContinuationPending pending) {
			Object o = pending.getContinuation();
			if (o == null) {
				scriptEnvironment.setAsync(true);
				return scriptEnvironment;
			}
			logger.info("Continuation scheduled", pending);
			throw new NavajoDoneException(pending);
			// throw pending;
		} catch (NavajoDoneException pending) {
			// do NOT free the scope!
			throw pending;
		} catch (ConditionError e) {
			logger.error("Condition error:", e);

			NavajoScopeManager.getInstance().releaseScope(globalScope);
			a.setOutputDoc(e.getConditionErrors());
			return scriptEnvironment;
		} catch (BreakError e) {
			generateErrorMessage("Break Error detected: " + e.getMessage(), e,a);
			logger.warn("Break error detected", e);
			NavajoScopeManager.getInstance().releaseScope(globalScope);
			return scriptEnvironment;
		} catch (WrappedException e) {
			generateErrorMessage("Wrapped error: " + e.getCause().getMessage(),e.getCause(), a);
			NavajoScopeManager.getInstance().releaseScope(globalScope);
			logger.error("Wrapped error: ",e);
			return scriptEnvironment;
		} catch (Throwable e) {
			logger.error("Unknown error:", e);
			generateErrorMessage("Unknown error:" + e.getMessage(), e, a);
			NavajoScopeManager.getInstance().releaseScope(globalScope);
			return scriptEnvironment;
		} finally {
			if (Context.getCurrentContext() != null) {
				Context.exit();
			}
			fileReader.close();
			includeReader.close();
			includeCompiledReader.close();
		}
		// special case without interruptions. What to do?
	}

	private void generateErrorMessage(String message, Throwable e, Access a) {

		try {
			DispatcherFactory.getInstance().generateErrorMessage(a, message,
					-1, 0, e);
		} catch (FatalException e1) {
			logger.error("Error: ", e1);
		}
	}

	private Reader getIncludeReader(String path) throws FileNotFoundException {
		if (localMode) {
			File localFile = new File(path);
			if (localFile.exists()) {
				return new FileReader(localFile);
			}
		}
		InputStream is = getClass().getResourceAsStream("include/" + path);
		if (is != null) {
			return new InputStreamReader(is);
		}
		File adapter = new File(DispatcherFactory.getInstance()
				.getNavajoConfig().getAdapterPath());
		File target = new File(adapter, "js/include/" + path);
		if (target.exists()) {
			return new FileReader(target);
		}
		throw new FileNotFoundException("Can not locate include: " + path);

	}

	public InputStream getScriptStream(String script) throws IOException {
		if (localMode) {
			String scriptName = script + ".js";
			FileInputStream fis = new FileInputStream(scriptName);
			return fis;
		} else {
			File compiledPath = new File(DispatcherFactory.getInstance()
					.getNavajoConfig().getCompiledScriptPath()
					+ "/" + script + ".js");
			FileInputStream fis = new FileInputStream(compiledPath);
			return fis;
		}
	}

	public void run(final Access access) throws NavajoException,
			SystemException, NavajoDoneException {
		InputStream is = null;
		try {
			if (access.getOutputDoc() == null) {
				access.setOutputDoc(NavajoFactory.getInstance().createNavajo());
			}
			is = getScriptStream(access.getRpcName());
			runScript(is, access, new ScriptFinishHandler() {
				@Override
				public void run() {
					logger.info("Script: " + access.getRpcName() + " complete");

				}
			});
		} catch (IOException e) {
			throw new SystemException(-1, "Error running rhino: ", e);
		}
	}

}
