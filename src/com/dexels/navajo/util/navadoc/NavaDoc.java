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

import java.io.File;

// logging
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

// regular expressions
import gnu.regexp.RE;
import gnu.regexp.REException;

public class NavaDoc {

  public static final Logger logger =
    Logger.getLogger( NavaDoc.class.getName() );

  private NavaDocConfigurator config = new NavaDocConfigurator();
  private File servicesPath = null;


  public NavaDoc()
    throws ConfigurationException {
    config.configure();
    this.servicesPath = config.getPathProperty( "services-path" );
    File[] fList = this.servicesPath.listFiles();
    try {
      RE tmlRE = new RE( ".*[.]xsl$" );
      for ( int i = 0; i < fList.length; i++ ) {
        File f = fList[i];
        if ( f.isFile() ) {
          String n = f.getName();
          if ( tmlRE.isMatch(n) ) {
            logger.log( Priority.DEBUG, "found services file: '" + n + "'" );
          }
        }
      }
    } catch ( REException ree ) {
      ConfigurationException e =
        new ConfigurationException( ree.toString() );
      throw( e );
    }

  }

  public static void main( String[] args )
    throws ConfigurationException {

    NavaDoc documenter = new NavaDoc();
    logger.log( Priority.INFO, "finished" );

  }

} // public class NavaDoc

// EOF: $RCSfile$ //
