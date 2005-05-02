/*
 * Created on Apr 4, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.studio.script.plugin.actions;

import org.eclipse.core.resources.*;
import org.eclipse.jface.action.*;
import org.eclipse.jface.dialogs.*;
import org.eclipse.jface.viewers.*;
import org.eclipse.ui.*;

import com.dexels.navajo.studio.script.plugin.*;

/**
 * @author Administrator
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class FindJavaSource extends BaseNavajoAction {
    private IWorkbenchWindow window;

    public FindJavaSource() {
        super();
    }

    public void run(IAction action) {
        IFile scriptFile = NavajoScriptPluginPlugin.getDefault().getCompiledScriptFile(file.getProject(), scriptName);
        if (scriptFile != null) {
            //                System.err.println("not null");
            //                System.err.println("TML: "+tmlFile.getFullPath().toString());
            if (scriptFile.exists()) {
                //                    System.err.println("And it exists");
                NavajoScriptPluginPlugin.getDefault().openInEditor(scriptFile);
            }
        }
    }

}
