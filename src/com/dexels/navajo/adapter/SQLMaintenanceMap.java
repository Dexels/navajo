package com.dexels.navajo.adapter;


import com.dexels.navajo.mapping.*;
import com.dexels.navajo.server.*;
import com.dexels.navajo.document.*;
import com.dexels.navajo.document.jaxpimpl.xml.XMLutils;
import java.util.*;
import java.io.*;

/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author Arjen Schoneveld
 * @version 1.0
 */

public class SQLMaintenanceMap implements Mappable {

  public SQLMaintenanceMap() {
  }

  private Navajo sqlMapConfigFile;
  public SQLMapDatasourceMap [] datasources;
  public SQLMapDatasourceMap [] deleteDatasources;

  private boolean dirty = false;

  private Parameters parms;
  private Navajo inMessage;
  private Access access;
  private NavajoConfig config;

  private static boolean noAccess = false;

  public void load(Parameters parms, Navajo inMessage, Access access, NavajoConfig config) throws MappableException, UserException {

      if (noAccess)
        throw new MappableException("Cannot enter maintenance object, already in use");

      noAccess = true;
      System.out.println("In SQLMaintenanceMap");

      this.parms = parms;
      this.inMessage = inMessage;
      this.access = access;
      this.config = config;

      try {
//        sqlMapConfigFile = XMLutils.createNavajoInstance(config. getConfigPath() + "/sqlmap.xml");
        sqlMapConfigFile = config.readConfig("/sqlmap.xml");
      } catch (Exception e) {
        e.printStackTrace();
        throw new MappableException(e.getMessage());
      }
      System.out.println(sqlMapConfigFile.toString());
  }

  public synchronized void store() throws MappableException, UserException {
    noAccess = false;
  }

  public SQLMapDatasourceMap [] getDatasources() {

    ArrayList all = new ArrayList();
    ArrayList list = sqlMapConfigFile.getMessage("datasources").getAllMessages();
    for (int i = 0; i < list.size(); i++) {
        Message msg = (Message) list.get(i);
        SQLMapDatasourceMap dm = new SQLMapDatasourceMap();
        dm.datasourceName = msg.getName();
        dm.url = msg.getProperty("url").getValue();
        dm.logfile = msg.getProperty("logfile").getValue();
        dm.max_connections = Integer.parseInt(msg.getProperty("max_connections").getValue());
        dm.min_connections = Integer.parseInt(msg.getProperty("min_connections").getValue());
        dm.password = msg.getProperty("password").getValue();
        dm.refresh = Float.parseFloat(msg.getProperty("refresh").getValue());
        dm.username = msg.getProperty("username").getValue();
        dm.driver = msg.getProperty("driver").getValue();
        all.add(dm);
    }

    datasources = new SQLMapDatasourceMap[all.size()];
    datasources = (SQLMapDatasourceMap []) all.toArray(datasources);

    return datasources;
  }

  private void saveConfigFile(boolean copy) throws MappableException {
       // write new config to file.
        try {
          config.writeConfig("/sqlmap.xml",sqlMapConfigFile);
//          FileWriter f = new FileWriter(config.getConfigPath() + "/sqlmap.xml" + (copy ? "~" : ""), false);
//          f.write(sqlMapConfigFile.toString());
//          f.close();
        } catch (IOException ioe) {
          noAccess = false;
          throw new MappableException(ioe.getMessage());
        }
  }

  public synchronized void setDeleteDatasources(SQLMapDatasourceMap [] datasources) throws MappableException, UserException {

      saveConfigFile(true);

      SQLMap sqlMap = new SQLMap();
      dirty = false;
      for (int i = 0; i < datasources.length; i++) {
          Message msg = sqlMapConfigFile.getMessage("datasources").getMessage(datasources[i].getDatasourceName());
          if (msg != null) {
            dirty = true;
            sqlMapConfigFile.getMessage("datasources").removeMessage(msg);
          }
          sqlMap.setDeleteDatasource(datasources[i].datasourceName);
      }
      if (dirty) {  // rewrite sqlmap.xml property file!
        saveConfigFile(false);
      }
  }

  public synchronized void setDatasources(SQLMapDatasourceMap [] datasources) throws UserException, NavajoException, MappableException {

    saveConfigFile(true);

    for (int i = 0; i < datasources.length; i++) {
      SQLMapDatasourceMap datasource = datasources[i];
      this.dirty = true;
      Message msg = sqlMapConfigFile.getMessage("datasources").getMessage(datasource.getDatasourceName());
      if (msg == null) {
        msg = NavajoFactory.getInstance().createMessage(sqlMapConfigFile, datasource.getDatasourceName());
        sqlMapConfigFile.getMessage("datasources").addMessage(msg);
        Property prop = NavajoFactory.getInstance().createProperty(sqlMapConfigFile, "url", Property.STRING_PROPERTY, datasource.url,
                                        0, "", Property.DIR_IN);
        msg.addProperty(prop);
         prop = NavajoFactory.getInstance().createProperty(sqlMapConfigFile, "logfile", Property.STRING_PROPERTY, datasource.logfile,
                                        0, "", Property.DIR_IN);
        msg.addProperty(prop);
         prop = NavajoFactory.getInstance().createProperty(sqlMapConfigFile, "max_connections", Property.INTEGER_PROPERTY, ""+datasource.max_connections,
                                        0, "", Property.DIR_IN);
        msg.addProperty(prop);
         prop = NavajoFactory.getInstance().createProperty(sqlMapConfigFile, "min_connections", Property.INTEGER_PROPERTY, ""+datasource.min_connections,
                                        0, "", Property.DIR_IN);
        msg.addProperty(prop);
         prop = NavajoFactory.getInstance().createProperty(sqlMapConfigFile, "password", Property.STRING_PROPERTY, datasource.password,
                                        0, "", Property.DIR_IN);
        msg.addProperty(prop);
         prop = NavajoFactory.getInstance().createProperty(sqlMapConfigFile, "refresh", Property.STRING_PROPERTY, datasource.refresh+"",
                                        0, "", Property.DIR_IN);
        msg.addProperty(prop);
         prop = NavajoFactory.getInstance().createProperty(sqlMapConfigFile, "username", Property.STRING_PROPERTY, datasource.username,
                                        0, "", Property.DIR_IN);
        msg.addProperty(prop);
          prop = NavajoFactory.getInstance().createProperty(sqlMapConfigFile, "driver", Property.STRING_PROPERTY, datasource.driver,
                                        0, "", Property.DIR_IN);
        msg.addProperty(prop);
      } else {
        msg.getProperty("url").setValue(datasource.getUrl());
        msg.getProperty("logfile").setValue(datasource.getLogfile());
        msg.getProperty("max_connections").setValue(datasource.max_connections+"");
        msg.getProperty("min_connections").setValue(datasource.min_connections+"");
        msg.getProperty("password").setValue(datasource.password);
        msg.getProperty("refresh").setValue(datasource.refresh+"");
        msg.getProperty("username").setValue(datasource.username);
        msg.getProperty("driver").setValue(datasource.driver);
      }

        if (dirty) {
        saveConfigFile(false);
        // reload sqlmap.xml
        SQLMap sqlMap = new SQLMap();
        sqlMap.load(parms, inMessage, access, config);
        sqlMap.setReload(datasource.datasourceName);
      }
    }
  }

  public void kill() {
    noAccess = false;
  }
}
