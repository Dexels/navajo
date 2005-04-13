/*
 * Created on Apr 6, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.studio.script.plugin.swtimpl;

import java.util.*;

import org.eclipse.jface.viewers.*;
import org.eclipse.swt.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.internal.*;

import com.dexels.navajo.document.*;
import com.sun.media.sound.*;

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

    public CellEditor createTableEditor(Table tv, Property elt) {
        try {
            if (elt.getType().equals(Property.SELECTION_PROPERTY)) {
                Object[] oo = elt.getAllSelections().toArray();
                String[] ss = new String[oo.length];
                for (int i = 0; i < oo.length; i++) {
                    Selection s = (Selection) oo[i];
                    ss[i] = s.getName();
                }
                ComboBoxCellEditor cb = new ComboBoxCellEditor(tv, ss);
                return cb;
            }
            if (elt.getType().equals(Property.BOOLEAN_PROPERTY)) {
                return new CheckboxCellEditor(tv);
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
            // TODO Auto-generated catch block
            e.printStackTrace();

        }
        return new TextCellEditor(tv);
    }

    /**
     * @param element
     * @param spb
     */

    public void addTable(final Message element, Composite spb) {
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
            System.err.println("Empty table");
            return;
        }
        // TODO Add definition message support
        tv.getTable().setLayout(layout);
        Message m = element.getMessage(0);
        ArrayList al = m.getAllProperties();
        CellEditor[] editors = new CellEditor[al.size()];
        String[] colNames = new String[al.size()];
        int count = 0;
        for (Iterator iter = al.iterator(); iter.hasNext();) {
            final Property elt = (Property) iter.next();
            //            layout.addColumnData(new ColumnWeightData(33, 75, true));
            TableColumn tc = new TableColumn(tv.getTable(), SWT.LEFT);
            tc.setText(elt.getName());
            System.err.println("Added column: " + elt.getName());
            colNames[count] = elt.getName();
            editors[count] = SwtFactory.getInstance().createTableEditor(tv.getTable(), elt);
            tc.addSelectionListener(new SelectionAdapter() {

                public void widgetSelected(SelectionEvent e) {
                    System.err.println("Sort hit: " + elt.getName());
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
    }

    public void addTableTree(final Message element, Composite spb) {
        //      Table t = getKit().createTable(spb,SWT.FULL_SELECTION|SWT.V_SCROLL);
        final TableTreeViewer tv = new TableTreeViewer(spb, SWT.FULL_SELECTION);
        final MessageContentProvider mc = new MessageContentProvider();
        tv.setLabelProvider(mc);
        tv.setContentProvider(mc);
        tv.setInput(element);
        tv.getTableTree().getTable().setHeaderVisible(true);
        tv.getTableTree().getTable().setLinesVisible(true);

        //      Set up the table layout
        TableLayout layout = new TableLayout();
        if (element.getArraySize() == 0) {
            System.err.println("Empty table");
            return;
        }
        // TODO Add definition message support
        tv.getTableTree().getTable().setLayout(layout);
        Message m = element.getMessage(0);
        ArrayList al = mc.getRecursiveProperties(m);
        CellEditor[] editors = new CellEditor[al.size()];
        String[] colNames = new String[al.size()];
        int count = 0;
        for (Iterator iter = al.iterator(); iter.hasNext();) {
            final Property elt = (Property) iter.next();
            //          layout.addColumnData(new ColumnWeightData(33, 75, true));
            TableColumn tc = new TableColumn(tv.getTableTree().getTable(), SWT.LEFT);
            tc.setText(elt.getName());
            System.err.println("Added column: " + elt.getName());
            colNames[count] = elt.getName();
            editors[count] = SwtFactory.getInstance().createTableEditor(tv.getTableTree().getTable(), elt);
            tc.addSelectionListener(new SelectionAdapter() {

                public void widgetSelected(SelectionEvent e) {
                    System.err.println("Sort hit: " + elt.getName());
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
        for (int i = 0; i < tv.getTableTree().getTable().getColumnCount(); i++) {
            tv.getTableTree().getTable().getColumn(i).pack();
        }

    }

    public GenericPropertyComponent createProperty(Composite spb) {
        GenericPropertyComponent gpc = new GenericPropertyComponent(spb);
//        gpc.getComposite().setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
//        spb.layout();
        return gpc;
        //        gpc.getComposite().setBackground(new Color(null,200,100,100));
    }

    public TreeViewer createNavajoTree(final Navajo element, Composite spb) {
        final TreeViewer tv = new TreeViewer(spb,  SWT.BORDER );
        final MessageContentProvider mc = new MessageContentProvider();
        tv.setLabelProvider(mc);
        tv.setContentProvider(mc);
        tv.setInput(element);
        tv.setSorter(new PropertySorter());
        return tv;
    }

}