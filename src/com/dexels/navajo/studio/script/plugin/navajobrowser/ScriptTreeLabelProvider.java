/*
 * Created on Feb 9, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.studio.script.plugin.navajobrowser;

import org.eclipse.jface.viewers.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.ui.*;

import com.sun.media.sound.*;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ScriptTreeLabelProvider extends LabelProvider {

    private final static Image scriptDir = PlatformUI.getWorkbench().getSharedImages().getImage(PlatformUI.getWorkbench().getSharedImages().IMG_OBJ_FOLDER);
    private final static Image initScript = PlatformUI.getWorkbench().getSharedImages().getImage(PlatformUI.getWorkbench().getSharedImages().IMG_OBJ_FILE);
    private final static Image processScript = PlatformUI.getWorkbench().getSharedImages().getImage(PlatformUI.getWorkbench().getSharedImages().IMG_OBJ_FILE);
    private final static Image server = PlatformUI.getWorkbench().getSharedImages().getImage(PlatformUI.getWorkbench().getSharedImages().IMG_TOOL_NEW_WIZARD);
    private final static Image root = null;
    
    public ScriptTreeLabelProvider() {
        super();
        ImageLoader l = new ImageLoader();
 
//    	action1.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().
//    			getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));
        
        
    }

    public String getText(Object element) {
    	return element == null ? "" : element.toString();
    }
    
    public Image getImage(Object element) {
        if (element instanceof ScriptDir) {
            return scriptDir;
        }
        if (element instanceof ServerConnection) {
            return server;
        }
        if (element instanceof ScriptFile) {
            ScriptFile sf = (ScriptFile)element;
            if (sf.getName().indexOf("Init")!=-1) {
                return initScript;
            }

            return processScript;
        }
        return root;
    }
    
}
