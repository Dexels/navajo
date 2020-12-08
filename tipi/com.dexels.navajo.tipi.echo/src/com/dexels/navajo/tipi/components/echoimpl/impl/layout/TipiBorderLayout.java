/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.components.echoimpl.impl.layout;

import java.awt.BorderLayout;

import com.dexels.navajo.tipi.TipiValue;
import com.dexels.navajo.tipi.components.core.TipiLayoutImpl;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class TipiBorderLayout
    extends TipiLayoutImpl {
	private static final long serialVersionUID = -3861071282137448373L;
  private EchoBorderLayoutImpl echoBorderLayoutImpl;
 
  public TipiBorderLayout() {
  }

  public void createLayout() {
    echoBorderLayoutImpl = new EchoBorderLayoutImpl();
    echoBorderLayoutImpl.setParentComponent(myComponent);
    setLayout(echoBorderLayoutImpl);
  }

  public Object createDefaultConstraint(int index) {
    switch (index) {
      case 0:
        return BorderLayout.CENTER;
      case 1:
        return BorderLayout.NORTH;
      case 2:
        return BorderLayout.SOUTH;
      case 3:
        return BorderLayout.EAST;
      case 4:
        return BorderLayout.WEST;
      default:
        return null;
    }
  }



protected void setValue(String name, TipiValue tv) {
    throw new UnsupportedOperationException("Not implemented.");
  }

  public Object parseConstraint(String text, int index) {
	  return text;
	  
  }
  
  

  public void commitLayout() {
	  echoBorderLayoutImpl.commitToParent();
  }
}