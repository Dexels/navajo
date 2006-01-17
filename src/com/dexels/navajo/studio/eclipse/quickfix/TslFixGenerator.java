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
public class TslFixGenerator implements IMarkerResolutionGenerator {

    /* (non-Javadoc)
     * @see org.eclipse.ui.IMarkerResolutionGenerator#getResolutions(org.eclipse.core.resources.IMarker)
     */
    public IMarkerResolution[] getResolutions(IMarker marker) {
            int code = marker.getAttribute("code", 0);
            System.err.println("<><>CODE: "+code);
            if (code != 0) {
                TslFixField tff = new TslFixField(code);
                TslFixField tff2 = new TslFixField(code);
                TslFixField tff3 = new TslFixField(code);
                return new IMarkerResolution[]{tff,tff2,tff3};
            }
        return new IMarkerResolution[]{};
    }

}
