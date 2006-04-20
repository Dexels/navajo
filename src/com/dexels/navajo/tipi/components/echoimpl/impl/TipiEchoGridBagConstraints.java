package com.dexels.navajo.tipi.components.echoimpl.impl;

import java.util.*;

import nextapp.echo2.app.Alignment;
import nextapp.echo2.app.Insets;
import nextapp.echo2.app.layout.GridLayoutData;

import com.dexels.navajo.tipi.tipixml.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 * @deprecated Will never really work well. Use the gridpanel.
 */
public class TipiEchoGridBagConstraints
    extends GridLayoutData {
	
	 public static final int RELATIVE = -1;
	  public static final int REMAINDER = 0;
	  public static final int NONE = 0;
	  public static final int BOTH = 1;
	  public static final int HORIZONTAL = 2;
	  public static final int VERTICAL = 3;
	  public static final int CENTER = 10;
	  public static final int NORTH = 11;
	  public static final int NORTHEAST = 12;
	  public static final int EAST = 13;
	  public static final int SOUTHEAST = 14;
	  public static final int SOUTH = 15;
	  public static final int SOUTHWEST = 16;
	  public static final int WEST = 17;
	  public static final int NORTHWEST = 18;
	  public static final int PAGE_START = 19;
	  public static final int PAGE_END = 20;
	  public static final int LINE_START = 21;
	  public static final int LINE_END = 22;
	  public static final int FIRST_LINE_START = 23;
	  public static final int FIRST_LINE_END = 24;
	  public static final int LAST_LINE_START = 25;
	  public static final int LAST_LINE_END = 26;

  public TipiEchoGridBagConstraints() {
  }

  public static GridLayoutData parse(String cs) {
//    String cs = elm.getStringAttribute("gridbag");
    StringTokenizer tok = new StringTokenizer(cs, ", ");
    String[] myConstraints = new String[14];    
    int tokenCount = tok.countTokens();
//    if (tokenCount != 4) {
//      throw new RuntimeException("Gridbag for: " + cs + " is invalid!");
//    }
//    else {
      for (int i = 0; i < tokenCount; i++) {
//        int con = new Integer(tok.nextToken()).intValue();
    	  myConstraints[i] = tok.nextToken();
      }
      int gridx = Integer.parseInt(myConstraints[0]);
      int gridy = Integer.parseInt(myConstraints[1]);
      int gridwidth = Integer.parseInt(myConstraints[2]);
      int gridheight = Integer.parseInt(myConstraints[3]);
      double weightx = Double.parseDouble(myConstraints[4]);
      double weighty = Double.parseDouble(myConstraints[5]);
	  int anchor = Integer.parseInt(myConstraints[6]);
      int fill = Integer.parseInt(myConstraints[7]);

      
      Insets insets = new Insets(Integer.parseInt(myConstraints[8]), Integer.parseInt(myConstraints[9]), Integer.parseInt(myConstraints[10]), Integer.parseInt(myConstraints[11]));
//      ipadx = Integer.parseInt(myConstraints[12]);
//      ipady = Integer.parseInt(myConstraints[13]);
      GridLayoutData gdl = new GridLayoutData();
      gdl.setAlignment(new Alignment(Alignment.LEADING,Alignment.CENTER));
      gdl.setColumnSpan(gridwidth);
      gdl.setRowSpan(gridheight);
      gdl.setInsets(insets);
      return gdl;
//    }
  }

  public TipiEchoGridBagConstraints(String s) {
    parse(s);
  }

  public static void main(String[] args) {
    XMLElement bert = new CaseSensitiveXMLElement();
    bert.setName("Bert");
    bert.setAttribute("name", "bert_een");
    bert.setAttribute("gridbag", "1, 1, 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0");
    TipiEchoGridBagConstraints bertje = new TipiEchoGridBagConstraints("1, 1, 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0");
//    System.err.println("You made: " + bert.toString());
  }

//  public String toString() {
//    return "" + gridx + "," + gridy + "," + gridwidth + "," + gridheight + "," + weightx + "," + weighty + "," + anchor + "," + fill + "," + insets.top + "," + insets.left + "," + insets.bottom + "," + insets.right + "," + ipadx + "," + ipady;
//  }
}
