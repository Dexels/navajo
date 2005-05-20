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
import org.eclipse.ui.ide.*;
import org.eclipse.ui.internal.*;
import org.eclipse.ui.part.ViewPart;

import com.dexels.navajo.document.*;
import com.dexels.navajo.functions.*;
import com.dexels.navajo.studio.script.plugin.*;
import com.dexels.navajo.studio.script.plugin.swtimpl.*;

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
    private final ScrolledForm myForm;

    private final FormToolkit kit;

    private final TmlEditor myEditor;

    private Composite mainMessageContainer;
    
    private IFile myCurrentFile = null;

    //    private ScrolledComposite mainMessageScroll;

    public TmlFormComposite(TmlEditor ee, Composite parent) {
        super(parent, SWT.NONE);
        
        myEditor = ee;
        kit = new FormToolkit(parent.getDisplay());
        
        myForm = kit.createScrolledForm(parent);
//        myForm = kit.createScrolledForm(parent);
        myForm.setExpandHorizontal(true);
        myForm.setExpandVertical(true);
//        myForm.setAlwaysShowScrollBars(true);
        myForm.getBody().setLayout(new TableWrapLayout());
         
        myForm.getBody().setBackground(new Color(Display.getCurrent(), 240, 240, 220));
      
    }

    public ScrolledForm getForm() {
        return myForm;
    }

    public void setNavajo(Navajo n, IFile myFile) {
        System.err.println("Setting navajo");
        
        myCurrentFile = myFile;
        //        mainMessageScroll = new ScrolledComposite(getForm().getBody(),
        // SWT.BORDER | SWT.V_SCROLL);
        //        mainMessageScroll.setExpandHorizontal(true);
        //        mainMessageScroll.setExpandVertical(true);
        mainMessageContainer = getKit().createComposite(myForm.getBody(), SWT.NONE);
        mainMessageContainer.setVisible(false);
        //        getKit().adapt(book);
        getKit().adapt(mainMessageContainer);
        //   		GridData gd = new
        // GridData(GridData.FILL,GridData.BEGINNING,true,false);
        //        gd.grabExcessHorizontalSpace = true;
        //        gd.grabExcessVerticalSpace = true;
        //        TableWrapData td = new
        // TableWrapData(TableWrapData.FILL_GRAB,TableWrapData.FILL_GRAB);
        //        mainMessageScroll.setLayoutData(td);

        //        container.setLayoutData(gd);
        TableWrapData tff = new TableWrapData(TableWrapData.FILL_GRAB,TableWrapData.FILL_GRAB);
        tff.grabHorizontal = true;
        mainMessageContainer.setLayoutData(tff);

        mainMessageContainer.setBackground(new Color(Display.getCurrent(), 220, 220, 240));
        mainMessageContainer.setLayout(new TableWrapLayout());
        setMessages(n, mainMessageContainer);
        setMethods(n, myFile);
        mainMessageContainer.setVisible(true);
        
        //        mainMessageContainer.pack();
        //        mainMessageContainer.layout();
        //             mainMessageScroll.setContent(mainMessageContainer);
        //        mainMessageScroll.layout();
        //        mainMessageScroll.pack();

        //        setTreeNavajo(n, myFile);
        //      getForm().getBody().layout();
    }

    public void setTreeNavajo(Navajo n, IFile myFile) {
        System.err.println("Setting navajo");
        //        ScrolledComposite book = new ScrolledComposite(getForm().getBody(),
        // SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
        //        getKit().adapt(book);

        final TreeViewer tv = SwtFactory.getInstance().createNavajoTree(n, getForm().getBody());
        //        book.setContent(tv.getTree());
        //        System.err.println("Bookheight: "+book.getSize().y);
        getForm().getBody().setBackground(new Color(Workbench.getInstance().getDisplay(), 240, 240, 220));

        GridData gd = new GridData(GridData.FILL, GridData.BEGINNING, true, false);
        gd.grabExcessHorizontalSpace = true;
        tv.getTree().setLayoutData(gd);
        tv.getTree().addTreeListener(new TreeListener() {

            public void treeCollapsed(TreeEvent e) {
                System.err.println("Tree opened!");
//                tv.getTree().pack();
//                tv.getTree().layout();
//                getForm().getBody().layout();
            }

            public void treeExpanded(TreeEvent e) {
                System.err.println("Tree opened!");
//                tv.getTree().pack();
//                tv.getTree().layout();
//                getForm().getBody().layout();
            }
        });
        myForm.reflow(true);
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
            System.err.println("Adding message: " + element.getName());
            addMessage(element, container);
        }

    }

    /**
     * @param element
     * @param spb
     */
    public void addMessage(Message element, final Composite spb) {
        final ExpandableComposite ss = getKit().createExpandableComposite(spb, ExpandableComposite.TWISTIE);
        ss.setText("-");
        ss.setExpanded(true);
        ss.setBackground(new Color(Display.getCurrent(), 240, 220, 220));
          TableWrapData td = new TableWrapData(TableWrapData.FILL_GRAB, TableWrapData.FILL_GRAB);
        ss.setLayoutData(td);

//        spb.layout();
        final Composite s = getKit().createComposite(ss, SWT.BORDER);
        ss.addExpansionListener(new IExpansionListener() {

            public void expansionStateChanging(ExpansionEvent e) {
            }

            public void expansionStateChanged(ExpansionEvent e) {
                System.err.println("REFLOWING!");
                myForm.reflow(false);
            }
        });
        s.setLayout(new TableWrapLayout());
        ss.setText(element.getName());
        if (Message.MSG_TYPE_ARRAY.equals(element.getType())) {
            if (element.getArraySize()==0) {
                Label l = getKit().createLabel(s, "Empty table.");
                TableWrapData tff = new TableWrapData(TableWrapData.FILL_GRAB, TableWrapData.FILL_GRAB);
                l.setLayoutData(tff);
            } else {
                System.err.println("adding table");
                TableTreeViewer tc = SwtFactory.getInstance().addTableTree(element, s);
                TableWrapData tff = new TableWrapData(TableWrapData.FILL_GRAB, TableWrapData.FILL_GRAB);
               tff.grabHorizontal = true;
                if (tc!=null) {
                    tc.getTableTree().setLayoutData(tff);
                }
                
            }
        } else {
            
//            long mmm = System.currentTimeMillis();
//             s.setLayout(new TableWrapLayout());
            ArrayList al = element.getAllProperties();
            if (al.size() > 0) {
//                System.err.println("MESSAGE " + element.getName() + " has properties...: " + al);
                TableWrapLayout llayout = new TableWrapLayout();
                llayout.numColumns = 2;

                Composite props = getKit().createComposite(s, SWT.NONE);
                props.setLayout(llayout);
                TableWrapData tff = new TableWrapData(TableWrapData.FILL_GRAB,TableWrapData.TOP);
                tff.grabHorizontal = true;

//                props.setBackground(new Color(Display.getCurrent(), 240, 240, 220));
             props.setLayoutData(tff);
//                props.setLayout(new TableWrapLayout());

                addProperties(element, props);
                //                props.pack();
                //                s.pack();
//                System.err.println("Added props: "+(System.currentTimeMillis() - mmm)+" millis");
            } else {
//                System.err.println("MESSAGE " + element.getName() + " has NO properties...");

            }
            ArrayList subm = element.getAllMessages();

            if (subm.size() != 0) {
                Composite submsgs = getKit().createComposite(s, SWT.NONE);
                submsgs.setBackground(new Color(Display.getCurrent(), 240, 220, 240));
                TableWrapData tdd = new TableWrapData(TableWrapData.FILL_GRAB, TableWrapData.FILL_GRAB);

                submsgs.setLayoutData(tdd);
                submsgs.setLayout(new TableWrapLayout());
//                System.err.println("SUBMESSAGES: " + subm.toString());

                for (Iterator iter = subm.iterator(); iter.hasNext();) {
                    Message submsg = (Message) iter.next();
                    addMessage(submsg, submsgs);
                }
                //                 submsgs.pack();

            }

        }
        ss.setClient(s);
        //          ss.pack();
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
            addFormProperty(prop, spb);
        }
    }

    /**
     * @param prop
     * @param element
     * @param spb
     */
    private void addFormProperty(Property prop, Composite spb) {
        Label l = getKit().createLabel(spb,prop.getName());
        l.setLayoutData(new TableWrapData(TableWrapData.LEFT, TableWrapData.TOP));
        GenericPropertyComponent gpc = SwtFactory.getInstance().createProperty(spb);
        gpc.showLabels(false);
        gpc.setProperty(prop);
        gpc.adapt(getKit());
        gpc.getComposite().setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB, TableWrapData.TOP));
    }

    /**
     * @param n
     * @param myFile
     */
    private void setMethods(final Navajo n, final IFile myFile) {
        Section sss = getKit().createSection(getForm().getBody(), Section.TITLE_BAR);
        sss.setText("Methods:");

        Composite list = getKit().createComposite(sss);
        sss.setClient(list);
        TableWrapData td = new TableWrapData(TableWrapData.FILL_GRAB, TableWrapData.BOTTOM);
        td.grabVertical = false;
        sss.setLayoutData(td);
//        list.setLayout(new RowLayout(SWT.HORIZONTAL));
        TableWrapLayout layout = new TableWrapLayout();
        layout.numColumns = 3;
        list.setLayout(layout);
        addBackHref(myFile.getName(), list,n,myFile);
        addReloadHref(myFile.getName(), list,n,myFile);
        
        for (Iterator iter = n.getAllMethods().iterator(); iter.hasNext();) {
            final Method element = (Method) iter.next();
            System.err.println("Adding method: " + element.getName());
            final Hyperlink hl = getKit().createHyperlink(list, element.getName(), SWT.NONE);
            hl.setHref(element.getName());
            
            if (!NavajoScriptPluginPlugin.getDefault().isScriptExisting(myFile.getProject(), element.getName())) {
                hl.setForeground(new Color(null,200,0,0));
                hl.setToolTipText("This script does not exist!");
                hl.addMouseTrackListener(new MouseTrackListener(){
                    public void mouseEnter(MouseEvent e) {
                        hl.setForeground(new Color(null,255,0,0));
                    }
                    public void mouseExit(MouseEvent e) {
                        hl.setForeground(new Color(null,200,0,0));
                    }
                    public void mouseHover(MouseEvent e) {
                    }});
            }
            TableWrapData tdd = new TableWrapData();
            hl.setLayoutData(tdd);
            hl.addHyperlinkListener(new HyperlinkAdapter() {
                public void linkActivated(HyperlinkEvent e) {
                   runHref(myFile, element.getName(), e);
                }
            });
        }
        
        
        //        sss.pack();
    }

    /**
     * @param list
     * @param n
     */
    private void addReloadHref(final String name, Composite list,final Navajo n, final IFile myFile) {
        final Hyperlink hl = getKit().createHyperlink(list, "[[Reload]]", SWT.NONE);
        hl.setHref(name);
           TableWrapData tdd = new TableWrapData();
        hl.setLayoutData(tdd);
        hl.addHyperlinkListener(new HyperlinkAdapter() {
            public void linkActivated(HyperlinkEvent e) {
                System.err.println("My id: "+myEditor.getEditorSite().getId());
                String scriptName = NavajoScriptPluginPlugin.getDefault().getScriptNameFromResource(myFile);
                String sourceTml = n.getHeader().getAttribute("sourceScript");
                if (sourceTml==null) {
                    runHref(null, scriptName, e);
                } else {
                    IFile sourceTmlFile = NavajoScriptPluginPlugin.getDefault().getTmlFile(myFile.getProject(), sourceTml);
                    runHref(sourceTmlFile, scriptName, e);
                }
//                myEditor.dispose();
                Workbench.getInstance().getActiveWorkbenchWindow().getActivePage().closeEditor(myEditor, false);
            }
        });
        
    }

    private void addBackHref(final String name, Composite list,final Navajo n, final IFile myFile) {
        final String sourceTml = n.getHeader().getAttribute("sourceScript");
        System.err.println("SOURCETML: "+sourceTml);
        if (sourceTml==null) {
            return;
        }
        final Hyperlink hl = getKit().createHyperlink(list, "[[Back]]", SWT.NONE);
      hl.setHref(name);
           TableWrapData tdd = new TableWrapData();
        hl.setLayoutData(tdd);
        hl.addHyperlinkListener(new HyperlinkAdapter() {
            public void linkActivated(HyperlinkEvent e) {
                System.err.println("Sourcetml: "+sourceTml);
//                String scriptName = NavajoScriptPluginPlugin.getDefault().getScriptNameFromResource(myFile);
                if (sourceTml==null) {
//                    runHref(null, scriptName, e);
                } else {
                    IFile sourceTmlFile = NavajoScriptPluginPlugin.getDefault().getTmlFile(myFile.getProject(), sourceTml);
                    System.err.println("SourceMTL: "+sourceTmlFile.getFullPath());
                    NavajoScriptPluginPlugin.getDefault().openInEditor(sourceTmlFile);
//                    runHref(sourceTmlFile, scriptName, e);
                    
                }
            }
        });
        
    }

    
    /**
     * @return Returns the kit.
     */
    public FormToolkit getKit() {
        return kit;
    }

    private void runHref(final IFile myFile, final String name, HyperlinkEvent e) {
        if (myEditor != null) {
            myEditor.doSave(null);
        }
        IProject ipp = myCurrentFile.getProject();
        IFile scriptFile = NavajoScriptPluginPlugin.getDefault().getScriptFile(ipp, name);
        IFile tmlFile = NavajoScriptPluginPlugin.getDefault().getTmlFile(ipp, name);

        int stateMask = e.getStateMask();

        if ((stateMask & SWT.CTRL) != 0) {
            NavajoScriptPluginPlugin.getDefault().openInEditor(scriptFile);
        } else {
            try {
                Launch l = NavajoScriptPluginPlugin.getDefault().runNavajo("com.dexels.navajo.client.impl.NavajoRunner", scriptFile,
                        myFile);
            } catch (CoreException e1) {
                e1.printStackTrace();
            }

        }
    }
}
