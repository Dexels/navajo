package com.dexels.navajo.tipi.impl;

import java.awt.*;
import java.awt.GridBagConstraints;
import com.dexels.navajo.tipi.tipixml.*;
import java.util.StringTokenizer;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class DefaultTipiGridBagConstraints extends GridBagConstraints {

  private int[] myConstraints = new int[14];

  public DefaultTipiGridBagConstraints() {
  }

  public void setElement(XMLElement elm){
    String cs = elm.getStringAttribute("gridbag");
    StringTokenizer tok = new StringTokenizer(cs, ", ");
    int tokenCount = tok.countTokens();
    if(tokenCount != 14){
      throw new RuntimeException("Gridbag for: " + elm.getName() + "[" + elm.getStringAttribute("name") +"] has an invalid number of constraints["  + tokenCount + "]");
    }else{
      for(int i=0;i<14;i++){
        int con = new Integer(tok.nextToken()).intValue();
        myConstraints[i] = con;
      }
      gridx = myConstraints[0];
      gridy = myConstraints[1];
      gridwidth = myConstraints[2];
      gridheight = myConstraints[3];
      weightx = myConstraints[4];
      weighty = myConstraints[5];
      anchor = myConstraints[6];
      fill = myConstraints[7];
      insets = new Insets(myConstraints[8],myConstraints[9],myConstraints[10],myConstraints[11]);
      ipadx = myConstraints[12];
      ipady = myConstraints[13];
    }

  }

  public DefaultTipiGridBagConstraints(XMLElement elm){
    setElement(elm);
  }

  public static void main(String[] args){
    XMLElement bert = new CaseSensitiveXMLElement();
    bert.setName("Bert");
    bert.setAttribute("name", "bert_een");
    bert.setAttribute("gridbag", "1, 1, 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0,4");
    DefaultTipiGridBagConstraints bertje = new DefaultTipiGridBagConstraints(bert);
    System.err.println("You made: " + bertje.toString());

  }

  public String toString(){
    return ""+gridx+","+gridy+","+gridwidth+","+gridheight+","+weightx+","+weighty+","+anchor+","+fill+","+insets.top+","+insets.left+","+insets.bottom+","+insets.right+","+ipadx+","+ipady;
  }

}