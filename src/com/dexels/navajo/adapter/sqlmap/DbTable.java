package com.dexels.navajo.adapter.sqlmap;

import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.server.Parameters;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.NavajoConfig;
import com.dexels.navajo.server.UserException;
import com.dexels.navajo.mapping.MappableException;
import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.sql.ResultSetMetaData;
import com.dexels.navajo.adapter.SQLMap;
import com.dexels.navajo.server.Dispatcher;
import java.sql.Connection;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class DbTable implements Mappable {

  public String name;
  public String datasource;
  public String catalogName;
  public String schemaName;

  public DbColumn[] columns;

  public void load(Parameters parms, Navajo inMessage, Access access,
                   NavajoConfig config) throws MappableException, UserException {
  }

  public void store() throws MappableException, UserException {
  }

  public void kill() {
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public DbColumn[] getColumns() {
    if (columns == null) {
      try {
        setColumns();
      }
      catch (Exception ex) {
        ex.printStackTrace(System.err);
      }
    }
    return columns;
  }

  protected final void setColumns() throws Exception {

    SQLMap sqlMap = new SQLMap();
    sqlMap.load(null, null, null, Dispatcher.getNavajoConfig());
    sqlMap.setDatasource(datasource);
    Connection c = sqlMap.getConnection();
    ArrayList l = new ArrayList();

    if (c != null) {

      DatabaseMetaData metaData = c.getMetaData();

      try {
        ResultSet rs = metaData.getColumns(catalogName, schemaName, name, null);
        while (rs.next()) {
          String value = rs.getString("COLUMN_NAME");
          String type = rs.getString("TYPE_NAME");
          String nullable = rs.getString("NULLABLE");
          String size = rs.getString("COLUMN_SIZE");
          DbColumn t = new DbColumn();
          t.name = value;
          t.type = type;
          l.add(t);
        }
        rs.close();
      }
      catch (SQLException ex) {
        ex.printStackTrace(System.err);
      }
      finally {
        sqlMap.store();
      }
    }
    columns = new DbColumn[l.size()];
    columns = (DbColumn[]) l.toArray(columns);
  }

}