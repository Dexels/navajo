package com.dexels.navajo.util.navadoc;

/**
 * <p>Title: NavaDoc: NavaConverter</p>
 * <p>Description: converts from two-file old Navajo script format
 * to new single file script format</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels.com</p>
 * @author Matthew Eichler
 * @version 1.0
 */

import java.io.File;
import java.util.Iterator;

// logging
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

// W3C DOM
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Attr;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.NamedNodeMap;

// XML Parsers
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.OutputKeys;
import com.dexels.navajo.util.navadoc.config.*;

public class NavaConverter {

  public static final String vcIdent = "$Id$";

  public static final Logger logger =
    Logger.getLogger( NavaConverter.class.getName() );

  private NavaDocConfigurator config = new NavaDocConfigurator();

  // paths
  private File servicesPath = null;
  private File targetPath = null;

  private ServicesList list = null;

  // document builder & transformer
  DocumentBuilder docBuilder = null;
  Transformer transformer = null;

  // document paths and DOM's
  private String tmlPath = null;
  private String xslPath = null;

  private File tmlFile = null;
  private File xslFile = null;

  private Document tmlDoc = null;
  private Document xslDoc = null;

  private Element tmlRoot = null;
  private Element xslRoot = null;

  // ------------------------------------------------------------ public methods

  public NavaConverter()
    throws ConfigurationException {

    config.configure();

    this.servicesPath = config.getPathProperty( "services-path" );
    this.targetPath = config.getPathProperty( "target-path" );

    this.list = new ServicesList( this.servicesPath );

    try {

      this.docBuilder =
        DocumentBuilderFactory.newInstance().newDocumentBuilder();

      this.transformer = TransformerFactory.newInstance().newTransformer(); ;
      this.transformer.setOutputProperty(OutputKeys.METHOD, "xml");
      transformer.setOutputProperty(OutputKeys.INDENT, "yes");

      Iterator iter = this.list.iterator();

      while ( iter.hasNext() ) {
        String baseName = (String) iter.next();

        this.tmlPath = this.servicesPath + File.separator +
          baseName + ".tml";
        this.tmlFile = new File ( this.tmlPath );
        this.tmlDoc = docBuilder.parse( this.tmlFile );
        this.tmlRoot = this.tmlDoc.getDocumentElement();

        this.xslPath = this.servicesPath + File.separator +
          baseName + ".xsl";
        this.xslFile = new File ( this.xslPath );
        this.xslDoc = docBuilder.parse( this.xslFile );
        this.xslRoot = this.xslDoc.getDocumentElement();

        this.convert();
        this.write( baseName );

      }

    } catch ( Exception e ) {
      logger.log( Priority.ERROR, e.toString() );
    }


  } // public NavaConverter()

  public static void main( String[] args )
    throws ConfigurationException {

    NavaConverter converter = new NavaConverter();

    logger.log( Priority.INFO, "finished" );

  } // main()

  // ----------------------------------------------------------- private methods

  private void convert() {

    // copy root node attributes from TML to XSL
    NamedNodeMap attrMap = this.tmlRoot.getAttributes();
    if ( attrMap != null ) {
      for ( int i = 0; i < attrMap.getLength(); i++ ) {
         Attr a = (Attr) this.xslDoc.importNode( attrMap.item( i ), false );
         this.xslRoot.setAttributeNode( a );
      }
    }

    // copy the children of the TML over to the XSL document
    NodeList children = this.tmlRoot.getChildNodes();
    Node refChild = this.xslRoot.getFirstChild();
    if ( children != null ) {
      for ( int i = 0; i < children.getLength(); i++ ) {
        Node imported = this.xslDoc.importNode( children.item( i ), true );
        this.xslRoot.insertBefore( imported, refChild );
      }
    }

  }

  private void write( String name )
    throws TransformerException {

    File outputFile = new File (
      this.targetPath + File.separator + name + ".xsl" );

    this.xslRoot.normalize();

    this.transformer.transform(
      new DOMSource( this.xslDoc ), new StreamResult( outputFile ) );

    logger.log( Priority.INFO, "wrote new XSL file to " +
      outputFile.getAbsoluteFile() );
  }

} // public class NavaConverter

// EOF: $RCSfile$ //
