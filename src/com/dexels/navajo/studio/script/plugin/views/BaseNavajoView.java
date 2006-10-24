/*
 * Created on Jan 6, 2006
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.studio.script.plugin.views;

import org.eclipse.core.runtime.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.*;
import org.eclipse.ui.contexts.*;
import org.eclipse.ui.part.*;

import com.dexels.navajo.studio.script.plugin.*;

public abstract class BaseNavajoView extends ViewPart {

    public String getContextId() {
        return "com.dexels.navajoContext";
    }
    private IContextActivation currentActivation = null;
    

    public void init(final IViewSite site) throws PartInitException {
        super.init(site);
        final BaseNavajoView me = this;
        final IContextService ics = (IContextService)NavajoScriptPluginPlugin.getDefault().getWorkbench().getAdapter(IContextService.class);

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
