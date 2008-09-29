package com.dexels.navajo.adapter.csvmap;

import com.dexels.navajo.mapping.*;
import com.dexels.navajo.server.*;
import com.dexels.navajo.document.*;
import java.util.*;

/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author Arjen Schoneveld
 * @version 1.0
 *
 * This mappable object represents a single line of a CSV file
 * (Used in combination  with CSVMap)
 *
 * $Id$
 *
 */

public class CSVEntryMap implements Mappable {

  public String entry;
  public int column;
  public String [] entries;

  private HashMap newEntries;
  private int max = -1;
  private boolean update = false;

  public void load(Access access) throws MappableException, UserException {
    newEntries = new HashMap();
  }

  public String getEntry() {
    return "";
  }

  public void setColumn(int columnIndex) {
    this.column = columnIndex;
    if (column > max)
      max = column;
  }

  public void setEntry(String value) {
    newEntries.put(new Integer(column), value);
    update = true;
  }

  public String getEntry(Integer columnIndex) {
    if (columnIndex.intValue() < entries.length)
       return entries[columnIndex.intValue()];
    else
       return "";
  }

  public int getEntrySize() {
	  return entries.length;
  }
  
  public void store() throws MappableException, UserException {
      if (update) {
          entries = new String[max+1];
          for (int i = 0; i < max+1; i++) {
            String value = (String) newEntries.get(new Integer(i));
            if (value == null)
              value = "";
            entries[i] = value;
          }
      }
  }

  public void kill() {

  }
}
