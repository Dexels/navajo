package com.dexels.navajo.util.navadoc;

import junit.framework.*;

// test fixture
import com.dexels.navajo.util.navadoc.NavaDocTestFixture;

// IO
import java.io.File;

// objects included in testing
import com.dexels.navajo.util.navadoc.NavaDocConfigurator;
import com.dexels.navajo.util.navadoc.ConfigurationException;

// regular expressions
import gnu.regexp.RE;
import gnu.regexp.REException;
import gnu.regexp.REMatch;

// logging
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

public class TestNavaDoc extends TestCase {

  public static final Logger logger =
    Logger.getLogger( NavaDoc.class.getName() );

  private NavaDocTestFixture fixture = new NavaDocTestFixture( this );

  private NavaDocConfigurator config =
    new NavaDocConfigurator();

  // filename match expression
  public static final String FMATCH = "[.]html$";

  // paths
  private File targetPath = null;

  public TestNavaDoc(String s) {
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
    this.targetPath = this.config.getPathProperty( "target-path" );
  }

  protected void tearDown() {
    fixture.tearDown();
  }

  public void testNavaDocContructor() {
    try {
      NavaDoc documenter = new NavaDoc();
      this.assertNotNull( documenter );
      this.assertEquals( 7, this.countResults() );
    } catch ( ConfigurationException e ) {
      fail( "testGetLoggerConfig() failed with Exception: " + e.toString() );
    }
  }

  private int countResults()
    throws ConfigurationException {
    int result = 0;

    File[] fList = this.targetPath.listFiles();
    if ( fList != null ) {
      try {
        RE xslRE = new RE( ".*" + this.FMATCH );
        for ( int i = 0; i < fList.length; i++ ) {
          File f = fList[i];
          if ( f.isFile() ) {
            String n = f.getName();
            if ( xslRE.isMatch( n ) ) {
              logger.log( Priority.DEBUG, "found result: '" +
                   n + "'" );
              result++;
            }
          }
        }
      } catch ( REException ree ) {
        ConfigurationException e =
          new ConfigurationException( ree.toString() );
        throw( e );
      }
    }

    return( result );

  } // private int countResults()

} // public class TestNavaDoc
