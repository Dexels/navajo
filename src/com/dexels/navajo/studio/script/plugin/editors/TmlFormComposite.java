/*
 * Created on Mar 29, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.studio.script.plugin.editors;

import java.util.*;


import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.*;
import org.eclipse.swt.custom.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.forms.events.*;
import org.eclipse.ui.forms.widgets.*;
import org.eclipse.ui.part.ViewPart;

import com.dexels.navajo.document.*;
import com.dexels.navajo.functions.*;
import com.dexels.navajo.studio.script.plugin.*;
import com.dexels.navajo.studio.script.plugin.swtimpl.*;
import com.sun.rsasign.*;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */


public class TmlFormComposite extends Composite {

    /**
     * @param parent
     * @param style
     */
    private final Form myForm;
    private final FormToolkit kit;
    private final MultiPageEditorExample myEditor;
    
    public TmlFormComposite(MultiPageEditorExample ee, Composite parent) {
        super(parent,SWT.NONE);
        myEditor = ee;
        kit = new FormToolkit(parent.getDisplay());
        myForm = kit.createForm(parent);
        myForm.getBody().setLayout(new GridLayout(1,true));
    }
    public Form getForm() {
        return myForm;
    }

    public void setNavajo(Navajo n, IFile myFile) {
        System.err.println("Setting navajo");
//        ScrolledPageBook book = new ScrolledPageBook(getForm().getBody());
        ScrolledComposite book = new ScrolledComposite(getForm().getBody(), SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
        Composite container = getKit().createComposite(book);
        getKit().adapt(book);
        getKit().adapt(container);
        
        
        
//        book.setContent(container);
        book.setLayoutData(new GridData(GridData.FILL_BOTH));
        container.setLayout(new RowLayout(SWT.VERTICAL));
        setMessages(n,myFile,container);
        setMethods(n,myFile);
        container.pack();
        book.setContent(container);
        book.setExpandHorizontal(true);
        book.setExpandVertical(true);
//        container.get
        System.err.println("Bookheight: "+book.getSize().y);
//     book.setMinWidth(book.getSize().x);
      book.setMinHeight(800);
      getForm().getBody().pack(true);
    }
    /**
     * @param n
     * @param myFile
     */
    private void setMessages(Navajo n, IFile myFile, Composite container) {
        ArrayList al;
         try {
            al = n.getAllMessages();
        } catch (NavajoException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return;
        }
        for (Iterator iter = al.iterator(); iter.hasNext();) {
            Message element = (Message) iter.next();
            System.err.println("Adding message: "+element.getName());
            addMessage(element,container);
        }
        
    }
    /**
     * @param element
     * @param spb
     */
    public void addMessage(Message element, Composite spb) {
//        Section ss = getKit().createSection(spb, Section.TITLE_BAR);

      ExpandableComposite ss = getKit().createExpandableComposite
      (spb, ExpandableComposite.TWISTIE);
      ss.setText("The Eclipse Forms toolset is:");
      ss.setExpanded(true);
      FormText ft = kit.createFormText(ss, true);
      ss.setClient(ft);        
        
        Composite s = getKit().createComposite(ss);
        ss.setClient(s);
        ss.setText(element.getName());
          if (Message.MSG_TYPE_ARRAY.equals(element.getType())) {
            System.err.println("adding table");
            s.setLayout(new FillLayout(SWT.HORIZONTAL));
          addTable(element,s);
        } else {
            s.setLayout(new GridLayout(2,true));
            addForm(element,s);
        }
    }

    /**
     * @param element
     * @param spb
     */
    private void addForm(Message element, Composite spb) {
        System.err.println("adding form");
        ArrayList al = element.getAllProperties();
        for (Iterator iter = al.iterator(); iter.hasNext();) {
            Property prop = (Property) iter.next();
            addProperty(prop,element,spb);
        }
    }
    /**
     * @param prop
     * @param element
     * @param spb
     */
    private void addProperty(Property prop, Message msg, Composite spb) {
//        Composite s = getKit().createComposite(spb);
//        s.setLayout(new RowLayout(SWT.HORIZONTAL));
//        System.err.println("adding property");
//        Label l = getKit().createLabel(s, prop.getName());
//        Text tt = new Text(s,SWT.SINGLE);
//        getKit().adapt(tt,true,true);
//        tt.setToolTipText(prop.getDescription());
//        tt.setText(prop.getValue());

        GenericPropertyComponent gpc = new GenericPropertyComponent(spb,getKit(),getForm());
        gpc.getComposite().setLayoutData(new GridData(SWT.FILL,SWT.CENTER,true,false));
//        gpc.getComposite().setBackground(new Color(null,200,100,100));
        gpc.setProperty(prop);
    }
    /**
     * @param element
     * @param spb
     */
    private void addTable(final Message element, Composite spb) {
//        Table t = getKit().createTable(spb,SWT.FULL_SELECTION|SWT.V_SCROLL);
        final TableViewer tv = new TableViewer(spb,SWT.FULL_SELECTION);
        final MessageContentProvider mc = new MessageContentProvider();
        tv.setLabelProvider(mc);
        tv.setContentProvider(mc);
        tv.setInput(element);
        tv.getTable().setHeaderVisible(true);
        tv.getTable().setLinesVisible(true);
 
        //      Set up the table layout
        TableLayout layout = new TableLayout();
         if (element.getArraySize()==0) {
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
           TableColumn tc = new TableColumn(tv.getTable() ,SWT.LEFT);
            tc.setText(elt.getName());
            System.err.println("Added column: "+elt.getName());
            colNames[count] = elt.getName();
            editors[count] = createEditor(tv,elt);
            tc.addSelectionListener(new SelectionAdapter() {
    	       	
    				public void widgetSelected(SelectionEvent e) {
    				    System.err.println("Sort hit: "+elt.getName());
    				    ((PropertySorter)tv.getSorter()).setPropertyName(elt.getName());
    				    tv.getSorter().sort(tv, mc.getElements(element));
    				    tv.refresh();
    				}
    			});
            
            count++;
        }
        tv.setColumnProperties(colNames);
        tv.setCellEditors(editors);
        tv.setSorter(new PropertySorter());
        tv.setCellModifier(new PropertyModifier(myEditor,tv));
        for (int i = 0; i < tv.getTable().getColumnCount(); i++) {
            tv.getTable().getColumn(i).pack();
        }        
//        for (Iterator iter = element.getAllMessages().iterator(); iter.hasNext();) {
//            Message current = (Message) iter.next();
//            ArrayList columns = current.getAllProperties();
//            String[] texts = new String[columns.size()];
//            int ii = 0;
//            for (Iterator itr = columns.iterator(); itr.hasNext();) {
//                Property pro = (Property) itr.next();
//                texts[ii] = pro.getValue();
//                ii++;
//            }
//            TableItem ti = new TableItem(t,0);
//            ti.setText(texts);
//            
//        }
        
    }
    /**
     * @param elt
     * @return
     */
    private CellEditor createEditor(TableViewer tv, Property elt) {
        try {
            if (elt.getType().equals(Property.SELECTION_PROPERTY)) {
                Object[] oo = elt.getAllSelections().toArray();
                String[] ss = new String[oo.length];
                for (int i = 0; i < oo.length; i++) {
                    Selection s = (Selection)oo[i];
                    ss[i] = s.getName();
                }
                ComboBoxCellEditor cb = new ComboBoxCellEditor(tv.getTable(),ss);
                return cb;
            }
            if (elt.getType().equals(Property.BOOLEAN_PROPERTY)) {
                return new CheckboxCellEditor(tv.getTable());
            }
            if (elt.getType().equals(Property.INTEGER_PROPERTY)) {
                TextCellEditor textEditor = new TextCellEditor(tv.getTable());
                ((Text) textEditor.getControl()).addVerifyListener(

                 new VerifyListener() {
                        public void verifyText(VerifyEvent e) {
                            e.doit = "-0123456789".indexOf(e.text) >= 0;  
                        }
                    });
                return textEditor;
            }
            if (elt.getType().equals(Property.FLOAT_PROPERTY)) {
                TextCellEditor textEditor = new TextCellEditor(tv.getTable());
                ((Text) textEditor.getControl()).addVerifyListener(

                 new VerifyListener() {
                        public void verifyText(VerifyEvent e) {
                            e.doit = "-0123456789.+eE".indexOf(e.text) >= 0;  
                        }
                    });
                return textEditor;
            }
            if (elt.getType().equals(Property.STRING_PROPERTY)) {
                TextCellEditor textEditor = new TextCellEditor(tv.getTable());
                int len = elt.getLength();
                if (len>0) {
                    ((Text) textEditor.getControl()).setTextLimit(elt.getLength());
                }
                return textEditor;
            }

        } catch (NavajoException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
    
        }
        return new TextCellEditor(tv.getTable());
    }
    /**
     * @param n
     * @param myFile
     */
    private void setMethods(final Navajo n, final IFile myFile) {
        Section sss = getKit().createSection(getForm().getBody(), Section.TITLE_BAR);
//          Section sss = kit.createSection(getForm().getBody(), Section.TITLE_BAR);
        sss.setText("Methods:");
        
        Composite list = getKit().createComposite(sss);
        sss.setClient(list);
        GridData gd = new GridData(GridData.FILL,GridData.CENTER,true,false);
        gd.grabExcessHorizontalSpace = true;
        sss.setLayoutData(gd);
        list.setLayout(new RowLayout(SWT.HORIZONTAL));

        for (Iterator iter = n.getAllMethods().iterator(); iter.hasNext();) {
            final Method element = (Method) iter.next();
            System.err.println("Adding method: "+element.getName());
            Hyperlink hl = getKit().createHyperlink(list, element.getName(), SWT.NONE);
            hl.setHref(element.getName());
            hl.addHyperlinkListener(new HyperlinkAdapter() {
                public void linkActivated(HyperlinkEvent e) {
                    String href = (String)e.getHref();
                    if (myEditor!=null) {
                        myEditor.doSave(null);
                    }

                    IProject ipp = myFile.getProject();
                    IFile scriptFile = NavajoScriptPluginPlugin.getDefault().getScriptFile(ipp, element.getName());
                    try {
                        NavajoScriptPluginPlugin.getDefault().runNavajo(scriptFile,myFile);
                    } catch (CoreException e1) {
                        e1.printStackTrace();
                    }  
                }
            });
        }
        sss.pack();
    }
    /**
     * @return Returns the kit.
     */
    public FormToolkit getKit() {
        return kit;
    }
}
