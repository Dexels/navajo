package com.dexels.navajo.util.navadoc;


/**
 * <p>Title: NavaDoc</p>
 * <p>Description: Automated documentation utility for Navajo Web Services</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author Matthew Eichler
 * @version $Revision$
 */

import com.dexels.navajo.util.navadoc.NavaDocConfigurator;
import com.dexels.navajo.util.navadoc.ConfigurationException;
import com.dexels.navajo.util.navadoc.ServicesList;
import com.dexels.navajo.util.navadoc.NavaDocTransformer;
import com.dexels.navajo.util.navadoc.NavaDocIndexDOM;
import com.dexels.navajo.util.navadoc.NavaDocOutputter;

import java.io.File;
import java.util.Iterator;

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

  private ServicesList list = null;
  private NavaDocTransformer transformer = null;
  private NavaDocIndexDOM index = null;

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
    String pname = config.getStringProperty( "project-name" );
    String cssUri = config.getStringProperty( "css-uri" );

    try {
      this.transformer = new NavaDocTransformer(
            this.styleSheetPath, this.servicesPath );
      this.list = new ServicesList( this.servicesPath );

      // set optional parameters, nulls OK
      this.transformer.setProjectName( pname );
      this.transformer.setCssUri( cssUri );

      // set-up an index DOM
      this.index = new NavaDocIndexDOM( pname, cssUri );

    } catch ( ConfigurationException ce ) {
      // set configuration URI to inform user and throw upwards
      ce.setConfigUri( this.config.getConfigUri() );
      throw ( ce );
    } catch ( TransformerConfigurationException tce ) {
      ConfigurationException ce =
        new ConfigurationException( tce.toString(),
          this.config.getConfigUri() );

      throw ( ce );
    } catch ( ParserConfigurationException pce ) {
      ConfigurationException ce =
        new ConfigurationException( pce.toString(),
          this.config.getConfigUri() );

      throw ( ce );
    }

    this.document();

    // output the index page
    NavaDocOutputter idxOut =
      new NavaDocOutputter( this.index, this.targetPath );

  }

  /**
   * does all the work of going through the list of web
   * services and using the transformer to generate the
   * documentation.  There will be a single document combining
   * BPFL and BPCL documentation for a logical web service.
   * A link in the index page is created after each service
   * has an HTML page generated.
   */

  public void document() {
    Iterator iter = this.list.iterator();

    while ( iter.hasNext() ) {

      String sname = (String) iter.next();

      this.transformer.transformWebService( sname );
      NavaDocOutputter outputter =
        new NavaDocOutputter( this.transformer, this.targetPath );

      this.index.addEntry( sname, this.transformer.getNotes() );
    }
  } // public void document()

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

} // public class NavaDoc

// EOF: $RCSfile$ //
