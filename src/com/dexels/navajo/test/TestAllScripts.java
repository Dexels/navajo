package com.dexels.navajo.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import junit.framework.TestCase;
import junit.framework.TestSuite;

public class TestAllScripts extends TestSuite {

	public static junit.framework.Test suite() {
		TestSuite ts = new TestSuite("AllScripts");
		InputStream is = TestAllScripts.class.getClassLoader().getResourceAsStream("test.txt");
		loadAllTests(ts, is);

		return ts;
	}

	@SuppressWarnings("unchecked")
	public static void loadAllTests(TestSuite ts, InputStream is) {
		try {

			// ScriptTestContext dd = ScriptTestContext.getInstance();
			BufferedReader ir = new BufferedReader(new InputStreamReader(is));
			String line = null;
			line = ir.readLine();
			do {
				System.err.println("Adding: " + line);
				try {
					Class<? extends TestCase> c = (Class<? extends TestCase>) Class.forName(line);
					ts.addTestSuite(c);
				} catch (Exception e) {
					e.printStackTrace();
				}
				line = ir.readLine();

			} while (line != null);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

}
