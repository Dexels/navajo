package com.dexels.navajo.tipi.mig;

import java.awt.*;

import javax.swing.*;

import net.miginfocom.swing.MigLayout;

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
public class TipiMigLayout
    extends TipiLayoutImpl {
	private static final long serialVersionUID = 7200147765607642561L;

public TipiMigLayout() {
  }

  @Override
public void createLayout() {
    String grid = myDefinition.getStringAttribute("grid");
//    logger.info("Creating grid: >"+grid+"<");
    MigLayout layout = new MigLayout(grid);
    
    setLayout(layout);
  }

  @Override
public Object parseConstraint(String text, int index) {
//	  logger.info("parsing constraint: "+text);
    return text;
  }
  
  

@Override
protected void setValue(String name, TipiValue tv) {
    throw new UnsupportedOperationException("Not implemented yet. But I should.");
  }

public static void main(String[] args) {
	JFrame g = new JFrame("Aap");
	Container c = g.getContentPane();
	c.setBackground(Color.red);
	c.setLayout(new MigLayout("wrap 3"));
	c.add(createSomething("comp1"));
	c.add(createSomething("comp2"),"span 2 2, growx, growy");
	c.add(createSomething("comp3"),"wrap");
	c.add(createSomething("comp4"));
	c.add(createSomething("comp5"));
	c.add(createSomething("comp6"));
	c.add(createSomething("comp7"));
	g.setSize(400,300);
	g.setVisible(true);
}

public static Component createSomething(String text) {
	JTextArea j = new JTextArea(text);
	j.setBorder(BorderFactory.createTitledBorder(text));
	return j;
}
}
