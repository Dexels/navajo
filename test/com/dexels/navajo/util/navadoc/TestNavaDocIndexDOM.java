package com.dexels.navajo.util.navadoc;

import java.io.*;
import java.util.*;

import org.custommonkey.xmlunit.*;
import org.w3c.dom.*;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import com.dexels.navajo.util.navadoc.*;

import com.dexels.navajo.util.navadoc.*;

// logging
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

public class TestNavaDocIndexDOM extends XMLTestCase {

  public static final Logger logger =
    Logger.getLogger( TestNavaDocBaseDOM.class.getName() );

  boolean captureOn = false;

  NavaDocTestFixture fixture = null;
  private HashMap resultsMap = new HashMap();

  private NavaDocConfigurator config =
    new NavaDocConfigurator();

  // our own XMLUnit difference listener
  private DifferenceListener dListener =
    new NavaDocDifferenceListener();

  // paths
  private File targetPath = null;

  public TestNavaDocIndexDOM(String s) {
    super(s);
  }

  protected void setUp()
    throws Exception {
    this.fixture = new NavaDocTestFixture(this);
    fixture.setUp();
    try {
      this.config.configure();
    } catch ( ConfigurationException ce ) {
      this.fail( this.getClass() + "cannot configure: " +
        ce );
    }
    this.targetPath = this.config.getPathProperty( "target-path" );

    String save = System.getProperty( "saveResults" );
    if ( save != null &&
         ( save.compareToIgnoreCase( "yes" ) == 0 ) ) {
      this.captureOn = true;
      logger.log( Priority.DEBUG, "capture on, control HTML documents " +
        "will be kept in '" + this.targetPath.getAbsolutePath() + "'" );
    }
  }

  protected void tearDown() {
    fixture.tearDown();

    if ( ! this.captureOn ) {
      // @todo: this will get factored into the fixture sometime
      File r = (File) this.resultsMap.get( "index" );
      if ( r != null ) {
        r.delete();
        logger.log( Priority.DEBUG, "removed file '" +
          r.getAbsoluteFile() + "'" );
      }
    }

  }

  public void testAddEntry()
    throws ParserConfigurationException,
      FileNotFoundException, IOException,
      SAXException {

    String s = "TestNavaDocIndexDOM.testAddEntry";
    NavaDocIndexDOM dom = new NavaDocIndexDOM( s,  s + ".css" );
    dom.addEntry( s, s + " description" );
    this.resultsMap.put( s, this.capture( dom ) );

    // get and compare documents
    File e = (File) this.fixture.getExpectedHtmlMap().get( s );
    File r = (File) this.resultsMap.get( s );
    logger.log( Priority.DEBUG, "expected HTML file is '" +
      e.getAbsoluteFile() + "'" );
    logger.log( Priority.DEBUG, "results HTML file is '" +
      r.getAbsoluteFile() + "'" );
    FileReader expected = new FileReader( e );
    FileReader result = new FileReader( r );
    Diff d = new Diff( expected, result );
    d.overrideDifferenceListener( this.dListener );
    this.assertTrue( s + ".html reasonably correct", d.similar() );

  }

    // todo: factor this into some kind of generic text fixture super class
  private File capture ( NavaDocIndexDOM dom ) {
    NavaDocOutputter o = new NavaDocOutputter( dom, this.targetPath );
    logger.log( Priority.DEBUG, "captured control document: '" +
      o.getTargetFile().getAbsoluteFile() + "'" );
    return ( o.getTargetFile() );
  }

}
