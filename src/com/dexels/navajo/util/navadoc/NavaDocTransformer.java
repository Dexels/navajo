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

import com.dexels.navajo.util.navadoc.NavaDocBaseDOM;
import com.dexels.navajo.util.navadoc.NavaDocIndexDOM;

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


public class NavaDocTransformer extends NavaDocBaseDOM {

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

  // XML transformation
  private TransformerFactory tFactory = TransformerFactory.newInstance();
  protected Transformer transformer = null;

  // error information
  private String errorText = null;

  public NavaDocTransformer( File styPath, File svcPath )
    throws TransformerConfigurationException,
      ParserConfigurationException {

    super();

    // path housekeeping
    this.styleSheetPath = styPath;
    this.servicesPath = svcPath;

    // get an XSLT transformer for our style sheet
    this.transformer =
        tFactory.newTransformer( new StreamSource( this.styleSheetPath ) );
    this.dumpProperties();

  } // public NavaDocTransformer

  // getters
  public Transformer getTransformer() {
    return ( this.transformer );
  }

  /**
   * Using the transformer based on the given style-sheet,
   * transforms all related BPFL and BPCL into a single document
   *
   * @param logical name of web service as a string
   */

  public void transformWebService( String sname ) {

    // new web service, new document
    this.newDocument();

    this.baseName = sname;
    this.setHeaders();

    Element eBF = this.dom.createElement( "span" );

    eBF.setAttribute( "class", "bpfl" );
    Element eBC = this.dom.createElement( "span" );

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
    this.addBody( "document-body" );

    try {
      this.errorText = null;
      this.transformer.transform( sBFSrc, dBFRes );
      this.body.appendChild( eBF );
    } catch ( TransformerException te ) {
      this.errorText = "unable to transform source '" + fBFSrc + "': " + te;
      logger.log( Priority.WARN, this.errorText );
      this.setErrorText( this.body );
    }

    try {
      this.errorText = null;
      this.transformer.transform( sBCSrc, dBCRes );
      this.body.appendChild( eBC );
    } catch ( TransformerException te ) {
      this.errorText = "unable to transform source '" + fBCSrc + "': " + te;
      logger.log( Priority.WARN, this.errorText );
      this.setErrorText( body );
    }

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
  } // public void dumpProperties()

  // ----------------------------------------------------  private methods

  // sets the error text into the document
  private void setErrorText( Element body ) {

    Element p = this.dom.createElement( "p" );
    p.setAttribute( "class", "error" );
    Text t = this.dom.createTextNode( this.errorText );
    p.appendChild( t );
    this.body.appendChild( p );

  } // private void setErrorText()

  // over-ridden setHeaders method
  private void setHeaders() {
    String titl =
      ( ( ( this.projectName != null ) &&
          ( this.projectName.length() > 0 ) ? this.projectName : "Web" )
        + " Service: " ) + this.serviceName;
    this.setHeaders( titl );
  } //private void setHeaders()

} // public class NavaDocTransformer

// EOF: $RCSfile$ //
