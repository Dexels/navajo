package com.dexels.navajo.tipi.internal;

import java.util.*;
import com.dexels.navajo.tipi.tipixml.*;
import com.dexels.navajo.tipi.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */


public class TipiPackage {
  private String description;
  private String id;
  private XMLElement myXml;
  private Set myJars = new HashSet();
  private Set myDeps = new HashSet();
  private String classDef = null;
  private final TipiContext myContext;
  public TipiPackage(TipiContext tc, XMLElement x) {
    myXml = x;
    myContext = tc;
    id = x.getStringAttribute("id");
    description = x.getStringAttribute("name");
    classDef = x.getStringAttribute("classdef");
    Vector v = x.getChildren();
    for (int i = 0; i < v.size(); i++) {
      XMLElement current = (XMLElement)v.get(i);
      if (current.getName().equals("jar")) {
        String file = current.getStringAttribute("file");
        myJars.add(file);
      }
      if (current.getName().equals("tipi-dependency")) {
        String id = current.getStringAttribute("id");
        myDeps.add(id);
      }
    }
  }

  public XMLElement getXml() {
    return myXml;
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return description;
  }

  public Set getJars() {
    return myJars;
  }

  public Set getDeps() {
    return myDeps;
  }

  public String getClassDef() {
    return classDef;
  }

  public String toString() {
    if (description!=null) {
      return description;
    }
    if (id!=null) {
      return id;
    }
    return "Mystery";
  }
}
