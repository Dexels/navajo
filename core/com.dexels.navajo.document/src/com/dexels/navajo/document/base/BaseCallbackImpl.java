/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.document.base;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.dexels.navajo.document.Navajo;

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
public class BaseCallbackImpl extends BaseNode {

	private static final long serialVersionUID = 8739370944781100854L;
private final List<BaseNode> myObjects = new ArrayList<>();
   
  public BaseCallbackImpl(Navajo n) {
    super(n);
  }

    @Override
	public Map<String,String> getAttributes() {
        return null;
    }

    @Override
	public List<BaseNode> getChildren() {
        return getObjects();
    }

    @Override
	public String getTagName() {
        return "callback";
    }

    public void addObject(BaseObjectImpl boi) {
        myObjects.add(boi);
    }
    
    public BaseObjectImpl getRef(String name) {
    	for (int i = 0; i < myObjects.size(); i++ ) {
    		BaseObjectImpl obj = (BaseObjectImpl) myObjects.get(i);
    		if ( obj.getName().equals(name) ) {
    			return obj;
    		}
    	}
    	return null;
    }
    
    public List<BaseNode> getObjects() {
    	return myObjects;
    }
    
    public String getSignature() {
    	StringBuilder sb = new StringBuilder();
    	for (int i = 0; i < myObjects.size(); i++ ) {
    		BaseObjectImpl obj = (BaseObjectImpl) myObjects.get(i);
    		sb.append(obj.getName() +"-"+obj.getRef());
    	}
    	return sb.toString();
    }
}

// EOF $RCSfile$ //
