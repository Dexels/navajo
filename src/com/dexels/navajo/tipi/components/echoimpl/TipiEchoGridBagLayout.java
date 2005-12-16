package com.dexels.navajo.tipi.components.echoimpl;

import com.dexels.navajo.tipi.TipiValue;
import com.dexels.navajo.tipi.components.core.TipiLayoutImpl;
import com.dexels.navajo.tipi.components.echoimpl.impl.TipiEchoGridBagConstraints;

import nextapp.echo2.app.Grid;
import nextapp.echo2.app.layout.GridLayoutData;


/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author Frank Lyaruu
 * @version 1.0
 */

 public class TipiEchoGridBagLayout
 extends TipiLayoutImpl {
 public TipiEchoGridBagLayout() {
	 System.err.println("CREATED: "+getClass());
 }

 protected void setValue(String name, TipiValue tv) {
 /**@todo Implement this com.dexels.navajo.tipi.internal.TipiLayout abstract
 method*/
 }

 public void createLayout() throws com.dexels.navajo.tipi.TipiException {
 myLayout = new Grid(3);
 // EchoGridBagLayout p = (EchoGridBagLayout) myLayout;
 // System.err.println("EchoGridBagLayout created!!");
 // setLayout(p);
 }

 public Object parseConstraint(String text, int index) {
// return new EchoGridBagConstraints(text);
	 TipiEchoGridBagConstraints g = new TipiEchoGridBagConstraints();
	 g.parse(text);
	 return g;
 }

 }
