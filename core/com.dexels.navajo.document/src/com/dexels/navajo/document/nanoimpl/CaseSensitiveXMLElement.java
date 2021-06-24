/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.document.nanoimpl;
import java.util.Hashtable;
/**
 * 
 * @author Frank Lyaruu
 * 
 */

public class CaseSensitiveXMLElement extends XMLElement {

	private static final long serialVersionUID = -6830073001073118575L;

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
	
  @Override
protected XMLElement createAnotherElement()
  {
      return new CaseSensitiveXMLElement();
                            
  }

}