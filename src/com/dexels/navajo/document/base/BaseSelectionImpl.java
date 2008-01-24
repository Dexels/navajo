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
  protected String value ="-1";
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

  public final String getName() {
    return ( this.name );
  }
  public final void setName( String newName ) {
    this.name = newName;
  }

  public final String getValue() {
    return ( this.value );
  }
  public final void setValue( String newVal ) {
    this.value = newVal;
  }

  public final boolean isSelected() {
    return ( this.isSelected );
  }
  
  public final void setSelected( boolean selected ) {
	  isSelected = selected;
  }

  public final String toString() {
    //System.err.println("toString(): " + getName().trim());
    if (getName() != null)
      return getName().trim();
    else
      return "";
  }

  public final Selection copy(Navajo n) {
      BaseSelectionImpl cp = (BaseSelectionImpl)NavajoFactory.getInstance().createSelection(n,getName(),getValue(),isSelected());
    cp.setRootDoc(n);
    return cp;
  }
  public final Property getParent() {
    return myProperty;
  }

  public final void setParent(Property m) {
    myProperty = m;
  }

  public final String getPath() {
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


  public int hashCode() {
  	return super.hashCode();
  }
  
  public boolean equals(Object o) {
  	return super.equals(o);
  }
  
  public final int compareTo(Selection o) {
 
    if (o.getName() == null && getName() == null) {
      return 0;
    }

    if (o.getName() != null && getName() == null) {
      return -1;
    }

    if (o.getName() == null && getName() != null) {
      return 1;
    }

    return (getName().compareTo(o.getName()));

  }
public Map<String,String> getAttributes() {
    Map<String,String> m = new HashMap<String,String>();
    m.put("name", name);
    m.put("value", value);
    m.put("selected", isSelected?"1":"0");
    return m;
}
public List<? extends BaseNode> getChildren() {
    return null;
}
public final String getTagName() {
    return Selection.SELECTION_DEFINITION;
}
public Object getRef() {
    throw new UnsupportedOperationException("getRef not possible on base type. Override it if you need it");
}


}

// EOF $RCSfile$ //
