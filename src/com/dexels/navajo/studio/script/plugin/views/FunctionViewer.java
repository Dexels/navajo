/*
 * Created on Apr 8, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.studio.script.plugin.views;

import java.io.*;
import java.net.*;
import java.util.*;

import org.eclipse.core.internal.resources.*;
import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.eclipse.core.runtime.jobs.*;
import org.eclipse.debug.core.*;
import org.eclipse.jdt.core.*;
import org.eclipse.jdt.core.search.*;
import org.eclipse.jdt.internal.core.*;
import org.eclipse.jface.text.*;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.text.edits.*;
import org.eclipse.ui.*;
import org.eclipse.ui.internal.*;
import org.eclipse.ui.part.*;
import org.eclipse.ui.texteditor.*;

import com.dexels.navajo.client.impl.*;
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
    private Text inputField = null;
    private Text outputField = null;
    private Button refreshButton = null;
    private Button calculateButton = null;
    private Socket currentSocket;
    private PrintWriter socketWriter;

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.IWorkbenchPart#createPartControl(org.eclipse.swt.widgets.Composite)
     */

    public void createPartControl(Composite parent) {
        parent.setLayout(new GridLayout());
        functionSelector = new ComboViewer(parent);
        searchProvider = new SearchResultContentProvider();
        GridData gda = new GridData(GridData.FILL, GridData.VERTICAL_ALIGN_BEGINNING, true, false);
        gda.grabExcessHorizontalSpace = true;
        functionSelector.getCombo().setLayoutData(gda);
   
        functionSelector.setContentProvider(searchProvider);
        functionSelector.setLabelProvider(searchProvider);
        refreshButton = new Button(parent, SWT.PUSH);
        refreshButton.setText("Restart");
        GridData gd = new GridData(GridData.HORIZONTAL_ALIGN_FILL, GridData.VERTICAL_ALIGN_BEGINNING, false, false);
        refreshButton.setLayoutData(gd);

        Button insertExpressionButton = new Button(parent, SWT.PUSH);
        insertExpressionButton.setText("Insert expression");
        GridData gdx = new GridData(GridData.HORIZONTAL_ALIGN_FILL, GridData.VERTICAL_ALIGN_BEGINNING, false, false);
        insertExpressionButton.setLayoutData(gdx);
 

        inputField = new Text(parent, SWT.SINGLE | SWT.BORDER);
        gd = new GridData(GridData.FILL, GridData.VERTICAL_ALIGN_BEGINNING, true, false);
        gd.grabExcessHorizontalSpace = true;
        inputField.setLayoutData(gd);
        calculateButton = new Button(parent, SWT.PUSH);
        calculateButton.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL, GridData.VERTICAL_ALIGN_BEGINNING, false, false));
        calculateButton.setText("go!");
  

        outputField = new Text(parent, SWT.V_SCROLL | SWT.MULTI | SWT.BORDER);
        gd = new GridData(GridData.FILL, GridData.FILL, true, true);
        gd.grabExcessHorizontalSpace = true;
        gd.grabExcessVerticalSpace = true;
        outputField.setLayoutData(gd);
        
        insertExpressionButton.addSelectionListener(new SelectionListener() {
            public void widgetSelected(SelectionEvent e) {
                NavajoScriptPluginPlugin.getDefault().insertIntoCurrentTextEditor("<expression value=\"" + inputField.getText() + "\"/>");
            }

            public void widgetDefaultSelected(SelectionEvent e) {
            }
        });

        refreshButton.addSelectionListener(new SelectionListener() {
            public void widgetSelected(SelectionEvent e) {
                restartExpressionRunner();
            }

        

            public void widgetDefaultSelected(SelectionEvent e) {
            }
        });

        calculateButton.addSelectionListener(new SelectionListener() {

            public void widgetSelected(SelectionEvent e) {
                final String outexp = "E|"+inputField.getText();
                new Job("Evaluating expression") {
                    protected IStatus run(IProgressMonitor monitor) {
                        final String line = sendRunnerCommand(outexp);
                        Display.getDefault().syncExec(new Runnable() {
                            public void run() {
                                outputField.append(line);
                                outputField.append("\n");
                           }
                        });

                         return Status.OK_STATUS;

                    }
                }.schedule();
             }

            public void widgetDefaultSelected(SelectionEvent e) {
                widgetSelected(e);
            }
        });

        //		Workbench.getInstance().

        initializeFunctionDropdown();
        parent.layout(true);

    }
    private void restartExpressionRunner() {
        try {
            if (NavajoScriptPluginPlugin.getDefault().getCurrentFunctionLaunch() != null) {
                NavajoScriptPluginPlugin.getDefault().getCurrentFunctionLaunch().terminate();
                NavajoScriptPluginPlugin.getDefault().setCurrentFunctionLaunch(null);
            }
             IEditorPart current = Workbench.getInstance().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
            IFile iff = (IFile) current.getEditorInput().getAdapter(IFile.class);
//            Job j = new Job ("Checking expression launch...") {
//                protected IStatus run(IProgressMonitor monitor) {
//                    final String result = sendRunnerCommand("T|test ok.");
//                    Display.getDefault().syncExec(new Runnable() {
//                        public void run() {
//                            outputField.append(result);
//                            outputField.append("\n");
//                       }
//                    });
//                   return Status.OK_STATUS;
//                }};
            Launch lll = NavajoScriptPluginPlugin.getDefault().runNavajoBootStrap("com.dexels.navajo.client.impl.NavajoExpressionRunner",
                    true, iff,  "", "", null);

            NavajoScriptPluginPlugin.getDefault().setCurrentFunctionLaunch(lll);

        } catch (CoreException e1) {
            e1.printStackTrace();
        }
    }
    private void initializeFunctionDropdown() {
        myProject = NavajoScriptPluginPlugin.getDefault().getCurrentProject();
        new Job("Looking for functions") {
            protected IStatus run(IProgressMonitor monitor) {
                loadFunctions();
                Display.getDefault().syncExec(new Runnable() {
                    public void run() {
                        functionSelector.setInput(adapters);
                    }
                });

                functionSelector.addSelectionChangedListener(new ISelectionChangedListener() {
                    public void selectionChanged(SelectionChangedEvent event) {
                        int index = functionSelector.getCombo().getSelectionIndex();
                        SearchMatch sm = (SearchMatch) functionSelector.getElementAt(index);
                        String classname = searchProvider.getText(sm);
                        final String outexp = "R|" + classname;
                        new Job("Evaluating expression") {
                            protected IStatus run(IProgressMonitor monitor) {
                                final String line = sendRunnerCommand(outexp);
                                Display.getDefault().syncExec(new Runnable() {
                                    public void run() {
                                        outputField.append(line);
                                        outputField.append("\n");
                                   }
                                });

                                 return Status.OK_STATUS;

                            }
                        }.schedule();
                     }
                });
                return Status.OK_STATUS;

            }
        }.schedule();
    }

    private void adapterSelected() {

    }

    private void loadFunctions() {
        try {
            adapters = NavajoScriptPluginPlugin.getDefault().searchForExtendingClasses(myProject, NavajoScriptPluginPlugin.NAVAJO_FUNCTION_CLASS,
                    null);
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

    private String sendRunnerCommand(String input) {

        try {
            currentSocket = new Socket(InetAddress.getLocalHost(), NavajoExpressionRunner.PORT);
            socketWriter = new PrintWriter(new OutputStreamWriter(currentSocket.getOutputStream()));
                    socketWriter.write(input + "\n");
                    socketWriter.flush();
                    BufferedReader br = new BufferedReader(new InputStreamReader(currentSocket.getInputStream()));
                 final String line = br.readLine();
//                Display.getDefault().syncExec(r);
                return line;
           } catch (UnknownHostException e2) {
                e2.printStackTrace();
                return "error: "+e2.getMessage();
            } catch (IOException e2) {
                e2.printStackTrace();
                return "error"+e2.getMessage();
                                          
        } finally {
            try {
                if (currentSocket!=null) {
                    currentSocket.close();
                } else {
                    System.err.println("Null socket. Strange.");
                }
            } catch (Exception e) {
                e.printStackTrace();
                return "error closing socket";
            }
        }
 
      }

}
