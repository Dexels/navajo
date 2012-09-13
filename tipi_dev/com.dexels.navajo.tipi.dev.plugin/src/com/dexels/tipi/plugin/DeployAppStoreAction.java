package com.dexels.tipi.plugin;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import com.dexels.navajo.tipi.ant.AntRun;

public class DeployAppStoreAction implements IObjectActionDelegate {

	private ISelection selection;

//	private Combo templateCombo = null;
//	private String selectedTemplate = null;
//	private boolean includeJarsSelected;

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
			for (Iterator<IStructuredSelection> it = ((IStructuredSelection) selection).iterator(); it.hasNext();) {
				Object element = it.next();
				IProject project = null;
				if (element instanceof IProject) {
					project = (IProject) element;
				} else if (element instanceof IAdaptable) {
					project = (IProject) ((IAdaptable) element).getAdapter(IProject.class);
				}
				if(project==null) {
					return;
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
