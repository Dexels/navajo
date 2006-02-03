package com.dexels.navajo.util.navadoc;



import java.io.File;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.dexels.navajo.util.navadoc.NavaDoc;
import com.dexels.navajo.util.navadoc.NavaDocConstants;
import com.dexels.navajo.util.navadoc.config.ConfigurationException;

public class ServicesList extends TreeSet {

  // filename match expression
  public static final String FMATCH = "[.]" + NavaDocConstants.NAVASCRIPT_EXT + "$";

  private File path = null;

  public ServicesList( File p )
    throws ConfigurationException {

    this.path = p;

    File[] fList = this.path.listFiles();

    if ( fList != null ) {
      try {
     
        for ( int i = 0; i < fList.length; i++ ) {
          File f = fList[i];

          if ( f.isFile() ) {
            String n = f.getName();

            // this gets the base name of the web service
            if ( n.matches( ".*" + com.dexels.navajo.util.navadoc.ServicesList.FMATCH ) ) {
              //RE extRE = new RE( com.dexels.navajo.util.navadoc.ServicesList.FMATCH );
              Pattern extRE = Pattern.compile( com.dexels.navajo.util.navadoc.ServicesList.FMATCH );
              Matcher m = extRE.matcher( n );
              m.find();
              String base = n.substring( 0, m.start() );
              this.add( base );
            }
          }
        }
      } catch ( Exception ree ) {
        ConfigurationException e =
          new ConfigurationException( ree.toString() );

        throw ( e );
      }
    }

  } // public ServicesList()

  public static void main(String [] args) {
	  Pattern p = Pattern.compile( com.dexels.navajo.util.navadoc.ServicesList.FMATCH );
	  String aap = "Aap.xml";
	  Matcher m = p.matcher( aap );
	  System.err.println( m.find() );
	  System.err.println( aap.substring(0, m.start() ) );
	  
  }
} // public class ServicesList

// EOF: $RCSfile$ //
