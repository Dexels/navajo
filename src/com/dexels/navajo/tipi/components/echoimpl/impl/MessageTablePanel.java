package com.dexels.navajo.tipi.components.echoimpl.impl;

import java.util.*;

import com.dexels.navajo.document.*;
import echopoint.*;
import echopoint.layout.*;
import echopoint.layout.GridLayoutManager.*;
import nextapp.echo.event.*;
import com.dexels.navajo.tipi.components.echoimpl.layout.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Dexels</p>
 * @author not attributable
 * @version 1.0
 */

public class MessageTablePanel extends echopoint.ScrollablePanel {
  private HtmlTemplatePanel myHtmlTemplatePanel = new HtmlTemplatePanel();
  private MessageTable myTable = new MessageTable();
  private PushButton nextButton, previousButton, loadMessage;
  private Label myLabel;
  private ArrayList myListeners = new ArrayList();

  public MessageTablePanel() {
    try {
      myHtmlTemplatePanel.setTemplate(getClass().getResource("table.html"));
      myHtmlTemplatePanel.setLoudErrorsUsed(true);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    initUI();
  }

  private void initUI() {
    nextButton = new PushButton("Next page");
    previousButton = new PushButton("Previous page");
    loadMessage = new PushButton("Get details..");
    myLabel = new Label();

    nextButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        myTable.nextPage();
        updateTablebar();
      }
    });
    previousButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        myTable.previousPage();
        updateTablebar();
      }
    });
    loadMessage.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < myListeners.size(); i++) {
          ActionListener l = (ActionListener) myListeners.get(i);
          l.actionPerformed(new ActionEvent(myTable, "onActionPerformed"));
        }
      }
    });

    this.setLayoutManager(new EchoBorderLayout());
    this.add(myHtmlTemplatePanel, new CellConstraints(0,0));
    myHtmlTemplatePanel.add(nextButton, "nextButton");
    myHtmlTemplatePanel.add(previousButton, "previousButton");
    myHtmlTemplatePanel.add(loadMessage, "loadButton");
    myHtmlTemplatePanel.add(myLabel, "label");
    myHtmlTemplatePanel.add(myTable, "table");
  }

  public void addActionListener(ActionListener l) {
    myListeners.add(l);
  }

  public void removeActionListener(ActionListener l) {
    myListeners.remove(l);
  }

  private final void updateTablebar() {
    int index = myTable.getCurrentPage() + 1;
    int count = myTable.getPageCount() + 1;
    myLabel.setText("page " + index + " of " + count);
  }

  public int getColumnCount() {
    return myTable.getColumnCount();
  }

  public void setMessage(Message m) {
    if(m.getArraySize() > 0){
      nextButton.setVisible(true);
      previousButton.setVisible(true);
      loadMessage.setVisible(true);
      myLabel.setVisible(true);
    }
    myTable.setMessage(m);
    updateTablebar();
  }

  public Message getSelectedMessage() {
    return myTable.getSelectedMessage();
  }

  public void addColumn(String id, String name, boolean editable) {
    myTable.addColumn(id, name, editable);
  }

  public void removeAllColumns() {
    myTable.removeAllColumns();
  }

}
