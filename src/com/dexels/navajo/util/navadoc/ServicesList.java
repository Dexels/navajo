package com.dexels.navajo.util.navadoc;


import gnu.regexp.RE;
import gnu.regexp.REException;
import gnu.regexp.REMatch;

import java.io.File;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

import com.dexels.navajo.util.navadoc.NavaDoc;
import com.dexels.navajo.util.navadoc.NavaDocConstants;
import com.dexels.navajo.util.navadoc.config.ConfigurationException;

public class ServicesList extends TreeSet {


  public static final Logger logger =
    Logger.getLogger( NavaDoc.class.getName() );

  // filename match expression
  public static final String FMATCH = "[.]" + NavaDocConstants.NAVASCRIPT_EXT + "$";

  private File path = null;

  public ServicesList( File p )
    throws ConfigurationException {

    this.path = p;

    File[] fList = this.path.listFiles();

    if ( fList != null ) {
      try {
        RE xslRE = new RE( ".*" + com.dexels.navajo.util.navadoc.ServicesList.FMATCH );

        for ( int i = 0; i < fList.length; i++ ) {
          File f = fList[i];

          if ( f.isFile() ) {
            String n = f.getName();

            // this gets the base name of the web service
            if ( xslRE.isMatch( n ) ) {
              RE extRE = new RE( com.dexels.navajo.util.navadoc.ServicesList.FMATCH );
              REMatch match = extRE.getMatch( n );
              String base = n.substring( 0, match.getStartIndex() );

              logger.log( Priority.DEBUG, "found service, basename is: '" +
                base + "'" );
              this.add( base );
            }
          }
        }
      } catch ( REException ree ) {
        ConfigurationException e =
          new ConfigurationException( ree.toString() );

        throw ( e );
      }
    }

  } // public ServicesList()

} // public class ServicesList

// EOF: $RCSfile$ //
