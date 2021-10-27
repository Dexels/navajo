/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.compiler.tsl.internal;

import java.io.File;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.dexels.navajo.repository.api.AppStoreOperation;
import com.dexels.navajo.repository.api.RepositoryInstance;

/**
 * Mock implementation of the RepositoryInstance interface, which allows one to specify a file 
 * that serves as the applicationFolder root for tests
 */
public class RepositoryInstanceMock implements RepositoryInstance {

    private File applicationFolder;
    
    public RepositoryInstanceMock( File applicationFolder )
    {
        this.applicationFolder = applicationFolder;
    }
    
    @Override
    public File getRepositoryFolder()
    {
        return applicationFolder;
    }

    @Override
    public String getRepositoryName()
    {
        return "mock";
    }

    @Override
    public int compareTo( RepositoryInstance o )
    {
        if ( o instanceof RepositoryInstanceMock )
        {
            return 0;
        }
        else
        {
            return -1;
        }
    }

    @Override
    public File getTempFolder()
    {
        throw new UnsupportedOperationException( "Not implemented by RepositoryInstanceMock" );
    }

    @Override
    public File getOutputFolder()
    {
        throw new UnsupportedOperationException( "Not implemented by RepositoryInstanceMock" );
    }

    @Override
    public Map<String, Object> getSettings()
    {
        throw new UnsupportedOperationException( "Not implemented by RepositoryInstanceMock" );
    }

    @Override
    public void addOperation( AppStoreOperation op, Map<String, Object> settings )
    {
        throw new UnsupportedOperationException( "Not implemented by RepositoryInstanceMock" );
    }

    @Override
    public void removeOperation( AppStoreOperation op, Map<String, Object> settings )
    {
        throw new UnsupportedOperationException( "Not implemented by RepositoryInstanceMock" );
    }

    @Override
    public List<String> getOperations()
    {
        throw new UnsupportedOperationException( "Not implemented by RepositoryInstanceMock" );
    }

    @Override
    public void refreshApplication() throws IOException
    {
        throw new UnsupportedOperationException( "Not implemented by RepositoryInstanceMock" );
    }

    @Override
    public void refreshApplicationLocking() throws IOException
    {
        throw new UnsupportedOperationException( "Not implemented by RepositoryInstanceMock" );
    }

    @Override
    public String repositoryType()
    {
        throw new UnsupportedOperationException( "Not implemented by RepositoryInstanceMock" );
    }

    @Override
    public String applicationType()
    {
        throw new UnsupportedOperationException( "Not implemented by RepositoryInstanceMock" );
    }

    @Override
    public String getDeployment()
    {
        throw new UnsupportedOperationException( "Not implemented by RepositoryInstanceMock" );
    }

    @Override
    public Set<String> getAllowedProfiles()
    {
        throw new UnsupportedOperationException( "Not implemented by RepositoryInstanceMock" );
    }

    @Override
    public Map<String, Object> getDeploymentSettings( Map<String, Object> source )
    {
        throw new UnsupportedOperationException( "Not implemented by RepositoryInstanceMock" );
    }

    @Override
    public boolean requiredForServerStatus()
    {
        throw new UnsupportedOperationException( "Not implemented by RepositoryInstanceMock" );
    }
}
