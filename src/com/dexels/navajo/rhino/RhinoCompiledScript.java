package com.dexels.navajo.rhino;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import com.dexels.navajo.mapping.CompiledScript;
import com.dexels.navajo.server.Access;

public class RhinoCompiledScript extends CompiledScript {

	File script = null;

	public File getScript() {
		return script;
	}

	public void setScript(File script) {
		this.script = script;
	}


	 static public String getContents(File aFile) {
		    //...checks on aFile are elided
		    StringBuilder contents = new StringBuilder();
		    
		    try {
		      //use buffering, reading one line at a time
		      //FileReader always assumes default encoding is OK!
		      BufferedReader input =  new BufferedReader(new FileReader(aFile));
		      try {
		        String line = null; //not declared within while loop
		        /*
		        * readLine is a bit quirky :
		        * it returns the content of a line MINUS the newline.
		        * it returns null only for the END of the stream.
		        * it returns an empty String if two newlines appear in a row.
		        */
		        while (( line = input.readLine()) != null){
		          contents.append(line);
		          contents.append(System.getProperty("line.separator"));
		        }
		      }
		      finally {
		        input.close();
		      }
		    }
		    catch (IOException ex){
		      ex.printStackTrace();
		    }
		    
		    return contents.toString();
		  }
	 
	@Override
	public void execute(Access access) throws Exception {
		// TODO: Reuse this bugger
		RhinoHandler rh = new RhinoHandler();
		
		rh.setInput(access);
		rh.doService();
		
	}

	
	
	@Override
	public void finalBlock(Access access) throws Exception {
	}

	@Override
	public void setValidations() {
		// TODO Auto-generated method stub

	}

}
