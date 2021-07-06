/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.osgi.mock;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.cert.X509Certificate;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.Version;

/**
 * Mocks an OSGi BundleContext
 * Only implements those methods needed to make the tests run,
 * whether that behaviour is sufficiently like the OSGi framework is an unanswered question
 */
public class BundleMock implements Bundle
{
    private long id = -1;
    private String location = null;
    private String myName = null;
    private BundleContextMock bundleContext = null;

    public BundleMock( long id, String location, BundleContextMock bundleContext )
    {
        this.id = id;
        this.location = location;
        this.bundleContext = bundleContext;
        // Get a first approximation of the name. This will be overwritten if the DS file could be parsed
        this.myName = stripToFileNameWithoutExtension( location );
    }

    /**
     * Parse the location for our name
     * @param location The location
     * @return our name
     */
    private String stripToFileNameWithoutExtension( String location )
    {
        return location.replaceAll( ".*classes/", "" ).replaceAll( "\\..*", "" );
    }

    /**
     * Helper method to set our name after instantiation (when the DS file was parsed)
     * @param symbolicName The name to set
     */
    public void setSymbolicName( String symbolicName )
    {
        this.myName = symbolicName;
    }

    @Override
    public long getBundleId()
    {
        return id;
    }

    @Override
    public String getLocation()
    {
        return location;
    }

    @Override
    public BundleContext getBundleContext()
    {
        return bundleContext;
    }

    @Override
    public void uninstall() throws BundleException
    {
        bundleContext.uninstallBundle( this );
        bundleContext = null;
    }

    @Override
    public String getSymbolicName()
    {
        return myName;
    }

    @Override
    public int compareTo( Bundle o )
    {
        return Long.compare( id, o.getBundleId() );
    }

    @Override
    public int getState()
    {
        return 0;
    }

    @Override
    public void start( int options ) throws BundleException
    {
    }

    @Override
    public void start() throws BundleException
    {
    }

    @Override
    public void stop( int options ) throws BundleException
    {
    }

    @Override
    public void stop() throws BundleException
    {
    }

    @Override
    public void update( InputStream input ) throws BundleException
    {
        throw new UnsupportedOperationException( "update( InputStream ) unsupported in BundleMock" );
    }

    @Override
    public void update() throws BundleException
    {
        throw new UnsupportedOperationException( "update unsupported in BundleMock" );
    }

    @Override
    public Dictionary<String, String> getHeaders()
    {
        throw new UnsupportedOperationException( "getHeaders unsupported in BundleMock" );
    }

    @Override
    public ServiceReference<?>[] getRegisteredServices()
    {
        throw new UnsupportedOperationException( "getRegisteredServices unsupported in BundleMock" );
    }

    @Override
    public ServiceReference<?>[] getServicesInUse()
    {
        throw new UnsupportedOperationException( "getServicesInUse unsupported in BundleMock" );
    }

    @Override
    public boolean hasPermission( Object permission )
    {
        throw new UnsupportedOperationException( "hasPermission unsupported in BundleMock" );
    }

    @Override
    public URL getResource( String name )
    {
        throw new UnsupportedOperationException( "getResource unsupported in BundleMock" );
    }

    @Override
    public Dictionary<String, String> getHeaders( String locale )
    {
        throw new UnsupportedOperationException( "getHeaders( String ) unsupported in BundleMock" );
    }

    @Override
    public Class<?> loadClass( String name ) throws ClassNotFoundException
    {
        throw new UnsupportedOperationException( "loadClass unsupported in BundleMock" );
    }

    @Override
    public Enumeration<URL> getResources( String name ) throws IOException
    {
        throw new UnsupportedOperationException( "getResources unsupported in BundleMock" );
    }

    @Override
    public Enumeration<String> getEntryPaths( String path )
    {
        throw new UnsupportedOperationException( "getEntryPaths unsupported in BundleMock" );
    }

    @Override
    public URL getEntry( String path )
    {
        throw new UnsupportedOperationException( "getEntry unsupported in BundleMock" );
    }

    @Override
    public long getLastModified()
    {
        throw new UnsupportedOperationException( "getLastModified unsupported in BundleMock" );
    }

    @Override
    public Enumeration<URL> findEntries( String path, String filePattern, boolean recurse )
    {
        throw new UnsupportedOperationException( "findEntries unsupported in BundleMock" );
    }

    @Override
    public Map<X509Certificate, List<X509Certificate>> getSignerCertificates( int signersType )
    {
        throw new UnsupportedOperationException( "getSignerCertificates unsupported in BundleMock" );
    }

    @Override
    public Version getVersion()
    {
        throw new UnsupportedOperationException( "getVersion unsupported in BundleMock" );
    }

    @Override
    public <A> A adapt( Class<A> type )
    {
        throw new UnsupportedOperationException( "adapt unsupported in BundleMock" );
    }

    @Override
    public File getDataFile( String filename )
    {
        throw new UnsupportedOperationException( "getDataFile unsupported in BundleMock" );
    }
}