package com.dexels.navajo.tipi;
import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.components.*;
import java.util.*;
import nanoxml.*;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */


public interface Tipi extends TipiContainer {
  public Navajo getNavajo();
  public void addTipiContainer(TipiContainer t, TipiContext context, Map td);
  public void addTipi(Tipi t, TipiContext context, Map td, XMLElement definition);
  public void performService(TipiContext context) throws TipiException;
  public void performService(TipiContext context, String service) throws TipiException;
  public void addMethod(MethodComponent m);
  public String getName();
  public ArrayList getServices();
//  public TipiContainer getContainerByPath(String path);
  public Tipi getTipiByPath(String path);
  public String getId();
  public void setId(String id);
  public TipiLayout getLayout();
  public void clearProperties();
}