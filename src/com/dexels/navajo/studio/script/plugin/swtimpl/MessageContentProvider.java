/*
 * Created on Mar 31, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.studio.script.plugin.swtimpl;

import java.util.*;

import org.eclipse.core.internal.resources.*;
import org.eclipse.core.runtime.*;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.*;

import com.dexels.navajo.document.*;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class MessageContentProvider extends LabelProvider implements IStructuredContentProvider,  ITableLabelProvider, IColorProvider, ITreeContentProvider {

    /**
     * 
     */
//    private final Message myMessage;
//    private Color selectedColor = new Color(220, 220, 255);
//    private Color highColor = new Color(255, 255, 255);
//    private Color lowColor = new Color(240, 240, 240);

    public MessageContentProvider() {
        super();
//        myMessage = m;
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
     */
    public Object[] getElements(Object inputElement) {
        Message m = (Message)inputElement;
        Object[] oo = m.getAllMessages().toArray();
        System.err.println("MessageContentProvider returning: "+oo.length);
        return oo;
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.viewers.IContentProvider#dispose()
     */
    public void dispose() {
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
     */
    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnImage(java.lang.Object, int)
     */
    public Image getColumnImage(Object element, int columnIndex) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnText(java.lang.Object, int)
     */
    public String getColumnText(Object element, int columnIndex) {
        if (!(element instanceof Message)) {
            System.err.println("Class cast ex: "+element.getClass());
        }
        Message current = (Message)element;
        ArrayList props = getRecursiveProperties(current);
        if (columnIndex >= props.size()) {
            return "xxx";
        }
        Property pp = (Property)props.get(columnIndex);
        return pp.getValue();
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.viewers.IBaseLabelProvider#isLabelProperty(java.lang.Object, java.lang.String)
     */
    public boolean isLabelProperty(Object element, String property) {
        
        return false;
    }
    
    public Color getForeground(Object element) {
        // Always use default.
        return null;
    }

    /** @see IColorProvider#getBackground(Object) */
    public Color getBackground(Object element) {
        Message m = (Message)element;
        int ii = m.getIndex();
        System.err.println("Checking index: "+ii);
        if (ii % 2==0) {
            // Red background if balance negative.
//            return Display.getCurrent().getSystemColor(SWT.COLOR_RED);
            return new Color(null,240, 240, 240);
        } else {
            // Default background color if balance positive or zero.
            return null;
        }
    }    
//  private Color selectedColor = new Color(220, 220, 255);
//  private Color highColor = new Color(255, 255, 255);
//  private Color lowColor = new Color(240, 240, 240);

    /* (non-Javadoc)
     * @see org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.Object)
     */
    public Object[] getChildren(Object parentElement) {
        Message m = (Message)parentElement;
        Object[] oo = m.getAllMessages().toArray();
        System.err.println("MessageContentProvider returning: "+oo.length);
        return oo;

       }

    /* (non-Javadoc)
     * @see org.eclipse.jface.viewers.ITreeContentProvider#getParent(java.lang.Object)
     */
    public Object getParent(Object element) {
        // TODO Auto-generated method stub
        Message m = (Message)element;
        return m.getParentMessage();
//       return null;
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(java.lang.Object)
     */
    public boolean hasChildren(Object element) {
        // TODO Auto-generated method stub
        Message m = (Message)element;
        return m.getArraySize()>0;
//        return false;
    }

    
    public ArrayList getRecursiveProperties(Message m) {
        ArrayList l = new ArrayList();
        l.addAll(m.getAllProperties());
        ArrayList n = m.getAllMessages();
        for (Iterator iter = n.iterator(); iter.hasNext();) {
            Message element = (Message) iter.next();
            l.addAll(getRecursiveProperties(element));
        }
        return l;
    }

    
//    private final Set listeners = new HashSet();
}
