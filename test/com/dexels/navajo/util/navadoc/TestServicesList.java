
package com.dexels.navajo.util.navadoc;


import junit.framework.*;

import com.dexels.navajo.util.navadoc.*;

// logging
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import com.dexels.navajo.util.navadoc.config.*;


public class TestServicesList extends TestCase {

  public static final Logger logger =
    Logger.getLogger( NavaDoc.class.getName() );

  NavaDocTestFixture fixture = null;

  private NavaDocConfigurator config =
    new NavaDocConfigurator();

  public TestServicesList( String s ) {
    super( s );
    try {
      this.fixture = new NavaDocTestFixture( this );
    } catch ( Exception e ) {
      fail( "failed to set-up fixture: " + e );
    }
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
      final DocumentSet dset = (DocumentSet) config.getDocumentSetMap().get( "Test Project" );
      ServicesList list =
        new ServicesList( dset.getPathConfiguration().getPath(NavaDocConstants.SVC_PATH_ELEMENT) );

      this.assertEquals( 7, list.size() );
      this.assertEquals( "InitBirthdateQueryMembers", list.iterator().next().toString());
    } catch ( ConfigurationException ce ) {
      this.fail( ce.toString() );
    }
  }

}
