package com.dexels.navajo.tipi;
import com.dexels.navajo.nanodocument.*;
import com.dexels.navajo.tipi.components.*;
import java.awt.*;
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

public interface Tipi extends TipiBase {
  public Navajo getNavajo();
//  public void addTipiContainer(TipiContainer t, TipiContext context, Map td);
//  public void addTipi(Tipi t, TipiContext context, Map td, XMLElement definition);
  public void loadData(Navajo n,TipiContext context) throws TipiException;
//  public Tipi addTipiInstance(TipiContext context, Object constraints, XMLElement inst) throws TipiException;
//  public void addPropertyInstance(TipiContext context, XMLElement instance, Map columnAttributes) throws TipiException;
  public void addAnyInstance(TipiContext context, XMLElement instance, Object constraints) throws TipiException;
  public void performService(TipiContext context) throws TipiException;
  public void performService(TipiContext context, String service) throws TipiException;
  public void addMethod(MethodComponent m);
  public String getName();
  public ArrayList getServices();
//  public TipiContainer getContainerByPath(String path);
  public Tipi getTipiByPath(String path);
  public TipiComponent getTipiComponent(String name);
  public TipiComponent getTipiComponentByPath(String path);
//  public String getId();
//  public void setId(String id);
  public TipiLayout getLayout();
  public void clearProperties();
  public void setContainerLayout(LayoutManager layout);
  public LayoutManager getContainerLayout();

 }

