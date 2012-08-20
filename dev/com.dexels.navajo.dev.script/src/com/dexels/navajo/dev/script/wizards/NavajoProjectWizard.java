package com.dexels.navajo.dev.script.wizards;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWizard;
import org.eclipse.ui.wizards.newresource.BasicNewProjectResourceWizard;

/**
 * This is a sample new wizard. Its role is to create a new file 
 * resource in the provided container. If the container resource
 * (a folder or a project) is selected in the workspace 
 * when the wizard is opened, it will accept it as the target
 * container. The wizard creates one file with the extension
 * "mpe". If a sample multi-page editor (also available
 * as a template) is registered for the same extension, it will
 * be able to open it.
 */

public class NavajoProjectWizard extends BasicNewProjectResourceWizard implements INewWizard {
//	private SampleNewWizardPage page;
//	private ISelection selection;

	/**
	 * Constructor for NavajoProjectWizard.
	 */
	public NavajoProjectWizard() {
		super();
		setNeedsProgressMonitor(true);
	}
	
	/**
	 * Adding the page to the wizard.
	 */

	@Override
	public void addPages() {
		super.addPages();
//		page = new SampleNewWizardPage(selection);
//		addPage(page);
	}

//	/**
//	 * This method is called when 'Finish' button is pressed in
//	 * the wizard. We will create an operation and run it
//	 * using wizard as execution context.
//	 */
	@Override
	public boolean performFinish() {
		return super.performFinish();
//		IProject ip = getNewProject();
//		System.err.println("Created: "+ip);
//		return true;
		
	}
//		final String containerName = page.getContainerName();
//		final String fileName = page.getFileName();
//		IRunnableWithProgress op = new IRunnableWithProgress() {
//			public void run(IProgressMonitor monitor) throws InvocationTargetException {
//				try {
//					doFinish(containerName, fileName, monitor);
//				} catch (CoreException e) {
//					throw new InvocationTargetException(e);
//				} finally {
//					monitor.done();
//				}
//			}
//		};
//		try {
//			getContainer().run(true, false, op);
//		} catch (InterruptedException e) {
//			return false;
//		} catch (InvocationTargetException e) {
//			Throwable realException = e.getTargetException();
//			MessageDialog.openError(getShell(), "Error", realException.getMessage());
//			return false;
//		}
//		return true;
//	}
	
//	/**
//	 * The worker method. It will find the container, create the
//	 * file if missing or just replace its contents, and open
//	 * the editor on the newly created file.
//	 */
//
//	private void doFinish(
//		String containerName,
//		String fileName,
//		IProgressMonitor monitor)
//		throws CoreException {
//		// create a sample file
//		monitor.beginTask("Creating " + fileName, 2);
//		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
//		IResource resource = root.findMember(new Path(containerName));
//		if (!resource.exists() || !(resource instanceof IContainer)) {
//			throwCoreException("Container \"" + containerName + "\" does not exist.");
//		}
//		IContainer container = (IContainer) resource;
//		final IFile file = container.getFile(new Path(fileName));
//		try {
//			InputStream stream = openContentStream();
//			if (file.exists()) {
//				file.setContents(stream, true, true, monitor);
//			} else {
//				file.create(stream, true, monitor);
//			}
//			stream.close();
//		} catch (IOException e) {
//		}
//		monitor.worked(1);
//		monitor.setTaskName("Opening file for editing...");
//		getShell().getDisplay().asyncExec(new Runnable() {
//			public void run() {
//				IWorkbenchPage page =
//					PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
//				try {
//					IDE.openEditor(page, file, true);
//				} catch (PartInitException e) {
//				}
//			}
//		});
//		monitor.worked(1);
//	}
	
	/**
	 * We will initialize file contents with a sample text.
	 */

//	private InputStream openContentStream() {
//		String contents =
//			"This is the initial file contents for *.mpe file that should be word-sorted in the Preview page of the multi-page editor";
//		return new ByteArrayInputStream(contents.getBytes());
//	}

//	private void throwCoreException(String message) throws CoreException {
//		IStatus status =
//			new Status(IStatus.ERROR, "com.dexels.navajo.dev.script", IStatus.OK, message, null);
//		throw new CoreException(status);
//	}

	/**
	 * We will accept the selection in the workbench to see if
	 * we can initialize from it.
	 * @see IWorkbenchWizard#init(IWorkbench, IStructuredSelection)
	 */
	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		super.init(workbench, selection);
		this.selection = selection;
	}
}