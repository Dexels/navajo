package com.dexels.navajo.tipi;
import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.components.*;
import java.awt.*;
import java.util.*;
import com.dexels.navajo.tipi.tipixml.*;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public interface Tipi extends TipiEventListener {
  public Navajo getNavajo();
  public void loadData(Navajo n,TipiContext context) throws TipiException;
  public TipiComponent addAnyInstance(TipiContext context, XMLElement instance, Object constraints) throws TipiException;
  public void performService(TipiContext context, String tipiPath, String service) throws TipiException;
  public String getName();
  public ArrayList getServices();
  public void addService(String service);
  public void removeService(String service);

  public Tipi getTipiByPath(String path);
  public TipiComponent getTipiComponent(String name);
  public TipiComponent getTipiComponentByPath(String path);
  public TipiLayout getLayout();
  public void clearProperties();
  public void setContainerLayout(LayoutManager layout);
  public LayoutManager getContainerLayout();
  public boolean loadErrors(Navajo n);
  public void setConstraints(Object td);
  public String getPath();
  public boolean hasPath(String path);
  public void autoLoadServices(TipiContext context) throws TipiException;
  public void tipiLoaded();
  public void childDisposed();
  public abstract void load(XMLElement definition, XMLElement instance, TipiContext context) throws TipiException;
  public void instantiateComponent(XMLElement instance, XMLElement classdef) throws TipiException;
  public void addComponent(TipiComponent c, TipiContext context, Object td);
  public Container getContainer();
  public Container getOuterContainer();
  public void setContainer(Container c);
  public Container createContainer();
  public void addToContainer(Component c, Object constraints);
  public void removeFromContainer(Component c);
  public boolean isReusable();
  public void reUse();
  public String getId();
  public void setValue(String name, Object value);
  public Object getValue(String name);
  public void setLayout(TipiLayout tl);
 }

