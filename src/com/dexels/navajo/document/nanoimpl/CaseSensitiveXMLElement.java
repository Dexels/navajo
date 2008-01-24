package com.dexels.navajo.document.nanoimpl;
import java.util.*;
/**
 * 
 * @author Frank Lyaruu
 * @deprecated
 */
@Deprecated
public class CaseSensitiveXMLElement extends XMLElement {

  public CaseSensitiveXMLElement() {
    super(new Hashtable<String,char[]>(),true,false);
  }
  protected XMLElement createAnotherElement()
  {
      return new CaseSensitiveXMLElement();
                            
  }

}