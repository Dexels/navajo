/*
 * Created on Dec 14, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.studio.eclipse;

import org.eclipse.core.resources.IStorage;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.PlatformObject;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IPersistableElement;
import org.eclipse.ui.IStorageEditorInput;

import com.dexels.navajo.document.Navajo;

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
        return myStorage.getName()+".xml";
    }

    public IPersistableElement getPersistable() {
           return null;
    }

    public String getToolTipText() {
        return "TML Browser source: "+myStorage.getName();
    }

}
