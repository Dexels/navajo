/*
 * Created on May 9, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.studio.script.plugin.actions;

import java.util.Iterator;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import com.dexels.navajo.studio.script.plugin.NavajoScriptPluginPlugin;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class RemoveNavajoNatureAction extends BaseNavajoAction implements IWorkbenchWindowActionDelegate {

    /* (non-Javadoc)
     * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
     */
    @Override
	public void run(IAction action) {
              
           System.err.println("Performing add nature...");
           if (!(selection instanceof IStructuredSelection))
               return;
           @SuppressWarnings("rawtypes")
           Iterator iter = ((IStructuredSelection) selection).iterator();
           while (iter.hasNext()) {
               System.err.println("Looping through selection...");
               Object element = iter.next();

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
               
               removeNature(project);

             }
    }
   
       private void removeNature(IProject project) {
           try {
            NavajoScriptPluginPlugin.getDefault().removeNavajoNature(project);
        } catch (CoreException e) {
            NavajoScriptPluginPlugin.getDefault().log("Error removing nature",e);
        }
       }

    }


