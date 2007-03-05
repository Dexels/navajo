package com.dexels.navajo.document;

/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author Arjen Schoneveld
 * @version 1.0
 */
import java.util.HashMap;

public class LazyMessageImpl implements java.io.Serializable{

   /**
	 * 
	 */
	private static final long serialVersionUID = -7446208229906069903L;

 class Entry {
     public String name;
     public int startIndex;
     public int endIndex;
     public int totalElements;
   }

   private HashMap entries;

   public LazyMessageImpl() {
      entries = new HashMap();
   }

   public void addLazyMessage(String name, int startIndex, int endIndex, int totalElements) {
      Entry e = new Entry();
      e.name = name;
      e.startIndex = startIndex;
      e.endIndex = endIndex;
      e.totalElements = totalElements;
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

   public int getTotalElements(String name) {
     Entry e = (Entry) entries.get(name);
     if (e != null)
       return e.totalElements;
     else
       return -1;

   }
}
