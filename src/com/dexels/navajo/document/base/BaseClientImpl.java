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
public class BaseClientImpl extends BaseNode {

  private final Map myAttributes = new HashMap();
  
  public BaseClientImpl(Navajo n) {
    super(n);
  }

    public Map getAttributes() {
        return myAttributes;
    }

    public List getChildren() {
        return null;
    }

    public String getTagName() {
        return "client";
    }

    public String getHost() {
        return (String)myAttributes.get("host");
    }
    public String getAddress() {
        return (String)myAttributes.get("address");
    }
   
    public void setHost(String host) {
        myAttributes.put("host", host);
    }
    public void setAddress(String host) {
        myAttributes.put("host", host);
    }
}

// EOF $RCSfile$ //
