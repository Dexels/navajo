package com.dexels.navajo.persistence;

import com.dexels.navajo.document.Navajo;

/**
 * Title:        Navajo Product Project
 * Description:  This is the official source for the Navajo server
 * Copyright:    Copyright (c) 2002
 * Company:      Dexels BV
 * @author Arjen Schoneveld
 * @version $Id$
 *
 * $Log$
 * Revision 1.5  2008/02/15 16:47:50  arjen
 * Backwards compatible method signature added
 *
 * Revision 1.4  2008/02/08 14:32:51  arjen
 * Added service parameter in get() method interface
 *
 * Revision 1.3  2008/02/06 16:51:40  arjen
 * Minimum PersistenceManagerInterface
 *
 * Revision 1.2  2008/02/06 14:37:00  arjen
 * *** empty log message ***
 *
 * Revision 1.1.1.1  2003/04/16 09:45:43  arjen
 * NavajoDocument contains the interface for the Navajo Document model and 
 * it's implementations: jaxpimpl and nanoimpl
 *
 * Revision 1.4  2003/03/10 16:12:35  arjen
 * Post interfaced document version
 *
 * Revision 1.3  2002/11/06 09:33:47  arjen
 * Used Jacobe code beautifier over all source files.
 * Added log4j support.
 *
 * Revision 1.2  2002/09/25 12:06:00  arjen
 * *** empty log message ***
 *
 * Revision 1.1  2002/09/24 09:27:58  arjen
 * <No Comment Entered>
 *
 *
 */

public interface PersistenceManager {

    public static final int UNIT_SECONDS = 0;
    public static final int UNIT_HOURS = 1;
    public static final int UNIT_DAYS = 2;
    public static final int UNIT_WEEKS = 3;
    public static final int UNIT_MONTHS = 4;

    /**
     * Get the persistent object and pass the constructor that is responsible for its creation.
     */
    public Persistable get(Constructor c, String key, String service, long expirationInterval, boolean persist) throws Exception;
    
    public Persistable get(Constructor c, String key, long expirationInterval, boolean persist) throws Exception;

    public void setConfiguration(Navajo config);

  
}
