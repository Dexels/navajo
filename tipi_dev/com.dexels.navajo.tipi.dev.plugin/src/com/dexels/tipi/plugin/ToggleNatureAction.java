package com.dexels.tipi.plugin;

import java.util.Iterator;
import java.util.ResourceBundle;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
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
//	private Combo templateCombo = null;

	private String selectedTemplate = null;
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
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

	      InputDialog repositoryDialog = new InputDialog(Display.getCurrent().getActiveShell(), "Tipi Repository","Choose your repository", getDefaultRepository(), null);
			String repository = null;
			if (repositoryDialog.open() == Window.OK) {
				repository = repositoryDialog.getValue();
			} else {
				System.err.println("No rep??! ");
				return;
			}
				
	        
			
//			TitleAreaDialog dlg = new TitleAreaDialog(Display.getCurrent().getActiveShell()) {
//				public Composite createDialogArea(Composite parent) {
//					Composite composite = (Composite) super.createDialogArea(parent);
//					templateCombo = new Combo(parent, SWT.READ_ONLY);
//					String[] retrieveTemplates = new String[] { "TemplateJnlpProject", "TemplateEchoProject"};
//
//					templateCombo.setItems(retrieveTemplates);
//					templateCombo.setSize(200, 200);
//					// add controls to composite as necessary
//					templateCombo.addSelectionListener(new SelectionListener(){
//
//						public void widgetDefaultSelected(SelectionEvent e) {
//							
//						}
//
//						public void widgetSelected(SelectionEvent e) {
//							 selectedTemplate = templateCombo.getItems()[templateCombo.getSelectionIndex()];
//						}});
//					templateCombo.select(0);
//					return composite;
//				}
//			};
//			
//			if (dlg.open() == Window.OK) {
//			} else {
//				System.err.println("No templ??! ");
//				return;
//			}
				
			selectedTemplate = "TemplateJnlpProject";
	//		downloadZippedDemoFiles(repository,repository+"Development/", project.getLocation().toFile(),selectedTemplate,null);
			ClientActions.downloadZippedDemoFiles(repository+"Development/",repository, project.getLocation().toFile(),selectedTemplate);

			project.refreshLocal(IResource.DEPTH_INFINITE, null);	
				System.err.println("Download completed");
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

//	private void downloadZippedDemoFiles(String repository,String developmentRepository, File projectDir, String templateName,IProgressMonitor monitor) throws IOException {
//		ClientActions.downloadZippedDemoFiles(developmentRepository, projectDir,templateName);
//		File f = new File(projectDir,"settings/tipi.properties");
//		FileWriter fw = new FileWriter(f,true);
//		fw.write("repository="+repository+"\n");
//		fw.flush();
//		fw.close();
//	}	
	
//	private void createDemoFiles(String repository,String developmentRepository, IProject project, String templateName,IProgressMonitor monitor) throws CoreException, IOException {
//			ClientActions.downloadDemoFiles(developmentRepository, project.getLocation().toFile(),templateName);
//			File f = new File(project.getLocation().toFile(),"settings/tipi.properties");
//			FileWriter fw = new FileWriter(f,true);
//			fw.write("repository="+repository+"\n");
//			fw.flush();
//			fw.close();
//			project.refreshLocal(IResource.DEPTH_INFINITE, monitor);
//	}

}
