package com.dexels.navajo.client;

import java.io.*;
import java.net.*;
import com.dexels.navajo.document.*;

public class SimpleClient extends Thread {

    private Navajo inputFile;
    private int total = 0;
    private String header = "";
    private String URI = "";

    public SimpleClient(Navajo inputFile, String uri) {
      this.inputFile = inputFile;
      this.URI = uri;
    }

    private synchronized void sumTotal(int length) {
        //System.out.println("in sumTotal: length = " + length);
        total += length;
    }

    public  int getTotal() {
      return total;
    }

    public  void run() {
        try {
          URL url = new URL("http://"+this.URI);
          URLConnection con = url.openConnection();
          con.setDoOutput(true);
          con.setDoInput(true);
          con.setUseCaches(false);
          con.setRequestProperty("Content-type", "text/plain");
          OutputStream o = con.getOutputStream();

          PrintWriter writer = new PrintWriter(con.getOutputStream());
          writer.write(inputFile.toString());
          writer.close();

          InputStream i = con.getInputStream();
          BufferedReader in = new BufferedReader(new java.io.InputStreamReader(i));
          StringBuffer buffer = new StringBuffer(4048);
          String line = "";
          while ((line = in.readLine()) != null) {
            buffer.append(line);
          }

          sumTotal(buffer.toString().length());

        } catch (Throwable e) {
            System.out.println(e.getMessage());
        }

    }

    public static void main(String args[]) throws Exception {

        int totalThreads = (System.getProperty("threads") != null) ?
                            Integer.parseInt(System.getProperty("threads")) + 1 : 25;
        int totalExperiments = (System.getProperty("experiments") != null) ?
                            Integer.parseInt(System.getProperty("experiments")) : 1;
        if (args.length < 3) {
          System.out.println("java -jar performance.jar <Navajo server URI> <input file> <service> [expiration]");
          System.exit(1);
        }
        String uri = args[0];
        String inputFile = args[1];
        String rpcName = args[2];
        String userName = "";
        String passWord = "";
        long expiration_interval = (args.length > 3) ? Integer.parseInt(args[3]) : -1;
        Navajo f = NavajoFactory.getInstance().createNavajo(new FileInputStream(inputFile));

        System.out.println("Constructed TML: ");
        System.out.println(f.toString());
        for (int i = 1; i < totalThreads; i++) {
            double avgBw = 0.0;
            double avgReceived = 0.0;
            double avgTotal = 0.0;
            for (int j = 0; j < totalExperiments; j++) {
              long start = System.currentTimeMillis();
              SimpleClient c1= new SimpleClient(f, uri);
              for (int k = 0; k < i; k++) {
                c1.run();
              }
              Thread.yield();
              long end = System.currentTimeMillis();

              double total = ((end - start)/1000.0);
              avgTotal += total;
              double bw = c1.getTotal()/1024.0/total;
              avgBw += bw;
              avgReceived += c1.getTotal();
            }
            avgTotal /= totalExperiments;
            avgBw /= totalExperiments;
            avgReceived /= totalExperiments;
            System.out.println("Threads: " + i + ", Received: " + avgReceived + " bytes in " + avgTotal + " secs. (" + avgBw + " Kb/s)");
        }
    }

}
