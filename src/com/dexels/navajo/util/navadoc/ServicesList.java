package com.dexels.navajo.util.navadoc;


import java.util.ArrayList;
import java.util.TreeSet;

/**
 * <p>Title: NavaDoc: ServicesList</p>
 * <p>Description: the List of services to be documented</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels.com</p>
 * @author Matthew Eichler
 * @version $Id$
 */

import java.io.File;

import com.dexels.navajo.util.navadoc.ConfigurationException;

// regular expressions
import gnu.regexp.RE;
import gnu.regexp.REException;
import gnu.regexp.REMatch;

// logging
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

public class ServicesList extends TreeSet {


  public static final Logger logger =
    Logger.getLogger( NavaDoc.class.getName() );

  // filename match expression
  public static final String FMATCH = "[.]xsl$";

  private File path = null;

  public ServicesList( File p )
    throws ConfigurationException {

    this.path = p;

    File[] fList = this.path.listFiles();

    if ( fList != null ) {
      try {
        RE xslRE = new RE( ".*" + this.FMATCH );

        for ( int i = 0; i < fList.length; i++ ) {
          File f = fList[i];

          if ( f.isFile() ) {
            String n = f.getName();

            // this gets the base name of the web service
            if ( xslRE.isMatch( n ) ) {
              RE extRE = new RE( this.FMATCH );
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
