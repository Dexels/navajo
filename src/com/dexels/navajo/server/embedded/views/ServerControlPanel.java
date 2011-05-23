package com.dexels.navajo.server.embedded.views;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jetty.util.component.LifeCycle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;
import org.eclipse.ui.part.ViewPart;

import com.dexels.navajo.client.context.NavajoContext;
import com.dexels.navajo.server.embedded.impl.ServerInstanceImpl;
import com.dexels.navajo.studio.script.plugin.ServerInstance;


/**
 * This sample class demonstrates how to plug-in a new
 * workbench view. The view shows data obtained from the
 * model. The sample creates a dummy model on the fly,
 * but a real implementation would connect to the model
 * available either in this or another plug-in (e.g. the workspace).
 * The view is connected to the model using a content provider.
 * <p>
 * The view uses a label provider to define how model
 * objects should be presented in the view. Each
 * view can present the same model objects using
 * different labels and icons, if needed. Alternatively,
 * a single label provider can be shared between views
 * in order to ensure that objects of the same type are
 * presented in the same way everywhere.
 * <p>
 */

public class ServerControlPanel extends ViewPart {

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "com.dexels.navajo.server.embedded.views.ServerControlPanel";

	private Composite viewer;
//	private Action startServerAction;

//	private String serverURL = null;

//	private IProject currentProject = null;
	private NavajoContext localContext;
	
	private List<ServerInstanceImpl> serverInstances = new ArrayList<ServerInstanceImpl>();

	private CTabFolder tabFolder;

	private Combo projectSelection;

	private Appendable outputAppendable;
	
	/**
	 * The constructor.
	 */
	public ServerControlPanel() {
	}

	/**
	 * This is a callback that will allow us
	 * to create the viewer and initialize it.
	 */
	public void createPartControl(Composite parent) {
		viewer = new Composite(parent, SWT.NORMAL);
		
		viewer.setBackground(new Color(Display.getCurrent(), 240, 240, 220));

//		viewer.setLayout(new GridLayout(1,false));
      
		viewer.setLayout(new GridLayout(1,false));
        
        Composite headComp = new Composite(viewer,SWT.BORDER);
        headComp.setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,false));
        headComp.setBackground(new Color(Display.getCurrent(), 240, 240, 220));
        TableWrapLayout twl = new TableWrapLayout();
        twl.numColumns=9;
        headComp.setLayout(twl);		
        
        createProjectSelector(headComp);
        
        Button refresh = new Button(headComp,SWT.PUSH);
        refresh.setText("Refresh");

        Button start = new Button(headComp,SWT.PUSH);
        start.setText("Start");
        
        start.addSelectionListener(new SelectionListener(){

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				
			}

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				startSelectedProjectServer();
//				System.err.println("Starting");
				
			}});
		// Create the help context id for the viewer's control
		PlatformUI.getWorkbench().getHelpSystem().setHelp(viewer, "com.dexels.navajo.server.embedded.viewer");
//		makeActions("Navajo");
//		contributeToActionBars();
		tabFolder = new CTabFolder(viewer, SWT.NONE);
		tabFolder.setTabPosition(SWT.BOTTOM);
		tabFolder.setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,true));

	}

	private void createProjectSelector(Composite headComp) {
		projectSelection = new Combo(headComp, SWT.NORMAL);
		projectSelection.setLayoutData(new TableWrapData(TableWrapData.LEFT,TableWrapData.FILL_GRAB));

		IProject[] pp = ResourcesPlugin.getPlugin().getWorkspace().getRoot().getProjects();
		for (IProject iProject : pp) {
			projectSelection.add(iProject.getName());
		}
		projectSelection.select(0);
	}

	private void startSelectedProjectServer() {
		System.err.println("Selectionindex: "+projectSelection.getSelectionIndex());
		String item = projectSelection.getItem(projectSelection.getSelectionIndex());
		CTabItem cc = createNavajoServerTab(item);
		ServerInstance si = startServerInstance(item,cc);
		cc.setText(item+":"+si.getPort());
		tabFolder.setSelection(cc);
	}

	private CTabItem createNavajoServerTab(String name) {
		CTabItem cti = new CTabItem(tabFolder, SWT.NONE);

		cti.setShowClose(true);
		cti.setText(name);
	        return cti;
	}

//	private void contributeToActionBars() {
//		IActionBars bars = getViewSite().getActionBars();
//		fillLocalToolBar(bars.getToolBarManager());
//	}

	

	
//	private void fillLocalToolBar(IToolBarManager manager) {
//		manager.add(startServerAction);
////		manager.add(stopServerAction);
//	}

//	private void makeActions(final String projectName) {
//		startServerAction = createStartServerAction(projectName);
////		stopServerAction = createStopAction(projectName);
//	}
//
//	private Action createStartServerAction(final String projectName, final CTabItem tabItem) {
//		Action startServerAction = new Action() {
//
//			public void run() {
////				startServer(projectName);
//				startServerInstance();
//			} 
//			
//		};
//		
//		startServerAction.setText("Start server");
//		
//		startServerAction.setToolTipText("Starts server for project: "+projectName);
//		startServerAction.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().
//			getImageDescriptor(ISharedImages.IMG_TOOL_FORWARD));
//		return startServerAction;
//	}

//	private Action createStopAction(final String projectName) {
//		Action stopServerAction = new Action() {
//			public void run() {
//				EmbeddedServerActivator.getDefault().stopServer();
//			}
//		};
//		stopServerAction.setEnabled(false);
//		stopServerAction.setText("Stop server");
//		stopServerAction.setToolTipText("Stops server for project: "+projectName);
//		stopServerAction.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_ELCL_STOP));
//		return stopServerAction;
//	}

//	protected void setupClient(String server, String user, String pass) {
//		localContext = new NavajoContext();
//		localContext.setupClient(server,user, pass);
//		EmbeddedServerActivator.getDefault().setCurrentContext(localContext);
//		
//	}
//
//	protected void callPluginServices(IProject project) throws CoreException {
//		try {
//			localContext.callService("plugin/InitNavajoBundle");
//			Navajo n = localContext.getNavajo("plugin/InitNavajoBundle");
//			n.write(System.err);
//			Binary b = (Binary) n.getProperty("NavajoBundle/FunctionDefinition").getTypedValue();
//			IFolder iff = project.getFolder("navajoconfig");
//			if(!iff.exists()) {
//				iff.create(true, true, null);
//			}
//			IFile ifi = iff.getFile("functions.xml");
//			if(!ifi.exists()) {
//				ifi.create(b.getDataAsStream(), true, null);
//			} else {
//				ifi.setContents(b.getDataAsStream(), true, false,null);
//				ifi.refreshLocal(1, null);
//			}
//		} catch (ClientException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (NavajoException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

//	protected void dumpBundleStates() {
//		Bundle myBundle = EmbeddedServerActivator.getDefault().getBundle();
//		Bundle[] b = myBundle.getBundleContext().getBundles();
//		for (Bundle bundle : b) {
//			System.err.println("| > "+bundle.getSymbolicName()+" state: "+ bundle.getState());
//			if(bundle.getState()==Bundle.ACTIVE) {
//				System.err.println("Running");
//			}
//			
//		}
//		
//	}



	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		viewer.setFocus();
	}

	private ServerInstance startServerInstance(String projectName, final CTabItem ci) {

//		ci.setLayout(new GridLayout(1,false));
        
        Composite panel = new Composite(tabFolder,SWT.BORDER);
        panel.setBackground(new Color(Display.getCurrent(), 240, 240, 220));
        ci.setControl(panel);
		panel.setLayout(new GridLayout(1,false));

		Composite tb = new Composite(panel, SWT.NORMAL);
        tb.setBackground(new Color(Display.getCurrent(), 220, 220, 240));
        tb.setLayoutData(new GridData(GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL ));
        RowLayout rowLayout = new RowLayout();
        tb.setLayout(rowLayout);

		final StyledText textArea = new StyledText(panel, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
		textArea.setLayoutData(new GridData(GridData.FILL_BOTH | GridData.GRAB_HORIZONTAL | GridData.GRAB_VERTICAL));
		textArea.setEditable(false);

		outputAppendable = new Appendable() {
			
			@Override
			public Appendable append(CharSequence csq, int start, int end)
					throws IOException {
			    CharSequence cs = csq.subSequence(start, end);
			    append(cs);
			    return this;
			}
			
			@Override
			public Appendable append(final char c) throws IOException {

				Display.getDefault().syncExec(new Runnable(){

					@Override
					public void run() {
						textArea.append(""+c);
					}});
				return this;
			}
			
			@Override
			public Appendable append(final CharSequence csq) throws IOException {
				Display.getDefault().syncExec(new Runnable(){

					@Override
					public void run() {
						textArea.append(""+csq+"\n");
						
					}});
				return this;
			}
		};
		
		final ServerInstanceImpl si = new ServerInstanceImpl(outputAppendable);

		Button stop = new Button(tb,SWT.PUSH);
		stop.setText("Stop");
		stop.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				si.stopServer();
				ci.dispose();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		Button clear = new Button(tb,SWT.PUSH);
		clear.setText("Clear");
		clear.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				textArea.setText("");
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		


		si.addLifeCycleListener(new LifeCycle.Listener() {
			
			@Override
			public void lifeCycleStopping(LifeCycle arg0) {
				
			}
			
			@Override
			public void lifeCycleStopped(LifeCycle arg0) {
				ci.dispose();
	
			}
			
			@Override
			public void lifeCycleStarting(LifeCycle arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void lifeCycleStarted(LifeCycle arg0) {
//				System.err.println("Started");
				
			}
			
			@Override
			public void lifeCycleFailure(LifeCycle arg0, Throwable arg1) {
				// TODO Auto-generated method stub
				
			}
		});
//	cti.addDisposeListener(new DisposeListener() {
//			
//			@Override
//			public void widgetDisposed(DisposeEvent arg0) {
//				System.err.println("Closing tab");
//			}
//		});

		serverInstances.add(si);
		si.startServer(projectName);
		return si;
	}


}