

/**
 * Title:        Navajo<p>
 * Description:  <p>
 * Copyright:    Copyright (c) Arjen Schoneveld<p>
 * Company:      Dexels<p>
 * @author Arjen Schoneveld
 * @version 1.0
 */
package com.dexels.navajo.server;


public class ConditionData {

    public int id;
    public int serviceId;
    public int userId;
    public String condition;
    public String comment;
    public String replacedCondition;

    public ConditionData() {
        id = 0;
        serviceId = 0;
        userId = 0;
        condition = "empty";
        comment = "empty";
        replacedCondition = "empty";
    }
}
