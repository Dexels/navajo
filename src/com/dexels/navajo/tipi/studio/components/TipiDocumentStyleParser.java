package com.dexels.navajo.tipi.studio.components;

import java.awt.*;
import nanoxml.*;
import java.util.*;
import javax.swing.text.*;
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
  private MutableAttributeSet tag;
  private MutableAttributeSet attribute;
  private MutableAttributeSet value;
  private MutableAttributeSet comment;
  private int documentPosition = 0;


  public TipiDocumentStyleParser(XMLElement e) {
    myElement = e;
    initializeStyles();
    parseStyle(myElement, 0);
  }

  public TipiDocumentStyleParser() {
  }

  private void initializeStyles(){
    tag = new SimpleAttributeSet();
    StyleConstants.setForeground(tag, Color.black);
    attribute = new SimpleAttributeSet();
    StyleConstants.setForeground(attribute, Color.decode("#F8BF24"));
    value = new SimpleAttributeSet();
    StyleConstants.setForeground(value, Color.blue);
    comment = new SimpleAttributeSet();
    StyleConstants.setForeground(comment, Color.decode("#00C000"));
    StyleConstants.setFontSize(tag, 12);
    StyleConstants.setFontSize(attribute, 12);
    StyleConstants.setFontSize(value, 12);
    StyleConstants.setFontSize(comment, 12);
  }
  public void setElement(XMLElement e){
    myElement = e;
    initializeStyles();
    parseStyle(myElement, 0);
  }

  private void parseStyle(XMLElement e, int indent){
    try{
      appendString("<" + e.getName(), tag, indent);
      Enumeration attrs = e.enumerateAttributeNames();
      while(attrs.hasMoreElements()){
        String attrName = (String)attrs.nextElement();
        String attrValue = (String)e.getAttribute(attrName);

        appendString(" " + attrName + "=", attribute, indent);
        appendString("\"" + attrValue + "\"", value, indent);
      }
      Vector children = e.getChildren();
      if(children.size() == 0){
        appendString("/>\n", tag, indent);
      }else{
        appendString(">\n", tag, indent);
        for(int i=0;i<children.size();i++){
          XMLElement kid = (XMLElement)children.elementAt(i);
          parseStyle(kid, indent+2);
        }
        appendString("</" + e.getName() +">\n", tag, indent);
      }
    }catch(Exception ex){
      ex.printStackTrace();
    }
  }

  private void appendString(String text, AttributeSet a, int indent){
    try{
      //documentPosition += indent;
      myDoc.insertString(documentPosition,text, a);
      documentPosition += text.length();
    }catch(Exception e){
      e.printStackTrace();
    }
  }

  public StyledDocument getDocument(){
    return myDoc;
  }

}