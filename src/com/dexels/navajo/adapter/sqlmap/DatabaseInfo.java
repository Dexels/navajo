package com.dexels.navajo.adapter.sqlmap;

import com.dexels.navajo.adapter.*;
import java.sql.DatabaseMetaData;
import java.sql.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class DatabaseInfo {

  public String vendor = "";
  public String version = "";

  public DatabaseInfo(DatabaseMetaData dbmd) {

    try {
      this.vendor = dbmd.getDatabaseProductName();
    }
    catch (SQLException ex) {
    }
    try {
      this.version = dbmd.getDatabaseProductVersion();
    }
    catch (SQLException ex1) {
    }
    System.err.println("vendor = " + vendor);
    System.err.println("version = " + version);
  }

  public String getVendor() {
    return this.vendor;
  }

  public String getVersion() {
    return this.version;
  }

}
