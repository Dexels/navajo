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

import com.dexels.navajo.loader.NavajoClassLoader;


public class RepositoryFactory {

	private final static Map<String,Repository> repositoryRepository = new HashMap<String, Repository>();
	
	// Put SimpleRepository in repositoryRepository.
	static {
		repositoryRepository.put("com.dexels.navajo.server.SimpleRepository", new SimpleRepository());
	}
	
    public static Repository getRepository(String className, NavajoConfigInterface config) {
        try {
        	Repository localRp = repositoryRepository.get(className);
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


    public static void registerRepository(String className, Repository r) {
    	repositoryRepository.put(className, r);
    }
    @SuppressWarnings("unchecked")
    public static Repository getRepository(NavajoClassLoader loader, String repositoryClass, NavajoConfigInterface config) {
    	Repository localRp = repositoryRepository.get(repositoryClass);
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
