package com.dexels.navajo.studio.script.plugin.propertypages;

import java.io.*;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.eclipse.swt.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.dialogs.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.document.nanoimpl.*;
import com.dexels.navajo.studio.script.plugin.*;
import com.dexels.navajo.studio.script.plugin.editors.*;

/*
 * Created on Apr 25, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

/**
 * @author Administrator
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class NavajoStudioServerConfig extends PropertyPage {

    private TmlFormComposite tfc;

    protected void performDefaults() {

        super.performDefaults();
        try {
            loadContents();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.preference.PreferencePage#createContents(org.eclipse.swt.widgets.Composite)
     */
    protected Control createContents(Composite parent) {
        //        parent.setLayout(new FillLayout(SWT.VERTICAL));
        tfc = new TmlFormComposite(parent);
        GridData gd = new GridData(GridData.FILL, GridData.FILL, true, true);
        tfc.setLayoutData(gd);
        System.err.println("Layoutclass: " + parent.getLayout().getClass());
        try {
            loadContents();
        } catch (Exception e) {
            e.printStackTrace();
        }
        parent.layout();
        return tfc;
    }

    public void loadContents() throws XMLParseException, IOException, CoreException, NavajoPluginException {
        IProject ip = NavajoScriptPluginPlugin.getDefault().getDefaultNavajoProject();
        IFile iff = NavajoScriptPluginPlugin.getDefault().getServerXml(ip);
        Navajo n = NavajoScriptPluginPlugin.getDefault().loadNavajo(iff);
        tfc.setNavajo(n, iff,null);
    }
}
