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

    public String empty = null;
    public TestAdapter [] testAdapters;
    public TestAdapter single;

    public TestAdapter() {}

    public void load(Parameters parms, Navajo inMessage, Access access, NavajoConfig config) throws MappableException, UserException {
       testAdapters = new TestAdapter[5];
       for (int i = 0; i < 5; i++) {
        testAdapters[i] = new TestAdapter();
        testAdapters[i].empty = "adapter"+i;
       }
       single = new TestAdapter();
       single.empty = "I am single";
    }

    public void store() throws MappableException, UserException {}

    public void kill() {}

    public TestAdapter getSingle() {
      return this.single;
    }

    public void setSingle(TestAdapter s) {
      this.single = s;
    }


    public void setTestAdapters(TestAdapter [] all) {
      this.testAdapters = all;
    }

    public TestAdapter [] getTestAdapters() {
      return this.testAdapters;
    }

    public void setEmpty(String s) {
      empty = s;
    }

    public String getEmpty() {
        return empty;
    }
}
