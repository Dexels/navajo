package com.dexels.navajo.tipi;
import com.dexels.navajo.document.*;
import nanoxml.*;
import java.util.*;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public interface TipiContainer extends TipiBase {
//  public void addProperty(String name, TipiComponent comp, TipiContext context, Map td);
//  public void load(XMLElement x,TipiContext context) throws TipiException;
  public void loadData(Navajo n,TipiContext context) throws TipiException;
//  public void addTipiContainer(TipiContainer t, TipiContext context, Map td);
  public String getName();
//  public TipiContainer getContainerByPath(String path);
//  public void parseTable(TipiContext context, Tipi tipiParent,  XMLElement table) throws TipiException;
}