package com.dexels.navajo.studio.script.plugin.navajobrowser;

import java.util.*;

import javax.swing.tree.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public interface ScriptNode {
  public String getFullPath();
  public Object getParent();

  public int getChildCount();

  public boolean getAllowsChildren();

  public boolean isLeaf();

  public Enumeration children();

 
  public Object getChildAt(int childIndex);}
