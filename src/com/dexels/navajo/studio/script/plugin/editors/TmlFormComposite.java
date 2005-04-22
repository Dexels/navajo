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
import org.eclipse.debug.core.*;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.*;
import org.eclipse.swt.custom.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.forms.events.*;
import org.eclipse.ui.forms.widgets.*;
import org.eclipse.ui.internal.*;
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
    private final TmlEditor myEditor;
    private Composite mainMessageContainer;
    private ScrolledComposite mainMessageScroll;
    
    public TmlFormComposite(TmlEditor ee, Composite parent) {
        super(parent,SWT.NONE);
        myEditor = ee;
        kit = new FormToolkit(parent.getDisplay());
        myForm = kit.createForm(parent);
//        myForm.getBody().setLayout(new GridLayout(1,false));
        myForm.getBody().setLayout(new TableWrapLayout());
    }
    public Form getForm() {
        return myForm;
    }

    public void setNavajo(Navajo n, IFile myFile) {
        System.err.println("Setting navajo");
        mainMessageScroll = new ScrolledComposite(getForm().getBody(), SWT.BORDER | SWT.V_SCROLL);
        mainMessageScroll.setExpandHorizontal(true);
        mainMessageScroll.setExpandVertical(true);
             mainMessageContainer = getKit().createComposite(mainMessageScroll,SWT.NONE);
        //        getKit().adapt(book);
        getKit().adapt(mainMessageContainer);
//   		GridData gd = new GridData(GridData.FILL,GridData.BEGINNING,true,false);
//        gd.grabExcessHorizontalSpace = true;
//        gd.grabExcessVerticalSpace = true;
        TableWrapData td = new TableWrapData(TableWrapData.FILL_GRAB,TableWrapData.FILL_GRAB);
        mainMessageScroll.setLayoutData(td);
 
//        container.setLayoutData(gd);
        
        
        mainMessageContainer.setBackground(new Color(Display.getCurrent(),220,220,240));
        mainMessageContainer.setLayout(new TableWrapLayout());
        setMessages(n,mainMessageContainer);
        setMethods(n,myFile);
        mainMessageContainer.pack();
        mainMessageContainer.layout();
             mainMessageScroll.setContent(mainMessageContainer);
        mainMessageScroll.layout();
        mainMessageScroll.pack();
        
//        setTreeNavajo(n, myFile);
//      getForm().getBody().layout();
    }
    
    
    public void setTreeNavajo(Navajo n, IFile myFile) {
        System.err.println("Setting navajo");
//        ScrolledComposite book = new ScrolledComposite(getForm().getBody(), SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
//        getKit().adapt(book);
        

 
   		
   		final TreeViewer tv = SwtFactory.getInstance().createNavajoTree(n, getForm().getBody());
//        book.setContent(tv.getTree());
//        System.err.println("Bookheight: "+book.getSize().y);
         getForm().getBody().setBackground(new Color(Workbench.getInstance().getDisplay(),240,240,220));

    		GridData gd = new GridData(GridData.FILL,GridData.BEGINNING,true,false);
            gd.grabExcessHorizontalSpace = true;
            tv.getTree().setLayoutData(gd);
  		tv.getTree().addTreeListener(new TreeListener(){

            public void treeCollapsed(TreeEvent e) {
                System.err.println("Tree opened!");
                tv.getTree().pack();
                tv.getTree().layout();
                getForm().getBody().layout();
            }

            public void treeExpanded(TreeEvent e) {
                System.err.println("Tree opened!");
                tv.getTree().pack();
                tv.getTree().layout();
                getForm().getBody().layout();
            }});
    getForm().getBody().layout();
    }
    
    /**
     * @param n
     * @param myFile
     */
    private void setMessages(Navajo n, Composite container) {
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
    public void addMessage(Message element, final Composite spb) {
      final ExpandableComposite ss = getKit().createExpandableComposite
      (spb, ExpandableComposite.TWISTIE);
      ss.setText("-");
      ss.setExpanded(true);
      ss.setBackground(new Color(Display.getCurrent(),240,220,220));
//  		GridData gd = new GridData(GridData.FILL,GridData.BEGINNING,true,false);
//        gd.grabExcessHorizontalSpace = true;
      mainMessageScroll.setExpandHorizontal(true);
      mainMessageScroll.setExpandVertical(true);
      mainMessageScroll.layout();
       TableWrapData td = new TableWrapData(TableWrapData.FILL_GRAB,TableWrapData.FILL_GRAB);

        ss.setLayoutData(td);
        
        
        spb.layout();
        final Composite s = getKit().createComposite(ss,SWT.BORDER);
       ss.addExpansionListener(new IExpansionListener() {

            public void expansionStateChanging(ExpansionEvent e) {
            }

            public void expansionStateChanged(ExpansionEvent e) {
//               spb.pack(true);
               s.pack();
               ss.pack(true);
//               
                mainMessageContainer.layout(true);
//                myForm.pack(true);
//                myForm.layout();
                mainMessageScroll.layout();
            }});
         ss.setText(element.getName());
          if (Message.MSG_TYPE_ARRAY.equals(element.getType())) {
            System.err.println("adding table");
            s.setLayout(new FillLayout(SWT.HORIZONTAL));
//          addTable(element,s);
           SwtFactory.getInstance().addTableTree(element,s);
        } else {
            s.setLayout(new TableWrapLayout());
            ArrayList al = element.getAllProperties();
            if (al.size()>0) {
                System.err.println("MESSAGE "+element.getName()+" has properties...: "+al);
                Composite props = getKit().createComposite(s,SWT.NONE);
//                GridData gridd = new GridData(GridData.FILL,GridData.BEGINNING,true,false);
//                gridd.grabExcessHorizontalSpace = true;
                props.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB,TableWrapData.TOP));
//                props.setLayout(new RowLayout())
                props.setLayout(new TableWrapLayout());

                addProperties(element,props);
                props.pack();
                s.pack();
                
                
            } 
            else {
                System.err.println("MESSAGE "+element.getName()+" has NO properties...");
                
            }
            ArrayList subm = element.getAllMessages();

            if (subm.size()!=0) {
                Composite submsgs = getKit().createComposite(s,SWT.NONE);
                submsgs.setBackground(new Color(Display.getCurrent(),240,220,240));
//                GridData gridd = new GridData(GridData.FILL,GridData.BEGINNING,true,false);
//                gridd.grabExcessHorizontalSpace = true;
                TableWrapData tdd = new TableWrapData(TableWrapData.FILL_GRAB,TableWrapData.FILL_GRAB);

                submsgs.setLayoutData(tdd);
//                submsgs.setLayoutData(gridd);
                submsgs.setLayout(new TableWrapLayout());
                System.err.println("SUBMESSAGES: "+subm.toString());
//                submsgs.setLayout(new TableWrapLayout());
//                ((TableWrapLayout)props.getLayout()).numColumns = 2;
                
                 for (Iterator iter = subm.iterator(); iter.hasNext();) {
                    Message submsg = (Message) iter.next();
                    addMessage(submsg, submsgs);
                }
                 submsgs.pack();
            
            } 
  
        }
          ss.setClient(s);
          ss.pack();
    }

    /**
     * @param element
     * @param spb
     */
    private void addProperties(Message element, Composite spb) {
        System.err.println("adding properties");
        ArrayList al = element.getAllProperties();
        for (Iterator iter = al.iterator(); iter.hasNext();) {
            Property prop = (Property) iter.next();
            addFormProperty(prop,spb);
        }
    }
    /**
     * @param prop
     * @param element
     * @param spb
     */
    private void addFormProperty(Property prop, Composite spb) {
        GenericPropertyComponent gpc = SwtFactory.getInstance().createProperty(spb);
        gpc.setProperty(prop);
        gpc.adapt(getKit());
        gpc.getComposite().setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB,TableWrapData.MIDDLE));
        //        GenericPropertyComponent gpc = new GenericPropertyComponent(spb,getKit());
//        gpc.setProperty(prop);
//        gpc.getComposite().setLayoutData(new GridData(SWT.FILL,SWT.CENTER,true,false));
//        spb.layout();
        //        gpc.getComposite().setBackground(new Color(null,200,100,100));
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
//        GridData gd = new GridData(GridData.FILL,GridData.BEGINNING,true,false);
//        gd.grabExcessHorizontalSpace = true;
        TableWrapData td = new TableWrapData(TableWrapData.FILL_GRAB,TableWrapData.TOP);
        sss.setLayoutData(td);
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

                    int stateMask = e.getStateMask();
                    
                    if ((stateMask & SWT.CTRL)!= 0) {
                        NavajoScriptPluginPlugin.getDefault().openInEditor(scriptFile );
                    } else {
                        try {
                            Launch l = NavajoScriptPluginPlugin.getDefault().runNavajo("com.dexels.navajo.client.impl.NavajoRunner", scriptFile,myFile);
                        } catch (CoreException e1) {
                            e1.printStackTrace();
                        }  
                        
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
