package com.dexels.navajo.util.navadoc.config;

/**
 * <p>Title: NavaDocConfigurator</p>
 * <p>Description: convenience class for configuration of
 * of the NavaDoc documentation automation runner</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author Matthew Eichler
 * @version $Revision$
 */

import com.dexels.navajo.util.navadoc.*;

// typical Java IO stuff
import java.io.File;
import java.io.FileFilter;
import java.util.Properties;
import java.util.Map;
import java.util.HashMap;

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
import com.dexels.navajo.util.navadoc.NavaDocConstants;
import java.util.Set;
import java.util.Iterator;

public class NavaDocConfigurator {

  public static final String vcIdent =
      "$Id$";

  public static final Logger logger =
      Logger.getLogger(NavaDocConfigurator.class.getName());

  private String configUri = System.getProperty("configUri");
  private File targetPath = null;

  private Document configDOM = null;
  private Element navConf = null;
  private Element loggerConfig = null;
  private NodeList docProps = null;
  private FileFilter ffilter = null;

  private Map setMap = new HashMap();

  public NavaDocConfigurator() { // nothing!
  }

  public void configure() throws ConfigurationException {

    // get configuration as DOM
    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();

    try {
      DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

      this.configDOM = dBuilder.parse(this.configUri);
    }
    catch (Exception e) {
      ConfigurationException ce =
          new ConfigurationException(e.toString(), this.configUri);

      throw (ce);
    }

    this.configureLogging();

    // get NavaDoc configuration from DOM
    this.navConf =
        (Element)this.configDOM.getElementsByTagName(
        NavaDocConstants.CONFIGURATION_ELEMENT).item(0);
    if (this.navConf == null) {
      throw new ConfigurationException("configuration does not have a valid '" +
                                       NavaDocConstants.CONFIGURATION_ELEMENT +
                                       "' element'", this.configUri);
    }

    this.docProps = navConf.getElementsByTagName("property");

    final NodeList sList = this.navConf.getElementsByTagName( NavaDocConstants.DOCSET_ELEMENT );
    if ( ( sList == null ) || ( sList.getLength() == 0 ) ) {
      throw new ConfigurationException("must define at least one '" +
                                       NavaDocConstants.DOCSET_ELEMENT +
                                       "' element'", this.configUri);
    }

    for ( int i = 0; i < sList.getLength(); i++ ) {
      if ( sList.item(i).getNodeType() != Node.ELEMENT_NODE ) {
        throw new ConfigurationException("malformed '" +
                                       NavaDocConstants.DOCSET_ELEMENT +
                                       "' element'", this.configUri);

      }
      final DocumentSet set = new DocumentSet( (Element) sList.item(i), this.configUri );
      this.setMap.put( set.getName(), set );
      this.logger.log( Priority.DEBUG, "configured: " + set );
    }




  } // Configurator()

  // getters
  public String getConfigUri() {
    return (this.configUri);
  }

  public Document getEntireConfig() {
    return (this.configDOM);
  }

  public Element getLoggerConfig() {
    return (this.loggerConfig);
  }

  public NodeList getAllProperties() {
    return (this.docProps);
  }

  public FileFilter getFileFilter() {
    return (this.ffilter);
  }

  /**
   * Gets a property by name as a string
   *
   * @param name of string property
   * @return property as string
   */

  public String getStringProperty(String propName) {
    String empty = null;

    for (int i = 0; i < this.docProps.getLength(); i++) {
      Node n = this.docProps.item(i);
      NamedNodeMap nMap = n.getAttributes();
      Node nameAttr = nMap.getNamedItem("name");

      if (nameAttr != null) {
        String name = nameAttr.getNodeValue();

        if (name.equals(propName)) {
          Node valAttr = nMap.getNamedItem("value");

          if (valAttr != null) {
            String p = valAttr.getNodeValue();

            return (p);
          }
        }
      }
    }
    return (empty);
  } // public File getStringProperty()

  /**
   * Gets a property by name as a File object
   *
   * @param name of path property
   * @return property as File object
   */

  public File getPathProperty(String propName) {
    File empty = null;

    for (int i = 0; i < this.docProps.getLength(); i++) {
      Node n = this.docProps.item(i);
      NamedNodeMap nMap = n.getAttributes();
      Node nameAttr = nMap.getNamedItem("name");

      if (nameAttr != null) {
        String name = nameAttr.getNodeValue();

        if (name.equals(propName)) {
          Node valAttr = nMap.getNamedItem("value");

          if (valAttr != null) {
            String p = valAttr.getNodeValue();

            return (new File(p));
          }
        }
      }
    }
    return (empty);
  } // public File getPathProperty()

  // ----------------------------------------------------------- private methods

   /**
   * configure the logging sub-system if one has been provided
   * http://jakarta.apache.org/log4j/
   */

  private void configureLogging() {

    Node n;
    NodeList logList = this.configDOM.getElementsByTagName(
        NavaDocConstants.LOG4JCONFIG_ELEMENT);

    // this funny bit of logic allows local declaration of the LOG4J
    // namespace in the document and expects that NavaTest configuration will
    // always be first and LOG4J will always be 2nd in the document

    if ( (logList != null) && (logList.getLength() == 1)) {
      n = logList.item(0);
      if (n.getNodeType() == Node.ELEMENT_NODE) {
        this.loggerConfig = (Element) n;
        DOMConfigurator.configure(this.loggerConfig);
        this.logger.log(Priority.DEBUG, "reconfigured logging");
        return;
      }
    }
    else {
      logList = this.configDOM.getElementsByTagName(NavaDocConstants.CONFIGURATION_ELEMENT);
      if ( (logList != null) && (logList.getLength() == 2)) {
        n = logList.item(1);
        final Element clone = this.configDOM.createElement(NavaDocConstants.LOG4JCONFIG_ELEMENT);
        this.configDOM.getDocumentElement().appendChild(clone);
        final NodeList children = n.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
          final Node m = (children.item(i)).cloneNode(true);
          clone.appendChild(m);
        }
        this.loggerConfig = clone;
        DOMConfigurator.configure(this.loggerConfig);
        this.logger.log(Priority.DEBUG, "reconfigured logging");
        return;
      }
      else {
        this.logger.log( Priority.DEBUG, "no valid logging configuration found" );
        return;
      }
    }
  }

} // public class NavaDocConfigurator
// EOF: $RCSfile$ //