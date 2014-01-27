package com.dexels.navajo.adapter;


import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.Mappable;
import com.dexels.navajo.script.api.MappableException;
import com.dexels.navajo.script.api.UserException;


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

    @Override
	public void load(Access access) throws MappableException, UserException {
    }

    @Override
	public void store() throws MappableException, UserException {
    }

    public void setSqlMap(SQLMap[] sql) {
        if (sql != null)
            this.sqlMap = sql;
    }

    public SQLMap[] getSqlMap() {
    	return this.sqlMap;
    }
     
    public void setMultiSqlMap(MultipleSQLMap[] sql) {
        if (sql != null)
            this.multiSqlMap = sql;
    }

    public void setSpMap(SPMap[] sp) {
        if (sp != null)
            this.spMap = sp;
    }

    @Override
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
