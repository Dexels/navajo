/*
 * Created on Feb 23, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.studio.script.plugin.navajobrowser;

import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.part.*;

/**
 * @author Administrator
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class TmlSource extends ViewPart {
    private static TmlSource instance;

    private Text myText;

    public TmlSource() {
        super();
        instance = this;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.IWorkbenchPart#createPartControl(org.eclipse.swt.widgets.Composite)
     */
    public void createPartControl(Composite parent) {
        myText = new Text(parent, SWT.MULTI | SWT.READ_ONLY);

    }

    public void setText(String s) {
        myText.setText(s);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.IWorkbenchPart#setFocus()
     */
    public void setFocus() {
        // TODO Auto-generated method stub
        if (myText != null) {
            myText.setFocus();

        }
    }

    public void dispose() {
        instance = null;
        super.dispose();
    }

    public static TmlSource getInstance() {
        return instance;
    }
}
