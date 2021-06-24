/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
/*
 * Created on Jun 21, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.legacy.compiler;

import java.io.OutputStream;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
@SuppressWarnings("rawtypes")
public class EclipseCompiler implements JavaCompiler {

	
	private static final Logger logger = LoggerFactory
			.getLogger(EclipseCompiler.class);
    String encoding;
    String classpath; // ignored
    String compilerPath;
    String outdir;
    StringWriter out;
    boolean classDebugInfo=false;

    ClassLoader loader=null;
	private Class<?> compilerClass;

    @Override
	public void setCompilerClass(Class c) {
        compilerClass = c;
    }
    
    /**
     * Specify where the compiler can be found
     */
    @Override
	public void setCompilerPath(String compilerPath) {
    }

    /**
     * Set the encoding (character set) of the source
     */
    @Override
	public void setEncoding(String encoding) {
      this.encoding = encoding;
    }

    /**
     * Set the class path for the compiler
     */
    @Override
	public void setClasspath(String classpath) {
      this.classpath = classpath;
    }

    /**
     * Set the output directory
     */
    @Override
	public void setOutputDir(String outdir) {
      this.outdir = outdir;
    }

    /**
     * Set where you want the compiler output (messages) to go
     */
    @Override
	public void setMsgOutput(OutputStream out) {
 
    }


    @Override
	public void setOutputWriter(StringWriter out) {
    	this.out = out;
    }
    
    /**
     * Set where you want the compiler output (messages) to go
     * @param out 
     */
    public void setOut(OutputStream out) {
       
    }

    /**
     * Set if you want debugging information in the class file
     */
    @Override
	public void setClassDebugInfo(boolean classDebugInfo) {
        this.classDebugInfo = classDebugInfo;
    }

    @Override
	public void setCompileClassLoader( ClassLoader cl  ) {
        loader=cl;
    }

	@Override
	public boolean compile(List<String> elements) {
        try {
            if (compilerClass==null) {
                if( loader==null )
                    compilerClass = Class.forName("org.eclipse.jdt.internal.compiler.batch.Main");
                else
                    compilerClass=loader.loadClass("org.eclipse.jdt.internal.compiler.batch.Main");
                
            }
        Method compile = compilerClass.getMethod("main",String[].class);
       String[] args;
      if (classDebugInfo) {
          args = new String[]
              {
                  "-g",
                  "-encoding", encoding,
                  "-classpath", classpath,
                  "-d", outdir, "-nowarn", "-noExit", "-time","-proceedOnError" //"-Xlint:unchecked",
                  
              };
      } else {
          args = new String[]
              {
                  "-encoding", encoding,
                  "-classpath", classpath,
                  "-d", outdir, "-nowarn", "-noExit", "-time","-proceedOnError" //"-Xlint:unchecked",
                  
              };
      }
      String[] newArgs = new String[args.length+elements.size()];
      System.arraycopy(args, 0, newArgs, 0, args.length);
     
      for (int i = 0; i < elements.size(); i++) {
        newArgs[i+args.length] = elements.get(i);
    }
      compile.invoke(null, (Object[])newArgs);
      
      return true;
       }
        catch (ClassNotFoundException ex) {
        	logger.error("Error: ", ex);
            return false;
        }
        catch (Exception ex1) {
        	logger.error("Error: ", ex1);
        	return false;
        }
    
    }
    
	@Override
	public boolean compile(String source) {

        try {
            Class<?> c;
            if( loader==null )
                c = Class.forName("org.eclipse.jdt.internal.compiler.batch.Main");
            else
                c=loader.loadClass("org.eclipse.jdt.internal.compiler.batch.Main");

            Method compile = c.getMethod("main",
                                         String[].class);

            String[] args;
            if (classDebugInfo) {
                args = new String[]
                    {
                        "-g",
                        "-encoding", encoding,
                        "-classpath", classpath,
                        "-d", outdir, "-nowarn", "-noExit", //"-Xlint:unchecked",
                        source
                    };
            } else {
                args = new String[]
                    {
                        "-encoding", encoding,
                        "-classpath", classpath,
                        "-d", outdir, "-nowarn", "-noExit", //"-Xlint:unchecked",
                        source
                    };
            }
            compile.invoke(null, (Object[])args);
            return true;
        }
        catch (ClassNotFoundException ex) {
        	logger.error("Error: ", ex);
        	return false;
        }
        catch (Exception ex1) {
        	logger.error("Error: ", ex1);
        	return false;
        }
    }


}
