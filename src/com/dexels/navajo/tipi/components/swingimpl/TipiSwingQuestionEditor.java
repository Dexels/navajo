package com.dexels.navajo.tipi.components.swingimpl;

import com.dexels.navajo.document.*;
import com.dexels.navajo.swingclient.components.*;
import javax.swing.*;
import java.awt.*;
import com.dexels.navajo.tipi.components.swingimpl.swing.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class TipiSwingQuestionEditor extends JPanel {
  BorderLayout borderLayout1 = new BorderLayout();
  JPanel buttonBar = new JPanel();
  JPanel jPanel2 = new JPanel();
  JButton saveButton = new JButton();
  public TipiSwingQuestionEditor() {
    MessageTreePanel mtp;
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }
  public void loadData(Navajo n)  {
//    Message group = n.getMessage("Group");
//    for (int i = 0; i < group.getArraySize(); i++) {
//      Message m = group.getMessage(i);
//      TipiSwingGroupPanel tsgp = new TipiSwingGroupPanel(m);
//      groupTabs.add(tsgp,tsgp.getGroupName());
//    }
    Message list = n.getMessage("QuestionList");
      TipiSwingGroupPanel tsgp = new TipiSwingGroupPanel(list);
      add(tsgp,BorderLayout.CENTER);
  }
  private void jbInit() throws Exception {
    this.setLayout(borderLayout1);
    saveButton.setText("Save");
    buttonBar.setAlignmentX((float) 1.0);
    this.add(buttonBar, BorderLayout.SOUTH);
    this.add(jPanel2, BorderLayout.NORTH);
    buttonBar.add(saveButton, null);
  }

}
