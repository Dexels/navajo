package com.dexels.navajo.studio.script.plugin.navajobrowser;

import com.dexels.navajo.document.*;
import java.util.Enumeration;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class ScriptFile
    implements ScriptNode {

  private final ScriptDir parent;
  private final String name;
  private final Message myMessage;

  public ScriptFile(ScriptDir parent, String name, Message m) {
    this.parent =parent;
    if (name.startsWith("/")) {
      this.name = name.substring(1);
    } else {
      this.name = name;
    }
    this.myMessage = m;
  }

  public int getChildCount() {
    return 0;
  }

  public boolean getAllowsChildren() {
    return false;
  }

  public boolean isLeaf() {
    return true;
  }

  public Enumeration children() {
    return null;
  }

  public Object getParent() {
    return parent;
  }

  public Object getChildAt(int childIndex) {
    return null;
  }

  public int getIndex(Object node) {
    return 0;
  }
  public String toString() {
    return name;
  }
  public String getFullPath() {
    if ("".equals(parent.getFullPath())) {
      if (name.startsWith("/")) {
        return name.substring(1);
      }
      return name;
    }
    String parentPath = parent.getFullPath();
//    if (parentPath.endsWith(":")) {
//      return parentPath+name;
//    }
    String ss = parentPath;
    if (parentPath.startsWith("/")) {
      ss = parentPath.substring(1);
    }
    String ns = name;
    if (name.startsWith("/")) {
      ns = name.substring(1);
    }
    if (ss.endsWith("/")) {
      ss = ss.substring(0,ss.length()-1);
    }
    String ppp = ss+"/"+ns;
    return ppp.replaceAll(":/",":");
  }

  public String getConnectionName() {
    if (parent==null) {
      return null;
    }

    return parent.getConnectionName();
  }

  public String getName() {
    return name;
  }

}
