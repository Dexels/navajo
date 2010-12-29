package com.dexels.navajo.studio.script.plugin.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import com.dexels.navajo.studio.script.plugin.NavajoScriptPluginPlugin;
import com.dexels.navajo.studio.script.plugin.views.TmlBrowser;
import com.dexels.navajo.studio.script.plugin.views.TmlViewer;

/**
 * Our sample action implements workbench action delegate. The action proxy will
 * be created by the workbench and shown in the UI. When the user tries to use
 * the action, this delegate will be created and execution will be delegated to
 * it.
 * 
 * @see IWorkbenchWindowActionDelegate
 */
public class NavajoReloadAction extends BaseNavajoAction {
    //	private IEditorPart myEditor = null;
    //	private IWorkbenchWindow window;
    //    private ISelection selection;
    /**
     * The constructor.
     */
    public NavajoReloadAction() {
    }

    /**
     * The action has been activated. The argument of the method represents the
     * 'real' action sitting in the workbench UI.
     * 
     * @see IWorkbenchWindowActionDelegate#run
     */
    public void run(IAction action) {
        try {
            IWorkbenchPart iwp = NavajoScriptPluginPlugin.getDefault().getWorkbench().getActiveWorkbenchWindow().getActivePage().getActivePart();
            TmlViewer tv = NavajoScriptPluginPlugin.getDefault().getTmlViewer();
            if (iwp == tv) {
                if (tv!=null) {
                    tv.reload();
  // Isn't this REALLY strange? Why check this if it is visible? It only gets here because it is?!
                    if (!NavajoScriptPluginPlugin.getDefault().getWorkbench().getActiveWorkbenchWindow().getActivePage().isPartVisible(tv)) {
                        NavajoScriptPluginPlugin.getDefault().getWorkbench().getActiveWorkbenchWindow().getActivePage().showView("com.dexels.TmlViewer");
                    }
                }
            }

            if (iwp instanceof TmlBrowser) {
                TmlBrowser tb = (TmlBrowser)iwp;
                tb.reload();
            }
         } catch (Exception e) {
             NavajoScriptPluginPlugin.getDefault().log("Reload did not work. Big deal.",e);
        }
    }
}