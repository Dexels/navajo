package com.dexels.navajo.util.navadoc;

import junit.framework.*;
import org.custommonkey.xmlunit.*;

// test fixture
import com.dexels.navajo.util.navadoc.NavaDocTestFixture;
import com.dexels.navajo.util.navadoc.NavaDocDifferenceListener;

import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.Iterator;

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

  // our own XMLUnit difference listener
  private DifferenceListener dListener =
    new NavaDocDifferenceListener();

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

    // check if we want to save the results
    String save = System.getProperty( "saveResults" );
    if ( save != null &&
         ( save.compareToIgnoreCase( "yes" ) == 0 ) ) {
      logger.log( Priority.INFO, "HTML results pages saved in '" +
        this.targetPath + "'" );
      return;
    }

    // remove results pages
    Set keys = this.resultsMap.keySet();
    Iterator iter = keys.iterator();
    while ( iter.hasNext() ) {
      File r = (File) this.resultsMap.get( iter.next() );
      r.delete();
      logger.log( Priority.DEBUG, "removed file '" +
        r.getAbsoluteFile() + "'" );
    }
  }

  public void testNavaDocContructor() {
    try {
      NavaDoc documenter = new NavaDoc();
      this.assertNotNull( documenter );
      int cnt = this.storeResultList();
    } catch ( ConfigurationException e ) {
      fail( "testGetLoggerConfig() failed with Exception: " + e.toString() );
    }
  }

  public void testCount() {
    try {
      NavaDoc documenter = new NavaDoc();
      this.assertTrue( ( documenter.count() > 0 ) &&
                       ( documenter.count() < 10 ) );
    } catch ( ConfigurationException e ) {
      fail( "testCount() failed with Exception: " + e.toString() );
    }
  }


  public void testNumberOfResults() {
    try {
      NavaDoc documenter = new NavaDoc();
      int cnt = this.storeResultList();
      // because of the index page, our results
      // should always be the number of web services
      // plus one
      this.assertEquals( ( 1 + documenter.count() ), cnt );
    } catch ( Exception e ) {
      fail( e.toString() );
    }

  }

  public void testHtmlResultPage() {
    try {
      NavaDoc documenter = new NavaDoc();
      int cnt = this.storeResultList();
      File e = (File) this.fixture.getExpectedHtmlMap().get( "euro" );
      File r = (File) this.resultsMap.get( "euro" );
      logger.log( Priority.DEBUG, "expected HTML file is '" +
        e.getAbsoluteFile() + "'" );
      logger.log( Priority.DEBUG, "results HTML file is '" +
        r.getAbsoluteFile() + "'" );
      FileReader expected = new FileReader( e );
      FileReader result = new FileReader( r );
      Diff d = new Diff( expected, result );
      d.overrideDifferenceListener( this.dListener );
      this.assertTrue( d.toString(), d.similar() );
    } catch ( Exception e ) {
      fail( e.toString() );
    }

  }

  public void testHtmlResultPageWithErrorText() {
    try {
      NavaDoc documenter = new NavaDoc();
      int cnt = this.storeResultList();
      File e = (File) this.fixture.getExpectedHtmlMap().get( "mangled" );
      File r = (File) this.resultsMap.get( "mangled" );
      logger.log( Priority.DEBUG, "expected HTML file is '" +
        e.getAbsoluteFile() + "'" );
      logger.log( Priority.DEBUG, "results HTML file is '" +
        r.getAbsoluteFile() + "'" );
      FileReader expected = new FileReader( e );
      FileReader result = new FileReader( r );
      Diff d = new Diff( expected, result );
      d.overrideDifferenceListener( this.dListener );
      this.assertTrue( "mangled.html reasonably correct", d.similar() );
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