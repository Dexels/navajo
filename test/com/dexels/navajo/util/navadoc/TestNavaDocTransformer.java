
package com.dexels.navajo.util.navadoc;


import junit.framework.*;

import com.dexels.navajo.util.navadoc.*;

// XML stuff
import org.w3c.dom.*;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.parsers.ParserConfigurationException;

// logging
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;


public class TestNavaDocTransformer extends TestCase {

  public static final Logger logger =
    Logger.getLogger( NavaDoc.class.getName() );

  NavaDocTestFixture fixture = null;

  private NavaDocConfigurator config =
    new NavaDocConfigurator();

  public TestNavaDocTransformer( String s ) {
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

  public void testGetTransformer()
    throws TransformerConfigurationException,
      ParserConfigurationException {

    logger.log( Priority.DEBUG, "testing NavaDocTransformer to get a fully realized XML Transformer" );

    NavaDocTransformer transformer = new NavaDocTransformer(
        config.getPathProperty( "stylesheet-path" ),
        config.getPathProperty( "services-path" ) );

    Transformer t = transformer.getTransformer();

    this.assertEquals( "xml", t.getOutputProperty( "method" ) );

  }

  public void testTransformWebService()
    throws TransformerConfigurationException,
      ParserConfigurationException {

    logger.log( Priority.DEBUG, "testing NavaDocTransformer ability to transform web service" );

    NavaDocTransformer transformer = new NavaDocTransformer(
        config.getPathProperty( "stylesheet-path" ),
        config.getPathProperty( "services-path" ) );

    transformer.transformWebService( "euro" );
    Document d = transformer.getDocument();
    NodeList nList = d.getElementsByTagName( "span" );

    assertEquals( 4, nList.getLength() );

  }

  public void testTransformWebServiceWithCss()
    throws TransformerConfigurationException,
      ParserConfigurationException {

    logger.log( Priority.DEBUG, "testing NavaDocTransformer with option CSS URI" );

    NavaDocTransformer transformer = new NavaDocTransformer(
        config.getPathProperty( "stylesheet-path" ),
        config.getPathProperty( "services-path" ) );

    transformer.setCssUri( "./am/I/stupid.css" );
    transformer.transformWebService( "euro" );
    Document d = transformer.getDocument();
    NodeList nList = d.getElementsByTagName( "link" );
    Element e = (Element) nList.item( 0 );

    assertEquals( "stylesheet", e.getAttribute( "rel" ) );

  }

  public void testGetNotesFromBPFL()
    throws TransformerConfigurationException,
      ParserConfigurationException {

    logger.log( Priority.DEBUG, "testing get notes method, BPFL" );

    NavaDocTransformer transformer = new NavaDocTransformer(
        config.getPathProperty( "stylesheet-path" ),
        config.getPathProperty( "services-path" ) );

    transformer.transformWebService( "euro" );
    String s = transformer.getNotes();

    assertEquals( "Euro Calculator", s.substring( 0, 15 ) );

  }

  public void testGetNotesFromBPCL()
    throws TransformerConfigurationException,
      ParserConfigurationException {

    logger.log( Priority.DEBUG, "testing get notes method, BPCL" );

    NavaDocTransformer transformer = new NavaDocTransformer(
        config.getPathProperty( "stylesheet-path" ),
        config.getPathProperty( "services-path" ) );

    transformer.transformWebService( "ProcessBirthdateQueryMembers" );
    String s = transformer.getNotes();

    assertEquals( "Mit WEB.DE", s.substring( 0, 10 ) );

  }

} // public class TestNavaDocTransformer
