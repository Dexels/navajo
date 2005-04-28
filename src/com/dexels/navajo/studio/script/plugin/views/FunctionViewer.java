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
//    private ComboViewer functionSelector;
//    private Label usageLabel;
    private SearchResultContentProvider searchProvider;
//    private Text inputField = null;
//    private Text outputField = null;
//    private Button refreshButton = null;
//    private Button calculateButton = null;
    private Socket currentSocket;
    private PrintWriter socketWriter;

    
    
    

//	private org.eclipse.swt.widgets.Shell sShell = null;  //  @jve:decl-index=0:visual-constraint="27,46"
	private Composite mainPanel = null;
	private Composite functionsPanel = null;
	private ListViewer functionList = null;
	private Composite functionOverview = null;
	private Label otherUsageLabel = null;
	private Label functionUsageLabel = null;
	private Label remarksLabel = null;
	private Text remarksField = null;
	private TabFolder tabFolder = null;
	private Button refreshFunctionsButton = null;
	private Composite expressionTab = null;
	private Text expressionField = null;
	private Label expression = null;
	private Label resultLabel = null;
	private Button goButton = null;
	private Text resultArea = null;
	private Button clearButton = null;
	private Button restartButton = null;
	/**
	 * This method initializes mainPanel	
	 *
	 */    
	private void createComposite(Composite parent) {
		mainPanel = new Composite(parent, SWT.NONE);		   
		createTabFolder();
		mainPanel.setLayout(new FillLayout());
	}
	/**
	 * This method initializes expressionTab	
	 *
	 */    
	private void createComposite1() {
		functionsPanel = new Composite(tabFolder, SWT.BORDER);		   
		functionList = new ListViewer(functionsPanel, SWT.V_SCROLL | SWT.BORDER);
		createComposite2();
		functionsPanel.setLayout(new FillLayout());
	}
	/**
	 * This method initializes composite2	
	 *
	 */    
	private void createComposite2() {
		GridData gridData10 = new GridData();
		GridLayout gridLayout9 = new GridLayout();
		GridData gridData8 = new GridData();
		GridData gridData6 = new GridData();
		GridData gridData5 = new GridData();
		functionOverview = new Composite(functionsPanel, SWT.NONE);		   
		otherUsageLabel = new Label(functionOverview, SWT.NONE);
		refreshFunctionsButton = new Button(functionOverview, SWT.NONE);
		functionUsageLabel = new Label(functionOverview, SWT.NONE);
		remarksLabel = new Label(functionOverview, SWT.NONE);
		remarksField = new Text(functionOverview, SWT.MULTI | SWT.WRAP | SWT.BORDER);
		otherUsageLabel.setText("Usage:");
		otherUsageLabel.setFont(new org.eclipse.swt.graphics.Font(org.eclipse.swt.widgets.Display.getDefault(), "Tahoma", 8, org.eclipse.swt.SWT.BOLD));
		functionUsageLabel.setText("-");
		functionUsageLabel.setLayoutData(gridData5);
		remarksLabel.setText("Remarks:");
		remarksLabel.setFont(new org.eclipse.swt.graphics.Font(org.eclipse.swt.widgets.Display.getDefault(), "Tahoma", 8, org.eclipse.swt.SWT.BOLD));
		remarksLabel.setLayoutData(gridData10);
		gridData5.grabExcessHorizontalSpace = true;
		gridData5.horizontalAlignment = org.eclipse.swt.layout.GridData.FILL;
		gridData5.horizontalSpan = 2;
		gridData6.grabExcessHorizontalSpace = true;
		gridData6.grabExcessVerticalSpace = true;
		gridData6.horizontalAlignment = org.eclipse.swt.layout.GridData.FILL;
		gridData6.verticalAlignment = org.eclipse.swt.layout.GridData.FILL;
		gridData6.horizontalSpan = 2;
		remarksField.setLayoutData(gridData6);
		gridData8.horizontalAlignment = org.eclipse.swt.layout.GridData.END;
		refreshFunctionsButton.setLayoutData(gridData8);
		refreshFunctionsButton.setText("Refresh");
		functionOverview.setLayout(gridLayout9);
		gridLayout9.numColumns = 2;
		gridData10.horizontalSpan = 2;
	}
	/**
	 * This method initializes tabFolder	
	 *
	 */    
	private void createTabFolder() {
		tabFolder = new TabFolder(mainPanel, SWT.BOTTOM);		   
		createComposite1();
		createComposite12();
		TabItem tabItem7 = new TabItem(tabFolder, SWT.NONE);
		TabItem tabItem1 = new TabItem(tabFolder, SWT.NONE);
		tabItem7.setControl(functionsPanel);
		tabItem7.setText("Functions");
		tabItem1.setControl(expressionTab);
		tabItem1.setText("Expressions");
	}
	/**
	 * This method initializes expressionTab	
	 *
	 */    
	private void createComposite12() {
		GridData gridData7 = new GridData();
		GridData gridData61 = new GridData();
		GridData gridData51 = new GridData();
		GridData gridData4 = new GridData();
		GridData gridData3 = new GridData();
		GridData gridData2 = new GridData();
		expressionTab = new Composite(tabFolder, SWT.NONE);		   
		GridLayout gridLayout11 = new GridLayout(3,false);
		expression = new Label(expressionTab, SWT.NONE);
		expressionTab.setLayout(gridLayout11);
		expressionField = new Text(expressionTab, SWT.BORDER);
		goButton = new Button(expressionTab, SWT.NONE);
		resultLabel = new Label(expressionTab, SWT.NONE);
		resultArea = new Text(expressionTab, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.BORDER);
		restartButton = new Button(expressionTab, SWT.NONE);
		clearButton = new Button(expressionTab, SWT.NONE);
		resultLabel.setText("Result:");
		resultLabel.setLayoutData(gridData51);
		expression.setText("Expression:");
		expressionField.setVisible(true);
		expressionField.setLayoutData(gridData2);
		goButton.setText("Go");
		goButton.setVisible(true);
		goButton.setLayoutData(gridData7);
		gridData2.grabExcessHorizontalSpace = true;
		gridData2.horizontalAlignment = org.eclipse.swt.layout.GridData.FILL;
		gridData3.verticalSpan = 2;
		gridData3.horizontalAlignment = org.eclipse.swt.layout.GridData.FILL;
		gridData3.grabExcessVerticalSpace = true;
		gridData3.grabExcessHorizontalSpace = true;
		gridData3.verticalAlignment = org.eclipse.swt.layout.GridData.FILL;
		resultArea.setLayoutData(gridData3);
		clearButton.setText("Clear");
		clearButton.setLayoutData(gridData4);
		gridData4.horizontalAlignment = org.eclipse.swt.layout.GridData.FILL;
		gridData4.verticalAlignment = org.eclipse.swt.layout.GridData.BEGINNING;
		gridData51.verticalSpan = 2;
		gridData51.verticalAlignment = org.eclipse.swt.layout.GridData.BEGINNING;
		gridData61.verticalAlignment = org.eclipse.swt.layout.GridData.BEGINNING;
		gridData61.horizontalAlignment = org.eclipse.swt.layout.GridData.FILL;
		restartButton.setLayoutData(gridData61);
		restartButton.setText("Restart");
		gridData7.horizontalAlignment = org.eclipse.swt.layout.GridData.FILL;
	}
    
    
    
    
    
    
    
    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.IWorkbenchPart#createPartControl(org.eclipse.swt.widgets.Composite)
     */

    public void createPartControl(Composite parent) {
        createComposite(parent);
//        parent.setLayout(new GridLayout());
//        functionSelector = new ComboViewer(parent);
        searchProvider = new SearchResultContentProvider();
//        GridData gda = new GridData(GridData.FILL, GridData.VERTICAL_ALIGN_BEGINNING, true, false);
//        gda.grabExcessHorizontalSpace = true;
//        functionSelector.getCombo().setLayoutData(gda);

        
//        functionList.getCombo().setLayoutData(gda);

        //   
        functionList.setContentProvider(searchProvider);
        functionList.setLabelProvider(searchProvider);
//        refreshButton = new Button(parent, SWT.PUSH);
//        refreshButton.setText("Restart");
//        GridData gd = new GridData(GridData.HORIZONTAL_ALIGN_FILL, GridData.VERTICAL_ALIGN_BEGINNING, false, false);
//        refreshButton.setLayoutData(gd);
//
//        Button insertExpressionButton = new Button(parent, SWT.PUSH);
//        insertExpressionButton.setText("Insert expression");
//        GridData gdx = new GridData(GridData.HORIZONTAL_ALIGN_FILL, GridData.VERTICAL_ALIGN_BEGINNING, false, false);
//        insertExpressionButton.setLayoutData(gdx);
// 
//
//        inputField = new Text(parent, SWT.SINGLE | SWT.BORDER);
//        gd = new GridData(GridData.FILL, GridData.VERTICAL_ALIGN_BEGINNING, true, false);
//        gd.grabExcessHorizontalSpace = true;
//        inputField.setLayoutData(gd);
//        calculateButton = new Button(parent, SWT.PUSH);
//        calculateButton.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL, GridData.VERTICAL_ALIGN_BEGINNING, false, false));
//        calculateButton.setText("go!");
//  
//
//        outputField = new Text(parent, SWT.V_SCROLL | SWT.MULTI | SWT.BORDER);
//        gd = new GridData(GridData.FILL, GridData.FILL, true, true);
//        gd.grabExcessHorizontalSpace = true;
//        gd.grabExcessVerticalSpace = true;
//        outputField.setLayoutData(gd);
//        
//        insertExpressionButton.addSelectionListener(new SelectionListener() {
//            public void widgetSelected(SelectionEvent e) {
//                NavajoScriptPluginPlugin.getDefault().insertIntoCurrentTextEditor("<expression value=\"" + inputField.getText() + "\"/>");
//            }
//
//            public void widgetDefaultSelected(SelectionEvent e) {
//            }
//        });
//
        
        refreshFunctionsButton.addSelectionListener(new SelectionListener() {
            public void widgetSelected(SelectionEvent e) {
                restartExpressionRunner();
            }
            public void widgetDefaultSelected(SelectionEvent e) {
            }
        });
       
        
        restartButton.addSelectionListener(new SelectionListener() {
            public void widgetSelected(SelectionEvent e) {
                restartExpressionRunner();
            }
            public void widgetDefaultSelected(SelectionEvent e) {
            }
        });
//        refreshFunctionsButton.addSelectionListener(new SelectionListener() {
//            public void widgetSelected(SelectionEvent e) {
//                restartExpressionRunner();
//            }
//            public void widgetDefaultSelected(SelectionEvent e) {
//            }
//        });
        
        clearButton.addSelectionListener(new SelectionListener() {
            public void widgetSelected(SelectionEvent e) {
                expressionField.setText("");
                resultArea.setText("");
            }

        

            public void widgetDefaultSelected(SelectionEvent e) {
            }
        });

        goButton.addSelectionListener(new SelectionListener() {

            public void widgetSelected(SelectionEvent e) {
                final String outexp = "E|"+expressionField.getText();
                new Job("Evaluating: "+outexp) {
                    protected IStatus run(IProgressMonitor monitor) {
                        final String line = sendRunnerCommand(outexp);
                        Display.getDefault().syncExec(new Runnable() {
                            public void run() {
                                resultArea.append(line);
                                resultArea.append("\n");
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
        
        functionList.addDoubleClickListener(new IDoubleClickListener(){

            public void doubleClick(DoubleClickEvent event) {
                IStructuredSelection iss =  (IStructuredSelection)functionList.getSelection();
                if (iss==null) {
                    return;
                }
                SearchMatch sss = (SearchMatch)iss.getFirstElement();
                if (sss==null) {
                    return;
                }
                NavajoScriptPluginPlugin.getDefault().openInEditor((IFile)sss.getResource());
                              
                
            }});
//
//        //		Workbench.getInstance().
//
        initializeFunctionDropdown();
        parent.layout(true);

    }
    
    private void openFunctionClass(String name) {
        try {
            Class cc = Class.forName(name);
            if (myProject==null) {
                return;
            }
            if (!(myProject instanceof JavaProject)) {
                return;
            }
            JavaProject jjj = (JavaProject)myProject;
            try {
                IType itt = jjj.findType(name);
                if (itt==null) {
                    return;
                }
                IResource irr = itt.getResource();
                if (irr!=null && irr instanceof IFile) {
                    NavajoScriptPluginPlugin.getDefault().openInEditor((IFile)irr);
                }
            } catch (JavaModelException e1) {
                e1.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    private void restartExpressionRunner() {
        try {
            if (NavajoScriptPluginPlugin.getDefault().getCurrentFunctionLaunch() != null) {
                NavajoScriptPluginPlugin.getDefault().getCurrentFunctionLaunch().terminate();
                NavajoScriptPluginPlugin.getDefault().setCurrentFunctionLaunch(null);
            }
            //TODO: Create some kind of message when no file has been opened.
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
                        functionList.setInput(adapters);
                    }
                });

                functionList.addSelectionChangedListener(new ISelectionChangedListener() {
                    public void selectionChanged(SelectionChangedEvent event) {
                        int index = functionList.getList().getSelectionIndex();
                        SearchMatch sm = (SearchMatch) functionList.getElementAt(index);
                        String classname = searchProvider.getText(sm);
                        final String outexp = "R|" + classname;
                        new Job("Evaluating: "+outexp) {
                            protected IStatus run(IProgressMonitor monitor) {
                                final String line = sendRunnerCommand(outexp);
                                Display.getDefault().syncExec(new Runnable() {
                                    public void run() {
                                        if (line.indexOf('|')==-1) {
                                            
                                        } else {
                                            StringTokenizer st =  new StringTokenizer(line,"|");
                                            String remarks = st.nextToken();
                                            String usage = st.nextToken();
                                            functionUsageLabel.setText(usage);
                                            remarksField.setText(remarks);
                                            resultArea.append(line);
                                            resultArea.append("\n");
                                        }
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
                // TODO Add dialog here explaining that you need to start the evaluation first.
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
