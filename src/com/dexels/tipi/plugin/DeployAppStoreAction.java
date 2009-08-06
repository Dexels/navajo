package com.dexels.tipi.plugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.Map.Entry;

import org.eclipse.ant.core.AntRunner;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.browser.IWebBrowser;
import org.eclipse.ui.browser.IWorkbenchBrowserSupport;

import com.dexels.navajo.tipi.ant.AntRun;
import com.dexels.navajo.tipi.ant.projectbuilder.TipiBuildDeployJnlp;
import com.dexels.navajo.tipi.projectbuilder.ClientActions;
import com.dexels.tipi.plugin.impl.Deployer;

public class DeployAppStoreAction implements IObjectActionDelegate {

	private ISelection selection;
	private Combo templateCombo = null;

	private String selectedTemplate = null;
	private boolean includeJarsSelected;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	
//	  <property name="tipiServerUrl" value="http://localhost:8080/TipiServer/TipiAdminServlet"/>
//	  <property name="tipiServerUsername" value="ad"/>
//	  <property name="tipiServerPassword" value="pw"/>
//	  <property name="tipiServerApplication" value="${ant.project.name}"/>

	public void run(IAction action) {
		try {
		if (selection instanceof IStructuredSelection) {
			for (Iterator it = ((IStructuredSelection) selection).iterator(); it.hasNext();) {
				Object element = it.next();
				IProject project = null;
				if (element instanceof IProject) {
					project = (IProject) element;
				} else if (element instanceof IAdaptable) {
					project = (IProject) ((IAdaptable) element).getAdapter(IProject.class);
				}
				final IProject rProject = project;
				
				Map<String, String> preferenceSettings = getPreferenceSettings();
				preferenceSettings.put("tipiServerApplication", project.getName());
				MessageDialog.openInformation(Display.getCurrent().getActiveShell(), "Deploying", "Server: "+preferenceSettings.get("tipiServerUrl")+" app: "+preferenceSettings.get("tipiServerApplication"));
				InputStream antStream = getClass().getClassLoader().getResourceAsStream("com/dexels/tipi/plugin/tipiProjectBuild.xml");
				String result = AntRun.callAnt(antStream, rProject.getLocation().toFile(), preferenceSettings);
				System.err.println("Result: "+result);
			}
		}
		}catch (Throwable e) {
			e.printStackTrace();
		}
	}


	private Map<String,String> getPreferenceSettings() {
		Map<String,String> result = new HashMap<String, String>();
		IPreferenceStore st = TipiPluginActivator.getDefault().getPreferenceStore();
		result.put("tipiServerUrl", st.getString("tipiServerUrl"));
		result.put("tipiServerUsername", st.getString("tipiServerUsername"));
		result.put("tipiServerPassword", st.getString("tipiServerPassword"));
		return result;
	}
	


	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action
	 * .IAction, org.eclipse.jface.viewers.ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		this.selection = selection;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.IObjectActionDelegate#setActivePart(org.eclipse.jface.action
	 * .IAction, org.eclipse.ui.IWorkbenchPart)
	 */
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
	}

}
