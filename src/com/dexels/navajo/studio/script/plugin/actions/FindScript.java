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
public class FindScript extends BaseNavajoAction {

    public FindScript() {
        super();
    }

    /**
     * The action has been activated. The argument of the method represents the
     * 'real' action sitting in the workbench UI.
     * 
     * @see IWorkbenchWindowActionDelegate#run
     */
    public void run(IAction action) {
        //			scriptName);
        try {
            System.err.println("Scriptfile: "+scriptName);
            IFile xmlFile = NavajoScriptPluginPlugin.getDefault().getScriptFile(file.getProject(), scriptName);
            if (xmlFile != null) {
                //                System.err.println("not null");
                //                System.err.println("TML: "+tmlFile.getFullPath().toString());
                if (xmlFile.exists()) {
                    //                    System.err.println("And it exists");
                    NavajoScriptPluginPlugin.getDefault().openInEditor(xmlFile);
                }
            }
        } catch (NavajoPluginException e) {
            NavajoScriptPluginPlugin.getDefault().log("Error finding script ",e);
        }
    }

}
