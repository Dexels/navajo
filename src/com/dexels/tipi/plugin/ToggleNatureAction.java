package com.dexels.tipi.plugin;

import java.io.IOException;
import java.util.Iterator;
import java.util.ResourceBundle;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import com.dexels.navajo.tipi.projectbuilder.ClientActions;

public class ToggleNatureAction implements IObjectActionDelegate {

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
					toggleNature(project);
				}
			}
		}
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

	/**
	 * Toggles sample nature on a project
	 * 
	 * @param project
	 *            to have sample nature added or removed
	 */
	private void toggleNature(IProject project) {
		try {
			IProjectDescription description = project.getDescription();
			String[] natures = description.getNatureIds();

			for (int i = 0; i < natures.length; ++i) {
				if (TipiNature.NATURE_ID.equals(natures[i])) {
					// Remove the nature
					String[] newNatures = new String[natures.length - 1];
					System.arraycopy(natures, 0, newNatures, 0, i);
					System.arraycopy(natures, i + 1, newNatures, i,
							natures.length - i - 1);
					description.setNatureIds(newNatures);
					project.setDescription(description, null);
					return;
				}
			}

	       InputDialog dlg = new InputDialog(Display.getCurrent().getActiveShell(),"Tipi Repository", "Choose your repository", getDefaultRepository(),null);			
	        if (dlg.open() == Window.OK) {
	           // User clicked OK; update the label with the input
	           //label.setText(dlg.getValue());
	         }
			// Add the nature
	        // try this first, so the nature won't be added if it fails
				createDemoFiles(dlg.getValue(), project,null);

	        String[] newNatures = new String[natures.length + 1];
			System.arraycopy(natures, 0, newNatures, 0, natures.length);
			newNatures[natures.length] = TipiNature.NATURE_ID;
			
			description.setNatureIds(newNatures);
			project.setDescription(description, null);
		} catch (Exception e) {
			System.err.println("Core ex");
			e.printStackTrace();
	       Status status = new Status(IStatus.ERROR, "TipiPlugin", 0,e.getMessage(), null);
	       ErrorDialog.openError(Display.getCurrent().getActiveShell(),"Tipi Repository problem","Error downloading from repository!", status);
		}
	}

	private String getDefaultRepository() {
		ResourceBundle b = ResourceBundle.getBundle("tipiplugin");
		return b.getString("defaultRepository");
	}

	private void createDemoFiles(String repository, IProject project, IProgressMonitor monitor) throws CoreException, IOException {
			ClientActions.downloadDemoFiles(repository, project.getLocation().toFile());
			project.refreshLocal(IResource.DEPTH_INFINITE, monitor);
		
	}

}
