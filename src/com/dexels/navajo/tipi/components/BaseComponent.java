package com.dexels.navajo.tipi.components;

import nanoxml.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.impl.*;
import com.dexels.navajo.tipi.components.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class BaseComponent extends TipiComponent {
  BorderLayout borderLayout1 = new BorderLayout();

  public void addToContainer(Component c, Object constraints) {
    throw new UnsupportedOperationException("Can not add to container of class: "+getClass());
   }
   public void setContainerLayout(LayoutManager layout){
     throw new UnsupportedOperationException("Can not set layout of container of class: "+getClass());
   }

   public Container createContainer() {
     return new JPanel();
   }

  public BaseComponent() {
    setContainer(createContainer());
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }


  public void load(XMLElement elm, XMLElement instance, TipiContext context) throws TipiException {
    String type = (String)elm.getAttribute("type","label");
    if (type.equals("label")) {
      TipiLabel tl = new TipiLabel();
      tl.setText((String)elm.getAttribute("value",""));
      ((JPanel)getContainer()).add(tl.getContainer(), BorderLayout.CENTER);
      //((TipiPanel)getContainer()).tipiAdd(tl.getContainer(),BorderLayout.CENTER);
    }
    if (type.equals("image")) {
      System.err.println("----------> Image found");
      TipiImage ti = new TipiImage();
      ((JLabel)ti.getOuterContainer()).setBackground(Color.red);
      ti.setImage((String)elm.getAttribute("url",null));

     ((JPanel)getContainer()).add(ti.getContainer(), BorderLayout.CENTER);
      //((TipiPanel)getContainer()).tipiAdd(ti.getContainer(),BorderLayout.CENTER);
    }

    if (type.equals("hidden")) {
      TipiLabel tl = new TipiLabel();
      tl.setText("");
      ((JPanel)getContainer()).add(tl.getContainer(), BorderLayout.CENTER);
      //((TipiPanel)getContainer()).tipiAdd(tl.getContainer(),BorderLayout.CENTER);
    }
    if (type.equals("button")) {
      TipiButton tl = new TipiButton();
      tl.setText((String)elm.getAttribute("value",""));
      ((JPanel)getContainer()).add(tl.getContainer(), BorderLayout.CENTER);
      //((TipiPanel)getContainer()).tipiAdd(tl.getContainer(),BorderLayout.CENTER);
    }
  }

  private void jbInit() throws Exception {
    getContainer().setLayout(borderLayout1);
  }

}