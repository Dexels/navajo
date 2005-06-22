/*
 * $Header$
 * $Revision$
 * $Date$
 *
 * ====================================================================
 *
 * The Apache Software License, Version 1.1
 *
 * Copyright (c) 1999 The Apache Software Foundation.  All rights
 * reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution, if
 *    any, must include the following acknowlegement:
 *       "This product includes software developed by the
 *        Apache Software Foundation (http://www.apache.org/)."
 *    Alternately, this acknowlegement may appear in the software itself,
 *    if and wherever such third-party acknowlegements normally appear.
 *
 * 4. The names "The Jakarta Project", "Tomcat", and "Apache Software
 *    Foundation" must not be used to endorse or promote products derived
 *    from this software without prior written permission. For written
 *    permission, please contact apache@apache.org.
 *
 * 5. Products derived from this software may not be called "Apache"
 *    nor may "Apache" appear in their names without prior written
 *    permission of the Apache Group.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 */

package org.apache.jasper.compiler;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.util.*;

//import com.sun.tools.javac.Main;

/**
 * The default compiler. This is the javac present in JDK 1.1.x and
 * JDK 1.2.
 *
 * At some point we need to make sure there is a class like this for
 * JDK 1.3, and other javac-like animals that people want to use.
 *
 * @author Anil K. Vijendran
 */
public class SunJavaCompiler implements JavaCompiler {

    String encoding;
    String classpath; // ignored
    String compilerPath;
    String outdir;
    StringWriter out;
    boolean classDebugInfo=false;

    /**
     * Specify where the compiler can be found
     */
    public void setCompilerPath(String compilerPath) {
        // not used by the SunJavaCompiler
	this.compilerPath = compilerPath;
    }

    /**
     * Set the encoding (character set) of the source
     */
    public void setEncoding(String encoding) {
      this.encoding = encoding;
    }

    /**
     * Set the class path for the compiler
     */
    public void setClasspath(String classpath) {
      this.classpath = classpath;
    }

    /**
     * Set the output directory
     */
    public void setOutputDir(String outdir) {
      this.outdir = outdir;
    }

    /**
     * Set where you want the compiler output (messages) to go
     */
    public void setMsgOutput(OutputStream out) {
 
    }

    public void setOutputWriter(StringWriter out) {
    	this.out = out;
    }
    
    /**
     * Set where you want the compiler output (messages) to go
     */
    public void setOut(OutputStream out) {
       
    }

    /**
     * Set if you want debugging information in the class file
     */
    public void setClassDebugInfo(boolean classDebugInfo) {
        this.classDebugInfo = classDebugInfo;
    }

    ClassLoader loader=null;
    public void setLoader( ClassLoader cl  ) {
        loader=cl;
    }

    public boolean compile(String source) {

        try {
            Class c;
            if( loader==null )
                c = Class.forName("com.sun.tools.javac.Main");
            else
                c=loader.loadClass("com.sun.tools.javac.Main");

            Object compiler = c.newInstance();

            // Call the compile() method
            Method compile = c.getMethod("compile",
                                         new Class [] { String[].class, PrintWriter.class });

            String[] args;
//            Main m = null;
            if (classDebugInfo) {
                args = new String[]
                    {
                        "-g",
                        "-encoding", encoding,
                        "-classpath", classpath,
                        "-d", outdir,
                        source
                    };
            } else {
                args = new String[]
                    {
                        "-encoding", encoding,
                        "-classpath", classpath,
                        "-d", outdir,
                        source
                    };
            }
            PrintWriter w = new PrintWriter(out);
            compile.invoke(compiler, new Object[] {args, w});
            w.close();
            
            return true;
        }
        catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            return false;
        }
        catch (Exception ex1) {
            ex1.printStackTrace();
            return false;
        }
    }

    public static void main(String [] args) throws Exception {
      String classPath = System.getProperty("java.class.path");
      ///System.out.println("classPath = " + classPath);
      classPath += ":/home/arjen/projecten/sportlink-serv/navajo-tester/WEB-INF/lib/sportlink_functions.jar";
      System.setProperty("java.class.path", classPath);
      //System.out.println("new classPath = " + System.getProperty("java.class.path"));

      JavaCompiler compiler = new SunJavaCompiler();
      compiler.setClasspath(classPath);
      compiler.setOutputDir("/home/arjen/projecten/sportlink-serv/navajo-tester/auxilary/navajo/adapters/work/");
      compiler.setClassDebugInfo(true);
      compiler.setEncoding("UTF8");
      compiler.setMsgOutput(System.out);
      compiler.compile("/home/arjen/projecten/sportlink-serv/navajo-tester/auxilary/navajo/adapters/work/InitUpdateClub.java");

    }

    public boolean compile(ArrayList elements) {
        throw new UnsupportedOperationException("Unsupported operation. You can implement it if you want");
    }
    
}
