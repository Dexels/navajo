package com.dexels.navajo.tipi.util;
import java.util.Hashtable;
/**
 * 
 * @author Frank Lyaruu
 * 
 */

public class CaseSensitiveXMLElement extends XMLElement {

	private static final long serialVersionUID = 1L;

	public CaseSensitiveXMLElement(String name) {
		    super(new Hashtable<String,char[]>(),false,false);
		    setName(name);
	  }
	  
	public CaseSensitiveXMLElement() {
    super(new Hashtable<String,char[]>(),false,false);
  }
	public CaseSensitiveXMLElement(boolean skipLeadingWhiteSpace) {
	    super(new Hashtable<String,char[]>(), skipLeadingWhiteSpace, false);
	  }
	
  protected XMLElement createAnotherElement()
  {
      return new CaseSensitiveXMLElement();
                            
  }

}