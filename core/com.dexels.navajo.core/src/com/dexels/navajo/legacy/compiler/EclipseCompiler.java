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
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class EclipseCompiler implements JavaCompiler {

	
	private final static Logger logger = LoggerFactory
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
        // not used by the SunJavaCompiler
	//this.compilerPath = compilerPath;
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
	public boolean compile(ArrayList elements) {
//        System.err.println("Compiling: "+elements.size()+" elements");
//        System.err.println("Classpath: "+classpath.replace(';', '\n'));
        try {
            if (compilerClass==null) {
                if( loader==null )
                    compilerClass = Class.forName("org.eclipse.jdt.internal.compiler.batch.Main");
                else
                    compilerClass=loader.loadClass("org.eclipse.jdt.internal.compiler.batch.Main");
                
            }
        Method compile = compilerClass.getMethod("main",new Class [] { String[].class});
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
      for (int i = 0; i < args.length; i++) {
        newArgs[i] = args[i];
    }
      for (int i = 0; i < elements.size(); i++) {
        newArgs[i+args.length] = (String)elements.get(i);
    }
//    PrintWriter w = new PrintWriter(out);
      compile.invoke(null, new Object[] {newArgs});
//      w.close();
//      errWriter.close();
//      System.err.println("ERR: "+err.toString());
      
      
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
                                         new Class [] { String[].class});

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
            System.err.println("Arguments:");
            for (int i = 0; i < args.length; i++) {
                System.err.println(">"+args[i]+"<");
            }
//            PrintWriter w = new PrintWriter(out);
            compile.invoke(null, new Object[] {args});
//            w.close();
//            errWriter.close();
//            System.err.println("ERR: "+err.toString());
            
            
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
