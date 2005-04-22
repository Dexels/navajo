package com.dexels.navajo.tipi.components.swingimpl.layout;

import com.dexels.navajo.tipi.internal.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.core.*;
import com.dexels.navajo.tipi.components.swingimpl.swing.*;

import javax.swing.*;
import java.awt.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class TipiHorizontalBoxLayout extends TipiLayoutImpl {
  public TipiHorizontalBoxLayout() {
  }
  protected void setValue(String name, TipiValue tv) {
     /**
      * Not necessaru
      */
   }
  protected Object parseConstraint(String text,int index) {
      return createDefaultConstraint(index);
   }
   public void createLayout() throws com.dexels.navajo.tipi.TipiException {
//     Container c = (Container)super.myComponent.getContainer();
//     BoxLayout layout = new BoxLayout(c,BoxLayout.X_AXIS);
//  setLayout(layout);
  setLayout(new GridBagLayout());

     /**@todo Implement this com.dexels.navajo.tipi.internal.TipiLayout abstract method*/
   }
   public Object createDefaultConstraint(int index) {
       return new TipiSwingGridBagConstraints(index, 0, 1, 1, 1, 1, GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0);
     }

}
