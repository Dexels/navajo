package com.dexels.navajo.tipi;
import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.tipixml.*;
import java.util.*;
import java.awt.*;
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

  public TipiLayout() {
  }

//  public abstract void instantiateLayout(TipiContext context, Tipi t, XMLElement def);
  public abstract void createLayout() throws TipiException;
  protected abstract void loadLayout(XMLElement def, Tipi current, Navajo n) throws TipiException;

  public TipiConstraintEditor getConstraintEditor() {
    return myConstraintEditor;

  }
//  public abstract void reCreateLayout(TipiContext context,Tipi t, Navajo n) throws TipiException;
//  public abstract boolean needReCreate();
//  public abstract boolean customParser();

  public void loadLayout(Tipi current, Navajo n) throws TipiException {
    loadLayout(myDefinition,current,n);
  }


  public void initializeLayout(XMLElement def) throws TipiException {
    myDefinition = def;
    String constraintClass = def.getStringAttribute("");
    if (constraintClass.equals("")) {
      return;
    }
    Class constraintEditor;
    try {
      constraintEditor = Class.forName(constraintClass);
    }
    catch (ClassNotFoundException ex) {
      System.err.println("Warning constrainteditor class: "+constraintClass);
      return;
    }
    if (!TipiConstraintEditor.class.isInstance(constraintEditor)) {
      System.err.println("Warning: ConstraintEditor class: "+constraintEditor+" does not implement the TipiConstraintEditor interface!");
      return;
    }
    try {
      myConstraintEditor = (TipiConstraintEditor) constraintEditor.newInstance();
    }
    catch (IllegalAccessException ex1) {
      System.err.println("Warning error initializing constrainteditor class: "+constraintClass+" error: "+ex1.getMessage());
      return;
    }
    catch (InstantiationException ex1) {
      System.err.println("Warning error initializing constrainteditor class: "+constraintClass+" error: "+ex1.getMessage());
      return;
    }
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