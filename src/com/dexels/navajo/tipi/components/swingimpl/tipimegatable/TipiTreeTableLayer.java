package com.dexels.navajo.tipi.components.swingimpl.tipimegatable;

import com.dexels.navajo.tipi.tipixml.*;
import com.dexels.navajo.document.*;
import java.util.*;
import javax.swing.*;
import com.dexels.navajo.swingclient.components.treetable.*;
import java.awt.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class TipiTreeTableLayer extends TipiMegaTableLayer {
  private final ArrayList columns = new ArrayList();
  private final ArrayList columnSize = new ArrayList();

  private boolean columnsButtonVisible = false;
  private boolean filtersVisible = false;
  private boolean useScrollBars = true;
  private boolean headerVisible = true;
  private int rowHeight = 15;


  public TipiTreeTableLayer(TipiMegaTable tmt) {
    super(tmt);
  }
  public void loadData(Navajo n, Message current, Stack layerStack, JComponent currentPanel) {
    MessageTreeTablePanel mttp = new MessageTreeTablePanel();
    Message m = n.getMessage(getMessagePath());
    if (m.getArraySize() > 0) {
      Message first = m.getMessage(0);
      for (int j = 0; j < columns.size(); j++) {
        String column = (String) columns.get(j);
        Property p = first.getProperty(column);
        if (p != null) {
          mttp.addColumn(p.getName(), p.getDescription(), p.isDirIn());
        }
      }
    }

    mttp.setMessage(m);
    currentPanel.add(mttp,BorderLayout.CENTER);
  }

  public void loadLayer(XMLElement elt) {
    super.loadLayer(elt);
    columns.clear();
    columnSize.clear();

    columnsButtonVisible = elt.getBooleanAttribute("columnsButtonVisible","true","false",false);
    filtersVisible = elt.getBooleanAttribute("filtersVisible","true","false",false);
    useScrollBars = elt.getBooleanAttribute("useScrollBars","true","false",true);
    headerVisible = elt.getBooleanAttribute("headerVisible","true","false",true);

    Vector children = elt.getChildren();
    for (int i = 0; i < children.size(); i++) {
      XMLElement child = (XMLElement) children.elementAt(i);
      loadColumn(child);
    }
  }

  private void loadColumn(XMLElement child) {
    String name = (String) child.getAttribute("name");
    columns.add(name);
    int size = child.getIntAttribute("size", -1);
    columnSize.add(new Integer(size));
  }
  public XMLElement store() {

    XMLElement newElt = super.store();
    newElt.setAttribute("type","treetable");
    newElt.setAttribute("columnsButtonVisible",columnsButtonVisible?"true":"false");
     newElt.setAttribute("filtersVisible",filtersVisible?"true":"false");
     newElt.setAttribute("useScrollBars",useScrollBars?"true":"false");
     newElt.setAttribute("headerVisible",headerVisible?"true":"false");


    for (int i = 0; i < columns.size(); i++) {
      XMLElement xxx = new CaseSensitiveXMLElement();
      xxx.setName("column");
      xxx.setAttribute("name",((String)columns.get(i)));
      xxx.setIntAttribute("size",((Integer)columnSize.get(i)).intValue());
      newElt.addChild(xxx);
    }
    return newElt;
  }

}
