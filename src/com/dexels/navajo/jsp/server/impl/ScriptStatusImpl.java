package com.dexels.navajo.jsp.server.impl;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.io.IOException;

import com.dexels.navajo.jsp.server.NavajoServerContext;
import com.dexels.navajo.jsp.server.ScriptStatus;

public class ScriptStatusImpl implements ScriptStatus {

	private final File scriptRoot;
	private  File currentFile;
	private final File compileRoot;
	private final String name;
	private final String path;
	private final String extension;
//	private final NavajoServerContext navajoServerContext;

	public final String[] acceptedCompileCode = new String[]{"java","js"};
	
	public ScriptStatusImpl(NavajoServerContext nsc, File scriptRoot, File currentFile, File compileRoot) throws IOException {
//		this.navajoServerContext = nsc;
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
		currentFile = currentFile.getCanonicalFile();

	}
	
	public ScriptStatusImpl(NavajoServerContext nsc, File scriptRoot, File compileRoot, String servicePath) throws IOException {
//		this.navajoServerContext = nsc;
		this.scriptRoot = scriptRoot;
		this.compileRoot = compileRoot;
		// determine working dir:
		resolveScriptPath(scriptRoot, servicePath);
		if(this.currentFile==null || !this.currentFile.exists()) {
			// fall back to the context root
			File scriptContextRoot = new File(nsc.getContextRoot(),"scripts");
			resolveScriptPath(scriptContextRoot, servicePath);
		}
		
		if(this.currentFile==null) {
			this.path = "unknown";
			this.name = "unknown";
			this.extension = "unknown";
			return;
		}
		currentFile = currentFile.getCanonicalFile();
		String[] paths = currentFile.getPath().split("\\.");
		this.name=paths[0];
		String pp = scriptRoot.toURI().relativize(currentFile.toURI()).getPath();
		int ii = pp.lastIndexOf('.');
		this.path = pp.substring(0,ii);
		this.extension=currentFile.getPath().substring(currentFile.getPath().lastIndexOf(".")+1);
	}
	
	
	public static void main(String[] aa) {
		String a = "/Users/frank/Documents/Spiritus/NavajoExampleProject/scripts/InitNavajoDemo.xml";
		String v =a.substring(a.lastIndexOf(".")+1);
		System.err.println("Extension::: "+v);
		
	}
	

	protected void resolveScriptPath(File scriptRoot, String servicePath) {
		if(servicePath.indexOf('/')==-1) {
			this.currentFile = getSource(scriptRoot, servicePath);
		} else {
//			String[] spl = servicePath.split("/");
			int lastIndex = servicePath.lastIndexOf('/');
			String path = servicePath.substring(0,lastIndex);
			String serviceName = servicePath.substring(lastIndex+1,servicePath.length());
			File currentPath = new File(scriptRoot,path);
			this.currentFile = getSource(currentPath,serviceName);
		}
	}

	/**
	 * Might return null in some circumstances
	 * @param sourceDir
	 * @param serviceName
	 * @return
	 */
	private File getSource(File sourceDir, final String serviceName) {
		File[] files = sourceDir.listFiles(new FilenameFilter() {
			
			@Override
			public boolean accept(File dir, String name) {
				return name.startsWith(serviceName);
			}
		});
		if(files==null || files.length==0) {
			System.err.println("No qualifying script found in folder: "+sourceDir.getAbsolutePath()+" with name like: "+serviceName+".*");
			return null;
		}
		if(files.length>1) {
			
		}
		return files[0];
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
	
	public File getCompiledPath() {
		return new File(this.compileRoot,path);
	}

	public File getCompiledUnit() {
//		System.err.println("DEBUG: \n"+this.toString());
//		System.err.println("Compileroot: "+compileRoot+" - "+path);
		 // fictional file, will never exist.
		final File ff = new File(this.compileRoot,path);
//		System.err.println("assuming dir: "+ff.getParentFile().getAbsolutePath()	);
		File[] list = ff.getParentFile().listFiles(new FileFilter() {
			
			public boolean accept(File pathname) {
				String name = ScriptStatusImpl.this.path.substring(ScriptStatusImpl.this.path.lastIndexOf('/')+1);
				if(!pathname.getName().startsWith(name)) {
					return false;
				}
				for (String suffix : acceptedCompileCode) {
//					String proposed = name+"."+suffix;
//					System.err.println("Examining: "+proposed);
//					File p = new File(ff.getParentFile(), proposed);
//					System.err.println("Examining: "+p.getAbsolutePath());
					if(pathname.getName().endsWith(suffix)) {
						return true;
					}
				}
				return false;
			}
		});

		if(list==null) {
			return null;
		}
		if(list.length==0) {
			return null;
		}
//		System.err.println("FIOLLLL: "+list[0].getAbsolutePath());
		return list[0];
	}


	
//	new FilenameFilter() {
//		
//		public boolean accept(File dir, String name) {
//			for (String suffix : acceptedCompileCode) {
//				String proposed = name+"."+suffix;
//				File p = new File(dir, proposed);
//				return 
//			}
//			return false;
//		}
//	}
	
	
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

	@Override
	public int compareTo(ScriptStatus o) {
		if(o==null || getName()==null) {
			return 0;
		}
		return getName().compareTo(o.getName());
	}

}
