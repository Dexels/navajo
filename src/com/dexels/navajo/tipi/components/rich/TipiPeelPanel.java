package com.dexels.navajo.tipi.components.rich;

import javax.swing.*;

import com.dexels.navajo.rich.components.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.swingimpl.*;
import com.dexels.navajo.tipi.internal.*;

public class TipiPeelPanel extends TipiPanel {

	private static final long serialVersionUID = 8431646615101260935L;
	private PeelPanel myPanel;
//	private int speed = 750;

	public TipiPeelPanel() {

	}

	public Object createContainer() {
		myPanel = new PeelPanel();
		myPanel.setOpaque(false);
		return myPanel;
	}

	@Override
	public void addToContainer(final Object c, Object constraints) {
		runSyncInEventThread(new Runnable() {

			public void run() {
				myPanel.addComponent((JComponent) c);
			}
		});
	}

	@Override
	public void removeFromContainer(Object c) {
		myPanel.removeComponent((JComponent) c);
	}

	@Override
	public void setContainerLayout(Object layout) {
		// nop
	}

	
	@Override
	protected void performComponentMethod(String name, TipiComponentMethod compMeth, TipiEvent event) throws TipiBreakException {
		super.performComponentMethod(name, compMeth, event);
		
	}

	public static void main(String[] args) {
		new TipiPeelPanel();
	}

}
