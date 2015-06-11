package com.dexels.navajo.tipi.components.rich;

import javax.swing.JComponent;

import com.dexels.navajo.rich.components.PeelPanel;
import com.dexels.navajo.tipi.components.swingimpl.TipiPanel;

public class TipiPeelPanel extends TipiPanel {

	private static final long serialVersionUID = 8431646615101260935L;
	private PeelPanel myPanel;
//	private int speed = 750;

	public TipiPeelPanel() {

	}

	@Override
	public Object createContainer() {
		myPanel = new PeelPanel();
		myPanel.setOpaque(false);
		return myPanel;
	}

	@Override
	public void addToContainer(final Object c, Object constraints) {
		runSyncInEventThread(new Runnable() {

			@Override
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

	

	public static void main(String[] args) {
		new TipiPeelPanel();
	}

}
