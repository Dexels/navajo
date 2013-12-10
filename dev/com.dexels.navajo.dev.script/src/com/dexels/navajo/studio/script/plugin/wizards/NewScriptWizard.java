package com.dexels.navajo.studio.script.plugin.wizards;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWizard;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.studio.script.plugin.NavajoPluginException;
import com.dexels.navajo.studio.script.plugin.NavajoScriptPluginPlugin;

/**
 * This is a sample new wizard. Its role is to create a new file resource in the
 * provided container. If the container resource (a folder or a project) is
 * selected in the workspace when the wizard is opened, it will accept it as the
 * target container. The wizard creates one file with the extension "tsl". If a
 * sample multi-page editor (also available as a template) is registered for the
 * same extension, it will be able to open it.
 */

public class NewScriptWizard extends Wizard implements INewWizard {
    private NewScriptWizardPage page;
    
	private final static Logger logger = LoggerFactory
			.getLogger(NewScriptWizard.class);
	

    protected IResource selectedFile = null;

  
    /**
     * Constructor for NewScriptWizard.
     */
    public NewScriptWizard() {
        super();
        setNeedsProgressMonitor(true);
    }

    /**
     * Adding the page to the wizard.
     */

    @Override
	public void addPages() {
        page = new NewScriptWizardPage();
        addPage(page);
    }

    /**
     * This method is called when 'Finish' button is pressed in the wizard. We
     * will create an operation and run it using wizard as execution context.
     */
    @Override
	public boolean performFinish() {
        final String scriptName = page.getScriptName();
        //		final String fileName = page.getFileName();
        IRunnableWithProgress op = new IRunnableWithProgress() {
            @Override
			public void run(IProgressMonitor monitor) throws InvocationTargetException {
                try {
                    doFinish(scriptName, monitor);
                } catch (Exception e) {
                    throw new InvocationTargetException(e);
                } finally {
                    monitor.done();
                }
            }
        };
        try {
            getContainer().run(true, false, op);
        } catch (InterruptedException e) {
            return false;
        } catch (InvocationTargetException e) {
            Throwable realException = e.getTargetException();
            MessageDialog.openError(getShell(), "Error", realException.getMessage());
            return false;
        }
        return true;
    }

    /**
     * The worker method. It will find the container, create the file if missing
     * or just replace its contents, and open the editor on the newly created
     * file.
     */

    private void doFinish(String scriptName, IProgressMonitor monitor) throws CoreException, NavajoPluginException {
        // create a sample file
        System.err.println("Creating file: " + scriptName);
        monitor.beginTask("Creating " + scriptName, 2);
        //		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();

        //		IResource resource = root.findMember(new Path(containerName));
        IResource resource = selectedFile;
        if (selectedFile == null) {
            NavajoScriptPluginPlugin.getDefaultWorkbench().getDisplay().syncExec(new Runnable() {

                @Override
				public void run() {
                    IEditorPart ipp = NavajoScriptPluginPlugin.getDefaultWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
                    if (ipp != null) {
                        selectedFile = (IResource) ipp.getEditorInput().getAdapter(IResource.class);
                    }

                }
            });
        }
        if (selectedFile == null) {
            throwCoreException("Please select a resource in the project you want to add the script to.");
        }
         IProject myProject = resource.getProject();
        System.err.println("Project: " + myProject);
        //		if (!resource.exists() ) {
        //			throwCoreException("Container \"" + scriptName + "\" does not
        // exist.");
        //		}
        //		IContainer container = (IContainer) resource;
        IFolder scriptFolder = NavajoScriptPluginPlugin.getDefault().getScriptFolder(myProject);
        System.err.println("Sciptfolder: " + scriptFolder);

        if (!scriptName.endsWith(".xml")) {
            scriptName = scriptName + ".xml";
        }

        final IFile file = scriptFolder.getFile(new Path(scriptName));

        System.err.println("file: " + file);
        try {
            InputStream stream = openContentStream();
            if (file.exists()) {
                file.setContents(stream, true, true, monitor);
            } else {
                checkParents(file.getParent(), monitor);
                file.create(stream, true, monitor);
            }
            stream.close();
        } catch (IOException e) {
        	logger.error("Error: ", e);
        }
        monitor.worked(1);
        monitor.setTaskName("Opening file for editing...");
        getShell().getDisplay().asyncExec(new Runnable() {
            @Override
			public void run() {
                IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
                try {
                    IDE.openEditor(page, file, true);
                } catch (PartInitException e) {
                }
            }
        });
        monitor.worked(1);
    }

    /**
     * @param file
     */
    private void checkParents(IResource file, IProgressMonitor monitor) throws CoreException {
        if (file == null) {
            return;
        }
        IResource parent = file.getParent();
        if (parent == null) {
            return;
        }
        if (parent.exists()) {
            return;
        } else {
            checkParents(parent, monitor);
            if (parent instanceof IFolder) {
                IFolder iffold = (IFolder) parent;
                iffold.create(true, false, monitor);
            }
        }
    }

    /**
     * We will initialize file contents with a sample text.
     */

    private InputStream openContentStream() {
        String contents = "<tsl/>";
        return new ByteArrayInputStream(contents.getBytes());
    }

    private void throwCoreException(String message) throws CoreException {
        IStatus status = new Status(IStatus.ERROR, "NavajoStudioPlugin", IStatus.OK, message, null);
        throw new CoreException(status);
    }

    /**
     * We will accept the selection in the workbench to see if we can initialize
     * from it.
     * 
     * @see IWorkbenchWizard#init(IWorkbench, IStructuredSelection)
     */
    @Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
        if (selection == null) {
            return;
        }
        IStructuredSelection iss = selection;
        Object oss = iss.getFirstElement();
        if (!(oss instanceof IResource)) {
            return;
        }
        selectedFile = (IResource) oss;
//        System.err.println("FILE::: " + selectedFile);
    }

}