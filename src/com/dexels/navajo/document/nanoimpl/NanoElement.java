package com.dexels.navajo.document.nanoimpl;
/**
 * <p>Title: ShellApplet</p>
 * <p>Description: </p>
 * <p>Part of the Navajo mini client, based on the NanoXML parser</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels </p>
 * @author Frank Lyaruu
 * @version 1.0
 */

//import nanoxml.*;
import com.dexels.navajo.document.*;
import com.dexels.navajo.document.base.*;

public interface NanoElement {

 
  public void fromXml(XMLElement e);
  public XMLElement toXml();
}
