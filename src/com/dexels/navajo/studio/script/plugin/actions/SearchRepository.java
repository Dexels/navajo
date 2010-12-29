package com.dexels.navajo.studio.script.plugin.actions;

import java.util.ArrayList;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.action.IAction;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import com.dexels.navajo.studio.script.plugin.NavajoScriptPluginPlugin;

/**
 * Our sample action implements workbench action delegate. The action proxy will
 * be created by the workbench and shown in the UI. When the user tries to use
 * the action, this delegate will be created and execution will be delegated to
 * it.
 * 
 * @see IWorkbenchWindowActionDelegate
 */
public class SearchRepository extends BaseNavajoAction {
    //	private IEditorPart myEditor = null;
    //	private IWorkbenchWindow window;
    //    private ISelection selection;
    /**
     * The constructor.
     */
    public SearchRepository() {
    }

    /**
     * The action has been activated. The argument of the method represents the
     * 'real' action sitting in the workbench UI.
     * 
     * @see IWorkbenchWindowActionDelegate#run
     */
    public void run(IAction action) {
        System.err.println("Running REPOSITORY SEARCH!!!!!!!!!");
        if (folder == null) {
            if (file == null) {
                return;
            }
            folder = file.getProject();
        }
        if (!(folder instanceof IProject)) {
            if (folder != null) {
                folder = folder.getProject();
            } else {
                return;
            }
        }
        final IProject project = (IProject) folder;
        new Job("Looking for repositories") {

            protected IStatus run(IProgressMonitor monitor) {

                try {
                    ArrayList matches = NavajoScriptPluginPlugin.getDefault().searchForImplementingClasses((IProject) folder,
                            NavajoScriptPluginPlugin.NAVAJO_REPOSITORY_INTERFACE, monitor);
                    System.err.println(matches.size() + " repositories found.");
                    matches = NavajoScriptPluginPlugin.getDefault().searchForImplementingClasses((IProject) folder,
                            NavajoScriptPluginPlugin.NAVAJO_ADAPTER_INTERFACE, monitor);
                    System.err.println(matches.size() + " adapters found.");
                    matches = NavajoScriptPluginPlugin.getDefault().searchForExtendingClasses((IProject) folder,
                            NavajoScriptPluginPlugin.NAVAJO_FUNCTION_CLASS, monitor);
                    System.err.println(matches.size() + " functions found.");
                    return Status.OK_STATUS;
                } catch (CoreException e) {
                    NavajoScriptPluginPlugin.getDefault().log("Unable to find repository.",e);
                    return new Status(Status.ERROR, "com.dexels.plugin", -11, "Could not find stuff", e);
                }
            }
        }.schedule();
    }

}