package com.dexels.navajo.server;


/**
 * Title:        Navajo
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Dexels
 * @author Arjen Schoneveld en Martin Bergman
 * @version 1.0
 */

public class User {
    public int id = 0;
    public String name;
    public String password;

    public User(int id1, String name1, String password1) {
        id = id1;
        name = name1;
        password = password1;
    }

    public User() {
        id = 0;
        name = "empty";
        password = "empty";
    }
}
