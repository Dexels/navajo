package com.dexels.navajo.tipi.impl;

import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.*;
import nanoxml.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import com.dexels.navajo.document.*;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class DefaultTipiContainer extends TipiComponent implements TipiContainer{

//  private TipiPanel myPanel = new TipiPanel();
  protected ArrayList containerList = new ArrayList();
  protected String prefix;
  protected String myName;
  protected Map containerMap = new HashMap();
  protected TipiPopupMenu myPopupMenu = null;

  public DefaultTipiContainer() {
//    myPanel.setBackground(Color.blue);
 //   setPreferredSize(new Dimension(100,50));
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }
  public void parseTable(TipiContext context, Tipi tipiParent, XMLElement table) throws TipiException {

    TipiComponent comp = this;
    System.err.println("Parsing type: "+comp.getClass());
    TipiTableLayout layout = new TipiTableLayout();
//    Container con = (Container) comp;
    Container con = comp.getContainer();
    /** @todo REPLACE THIS DIRTY CONSTRUCTION */
    if (JInternalFrame.class.isInstance(con)) {
      ((JInternalFrame)con).getContentPane().setLayout(layout);
    } else {
      con.setLayout(layout);
    }
    Map columnAttributes = new HashMap();
    Vector rows = table.getChildren();
    /** @todo ANOTHER UGLY CONSTRuCTION */
    for (int r = 0; r < rows.size(); r++) {
      XMLElement row = (XMLElement) rows.elementAt(r);
      TipiTableLayout l;
      if (JInternalFrame.class.isInstance(con)) {
        l = (TipiTableLayout)((JInternalFrame)con).getContentPane().getLayout();
      } else {
        l = (TipiTableLayout) con.getLayout();
      }
      l.startRow();
      Vector columns = row.getChildren();
      for (int c = 0; c < columns.size(); c++) {
        XMLElement column = (XMLElement) columns.elementAt(c);
        Enumeration attributes = column.enumerateAttributeNames();
        while (attributes.hasMoreElements()) {
          String attrName = (String) attributes.nextElement();
          columnAttributes.put(attrName, column.getStringAttribute(attrName));
        }
        l.startColumn();
        if (column.countChildren() > 1 || column.countChildren() == 0) {
          throw new TipiException(
              "More then one, or no children found inside <td>");
        }
        else {
          XMLElement component = (XMLElement) column.getChildren().elementAt(0);
          String componentName = component.getName();
          String cname = (String)component.getAttribute("name");
          if (componentName.equals("tipi-instance")) {
            String type = (String)component.getAttribute("type");
            Tipi s;
            if (type!=null && "tipitable".equals(type)) {
              s = context.instantiateTipiTable(cname);
            } else {
              s = context.instantiateTipi(component);
            }
//            currentTipi = s;
              ( (Tipi) comp).addTipi(s, context, columnAttributes);
              ( (Tipi) comp).addComponent(s,context,columnAttributes);
          }
            else {
              throw new RuntimeException("Que?");
            }
          if (componentName.equals("container-instance")) {
            TipiContainer cn = context.instantiateTipiContainer(tipiParent, component);
            if (Tipi.class.isInstance(comp)) {
              ( (Tipi) comp).addTipiContainer(cn,context, columnAttributes);
            }
            else
            if (TipiContainer.class.isInstance(comp)) {
              ( (TipiContainer) comp).addTipiContainer(cn, context,
                  columnAttributes);
            }
            else {
              throw new RuntimeException("Que?");
            }
         }
          if (componentName.equals("property")) {
            BasePropertyComponent pc = new BasePropertyComponent();
            String propertyName = (String) component.getAttribute("name");
            TipiContainer tc = (TipiContainer) comp;
            pc.load(component, context);
            tc.addProperty(propertyName, pc, context, columnAttributes);
          }
          if (componentName.equals("component")) {
            BaseComponent pc = new BaseComponent();
//            String propertyName = (String) component.getAttribute("name");
            TipiContainer tc = (TipiContainer) comp;
            pc.load(component, context);
            tc.addComponent(pc, context, columnAttributes);
          }
          if (componentName.equals("method")) {
            MethodComponent pc = new DefaultMethodComponent();
            pc.load(component, comp, context);
            comp.addComponent(pc, context, columnAttributes);
          }
          if (componentName.equals("button-instance")) {
            String buttonName = (String) component.getAttribute("name");
            TipiButton pc = context.instantiateTipiButton(buttonName,tipiParent);
            pc.load(component, context);
            comp.addComponent(pc, context, columnAttributes);
          }
        }
        columnAttributes.clear();
        l.endColumn();
      }
      l.endRow();
    }
  }

  public void load(XMLElement elm, TipiContext context) throws com.dexels.navajo.tipi.TipiException {
    TipiPanel myPanel = new TipiPanel();
    setContainer(myPanel);
    prefix = (String) elm.getAttribute("prefix");
    myName = (String) elm.getAttribute("name");
    String popup = (String) elm.getAttribute("popup");
    if (popup!=null) {
//      myPopupMenu = new TipiPopupMenu();
      myPopupMenu = context.instantiateTipiPopupMenu(popup);
      getContainer().addMouseListener(new MouseAdapter() {
        public void mousePressed(MouseEvent e) {
          if (e.isPopupTrigger()) {
            showPopup(e);
          }
        }
        public void mouseReleased(MouseEvent e) {
          if (e.isPopupTrigger()) {
            showPopup(e);
          }
        }
      });

    }
  }

  public void showPopup(MouseEvent e) {
    myPopupMenu.show(getContainer(),e.getX(),e.getY());
  }

  public String getName() {
    return myName;
  }

  public void addTipiContainer(TipiContainer t, TipiContext context, Map td) {
      containerList.add(t);
      containerMap.put(t.getName(),t);
      addComponent(t, context, td);
  }

  public TipiContainer getTipiContainer(String s) {
    return (TipiContainer)containerMap.get(s);
  }

  public TipiContainer getTipiContainer(int t) {
    return (TipiContainer)containerList.get(t);
  }

  public int getTipiContainerCount() {
    return containerMap.size();
  }

  public void loadData(Navajo n, TipiContext context) {
     for (int i = 0; i < containerList.size(); i++) {
      TipiContainer current = (TipiContainer)containerList.get(i);
      current.loadData(n,context);
    }
    for (int i = 0; i < properties.size(); i++) {
      BasePropertyComponent current = (BasePropertyComponent)properties.get(i);
      Property p;
      if(prefix != null){
        p = n.getRootMessage().getPropertyByPath(prefix + "/" + (String)propertyNames.get(i));
      }else{
        p = n.getRootMessage().getPropertyByPath((String)propertyNames.get(i));
      }
      current.setProperty(p);
    }
  }
  private void jbInit() throws Exception {
//    getContainer().setBorder(BorderFactory.createEtchedBorder());
  }
  public TipiContainer getContainerByPath(String path) {
    int s = path.indexOf("/");
    if (s==-1) {
      throw new RuntimeException("Can not retrieve container from screen!");
    }
    if (s==0) {
      return getContainerByPath(path.substring(1));
    }

    String name = path.substring(0,s);
    String rest = path.substring(s);
    System.err.println("Name: "+name);
    System.err.println("Rest: "+rest);
    TipiContainer t = getTipiContainer(name);
    if (t==null) {
      return null;
    }
    /** @todo Add support for nested tipis */
    return t.getContainerByPath(rest);
  }

}