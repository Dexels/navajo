package com.dexels.navajo.adapter;

import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.UserException;
import com.dexels.navajo.mapping.MappableException;
import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

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
  public String dir = "";
  
  private String parameterList = null;

  private boolean outputFinished = false;
  private boolean errorFinished = false;
  
  private static Object semaphore = new Object();

  public void load(Access access) throws MappableException, UserException {

  }

  public void setScript(String s) {
    this.script = s;
  }

  public void setParameter(String s) {
     if (parameterList == null)
       parameterList = s;
     else
       parameterList += " " + s;
  }

  public void setDir(String s) {
	  this.dir = s;
  }
  
  public void setRun(boolean b) throws UserException {

	  // Note, this must be synchronized since Process is not thread safe.
	  synchronized (semaphore) {

		  try {
			  String command = script;
			  if (parameterList != null)
				  command += " " + parameterList;

			  //System.err.println("in RuntimeAdapter: about to execute: " + command);

			  //Runtime rt = Runtime.getRuntime();
			  StringTokenizer tk = new StringTokenizer(command, ";");

			  while (tk.hasMoreTokens()) {

				  ArrayList cmds = new ArrayList();
				  String cmdString = tk.nextToken();
				  System.err.println("cmdString = " + cmdString);
				  StringTokenizer tk2 = new StringTokenizer(cmdString, " ");
				  while (tk2.hasMoreTokens() ) {
					  cmds.add(tk2.nextToken());
				  }

				  ProcessBuilder pb = new ProcessBuilder(cmds);

				  if ( dir != null && !dir.equals("") ) {
					  pb.directory(new File(dir));
				  }

				  Process p = pb.start();

				  StringWriter outputWriter = new StringWriter();

				  try {
					  BufferedReader br_in = new BufferedReader(new InputStreamReader(p.
							  getInputStream()));
					  String buff = null;
					  while ( (buff = br_in.readLine()) != null) {
						  outputWriter.write(buff + "\n");
					  }
					  br_in.close();
				  }
				  catch (IOException ioe) {
					  System.out.println("Exception caught printing javac result");
					  ioe.printStackTrace();
				  }
				  outputFinished = true;


				  StringWriter errorWriter = new StringWriter();

				  try {
					  BufferedReader br_in = new BufferedReader(new InputStreamReader(p.
							  getErrorStream()));
					  String buff = null;
					  while ( (buff = br_in.readLine()) != null) {
						  errorWriter.write(buff + "\n");
					  }
					  br_in.close();
				  }
				  catch (IOException ioe) {
					  System.out.println("Exception caught printing javac result");
					  ioe.printStackTrace();
				  }
				  errorFinished = true;

				  while (!(errorFinished && outputFinished)) {
					  Thread.sleep(1000);
					  //System.err.println("WAITING FOR SCRIPT TO FINISH....");
				  }

				  output += outputWriter.toString();
				  error += errorWriter.toString();
				  //println("output = " + output);
				  //System.err.println("error = " + error);
			  }

			  //System.err.println("in RuntimeAdapter: finished script.");
		  } catch (Exception e) {
			  e.printStackTrace(System.err);
			  throw new UserException(-1, e.getMessage());
		  }

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
    
    ra.setScript("cat aap;ls -la");
    ra.setDir("/home/arjen/projecten/MigratieGlidepath");
//    ra.setParameter("/home/arjen/projecten/sportlink-serv/navajo-tester/ssl");
//    ra.setParameter("ONGELOOFELIJK");
    ra.setRun(true);
    
    System.err.println("FINITO: " + ra.getOutput());
    System.err.println("ERROR STRING:>" + ra.getError() + "<");
  }

}