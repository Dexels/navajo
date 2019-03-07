package com.dexels.navajo.adapter;

/**
 * <p>Title: SequencedInsertMap</p>
 * <p>Description: A specialized version of the Navajo SQLMap that
 * allows for a single insert into a table that where a generated
 * serial/sequence primary key is created.  This Adapter attempts to
 * handle the various database product implementation of that feature</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Dexels B.V.</p>
 * @author Matthew Eichler meichler@dexels.com
 * @version $Id: SequencedInsertMap.java,v 1.6 2012/03/30 15:38:45 frank Exp $
 */

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.script.api.UserException;

public class SequencedInsertMap extends SQLMap {
    private static final Logger logger = LoggerFactory.getLogger(SequencedInsertMap.class);

    
  public final static String ORACLEPRODUCTNAME = "Oracle";
  public final static String HSQLPRODUCTNAME = "HSQL Database Engine";
  public final static String SELSEQUENCESQL = "SELECT {0}.nextval FROM DUAL";
  public final static String SELIDENTITYSQL = "CALL IDENTITY()";

  public String databaseProduct = null;
  public String sequenceName = null;
  public Integer identity = null;
  public boolean identityPlaceholder = false;

  // -------------------------------------------------------------- constructors

  public SequencedInsertMap() {
    super();
  }

  // ------------------------------------------------------------ public setters

  /**
   * Set the database product vendor.  This is safer to do as soon as possible.
   * A null product will assumed to be Oracle later.
   * @param product name
   */

  public void setDatabaseProduct(final String product) {
    this.databaseProduct = product;
  }

  /**
   * Set the name of the Oracle sequence that should be used to generate a
   * new primary key value, if Oracle is being used
   * @param name
   */

  public void setSequenceName(final String name) {
    this.sequenceName = name;
  }

  /**
   * Use the identity setter in correct position while setting parameters
       * for the insert statement.  This will put the identity column in the correct
   * place amongst the prepared statement parameteers
   * @param whatever (boolean value doesn't matter)
   * @throws UserException
   */

  public void setIdentityPlaceholder(final boolean whatever) throws
      UserException {
    if ( (this.databaseProduct == null ||
          this.databaseProduct.equals(ORACLEPRODUCTNAME)) &&
        this.sequenceName != null) {
      final MessageFormat formatter = new MessageFormat(SequencedInsertMap.SELSEQUENCESQL);
      final Object[] args = {
          this.sequenceName};
      final String qStr = formatter.format(args);
      try {
        this.createConnection();
        final PreparedStatement prepared = this.getConnection().prepareStatement(qStr);
        final ResultSet rs = prepared.executeQuery();
        if (rs.next()) {
          this.identity = Integer.valueOf(rs.getInt(1));
        }
        rs.close();
        prepared.close();

      }
      catch (SQLException sqle) {
        throw new UserException( -1, sqle.getMessage());
      }

      if (this.getDebug() && (this.identity != null)) {
        logger.debug(this.getClass() +
                           ": " + this.databaseProduct +
                           ": generated new identifier '" + this.identity +
                           "' from sequence '" + this.sequenceName + "'");
      }
    }
    else {
      this.identity = null;
    }
    this.setParameter(this.identity);

  }

  // ------------------------------------------------------------ public getters

  /**
       * @return the identity primary key value that was generated as an integer using
   * the appropriate technique based on the database vendor
   * @throws UserException
   */

  public Integer getIdentity() throws UserException {
    if (this.identity == null && this.databaseProduct != null &&
        this.databaseProduct.equals(SequencedInsertMap.HSQLPRODUCTNAME)) {
      try {
        this.createConnection();
        final PreparedStatement prepared = 
        	this.getConnection().prepareStatement(SequencedInsertMap.SELIDENTITYSQL);
        final ResultSet rs = prepared.executeQuery();
        if (rs.next()) {
          this.identity = Integer.valueOf(rs.getInt(1));
        }
        rs.close();
        prepared.close();

      }
      catch (SQLException sqle) {
        throw new UserException( -1, sqle.getMessage());
      }

      if (this.getDebug() && (this.identity != null)) {
          logger.debug(this.getClass() +
                           ": " + this.databaseProduct +
                           ": has generated new identifier '" + this.identity +
                           "'");
      }

    }
    return (this.identity);
  }

} // public class SequencedInsertMap extends SQLMap
// EOF: $RCSfile: SequencedInsertMap.java,v $ //