/*
 * Created on Jun 6, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.studio.eclipse.quickfix;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.eclipse.ui.*;

import com.dexels.navajo.mapping.compiler.*;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class TslFixField implements IMarkerResolution {

    /* (non-Javadoc)
     * @see org.eclipse.ui.IMarkerResolution#getLabel()
     */
    private final int code;
    
    public TslFixField(int code) {
        this.code = code;
    }
    
    public String getLabel() {
        return "Je bent een aap. Geef het maar toe!";
    }

    /* (non-Javadoc)
     * @see org.eclipse.ui.IMarkerResolution#run(org.eclipse.core.resources.IMarker)
     */
    public void run(IMarker marker) {
        IResource r = marker.getResource();
        System.err.println("AAP: "+r.getFullPath()+(" code: "+code));
        int start = marker.getAttribute(IMarker.CHAR_START, 0);
        int end = marker.getAttribute(IMarker.CHAR_END, 0);
    }

}
