package com.dexels.navajo.jsp.server.impl;

import java.io.File;

import com.dexels.navajo.jsp.server.NavajoServerContext;
import com.dexels.navajo.jsp.server.ScriptStatus;

public class ScriptStatusImpl implements ScriptStatus {

	private final File scriptRoot;
	private final File currentFile;
	private final File compileRoot;
	private final String name;
	private final String path;
	private final String extension;
	private final NavajoServerContext navajoServerContext;
	
	public ScriptStatusImpl(NavajoServerContext nsc, File scriptRoot, File currentFile, File compileRoot) {
		this.navajoServerContext = nsc;
		this.scriptRoot = scriptRoot;
		this.currentFile = currentFile;
		this.compileRoot = compileRoot;
		int ii = currentFile.getName().lastIndexOf('.');
		this.name = currentFile.getName().substring(0,ii);
		this.extension = currentFile.getName().substring(ii+1,currentFile.getName().length());
		
//		String path = "/var/data/stuff/xyz.dat";
//		String base = "/var/data";
		String pp = scriptRoot.toURI().relativize(currentFile.toURI()).getPath();
		ii = pp.lastIndexOf('.');
		this.path = pp.substring(0,ii);
		// relative == "stuff/xyz.dat"
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Script name: "+this.name);
		sb.append("\nScript root: "+this.scriptRoot.getAbsolutePath());
		sb.append("\nScript currentFile: "+this.currentFile.getAbsolutePath());
		sb.append("\nScript compileRoot: "+this.compileRoot.getAbsolutePath());
		sb.append("\nScript extension: "+this.extension);
		sb.append("\nScript path: "+this.path);
		sb.append("\nScript isCompiled: "+this.isCompiled());
		sb.append("\nScript isByteCodeCompiled: "+this.isByteCodeCompiled());
		sb.append("\nScript language: "+this.getLanguage());
		sb.append("\n");
		return sb.toString();
	}
	
	public File getCompiled() {
		return new File(this.compileRoot,path);
	}

	public String getLanguage() {
		if(extension.equals("rb")) {
			return "Ruby";
		}
		if(extension.equals("js")) {
			return "JavaScript";
		}
		if(extension.equals("java")) {
			return "Java";
		}
		if(extension.equals("xml")) {
			return "Tsl";
		}
		return extension;
	}

	public String getName() {
		return name;
	}

	public File getSource() {
		return currentFile;
	}

	public boolean isCompiled() {
		if(extension.equals("rb")) {
			return false;
		}
		if(extension.equals("js")) {
			return false;
		}
		if(extension.equals("java")) {
			return false;
		}
		if(extension.equals("xml")) {
			return true;
		}
		return false;
	}

	public boolean isByteCodeCompiled() {
		if(extension.equals("rb")) {
			return false;
		}
		if(extension.equals("js")) {
			return false;
		}
		if(extension.equals("java")) {
			return true;
		}
		if(extension.equals("xml")) {
			return true;
		}
		return false;
	}
	
	public boolean isDocumented() {
		if(extension.equals("xml")) {
			return true;
		}
		return false;
	}

	public boolean isLoaded() {
		return false;
	}

}
