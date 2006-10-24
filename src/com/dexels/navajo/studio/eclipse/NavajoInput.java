/*
 * Created on Dec 14, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.studio.eclipse;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.eclipse.jface.resource.*;
import org.eclipse.ui.*;

import com.dexels.navajo.document.*;

public class NavajoInput extends PlatformObject implements IStorageEditorInput {

    private final NavajoStorage myStorage;
    
    public NavajoInput(String name,Navajo n) {
         myStorage = new NavajoStorage(name,n);
    }
    public IStorage getStorage() throws CoreException {
        return myStorage;
    }

    public boolean exists() {
        return true;
    }

    public ImageDescriptor getImageDescriptor() {
        return null;
    }

    public String getName() {
        return myStorage.getName();
    }

    public IPersistableElement getPersistable() {
           return null;
    }

    public String getToolTipText() {
        return "TML Browser source: "+myStorage.getName();
    }

}
