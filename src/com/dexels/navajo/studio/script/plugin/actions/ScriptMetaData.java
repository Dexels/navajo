/*
 * Created on Apr 4, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.studio.script.plugin.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import com.dexels.navajo.studio.script.plugin.NavajoPluginException;
import com.dexels.navajo.studio.script.plugin.NavajoScriptPluginPlugin;

/**
 * @author Administrator
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ScriptMetaData extends BaseNavajoAction {

    public ScriptMetaData() {
        super();
    }

    /**
     * The action has been activated. The argument of the method represents the
     * 'real' action sitting in the workbench UI.
     * 
     * @see IWorkbenchWindowActionDelegate#run
     */
    public void run(IAction action) {
        try {
            NavajoScriptPluginPlugin.getDefault().showMetaData(file,scriptName);
        } catch (NavajoPluginException e) {
            NavajoScriptPluginPlugin.getDefault().log("Error showing metadata. ",e);
        }
    }

}
