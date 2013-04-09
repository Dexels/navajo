package com.dexels.navajo.resource.jdbc.oracle;

import java.io.IOException;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;


import org.osgi.framework.InvalidSyntaxException;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.jdbc.DataSourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.resource.jdbc.JdbcTunnelBaseImpl;

public class TunnelOracleImpl extends JdbcTunnelBaseImpl{

	private final Map<String,Configuration> resourcePids = new HashMap<String,Configuration>();
	
	private final static Logger logger = LoggerFactory
			.getLogger(TunnelOracleImpl.class);
	
	private ConfigurationAdmin configAdmin = null;
	
	
	@Override
	public void activate(Map<String, Object> settings) {
		String localhost = super.getTunnel().getLocalHost();
		int localport = super.getTunnel().getLocalPort();
		String sid = (String) settings.get("sid");
		String url = createDbUrl(localhost,localport,sid);
		String user = (String) settings.get(DataSourceFactory.JDBC_USER);
		String password = (String) settings.get(DataSourceFactory.JDBC_PASSWORD);
		final Integer minPoolSize = (Integer)settings.get(DataSourceFactory.JDBC_MIN_POOL_SIZE);
		final Integer maxPoolSize = (Integer)settings.get(DataSourceFactory.JDBC_MAX_POOL_SIZE);

//		DataSource source = createPooledConnection(base,(String) props.get(DataSourceFactory.JDBC_USER), (String)props.get(DataSourceFactory.JDBC_PASSWORD),  minPoolSize,  (Integer)settings.get(DataSourceFactory.JDBC_MAX_POOL_SIZE));

	}

	private String createDbUrl(String localhost, int localport, String sid) {
		return "jdbc:oracle:thin:@"+localhost+":"+localport+":"+sid;
	}


	@Override
	public void deactivate() {
		super.deactivate();
		logger.info("TunnelOracle deactivate");
		for (Entry<String,Configuration> entry : resourcePids.entrySet()) {
			try {
				entry.getValue().delete();
			} catch (IOException e) {
				logger.error("Problem deleting configuration for pid: "+entry.getKey(),e);
			}
		}
	}

	private void emitConfig(String pid, Dictionary<String,Object> settings) throws IOException {
		Configuration config =  configAdmin.getConfiguration(pid,null);
		updateIfChanged(config, settings);
	}

	private void emitFactoryIfChanged(String pid, String filter,Dictionary<String,Object> settings) throws IOException {
		updateIfChanged(createOrReuseFactoryConfiguration(pid, filter), settings);
	}
	
	protected Configuration createOrReuseFactoryConfiguration(String pid, final String filter)
			throws IOException {
		Configuration cc = null;
		try {
			Configuration[] c = configAdmin.listConfigurations(filter);
			if(c!=null && c.length>1) {
				logger.warn("Multiple configurations found for filter: {}", filter);
			}
			if(c!=null && c.length>0) {
				cc = c[0];
			}
		} catch (InvalidSyntaxException e) {
			logger.error("Error in filter: {}",filter,e);
		}
		if(cc==null) {
			cc = configAdmin.createFactoryConfiguration(pid,null);
			resourcePids.put(cc.getPid(),cc);
		}
		return cc;
	}
	
	private void updateIfChanged(Configuration c, Dictionary<String,Object> settings) throws IOException {
		Dictionary<String,Object> old = c.getProperties();
		if(old!=null) {
			if(!old.equals(settings)) {
				c.update(settings);
			} else {
				logger.info("Ignoring equal");
			}
		} else {
			// this will make this component 'own' this configuration, unsure if this is desirable.
			resourcePids.put(c.getPid(),c);
			c.update(settings);
		}
	}
	
	
	
}
