package com.dexels.navajo.tipi.components.rich;

import javax.swing.JComponent;

import com.dexels.navajo.rich.components.FlipPanel;
import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiComponentMethod;
import com.dexels.navajo.tipi.components.swingimpl.TipiPanel;
import com.dexels.navajo.tipi.internal.TipiEvent;

public class TipiFlipPanel extends TipiPanel {

	private static final long serialVersionUID = 2849056634148698485L;
	private FlipPanel myPanel;
	private int speed = 750;

	public TipiFlipPanel() {

	}

	public Object createContainer() {
		myPanel = new FlipPanel();
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

	public void flip() {
		myPanel.flipForwards();
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getSpeed() {
		return this.speed;
	}

	@Override
	protected void performComponentMethod(String name, TipiComponentMethod compMeth, TipiEvent event) throws TipiBreakException {
		super.performComponentMethod(name, compMeth, event);
		if ("flip".equals(name)) {
			runSyncInEventThread(new Runnable() {
				public void run() {
					flip();
				}
			});
		}
		if ("flipBackwards".equals(name)) {
			runSyncInEventThread(new Runnable() {
				public void run() {
					myPanel.flipBackwards();
				}
			});
		}

	}

	public static void main(String[] args) {
		new TipiFlipPanel();
	}

}
