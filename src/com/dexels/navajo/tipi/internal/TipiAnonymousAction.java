package com.dexels.navajo.tipi.internal;

import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.tipixml.*;

public class TipiAnonymousAction  implements TipiExecutable {

	private final Runnable myRunnable;
	
	public TipiAnonymousAction(Runnable r) {
		myRunnable = r;
	}

	public String getBlockParam(String key) {
		return null;
	}

	public TipiComponent getComponent() {
		return null;
	}

	public TipiEvent getEvent() {
		return null;
	}

	public TipiExecutable getExecutableChild(int index) {
		return null;
	}

	public int getExecutableChildCount() {
		return 0;
	}

	public void performAction(TipiEvent te, TipiExecutable parent, int index) throws TipiBreakException, TipiException {
		myRunnable.run();

		
	}

	public void setEvent(TipiEvent e) {
		
	}

	public XMLElement store() {
		return null;
	}

	public TipiStackElement getStackElement() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setStackElement(TipiStackElement myStackElement) {
		// TODO Auto-generated method stub
		
	}

	public void dumpStack(String message) {
		// TODO Auto-generated method stub
		
	}

}
