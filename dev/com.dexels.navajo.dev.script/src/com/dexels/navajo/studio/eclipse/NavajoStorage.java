/*
 * Created on Dec 14, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.studio.eclipse;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;

import org.eclipse.core.resources.IStorage;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.PlatformObject;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.studio.script.plugin.NavajoScriptPluginPlugin;

public class NavajoStorage extends PlatformObject implements IStorage {

    
    private final Navajo myNavajo;
    private final String myName;

    public NavajoStorage(String name, Navajo n) {
        myNavajo = n;
        myName = name;
    }
    
    public InputStream getContents() throws CoreException {
        ByteArrayInputStream bais;
        try {
            StringWriter sw = new StringWriter();
            myNavajo.write(sw);
            String ss = sw.toString();
//            System.err.println("Navajo: "+ss);
             bais = new ByteArrayInputStream(ss.getBytes());
            return bais;
        } catch (NavajoException e) {
            NavajoScriptPluginPlugin.getDefault().log("Error creating navajo storage object",e);
            return null;
        }
    }

    public IPath getFullPath() {
         return null;
    }

    public String getName() {
        return myName+".xml";
    }

    public boolean isReadOnly() {
        return true;
    }

    
}
