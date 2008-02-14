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
        	System.err.println("Could NOT FIND PersistenceManager: " + className + ", trying SimplePersistenceManagerImpl..");
        	PersistenceManager pm;
			try {
				pm = (PersistenceManager) Class.forName("com.dexels.navajo.persistence.SimplePersistenceManagerImpl").newInstance();
			} catch (Exception  e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				return null;
			} 
        	return pm;
        }
    }
}
