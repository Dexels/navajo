package com.dexels.navajo.compiler;

/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author Arjen Schoneveld
 * @version $Id$
 *
 * DISCLAIMER
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL DEXELS BV OR ITS CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY,
 * OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA,
 * OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
 * TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
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

            //System.out.println("in NavajoCompiler(): new classPath = " + classPath);

            JavaCompiler compiler = new SunJavaCompiler();

            compiler.setClasspath(classPath);
            compiler.setOutputDir(outputPath);
            compiler.setClassDebugInfo(true);
            compiler.setEncoding("UTF8");
            compiler.setMsgOutput(System.out);
            compiler.compile(source);
        }

}