
package com.dexels.navajo.util.navadoc;

import junit.framework.*;


import com.dexels.navajo.util.navadoc.*;

// XML stuff
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.parsers.ParserConfigurationException;

// logging
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

public class TestNavaDocTransformer extends TestCase {

  public static final Logger logger =
    Logger.getLogger( NavaDoc.class.getName() );

  NavaDocTestFixture fixture = new NavaDocTestFixture(this);

  private NavaDocConfigurator config =
    new NavaDocConfigurator();

  public TestNavaDocTransformer(String s) {
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

  public void testGetTransformer() {

    logger.log( Priority.DEBUG, "testing NavaDocTransformer to get a fully realized XML Transformer" );

    try {

       NavaDocTransformer transformer = new NavaDocTransformer(
        config.getPathProperty( "stylesheet-path" ),
        config.getPathProperty( "services-path" ),
        config.getPathProperty( "target-path" ) );

      Transformer t = transformer.getTransformer();
      this.assertEquals( "xml", t.getOutputProperty( "method" ) );

    } catch ( TransformerConfigurationException tce ) {
      fail( tce.toString() );
    } catch ( ParserConfigurationException pce ) {
      fail( pce.toString() );
    }

  }

  public void testTransformWebService() {

    logger.log( Priority.DEBUG, "testing NavaDocTransformer ability to transform web service" );

    try {

       NavaDocTransformer transformer = new NavaDocTransformer(
        config.getPathProperty( "stylesheet-path" ),
        config.getPathProperty( "services-path" ),
        config.getPathProperty( "target-path" ) );

     transformer.transformWebService( "EU", "euro" );
     Document d = transformer.getResult();
     NodeList nList = d.getElementsByTagName( "span" );
     assertEquals( 4, nList.getLength() );

    } catch ( TransformerConfigurationException tce ) {
      fail( tce.toString() );
    } catch ( ParserConfigurationException pce ) {
      fail( pce.toString() );
    }

  }

}
