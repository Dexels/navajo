/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.osgi.mock;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipFile;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleException;
import org.osgi.framework.Filter;
import org.osgi.framework.ServiceReference;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.dexels.navajo.script.api.CompiledScriptFactory;

/**
 * Central class for the mocking of the OSGi framework.
 * Should be shared by all OSGi related mocking classes in one test 
 *
 */
public class OSGiFrameworkMock {
    
    private long highestUsedId = 0;
    
    private Map<String, Bundle> installedBundlesBySymbolicName = new HashMap<>();
    private List<ServiceReferenceMock<?>> serviceReferences = new ArrayList<>();
    
    /**
     * Mimics the installing of a bundle. Ignores the inputStream
     * 
     * @param location The string describing the location of the jar file (an URI is assumed)
     * @param input    Mostly ignored, is closed as part of the implementation
     * @return         The created Bundle object
     * @throws BundleException
     */
    public Bundle installBundle( String location, InputStream input ) throws BundleException
    {
        Bundle bundle = installBundle( location );
        try
        {
            input.close();
        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }
        return bundle;
    }

    /**
     * Mimics the installing of a bundle using the various Mock classes
     *  
     * @param location The string describing the location of the jar file (an URI is assumed)
     * @return         The created Bundle object
     * @throws BundleException
     */
    public Bundle installBundle( String location ) throws BundleException
    {
        long myId = highestUsedId++;
        BundleMock bundle = new BundleMock( myId, location, new BundleContextMock( this ) );
        Bundle newBundle = bundle;
        ((BundleContextMock) newBundle.getBundleContext()).setBundle( newBundle );
        
        installedBundlesBySymbolicName.put( newBundle.getSymbolicName(), newBundle );
        
        parseDSFileAndAddServiceReference( location, bundle );

        return newBundle;
    }

    /**
     * Helper function to parse the generated DS file and create the necessary ServiceReference object, which is then registered in this class
     * Only supports ServiceReference to CompiledScriptFactory 
     * 
     * If something goes wrong trying to access the DS file, the service reference is still created but will not contain any properties
     * 
     * @param location The string describing the location of the jar file
     * @param bundle   The bundle for which to create the service reference
     */
    private void parseDSFileAndAddServiceReference( String location, BundleMock bundle ) throws BundleException
    {
        ServiceReferenceMock<CompiledScriptFactory> serviceReference = new ServiceReferenceMock<>( CompiledScriptFactory.class, bundle );
        serviceReferences.add( serviceReference );
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        builderFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
        builderFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");

        // Find the DS file in the jar and parse it for the properties
        try( ZipFile jarFile = new ZipFile( Paths.get( URI.create( location ) ).toFile() ) )
        {
            InputStream source = jarFile.getInputStream( jarFile.getEntry( "OSGI-INF/script.xml" ) );
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document document = builder.parse(source);
            document.normalize();
            
            NodeList components = document.getElementsByTagName( "scr:component" );
            if ( components == null || components.getLength() != 1
                    || !( components.item(0) instanceof Element ) )
            {
                throw new BundleException( "Structure of DS file not as expected" );
            }
            Element component = (Element) components.item(0);
            String symbolicName = component.getAttribute( "name" );
            if( symbolicName != null )
            {
                bundle.setSymbolicName( symbolicName );
            }
            
            NodeList nodes = component.getChildNodes();
            
            for ( int i = 0; i < nodes.getLength(); i++ )
            {
                Node n = nodes.item(i);
                if ( n instanceof Element )
                {
                    Element e = (Element) n;
                    if ( e.getTagName().equals("property") )
                    {
                        serviceReference.addProperty( e.getAttribute( "name" ), e.getAttribute( "value" ) );
                    }
                }
            }
            
        }
        catch ( Exception e )
        {
            // then we don't do properties :(
            e.printStackTrace();
        }
        
    }

    /**
     * Helper method to get all installed bundles
     *
     * @return         The array of all installed bundles
     */
    public Bundle[] getBundles()
    {
        Collection<Bundle> installedBundles = installedBundlesBySymbolicName.values();
        return installedBundles.toArray( new Bundle[installedBundles.size()] );
    }

    /**
     * Helper method to get a bundle by id
     *
     * @param id  The id
     * @return    The bundle if found, else null
     */
    public Bundle getBundle( long id )
    {
        for( Bundle bundle : installedBundlesBySymbolicName.values() )
        {
            if( bundle.getBundleId() == id )
            {
                return bundle;
            }
        }
        return null;
    }

    /**
     * Helper method to get a bundle by location
     *
     * @param location The location
     * @return         The bundle if found, else null
     */
    public Bundle getBundle( String location )
    {
        if( location != null )
        {
            for( Bundle bundle : installedBundlesBySymbolicName.values() )
            {
                if( location.equals( bundle.getLocation() ) )
                {
                    return bundle;
                }
            }
        }
        return null;
    }

    /**
     * Main entry method for tests to verify whether a bundle is there or not
     * 
     * @param symbolicName The name under which a bundle is known ( path/scriptName or path/scriptName_tenant )
     * @return The bundle if found, else null
     */
    public Bundle getBundleByName( String symbolicName )
    {
        return installedBundlesBySymbolicName.get( symbolicName );
    }
    
    /**
     * Helper method to retrieve a servicereference based on class and filter
     */
    public <S> Collection<ServiceReference<S>> getServiceReferences( Class<S> clazz, Filter filter )
    {
        Collection<ServiceReference<S>> result = new ArrayList<>();
        for( ServiceReferenceMock<?> serviceReference : serviceReferences )
        {
            if( clazz.equals( serviceReference.getType() ) )
            {
                // check the filter
                if( filter.match( serviceReference ) )
                {
                    result.add( (ServiceReference<S>) serviceReference );
                }
            }
        }
        return result;
    }

    /**
     * Helper method to retrieve a servicereference based on class and filter
     * This one is actually used from our implementation classes
     */
    public ServiceReference<?>[] getServiceReferences( String clazz, Filter filter )
    {
        ArrayList<ServiceReference<?>> result = new ArrayList<>();
        for( ServiceReferenceMock<?> serviceReference : serviceReferences )
        {
            if( clazz.equals( serviceReference.getType().getName() ) )
            {
                // check the filter
                if( filter.match( serviceReference ) )
                {
                    result.add( serviceReference );
                }
            }
        }
        return result.toArray( new ServiceReference<?>[result.size()] );
    }

    /**
     * Mimics uninstalling a bundle
     * 
     * @param bundle The bundle to uninstall
     */
    public void uninstallBundle( Bundle bundle )
    {
        installedBundlesBySymbolicName.remove( bundle.getSymbolicName() );
        serviceReferences.removeIf( b -> b.getBundle().compareTo( bundle ) == 0 );
    }

    /**
     * Helper method to remove all registered bundles and related service references
     */
    public void clean()
    {
        installedBundlesBySymbolicName.clear();
        serviceReferences.clear();
    }
}
