/*
 * Created on Jun 6, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.studio.eclipse.quickfix;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.ui.*;

import com.dexels.navajo.mapping.compiler.*;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class TslFixField implements IMarkerResolution2 {

    /* (non-Javadoc)
     * @see org.eclipse.ui.IMarkerResolution#getLabel()
     */
    private final String name;
    private final String value;
    
    public TslFixField(String name, String value) {
        System.err.println("CREATED TSLFIXFIELD: "+name+" / "+value);
        this.name = name;
        this.value = value;
    }
    
    public String getLabel() {
        return name;
    }

    /* (non-Javadoc)
     * @see org.eclipse.ui.IMarkerResolution#run(org.eclipse.core.resources.IMarker)
     */
    public void run(IMarker marker) {
        IResource r = marker.getResource();
        System.err.println("AAP: "+r.getFullPath()+(" code: "+name+" val: "+value));
        int start = marker.getAttribute(IMarker.CHAR_START, 0);
        int end = marker.getAttribute(IMarker.CHAR_END, 0);
    }

    public String getDescription() {
       
        return "Compile problem:";
    }

    public Image getImage() {
        // TODO Auto-generated method stub
        return null;
    }

}
