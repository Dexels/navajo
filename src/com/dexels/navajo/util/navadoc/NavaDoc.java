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

import java.io.File;

// logging
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

public class NavaDoc {

  public static final Logger logger =
    Logger.getLogger( NavaDoc.class.getName() );


  private NavaDocConfigurator config = new NavaDocConfigurator();
  private File servicesPath = null;
  private ServicesList list = null;


  public NavaDoc()
    throws ConfigurationException {
    config.configure();
    this.servicesPath = config.getPathProperty( "services-path" );
    this.list = new ServicesList( this.servicesPath );

  }

  public static void main( String[] args )
    throws ConfigurationException {

    NavaDoc documenter = new NavaDoc();
    logger.log( Priority.INFO, "finished" );

  }

} // public class NavaDoc

// EOF: $RCSfile$ //
