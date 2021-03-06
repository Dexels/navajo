/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.document.types;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.dexels.navajo.document.NavajoFactory;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public abstract class NavajoType implements  Serializable {
	private static final long serialVersionUID = -112880355087638085L;
	private transient Map<String, String> subTypeMap = null;

	public abstract boolean isEmpty();

	public NavajoType(String type) {
		String subtype = NavajoFactory.getInstance().getDefaultSubtypeForType(type);
		if (subtype != null) {
			subTypeMap = NavajoFactory.getInstance().parseSubTypes(subtype);
		}
	}

  public NavajoType() {
	  
  }
  
  public String toTmlString() {
	  return toString();
  }
  
  public NavajoType(String type, String subtype) {
    if (subtype!=null) {
      subTypeMap = NavajoFactory.getInstance().parseSubTypes(subtype);
    } else {
      subtype = NavajoFactory.getInstance().getDefaultSubtypeForType(type);
      if (subtype!=null) {
        subTypeMap = NavajoFactory.getInstance().parseSubTypes(subtype);
      }

    }
  }

  public String getSubType(String key) {
    if (subTypeMap!=null) {
      return subTypeMap.get(key);
    }
    return null;
  }

  public void setSubType(String subtype) {
    if (subtype!=null) {
      subTypeMap = NavajoFactory.getInstance().parseSubTypes(subtype);
    }
  }
  
  public void putSubType(String key, String value) {
	  if(subTypeMap==null) {
		  subTypeMap = new HashMap<>();
	  }
	  subTypeMap.put(key, value);
  }
}
