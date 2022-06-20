/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.compiler.tsl;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import org.junit.Assert;
import org.junit.Test;

import com.dexels.navajo.script.api.CompilationException;
import com.dexels.osgicompiler.internal.OSGiJavaCompilerImplementation;

public class TestWrappedCompiler {

	@Test
	public void testWrapped() throws IOException, CompilationException {
		OSGiJavaCompilerImplementation o = new OSGiJavaCompilerImplementation();
		o.activateCompiler(null, null);
		InputStream is = TestWrappedCompiler.class.getClassLoader().getResourceAsStream("Example.java.txt");
		byte[] result = o.compile("Example", is);
		System.err.println("result: "+result.length);
		Assert.assertTrue(result.length>100);
	}
	
	public static void main(String[] args) throws IOException, CompilationException {
		OSGiJavaCompilerImplementation o = new OSGiJavaCompilerImplementation();
		o.activateCompiler(null, null);
		InputStream is = TestWrappedCompiler.class.getClassLoader().getResourceAsStream("Example.java.txt");
		byte[] result = o.compile("Example", is);
		System.err.println("result: "+result.length);
		Assert.assertTrue(result.length>100);		
	}
	@Test
	public void testPackageCompile() throws IOException, CompilationException {
		OSGiJavaCompilerImplementation o = new OSGiJavaCompilerImplementation();
		o.activateCompiler(null, null);
		InputStream is = TestWrappedCompiler.class.getClassLoader().getResourceAsStream("ExampleInPackage.java.txt");
		byte[] result = o.compile("somepkg/ExampleInPackage", is);
		System.err.println("result: "+result.length);
		Assert.assertTrue(result.length>100);
		
	}
	@SuppressWarnings("unchecked")
	@Test
	public void testActualClassLoad() throws Exception {
		OSGiJavaCompilerImplementation o = new OSGiJavaCompilerImplementation();
		o.activateCompiler(null, null);
		InputStream is = TestWrappedCompiler.class.getClassLoader().getResourceAsStream("Example.java.txt");
		byte[] result = o.compile("Example", is);
		Map<String,byte[]> definitions = new HashMap<>();
		definitions.put("Example", result);
		ByteClassLoader bcl = new ByteClassLoader(new URL[] {}, this.getClass().getClassLoader(), definitions);
		Class<Callable<Integer>> clz = (Class<Callable<Integer>>) Class.forName("Example",true,bcl);
		
		Callable<Integer> c = clz.getDeclaredConstructor().newInstance();
		int i = c.call();
		Assert.assertEquals(13, i);


	}
	
	  public static class ByteClassLoader extends URLClassLoader {
		    private final Map<String, byte[]> extraClassDefs;

		    public ByteClassLoader(URL[] urls, ClassLoader parent, Map<String, byte[]> extraClassDefs) {
		      super(urls, parent);
		      this.extraClassDefs = new HashMap<String, byte[]>(extraClassDefs);
		    }

		    @Override
		    protected Class<?> findClass(final String name) throws ClassNotFoundException {
		      byte[] classBytes = this.extraClassDefs.remove(name);
		      if (classBytes != null) {
		        return defineClass(name, classBytes, 0, classBytes.length); 
		      }
		      return super.findClass(name);
		    }

		  }
}
