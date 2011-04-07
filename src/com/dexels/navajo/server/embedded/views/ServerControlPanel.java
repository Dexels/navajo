package com.dexels.navajo.server.embedded.views;


import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.component.LifeCycle;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;
import org.osgi.framework.Bundle;

import com.dexels.navajo.server.embedded.EmbeddedServerActivator;


/**
 * This sample class demonstrates how to plug-in a new
 * workbench view. The view shows data obtained from the
 * model. The sample creates a dummy model on the fly,
 * but a real implementation would connect to the model
 * available either in this or another plug-in (e.g. the workspace).
 * The view is connected to the model using a content provider.
 * <p>
 * The view uses a label provider to define how model
 * objects should be presented in the view. Each
 * view can present the same model objects using
 * different labels and icons, if needed. Alternatively,
 * a single label provider can be shared between views
 * in order to ensure that objects of the same type are
 * presented in the same way everywhere.
 * <p>
 */

public class ServerControlPanel extends ViewPart {

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "com.dexels.navajo.server.embedded.views.ServerControlPanel";

	private Composite viewer;
	private Action startServerAction;
	private Action stopServerAction;

	/**
	 * The constructor.
	 */
	public ServerControlPanel() {
	}

	/**
	 * This is a callback that will allow us
	 * to create the viewer and initialize it.
	 */
	public void createPartControl(Composite parent) {
		viewer = new Composite(parent, SWT.NORMAL);
		// Create the help context id for the viewer's control
		PlatformUI.getWorkbench().getHelpSystem().setHelp(viewer, "com.dexels.navajo.server.embedded.viewer");
		makeActions();
		contributeToActionBars();
	}



	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		fillLocalToolBar(bars.getToolBarManager());
	}


	
	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(startServerAction);
		manager.add(stopServerAction);
	}

	private void makeActions() {
		startServerAction = new Action() {
			public void run() {
				try {
					dumpBundleStates();
					Server s = EmbeddedServerActivator.getDefault().startServer(8888,"/Users/frank/knvb");
					s.addLifeCycleListener(new LifeCycle.Listener() {
						
						@Override
						public void lifeCycleStopping(LifeCycle l) {
							
						}
						
						@Override
						public void lifeCycleStopped(LifeCycle l) {
							stopServerAction.setEnabled(false);
							startServerAction.setEnabled(true);
							
						}
						
						@Override
						public void lifeCycleStarting(LifeCycle l) {
							
						}
						
						@Override
						public void lifeCycleStarted(LifeCycle l) {
							stopServerAction.setEnabled(true);
							startServerAction.setEnabled(false);							
						}
						
						@Override
						public void lifeCycleFailure(LifeCycle l, Throwable e) {
							l.removeLifeCycleListener(this);
							stopServerAction.setEnabled(false);
							startServerAction.setEnabled(true);
						}
					});
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		startServerAction.setText("Start server");
		
		startServerAction.setToolTipText("Start Navajo server");
		startServerAction.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().
			getImageDescriptor(ISharedImages.IMG_TOOL_FORWARD));
		
		stopServerAction = new Action() {
			public void run() {
				EmbeddedServerActivator.getDefault().stopServer();
			}
		};
		stopServerAction.setEnabled(false);
		stopServerAction.setText("Stop server");
		stopServerAction.setToolTipText("Action 2 tooltip");
		stopServerAction.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_ELCL_STOP));
	
	}

	protected void dumpBundleStates() {
		Bundle myBundle = EmbeddedServerActivator.getDefault().getBundle();
		Bundle[] b = myBundle.getBundleContext().getBundles();
		for (Bundle bundle : b) {
			System.err.println("| > "+bundle.getSymbolicName()+" state: "+ bundle.getState());
			if(bundle.getState()==Bundle.ACTIVE) {
				System.err.println("Running");
			}
			
		}
		
	}

	private void showMessage(String message) {
		MessageDialog.openInformation(
			viewer.getShell(),
			"Control Panel",
			message);
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		viewer.setFocus();
	}
}