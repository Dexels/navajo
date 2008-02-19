package com.dexels.navajo.tipi.actions;

import java.io.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.internal.*;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */
public class TipiLoadStateNavajo extends TipiAction {
	public void execute(TipiEvent event) throws com.dexels.navajo.tipi.TipiException, com.dexels.navajo.tipi.TipiBreakException {
		try {
			FileWriter fw = new FileWriter("c:/state.xml");
			
//			myContext.getStateNavajo().write(System.err);
			myContext.getStateNavajo().write(fw);
			fw.flush();
			fw.close();
		} catch (NavajoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		myContext.loadNavajo(myContext.getStateNavajo(),"StateNavajo");
	}
}