package com.dexels.navajo.adapter;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.adapter.sqlmap.SQLMapDatasourceMap;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.Mappable;
import com.dexels.navajo.script.api.MappableException;
import com.dexels.navajo.script.api.UserException;
import com.dexels.navajo.server.DispatcherFactory;
import com.dexels.navajo.server.NavajoConfigInterface;

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
  public int transactionContextCount;
  
private final static Logger logger = LoggerFactory
		.getLogger(SQLMaintenanceMap.class);


private boolean dirty = false;

  private Access access;
  private NavajoConfigInterface config;

  private static boolean noAccess = false;


  private static void setNoAccess(boolean noAccess) {
		SQLMaintenanceMap.noAccess = noAccess;
	}

public void load(Access access) throws MappableException, UserException {

      //if (noAccess)
      //  throw new MappableException("Cannot enter maintenance object, already in use");

      setNoAccess(true);
      logger.debug("In SQLMaintenanceMap");

      this.access = access;
      this.config = DispatcherFactory.getInstance().getNavajoConfig();

      try {
//        sqlMapConfigFile = XMLutils.createNavajoInstance(config. getConfigPath() + "/sqlmap.xml");
        sqlMapConfigFile = config.readConfig("/sqlmap.xml");
      } catch (Exception e) {
        throw new MappableException(e.getMessage());
      }
      //logger.debug(sqlMapConfigFile.toString());
  }

  public synchronized void store() throws MappableException, UserException {
	  setNoAccess(false);
  }

  public synchronized SQLMapDatasourceMap [] getDatasources() {

    List<SQLMapDatasourceMap> all = new ArrayList<SQLMapDatasourceMap>();
    List<Message> list = sqlMapConfigFile.getMessage("datasources").getAllMessages();
    for (int i = 0; i < list.size(); i++) {
        Message msg = list.get(i);
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
    datasources = all.toArray(datasources);

    return datasources;
  }

  /**
 * @param copy  
 */
private void saveConfigFile(boolean copy) throws MappableException {
	  
	  if (noAccess)
	        throw new MappableException("Cannot enter maintenance object in write mode, already in use");
	  
       // write new config to file.
        try {
          config.writeConfig("/sqlmap.xml",sqlMapConfigFile);
//          FileWriter f = new FileWriter(config.getConfigPath() + "/sqlmap.xml" + (copy ? "~" : ""), false);
//          f.write(sqlMapConfigFile.toString());
//          f.close();
        } catch (IOException ioe) {
        	setNoAccess(false);
          throw new MappableException(ioe.getMessage());
        }
  }


  public synchronized void setDatasources(SQLMapDatasourceMap [] datasources) throws UserException, NavajoException, MappableException {

	  if (noAccess)
	        throw new MappableException("Cannot enter maintenance object in write mode, already in use");
	  
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
        sqlMap.load(access);
        sqlMap.setReload(datasource.datasourceName);
      }
    }
  }

  
  public int getTransactionContextCount() {
	  return 0;
	
  }
  
  public void kill() {
	  setNoAccess(false);
  }
}
