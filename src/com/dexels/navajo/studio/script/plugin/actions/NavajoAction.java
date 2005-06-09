package com.dexels.navajo.studio.script.plugin.actions;

import java.util.*;

import org.eclipse.core.internal.resources.*;
import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.eclipse.jdt.core.*;
import org.eclipse.jdt.internal.ui.*;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.*;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.*;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.internal.*;
import org.eclipse.ui.internal.misc.*;
import org.eclipse.jface.dialogs.MessageDialog;

import com.dexels.navajo.studio.script.plugin.*;

/**
 * Our sample action implements workbench action delegate. The action proxy will
 * be created by the workbench and shown in the UI. When the user tries to use
 * the action, this delegate will be created and execution will be delegated to
 * it.
 * 
 * @see IWorkbenchWindowActionDelegate
 */
public class NavajoAction extends BaseNavajoAction {
    //	private IEditorPart myEditor = null;
    //	private IWorkbenchWindow window;
    //    private ISelection selection;
    /**
     * The constructor.
     */
    public NavajoAction() {
    }

    /**
     * The action has been activated. The argument of the method represents the
     * 'real' action sitting in the workbench UI.
     * 
     * @see IWorkbenchWindowActionDelegate#run
     */
    public void run(IAction action) {
        try {
            NavajoScriptPluginPlugin.getDefault().runNavajo(NavajoScriptPluginPlugin.NAVAJO_RUNNER_CLASS, file);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}