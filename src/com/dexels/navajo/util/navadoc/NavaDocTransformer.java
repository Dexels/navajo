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
import java.util.Properties;
import java.util.Enumeration;

// XML stuff
import org.w3c.dom.*;
import org.w3c.dom.html.*;
import javax.xml.transform.*;
import javax.xml.transform.stream.*;
import javax.xml.transform.dom.*;
import javax.xml.parsers.*;

// logging
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;


public class NavaDocTransformer {

  public static final String vcIdent = "$Id$";

  public static final Logger logger =
    Logger.getLogger( NavaDocTransformer.class.getName() );

  // handy constants for file extensions
  public static final String BPFLEXT = "tml";
  public static final String BPCLEXT = "xsl";

  // paths
  private File styleSheetPath = null;
  private File servicesPath = null;

  // current service we last worked on
  private String serviceName = null;

  // optional properties for XHTML document headers
  private String projectName = null;
  private String cssUri = null;

  // XML transformation
  private TransformerFactory tFactory = TransformerFactory.newInstance();
  protected Transformer transformer = null;

  // DOM Document Builder
  private DocumentBuilder dBuilder = null;
  private Document result = null;

  // error information
  private String errorText = null;

  public NavaDocTransformer( File styPath, File svcPath )
    throws TransformerConfigurationException,
      ParserConfigurationException {

    // path housekeeping
    this.styleSheetPath = styPath;
    this.servicesPath = svcPath;

    // get an XSLT transformer for our style sheet
    this.transformer =
        tFactory.newTransformer( new StreamSource( this.styleSheetPath ) );
    this.dumpProperties();

    // get a DOM document builder
    this.dBuilder =
        ( DocumentBuilderFactory.newInstance() ).newDocumentBuilder();

  } // public NavaDocTransformer

  // setters for optional header properties
  public void setProjectName( String pName ) {
    this.projectName = pName;
  }

  public void setCssUri( String uri ) {
    this.cssUri = uri;
  }

  // getters
  public Transformer getTransformer() {
    return ( this.transformer );
  }

  public String getServiceName() {
    return ( this.serviceName );
  }

  public Document getResult() {
    return ( this.result );
  }

  /**
   * Using the transformer based on the given style-sheet,
   * transforms all related BPFL and BPCL into a single document
   *
   * @param logical name of web service as a string
   */

  public void transformWebService( String sname ) {

    this.serviceName = sname;

    // start a new document
    DOMImplementation domImpl = this.dBuilder.getDOMImplementation();

    this.result = domImpl.createDocument(
          "http://www.w3.org/1999/xhtml", "html", null );
    this.setHeaders();

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

    // combine the two document result nodes into one DOM
    Element root = this.result.getDocumentElement();
    Element body = this.result.createElement( "body" );
    body.setAttribute( "class", "document-body" );

    try {
      this.errorText = null;
      this.transformer.transform( sBFSrc, dBFRes );
      body.appendChild( eBF );
    } catch ( TransformerException te ) {
      this.errorText = "unable to transform source '" + fBFSrc + "': " + te;
      logger.log( Priority.WARN, this.errorText );
      this.setErrorText( body );
    }

    try {
      this.errorText = null;
      this.transformer.transform( sBCSrc, dBCRes );
      body.appendChild( eBC );
    } catch ( TransformerException te ) {
      this.errorText = "unable to transform source '" + fBCSrc + "': " + te;
      logger.log( Priority.WARN, this.errorText );
      this.setErrorText( body );
    }

    root.appendChild( body );
    logger.log( Priority.INFO, "finished transformation for '" + sname + "'" );

  } // public void transformWebService()

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

  // ----------------------------------------------------  private methods

  // sets the error text into the document
  private void setErrorText( Element body ) {

    Element p = this.result.createElement( "p" );
    p.setAttribute( "class", "error" );
    Text t = this.result.createTextNode( this.errorText );
    p.appendChild( t );
    body.appendChild( p );

  } // private void setErrorText()

  private void setHeaders() {

    Element root = this.result.getDocumentElement();

    root.setAttribute( "class", "navadoc" );
    root.setAttribute( "xmlns", "http://www.w3.org/1999/xhtml" );

    Element header = this.result.createElement( "head" );

    Element metaGen = this.result.createElement( "meta" );

    metaGen.setAttribute( "http-equiv", "generator" );
    metaGen.setAttribute( "content", NavaDocTransformer.vcIdent );
    header.appendChild( metaGen );

    Element title = this.result.createElement( "title" );
    Text tText = this.result.createTextNode(
      ( ( ( this.projectName != null ) &&
          ( this.projectName.length() > 0 ) ? this.projectName : "Web" )
        + " Service: " ) + this.serviceName );

    title.appendChild( tText );
    header.appendChild( title );

    if ( ( this.cssUri != null ) && ( this.cssUri.length() > 0 ) ) {
      Element css = this.result.createElement( "link" );

      css.setAttribute( "rel", "stylesheet" );
      css.setAttribute( "type", "text/css" );
      css.setAttribute( "href", this.cssUri );
      header.appendChild( css );
    }

    root.appendChild( header );

  }

}
