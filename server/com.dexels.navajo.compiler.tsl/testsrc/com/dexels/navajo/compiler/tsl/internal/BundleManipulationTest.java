/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.compiler.tsl.internal;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.CopyOption;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.rules.TestName;
import org.osgi.framework.BundleContext;
import org.osgi.service.event.Event;

import com.dexels.navajo.compiler.JavaCompiler;
import com.dexels.navajo.compiler.JavaCompilerMock;
import com.dexels.navajo.dependency.DependencyAnalyzer;
import com.dexels.navajo.osgi.mock.BundleContextMock;
import com.dexels.navajo.osgi.mock.OSGiFrameworkMock;
import com.dexels.navajo.repository.api.RepositoryInstance;
import com.dexels.navajo.server.NavajoIOConfig;
import com.dexels.navajo.server.test.NavajoIOConfigMock;

/**
 * This test suite tests the interaction of the BundleQueueComponent, the BundleCreatorComponent, the TslScriptCompiler, the DependencyAnalyzer (com.dexels.navajo.core) and the TslPreCompiler (com.dexels.navajo.core)
 *
 */
public class BundleManipulationTest {

    private static final String REPOSITORY_CHANGE_EVENT_TYPE = "repository/change";
    private static final String ENTRY_CHANGE_TYPE_ADD = "ADD";
    private static final String ENTRY_CHANGE_TYPE_COPY = "COPY";
    private static final String ENTRY_CHANGE_TYPE_DELETE = "DELETE";
    private static final String ENTRY_CHANGE_TYPE_MODIFY = "MODIFY";
    private static final String REPOSITORY_KEY = "repository";
    private static final String REPOSITORY_NAME_KEY = "repositoryName";

    private BundleQueueComponent bundleQueue = null;
    private BundleCreatorComponent bundleCreator = null;
    private DependencyAnalyzer depAnalyzer = null;
    private TslScriptCompiler tslScriptCompiler = null;

    private OSGiFrameworkMock frameworkMock = null;
    private BundleContext bundleContextMock = null;
    private NavajoIOConfig navajoIOConfigMock = null;
    private JavaCompiler javaCompilerMock = null;
    private RepositoryInstance repositoryInstanceMock = null;

    private File tempFolder = null;

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Rule
    public TestName testName = new TestName();

    @Before
    public void setUp() throws URISyntaxException, IOException
    {
       bundleQueue = new BundleQueueComponent( true ); // force synchronized compilation so we can actually check on this thing
       tempFolder = folder.newFolder();
       // copy the files in testserver to the temporary folder:
       Path testServer = Paths.get( this.getClass().getResource( "/testserver" ).toURI() );
       copyFolder( testServer, tempFolder.toPath() );

       // create the necessary mocks for the subcomponents
       frameworkMock = new OSGiFrameworkMock();
       navajoIOConfigMock = new NavajoIOConfigMock( tempFolder );
       javaCompilerMock = new JavaCompilerMock( navajoIOConfigMock );
       bundleContextMock = new BundleContextMock( frameworkMock );
       repositoryInstanceMock = new RepositoryInstanceMock( tempFolder );

       // Instantiate, configure and activate the TslScriptCompiler, not a mock
       tslScriptCompiler = new TslScriptCompiler();
       tslScriptCompiler.setIOConfig( navajoIOConfigMock );
       // 1) inspection suggests we can skip providing a classloader
       // 2) currently the expressionEvaluator in the tslScriptCompiiler is only used when an AdapterFieldDependency is encountered
       //    which we don't (need to) use, so we don't have to provide one
       tslScriptCompiler.activate();

       // Instantiate, configure and activate the dependencyAnalyzer, not a mock
       depAnalyzer = new DependencyAnalyzer();
       depAnalyzer.setIOConfig( navajoIOConfigMock );
       depAnalyzer.activate();

       bundleQueue.setDependencyAnalyzer( depAnalyzer );

       // Instantiate, configure and activate the bundleCreator, not a mock
       bundleCreator = new BundleCreatorComponent();
       // Do not set the EventAdmin as we're not interested in testing that bit of code
       // and it can handle not having an EventAdmin
       bundleCreator.setJavaCompiler( javaCompilerMock );
       bundleCreator.setTslScriptCompiler( tslScriptCompiler );
       // Do not set a ScalaCompiler, as what we're testing is independent of script type
       bundleCreator.setIOConfig( navajoIOConfigMock );
       bundleCreator.setDependencyAnalyzer( depAnalyzer );
       bundleCreator.activate( bundleContextMock );

       bundleQueue.setBundleCreator( bundleCreator );

       bundleQueue.activate();
    }

    @After
    public void teardown()
    {
        // inverse actions of the setup in inverse order
        bundleQueue.deactivate();
        bundleQueue.clearBundleCreator( bundleCreator );
        
        bundleCreator.deactivate();
        bundleCreator.clearDependencyAnalyzer( depAnalyzer );
        bundleCreator.clearIOConfig( navajoIOConfigMock );
        bundleCreator.removeTslScriptCompiler( tslScriptCompiler );
        bundleCreator.clearJavaCompiler( javaCompilerMock );
        bundleCreator = null;
        
        bundleQueue.clearDependencyAnalyzer( depAnalyzer );
        
        depAnalyzer.deactivate();
        depAnalyzer.clearIOConfig( navajoIOConfigMock );
        depAnalyzer = null;
        
        tslScriptCompiler.deactivate();
        tslScriptCompiler.clearIOConfig( navajoIOConfigMock );
        tslScriptCompiler = null;

        // the mocks
        bundleContextMock = null;
        javaCompilerMock = null;
        navajoIOConfigMock = null;
        frameworkMock.clean();
        frameworkMock = null;
        repositoryInstanceMock = null;
        
        tempFolder = null;
        
        bundleQueue = null;
    }
    
    private void copyFolder( Path source, Path target, CopyOption... options )
            throws IOException
    {
        Files.walkFileTree(source, new SimpleFileVisitor<Path>()
        {
            @Override
            public FileVisitResult preVisitDirectory( Path dir, BasicFileAttributes attrs )
                    throws IOException
            {
                Files.createDirectories( target.resolve( source.relativize( dir ) ) );
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
                    throws IOException
            {
                Files.copy( file, target.resolve( source.relativize( file ) ), options );
                return FileVisitResult.CONTINUE;
            }
        });
    }

    private Event createAddEventForFile( String fileName )
    {
        return createEventForFile( fileName, ENTRY_CHANGE_TYPE_ADD );
    }
    
    private Event createModifyEventForFile( String fileName )
    {
        return createEventForFile( fileName, ENTRY_CHANGE_TYPE_MODIFY );
    }

    @SuppressWarnings( "unused" )
    private Event createCopyEventForFile( String fileName )
    {
        return createEventForFile( fileName, ENTRY_CHANGE_TYPE_COPY );
    }
    
    private Event deleteFileAndcreateDeleteEventForFile( String fileName )
    {
        try
        {
            Files.delete( tempFolder.toPath().resolve( fileName ) );
        }
        catch ( IOException e )
        {
            e.printStackTrace();
            assertTrue( "Failed at deleting file " + fileName, false );
        }
        return createEventForFile( fileName, ENTRY_CHANGE_TYPE_DELETE );
    }
    
    private Event createEventForFile( String fileName, String type )
    {
        Map<String, Object> properties = new HashMap<>();
        properties.put( type, Collections.singletonList( fileName ) );
        properties.put( REPOSITORY_KEY, repositoryInstanceMock );
        properties.put( REPOSITORY_NAME_KEY, repositoryInstanceMock.getRepositoryName() );
        Event e = new Event( REPOSITORY_CHANGE_EVENT_TYPE, properties );
        return e;
    }
    
    @Test
    public void testBundleCreation()
    {
        System.out.println( "Starting " + testName.getMethodName() );

        bundleQueue.handleEvent( createAddEventForFile( "scripts" + File.separator + "MainFile.xml" ) );
        assertNotNull( frameworkMock.getBundleByName( "MainFile" ) );
    }

    @Test
    public void testBundleCreationAndDeletion()
    {
        System.out.println( "Starting " + testName.getMethodName() );

        bundleQueue.handleEvent( createAddEventForFile( "scripts" + File.separator + "MainFile.xml" ) );
        assertNotNull( frameworkMock.getBundleByName( "MainFile" ) );
        bundleQueue.handleEvent( deleteFileAndcreateDeleteEventForFile( "scripts" + File.separator + "MainFile.xml" ) );
        assertNull( frameworkMock.getBundleByName( "MainFile" ) );
    }

    @Test
    public void testBundleCreationWithInclude()
    {
        System.out.println( "Starting " + testName.getMethodName() );

        bundleQueue.handleEvent( createAddEventForFile( "scripts" + File.separator + "MainFileWithInclude.xml" ) );
        assertNotNull( frameworkMock.getBundleByName( "MainFileWithInclude" ) );
        assertNotNull( frameworkMock.getBundleByName( "MainFileWithInclude_KNVB" ) );
    }

    @Test
    public void testBundleCreationWithIncludeAndDeletion()
    {
        System.out.println( "Starting " + testName.getMethodName() );

        bundleQueue.handleEvent( createAddEventForFile( "scripts" + File.separator + "MainFileWithInclude.xml" ) );
        assertNotNull( frameworkMock.getBundleByName( "MainFileWithInclude" ) );
        assertNotNull( frameworkMock.getBundleByName( "MainFileWithInclude_KNVB" ) );
        bundleQueue.handleEvent( deleteFileAndcreateDeleteEventForFile( "scripts" + File.separator + "MainFileWithInclude.xml" ) );
        assertNull( frameworkMock.getBundleByName( "MainFileWithInclude" ) );
        // TODO: This part of the system doesn't work yet but this file should also be cleaned up. See #596
//        assertNull( frameworkMock.getBundleByName( "MainFileWithInclude_KNVB" ) );
    }

    @Test
    public void testBundleCreationWithoutGenericInclude()
    {
        System.out.println( "Starting " + testName.getMethodName() );

        bundleQueue.handleEvent( createAddEventForFile( "scripts" + File.separator + "MainFileWithTenantSpecificInclude.xml" ) );
        assertNull( frameworkMock.getBundleByName( "MainFileWithTenantSpecificInclude" ) );
        assertNull( frameworkMock.getBundleByName( "MainFileWithTenantSpecificInclude_KNVB" ) );
    }

    @Test
    public void testBundleCreationWithIncludeThenDeleteSpecificInclude()
    {
        System.out.println( "Starting " + testName.getMethodName() );

        bundleQueue.handleEvent( createModifyEventForFile( "scripts" + File.separator + "MainFileWithInclude.xml" ) );
        assertNotNull( frameworkMock.getBundleByName( "MainFileWithInclude" ) );
        assertNotNull( frameworkMock.getBundleByName( "MainFileWithInclude_KNVB" ) );
        bundleQueue.handleEvent( deleteFileAndcreateDeleteEventForFile( "scripts" + File.separator + "include" + File.separator + "FileToInclude_KNVB.xml" ) );
        assertNotNull( frameworkMock.getBundleByName( "MainFileWithInclude" ) );
        assertNull( frameworkMock.getBundleByName( "MainFileWithInclude_KNVB" ) );
    }
}
