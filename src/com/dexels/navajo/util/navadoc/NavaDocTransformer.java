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
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.stream.StreamSource;
import javax.xml.transform.TransformerConfigurationException;

// logging
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

public class NavaDocTransformer {

  public static final Logger logger =
    Logger.getLogger( NavaDoc.class.getName() );

  // handy constants for file extensions
  public static final String BPFLEXT = "tml";
  public static final String BPCLEXT = "tsl";

  // paths
  private File styleSheetPath = null;
  private File servicesPath = null;
  private File targetPath = null;

  // XML transformation
  private TransformerFactory tFactory = TransformerFactory.newInstance();
  protected Transformer transformer = null;

  public NavaDocTransformer( File styPath, File svcPath, File trgPath )
    throws TransformerConfigurationException {

    // path housekeeping
    this.styleSheetPath = styPath;
    this.servicesPath = svcPath;
    this.targetPath = trgPath;

    // get an XSLT transformer for our style sheet
    this.transformer =
      tFactory.newTransformer( new StreamSource( this.styleSheetPath ) );

    Properties props = this.transformer.getOutputProperties();
    Enumeration enum = props.propertyNames();
    while ( enum.hasMoreElements() ) {
      String s = (String) enum.nextElement();
      logger.log( Priority.DEBUG, s + " = " + props.getProperty( s ) );
    }

  } // public NavaDocTransformer

  // getters
  public Transformer getTransformer() { return( this.transformer ); }

}