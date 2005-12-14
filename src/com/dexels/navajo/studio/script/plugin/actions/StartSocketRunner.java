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
public class StartSocketRunner extends BaseNavajoAction {

    /* (non-Javadoc)
     * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
     */
    public void run(IAction action) {
        // TODO Auto-generated method stub
        try {
//            if (getProject()==null || !getProject().isOpen() || !getProject().hasNature(NavajoScriptPluginPlugin.NAVAJO_NATURE)) {
//                NavajoScriptPluginPlugin.getDefault().showError("Make sure a file in a navajo project has been selected\nbefore attempting to start the socket runner.\nOh and make sure it is opened!");
//            }
            if (NavajoScriptPluginPlugin.getDefault().getCurrentSocketLaunch() != null) {
                NavajoScriptPluginPlugin.getDefault().getCurrentSocketLaunch().terminate();
                NavajoScriptPluginPlugin.getDefault().setCurrentSocketLaunch(null);
            }
            
//            IProject ip = getProject();
            IProject ip = NavajoScriptPluginPlugin.getDefault().getDefaultNavajoProject();
            if (ip==null) {
              NavajoScriptPluginPlugin.getDefault().showError("Check preferences. There is no default Navajo project selected.");
              return;
            }
            Launch lll = null;
            
            try {
                lll = NavajoScriptPluginPlugin.getDefault().runNavajoBootStrap("com.dexels.navajo.client.socket.NavajoSocketLauncher", true, ip,
                        "", "", null,null, new String[]{""+NavajoScriptPluginPlugin.getDefault().getRemotePort(),NavajoScriptPluginPlugin.getDefault().getServerXml(ip).getLocation().toOSString()});
            } catch (Throwable e) {
                 e.printStackTrace();
            }

            NavajoScriptPluginPlugin.getDefault().setCurrentSocketLaunch(lll);

        } catch (Exception e1) {
            e1.printStackTrace();
        }
        
    }

}
