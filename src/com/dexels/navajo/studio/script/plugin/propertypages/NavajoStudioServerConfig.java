package com.dexels.navajo.studio.script.plugin.propertypages;

import java.io.IOException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.dialogs.PropertyPage;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.nanoimpl.XMLParseException;
import com.dexels.navajo.studio.script.plugin.NavajoPluginException;
import com.dexels.navajo.studio.script.plugin.NavajoScriptPluginPlugin;
import com.dexels.navajo.studio.script.plugin.editors.TmlFormComposite;

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
