/*
 * Created on Mar 2, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.studio.script.plugin.actions;

import java.util.*;
import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.eclipse.jdt.core.*;
import org.eclipse.jface.action.*;
import org.eclipse.jface.viewers.*;
import org.eclipse.jface.wizard.*;
import org.eclipse.ui.*;
import org.eclipse.ui.internal.*;

import com.dexels.navajo.studio.script.plugin.*;
import com.dexels.navajo.studio.script.plugin.wizards.*;

/**
 * @author Administrator
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class NavajoNatureAction implements IWorkbenchWindowActionDelegate {

    /**
     *  
     */
    public NavajoNatureAction() {
        super();
    }

    //    private static final String NATURE_ID = "com.dexels.plugin.NavajoNature";

    private ISelection selection;

    public void init(IWorkbenchWindow window) {
        // Ignored.
    }

    public void selectionChanged(IAction action, ISelection selection) {
        this.selection = selection;
    }

    public void run(IAction action) {
         
        System.err.println("Performing add nature...");
        if (!(selection instanceof IStructuredSelection))
            return;
        Iterator iter = ((IStructuredSelection) selection).iterator();
        while (iter.hasNext()) {
            System.err.println("Looping through selection...");
            Object element = iter.next();
            if (element instanceof IJavaProject) {
                element = ((IJavaProject) element).getResource();
            }
            if (!(element instanceof IResource)) {
                System.err.println("Element: " + element + " is not a resource but a : " + element.getClass());
                continue;
            }
            IResource resource = (IResource) element;
            IProject project = resource.getProject();
            if (project == null) {
                System.err.println("It doesn't have a project. strange.");
                continue;
            }
            // Cannot modify closed projects.
            if (!project.isOpen()) {
                System.err.println("Its closed.");
                continue;
            }
            
            showWizard(project);

          }
    }

    private void showWizard(IProject project) {
        AddNavajoNatureWizard wizard = new AddNavajoNatureWizard(project);
        WizardDialog wizardDialog = new WizardDialog(
        Workbench.getInstance().getActiveWorkbenchWindow().getShell(),
        wizard);
        wizardDialog.create();
        wizardDialog.setTitle("");
        wizardDialog.open();
    }

    public void dispose() {
    }

}