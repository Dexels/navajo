package com.dexels.navajo.util.navadoc;

import junit.framework.*;
import org.custommonkey.xmlunit.*;

// test fixture
import com.dexels.navajo.util.navadoc.NavaDocTestFixture;

import java.util.HashMap;

// IO
import java.io.File;
import java.io.FileReader;

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

public class TestNavaDoc extends XMLTestCase {

  public static final Logger logger =
    Logger.getLogger( NavaDoc.class.getName() );

  private NavaDocTestFixture fixture = null;

  private NavaDocConfigurator config =
    new NavaDocConfigurator();

  // paths
  private File targetPath = null;
  private HashMap resultsMap = new HashMap();

  public TestNavaDoc(String s) {
    super(s);
    try {
      this.fixture = new NavaDocTestFixture(this);
    } catch ( Exception e ) {
      fail( "failed to set-up fixture: " + e );
    }
  }

  protected void setUp()
    throws Exception {
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
    } catch ( ConfigurationException e ) {
      fail( "testGetLoggerConfig() failed with Exception: " + e.toString() );
    }
  }

  public void testCount() {
    try {
      int cnt = this.storeResultList();
      this.assertEquals( 7, cnt );
    } catch ( Exception e ) {
      fail( e.toString() );
    }

  }

  public void testHtmlResultPage() {
    try {
      int cnt = this.storeResultList();
      File e = (File) this.fixture.getExpectedHtmlMap().get( "euro" );
      File r = (File) this.resultsMap.get( "euro" );
      logger.log( Priority.DEBUG, "expected HTML file is '" +
        e.getAbsoluteFile() + "'" );
      logger.log( Priority.DEBUG, "results HTML file is '" +
        r.getAbsoluteFile() + "'" );
      FileReader expected = new FileReader( e );
      FileReader result = new FileReader( r );
      this.assertXMLEqual( expected, result );
    } catch ( Exception e ) {
      fail( e.toString() );
    }

  }

  public void testHtmlResultPageWithErrorText() {
    try {
      int cnt = this.storeResultList();
      File e = (File) this.fixture.getExpectedHtmlMap().get( "mangled" );
      File r = (File) this.resultsMap.get( "mangled" );
      logger.log( Priority.DEBUG, "expected HTML file is '" +
        e.getAbsoluteFile() + "'" );
      logger.log( Priority.DEBUG, "results HTML file is '" +
        r.getAbsoluteFile() + "'" );
      FileReader expected = new FileReader( e );
      FileReader result = new FileReader( r );
      this.assertXMLEqual( expected, result );
    } catch ( Exception e ) {
      fail( e.toString() );
    }

  }

  private int storeResultList()
    throws ConfigurationException {

    int cnt = 0;

    File[] fList = this.targetPath.listFiles();
    if ( fList != null ) {
      try {
        RE xslRE = new RE( ".*[.]html$" );
        for ( int i = 0; i < fList.length; i++ ) {
          File f = fList[i];
          if ( f.isFile() ) {
            String n = f.getName();
            if ( xslRE.isMatch( n ) ) {
              logger.log( Priority.DEBUG, "found result: '" +
                   f.getAbsoluteFile() + "'" );
              RE extRE = new RE( "[.]html$" );
              REMatch match = extRE.getMatch( n );
              String base = n.substring( 0, match.getStartIndex() );
              this.resultsMap.put( base, f );
              cnt++;
            }
          }
        }
      } catch ( REException ree ) {
        ConfigurationException e =
          new ConfigurationException( ree.toString() );
        throw( e );
      }
    }

    return ( cnt );

  } // private void storeResultList()

} // public class TestNavaDoc

// EOF: $RCSfile$ //