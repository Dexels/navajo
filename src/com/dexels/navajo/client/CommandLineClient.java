package com.dexels.navajo.client;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

@Deprecated

/**
 * It doesnt seem to be used or be in any kind of production environment.
 */
public class CommandLineClient {

	  public CommandLineClient() {
	  }

	  public CommandLineClient(String input){
	    try{
//	      BufferedReader inputMsg = new BufferedReader(new FileReader(new File(input)));

	      URL processURL = new URL("http://penelope1.dexels.com/sportlink/knhb/servlet/Postman");

	      System.out.println("Connecting to: " + processURL.toString());
	      HttpURLConnection conn = (HttpURLConnection)processURL.openConnection();
	      conn.setDoOutput(true);
	      conn.setRequestMethod("POST");
	      conn.setUseCaches(false);
	      //PrintWriter out = new PrintWriter(conn.getOutputStream());

//	      StringWriter sw = new StringWriter();
	      BufferedReader br = new BufferedReader(new FileReader(new File(input)));
//	      String line = "";
	      char[] buffer = new char[1024];
	      int read = 0;

	      OutputStreamWriter smeg = new OutputStreamWriter(conn.getOutputStream());

	      while((read = br.read(buffer)) > -1) {
	        smeg.write(buffer, 0, read);
	      }
	      br.close();
	      smeg.flush();
	      smeg.close();
	      
	      // Required for reading repsponse
	      BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	      String response = "";
	      while((response = in.readLine()) != null){
	        System.out.println(response);
	      }
	      System.err.println("Done..");
	      in.close();
	      conn.disconnect();
	    }catch(Exception e){
	      e.printStackTrace();
	    }
	  }

	  public static void main(String args[]){
	    try{
	    	new CommandLineClient("/home/arjen/aap.xml");
	    }catch(Exception e){
	      e.printStackTrace();
	    }
	  }

	}
