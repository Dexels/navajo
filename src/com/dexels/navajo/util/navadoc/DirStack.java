package com.dexels.navajo.util.navadoc;

/**
 * <p>Title: NavaDoc</p>
 * <p>Description: Navajo Web Services Automated Documentation Facility
 * helper class for walking the directory tree of web services,
 * also keeps the index documents and referencing straight</p>
 * <p>Copyright: Copyright (c) 2002 - 2003</p>
 * <p>Company: Dexels BV</p>
 * @author Matthew Eichler meichler@dexels.com
 * @version $Id$
 */

import java.util.Stack;
import java.util.HashMap;
import java.util.Map;

// logging
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

class DirStack extends Stack {

  public static final String vcIdent = "$Id$";

  public static final Logger logger =
    Logger.getLogger( DirStack.class.getName() );

  private Map idxdocMap = new HashMap();

  public DirStack() {
    super();
  }

  // ------------------------------------------------------------ public methods

  /**
   * saves an index page for later
   * @param relative sub-directory as String, could be empty string for main
   * directory
   * @param NavaDocIndexDOM to save
   */

  public void putIdxDoc( final String dir, final NavaDocIndexDOM doc ) {

    this.idxdocMap.put( ( dir.length() == 0 ? "." : dir ), doc );
  }

  /**
   * @return the map of NavaDocIndexDOM documents
   */

  public Map getIdxDocMap() {
    return ( this.idxdocMap );
  }

} // public class DirStack extends Stack

// EOF: $RCSfile$ //
