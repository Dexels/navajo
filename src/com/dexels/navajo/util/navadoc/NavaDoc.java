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

// XML Document & Parssers
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Element;

// logging
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.xml.DOMConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;


public class NavaDoc {

  public static final Logger logger =
    Logger.getLogger( NavaDoc.class.getName() );

  private Configurator config = null;
  private String[] args = null;

  public static void main( String[] args )
    throws Exception {
    NavaDoc documenter = new NavaDoc();

    documenter.setArguments( args );
    documenter.configure();
    logger.log( Priority.INFO, "finished" );

  }

  public void setArguments( String[] args ) {
    this.args = args;
  }

  public void configure()
    throws Exception {
    config = new Configurator( this.args );
  }

  protected class Configurator {

    private File configPath = new File( "navadoc.xml" );

    public File servicesPath = null;
    public File stylesheetPath = null;

    public Configurator( String[] args )
      throws Exception {

      for ( int i = 0; i < args.length; i++ ) {
        if ( args[i].compareToIgnoreCase( "--config" ) == 0 ) {
          configPath = new File( args[ i + 1 ] );
          i++;
        }
      }

      // get configuration as DOM
      DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
      Document xConf = dBuilder.parse( this.configPath );

      DOMConfigurator.configure(
        (Element) xConf.getElementsByTagName( "log4j:configuration" ).item( 0 ) );

      Logger.getLogger( this.getClass() ).log( Priority.DEBUG, "started logging" );

      // get NavaDoc configuration from DOM
      Element navConf = (Element) xConf.getElementsByTagName( "configuration" ).item( 0 );
      NodeList nList = navConf.getElementsByTagName( "property" );

      for ( int i = 0; i < nList.getLength(); i++ ) {
        Node n = nList.item( i );
        NamedNodeMap nMap = n.getAttributes();
        Node attr = nMap.getNamedItem( "name" );

      }

      if ( this.servicesPath == null ) {
        Exception e = new Exception( "services-path is not configured, " +
            "check the file " + this.configPath );

        throw ( e );
      } else {
        Logger.getLogger( this.getClass() ).log( Priority.DEBUG,
          "services-path = '" + this.servicesPath.toString() + "'" );
      }
      if ( this.stylesheetPath == null ) {
        Exception e = new Exception( "stylesheet-path is not configured, " +
            "check the file " + this.configPath );

        throw ( e );
      } else {
        Logger.getLogger( this.getClass() ).log( Priority.DEBUG,
          "stylesheet-path = '" + this.stylesheetPath.toString() + "'" );
      }

    } // Configurator()

  } // private class configurator

} // public class NavaDoc

// EOF: $RCSfile$ //
