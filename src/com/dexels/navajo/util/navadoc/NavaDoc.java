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
import java.util.Properties;

// logging
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.LogConfigurationException;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

public class NavaDoc {

  private Configurator config = null;
  private String[] args = null;
  private Logger logger = null;


  public static void main( String[] args )
    throws Exception {
    NavaDoc documenter = new NavaDoc();
    documenter.setArguments( args );
    documenter.configure();
    documenter.getLogger().log( Priority.INFO, "finished" );

  }

  public void setArguments( String[] args ) { this.args = args; }
  public void configure()
    throws Exception {

     config = new Configurator( this.args, this.getClass() );
     this.logger = config.getLogger();

  }
  public Logger getLogger() { return( this.logger ); }

  private class Configurator {

     private File configPath =
       new File( "." + File.pathSeparator + "navadoc-config.xml" );
     private Logger logger = null;

     public Configurator( String[] args, Class parent )
       throws Exception {

       for ( int i = 1; i < args.length; i++ ) {
         if ( args[i].compareToIgnoreCase( "--config" ) == 0 ) {
           configPath = new File( args[ i + 1 ] );
           i++;
         }
       }

       // @todo: configure logger from central XML file

      Properties lProps = new Properties();
      lProps.setProperty( "log4j.rootLogger", "debug, stdout, R" );
      lProps.setProperty( "log4j.appender.stdout", "org.apache.log4j.ConsoleAppender" );
      lProps.setProperty( "log4j.appender.stdout.layout", "org.apache.log4j.PatternLayout" );
      lProps.setProperty( "log4j.appender.stdout.layout.ConversionPattern", "%d %5p [%t] (%F:%L) - %m%n" );
      lProps.setProperty( "log4j.appender.R", "org.apache.log4j.RollingFileAppender" );
      lProps.setProperty( "log4j.appender.R.File", "D:/Projecten/NavaDoc/log/navadoc.log" );
      lProps.setProperty( "log4j.appender.R.MaxFileSize", "100KB" );
      lProps.setProperty( "log4j.appender.R.MaxBackupIndex", "1" );
      lProps.setProperty( "log4j.appender.R.layout", "org.apache.log4j.PatternLayout" );
      lProps.setProperty( "log4j.appender.R.layout.ConversionPattern", "%d %5p [%t] (%F:%L) - %m%n" );
      PropertyConfigurator.configure( lProps );

      this.logger = Logger.getLogger( parent.toString() );
      logger.log( Priority.DEBUG, "started logging" );

     } // Configurator()

     public Logger getLogger() { return ( this.logger ); }

  } // private class configurator

} // public class NavaDoc


// EOF: $RCSfile$ //
