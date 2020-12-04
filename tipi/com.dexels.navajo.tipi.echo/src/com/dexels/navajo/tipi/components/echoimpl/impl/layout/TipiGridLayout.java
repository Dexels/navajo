/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.components.echoimpl.impl.layout;

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
public class TipiGridLayout
    extends TipiLayoutImpl {
	private static final long serialVersionUID = 6706479367509952989L;
  private EchoGridLayoutImpl echoGridLayoutImpl;
 
  public TipiGridLayout() {
  }

  public void createLayout() {
	  echoGridLayoutImpl = new EchoGridLayoutImpl();
	  echoGridLayoutImpl.setParentComponent(myComponent);
    setLayout(echoGridLayoutImpl);
  }



protected void setValue(String name, TipiValue tv) {
    throw new UnsupportedOperationException("Not implemented.");
  }

  public Object parseConstraint(String text, int index) {
  
	  TipiEchoGridBagConstraints t =  new TipiEchoGridBagConstraints();
	  t.parse(text, index);
	  return t;
  }
  
  

  public void commitLayout() {
	  echoGridLayoutImpl.commitToParent();
  }
}