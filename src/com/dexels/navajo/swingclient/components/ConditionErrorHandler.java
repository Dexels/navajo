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

public interface ConditionErrorHandler {
  public void checkValidation(Message msg);
  public boolean hasConditionErrors();
}