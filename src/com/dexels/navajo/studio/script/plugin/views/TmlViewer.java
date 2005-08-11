/*
 * Created on May 30, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.studio.script.plugin.views;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.eclipse.swt.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.*;
import org.eclipse.ui.forms.widgets.*;
import org.eclipse.ui.part.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.studio.script.plugin.*;
import com.dexels.navajo.studio.script.plugin.editors.*;

/**
 * @author Administrator
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class TmlViewer extends ViewPart implements IResourceChangeListener {
    private Navajo myCurrentNavajo = null;

    private IFile myCurrentFile = null;

    private TmlFormComposite formComposite;
    private String currentService = null;

    public void dispose() {
          super.dispose();
          NavajoScriptPluginPlugin.getDefault().setTmlViewer(null);
          ResourcesPlugin.getWorkspace().removeResourceChangeListener(this);
    }
   
//    private Composite mainPanel;
//    int iii= 0;
    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.IWorkbenchPart#createPartControl(org.eclipse.swt.widgets.Composite)
     */
    
    
    public void resourceChanged(IResourceChangeEvent event) {
        IResourceDeltaVisitor visitor = new IResourceDeltaVisitor() {
            public boolean visit(IResourceDelta delta) {
                IResource resource = delta.getResource();
                IPath ip = resource.getFullPath();
                if (myCurrentFile==null) {
                    return false;
                }
                IPath myPath = myCurrentFile.getFullPath();
                if (resource.equals(myCurrentFile)) {
                    NavajoScriptPluginPlugin.getDefault().showTml(myCurrentFile,currentService);
                    return false;
                }
                if (ip.isPrefixOf(myPath)) {
                    return true;
                }
                return false;  
            }
        };
        try {
            IResourceDelta ird = event.getDelta();
            if (ird!=null) {
                ird.accept(visitor);
            }
        } catch (CoreException e) {
             e.printStackTrace();
        }        
    }    
    public void createPartControl(Composite parent) {
//        System.err.println("Creating part control. Invokation number: "+iii++);
        NavajoScriptPluginPlugin.getDefault().setTmlViewer(this);
        Control[] c = parent.getChildren();
        for (int i = 0; i < c.length; i++) {
            System.err.println("Child: "+c[i].getClass());
            c[i].dispose();
        }
        formComposite = new TmlFormComposite(null, parent);
       System.err.println("PARENTLAYOUT CLASS: "+parent.getLayout().getClass());
       ResourcesPlugin.getWorkspace().addResourceChangeListener(this);     

  
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.IWorkbenchPart#setFocus()
     */
    public void setFocus() {
        // TODO Auto-generated method stub
    }

    public void setNavajo(final Navajo n, final IFile myFile, final String scriptName) {
         System.err.println("TMLVIEWER: Setting to service: "+scriptName);
        final Display d = PlatformUI.getWorkbench().getDisplay();
        d.syncExec(new Runnable() {
            public void run() {
                myCurrentFile = myFile;
                myCurrentNavajo = n;
                currentService = scriptName;
                System.err.println(">>>>>>>>>>>>>>>>>>>>>> SETTING NAVAJO IN TML VIEWER");
                if (formComposite != null) {
                    formComposite.setNavajo(n, myCurrentFile,scriptName);
                    formComposite.reflow();
                } else {
                    System.err.println("hmmm. No formComposite");
                }
            }
        });

    }

    public Navajo getNavajo() {
        return myCurrentNavajo;
    }

    public String getService() {
        // TODO Auto-generated method stub
        return currentService;
    }

}
