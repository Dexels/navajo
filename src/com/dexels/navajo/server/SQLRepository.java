package com.dexels.navajo.server;

/**
 * Title:        Navajo Product Project
 * Description:  This is the official source for the Navajo server
 * Copyright:    Copyright (c) 2002
 * Company:      Dexels BV
 * @author Arjen Schoneveld
 * @version 1.0
 */
import java.util.*;
import java.sql.*;
import org.dexels.grus.DbConnectionBroker;
import com.dexels.navajo.util.*;

/**
 * An implementation of the Repository interface.
 * This repository implements an SQL based database repository. It support MYSQL and MSSQL databases.
 */

public class SQLRepository implements Repository {

  private static ResourceBundle properties;
  private static Authorisation authorisation = null;
  private static DbConnectionBroker myBroker = null;

  private void createConnectionBroker() {

      try {
        Util.debugLog("Trying to open connection to database");
        Util.debugLog(properties.getString("authorisation_driver"));
        Util.debugLog(properties.getString("authorisation_url"));
        Util.debugLog(properties.getString("authorisation_user"));
        Util.debugLog(properties.getString("authorisation_pwd")+"");
        Util.debugLog(properties.getString("navajo_logon"));

        myBroker = new DbConnectionBroker(properties.getString("authorisation_driver"),
                                          properties.getString("authorisation_url"),
                                          properties.getString("authorisation_user"),
                                          properties.getString("authorisation_pwd"),
                                          2, 25, "/tmp/log.db", 0.1);

        Connection con = myBroker.getConnection();
        if (con == null) {
          myBroker = null;
        } else {
          Util.debugLog("Got test connection: " + con);
          myBroker.freeConnection(con);
          System.out.println("Opened connection to AUTHORISATION DB: " + properties.getString("authorisation_url"));
        }

      } catch (Exception e) {
        Util.debugLog(e.getMessage());
        myBroker = null;
      }

  }

  public SQLRepository() {
  }

  public void setResourceBundle(ResourceBundle b) {
    this.properties = b;
    if (myBroker == null)
      createConnectionBroker();
    String dbms = properties.getString("authorisation_dbms");
    if (dbms.equals("mysql"))
      this.authorisation = new Authorisation(Authorisation.DBMS_MYSQL, myBroker);
    else if (dbms.equals("mssql"))
      this.authorisation = new Authorisation(Authorisation.DBMS_MSSQL, myBroker);
  }

  public Access authorizeUser(String username, String password, String service) throws SystemException {
    return authorisation.authorizeUser(myBroker, username, password, service, "", "", "", false);
  }

  public ConditionData [] getConditions(Access access) throws SystemException, UserException {
    return authorisation.checkConditions(access);
  }

  public Parameter [] getParameters(Access access) throws SystemException {
    return authorisation.getParameters(access);
  }

  public void logTiming(Access access, int part, long timespent) throws SystemException {
    authorisation.logTiming(access, part, timespent);
  }

  public void logAction(Access access, int level, String comment)
                     throws SystemException {
      authorisation.logAction(access, level, comment);
  }

  public String getServlet(Access access) throws SystemException {
    return authorisation.getServlet(access);
  }

  public Authorisation getAuthorisation() {
    return this.authorisation;
  }

  public String [] getServices(Access access) throws SystemException {
      Vector v = authorisation.allServices(access);
      String [] all = new String[v.size()];
      all = (String []) v.toArray(all);
      return all;
  }
}