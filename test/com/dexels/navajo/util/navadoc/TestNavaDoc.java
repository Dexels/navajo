package com.dexels.navajo.util.navadoc;

import junit.framework.*;
// test fixture

import com.dexels.navajo.util.navadoc.NavaDocTestFixture;

// objects included in testing
import com.dexels.navajo.util.navadoc.NavaDocConfigurator;
import com.dexels.navajo.util.navadoc.ConfigurationException;

// regular expressions
import gnu.regexp.RE;
import gnu.regexp.REException;


public class TestNavaDoc extends TestCase {

  private NavaDocTestFixture fixture = new NavaDocTestFixture( this );

  public TestNavaDoc(String s) {
    super(s);
  }

  protected void setUp() {
  }

  protected void tearDown() {
  }

  public void testNavaDocContructor() {
    try {
      NavaDoc documenter = new NavaDoc();
      this.assertNotNull( documenter );
    } catch ( ConfigurationException e ) {
      fail( "testGetLoggerConfig() failed with Exception: " + e.toString() );
    }
  }
}
