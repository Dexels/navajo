package com.dexels.navajo.tipi;

import com.dexels.navajo.document.*;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributablecheckValidation
 * @version 1.0
 */

public interface PropertyValidatable {
	public void resetComponentValidationStateByRule(String id);

	public void checkForConditionErrors(Message msg);

}
