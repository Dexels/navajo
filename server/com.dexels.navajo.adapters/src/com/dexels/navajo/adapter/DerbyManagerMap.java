package com.dexels.navajo.adapter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Properties;

import com.dexels.navajo.server.DispatcherFactory;
import com.dexels.navajo.server.NavajoConfigInterface;
import com.dexels.navajo.server.UserException;

public class DerbyManagerMap {
	public String driver = "org.apache.derby.jdbc.EmbeddedDriver";
	public String protocol = "jdbc:derby:";
	private File derbySystemDir;
	
	public DerbyManagerMap() {
		NavajoConfigInterface myConfig = DispatcherFactory.getInstance().getNavajoConfig();
		String path = myConfig.getResourcePath();
		if (path == null) {
			throw new IllegalStateException("No resource path found. Define paths/resource in the server.xml file."); 
		}
		File resourceSystemDir = new File(path);
		derbySystemDir = new File(resourceSystemDir,"derby");
		if (!derbySystemDir.exists()) {
			derbySystemDir.mkdirs();
		}
		System.setProperty("derby.system.home",derbySystemDir.getAbsolutePath());
	}

	public void setCreateDataSource(String name) throws UserException {
		try {
			Driver d = (Driver) Class.forName(driver).newInstance();
			DriverManager.registerDriver(d);
			Properties props = null;
			Connection conn = DriverManager.getConnection(protocol +name+";create=true", props);
			conn.close();
		}
		catch (Exception e) {
			throw new UserException(-1, "Error creating derby database", e);
		}
	}

	public void setShutdownDataSource(String name) throws UserException {
		try {
			Class.forName(driver).newInstance();
			Properties props = null;
			Connection conn = DriverManager.getConnection(protocol +name+";shutdown=true", props);
			conn.close();
		}
		catch (Exception e) {
			e.printStackTrace();
			System.err.println("Shutdown failed. That's ok, continuing.");
		}
	}

	public void setPurgeDataSource(String name) throws UserException {
		try {
			delete(derbySystemDir);
		}
		catch (Exception e) {
			throw new UserException(-1, "Error deleting derby database", e);
		}
	}
	
	private void delete(File f) throws IOException {
		  if (f.isDirectory()) {
		    for (File c : f.listFiles())
		      delete(c);
		  }
		  if (!f.delete())
		    throw new FileNotFoundException("Failed to delete file: " + f);
		}
}
