/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.compiler.tsl.internal;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.compiler.BundleCreator;
import com.dexels.navajo.server.NavajoIOConfig;

public class AutoCompiler {
	
	private static final Logger logger = LoggerFactory.getLogger(AutoCompiler.class);
	
	private static final int DEFAULT_WARMUP_WAIT = 20000;
	// default to 15m
	private static final int DEFAULT_SCHEDULE_WAIT = 900000;
	private static final boolean DEFAULT_ENABLED = false;

	private long scheduleWait = DEFAULT_SCHEDULE_WAIT;

	
	private NavajoIOConfig navajoIOConfig;
	private BundleCreator bundleCreator;
	private AtomicBoolean active = new AtomicBoolean();

	private ExecutorService executor;

	
	public void activate(Map<String, Object> settings) {
		Boolean enabled = DEFAULT_ENABLED;
		if (settings.containsKey("enabled")) {
			enabled =  Boolean.valueOf((String) settings.get("enabled"));
		}
		long warmupWait = parseValue(settings,"warmupWait",DEFAULT_WARMUP_WAIT);
		this.scheduleWait = parseValue(settings,"scheduleWait",DEFAULT_SCHEDULE_WAIT);
		logger.info("Activating autocompiling starting warm up of {}",warmupWait);
		this.executor = Executors.newFixedThreadPool(1);
		this.executor.execute(() -> {
			try {
				Thread.sleep(warmupWait);
			} catch (InterruptedException e) {
				logger.trace("Error: ", e);
			}
		});
		logger.info("Scanning auto starting");
		active.set(enabled);
		scanFiles();
	}

	private int parseValue(Map<String, Object> settings, String key, int defaultValue) {
		if(settings==null) {
			return defaultValue;
		}
		Object value = settings.get(key);
		if(value!=null) {
			if(value instanceof String) {
				return Integer.parseInt((String)value);
			}
			if(value instanceof Integer) {
				return (Integer)value;
			}
			logger.warn("Weird settings type for key: {} in autocompiler component. Returning default: {}", key, defaultValue);
		}
		return defaultValue;
	}

	public void scanFiles() {
		if(executor!=null) {
			executor.execute(() -> {
				while(active.get()) {
					scan();
					try {
						Thread.sleep(scheduleWait);
					} catch (InterruptedException e) {
						logger.error("Error: ", e);
					}
				}
			});
		}
	}
	public void deactivate() {
		active.set(false);
		if(executor!=null) {
			executor.shutdownNow();
		}
		executor = null;
	}
	
	private void scan() {
		try {
			scan(Paths.get(navajoIOConfig.getScriptPath()),Paths.get(navajoIOConfig.getCompiledScriptPath()));
		} catch (Exception e) {
			logger.error("Error performing auto compile scan: ",e);
		}
	}
	private void scan(final Path scriptPath,final Path compiledPath) {
		 try {
		    Files.walkFileTree(scriptPath, new SimpleFileVisitor<Path>(){
		     @Override
		     public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
		          if(!attrs.isDirectory()){
		        	  if(active.get()) {
			        	  process(scriptPath,compiledPath,file,attrs.lastModifiedTime());
		        	  } else {
		        		  logger.info("Inactive autocompiler... skipping.");
		        	  }
		          }
		          return FileVisitResult.CONTINUE;
		      }
		     });
		 } catch (IOException e) {
			 logger.error("Error",e);
		 }
	}

	protected void process(Path scriptPath, Path compiledPath, Path script,
			FileTime lastModifiedTime) throws IOException {

		 Path pathRelative = scriptPath.relativize(script);
		 String comp = pathRelative.toString(); // 
		 
		 // replace extension
		 if(comp.endsWith(".xml")) {
			 String cleanPath = comp.substring(0,comp.length()-".xml".length());
			 Path jarPath = compiledPath.resolve(cleanPath+".jar");
			 boolean needRecompile = false;
			 if(jarPath.toFile().exists()) {
				 FileTime jarLastMod = Files.getLastModifiedTime(jarPath);
				 needRecompile = lastModifiedTime.compareTo(jarLastMod) >0;
			 } else {
				 needRecompile = true;
			 }
			 if(needRecompile) {
				 cleanPath = cleanPath.replace('\\', '/');
				 logger.debug("Need to recompile : {}",cleanPath);
				 List<String> success = new ArrayList<>();
				 try {
					bundleCreator.createBundle(cleanPath, new ArrayList<String>(), success, new ArrayList<String>(), false, false);
					Thread.yield();
				 } catch (Exception e) {
					logger.warn("Bundle creation problem for bundle: "+cleanPath,e);
				}
			 }
		 }
	}

	public void setNavajoIOConfig(NavajoIOConfig navajoIOConfig) {
		this.navajoIOConfig = navajoIOConfig;
	}

	public void clearNavajoIOConfig(NavajoIOConfig navajoIOConfig) {
		this.navajoIOConfig = null;
	}


	public void setBundleCreator(BundleCreator bundleCreator) {
		this.bundleCreator = bundleCreator;
	}

	public void clearBundleCreator(BundleCreator bundleCreator) {
		this.bundleCreator = null;
	}

}
