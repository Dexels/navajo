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

// DOM
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

// logging
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
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

  // -------------------------------------------------------------- constructors

  public DocumentSet(final Element e, final String uri) throws
      ConfigurationException {
    this.elem = e;
    this.configUri = uri;

    this.name = this.elem.getAttribute("name");
    if ( (this.name == null) || (this.name.length() == 0)) {
      throw new ConfigurationException("'" + NavaDocConstants.DOCSET_ELEMENT +
                                       "' requires '" +
                                       NavaDocConstants.NAME_ATTR +
                                       "' attribute", this.configUri);
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

    final NodeList pList = this.elem.getElementsByTagName( NavaDocConstants.PATH_ELEMENT );
    if ( ( pList == null ) || ( pList.getLength() != 1 ) ) {
      throw new ConfigurationException( "'" + NavaDocConstants.DOCSET_ELEMENT +
                                       "' requires one and only one '" +
                                       NavaDocConstants.PATH_ELEMENT +
                                       "' element", this.configUri);

    }
    /**
     * TODO: create a Path object and configure here
     */


  }

  // ------------------------------------------------------------ public methods

  /**
   * @return an interesting String representation of this object
   */
  public String toString() {
    return ("<document-set> name = '" + this.name + "', description = '" +
            this.description + "'");

  }

  /**
   * @return name of the document set
   */

  public String getName() {
    return ( this.name );
  }

} // public class DocumentSet
// EOF: $RCSfile$ //