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

public interface Selection {

  public String getName();
  public void setName( String newName );

  public String getValue();
  public void setValue( String newVal );

  public boolean isSelected();
  public void setSelected( boolean selected );

  public XMLElement toXml(XMLElement parent);
  public void fromXml(XMLElement e);
  public String toString();
  public Navajo getRootDoc();
  public Selection copy(Navajo n);
  public void setRootDoc(Navajo n);
  public Property getParent();
  public void setParent(Property m);
  public String getPath();

}

// EOF $RCSfile$ //
