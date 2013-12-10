package com.dexels.navajo.studio.script.plugin.actions;

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
public class NavajoRemoteAction extends BaseNavajoAction {
    //	private IEditorPart myEditor = null;
    //	private IWorkbenchWindow window;
    //    private ISelection selection;
    /**
     * The constructor.
     */
    public NavajoRemoteAction() {
    }

    /**
     * The action has been activated. The argument of the method represents the
     * 'real' action sitting in the workbench UI.
     * 
     * @see IWorkbenchWindowActionDelegate#run
     */
    @Override
	public void run(IAction action) {
        try {
        	throw new UnsupportedOperationException("Not implemented");

        	//            NavajoScriptPluginPlugin.getDefault().runNavajo(NavajoScriptPluginPlugin.NAVAJO_RUNNER_CLASS, file);
//            NavajoScriptPluginPlugin.getDefault().runRemoteNavajo(file,scriptName);
        } catch (Exception e) {
            NavajoScriptPluginPlugin.getDefault().log("Running a remote navajo did not work.",e);
        }
    }


}