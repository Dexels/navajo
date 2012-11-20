/*
 * Created on Jan 6, 2006
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.studio.script.plugin.views;

import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.contexts.IContextActivation;
import org.eclipse.ui.contexts.IContextService;
import org.eclipse.ui.part.ViewPart;

import com.dexels.navajo.studio.script.plugin.NavajoScriptPluginPlugin;

public abstract class BaseNavajoView extends ViewPart {

    public String getContextId() {
        return "com.dexels.navajoContext";
    }
    private IContextActivation currentActivation = null;
    

    @Override
	public void init(final IViewSite site) throws PartInitException {
        super.init(site);
        final BaseNavajoView me = this;
//        NavajoScriptPluginPlugin plugin = NavajoScriptPluginPlugin.getDefault();
//		IWorkbench workbench = ResourcesPlugin.getPlugin().getWorkbench();
		//IWorkbench workbench =  Platform. //ResourcesPlugin.getWorkspace();
		IWorkbench workbench = PlatformUI.getWorkbench();
		final IContextService ics = (IContextService)workbench.getAdapter(IContextService.class);

         NavajoScriptPluginPlugin.getDefaultWorkbench().getDisplay().syncExec(new Runnable(){

            public void run() {
            	
                IWorkbenchPage[] pages = site.getWorkbenchWindow().getPages();

                IPartListener ip = new IPartListener(){
                	
                	//                NavajoScriptPluginPlugin.getDefault().getWorkbench().getActiveWorkbenchWindow().getActivePage().addPartListener(new IPartListener(){
                    public void partActivated(IWorkbenchPart part) {
                        if (part==me) {
                            System.err.println("Activated");
                            if (getContextId()!=null) {
                                currentActivation = ics.activateContext(getContextId());
                            }
                            
                        }
                    }

                    public void partBroughtToTop(IWorkbenchPart part) {}

                    public void partClosed(IWorkbenchPart part) {
                        if (part==me) {
                        System.err.println("Close");
                        // TODO: RESET Plugin viewer?
                        }
                    }

                    public void partDeactivated(IWorkbenchPart part) {
                        if (part==me) {
                            if (currentActivation!=null) {
                                ics.deactivateContext(currentActivation);
                            }
                       }
                    }

                    public void partOpened(IWorkbenchPart part) {}
                };
                
                for (int i = 0; i < pages.length; i++) {
					pages[i].addPartListener(ip);
				}
                if (getContextId()!=null && ics!=null) {
                    currentActivation = ics.activateContext(getContextId());
                }

            }});
     }


}
