package com.dexels.navajo.client;


import java.io.*;
import java.util.*;
import java.net.*;

public class SimpleClient  {

    public static void run(String inputFile) {
        try {
          URL url = new URL("http://dexels.durgerlan.nl/sport-tester/servlet/Postman");
          URLConnection con = url.openConnection();
          con.setDoOutput(true);
          con.setDoInput(true);
          con.setUseCaches(false);
          con.setRequestProperty("Content-type", "text/plain");
          OutputStream o = con.getOutputStream();

          PrintWriter writer = new PrintWriter(con.getOutputStream());
          writer.write("<?xml version='1.0'?>");

          BufferedReader reader = new BufferedReader(new FileReader(inputFile));
          String line = "";
          while ((line = reader.readLine()) != null) {
            writer.write(line);
          }
          writer.close();

          InputStream i = con.getInputStream();
          BufferedReader in = new BufferedReader(new java.io.InputStreamReader(i));
          while ((line = in.readLine()) != null) {
            System.out.println(line);
          }

        } catch (Throwable e) {
            System.out.println(e.getMessage());
        }

    }

    public static void main(String args[]) {

        String inputFile = args[0];
        run(inputFile);
    }

}
