package com.dexels.navajo.tipi;

import com.dexels.navajo.tipi.tipixml.*;
import java.util.*;
import com.dexels.navajo.document.*;
import java.awt.*;
import com.dexels.navajo.tipi.tipixml.XMLElement;
import javax.swing.tree.TreeNode;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public abstract class TipiCondition implements TreeNode {

  protected TipiComponent myComponent = null;
  protected Map myParams = new HashMap();

  public TipiCondition() {
  }

  public void load(XMLElement elm, TipiComponent parent, TipiEvent event){
    myComponent = parent;
    if(elm.getName().equals("condition")){
      Vector temp = elm.getChildren();
      for(int i=0;i<temp.size();i++){
        XMLElement current = (XMLElement)temp.elementAt(i);
        if(current.getName().equals("param")){
          String name = (String)current.getAttribute("name");
          String value = (String)current.getAttribute("value");
           myParams.put(name, value);
        }
      }
    }
  }

  public abstract boolean evaluate(Navajo n, TipiContext context, Object source, Object event) throws TipiBreakException, TipiException;

  public XMLElement store(){
    XMLElement cond = new CaseSensitiveXMLElement();
    cond.setName("condition");
    Iterator it = myParams.keySet().iterator();
    while(it.hasNext()){
      XMLElement parm = new CaseSensitiveXMLElement();
      parm.setName("param");
      String name = (String)it.next();
      String value = (String)myParams.get(name);
      if(name != null){
        parm.setAttribute("name", name);
      }
      if(value != null){
        parm.setAttribute("value", value);
      }
      cond.addChild(parm);
    }
    return cond;
  }
  public TreeNode getChildAt(int parm1) {
    return null;
  }
  public int getChildCount() {
    return 0;
  }
  public TreeNode getParent() {
    return myComponent;
  }
  public int getIndex(TreeNode parm1) {
    return -1;
  }
  public boolean getAllowsChildren() {
    return false;
  }
  public boolean isLeaf() {
    return true;
  }
  public Enumeration children() {
    return null;
  }
}
