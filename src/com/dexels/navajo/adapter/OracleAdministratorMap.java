package com.dexels.navajo.adapter;

/**
 * <p>Title: OracleAdministratorMap</p>
 * <p>Description: extends the Navajo SQLMap to add some Oracle specific
 * administration functions such as enabling and disable constraints.  The
 * database user should have DBA permissions on the database for this.</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author Matthew Eichler
 * @version $Id$
 */

import java.util.StringTokenizer;
import java.util.ArrayList;
import java.text.MessageFormat;
import java.util.Iterator;
import com.dexels.navajo.server.UserException;

public class OracleAdministratorMap
    extends SQLMap {

  public static final String LISTDELIMITER = ":";

  protected static final String constraintSQL = "SELECT " +
      "''ALTER TABLE '' ||  T.table_name || " +
      "'' MODIFY CONSTRAINT '' || T.constraint_name " +
      "FROM all_constraints T, all_constraints R " +
      "WHERE T.r_constraint_name = R.constraint_name " +
      "AND T.r_owner = R.owner AND T.constraint_type=''R'' AND " +
      "R.owner = ''{0}'' AND T.owner = ''{0}'' AND ( R.table_name IN ( {1} ) " +
      "OR T.table_name IN ( {1} ) )";
  protected static final String loggingSQL = "SELECT " +
      "''ALTER TABLE '' || table_name " +
      "FROM all_tables WHERE owner = ''{0}'' AND table_name IN ( {1} )";

  protected static final String getownerSQL = "SELECT " +
      "DISTINCT username FROM user_users";

  private ResultSetMap[] rsMap = null;
  private ArrayList tableListArray = new ArrayList();

  public String schemaOwner = null;
  public String tableList = null;
  public String constraintMode = "ENABLE VALIDATE";
  public String loggingMode = "LOGGING";

  // -------------------------------------------------------------- constructors

  public OracleAdministratorMap() {
    super();
  }

  // ------------------------------------------------------------ public setters

  /**
   * always set the schema owner, otherwise the wrong tables might be modified
   * @param SchemaOwner as String
   */

  public void setSchemaOwner(final String o) {
    this.schemaOwner = o.trim().toUpperCase();
    if (this.debug) {
      System.out.println(this.getClass() + ": schema owner set to '" +
                         this.schemaOwner +
                         "'");
    }

  }

  /**
   * set the list of tables to be modified, each delimited with DELIMITER
   * @param list of tables as String
   */

  public void setTableList(final String s) {
    this.tableList = s.trim();

    final StringTokenizer tok = new StringTokenizer(s, LISTDELIMITER);
    while (tok.hasMoreTokens()) {
      final String tname = tok.nextToken().trim().toUpperCase();
      this.tableListArray.add(tname);
      if (this.debug) {
        System.out.println(this.getClass() + ": added table '" + tname +
                           "' to list");
      }
    }

  }

  /**
   * Alters the tables from the table list for the given schema owner to
   * the given Mode, usually 'ENABLE VALIDATE' or 'DISABLE NOVALIDATE'
   * @param desired constraint mode as String
   * @throws UserException
   */

  public void setConstraintMode(final String m) throws UserException {
    this.constraintMode = m.trim().toUpperCase();
    this.checkSchemaOwner();
    final MessageFormat formatter = new MessageFormat(this.constraintSQL);
    final Object[] args = {
        this.schemaOwner, this.formatTableList()};
    final String qStr = formatter.format(args);
    this.setQuery(qStr);
    this.executeStatements(this.constraintMode);
  }

  /**
   * Alters the tables from the table list for the given schema owner to
   * the given loggin mode, either 'LOGGING' or 'NOLOGGING'
   * @param logging mode as String
   * @throws UserException
   */

  public void setLoggingMode(final String m) throws UserException {
    this.loggingMode = m.trim().toUpperCase();
    this.checkSchemaOwner();
    final MessageFormat formatter = new MessageFormat(this.loggingSQL);
    final Object[] args = {
        this.schemaOwner, this.formatTableList()};
    final String qStr = formatter.format(args);
    this.setQuery(qStr);
    this.executeStatements(this.loggingMode);

  }

  // ----------------------------------------------------------- private methods

  private String formatTableList() {
    final StringBuffer tableBuf = new StringBuffer();
    final Iterator iter = this.tableListArray.iterator();
    while (iter.hasNext()) {
      final String t = (String) iter.next();
      tableBuf.append("'" + t + "'");
      if (iter.hasNext()) {
        tableBuf.append(",");
      }

    }
    return (tableBuf.toString());
  }

  private void executeStatements(final String modeClause) throws UserException {
    this.update = null;
    this.rsMap = this.getResultSet();
    for (int i = 0; i < this.rsMap.length; i++) {
      final StringBuffer s = new StringBuffer( (String)this.rsMap[i].
                                              getColumnValue(new Integer(0)));
      s.append(" " + modeClause);
      if (this.debug) {
        System.out.println(this.getClass() +
                           ": attempting to execute Oracle DDL statement '" +
                           s.toString() + "'");
      }
      this.query = null;
      this.setUpdate(s.toString());
      this.setDoUpdate(true);
    }

  }

  private void checkSchemaOwner() throws UserException {
    if ( this.schemaOwner == null ) {
      this.update = null;
      this.query = this.getownerSQL;
      this.rsMap = this.getResultSet();
      if ( rsMap != null && rsMap.length > 0 ) {
        final String s = (String)this.rsMap[0].getColumnValue(new Integer(0));
        this.schemaOwner = s.trim().toUpperCase();
        if ( this.debug ) {
          System.out.println( this.getClass() + ": guessed schema owner is '" + this.schemaOwner + "'");
        }
      }
    }
    if ( this.schemaOwner == null ) {
      throw new UserException( -1328, "unable to determine database schema owner" );
    }
  }

} // public class OracleAdministratorMap extends SQLMap
// EOF: $RCSfile$ //