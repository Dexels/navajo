/*
 * Created on Apr 4, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.studio.script.plugin.actions;

import java.util.*;

import org.eclipse.core.internal.resources.*;
import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.eclipse.jface.action.*;
import org.eclipse.jface.dialogs.*;
import org.eclipse.jface.viewers.*;
import org.eclipse.ui.*;

import com.dexels.navajo.studio.script.plugin.*;

/**
 * @author Administrator
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class RecompileAction extends BaseNavajoAction {

    public RecompileAction() {
        super();
    }

    /**
     * The action has been activated. The argument of the method represents the
     * 'real' action sitting in the workbench UI.
     * 
     * @see IWorkbenchWindowActionDelegate#run
     */
    public void run(IAction action) {
            new WorkspaceJob("Recompiling..."){

               public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
                   try {
                       
                       for (Iterator iter = selectionList.iterator(); iter.hasNext();) {
                           IResource element = (IResource) iter.next();
                           element.touch(null);
                           if (element instanceof IFolder) {
                               IFolder ff = (IFolder)element;
                               touchRecursive(ff);
                           }
                       }
                       } catch (CoreException e) {
                           NavajoScriptPluginPlugin.getDefault().log("Touching failed. That is unexpected. Maybe refresh?",e);
                       }
                    return Status.OK_STATUS;
                }}.schedule();
     }

    	private void touchRecursive(IFolder fold) throws CoreException {
            IResource[] ir =  fold.members();
    	    for (int i = 0; i < ir.length; i++) {
                if (ir[i] instanceof IFolder) {
                    touchRecursive(((IFolder)ir[i]));
                } 
                ir[i].touch(null);
            }
    	}
}
