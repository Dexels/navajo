package com.dexels.navajo.tipi.components.core.parsers;

import com.dexels.navajo.tipi.*;
import com.dexels.navajo.document.*;


abstract class BaseTipiParser extends TipiTypeParser {

    protected TipiDataComponent getTipiByPath(TipiComponent source, String path) {
      return (TipiDataComponent) getTipiComponent(source,path);
    }

    protected TipiComponent getTipiComponent(TipiComponent source, String path) {
      if (path.startsWith(".")) { // Relative path
        return source.getTipiComponentByPath(path);
      }
      else { // Absolute path
        return myContext.getTipiComponentByPath(path);
      }
    }

    protected Property getPropertyByPath(TipiComponent source, String path) {
      TipiComponent myTipi = getTipiComponent(source,path);
      Message m = getMessageByPath(source, path);
      if (m != null) {
        Property p = m.getPathProperty(path);
        return p;
      }
      else {
        Navajo myNavajo = myTipi.getNearestNavajo();
        Property p = myNavajo.getProperty(path);
        return p;
      }
    }
    protected Message getMessageByPath(TipiComponent source, String path) {
      TipiComponent myTipi = getTipiComponent(source,path);
      String first_bit;
      if (path.indexOf(":") > -1) {
        first_bit = path.substring(0, path.indexOf(":"));
      }
      else {
        first_bit = path;
      }
      if (first_bit.equals(".")) {
        String last_bit = path.substring(path.indexOf(":") + 1);
        return ( (Navajo) myTipi.getValue(first_bit)).getMessage(last_bit);
      }
      else {
        return (Message) myTipi.getValue(first_bit);
      }
    }
}