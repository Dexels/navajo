package com.dexels.navajo.tipi;
import nanoxml.*;
import com.dexels.navajo.tipi.components.*;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class BaseTipiComponent {

//  private TipiContainer myParent = null;
  private TipiButton myButton = new TipiButton();
  private TipiLabel myLabel = new TipiLabel();

  public BaseTipiComponent() {
  }



  public void load(XMLElement x, XMLElement instance, TipiContext context) throws TipiException {
    String type = (String)x.getAttribute("type");
    if ("button".equals(type)) {
      myButton.load(x,instance,context);

//      myParent.getContainer().addComponent(myButton);
    }
    if ("label".equals(type)) {
//      myLabel.fromXml(x,myParent);
//      myParent.getContainer().addComponent(myLabel);
    }

  }
}