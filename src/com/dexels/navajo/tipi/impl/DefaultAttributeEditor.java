package com.dexels.navajo.tipi.impl;

import com.dexels.navajo.tipi.components.SwingTipiComponent;
import java.awt.Container;
import com.dexels.navajo.tipi.*;
import javax.swing.*;
import com.dexels.navajo.tipi.components.*;
import java.awt.*;
import com.dexels.navajo.tipi.studio.ComponentSelectionListener;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class DefaultAttributeEditor extends SwingTipiComponent implements ComponentSelectionListener {
  private JTable attributeTable = null;
  private TipiAttributeTableModel myModel = null;
  private TipiAttributeTableRenderer myRenderer = new TipiAttributeTableRenderer();
  private  TipiAttributeTableEditor myEditor = new TipiAttributeTableEditor();
  public DefaultAttributeEditor() {
  }
  public Container createContainer() {
    attributeTable = new JTable();
    myModel = new TipiAttributeTableModel();
    attributeTable.setModel(myModel);
    attributeTable.setDefaultRenderer(Object.class,myRenderer);
    attributeTable.setDefaultEditor(Object.class, myEditor);
    attributeTable.setCellSelectionEnabled(true);
    JScrollPane scroll = new JScrollPane();
    scroll.getViewport().add(attributeTable);
    /**@todo Implement this com.dexels.navajo.tipi.TipiBase abstract method*/
    TipiContext.getInstance().addComponentSelectionListener(this);
    return scroll;
  }

  public void setSelectedComponent(TipiComponent tc) {
    myRenderer.setSelectedComponent(tc);
    myEditor.setSelectedComponent(tc);
    myModel.setAttributes(tc);
    myModel.fireTableStructureChanged();
  }

}