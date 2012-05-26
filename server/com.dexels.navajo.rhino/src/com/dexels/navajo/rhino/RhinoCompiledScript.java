package com.dexels.navajo.rhino;

import java.io.File;

import com.dexels.navajo.mapping.CompiledScript;
import com.dexels.navajo.script.api.NavajoDoneException;
import com.dexels.navajo.server.Access;

public class RhinoCompiledScript extends CompiledScript {

	private File script = null;

	// static public String getContents(File aFile) {
	// StringBuilder contents = new StringBuilder();
	// try {
	// BufferedReader input = new BufferedReader(new FileReader(aFile));
	// try {
	// String line = null;
	// while (( line = input.readLine()) != null){
	// contents.append(line);
	// contents.append(System.getProperty("line.separator"));
	// }
	// }
	// finally {
	// input.close();
	// }
	// }
	// catch (IOException ex){
	// ex.printStackTrace();
	// }
	// return contents.toString();
	// }

	@Override
	public void execute(Access access) throws Exception, NavajoDoneException {
		// TODO: Reuse this bugger
		RhinoRunner rh = new RhinoRunner();
		rh.run(access);
	}

	public void setScript(File script) {
		this.script = script;
	}

	public File getScript() {
		return script;
	}

	@Override
	public void finalBlock(Access access) throws Exception {
	}

	@Override
	public void setValidations() {
		// TODO Auto-generated method stub

	}

}
