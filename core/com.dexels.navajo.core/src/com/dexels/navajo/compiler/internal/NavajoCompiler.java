package com.dexels.navajo.compiler.internal;

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

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.legacy.compiler.SunJavaCompiler;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.NavajoConfigInterface;

public class NavajoCompiler
{
		public static final String VERSION = "$Id$";
	
		public String errors;
		
        public void compile(Access access, NavajoConfigInterface config, String source) throws Throwable{

        	try {
				Class.forName("com.sun.tools.javac.Main");
			} catch (ClassNotFoundException e) {
				System.err.println("No sun compiler.");
				throw NavajoFactory.getInstance().createNavajoException("No java compiler found! Is tools.jar in your classpath? Are you using a full JDK?");
//				return;
			}
        	
            String classPath = null;

            String sep = System.getProperty("path.separator");

            String adapterPath = config.getAdapterPath();
            String outputPath = config.getCompiledScriptPath();
            File jarFolder = config.getJarFolder();
            StringBuffer mainCp = new StringBuffer();
            // Find all jar's in adapter path.
            if (jarFolder!=null && jarFolder.exists()) {
				File[] jars = jarFolder.listFiles(new FilenameFilter(){
					public boolean accept(File dir, String name) {
						return name.endsWith(".jar");
					}});
				for (int i = 0; i < jars.length; i++) {
					if (!jars[i].exists()) {
						System.err.println("JAR: "+jars[i]+" does not exist!");
					
					} else {
						boolean valid = isValidZip(jars[i]);
						if(valid) {
							if (mainCp.length()>0) {
								mainCp.append(sep);
							}
							mainCp.append(jars[i].getAbsolutePath());
							
						}
					}
				}
				classPath = mainCp.toString() + sep + jarFolder.getAbsolutePath() + "/../classes";
			} else {
				 classPath = config.getClassPath();
		           
			}
            
            
            
            File [] files = config.getClassloader().getJarFiles(adapterPath, false);
            StringBuffer additional = new StringBuffer();
            if (files != null) {
              for (int i = 0; i < files.length; i++) {
                additional.append(sep + files[i].getAbsolutePath());
              }
            }
      
            
            classPath += additional.toString();

            //System.err.println("in NavajoCompiler(): new classPath = " + classPath);
            
            SunJavaCompiler compiler = new SunJavaCompiler();

            compiler.setClasspath(classPath);
            compiler.setOutputDir(outputPath);
            compiler.setClassDebugInfo(true);
            compiler.setEncoding("UTF8");
            StringWriter bos = new StringWriter();
            compiler.setOutputWriter(bos);
            compiler.compile(source);
            bos.close();
            errors = bos.toString();
        }
        
        private boolean isValidZip(final File file) {
      	    ZipFile zipfile = null;
      	    try {
      	        zipfile = new ZipFile(file);
      	        return true;
      	    } catch (ZipException e) {
      	        return false;
      	    } catch (IOException e) {
      	        return false;
      	    } finally {
      	        try {
      	            if (zipfile != null) {
      	                zipfile.close();
      	                zipfile = null;
      	            }
      	        } catch (IOException e) {
      	        }
      	    }
      	}

}