package com.dexels.tipi.plugin.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PropertyResourceBundle;

import org.eclipse.ant.core.AntRunner;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.browser.IWebBrowser;
import org.eclipse.ui.browser.IWorkbenchBrowserSupport;

import com.dexels.tipi.plugin.TipiNature;

public class Deployer {
	


	public void deployProject(final IProject project,final String deployName, final String username, final String password, final boolean includeJars) {
		System.err.println("Deploying: "+deployName);
		Job job = new Job("Deploy task") {
		     protected IStatus run(IProgressMonitor monitor) {
		  		try {
					if(project.hasNature(TipiNature.NATURE_ID)) {
							runDeployScript(project,deployName,username,password, includeJars,monitor);
					}
				} catch (CoreException e) {
					StringWriter sw = new StringWriter();
					
					e.printStackTrace();
					e.printStackTrace(new PrintWriter(sw));
				
			      ErrorDialog.openError(Display.getCurrent().getActiveShell(),"Tipi Deployment problem",sw.toString(), Status.CANCEL_STATUS);
			      
				} catch (IOException e) {
					StringWriter sw = new StringWriter(); 
					e.printStackTrace();
					e.printStackTrace(new PrintWriter(sw));
					
			      ErrorDialog.openError(Display.getCurrent().getActiveShell(),"Tipi Deployment problem",sw.toString(), Status.CANCEL_STATUS);
				}
					return Status.OK_STATUS;
		        }
		     };
		  job.setPriority(Job.LONG);
		  job.schedule(); // start as soon as possible

	}

	private void runDeployScript(IProject project,String deployName,String username, String password,boolean includeJars,IProgressMonitor monitor) throws CoreException, IOException {
			runBuild(project.getFile("settings/build.xml"),deployName,username, password,includeJars, monitor);
	}
	public void runBuild(IFile f, final String deployName,String username, String password, boolean includeJars,IProgressMonitor monitor) throws IOException  {
		if(!f.exists()) {
			System.err.println("File not found!");
			return;
		}
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
   			System.err.println("Deploy name: "+deployName);
   			Map<String,String> standardProperties =	retrieveProperties(myProject,"settings/deploy.properties");
   			properties.putAll(standardProperties);
   			IFolder deployments = myProject.getFolder("settings/deployments/");
   			if(deployName!=null && deployments.exists()) {
      			Map<String,String> deployProperties =	retrieveProperties(myProject,"settings/deployments/"+deployName+".properties");
   				properties.putAll(deployProperties);
   				
   			}
   			final String projectName = f.getProject().getName().toLowerCase();

   			String profileLinkList = createProfileLinkList(myProject,projectName,buildType,properties);
   			
				properties.put("projectName", projectName);
				properties.put("deployName", deployName);
				if(includeJars) {
					properties.put("includeJars","true");
				}
				properties.put("username", username);
				properties.put("password", password);
				properties.put("buildType", buildType);
				properties.put("profileLinkList", profileLinkList);
//				properties.put("codebase", f.getProject().getName());
				properties.put("message", "building tipi application...");
				properties.put("deployPath", "deploy/current");
				properties.put("baseDir", f.getProject().getLocation().toString());
   			System.err.println("Properties: "+properties);
   			
   			
				AntRunner runner = new AntRunner();
//				runner.addBuildLogger(className)
   			runner.setBuildFileLocation(f.getLocation().toString());
   			runner.setExecutionTargets(new String[]{target});
   			
   	//		runner.setArguments("-DprojectName="+f.getProject().getName()+" -Dmessage=Building -Dcodebase="+codebase+" -DdeployRootSsh="+deployRootSsh+" -DbaseDir="+f.getProject().getLocation().toString()+" -DdeployPath="+"deploy/current");
   			runner.setArguments(createArguments(properties));
   			//		runner.addUserProperties(properties);
//   		   			System.err.println("Starting run");
   			
   	//		runner.addBuildLogger("com.dexels.tipi.plugin.TipiBuildLogger");
				for (Entry<String, String> ee : properties.entrySet()) {
					System.err.println("Deployment properties: "+ee.getKey()+" :: "+ee.getValue());
					
				}

				monitor.subTask("Running build script");
   			runner.run(monitor);
   			System.err.println("Run completed: "+target);
   			
//      		MessageDialog.openInformation(Display.getCurrent().getActiveShell(), "Thank you for deploying using the TipiPlugin!", properties.get("codebase")+"Application.jnlp");
      		Display.getDefault().asyncExec(new Runnable() {
				public void run() {
					IWorkbenchBrowserSupport browserSupport = PlatformUI.getWorkbench().getBrowserSupport();
					IWebBrowser browser;
					MessageDialog.openConfirm(Display.getCurrent().getActiveShell(), "Done", "Deploying "+projectName+" to "+deployName+" complete.");
					try {
						
						browser = browserSupport.createBrowser(IWorkbenchBrowserSupport.AS_VIEW, null, "Test", "Test");
						URL url = null;
						url = new URL(properties.get("deployTarget") );
						browser.openURL(url);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
   		} catch (Exception e) {
   			final StringWriter sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
   			System.err.println("Showing error");
       		Display.getDefault().asyncExec(new Runnable(){
   				public void run() {
   					
   			     // ErrorDialog.openError(Display.getCurrent().getActiveShell(),"Tipi Deployment problem",sw.toString(), Status.CANCEL_STATUS);

//   					ErrorDialog.openError(Display.getCurrent().getActiveShell(),"Tipi Deployment problem","Error building deploy", Status.CANCEL_STATUS);
   					Status status = new Status(IStatus.ERROR, "TipiPlugin", 0,
   			            "Error deploying to: "+properties.get("deployRootSsh"), null);

   			        // Display the dialog
   			        ErrorDialog.openError(Display.getCurrent().getActiveShell(),
   			            "Deployment error", "Unable to deploy. Check password, deployment server, and the settings/deploy.properties file.\n"+sw.toString(), status);
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
