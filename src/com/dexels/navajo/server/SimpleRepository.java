package com.dexels.navajo.server;

/**
 * Title:        Navajo Product Project
 * Description:  This is the official source for the Navajo server
 * Copyright:    Copyright (c) 2002
 * Company:      Dexels BV
 * @author Arjen Schoneveld
 * @version 1.0
 */
import java.util.ResourceBundle;

public class SimpleRepository implements Repository {


  public NavajoConfig config;

  public SimpleRepository() {
  }

  public void setNavajoConfig(NavajoConfig config) {
    this.config = config;
  }

  public Access authorizeUser(String username, String password, String service) throws SystemException {
    return new Access(1, 1, 1, username, service, "", "", "");
  }
  public ConditionData[] getConditions(Access access) throws SystemException, UserException {
    return null;
  }
  public Parameter[] getParameters(Access access) throws SystemException {
    return null;
  }
  public void logTiming(Access access, int part, long timespent) throws SystemException {

  }
  public void logAction(Access access, int level, String comment) throws SystemException {

  }
  public String getServlet(Access access) throws SystemException {
    if (access.rpcName.startsWith("navajo"))
      return "com.dexels.navajo.server.MaintainanceHandler";
    else
      return "com.dexels.navajo.server.GenericHandler";
  }

  public String [] getServices(Access access) throws SystemException {
    return null;
  }
}