package com.dexels.navajo.util.navadoc;


/**
 * <p>Title: NavaDoc</p>
 * <p>Description: Automated documentation utility for Navajo Web Services</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author Matthew Eichler
 * @version $Revision$
 */

import java.io.File;
import java.io.FileFilter;
import java.util.Iterator;
import java.util.Stack;

// logging
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

// XML stuff
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.parsers.ParserConfigurationException;

public class NavaDoc {

  public static final String vcIdent = "$Id$";

  public static final Logger logger =
    Logger.getLogger( NavaDoc.class.getName() );

  private NavaDocConfigurator config = new NavaDocConfigurator();

  // paths
  private File styleSheetPath = null;
  private File servicesPath = null;
  private File targetPath = null;

  // output settings
  private String pname = null;
  private String cssUri = null;
  private String indent = null;

  private ServicesList list = null;
  private NavaDocTransformer transformer = null;
  private NavaDocIndexDOM index = null;

  private Stack subDirs = new Stack();

  /**
   * Outside mediator object which controls all the
   * inner documentation objects, looping through the
   * list of services and generating HTML content.
   * NavaDoc also keeps a nice index page of all the services
   * found
   *
   * @throws ConfigurationException when required configuration
   * objects are wrong or don't exist
   */

  public NavaDoc()
    throws ConfigurationException {

    config.configure();

    this.styleSheetPath = config.getPathProperty( "stylesheet-path" );
    this.servicesPath = config.getPathProperty( "services-path" );
    this.targetPath = config.getPathProperty( "target-path" );

    // optional parameters, null's OK
    this.pname = this.config.getStringProperty( "project-name" );
    this.cssUri = this.config.getStringProperty( "css-uri" );
    this.indent = this.config.getStringProperty( "indent" );

    this.walkTree( this.servicesPath, this.config.getFileFilter() );

  }

  /**
   * Provides a count of all the web services found based
   * on the configuration.  This was useful for testing
   * purposes
   *
   * @return int count of web services
   */

  public int count() {
    return ( this.list.size() );
  } // public int count()

  public static void main( String[] args )
    throws ConfigurationException {

    NavaDoc documenter = new NavaDoc();

    logger.log( Priority.INFO, "finished" );

  }

  // ----------------------------------------------------------- private methods

  private void walkTree( final File dir, final FileFilter filter )
   throws ConfigurationException {

    this.logger.log( Priority.DEBUG, "scripts directory '" +
      dir.getAbsolutePath() + "' found." );
    String d = "";
    final String prev = this.subDirs.isEmpty() ? "" :
      (String) this.subDirs.peek();

    if ( ! dir.equals( this.servicesPath ) ) {
      d = dir.getName();
      d = ( prev.length() == 0 ? "" : prev + File.separator ) + dir.getName();
    }


    this.subDirs.push( d );
    this.checkTarget( d );

    final File[] contents = dir.listFiles( filter );
    for ( int i = 0; i < contents.length; i++ ) {
      if ( contents[i].isDirectory() ) {
        this.walkTree( contents[i], filter );
      }
    }

    // document this directory
    this.setTransformer( d );
    this.document( d );

    // output the index page
    NavaDocOutputter idxOut =
      new NavaDocOutputter( this.index, new File( this.targetPath, d ) );

    this.subDirs.pop();

  } // private void walkTree()

  private void setTransformer( final String dir )
    throws ConfigurationException {

    final File sPath = new File( this.servicesPath, dir );

    try {
      this.transformer = new NavaDocTransformer(
          this.styleSheetPath, sPath, this.indent);
      this.list = new ServicesList( sPath );

      // set optional parameters, nulls OK
      this.transformer.setProjectName( this.pname );
      this.transformer.setCssUri( this.cssUri );

      // set-up an index DOM
      this.index = new NavaDocIndexDOM( this.pname, this.cssUri);
    }
    catch (ConfigurationException ce) {
      // set configuration URI to inform user and throw upwards
      ce.setConfigUri(this.config.getConfigUri());
      throw (ce);
    }
    catch (TransformerConfigurationException tce) {
      ConfigurationException ce =
          new ConfigurationException(tce.toString(),
                                     this.config.getConfigUri());
      throw (ce);
    }
    catch (ParserConfigurationException pce) {
      ConfigurationException ce =
          new ConfigurationException(pce.toString(),
                                     this.config.getConfigUri());
      throw (ce);
    }

  }

  /**
   * does all the work of going through the list of web
   * services and using the transformer to generate the
   * documentation.  There will be a single document combining
   * BPFL and BPCL documentation for a logical web service.
   * A link in the index page is created after each service
   * has an HTML page generated.
   */

  private void document( final String dir )
   throws ConfigurationException {

    final File tPath = new File( this.targetPath, dir );
    final Iterator iter = this.list.iterator();

    while ( iter.hasNext() ) {

      final String sname = (String) iter.next();

      this.transformer.transformWebService( sname, dir );
      NavaDocOutputter outputter =
        new NavaDocOutputter( this.transformer, tPath );

      this.index.addEntry( sname, this.transformer.getNotes() );
    }

  } // private void document()

  // checks documentation target directory and creates it if possible
  private void checkTarget( final String dir )
    throws ConfigurationException {

    final File path = new File( this.targetPath, dir );

    if ( ! path.exists() ) {
      final boolean result = path.mkdir();
      if ( ! result ) {
        throw ( new ConfigurationException(
          "unable to create documentation target directory '" +
          path, this.config.getConfigUri() ) );
      } else {
        this.logger.log( Priority.DEBUG, "needed to create directory " + path );
      }
    }

  }

} // public class NavaDoc

// EOF: $RCSfile$ //
