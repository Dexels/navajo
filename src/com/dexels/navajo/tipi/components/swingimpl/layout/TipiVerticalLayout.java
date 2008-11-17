package com.dexels.navajo.tipi.components.swingimpl.layout;

import java.awt.*;

import javax.swing.*;

import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.core.*;
import com.dexels.navajo.tipi.components.swingimpl.swing.*;
import com.dexels.navajo.tipi.tipixml.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class TipiVerticalLayout
    extends TipiLayoutImpl {
@Override

//  GridBagLayout layout = null;
  protected void setValue(String name, TipiValue tv) {
    /**@todo Implement this com.dexels.navajo.tipi.TipiLayout abstract method*/
  }

  protected Object parseConstraint(String text,int index) {
//    TipiSwingGridBagConstraints gt = new TipiSwingGridBagConstraints(text);
    return createDefaultConstraint(index);
  }

  public void createLayout() {
    GridBagLayout lay = new GridBagLayout();
    setLayout(lay);
    
  }
  
	public void loadLayout(XMLElement def, TipiComponent t) throws TipiException {
		super.loadLayout(def, t);
		Container c = ((Container)t.getContainer());
		c.add(new JLabel(""),new TipiSwingGridBagConstraints(0, 999, 1, 1, 0, 1, GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));

	}

  
  
  public Object createDefaultConstraint(int index) {

    return new TipiSwingGridBagConstraints(0, index, 1, 1, 0, 0, GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0);
  }
}
