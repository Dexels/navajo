package com.dexels.navajo.document.lazy;

public interface MessageListener {
  public void messageLoaded(int startIndex,int endIndex, int oldTotal);
}
