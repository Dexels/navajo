package com.dexels.navajo.tipi;
import nanoxml.*;
import java.util.*;
import java.awt.*;
import com.dexels.navajo.tipi.components.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public interface TipiBase extends TipiEventListener {
  public abstract void load(XMLElement definition, XMLElement instance, TipiContext context) throws TipiException;
  public void instantiateComponent(XMLElement instance, XMLElement classdef) throws TipiException;

//  public void addProperty(String name, BasePropertyComponent bpc,TipiContext context, Map contraints);
  public void addComponent(TipiBase c, TipiContext context, Object td);
  public Container getContainer();
  public Container getOuterContainer();
  public void setContainer(Container c);
  public Container createContainer();
  public void addToContainer(Component c, Object constraints);
//  public void setContainerLayout(LayoutManager layout);
//  public void addComponentInstance(TipiContext context, XMLElement instance, Map constraints) throws TipiException;
  public TipiComponent addComponentInstance(TipiContext context, XMLElement instance, Object constraints) throws TipiException;
//  public void setValue(String s);
  public void setComponentValue(String name, Object component);
  public Object getComponentValue(String name);
  public String getName();
  public String getId();
  public void setValue(String name, Object value);
  public Object getValue(String name);

}