/*
 * Created on Jun 6, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.studio.eclipse.quickfix;

import java.util.Iterator;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.ui.IMarkerResolution;
import org.eclipse.ui.IMarkerResolution2;
import org.eclipse.ui.IMarkerResolutionGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.mapping.compiler.TslCompileException;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */



public class TslFixGenerator implements IMarkerResolutionGenerator {

	private final static Logger logger = LoggerFactory
			.getLogger(TslFixGenerator.class);

	/* (non-Javadoc)
     * @see org.eclipse.ui.IMarkerResolutionGenerator#getResolutions(org.eclipse.core.resources.IMarker)
     */
    public IMarkerResolution[] getResolutions(IMarker marker) {
//            int code = marker.getAttribute("code", 0);
            TslCompileException tce = null;
            try {
                tce = (TslCompileException)marker.getAttribute("tslCompileException");
            } catch (CoreException e) {
                logger.error("Error: ", e);
            }
            if (tce==null) {
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
            return new IMarkerResolution[]{new TslFixField("mies","wim")};
    }


}
