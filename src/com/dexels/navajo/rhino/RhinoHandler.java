package com.dexels.navajo.rhino;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

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

	


	

	@Override
	public Navajo doService() throws NavajoException, UserException,
			SystemException, AuthorizationException {
		
		
		
    	CompiledScript cso;
		try {
			cso = compileScript(access, new StringBuffer());
	        Navajo outDoc = NavajoFactory.getInstance().createNavajo();
	        access.setOutputDoc(outDoc);
	        access.setCompiledScript(cso);
	        cso.run(access);
	        return outDoc;
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return null;
		


	}
	  
    public static CompiledScript compileScript(Access a, StringBuffer compilerErrors) throws Exception {
    	// TODO reuse this bugger
    	BasicScriptCompiler compiler = new BasicScriptCompiler();
    	InputStream inStr = DispatcherFactory.getInstance().getNavajoConfig().getScript(a.getRpcName());
    	File compiledPath = new File(DispatcherFactory.getInstance().getNavajoConfig().getCompiledScriptPath()+"/"+a.getRpcName()+".js");
    	
    	FileOutputStream baos = new FileOutputStream(compiledPath);
    	compiler.compileTsl(inStr, baos,compilerErrors);
    	
    	
    	RhinoCompiledScript rcs = new RhinoCompiledScript();
    	rcs.setScript(compiledPath);
    	return rcs;
    	
    }


}
