package com.dexels.navajo.util.navadoc;

import java.io.*;
import java.util.*;

import org.custommonkey.xmlunit.*;
import org.w3c.dom.*;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import com.dexels.navajo.util.navadoc.*;

// logging
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

public class TestNavaDocBaseDOM extends XMLTestCase {

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

  public TestNavaDocBaseDOM(String s)
    throws Exception {

    super(s);
    this.fixture = new NavaDocTestFixture(this);

  }

  protected void setUp()
    throws Exception {
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
      File r = (File) this.resultsMap.get( "TestNavaDocBaseDOM.testSetCssUri" );
      if ( r != null ) {
        r.delete();
        logger.log( Priority.DEBUG, "removed file '" +
          r.getAbsoluteFile() + "'" );
      }
      r = (File) this.resultsMap.get( "TestNavaDocBaseDOM.testSetProjectName" );
      if ( r != null ) {
        r.delete();
        logger.log( Priority.DEBUG, "removed file '" +
          r.getAbsoluteFile() + "'" );
      }
    }

  } // tearDown()

  public void testGetBaseName()
    throws ParserConfigurationException {
    NavaDocBaseDOM dom = new NavaDocBaseDOM();
    this.assertEquals( "index", dom.getBaseName() );
  }

  public void testGetBaseName2ndConstructor()
    throws ParserConfigurationException {
    String s = "TestNavaDoc.testGetBaseName2ndConstructor";
    NavaDocBaseDOM dom = new NavaDocBaseDOM( s );
    this.assertEquals( s, dom.getBaseName() );
  }

  public void testGetDocument()
    throws ParserConfigurationException {
    String s = "TestNavaDoc.testGetDocument";
    NavaDocBaseDOM dom = new NavaDocBaseDOM( s );
    Document doc = dom.getDocument();
    Element root = doc.getDocumentElement();
    this.assertEquals( "html", root.getTagName() );
  }

  public void testSetCssUri()
    throws ParserConfigurationException,
      FileNotFoundException, IOException,
      SAXException {
    String s = "TestNavaDocBaseDOM.testSetCssUri";
    DOMTestWrapper dom = new DOMTestWrapper( s );
    dom.setCssUri( s + ".css" );
    dom.setHeaders( s );
    this.resultsMap.put( s, capture( dom ) );

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

  public void testSetProjectName()
    throws ParserConfigurationException,
      FileNotFoundException, IOException,
      SAXException {
    String s = "TestNavaDocBaseDOM.testSetProjectName";
    DOMTestWrapper dom = new DOMTestWrapper( s );
    dom.setProjectName( s );
    dom.setHeaders( s );
    this.capture( dom );
    this.resultsMap.put( s, capture( dom ) );

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
  private File capture ( NavaDocBaseDOM dom ) {
    NavaDocOutputter o = new NavaDocOutputter( dom, this.targetPath );
    logger.log( Priority.DEBUG, "captured control document: '" +
      o.getTargetFile().getAbsoluteFile() + "'" );
    return ( o.getTargetFile() );
  }

  // a wrapper for test some protected methods
  class DOMTestWrapper extends NavaDocBaseDOM {

    public DOMTestWrapper( String s )
      throws ParserConfigurationException {
      super(s);
    }

    public void setHeaders( String s ) {
      super.setHeaders( s );
    }
  } //class DOMTestWrapper

}// public class TestNavaDocBaseDOM


// EOF: $RCSfile$ //

