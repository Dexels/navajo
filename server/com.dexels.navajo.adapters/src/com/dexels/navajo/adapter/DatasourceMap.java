package com.dexels.navajo.adapter;

import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.Mappable;
import com.dexels.navajo.script.api.MappableException;
import com.dexels.navajo.script.api.UserException;

/**
 * Title:        Navajo
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:      Dexels
 * @author Arjen Schoneveld en Martin Bergman
 * @version 1.0
 */

/**
 * This built in mappable class should be used as a general class for using arbitrary SQL queries and processing the ResultSet. Uses JDBC 2.0 JNDI Datasources and also implements a transaction context for queries executed in this
 object.
 */
public class DatasourceMap implements Mappable {
    public void load(Access access) throws MappableException, UserException {}

    public void store() {}

    public void kill() {}
}
