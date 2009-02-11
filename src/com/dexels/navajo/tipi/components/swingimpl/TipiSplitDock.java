package com.dexels.navajo.tipi.components.swingimpl;

import java.awt.Component;

import javax.swing.ImageIcon;

import com.dexels.navajo.tipi.components.core.TipiDataComponentImpl;
import com.dexels.navajo.tipi.components.swingimpl.swing.TipiDockable;
import com.javadocking.dock.Dock;
import com.javadocking.dock.Position;
import com.javadocking.dock.SplitDock;
import com.javadocking.dock.TabDock;
import com.javadocking.dockable.DefaultDockable;
import com.javadocking.dockable.Dockable;
import com.javadocking.dockable.DockingMode;

public class TipiSplitDock extends TipiDataComponentImpl {
	SplitDock mySplitDock;

	public Object createContainer() {
		runSyncInEventThread(new Runnable() {
			public void run() {
				mySplitDock = new SplitDock();
			}
		});
		return mySplitDock;
	}

	@Override
	public void addToContainer(final Object c, Object constraints) {
		int pos = Position.CENTER;
		
		if("top".equalsIgnoreCase(constraints.toString())){
			pos = Position.TOP;
		} else if("left".equalsIgnoreCase(constraints.toString())){
			pos = Position.LEFT;
		} else if("bottom".equalsIgnoreCase(constraints.toString())){
			pos = Position.BOTTOM;
		} else if("right".equalsIgnoreCase(constraints.toString())){
			pos = Position.RIGHT;
		} else if("center".equalsIgnoreCase(constraints.toString())){
			pos = Position.CENTER;
		}  
		final int position = pos;
		
		runSyncInEventThread(new Runnable() {
			public void run() {
				mySplitDock.addChildDock((Dock)c, new Position(position));
			}
		});
	}
}
