package com.dexels.navajo.util.navadoc.config;

/**
 * <p>Title: NavaDoc</p>
 * <p>Description: Navajo Web Services Automated Documentation Facility
 * configuration element which defines aspects of a Navajo script
 * documentation set</p>
 * <p>Copyright: Copyright (c) 2002 - 2003</p>
 * <p>Company: Dexels BV</p>
 * @author Matthew Eichler meichler@dexels.com
 * @version $Id$
 */

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import com.dexels.navajo.util.navadoc.NavaDocConstants;

public class DocumentSet {
  public static final String vcIdent =
      "$Id$";

  public static final Logger logger =
      Logger.getLogger(DocumentSet.class.getName());

  private String configUri;
  private Element elem;

  // required attributes
  private String name;
  private String description = "";
  private boolean isDefault = false;

  // other properties, usually optional
  private Map propMap = new HashMap();

  private PathConfig pathConfig;

  // -------------------------------------------------------------- constructors

  public DocumentSet(final Element e, final String uri) throws
      ConfigurationException {
    this.elem = e;
    this.configUri = uri;
    this.init();
    this.setPropertyMap();
    this.setPathConfiguration();

  }

  // ------------------------------------------------------------ public methods

  /**
   * @return an interesting String representation of this object
   */
  public String toString() {
    final StringBuffer s = new StringBuffer
        ("<document-set> name = '" + this.name + "', description = '" +
         this.description + "'");
    s.append(" isDefault = " + (this.isDefault ? "true" : "false"));
    if (this.propMap.size() > 0) {
      final Set keys = this.propMap.keySet();
      final Iterator iter = keys.iterator();
      while (iter.hasNext()) {
        final String name = (String) iter.next();
        final String value = (String)this.propMap.get(name);
        s.append(" " + name + " = '" + value + "'");
      }

    }
    return (s.toString());

  }

  /**
   * @return name of the document set
   */

  public String getName() {
    return (this.name);
  }

  /**
   * @return description of the document set
   */
  public String getDescription() {
    return (this.description);
  }

  /**
   * @return true if the default attribute was set positively
   */

  public boolean isDefault() {
    return (this.isDefault);
  }

  /**
   * returns the value of a property given it's name
   * @param String name
   * @return String value
   */
  public String getProperty(String name) {
    return ( (String)this.propMap.get(name));
  }

  /**
   * @return path configuration objection
   */

  public PathConfig getPathConfiguration() {
    return (this.pathConfig);
  }

  // ----------------------------------------------------------- private methods

  /**
   * sets the name and description
   */
  private void init() throws ConfigurationException {
    this.name = this.elem.getAttribute(NavaDocConstants.NAME_ATTR);
    if ( (this.name == null) || (this.name.length() == 0)) {
      throw new ConfigurationException("'" + NavaDocConstants.DOCSET_ELEMENT +
                                       "' requires '" +
                                       NavaDocConstants.NAME_ATTR +
                                       "' attribute", this.configUri);
    }

    final String defAttr = this.elem.getAttribute(NavaDocConstants.DEFAULT_ATTR);
    if ( (defAttr != null) && (defAttr.equals("yes") || defAttr.equals("true"))) {
      this.isDefault = true;
    }
    final NodeList l = this.elem.getElementsByTagName(NavaDocConstants.
        DESC_ELEMENT);
    if ( (l != null) && (l.getLength() > 0)) {
      final Node n = l.item(0);
      if ( (n != null) && (n.getNodeType() == Node.ELEMENT_NODE)) {
        final Element f = (Element) n;
        final NodeList m = f.getChildNodes();
        if ( (m != null) && (m.getLength() > 0)) {
          for (int i = 0; i < m.getLength(); i++) {
            if (m.item(i).getNodeType() == Node.TEXT_NODE) {
              this.description = this.description +
                  ( (Text) m.item(i)).getData();
            }
          }
        }
      }
    }
  } // private void init()

  /**
   * sets-up a map of all the optional, miscellaneous properties
   */

  private void setPropertyMap() throws ConfigurationException {
    final NodeList l = this.elem.getElementsByTagName(NavaDocConstants.
        PROPERTY_ELEMENT);

    for (int i = 0; i < l.getLength(); i++) {
      if (l.item(i).getNodeType() != Node.ELEMENT_NODE) {
        throw new ConfigurationException("malformed '" +
                                         NavaDocConstants.PROPERTY_ELEMENT +
                                         "' element", this.configUri);
      }
      final Element e = (Element) l.item(i);
      final String name = e.getAttribute(NavaDocConstants.NAME_ATTR);
      if ( (name == null) || (name.length() == 0)) {
        throw new ConfigurationException("'" + NavaDocConstants.NAME_ATTR +
                                         "' attribute is required for '"
                                         + NavaDocConstants.PROPERTY_ELEMENT +
                                         "' element '");
      }
      final String value = e.getAttribute(NavaDocConstants.VALUE_ATTR);
      if ( (value == null) || (value.length() == 0)) {
        throw new ConfigurationException("'" + NavaDocConstants.VALUE_ATTR +
                                         "' attribute is required for '"
                                         + NavaDocConstants.PROPERTY_ELEMENT +
                                         "' element '");
      }
      this.propMap.put(name, value);
    }
  }

  private void setPathConfiguration() throws ConfigurationException {
    final NodeList pList = this.elem.getElementsByTagName(NavaDocConstants.
        PATH_ELEMENT);
    if ( (pList == null) || (pList.getLength() != 1)) {
      throw new ConfigurationException("'" + NavaDocConstants.DOCSET_ELEMENT +
                                       "' requires one and only one '" +
                                       NavaDocConstants.PATH_ELEMENT +
                                       "' element", this.configUri);

    }
    if (pList.item(0).getNodeType() != Node.ELEMENT_NODE) {
      throw new ConfigurationException("malformed '" +
                                       NavaDocConstants.PATH_ELEMENT +
                                       "' element",
                                       this.configUri);
    }
    this.pathConfig = new PathConfig( (Element) pList.item(0), this.configUri);

  }
} // public class DocumentSet
// EOF: $RCSfile$ //