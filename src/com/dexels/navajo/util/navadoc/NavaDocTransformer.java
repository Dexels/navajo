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

  // paths
  private File styleSheetPath = null;
  private File servicesPath = null;

  // current service we last worked on
  private String serviceName = null;

  // XML transformation
  private TransformerFactory tFactory = TransformerFactory.newInstance();
  protected Transformer transformer = null;

  // notes/description of the current web service
  private String notes = null;

  // error information
  private String errorText = null;

  // indent while transforming?
  private Integer indent = new Integer( 0 );

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

  public NavaDocTransformer( File styPath, File svcPath, String ind )
    throws TransformerConfigurationException,
      ParserConfigurationException {

    super();

    // path housekeeping
    this.styleSheetPath = styPath;
    this.servicesPath = svcPath;

    // set indentation
    this.setIndent( ind );

    // get an XSLT transformer for our style sheet
    this.transformer =
        tFactory.newTransformer( new StreamSource( this.styleSheetPath ) );

    this.setOutputProperties();

  } // public NavaDocTransformer [2]

  /**
   * a convenience method for directly accessing the
   * XML transformer object
   *
   * @return the XML transformer
   */

  public Transformer getTransformer() {
    return ( this.transformer );
  }

  /**
   * Returns the notes/description of the current
   * transformed web service.  The notes may occur in either
   * of the BPFL or BPCL scripts; if both exist, the
   * former takes precedence over the later
   *
   * @return String notes/description of web service
   */

  public String getNotes() {
    return ( this.notes );
  }

  /**
   * sets indentation property based on any positive hints
   * from the configuration
   * @param indentation property as a String
   */

  public void setIndent( String i ) {
    this.indent = new Integer(Integer.parseInt(i));
  }

  /**
   * @return whether transformer should indent if necessary as boolean
   */

  public boolean shouldIndent() {
    return ( this.indent.intValue() > 0 );
  }

  /**
   * Using the transformer based on the given style-sheet,
   * transforms all related BPFL and BPCL into a single document.
   * This method can be called repeatedly as it generated a
   * new DOM for each transformation result
   *
   * @param logical name of web service as a string (no file extensions
   * please)
   */

  public void transformWebService( final String sname ) {

    this.setOutputProperties();

    // new web service, new document
    this.newDocument();

    this.baseName = sname;
    this.setHeaders();
    this.addBody( "document-body" );

    try {
      // get a DOM document builder
      DocumentBuilder dBuilder =
        ( DocumentBuilderFactory.newInstance() ).newDocumentBuilder();
    } catch ( ParserConfigurationException pce ) {
      this.errorText = "unable to transform source " +
          "can't get a document builder: " + pce;
      logger.log( Priority.WARN, this.errorText );
      this.setErrorText( this.body );
      return;
    }

    final Element span = this.dom.createElement( "span" );

    span.setAttribute( "class", "navascript" );

    final File sFile = new File(
        this.servicesPath  + File.separator + sname + "." + NavaDocConstants.NAVASCRIPT_EXT );

    try {
      final Document sDoc = dBuilder.parse( sFile );
      DOMSource domSrc = new DOMSource( sDoc );
      DOMResult domRes = new DOMResult( span );

      this.errorText = null;
      this.transformer.transform( domSrc, domRes );
      this.body.appendChild( span );

      final Element root = sDoc.getDocumentElement();
      this.notes = root.getAttribute( "notes" );

    } catch ( Exception e ) {

      this.errorText = "unable to transform source '" + sFile + "': " + e;
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
  } // private void setHeaders()


  // sets all necessary output parameters
  private void setOutputProperties() {
    this.transformer.setOutputProperty(
      NavaDocConstants.OUTPUT_METHOD_PROP, NavaDocConstants.OUTPUT_METHOD_VALUE );

    // set ident values
    this.transformer.setOutputProperty( NavaDocConstants.INDENT,
      ( this.indent.intValue() > 0 ? "true" : "false" ) );
    this.transformer.setOutputProperty( NavaDocConstants.INDENT_AMOUNT,
       this.indent.toString() );
    // this.dumpProperties();
  }

} // public class NavaDocTransformer

// EOF: $RCSfile$ //
