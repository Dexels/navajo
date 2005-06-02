package com.dexels.navajo.server;


/**
 * Title:        Navajo Product Project
 * Description:  This is the official source for the Navajo server
 * Copyright:    Copyright (c) 2002
 * Company:      Dexels BV
 * @author Arjen Schoneveld
 * @version $Id$
 */

import com.dexels.navajo.loader.NavajoClassLoader;


public class RepositoryFactory {

    public static Repository getRepository(String className, NavajoConfig config) {
        try {
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

    public static Repository getRepository(NavajoClassLoader loader, String repositoryClass, NavajoConfig config) {
        try {
            Class c = loader.getClass(repositoryClass);
            Repository rp = (Repository) c.newInstance();

            rp.setNavajoConfig(config);
            return rp;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
