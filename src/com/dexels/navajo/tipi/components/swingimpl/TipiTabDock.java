package com.dexels.navajo.tipi.components.swingimpl;

import java.awt.Component;

import javax.swing.ImageIcon;

import com.dexels.navajo.tipi.components.core.TipiDataComponentImpl;
import com.dexels.navajo.tipi.components.swingimpl.swing.TipiDockable;
import com.javadocking.dock.Position;
import com.javadocking.dock.TabDock;
import com.javadocking.dockable.DefaultDockable;
import com.javadocking.dockable.Dockable;
import com.javadocking.dockable.DockableState;
import com.javadocking.dockable.DockingMode;
import com.javadocking.dockable.StateActionDockable;
import com.javadocking.dockable.action.DefaultDockableStateActionFactory;

public class TipiTabDock extends TipiDataComponentImpl {
	
	private static final long serialVersionUID = 3923115370762305969L;
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
