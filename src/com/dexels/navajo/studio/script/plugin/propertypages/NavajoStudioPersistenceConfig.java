/*
 * Created on Apr 26, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.studio.script.plugin.propertypages;

import java.io.*;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.dialogs.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.document.nanoimpl.*;
import com.dexels.navajo.studio.script.plugin.*;
import com.dexels.navajo.studio.script.plugin.editors.*;

/**
 * @author Administrator
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class NavajoStudioPersistenceConfig extends PropertyPage {

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
        tfc = new TmlFormComposite(null, parent);
        GridData gd = new GridData(GridData.FILL, GridData.FILL, true, true);
        gd.grabExcessHorizontalSpace = true;
        gd.grabExcessVerticalSpace = true;
        tfc.setLayoutData(gd);
        try {
            loadContents();
        } catch (Exception e) {
            e.printStackTrace();
        }
        parent.layout();
        return tfc;
    }

    public void loadContents() throws XMLParseException, CoreException, NavajoPluginException {
        IProject ip = NavajoScriptPluginPlugin.getDefault().getDefaultNavajoProject();
        IFile iff = NavajoScriptPluginPlugin.getDefault().getPersistenceXml(ip);
        InputStream contents = iff.getContents();
        Navajo n = NavajoFactory.getInstance().createNavajo(contents);
        try {
            if (contents!=null) {
                contents.close();
             
         }

        } catch (IOException e) {
              e.printStackTrace();
              throw new CoreException(Status.CANCEL_STATUS);
        }
        tfc.setNavajo(n, iff,null);
    }
}
