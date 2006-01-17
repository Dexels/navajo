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

    protected final ArrayList selectionList = new ArrayList();
    
    public BaseNavajoAction() {
        super();
    }

    public void selectionChanged(IAction action, ISelection selection) {
        file = null;
        //        System.err.println("SELECTION TYPE: " + selection.getClass());
//        System.err.println("Current proj:" +NavajoScriptPluginPlugin.getDefault().getDefaultNavajoProject());
        try {
            if (selection instanceof IStructuredSelection) {
                boolean empty = ((IStructuredSelection) selection).isEmpty();
                //            System.err.println("IS EMPTY ISS: " + empty);
                selectionList.clear();
                if (empty) {
                    
                    IEditorPart activeEditor = NavajoScriptPluginPlugin.getDefaultWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
                        this.selection = null;
                        file = null;
                        folder = null;
                        return;
//                    }
                } else {
                    Iterator iter = ((IStructuredSelection) selection).iterator();
                    this.selection = selection;

                    while (iter.hasNext()) {
                        Object element = iter.next();
                        //                    System.err.println("Current: " + element);

                        if (element instanceof IFile) {
                            file = (IFile) element;
                            folder = null;
                            selectionList.add(file);
                        }
                        if (element instanceof IContainer) {
                            folder = (IContainer) element;
                            file = null;
                            selectionList.add(folder);
                        }
                    }

                }
            }
            if (file == null) {
                //            System.err.println("Null aap!");
                return;
            }
            if (!NavajoScriptPluginPlugin.getDefault().hasNavajoBuilder()) {
            }
            if ("xml".equals(file.getFileExtension())) {
                scriptName = NavajoScriptPluginPlugin.getDefault().getScriptNameFromResource(file);
                //            System.err.println("SCRIPT FILE SELECTED: " + scriptName);
            }
            if ("tml".equals(file.getFileExtension())) {
                scriptName = NavajoScriptPluginPlugin.getDefault().getScriptNameFromResource(file);
            }
            if ("java".equals(file.getFileExtension())) {
                scriptName = NavajoScriptPluginPlugin.getDefault().getScriptNameFromResource(file);
             }
        } catch (NavajoPluginException e) {
            NavajoScriptPluginPlugin.getDefault().log("Setting resource selection in navajo project. This is bad. ",e);
        }

    }

    public IProject getProject() {
        // I am unsure whether this is a good idea.
        if (folder==null) {
            System.err.println("Hey! I thought this did not happen any more!");
             if (file!=null) {
                IProject ip = file.getProject();
                return ip;
            } else {
                NavajoScriptPluginPlugin.getDefault().log("BaseNavajoAction: Getting Default project. Shouldn't happen. Fix.");
                return NavajoScriptPluginPlugin.getDefault().getDefaultNavajoProject();
            }
        } else {
            if (folder instanceof IProject) {
                return (IProject)folder;
            } else {
                return folder.getProject();
            }
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