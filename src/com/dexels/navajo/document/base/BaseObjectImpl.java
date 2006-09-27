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

  private final ArrayList myObjects = new ArrayList();
  private final Map myAttributes = new HashMap();
  
  public BaseObjectImpl(Navajo n) {
    super(n);
  }

    public Map getAttributes() {
        return myAttributes;
    }

    public List getChildren() {
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
    	return (String)myAttributes.get("name");
    }
    public String getRef() {
        return (String)myAttributes.get("ref");
    }
    public String getPercReady() {
        return (String)myAttributes.get("perc_ready");
    }
    public boolean isFinished() {
        return "true".equals(myAttributes.get("finished"));
    }
    public String getInterrupt() {
        return (String)myAttributes.get("interrupt");
    }    
}

// EOF $RCSfile$ //
