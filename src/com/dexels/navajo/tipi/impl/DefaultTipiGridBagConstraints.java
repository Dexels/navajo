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

  private String[] myConstraints = new String[14];

  public DefaultTipiGridBagConstraints() {
  }

  public void parse(String cs){
//    String cs = elm.getStringAttribute("gridbag");
    StringTokenizer tok = new StringTokenizer(cs, ", ");
    int tokenCount = tok.countTokens();
    if(tokenCount != 14){
      throw new RuntimeException("Gridbag for: " + cs +" is invalid!");
       }else{
      for(int i=0;i<14;i++){
//        int con = new Integer(tok.nextToken()).intValue();
        myConstraints[i] = tok.nextToken();
      }
      gridx = Integer.parseInt(myConstraints[0]);
      gridy = Integer.parseInt(myConstraints[1]);
      gridwidth = Integer.parseInt(myConstraints[2]);
      gridheight = Integer.parseInt(myConstraints[3]);
      weightx = Double.parseDouble(myConstraints[4]);
      weighty = Double.parseDouble(myConstraints[5]);
      anchor = Integer.parseInt(myConstraints[6]);
      fill = Integer.parseInt(myConstraints[7]);
      insets = new Insets(Integer.parseInt(myConstraints[8]),Integer.parseInt(myConstraints[9]),Integer.parseInt(myConstraints[10]),Integer.parseInt(myConstraints[11]));
      ipadx = Integer.parseInt(myConstraints[12]);
      ipady = Integer.parseInt(myConstraints[13]);
    }
  }

  public DefaultTipiGridBagConstraints(String s){
    parse(s);
  }

  public static void main(String[] args){
    XMLElement bert = new CaseSensitiveXMLElement();
    bert.setName("Bert");
    bert.setAttribute("name", "bert_een");
    bert.setAttribute("gridbag", "1, 1, 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0");
    DefaultTipiGridBagConstraints bertje = new DefaultTipiGridBagConstraints("1, 1, 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0");
    System.err.println("You made: " + bert.toString());
  }

  public String toString(){
    return ""+gridx+","+gridy+","+gridwidth+","+gridheight+","+weightx+","+weighty+","+anchor+","+fill+","+insets.top+","+insets.left+","+insets.bottom+","+insets.right+","+ipadx+","+ipady;
  }

}