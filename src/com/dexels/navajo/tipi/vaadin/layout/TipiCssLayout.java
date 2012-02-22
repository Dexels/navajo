package com.dexels.navajo.tipi.vaadin.layout;


import com.dexels.navajo.tipi.TipiValue;
import com.dexels.navajo.tipi.components.core.TipiLayoutImpl;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class TipiCssLayout
    extends TipiLayoutImpl {
	private static final long serialVersionUID = -6324500249415341344L;
	CssLayout layout = null;
  public TipiCssLayout() {
  }

  public void createLayout() {
	 layout =  new CssLayout();
	  setLayout(layout);
  }

  protected void setValue(String name, TipiValue tv) {
    throw new UnsupportedOperationException("Not implemented.");
  }
  
	public void addToLayout(Object component, Object constraints) {
		layout.addComponent((Component) component);
	}
}