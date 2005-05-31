/*
 * Created on May 30, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.studio.script.plugin.views;

import org.eclipse.core.resources.*;
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
public class TmlViewer extends ViewPart {
    private Navajo myCurrentNavajo = null;

    private IFile myCurrentFile = null;

    private TmlFormComposite formComposite;

    private String currentService = null;

//    private Composite mainPanel;
    int iii= 0;
    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.IWorkbenchPart#createPartControl(org.eclipse.swt.widgets.Composite)
     */
    public void createPartControl(Composite parent) {
        System.err.println("Creating part control. Invokation number: "+iii++);
  //      parent.setLayout(new FillLayout());
//        mainPanel = new Composite(parent, SWT.NONE);
        NavajoScriptPluginPlugin.getDefault().setTmlViewer(this);
        Control[] c = parent.getChildren();
        for (int i = 0; i < c.length; i++) {
            System.err.println("Child: "+c[i].getClass());
            c[i].dispose();
        }
        formComposite = new TmlFormComposite(null, parent);
       System.err.println("PARENTLAYOUT CLASS: "+parent.getLayout().getClass());
//      mainPanel.setLayout(new TableWrapLayout());
        
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.IWorkbenchPart#setFocus()
     */
    public void setFocus() {
        // TODO Auto-generated method stub
    }

    public void setNavajo(final Navajo n, final IFile myFile) {
        //      final String currentName =
        // NavajoScriptPluginPlugin.getDefault().getScriptName(myFile,myFile.getProject());
        //        mainPanel.dispose();
        //        parent.setLayout(new TableWrapLayout());
        final Display d = PlatformUI.getWorkbench().getDisplay();

        d.syncExec(new Runnable() {

            public void run() {
//                if (formComposite != null) {
//                    formComposite.dispose();
//                }
//                
//                 TableWrapData twd = new TableWrapData(TableWrapData.FILL, TableWrapData.FILL);
//                twd.grabHorizontal = true;
//                twd.grabVertical = true;
//                formComposite.setLayoutData(twd);
                myCurrentFile = myFile;
                myCurrentNavajo = n;
                System.err.println(">>>>>>>>>>>>>>>>>>>>>> SETTING NAVAJO IN TML VIEWER");

                //                final NavajoBrowser nb = NavajoBrowser.getInstance();
                //                myPanel.navajoSelected("aap", n,myFile);
                if (formComposite != null) {
                    formComposite.setNavajo(n, myCurrentFile);
                    formComposite.reflow();
                    //                  formComposite.reflow();
//                    mainPanel.layout();
                } else {
                    System.err.println("hmmm. No formComposite");
                }
            }
        });

    }

}
