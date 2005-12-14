/*
 * Created on Jun 15, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.studio.script.plugin.views;

import java.util.*;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.eclipse.jdt.core.*;
import org.eclipse.jdt.core.search.*;
import org.eclipse.jdt.internal.core.*;
import org.eclipse.swt.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.forms.events.*;
import org.eclipse.ui.forms.widgets.*;
import org.eclipse.ui.part.*;

import com.dexels.navajo.mapping.compiler.meta.*;
import com.dexels.navajo.studio.script.plugin.*;
import com.dexels.navajo.studio.script.plugin.editors.*;

/**
 * @author Administrator
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class MetaDataViewer extends ViewPart {

    private static final Color SECTION_BACKGROUND_COLOR = new Color(Display.getCurrent(), 240, 240, 220);

    private static final Color SECTION_TITLE_COLOR = new Color(Display.getCurrent(), 220, 220, 240);
    
    private static final Color WHITE = new Color(Display.getCurrent(), 255,255,255);
    // private ScrolledForm myForm;

    private FormToolkit kit;
    private FormToolkit whiteKit;

    private Composite adapters;

    private Composite calls;

    private Composite calledBy;

    private Composite includedBy;

    private Composite includes;

    private ScrolledForm formContent;

    private IFile myFile;

    private Composite headPanel;

    private Hyperlink showScript;

    private Hyperlink showTml;

    private Hyperlink recompileScript;

    private Label titleLabel;
    
    
    private String scriptName = null;

    private Hyperlink editTml;

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.IWorkbenchPart#createPartControl(org.eclipse.swt.widgets.Composite)
     */
    public void createPartControl(Composite parent) {
        NavajoScriptPluginPlugin.getDefault().setMetaDataViewer(this);
        kit = new FormToolkit(parent.getDisplay());
        whiteKit = new FormToolkit(parent.getDisplay());
        kit.getHyperlinkGroup().setBackground(SECTION_BACKGROUND_COLOR);
       formContent = new ScrolledForm(parent,  SWT.V_SCROLL | SWT.V_SCROLL);
//        formContent.setText("Metadata");
        formContent.setExpandHorizontal(true);
        formContent.setExpandVertical(true);
//        formContent.setFont(new Font(Display.getCurrent(),"Arial",15,SWT.BOLD));
        formContent.setBackground(SECTION_BACKGROUND_COLOR);
          
//        TableWrapLayout twl = new TableWrapLayout();
//        twl.numColumns = 5;
        formContent.getBody().setLayout(new GridLayout(5,false));
        createHeadPanel(formContent.getBody());
        calledBy = createExpandable(formContent.getBody(), "Called by...");
        calls = createExpandable(formContent.getBody(), "Calls...");
        adapters = createExpandable(formContent.getBody(), "Adapters...");
        includedBy = createExpandable(formContent.getBody(), "Included by...");
        includes = createExpandable(formContent.getBody(), "Includes...");
//        formContent.layout();
    }

    private void createHeadPanel(Composite parent) {
        headPanel = new Composite(parent,SWT.BORDER);
        headPanel.setLayoutData(new GridData(SWT.FILL,SWT.BEGINNING,true,false,5,1));
        headPanel.setLayout(new RowLayout());
        titleLabel = new Label(headPanel,SWT.NONE);
        titleLabel.setText("No script");
        titleLabel.setFont(new Font(Display.getCurrent(),"Arial",15,SWT.BOLD));
        headPanel.setBackground(SECTION_BACKGROUND_COLOR);
        titleLabel.setBackground(SECTION_BACKGROUND_COLOR);
        showScript = kit.createHyperlink(headPanel, "[[Edit script]]", SWT.NONE);
        editTml = kit.createHyperlink(headPanel, "[[Edit TML]]", SWT.NONE);
        showTml = kit.createHyperlink(headPanel, "[[Show TML]]", SWT.NONE);
        recompileScript = kit.createHyperlink(headPanel, "[[Recompile script]]", SWT.NONE);
        showScript.addHyperlinkListener(new HyperlinkAdapter(){
            public void linkActivated(HyperlinkEvent e) {
                if (myFile!=null) {
                        NavajoScriptPluginPlugin.getDefault().openInEditor(myFile);
                 }
            }});
        editTml.addHyperlinkListener(new HyperlinkAdapter(){
            public void linkActivated(HyperlinkEvent e) {
                if (myFile!=null) {
                    try {
                        IFile tml = NavajoScriptPluginPlugin.getDefault().getTmlFile(myFile.getProject(), scriptName);
                        if (tml!=null) {
                            NavajoScriptPluginPlugin.getDefault().openInEditor(tml);
                        }
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }});
        showTml.addHyperlinkListener(new HyperlinkAdapter(){
            public void linkActivated(HyperlinkEvent e) {
                if (myFile!=null) {
                    try {
                        IFile tml = NavajoScriptPluginPlugin.getDefault().getTmlFile(myFile.getProject(), scriptName);
                        if (tml!=null) {
                            NavajoScriptPluginPlugin.getDefault().showTml(tml,scriptName);
                        }
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }});
        recompileScript.addHyperlinkListener(new HyperlinkAdapter(){
            public void linkActivated(HyperlinkEvent e) {
                if (myFile!=null) {
                    try {
                        IFile script = NavajoScriptPluginPlugin.getDefault().getScriptFile(myFile.getProject(), scriptName);
                        if (script!=null) {
         // TODO BEWARE, IS THIS NECESSARY?
                            script.touch(null);
                        }
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }});
    }

    /**
     * @param string
     * @return
     */
    private Composite createExpandable(final Composite parent, String title) {
        Section comp = whiteKit.createSection(parent, SWT.BORDER | Section.TITLE_BAR);
        comp.setText(title);
        comp.setExpanded(true);
        comp.setBackground(SECTION_TITLE_COLOR);
        
//        comp.setLayout(new TableWrapLayout());
        // comp.addExpansionListener(new IExpansionListener(){
        //
        // public void expansionStateChanging(ExpansionEvent e) {
        //                  
        // }
        //
        // public void expansionStateChanged(ExpansionEvent e) {
        // formContent.reflow(false);
        // }});
        Composite otherpanel = kit.createComposite(comp);
        comp.setClient(otherpanel);
        // Composite otherpanel = (Composite)panel.getContent();

        otherpanel.setLayout(new TableWrapLayout());
        otherpanel.setBackground(WHITE);
        // Hyperlink hr = kit.createHyperlink(otherpanel, "Aap:"+title,
        // SWT.NONE);
        // comp.setLayoutData(new
        // TableWrapData(TableWrapData.FILL,TableWrapData.TOP));
//        TableWrapData tff = new TableWrapData(TableWrapData.FILL_GRAB, TableWrapData.FILL_GRAB);
//        tff.grabHorizontal = false;
        comp.setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,true,1,1));
        
        return otherpanel;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.IWorkbenchPart#setFocus()
     */
    public void setFocus() {

    }

    public void showScript(IFile file, String scriptName, TslMetaDataHandler metaDataHandler) {
        myFile = file;
        this.scriptName = scriptName;
//        formContent.setText("Script: " + scriptName);
//        formContent.setText(scriptName);
        titleLabel.setText(scriptName+"   ");
        Set adapter = metaDataHandler.getScriptUsesAdaptersSet(scriptName);
        System.err.println("Adapters: " + adapter);
        Set callout = metaDataHandler.getScriptCallsSet(scriptName);
        Set callin = metaDataHandler.getScriptCalledBySet(scriptName);
        Set includein = metaDataHandler.getScriptIncludedBySet(scriptName);
        Set includeout = metaDataHandler.getScriptIncludesSet(scriptName);
        cleanControl(adapters);
        cleanControl(calledBy);
        cleanControl(calls);
        cleanControl(includes);
        cleanControl(includedBy);
        if (callin != null) {
            for (Iterator iter = callin.iterator(); iter.hasNext();) {
                String element = (String) iter.next();
                createMetaLink(calledBy, element, true);
            }

        }
        if (callout != null) {
            for (Iterator iter = callout.iterator(); iter.hasNext();) {
                String element = (String) iter.next();
                createMetaLink(calls, element, true);
            }

        }
        if (includein != null) {
            for (Iterator iter = includein.iterator(); iter.hasNext();) {
                String element = (String) iter.next();
                createMetaLink(includedBy, element, true);
            }

        }
        if (includeout != null) {
            for (Iterator iter = includeout.iterator(); iter.hasNext();) {
                String element = (String) iter.next();
                createMetaLink(includes, element, true);
            }

        }
        if (adapter != null) {
            System.err.println("# of adapters: " + adapter.size());
            for (Iterator iter = adapter.iterator(); iter.hasNext();) {
                String element = (String) iter.next();
                createMetaLink(adapters, element, false);
            }

        }
        Label depWarningLabel = kit.createLabel(adapters,"Warning: No deps shown.",SWT.NONE);
        depWarningLabel.setForeground(new Color(Display.getDefault(),200,20,20));
        adapters.layout();
         calls.layout();
         calledBy.layout();
         includedBy.layout();
         includes.layout();
        formContent.reflow(true);
    }

    private Hyperlink createMetaLink(Composite parent, final String name, final boolean isScript) {
        final Hyperlink hl = whiteKit.createHyperlink(parent, name, SWT.NONE);
        hl.setBackground(WHITE);
        TableWrapData td = new TableWrapData(TableWrapData.LEFT, TableWrapData.TOP);
        td.grabHorizontal = true;
        td.grabVertical = false;
        hl.setLayoutData(td);
        hl.addHyperlinkListener(new IHyperlinkListener() {
            public void linkEntered(HyperlinkEvent e) {
            }

            public void linkExited(HyperlinkEvent e) {
            }

            public void linkActivated(HyperlinkEvent e) {
                if (!isScript) {
                    IProject ipp = myFile.getProject();
                    IProjectNature ipn;
                    try {
                        ipn = ipp.getNature("org.eclipse.jdt.core.javanature");
                    } catch (CoreException e2) {
                        NavajoScriptPluginPlugin.getDefault().showInfo("Project error. Not a java project? Check the output.");
                        e2.printStackTrace();
                        return;
                    }
                     if (ipn instanceof IJavaProject) {
                        final ArrayList matches = new ArrayList();
                        IJavaProject jp = (IJavaProject) ipn;
                        IType itt = null;
                        try {
                            itt = jp.findType(name);
                        } catch (JavaModelException e1) {
                            e1.printStackTrace();
                            NavajoScriptPluginPlugin.getDefault().showInfo("Error resolving java type: "+name);
                        }
                        if (itt==null) {
                            NavajoScriptPluginPlugin.getDefault().showInfo("Can not resolve type: "+name);
                        } else {
                            IResource ir = itt.getPrimaryElement().getResource();
                            if (ir instanceof IFile) {
                                NavajoScriptPluginPlugin.getDefault().openInEditor((IFile)ir);
                            } else {
                                NavajoScriptPluginPlugin.getDefault().showInfo("Can not resolve type: "+name);
                            }
                        }
                        
                    }
                        return;
                }
                if (myFile == null) {
                    System.err.println("Error no file?!");
                    return;
                }
                int stateMask = e.getStateMask();
                try {
                    if ((stateMask & SWT.SHIFT) != 0) {
                        IFile fff = NavajoScriptPluginPlugin.getDefault().getScriptFile(myFile.getProject(), name);
                        if (fff == null || !fff.exists()) {
                            return;
                        }
                        NavajoScriptPluginPlugin.getDefault().openInEditor(fff);
                        return;
                    }
                    if ((stateMask & SWT.CTRL) != 0) {
                        IFile tmlfile = NavajoScriptPluginPlugin.getDefault().getTmlFile(myFile.getProject(), name);
                        if (tmlfile!=null && tmlfile.exists()) {
                            NavajoScriptPluginPlugin.getDefault().showTml(tmlfile,name);
                        } else {
                            NavajoScriptPluginPlugin.getDefault().showError("No tml file found. Run the script first.");
                        }
                        return;
                    }
                    NavajoScriptPluginPlugin.getDefault().showMetaData(myFile, name);
                } catch (Exception e1) {
                    NavajoScriptPluginPlugin.getDefault().showError("Error: "+e1.getMessage());
                    e1.printStackTrace();
                }
            }
        });
        return hl;
    }

    private void cleanControl(Composite c) {
        Control[] ss = c.getChildren();
        for (int i = 0; i < ss.length; i++) {
            ss[i].dispose();
        }
    }

    public void dispose() {
        super.dispose();
        NavajoScriptPluginPlugin.getDefault().setMetaDataViewer(null);
    }

}
