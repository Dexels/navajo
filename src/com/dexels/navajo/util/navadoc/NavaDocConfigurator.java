package com.dexels.navajo.util.navadoc;


/**
 * <p>Title: NavaDocConfigurator</p>
 * <p>Description: convenience class for configuration of
 * of the NavaDoc documentation automation runner</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author Matthew Eichler
 * @version $Revision$
 */

// typical Java IO stuff
import java.io.File;
import java.io.FileFilter;
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


public class NavaDocConfigurator {

  private String configUri = System.getProperty( "configUri" );
  private File targetPath = null;

  private Document configDOM = null;
  private Element navConf = null;
  private Element loggerConfig = null;
  private NodeList docProps = null;
  private FileFilter ffilter = null;

  public NavaDocConfigurator() {// nothing!
  }

  public void configure()
    throws ConfigurationException {

    // get configuration as DOM
    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();

    try {
      DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

      this.configDOM = dBuilder.parse( this.configUri );
    } catch ( Exception e ) {
      ConfigurationException ce =
        new ConfigurationException( e.toString(), this.configUri );

      throw ( ce );
    }

    this.loggerConfig =
        (Element) this.configDOM.getElementsByTagName( "log4j:configuration" ).item( 0 );

    if ( loggerConfig == null ) {
      ConfigurationException e =
        new ConfigurationException( "logging subsystem log4j is not configured, " +
          "check the file " + this.configUri, this.configUri );

      throw ( e );
    }
    DOMConfigurator.configure( this.loggerConfig );

    Logger.getLogger( this.getClass() ).log( Priority.DEBUG, "started logging" );

    // get NavaDoc configuration from DOM
    this.navConf =
      (Element) this.configDOM.getElementsByTagName(
        NavaDocConstants.CONFIGURATION_ELEMENT ).item( 0 );
    if ( this.navConf == null ) {
      throw new ConfigurationException( "XML does not have a valid '" +
        NavaDocConstants.CONFIGURATION_ELEMENT + "' element'", this.configUri );
    }

    this.docProps = navConf.getElementsByTagName( "property" );

    Logger.getLogger( this.getClass() ).log( Priority.DEBUG,
      NavaDocConstants.SVC_PATH_PROPERTY + " = '" +
      this.getPathProperty( NavaDocConstants.SVC_PATH_PROPERTY ) + "'" );
    Logger.getLogger( this.getClass() ).log( Priority.DEBUG,
      NavaDocConstants.STYLE_PATH_PROPERTY + " = '" +
      this.getPathProperty( NavaDocConstants.STYLE_PATH_PROPERTY ) + "'" );
    Logger.getLogger( this.getClass() ).log( Priority.DEBUG,
      NavaDocConstants.TARGET_PATH_PROPERTY + " = '" +
      this.getPathProperty( NavaDocConstants.TARGET_PATH_PROPERTY ) + "'" );

    this.setFileFilter();

  } // Configurator()

  // getters
  public String getConfigUri() {
    return ( this.configUri );
  }

  public Document getEntireConfig() {
    return ( this.configDOM );
  }

  public Element getLoggerConfig() {
    return ( this.loggerConfig );
  }

  public NodeList getAllProperties() {
    return ( this.docProps );
  }

  public FileFilter getFileFilter() {
    return ( this.ffilter );
  }

  /**
   * Gets a property by name as a string
   *
   * @param name of string property
   * @return property as string
   */

  public String getStringProperty( String propName ) {
    String empty = null;

    for ( int i = 0; i < this.docProps.getLength(); i++ ) {
      Node n = this.docProps.item( i );
      NamedNodeMap nMap = n.getAttributes();
      Node nameAttr = nMap.getNamedItem( "name" );

      if ( nameAttr != null ) {
        String name = nameAttr.getNodeValue();

        if ( name.equals( propName ) ) {
          Node valAttr = nMap.getNamedItem( "value" );

          if ( valAttr != null ) {
            String p = valAttr.getNodeValue();

            return ( p );
          }
        }
      }
    }
    return ( empty );
  } // public File getStringProperty()

  /**
   * Gets a property by name as a File object
   *
   * @param name of path property
   * @return property as File object
   */

  public File getPathProperty( String propName ) {
    File empty = null;

    for ( int i = 0; i < this.docProps.getLength(); i++ ) {
      Node n = this.docProps.item( i );
      NamedNodeMap nMap = n.getAttributes();
      Node nameAttr = nMap.getNamedItem( "name" );

      if ( nameAttr != null ) {
        String name = nameAttr.getNodeValue();

        if ( name.equals( propName ) ) {
          Node valAttr = nMap.getNamedItem( "value" );

          if ( valAttr != null ) {
            String p = valAttr.getNodeValue();

            return ( new File( p ) );
          }
        }
      }
    }
    return ( empty );
  } // public File getPathProperty()

  // ----------------------------------------------------------- private methods

  /**
   * set-ups the exclusion list if found
   * @throws ConfigurationException
   */

  private void setFileFilter()
    throws ConfigurationException {

    NodeList list =
      this.navConf.getElementsByTagName( NavaDocConstants.EXCLUSION_ELEMENT );
    if ( list == null || list.getLength() == 0 ) {
      return;
    }

    // snag only the first one found
    Element e = (Element) list.item( 0 );
    if ( e == null ) {
      return;
    }

    this.ffilter = new DirFileFilter( this.configUri,
      this.getStringProperty( NavaDocConstants.SVC_PATH_PROPERTY ), e );

  } // private void setExclusions()

} // public class NavaDocConfigurator

// EOF: $RCSfile$ //
