package com.dexels.navajo.adapter;

/**
 * <p>Title: SQL Batch Update Helper</p>
 * <p>Description:
 * assists in running multiple SQL Statements within an SQLMap in the case
 * where in Sybase and Oracle, multiple statements cannot be used in a single
 * prepared statement</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Dexels B.V.</p>
 * @author Matthew Eichler
 * @version $Id$
 */

import java.sql.Connection;
import java.util.StringTokenizer;
import com.dexels.navajo.logger.NavajoLogger;
import com.dexels.navajo.server.NavajoConfig;
import com.dexels.navajo.logger.NavajoPriority;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ParameterMetaData;
import java.util.ArrayList;
import java.sql.Types;
import java.sql.ResultSet;
import java.sql.SQLWarning;

public class SQLBatchUpdateHelper {

  public static final String DELIMITER = ";";
  public static final char PLACEHOLDER = '?';

  public static final String vcIdent =
      "$Id$";

  protected static NavajoLogger logger = NavajoConfig.getNavajoLogger(
      SQLBatchUpdateHelper.class);
  private boolean debug = false;

  private String sql = null;
  private Connection conn = null;
  private ArrayList params = null;

  private ArrayList parsed = new ArrayList();
  private ArrayList preparedList = new ArrayList();
  private int updateCount = 0;
  private int pptr = 0;
  private ResultSet rs = null;

  // -------------------------------------------------------------- constructors

  public SQLBatchUpdateHelper(final String sql, final Connection conn,
                              final ArrayList params) throws
      SQLException {
    this.sql = sql;
    this.conn = conn;
    this.params = params;

    this.parseStatements();
    this.executeStatements();
  }

  public SQLBatchUpdateHelper(final String sql, final Connection conn,
                              final ArrayList params, boolean debug) throws
      SQLException {
    this.sql = sql;
    this.conn = conn;
    this.params = params;
    this.debug = debug;

    this.parseStatements();
    this.executeStatements();
  }

  // ------------------------------------------------------------ public methods

  public int getUpdateCount() {
    return (this.updateCount);
  }

  public ResultSet getResultSet() {
    return (this.rs);
  }

  public void closeLast() {
    final int last = this.preparedList.size() - 1;
    final PreparedStatement pre = (PreparedStatement) this.preparedList.get(last);
    try {
      pre.close();
    } catch (Exception e) { /* don't care */ }
  }

  // ----------------------------------------------------------- private methods

  private void parseStatements() throws SQLException {
    final StringTokenizer tok = new StringTokenizer(this.sql, DELIMITER);
    if (tok.countTokens() == 0) {
      throw new SQLException("tried to pass empty SQL statement batch");
    }

    while (tok.hasMoreElements()) {
      final String s = tok.nextToken();
      if (s.length() > 0 && !s.matches("\\s*")) {
        if (this.debug) {
          System.out.println("parsed statement: " + s);
        }
        this.parsed.add(s);
        final PreparedStatement prepared = this.conn.prepareStatement(s);
        final int required = this.countRequiredParameters(s);
        if (this.debug) {
          System.out.println("required number of parameters = " + required);
        }
        this.setStatementParameters(prepared, required);
        this.preparedList.add(prepared);
      }

    }

  }

  private void executeStatements() throws SQLException {
    for (int i = 0; i < this.parsed.size(); i++) {

      final boolean last = i == (this.parsed.size() - 1);
      final String s = (String) parsed.get(i);
      PreparedStatement prepared = (PreparedStatement) this.preparedList.get(i);
      this.rs = null;

      if (!last) {
        prepared.executeUpdate();
        if (this.debug) {
          System.out.println("successful execution of SQL '" + s + "'");
        }
      }
      else {
        try {
          this.rs = prepared.executeQuery();
          if (this.debug) {
            System.out.println("executed last SQL '" + s + "' as query");
          }
        }
        catch (SQLException e) {
          this.rs = null;
          // For Sybase compatibility: sybase does not like to be called using executeQuery() if query does not return a resultset.
          if (e.getMessage().indexOf("JZ0R2") == -1) {
            e.printStackTrace();
            throw e;
          }
        }
      }
      this.logWarnings(prepared);
      this.updateCount = this.updateCount + prepared.getUpdateCount();
      if (this.debug) {
        System.out.println("cummulative update count is " + this.updateCount);
      }
      if (!last) {
        prepared.close();
        /**************************************************************
         * closing does't seem to be enough, so we kill it completely
         * otherwise there may be complaints about too many open
         * cursors
         * meichler@dexels.com 21.oct 2003
         **************************************************************/
        prepared = null;
      }
    }

  }

  private int countRequiredParameters(final String statement) {
    final char[] a = statement.toCharArray();
    int cnt = 0;
    for (int i = 0; i < a.length; i++) {
      if (a[i] == PLACEHOLDER) {
        cnt++;
      }
    }
    return (cnt);

  }

  private void setStatementParameters(final PreparedStatement pre, final int n) throws
      SQLException {
    if (this.params != null) {
      for (int i = 0; i < n; i++, this.pptr++) {
        if (this.pptr >= this.params.size()) {
          throw new SQLException(
              "not enough parameters provided for multiple SQL statements");
        }

        final Object param = this.params.get(this.pptr);
        this.setParameter(i, param, pre);
      }
    }

  }

  private void setParameter(final int idx,
                            final Object param,
                            final PreparedStatement pre) throws SQLException {
    if (this.debug) {
      System.out.println("parameter " + this.pptr + " = " + param);
    }
    if (param == null) {
      pre.setNull(idx + 1, Types.VARCHAR);
    }
    else if (param instanceof String) {
      pre.setString(idx + 1, (String) param);
    }
    else if (param instanceof Integer) {
      pre.setInt(idx + 1, ( (Integer) param).intValue());
    }
    else if (param instanceof Double) {
      pre.setDouble(idx + 1, ( (Double) param).doubleValue());
    }
    else if (param instanceof java.util.Date) {
      java.sql.Date sqlDate = new java.sql.Date( ( (java.util.Date) param).
                                                getTime());
      pre.setDate(idx + 1, sqlDate);
    }
    else if (param instanceof Boolean) {
      pre.setBoolean(idx + 1, ( (Boolean) param).booleanValue());
    }
  }

  private void logWarnings(final PreparedStatement pre) throws SQLException {
    if (this.debug) {
      SQLWarning warning = pre.getWarnings();
      while (warning != null) {
        System.out.println("SQL warning: " +
                           warning.getMessage());
        warning = warning.getNextWarning();
      }
    }

  }

} // public class SQLBatchUpdateHelper
// EOF: $RCSfile$ //