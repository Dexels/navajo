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
public class MetaDataViewer extends ViewPart  {
//    private ScrolledForm myForm;

    private FormToolkit kit;

    private Composite adapters;

    private Composite calls;

    private Composite calledBy;
    private Composite includedBy;
    private Composite includes;

    private ScrolledForm formContent;

    private IFile myFile;

    /* (non-Javadoc)
     * @see org.eclipse.ui.IWorkbenchPart#createPartControl(org.eclipse.swt.widgets.Composite)
     */
    public void createPartControl(Composite parent) {
//        System.err.println("Creating part control. Invokation number: "+iii++);
        NavajoScriptPluginPlugin.getDefault().setMetaDataViewer(this);
//        Control[] c = parent.getChildren();
//        for (int i = 0; i < c.length; i++) {
//            System.err.println("Child: "+c[i].getClass());
//            c[i].dispose();
//        }
        
        
        System.err.println("PARENTLAYOUT CLASS: "+parent.getLayout().getClass());
       
          kit = new FormToolkit(parent.getDisplay());

          formContent = new ScrolledForm(parent, SWT.V_SCROLL | SWT.H_SCROLL);
          formContent.setText("Metadata");
        formContent.setExpandHorizontal(true);
          formContent.setExpandVertical(true);
      TableWrapLayout twl = new TableWrapLayout();
          twl.numColumns = 5;
          formContent.getBody().setLayout(twl);

        calledBy = createExpandable(formContent.getBody(),"Called by...");
        calls = createExpandable(formContent.getBody(),"Calls...");
        adapters = createExpandable(formContent.getBody(),"Adapters...");

        includedBy = createExpandable(formContent.getBody(),"Included by...");
        includes = createExpandable(formContent.getBody(),"Includes...");
        
        formContent.layout();
//         myForm.reflow(true);

//       ResourcesPlugin.getWorkspace().addResourceChangeListener(this);     
    }

    /**
     * @param string
     * @return
     */
    private Composite createExpandable(final Composite parent, String title) {
        Section comp =  kit.createSection(parent, SWT.BORDER | Section.TITLE_BAR );
        comp.setText(title);
        comp.setExpanded(true);
        comp.setBackground(new Color(Display.getCurrent(), 220, 220, 240));
        comp.setLayout(new TableWrapLayout());
//        comp.addExpansionListener(new IExpansionListener(){
//
//            public void expansionStateChanging(ExpansionEvent e) {
//                  
//            }
//
//            public void expansionStateChanged(ExpansionEvent e) {
//                formContent.reflow(false);
//            }});
        Composite otherpanel = kit.createComposite(comp);
        comp.setClient(otherpanel);
        //        Composite otherpanel = (Composite)panel.getContent();
        
        otherpanel.setLayout(new FillLayout(SWT.VERTICAL));
        otherpanel.setBackground(new Color(Display.getCurrent(), 240, 240, 220));
//       Hyperlink hr = kit.createHyperlink(otherpanel, "Aap:"+title, SWT.NONE);
//        comp.setLayoutData(new TableWrapData(TableWrapData.FILL,TableWrapData.TOP));
        TableWrapData tff = new TableWrapData(TableWrapData.FILL_GRAB, TableWrapData.FILL_GRAB);
        tff.grabHorizontal = false;
        comp.setLayoutData(tff);


        return otherpanel;
    }

    /* (non-Javadoc)
     * @see org.eclipse.ui.IWorkbenchPart#setFocus()
     */
    public void setFocus() {
 
    }
    
    public void showScript(IFile file, String scriptName, TslMetaDataHandler metaDataHandler) {
        myFile = file;
        formContent.setText("Script: "+scriptName);
        
        Set adapter = metaDataHandler.getAdaptersUsedByScriptSet(scriptName);
        System.err.println("Adapters: "+adapter);
        Set callout = metaDataHandler.getScriptCallsSet(scriptName);
        Set callin = metaDataHandler.getScriptCalledBySet(scriptName);
        Set includein = metaDataHandler.getScriptIncludedBySet(scriptName);
        Set includeout = metaDataHandler.getScriptIncludesSet(scriptName);
        cleanControl(adapters);
        cleanControl(calledBy);
        cleanControl(calls);
        cleanControl(includes);
        cleanControl(includedBy);
        if (callin!=null) {
            for (Iterator iter = callin.iterator(); iter.hasNext();) {
                String element = (String) iter.next();
                createMetaLink(calledBy, element, true);
            }
               
        }
        if (callout!=null) {
            for (Iterator iter = callout.iterator(); iter.hasNext();) {
                String element = (String) iter.next();
                createMetaLink(calls, element, true);
            }
         
        }
        if (includein!=null) {
            for (Iterator iter = includein.iterator(); iter.hasNext();) {
                String element = (String) iter.next();
                createMetaLink(includedBy, element, true);
            }
         
        }
        if (includeout!=null) {
            for (Iterator iter = includeout.iterator(); iter.hasNext();) {
                String element = (String) iter.next();
                createMetaLink(includes, element, true);
            }
           
        }
        if (adapter!=null) {
            System.err.println("# of adapters: "+adapter.size());
            for (Iterator iter = adapter.iterator(); iter.hasNext();) {
                String element = (String) iter.next();
                createMetaLink(adapters, element, false);
            }
               
        }
//              adapters.layout();
//        calls.layout();
//        calledBy.layout();
//        includedBy.layout();
//        includes.layout();
       formContent.reflow(false);
    }
    
    private Hyperlink createMetaLink(Composite parent, final String name, final boolean isScript ){
//        System.err.println("CREATING HYPERLINKL: "+name);
        
        Hyperlink hl = kit.createHyperlink(parent, name, SWT.NONE);
        TableWrapData td = new TableWrapData(TableWrapData.LEFT,TableWrapData.TOP);
        td.grabHorizontal = true;
        td.grabVertical = false;
        hl.setLayoutData(td);
        hl.addHyperlinkListener(new IHyperlinkListener(){

            public void linkEntered(HyperlinkEvent e) {
            }

            public void linkExited(HyperlinkEvent e) {
            }

            public void linkActivated(HyperlinkEvent e) {
                if (!isScript) {
                    return;
                }
                if (myFile==null) {
                    System.err.println("Error no file?!");
                    return;
                }
                int stateMask = e.getStateMask();
                try {
                if ((stateMask & SWT.SHIFT) != 0) {
                    IFile fff = NavajoScriptPluginPlugin.getDefault().getScriptFile(myFile.getProject(), name);                    
                    if (fff==null ||!fff.exists()) {
                        return;
                    }
                    NavajoScriptPluginPlugin.getDefault().openInEditor(fff);
                    return;
                }
                if ((stateMask & SWT.CTRL) != 0) {
                    NavajoScriptPluginPlugin.getDefault().showTml(NavajoScriptPluginPlugin.getDefault().getTmlFile(myFile.getProject(), name));
                    return;
                }
                     NavajoScriptPluginPlugin.getDefault().showMetaData(myFile, name);
                } catch (NavajoPluginException e1) {
                 }
            }});
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
//        ResourcesPlugin.getWorkspace().removeResourceChangeListener(this);
  }
//    public void resourceChanged(IResourceChangeEvent event) {
//        IResourceDeltaVisitor visitor = new IResourceDeltaVisitor() {
//            public boolean visit(IResourceDelta delta) {
//                IResource resource = delta.getResource();
//                IPath ip = resource.getFullPath();
//                if (myCurrentFile==null) {
//                    return false;
//                }
//                IPath myPath = myCurrentFile.getFullPath();
//                if (resource.equals(myCurrentFile)) {
//                    NavajoScriptPluginPlugin.getDefault().showTml(myCurrentFile);
//                    return false;
//                }
//                if (ip.isPrefixOf(myPath)) {
//                    return true;
//                }
//                return false;  
//            }
//        };
//        try {
//            event.getDelta().accept(visitor);
//        } catch (CoreException e) {
//             e.printStackTrace();
//        }        
//    }    

    /**
     * @param scriptName
     * @param metaDataHandler
     */
//    public void showScript(String scriptName, TslMetaDataHandler metaDataHandler) {
//        
//    }
}
