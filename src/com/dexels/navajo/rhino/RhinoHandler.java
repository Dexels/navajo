package com.dexels.navajo.rhino;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.Method;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.mapping.CompiledScript;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.AuthorizationException;
import com.dexels.navajo.server.DispatcherFactory;
import com.dexels.navajo.server.ServiceHandler;
import com.dexels.navajo.server.SystemException;
import com.dexels.navajo.server.UserException;

public class RhinoHandler extends ServiceHandler {

	
	@Override
	public Navajo doService() throws NavajoException, UserException,
			SystemException, AuthorizationException {
		
		System.err.println("Rhino: Handler");
    	CompiledScript cso;
		try {
			cso = compileScript(access, new StringBuffer());
//	        Navajo outDoc = NavajoFactory.getInstance().createNavajo();
//	        access.setOutputDoc(outDoc);
	        access.setCompiledScript(cso);
	        cso.run(access);
	        return access.getOutputDoc();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return null;
	}
	  
    public static CompiledScript compileScript(Access a, StringBuffer compilerErrors) throws Exception {

 		System.err.println("Rhino: compileScript");
 		
   	 // TODO reuse this bugger
    	BasicScriptCompiler compiler = new BasicScriptCompiler();
    	InputStream inStr = DispatcherFactory.getInstance().getNavajoConfig().getScript(a.getRpcName());
    	File compiledPath = new File(DispatcherFactory.getInstance().getNavajoConfig().getCompiledScriptPath()+"/"+a.getRpcName()+".js");
    	File scriptPath = new File(DispatcherFactory.getInstance().getNavajoConfig().getScriptPath()+"/"+a.getRpcName()+".xml");
    	
    	compiler.setIncludeBase(new File(DispatcherFactory.getInstance().getNavajoConfig().getScriptPath()));
    	
    	File compileScriptDir = compiledPath.getParentFile();
    	if(!compileScriptDir.exists()) {
    		compileScriptDir.mkdirs();
    	}
    	System.err.println("scriptPath: "+scriptPath.getAbsolutePath());
    	System.err.println("CompiledPath: "+compiledPath.getAbsolutePath());
    	if(inStr==null) {
    		throw new FileNotFoundException("Script "+scriptPath.getAbsolutePath()+" not found.");
    	}
    	FileOutputStream baos = new FileOutputStream(compiledPath);
    	compiler.compileTsl(a.getRpcName(),inStr, baos,compilerErrors);
    	
    	baos.flush();
    	baos.close();
    	
    	ClassLoader cl = DispatcherFactory.getInstance().getNavajoConfig().getClassloader();
    	Class<? extends CompiledScript> rcsClass = (Class<? extends CompiledScript>) Class.forName("com.dexels.navajo.rhino.RhinoCompiledScript", true, cl);
//    	RhinoCompiledScript rcs = new RhinoCompiledScript();
    	CompiledScript rcs = (CompiledScript) rcsClass.newInstance();
    	Method ss = rcsClass.getMethod("setScript", File.class);
    	ss.invoke(rcs, compiledPath);
    	
    	
    	System.err.println("compiler Classloader id: "+compiler.getClass().getClassLoader());
    	System.err.println("Classloader id: "+rcs.getClass().getClassLoader());
//    	rcs.setScript(compiledPath);
    	return rcs;
    	
    }


}
