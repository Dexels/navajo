package com.dexels.navajo.tipi.components.swingimpl.tipimegatable;

import com.dexels.navajo.tipi.tipixml.*;
import com.dexels.navajo.document.*;
import javax.swing.*;
import java.util.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public abstract class TipiMegaTableLayer {

  protected final TipiMegaTable myTable;
  protected String titleProperty = null;
  protected String messagePath = null;

  public TipiMegaTableLayer(TipiMegaTable tmt) {
    myTable = tmt;
  }
  public String toString() {
     return "Class: "+getClass().getName()+" Title: "+titleProperty+" msgpath: "+messagePath;
   }


  public void loadLayer(XMLElement elt) {
    titleProperty= elt.getStringAttribute("title");
    messagePath= elt.getStringAttribute("messagePath");
  }

  public void loadData(Navajo n, Message current,Stack layerStack, JComponent currentPanel) {
  }

  public XMLElement store() {
    XMLElement xx = new CaseSensitiveXMLElement();
    xx.setName("layer");
    xx.setAttribute("messagePath",messagePath);
    xx.setAttribute("title",titleProperty);
    return xx;
  }




  public String getMessagePath() {
    return messagePath;
  }

}
