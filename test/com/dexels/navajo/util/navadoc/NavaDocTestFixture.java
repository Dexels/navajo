package com.dexels.navajo.util.navadoc;

import java.util.Properties;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class NavaDocTestFixture extends java.lang.Object {

  private Properties systemProps = new Properties();
  private Properties expectedProps = new Properties();
  private File testDataPath = new File ( "." );

  public NavaDocTestFixture(Object obj)
    throws Exception {

    String s = System.getProperty( "testdata-path" );
    if ( s == null ) {
       String msg = "test data path not found," +
         "pass parameter '-Dtestdata-path=<path>' to test runner";
       System.out.println( msg );
       Exception e = new Exception( msg );
       throw ( e );
    }

     // fake the System properties
    this.testDataPath = new File ( s );
    File testPropertiesFile = new File (
      this.testDataPath, "config.properties" );
    try {
      this.systemProps.load(
        new FileInputStream( testPropertiesFile.getAbsoluteFile() ) );
    } catch ( Exception e ) {
      System.out.println( e.toString() + ": unable to read " +
        "testing properties from file '" + s + "'" );
      throw ( e );
    }
    // add all remaing system properties so we don't blow away
    // any system configuration
    this.systemProps.putAll( System.getProperties() );
    System.setProperties( this.systemProps );

    File exp = new File( s + File.separator + "expected.properties" );
    try {
      this.expectedProps.load( new FileInputStream( exp ) );
    } catch ( IOException ioe ) {
       System.out.println( ioe.toString() + ": expected test properties " +
        "should be stored in file '" + s + "'" );
       Exception e = new Exception( ioe.toString() );
       throw ( e );
    }
  }

  public void setUp() {
  }

  public void tearDown() {
  }

  // getters
  public Properties getSystemProperties() { return ( this.systemProps ); }
  public Properties getExpectedProperties() { return ( this.expectedProps ); }
  public File getTestDataPath() { return ( this.testDataPath ); }

}
