package com.dexels.navajo.adapter;


import com.dexels.navajo.mapping.*;
import com.dexels.navajo.server.*;
import com.dexels.navajo.adapter.csvmap.CSVEntryMap;
import com.dexels.navajo.document.*;
import com.dexels.navajo.document.types.Binary;

import java.io.*;
import java.io.InputStreamReader;
import java.util.*;

/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author Arjen Schoneveld
 * @version 1.0
 *
 * This mappable object can be used to read a comma-separated-file.
 *
 * $Id$
 *
 */

public class CSVMap implements Mappable {

  public CSVEntryMap [] entries;
  public String fileName;
  public Binary fileContent;
  public String separator;
  public int entryCount;

  private boolean update = false;
  private ArrayList draftEntries = null;

  public void load(Parameters parms, Navajo inMessage, Access access, NavajoConfig config) throws MappableException, UserException {

  }

  public void setEntry(CSVEntryMap newEntry) {
    if (draftEntries == null)
       draftEntries = new ArrayList();
    draftEntries.add(newEntry);
    update = true;
  }

  public void setEntries(CSVEntryMap [] newEntries) {
    update = true;
    entries = newEntries;
  }

  public int getEntryCount() {
    return entries.length;
  }

  public CSVEntryMap [] getEntries() throws UserException {
    try {
    	Reader f = null;
    	if ( fileContent != null ) {
    		f = new InputStreamReader(fileContent.getDataAsStream());
    	} else {
    		f = new FileReader( fileName);
    	}
        
        if (f != null) {
            BufferedReader buffer = new BufferedReader(f);
            String line = "";
            ArrayList entryList = new ArrayList();
            while ((line = buffer.readLine()) != null) {
                StringTokenizer tokens = new StringTokenizer(line, separator);
                CSVEntryMap csvEntry = new CSVEntryMap();
                csvEntry.entries = new String[tokens.countTokens()];
                int index = 0;
                while (tokens.hasMoreTokens()) {
                  csvEntry.entries[index++] = tokens.nextToken();
                }
                entryList.add(csvEntry);
            }
          entries = new CSVEntryMap[entryList.size()];
          entries = (CSVEntryMap []) entryList.toArray(entries);
        }
    } catch (java.io.IOException ioe) {
      throw new UserException(-1, ioe.getMessage());
    }
    return entries;
  }

  public void setSeparator(String sep) {
    this.separator = sep;
  }

  public void setFileName(String s) throws UserException {
    this.fileName = s;
  }
  
  public void setFileContent(Binary b) throws UserException {
	  this.fileContent = b;
  }

  public void store() throws MappableException, UserException {
    if (draftEntries != null) {
      entries = new CSVEntryMap[draftEntries.size()];
      entries = (CSVEntryMap []) draftEntries.toArray();
    }
    if (update) {
        try {
            // Write CSV.
            if (entries != null) {
              FileWriter writer = new FileWriter(fileName);
              for (int i = 0; i < entries.length; i++) {
                  CSVEntryMap e = (CSVEntryMap) entries[i];
                  for (int j = 0; j < e.entries.length; j++) {
                    writer.write(e.getEntry(new Integer(j)));
                    if (j < (e.entries.length - 1))
                      writer.write(separator);
                  }
                  if (i < (entries.length - 1))
                    writer.write("\n");
              }
              writer.close();
            }
        } catch (IOException ioe) {
          throw new UserException(-1, ioe.getMessage());
        }
    }
  }

  public void kill() {

  }
  
  public static void main(String [] args) throws Exception {
	  Mappable csv = new CSVMap();
	  ((CSVMap) csv).setSeparator(";");
	  Binary b = new Binary(new File("/home/arjen/tmp/allmatches.csv"));
	  ((CSVMap) csv).setFileContent(b);
	  Mappable [] all = ((CSVMap) csv).getEntries();
	  for (int i = 0; i < all.length; i++ ) {
		  System.err.println("a = " + ((CSVEntryMap) all[i]).getEntry(new Integer(0)));
	  }
  }
}
