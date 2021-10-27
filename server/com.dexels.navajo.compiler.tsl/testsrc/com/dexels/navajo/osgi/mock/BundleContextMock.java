/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.osgi.mock;

import java.io.File;
import java.io.InputStream;
import java.util.Collection;
import java.util.Dictionary;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.BundleListener;
import org.osgi.framework.Filter;
import org.osgi.framework.FrameworkListener;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceFactory;
import org.osgi.framework.ServiceListener;
import org.osgi.framework.ServiceObjects;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

/**
 * Mocks an OSGi BundleContext
 * Only implements those methods needed to make the tests run,
 * whether that behaviour is sufficiently like the OSGi framework is an unanswered question
 *
 */
public class BundleContextMock implements BundleContext {
    private Bundle myBundle = null;
    private OSGiFrameworkMock myFramework = null;
    
    public BundleContextMock( OSGiFrameworkMock myFramework )
    {
        this.myFramework = myFramework;
    }

    /**
     * Uninstalls the given bundle from the framework and from myself if it's my bundle
     * 
     * @param bundle The bundle to uninstall
     */
    void uninstallBundle( Bundle bundle )
    {
        myFramework.uninstallBundle( bundle );
        if ( myBundle != null && myBundle.compareTo( bundle ) == 0 )
        {
            this.myBundle = null;
            this.myFramework = null;
        }
    }
    
    /**
     * Helper method to set the bundle we are handling
     * 
     * @param bundle My bundle
     */
    void setBundle( Bundle bundle )
    {
        this.myBundle = bundle;
    }

    @Override
    public Bundle getBundle()
    {
        return myBundle;
    }

    @Override
    public Bundle installBundle( String location, InputStream input ) throws BundleException
    {
        return myFramework.installBundle( location, input );
    }

    @Override
    public Bundle installBundle( String location ) throws BundleException
    {
        return myFramework.installBundle( location );
    }

    @Override
    public Bundle getBundle( long id )
    {
        return myFramework.getBundle( id );
    }

    @Override
    public Bundle[] getBundles()
    {
        return myFramework.getBundles();
    }

    @Override
    public Bundle getBundle( String location )
    {
        return myFramework.getBundle( location );
    }

    @Override
    public <S> Collection<ServiceReference<S>> getServiceReferences( Class<S> clazz, String filter ) throws InvalidSyntaxException
    {
        return myFramework.getServiceReferences( clazz, createFilter( filter ) );
    }

    @Override
    public ServiceReference<?>[] getServiceReferences( String clazz, String filter ) throws InvalidSyntaxException
    {
        return myFramework.getServiceReferences( clazz, createFilter( filter ) );
    }

    @Override
    public <S> S getService( ServiceReference<S> reference )
    {
        if( reference instanceof ServiceReferenceMock<?> )
        {
            return ((ServiceReferenceMock<S>) reference).getService();
        }
        else
        {
            return null;
        }
    }

    @Override
    public Filter createFilter( String filter ) throws InvalidSyntaxException
    {
        return FrameworkUtil.createFilter( filter );
    }

    // Start of not supported methods
    @Override
    public File getDataFile( String filename )
    {
        throw new UnsupportedOperationException( "getDataFile unsupported in BundleContextMock" );
    }

    @Override
    public String getProperty( String key )
    {
        throw new UnsupportedOperationException( "getProperty unsupported in BundleContextMock" );
    }

    @Override
    public <S> ServiceObjects<S> getServiceObjects( ServiceReference<S> reference )
    {
        throw new UnsupportedOperationException( "getServiceObject unsupported in BundleContextMock" );
    }

    @Override
    public boolean ungetService( ServiceReference<?> reference )
    {
        throw new UnsupportedOperationException( "ungetService unsupported in BundleContextMock" );
    }

    @Override
    public <S> ServiceReference<S> getServiceReference( Class<S> clazz )
    {
        throw new UnsupportedOperationException( "getServiceReference( Class ) unsupported in BundleContextMock" );
    }
    
    @Override
    public void addServiceListener( ServiceListener listener, String filter ) throws InvalidSyntaxException
    {
        throw new UnsupportedOperationException( "addServiceListener unsupported in BundleContextMock" );
    }

    @Override
    public void addServiceListener( ServiceListener listener )
    {
        throw new UnsupportedOperationException( "addServiceListener unsupported in BundleContextMock" );
    }

    @Override
    public void removeServiceListener( ServiceListener listener )
    {
        throw new UnsupportedOperationException( "removeServiceListener unsupported in BundleContextMock" );
    }

    @Override
    public void addBundleListener( BundleListener listener )
    {
        throw new UnsupportedOperationException( "addBundleListener unsupported in BundleContextMock" );
    }

    @Override
    public void removeBundleListener( BundleListener listener )
    {
        throw new UnsupportedOperationException( "removeBundleListener unsupported in BundleContextMock" );
    }

    @Override
    public void addFrameworkListener( FrameworkListener listener )
    {
        throw new UnsupportedOperationException( "addFrameworkListener unsupported in BundleContextMock" );
    }

    @Override
    public void removeFrameworkListener( FrameworkListener listener )
    {
        throw new UnsupportedOperationException( "removeFrameworkListener unsupported in BundleContextMock" );
    }

    @Override
    public ServiceRegistration<?> registerService( String[] clazzes, Object service, Dictionary<String, ?> properties )
    {
        throw new UnsupportedOperationException( "registerService( String[], Object, Dictionary ) unsupported in BundleContextMock" );
    }

    @Override
    public ServiceRegistration<?> registerService( String clazz, Object service, Dictionary<String, ?> properties )
    {
        throw new UnsupportedOperationException( "registerService( String, Object, Dictionary ) unsupported in BundleContextMock" );
    }

    @Override
    public <S> ServiceRegistration<S> registerService( Class<S> clazz, S service, Dictionary<String, ?> properties )
    {
        throw new UnsupportedOperationException( "registerService( Class, S, Dictionary ) unsupported in BundleContextMock" );
    }

    @Override
    public <S> ServiceRegistration<S> registerService( Class<S> clazz, ServiceFactory<S> factory, Dictionary<String, ?> properties )
    {
        throw new UnsupportedOperationException( "registerService( Class, ServiceFActory, Dictionary ) unsupported in BundleContextMock" );
    }

    @Override
    public ServiceReference<?>[] getAllServiceReferences( String clazz, String filter ) throws InvalidSyntaxException
    {
        throw new UnsupportedOperationException( "getAllServiceReferences unsupported in BundleContextMock" );
    }

    @Override
    public ServiceReference<?> getServiceReference( String clazz )
    {
        throw new UnsupportedOperationException( "getServiceReference( String ) unsupported in BundleContextMock" );
    }
}
