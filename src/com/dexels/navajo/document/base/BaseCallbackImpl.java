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
public class BaseCallbackImpl extends BaseNode {

  private final ArrayList myObjects = new ArrayList();
   
  public BaseCallbackImpl(Navajo n) {
    super(n);
  }

    public Map getAttributes() {
        return null;
    }

    public List getChildren() {
        return myObjects;
    }

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
    
    public ArrayList getObjects() {
    	return myObjects;
    }
}

// EOF $RCSfile$ //
