package com.dexels.navajo.adapter.ibatis;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.DispatcherFactory;
import com.dexels.navajo.server.NavajoConfig;
import com.dexels.navajo.server.UserException;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

public abstract class SqlMap implements Mappable {

	/**
	   * SqlMapClient instances are thread safe, so you only need one.
	   * In this case, we'll use a static singleton.  So sue me.  ;-)
	   */
	  protected volatile static SqlMapClient sqlMapper;

	  /**
	   * It's not a good idea to put code that can fail in a class initializer,
	   * but for sake of argument, here's how you configure an SQL Map.
	   */
	  static {
	    try {
	    	InputStream is = ( DispatcherFactory.getInstance().getNavajoConfig() != null ? DispatcherFactory.getInstance().getNavajoConfig().getConfig("SqlMapConfig.xml") : null);
	    	if ( is == null ) {
	    		is = new FileInputStream("/home/arjen/projecten/NavajoStandardEdition/config/SqlMapConfig.xml");
	    	}
	    	sqlMapper = SqlMapClientBuilder.buildSqlMapClient(is);
	    	is.close();
	    } catch (IOException e) {
	      // Fail fast.
	      throw new RuntimeException("Something bad happened while building the SqlMapClient instance." + e, e);
	    }
	  }

	public void kill() {
	}

	public void load(Access access) throws MappableException, UserException {
	}

	public void store() throws MappableException, UserException {
	}

	
}
