package com.dexels.tipi.plugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.Map.Entry;

import org.eclipse.ant.core.AntRunner;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.browser.IWebBrowser;
import org.eclipse.ui.browser.IWorkbenchBrowserSupport;

import com.dexels.navajo.tipi.ant.projectbuilder.TipiBuildDeployJnlp;
import com.dexels.navajo.tipi.projectbuilder.ClientActions;

public class DeployAction implements IObjectActionDelegate {

	private ISelection selection;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	public void run(IAction action) {
		if (selection instanceof IStructuredSelection) {
			for (Iterator it = ((IStructuredSelection) selection).iterator(); it
					.hasNext();) {
				Object element = it.next();
				IProject project = null;
				if (element instanceof IProject) {
					project = (IProject) element;
				} else if (element instanceof IAdaptable) {
					project = (IProject) ((IAdaptable) element)
							.getAdapter(IProject.class);
				}
				if (project != null) {
//					TipiNature.NATURE_ID
//					toggleNature(project);
					deployProject(project);
				}
			}
		}
	}

	private void deployProject(IProject project) {
		try {
			if(project.hasNature(TipiNature.NATURE_ID)) {
					runDeployScript(project);
			}
		} catch (CoreException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void runDeployScript(IProject project) throws CoreException, IOException {
			runBuild(project.getFile("settings/build.xml"),null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction,
	 *      org.eclipse.jface.viewers.ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		this.selection = selection;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IObjectActionDelegate#setActivePart(org.eclipse.jface.action.IAction,
	 *      org.eclipse.ui.IWorkbenchPart)
	 */
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
	}

	
	public void runBuild(IFile f, IProgressMonitor monitor) throws IOException  {
		if(!f.exists()) {
			System.err.println("File not found!");
			return;
		}
  //    InputDialog dlg = new InputDialog(Display.getCurrent().getActiveShell(),"SSH Login", "Deployment ssh password", "",null);			
   //   if (dlg.open() == Window.OK) {
		IProject myProject = f.getProject();
		final Map<String,String> properties =	retrieveProperties(myProject,"settings/tipi.properties");
		
		String target = "deployJnlp";
		String buildType = properties.get("build");
		if("web".equals(buildType)) {
			target = "deployEcho";
		}
		
   		try {
//   			String sshPw = dlg.getValue();
   			
//   			runner.set
   			Map<String,String> deployProperties =	retrieveProperties(myProject,"settings/deploy.properties");
				String projectName = f.getProject().getName().toLowerCase();

   			String profileLinkList = createProfileLinkList(myProject,projectName,buildType,deployProperties);
   			
				properties.putAll(deployProperties);
				properties.put("projectName", projectName);
				properties.put("buildType", buildType);
				properties.put("profileLinkList", profileLinkList);
//				properties.put("codebase", f.getProject().getName());
				properties.put("message", "building tipi application...");
				properties.put("deployPath", "deploy/current");
				properties.put("baseDir", f.getProject().getLocation().toString());
   			System.err.println("Properties: "+properties);
   			
   			properties.putAll(deployProperties);
				
				AntRunner runner = new AntRunner();
   			runner.setBuildFileLocation(f.getLocation().toString());
   			runner.setExecutionTargets(new String[]{target});
   			
   	//		runner.setArguments("-DprojectName="+f.getProject().getName()+" -Dmessage=Building -Dcodebase="+codebase+" -DdeployRootSsh="+deployRootSsh+" -DbaseDir="+f.getProject().getLocation().toString()+" -DdeployPath="+"deploy/current");
   			runner.setArguments(createArguments(properties));
   			//		runner.addUserProperties(properties);
//   		   			System.err.println("Starting run");
   			
   	//		runner.addBuildLogger("com.dexels.tipi.plugin.TipiBuildLogger");
   			runner.run(monitor);
   			System.err.println("Run completed: "+target);
   			
//      		MessageDialog.openInformation(Display.getCurrent().getActiveShell(), "Thank you for deploying using the TipiPlugin!", properties.get("codebase")+"Application.jnlp");
      		Display.getDefault().asyncExec(new Runnable() {
				public void run() {
					IWorkbenchBrowserSupport browserSupport = PlatformUI.getWorkbench().getBrowserSupport();
					IWebBrowser browser;
					try {
						
						browser = browserSupport.createBrowser(IWorkbenchBrowserSupport.AS_VIEW, null, "Test", "Test");
						URL url = new URL(properties.get("tipiAppStore")+properties.get("projectName"));
						browser.openURL(url);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
   		} catch (Exception e) {
   			System.err.println("Showing error");
       		Display.getDefault().asyncExec(new Runnable(){
   				public void run() {

//   					ErrorDialog.openError(Display.getCurrent().getActiveShell(),"Tipi Deployment problem","Error building deploy", Status.CANCEL_STATUS);
   					Status status = new Status(IStatus.ERROR, "TipiPlugin", 0,
   			            "Error deploying to: "+properties.get("deployRootSsh"), null);

   			        // Display the dialog
   			        ErrorDialog.openError(Display.getCurrent().getActiveShell(),
   			            "Deployment error", "Unable to deploy. Check password, deployment server, and the settings/deploy.properties file.", status);
   				}
       		});
   	      e.printStackTrace();
 		//	}
      }
   		try {
				f.getProject().refreshLocal(IResource.DEPTH_INFINITE, monitor);
			} catch (CoreException er) {
				er.printStackTrace();
			}

	}

	private String createProfileLinkList(IProject myProject, String projectName, String buildType, Map<String, String> deployProperties) {
		StringBuffer sb = new StringBuffer();
		File dir = myProject.getLocation().toFile();
		// No profile support in echo:
		if("web".equals(buildType)) {
			return "<a href='"+deployProperties.get("tipiTomcatServer")+projectName+"/app'>Start App</a>";
		}
		for (File f : dir.listFiles()) {
			if(f.getName().endsWith(".jnlp")) {
				String snip = "<a href='"+f.getName()+"'><br/>[[Start "+f.getName()+"]]</a><br/>";
				sb.append(snip);
			}
		}
		sb.append("<hr/>");
		return sb.toString();
	}

	private String createArguments(Map<String, String> properties) {
		StringBuffer result = new StringBuffer();
		for (Entry<String,String> e :properties.entrySet()) {
			result.append("-D"+e.getKey()+"=\""+e.getValue()+"\" ");
		}
		return result.toString();
	}

	private Map<String,String> retrieveProperties(IProject myProject,String path) throws IOException {
		Map<String,String> result = new HashMap<String, String>();
		IFile properties = myProject.getFile(path);
		URL propertyURL =  properties.getLocationURI().toURL();
		InputStream is = propertyURL.openStream();
		PropertyResourceBundle pr = new PropertyResourceBundle(is);
		Enumeration<String> keys = pr.getKeys();
		while (keys.hasMoreElements()) {
			String key =  keys.nextElement();
			result.put(key, pr.getString(key));
		}
		is.close();
		return result;
	}

	

}
