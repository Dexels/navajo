/*
 * Created on Apr 27, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.studio.script.plugin.wizards;

import org.eclipse.core.resources.*;
import org.eclipse.jdt.internal.ui.wizards.*;
import org.eclipse.jface.viewers.*;
import org.eclipse.jface.wizard.*;
import org.eclipse.ui.*;
import org.eclipse.ui.internal.*;
import org.eclipse.ui.internal.ide.dialogs.*;

/**
 * @author Administrator
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class NewNavajoProjectWizard extends JavaProjectWizard implements IWizard {

    //    public void init(IWorkbench workbench, IStructuredSelection
    // currentSelection) {
    //        super.init(workbench, currentSelection);
    //// setInitialProjectCapabilities(new Capability[]{});
    //    }
    public void addPages() {
        super.addPages();
    }

    public boolean performFinish() {
        boolean b = super.performFinish();
        System.err.println("ADD NAVAJO CAPABILITY");
        ISelection is = Workbench.getInstance().getActiveWorkbenchWindow().getActivePage().getSelection();
        System.err.println("SElection: " + is.toString() + " class: " + is.getClass());
        if (is instanceof IStructuredSelection) {
            IStructuredSelection iss = (IStructuredSelection) is;
            IResource irr = (IResource) iss.getFirstElement();
            if (irr.exists()) {
                System.err.println(">>> " + irr.getFullPath());
            }
        }
        return b;

    }
}
