package com.dexels.navajo.util.navadoc;

/**
 * <p>Title: NavaDocTransformer</p>
 * <p>Description: performs the XSLT transformation on Navajo Web Services
 * (does the nitty-gritty work of the NavaDoc facility)</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels.com</p>
 * @author Matthew Eichler
 * @version $Id$
 */

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;
import java.util.Enumeration;
import java.util.Properties;

// XML stuff
import org.w3c.dom.*;
import javax.xml.transform.*;
import javax.xml.transform.stream.*;
import javax.xml.transform.dom.*;
import javax.xml.parsers.*;

// Xalan serialization
import org.apache.xalan.serialize.Serializer;
import org.apache.xalan.serialize.SerializerFactory;
import org.apache.xalan.templates.OutputProperties;


// logging
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

public class NavaDocTransformer {

  public static final String vcIdent = "$Id$";

  public static final Logger logger =
    Logger.getLogger( NavaDoc.class.getName() );

  // handy constants for file extensions
  public static final String BPFLEXT = "tml";
  public static final String BPCLEXT = "xsl";

  // paths
  private File styleSheetPath = null;
  private File servicesPath = null;
  private File targetPath = null;

  // XML transformation
  private TransformerFactory tFactory = TransformerFactory.newInstance();
  protected Transformer transformer = null;

  // XML Serialization for capture
  private Serializer serializer = null;
  private Properties outputProps =
    OutputProperties.getDefaultMethodProperties( "html" );

  // DOM Document Builder
  private DocumentBuilder dBuilder = null;
  private Document result = null;

  public NavaDocTransformer( File styPath, File svcPath, File trgPath )
    throws TransformerConfigurationException,
      ParserConfigurationException {

    // path housekeeping
    this.styleSheetPath = styPath;
    this.servicesPath = svcPath;
    this.targetPath = trgPath;

    // get an XSLT transformer for our style sheet
    this.transformer =
      tFactory.newTransformer( new StreamSource( this.styleSheetPath ) );
    this.dumpProperties();

    // get a DOM document builder
    this.dBuilder =
      ( DocumentBuilderFactory.newInstance() ).newDocumentBuilder();

    // get a Xalan XML serializer
    this.serializer =
      SerializerFactory.getSerializer( this.outputProps );
    this.dumpOutputProperties();

  } // public NavaDocTransformer

  // getters
  public Transformer getTransformer() { return( this.transformer ); }
  public Document getResult() { return( this.result ); }

  /**
   * Using the transformer based on the given style-sheet,
   * transforms all related BPFL and BPCL into a single document
   *
   * @param logical name of web service as a string
   */

  public void transformWebService( String pname, String sname ) {
    // start a new document
    DOMImplementation domImpl = this.dBuilder.getDOMImplementation();
    this.result = domImpl.createDocument(
      "http://www.w3.org/1999/xhtml", "span", null );
    Element root = this.result.getDocumentElement();
    root.setAttribute( "class", "navadoc" );
    Element eBF = this.result.createElement( "span" );
    eBF.setAttribute( "class", "bpfl" );
    Element eBC = this.result.createElement( "span" );
    eBC.setAttribute( "class", "bpcl" );

    // transform the BPFL and BPCL from a single stylesheet
    File fBFSrc = new File(
      this.servicesPath + File.separator + sname + "." + BPFLEXT );
    File fBCSrc = new File(
      this.servicesPath + File.separator + sname + "." + BPCLEXT );
    StreamSource sBFSrc = new StreamSource( fBFSrc );
    StreamSource sBCSrc = new StreamSource( fBCSrc );
    DOMResult dBFRes = new DOMResult( eBF );
    DOMResult dBCRes = new DOMResult( eBC );
    try {
      this.transformer.transform ( sBFSrc, dBFRes );
    } catch ( TransformerException te ) {
       logger.log( Priority.WARN, "unable to transform source '" + fBFSrc + "': " +
        te );
    }
    try {
      this.transformer.transform ( sBCSrc, dBCRes );
    } catch ( TransformerException te ) {
       logger.log( Priority.WARN, "unable to transform source '" + fBCSrc + "': " +
        te );
    }

    // combine the two document result nodes into one DOM
    root.appendChild( eBF );
    root.appendChild( eBC );

    this.capture( sname );

    logger.log( Priority.INFO, "finished transformation for '" + sname + "'" );

  }

  // debugging
  public void dumpProperties() {
    Properties props = this.transformer.getOutputProperties();
    Enumeration enum = props.propertyNames();
    while ( enum.hasMoreElements() ) {
      String s = (String) enum.nextElement();
      logger.log( Priority.DEBUG, "transformer property: " +
        s + " = " + props.getProperty( s ) );
    }
  }

  public void dumpOutputProperties() {
    Properties props = this.outputProps;
    Enumeration enum = props.propertyNames();
    while ( enum.hasMoreElements() ) {
      String s = (String) enum.nextElement();
      logger.log( Priority.DEBUG, "output property: " +
        s + " = " + props.getProperty( s ) );
    }
  }

  /**
   * captures the resulting DOM to a file in the target directory
   */
  public void capture( String sname ) {
    // Instantiate an Xalan XML serializer and use it
    // to serialize the output DOM to a file
    // using a default output format.
    File target = new File (
      this.targetPath + File.separator + sname + ".html" );
    try {
      FileWriter fw = new FileWriter( target );
      this.serializer.setWriter( fw );
      this.serializer.asDOMSerializer().serialize(
        this.result.getDocumentElement() );
      fw.close();
    } catch ( IOException ioe ) {
      logger.log( Priority.WARN, "unable to capture result to file '" +
        target + "': " + ioe );
    }
  }

}