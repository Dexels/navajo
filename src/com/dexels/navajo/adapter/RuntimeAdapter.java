package com.dexels.navajo.adapter;

import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.server.Parameters;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.NavajoConfig;
import com.dexels.navajo.server.UserException;
import com.dexels.navajo.mapping.MappableException;
import java.io.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class RuntimeAdapter implements Mappable {

  public boolean run;
  public String script = "";
  public String parameter = "";
  public String output = "";
  public String error = "";

  private String parameterList = null;

  private boolean outputFinished = false;
  private boolean errorFinished = false;

  public void load(Parameters parms, Navajo inMessage, Access access, NavajoConfig config) throws MappableException, UserException {

  }

  public void setScript(String s) {
    this.script = s;
  }

  public void setParameter(String s) {
     if (parameterList == null)
       parameterList = s;
     else
       parameterList += " " + s;
    System.err.println("in setParameter(" +  s + ")");
    System.err.println("parameterList = " + parameterList);
  }

  public void setRun(boolean b) throws UserException {
    try {
      String command = script;
      if (parameterList != null)
        command += " " + parameterList;

      System.err.println("in RuntimeAdapter: about to execute: " + command);

      Runtime rt = Runtime.getRuntime();
      final Process p = rt.exec(command);
      final StringWriter outputWriter = new StringWriter();

      new Thread(new Runnable() {
        public void run() {
          try {
            BufferedReader br_in = new BufferedReader(new InputStreamReader(p.
                getInputStream()));
            String buff = null;
            while ( (buff = br_in.readLine()) != null) {
              outputWriter.write(buff + "\n");
              try {
                Thread.sleep(100);
              }
              catch (Exception e) {}
            }
            br_in.close();
          }
          catch (IOException ioe) {
            System.out.println("Exception caught printing javac result");
            ioe.printStackTrace();
          }
          outputFinished = true;
        }
      }).start();

      final StringWriter errorWriter = new StringWriter();

           new Thread(new Runnable() {
             public void run() {
               try {
                 BufferedReader br_in = new BufferedReader(new InputStreamReader(p.
                     getErrorStream()));
                 String buff = null;
                 while ( (buff = br_in.readLine()) != null) {
                   errorWriter.write(buff + "\n");
                   try {
                     Thread.sleep(100);
                   }
                   catch (Exception e) {}
                 }
                 br_in.close();
               }
               catch (IOException ioe) {
                 System.out.println("Exception caught printing javac result");
                 ioe.printStackTrace();
               }
               errorFinished = true;
             }
           }).start();

      while (!(errorFinished && outputFinished)) {
        Thread.sleep(1000);
        System.err.println("WAITING FOR SCRIPT TO FINISH....");
      }

      output = outputWriter.toString();
      error = errorWriter.toString();
      System.err.println("output = " + output);
      System.err.println("error = " + error);

      System.err.println("in RuntimeAdapter: finished script.");
    } catch (Exception e) {
      throw new UserException(-1, e.getMessage());
    }
  }

  public String getOutput() {
    return output;
  }

  public String getError() {
     return output;
   }

  public void store() throws MappableException, UserException {

  }

  public void kill() {

  }

  public static void main (String [] args) throws Exception {
    RuntimeAdapter ra = new RuntimeAdapter();
    ra.setScript("/home/arjen/projecten/sportlink-serv/navajo-tester/ssl/generate_client_keystore.sh");
    ra.setParameter("/home/arjen/projecten/sportlink-serv/navajo-tester/ssl");
    ra.setParameter("ONGELOOFELIJK");
    ra.setRun(true);
    System.err.println("FINITO: " + ra.getOutput());
    System.err.println("ERROR STRING:>" + ra.getError() + "<");
  }

}