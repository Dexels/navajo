package com.dexels.navajo.tipi.components.echoimpl.impl.layout;

import java.util.*;

import nextapp.echo2.app.*;
import nextapp.echo2.app.layout.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class TipiEchoGridBagConstraints
    extends GridLayoutData {
  private String[] myConstraints = new String[14];
private int gridx;
private int gridy;
  public TipiEchoGridBagConstraints() {
	  super();
  }

  public TipiEchoGridBagConstraints(int x, int y, int w, int h, double wx, double wy, int anchor, int fill, Insets insets, int padx, int pady) {
//    super(x, y, w, h, wx, wy, anchor, fill, insets, padx, pady);
  }

  public void parse(String cs, int index) {
//    String cs = elm.getStringAttribute("gridbag");
    StringTokenizer tok = new StringTokenizer(cs, ", ");
    int tokenCount = tok.countTokens();
    if (tokenCount != 14) {
      throw new RuntimeException("Gridbag for: " + cs + " is invalid!");
    }
    else {
      for (int i = 0; i < 14; i++) {
//        int con = new Integer(tok.nextToken()).intValue();
        myConstraints[i] = tok.nextToken();
      }
    
      gridx = Integer.parseInt(myConstraints[0]);
	gridy = Integer.parseInt(myConstraints[1]);
	int gridwidth = Integer.parseInt(myConstraints[2]);
      int gridheight = Integer.parseInt(myConstraints[3]);
      double weightx = Double.parseDouble(myConstraints[4]);
      double weighty = Double.parseDouble(myConstraints[5]);
      int anchor = Integer.parseInt(myConstraints[6]);
      int fill = Integer.parseInt(myConstraints[7]);
      Insets insets = new Insets(Integer.parseInt(myConstraints[8]), Integer.parseInt(myConstraints[9]), Integer.parseInt(myConstraints[10]), Integer.parseInt(myConstraints[11]));
      int ipadx = Integer.parseInt(myConstraints[12]);
      int ipady = Integer.parseInt(myConstraints[13]);
      
      
      setColumnSpan(gridwidth);
      setRowSpan(gridheight);
      if(gridheight>1) {
          System.err.println("ROW/COLUMNSPAN: "+gridwidth+" / "+gridheight);
      }
//      if(weightx>0 && child instanceof Sizeable) {
//    	  Sizeable s = (Sizeable)child;
//    	  s.setWidth(new Extent(90,Extent.PERCENT));
//      }
//      
    }
  }

  public String toString() {
	  return "x:"+gridx+"y:"+gridy+"w:"+getColumnSpan()+"h:"+getRowSpan();
  }
  public TipiEchoGridBagConstraints(String s, Component parent, Component child, int index) {
    parse(s,index);
  }

public int getColumn() {
	return gridx;
}


}
