package com.dexels.navajo.install;

/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author Arjen Schoneveld
 * @version 1.0
 */

import java.io.*;
import java.util.*;

public class Tools {

  private static String searchAndReplace(String text, String tag, String replace) {

     StringBuffer result;
     String before = "", last = "";
     int start, end;

     result = new StringBuffer();

     start = 0;
     end = -1;

     start = text.indexOf(tag, end);

     if (start == -1)
        return text;

     while (start != -1) {

       before = text.substring(end+1, start);
       end = text.indexOf("}", start);

       result.append(before);
       result.append(replace);

       start = text.indexOf(tag, end);
       if (start == -1)
         last = text.substring(end+1, text.length());
         result.append(last);
     }

     return result.toString();

   }


  /**
   * This method copies fileIn to fileOut while replacing tokens with a value.
   * Tokens are named: ${<TOKEN_NAME>} and are stored in a hashmap tokens as key-value pairs.
   *
   * @param fileIn
   * @param fileOut
   * @param tokens
   */
  public static void copyAndReplaceTokens(String fileIn, String fileOut, HashMap tokens) throws IOException, FileNotFoundException {

    BufferedReader reader = new java.io.BufferedReader(new java.io.InputStreamReader(new FileInputStream(new File(fileIn))));
    FileWriter fos = new FileWriter(new File(fileOut));

    String line = "";
    while ((line = reader.readLine()) != null) {
      Iterator allTokens =  tokens.keySet().iterator();
      while (allTokens.hasNext()) {
        String token = allTokens.next().toString();
        String value = tokens.get(token).toString();
        line = searchAndReplace(line, "{"+token+"}", value);
      }
      fos.write(line+"\n");
    }
    fos.close();

  }

  /**
   * This method extract a jar file to a specified directory.
   *
   * @param jarFile
   * @param extractPath
   * @throws FileNotFoundException
   * @throws IOException
   */
  public static void extractJar(String jarFile, String extractPath) throws FileNotFoundException, IOException {
     java.util.zip.ZipInputStream zipIn = new java.util.zip.ZipInputStream(new java.io.FileInputStream(jarFile));

    byte[] buf = new byte[1024];
    int len;
    java.util.zip.ZipEntry entry = null;

    while ((entry = zipIn.getNextEntry()) != null)  {
        if (entry.isDirectory()) {
          java.io.File zipDir = new java.io.File(extractPath+"/"+entry.getName());
          zipDir.mkdir();
        } else {
          java.io.BufferedOutputStream outFile = new java.io.BufferedOutputStream(new java.io.FileOutputStream(extractPath+"/"+entry.getName()));
          while ((len = zipIn.read(buf)) != -1)  {
            outFile.write(buf,0,len);
          }
          outFile.close();
        }
    }
    zipIn.close();
  }

  public static void mkDir(String dir) {
    File f = new File(dir);
    f.mkdir();
  }

  public static void main(String args[]) {
    String line = "d:\\install";
    line = line.replace('\\', '/');
    System.out.println(line);
  }
}