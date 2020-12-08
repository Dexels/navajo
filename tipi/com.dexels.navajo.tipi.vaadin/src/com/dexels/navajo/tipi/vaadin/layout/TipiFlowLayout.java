/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.vaadin.layout;


import com.dexels.navajo.tipi.TipiValue;
import com.dexels.navajo.tipi.components.core.TipiLayoutImpl;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class TipiFlowLayout
    extends TipiLayoutImpl {
	private static final long serialVersionUID = 863989359860579158L;
	HorizontalLayout layout = null;
  public TipiFlowLayout() {
  }

  @Override
public void createLayout() {
	 layout =  new HorizontalLayout();
	  setLayout(layout);
  }

  @Override
protected void setValue(String name, TipiValue tv) {
    throw new UnsupportedOperationException("Not implemented.");
  }
  
	@Override
	public void addToLayout(Object component, Object constraints) {
		layout.addComponent((Component) component);
	}
}