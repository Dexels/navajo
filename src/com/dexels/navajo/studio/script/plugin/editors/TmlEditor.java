/*******************************************************************************
 * Copyright (c) 2000, 2003 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package com.dexels.navajo.studio.script.plugin.editors;

import java.io.*;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.eclipse.jface.dialogs.*;
import org.eclipse.swt.custom.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.*;
import org.eclipse.ui.editors.text.*;
import org.eclipse.ui.ide.*;
import org.eclipse.ui.internal.*;
import org.eclipse.ui.part.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.studio.script.plugin.*;

/**
 * An example showing how to create a multi-page editor. This example has 3
 * pages:
 * <ul>
 * <li>page 0 contains a nested text editor.
 * <li>page 1 allows you to change the font used in page 2
 * <li>page 2 shows the words in page 0 in sorted order
 * </ul>
 */
public class TmlEditor extends MultiPageEditorPart implements IGotoMarker {

    /** The text editor used in page 0. */
    private TextEditor editor;
    private int editorIndex = 0;
    private int activePage = 0;
    private Navajo myCurrentNavajo = null;
    private IFile myCurrentFile = null;
    private TmlFormComposite formComposite;
    private String currentService = null;

    public TmlEditor() {
        super();
        IEditorPart iep = NavajoScriptPluginPlugin.getDefaultWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
        
    }

    /**
     * Creates page 0 of the multi-page editor, which contains a text editor.
     */
    void createPage0() {
        try {
                        IEditorDescriptor eddesc =
             NavajoScriptPluginPlugin.getDefaultWorkbench().getEditorRegistry().getDefaultEditor(getEditorInput().getName());
              
            editor = new TextEditor();

            editorIndex = addPage(editor, getEditorInput());
            setPageText(editorIndex, "Source"); //$NON-NLS-1$
            //            editor.get
        } catch (PartInitException e) {
            ErrorDialog.openError(getSite().getShell(), "ErrorCreatingNestedEditor", null, e.getStatus()); //$NON-NLS-1$
        }
    }

 
  
    protected void setActivePage(int pageIndex) {
        activePage = pageIndex;
        super.setActivePage(pageIndex);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.part.EditorPart#setInput(org.eclipse.ui.IEditorInput)
     */
    protected void setInput(IEditorInput input) {
        IFile iff = null;
        iff = (IFile) input.getAdapter(IFile.class);
       IEditorInput eiez = getEditorInput();
        if (eiez != null) {
            System.err.println("Input: " + eiez.getClass());
            iff = (IFile) eiez.getAdapter(IFile.class);
            if (!iff.exists()) {
                super.dispose();
                return;
            }
            
        }
        
// COMMENTED OUT. NEEDS FIX, BUT DONT THINK I WILL USE THIS CLASS ANY MORE        
//             IPath pp = iff.getFullPath().removeFirstSegments(NavajoScriptPluginPlugin.getDefault().getTmlFolder(iff.getProject()).getFullPath().segmentCount());
//            String pppName = pp.toString();
//            setPartName(pp.toString());
//            setContentDescription(pp.toString());
       
        super.setInput(input);
         InputStream contents = null;
        try {
            if (iff==null || !iff.exists()) {
                System.err.println("Could not open file!");
                return;
            }
            // is this refresh necessary?
//            iff.refreshLocal(IResource.DEPTH_INFINITE, null);
            contents = iff.getContents();
            myCurrentNavajo = NavajoFactory.getInstance().createNavajo(contents);
            setNavajo(myCurrentNavajo, iff);
        } catch (CoreException e1) {
            e1.printStackTrace();
        } finally {
           try {
               if (contents!=null) {
                   contents.close();
                
            }
        } catch (IOException e) {
            e.printStackTrace();
        } 
        }

    }

    /**
     * Creates page 1 of the multi-page editor, which allows you to change the
     * font used in page 2.
     */
    //    void createPage1() {
    //        Composite c = new Composite(getContainer(), SWT.EMBEDDED);
    //         java.awt.Frame frame = SWT_AWT.new_Frame(c);
    //        try {
    //            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    //        } catch (Exception e) {
    //             e.printStackTrace();
    //        }
    //        myPanel.setEditor(this);
    //        frame.setLayout(new BorderLayout());
    //        SwingUtilities.updateComponentTreeUI(frame);
    //        frame.pack();
    //        frame.add(myPanel, BorderLayout.CENTER);
    //        
    //        int index = addPage(c);
    //               setPageText(index,"Navajo"); //$NON-NLS-1$
    //    }
    //    /**
    //     * Creates page 2 of the multi-page editor, which shows the sorted text.
    //     */
    void createPage2() {
        formComposite = new TmlFormComposite(this, getContainer());
        if (myCurrentNavajo != null) {
            setNavajo(myCurrentNavajo, myCurrentFile);
        }
        int index = addPage(formComposite.getForm());
        setPageText(index, "Form version");
    }

    /**
     * Creates the pages of the multi-page editor.
     */
    protected void createPages() {
        System.err.println("Creating pages");
//        NavajoScriptPluginPlugin.getDefault().closeEditorsWithExtension(this,"tml");
               //        createPage1();
        createPage2();
        createPage0();
    }

    public void reload() {
        setInput(getEditorInput());
    }
    
    /**
     * Saves the multi-page editor's document.
     */
    public void doSave(IProgressMonitor monitor) {
        System.err.println("Saving");
        if (getActiveEditor()==editor) {
            if (editor.isDirty()) {
                editor.doSave(monitor);
                System.err.println("saving text area");
                setInput(getEditorInput());
                
            }
            return;
        }
        if (myCurrentNavajo != null) {
            IFile ir = (IFile) getEditorInput().getAdapter(IFile.class);
            if (ir != null) {
                System.err.println("Writing...");
                File f = new File(ir.getLocation().toOSString());
                try {
                    FileWriter fw = new FileWriter(f);
                    myCurrentNavajo.write(fw);
                    fw.flush();
                    fw.close();
                    try {
                        ir.refreshLocal(IResource.DEPTH_INFINITE, monitor);
                        System.err.println("Made it through...");
                    } catch (CoreException e1) {
                        e1.printStackTrace();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (NavajoException e) {
                    e.printStackTrace();
                }

            }
        }
        //        getEditor(0).doSave(monitor);
    }

    /**
     * Saves the multi-page editor's document as another file. Also updates the
     * text for page 0's tab, and updates this multi-page editor's input to
     * correspond to the nested editor's.
     */
    public void doSaveAs() {
        IEditorPart editor = getEditor(0);
        editor.doSaveAs();
        setPageText(0, editor.getTitle());
        setInput(editor.getEditorInput());
    }

    /**
     * The <code>TmlEditor</code> implementation of this method checks that
     * the input is an instance of <code>IFileEditorInput</code>.
     */
    public void init(IEditorSite site, IEditorInput editorInput) throws PartInitException {
        if (!(editorInput instanceof IFileEditorInput))
            throw new PartInitException("Strange Resource"); //$NON-NLS-1$
        super.init(site, editorInput);
    }

    /*
     * (non-Javadoc) Method declared on IEditorPart.
     */
    public boolean isSaveAsAllowed() {
        return true;
    }

    /**
     * Calculates the contents of page 2 when the it is activated.
     */
    protected void pageChange(int newPageIndex) {
        super.pageChange(newPageIndex);
        //        if (newPageIndex == 2) {
        //            sortWords();
        //        }
    }

    public void setNavajo(final Navajo n, final IFile myFile) {
//        final String currentName = NavajoScriptPluginPlugin.getDefault().getScriptName(myFile,myFile.getProject());
        myCurrentFile = myFile;
        myCurrentNavajo = n;
        final Display d = PlatformUI.getWorkbench().getDisplay();
        d.syncExec(new Runnable() {

            public void run() {
                //                final NavajoBrowser nb = NavajoBrowser.getInstance();
                //                myPanel.navajoSelected("aap", n,myFile);
                if (formComposite != null) {
                    formComposite.setNavajo(n, myFile,null);
                } else {
                    System.err.println("hmmm. No formComposite");
                }
            }
        });

    }
    
    
    public void gotoMarker(IMarker marker) {
        setActivePage(editorIndex);
        IDE.gotoMarker(editor, marker);
    }

    /**
     *  
     */
    public void refresh() {
        //        
        //        if(myCurrentNavajo!=null) {
        //            myPanel.navajoSelected(currentService == null?"none":currentService,
        // myCurrentNavajo, myCurrentFile);
        //        }
    }
}