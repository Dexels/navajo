/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.components.docker.swingimpl;

import java.awt.Component;

import javax.swing.ImageIcon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.components.core.TipiDataComponentImpl;
import com.dexels.navajo.tipi.components.docker.swingimpl.swing.TipiDockable;
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
	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiTabDock.class);
	
	TabDock myTabDock;

	@Override
	public Object createContainer() {
		runSyncInEventThread(new Runnable() {
			@Override
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
			@Override
			public void run() {
				TipiDockable td = (TipiDockable)c;
				Dockable dockable = new DefaultDockable(c.hashCode() + "", (Component) c, td.getTitle(), new ImageIcon(td.getIcon()), (DockingMode.ALL - DockingMode.FLOAT) );
				((DefaultDockable)dockable).setDescription(td.getDescription());
				int[] states = {DockableState.NORMAL};
				if(td.getCloseable()){
					logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Found closeable dock! " + td.getTitle());
					states = new int[]{DockableState.CLOSED,DockableState.NORMAL};
				}				
				dockable = new StateActionDockable(dockable, new DefaultDockableStateActionFactory(), states);
				td.setDockable(dockable);
				logger.info("Adding " + td.getTitle() + " to dock");
				myTabDock.addDockable(dockable, new Position(intConstr));
			}
		});
	}
}
