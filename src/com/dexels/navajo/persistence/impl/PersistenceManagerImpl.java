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

import javax.xml.transform.stream.StreamResult;

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
    protected int maxInMemoryCacheSize = 0;
}

class Frequency {

  public Frequency(String name) {
    this.name = name;
    this.frequency = 0;
    this.creationDate = System.currentTimeMillis();
    this.lastAccess = this.creationDate;
  }

  public boolean isExpired(long interval) {
    return ((creationDate + interval) < System.currentTimeMillis());
  }

  public void setCreation() {
    this.creationDate = System.currentTimeMillis();
    this.lastAccess = this.creationDate;
  }

  public void access(int size, long processingTime) {
    frequency++;
    lastAccess = System.currentTimeMillis();
    double rate = ((double) size / 1024.0 ) / ((double) processingTime / 1000.0 );
    totalThroughPut += rate;
    //System.out.println("Throughput = " + totalThroughPut);
  }

  public double getThroughPut() {
    return totalThroughPut / frequency;
  }

  public long getCreation() {
    return this.creationDate;
  }

  public long getLastAccess() {
    return this.lastAccess;
  }

  public String getName() {
    return this.name;
  }

  public int getTimesAccessed() {
    return this.frequency;
  }

  private long creationDate;
  private long lastAccess;
  private String name;
  private int frequency = 0;
  private double totalThroughPut = 0.0;
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

    private static String statisticsFile = null;

    private static HashMap inMemoryCache = null;
    private static int inMemoryCacheSize = 0;

    private static HashMap accessFrequency = null;

    public PersistenceManagerImpl() {
        nocachePerformance = new HashMap();
        cachePerformance = new HashMap();
        inMemoryCache = new HashMap();
        accessFrequency = new HashMap();
    }

    /**
     * Implement a least frequently used strategy.
     *
     * @return
     */
    private String findLFU() {
        int l = Integer.MAX_VALUE;
        String lUsed = "";
        Iterator all = accessFrequency.keySet().iterator();
        while (all.hasNext()) {
            Frequency f = (Frequency) accessFrequency.get(all.next().toString());
            if ((f.getTimesAccessed() < l) && (inMemoryCache.get(f.getName()) != null)) {
              l = f.getTimesAccessed();
              lUsed = f.getName();
            }
        }
        return lUsed;
    }

    private synchronized void addAccess(String key, long processingTime, int size) {

        //System.out.println("PT = " + processingTime + ", size = " + size);
        Frequency i = (Frequency) accessFrequency.get(key);
        if (i == null) {
          i = new Frequency(key);
          accessFrequency.put(key, i);

        }
        i.access(size, processingTime);
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
                this.statisticsFile = config.getProperty("/persistence-manager/logfile").getValue();
            }
            c.maxInMemoryCacheSize = Integer.parseInt(config.getProperty("/persistence-manager/memory_limit").getValue());
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
            FileWriter w = new FileWriter(this.statisticsFile, false);
            w.write("Bulk statistics:\n\n");
            w.write("total=" + totalhits + "\nhitRate=" + hr + "\navgInCacheProcessingTime=" + cpt +
                    "\navgOutOfCacheProcessingTime=" + ocpt + "\nstdDevInCacheProcessingTime=" + cptVar +
                    "\nstdDevOutOfCacheProcessingTime=" + ocptVar + "\n\n\n");
            w.write("Detailed statistics:\n\n");
            Iterator iter = this.accessFrequency.keySet().iterator();
            w.write("WSKey \t\t\t lastAccess \t creationTime \t frequency \t avgThroughput \n");
            while (iter.hasNext()) {
              String key = (String) iter.next();
              Frequency freq = (Frequency) this.accessFrequency.get(key);
              w.write(freq.getName()+"\t\t"+new java.util.Date(freq.getLastAccess())+
                      "\t" +
                      new java.util.Date(freq.getCreation())+"\t" + freq.getTimesAccessed()+
                      "\t" + freq.getThroughPut() +" Kb/s \n");
            }
            w.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Persistable get(Constructor c, String key, long expirationInterval, boolean persist) throws Exception {

        boolean inCache = false;
        long start = System.currentTimeMillis();
        //System.out.println("start = " + start);

        if (configuration == null)
            return c.construct();

        totalhits++;

        Persistable result = (persist) ? read(key, expirationInterval) : null;

        if (result == null) {
            result = c.construct();
            if (persist)
                write(result, key);

        } else {
            cachehits++;
            inCache = true;
        }

        long end = System.currentTimeMillis();
        //System.out.println("end = " + end);

        if (configuration.statistics) {
            statistics(start, end, inCache);
        }

        addAccess(key, (end - start), result.toString().length());

        return result;
    }

    private String genFileName(String key) {
        return configuration.persistencePath + "/"
                + configuration.persistencePrefix + key
                + configuration.persistencePostfix;
    }

    private synchronized Persistable memoryOperation(String key, Persistable document,
                                                     long expirationInterval, boolean read) {

        if (read) {
            Navajo pc = null;
            Frequency freq = (Frequency) accessFrequency.get(key);
            if (freq != null && !freq.isExpired(expirationInterval)) {
              pc = (Navajo) inMemoryCache.get(key);
              if (pc != null)
                 return pc;
            }
              if (freq != null && freq.isExpired(expirationInterval)) {
                System.out.println("IN MEMORY CACHE HAS EXPIRED!!!!!!!!");
                Navajo d = (Navajo) inMemoryCache.get(freq.getName());
                inMemoryCacheSize -= d.toString().length();
                inMemoryCache.remove(freq.getName());
              }
            return null;
        } else {
             if (inMemoryCache.get(key) == null) {
                 int size = document.toString().length();
                 inMemoryCacheSize += size;
                 System.out.println("New inMemoryCacheSize = " + inMemoryCacheSize);
                 while (inMemoryCacheSize > configuration.maxInMemoryCacheSize) {
                      String lru = findLFU();
                      System.out.println("REMOVING LRU: " + lru);
                      if (!lru.equals("")) {
                        Navajo n = (Navajo) inMemoryCache.get(lru);
                        inMemoryCacheSize -= n.toString().length();
                        inMemoryCache.remove(lru);
                      } else {
                        break;
                      }
                  }
                  Frequency freq = (Frequency) accessFrequency.get(key);
                  if (freq != null)
                    freq.setCreation();
                  inMemoryCache.put(key, document);
             }
              return document;
        }

    }

    /**
     * Note that write() is a critical section since multiple requests using the same key can be expected!
     */
    public synchronized boolean write(Persistable document, String key) {

        try {
            memoryOperation(key, document, -1, false);
            XMLDocumentUtils.toXML(((Navajo) document).getMessageBuffer(), null, null,
                                    new StreamResult(new File(genFileName(key))));
            fileWrites++;
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean isExpired(long stamp, long interval) {
        long now = System.currentTimeMillis();
        if ((stamp + interval) <= now)
            return true;
        else
            return false;
    }

    public Persistable read(String key, long expirationInterval) {
        Navajo pc = null;

        pc = (Navajo) memoryOperation(key, null, expirationInterval, true);
        if (pc != null)
          return pc;

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

                if (inMemoryCache.get(key) == null)
                  memoryOperation(key, pc, expirationInterval, false);

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
}
