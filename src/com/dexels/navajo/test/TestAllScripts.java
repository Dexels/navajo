package com.dexels.navajo.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import junit.framework.Test;
import junit.framework.TestSuite;

public class TestAllScripts extends TestSuite {

	  public static junit.framework.Test suite() {
		  TestSuite ts = new TestSuite();
				try {
					
					ScriptTestContext dd = ScriptTestContext.getInstance();
		  BufferedReader ir = new BufferedReader( new InputStreamReader(TestAllScripts.class.getClassLoader().getResourceAsStream("test.txt")));
		String line = null;
			line = ir.readLine();
		do {
			System.err.println("Adding: "+line);
			try {
				Class c = Class.forName(line);
				ts.addTestSuite(c);
			} catch (Exception e) {
				e.printStackTrace();
			}
			line = ir.readLine();
			
		} while(line!=null);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		return ts;
	  }


}
