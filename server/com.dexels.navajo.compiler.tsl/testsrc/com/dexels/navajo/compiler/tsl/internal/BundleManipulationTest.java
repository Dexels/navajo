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
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.rules.TestName;
import org.osgi.framework.Bundle;
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

    private static final String SCRIPTS_ROOT = "scripts" + File.separator;

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

    private Event createAddEventForFiles( String... fileNames )
    {
        return createEventForFiles( ENTRY_CHANGE_TYPE_ADD, fileNames );
    }
    
    private Event createModifyEventForFiles( String... fileNames )
    {
        return createEventForFiles( ENTRY_CHANGE_TYPE_MODIFY, fileNames );
    }

    @SuppressWarnings( "unused" )
    private Event createCopyEventForFiles( String... fileNames )
    {
        return createEventForFiles( ENTRY_CHANGE_TYPE_COPY, fileNames );
    }
    
    private Event deleteFilesAndcreateDeleteEventForFiles( String... fileNames )
    {
        for( String fileName :  fileNames )
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
        }
        return createEventForFiles( ENTRY_CHANGE_TYPE_DELETE, fileNames );
    }
    
    private Event createEventForFiles( String type, String... fileNames )
    {
        Map<String, Object> properties = new HashMap<>();
        properties.put( type, Arrays.asList( fileNames ) );
        properties.put( REPOSITORY_KEY, repositoryInstanceMock );
        properties.put( REPOSITORY_NAME_KEY, repositoryInstanceMock.getRepositoryName() );
        Event e = new Event( REPOSITORY_CHANGE_EVENT_TYPE, properties );
        return e;
    }
    
    @Test
    public void testBundleCreation()
    {
        System.out.println( "Starting " + testName.getMethodName() );

        bundleQueue.handleEvent( createAddEventForFiles( SCRIPTS_ROOT + "MainFile.xml" ) );
        assertNotNull( frameworkMock.getBundleByName( "MainFile" ) );
    }

    @Test
    public void testBundleCreationOnlyTenantSpecific()
    {
        System.out.println( "Starting " + testName.getMethodName() );

        bundleQueue.handleEvent( createAddEventForFiles( SCRIPTS_ROOT + "include" + File.separator + "TenantSpecificFileToInclude_KNVB.xml" ) );
        assertNull( frameworkMock.getBundleByName( "include.TenantSpecificFileToInclude" ) );
        assertNotNull( frameworkMock.getBundleByName( "include.TenantSpecificFileToInclude_KNVB" ) );
    }

    @Test
    public void testBundleCreationAndDeletion()
    {
        System.out.println( "Starting " + testName.getMethodName() );

        bundleQueue.handleEvent( createAddEventForFiles( SCRIPTS_ROOT + "MainFile.xml" ) );
        assertNotNull( frameworkMock.getBundleByName( "MainFile" ) );
        bundleQueue.handleEvent( deleteFilesAndcreateDeleteEventForFiles( SCRIPTS_ROOT + "MainFile.xml" ) );
        assertNull( frameworkMock.getBundleByName( "MainFile" ) );
    }

    // Basic include logic
    @Test
    public void testBundleCreationWithInclude()
    {
        System.out.println( "Starting " + testName.getMethodName() );

        bundleQueue.handleEvent( createAddEventForFiles( SCRIPTS_ROOT + "MainFileWithInclude.xml", SCRIPTS_ROOT + "include" + File.separator + "FileToInclude.xml" ) );
        assertNotNull( frameworkMock.getBundleByName( "MainFileWithInclude" ) );
        assertNotNull( frameworkMock.getBundleByName( "MainFileWithInclude_KNVB" ) );
        assertNotNull( frameworkMock.getBundleByName( "include.FileToInclude" ) );
        assertNotNull( frameworkMock.getBundleByName( "include.FileToInclude_KNVB" ) );
    }

    @Test
    public void testBundleCreationWithIncludeAndDeletion()
    {
        System.out.println( "Starting " + testName.getMethodName() );

        bundleQueue.handleEvent( createAddEventForFiles( SCRIPTS_ROOT + "MainFileWithInclude.xml", SCRIPTS_ROOT + "include" + File.separator + "FileToInclude.xml" ) );
        assertNotNull( frameworkMock.getBundleByName( "MainFileWithInclude" ) );
        assertNotNull( frameworkMock.getBundleByName( "MainFileWithInclude_KNVB" ) );
        assertNotNull( frameworkMock.getBundleByName( "include.FileToInclude" ) );
        assertNotNull( frameworkMock.getBundleByName( "include.FileToInclude_KNVB" ) );
        bundleQueue.handleEvent( deleteFilesAndcreateDeleteEventForFiles( SCRIPTS_ROOT + "MainFileWithInclude.xml" ) );
        assertNull( frameworkMock.getBundleByName( "MainFileWithInclude" ) );
        // TODO: This part of the system doesn't work yet but this file should also be cleaned up. See #596
//        assertNull( frameworkMock.getBundleByName( "MainFileWithInclude_KNVB" ) );
        assertNotNull( frameworkMock.getBundleByName( "include.FileToInclude" ) );
        assertNotNull( frameworkMock.getBundleByName( "include.FileToInclude_KNVB" ) );
    }

    @Test
    public void testBundleCreationWithoutGenericInclude()
    {
        System.out.println( "Starting " + testName.getMethodName() );

        bundleQueue.handleEvent( createAddEventForFiles( SCRIPTS_ROOT + "MainFileWithTenantSpecificInclude.xml", SCRIPTS_ROOT + "include" + File.separator + "TenantSpecificFileToInclude_KNVB.xml" ) );
        assertNull( frameworkMock.getBundleByName( "MainFileWithTenantSpecificInclude" ) );
        // Note that I can imagine logic changing to allow the creation of the tenant specific version, but right now the generic include is required before the tenant version can be made
        assertNull( frameworkMock.getBundleByName( "MainFileWithTenantSpecificInclude_KNVB" ) );
        assertNull( frameworkMock.getBundleByName( "include.TenantSpecificFileToInclude" ) );
        assertNotNull( frameworkMock.getBundleByName( "include.TenantSpecificFileToInclude_KNVB" ) );
    }

    @Test
    public void testBundleCreationWithIncludeThenDeleteGenericInclude()
    {
        System.out.println( "Starting " + testName.getMethodName() );

        bundleQueue.handleEvent( createAddEventForFiles( SCRIPTS_ROOT + "MainFileWithInclude.xml", SCRIPTS_ROOT + "include" + File.separator + "FileToInclude.xml" ) );
        assertNotNull( frameworkMock.getBundleByName( "MainFileWithInclude" ) );
        assertNotNull( frameworkMock.getBundleByName( "MainFileWithInclude_KNVB" ) );
        assertNotNull( frameworkMock.getBundleByName( "include.FileToInclude" ) );
        assertNotNull( frameworkMock.getBundleByName( "include.FileToInclude_KNVB" ) );

        bundleQueue.handleEvent( deleteFilesAndcreateDeleteEventForFiles( SCRIPTS_ROOT + "include" + File.separator + "FileToInclude.xml" ) );
        assertNull( frameworkMock.getBundleByName( "MainFileWithInclude" ) );
        // TODO: This part of the system doesn't work yet but this file should also be cleaned up. See #596
//        assertNull( frameworkMock.getBundleByName( "MainFileWithInclude_KNVB" ) );
        assertNull( frameworkMock.getBundleByName( "include.FileToInclude" ) );
        assertNotNull( frameworkMock.getBundleByName( "include.FileToInclude_KNVB" ) );
    }

    @Test
    public void testBundleCreationWithIncludeThenDeleteSpecificInclude()
    {
        System.out.println( "Starting " + testName.getMethodName() );

        bundleQueue.handleEvent( createAddEventForFiles( SCRIPTS_ROOT + "MainFileWithInclude.xml", SCRIPTS_ROOT + "include" + File.separator + "FileToInclude.xml" ) );
        Bundle mainFile = frameworkMock.getBundleByName( "MainFileWithInclude" );
        assertNotNull( mainFile );
        assertNotNull( frameworkMock.getBundleByName( "MainFileWithInclude_KNVB" ) );
        assertNotNull( frameworkMock.getBundleByName( "include.FileToInclude" ) );
        assertNotNull( frameworkMock.getBundleByName( "include.FileToInclude_KNVB" ) );
        long originalId = mainFile.getBundleId();

        bundleQueue.handleEvent( deleteFilesAndcreateDeleteEventForFiles( SCRIPTS_ROOT + "include" + File.separator + "FileToInclude_KNVB.xml" ) );
        // fetch again
        mainFile = frameworkMock.getBundleByName( "MainFileWithInclude" );
        assertNotNull( mainFile );
        assertNotEquals( mainFile.getBundleId(), originalId );
        assertNull( frameworkMock.getBundleByName( "MainFileWithInclude_KNVB" ) );
        assertNotNull( frameworkMock.getBundleByName( "include.FileToInclude" ) );
        assertNull( frameworkMock.getBundleByName( "include.FileToInclude_KNVB" ) );
    }

    @Test
    public void testSpecificScriptWithOtherSpecificIncludeDoesNotCompile()
    {
        System.out.println( "Starting " + testName.getMethodName() );

        bundleQueue.handleEvent( createAddEventForFiles( SCRIPTS_ROOT + "SpecificFileWithOtherSpecificInclude_KNKV.xml" ) );
        assertNull( frameworkMock.getBundleByName( "SpecificFileWithOtherSpecificInclude" ) );
        assertNull( frameworkMock.getBundleByName( "SpecificFileWithOtherSpecificInclude_KNKV" ) );
        assertNull( frameworkMock.getBundleByName( "SpecificFileWithOtherSpecificInclude_KNVB" ) );
    }

    @Test
    public void testSpecificScriptWithSameSpecificInclude()
    {
        System.out.println( "Starting " + testName.getMethodName() );

        bundleQueue.handleEvent( createAddEventForFiles( SCRIPTS_ROOT + "SpecificFileWithSameSpecificInclude_KNVB.xml" ) );
        assertNull( frameworkMock.getBundleByName( "SpecificFileWithSameSpecificInclude" ) );
        assertNull( frameworkMock.getBundleByName( "SpecificFileWithSameSpecificInclude_KNKV" ) );
        assertNotNull( frameworkMock.getBundleByName( "SpecificFileWithSameSpecificInclude_KNVB" ) );
    }

    // test include loop
    @Test
    public void testIncludingMyself()
    {
        System.out.println( "Starting " + testName.getMethodName() );

        bundleQueue.handleEvent( createModifyEventForFiles( SCRIPTS_ROOT + "MainFileIncludesItself.xml" ) );
        assertNull( frameworkMock.getBundleByName( "MainFileIncludesItself" ) );
    }

    @Test
    public void testIncludingEachOther()
    {
        System.out.println( "Starting " + testName.getMethodName() );

        bundleQueue.handleEvent( createModifyEventForFiles( SCRIPTS_ROOT + "MainFileIncludesOtherFile.xml" ) );
        assertNull( frameworkMock.getBundleByName( "MainFileIncludesOtherFile" ) );
        assertNull( frameworkMock.getBundleByName( "OtherFileIncludesMainFile" ) );
    }

    @Test
    public void testIncludeLoopInThreeSteps()
    {
        System.out.println( "Starting " + testName.getMethodName() );

        bundleQueue.handleEvent( createModifyEventForFiles( SCRIPTS_ROOT + "MainFileIncludesSecondFile.xml" ) );
        assertNull( frameworkMock.getBundleByName( "MainFileIncludesSecondFile" ) );
        assertNull( frameworkMock.getBundleByName( "SecondFileIncludesThirdFile" ) );
        assertNull( frameworkMock.getBundleByName( "ThirdFileIncludesMainFile" ) );
    }

    // test updates
    @Test
    public void testBundleCreationWithIncludeThenUpdateSpecificInclude()
    {
        System.out.println( "Starting " + testName.getMethodName() );

        bundleQueue.handleEvent( createAddEventForFiles( SCRIPTS_ROOT + "MainFileWithInclude.xml", SCRIPTS_ROOT + "include" + File.separator + "FileToInclude.xml" ) );
        assertNotNull( frameworkMock.getBundleByName( "MainFileWithInclude" ) );
        Bundle specificMainFile = frameworkMock.getBundleByName( "MainFileWithInclude_KNVB" );
        assertNotNull( specificMainFile );
        long originalSpecificMainFileId = specificMainFile.getBundleId();
        assertNotNull( frameworkMock.getBundleByName( "include.FileToInclude" ) );
        Bundle specificInclude = frameworkMock.getBundleByName( "include.FileToInclude_KNVB" );
        assertNotNull( specificInclude );
        long originalSpecificIncludeId = specificInclude.getBundleId();

        System.out.println( "Second phase " + testName.getMethodName() );
        bundleQueue.handleEvent( createModifyEventForFiles( SCRIPTS_ROOT + "include" + File.separator + "FileToInclude_KNVB.xml" ) );
        // refetch all bundles, they should still exist
        assertNotNull( frameworkMock.getBundleByName( "MainFileWithInclude" ) );
        specificMainFile = frameworkMock.getBundleByName( "MainFileWithInclude_KNVB" );
        assertNotNull( specificMainFile );
        assertNotNull( frameworkMock.getBundleByName( "include.FileToInclude" ) );
        specificInclude = frameworkMock.getBundleByName( "include.FileToInclude_KNVB" );
        assertNotNull( specificInclude );

        // require that certain bundles changed. We cannot require that bundles stay the same due to how it's implemented
        assertNotEquals( specificMainFile.getBundleId(), originalSpecificMainFileId );
        assertNotEquals( specificInclude.getBundleId(), originalSpecificIncludeId );
    }

    @Test
    public void testBundleCreationWithIncludeThenUpdateGenericInclude()
    {
        System.out.println( "Starting " + testName.getMethodName() );

        bundleQueue.handleEvent( createAddEventForFiles( SCRIPTS_ROOT + "MainFileWithInclude.xml", SCRIPTS_ROOT + "include" + File.separator + "FileToInclude.xml" ) );
        Bundle genericMainFile = frameworkMock.getBundleByName( "MainFileWithInclude" );
        assertNotNull( genericMainFile );
        long originalGenericMainFileId = genericMainFile.getBundleId();
        assertNotNull( frameworkMock.getBundleByName( "MainFileWithInclude_KNVB" ) );
        Bundle genericInclude = frameworkMock.getBundleByName( "include.FileToInclude" );
        assertNotNull( genericInclude );
        long originalGenericIncludeId = genericInclude.getBundleId();
        assertNotNull( frameworkMock.getBundleByName( "include.FileToInclude_KNVB" ) );

        System.out.println( "Second phase " + testName.getMethodName() );
        bundleQueue.handleEvent( createModifyEventForFiles( SCRIPTS_ROOT + "include" + File.separator + "FileToInclude.xml" ) );

        // refetch all bundles, they should still exist
        genericMainFile = frameworkMock.getBundleByName( "MainFileWithInclude" );
        assertNotNull( genericMainFile );
        assertNotNull( frameworkMock.getBundleByName( "MainFileWithInclude_KNVB" ) );
        genericInclude = frameworkMock.getBundleByName( "include.FileToInclude" );
        assertNotNull( genericInclude );
        assertNotNull( frameworkMock.getBundleByName( "include.FileToInclude_KNVB" ) );

        // require that certain bundles changed. We cannot require that bundles stay the same due to how it's implemented
        assertNotEquals( genericMainFile.getBundleId(), originalGenericMainFileId );
        assertNotEquals( genericInclude.getBundleId(), originalGenericIncludeId );
    }

    @Test
    public void testBundleCreationWithIncludeThenUpdateMainFile()
    {
        System.out.println( "Starting " + testName.getMethodName() );

        bundleQueue.handleEvent( createAddEventForFiles( SCRIPTS_ROOT + "MainFileWithInclude.xml", SCRIPTS_ROOT + "include" + File.separator + "FileToInclude.xml" ) );
        Bundle genericMainFile = frameworkMock.getBundleByName( "MainFileWithInclude" );
        assertNotNull( genericMainFile );
        long originalGenericMainFileId = genericMainFile.getBundleId();
        Bundle specificMainFile = frameworkMock.getBundleByName( "MainFileWithInclude_KNVB" );
        assertNotNull( specificMainFile );
        long originalSpecificMainFileId = specificMainFile.getBundleId();
        Bundle genericInclude = frameworkMock.getBundleByName( "include.FileToInclude" );
        assertNotNull( genericInclude );
        long originalGenericIncludeId = genericInclude.getBundleId();
        Bundle specificInclude = frameworkMock.getBundleByName( "include.FileToInclude_KNVB" );
        assertNotNull( specificInclude );
        long originalSpecificIncludeId = specificInclude.getBundleId();

        System.out.println( "Second phase " + testName.getMethodName() );
        bundleQueue.handleEvent( createModifyEventForFiles( SCRIPTS_ROOT + "MainFileWithInclude.xml" ) );

        // refetch all bundles, they should still exist
        genericMainFile = frameworkMock.getBundleByName( "MainFileWithInclude" );
        assertNotNull( genericMainFile );
        specificMainFile = frameworkMock.getBundleByName( "MainFileWithInclude_KNVB" );
        assertNotNull( specificMainFile );
        genericInclude = frameworkMock.getBundleByName( "include.FileToInclude" );
        assertNotNull( genericInclude );
        specificInclude = frameworkMock.getBundleByName( "include.FileToInclude_KNVB" );
        assertNotNull( specificInclude );

        // require that the MainFile bundles changed, and the include bundles didn't change
        assertNotEquals( genericMainFile.getBundleId(), originalGenericMainFileId );
        assertNotEquals( specificMainFile.getBundleId(), originalSpecificMainFileId );
        assertEquals( genericInclude.getBundleId(), originalGenericIncludeId );
        assertEquals( specificInclude.getBundleId(), originalSpecificIncludeId );
    }

    // test includeOnly tag
    @Test
    public void testBundleCreationWithIncludeUsingIncludeOnly()
    {
        System.out.println( "Starting " + testName.getMethodName() );

        bundleQueue.handleEvent( createAddEventForFiles( SCRIPTS_ROOT + "MainFileWithIncludeUsingIncludeOnly.xml", SCRIPTS_ROOT + "include" + File.separator + "FileToIncludeUsingIncludeOnly.xml" ) );
        assertNotNull( frameworkMock.getBundleByName( "MainFileWithIncludeUsingIncludeOnly" ) );
        assertNotNull( frameworkMock.getBundleByName( "MainFileWithIncludeUsingIncludeOnly_KNVB" ) );
        assertNull( frameworkMock.getBundleByName( "include.FileToIncludeUsingIncludeOnly" ) );
        assertNull( frameworkMock.getBundleByName( "include.FileToIncludeUsingIncludeOnly_KNVB" ) );
    }

    @Test
    public void testBundleCreationWithIncludeThenDeleteGenericIncludeUsingIncludeOnly()
    {
        System.out.println( "Starting " + testName.getMethodName() );

        bundleQueue.handleEvent( createAddEventForFiles( SCRIPTS_ROOT + "MainFileWithIncludeUsingIncludeOnly.xml", SCRIPTS_ROOT + "include" + File.separator + "FileToIncludeUsingIncludeOnly.xml" ) );
        assertNotNull( frameworkMock.getBundleByName( "MainFileWithIncludeUsingIncludeOnly" ) );
        assertNotNull( frameworkMock.getBundleByName( "MainFileWithIncludeUsingIncludeOnly_KNVB" ) );
        assertNull( frameworkMock.getBundleByName( "include.FileToIncludeUsingIncludeOnly" ) );
        assertNull( frameworkMock.getBundleByName( "include.FileToIncludeUsingIncludeOnly_KNVB" ) );

        bundleQueue.handleEvent( deleteFilesAndcreateDeleteEventForFiles( SCRIPTS_ROOT + "include" + File.separator + "FileToIncludeUsingIncludeOnly.xml" ) );
        assertNull( frameworkMock.getBundleByName( "MainFileWithIncludeUsingIncludeOnly" ) );
        // TODO: This part of the system doesn't work yet but this file should also be cleaned up. See #596
//        assertNull( frameworkMock.getBundleByName( "MainFileWithIncludeUsingIncludeOnly_KNVB" ) );
        assertNull( frameworkMock.getBundleByName( "include.FileToIncludeUsingIncludeOnly" ) );
        assertNull( frameworkMock.getBundleByName( "include.FileToIncludeUsingIncludeOnly_KNVB" ) );
    }

    @Test
    public void testBundleCreationWithIncludeThenDeleteSpecificIncludeUsingIncludeOnly()
    {
        System.out.println( "Starting " + testName.getMethodName() );

        bundleQueue.handleEvent( createAddEventForFiles( SCRIPTS_ROOT + "MainFileWithIncludeUsingIncludeOnly.xml", SCRIPTS_ROOT + "include" + File.separator + "FileToIncludeUsingIncludeOnly.xml" ) );
        Bundle mainFile = frameworkMock.getBundleByName( "MainFileWithIncludeUsingIncludeOnly" );
        assertNotNull( mainFile );
        assertNotNull( frameworkMock.getBundleByName( "MainFileWithIncludeUsingIncludeOnly_KNVB" ) );
        assertNull( frameworkMock.getBundleByName( "include.FileToIncludeUsingIncludeOnly" ) );
        assertNull( frameworkMock.getBundleByName( "include.FileToIncludeUsingIncludeOnly_KNVB" ) );
        long originalId = mainFile.getBundleId();

        bundleQueue.handleEvent( deleteFilesAndcreateDeleteEventForFiles( SCRIPTS_ROOT + "include" + File.separator + "FileToIncludeUsingIncludeOnly_KNVB.xml" ) );
        // fetch again
        mainFile = frameworkMock.getBundleByName( "MainFileWithIncludeUsingIncludeOnly" );
        assertNotNull( mainFile );
        assertNotEquals( mainFile.getBundleId(), originalId );
        assertNull( frameworkMock.getBundleByName( "MainFileWithInclude_KNVBUsingIncludeOnly" ) );
        assertNull( frameworkMock.getBundleByName( "include.FileToIncludeUsingIncludeOnly" ) );
        assertNull( frameworkMock.getBundleByName( "include.FileToIncludeUsingIncludeOnly_KNVB" ) );
    }

    // test double layered includes
    @Test
    public void testBundleCreationWithDoubleLayerInclude()
    {
        System.out.println( "Starting " + testName.getMethodName() );

        bundleQueue.handleEvent( createAddEventForFiles( SCRIPTS_ROOT + "MainFileWithDoubleInclude.xml", SCRIPTS_ROOT + "include" + File.separator + "IncludeWithInclude.xml", SCRIPTS_ROOT + "include" + File.separator + "FileToInclude.xml" ) );
        assertNotNull( frameworkMock.getBundleByName( "MainFileWithDoubleInclude" ) );
        // TODO: If second or further layer of the include is the first layer with tenantspecific versions, it does need to compile tenant specific versions. See #600
//        assertNotNull( frameworkMock.getBundleByName( "MainFileWithDoubleInclude_KNVB" ) );
        assertNotNull( frameworkMock.getBundleByName( "include.IncludeWithInclude" ) );
        assertNotNull( frameworkMock.getBundleByName( "include.IncludeWithInclude_KNVB" ) );
        assertNotNull( frameworkMock.getBundleByName( "include.FileToInclude" ) );
        assertNotNull( frameworkMock.getBundleByName( "include.FileToInclude_KNVB" ) );
    }

    @Test
    public void testBundleCreationWithDoubleLayerIncludeThenDeleteLowestInclude()
    {
        System.out.println( "Starting " + testName.getMethodName() );

        bundleQueue.handleEvent( createAddEventForFiles( SCRIPTS_ROOT + "MainFileWithDoubleInclude.xml", SCRIPTS_ROOT + "include" + File.separator + "IncludeWithInclude.xml", SCRIPTS_ROOT + "include" + File.separator + "FileToInclude.xml" ) );
        assertNotNull( frameworkMock.getBundleByName( "MainFileWithDoubleInclude" ) );
        // TODO: If second or further layer of the include is the first layer with tenantspecific versions, it does need to compile tenant specific versions. See #600
//        assertNotNull( frameworkMock.getBundleByName( "MainFileWithDoubleInclude_KNVB" ) );
        assertNotNull( frameworkMock.getBundleByName( "include.IncludeWithInclude" ) );
        assertNotNull( frameworkMock.getBundleByName( "include.IncludeWithInclude_KNVB" ) );
        assertNotNull( frameworkMock.getBundleByName( "include.FileToInclude" ) );
        assertNotNull( frameworkMock.getBundleByName( "include.FileToInclude_KNVB" ) );

        bundleQueue.handleEvent( deleteFilesAndcreateDeleteEventForFiles( SCRIPTS_ROOT + "include" + File.separator + "FileToInclude.xml" ) );
        assertNull( frameworkMock.getBundleByName( "MainFileWithInclude" ) );
        // Note that due to a combination of #596 and #600 this test currently passes. It is expected to fail if only #600 is fixed.
        assertNull( frameworkMock.getBundleByName( "MainFileWithInclude_KNVB" ) );
        assertNull( frameworkMock.getBundleByName( "include.IncludeWithInclude" ) );
        // TODO: This part of the system doesn't work yet but this file should also be cleaned up. See #596
//        assertNull( frameworkMock.getBundleByName( "include.IncludeWithInclude_KNVB" ) );
        assertNull( frameworkMock.getBundleByName( "include.FileToInclude" ) );
        assertNotNull( frameworkMock.getBundleByName( "include.FileToInclude_KNVB" ) );
    }
    // further tests with double layered includes do not make sense as long as #600 is not fixed
}
