package com.dexels.navajo.document;
import nanoxml.*;
/**
 * <p>Title: ShellApplet</p>
 * <p>Description: </p>
 * <p>Part of the Navajo mini client, based on the NanoXML parser</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels </p>
 * <p>$Id$</p>
 * @author Frank Lyaruu
 * @version $Revision$
 */
public class SelectionImpl extends BaseNode implements Selection {

  private String name ="";
  private String value ="";
  private boolean isSelected = false;
  private Property myProperty = null;

  public SelectionImpl(Navajo n, String name, String value, boolean isSelected) {
    super(n);
    this.name = name;
    this.value = value;
    this.isSelected = isSelected;
  }
  public SelectionImpl(Navajo n) {
    super(n);
  }

  public String getName() {
    return ( this.name );
  }
  public void setName( String newName ) {
    this.name = newName;
  }

  public String getValue() {
    return ( this.value );
  }
  public void setValue( String newVal ) {
    this.value = newVal;
  }

  public boolean isSelected() {
    return ( this.isSelected );
  }
  public void setSelected( boolean selected ) {
    this.isSelected = selected;
  }

  public void fromXml(XMLElement x) {
    String attr = (String)x.getAttribute("selected");
    isSelected = (attr!=null && !attr.equals("0"));
    name = (String)x.getAttribute("name");
    value = (String)x.getAttribute("value");
  }

  public XMLElement toXml(XMLElement parent) {
    XMLElement x = new CaseSensitiveXMLElement();
    x.setName("option");
    x.setAttribute("name",name);
    x.setAttribute("value",value==null?"":value);
    x.setAttribute("selected",isSelected?"1":"0");
    return x;
  }

  public String toString() {
    return getName().trim();
  }

  public Selection copy(Navajo n) {
    Selection cp = Navajo.createSelection(n,getName(),getValue(),isSelected());
    cp.setRootDoc(n);
    return cp;
  }
  public Property getParent() {
    return myProperty;
  }

  public void setParent(Property m) {
    myProperty = m;
  }

  public String getPath() {
    if (myProperty!=null) {
      return myProperty.getPath()+"/"+getName();
    } else {
      return "/"+getName();
    }
  }


}

// EOF $RCSfile$ //
