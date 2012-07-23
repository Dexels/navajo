package com.dexels.navajo.compiler.tsl.custom;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;

import javax.tools.JavaFileObject;
import javax.tools.JavaFileObject.Kind;
import javax.tools.StandardLocation;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class CustomClassLoader extends ClassLoader {

	private final CustomClassloaderJavaFileManager fileManager;
	
	private final static Logger logger = LoggerFactory
			.getLogger(CustomClassLoader.class);
	
	public CustomClassLoader(
			CustomClassloaderJavaFileManager customJavaFileManager) {
		this.fileManager = customJavaFileManager;
	}



	@Override
	protected Class<?> findClass(String className) throws ClassNotFoundException {
        byte[] b = loadClassData(className);
        if(b==null) {
        	return null;
        }
        return defineClass(className, b, 0, b.length);
	}

	

    private byte[] loadClassData(String className) {
    	logger.info("LOADING CLASS: "+className);
    	String name = className.replaceAll("\\.", "/");
//    	logger.info(">>>>< "+name);
//    	
//    	String packageName = null;
//    	String localName = null;
//    	if(name.indexOf("/")>0) {
//    		packageName = name.substring(0,name.lastIndexOf("/"));
//    		localName = name.substring(name.lastIndexOf("/")+1);
//    	} else {
//    		packageName = "";
//    		localName = className;
//    	}
//    	System.err.println("Package: "+packageName);
//    	System.err.println("localname: "+localName);
    	try {
//    		fileManager.getFo
			JavaFileObject jfo = fileManager.getJavaFileForInput(StandardLocation.CLASS_OUTPUT, name, Kind.CLASS);
			if(jfo==null) {
				return null;
			}
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			IOUtils.copy(jfo.openInputStream(), baos);
			return baos.toByteArray();
    	} catch (IOException e) {
			e.printStackTrace();
		}
    	return null;
    }
    

	@Override
	protected URL findResource(String res) {
    	String name = res.replaceAll("\\.", "/");
    	logger.error("findResource not yet implemented!");
    	try {
//    		fileManager.getFo
			JavaFileObject jfo = fileManager.getJavaFileForInput(StandardLocation.CLASS_OUTPUT, name, Kind.CLASS);
			if(jfo==null) {
				return null;
			}
    	} catch (IOException e) {
			e.printStackTrace();
		}
		return super.findResource(res);
	}



	@Override
	protected synchronized Class<?> loadClass(String name, boolean resolve)
			throws ClassNotFoundException {
		// TODO Auto-generated method stub
		return super.loadClass(name, resolve);
	}

}
