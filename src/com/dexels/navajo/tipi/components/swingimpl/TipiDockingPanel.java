package com.dexels.navajo.tipi.components.swingimpl;

import java.awt.Container;
import java.awt.Window;
import java.awt.event.ActionEvent;

import javax.swing.SwingUtilities;

import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiComponentMethod;
import com.dexels.navajo.tipi.components.swingimpl.swing.TipiDockable;
import com.dexels.navajo.tipi.components.swingimpl.swing.TipiSwingSplitDock;
import com.dexels.navajo.tipi.internal.TipiEvent;
import com.javadocking.DockingManager;
import com.javadocking.dock.*;
import com.javadocking.dockable.*;
import com.javadocking.dockable.action.DefaultDockableStateAction;
import com.javadocking.model.DockModel;
import com.javadocking.model.FloatDockModel;
import com.javadocking.util.LookAndFeelUtil;

public class TipiDockingPanel extends TipiPanel {
	DockModel dockModel;
	TipiSwingSplitDock myContainer;
	@Override
	
	public Object createContainer() {
		Container container = (Container)super.createContainer();
//		return container;
		myContainer = new TipiSwingSplitDock(this);
		return myContainer;
	}

	@Override
	public void addToContainer(final Object c, final Object constraints) {	
		
		// WARNING! <----------------------------------------------------------------------
		// Putting this inside the invokeLater is needed for the Stadium Design application
		// Without it the docking panels will fail upon startup.
		
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				// See if a global docModel is allready set, if so use that
				dockModel = DockingManager.getDockModel();
				if(dockModel == null){			
					dockModel =  new FloatDockModel();
					dockModel.addOwner("init", SwingUtilities.getWindowAncestor(myContainer));
					DockingManager.setDockModel(dockModel);			
					
				}
				
				// Check if current dock is not allready in the model
				if(dockModel.getRootDock(getId()) == null){	
					dockModel.addRootDock(getId(), myContainer, (Window)myContext.getTopLevel());
				}
				
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
				runSyncInEventThread(new Runnable(){
					public void run(){
						System.err.println("--> Adding doc to toplevel");
						myContainer.addChildDock((Dock)c, new Position(position));
					}
				});
				
			}
		});
		
	}

	@Override
	public void removeFromContainer(Object c) {
//		System.err.println("Removing: );
		if(c instanceof Dock){
			dockModel.removeRootDock((Dock)c);
		}
	}

	@Override
	public void setContainerLayout(Object layout) {
		// TODO Auto-generated method stub
		super.setContainerLayout(layout);
	}

	@Override
	protected void addedToParent() {		
		
	}

	@Override
	protected void performComponentMethod(String name, TipiComponentMethod compMeth, TipiEvent event) throws TipiBreakException {
		super.performComponentMethod(name, compMeth, event);
		
		if("openDockable".equals(name)){
			TipiDockablePanel dockPanel = (TipiDockablePanel)compMeth.getEvaluatedParameter("dockingpanel", event).value;
			TipiDockable dock = (TipiDockable)dockPanel.getContainer();
			openDockable(dock.getDockable());
		}
		if("closeDockable".equals(name)){
			TipiDockablePanel dockPanel = (TipiDockablePanel)compMeth.getEvaluatedParameter("dockingpanel", event).value;
			TipiDockable dock = (TipiDockable)dockPanel.getContainer();
			closeDockable(dock.getDockable());
		}
		
	}
	
	private void openDockable(Dockable dockable){
		final DefaultDockableStateAction restoreAction = new DefaultDockableStateAction(dockable, DockableState.NORMAL);
		try{
			runSyncInEventThread(new Runnable(){
				public void run(){
					restoreAction.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "Restore"));
				}
			});
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private void closeDockable(Dockable dockable){
		final DefaultDockableStateAction closeAction = new DefaultDockableStateAction(dockable, DockableState.CLOSED);
		try{
			runSyncInEventThread(new Runnable(){
				public void run(){
					closeAction.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "Close"));
				}
			});
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	

}
