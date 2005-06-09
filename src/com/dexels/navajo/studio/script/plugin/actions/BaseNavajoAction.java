/*
 * Created on Apr 4, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.studio.script.plugin.actions;

import java.util.*;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.eclipse.jface.action.*;
import org.eclipse.jface.dialogs.*;
import org.eclipse.jface.viewers.*;
import org.eclipse.ui.*;
import org.eclipse.ui.internal.*;

import com.dexels.navajo.studio.script.plugin.*;

/**
 * @author Administrator
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class BaseNavajoAction implements IWorkbenchWindowActionDelegate, IEditorActionDelegate {
    protected IWorkbenchWindow window;

    public final static int RESOURCE_TML = 1;

    public final static int RESOURCE_XML = 2;

    public final static int RESOURCE_PROJECT = 3;

    public final static int RESOURCE_JAVA = 4;

    public final static int CONFIG = 5;

    protected ISelection selection;

    protected IEditorPart myEditor = null;

    protected String scriptName;

    protected IFile file;

    protected IContainer folder;

    public BaseNavajoAction() {
        super();
    }

    public void selectionChanged(IAction action, ISelection selection) {
        file = null;
        //        System.err.println("SELECTION TYPE: " + selection.getClass());
        try {
            if (selection instanceof IStructuredSelection) {
                boolean empty = ((IStructuredSelection) selection).isEmpty();
                //            System.err.println("IS EMPTY ISS: " + empty);
                if (empty) {
                    IEditorPart activeEditor = Workbench.getInstance().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
                    if (activeEditor != null) {
                        file = (IFile) activeEditor.getEditorInput().getAdapter(IFile.class);
                    } else {
                        this.selection = null;
                        file = null;
                        return;
                    }
                    folder = null;
                } else {
                    Iterator iter = ((IStructuredSelection) selection).iterator();
                    this.selection = selection;

                    while (iter.hasNext()) {
                        Object element = iter.next();
                        //                    System.err.println("Current: " + element);

                        if (element instanceof IFile) {
                            file = (IFile) element;
                            folder = null;
                        }
                        if (element instanceof IContainer) {
                            folder = (IContainer) element;
                            file = null;
                        }
                    }

                }
            }
            if (!(selection instanceof IStructuredSelection) && file == null) {
                //            System.err.println("No structuredselection...");
                //            System.err.println(">>> " + selection.getClass());
                IEditorPart e = Workbench.getInstance().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
                boolean res = false;
                
                // This is possible, when no editor is open at all.
                if (e==null) {
                    return;
                }
                //            if (e.isDirty()) {
                //				res = MessageDialog.openQuestion(
                //				window.getShell(),
                //				"Navajo Studio Plug-in",
                //				"Do you want to save first?");
                //				if (res) {
                //                    e.doSave(null);
                //               }
                //            }
                IEditorInput ei = e.getEditorInput();
                file = (IFile) ei.getAdapter(IFile.class);
            }
            if (file == null) {
                //            System.err.println("Null aap!");
                return;
            }

            if ("xml".equals(file.getFileExtension())) {
                scriptName = NavajoScriptPluginPlugin.getDefault().getScriptNameFromResource(file);
                //            System.err.println("SCRIPT FILE SELECTED: " + scriptName);
            }
            if ("tml".equals(file.getFileExtension())) {
                //            System.err.println("Looking from tml file: " +
                // file.getFullPath());
                scriptName = NavajoScriptPluginPlugin.getDefault().getScriptNameFromResource(file);
                //            System.err.println("TML FILE SELECTED: " + scriptName);
                //                    IFile script =
                // NavajoScriptPluginPlugin.getDefault().getScriptFile(file.getProject(),
                // scriptName);
                //            NavajoScriptPluginPlugin.getDefault().runNavajo(script);
            }
            if ("java".equals(file.getFileExtension())) {
                scriptName = NavajoScriptPluginPlugin.getDefault().getScriptNameFromResource(file);
                //            System.err.println("SCRIPT FILE SELECTED: " + scriptName);
            }
        } catch (NavajoPluginException e) {
        }

    }

    /**
     * We can use this method to dispose of any system resources we previously
     * allocated.
     * 
     * @see IWorkbenchWindowActionDelegate#dispose
     */
    public void dispose() {
    }

    /**
     * We will cache window object in order to be able to provide parent shell
     * for the message dialog.
     * 
     * @see IWorkbenchWindowActionDelegate#init
     */
    public void init(IWorkbenchWindow window) {
        this.window = window;
    }

    //    public IProject getSelectedProject() {
    //        if (!(selection instanceof IStructuredSelection))
    //            return;
    //        Iterator iter = ((IStructuredSelection) selection).iterator();
    //        while (iter.hasNext()) {
    //            Object element = iter.next();
    //            if (!(element instanceof IProject))
    //                continue;
    //            IProject project = (IProject) element;
    //            return IProject;
    //        }
    //        return null;
    //    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.IEditorActionDelegate#setActiveEditor(org.eclipse.jface.action.IAction,
     *      org.eclipse.ui.IEditorPart)
     */
    public void setActiveEditor(IAction action, IEditorPart targetEditor) {
        myEditor = targetEditor;
        System.err.println("Editor set");
        // TODO Auto-generated method stub

    }

}