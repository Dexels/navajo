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
import com.dexels.navajo.document.*;
import com.dexels.navajo.xml.XMLutils;

/**
 * An implementation of the Repository interface.
 * This repository implements an SQL based database repository. It support MYSQL and MSSQL databases.
 */

public class SQLRepository implements Repository {

  private static HashMap properties;
  private static Authorisation authorisation = null;
  private static DbConnectionBroker myBroker = null;
  private static NavajoConfig navajoConfig;

  private void createConnectionBroker() {

      try {

        String configurationFileName = navajoConfig.getConfigPath() + "sql-repository.xml";
        Navajo config = XMLutils.createNavajoInstance(configurationFileName);
        Message body = config.getMessage("sql-configuration");

        Util.debugLog("Trying to open connection to database");
        Util.debugLog(body.getProperty("authorisation_driver").getValue());
        Util.debugLog(body.getProperty("authorisation_url").getValue());
        Util.debugLog(body.getProperty("authorisation_user").getValue());
        Util.debugLog(body.getProperty("authorisation_pwd").getValue());

        myBroker = new DbConnectionBroker(body.getProperty("authorisation_driver").getValue(),
                                          body.getProperty("authorisation_url").getValue(),
                                          body.getProperty("authorisation_user").getValue(),
                                          body.getProperty("authorisation_pwd").getValue(),
                                          2, 25, "/tmp/log.db", 0.1);

        Connection con = myBroker.getConnection();
        if (con == null) {
          myBroker = null;
        } else {
          Util.debugLog("Got test connection: " + con);
          myBroker.freeConnection(con);
          System.out.println("Opened connection to AUTHORISATION DB: " );
        }

        String dbms = body.getProperty("authorisation_dbms").getValue();
        if (dbms.equals("mysql"))
          this.authorisation = new Authorisation(Authorisation.DBMS_MYSQL, myBroker);
        else if (dbms.equals("mssql"))
          this.authorisation = new Authorisation(Authorisation.DBMS_MSSQL, myBroker);

      } catch (Exception e) {
        e.printStackTrace();
        Util.debugLog(e.getMessage());
        myBroker = null;
      }

  }

  public void setNavajoConfig(NavajoConfig config) {
    this.navajoConfig = config;
     if (myBroker == null)
      createConnectionBroker();
  }

  public SQLRepository() {
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