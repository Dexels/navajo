package com.dexels.navajo.adapter;


import com.dexels.navajo.server.Parameters;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.mapping.*;
import com.dexels.navajo.server.*;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.sql.*;
import org.dexels.grus.DbConnectionBroker;
import com.dexels.navajo.util.*;


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
    public void load(Parameters parms, Navajo inMessage, Access access, NavajoConfig config) throws MappableException, UserException {}

    public void store() {}

    public void kill() {}
}
