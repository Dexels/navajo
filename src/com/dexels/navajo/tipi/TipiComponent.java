package com.dexels.navajo.tipi;

import java.util.*;
import java.awt.*;
import com.dexels.navajo.client.*;
import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.internal.*;
import com.dexels.navajo.tipi.tipixml.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public interface TipiComponent
    extends ConditionErrorHandler, TipiEventListener {
  public void removeFromContainer(Component c);

  public void addToContainer(Component c, Object constraints);

  public void setHighlighted(boolean value);

  public boolean isHighlighted();

  public void showGrid(boolean value);

  public boolean isGridShowing();

  public void highLight(Component c, Graphics g);

  public void paintGrid(Component c, Graphics g);

  public TipiContext getContext();

  public void initContainer();

  public Set getPossibleValues();

  public void deregisterEvent(TipiEvent e);

  public void setName(String name);

  public void setContext(TipiContext tc);

  public boolean isPropertyComponent();

  public void setPropertyComponent(boolean b);

  public void setValue(String name, Object value);

  public void setValue(String name, Object value, TipiComponent source);

  public Object getValue(String name);

  public TipiValue getTipiValue(String name);

  /**
   * Loads an event definition from the component definition
   */
  public void loadEventsDefinition(TipiContext context, XMLElement definition, XMLElement classDef) throws TipiException;

  public void load(XMLElement def, XMLElement instance, TipiContext context) throws TipiException;

  public void setId(String id);

  public ArrayList getDefinedEvents();

  public Map getClassDefValues();

  public boolean isVisibleElement();

  public void instantiateComponent(XMLElement instance, XMLElement classdef) throws TipiException;

  public void loadStartValues(XMLElement element);

  public boolean isReusable();

  public void reUse();

  public String getId();

  public void performMethod(String methodName, TipiAction invocation);

  public TipiComponentMethod getTipiComponentMethod(String methodName);

  public TipiComponent getTipiComponentByPath(String path);

  public void setLayout(TipiLayout tl);

  public TipiLayout getLayout();

  public TipiComponent getTipiComponent(String s);

  public TipiComponent getTipiComponent(int i);

  public ArrayList getChildComponentIdList();

  public void disposeComponent();

  public void removeChild(TipiComponent child);

  public void setParent(TipiComponent tc);

  public TipiComponent getTipiParent();

  public void addComponent(TipiComponent c, TipiContext context, Object td);

  public Navajo getNavajo();

  public Navajo getNearestNavajo();

  public void setConstraints(Object constraints);

  public Object getConstraints();

  public void addTipiEvent(TipiEvent te);

  public void removeTipiEvent(TipiEvent e);

  public void refreshParent();

  public boolean performTipiEvent(String type, Object event) throws TipiException;

  public String getName();

  public Container getContainer();

  public void replaceContainer(Container c);

  public Container getOuterContainer();

  public void setContainer(Container c);

  public void setOuterContainer(Container c);

  public void setCursor(int cursorid);

  public XMLElement store();

  public void checkValidation(Message msg);

  public void resetComponentValidationStateByRule(String id);

  public boolean hasConditionErrors();

//  public TreeNode getChildAt(int childIndex);
//  public int getChildCount();
//  public TreeNode getParent();
//  public int getIndex(TreeNode node);
//  public boolean getAllowsChildren();
//  public boolean isLeaf();
//  public Enumeration children();
  public int getIndex(TipiComponent node);

//  public ImageIcon getIcon();
//  public ImageIcon getSelectedIcon();
  public String toString();

  public boolean hasPath(String path);

  public String getPath();

  public String getPath(String typedef);

  public AttributeRef getAttributeRef(String name);

  public ArrayList getEventList();

  public void tipiLoaded();

  public void childDisposed();

  public void componentInstantiated();

  public boolean isStudioElement();

  public void setStudioElement(boolean b);

  public void addHelper(TipiHelper th);

  public void removeHelper(TipiHelper th);

  public void helperSetComponentValue(String name, Object object);

  public Object helperGetComponentValue(String name);

  public void helperRegisterEvent(TipiEvent te);

  public void helperDeregisterEvent(TipiEvent te);

  public int getChildCount();

  public TipiComponent addComponentInstance(TipiContext context, XMLElement inst, Object constraints) throws TipiException;

}