/*
 * Created on Jul 29, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.studio.script.plugin.views;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;
import org.eclipse.ui.ide.IDE;

import com.dexels.navajo.client.ClientException;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.dsl.expression.proposals.INavajoContextProvider;
import com.dexels.navajo.dsl.tsl.ui.internal.TslActivator;
import com.dexels.navajo.parser.DefaultExpressionEvaluator;
import com.dexels.navajo.studio.eclipse.INavajoActivityListener;
import com.dexels.navajo.studio.eclipse.IServerEntryListener;
import com.dexels.navajo.studio.eclipse.NavajoInput;
import com.dexels.navajo.studio.script.plugin.NavajoScriptPluginPlugin;
import com.dexels.navajo.studio.script.plugin.ServerInstance;
import com.dexels.navajo.studio.script.plugin.editors.INavajoScriptListener;
import com.dexels.navajo.studio.script.plugin.editors.TmlFormComposite;
import com.google.inject.Injector;
@Deprecated
public class TmlClientView extends BaseNavajoView implements IServerEntryListener,INavajoActivityListener {
    private Navajo myCurrentNavajo = null;
    private TmlFormComposite formComposite;
//    private TextViewer myService;
//    private Button goButton;
    private Button backButton;
//    private ComboViewer localeBox;
    private Button forwardButton;
    private Button reloadButton;
    private Button sourceButton;
    private Button setAsInputButton;
    private String currentService = null;
    private String lastInit = null;
    private final Stack<String> historyList = new Stack<String>();
    private final Stack<String> futureList = new Stack<String>();
    private final Map<String,Navajo> scriptMap = new HashMap<String,Navajo>();
    private Composite myContainer;
//	private ServerEntry serverEntry;
	private ServerInstance serverInstance;
	
	
	private IProject currentProject = null;
  
	
	public void setServerInstance(ServerInstance si) {
		this.serverInstance = si;
		formComposite.setServerInstance(si);
	}
	
    public void createPartControl(Composite parent) {
    	
    	
        myContainer = new Composite(parent,SWT.NONE);
        myContainer.setBackground(new Color(Display.getCurrent(), 240, 240, 220));

        myContainer.setLayout(new GridLayout(1,false));
      
        
        Composite headComp = new Composite(myContainer,SWT.BORDER);
        headComp.setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,false));
        headComp.setBackground(new Color(Display.getCurrent(), 240, 240, 220));

        TableWrapLayout twl = new TableWrapLayout();
        twl.numColumns=9;
        headComp.setLayout(twl);

        
        backButton = new Button(headComp,SWT.PUSH);
        backButton.setText("<");
        backButton.setLayoutData(new TableWrapData(TableWrapData.LEFT,TableWrapData.FILL_GRAB));
        backButton.addSelectionListener(new SelectionListener() {
            public void widgetSelected(SelectionEvent e) {
                back();

            }

            public void widgetDefaultSelected(SelectionEvent e) {
            }});
        forwardButton = new Button(headComp,SWT.PUSH);
        forwardButton.setText(">");
        forwardButton.setLayoutData(new TableWrapData(TableWrapData.LEFT,TableWrapData.FILL_GRAB));
        forwardButton.addSelectionListener(new SelectionListener() {
            public void widgetSelected(SelectionEvent e) {
                forward();
            }

            public void widgetDefaultSelected(SelectionEvent e) {
            }});
        reloadButton = new Button(headComp,SWT.PUSH);
        reloadButton.setText("Reload");
        reloadButton.setLayoutData(new TableWrapData(TableWrapData.LEFT,TableWrapData.FILL_GRAB));
        reloadButton.addSelectionListener(new SelectionListener() {
            public void widgetSelected(SelectionEvent e) {
                reload();
            }

            public void widgetDefaultSelected(SelectionEvent e) {
            }});

        sourceButton = new Button(headComp,SWT.PUSH);
        sourceButton.setText("Show source");
        sourceButton.setEnabled(false);
        sourceButton.setLayoutData(new TableWrapData(TableWrapData.LEFT,TableWrapData.FILL_GRAB));
        sourceButton.addSelectionListener(new SelectionListener() {
            public void widgetSelected(SelectionEvent e) {
                showSource();
            }

            public void widgetDefaultSelected(SelectionEvent e) {
            }});

        setAsInputButton = new Button(headComp,SWT.PUSH);
        setAsInputButton.setText("Use as input");
        setAsInputButton.setLayoutData(new TableWrapData(TableWrapData.LEFT,TableWrapData.FILL_GRAB));
        setAsInputButton.addSelectionListener(new SelectionListener() {
            public void widgetSelected(SelectionEvent e) {
            	setCurrentAsInput(currentProject);
            }

            public void widgetDefaultSelected(SelectionEvent e) {
            }});

        
        
        forwardButton.setEnabled(false);
        backButton.setEnabled(false);
        reloadButton.setEnabled(false);
        
        formComposite = new TmlFormComposite(myContainer);
        formComposite.setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,true));
//        formComposite.addNavajoScriptListener(this);
        NavajoScriptPluginPlugin.getDefault().addNavajoActivityListener(this);
    }

    
    protected void setCurrentAsInput(IProject currentProject) {
		Injector ii = TslActivator.getInstance().getInjector("com.dexels.navajo.dsl.tsl.Tsl");
		INavajoContextProvider icp2 = ii.getInstance(INavajoContextProvider.class);
		icp2.setInputNavajo(currentProject, formComposite.getCurrentNavajo());
		
	}

	public void addNavajoScriptListener(INavajoScriptListener listener) {
    	formComposite.addNavajoScriptListener(listener);
	}

	public void removeNavajoScriptListener(INavajoScriptListener listener) {
		formComposite.removeNavajoScriptListener(listener);
	}

    
	protected void showSource() {

        IWorkbenchWindow window = NavajoScriptPluginPlugin.getDefault().getWorkbench().getActiveWorkbenchWindow();
        NavajoInput nai = new NavajoInput(currentService,myCurrentNavajo);
        IWorkbenchPage page = window.getActivePage();
        if (page != null) {
            try {
//            	IEditorDescriptor[] ee = Workbench.getInstance().getEditorRegistry();
            	IEditorDescriptor[] ee = PlatformUI.getWorkbench().getEditorRegistry().getEditors("test.xml");
            	if(ee==null || ee.length == 0) {
                    IDE.openEditor(page, nai, "org.eclipse.ui.DefaultTextEditor");
            	} else {
                    IDE.openEditor(page, nai, ee[0].getId());
            	}
            } catch (PartInitException e) {
                e.printStackTrace();
            }
        }
    }
    public void reload() {
        if (currentService==null) {
            return;
        }

        if (currentService.equals(lastInit)) {
            // init function;
//            myService.getTextWidget().setText(lastInit);
        } else {
            if (historyList.size()>1) {
                // top = current (size-1) , item before is calling script
                final String source = historyList.get(historyList.size()-2);
                final Navajo sourceNavajo = scriptMap.get(source);
                callNavajo(sourceNavajo);

            } else {
                callNavajo(null);
            }
        }
    }

	private void callNavajo(final Navajo sourceNavajo) {
		Job j = new Job("Running "+currentService+" on :"+serverInstance.getPort()){

		    protected IStatus run(IProgressMonitor monitor) {
		        try {
		        	myCurrentNavajo = serverInstance.callService(sourceNavajo, currentService);
//                            myCurrentNavajo = getServerEntry().runProcess(currentService,sourceNavajo);
		            setNavajo(myCurrentNavajo, currentService);
		                    return Status.OK_STATUS;
		        } catch (ClientException e) {
		            e.printStackTrace();
		            return Status.OK_STATUS;
		        }
		    }};
		    j.schedule();
	}

    
    public void dispose() {
//    	if(formComposite!=null) {
//            formComposite.removeNavajoScriptListener(this);
//    	}
        NavajoScriptPluginPlugin.getDefault().removeServerEntryListener(this);
        NavajoScriptPluginPlugin.getDefault().removeNavajoActivityListener(this);

        super.dispose();
    }
    private void back() {
         if (historyList.isEmpty()) {
             System.err.println("NO HISTORY?!");
            return;
         }
         System.err.println(">>> "+historyList);
       String current = (String)historyList.pop();
        String nn = (String)historyList.pop();
        System.err.println("BACKING TO: "+nn+" current: "+currentService);
         System.err.println(">>> "+historyList);
         Navajo n = scriptMap.get(nn);
         if (n!=null) {
//            scriptMap.put(current, myCurrentNavajo);
            setNavajo(n, nn);
            futureList.push(current);
        } else {
            System.err.println("ERROR GETTIN' HISTORY");
        }
         updateNavigationButtons();
    }

    private void forward() {
        if (futureList.isEmpty()) {
            return;
        }
        System.err.println("FUTURELIST: "+futureList);
        String nn = (String)futureList.pop();
        System.err.println("IN FORWARD: going to script: "+nn);
        Navajo n = (Navajo)scriptMap.get(nn);
        if (n!=null) {
            historyList.push(currentService);
//           scriptMap.put(currentService, myCurrentNavajo);
           setNavajo(n, nn);
                  }
        updateNavigationButtons();
    }

//    protected void serverChanged() {
//        formComposite.setServerEntry(getServerEntry());
//    }
//
//    private ServerEntry getServerEntry() {
//		return serverEntry;
//	}
//    
//    private void setServerEntry(ServerEntry s) {
//    	this.serverEntry = s;
//    }

//	protected void go() {
//        final ServerEntry se = getServerEntry();
////         final String script = myService.getTextWidget().getText();
//         lastInit = script;
//        Job j = new Job("Running "+script+" on "+se.getServer()){
//
//            protected IStatus run(IProgressMonitor monitor) {
//                try {
//                    myCurrentNavajo = se.runInit(script);
//                    setNavajo(myCurrentNavajo, script);
//                    if (myCurrentNavajo.getMessage("error")==null && myCurrentNavajo.getMessage("AuthenticationError")==null) {
//                        NavajoScriptPluginPlugin.getDefault().addToScriptInvocations(script);
//                    }
//                    return Status.OK_STATUS;
//                } catch (ClientException e) {
//                    e.printStackTrace();
//                    return Status.OK_STATUS;
//                }
//            }};
//            j.schedule();
//    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.IWorkbenchPart#setFocus()
     */
    public void setFocus() {
        // TODO Auto-generated method stub
    }

    public void setNavajo(final Navajo n, final String scriptName) {
         final Display d = PlatformUI.getWorkbench().getDisplay();
        d.syncExec(new Runnable() {
            public void run() {
                currentService = scriptName;
                if (NavajoFactory.getInstance().getExpressionEvaluator()==null) {
                    DefaultExpressionEvaluator dee = new DefaultExpressionEvaluator();
                    NavajoFactory.getInstance().setExpressionEvaluator(dee);

                }
                
                System.err.println("Setting NAVAJO TO SCRIPT: "+currentService);
                   myCurrentNavajo = n;
//                   sourceButton.setEnabled(true);
                   updateNavigationButtons();
                   if (formComposite != null) {
                    currentService = scriptName;
                    formComposite.setNavajo(n,scriptName);
                   myContainer.layout();
                    formComposite.reflow();
                } else {
                    System.err.println("hmmm. No formComposite");
                }
            }
        });

    }

    public void callingScript(String name) {
        futureList.clear();
    }

    public void gotoScript(String name, Navajo n) {
        currentService = name;
        scriptMap.put(name, n);
        if (historyList.isEmpty()) {
            historyList.push(name);
            return;
        }
        String last = historyList.peek();
        if (name.equals(last)) {
            return;
        }
        historyList.push(name);
        setNavajo(n, name);
        
        updateNavigationButtons();
    }

    private void updateNavigationButtons() {
       forwardButton.setEnabled(!futureList.isEmpty());        
       backButton.setEnabled(historyList.size()>1);
       reloadButton.setEnabled(myCurrentNavajo!=null);
       sourceButton.setEnabled(myCurrentNavajo!=null);
       }

    public void serverEntryChanged(int index) {
    }

    public Navajo getNavajo() {
        return scriptMap.get(historyList.peek());
    }

    public String getService() {
         return currentService;
    }
//
//	public void setServerPort(int port) {
//		ServerEntry se = new ServerEntry("local", "http","localhost:"+port+"/Postman","plugin","plugin");
//		setServerEntry(se);
//	}

	public void navajoResponse(Navajo n, String scriptName, IProject currentProject) {
		this.currentProject = currentProject;
		setNavajo(n, scriptName);
	}


}
