package com.dexels.navajo.tipi.internal;

import java.util.*;
import java.awt.*;
import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.studio.*;
import com.dexels.navajo.tipi.tipixml.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public abstract class TipiLayout {
  protected Map componentValues = new HashMap();
  protected String layoutName = null;
  protected LayoutManager myLayout;
  protected XMLElement myDefinition;
  protected TipiConstraintEditor myConstraintEditor = null;
  protected XMLElement myClassDef = null;
  protected TipiContext myContext;
  public TipiLayout() {
  }

  public void setContext(TipiContext tc) {
    myContext = tc;
  }

//  public abstract void instantiateLayout(TipiContext context, Tipi t, XMLElement def);
  public abstract void createLayout() throws TipiException;

  protected abstract void loadLayout(XMLElement def, TipiComponent current, Navajo n) throws TipiException;

  public TipiConstraintEditor getConstraintEditor() {
    if (myDefinition == null) {
      return null;
    }
    return myConstraintEditor;
  }

  public void loadLayout(TipiComponent current, Navajo n) throws TipiException {
    loadLayout(myDefinition, current, n);
  }

  public XMLElement store() {
    XMLElement xe = new CaseSensitiveXMLElement();
    xe.setName("layout");
    xe.setAttribute("type", getName());
    return xe;
  }

  public void setClassDef(XMLElement xe) {
    myClassDef = xe;
  }

  public void loadClassDef() {
    String constraintClass = myClassDef.getStringAttribute("constrainteditor", "");
    if (constraintClass.equals("")) {
      return;
    }
    Class constraintEditor;
    /** @todo Maybe check first... The classnotfound exception is not exceptional */
    try {
      constraintEditor = Class.forName(constraintClass);
    }
    catch (ClassNotFoundException ex) {
      // No problem.
      return;
    }
    try {
      myConstraintEditor = (TipiConstraintEditor) constraintEditor.newInstance();
    }
    catch (IllegalAccessException ex1) {
      System.err.println("Warning error initializing constrainteditor class: " + constraintClass + " error: " + ex1.getMessage());
      return;
    }
    catch (InstantiationException ex1) {
      System.err.println("Warning error initializing constrainteditor class: " + constraintClass + " error: " + ex1.getMessage());
      return;
    }
    if (!TipiConstraintEditor.class.isInstance(myConstraintEditor)) {
      System.err.println("Warning: ConstraintEditor class: " + constraintEditor.getClass() + " does not implement the TipiConstraintEditor interface, which is: " + TipiConstraintEditor.class);
      return;
    }
  }

  public void initializeLayout(XMLElement def) throws TipiException {
    myDefinition = def;
  }

  public Object createDefaultConstraint(int index) {
    return null;
  }

  protected abstract void setValue(String name, TipiValue tv);

  public String getName() {
    return layoutName;
  }

  public void setName(String name) {
    layoutName = name;
  }

  public LayoutManager getLayout() {
    return myLayout;
  }

  public void setLayout(LayoutManager l) {
    myLayout = l;
  }

  private void loadValues(XMLElement values) {
    Vector children = values.getChildren();
    for (int i = 0; i < children.size(); i++) {
      XMLElement xx = (XMLElement) children.get(i);
      String valueName = xx.getStringAttribute("name");
      TipiValue tv = new TipiValue();
      tv.load(xx);
      componentValues.put(valueName, tv);
      if (tv.getValue() != null && !"".equals(tv.getValue())) {
        setValue(tv.getName(), tv);
      }
    }
  }
}