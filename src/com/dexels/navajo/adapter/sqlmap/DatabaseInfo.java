package com.dexels.navajo.adapter.sqlmap;

import com.dexels.navajo.adapter.*;
import java.sql.DatabaseMetaData;
import java.sql.*;
import com.dexels.navajo.server.Parameters;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.NavajoConfig;
import com.dexels.navajo.server.UserException;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.mapping.Mappable;
import java.util.ArrayList;
import com.dexels.navajo.server.Dispatcher;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class DatabaseInfo implements Mappable {

  public String vendor = "";
  public String version = "";
  public DbCatalog [] catalogs;
  public String catalogName = null;
  public String schemaName = null;
  public DbSchema schema = null;
  public String datasource;

  public DatabaseInfo(DatabaseMetaData dbmd, String datasource) {

    try {
      this.vendor = dbmd.getDatabaseProductName();
      this.datasource = datasource;
      //setCatalogs(dbmd);
    }
    catch (SQLException ex) {
      ex.printStackTrace(System.err);
    }

    try {
      this.version = dbmd.getDatabaseProductVersion();
    }
    catch (SQLException ex1) {
    }
    System.err.println("vendor = " + vendor);
    System.err.println("version = " + version);
  }

  public DbCatalog [] getCatalogs() {
    setCatalogs();
    return catalogs;
  }

  private void setCatalogs() {

    if (catalogs == null) {

      SQLMap sqlMap = new SQLMap();
      DatabaseMetaData metaData = null;

      try {
        sqlMap.load(null, null, null, Dispatcher.getInstance().getNavajoConfig());
        sqlMap.setDatasource(datasource);
        Connection c = sqlMap.getConnection();
        metaData = c.getMetaData();
      }
      catch (Exception ex) {
      } finally {
        try {
          sqlMap.store();
        }
        catch (Exception ex1) {
          ex1.printStackTrace(System.err);
        }
      }

      if (metaData == null) {
        return;
      }

      boolean found = false;
      ArrayList list = new ArrayList();
      try {
        ResultSet rs = metaData.getCatalogs();
        while (rs.next()) {
          found = true;
          ResultSetMetaData rsmd = rs.getMetaData();
          int count = rsmd.getColumnCount();
          for (int i = 1; i <= count; i++) {
            String columnName = rsmd.getColumnName(i);
            String value = rs.getString(i);
            DbCatalog c = new DbCatalog();
            c.name = value;
            c.setSchemas(getAllSchemas(value, metaData));
            list.add(c);
          }
        }
        rs.close();

        if (!found) {
          DbCatalog c = new DbCatalog();
          c.name = "default";
          c.dummy = true;
          c.setSchemas(getAllSchemas(null, metaData));
          list.add(c);
        }

        catalogs = new DbCatalog[list.size()];
        catalogs = (DbCatalog[]) list.toArray(catalogs);
      }
      catch (SQLException sqle) {
        sqle.printStackTrace(System.err);
      }
    }

  }

  private final ArrayList getAllSchemas(String catSchema, DatabaseMetaData metaData) {

    boolean found = false;
    ArrayList l = new ArrayList();
    try {
      ResultSet rs =metaData.getSchemas();
      while (rs.next()) {
        found = true;
        ResultSetMetaData rsmd = rs.getMetaData();
        int count = rsmd.getColumnCount();
        for (int i = 1; i <= count; i++) {
          String columnName = rsmd.getColumnName(i);
          String value = rs.getString(i);
          DbSchema s = new DbSchema();
          s.name = value;
          s.setTables(getAllTables(value, catSchema, metaData));
          l.add(s);
        }
      }
      rs.close();

      if (!found) {
        DbSchema s = new DbSchema();
        s.name = "default";
        s.dummy = true;
        s.setTables(getAllTables(null, null, metaData));
        l.add(s);
      }

    } catch (SQLException sqle) {
      sqle.printStackTrace(System.err);
    }

    return l;

  }

  private final ArrayList getAllTables(String tableSchema, String catSchema, DatabaseMetaData metaData) {
    ArrayList l = new ArrayList();
    try {
      ResultSet rs = metaData.getTables(catSchema, tableSchema, null, new String[]{"TABLE"});
      while (rs.next()) {
        ResultSetMetaData rsmd = rs.getMetaData();
        String columnName = rsmd.getColumnName(3);
        String value = rs.getString(3);
        DbTable t = new DbTable();
        t.name = value;
        t.catalogName = catSchema;
        t.schemaName = tableSchema;
        t.datasource = datasource;
        l.add(t);
      }
      rs.close();
    }
    catch (Exception ex) {
      //ex.printStackTrace(System.err);
    }
    return l;
  }

  public String getVendor() {
    return this.vendor;
  }

  public String getVersion() {
    return this.version;
  }

  public void load(Parameters parms, Navajo inMessage, Access access, NavajoConfig config) throws MappableException, UserException {
  }

  public void store() throws MappableException, UserException {
  }

  public void kill() {
  }

  public void setCatalogName(String catalogName) {
    this.catalogName = catalogName;
  }

  public void setSchemaName(String schemaName) {
    this.schemaName = schemaName;
  }

  public DbSchema getSchema() throws UserException {

    DbCatalog [] all = getCatalogs();
    for (int i = 0; i < all.length; i++) {
      if (all[i].name.equals(catalogName == null ? "default" : catalogName)) {
        DbSchema [] schemas = all[i].getSchemas();
        for (int j = 0; j < schemas.length; j++) {
          if (schemas[j].name.equals(schemaName == null ? "default" : schemaName)) {
            return schemas[j];
          }
        }
      }
    }
    return null;
  }
}
