/*
 * Created on Mar 29, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.studio.script.plugin.editors;

import java.io.*;
import java.io.File;
import java.util.*;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.eclipse.core.runtime.jobs.*;
import org.eclipse.debug.core.*;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.*;
import org.eclipse.swt.custom.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.forms.*;
import org.eclipse.ui.forms.events.*;
import org.eclipse.ui.forms.widgets.*;
import org.eclipse.ui.ide.*;
import org.eclipse.ui.internal.*;
import org.eclipse.ui.part.ViewPart;

import com.dexels.navajo.document.*;
import com.dexels.navajo.functions.*;
import com.dexels.navajo.studio.eclipse.*;
import com.dexels.navajo.studio.script.plugin.*;
import com.dexels.navajo.studio.script.plugin.swtimpl.*;
import com.dexels.navajo.swtclient.*;

/**
 * @author Administrator
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

public class TmlFormComposite extends Composite {

    private static final Color LINK_BACKGROUND_COLOR = new Color(Display.getCurrent(), 240, 240, 220);
    private static final Color BLUE_BACKGROUND_COLOR = new Color(Display.getCurrent(), 220, 220, 240);

    private static final Color FORM_BACKGROUND_COLOR = LINK_BACKGROUND_COLOR;

    /**
     * @param parent
     * @param style
     */
    private final ScrolledForm myForm;

    private final FormToolkit kit;

    private final FormToolkit whiteKit;

    private final TmlEditor myEditor;

    private Composite mainMessageContainer;

    private IFile myCurrentFile = null;
 
    private Section methodSection;

    private Navajo myCurrentNavajo;

//    private final Menu popup;

    private String myCurrentName;

    private ServerEntry myServerEntry;

    private final ArrayList myScriptListeners = new ArrayList();
    //    private ScrolledComposite mainMessageScroll;

    public TmlFormComposite(TmlEditor ee, Composite parent) {
        super(parent, SWT.NONE);
        setLayout(new FillLayout());
        myEditor = ee;
        kit = new FormToolkit(parent.getDisplay());
        whiteKit = new FormToolkit(parent.getDisplay());

        myForm = new ScrolledForm(this, SWT.V_SCROLL | SWT.H_SCROLL);
        myForm.setExpandHorizontal(true);
        myForm.setExpandVertical(true);

//        popup = new Menu(getShell(), SWT.POP_UP);
//        MenuItem back = new MenuItem(popup, SWT.PUSH);
        myForm.getBody().setLayout(new TableWrapLayout());
        myForm.setBackground(FORM_BACKGROUND_COLOR);
        kit.getHyperlinkGroup().setBackground(LINK_BACKGROUND_COLOR);
        myForm.getBody().addMouseListener(new MouseAdapter() {
            public void mouseDown(MouseEvent e) {
                System.err.println("CLICK!");
                if (e.button == 3) {
//                    popup.setVisible(true);
                }
            }
        });

    }

    public ScrolledForm getForm() {
        return myForm;
    }
    
    

    
    public void setNavajo(Navajo n, IFile myFile, String scriptName) {
        if (mainMessageContainer != null) {
            mainMessageContainer.dispose();
        }
        myCurrentName = scriptName;
        myCurrentFile = myFile;
        myCurrentNavajo = n;
        mainMessageContainer = getKit().createComposite(myForm.getBody(), SWT.NONE);
        mainMessageContainer.setVisible(false);
        getKit().adapt(mainMessageContainer);
        TableWrapData tff = new TableWrapData(TableWrapData.FILL_GRAB, TableWrapData.FILL_GRAB);
        tff.grabHorizontal = false;
        mainMessageContainer.setLayoutData(tff);

        mainMessageContainer.setBackground(FORM_BACKGROUND_COLOR);
        mainMessageContainer.setLayout(new TableWrapLayout());
        Composite headComp = new Composite(mainMessageContainer,SWT.BORDER);
        headComp.setBackground(FORM_BACKGROUND_COLOR);
        headComp.setLayout(new RowLayout());
        Label l = new Label(headComp,SWT.BOLD);
        l.setFont(new Font(Display.getCurrent(),"Arial",15,SWT.BOLD));
        l.setBackground(FORM_BACKGROUND_COLOR);
        l.setText(scriptName);
        headComp.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB,TableWrapData.TOP));
        try {
            addEditScriptHref(scriptName, headComp, n, myCurrentFile);
            addEditTmlHref(scriptName, headComp, n, myCurrentFile);
        } catch (NavajoPluginException e1) {
            e1.printStackTrace();
        }
        setMessages(n, mainMessageContainer);
        try {
            setMethods(n, myCurrentFile,scriptName);
        } catch (NavajoPluginException e) {
            e.printStackTrace();
        }
        mainMessageContainer.setVisible(true);
//        mainMessageContainer.getParent().layout();
        fireGotoScript(scriptName, n);
        myForm.reflow(true);
    }

//    public void setTreeNavajo(Navajo n, IFile myFile) {
//        System.err.println("Setting navajo");
// 
//        final TreeViewer tv = SwtFactory.getInstance().createNavajoTree(n, getForm().getBody());
//        //        book.setContent(tv.getTree());
//        //        System.err.println("Bookheight: "+book.getSize().y);
//        getForm().getBody().setBackground(new Color(Workbench.getInstance().getDisplay(), 240, 240, 220));
//
//        GridData gd = new GridData(GridData.FILL, GridData.BEGINNING, true, false);
//        gd.grabExcessHorizontalSpace = true;
//        tv.getTree().setLayoutData(gd);
//        tv.getTree().addTreeListener(new TreeListener() {
//
//            public void treeCollapsed(TreeEvent e) {
//                System.err.println("Tree opened!");
//                //                tv.getTree().pack();
//                //                tv.getTree().layout();
//                //                getForm().getBody().layout();
//            }
//
//            public void treeExpanded(TreeEvent e) {
//                System.err.println("Tree opened!");
//                //                tv.getTree().pack();
//                //                tv.getTree().layout();
//                //                getForm().getBody().layout();
//            }
//        });
//        myForm.reflow(true);
//    }

    /**
     * @param n
     * @param myFile
     */
    private void setMessages(Navajo n, Composite container) {
        ArrayList al;
        try {
            al = n.getAllMessages();
        } catch (NavajoException e) {
            e.printStackTrace();
            return;
        }
        for (Iterator iter = al.iterator(); iter.hasNext();) {
            Message element = (Message) iter.next();
            System.err.println("Adding message: " + element.getName());
            addMessage(element, container);
        }

    }

    
    private boolean isSuitableForTreeTable(Message m) {
        if (m==null) {
            return false;
        }
        if (!Message.MSG_TYPE_ARRAY.equals(m.getType())) {
            return false;
        }
        for (int i = 0; i < m.getArraySize(); i++) {
            Message element = m.getMessage(i);
            if (element.getAllMessages().size()>0) {
                return false;
            }
        }
        return true;
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
        final Composite s = getKit().createComposite(ss, SWT.BORDER);
        ss.addExpansionListener(new IExpansionListener() {

            public void expansionStateChanging(ExpansionEvent e) {
            }

            public void expansionStateChanged(ExpansionEvent e) {
                myForm.reflow(true);
            }
        });
        s.setLayout(new TableWrapLayout());
        ss.setText(element.getName());
        if (isSuitableForTreeTable(element)) {
            if (element.getArraySize() == 0) {
                Label l = getKit().createLabel(s, "Empty table.");
                TableWrapData tff = new TableWrapData(TableWrapData.FILL_GRAB, TableWrapData.FILL_GRAB);
                l.setLayoutData(tff);
            } else {
                TableViewer tc = SwtFactory.getInstance().addTable(element, s);
                TableWrapData tff = new TableWrapData(TableWrapData.FILL_GRAB, TableWrapData.FILL_GRAB);
                tff.grabHorizontal = true;
                if (tc != null) {
                    tc.getTable().setLayoutData(tff);
                }

            }
        } else {
            ArrayList al = element.getAllProperties();
            if (al.size() > 0) {
                TableWrapLayout llayout = new TableWrapLayout();
                llayout.numColumns = 2;
                Composite props = getKit().createComposite(s, SWT.NONE);
                setupMenuListener(props);
                props.setLayout(llayout);
                TableWrapData tff = new TableWrapData(TableWrapData.FILL_GRAB, TableWrapData.TOP);
                tff.grabHorizontal = true;
                props.setLayoutData(tff);
                addProperties(element, props);
            }
            ArrayList subm = element.getAllMessages();

            if (subm.size() != 0) {
                Composite submsgs = getKit().createComposite(s, SWT.NONE);
                setupMenuListener(submsgs);
                submsgs.setBackground(new Color(Display.getCurrent(), 240, 220, 240));
                TableWrapData tdd = new TableWrapData(TableWrapData.FILL_GRAB, TableWrapData.FILL_GRAB);

                submsgs.setLayoutData(tdd);
                submsgs.setLayout(new TableWrapLayout());
                for (Iterator iter = subm.iterator(); iter.hasNext();) {
                    Message submsg = (Message) iter.next();
                    addMessage(submsg, submsgs);
                }
            }
        }
        ss.setClient(s);
    }

    private void setupMenuListener(Composite c) {
        c.addMouseListener(new MouseAdapter() {
            public void mouseDown(MouseEvent e) {
                if (e.button != 1) {
//                    popup.setVisible(true);
                }
            }

        });
    }

    /**
     * @param element
     * @param spb
     */
    private void addProperties(Message element, Composite spb) {
//        System.err.println("adding properties");
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
        Label l = getKit().createLabel(spb, prop.getName());
        l.setLayoutData(new TableWrapData(TableWrapData.LEFT, TableWrapData.TOP));
        GenericPropertyComponent gpc = SwtFactory.getInstance().createProperty(spb);
        gpc.showLabels(false);
        gpc.setProperty(prop);
        gpc.adapt(getKit());
        setupMenuListener(gpc.getComposite());

        gpc.getComposite().setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB, TableWrapData.TOP));
    }

    /**
     * @param n
     * @param myFile
     */
    private void setMethods(final Navajo n, final IFile myFile, String scriptName)  throws NavajoPluginException {
        HyperlinkGroup hg = new HyperlinkGroup(mainMessageContainer.getDisplay());
        if (methodSection != null) {
            methodSection.dispose();
        }
        hg.setBackground(new Color(mainMessageContainer.getDisplay(),255,255,255));
        System.err.println("****************************************** PRINTING HEADER");
        n.getHeader().write(System.err);
        methodSection = getKit().createSection(getForm().getBody(), Section.TITLE_BAR);
        methodSection.setText("Methods:");
//        MenuItem[] items = popup.getItems();
//        for (int i = 0; i < items.length; i++) {
//            items[i].dispose();
//        }

        setupMenuListener(methodSection);
        Composite list = getKit().createComposite(methodSection);
        setupMenuListener(list);
        methodSection.setClient(list);
        TableWrapData td = new TableWrapData(TableWrapData.FILL_GRAB, TableWrapData.BOTTOM);
        td.grabVertical = false;
        methodSection.setLayoutData(td);
        //        list.setLayout(new RowLayout(SWT.HORIZONTAL));
        TableWrapLayout layout = new TableWrapLayout();
        layout.numColumns = 2;
        list.setLayout(layout);
//        String currentScriptName = NavajoScriptPluginPlugin.getDefault().getScriptNameFromResource(n,myFile);
        addSaveHref(scriptName, list, n, myFile);
        addBackHref(scriptName, list, n, myFile);
        addReloadHref(scriptName, list, n, myFile);

        for (Iterator iter = n.getAllMethods().iterator(); iter.hasNext();) {
            final Method element = (Method) iter.next();
//            System.err.println("Adding method: " + element.getName());
            final Hyperlink hl = whiteKit.createHyperlink(list, element.getName(), SWT.NONE);
            hl.setHref(element.getName());
            if (myFile!=null) {
                if (!NavajoScriptPluginPlugin.getDefault().isScriptExisting(myFile.getProject(), element.getName())) {
                    hl.setForeground(new Color(null, 200, 0, 0));
                    hl.setToolTipText("This script does not exist!");
                 }
            }
            TableWrapData tdd = new TableWrapData();
            hl.setLayoutData(tdd);
            hl.addHyperlinkListener(new HyperlinkAdapter() {
                public void linkActivated(HyperlinkEvent e) {
                    try {
                        runHref(myFile, element.getName(), e);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            });

//            MenuItem mi = new MenuItem(popup, SWT.PUSH);
//            mi.setText(element.getName());
//            mi.addSelectionListener(new SelectionAdapter() {
//                public void widgetSelected(SelectionEvent e) {
//                    try {
//                        runHref(myFile, element.getName(), null);
//                    } catch (Exception e1) {
//                        e1.printStackTrace();
//                    }
//                }
//            });
        }

        //        sss.pack();
    }

    /**
     * @param list
     * @param n
     */
    private void addReloadHref(final String name, Composite list, final Navajo n, final IFile myFile) throws NavajoPluginException {
        if(myFile==null) {
            return;
        }
        final Hyperlink hl = whiteKit.createHyperlink(list, "[[Reload]]", SWT.NONE);
        hl.setHref(name);
        TableWrapData tdd = new TableWrapData();
        hl.setLayoutData(tdd);
        hl.addHyperlinkListener(new HyperlinkAdapter() {
            public void linkActivated(HyperlinkEvent e) {
                try {
                    reload(n, myFile, e);
                } catch (NavajoPluginException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }

        });
//        MenuItem mi = new MenuItem(popup, SWT.PUSH);
//        mi.setText("Reload");
//        mi.addSelectionListener(new SelectionAdapter() {
//            public void widgetSelected(SelectionEvent e) {
//                try {
//                    reload(n, myFile, null);
//                } catch (NavajoPluginException e1) {
//                    e1.printStackTrace();
//                }
//            }
//        });
//        MenuItem mi2 = new MenuItem(popup, SWT.PUSH);
//        mi2.setText("Edit script..");
//        mi2.addSelectionListener(new SelectionAdapter() {
//            public void widgetSelected(SelectionEvent e) {
//                try {
//                    IFile scriptFile = NavajoScriptPluginPlugin.getDefault().getScriptFile(myFile.getProject(), name);
//                    System.err.println(".... Name: "+scriptFile.getFullPath());    
//                    NavajoScriptPluginPlugin.getDefault().openInEditor(scriptFile);
//                } catch (NavajoPluginException e1) {
//                     e1.printStackTrace();
//                }
//                 }
//        });

        
    }
    private void addEditTmlHref(final String name, Composite list, final Navajo n, final IFile myFile)  throws NavajoPluginException{
        if(myFile==null) {
            return;
        }
        final Hyperlink hl = getKit().createHyperlink(list, "[[Edit TML]]", SWT.NONE);
        hl.setHref(name);
//        TableWrapData tdd = new TableWrapData();
//        hl.setLayoutData(new TableWrapData(TableWrapData.LEFT,TableWrapData.TOP));
//        hl.setBackground(new Color(Display.getCurrent(), 220, 220, 240));
      hl.addHyperlinkListener(new HyperlinkAdapter() {
            public void linkActivated(HyperlinkEvent e) {
                try {
                     IFile tmlFile = NavajoScriptPluginPlugin.getDefault().getTmlFile(myFile.getProject(), name);
                    NavajoScriptPluginPlugin.getDefault().openInEditor(tmlFile);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
     }
    private void addEditScriptHref(final String name, Composite list, final Navajo n, final IFile myFile)  throws NavajoPluginException{
        if(myFile==null) {
            return;
        }
        final Hyperlink hl = getKit().createHyperlink(list, "[[Edit Script]]", SWT.NONE);
        hl.setHref(name);
//        TableWrapData tdd = new TableWrapData();
//        hl.setLayoutData(new TableWrapData(TableWrapData.LEFT,TableWrapData.TOP));
//        hl.setBackground(new Color(Display.getCurrent(), 220, 220, 240));
        
        hl.addHyperlinkListener(new HyperlinkAdapter() {
            public void linkActivated(HyperlinkEvent e) {
                try {
                    IFile scriptFile = NavajoScriptPluginPlugin.getDefault().getScriptFile(myFile.getProject(), name);
                     NavajoScriptPluginPlugin.getDefault().openInEditor(scriptFile);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
     }

    private void addSaveHref(final String name, Composite list, final Navajo n, final IFile myFile)  throws NavajoPluginException{
        if(myFile==null) {
            return;
        }
       final Hyperlink hl = whiteKit.createHyperlink(list, "[[Save]]", SWT.NONE);
        hl.setHref(name);
//        TableWrapData tdd = new TableWrapData();
//        hl.setLayoutData(tdd);
        hl.addHyperlinkListener(new HyperlinkAdapter() {
            public void linkActivated(HyperlinkEvent e) {
                try {
                    //                System.err.println("My id:
                    // "+myEditor.getEditorSite().getId());
                    saveFile();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
//        MenuItem mi = new MenuItem(popup, SWT.PUSH);
//        mi.setText("Save");
//        mi.addSelectionListener(new SelectionAdapter() {
//            public void widgetSelected(SelectionEvent e) {
//                try {
//                    saveFile();
//                } catch (Exception e1) {
//                    e1.printStackTrace();
//                }
//            }
//        });

    }

    private void reload(final Navajo n, final IFile myFile, HyperlinkEvent e)  throws NavajoPluginException{
        //                System.err.println("My id: "+myEditor.getEditorSite().getId());
        String scriptName = NavajoScriptPluginPlugin.getDefault().getScriptNameFromResource(myFile);
        String sourceTml = n.getHeader().getAttribute("sourceScript");
        try {
            if (sourceTml == null) {
                runHref(null, scriptName, e);
            } else {
                IFile sourceTmlFile = NavajoScriptPluginPlugin.getDefault().getTmlFile(myFile.getProject(), sourceTml);
                runHref(sourceTmlFile, scriptName, e);
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        //                myEditor.dispose();
        if (myEditor != null) {
            Workbench.getInstance().getActiveWorkbenchWindow().getActivePage().closeEditor(myEditor, false);

        }

    }

    private void addBackHref(final String name, Composite list, final Navajo n, final IFile myFile) {
        final String sourceTml = n.getHeader().getAttribute("sourceScript");
        if (sourceTml == null || "".equals(sourceTml) || myFile==null) {
            return;
        }
        final Hyperlink hl = whiteKit.createHyperlink(list, "[[Back]]", SWT.NONE);
        hl.setHref(name);
        TableWrapData tdd = new TableWrapData();
        hl.setLayoutData(tdd);
        hl.addHyperlinkListener(new HyperlinkAdapter() {
            public void linkActivated(HyperlinkEvent e) {
                if (sourceTml != null) {
                    try {
                        back(myFile, sourceTml);
                    } catch (NavajoPluginException e1) {
                        e1.printStackTrace();
                    }

                }
            }
        });
    }

    private void saveFile() throws IOException, CoreException, NavajoException {
        if (myCurrentFile==null) {
            return;
        }
        if (myEditor != null) {
            myEditor.doSave(null);
        } else {
            File currentF = new File(myCurrentFile.getLocation().toOSString());
            FileOutputStream fos = new FileOutputStream(currentF);
            myCurrentNavajo.write(fos);
            fos.flush();
            fos.close();
            myCurrentFile.refreshLocal(0, null);
        }

    }

    /**
     * @return Returns the kit.
     */
    public FormToolkit getKit() {
        return kit;
    }

    private void runHref(final IFile myFile, final String name, HyperlinkEvent e) throws Exception {
        fireScriptCalled(name);
       if (myServerEntry!=null) {
            Navajo n = myServerEntry.runProcess(name, myCurrentNavajo);
            setNavajo(n, null, name);
            return;
        }
        saveFile();
        final IProject ipp = myCurrentFile.getProject();
        IFile scriptFile = NavajoScriptPluginPlugin.getDefault().getScriptFile(ipp, name);
        IFile tmlFile = NavajoScriptPluginPlugin.getDefault().getTmlFile(ipp, name);
        int stateMask = 0;

        if (e != null) {
            stateMask = e.getStateMask();
        }

        if ((stateMask & SWT.SHIFT) != 0) {
            NavajoScriptPluginPlugin.getDefault().openInEditor(scriptFile);
            return;
        }
        if ((stateMask & SWT.CTRL) != 0) {
            if (!tmlFile.exists()) {
                return;
            }
            InputStream is = tmlFile.getContents();
            Navajo n = NavajoFactory.getInstance().createNavajo(is);
            is.close();
            setNavajo(n, tmlFile,myCurrentName);
            fireGotoScript(name, n);
            myForm.reflow(false);
            return;
        }
        final IFile finalScript = scriptFile;
        String ll = myCurrentNavajo.getHeader().getAttribute("local");
        myCurrentNavajo.getHeader().setAttribute("sourceScript", myCurrentName);
        if ("true".equals(ll)) {
            try {
                Launch l = NavajoScriptPluginPlugin.getDefault().runNavajo("com.dexels.navajo.client.impl.NavajoRunner", finalScript, myFile);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        } else {
            Job j = new Job("Running Navajo...") {

                protected IStatus run(IProgressMonitor monitor) {
                    NavajoScriptPluginPlugin.getDefault().runRemoteNavajo(ipp, name, myFile, myCurrentName);
                    return Status.OK_STATUS;
                }
            };
            j.schedule();
        }

    }

     public void reflow() {

        myForm.reflow(false);
    }

    private void back(final IFile myFile, final String sourceTml) throws NavajoPluginException {
        if(myFile==null) {
            return;
        }
        IFile sourceTmlFile = NavajoScriptPluginPlugin.getDefault().getTmlFile(myFile.getProject(), sourceTml);
        System.err.println("SourceMTL: " + sourceTmlFile.getFullPath());
        if (myEditor != null) {
            NavajoScriptPluginPlugin.getDefault().openInEditor(sourceTmlFile);
        }
        try {
            Navajo n = NavajoFactory.getInstance().createNavajo(sourceTmlFile.getContents());
            setNavajo(n, sourceTmlFile,sourceTml);
            fireGotoScript(sourceTml, n);
            // BEware here...
            
            myForm.reflow(true);
            //                    runHref(sourceTmlFile, scriptName, e);
        } catch (CoreException e1) {
            e1.printStackTrace();
        }
    }

    public void setServerEntry(ServerEntry se) {
        myServerEntry = se;
    }

    public void addNavajoScriptListener(INavajoScriptListener listener) {
        myScriptListeners.add(listener);
    }

    public void removeNavajoScriptListener(INavajoScriptListener listener) {
        myScriptListeners.remove(listener);
    }

    private void fireGotoScript(String scriptName, Navajo n) {
        System.err.println("goto Script hit!!!");
        for (int i = 0; i < myScriptListeners.size(); i++) {
            INavajoScriptListener current = (INavajoScriptListener)myScriptListeners.get(i);
            current.gotoScript(scriptName, n);
        }
    }

    private void fireScriptCalled(String scriptName) {
        System.err.println("Script called hit!!!");
        for (int i = 0; i < myScriptListeners.size(); i++) {
            INavajoScriptListener current = (INavajoScriptListener)myScriptListeners.get(i);
            current.callingScript(scriptName);
        }
    }

}


