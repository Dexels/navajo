/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.osgi.mock;

import java.util.Collections;
import java.util.Dictionary;
import java.util.Hashtable;

import org.osgi.framework.Bundle;
import org.osgi.framework.ServiceReference;

import com.dexels.navajo.script.api.CompiledScriptFactory;
import com.dexels.navajo.script.api.CompiledScriptFactoryMock;

/**
 * Mocks an OSGi ServiceReference
 * Only implements those methods needed to make the tests run,
 * whether that behaviour is sufficiently like the OSGi framework is an unanswered question
 */
public class ServiceReferenceMock<S> implements ServiceReference<S> {

    private Bundle myBundle = null;
    private Dictionary<String, Object> properties = new Hashtable<String, Object>();
    private final Class<S> type;

    public ServiceReferenceMock( Class<S> type, Bundle bundle )
    {
        this.type = type;
        this.myBundle = bundle;
    }
    
    public void addProperty( String key, Object value )
    {
        properties.put( key, value );
    }

    public S getService()
    {
        if( type.equals( CompiledScriptFactory.class ) )
        {
            return type.cast( new CompiledScriptFactoryMock( myBundle.getSymbolicName() ) );
        }
        return null;
    }
    
    public Class<S> getType()
    {
        return type;
    }

    @Override
    public Object getProperty( String key )
    {
        return properties.get( key );
    }

    @Override
    public String[] getPropertyKeys()
    {
        return Collections.list( properties.keys() ).toArray( new String[properties.size()] );
    }

    @Override
    public Bundle getBundle()
    {
        return myBundle;
    }

    @Override
    public Bundle[] getUsingBundles()
    {
        return new Bundle[] { myBundle };
    }

    @Override
    public boolean isAssignableTo( Bundle bundle, String className )
    {
        throw new UnsupportedOperationException( "isAssignableTo not supported in ServiceReferenceMock" );
    }

    @SuppressWarnings( "rawtypes" )
    @Override
    public int compareTo( Object reference )
    {
        if( reference instanceof ServiceReferenceMock )
        {
            return myBundle.compareTo( ((ServiceReferenceMock) reference).getBundle() );
        }
        else
        {
            throw new UnsupportedOperationException( "compareTo a non-ServiceReferenceMock is not supported" );
        }
    }

    @Override
    public Dictionary<String, Object> getProperties()
    {
        return properties;
    }
}
