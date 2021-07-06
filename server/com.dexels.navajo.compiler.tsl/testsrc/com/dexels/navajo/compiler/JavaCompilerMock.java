/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.compiler;

import java.io.File;

import com.dexels.navajo.server.NavajoIOConfig;

/**
 * Mock version of the Java compiler which does not compile, only creates an empty class file 
 *
 */
public class JavaCompilerMock implements JavaCompiler {

    private NavajoIOConfig navajoIOConfig = null;

    public JavaCompilerMock( NavajoIOConfig navajoIOConfig )
    {
        this.navajoIOConfig = navajoIOConfig;
    }

    /**
     * Only write an empty file so it can be picked up further along in the process
     */
    @Override
    public void compileJava( String script ) throws Exception
    {
        // alter the path dir for the compiler:
        if( script.indexOf( '/' ) == -1 )
        {
            script = "defaultPackage/" + script;
        }
        // create an empty class file
        File file = new File( navajoIOConfig.getCompiledScriptPath() + "/" + script + ".class" );
        file.getParentFile().mkdirs();
        file.createNewFile();
    }

}
