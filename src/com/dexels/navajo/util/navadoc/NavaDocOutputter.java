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
import java.io.FileFilter;

// Xalan serialization
import org.apache.xalan.serialize.Serializer;
import org.apache.xalan.serialize.SerializerFactory;
import org.apache.xalan.templates.OutputProperties;

// logging
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import java.util.Set;
import java.util.Iterator;


public class NavaDocOutputter {

  public static final String vcIdent = "$Id$";

  public static final Logger logger =
    Logger.getLogger( NavaDocOutputter.class.getName() );

  private NavaDocBaseDOM dom = null;

  // paths
  private File targetPath = null;
  private File targetFile = null;

  // XML Serialization
  private Serializer serializer = null;
  private Properties outputProps = null;

  // indent setting
  private Integer indent = new Integer( 2 );

  private DirStack dirStack = null;
  private FileFilter ffilter = null;

  private StringBuffer s = new StringBuffer();

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
    this.init();
    this.targetFile = new File(
      this.targetPath + File.separator +
      this.dom.getBaseName() + ".html" );
    this.output();

  } // public NavaDocOutputter()

  public NavaDocOutputter( NavaDocBaseDOM d, File p, int i ) {
    this.dom = d;
    this.targetPath = p;
    this.indent = new Integer( i );
    this.init();
    this.targetFile = new File(
      this.targetPath + File.separator +
      this.dom.getBaseName() + ".html" );
    this.output();
  }

  public NavaDocOutputter( DirStack stack, File p, FileFilter filter ) {
    this.dirStack = stack;
    this.targetPath = p;
    this.ffilter = filter;
    this.init();

    final Set keys = this.dirStack.getIdxDocMap().keySet();
    final Iterator iter = keys.iterator();
    while ( iter.hasNext() ) {
      final String d = (String) iter.next();
      this.dom = (NavaDocBaseDOM) this.dirStack.getIdxDocMap().get( d );

      this.s = new StringBuffer();
      this.walkTargetTree( d, d, (NavaDocIndexDOM) this.dom );
      this.logger.log( Priority.DEBUG, s.toString() );

      this.targetFile = new File(
        this.targetPath + File.separator + d + File.separator + "index.html" );
      this.output();
    }

  }

  // ------------------------------------------------------------ public methods

  /**
   * Returns the full path of the target output
   * as a File object.  Convenient for testing
   *
   * @return outputted target File path
   */

  public File getTargetFile() {
    return ( this.targetFile );
  } // public File getTargetFile()

  // ----------------------------------------------------------- private methods

  // initializes properties for serializer
  private void init() {
    this.outputProps =
        OutputProperties.getDefaultMethodProperties( NavaDocConstants.OUTPUT_METHOD_VALUE );
    this.outputProps.setProperty( NavaDocConstants.OUTPUT_METHOD_PROP,
                                  NavaDocConstants.OUTPUT_METHOD_VALUE );

    // set ident values
    this.outputProps.setProperty( NavaDocConstants.INDENT,
                                  ( this.indent.intValue() > 0 ? "true" : "false" ) );
    this.outputProps.setProperty( NavaDocConstants.INDENT_AMOUNT,
                                  this.indent.toString() );

    // get a Xalan XML serializer
    this.serializer =
        SerializerFactory.getSerializer( this.outputProps );
    // this.dumpProperties();
  }

  // debugging

  private void dumpProperties() {
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

    try {
      FileWriter fw = new FileWriter( targetFile );

      this.serializer.setWriter( fw );
      this.serializer.asDOMSerializer().serialize(
        this.dom.getDocument().getDocumentElement() );
      fw.close();
    } catch ( IOException ioe ) {
      logger.log( Priority.WARN, "unable to capture result to file '" +
        this.targetFile.getAbsoluteFile() + "': " + ioe );
    }
  } // private void output()

  private void walkTargetTree( final String dir, final String base,
    final NavaDocIndexDOM idx ) {

    final String relPath =
      dir.replaceFirst( ( base + File.separator ), "" );

    if ( ! dir.equals( base ) ) {
      this.s.append( " ... " + dir );
      idx.addSubDirEntry( dir );
    }

    File p = new File( this.targetPath, dir );
    final File[] contents = p.listFiles( this.ffilter );
    for ( int i = 0; i < contents.length; i++ ) {
      final File found = contents[i];
      if ( found.isDirectory() ) {
        final String sub = found.toString().replaceFirst(
           ( this.targetPath + File.separator ), "" );
        if ( ( sub != null ) && ( sub.length() > 0 ) ) {

          this.walkTargetTree( sub, base, idx );
        } else {
          this.logger.log( Priority.WARN,
            "failed parse of sub-directory out of path '" + found + "'" );
        }
      }
    }

  } // private void walkTargetTree()



} // public class NavaDocOutputter

// EOF: $RCSfile$ //