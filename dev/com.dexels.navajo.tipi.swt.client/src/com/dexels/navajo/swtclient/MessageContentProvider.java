/*
 * Created on Mar 31, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.swtclient;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.Selection;

/**
 * @author Administrator
 *  
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class MessageContentProvider extends LabelProvider implements IStructuredContentProvider, ITableLabelProvider, IColorProvider,
        ITreeContentProvider {

	private final static Logger logger = LoggerFactory
			.getLogger(MessageContentProvider.class);
	
	public MessageContentProvider() {
        super();
        //        myMessage = m;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
     */
    public Object[] getElements(Object inputElement) {
        Object[] oo = null;
        if (inputElement instanceof Message) {
            Message m = (Message) inputElement;
            oo = m.getAllMessages().toArray();
        }
        if (inputElement instanceof Navajo) {
            Navajo m = (Navajo) inputElement;
            try {
                oo = m.getAllMessages().toArray();
            } catch (NavajoException e) {
                logger.error("Error: ",e);
            }
        }

//        logger.info("MessageContentProvider returning: " + oo.length);
        return oo;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.IContentProvider#dispose()
     */
    public void dispose() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer,
     *      java.lang.Object, java.lang.Object)
     */
    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnImage(java.lang.Object,
     *      int)
     */
    public Image getColumnImage(Object element, int columnIndex) {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnText(java.lang.Object,
     *      int)
     */
    public String getColumnText(Object element, int columnIndex) {
        if (!(element instanceof Message)) {
            logger.info("Class cast ex: " + element.getClass());
        }
        Message current = (Message) element;
        List <Property> props = getRecursiveProperties(current);
        if (columnIndex >= props.size()) {
            return "xxx";
        }
        Property pp = props.get(columnIndex);
        if (Property.SELECTION_PROPERTY.equals(pp.getType())) {
            try {
                
                Selection ss = pp.getSelected();
                if (ss!=null) {
                    if (Selection.DUMMY_SELECTION.equals(ss.getName())) {
                        return "-";
                    }
                    return ss.getName();
                } else {
                    logger.info("Thought this would not happen");
                    return "-";
                }
            } catch (NavajoException e) {
                logger.error("Error: ",e);
                return "-";
            } 
        }
        Object value = pp.getTypedValue();
        if (value==null) {
            return ">null<";
        }
        return ""+value;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.IBaseLabelProvider#isLabelProperty(java.lang.Object,
     *      java.lang.String)
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
        Message m = (Message) element;
        int ii = m.getIndex();
//        logger.info("Checking index: " + ii);
        if (ii % 2 == 0) {
            // Red background if balance negative.
            //            return Display.getCurrent().getSystemColor(SWT.COLOR_RED);
            return new Color(null, 240, 240, 240);
        } else {
            // Default background color if balance positive or zero.
            return null;
        }
    }

    //  private Color selectedColor = new Color(220, 220, 255);
    //  private Color highColor = new Color(255, 255, 255);
    //  private Color lowColor = new Color(240, 240, 240);

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.Object)
     */
    public Object[] getChildren(Object parentElement) {
        Message m = (Message) parentElement;
        Object[] oo = m.getAllMessages().toArray();
//        logger.info("MessageContentProvider returning: " + oo.length);
        return oo;

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.ITreeContentProvider#getParent(java.lang.Object)
     */
    public Object getParent(Object element) {
        Message m = (Message) element;
        return m.getParentMessage();
        //       return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(java.lang.Object)
     */
    public boolean hasChildren(Object element) {
        Message m = (Message) element;
        return m.getArraySize() > 0;
        //        return false;
    }

    public List<Property> getRecursiveProperties(Message m) {
        List<Property> l = new ArrayList<Property>();
        l.addAll(m.getAllProperties());
        List<Message> n = m.getAllMessages();
        for (Iterator<Message> iter = n.iterator(); iter.hasNext();) {
            Message element = iter.next();
            l.addAll(getRecursiveProperties(element));
        }
        return l;
    }

    //    private final Set listeners = new HashSet();
}
