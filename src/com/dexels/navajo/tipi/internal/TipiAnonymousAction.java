package com.dexels.navajo.tipi.internal;

import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.tipixml.*;

public class TipiAnonymousAction  implements TipiExecutable {

	private final Runnable myRunnable;
	
	public TipiAnonymousAction(Runnable r) {
		myRunnable = r;
	}

	public String getBlockParam(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	public TipiComponent getComponent() {
		// TODO Auto-generated method stub
		return null;
	}

	public TipiEvent getEvent() {
		// TODO Auto-generated method stub
		return null;
	}

	public TipiExecutable getExecutableChild(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	public int getExecutableChildCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void performAction(TipiEvent te, TipiExecutable parent, int index) throws TipiBreakException, TipiException {
		myRunnable.run();

		
	}

	public void setEvent(TipiEvent e) {
		// TODO Auto-generated method stub
		
	}

	public XMLElement store() {
		// TODO Auto-generated method stub
		return null;
	}

}
