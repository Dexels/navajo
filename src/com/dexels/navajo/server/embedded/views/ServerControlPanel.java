package com.dexels.navajo.server.embedded.views;


import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.component.LifeCycle;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.GroupMarker;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;
import org.osgi.framework.Bundle;

import com.dexels.navajo.client.ClientException;
import com.dexels.navajo.client.context.NavajoContext;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.server.embedded.EmbeddedServerActivator;
import com.dexels.navajo.studio.script.plugin.views.TmlClientView;


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

//	private String serverURL = null;

//	private IProject currentProject = null;
	private NavajoContext localContext;
	
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
		makeActions("Navajo");
		contributeToActionBars();
	}

	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		fillLocalToolBar(bars.getToolBarManager());
	}

	
	 protected void fillMenuBar(IMenuManager menuBar) {
	        MenuManager fileMenu = new MenuManager("&File", IWorkbenchActionConstants.M_FILE);
	        MenuManager helpMenu = new MenuManager("&Help", IWorkbenchActionConstants.M_HELP);
	        
	        menuBar.add(fileMenu);
	        // Add a group marker indicating where action set menus will appear.
	        menuBar.add(new GroupMarker(IWorkbenchActionConstants.MB_ADDITIONS));
	        menuBar.add(helpMenu);
	        
	        // File
	        fileMenu.add(stopServerAction);
	        fileMenu.add(new Separator());
//	        fileMenu.add(messagePopupAction);
//	        fileMenu.add(openViewAction);
//	        fileMenu.add(new Separator());
//	        fileMenu.add(exitAction);
//	        
	        // Help
//	        helpMenu.add(aboutAction);
	    }
	
	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(startServerAction);
		manager.add(stopServerAction);
	}

	private void makeActions(final String projectName) {
		startServerAction = new Action() {
			private Server jettyServer;

			public void run() {
				try {

//					dumpBundleStates();
					LifeCycle.Listener lifecycleListener = new LifeCycle.Listener() {
						
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
							int port = jettyServer.getConnectors()[0].getPort();
							stopServerAction.setEnabled(true);
							startServerAction.setEnabled(false);							

							String server = "localhost:"+port+"/Postman";
							setupClient(server, "plugin","plugin");
							IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
							try {
								callPluginServices(project);
							} catch (CoreException e1) {
								e1.printStackTrace();
							}
							

						}
						
						@Override
						public void lifeCycleFailure(LifeCycle l, Throwable e) {
							l.removeLifeCycleListener(this);
							stopServerAction.setEnabled(false);
							startServerAction.setEnabled(true);
						}
					};
					jettyServer = EmbeddedServerActivator.getDefault().startServer(projectName,lifecycleListener).getServer();
					System.err.println("SErver: "+jettyServer);
					int port = jettyServer.getConnectors()[0].getPort();
					IWorkbenchWindow window = EmbeddedServerActivator.getDefault().getWorkbench().getActiveWorkbenchWindow();

				IWorkbenchPage page = window.getActivePage();	
				TmlClientView tw;
					tw = (TmlClientView) page.showView("com.dexels.TmlClientView");
					tw.setServerPort(port);

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

	protected void setupClient(String server, String user, String pass) {
		localContext = new NavajoContext();
		localContext.setupClient(server,user, pass);
		
	}

	protected void callPluginServices(IProject project) throws CoreException {
		try {
			localContext.callService("plugin/InitNavajoBundle");
			Navajo n = localContext.getNavajo("plugin/InitNavajoBundle");
			n.write(System.err);
			Binary b = (Binary) n.getProperty("NavajoBundle/FunctionDefinition").getTypedValue();
			IFolder iff = project.getFolder("navajoconfig");
			if(!iff.exists()) {
				iff.create(true, true, null);
			}
			IFile ifi = iff.getFile("functions.xml");
			if(!ifi.exists()) {
				ifi.create(b.getDataAsStream(), true, null);
			} else {
				ifi.setContents(b.getDataAsStream(), true, false,null);
				ifi.refreshLocal(1, null);
			}
		} catch (ClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NavajoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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