/*
 * Created on Jul 21, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.studio.script.plugin.actions;

import java.util.*;

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
public class StartSocketRunner extends BaseNavajoAction {

    /* (non-Javadoc)
     * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
     */
    public void run(IAction action) {
        // TODO Auto-generated method stub
        try {
            IProject p = getProject();
            if (p==null) {
                NavajoScriptPluginPlugin.getDefault().showError("Check preferences. There is no default Navajo project selected.");
                return;
              }
            NavajoScriptPluginPlugin.getDefault().startSocketRunner(p);

        } catch (Exception e1) {
            NavajoScriptPluginPlugin.getDefault().log("starting socket runner did not work.",e1);
        }
        
    }

}
