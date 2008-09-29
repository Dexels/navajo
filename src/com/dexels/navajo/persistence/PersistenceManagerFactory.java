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

	static volatile PersistenceManager instance;
	final static Object object = new Object();
	
	public static PersistenceManager getInstance() {
		if ( instance != null ) {
			return instance;
		} else {
			return null;
		}
	}
	
	public static PersistenceManager getInstance(String className, String configPath) {

		if ( instance != null ) {
			return instance;
		}

		synchronized (object) {

			if ( instance == null ) {
				try {
					instance = (PersistenceManager) Class.forName(className).newInstance();

				} catch (Exception e) {
					System.err.println("Could NOT FIND PersistenceManager: " + className + ", trying SimplePersistenceManagerImpl..");
					try {
						instance = (PersistenceManager) Class.forName("com.dexels.navajo.persistence.SimplePersistenceManagerImpl").newInstance();
					} catch (Exception  e1) {
						throw new RuntimeException(e1);
					} 

				}
			}
		}
		return instance;
	}
}
