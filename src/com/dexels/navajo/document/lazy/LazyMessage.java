package com.dexels.navajo.document.lazy;

/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author Arjen Schoneveld
 * @version 1.0
 */
import java.util.HashMap;

public class LazyMessage {

   class Entry {
     public String name;
     public int startIndex;
     public int endIndex;
   }

   private HashMap entries;

   public LazyMessage() {
      entries = new HashMap();
   }

   public void addLazyMessage(String name, int startIndex, int endIndex) {
      Entry e = new Entry();
      e.name = name;
      e.startIndex = startIndex;
      e.endIndex = endIndex;
      entries.put(name, e);
   }

   public boolean isLazy(String name) {
      return entries.containsKey(name);
   }

   public int getStartIndex(String name) {
      Entry e = (Entry) entries.get(name);
      if (e != null)
        return e.startIndex;
      else
        return 0;
   }

   public int getEndIndex(String name) {
      Entry e = (Entry) entries.get(name);
      if (e != null)
        return e.endIndex;
      else
        return -1;
   }
}