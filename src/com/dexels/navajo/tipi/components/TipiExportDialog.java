package com.dexels.navajo.tipi.components;

import com.dexels.navajo.tipi.impl.*;
import java.awt.*;
import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.*;
import javax.swing.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class TipiExportDialog extends DefaultTipiDialog{
  private JDialog d = new JDialog();

  public TipiExportDialog() {
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }



  private void jbInit() throws Exception {
    JPanel container = new JPanel();
    d.getContentPane().setLayout(new BorderLayout());
    d.getContentPane().add(container, BorderLayout.CENTER);
    container.setLayout(new CardLayout());
    TipiExportSortingPanel sp = new TipiExportSortingPanel();
    container.add(sp, "Sort");
    container.add(new TipiExportFilterPanel(), "Filter");
    container.add(new TipiExportSeparatorPanel(), "Separator");
    d.setSize(new Dimension(500, 400));
    CardLayout c = (CardLayout)container.getLayout();
    c.first(container);
  }

  public void setContainerLayout(LayoutManager m){
  }

  public void loadData(Navajo n, TipiContext tc) throws com.dexels.navajo.tipi.TipiException {
    // Jo
  }



  public Container getContainer(){
    if(d == null){
     return createContainer();
   }else{
     return d;
   }
  }

  public Container createContainer() {
   return d;
  }
}