package com.dexels.navajo.server;


/**
 * Title:        Navajo
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Dexels
 * @author Arjen Schoneveld en Martin Bergman
 * @version 1.0
 */

public class ServiceGroup {

    public int id = 0;
    public String name;
    public String servlet;

    public ServiceGroup(int id1, String name1, String servlet1) {
        id = id1;
        name = name1;
        servlet = servlet1;
    }

    public ServiceGroup() {
        id = 0;
        name = "empty";
        servlet = "empty";
    }
}
