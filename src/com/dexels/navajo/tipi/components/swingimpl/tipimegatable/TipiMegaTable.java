package com.dexels.navajo.tipi.components.swingimpl.tipimegatable;

import com.dexels.navajo.tipi.components.core.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import com.dexels.navajo.tipi.tipixml.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.swingclient.components.*;
import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.components.swingimpl.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class TipiMegaTable extends TipiSwingDataComponentImpl {
  public TipiMegaTable() {
  }
  private JPanel myPanel = null;
  private boolean useTabs = true;
  private String outerMessageName = null;
  private String innerMessageName = null;
  private String titlePropertyName = "Title";
  private boolean columnsButtonVisible = false;
  private boolean filtersVisible = false;
  private boolean useScrollBars = true;
  private boolean headerVisible = false;
  private int rowHeight = -1;

  private final Stack layers = new Stack();

  public Object createContainer() {
    /**@todo Implement this com.dexels.navajo.tipi.components.core.TipiComponentImpl abstract method*/
    myPanel = new JPanel();
    myPanel.setLayout(new BorderLayout());
    return myPanel;
  }

  public void load(XMLElement elm, XMLElement instance, TipiContext context) throws
      com.dexels.navajo.tipi.TipiException {
    super.load(elm, instance, context);
    loadLevels(elm);
  }

  public XMLElement store() {
    XMLElement xx = super.store();
    for (int i = layers.size()-1; i >= 0; i--) {
      TipiMegaTableLayer tmtl = (TipiMegaTableLayer)layers.get(i);
      XMLElement cc = tmtl.store();
      xx.addChild(cc);
    }
    return xx;
  }



  private void loadLevels(XMLElement elm) {
    Vector children = elm.getChildren();
   for (int i = children.size()-1; i >= 0; i--) {
     XMLElement child = (XMLElement) children.elementAt(i);
     if (child.getName().equals("layer")) {
       String type = child.getStringAttribute("type");
       TipiMegaTableLayer tmtl = null;
       if (type.equals("tab")) {
         tmtl = new TipiTabLayer(this);
       }
       if (type.equals("panel") || type.equals("scroll")) {
         tmtl = new TipiScrollLayer(this);
       }
       if (type.equals("table")) {
         tmtl = new TipiTableLayer(this);
       }
       if (type.equals("treetable")) {
         tmtl = new TipiTreeTableLayer(this);
       }
       if (tmtl!=null) {
         tmtl.loadLayer(child);
         layers.add(tmtl);
       }
     }
   }
  }



//
//  public XMLElement store() {
//    XMLElement xx = super.store();
//    for (int i = 0; i < columns.size(); i++) {
//      XMLElement columnDefinition = new CaseSensitiveXMLElement();
//      String cc = (String) columns.get(i);
//      columnDefinition.setName("column");
//      columnDefinition.setAttribute("name", cc);
//      columnDefinition.setIntAttribute("size",
//                                       ( (Integer) columnSize.get(i)).intValue());
//      xx.addChild(columnDefinition);
//    }
//    return xx;
//  }

  private void reload() {
    try {
      if (myNavajo != null) {
        loadData(getNavajo(), myContext);
      }
      else {
        System.err.println("Can not reload, no navajo!");
      }
    }
    catch (TipiException ex) {
      ex.printStackTrace();
    }
  }

  public Object getComponentValue(String name) {
    if (name.equals("columnsButtonVisible")) {
      return new Boolean(columnsButtonVisible);
    }
    if (name.equals("filtersVisible")) {
      return new Boolean(filtersVisible);
    }
    if (name.equals("useScrollBars")) {
      return new Boolean(useScrollBars);
    }
    if (name.equals("headerVisible")) {
      return new Boolean(headerVisible);
    }
    if (name.equals("useTabs")) {
      return new Boolean(useTabs);
    }
    if (name.equals("outerMessageName")) {
      return outerMessageName;
    }
    if (name.equals("innerMessageName")) {
      return innerMessageName;
    }
    if (name.equals("titlePropertyName")) {
      return titlePropertyName;
    }
    return super.getComponentValue(name);
  }

//  private boolean columnButtonsVisible = false;
//  private boolean filtersVisible = false;
//  private boolean useScrollBars = true;
//  private boolean headerVisible = false;
  public void setComponentValue(String name, Object object) {
    if (name.equals("columnButtonVisible")) {
      columnsButtonVisible = (Boolean.valueOf(object.toString()).booleanValue());
      reload();
    }
    if (name.equals("filtersVisible")) {
      filtersVisible = (Boolean.valueOf(object.toString()).booleanValue());
      reload();
    }
    if (name.equals("useScrollBars")) {
      useScrollBars = (Boolean.valueOf(object.toString()).booleanValue());
      reload();
    }
    if (name.equals("headerVisible")) {
      headerVisible = (Boolean.valueOf(object.toString()).booleanValue());
      reload();
    }
    if (name.equals("useTabs")) {
      useTabs = (Boolean.valueOf(object.toString()).booleanValue());
      reload();
    }
//    if (name.equals("outerMessageName")) {
//      System.err.println("Setting outerMessage to: " + object);
//      outerMessageName = (String) object;
//      reload();
//    }
//    if (name.equals("innerMessageName")) {
//      System.err.println("Setting innerMessage to: " + object);
//      innerMessageName = (String) object;
//      reload();
//    }
//    if (name.equals("titlePropertyName")) {
//      titlePropertyName = object.toString();
//      reload();
//    }
//    if (name.equals("rowHeight")) {
//      rowHeight = ( (Integer) object).intValue();
//      reload();
//      setColumnsVisible(Boolean.valueOf(object.toString()).booleanValue());
//    }
    super.setComponentValue(name, object);
  }


  public void loadData(final Navajo n, TipiContext context) throws
      TipiException {

    Stack currentLayers = (Stack)layers.clone();
    Message current = null;
    TipiMegaTableLayer tmtl = (TipiMegaTableLayer)currentLayers.pop();

    current = n.getMessage(tmtl.getMessagePath());
    tmtl.loadData(n,null,currentLayers,myPanel);

    super.loadData(n, context);
  }

}
