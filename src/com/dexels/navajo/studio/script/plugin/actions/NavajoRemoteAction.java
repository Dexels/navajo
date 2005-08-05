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
    public void run(IAction action) {
        try {
//            NavajoScriptPluginPlugin.getDefault().runNavajo(NavajoScriptPluginPlugin.NAVAJO_RUNNER_CLASS, file);
            boolean ok = true;
            if (file==null) {
                ok = false;
            }
            if (ok && !file.exists()) {
                ok = false;
            }
            IProject ipp = file.getProject();
            if (ok && !ipp.hasNature(NavajoScriptPluginPlugin.NAVAJO_NATURE)) {
                ok = false;
            }
            if (ok) {
                Job job = new Job("Running "+scriptName+"...") {
                    protected IStatus run(IProgressMonitor monitor) {
                        NavajoScriptPluginPlugin.getDefault().runRemoteNavajo(file.getProject(), scriptName, null,null);
                        return Status.OK_STATUS;
                    }
                };
                job.schedule();
               
            } else {
                NavajoScriptPluginPlugin.getDefault().showError("I don't know which project you mean. Select a file in the navigator,\nwhich is a child of a navajo-project.\n\nThen start the socket runner again.");
            }
                   
//            NavajoClientFactory.createClient("com.dexels.navajo.client.NavajoSocketClient", null);
//            NavajoClientFactory.getClient().setServerUrl("bananus:10000");
//            NavajoClientFactory.getClient().setUsername("ROOT");
//            NavajoClientFactory.getClient().setPassword("");
//            Navajo result = NavajoClientFactory.getClient().doSimpleSend(scriptName);
//            String path = NavajoScriptPluginPlugin.getDefault().getTmlFile(file.getProject(), scriptName).getLocation().toOSString();
//            System.err.println("Path: "+path);
//            FileWriter fw = new FileWriter(path);
//            result.write(fw);
//            fw.flush();
//            fw.close();
//            IFile ff = NavajoScriptPluginPlugin.getDefault().getTmlFile(file.getProject(), scriptName);
//            ff.refreshLocal(0, null);
//            NavajoScriptPluginPlugin.getDefault().showTml(ff,scriptName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}