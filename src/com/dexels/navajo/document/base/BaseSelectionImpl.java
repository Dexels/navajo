package com.dexels.navajo.document.base;

import java.util.*;

import com.dexels.navajo.document.*;

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
public class BaseSelectionImpl extends BaseNode implements Selection{

  protected String name ="";
  protected String value ="";
  protected boolean isSelected = false;
  private Property myProperty = null;

  public BaseSelectionImpl(Navajo n, String name, String value, boolean isSelected) {
    super(n);
    this.name = name;
    this.value = value;
    this.isSelected = isSelected;
  }
  public BaseSelectionImpl(Navajo n) {
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

  public String toString() {
    //System.err.println("toString(): " + getName().trim());
    if (getName() != null)
      return getName().trim();
    else
      return "";
  }

  public Selection copy(Navajo n) {
      BaseSelectionImpl cp = (BaseSelectionImpl)NavajoFactory.getInstance().createSelection(n,getName(),getValue(),isSelected());
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
      try {
        return myProperty.getFullPropertyName() + "/" + getName();
      }
      catch (NavajoException ex) {
        ex.printStackTrace();
        return null;
      }
    } else {
      return "/"+getName();
    }
  }


  public final int compareTo(Object o) {
    if (!(o instanceof Selection)) {
      return 0;
    }


    if (((Selection) o).getName() == null && getName() == null) {
      return 0;
    }

    if (((Selection) o).getName() != null && getName() == null) {
      return -1;
    }

    if (((Selection) o).getName() == null && getName() != null) {
      return 1;
    }

    return (getName().compareTo(((Selection) o).getName()));

  }
public Map getAttributes() {
    Map m = new HashMap();
    m.put("name", name);
    m.put("value", value);
    m.put("selected", isSelected?"1":"0");
    return m;
}
public List getChildren() {
    return null;
}
public String getTagName() {
    return Selection.SELECTION_DEFINITION;
}
public Object getRef() {
    throw new UnsupportedOperationException("getRef not possible on base type. Override it if you need it");
}


}

// EOF $RCSfile$ //
