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
    protected boolean statistics = false;
}


public class PersistenceManagerImpl implements PersistenceManager {

    public static Configuration configuration = null;

    private static String configPath = "";

    private static long totalhits = 0;
    private static long cachehits = 0;

    private static long totalCacheProcessing = 0;
    private static long totalCacheProcessing2 = 0;
    private static long totalOutOfCacheProcessing = 0;
    private static long totalOutOfCacheProcessing2 = 0;
    private static long fileWrites = 0;

    private static HashMap nocachePerformance = null;
    private static HashMap cachePerformance = null;

    private static File statisticsFile = null;

    public PersistenceManagerImpl() {
        nocachePerformance = new HashMap();
        cachePerformance = new HashMap();
    }

    public void setParameter(String key, String value) {

        // System.out.println("in SetParameter(), key = " + key + ", value = " + value);
        if (key.equals("configPath")) {
            configPath = value;
            try {
                configuration = readConfiguration(configPath + (configPath.endsWith("/") ? "" : "/") + "persistence-manager.xml");
                // System.out.println("Persistence manager configuration: " + configuration);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private synchronized Configuration readConfiguration(String fileName) {
        try {
            // System.out.println("in readConfiguration()");
            Document d = null;
            FileInputStream input = new FileInputStream(new File(fileName));

            d = XMLDocumentUtils.createDocument(input, false);
            d.getDocumentElement().normalize();
            Navajo config = new Navajo(d);
            Configuration c = new Configuration();

            c.persistencePath = config.getProperty("/persistence-manager/path").getValue();
            c.persistencePrefix = config.getProperty("/persistence-manager/prefix").getValue();
            c.statistics = ((config.getProperty("/persistence-manager/statistics")
                    != null)
                    && (config.getProperty("/persistence-manager/statistics").getValue().equals("on")));
            if ((c.statistics)
                    && (config.getProperty("/persistence-manager/statistics")
                    != null)) {
                this.statisticsFile = new File(config.getProperty("/persistence-manager/logfile").getValue());
            }
            return c;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private synchronized void statistics(long start, long end, boolean inCache) {
        long total = (end - start);

        if (inCache) {
            this.totalCacheProcessing += total;
            this.totalCacheProcessing2 += (total * total);
        } else {
            this.totalOutOfCacheProcessing += total;
            this.totalOutOfCacheProcessing2 += (total * total);
        }
        double pt = total / (double) 1000.0; // Processing time.
        double hr = ((cachehits) / (double) totalhits * 100.0); // Hit rate
        double cpt = (this.totalCacheProcessing / (double) cachehits) / 1000.0; // Average in cache processing time.
        double cptVar = ((totalCacheProcessing * totalCacheProcessing)
                / (double) cachehits
                - totalCacheProcessing2 / (double) cachehits)
                / 1000000.0;

        double ocpt = (this.totalOutOfCacheProcessing
                / (double) (totalhits - cachehits))
                / (1000.0); // Average out of cache processing time.
        double ocptVar = ((totalOutOfCacheProcessing * totalOutOfCacheProcessing)
                / (double) (totalhits - cachehits)
                - totalOutOfCacheProcessing2 / (double) (totalhits - cachehits))
                / 1000000.0;

        // System.out.println("Processing time: " + pt + " secs.");
        // System.out.println("Total hits: " + totalhits);
        // System.out.println("Cache hits: " + cachehits);
        // System.out.println("Cache hit rate: " +  hr + "%");
        // System.out.println("Total file writes: " + this.fileWrites);
        if (cachehits > 0) {// System.out.println("Average in cache processing time: " + cpt + " secs. (" + cptVar + ")" );
        }
        // System.out.println("Average out of cache processing time: " + ocpt + " secs. (" + ocptVar + ")" );

        try {
            FileWriter w = new FileWriter(this.statisticsFile);

            w.write("total=" + totalhits + ",hr=" + hr + ",cpt=" + cpt + ",ocpt=" + ocpt + ",cpt2=" + cptVar + ",ocpt2=" + ocptVar);
            w.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Persistable get(Constructor c, String key, long expirationInterval, boolean persist) throws Exception {

        boolean inCache = false;
        long start = 0;

        if (configuration == null)
            return c.construct();

        if (configuration.statistics) {
            start = System.currentTimeMillis();
        }

        totalhits++;

        // System.out.println("Trying to retrieve file with key: " + key + " from cache");

        Persistable result = (persist) ? read(key, expirationInterval) : null;

        if (result == null) {
            // System.out.println("No persistent instance present...constructing");
            result = c.construct();
            if (persist)
                write(result, key);

        } else {
            cachehits++;
            inCache = true;
            // System.out.println("Found persistent instance");
        }

        if (configuration.statistics) {
            long end = System.currentTimeMillis();

            statistics(start, end, inCache);
        }

        return result;
    }

    private String genFileName(String key) {
        return configuration.persistencePath + "/"
                + configuration.persistencePrefix + key
                + configuration.persistencePostfix;
    }

    /**
     * Note that write() is a critical section since multiple requests using the same key can be expected!
     */
    public synchronized boolean write(Persistable document, String key) {

        try {
            FileWriter fo = new FileWriter(genFileName(key));

            fo.write(document.toString());
            fo.close();
            fileWrites++;
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean isExpired(long stamp, long interval) {
        long now = System.currentTimeMillis();

        // System.out.println("now      = " + now);
        // System.out.println("stamp    = " + stamp);
        // System.out.println("interval = " + interval);
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
                    // System.out.println("File in cache is expired");
                    f.delete();
                    return null;
                }

                FileInputStream input = new FileInputStream(f);
                Document d = XMLDocumentUtils.createDocument(input, false);

                d.getDocumentElement().normalize();
                pc = new Navajo(d);

            } else {
                // System.out.println("File not found");
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

        // "e:/projecten/NavajoDemo/demo/auxilary/config/persistence-manager.xml"
        pm.setParameter("configPath", "/home/arjen/projecten/NavajoDemo/demo/auxilary/config");
        ConstructorClass cc = new ConstructorClass("<tml><message name=\"family\"><property name=\"freddy\"/><property name=\"jip\"/><property name=\"daan\"/></message></tml>");
        Navajo pc = (Navajo) pm.get(cc, "mykey2", Long.MAX_VALUE / 2, true);
        // System.out.println(pc.toString());
        Navajo pc2 = (Navajo) pm.get(cc, "mykey2", Long.MAX_VALUE / 2, true);
        // System.out.println(pc2.toString());
    }
}
