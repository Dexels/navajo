package com.dexels.navajo.util.navadoc;

import junit.framework.*;

// test fixture
import com.dexels.navajo.util.navadoc.NavaDocTestFixture;

// objects included in testing
import com.dexels.navajo.util.navadoc.NavaDocConfigurator;
import com.dexels.navajo.util.navadoc.ConfigurationException;

import java.util.Arrays;
import java.util.Properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

// XML Document & Parssers
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class TestNavaDocConfigurator extends TestCase {

  private NavaDocTestFixture fixture = new NavaDocTestFixture( this );
  private File dataPath = null;
  private Properties expectedProps = new Properties();

  public TestNavaDocConfigurator(String s) {
    super(s);
  }

  protected void setUp() {
    String s = System.getProperty( "testdata-path" );
    if ( s == null ) {
       this.fail( "test data path not found," +
         "pass parameter '-Dtestdata-path=<path>' to test runner" );
    }
    this.dataPath = new File( s + File.separator + "expected.properties" );
    try {
      this.expectedProps.load( new FileInputStream( this.dataPath ) );
    } catch ( IOException ioe ) {
       this.fail( ioe.toString() + ": expected test properties " +
        "should be stored in file '" + s + "'" );
    }
  }

  protected void tearDown() {
  }

  public void testConfigurationException() {

    try {
      ConfigurationException e =
        new ConfigurationException( "testing", System.getProperty( "configUri" ) );
      throw ( e );
    } catch ( ConfigurationException ce ) {
      this.assertEquals( ce.getConfigUri(),
        System.getProperty( "configUri" )  );
    }
  }

  public void testConfigExceptionSetUri() {
    try {
      ConfigurationException e =
        new ConfigurationException( "testing", System.getProperty( "configUri" ) );
      e.setConfigUri( "file:///what/ever" );
      throw ( e );
    } catch ( ConfigurationException ce ) {
      this.assertEquals( "file:///what/ever", ce.getConfigUri() );
    }
  }

  public void testConfiguratorContructor() {
    // just see if we make it through the constructor
    NavaDocConfigurator configurator =
      new NavaDocConfigurator();
    this.assertNotNull( configurator );
  }

  public void testGetConfigUri() {
    try {
      NavaDocConfigurator configurator =
        new NavaDocConfigurator();
      configurator.configure();
      this.assertEquals( System.getProperty( "configUri" ),
                         configurator.getConfigUri() );
    } catch ( ConfigurationException e ) {
      fail( "testGetConfigUri() failed with Exception: " + e.toString() );
    }
  }

  public void testGetLoggerConfig() {
    // see if we're getting a good log4j configuration
    // latter we'll do a real comparison of DOM's
    try {
      NavaDocConfigurator configurator =
        new NavaDocConfigurator();
      configurator.configure();
      Element e = configurator.getLoggerConfig();
      this.assertEquals( e.getNodeName(), "log4j:configuration" );
    } catch ( ConfigurationException e ) {
      fail( "testGetLoggerConfig() failed with Exception: " + e.toString() );
    }
  }

  public void testPathProperty() {
    // with a good configuration file, this should product a a good path string
    NavaDocConfigurator configurator =
      new NavaDocConfigurator();
    try {
      configurator.configure();
      File p = new File(
        this.expectedProps.getProperty( "services-path" ) );
      assertEquals( p, configurator.getPathProperty( "services-path" ) );
    } catch ( ConfigurationException e ) {
      fail( "testPathProperty() failed with Exception: " + e.toString() );
    }
  }

}
