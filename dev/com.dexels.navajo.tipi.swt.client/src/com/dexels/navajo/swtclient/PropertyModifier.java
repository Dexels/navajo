/*
 * Created on Mar 31, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.swtclient;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.Selection;

/**
 * @author Administrator
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class PropertyModifier implements ICellModifier {

	
	private final static Logger logger = LoggerFactory
			.getLogger(PropertyModifier.class);
	private final StructuredViewer myViewer;

    /**
     * @param myEditor
     *  
     */
    public PropertyModifier(StructuredViewer myViewer) {
        super();
        //        this.myEditor = myEditor;
        this.myViewer = myViewer;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.ICellModifier#canModify(java.lang.Object,
     *      java.lang.String)
     */
    @Override
	public boolean canModify(Object element, String property) {
         if (element instanceof Property) {
            Property p = (Property) element;
            if (!p.isDirIn()) {
                return false;
            }
            if (myViewer  instanceof TableViewer) {
                TableViewer tv = (TableViewer)myViewer;
                CellEditor[] ice = tv.getCellEditors();
                  for (int i = 0; i < ice.length; i++) {
                    String propName = tv.getTable().getColumn(i).getText();
                    if (p.getName().equals(propName)) {
                         CellEditor cc = SwtFactory.getInstance().createTableEditor(tv.getTable(), p);
                        ice[i] = cc;
                        tv.setCellEditors(ice);
                    }
                }
                
            }
            
            return p.isDirIn();
        }
        if (element instanceof Message) {
            Message mm = (Message) element;
            Property p = mm.getProperty(property);
            if (p!=null) {
                 
                if (myViewer  instanceof TableViewer) {
                    TableViewer tv = (TableViewer)myViewer;
                    CellEditor[] ice = tv.getCellEditors();
                      for (int i = 0; i < ice.length; i++) {
                        String propName = tv.getTable().getColumn(i).getText();
                        if (p.getName().equals(propName)) {
                             CellEditor cc = SwtFactory.getInstance().createTableEditor(tv.getTable(), p);
                            ice[i] = cc;
                            tv.setCellEditors(ice);
                        }
                    }
                    
                }
                
                
                return p.isDirIn();
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
    @Override
	public Object getValue(Object element, String property) {
           Message mm = (Message) element;
        Property p = mm.getProperty(property);
        if (Property.SELECTION_PROPERTY.equals(p.getType())) {
            if (!Property.CARDINALITY_SINGLE.equals(p.getCardinality())) {
                logger.info("I DONT SUPPORT CARDINALITY PLUS IN TABLES DAMMIT");
            }
            try {
                ArrayList<Selection> al = p.getAllSelections();
//                for (int i = 0; i < al.size(); i++) {
//                    Selection current =  (Selection)al.get(i);
//                    logger.info("NAME: "+current.getName());
//                    logger.info("VALUE:"+current.getValue());
//                }
               for (int i = 0; i < al.size(); i++) {
                    Selection current =  al.get(i);
                    if (current.isSelected()) {
                        return new Integer(i+1);
                    }
                }
            } catch (NavajoException e) {
                 logger.error("Error: ",e);
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
        //                logger.error("Error: ",e);
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
    @Override
	public void modify(Object element, String property, Object value) {
        if (element instanceof Item) {
            element = ((Item) element).getData();
        }
  
        Message mm = (Message) element;
        Property p = mm.getProperty(property);


        if (Property.SELECTION_PROPERTY.equals(p.getType())) {
          
            if (!Property.CARDINALITY_SINGLE.equals(p.getCardinality())) {
                logger.info("I DONT SUPPORT CARDINALITY PLUS IN TABLES DAMMIT");
            }
                try {
                    int index = ((Integer)value).intValue();
                    List<Selection> al = p.getAllSelections();
                    
                    // MAYBE DUMMY?
                    if (index==0) {
                        p.setSelected(new ArrayList<String>());
                    } else {
                        Selection s = al.get(index-1);
                        p.setSelected(s);
                    }
                 } catch (NavajoException e) {
                      logger.error("Error: ",e);
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
