package com.dexels.navajo.server;


/**
 * Title:        Navajo Product Project
 * Description:  This is the official source for the Navajo server
 * Copyright:    Copyright (c) 2002
 * Company:      Dexels BV
 * @author Arjen Schoneveld
 * @version $Id$
 */

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.loader.NavajoClassLoader;


public class RepositoryFactoryImpl implements RepositoryFactory {

	private final static Map<String,Repository> legacyRepositoryRepository = new HashMap<String, Repository>();
	
	private final Map<String,Repository> repositoryRepository = new HashMap<String, Repository>();

	private static RepositoryFactory instance;

	private static final Logger logger = LoggerFactory.getLogger(RepositoryFactory.class);
	
	// Put SimpleRepository in repositoryRepository.
	static {
		legacyRepositoryRepository.put("com.dexels.navajo.server.SimpleRepository", new SimpleRepository());
	}
	
	/* (non-Javadoc)
	 * @see com.dexels.navajo.server.RepositoryFactory#addRepository(com.dexels.navajo.server.Repository)
	 */
	@Override
	public void addRepository(Repository r) {
		logger.info("Adding repository: {}",r.getClass().getName());
		repositoryRepository.put(r.getClass().getName(),r);
	}
	/* (non-Javadoc)
	 * @see com.dexels.navajo.server.RepositoryFactory#removeRepository(com.dexels.navajo.server.Repository)
	 */
	@Override
	public void removeRepository(Repository r) {
		logger.info("Removing repository: {}",r.getClass().getName());
		repositoryRepository.remove(r.getClass().getName());
	}

	public void activate() {
		instance = this;
	}
	
	public void deactivate() {
		instance = null;
	}
	
	public static RepositoryFactory getInstance() {
		return instance;
	}

	
    public static Repository getRepository(String className, NavajoConfigInterface config) {
        try {
        	Repository localRp = legacyRepositoryRepository.get(className);
        	if(localRp!=null) {
        		localRp.setNavajoConfig(config);
        		return localRp;
        	}
        	Repository rp = (Repository) config.getClassloader().getClass(className).newInstance();
            rp.setNavajoConfig(config);
            System.out.println("Using alternative repository: " + className);
            return rp;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Using default repository: SimpleRepository");
            // Use default Repository.
            Repository rp = new SimpleRepository();

            rp.setNavajoConfig(config);
            return rp;
        }
    }

    /* (non-Javadoc)
	 * @see com.dexels.navajo.server.RepositoryFactory#getRepository(java.lang.String)
	 */
    @Override
	public Repository getRepository(String repository) {
    	return repositoryRepository.get(repository);
    }

//    public static void registerRepository(String className, Repository r) {
//    	repositoryRepository.put(className, r);
//    }
    
    @SuppressWarnings("unchecked")
    public static Repository getRepository(NavajoClassLoader loader, String repositoryClass, NavajoConfigInterface config) {
    	Repository localRp = legacyRepositoryRepository.get(repositoryClass);
    	if(localRp!=null) {
    		localRp.setNavajoConfig(config);
    		return localRp;
    	}

    	try {
			Class<? extends Repository> c = (Class<? extends Repository>)loader.getClass(repositoryClass);
            Repository rp = (Repository) c.newInstance();

            rp.setNavajoConfig(config);
            return rp;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
