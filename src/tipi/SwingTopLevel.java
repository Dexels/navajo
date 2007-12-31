package tipi;

import java.io.*;
import java.util.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.swingimpl.*;
import com.dexels.navajo.tipi.studio.*;

public class SwingTopLevel implements TipiStandaloneToplevelContainer {

	private SwingTipiContext myContext = null;
	public TipiContext getContext() {
		return myContext;
	}

//	public ArrayList getListeningServices() {
//		return getListeningServices();
//	}

	public void loadClassPathLib(String location) {

			System.err.println("Adding location. Thought it was deprecated.");
	}


	public void loadNavajo(Navajo nav, String method) throws TipiException, TipiBreakException {
		myContext.loadNavajo(nav, method);
	}

	public void shutDownTipi() {
		myContext.exit();
		myContext = null;
	}



	public void loadDefinition(String tipiPath, String definitionName, String resourceBaseDirectory) throws IOException, TipiException {
		// TODO Auto-generated method stub
		
	}

}
