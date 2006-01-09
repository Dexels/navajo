/*
 * Created on May 30, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.studio.script.plugin.views;

import java.util.*;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.*;
import org.eclipse.ui.contexts.*;
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
public class TmlViewer extends BaseNavajoView implements IResourceChangeListener {
    private static final String VIEW_ID = "com.dexels.TmlViewer";

    private Navajo myCurrentNavajo = null;

    private IFile myCurrentFile = null;

    private final Stack backStack = new Stack();
    private TmlFormComposite formComposite;
    private String currentService = null;

    private boolean listeningForChange = true;
    
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
    
    public void setListeningForResourceChanges(boolean b) {
        listeningForChange = b;
    }
    
    public void reload() throws NavajoPluginException {
        if (formComposite.getCurrentNavajo()!=null) {
            formComposite.reload();
        }
    }
    public void resourceChanged(IResourceChangeEvent event) {
        if (!listeningForChange) {
            System.err.println("Ignoring resource change in viewer");
            return;
        }
        IResourceDeltaVisitor visitor = new IResourceDeltaVisitor() {
            public boolean visit(IResourceDelta delta) {
                IResource resource = delta.getResource();
                IPath ip = resource.getFullPath();
                if (myCurrentFile==null) {
                    return false;
                }
//                NavajoScriptPluginPlugin.getDefault().getTmlFolder(myCurrentFile.getProject())
                IPath myPath = myCurrentFile.getFullPath();
                if (resource.equals(myCurrentFile)) {
                    try {
                        NavajoScriptPluginPlugin.getDefault().showTml(myCurrentFile,currentService);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
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
         System.err.println("Sourcescript: "+n.getHeader().getAttribute("sourceScript"));
         final Display d = PlatformUI.getWorkbench().getDisplay();
         backStack.clear();
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
                listeningForChange = true;
            }
        });

    }

    public Navajo getNavajo() {
        return myCurrentNavajo;
    }

    public String getService() {
        return currentService;
    }

    public void back() {
        try {
            backStack.push(createStackEntry());
            formComposite.back();
        } catch (NavajoPluginException e) {
             e.printStackTrace();
        }
    }

    public void forward() {
        System.err.println("Does not work well yet. Disabled for now");
//        if (backStack.isEmpty()) {
        if (true) {
            return;
        } 
        StackEntry se = (StackEntry)backStack.pop();
        formComposite.setNavajo(se.navajo, se.file, se.name);
    }

    private StackEntry createStackEntry() {
        return new StackEntry(formComposite.getCurrentScript(), formComposite.getCurrentFile(),formComposite.getCurrentNavajo());
    }
    
    class StackEntry {
        public final String name;
        public final IFile file;
        public final Navajo navajo;
        
        public StackEntry(String name, IFile file, Navajo navajo) {
            this.name = name;
            this.file = file;
            this.navajo = navajo;
        }
    }
}
