package com.dexels.navajo.persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.NavajoException;


/**
 * Title:        Navajo Product Project
 * Description:  This is the official source for the Navajo server
 * Copyright:    Copyright (c) 2002
 * Company:      Dexels BV
 * @author Arjen Schoneveld
 * @version $Id$
 */

public class PersistenceManagerFactory {

	static PersistenceManager instance;
	static final Object object = new Object();
	
	private static final Logger logger = LoggerFactory
			.getLogger(PersistenceManagerFactory.class);
	
	private PersistenceManagerFactory() {
		// no instance
	}
	public static void clearInstance() {
		instance = null;
	}
	
	public static PersistenceManager getInstance() {
		if ( instance != null ) {
			return instance;
		} else {
			return null;
		}
	}
	
	/**
	 * @param configPath unused, it seems 
	 */
	public static PersistenceManager getInstance(String className, String configPath) {

		if ( instance != null ) {
			return instance;
		}

		synchronized (object) {

			if ( instance == null ) {
				try {
					instance = (PersistenceManager) Class.forName(className).getDeclaredConstructor().newInstance();

				} catch (Exception e) {
					logger.info("Could NOT FIND PersistenceManager: {}, trying SimplePersistenceManagerImpl..",className);
					try {
						instance = (PersistenceManager) Class.forName("com.dexels.navajo.persistence.SimplePersistenceManagerImpl").getDeclaredConstructor().newInstance();
					} catch (Exception  e1) {
						throw new NavajoException(e1);
					} 

				}
			}
		}
		return instance;
	}
}
