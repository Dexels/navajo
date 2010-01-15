package com.dexels.navajo.tipi.components.swingimpl;

import java.awt.Component;

import javax.swing.ImageIcon;

import com.dexels.navajo.tipi.components.core.TipiDataComponentImpl;
import com.dexels.navajo.tipi.components.swingimpl.swing.TipiDockable;
import com.javadocking.dock.*;
import com.javadocking.dockable.*;
import com.javadocking.dockable.action.*;
import com.javadocking.event.DockingEvent;
import com.javadocking.event.DockingListener;

public class TipiTabDock extends TipiDataComponentImpl {
	TabDock myTabDock;

	public Object createContainer() {
		runSyncInEventThread(new Runnable() {
			public void run() {
				myTabDock = new TabDock();				
			}
		});
		return myTabDock;
	}

	@Override
	public void addToContainer(final Object c, Object constraints) {
		String constr = "0";
		if(constraints!= null){
			constr = constraints.toString();			
		}
		final int intConstr = Integer.parseInt(constr);
		runSyncInEventThread(new Runnable() {
			public void run() {
				TipiDockable td = (TipiDockable)c;
				Dockable dockable = new DefaultDockable(c.hashCode() + "", (Component) c, td.getTitle(), new ImageIcon(td.getIcon()), (DockingMode.ALL - DockingMode.FLOAT) );
				((DefaultDockable)dockable).setDescription(td.getDescription());
				int[] states = {DockableState.NORMAL};
				if(td.getCloseable()){
					System.err.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Found closeable dock! " + td.getTitle());
					states = new int[]{DockableState.CLOSED,DockableState.NORMAL};
				}				
				dockable = new StateActionDockable(dockable, new DefaultDockableStateActionFactory(), states);
				td.setDockable(dockable);
				System.err.println("Adding " + td.getTitle() + " to dock");
				myTabDock.addDockable(dockable, new Position(intConstr));
			}
		});
	}
}
