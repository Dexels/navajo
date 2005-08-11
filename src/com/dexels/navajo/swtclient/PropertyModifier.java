/*
 * Created on Mar 31, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.swtclient;

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
        System.err.println("PROPERTYMODIFIER!!!!!!!!!!!!!!!!!");
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
        if (element instanceof Message) {
            Message mm = (Message) element;
            Property p = mm.getProperty(property);
            if (p!=null) {
                System.err.println("Property type: "+p.getType());
                return p.isDirIn();
            } else {
                System.err.println("Not found");
            }
            return false;
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
        if (Property.SELECTION_PROPERTY.equals(p.getType())) {
            if (!Property.CARDINALITY_SINGLE.equals(p.getCardinality())) {
                System.err.println("I DONT SUPPORT CARDINALITY PLUS IN TABLES DAMMIT");
            }
            try {
                ArrayList al = p.getAllSelections();
                for (int i = 0; i < al.size(); i++) {
                    Selection current =  (Selection)al.get(i);
                    System.err.println("NAME: "+current.getName());
                    System.err.println("VALUE:"+current.getValue());
                }
               for (int i = 0; i < al.size(); i++) {
                    Selection current =  (Selection)al.get(i);
                    if (current.isSelected()) {
                        return new Integer(i+1);
                    }
                }
            } catch (NavajoException e) {
                 e.printStackTrace();
            }
            return new Integer(0);
        }
        if (Property.BOOLEAN_PROPERTY.equals(p.getType())) {
            return Boolean.valueOf(p.getValue());
        }
        
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
        if (value==null) {
            System.err.println("Null value!");
        } else {
            System.err.println("Value type:" + value.getClass());
        }
        Message mm = (Message) element;
        Property p = mm.getProperty(property);


        if (Property.SELECTION_PROPERTY.equals(p.getType())) {
            System.err.println("Setting selection property");
            if (!Property.CARDINALITY_SINGLE.equals(p.getCardinality())) {
                System.err.println("I DONT SUPPORT CARDINALITY PLUS IN TABLES DAMMIT");
            }
                try {
                    int index = ((Integer)value).intValue();
                    ArrayList al = p.getAllSelections();
                    
                    // MAYBE DUMMY?
                    if (index==0) {
                        p.setSelected(new ArrayList());
                    } else {
                        Selection s = (Selection)al.get(index-1);
                        p.setSelected(s);
                        System.err.println("Index: "+index+" s: "+s.getName()+" val: "+s.getValue());
                  }
                 } catch (NavajoException e) {
                      e.printStackTrace();
                }
        }
        if (Property.BOOLEAN_PROPERTY.equals(p.getType())) {
            p.setAnyValue(value);
        }

        
        
        
        p.setValue("" + value);
        myViewer.update(element, null);
        //        myEditor.doSave(null);
        //        myEditor.refresh();
    }

}
