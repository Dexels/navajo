package com.dexels.navajo.adapter;


import com.dexels.navajo.mapping.*;
import com.dexels.navajo.server.*;
import com.dexels.navajo.document.Navajo;


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

    public void load(Parameters parms, Navajo inMessage, Access access, NavajoConfig config) throws MappableException, UserException {
        System.out.println("in MultipleSQLMap load()");
    }

    public void store() throws MappableException, UserException {
        System.out.println("in MultipleSQLMap store()");
    }

    public void setSqlMap(SQLMap[] sql) {
        System.out.println("MultipleSQLMap in setSqlMap()");
        if (sql != null)
            this.sqlMap = sql;
    }

    public void setMultiSqlMap(MultipleSQLMap[] sql) {
        System.out.println("MultipleSQLMap in setmultiSqlMap()");
        if (sql != null)
            this.multiSqlMap = sql;
    }

    public void setSpMap(SPMap[] sp) {
        System.out.println("MultipleSQLMap: in setSpMap()");
        if (sp != null)
            this.spMap = sp;
        System.out.println("Leaving MultipleSQLMap");
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
