package com.dexels.navajo.adapter;


import com.dexels.navajo.mapping.*;
import com.dexels.navajo.server.*;
import com.dexels.navajo.document.*;


/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author Arjen Schoneveld
 * @version $Id$
 */

public class MultipleSQLMap implements Mappable {

    public SQLMap[] sqlMap;
    public SPMap[] spMap;
    public MultipleSQLMap[] multiSqlMap;

    public MultipleSQLMap() {}

    public void load(Access access) throws MappableException, UserException {
    }

    public void store() throws MappableException, UserException {
    }

    public void setSqlMap(SQLMap[] sql) {
        if (sql != null)
            this.sqlMap = sql;
    }

    public void setMultiSqlMap(MultipleSQLMap[] sql) {
        if (sql != null)
            this.multiSqlMap = sql;
    }

    public void setSpMap(SPMap[] sp) {
        if (sp != null)
            this.spMap = sp;
    }

    public void kill() {}

    public static void main(String args[]) {
        MultipleSQLMap ms = new MultipleSQLMap();
        MultipleSQLMap[] children = new MultipleSQLMap[5];

        for (int i = 0; i < 5; i++) {
            children[i] = new MultipleSQLMap();
            children[i].setSpMap(null);
        }
        ms.setMultiSqlMap(children);
    }
}
