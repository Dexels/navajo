/*
 * Created on Feb 9, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.studio.script.plugin.navajobrowser;

import java.util.*;

import org.eclipse.jface.viewers.*;
import org.eclipse.ui.*;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ScriptTreeContentProvider implements IStructuredContentProvider, ITreeContentProvider {

    /**
     * 
     */
private ScriptDir myRoot = null;

	private final IViewSite mySite;

    public ScriptTreeContentProvider(IViewSite mySite) {
        super();
        this.mySite = mySite;
        
        
     }
    
    public void initialize(ScriptDir root) {
        myRoot = root;
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
     */
    public Object[] getElements(Object inputElement) {
		if (inputElement.equals(mySite)) {
		    Object[] oo =  myRoot.getChildren().toArray();
		    System.err.println("get elements in toplevel.. returning #" +oo.length);
		    return oo;
		}

	      if (inputElement instanceof ScriptDir) {
	            ScriptDir sd = (ScriptDir)inputElement;
			    System.err.println("get elements in scriptdir.. returning #" +sd.getChildCount());
	            return sd.getChildren().toArray();
	        }

		return new Object[0];
    }
//	public Object[] getElements(Object parent) {
//		if (parent.equals(getViewSite())) {
//			if (invisibleRoot==null) initialize();
//			return getChildren(invisibleRoot);
//		}
//		return getChildren(parent);
//	}

    /* (non-Javadoc)
     * @see org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.Object)
     */
    public Object[] getChildren(Object parentElement) {
        if (parentElement instanceof ScriptDir) {
            ScriptDir sd = (ScriptDir)parentElement;
            Object[] oo = new Object[sd.getChildCount()];
            for (int i = 0; i < oo.length; i++) {
                oo[i] = sd.getChildAt(i);
            }
            return oo;
        }
        return null;
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.viewers.ITreeContentProvider#getParent(java.lang.Object)
     */
    public Object getParent(Object element) {
        if (element instanceof ScriptNode) {
            ScriptNode sd = (ScriptNode)element;
            return sd.getParent();
            // return ...
        }
        
        return null;
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(java.lang.Object)
     */
    public boolean hasChildren(Object element) {
        if (element instanceof ScriptNode) {
            ScriptNode sd = (ScriptNode)element;
            return sd.getChildCount()>0;
            // return ...
        }
        return false;
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.viewers.IContentProvider#dispose()
     */
    public void dispose() {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
     */
    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
        	System.err.println("inputChanged called!!!");

    }

}
