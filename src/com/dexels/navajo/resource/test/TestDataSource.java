package com.dexels.navajo.resource.test;

import java.sql.SQLException;

import org.eclipse.osgi.framework.console.CommandInterpreter;
import org.eclipse.osgi.framework.console.CommandProvider;

public class TestDataSource implements CommandProvider {

	@Override
	public String getHelp() {
		return "\n---- Datasource test ----- \ntest datasources. Try: mongo, h2, mysql";
	}
	
//    context.registerService(CommandProvider.class.getName(), service, null); 
	public Object _test(CommandInterpreter intp) {
		try {
			String next = intp.nextArgument();
			if("h2".equals(next)) {
				ResourceManagerTest.getInstance().testH2();
			} else if("mysql".equals(next)) {
				ResourceManagerTest.getInstance().testMysql();
			} else if("mongo".equals(next)) {
				ResourceManagerTest.getInstance().testMongo();
			} else if("load".equals(next)) {
				ResourceManagerTest.getInstance().loadResources();
			} else if("jdbcmap".equals(next)) {
				ResourceManagerTest.getInstance().testJDBCMap2();
			} else if("resource".equals(next)) {
				ResourceManagerTest.getInstance().testLoadedMysql();
			} else {
				
				intp.println("\nUnknown.");
			}
		} catch (SQLException e) {
			intp.printStackTrace(e);
		} catch (Exception e) {
			intp.printStackTrace(e);
		}
        return null; 
   } 

}
