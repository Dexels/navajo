/*
 * Created on Mar 31, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.studio.script.plugin.swtimpl;

import java.util.*;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.studio.script.plugin.editors.*;

/**
 * @author Administrator
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class PropertyModifier implements ICellModifier {

    // private final TmlEditor myEditor;
    private final StructuredViewer myViewer;

    /**
     * @param myEditor
     *  
     */
    public PropertyModifier(StructuredViewer myViewer) {
        super();
        //        this.myEditor = myEditor;
        this.myViewer = myViewer;
        // TODO Auto-generated constructor stub
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.ICellModifier#canModify(java.lang.Object,
     *      java.lang.String)
     */
    public boolean canModify(Object element, String property) {
        System.err.println("Element type: " + element.getClass());
        if (element instanceof Property) {
            Property p = (Property) element;
            return p.isDirIn();
        }
        return false;
        //        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.ICellModifier#getValue(java.lang.Object,
     *      java.lang.String)
     */
    public Object getValue(Object element, String property) {
        // TODO Auto-generated method stub
        System.err.println("property: " + property + " type: " + element.getClass());
        Message mm = (Message) element;
        Property p = mm.getProperty(property);
        return p.getValue();
        //        if ("type".equals(property)) {
        //            return p.getType();
        //        }
        //        if ("value".equals(property)) {
        //            return p.getValue();
        //        }
        //        if ("direction".equals(property)) {
        //            return p.getDirection();
        //        }
        //        if ("cardinality".equals(property)) {
        //            return p.getCardinality();
        //        }
        //        if ("selections".equals(property)) {
        //            try {
        //                return p.getAllSelections();
        //            } catch (NavajoException e) {
        //                // TODO Auto-generated catch block
        //                e.printStackTrace();
        //                return null;
        //            }
        //            
        //        }
        //
        //       return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.ICellModifier#modify(java.lang.Object,
     *      java.lang.String, java.lang.Object)
     */
    public void modify(Object element, String property, Object value) {
        if (element instanceof Item) {
            element = ((Item) element).getData();
        }
        System.err.println("Element type:" + element.getClass());
        Message mm = (Message) element;
        Property p = mm.getProperty(property);
        p.setValue("" + value);
        myViewer.update(element, null);
        //        myEditor.doSave(null);
        //        myEditor.refresh();
    }

}
