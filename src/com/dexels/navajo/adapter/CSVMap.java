package com.dexels.navajo.adapter;

import com.dexels.navajo.mapping.*;
import com.dexels.navajo.server.*;
import com.dexels.navajo.document.*;

import java.io.*;
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
  public String separator;
  public int entryCount;

  private boolean update = false;

  public void load(Parameters parms, Navajo inMessage, Access access, NavajoConfig config) throws MappableException, UserException {

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
        FileReader f = new FileReader(fileName);
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

  public void store() throws MappableException, UserException {
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
}