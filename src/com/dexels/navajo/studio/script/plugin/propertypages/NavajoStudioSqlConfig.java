/*
 * Created on Apr 25, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.studio.script.plugin.propertypages;

import java.io.IOException;
import java.io.InputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Status;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.dialogs.PropertyPage;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.nanoimpl.XMLParseException;
import com.dexels.navajo.studio.script.plugin.NavajoPluginException;
import com.dexels.navajo.studio.script.plugin.NavajoScriptPluginPlugin;
import com.dexels.navajo.studio.script.plugin.editors.TmlFormComposite;

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
