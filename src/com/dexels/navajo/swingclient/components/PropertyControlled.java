package com.dexels.navajo.swingclient.components;

import com.dexels.navajo.document.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels.com</p>
 * @author unascribed
 * @version 1.0
 */

public interface PropertyControlled {
  public Property getProperty();
  public void setProperty(Property p);
  public void setGhosted(boolean b);
  public boolean isGhosted();
  public void update();
}