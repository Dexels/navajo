package com.dexels.navajo.util.navadoc;

/**
 * <p>Title: NavaDocOuputter</p>
 * <p>Description: Responsible for outputting the results
 * document somewhere, usually the filesystem</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels.com</p>
 * @author Matthew Eichler
 * @version $Id$
 */

import com.dexels.navajo.util.navadoc.NavaDocBaseDOM;

// useful Java stuff
import java.util.Properties;
import java.util.Enumeration;

// IO
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

// Xalan serialization
import org.apache.xalan.serialize.Serializer;
import org.apache.xalan.serialize.SerializerFactory;
import org.apache.xalan.templates.OutputProperties;

// logging
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

public class NavaDocOutputter {

  public static final String vcIdent = "$Id$";

  public static final Logger logger =
    Logger.getLogger( NavaDocOutputter.class.getName() );

  public static final String DEFAULT_METHOD = "xml";

  private NavaDocBaseDOM dom = null;

  // paths
  private File targetPath = null;

  // XML Serialization
  private Serializer serializer = null;
  private Properties outputProps = null;

  /**
   * Contructs a NavaDocOutputter based on the current transformation
   * result
   *
   * @param a NavaDocTransformer which contains the result DOM
   * @param File path to target directory where output goes
   */

  public NavaDocOutputter( NavaDocBaseDOM d, File p ) {

    this.dom = d;
    this.targetPath = p;
    this.outputProps =
      OutputProperties.getDefaultMethodProperties( DEFAULT_METHOD );

    // get a Xalan XML serializer
    this.serializer =
        SerializerFactory.getSerializer( this.outputProps );
    this.dumpProperties();

    // do the work
    this.output();

  } // public NavaDocOutputter()

  // debugging

  public void dumpProperties() {
    Properties props = this.outputProps;
    Enumeration enum = props.propertyNames();

    while ( enum.hasMoreElements() ) {
      String s = (String) enum.nextElement();

      logger.log( Priority.DEBUG, "output property: " +
        s + " = " + props.getProperty( s ) );
    }
  } // public void dumpProperties()

  /**
   * outputs the resulting DOM to a file in the target directory
   */
  private void output() {
    // Instantiate an Xalan XML serializer and use it
    // to serialize the output DOM to a file
    // using a default output format.
    File target = new File(
        this.targetPath + File.separator +
        this.dom.getBaseName() + ".html" );

    try {
      FileWriter fw = new FileWriter( target );

      this.serializer.setWriter( fw );
      this.serializer.asDOMSerializer().serialize(
        this.dom.getDocument().getDocumentElement() );
      fw.close();
    } catch ( IOException ioe ) {
      logger.log( Priority.WARN, "unable to capture result to file '" +
        target + "': " + ioe );
    }
  } // public void capture()

} // public class NavaDocOutputter

// EOF: $RCSfile$ //