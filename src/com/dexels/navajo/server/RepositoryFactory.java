package com.dexels.navajo.server;

/**
 * Title:        Navajo Product Project
 * Description:  This is the official source for the Navajo server
 * Copyright:    Copyright (c) 2002
 * Company:      Dexels BV
 * @author Arjen Schoneveld
 * @version 1.0
 */
import java.util.ResourceBundle;
import com.dexels.navajo.loader.NavajoClassLoader;

public class RepositoryFactory {

  public static Repository getRepository(ResourceBundle b) {
    try {
      String repositoryClass = b.getString("repository_class");
      Repository rp = (Repository) Class.forName(repositoryClass).newInstance();
      rp.setResourceBundle(b);
      System.out.println("Using alternative repository: " + repositoryClass);
      return rp;
    } catch (Exception e) {
      //e.printStackTrace();
      System.out.println("Using default repository: SQLRepository");
      // Use default Repository.
      Repository rp = new SQLRepository();
      rp.setResourceBundle(b);
      return rp;
    }
  }

  public static Repository getRepository(ResourceBundle b, NavajoClassLoader loader, String repositoryClass)  {
    try {
      Class c = loader.getClass(repositoryClass);
      Repository rp = (Repository) c.newInstance();
      rp.setResourceBundle(b);
      return rp;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }
}