package com.dexels.navajo.persistence.impl;

import com.dexels.navajo.persistence.Persistable;

/**
 * Title:        Navajo Product Project
 * Description:  This is the official source for the Navajo server
 * Copyright:    Copyright (c) 2002
 * Company:      Dexels BV
 * @author Arjen Schoneveld
 * @version 1.0
 */

public class PersistentClass implements Persistable {

  private String value = "";

  public PersistentClass(String value) {
    this.value = value;
  }

  public long  getExpirationInterval() {
      return 1000 * 60 * 60 * 24 * 365 * 10;
  }

  public boolean isPersistent() {
    return true;
  }

  public String toString() {
    return value;
  }

  public void setPersistent(boolean b) {

  }

  public String persistenceKey() {
    return this.hashCode()+"";
  }
}