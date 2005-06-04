package com.dexels.navajo.adapter.sqlmap;


import com.dexels.navajo.mapping.*;
import com.dexels.navajo.server.*;
import com.dexels.navajo.document.*;

/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author Arjen Schoneveld
 * @version 1.0
 */

 /**
  *  <property name="url" value="jdbc:sybase:Tds:cerberus.knvb.nl:5001/knvb"/>
      <property name="username" value="sportlink"/>
      <property name="password" value="sportlink"/>
      <property name="logfile" value="default.sqlmap"/>
      <property name="refresh" type="float" value="0.1"/>
      <property name="min_connections" type="integer" value="10"/>
      <property name="max_connections" type="integer" value="50"/>
  * <p>Title: Navajo Product Project</p>
  * <p>Description: This is the official source for the Navajo server</p>
  * <p>Copyright: Copyright (c) 2002</p>
  * <p>Company: Dexels BV</p>
  * @author Arjen Schoneveld
  * @version 1.0
  */
public class SQLMapDatasourceMap implements Mappable {

  public SQLMapDatasourceMap() {
  }

  public String datasourceName = "";
  public String url = "";
  public String username = "";
  public String password = "";
  public String logfile = "";
  public double refresh;
  public int min_connections;
  public int max_connections;
  public String driver;

  public void load(Parameters parms, Navajo inMessage, Access access, NavajoConfig config) throws MappableException, UserException {

  }

  public void store() throws MappableException, UserException {

  }

  public String getDatasourceName() {
    return this.datasourceName;
  }

  public void setDatasourceName(String s) {
    this.datasourceName = s;
  }

  public void kill() {

  }
  public String getLogfile() {
    return logfile;
  }
  public void setLogfile(String logfile) {
    this.logfile = logfile;
  }
  public void setMax_connections(int max_connections) {
    this.max_connections = max_connections;
  }
  public int getMax_connections() {
    return max_connections;
  }
  public int getMin_connections() {
    return min_connections;
  }
  public void setMin_connections(int min_connections) {
    this.min_connections = min_connections;
  }
  public void setPassword(String password) {
    this.password = password;
  }
  public void setRefresh(double refresh) {
    this.refresh = refresh;
  }
  public String getPassword() {
    return password;
  }
  public double getRefresh() {
    return refresh;
  }
  public String getUrl() {
    return url;
  }
  public void setUrl(String url) {
    this.url = url;
  }
  public void setUsername(String username) {
    this.username = username;
  }
  public String getUsername() {
    return username;
  }
  public void setDriver(String driver) {
    this.driver = driver;
  }
  public String getDriver() {
    return driver;
  }
}
