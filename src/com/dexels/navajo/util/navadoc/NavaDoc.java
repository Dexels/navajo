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

// logging
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

// XML stuff
import javax.xml.transform.TransformerConfigurationException;


public class NavaDoc {

  public static final Logger logger =
    Logger.getLogger( NavaDoc.class.getName() );


  private NavaDocConfigurator config = new NavaDocConfigurator();

  // paths
  private File styleSheetPath = null;
  private File servicesPath = null;
  private File targetPath = null;

  private ServicesList list = null;
  private NavaDocTransformer transformer = null;


  public NavaDoc()
    throws ConfigurationException {
    config.configure();
    this.styleSheetPath = config.getPathProperty( "stylesheet-path" );
    this.servicesPath = config.getPathProperty( "services-path" );
    this.targetPath = config.getPathProperty( "target-path" );

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
    }

  }

  public static void main( String[] args )
    throws ConfigurationException {

    NavaDoc documenter = new NavaDoc();
    logger.log( Priority.INFO, "finished" );

  }

} // public class NavaDoc

// EOF: $RCSfile$ //
