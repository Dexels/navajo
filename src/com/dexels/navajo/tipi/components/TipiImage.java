package com.dexels.navajo.tipi.components;

import com.dexels.navajo.tipi.*;
import nanoxml.*;
import java.awt.*;
import javax.swing.*;
import java.net.URL;
import java.util.*;
import tipi.*;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class TipiImage extends TipiLabel {

  public TipiImage() {
    setContainer(createContainer());
  }


  public void load(XMLElement e, XMLElement instance, TipiContext tc) {
    super.load(e, instance, tc);
    String url = (String) e.getAttribute("url", null);
    setImage(url);
  }

  public void setImage(String img) {
    System.err.println("----------> Setting image: " + img);
    if(img != null){
      ImageIcon i;
      try{
        URL iu = new URL(img);
        i = new ImageIcon(iu);
      }catch(Exception e){
        i = new ImageIcon(MainApplication.class.getResource(img));
      }
      if(i != null){
        System.err.println("----------> Setting icon!");
        ( (JLabel) getContainer()).setIcon(i);
      }
    }

  }


}