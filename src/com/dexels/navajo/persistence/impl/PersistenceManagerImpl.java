package com.dexels.navajo.persistence.impl;

import com.dexels.navajo.persistence.PersistenceManager;
import com.dexels.navajo.persistence.*;
import com.dexels.navajo.xml.XMLDocumentUtils;
import com.dexels.navajo.xml.XMLutils;
import com.dexels.navajo.document.*;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.w3c.dom.*;

/**
 * Title:        Navajo Product Project
 * Description:  This is the official source for the Navajo server
 * Copyright:    Copyright (c) 2002
 * Company:      Dexels BV
 * @author Arjen Schoneveld
 * @version $Id$
 */

import java.io.*;
import java.util.*;

class Configuration {
  protected String persistencePath = "/tmp";
  protected String persistencePrefix = "persistent";
  protected String persistencePostfix = ".xml";
}

public class PersistenceManagerImpl implements PersistenceManager {

  public static Configuration configuration = null;

  private static String configPath = "";

  public PersistenceManagerImpl() {
  }

  public void setParameter(String key, String value) {
    if (key.equals("configPath")) {
      configPath = value;
      try {
        configuration = readConfiguration(configPath + (configPath.endsWith("/") ? "" : "/") + "persistence-manager.xml");
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  private Configuration readConfiguration(String fileName) throws FileNotFoundException, com.dexels.navajo.document.NavajoException {
      Document d = null;
      FileInputStream input = new FileInputStream(new File(fileName));
      d = XMLDocumentUtils.createDocument(input, false);
      d.getDocumentElement().normalize();
      Navajo config = new Navajo(d);
      Configuration c = new Configuration();
      c.persistencePath = config.getProperty("/persistence-manager/path").getValue();
      c.persistencePrefix = config.getProperty("/persistence-manager/prefix").getValue();
      return c;
  }

  public Persistable get(Constructor c, String key, long expirationInterval, boolean persist) throws Exception {

    if (configuration == null)
      return c.construct();

    System.out.println("Trying to retrieve file with key: " + key + " from cache");

    Persistable result = read(key, expirationInterval);
    if (result == null) {
      System.out.println("No persistent instance present...constructing");
      result = c.construct();
      if (persist)
        write(result, key);
      else
        System.out.println("Do not persist");
    } else {
      System.out.println("Found persistent instance");
    }
    return result;
  }

  private String genFileName(String key) {
    return configuration.persistencePath + "/" + configuration.persistencePrefix + key + configuration.persistencePostfix;
  }

  public boolean write(Persistable document, String key) {

    try {
      FileWriter fo = new FileWriter(genFileName(key));
      fo.write(document.toString());
      fo.close();
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  private boolean isExpired(long stamp, long interval) {
    long now = System.currentTimeMillis();
    System.out.println("now      = " + now);
    System.out.println("stamp    = " + stamp);
    System.out.println("interval = " + interval);
    if ((stamp + interval) <= now)
      return true;
    else
      return false;
  }

  public Persistable read(String key, long expirationInterval) {
    Navajo pc = null;
    try {
      File f = new File(genFileName(key));
      if (f.exists()) {
        if (isExpired(f.lastModified(), expirationInterval)) {
          System.out.println("File in cache is expired");
          f.delete();
          return null;
        }


        FileInputStream input = new FileInputStream(f);
        Document d = XMLDocumentUtils.createDocument(input, false);
        d.getDocumentElement().normalize();
        pc = new Navajo(d);

      } else {
        System.out.println("File not found");
        return null;
      }
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
    return pc;
  }

  public static void main(String args[]) throws Exception {
     PersistenceManager pm = new PersistenceManagerImpl();
     //"e:/projecten/NavajoDemo/demo/auxilary/config/persistence-manager.xml"
     pm.setParameter("configPath", "/home/arjen/projecten/NavajoDemo/demo/auxilary/config");
     ConstructorClass cc = new ConstructorClass("<tml><message name=\"family\"><property name=\"freddy\"/><property name=\"jip\"/><property name=\"daan\"/></message></tml>");
     Navajo pc = (Navajo) pm.get(cc, "mykey2", Long.MAX_VALUE/2, true);
     System.out.println(pc.toString());
     Navajo pc2 = (Navajo) pm.get(cc, "mykey2", Long.MAX_VALUE/2, true);
     System.out.println(pc2.toString());
  }
}