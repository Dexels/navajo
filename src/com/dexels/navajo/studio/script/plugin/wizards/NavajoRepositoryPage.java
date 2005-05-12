/*
 * Created on May 6, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.studio.script.plugin.wizards;

import java.util.*;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.eclipse.core.runtime.jobs.*;
import org.eclipse.jface.resource.*;
import org.eclipse.jface.viewers.*;
import org.eclipse.jface.wizard.*;
import org.eclipse.swt.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.*;
import org.eclipse.ui.internal.*;

import com.dexels.navajo.studio.script.plugin.*;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class NavajoRepositoryPage extends WizardPage {

    /**
     * @param pageName
     * @param title
     * @param titleImage
     */
    private final IProject myProject;
    
    private ListViewer repCompo;

    private ArrayList matches;
    
    protected NavajoRepositoryPage(String pageName, String title, ImageDescriptor titleImage, IProject project) {
        super(pageName, title, titleImage);
        myProject = project;
    }
// 
//    private GridData createGridData() {
//        return new GridData(GridData.HORIZONTAL_ALIGN_FILL,GridData.VERTICAL_ALIGN_BEGINNING,true,true);
//    }

    private GridData createGridData() {
        GridData gd = new GridData(GridData.FILL, GridData.BEGINNING, true, false);
        gd.grabExcessHorizontalSpace = true;
//       return new GridData(GridData.HORIZONTAL_ALIGN_FILL,GridData.VERTICAL_ALIGN_FILL,true,true);
        return gd;
    }
    
    
    /* (non-Javadoc)
     * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
     */
    public void createControl(Composite parent) {
        Composite top = new Composite(parent,SWT.NONE);
        top.setLayout(new GridLayout(1,false));

        final Label l = new Label(top,SWT.BOLD);
        l.setLayoutData(createGridData());
        repCompo = new ListViewer(top);
        repCompo.getList().setLayoutData(createGridData());
        SearchResultContentProvider srcp = new SearchResultContentProvider();
        repCompo.setContentProvider(srcp);
        repCompo.setLabelProvider(srcp);
        repCompo.addSelectionChangedListener(new ISelectionChangedListener(){
            public void selectionChanged(SelectionChangedEvent event) {
                IStructuredSelection iss = (IStructuredSelection)repCompo.getSelection();
                setPageComplete(iss!=null && iss.getFirstElement()!=null);
            }});
  
        l.setText("Please wait...");

        super.setPageComplete(false);

        new Job("Looking for repositories"){

            protected IStatus run(IProgressMonitor monitor) {
                try {
                    matches = NavajoScriptPluginPlugin.getDefault().searchForImplementingClasses(myProject,
                                        NavajoScriptPluginPlugin.NAVAJO_REPOSITORY_INTERFACE, null);
                    Workbench.getInstance().getDisplay().syncExec(new Runnable(){

                        public void run() {
                            // TODO Auto-generated method stub
                            repCompo.setInput(matches);
//                            setPageComplete(true);
                            l.setText("Select the root repository to use. If it is not here, the classpath\n " +
                    		"of this project may not include it. In that case, fix the classpath and rerun. ");

                 
                        }});
                } catch (CoreException e) {
                    l.setText("Error loading repositories.");
                    return Status.CANCEL_STATUS;
                }
                return Status.OK_STATUS;
            }}.schedule();
        
        
//         Label l2 = new Label(top,SWT.NONE);
//        l.setText("Usually this is something like: navajo-tester or runtime_dir.\n" +
//        		"For existing projects, it is important to get this one right. ");
//        
//        rootField = new Text(top,SWT.BORDER);
//        rootField.addModifyListener(new ModifyListener(){
//            public void modifyText(ModifyEvent e) {
//                System.err.println("Setting to: "+(rootField.getText().length()>0));
//                setPageComplete(rootField.getText().length()>0);
//            }});
        setControl(top);
        // TODO Auto-generated method stub

    }

    /**
     * @return
     */
    public String getSelectedRepository() {
        String[] sel = repCompo.getList().getSelection();
        if (sel.length==0) {
            return null;
        } else {
            System.err.println("Repository list: Returning: "+sel[0]);
            return sel[0];
        }
    }

}
