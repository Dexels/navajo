package com.dexels.navajo.persistence;


/**
 * Title:        Navajo Product Project
 * Description:  This is the official source for the Navajo server
 * Copyright:    Copyright (c) 2002
 * Company:      Dexels BV
 * @author Arjen Schoneveld
 * @version $Id$
 */

public class PersistenceManagerFactory {

    public static PersistenceManager getInstance(String className, String configPath) {
        try {
            PersistenceManager pm = (PersistenceManager) Class.forName(className).newInstance();
            return pm;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
