package com.dexels.navajo.tipi.components.swingimpl;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class TipiPrintPreview extends JPanel {
  BorderLayout borderLayout1 = new BorderLayout();
  JToolBar slideToolbar = new JToolBar();
  JScrollPane imageScroller = new JScrollPane();
  JLabel imageLabel = new JLabel();

  private TipiSwingDataComponentImpl myComponent;
  private int pageCount = 0;
  private int currentPage = 0;

  private String myHeader = null;
  private String myFooter = null;
  JButton nextButton = new JButton();
  JButton previousButton = new JButton();
  JLabel pageLabel = new JLabel();


  public TipiPrintPreview(String header, String footer) {

    myHeader = header;
    myFooter = footer;
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  public void setSwingDataComponent(TipiSwingDataComponentImpl tsdci) {
    myComponent = tsdci;
    updateImage(0);
   }

  private void updateImage(int page) {
    ImageIcon ii = myComponent.getPreview(myHeader,myFooter,page);
    imageLabel.setIcon(ii);
    pageCount = myComponent.getNumberOfPages();

    nextButton.setEnabled(currentPage<pageCount-1);
    previousButton.setEnabled(currentPage>0);

    pageLabel.setText("Pagina "+(page+1)+"/"+pageCount);
//    pageSlider.setMaximum(pageCount);

  }

  private void jbInit() throws Exception {
    this.setLayout(borderLayout1);
    previousButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("com/dexels/navajo/tipi/components/swingimpl/swing/arrow_left.gif")));
    previousButton.setText("");
    previousButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        previousButton_actionPerformed(e);
      }
    });
    nextButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("com/dexels/navajo/tipi/components/swingimpl/swing/arrow_right.gif")));
    nextButton.setText("");
    nextButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        nextButton_actionPerformed(e);
      }
    });

    slideToolbar.setFloatable(false);
    pageLabel.setText("jLabel1");
    this.add(slideToolbar,  BorderLayout.SOUTH);
    slideToolbar.add(previousButton, null);
    slideToolbar.add(nextButton, null);
    slideToolbar.add(pageLabel, null);
    this.add(imageScroller, BorderLayout.CENTER);
    imageScroller.getViewport().add(imageLabel, null);
  }

  void nextButton_actionPerformed(ActionEvent e) {
    currentPage++;
    updateImage(currentPage);
  }

  void previousButton_actionPerformed(ActionEvent e) {
    currentPage--;
    updateImage(currentPage);
  }



}
