package com.dexels.navajo.adapter;

import com.dexels.navajo.mapping.*;
import javax.naming.Context;
import com.dexels.navajo.server.*;
import com.dexels.navajo.document.*;
import java.util.ArrayList;


/**
 * Title:        Navajo Product Project
 * Description:  This is the official source for the Navajo server
 * Copyright:    Copyright (c) 2002
 * Company:      Dexels BV
 * @author Arjen Schoneveld
 * @version 1.0
 */

public class TestAdapter implements Mappable {

    String empty = null;

    public TestAdapter() {}

    public void load(Parameters parms, Navajo inMessage, Access access, NavajoConfig config) throws MappableException, UserException {}

    public void store() throws MappableException, UserException {}

    public void kill() {}

    public String getEmpty() {
        return empty;
    }
}
