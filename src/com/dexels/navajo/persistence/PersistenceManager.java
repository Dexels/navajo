package com.dexels.navajo.persistence;

/**
 * Title:        Navajo Product Project
 * Description:  This is the official source for the Navajo server
 * Copyright:    Copyright (c) 2002
 * Company:      Dexels BV
 * @author Arjen Schoneveld
 * @version $Id$
 *
 * $Log$
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

   public boolean write(Persistable document, String key);

   public Persistable read(String key, long expirationInterval);

   /**
    * Get the persistent object and pass the constructor that is responsible for its creation.
    */
   public Persistable get(Constructor c, String key, long expirationInterval, boolean persist) throws Exception;

}