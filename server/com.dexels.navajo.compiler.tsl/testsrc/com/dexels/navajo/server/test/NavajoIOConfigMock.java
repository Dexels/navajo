/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.server.test;

import java.io.File;
import com.dexels.navajo.server.FileNavajoConfig;

/**
 * Mock version of the NavajoIOConfig path that allows one to specify a rootPath 
 */
public class NavajoIOConfigMock extends FileNavajoConfig {

    private File rootPath = null;
    
    public NavajoIOConfigMock( File rootPath )
    {
        this.rootPath = rootPath;
    }
    
    @Override
    public String getConfigPath()
    {
        return new File( rootPath, "config" ).getAbsolutePath();
    }


    @Override
    public String getAdapterPath()
    {
        return new File( rootPath, "adapters" ).getAbsolutePath();
    }


    @Override
    public String getScriptPath()
    {
        return new File( rootPath, "scripts" ).getAbsolutePath();
    }

    @Override
    public String getCompiledScriptPath()
    {
        return new File( rootPath, "classes" ).getAbsolutePath();
    }

    
    @Override
    public String getRootPath()
    {
        return rootPath.getAbsolutePath();
    }

    @Override
    public File getContextRoot()
    {
        return null;
    }

    @Override
    public String getResourcePath()
    {
        return new File( rootPath, "resources" ).getAbsolutePath();
    }

    @Override
    public String getDeployment()
    {
        return "mock";
    }
}
