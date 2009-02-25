package com.dexels.tipi.plugin;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.Iterator;
import java.util.PropertyResourceBundle;

import org.eclipse.ant.core.AntRunner;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.browser.IWebBrowser;
import org.eclipse.ui.browser.IWorkbenchBrowserSupport;

import com.dexels.navajo.tipi.ant.projectbuilder.TipiBuildDeployJnlp;
import com.dexels.navajo.tipi.projectbuilder.ClientActions;

public class DeployAction implements IObjectActionDelegate {

	private ISelection selection;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	public void run(IAction action) {
		if (selection instanceof IStructuredSelection) {
			for (Iterator it = ((IStructuredSelection) selection).iterator(); it
					.hasNext();) {
				Object element = it.next();
				IProject project = null;
				if (element instanceof IProject) {
					project = (IProject) element;
				} else if (element instanceof IAdaptable) {
					project = (IProject) ((IAdaptable) element)
							.getAdapter(IProject.class);
				}
				if (project != null) {
//					TipiNature.NATURE_ID
//					toggleNature(project);
					deployProject(project);
				}
			}
		}
	}

	private void deployProject(IProject project) {
		try {
			if(project.hasNature(TipiNature.NATURE_ID)) {
				IFile deploy = project.getFile("settings/deploy.properties");
				if(deploy.exists()) {
					System.err.println("Deploy settings: "+deploy.getLocation().toString());
					InputStream is = deploy.getContents();
					PropertyResourceBundle prp = new PropertyResourceBundle(is);
					is.close();
					String deployRoot = prp.getString("deployRoot");
					String deployRootSsh = prp.getString("deployRootSsh");
					runDeployScript(project, deployRoot, deployRootSsh);
					
				}
			}
		} catch (CoreException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void runDeployScript(IProject project,String deployRoot, String deployRootSsh) throws CoreException {
		System.err.println("Root: "+deployRoot+" ssh: "+deployRootSsh);
		IFile buildDeploy = project.getFile("settings/build.xml");
		runBuild(buildDeploy,null,deployRoot,deployRootSsh);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction,
	 *      org.eclipse.jface.viewers.ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		this.selection = selection;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IObjectActionDelegate#setActivePart(org.eclipse.jface.action.IAction,
	 *      org.eclipse.ui.IWorkbenchPart)
	 */
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
	}

	
	public void runBuild(IFile f, IProgressMonitor monitor,final String codebase, String deployRootSsh)  {
		if(!f.exists()) {
			System.err.println("File not found!");
			return;
		}
      InputDialog dlg = new InputDialog(Display.getCurrent().getActiveShell(),"SSH Login", "Deployment ssh password", "",null);			
      if (dlg.open() == Window.OK) {
   		try {
   			String sshPw = dlg.getValue();
   			AntRunner runner = new AntRunner();
//   			runner.set
   			runner.setBuildFileLocation(f.getLocation().toString());
   			runner.setArguments("-DprojectName="+f.getProject().getName()+" -Dmessage=Building -Dcodebase="+codebase+" -DdeployRootSsh="+deployRootSsh+" -DdeployPassword="+sshPw+" -DbaseDir="+f.getProject().getLocation().toString()+" -DdeployPath="+"deploy/current");
   			System.err.println("Starting run");
   			
   	//		runner.addBuildLogger("com.dexels.tipi.plugin.TipiBuildLogger");
   			runner.run(monitor);
   			System.err.println("Run completed.");
   		} catch (CoreException e) {
   			System.err.println("Showing error");
   	      ErrorDialog.openError(Display.getCurrent().getActiveShell(),"Tipi Deployment problem","Error building deploy", Status.CANCEL_STATUS);
   			e.printStackTrace();
   			
   		}
   		MessageDialog.openInformation(Display.getCurrent().getActiveShell(), "Thank you for deploying using the TipiPlugin!", codebase+"Application.jnlp");
   		Display.getDefault().asyncExec(new Runnable(){
				public void run() {
		 
   		IWorkbenchBrowserSupport browserSupport = PlatformUI.getWorkbench().getBrowserSupport();
   		 IWebBrowser browser;
			try {
				browser = browserSupport.createBrowser(IWorkbenchBrowserSupport.AS_VIEW, null,"Test", "Test");
				 URL url = new URL(codebase);
	   		 browser.openURL(url);
			} catch (Exception e) {
				e.printStackTrace();
			}
				}
   		});
   		try {
				f.getProject().refreshLocal(IResource.DEPTH_INFINITE, monitor);
			} catch (CoreException e) {
				e.printStackTrace();
			}
   		//   		Display.getCurrent().getActiveShell().
      }
		

	}

	

}
