package com.dexels.navajo.tipi.swingclient.components.validation;

import com.dexels.navajo.document.*;

/**
 * <p>Title: Seperate project for Navajo Swing client</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Dexels</p>
 * @author not attributable
 * @version 1.0
 */

public interface PropertyValidatable {
  public void resetComponentValidationStateByRule(String id);
  public void checkForConditionErrors(Message msg);

}
