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
import org.eclipse.jface.action.*;
import org.eclipse.jface.viewers.*;
import org.eclipse.ui.*;

import com.dexels.navajo.studio.script.plugin.*;

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

    private static final String NATURE_ID = "com.dexels.plugin.NavajoNature";

    private ISelection selection;

    public void init(IWorkbenchWindow window) {
        // Ignored.
    }

    public void selectionChanged(IAction action, ISelection selection) {
        this.selection = selection;
    }

    public void run(IAction action) {
        if (!(selection instanceof IStructuredSelection))
            return;
        Iterator iter = ((IStructuredSelection) selection).iterator();
        while (iter.hasNext()) {
            Object element = iter.next();
            if (!(element instanceof IProject))
                continue;
            IProject project = (IProject) element;

            // Cannot modify closed projects.
            if (!project.isOpen())
                continue;

            // Get the description.
            IProjectDescription description;
            try {
                description = project.getDescription();
            } catch (CoreException e) {
                e.printStackTrace();
                //                FavoritesLog.logError(e);
                continue;
            }
            System.err.println("Found open project. Desc: "+description.getName());
            // Toggle the nature.
            List newIds = new ArrayList();
            newIds.addAll(Arrays.asList(description.getNatureIds()));
            int index = newIds.indexOf(NATURE_ID);
            if (index == -1) {
                System.err.println("Adding,,,");
                newIds.add(NATURE_ID);
            }
            else {
                System.err.println("Removing...");
                newIds.remove(index);
            }
            description.setNatureIds((String[]) newIds.toArray(new String[newIds.size()]));

            // Save the description.
            try {
                project.setDescription(description, null);
            } catch (CoreException e) {
                e.printStackTrace();
                //                FavoritesLog.logError(e);
            }
        }
    }

    public void dispose() {
    }

}