package com.dexels.navajo.compiler;

/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author Arjen Schoneveld
 * @version 1.0
 */
import com.dexels.navajo.server.NavajoConfig;
import com.dexels.navajo.server.Access;

import org.apache.jasper.compiler.*;

import java.lang.reflect.*;
import java.util.*;
import java.io.*;

public class NavajoCompiler
{
        public void compile(Access access, NavajoConfig config, String source) throws Throwable{

            String classPath = config.getClassPath();
            String sep = System.getProperty("path.separator");

            String adapterPath = config.getAdapterPath();
            String outputPath = config.getCompiledScriptPath();

            // Find all jar's in adapter path.
            File [] files = config.getClassloader().getJarFiles(adapterPath, access.betaUser);
            StringBuffer additional = new StringBuffer();
            if (files != null) {
              for (int i = 0; i < files.length; i++) {
                additional.append(sep + files[i].getAbsolutePath());
              }
            }

            classPath += additional.toString();

            System.out.println("in NavajoCompiler(): new classPath = " + classPath);

            JavaCompiler compiler = new SunJavaCompiler();

            compiler.setClasspath(classPath);
            compiler.setOutputDir(outputPath);
            compiler.setClassDebugInfo(true);
            compiler.setEncoding("UTF8");
            compiler.setMsgOutput(System.out);
            compiler.compile(source);
        }

}