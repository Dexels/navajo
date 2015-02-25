package com.dexels.navajo.compiler.tsl.internal;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.compiler.BundleCreator;
import com.dexels.navajo.server.NavajoIOConfig;
import com.dexels.navajo.server.test.TestNavajoConfig;

public class AutoCompiler {
	
	private static final Logger logger = LoggerFactory.getLogger(AutoCompiler.class);
	   
	private NavajoIOConfig navajoIOConfig;
	private BundleCreator bundleCreator;

	
	public void activate(Map<String,Object> settings) {
		logger.info("Scanning auto");
		scan();
	}

	public void deactivate() {
		
	}
	
	private void scan() {
		scan(Paths.get(navajoIOConfig.getScriptPath()),Paths.get(navajoIOConfig.getCompiledScriptPath()));
	}
	private void scan(final Path scriptPath,final Path compiledPath) {
		 try {
		    Files.walkFileTree(scriptPath, new SimpleFileVisitor<Path>(){
		     @Override
		     public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
		          if(!attrs.isDirectory()){
		        	  
		        	  process(scriptPath,compiledPath,file,attrs.lastModifiedTime());
		          }
		          return FileVisitResult.CONTINUE;
		      }
		     });
		 } catch (IOException e) {
		      e.printStackTrace();
		 }
	}

	protected void process(Path scriptPath, Path compiledPath, Path script,
			FileTime lastModifiedTime) throws IOException {

		 Path pathRelative = scriptPath.relativize(script);
		 String comp = pathRelative.toString(); // 
		 
		 // replace extension
		 if(comp.endsWith(".xml")) {
			 String cleanPath = comp.substring(0,comp.length()-".xml".length());
//			 String jarPathString = cleanPath+".jar";
			 Path jarPath = compiledPath.resolve(cleanPath+".jar");
//			 Path jarPath = Paths.get(jarPathString);
			 boolean needRecompile = false;
			 if(jarPath.toFile().exists()) {
				 FileTime jarLastMod = Files.getLastModifiedTime(jarPath);
				 needRecompile = lastModifiedTime.compareTo(jarLastMod) >0;
//				 System.err.println("Needs recompile: "+needRecompile);
			 } else {
				 needRecompile = true;
			 }
			 if(needRecompile) {
				 System.err.println("Need to recompile : "+cleanPath);
				 List<String> success = new ArrayList<String>();
				 try {
					bundleCreator.createBundle(cleanPath, new Date(), ".xml", new ArrayList<String>(), success, new ArrayList<String>(), false, false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			 }
		 } else {
//			 System.err.println("skipping: "+comp);
		 }

	}

	public void setNavajoIOConfig(NavajoIOConfig navajoIOConfig) {
		this.navajoIOConfig = navajoIOConfig;
	}

	public void clearNavajoIOConfig(NavajoIOConfig navajoIOConfig) {
		this.navajoIOConfig = null;
	}

	
//	public void setBundleQueue(BundleQueue queue) throws Exception {
//		this.bundleQueue = queue;
//	}
//	public void clearBundleQueue(BundleQueue queue) {
//		this.bundleQueue = null;
//	}

	public void setBundleCreator(BundleCreator bundleCreator) {
		this.bundleCreator = bundleCreator;
	}

	public void clearBundleCreator(BundleCreator bundleCreator) {
		this.bundleCreator = null;
	}
	

	public static void main(String[] args) throws Exception {
		AutoCompiler ac = new AutoCompiler();
		NavajoIOConfig nc = new TestNavajoConfig(new File("/Users/frank/git/sportlink.restructure"));
		ac.setNavajoIOConfig(nc);
//		ac.setBundleCreator(new BundleCreator());
		ac.scan();
//		ac.scan(Paths.get("/Users/frank/git/sportlink.restructure/scripts"),Paths.get("/Users/frank/git/sportlink.restructure/classes"));
	}
}
