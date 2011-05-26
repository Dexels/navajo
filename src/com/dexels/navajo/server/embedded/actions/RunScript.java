package com.dexels.navajo.server.embedded.actions;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import com.dexels.navajo.client.ClientException;
import com.dexels.navajo.client.context.NavajoContext;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.server.embedded.EmbeddedServerActivator;
import com.dexels.navajo.studio.script.plugin.NavajoPluginException;
import com.dexels.navajo.studio.script.plugin.NavajoScriptPluginPlugin;
import com.dexels.navajo.studio.script.plugin.ServerInstance;

/**
 * Our sample action implements workbench action delegate.
 * The action proxy will be created by the workbench and
 * shown in the UI. When the user tries to use the action,
 * this delegate will be created and execution will be 
 * delegated to it.
 * @see IWorkbenchWindowActionDelegate
 */
public class RunScript implements IWorkbenchWindowActionDelegate {
	private String scriptName;
	private IProject currentProject = null;
	/**
	 * The constructor.
	 */
	public RunScript() {
	}

	/**
	 * The action has been activated. The argument of the
	 * method represents the 'real' action sitting
	 * in the workbench UI.
	 * @see IWorkbenchWindowActionDelegate#run
	 */
	public void run(IAction action) {
		final String currentService = "examples/InitTestDirMap";
        Job j = new Job("Running "+currentService){

            protected IStatus run(IProgressMonitor monitor) {
            		try {
            			ServerInstance si = EmbeddedServerActivator.getDefault().getServerInstanceForProject(currentProject);
            			if(si==null) {
            				//
            				Display.getDefault().syncExec(new Runnable(){

								@Override
								public void run() {
									MessageDialog.openInformation(
		            						Display.getDefault().getActiveShell(),
		            						"Error",
		            						"Navajo instance not running!");
									
								}});
            				
            			} else {
                			NavajoContext nc = si.getNavajoContext();
                			
                			nc.callService(scriptName);
                			Navajo response = nc.getNavajo(scriptName);
                			NavajoScriptPluginPlugin.getDefault().injectNavajoResponse(response, scriptName);
            				
            			}
            		} catch (ClientException e) {
            			e.printStackTrace();
            		}
                    return Status.OK_STATUS;
            }};
            j.schedule();

		
		
	}

	/**
	 * Selection in the workbench has been changed. We 
	 * can change the state of the 'real' action here
	 * if we want, but this can only happen after 
	 * the delegate has been created.
	 * @see IWorkbenchWindowActionDelegate#selectionChanged
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		IStructuredSelection iss = (IStructuredSelection)selection;
        
		System.err.println("Selection size: "+iss.size());
		IResource irr = (IResource) iss.getFirstElement();
		IFile iff = (IFile)irr;
		if(iff==null) {
			scriptName=null;
			return;
		}
        try {
        	currentProject  = iff.getProject();
        	scriptName = NavajoScriptPluginPlugin.getDefault().getScriptNameFromResource(iff);
		} catch (NavajoPluginException e) {
			e.printStackTrace();
		}
	}

	/**
	 * We can use this method to dispose of any system
	 * resources we previously allocated.
	 * @see IWorkbenchWindowActionDelegate#dispose
	 */
	public void dispose() {
	}

	/**
	 * We will cache window object in order to
	 * be able to provide parent shell for the message dialog.
	 * @see IWorkbenchWindowActionDelegate#init
	 */
	public void init(IWorkbenchWindow window) {
	}
}