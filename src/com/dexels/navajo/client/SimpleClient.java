package com.dexels.navajo.client;

/**
 * Title:        This/Navajo test client
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Da Vinci Software
 * @author Arjen Schoneveld en Martin Bergman
 * @version 1.0
 */

import com.dexels.navajo.client.NavajoAgent;
import com.dexels.navajo.util.Util;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import com.dexels.navajo.document.*;
import utils.FileUtils;

public class SimpleClient extends Thread {

  int alive = 0;
  long NTRANS = 500;
  FileWriter fw = null;
  int nr_threads = 0;
  int thread_counter = 0;
  double result = 0;

  public SimpleClient(FileWriter fw, int N, int nr_threads, int thread_counter) {
    NTRANS = N;
    this.fw = fw;
    this.nr_threads = nr_threads;
    this.thread_counter = thread_counter;
  }

  public synchronized int getAlive() {
    return alive;
  }

  public synchronized void incAlive() {
    alive++;
  }

  public synchronized void decAlive() {
    alive--;
  }

  public void run() {
    try {
      Navajo doc = null;
      int failed = 0;

      incAlive();

      NavajoAgent agent = new NavajoAgent(ResourceBundle.getBundle("navajoagent"));

      Util u = new Util();

      //doc = u.readNavajoFile("e:/projecten/NBWOdeploy/nbwo/auxilary/scripts/NBWO_basistoets.tml");
      doc = u.readNavajoFile("c:/AAP.txt");
      //agent.send("elife_aanvraag_verstuur", doc, false);
      System.out.println("Beginning statistics");



      String [] postcodes = {"1624TK", "1445MG", "1013SR", "2011DH", "3011BT", "4001AH", "5011AA", "6001AD", "8011AA", "7001CG"};
      int [] huisnummers = {4, 36, 19, 6, 887, 38, 18, 19, 1, 30};
      long start = System.currentTimeMillis();
      Property prop = null;
      for (int i = 0; i < NTRANS; i++) {

        //java.util.Random rand = new java.util.Random(System.currentTimeMillis());
        //int offset = rand.nextInt();
        //String id = "pi"+offset;
        //id = id.substring(0, 8);
        //prop.setValue("1621AB");
        /** BASISTOETS
        prop = doc.getProperty("begin_pagina.Postcode");
        System.out.println("Postcode = " + postcodes[i]);
        prop.setValue(postcodes[i]);
        */
        prop = doc.getProperty("user.username");
        prop.setValue("veh_rem");

        doc.getProperty("dhl_invoergegevens.adres_gegevens.Postcode").setValue(postcodes[i]);
        System.out.println("Set postcode");
        doc.getProperty("dhl_invoergegevens.adres_gegevens.Huisnummer").setValue(huisnummers[i]+"");

        doc.getProperty("dhl_invoergegevens.woning_type.TypeWoning").getSelection("Eengezinswoning").setSelected(true);
        doc.getProperty("dhl_invoergegevens.woning_afmetingen.InhoudWoning").setValue(((10*i)+50)+"");
        doc.getProperty("dhl_invoergegevens.woning_afmetingen.Perceeloppervlakte").setValue(100+"");
        doc.getProperty("dhl_invoergegevens.woning_eigenschappen.Bouwjaar").setValue((1930+(i*5))+"");

        /** BASISTOETS
        prop = doc.getProperty("begin_pagina.Huisnummer");
        //prop.setValue("3");
        prop.setValue(huisnummers[i]+"");
        */
        //System.out.println("About to send: ");
        //agent.send("NBWO_toetsing", doc, false);
        //agent.send("DHL_bereken", doc, false, false, "");
        long tmp = System.currentTimeMillis();
        System.out.println("thread: " + thread_counter + ",:"+ i + ": Done: " + ((tmp - start)/(float)(i+1))/1000.0);
      }
      long end = System.currentTimeMillis();

      double total = ((end - start)/NTRANS)/1000.0;
      System.out.println("Total time/transaction: " + total);

      if (thread_counter == 0)  { // Only thread 0 can write to file.
        //System.out.println("Writing to file");
        fw.write(""+nr_threads+" " + total + "\n");
        fw.flush();
        this.result = total;
      }

    } catch (Throwable e) {
      System.out.println(e.getMessage());
    }
    System.out.println("Finished thread: " + thread_counter);
  }

  public static void main(String args[]) {

    int thread_counter = 0;
    int threads = Integer.parseInt(args[0]);
    int N = Integer.parseInt(args[1]);
    System.out.println("Starting " + threads + " threads, each doing " + N + " iterations");

    FileWriter f = null;
     try {
        f = new FileWriter("experiment", true);
        f.write("Timing experiment with a maximum of " + threads + " threads\n");
        f.write("time: " + new Date() + "\n");
      } catch (Exception e) {
        System.err.println(e.getMessage());
      }

    SimpleClient [] scs = new SimpleClient[threads];

    for (int i = 0; i < threads; i++)
      new SimpleClient(f, N, threads, i).start();

    /**
    for (int j = 1; j < (threads+1); j++) {

      System.out.println("**********************************");
      System.out.println("STARTING NEXT SERIES OF THREADS: ");
      System.out.println("**********************************");

      for (int i = 0; i < j; i++) {
        scs[i] = new SimpleClient(f, N, j, i);
        scs[i].start();
        System.out.println("Started thread: " + i);
      }
      int index = 0;
      int count = j;
      try {
        while (count != 0) {
          sleep(1000);
          if (!scs[index].isAlive())
            count--;
          System.out.println("Waiting, count: " + count);
          index++;
          if (index >= j)
            index = 0;
        }
      } catch (InterruptedException ie) {

      }

      System.out.println("*************************************\n\n");
    }
    **/


    System.out.println("Finished experiments");

  }

}
