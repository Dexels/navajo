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

    private Section methodSection;

    private Navajo myCurrentNavajo;

    private final Menu popup;

    //    private ScrolledComposite mainMessageScroll;

    public TmlFormComposite(TmlEditor ee, Composite parent) {
        super(parent, SWT.NONE);
        setLayout(new FillLayout());
        myEditor = ee;
        kit = new FormToolkit(parent.getDisplay());

        myForm = new ScrolledForm(this, SWT.V_SCROLL | SWT.H_SCROLL);
        myForm.setExpandHorizontal(false);
        myForm.setExpandVertical(true);

        popup = new Menu(getShell(), SWT.POP_UP);
        MenuItem back = new MenuItem(popup, SWT.PUSH);

        //   myForm = kit.createScrolledForm(this);
        //        myForm.s
        //        myForm = kit.createScrolledForm(parent);
//        myForm.setExpandHorizontal(true);
//        myForm.setExpandVertical(true);
//        //        myForm.setAlwaysShowScrollBars(true);
        myForm.getBody().setLayout(new TableWrapLayout());

        myForm.getBody().setBackground(new Color(Display.getCurrent(), 240, 240, 220));
        myForm.getBody().addMouseListener(new MouseAdapter() {
            public void mouseDown(MouseEvent e) {
                System.err.println("CLICK!");
                if (e.button == 3) {
                    popup.setVisible(true);
                }
            }
        });

    }

    public ScrolledForm getForm() {
        return myForm;
    }

    public void setNavajo(Navajo n, IFile myFile) {
        System.err.println("Setting navajo");
        if (mainMessageContainer != null) {
            mainMessageContainer.dispose();
        }
        //        if (mainMessageContainer!=null) {
        //            me.dispose();
        //        }
        myCurrentFile = myFile;
        myCurrentNavajo = n;
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
        TableWrapData tff = new TableWrapData(TableWrapData.FILL_GRAB, TableWrapData.FILL_GRAB);
        tff.grabHorizontal = false;
        mainMessageContainer.setLayoutData(tff);

        mainMessageContainer.setBackground(new Color(Display.getCurrent(), 220, 220, 240));
        mainMessageContainer.setLayout(new TableWrapLayout());
        setMessages(n, mainMessageContainer);
        try {
            setMethods(n, myFile);
        } catch (NavajoPluginException e) {
            e.printStackTrace();
        }
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
            if (element.getArraySize() == 0) {
                Label l = getKit().createLabel(s, "Empty table.");
                TableWrapData tff = new TableWrapData(TableWrapData.FILL_GRAB, TableWrapData.FILL_GRAB);
                l.setLayoutData(tff);
            } else {
                System.err.println("adding table");
                TableTreeViewer tc = SwtFactory.getInstance().addTableTree(element, s);
                TableWrapData tff = new TableWrapData(TableWrapData.FILL_GRAB, TableWrapData.FILL_GRAB);
                tff.grabHorizontal = true;
                if (tc != null) {
                    tc.getTableTree().setLayoutData(tff);
                }

            }
        } else {

            //            long mmm = System.currentTimeMillis();
            //             s.setLayout(new TableWrapLayout());
            ArrayList al = element.getAllProperties();
            if (al.size() > 0) {
                //                System.err.println("MESSAGE " + element.getName() + " has
                // properties...: " + al);
                TableWrapLayout llayout = new TableWrapLayout();
                llayout.numColumns = 2;

                Composite props = getKit().createComposite(s, SWT.NONE);
                setupMenuListener(props);
                props.setLayout(llayout);
                TableWrapData tff = new TableWrapData(TableWrapData.FILL_GRAB, TableWrapData.TOP);
                tff.grabHorizontal = true;

                //                props.setBackground(new Color(Display.getCurrent(), 240, 240,
                // 220));
                props.setLayoutData(tff);
                //                props.setLayout(new TableWrapLayout());

                addProperties(element, props);
                //                props.pack();
                //                s.pack();
                //                System.err.println("Added props:
                // "+(System.currentTimeMillis() - mmm)+" millis");
            } else {
                //                System.err.println("MESSAGE " + element.getName() + " has NO
                // properties...");

            }
            ArrayList subm = element.getAllMessages();

            if (subm.size() != 0) {
                Composite submsgs = getKit().createComposite(s, SWT.NONE);
                setupMenuListener(submsgs);
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

    private void setupMenuListener(Composite c) {
        c.addMouseListener(new MouseAdapter() {
            public void mouseDown(MouseEvent e) {
                if (e.button != 1) {
                    popup.setVisible(true);
                }
            }

        });
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
    private void setMethods(final Navajo n, final IFile myFile)  throws NavajoPluginException {
        if (methodSection != null) {
            methodSection.dispose();
        }
        methodSection = getKit().createSection(getForm().getBody(), Section.TITLE_BAR);
        methodSection.setText("Methods:");
        MenuItem[] items = popup.getItems();
        for (int i = 0; i < items.length; i++) {
            items[i].dispose();
        }

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
        String currentScriptName = NavajoScriptPluginPlugin.getDefault().getScriptNameFromResource(myFile);
        addSaveHref(currentScriptName, list, n, myFile);
        addBackHref(currentScriptName, list, n, myFile);
        addReloadHref(currentScriptName, list, n, myFile);

        for (Iterator iter = n.getAllMethods().iterator(); iter.hasNext();) {
            final Method element = (Method) iter.next();
            System.err.println("Adding method: " + element.getName());
            final Hyperlink hl = getKit().createHyperlink(list, element.getName(), SWT.NONE);
            hl.setHref(element.getName());

            if (!NavajoScriptPluginPlugin.getDefault().isScriptExisting(myFile.getProject(), element.getName())) {
                hl.setForeground(new Color(null, 200, 0, 0));
                hl.setToolTipText("This script does not exist!");
                hl.addMouseTrackListener(new MouseTrackListener() {
                    public void mouseEnter(MouseEvent e) {
                        hl.setForeground(new Color(null, 255, 0, 0));
                    }

                    public void mouseExit(MouseEvent e) {
                        hl.setForeground(new Color(null, 200, 0, 0));
                    }

                    public void mouseHover(MouseEvent e) {
                    }
                });
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

            MenuItem mi = new MenuItem(popup, SWT.PUSH);
            mi.setText(element.getName());
            mi.addSelectionListener(new SelectionAdapter() {
                public void widgetSelected(SelectionEvent e) {
                    try {
                        runHref(myFile, element.getName(), null);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            });
        }

        //        sss.pack();
    }

    /**
     * @param list
     * @param n
     */
    private void addReloadHref(final String name, Composite list, final Navajo n, final IFile myFile) throws NavajoPluginException {
        final Hyperlink hl = getKit().createHyperlink(list, "[[Reload]]", SWT.NONE);
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
        MenuItem mi = new MenuItem(popup, SWT.PUSH);
        mi.setText("Reload");
        mi.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                try {
                    reload(n, myFile, null);
                } catch (NavajoPluginException e1) {
                    e1.printStackTrace();
                }
            }
        });
        MenuItem mi2 = new MenuItem(popup, SWT.PUSH);
        mi2.setText("Edit script..");
        mi2.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                try {
                    IFile scriptFile = NavajoScriptPluginPlugin.getDefault().getScriptFile(myFile.getProject(), name);
                    System.err.println(".... Name: "+scriptFile.getFullPath());    
                    NavajoScriptPluginPlugin.getDefault().openInEditor(scriptFile);
                } catch (NavajoPluginException e1) {
                     e1.printStackTrace();
                }
                 }
        });

        
    }

    private void addSaveHref(final String name, Composite list, final Navajo n, final IFile myFile)  throws NavajoPluginException{
        final Hyperlink hl = getKit().createHyperlink(list, "[[Save]]", SWT.NONE);
        hl.setHref(name);
        TableWrapData tdd = new TableWrapData();
        hl.setLayoutData(tdd);
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
        MenuItem mi = new MenuItem(popup, SWT.PUSH);
        mi.setText("Save");
        mi.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                try {
                    saveFile();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });

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
        System.err.println("SOURCETML: " + sourceTml);
        if (sourceTml == null) {
            return;
        }
        final Hyperlink hl = getKit().createHyperlink(list, "[[Back]]", SWT.NONE);
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
        MenuItem mi = new MenuItem(popup, SWT.PUSH);
        mi.setText("Back");
        mi.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                try {
                    back(myFile, sourceTml);
                } catch (NavajoPluginException e1) {
                    e1.printStackTrace();
                }
            }
        });

    }

    private void saveFile() throws IOException, CoreException, NavajoException {
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
        saveFile();
        IProject ipp = myCurrentFile.getProject();
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
            setNavajo(n, tmlFile);
            myForm.reflow(false);
            return;
        }
        try {
            Launch l = NavajoScriptPluginPlugin.getDefault().runNavajo("com.dexels.navajo.client.impl.NavajoRunner", scriptFile, myFile);
        } catch (CoreException e1) {
            e1.printStackTrace();
        }

    }

    /**
     *  
     */
    public void reflow() {

        myForm.reflow(false);
    }

    private void back(final IFile myFile, final String sourceTml) throws NavajoPluginException {
        IFile sourceTmlFile = NavajoScriptPluginPlugin.getDefault().getTmlFile(myFile.getProject(), sourceTml);
        System.err.println("SourceMTL: " + sourceTmlFile.getFullPath());
        if (myEditor != null) {
            NavajoScriptPluginPlugin.getDefault().openInEditor(sourceTmlFile);
        }
        try {
            Navajo n = NavajoFactory.getInstance().createNavajo(sourceTmlFile.getContents());
            setNavajo(n, sourceTmlFile);
            myForm.reflow(false);
            //                    runHref(sourceTmlFile, scriptName, e);
        } catch (CoreException e1) {
            e1.printStackTrace();
        }
    }
}
