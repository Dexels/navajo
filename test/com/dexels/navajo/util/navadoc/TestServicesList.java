
package com.dexels.navajo.util.navadoc;

import junit.framework.*;


import com.dexels.navajo.util.navadoc.*;

// logging
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;


public class TestServicesList extends TestCase {

  public static final Logger logger =
    Logger.getLogger( NavaDoc.class.getName() );

  NavaDocTestFixture fixture = new NavaDocTestFixture(this);

  private NavaDocConfigurator config =
    new NavaDocConfigurator();

  public TestServicesList(String s) {
    super(s);
  }

  protected void setUp() {
    fixture.setUp();
    try {
      config.configure();
    } catch ( ConfigurationException ce ) {
      this.fail( this.getClass() + "cannot configure: " +
        ce );
    }

  }

  protected void tearDown() {
    fixture.tearDown();
  }

  public void testConstructor() {
    logger.log( Priority.INFO, "testing ServicesList constructor" );
    try {
      ServicesList list =
        new ServicesList( config.getPathProperty( "services-path" ) );
      this.assertEquals( 7, list.size() );
      this.assertEquals( "euro_verstuur", list.get( 0 ) );
    } catch ( ConfigurationException ce ) {
      this.fail( ce.toString() );
    }
  }

}
