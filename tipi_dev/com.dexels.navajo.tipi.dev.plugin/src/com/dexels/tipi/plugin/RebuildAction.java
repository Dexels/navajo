package com.dexels.tipi.plugin;

import java.util.Iterator;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

public class RebuildAction implements IObjectActionDelegate {

	private ISelection selection;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	@SuppressWarnings("unchecked")
	public void run(IAction action) {
		if (selection instanceof IStructuredSelection) {
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
					final IFile settings = project.getFile("settings/tipi.properties");
					Job job = new Job("Manual Rebuild task") {
					     protected IStatus run(IProgressMonitor monitor) {
					   	  try {
									settings.touch(monitor);
								} catch (CoreException e) {
									e.printStackTrace();
								}
								return Status.OK_STATUS;
					        }
					     };
					  job.setPriority(Job.LONG);
					  job.schedule(); // start as soon as possible
				}
			}
		}
	}


	public void selectionChanged(IAction action, ISelection selection) {
		this.selection = selection;
	}


	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
	}



	

}
