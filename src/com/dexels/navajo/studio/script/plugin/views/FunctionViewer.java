/*
 * Created on Apr 8, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.studio.script.plugin.views;

import java.util.*;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.eclipse.core.runtime.jobs.*;
import org.eclipse.jdt.core.*;
import org.eclipse.jdt.core.search.*;
import org.eclipse.jdt.internal.core.*;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.internal.*;
import org.eclipse.ui.part.*;

import com.dexels.navajo.studio.script.plugin.*;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class FunctionViewer extends ViewPart {

	private ArrayList adapters = new ArrayList();
    private IProject myProject;
    private ComboViewer functionSelector;
    private Label usageLabel;
    private SearchResultContentProvider searchProvider;

    /* (non-Javadoc)
     * @see org.eclipse.ui.IWorkbenchPart#createPartControl(org.eclipse.swt.widgets.Composite)
     */
    public void createPartControl(Composite parent) {
        
        functionSelector = new ComboViewer(parent);
		searchProvider = new SearchResultContentProvider();
//		adapterSelector.getCombo().setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL,GridData.HORIZONTAL_ALIGN_BEGINNING,true,false));

		functionSelector.setContentProvider(searchProvider);
		functionSelector.setLabelProvider(searchProvider);

		
 
        parent.layout(true);
        
        myProject = NavajoScriptPluginPlugin.getDefault().getCurrentProject();
        new Job("Looking for functions"){

            protected IStatus run(IProgressMonitor monitor) {
                loadAdapters();
                 Display.getDefault().syncExec(new Runnable() {

                     public void run() {
                         functionSelector.setInput(adapters);
                     }
                     });

                 functionSelector.addSelectionChangedListener(new ISelectionChangedListener(){
                    public void selectionChanged(SelectionChangedEvent event) {
                        int index = functionSelector.getCombo().getSelectionIndex();
                        SearchMatch sm = (SearchMatch)functionSelector.getElementAt(index);
//                        JavaElement ik = (JavaElement)JavaCore.create(sm.getResource());
                        String classname = searchProvider.getText(sm);
                        
                    }});
                 return Status.OK_STATUS;

            }
		}.schedule();

    }

    private void adapterSelected() {
        
    }
    
	private void loadAdapters() {
        try {
            adapters = NavajoScriptPluginPlugin.getDefault().searchForExtendingClasses(myProject, NavajoScriptPluginPlugin.NAVAJO_FUNCTION_CLASS, null);
        } catch (CoreException e) {
            e.printStackTrace();
        }
	}
    
    
    /* (non-Javadoc)
     * @see org.eclipse.ui.IWorkbenchPart#setFocus()
     */
    public void setFocus() {
        // TODO Auto-generated method stub

    }

}
