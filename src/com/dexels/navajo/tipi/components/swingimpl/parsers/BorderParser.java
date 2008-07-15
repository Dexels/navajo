package com.dexels.navajo.tipi.components.swingimpl.parsers;

import java.awt.*;
import java.util.*;

import javax.swing.*;
import javax.swing.border.*;

import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.internal.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class BorderParser
    extends TipiTypeParser {
  public Object parse(TipiComponent source, String expression,TipiEvent event) {
    return parseBorder(expression);
  }

  private Border parseBorder(String s) {
    StringTokenizer st = new StringTokenizer(s, "-");
    String borderName = st.nextToken();
    if ("etched".equals(borderName)) {
      return BorderFactory.createEtchedBorder();
    }
    if ("raised".equals(borderName)) {
      return BorderFactory.createRaisedBevelBorder();
    }
    if ("lowered".equals(borderName)) {
      return BorderFactory.createLoweredBevelBorder();
    }
    if ("titled".equals(borderName)) {
      String title = st.nextToken();
      return BorderFactory.createTitledBorder(title);
    }
    if ("loweredtitled".equals(borderName)) {
        String title = st.nextToken();
        return BorderFactory.createTitledBorder(BorderFactory.createLoweredBevelBorder(),title);
      }
    if ("raisedtitled".equals(borderName)) {
        String title = st.nextToken();
        return BorderFactory.createTitledBorder(BorderFactory.createRaisedBevelBorder(),title);
      }
    if ("boldtitled".equals(borderName)) {
        String title = st.nextToken();
        return BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.darkGray,1),title);
      }
    
    if ("indent".equals(borderName)) {
      try {
        int top = Integer.parseInt(st.nextToken());
        int left = Integer.parseInt(st.nextToken());
        int bottom = Integer.parseInt(st.nextToken());
        int right = Integer.parseInt(st.nextToken());
        return BorderFactory.createEmptyBorder(top, left, bottom, right);
      }
      catch (Exception ex) {
        System.err.println("Error while parsing border");
      }
    }
    return BorderFactory.createEmptyBorder();
  }
  
  public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
	  UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	  JFrame aap = new JFrame("test");
	  
	  aap.setSize(500,300);
	  aap.getContentPane().setLayout(new FlowLayout());
	  addTest(aap,BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),"Test1"),"Test1");
	  addTest(aap,BorderFactory.createTitledBorder("Test2"),"Test2");
	  addTest(aap,BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.darkGray,1),"Test3"),"Test3");
	  addTest(aap,BorderFactory.createTitledBorder(BorderFactory.createLoweredBevelBorder(),"Test1"),"Test1");
	  
	  aap.setVisible(true);
  }
  private static void addTest(JFrame root, Border b, String label) {
	  JPanel test = new JPanel();
	  test.setPreferredSize(new Dimension(170,80));
	  test.add(new JButton("AAP"));
	  test.add(new JButton("NOOT"));
	  test.add(new JButton("MIEs"));
	  test.setBorder(b);
	  root.getContentPane().add(test);
  }
}
