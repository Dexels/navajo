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
import com.sun.corba.se.internal.Activation.*;

/**
 * Our sample action implements workbench action delegate.
 * The action proxy will be created by the workbench and
 * shown in the UI. When the user tries to use the action,
 * this delegate will be created and execution will be 
 * delegated to it.
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
	 * The action has been activated. The argument of the
	 * method represents the 'real' action sitting
	 * in the workbench UI.
	 * @see IWorkbenchWindowActionDelegate#run
	 */
	public void run(IAction action) {
      try {
        NavajoScriptPluginPlugin.getDefault().runNavajo(file);
    } catch (CoreException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }

	    //		
//		try {
//
//		    if (!(selection instanceof IStructuredSelection)) {
//	            System.err.println("No structuredselection...");
//	            System.err.println(">>> "+selection.getClass());
//                IEditorPart e = Workbench.getInstance().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
//                boolean res = false;
//                if (e.isDirty()) {
//    				res = MessageDialog.openQuestion(
//					window.getShell(),
//					"Navajo Studio Plug-in",
//					"Do you want to save first?");
//    				if (res) {
//                        e.doSave(null);
////                      TODO Make sure it blocks until the save/recompile is finished!!
//                   }
//                }
//                IEditorInput ei = e.getEditorInput();
//                IFile file = (IFile)ei.getAdapter(IFile.class);
//                if (file!=null) {
//                    NavajoScriptPluginPlugin.getDefault().runNavajo(file);
//                }
//                return;
//	        }
//	        Iterator iter = ((IStructuredSelection) selection).iterator();
//	        while (iter.hasNext()) {
//	            Object element = iter.next();
//	            if (!(element instanceof IFile))
//	                continue;
//	            IFile file = (IFile)element;
//	            if ("xml".equals(file.getFileExtension())) {
//                    NavajoScriptPluginPlugin.getDefault().runNavajo(file);
//                }
//	            if ("tml".equals(file.getFileExtension())) {
//	                System.err.println("Looking from tml file: "+file.getFullPath());
////	                String scriptName = NavajoScriptPluginPlugin.getDefault().getScriptNameFromTml(file,file.getProject());
//	                String scriptName = NavajoScriptPluginPlugin.getDefault().getScriptNameFromResource(file);
//	                IFile script = NavajoScriptPluginPlugin.getDefault().getScriptFile(file.getProject(), scriptName);
//	                NavajoScriptPluginPlugin.getDefault().runNavajo(script);
//	            }
//			    if (file==null) {
//	                System.err.println("Null aap!");
//	            } else {
//	                
//	            }
//	        }
//        } catch (CoreException e) {
//            e.printStackTrace();
//        }
	}

//    public void selectionChanged(IAction action, ISelection selection) {
//        this.selection = selection;
//    }

//	/**
//	 * Selection in the workbench has been changed. We 
//	 * can change the state of the 'real' action here
//	 * if we want, but this can only happen after 
//	 * the delegate has been created.
//	 * @see IWorkbenchWindowActionDelegate#selectionChanged
//	 */

//	public void dispose() {
//	}
//
//	/**
//	 * We will cache window object in order to
//	 * be able to provide parent shell for the message dialog.
//	 * @see IWorkbenchWindowActionDelegate#init
//	 */
//	public void init(IWorkbenchWindow window) {
//		this.window = window;
//	}
//	
//	
//    /* (non-Javadoc)
//     * @see org.eclipse.ui.IEditorActionDelegate#setActiveEditor(org.eclipse.jface.action.IAction, org.eclipse.ui.IEditorPart)
//     */
//    public void setActiveEditor(IAction action, IEditorPart targetEditor) {
//        myEditor = targetEditor;
//        System.err.println("Editor set");
//        // TODO Auto-generated method stub
//        
//    }
}