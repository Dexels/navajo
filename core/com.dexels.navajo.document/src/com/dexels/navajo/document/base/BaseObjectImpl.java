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
public class BaseObjectImpl extends BaseNode {

	private static final long serialVersionUID = -1937743662725211261L;
	private final Map<String,String> myAttributes = new HashMap<String,String>();
  
  public BaseObjectImpl(Navajo n) {
    super(n);
  }

    public Map<String,String> getAttributes() {
        return myAttributes;
    }

    public List<BaseNode> getChildren() {
        return null;
    }

    public String getTagName() {
        return "object";
    }

    public void setName(String name) {
        myAttributes.put("name", name);
    }
    public void setRef(String ref) {
        myAttributes.put("ref", ref);
    }
    public void setPercReady(double perc_ready) {
        myAttributes.put("perc_ready", ""+perc_ready);
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
