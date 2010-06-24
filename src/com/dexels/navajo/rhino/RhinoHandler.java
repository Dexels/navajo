package com.dexels.navajo.rhino;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContinuationPending;
import org.mozilla.javascript.Script;
import org.mozilla.javascript.ScriptableObject;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.mapping.CompiledScript;
import com.dexels.navajo.parser.DefaultExpressionEvaluator;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.AuthorizationException;
import com.dexels.navajo.server.Dispatcher;
import com.dexels.navajo.server.DispatcherFactory;
import com.dexels.navajo.server.ServiceHandler;
import com.dexels.navajo.server.SystemException;
import com.dexels.navajo.server.UserException;
import com.dexels.navajo.server.test.TestNavajoConfig;

public class RhinoHandler extends ServiceHandler {

	
	private ScriptableObject globalScope;
	private Context cx;
	private Navajo response = null;
	private boolean localMode = false;
	
	public static void main(String[] args) throws IOException, InterruptedException, NavajoException, UserException, SystemException, AuthorizationException {
		System.err.println("Version: " + System.getProperty("java.version"));
	
		RhinoHandler t = new RhinoHandler();
		t.localMode = true;
		Navajo input = NavajoFactory.getInstance().createNavajo();
		Access a = new Access();
		a.rpcName = "test2";
		a.setInDoc(input);
		NavajoFactory.getInstance().setExpressionEvaluator(new DefaultExpressionEvaluator());
		DispatcherFactory df = new DispatcherFactory(new Dispatcher(new TestNavajoConfig()));
		t.setInput(a);
		Navajo n = t.doService();
		n.write(System.err);
	//	Thread.sleep(60000);
	}

public InputStream getScriptStream(String script) throws IOException {
	if(localMode ) {
		String scriptName = RhinoHandler.this.access.getRpcName()+".js";
		FileInputStream fis = new FileInputStream(scriptName);
		return fis;
	} else {
		File compiledPath = new File(DispatcherFactory.getInstance().getNavajoConfig().getCompiledScriptPath()+"/"+access.getRpcName()+".js");
		FileInputStream fis = new  FileInputStream(compiledPath);
		return fis;
	}
}
	
	

	@Override
	public Navajo doService() throws NavajoException, UserException,
			SystemException, AuthorizationException {
		final ByteArrayOutputStream baos = new ByteArrayOutputStream();
		InputStream is = null;
		try {
			is = getScriptStream(access.getRpcName());
			return runBlockingScript(is, access, baos, new Runnable() {
				public void run() {
//					System.err.println("Result: " + new String(baos.toByteArray()));
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
			throw new UserException(-1,"Error running rhino: ",e);
		} finally {
			if(is!=null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	public Navajo runBlockingScript(InputStream script, Access a, final OutputStream output, final Runnable onFinish) throws  IOException, NavajoException {
		try {
			final ScriptEnvironment se = runScript(script, a, new ScriptFinishHandler() {
				
				public void run() {
					System.err.println("FinishHandler");
					response = getScriptEnvironment().getResponse();
					try {
						response.write(output);
					} catch (NavajoException e) {
						e.printStackTrace();
					}
					onFinish.run();
					synchronized (getScriptEnvironment()) {
						getScriptEnvironment().notify();
					}
				}});
			synchronized (se) {
				try {
					se.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			return response;
		} catch (ContinuationPending e1) {
			ContinuationHandler ch = (ContinuationHandler)e1.getApplicationState();
		}
		return null;

	}
	public void runScript(InputStream script, Access a, final OutputStream output, final Runnable onFinish) throws  IOException, NavajoException {
		try {
			final ScriptEnvironment se = runScript(script, a, new ScriptFinishHandler() {
				
				public void run() {
					System.err.println("FinishHandler");
					response = getScriptEnvironment().getResponse();
					try {
						response.write(output);
					} catch (NavajoException e) {
						e.printStackTrace();
					}
					onFinish.run();
				}});
		} catch (ContinuationPending e1) {
			ContinuationHandler ch = (ContinuationHandler)e1.getApplicationState();
		}
	}
	
	  public ScriptEnvironment runScript(InputStream script, Access a, ScriptFinishHandler onFinish) throws IOException {
		  Navajo output = NavajoFactory.getInstance().createNavajo();
		  Reader fileReader = new InputStreamReader(script);
		  Reader includeReader = new FileReader("include.js");
		  Reader includeCompiledReader = new FileReader("includeCompiled.js");
		  cx = Context.enter();
	      ScriptEnvironment scriptEnvironment = new StackScriptEnvironment();
	      onFinish.setScriptEnvironment(scriptEnvironment);
	      scriptEnvironment.setFinishClosure(onFinish);
	      try {
		    globalScope = cx.initStandardObjects();
		    a.setInDoc(a.getInDoc());
		    a.setOutputDoc(output);
		    scriptEnvironment.setAccess(a);
		    scriptEnvironment.setGlobalScope(globalScope);
	        ScriptableObject.putProperty(globalScope, "input", Context.javaToJS(a.getInDoc(), globalScope));
	        ScriptableObject.putProperty(globalScope, "output", Context.javaToJS(output, globalScope));
			ScriptableObject.putProperty(globalScope, "env", Context.javaToJS(scriptEnvironment, globalScope));
			ScriptableObject.putProperty(globalScope, "map", Context.javaToJS(new MappableFactory(), globalScope));
			ScriptableObject.putProperty(globalScope, "access", Context.javaToJS(a, globalScope));
	      	cx.setOptimizationLevel(-1); // must use interpreter mode
			Script includeCompiledRun = cx.compileReader(includeCompiledReader, a.getRpcName(), 1, null); 
			Script includeRun = cx.compileReader(includeReader, a.getRpcName(), 1, null); 
			Script scriptrun = cx.compileReader(fileReader, a.getRpcName(), 1, null); 
			cx.executeScriptWithContinuations(includeRun, globalScope);
			cx.executeScriptWithContinuations(includeCompiledRun, globalScope);
			
			cx.executeScriptWithContinuations(scriptrun, globalScope);
			// this only happens when the entire scripts runs without continuations.
			System.err.println("End of run, should only happen if no continuations happened!");
			scriptEnvironment.finishRun();
		//	onFinish.run();
			return scriptEnvironment;
	      } catch (ContinuationPending pending) {
//	    	  continueScript(pending);
	    	  Object o = pending.getApplicationState();
	    	  if(o==null) {
		    	  scriptEnvironment.setAsync(true);
		    	  return scriptEnvironment;	    		  
	    	  }
	    	  throw pending;
	      } catch (Exception e) {
			e.printStackTrace();
			return scriptEnvironment;
		} finally {
	    	  Context.exit();
	    	  fileReader.close();
	    	  includeReader.close();
		}
		// special case without interruptions. What to do?
	}
	  
	  
    public static CompiledScript compileScript(Access a, StringBuffer compilerErrors) throws Exception {
    	BasicScriptCompiler compiler = new BasicScriptCompiler();
    	InputStream inStr = DispatcherFactory.getInstance().getNavajoConfig().getScript(a.getRpcName());
    	File compiledPath = new File(DispatcherFactory.getInstance().getNavajoConfig().getCompiledScriptPath()+"/"+a.getRpcName()+".js");
    	
    	FileOutputStream baos = new FileOutputStream(compiledPath);
    	compiler.compileTsl(inStr, baos);
    	
    	
    	RhinoCompiledScript rcs = new RhinoCompiledScript();
    	rcs.setScript(compiledPath);
    	return rcs;
    	
    }


}
