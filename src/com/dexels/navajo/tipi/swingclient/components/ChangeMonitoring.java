package com.dexels.navajo.tipi.swingclient.components;

public interface ChangeMonitoring {
  public boolean hasChanged();
  public boolean hasFocus();
  // Added by Arjen (18/11/2004 22:55 to explicitly reset changed flag (for supporting IGNORE functionality).
  public void resetChanged();
}