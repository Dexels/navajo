package com.dexels.navajo.installer;


import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import java.io.InputStream;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.helper.ProjectHelper2;

public class Test1 {

    public static void main(String[] args) 
    {
        try
        {
                
                JarFile jar = new JarFile( "dist/test1.jar" );
                ZipEntry xml = jar.getEntry( "test1.xml" );
                if ( xml == null )
                {
                        System.out.println("ERROR : no entry for file test1.xml");
                        System.exit( 1 );
                }       
                
                InputStream stream = jar.getInputStream( xml );  
                
                
                Project project = new Project();                
                ProjectHelper2 helper = new ProjectHelper2();
                helper.parse( project, stream );

                System.out.println("NAME = " + project.getName() );
                System.out.println("BASE = " + project.getBaseDir().getPath() );
 
                project.init();
                project.executeTarget( "do" );
        
                
        }
        catch ( Exception e1 )
        {
                e1.printStackTrace();
        }       
    }
}
