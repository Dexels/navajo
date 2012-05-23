/*
 * Created on Apr 6, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.swtclient;

import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CheckboxCellEditor;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.ICellEditorListener;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.ScrolledForm;

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
public class SwtFactory {

    private static SwtFactory instance;

    /**
     * @param elt
     * @return
     */

    public static SwtFactory getInstance() {
        if (instance == null) {
            instance = new SwtFactory();
        }
        return instance;
    }

    public CellEditor createTableEditor(Table tv, final Property elt) {
        try {
            if (elt.getType().equals(Property.SELECTION_PROPERTY)) {
                Object[] oo = elt.getAllSelections().toArray();
                String[] ss = new String[oo.length+1];
                ss[0] = "-";
                for (int i = 0; i < oo.length; i++) {
                    Selection s = (Selection) oo[i];
                    ss[i+1] = s.getName();
                }
                final ComboBoxCellEditor cb = new ComboBoxCellEditor(tv, ss);
                cb.addListener(new ICellEditorListener(){

                    public void applyEditorValue() {
                    }

                    public void cancelEditor() {
                    }

                    public void editorValueChanged(boolean oldValidState, boolean newValidState) {
                       
                    }});
               return cb;
            }
            if (elt.getType().equals(Property.BOOLEAN_PROPERTY)) {
                CheckboxCellEditor cce = null;
                cce = new CheckboxCellEditor(tv);
//                cce.create(tv);
                return cce;
            }
            if (elt.getType().equals(Property.INTEGER_PROPERTY)) {
                TextCellEditor textEditor = new TextCellEditor(tv);
                ((Text) textEditor.getControl()).addVerifyListener(

                new VerifyListener() {
                    public void verifyText(VerifyEvent e) {
                        e.doit = "-0123456789".indexOf(e.text) >= 0;
                    }
                });
                return textEditor;
            }
            if (elt.getType().equals(Property.FLOAT_PROPERTY)) {
                TextCellEditor textEditor = new TextCellEditor(tv);
                ((Text) textEditor.getControl()).addVerifyListener(

                new VerifyListener() {
                    public void verifyText(VerifyEvent e) {
                        e.doit = "-0123456789.+eE".indexOf(e.text) >= 0;
                    }
                });
                return textEditor;
            }
            if (elt.getType().equals(Property.STRING_PROPERTY)) {
                TextCellEditor textEditor = new TextCellEditor(tv);
                int len = elt.getLength();
                if (len > 0) {
                    ((Text) textEditor.getControl()).setTextLimit(elt.getLength());
                }
                return textEditor;
            }

        } catch (NavajoException e) {
            e.printStackTrace();

        }
        return new TextCellEditor(tv);
    }

    /**
     * @param element
     * @param spb
     */

    public TableViewer addTable(final Message element, Composite spb) {
        //        Table t = getKit().createTable(spb,SWT.FULL_SELECTION|SWT.V_SCROLL);
        final TableViewer tv = new TableViewer(spb, SWT.FULL_SELECTION);
        final MessageContentProvider mc = new MessageContentProvider();
        tv.setLabelProvider(mc);
        tv.setContentProvider(mc);
        tv.setInput(element);
        tv.getTable().setHeaderVisible(true);
        tv.getTable().setLinesVisible(true);

        //      Set up the table layout
        TableLayout layout = new TableLayout();
        if (element.getArraySize() == 0) {
            return tv;
        }
        tv.getTable().setLayout(layout);
        Message m = element.getMessage(0);
        List<Property> al = m.getAllProperties();
        System.err.println("Got properties for table. Count: "+al.size());
        System.err.println("AL: "+al);
        CellEditor[] editors = new CellEditor[al.size()];
        String[] colNames = new String[al.size()];
        int count = 0;
        for (Iterator<Property> iter = al.iterator(); iter.hasNext();) {
            final Property elt = iter.next();
            //            layout.addColumnData(new ColumnWeightData(33, 75, true));
            TableColumn tc = new TableColumn(tv.getTable(), SWT.LEFT);
            tc.setText(elt.getName());
            colNames[count] = elt.getName();
            editors[count] = SwtFactory.getInstance().createTableEditor(tv.getTable(), elt);
            tc.addSelectionListener(new SelectionAdapter() {
                public void widgetSelected(SelectionEvent e) {
                    ((PropertySorter) tv.getSorter()).setPropertyName(elt.getName());
                    
                    tv.getSorter().sort(tv, mc.getElements(element));
                    tv.refresh();
                }
            });

            count++;
        }
        tv.setColumnProperties(colNames);
        tv.setCellEditors(editors);
        tv.setSorter(new PropertySorter());
        tv.setCellModifier(new PropertyModifier(tv));
        for (int i = 0; i < tv.getTable().getColumnCount(); i++) {
            tv.getTable().getColumn(i).pack();
        }
        return tv;
    }

    public GenericPropertyComponent createProperty(Composite spb) {
        GenericPropertyComponent gpc = new GenericPropertyComponent(spb);
        //        gpc.getComposite().setLayoutData(new GridData(SWT.FILL, SWT.CENTER,
        // true, false));
        //        spb.layout();
        return gpc;
        //        gpc.getComposite().setBackground(new Color(null,200,100,100));
    }
    public GenericPropertyComponent createProperty(Composite spb, ScrolledForm f) {
        GenericPropertyComponent gpc = new GenericPropertyComponent(spb,f);
        //        gpc.getComposite().setLayoutData(new GridData(SWT.FILL, SWT.CENTER,
        // true, false));
        //        spb.layout();
        return gpc;
        //        gpc.getComposite().setBackground(new Color(null,200,100,100));
    }

    public TreeViewer createNavajoTree(final Navajo element, Composite spb) {
        final TreeViewer tv = new TreeViewer(spb, SWT.BORDER);
        final MessageContentProvider mc = new MessageContentProvider();
        tv.setLabelProvider(mc);
        tv.setContentProvider(mc);
        tv.setInput(element);
        tv.setSorter(new PropertySorter());
        return tv;
    }

}