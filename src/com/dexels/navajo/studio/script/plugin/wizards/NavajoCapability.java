/*
 * Created on Apr 13, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.studio.script.plugin.wizards;

import org.eclipse.core.resources.*;
import org.eclipse.jface.viewers.*;
import org.eclipse.jface.wizard.*;
import org.eclipse.ui.*;
import org.eclipse.ui.internal.ide.registry.*;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class NavajoCapability extends Wizard implements ICapabilityInstallWizard {

    /**
     * @param natureId
     */
	public void init(IWorkbench workbench, IStructuredSelection selection, IProject project) {
	    System.err.println("AAAAAAAAAPT AAPT AATP!");
	}

    /* (non-Javadoc)
     * @see org.eclipse.jface.wizard.Wizard#performFinish()
     */
    public boolean performFinish() {
        // TODO Auto-generated method stub
        System.err.println("OK!!!");
        return false;
    }

}
