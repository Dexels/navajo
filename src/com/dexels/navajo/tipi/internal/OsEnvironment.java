package com.dexels.navajo.tipi.internal;

/*
 * @(#)OSEnvironment.java

 *

 * Copyright 2002 ROLANDS & ASSOCIATES Corporation. All rights reserved.

 *

 */

/** OSEnvironment class provides read only access to the operating system environment variables.
 * <BR>

 * Presently, it will obtain the environment variables for Windows 95/98/NT/2000 and Unix.

 * @author David J. Ward

 * @version 1.0

 *

 */
public class OsEnvironment {
  /**
   * Private Constructor. OSEnvironment is not instantiable.

   */
  private OsEnvironment() {}

  /**
   * @return a Properties object containing the environment variables and their associated values.

   * @throws Throwable if an execption occurs.

   */
  private static java.util.Properties getEnvironment() {
    Process p = null;
    java.util.Properties envVars = new java.util.Properties();
    Runtime r = Runtime.getRuntime();
    String OS = System.getProperty("os.name").toLowerCase();
    try {
      // Get the Windows 95 environment variables
      if (OS.indexOf("windows 9") > -1) {
        p = r.exec("command.com /c set");
      }
      // Get the Windows NT environment variables
      else if (OS.indexOf("nt") > -1) {
        p = r.exec("cmd.exe /c set");
      }
      // Get the Windows 2000 environment variables
      else if (OS.indexOf("2000") > -1) {
        p = r.exec("cmd.exe /c set");
      }
      // Get the Windows XP environment variables
      else if (OS.indexOf("xp") > -1) {
        p = r.exec("cmd.exe /c set");
      }
      // Get the unix environment variables
      else if (OS.indexOf("linux") > -1) {
        p = r.exec("env");
      }
      // Get the unix environment variables
      else if (OS.indexOf("unix") > -1) {
        p = r.exec("/bin/env");
      }
      // Get the unix environment variables
      else if (OS.indexOf("sunos") > -1) {
        p = r.exec("/bin/env");
      }
      else {
        System.out.println("OS not known: " + OS);
      }
    }
    catch (java.io.IOException e) {
      e.printStackTrace();
    }
    java.io.BufferedReader br = new java.io.BufferedReader(new java.io.InputStreamReader(p.getInputStream()));
    String line;
    try {
      int idx;
      String key, value;
      while ( (line = br.readLine()) != null) {
        idx = line.indexOf('=');
        // if there is no equals sign on the line skip to the net line

        // this occurs when there are newline characters in the environment variable

        //
        if (idx < 0) {
          continue;
        }
        key = line.substring(0, idx);
        value = line.substring(idx + 1);
        envVars.setProperty(key, value);
      }
    }
    catch (java.io.IOException e) {
      e.printStackTrace();
    }
    return envVars;
  }

  private static java.util.Properties env = null;
  /** Return a Properties object containing the environment variables.
   *

   * @return a Properties object containing the environment variables and their associated values.

   */
  public static java.util.Properties get() {
    if (env == null) {
      env = getEnvironment();
    }
    return (java.util.Properties) env.clone();
  }

  /**
   * Searches for the property with the specified key in the environment.

   *  The method returns

   * <code>null</code> if the property is not found.

   *

   * @param   key   the property key.

   * @return  the value in environment with the specified key value.

   *

   */
  public static String getProperty(String key) {
    if (env == null) {
      env = getEnvironment();
    }
    return env.getProperty(key);
  }

  /**
   * Searches for the property with the specified key in environment.

   * The method returns the

   * default value argument if the property is not found.

   *

   * @param   key            the hashtable key.

   * @param   defaultValue   a default value.

   *

   * @return  the value in this property list with the specified key value.

   */
  public static String getProperty(String key, String defaultValue) {
    if (env == null) {
      env = getEnvironment();
    }
    String val = env.getProperty(key);
    return (val == null) ? defaultValue : val;
  }

  static Boolean windowsEnvironment;
  public static boolean isWindows() {
    if (windowsEnvironment == null) {
      String OS = System.getProperty("os.name").toLowerCase();
      // Get the Windows 95 environment variables
      if (OS.indexOf("windows 9") > -1) {
        windowsEnvironment = new Boolean(true);
      }
      // Get the Windows NT environment variables
      else if (OS.indexOf("nt") > -1) {
        windowsEnvironment = new Boolean(true);
      }
      // Get the Windows 2000 environment variables
      else if (OS.indexOf("2000") > -1) {
        windowsEnvironment = new Boolean(true);
      }
      // Get the Windows XP environment variables
      else if (OS.indexOf("xp") > -1) {
        windowsEnvironment = new Boolean(true);
      }
      // Get the unix environment variables
      else if (OS.indexOf("linux") > -1) {
        windowsEnvironment = new Boolean(false);
      }
      // Get the unix environment variables
      else if (OS.indexOf("unix") > -1) {
        windowsEnvironment = new Boolean(false);
      }
      // Get the unix environment variables
      else if (OS.indexOf("sunos") > -1) {
        windowsEnvironment = new Boolean(false);
      }
      else {
        new Exception("OS not known: " + OS).printStackTrace();
        windowsEnvironment = new Boolean(false);
      }
    }
    return windowsEnvironment.booleanValue();
  }

  /** Main method to test the OSEnvironment class.
   * @param args command line arguments

   *

   */
  public static void main(String args[]) {
    try {
      java.util.Properties p = OsEnvironment.get();
      p.list(System.out);
      System.out.println("the current value of TEMP is : " +
                         p.getProperty("TEMP"));
    }
    catch (Throwable e) {
      e.printStackTrace();
    }
  }
}
