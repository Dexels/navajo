package com.dexels.navajo.client;

import com.dexels.navajo.document.Message;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels.com</p>
 * @author unascribed
 * @version 1.0
 * @deprecated, can be removed when Tipi no longer uses it.
 */

public interface ConditionErrorHandler {
  public void checkValidation(Message msg);
  public boolean hasConditionErrors();
  public void clearConditionErrors();
}
