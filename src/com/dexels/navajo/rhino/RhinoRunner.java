package com.dexels.navajo.rhino;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContinuationPending;
import org.mozilla.javascript.JavaScriptException;
import org.mozilla.javascript.NativeJavaObject;
import org.mozilla.javascript.Script;
import org.mozilla.javascript.ScriptableObject;

import com.dexels.navajo.document.Header;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.parser.ConditionException;
import com.dexels.navajo.parser.DefaultExpressionEvaluator;
import com.dexels.navajo.rhino.flow.BreakError;
import com.dexels.navajo.rhino.flow.ConditionError;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.AuthorizationException;
import com.dexels.navajo.server.Dispatcher;
import com.dexels.navajo.server.DispatcherFactory;
import com.dexels.navajo.server.SystemException;
import com.dexels.navajo.server.UserException;
import com.dexels.navajo.server.test.TestNavajoConfig;

public class RhinoRunner {
	//private ScriptableObject globalScope;
	// private Context cx;
	// private Navajo response = null;

	private boolean localMode = false;

	public static void main(String[] args) throws IOException, InterruptedException, NavajoException, UserException,
			SystemException, AuthorizationException, ClassNotFoundException {
		// System.err.println("Version: " + System.getProperty("java.version"));
		//
		// com.dexels.navajo.functions.SingleValueQuery s;

		Class.forName("com.dexels.navajo.functions.ToBinary", true, RhinoRunner.class.getClassLoader());
		
		Navajo input = NavajoFactory.getInstance().createNavajo();
		// Navajo x = runScript("initinsert",input,false);
		// x.write(System.err);
		//
		// if(true) {
		// return;
		// }
		
		
		
		Navajo n = runScript("initproperties", input, false);
		System.err.println("\n\nENTEREING CHECKFIELDS........\n\n");
//		Navajo o = runScript("checkparam", n, false);
//		Navajo o = runScript("checksimpleparam", n, false);
		Navajo o = runScript("TestFields", n, true);

		n.write(System.err);
		o.write(System.err);
//		Thread.sleep(2000);

	}

	private static Navajo runScript(String scriptName, Navajo input, boolean compile) throws FileNotFoundException, IOException,
			NavajoException {
		RhinoRunner t = new RhinoRunner();
		t.localMode = true;
		Access a = new Access();
		a.rpcName = scriptName;

		// a.rpcName = "InitTestPropertyTypes";
		a.setInDoc(input);
		NavajoFactory.getInstance().setExpressionEvaluator(new DefaultExpressionEvaluator());
		new DispatcherFactory(new Dispatcher(new TestNavajoConfig()));
		// t.run(a);
		if (compile) {
			BasicScriptCompiler bsc = new BasicScriptCompiler();
			FileOutputStream os = new FileOutputStream(a.getRpcName() + ".js");
			bsc.compileTsl("ArrayMapTest", new FileInputStream("testscript/" + a.getRpcName() + ".xml"), os, new StringBuffer());
			os.flush();
			os.close();
		}
		Navajo output = NavajoFactory.getInstance().createNavajo();
		a.setOutputDoc(output);
		Navajo n = t.runBlockingScript(new FileInputStream(new File(a.getRpcName() + ".js")), a);
		System.err.println("Main finished");
		return n;
	}

	public Navajo runBlockingScript(InputStream script, final Access a) throws IOException, NavajoException {

		try {
			final ScriptEnvironment se = runScript(script, a, new ScriptFinishHandler() {

				public void run() {
					// response = getScriptEnvironment().getResponse();
					// a.setOutputDoc(response);
					if (getScriptEnvironment().isAsync()) {
						synchronized (getScriptEnvironment()) {
							System.err.println("Notifying for scriptFinished");
							getScriptEnvironment().notify();
						}
					}
				}
			});

			// if the script is not 'async' (meaning no continuations or something
			// happened)
			// do not wait then, as the script is already done.
			if (se.isAsync()) {
				synchronized (se) {
					try {
						System.err.println("Waiting for scriptFinish");
						se.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			return a.getOutputDoc();
		} catch (ContinuationPending e1) {
			ContinuationHandler ch = (ContinuationHandler) e1.getApplicationState();
			System.err.println("This really should not have happened. I am disappointed.");

		}
		System.err.println("Blocking script failed because of continuation?");
		return null;

	}

	public ScriptEnvironment runScript(InputStream script, Access a, ScriptFinishHandler onFinish) throws IOException {
		// Navajo output = null;
		// if(a.getOutputDoc()!=null) {
		// output = a.getOutputDoc();
		// } else {
		// output = NavajoFactory.getInstance().createNavajo();
		// a.setOutputDoc(output);
		// }
		//

		Reader fileReader = new InputStreamReader(script);
		Reader includeReader = getIncludeReader("include.js");
		Reader includeCompiledReader = getIncludeReader("includeCompiled.js");
		Context cx = Context.enter();
		Header h = a.getOutputDoc().getHeader();
		if (h == null) {
			h = NavajoFactory.getInstance().createHeader(a.getOutputDoc(), a.getRpcName(), a.getRpcUser(), "", -1);
			h.setHeaderAttribute("language", "js vesion:" + cx.getLanguageVersion());
			a.getOutputDoc().addHeader(h);
		}
		ScriptEnvironment scriptEnvironment = new StackScriptEnvironment();
		a.setScriptEnvironment(scriptEnvironment);
		scriptEnvironment.setCurrentContext(cx);
		onFinish.setScriptEnvironment(scriptEnvironment);
		scriptEnvironment.setFinishClosure(onFinish);
		try {
			ScriptableObject globalScope = cx.initStandardObjects();
			a.setInDoc(a.getInDoc());
			scriptEnvironment.setAccess(a);
			scriptEnvironment.setGlobalScope(globalScope);
			ScriptableObject.putProperty(globalScope, "input", Context.javaToJS(a.getInDoc(), globalScope));
			ScriptableObject.putProperty(globalScope, "output", Context.javaToJS(a.getOutputDoc(), globalScope));
			ScriptableObject.putProperty(globalScope, "env", Context.javaToJS(scriptEnvironment, globalScope));
			// ScriptableObject.putProperty(globalScope, "map",
			// Context.javaToJS(new MappableFactory(), globalScope));
			ScriptableObject.putProperty(globalScope, "access", Context.javaToJS(a, globalScope));
			cx.setOptimizationLevel(-1); // must use interpreter mode
			Script includeCompiledRun = cx.compileReader(includeCompiledReader, "includeCompiled.js", 1, null);
			Script includeRun = cx.compileReader(includeReader, "include.js", 1, null);
			Script scriptrun = cx.compileReader(fileReader, a.getRpcName() + ".js", 1, null);
			cx.executeScriptWithContinuations(includeRun, globalScope);
			cx.executeScriptWithContinuations(includeCompiledRun, globalScope);

//			com.dexels.navajo.functions.ToBinary b;
			//			System.err.println("Taking off!");
			try {
				cx.executeScriptWithContinuations(scriptrun, globalScope);
			} catch (JavaScriptException e) {
				Object o = e.getValue();
				o = Context.jsToJava(o, Object.class);
				System.err.println("o: " + o);
				if (o instanceof RuntimeException) {
					RuntimeException t = (RuntimeException) o;
					System.err.println("Rethrowing.......");
					throw t;
				}
				System.err.println("Other exception?");
				e.printStackTrace();
			}
			// this only happens when the entire scripts runs without
			// continuations.
			System.err.println("End of run, should only happen if no continuations happened!");
			scriptEnvironment.finishRun();
			// onFinish.run();
			return scriptEnvironment;
		} catch (ContinuationPending pending) {
			// continueScript(pending);
			Object o = pending.getApplicationState();
			if (o == null) {
				scriptEnvironment.setAsync(true);
				return scriptEnvironment;
			}
			throw pending;

		} catch (ConditionError e) {
			System.err.println("Condition Error detected!");
			a.setOutputDoc(e.getConditionErrors());
			return scriptEnvironment;
		} catch (BreakError e) {
			System.err.println("Break Error detected!");
			return scriptEnvironment;
		} catch (Exception e) {
			System.err.println("Very unknown error. Should create error message!");
			e.printStackTrace();
			return scriptEnvironment;
		} finally {
			if(Context.getCurrentContext()!=null) {
				Context.exit();
			}
			fileReader.close();
			includeReader.close();
		}
		// special case without interruptions. What to do?
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
		File adapter = new File(DispatcherFactory.getInstance().getNavajoConfig().getAdapterPath());
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
			File compiledPath = new File(DispatcherFactory.getInstance().getNavajoConfig().getCompiledScriptPath() + "/" + script
					+ ".js");
			FileInputStream fis = new FileInputStream(compiledPath);
			return fis;
		}
	}

	public void run(Access access) throws NavajoException, SystemException {
		InputStream is = null;
		try {
			// Thread.dumpStack();
			if (access.getOutputDoc() == null) {
				System.err.println("No out doc found. Setting to blank");
				Thread.dumpStack();
				access.setOutputDoc(NavajoFactory.getInstance().createNavajo());
			}
			is = getScriptStream(access.getRpcName());
			runBlockingScript(is, access);
			System.err.println("Ending blocking run.");
		} catch (IOException e) {
			e.printStackTrace();

			throw new SystemException(-1, "Error running rhino: ", e);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
