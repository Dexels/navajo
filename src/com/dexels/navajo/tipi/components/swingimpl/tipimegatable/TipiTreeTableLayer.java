package com.dexels.navajo.tipi.components.swingimpl.tipimegatable;

import java.awt.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.swingclient.components.treetable.*;
import com.dexels.navajo.tipi.tipixml.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class TipiTreeTableLayer extends TipiTableBaseLayer {
  private final List<String> columns = new ArrayList<String>();
  private final List<Integer> columnSize = new ArrayList<Integer>();

  private boolean columnsButtonVisible = false;
  private boolean filtersVisible = false;
  private boolean useScrollBars = true;
  private boolean headerVisible = true;


  public TipiTreeTableLayer(TipiMegaTable tmt) {
    super(tmt);
  }
  public void loadData(Navajo n, Message current, Stack<TipiTableBaseLayer> layerStack, JComponent currentPanel) {
    MessageTreeTablePanel mttp = new MessageTreeTablePanel();
    Message m = n.getMessage(getMessagePath());
    if (m.getArraySize() > 0) {
      Message first = m.getMessage(0);
      for (int j = 0; j < columns.size(); j++) {
        String column = columns.get(j);
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

    List<XMLElement> children = elt.getChildren();
    for (int i = 0; i < children.size(); i++) {
      XMLElement child = children.get(i);
      loadColumn(child);
    }
  }

  private final void loadColumn(XMLElement child) {
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
      xxx.setAttribute("name",columns.get(i));
      xxx.setIntAttribute("size",columnSize.get(i));
      newElt.addChild(xxx);
    }
    return newElt;
  }
  public int getCurrentSelection() {
      return -1;
  }

  public void setCurrentSelection(int s) {
  // ignore. Don't even know if this class works.  
  }
}
