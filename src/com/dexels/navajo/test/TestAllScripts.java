package com.dexels.navajo.test;

	
public class TestAllScripts  {

	// TODO Suppressed all, no idea what to do with this thing
	
//	public static junit.framework.Test suite() {
//		TestSuite ts = new TestSuite("AllScripts");
//		InputStream is = TestAllScripts.class.getClassLoader().getResourceAsStream("test.txt");
//		loadAllTests(ts, is);
//
//		return ts;
//	}
//
//	@SuppressWarnings("unchecked")
//	public static void loadAllTests(TestSuite ts, InputStream is) {
//		try {
//
//			// ScriptTestContext dd = ScriptTestContext.getInstance();
//			BufferedReader ir = new BufferedReader(new InputStreamReader(is));
//			String line = null;
//			line = ir.readLine();
//			do {
//				System.err.println("Adding: " + line);
//				try {
//					Class<? extends TestCase> c = (Class<? extends TestCase>) Class.forName(line);
//					ts.addTestSuite(c);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//				line = ir.readLine();
//
//			} while (line != null);
//		} catch (IOException e1) {
//			e1.printStackTrace();
//		}
//	}

}
