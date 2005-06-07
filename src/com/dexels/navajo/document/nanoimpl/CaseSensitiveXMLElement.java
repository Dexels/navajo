package com.dexels.navajo.document.nanoimpl;
import java.util.*;

public class CaseSensitiveXMLElement extends XMLElement {

  public CaseSensitiveXMLElement() {
    super(new Hashtable(),true,false);
  }
  protected XMLElement createAnotherElement()
  {
      return new CaseSensitiveXMLElement();
                            
  }

}