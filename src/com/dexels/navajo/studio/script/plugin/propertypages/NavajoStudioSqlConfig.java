/*
 * Created on Apr 25, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
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

/**
 * @author Administrator
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class NavajoStudioSqlConfig extends PropertyPage {

    protected void performDefaults() {

        super.performDefaults();
        try {
            loadContents();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private TmlFormComposite tfc;

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.preference.PreferencePage#createContents(org.eclipse.swt.widgets.Composite)
     */
    protected Control createContents(Composite parent) {
        parent.setLayout(new FillLayout(SWT.VERTICAL));
        tfc = new TmlFormComposite(parent);
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

    public void loadContents() throws XMLParseException, IOException, CoreException, NavajoPluginException {
        IProject ip = NavajoScriptPluginPlugin.getDefault().getDefaultNavajoProject();
        IFile iff = NavajoScriptPluginPlugin.getDefault().getSqlXml(ip);
        //        XMLElement xx = new XMLElement();
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
