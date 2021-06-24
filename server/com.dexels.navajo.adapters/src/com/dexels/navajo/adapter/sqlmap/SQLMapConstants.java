/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.adapter.sqlmap;

import com.dexels.navajo.server.DispatcherFactory;

/**
 * Class to hold some SQLMap constants
 * 
 * @author Erik Versteeg
 */
public class SQLMapConstants {
    public static final String ENTERPRISEDB = "enterprisedb";
    public static final String ORACLEDB = "oracle";
    public static final String POSTGRESDB = "postgresql";
    public static final String MYSQLDB = "mysql";

    public static boolean isLegacyMode() {
        try {
            return DispatcherFactory.getInstance().getNavajoConfig().useLegacyDateMode();
        } catch (NullPointerException e) {
            return true;
        }
    }

}
