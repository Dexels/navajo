package com.dexels.navajo.tipi.studio.components;

import java.awt.*;
import nanoxml.*;
import java.util.*;
import javax.swing.text.*;
import com.dexels.navajo.document.nanoimpl.*;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class TipiDocumentStyleParser {
  private XMLElement myElement;
  private StyledDocument myDoc = new DefaultStyledDocument();
  private MutableAttributeSet character;
  private MutableAttributeSet tag;
  private MutableAttributeSet attribute;
  private MutableAttributeSet value;
  private MutableAttributeSet comment;
  private int documentPosition = 0;


  public TipiDocumentStyleParser(XMLElement e) {
    myElement = e;
    initializeStyles();
    parseStyle(myElement, 1);
  }

  public TipiDocumentStyleParser() {
  }

  private void initializeStyles(){
    character = new SimpleAttributeSet();
    StyleConstants.setForeground(character, Color.blue);
    tag = new SimpleAttributeSet();
    StyleConstants.setForeground(tag, Color.decode("#800000"));
    attribute = new SimpleAttributeSet();
    StyleConstants.setForeground(attribute, Color.red);
    value = new SimpleAttributeSet();
    StyleConstants.setForeground(value, Color.black);
    comment = new SimpleAttributeSet();
    StyleConstants.setForeground(comment, Color.lightGray);
    StyleConstants.setFontSize(tag, 14);
    StyleConstants.setFontSize(attribute, 14);
    StyleConstants.setFontSize(value, 14);
    StyleConstants.setFontSize(comment, 14);
    StyleConstants.setFontSize(character, 14);
  }
  public void setElement(XMLElement e){
    myElement = e;
    initializeStyles();
    parseStyle(myElement, 1);
  }

  private void parseStyle(XMLElement e, int indent){
    try{
      appendString("<", character, indent-1, true);
      appendString(e.getName(), tag, indent, false);
      Enumeration attrs = e.enumerateAttributeNames();
      while(attrs.hasMoreElements()){
        String attrName = (String)attrs.nextElement();
        String attrValue = (String)e.getAttribute(attrName);

        appendString(" " + attrName, attribute, indent, false);
        appendString("=\"", character, indent, false);
        appendString(attrValue, value, indent, false);
        appendString("\"", character, indent, false);
      }
      Vector children = e.getChildren();
      if(children.size() == 0){
        appendString("/>\n", character, indent, false);
      }else{
        appendString(">\n", character, indent, false);
        for(int i=0;i<children.size();i++){
          XMLElement kid = (XMLElement)children.elementAt(i);
          parseStyle(kid, indent+1);
        }
        appendString("</", character, indent-1, true);
        appendString(e.getName(), tag, indent, false);
        appendString(">\n", character, indent, false);
      }
    }catch(Exception ex){
      ex.printStackTrace();
    }
  }

  private void appendString(String text, AttributeSet a, int indent, boolean doIndent){
    try{
      String t = "";
      if(doIndent){
        for (int i = 0; i <= indent; i++) {
          t = t + "  ";
        }
      }
      t = t +text;
      myDoc.insertString(documentPosition,t, a);
      documentPosition += t.length();
    }catch(Exception e){
      e.printStackTrace();
    }
  }

  public StyledDocument getDocument(){
    return myDoc;
  }

}