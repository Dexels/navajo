package com.dexels.navajo.tipi.components.core.parsers;

import com.dexels.navajo.tipi.*;
import com.dexels.navajo.document.*;
import java.util.*;


abstract class BaseTipiParser extends TipiTypeParser {

    protected TipiDataComponent getTipiByPath(TipiComponent source, String path) {
      return (TipiDataComponent) getTipiComponent(source,path);
    }

    protected TipiComponent getTipiComponent(TipiComponent source, String totalpath) {
      String path = getComponentPart(totalpath);
      if (path.startsWith(".")) { // Relative path
        return source.getTipiComponentByPath(path);
      }
      else { // Absolute path
        return myContext.getTipiComponentByPath(path);
      }
    }

    protected Property getPropertyByPath(TipiComponent source, String path) {
      StringTokenizer st = new StringTokenizer(path,":");
      String partOne = st.nextToken();
      String partTwo = st.nextToken();
      String partThree = st.nextToken();
      System.err.println("   >>>>>>>>> getPropertyByPath::::: "+path);
      TipiComponent myTipi = getTipiComponent(source,path);
      System.err.println("FIRST BIT: "+partOne);
      if (partTwo.equals(".")) {
        System.err.println("FOUND: .");
//        String last_bit = path.substring(path.indexOf(":") + 1);
        System.err.println("Last bit: "+partThree);
        return myTipi.getNavajo().getProperty(partThree);
      }
      else {
//        String last_bit = path.substring(path.indexOf(":") + 1);
        Message msg = (Message) myTipi.getValue(partTwo);
        System.err.println("VALUE::::::");
        if (msg==null) {
          return null;
        }
//        msg.write(System.err);

        return msg.getProperty(partThree);
      }
    }

    protected Message getMessageByPath(TipiComponent source, String path) {
      StringTokenizer st = new StringTokenizer(path,":");
      String partOne = st.nextToken();
      String partTwo = st.nextToken();
      String partThree = st.nextToken();
      System.err.println("   >>>>>>>>> getPropertyByPath::::: "+path);
      TipiComponent myTipi = getTipiComponent(source,path);
      System.err.println("FIRST BIT: "+partOne);
      if (partTwo.equals(".")) {
        System.err.println("FOUND: .");
//        String last_bit = path.substring(path.indexOf(":") + 1);
        System.err.println("Last bit: "+partThree);
        return myTipi.getNavajo().getMessage(partThree);
      }
      else {
//        String last_bit = path.substring(path.indexOf(":") + 1);
        Message msg = (Message) myTipi.getValue(partTwo);
        System.err.println("VALUE::::::");
        if (msg==null) {
          return null;
        }
//        msg.write(System.err);
        return msg.getMessage(partThree);
      }
    }

    protected String getComponentPart(String s) {
      return s.split(":")[0];
    }
}