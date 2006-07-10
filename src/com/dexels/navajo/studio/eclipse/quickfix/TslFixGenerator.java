/*
 * Created on Jun 6, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.studio.eclipse.quickfix;

import java.util.*;

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
        System.err.println("GETTING RESOLUTIONS FROM TSLFIXGENERATOR");
            int code = marker.getAttribute("code", 0);
            TslCompileException tce = null;
            try {
                tce = (TslCompileException)marker.getAttribute("tslCompileException");
            } catch (CoreException e) {
                System.err.println("shit.");
                e.printStackTrace();
            }
            if (tce==null) {
                System.err.println("No tce, creating dummy");
                return new IMarkerResolution[]{new TslFixField("aap","noot")};
            }
            if (tce.getSolutions()!=null) {
                IMarkerResolution[] imr = new IMarkerResolution2[tce.getSolutions().size()];
                int i = 0;
                for (Iterator iter = tce.getSolutions().keySet().iterator(); iter.hasNext();) {
                    String element = (String) iter.next();
                    imr[i++] = new TslFixField(element,(String)tce.getSolutions().get(element));
                }
                
                return imr;
            }
            System.err.println("No solutions, creating dummy");
            return new IMarkerResolution[]{new TslFixField("mies","wim")};
    }

    public boolean hasResolutions(IMarker marker) {
        // TODO Auto-generated method stub
        return true;
    }

}
