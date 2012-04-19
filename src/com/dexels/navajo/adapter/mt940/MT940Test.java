package com.dexels.navajo.adapter.mt940;

import java.io.File;
import java.io.Serializable;

import com.dexels.navajo.adapter.MT940Map;
import com.dexels.navajo.document.types.Binary;


/**
 * Title:        Navajo Product Project
 * Description:  This is the official source for the Navajo server
 * Copyright:    Copyright (c) 2002
 * Company:      Dexels BV
 * @author Erik Versteeg
 * @version 1.0
 */
public class MT940Test implements Serializable {
	private static final long serialVersionUID = 922784734690300149L;

    public MT940Test() {}

	public static void main(String[] args) throws Exception {
    	// Read the file
//		String fileName = "C:\\Temp\\ABN_MT940.sta";
		String fileName = "C:\\Temp\\MT940_EXAMPLE.sta";
//		String fileName = "C:\\Temp\\MT940_EXAMPLE2.sta";
    	MT940Map mt940 = new MT940Map(new Binary(new File(fileName)));
    	mt940.setPrintOutputToConsole(true);
    	System.out.println("Selected bank: " + mt940.getMt940Layout().getSelectedBank());
    	mt940.importFile();
    	mt940.printEntries();
    }
}
