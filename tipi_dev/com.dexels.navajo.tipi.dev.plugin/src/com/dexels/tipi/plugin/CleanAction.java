package com.dexels.tipi.plugin;

import java.util.Iterator;

import org.eclipse.ant.core.AntRunner;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.projectbuilder.ClientActions;

public class CleanAction implements IObjectActionDelegate {

	
	private final static Logger logger = LoggerFactory
			.getLogger(CleanAction.class);
	private ISelection selection;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	public void run(IAction action) {
		if (selection instanceof IStructuredSelection) {
			ClientActions.flushCache();
			for (Iterator<IStructuredSelection> it = ((IStructuredSelection) selection).iterator(); it
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
					cleanProject(project);
				}
			}
		}
	}

	private void cleanProject(IProject project) {
		try {
			if(project.hasNature(TipiNature.NATURE_ID)) {
					runCleanScript(project);
			}
		} catch (CoreException e) {
			logger.error("Error: ",e);
		}
	}

	private void runCleanScript(IProject project)  {
		IFile buildDeploy = project.getFile("settings/build.xml");
		runClean(project,buildDeploy,null,"clean");
	}


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

	
	public void runClean(IProject project, IFile f, IProgressMonitor monitor, String target)  {
		if(!f.exists()) {
			logger.info("File not found!");
			return;
		}
   		try {
   			AntRunner runner = new AntRunner();
//   			runner.set
   			runner.setBuildFileLocation(f.getLocation().toString());
   			runner.setExecutionTargets(new String[]{target});
   			runner.setArguments("-DprojectName="+f.getProject().getName()+"-DbaseDir="+f.getProject().getLocation().toString());
   			runner.run(monitor);
   			logger.info("Run completed.");
   		    boolean b = MessageDialog.openConfirm(Display.getCurrent().getActiveShell(), "Confirm", "Do a rebuild now?");
   		    if(b) {
   		   		if (project != null) {
   						IFile settings = project.getFile("settings/tipi.properties");
   						try {
   							settings.touch(null);
   						} catch (CoreException e) {
   							logger.error("Error: ",e);
   						}
   					}
   		    }
   	        
   		
   		
   		} catch (CoreException e) {
   			logger.info("Showing error");
   	      ErrorDialog.openError(Display.getCurrent().getActiveShell(),"Tipi Deployment problem","Error building deploy", Status.CANCEL_STATUS);
   			logger.error("Error: ",e);
   			
   		}

   		try {
				f.getProject().refreshLocal(IResource.DEPTH_INFINITE, monitor);
			} catch (CoreException e) {
				logger.error("Error: ",e);
			}
   		//   		Display.getCurrent().getActiveShell().
		

	}

	

}
