/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.document.base;

import java.util.HashMap;
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
public class BaseObjectImpl extends BaseNode {

	private static final long serialVersionUID = -1937743662725211261L;
	private final Map<String,String> myAttributes = new HashMap<>();
  
  public BaseObjectImpl(Navajo n) {
    super(n);
  }

    @Override
	public Map<String,String> getAttributes() {
        return myAttributes;
    }

    @Override
	public List<BaseNode> getChildren() {
        return null;
    }

    @Override
	public String getTagName() {
        return "object";
    }

    public void setName(String name) {
        myAttributes.put("name", name);
    }
    public void setRef(String ref) {
        myAttributes.put("ref", ref);
    }
    public void setPercReady(double percReady) {
        myAttributes.put("perc_ready", ""+percReady);
    }
    public void setFinished(boolean b) {
        myAttributes.put("finished", (b ? "true" : "false"));
    }
    public void setInterrupt(String in) {
        myAttributes.put("interrupt", in);
    }
    public String getName() {
    	return myAttributes.get("name");
    }
    public String getRef() {
        return myAttributes.get("ref");
    }
    public String getPercReady() {
        return myAttributes.get("perc_ready");
    }
    public boolean isFinished() {
        return "true".equals(myAttributes.get("finished"));
    }
    public String getInterrupt() {
        return myAttributes.get("interrupt");
    }    
}

// EOF $RCSfile$ //
