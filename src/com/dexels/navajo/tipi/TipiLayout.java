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
  protected XMLElement myClassDef = null;
  public TipiLayout() {
  }

//  public abstract void instantiateLayout(TipiContext context, Tipi t, XMLElement def);
  public abstract void createLayout() throws TipiException;
  protected abstract void loadLayout(XMLElement def, Tipi current, Navajo n) throws TipiException;

  public TipiConstraintEditor getConstraintEditor() {
    if (myDefinition==null) {
      return null;
    }
//    if (myConstraintEditor==null) {
///** @todo REMOVE THIS VILE CONSTRUCTION! */
//      try {
//        initializeLayout(myDefinition);
//      }
//      catch (TipiException ex) {
//        ex.printStackTrace();
//      }
//    }
    return myConstraintEditor;
  }
//  public abstract void reCreateLayout(TipiContext context,Tipi t, Navajo n) throws TipiException;
//  public abstract boolean needReCreate();
//  public abstract boolean customParser();

  public void loadLayout(Tipi current, Navajo n) throws TipiException {
    loadLayout(myDefinition,current,n);
  }

  public XMLElement store() {
    XMLElement xe = new CaseSensitiveXMLElement();
    xe.setName("layout");
    xe.setAttribute("type",getName());
    return xe;
  }

  public void setClassDef(XMLElement xe) {
    myClassDef = xe;
  }
  protected void loadClassDef() {
//    System.err.println("\n\nMy CLASS DEF: "+myClassDef);
        String constraintClass = myClassDef.getStringAttribute("constrainteditor","");
        if (constraintClass.equals("")) {
//          System.err.println("CONSTRAINTCLASS NULL inLAYOUT: "+getClass());
          return;
        }
//        System.err.println("CONSTRAINTCLASS NOT NULL!");
        Class constraintEditor;
        /** @todo Maybe check first... The classnotfound exception is not exceptional */
        try {
          constraintEditor = Class.forName(constraintClass);
        }
        catch (ClassNotFoundException ex) {
          // No problem.
//          System.err.println("Warning constrainteditor class: "+constraintClass);
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
        if (!TipiConstraintEditor.class.isInstance(myConstraintEditor)) {
          System.err.println("Warning: ConstraintEditor class: "+constraintEditor.getClass()+" does not implement the TipiConstraintEditor interface, which is: "+TipiConstraintEditor.class);
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