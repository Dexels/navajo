package com.dexels.navajo.studio.script.plugin.actions;

import java.io.*;
import java.util.*;

import org.eclipse.core.internal.resources.*;
import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.eclipse.core.runtime.jobs.*;
import org.eclipse.jdt.core.*;
import org.eclipse.jdt.internal.ui.*;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.*;
import org.eclipse.ui.*;
import org.eclipse.ui.internal.*;
import org.eclipse.ui.internal.misc.*;
import org.eclipse.jface.dialogs.MessageDialog;

import com.dexels.navajo.client.*;
import com.dexels.navajo.document.*;
import com.dexels.navajo.studio.script.plugin.*;
import com.dexels.navajo.studio.script.plugin.views.*;

/**
 * Our sample action implements workbench action delegate. The action proxy will
 * be created by the workbench and shown in the UI. When the user tries to use
 * the action, this delegate will be created and execution will be delegated to
 * it.
 * 
 * @see IWorkbenchWindowActionDelegate
 */
public class NavajoForwardAction extends BaseNavajoAction {
    //	private IEditorPart myEditor = null;
    //	private IWorkbenchWindow window;
    //    private ISelection selection;
    /**
     * The constructor.
     */
    public NavajoForwardAction() {
    }

    /**
     * The action has been activated. The argument of the method represents the
     * 'real' action sitting in the workbench UI.
     * 
     * @see IWorkbenchWindowActionDelegate#run
     */
    public void run(IAction action) {
        try {
            IWorkbenchPart iwp = NavajoScriptPluginPlugin.getDefault().getWorkbench().getActiveWorkbenchWindow().getActivePage().getActivePart();
            TmlViewer tv = NavajoScriptPluginPlugin.getDefault().getTmlViewer();
            if (iwp == tv) {
                if (tv!=null) {
                    System.err.println("No forward in TmlViewer. Wouldn't know where to go");
                    tv.forward();
                }
            }

            if (iwp instanceof TmlBrowser) {
                TmlBrowser tb = (TmlBrowser)iwp;
                tb.forward();
            }
         } catch (Exception e) {
             NavajoScriptPluginPlugin.getDefault().log("Forward did not work. Big deal.",e);
        }
    }
}