package com.dexels.navajo.document;

import java.util.*;

public interface MessageMappable extends java.io.Serializable {
  public String getMessageLabel(Message m);
}