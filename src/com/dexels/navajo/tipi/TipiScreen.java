package com.dexels.navajo.tipi;
import java.util.*;
import java.awt.*;
import nanoxml.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public interface TipiScreen{
  public void addTipi(Tipi t, TipiContext context,Map td);
  public Tipi getTipi(String name);
  public TipiContainer getContainerByPath(String path);
  public Tipi getTipiByPath(String path);
  public void load(XMLElement x,TipiContext context) throws TipiException;
  public Container getContainer();
}