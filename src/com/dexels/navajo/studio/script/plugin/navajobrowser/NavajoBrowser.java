/*
 * Created on Feb 23, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.studio.script.plugin.navajobrowser;


import java.awt.BorderLayout;

import javax.swing.*;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.eclipse.core.runtime.jobs.*;
import org.eclipse.jface.dialogs.*;
import org.eclipse.swt.*;
import org.eclipse.swt.awt.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.*;
import org.eclipse.ui.internal.*;
import org.eclipse.ui.part.*;
import org.eclipse.swt.widgets.Composite;

import com.dexels.navajo.client.*;
import com.dexels.navajo.document.*;
import com.dexels.navajo.studio.script.plugin.*;
;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class NavajoBrowser extends ViewPart {

    /**
     * 
     */
    
    private static NavajoBrowser instance;
    
    
//    NavajoDetailPanel myPanel = new NavajoDetailPanel();
    
    private Navajo myCurrentNavajo = null;
    
//    public NavajoBrowser() {
//        super();
//        instance = this;
//        NavajoScriptPluginPlugin.getDefault().setNavajoView(this);
//        System.err.println("Instance set!");
//    }

    /* (non-Javadoc)
     * @see org.eclipse.ui.IWorkbenchPart#createPartControl(org.eclipse.swt.widgets.Composite)
     */
    public void createPartControl(Composite parent) {
//        Composite c = new Composite(parent,SWT.EMBEDDED);	
//        //        java.awt.Panel panel = new java.awt.Panel();
//         java.awt.Frame frame = SWT_AWT.new_Frame(c);
//         try {
//             UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//         } catch (Exception e) {
//             // TODO Auto-generated catch block
//             e.printStackTrace();
//         }
//       frame.setLayout(new BorderLayout());
//        SwingUtilities.updateComponentTreeUI(frame);
//        frame.pack();
//        frame.add(myPanel,BorderLayout.CENTER);
//
    }

    /* (non-Javadoc)
     * @see org.eclipse.ui.IWorkbenchPart#setFocus()
     */
    public void setFocus() {
        // TODO Auto-generated method stub
    }
    

    
//    public void setNavajo(final Navajo n, final IFile currentTml) {
//        myCurrentNavajo = n;
//        final Display d = PlatformUI.getWorkbench().getDisplay();
//    d.syncExec(new Runnable() {
//
//            public void run() {
//                 final NavajoBrowser nb = NavajoBrowser.getInstance();
//                 myPanel.navajoSelected("aap", n, currentTml);
//}
//        });
//       
//    }

//    public void dispose() {
//        instance = null;
//        NavajoScriptPluginPlugin.getDefault().setNavajoView(null);
//      super.dispose();
//    }
    
//    public static NavajoBrowser getInstance() {
//        return instance;
//    }

    /**
     * @param selectedNavajo
     * @param string
     */
 }
