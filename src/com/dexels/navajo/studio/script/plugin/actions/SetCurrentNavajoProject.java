/*
 * Created on Jul 21, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.studio.script.plugin.actions;

import org.eclipse.core.resources.*;
import org.eclipse.debug.core.*;
import org.eclipse.jface.action.*;
import org.eclipse.ui.*;
import org.eclipse.ui.internal.*;

import com.dexels.navajo.studio.script.plugin.*;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class SetCurrentNavajoProject extends BaseNavajoAction {

    /* (non-Javadoc)
     * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
     */
    public void run(IAction action) {
        // TODO REFACTOR
  
//        NavajoScriptPluginPlugin.getDefault().showError("Did not fix this action yet");
//            NavajoScriptPluginPlugin.getDefault().refreshCompilerClassLoader();
        IProject p = getProject();
        if (p==null) {
            NavajoScriptPluginPlugin.getDefault().showError("Huh?");
            return;
        }
        String n = p.getName();
        NavajoScriptPluginPlugin.getDefault().getPreferenceStore().setValue(NavajoScriptPluginPlugin.NAVAJO_DEFAULT_PROJECT_KEY,n);
    }

}
