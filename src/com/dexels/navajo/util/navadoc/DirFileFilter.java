package com.dexels.navajo.util.navadoc;

/**
 * <p>Title: NavaDoc</p>
 * <p>Description: Navajo Web Services Automated Documentation Facility
 * defines exluded directoies</p>
 * <p>Copyright: Copyright (c) 2002 - 2003</p>
 * <p>Company: Dexels BV</p>
 * @author Matthew Eichler meichler@dexels.com
 * @version $Id$
 */

import java.io.FileFilter;
import java.io.File;

import java.util.Collection;
import java.util.Vector;
import java.util.Iterator;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

// logging
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;


public class DirFileFilter implements FileFilter {

  public static final String vcIdent = "$Id$";

  public static final Logger logger =
    Logger.getLogger( DirFileFilter.class.getName() );

  private String configUri;
  private String svcPath;
  private Collection exclList = new Vector();

  // -------------------------------------------------------------- constructors

  public DirFileFilter( final String uri, final String path, final Element e )
    throws ConfigurationException {

    this.configUri = uri;
    this.svcPath = path;

    final NodeList list = e.getElementsByTagName( NavaDocConstants.DIR_ELEMENT );
    for ( int i = 0; i < list.getLength(); i++ ) {
      final Element m = (Element) list.item(i);
      if ( m != null ) {
        final String a = m.getAttribute( NavaDocConstants.NAME_ATTR );
        if ( a != null && a.length() > 0 ) {
          exclList.add( a );
          this.logger.log( Priority.DEBUG, "exclusion '" + a + "' found" );
        } else {
          throw ( new ConfigurationException(
            "excluded directory tag element requires a 'name' attribute", this.configUri ) );
        }
      }
    }

    if ( this.exclList.isEmpty() ) {
      throw ( new ConfigurationException(
        "exclusion list doesn't contain any valid directory elements", this.configUri ) );
    }


  }

  // ------------------------------------------------------------ public methods

  /**
   * implements FileFilter accept method
   * @param File pathname to examine
   * @return true if the pathname is accepted
   */

  public boolean accept( File pathname ) {

    // loop through list of exclusions and check
    final Iterator iter = exclList.iterator();
    while ( iter.hasNext() ) {
      final String ex = (String) iter.next();
      if ( pathname.getName().equals( ex )  ) {
        this.logger.log( Priority.DEBUG, "excluding directory '" + ex + "'" );
        return ( false );
      }
    }

    return ( true );

  } // public boolean accept()


}  // public class DirFileFilter implements FileFilter

// EOF: $RCSfile$ //
