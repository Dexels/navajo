package com.dexels.navajo.nanoclient;

import com.dexels.navajo.nanodocument.*;

/**
 * <p>Title: SportLink Client:</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels.com</p>
 * @author unascribed
 * @version 1.0
 */

public interface NavajoLoadable {
  public void init(Message msg);
  public void load(Message msg);
  public void store(Message msg);
  public void insert(Message msg);
}