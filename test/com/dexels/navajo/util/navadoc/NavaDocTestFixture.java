package com.dexels.navajo.util.navadoc;

import java.util.Properties;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class NavaDocTestFixture extends java.lang.Object {

  public NavaDocTestFixture(Object obj) {

     // fake the System properties
    File testDataPath = new File ( "test" + File.separator + "data" );
    File testPropertiesFile = new File ( testDataPath, "config.properties" );
    Properties props = new Properties();
    try {
      props.load( new FileInputStream( testPropertiesFile.getAbsoluteFile() ) );
    } catch ( Exception e ) {
      // hmmm, not sure how to handle this
    }
    // add all remaing system properties so we don't blow away
    // any system configuration
    props.putAll( System.getProperties() );
    System.setProperties( props );
  }

  public void setUp() {
  }

  public void tearDown() {
  }

}
