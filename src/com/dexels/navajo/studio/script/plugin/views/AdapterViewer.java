/*
 * Created on Apr 8, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.studio.script.plugin.views;

import java.util.ArrayList;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.search.SearchMatch;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.TableColumn;

import com.dexels.navajo.studio.script.plugin.NavajoScriptPluginPlugin;
import com.dexels.navajo.studio.script.plugin.SearchResultContentProvider;
import com.dexels.navajo.studio.script.plugin.navajobrowser.TypeFieldContentProvider;

/**
 * @author Administrator
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class AdapterViewer extends BaseNavajoView {

    private ArrayList adapters = new ArrayList();

    private IProject myProject;

    private ComboViewer adapterSelector;

    private SearchResultContentProvider searchProvider;

    private TypeFieldContentProvider myProvider;

    private TableViewer myViewer;

    private ComboViewer projectSelector;

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.IWorkbenchPart#createPartControl(org.eclipse.swt.widgets.Composite)
     */
    public void createPartControl(Composite parent) {
        parent.setLayout(new GridLayout());
        projectSelector = new ComboViewer(parent);
        adapterSelector = new ComboViewer(parent);

        projectSelector.setContentProvider(new IStructuredContentProvider() {
            public void dispose() {
            }

            public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
            }

            public Object[] getElements(Object inputElement) {
                return ((ArrayList) inputElement).toArray();
            }
        });

        projectSelector.setLabelProvider(new LabelProvider() {
            public String getText(Object element) {
                if (element instanceof IProject) {
                    return ((IProject) element).getName();
                } else {
                    return super.getText(element);
                }
            }
        });
        try {
            projectSelector.setInput(NavajoScriptPluginPlugin.getDefault().getJavaProjects());
        } catch (CoreException e1) {
            e1.printStackTrace();
        }
        GridData gd = new GridData(GridData.FILL, GridData.BEGINNING, true, false);
        gd.grabExcessHorizontalSpace = true;
        projectSelector.getCombo().setLayoutData(gd);

        GridData gd2 = new GridData(GridData.FILL, GridData.BEGINNING, true, false);
        gd.grabExcessHorizontalSpace = true;

        adapterSelector.getCombo().setLayoutData(gd2);

        Button refresh = new Button(parent, SWT.PUSH);
        refresh.setText("Refresh!");

        projectSelector.addSelectionChangedListener(new ISelectionChangedListener() {
            public void selectionChanged(SelectionChangedEvent event) {
                loadAdaptersFromProject();
            }
        });
        refresh.addSelectionListener(new SelectionListener() {
            public void widgetSelected(SelectionEvent e) {
                loadAdaptersFromProject();
            }

            public void widgetDefaultSelected(SelectionEvent e) {
                loadAdaptersFromProject();
            }
        });

        Button openSource = new Button(parent, SWT.PUSH);
        openSource.setText("Open");
        openSource.addSelectionListener(new SelectionListener() {
            public void widgetSelected(SelectionEvent e) {
                try {
                    openSelectedField();
                } catch (JavaModelException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }

            public void widgetDefaultSelected(SelectionEvent e) {
                // TODO Auto-generated method stub

            }
        });

        searchProvider = new SearchResultContentProvider();
        //		adapterSelector.getCombo().setLayoutData(new
        // GridData(GridData.HORIZONTAL_ALIGN_FILL,GridData.HORIZONTAL_ALIGN_BEGINNING,true,false));

        adapterSelector.setContentProvider(searchProvider);
        adapterSelector.setLabelProvider(searchProvider);

        myProvider = new TypeFieldContentProvider();
        myViewer = new TableViewer(parent, SWT.BORDER | SWT.V_SCROLL | SWT.FULL_SELECTION);
        myViewer.setContentProvider(myProvider);
        myViewer.setLabelProvider(myProvider);

        GridData gd3 = new GridData(GridData.FILL, GridData.FILL, true, false, 2, 1);
        gd3.grabExcessHorizontalSpace = true;
        gd3.grabExcessVerticalSpace = true;
        myViewer.getTable().setLayoutData(gd3);

        TableColumn tc = new TableColumn(myViewer.getTable(), SWT.LEFT);
        TableColumn tc2 = new TableColumn(myViewer.getTable(), SWT.LEFT);
        myViewer.setColumnProperties(new String[] { "Name", "Type" });

        TableLayout layout = new TableLayout();
        layout.addColumnData(new ColumnWeightData(33, true));
        layout.addColumnData(new ColumnWeightData(33, true));
        myViewer.getTable().setLayout(layout);
        myViewer.getTable().setLinesVisible(true);
        myViewer.getTable().setHeaderVisible(true);
        myViewer.getTable().layout();
        parent.layout(true);
        //	       loadAdaptersFromProject();

    }


    protected void openSelectedField() throws JavaModelException {
        int index = myViewer.getTable().getSelectionIndex();
        IField field = (IField) myProvider.getElements(myViewer.getInput())[index];
        //        field.getSourceRange().
        NavajoScriptPluginPlugin.getDefault().openInEditor((IFile) field.getResource(), field.getSourceRange());
    }

    private void loadAdaptersFromProject() {
        //        myProject =
        // NavajoScriptPluginPlugin.getDefault().getCurrentProject();
        ArrayList input = (ArrayList) projectSelector.getInput();
        myProject = (IProject) input.get(projectSelector.getCombo().getSelectionIndex());
        //        String project = (String)
        // ((IStructuredSelection)projectSelector.getSelection()).getFirstElement();
        //        ResourcesPlugin.getPlugin().getWorkspace().
        //        System.err.println("project class: "+project.getClass());
        //        myProject =
        // ResourcesPlugin.getWorkspace().getRoot().getProject(project.toString());
        if (myProject == null) {
            System.err.println("NO PROJECT!");
            return;
        }
        new Job("Looking for adapters") {

            protected IStatus run(IProgressMonitor monitor) {
                loadAdapters();
                Display.getDefault().syncExec(new Runnable() {

                    public void run() {
                        adapterSelector.setInput(adapters);
                    }
                });

                adapterSelector.addSelectionChangedListener(new ISelectionChangedListener() {
                    public void selectionChanged(SelectionChangedEvent event) {
                        try {
                            int index = adapterSelector.getCombo().getSelectionIndex();
                            SearchMatch sm = (SearchMatch) adapterSelector.getElementAt(index);
                            IJavaElement ik = (IJavaElement) JavaCore.create(sm.getResource());

                            Object oldInp = myViewer.getInput();
                            myViewer.setInput(ik);
                            myProvider.inputChanged(myViewer, oldInp, ik);
                            myViewer.setContentProvider(myProvider);

                            String classname = searchProvider.getText(sm);
                            IType[] itts = JavaCore.createCompilationUnitFrom((IFile)sm.getResource()).getAllTypes();
                            if (itts.length == 0) {
                                return;
                            }

                            IType itt = itts[0];
                            IField[] fields = itt.getFields();
                            System.err.println("************    **************");
                            for (int i = 0; i < fields.length; i++) {
                                String sign = fields[i].getTypeSignature();
                                System.err.println("FIELD: " + fields[i].getFlags() + " :: " + fields[i].getElementName() + " >> " + sign);
                            }
                        } catch (JavaModelException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                    }
                });
                return Status.OK_STATUS;

            }
        }.schedule();
    }

    private void adapterSelected() {

    }

    private void loadAdapters() {
        try {
            adapters = NavajoScriptPluginPlugin.getDefault().searchForImplementingClasses(myProject,
                    NavajoScriptPluginPlugin.NAVAJO_ADAPTER_INTERFACE, null);
        } catch (CoreException e) {
            e.printStackTrace();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.IWorkbenchPart#setFocus()
     */
    public void setFocus() {
        // TODO Auto-generated method stub

    }

  }
