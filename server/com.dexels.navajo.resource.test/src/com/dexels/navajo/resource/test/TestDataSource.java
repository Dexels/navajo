package com.dexels.navajo.resource.test;

import java.sql.SQLException;

import org.apache.felix.service.command.CommandSession;
import org.apache.felix.service.command.Descriptor;
import org.osgi.framework.InvalidSyntaxException;

public class TestDataSource  {

//	@Override
//	public String getHelp() {
//		return "\n---- Datasource test ----- \ntest datasources. Try: mongo, h2, mysql";
//	}
	
////    context.registerService(CommandProvider.class.getName(), service, null); 
//	public Object _test() {
//		try {
//			String next = intp.nextArgument();
//			if("h2".equals(next)) {
////				h2test();
//			} else if("mysql".equals(next)) {
////				mysqltest();
//			} else if("oracle".equals(next)) {
//				oracletest();
//			} else if("mongo".equals(next)) {
//				mongotest();
//			} else if("load".equals(next)) {
//				loadresourcetest();
//			} else if("jdbcmap".equals(next)) {
//				testjdbc();
//			} else if("aaa".equals(next)) {
//				aaatest();
//			} else if("client".equals(next)) {
//				clienttest();
//			}
//		} catch (SQLException e) {
//			intp.printStackTrace(e);
//		} catch (Exception e) {
//			intp.printStackTrace(e);
//		}
//        return null; 
//   }

	protected void clienttest() {
		ResourceManagerTest.getInstance().testClient();
	}

	protected void aaatest() throws InvalidSyntaxException, SQLException {
		ResourceManagerTest.getInstance().testAAA();
	}

	@Descriptor(value = "test jdbc connectivity")
	protected void testjdbc() throws Exception {
		ResourceManagerTest.getInstance().testJDBCMap2();
	}



	@Descriptor(value = "test mongodb connectivity")
	protected void mongotest() throws Exception {
		ResourceManagerTest.getInstance().testMongo();
	}

	@Descriptor(value = "test oracle connectivity")
	protected void oracletest() throws Exception, SQLException {
		ResourceManagerTest.getInstance().testOracle();
	}

	@Descriptor(value = "test mysql connectivity")
	protected void mysqltest(CommandSession cc) throws SQLException, InvalidSyntaxException  {
		ResourceManagerTest.getInstance().testMysqlService();
	}
//	public void check(CommandSession session, @Descriptor(value = "The path of the script") String script,@Descriptor(value = "The current tenant")  String tenant) throws FileNotFoundException {

	@Descriptor(value = "test h2 connectivity") 
	public void h2test(CommandSession cc) throws Exception, SQLException {
		ResourceManagerTest.getInstance().testH2();
	} 

	@Descriptor(value = "test grus connectivity")  
	public void test(CommandSession cc, int iterations) throws Exception, SQLException {
		long stamp = System.currentTimeMillis();
		for (int i = 0; i < iterations; i++) {
			ResourceManagerTest.getInstance().testSqlMap(); //testGrus();
			System.err.println("Iteration:"+i);
		}
		System.err.println("elapsed: "+(System.currentTimeMillis() - stamp));
	} 

}
