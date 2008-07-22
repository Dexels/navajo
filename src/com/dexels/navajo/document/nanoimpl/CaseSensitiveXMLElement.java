package com.dexels.navajo.document.nanoimpl;
import java.util.*;
/**
 * 
 * @author Frank Lyaruu
 * 
 */

public class CaseSensitiveXMLElement extends XMLElement {

	  public CaseSensitiveXMLElement(String name) {
		    super(new Hashtable<String,char[]>(),true,false);
		    setName(name);
	  }
	  
	public CaseSensitiveXMLElement() {
    super(new Hashtable<String,char[]>(),true,false);
  }
  protected XMLElement createAnotherElement()
  {
      return new CaseSensitiveXMLElement();
                            
  }

}