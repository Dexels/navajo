package com.dexels.navajo.util.navadoc;


import junit.framework.*;

// test fixture
import com.dexels.navajo.util.navadoc.NavaDocTestFixture;

// objects included in testing
import com.dexels.navajo.util.navadoc.config.NavaDocConfigurator;
import com.dexels.navajo.util.navadoc.config.ConfigurationException;

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

  private NavaDocTestFixture fixture = null;
  private File dataPath = null;

  public TestNavaDocConfigurator( String s ) {
    super( s );
    try {
      this.fixture = new NavaDocTestFixture( this );
    } catch ( Exception e ) {
      fail( "failed to set-up fixture: " + e );
    }
  }

  protected void setUp() {}

  protected void tearDown() {}

  public void testConfigurationException() {

    try {
      ConfigurationException e =
        new ConfigurationException( "testing", System.getProperty( "configUri" ) );

      throw ( e );
    } catch ( ConfigurationException ce ) {
      this.assertEquals( ce.getConfigUri(),
        System.getProperty( "configUri" ) );
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



}
