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

import java.io.File;
import java.util.Iterator;

// logging
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

// XML stuff
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.parsers.ParserConfigurationException;


public class NavaDoc {

  public static final Logger logger =
    Logger.getLogger( NavaDoc.class.getName() );


  private NavaDocConfigurator config = new NavaDocConfigurator();

  private String projectName = null;
  private String cssUri = null;

  // paths
  private File styleSheetPath = null;
  private File servicesPath = null;
  private File targetPath = null;

  private ServicesList list = null;
  private NavaDocTransformer transformer = null;


  public NavaDoc()
    throws ConfigurationException {
    config.configure();
    this.projectName = config.getStringProperty( "project-name" );
    this.styleSheetPath = config.getPathProperty( "stylesheet-path" );
    this.servicesPath = config.getPathProperty( "services-path" );
    this.targetPath = config.getPathProperty( "target-path" );
    this.cssUri = config.getStringProperty( "css-uri" );

    try {
      this.transformer = new NavaDocTransformer(
        this.styleSheetPath, this.servicesPath, this.targetPath );
      this.list = new ServicesList( this.servicesPath );
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

  }

  /**
   * does all the work of going through the list of web
   * services and using the transformer to generate the
   * documentation.  There will be a single document combining
   * BPFL and BPCL documentation for a logical web service
   */

  public void document() {
    Iterator iter = this.list.iterator();
    while ( iter.hasNext() ) {
      this.transformer.transformWebService(
        this.projectName, (String) iter.next(), this.cssUri );
    }
  }

  public static void main( String[] args )
    throws ConfigurationException {

    NavaDoc documenter = new NavaDoc();
    logger.log( Priority.INFO, "finished" );

  }

} // public class NavaDoc

// EOF: $RCSfile$ //
