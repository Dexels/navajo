package com.dexels.navajo.document.lazy;

import com.dexels.navajo.document.*;

public interface MessageListener {
  public void messageLoaded(int startIndex,int endIndex, int newTotal);
}
