package com.dexels.navajo.studio.script.plugin;

import org.eclipse.core.internal.resources.*;
import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.eclipse.core.runtime.jobs.*;
import org.eclipse.debug.core.model.*;
import org.eclipse.jdt.core.*;
import org.eclipse.jdt.internal.core.*;
import org.eclipse.jdt.internal.ui.*;
import org.eclipse.jdt.launching.*;

import org.eclipse.jface.preference.*;
import org.eclipse.ui.*;
import org.eclipse.ui.internal.*;
import org.eclipse.ui.plugin.*;
import org.osgi.framework.BundleContext;

import com.dexels.navajo.client.*;
import com.dexels.navajo.functions.*;
import com.dexels.navajo.studio.eclipse.*;
import com.dexels.navajo.studio.eclipse.prefs.*;
import com.dexels.navajo.studio.script.plugin.navajobrowser.*;
import com.dexels.navajo.studio.script.plugin.navajobrowser.preferences.*;
import com.dexels.navajo.swingclient.components.*;


import java.io.*;
import java.io.File;
import java.util.*;
import java.util.Date;

/**
 * The main plugin class to be used in the desktop.
 */
public class NavajoScriptPluginPlugin extends AbstractUIPlugin {
	//The shared instance.
	private static NavajoScriptPluginPlugin plugin;
	//Resource bundle.
	private ResourceBundle resourceBundle;
//	GenericPropertyComponent gpc = new GenericPropertyComponent();
	
	
	
	
	
//	public static final String PLUGIN_ID = "com.sysdeo.eclipse.tomcat" ; 
//	public static final String NATURE_ID = PLUGIN_ID + ".tomcatnature" ; 

//	static final String TOMCAT_PREF_HOME_KEY = "tomcatDir";
//	static final String TOMCAT_PREF_BASE_KEY = "tomcatBase";	
//	static final String TOMCAT_PREF_CONFIGFILE_KEY = "tomcatConfigFile";
//	static final String TOMCAT_PREF_VERSION_KEY = "tomcatVersion";
	public static final String NAVAJO_PREF_JRE_KEY = "navajoJRE";
	public static final String NAVAJO_PREF_JVM_PARAMETERS_KEY = "jvmParameters";
	public static final String NAVAJO_PREF_JVM_CLASSPATH_KEY = "jvmClasspath";
	public static final String NAVAJO_PREF_JVM_BOOTCLASSPATH_KEY = "jvmBootClasspath";
	public static final String NAVAJO_PREF_PROJECTSINCP_KEY = "projectsInCp";
	public static final String NAVAJO_PREF_PROJECTSINSOURCEPATH_KEY = "projectsInSourcePath";
	public static final String NAVAJO_PREF_DEBUGMODE_KEY = "navajoDebugMode";
//	static final String TOMCAT_PREF_TARGETPERSPECTIVE = "targetPerspective";
				
//	private static final String TOMCAT_HOME_CLASSPATH_VARIABLE = "TOMCAT_HOME";
	
	
	public static final String NATURE_ID = "navajoNature";

    private String scriptPath = "navajo-tester/auxilary/scripts";

    private String compilePath = "navajo-tester/auxilary/compiled";
    private String tmlPath = "tml";

	public static final String PREF_JVM_PARAMETERS_KEY = "jvmParam";
	public static final String PREF_JVM_CLASSPATH_KEY = "jvmClasspath";
	public static final String PREF_JVM_BOOTCLASSPATH_KEY = "jvmBootClasspath";
    private NavajoBrowser navajoBrowser = null;

		
	private NavajoNature myNature = null;
	private NavajoBuilder myBuilder = null;
	
	private ClientInterface localClient = null;
    public static final String NAVAJO_SERVER_PATH = "navajo-tester/auxilary/config";
	/**
	 * The constructor.
	 */
	public NavajoScriptPluginPlugin() {
		super();
//		JavaCore jc = JavaCore.getJavaCore();
		
		System.err.println("Started NavajoScriptPlugin at: "+new Date());
	    System.setProperty("com.dexels.navajo.DocumentImplementation","com.dexels.navajo.document.nanoimpl.NavajoFactoryImpl");
	    System.setProperty("com.dexels.navajo.propertyMap", "com.dexels.navajo.studio.script.plugin.propertymap");
		plugin = this;
	}

	   public void runNavajo(IFile scriptFile) throws CoreException {
	       runNavajo(scriptFile,null);
	   }	


    public void runNavajo(IFile scriptFile, IFile sourceTml) throws CoreException {
        System.err.println("Running file with path: "+scriptFile.getFullPath());
        Thread.dumpStack();
        String name = null;
        if ("tml".equals(scriptFile.getFileExtension())) {
            System.err.println("Tml file found.");
            name = getScriptNameFromTml(sourceTml, scriptFile.getProject());
//            name = getScriptName(scriptFile, scriptFile.getProject());
            scriptFile = getScriptFolder(scriptFile.getProject()).getFile(name);
            System.err.println("Resolved to script: "+scriptFile.getLocation().toString());
        } else {
            name = getScriptName(scriptFile, scriptFile.getProject());
        }
        
        IFolder tml = getTmlFolder(scriptFile.getProject());
        IFile ii = tml.getFile(name+".tml");
//        IEditorReference ep[] = Workbench.getInstance().getActiveWorkbenchWindow().getActivePage().getEditorReferences();
//        for (int i = 0; i < ep.length; i++) {
//            IEditorPart iep = ep[i].getEditor(false);
//            if (iep==null) {
//                continue;
//            }
//            IFile iff = (IFile)iep.getEditorInput().getAdapter(IFile.class);
//            if (ii.equals(iff)) {
//                System.err.println("Disposing editor");
//                iep.dispose();
//            }
//        }
//        try {
//            ii.delete(false, null);
//        } catch (ResourceException e) {
//            System.err.println("Problem deleting resource: "+ii.getFullPath().toString());
//            e.printStackTrace();
//        }
        String location = null;
        if (sourceTml!=null) {
            location = sourceTml.getRawLocation().toString();
            System.err.println("Source TML FOUND: "+location);
        }
        runNavajoBootStrap(true,scriptFile,name,location);
    }


	private String[] addPreferenceProjectListToClasspath(String[] previouscp) {
	    List projectsList = NavajoScriptPluginPlugin.getDefault().getProjectsInCP();
    	System.err.println("Adding projectLIST: "+projectsList.size());
		String[] result = previouscp;
		Iterator it = projectsList.iterator();
		while (it.hasNext()) {
			try {
				ProjectListElement ple = (ProjectListElement) it.next();
				System.err.println("Found element: "+ple.getID());
//				IJavaProject jproject = JavaCore.create(ple.getProject());
				result = this.addProjectToClasspath(result, ple.getID());
			} catch (Exception e) {
			    e.printStackTrace();
				// nothing will be added to classpath
			}
		}

		return result;
	 	}

	private String[] addProjectToClasspath(String[] previouscp,String projectName) throws CoreException {
				IWorkspaceRoot root= ResourcesPlugin.getWorkspace().getRoot();
				IJavaProject project = JavaCore.getJavaCore().create(root.getProject(projectName));
		if ((project != null) && (project.exists() && project.isOpen())) {
			String[] projectcp = JavaRuntime.computeDefaultRuntimeClassPath(project);
//			System.err.println("Orig. size: "+previouscp.length);
//			System.err.println("Adding. size: "+projectcp.length);
			return StringUtil.concatUniq(projectcp, previouscp);
		} else {
//		    System.err.println("Returning previous... ");
			return previouscp;
		}
	}

	/**
	 * Launch a new JVM running Tomcat Main class
	 * Set classpath, bootclasspath and environment variable
	 */
	public void runNavajoBootStrap(boolean showInDebugger, IFile script,String scriptName, String sourceTmlPath) throws CoreException {
//			System.err.println("Running bootstrap!");
//		IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
		IProject myProject = script.getProject();
	    final IFolder tml = NavajoScriptPluginPlugin.getDefault().getTmlFolder(myProject);
		IFolder scriptPath = myProject.getFolder(NavajoScriptPluginPlugin.getDefault().getScriptPath());
		IFile file = myProject.getFile(new Path(NAVAJO_SERVER_PATH+"/server.xml"));
		IProjectNature ipn = myProject.getNature("org.eclipse.jdt.core.javanature");

		System.err.println("Raw location: "+myProject.getRawLocation());
		
		if (ipn!=null) {
            System.err.println("Java nature found: "+ipn.getClass());
            
		}
		String serverXml = file.getRawLocation().toString();
		
		
		String[] prgArgs;
		if (sourceTmlPath==null) {
			prgArgs = new String[]{serverXml,scriptName,tml.getRawLocation().toString()};
        } else {
    		prgArgs = new String[]{serverXml,scriptName,tml.getRawLocation().toString(),sourceTmlPath};
        }
		
	String[] classpath = new String[0];

		if (ipn instanceof JavaProject) {
		    System.err.println("Yes, it is a java project!!!");
            JavaProject jp = (JavaProject)ipn;
            IPath ipp = jp.getOutputLocation();
            System.err.println("Output location: "+ipp.toString());
            IResource ir = ResourcesPlugin.getWorkspace().getRoot().getFolder(ipp);
            System.err.println("Output: "+ir.getRawLocation().toOSString());
            classpath = new String[] {ir.getRawLocation().toOSString()};
            IClasspathEntry[] ice = jp.getRawClasspath();
            for (int i = 0; i < ice.length; i++) {
                System.err.println("CLASSPATHENTRY: "+ice[i].toString());
            }
		} else {
		    System.err.println("Not a java project?!");
		    System.err.println(" affe >>>"+myProject.getClass());
		}
		classpath = addPreferenceJvmToClasspath(classpath);
		classpath = addPreferenceProjectListToClasspath(classpath);
		classpath = StringUtil.concatUniq(classpath, this.getClasspath());

		String[] vmArgs = this.getVmArgs(myProject);
		vmArgs = addPreferenceParameters(vmArgs);

		for (int i = 0; i < vmArgs.length; i++) {
            System.err.println("vmARG: "+vmArgs[i]);
        }
		String[] bootClasspath = addPreferenceJvmToBootClasspath(new String[0]);

		StringBuffer programArguments = new StringBuffer();
		for (int i = 0; i < prgArgs.length; i++) {
			programArguments.append(" " + prgArgs[i]);
		}

		StringBuffer jvmArguments = new StringBuffer();
		for (int i = 0; i < vmArgs.length; i++) {
		    jvmArguments.append(" " + vmArgs[i]);
		}
		
		VMLauncherUtility.runVM("Navajo inline", "com.dexels.navajo.client.impl.NavajoRunner", classpath, bootClasspath, jvmArguments.toString(), programArguments.toString(), getSourceLocator(), isDebugMode(), showInDebugger,scriptName,myProject);

	}
	public String[] getClasspath() {
	    return new String[]{};
	}

	public String[] getVmArgs(IProject myProject) {
	    return new String[]{"-Duser.dir="+myProject.getLocation().toOSString()};
	}
	/**
     * @return
     */
    private ISourceLocator getSourceLocator() {
        // TODO Auto-generated method stub
        return null;
    }




    /**
     * @return
     */
    private boolean isDebugMode() {
         return false;
    }


    public static void log(String s) {
	    System.err.println(s);
	}
	
    public static void log(Throwable s) {
        s.printStackTrace();
    }
    
	/**
	 * This method is called upon plug-in activation
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		setup();
	}
	public String getNavajoJRE() {
		IPreferenceStore pref =	NavajoScriptPluginPlugin.getDefault().getPreferenceStore();
		String result = pref.getString(NAVAJO_PREF_JRE_KEY);
		if (result.equals("")) {
	      result = JavaRuntime.getDefaultVMInstall().getId();
		}
		return result;
	}
	public void setup() {
	    System.err.println("Setting up...");
	    IPreferenceStore ips = NavajoScriptPluginPlugin.getDefault().getPreferenceStore();
	    NavajoClientFactory.createDefaultClient().setUsername(ips.getString(NavajoPreferencePage.P_NAVAJO_USERNAME));
		NavajoClientFactory.createDefaultClient().setPassword(ips.getString(NavajoPreferencePage.P_NAVAJO_PASSWORD));
		NavajoClientFactory.createDefaultClient().setServerUrl(ips.getString(NavajoPreferencePage.P_NAVAJO_SERVERURL));
	}

	/**
	 * This method is called when the plug-in is stopped
	 */
	public void stop(BundleContext context) throws Exception {
		super.stop(context);
	}

	/**
	 * Returns the shared instance.
	 */
	public static NavajoScriptPluginPlugin getDefault() {
		return plugin;
	}

	/**
	 * Returns the string from the plugin's resource bundle,
	 * or 'key' if not found.
	 */
	public static String getResourceString(String key) {
		ResourceBundle bundle = NavajoScriptPluginPlugin.getDefault().getResourceBundle();
		try {
			return (bundle != null) ? bundle.getString(key) : key;
		} catch (MissingResourceException e) {
			return key;
		}
	}

	/**
	 * Returns the plugin's resource bundle,
	 */
	public ResourceBundle getResourceBundle() {
		return resourceBundle;
	}
	
	// BEWARE: Verify semicolon

	private String[] addPreferenceParameters(String[] previous) {
		String[] prefParams = StringUtil.cutString(NavajoScriptPluginPlugin.getDefault().getJvmParamaters(), ";");
		return StringUtil.concat(previous, prefParams);
	}



    private String[] addPreferenceJvmToClasspath(String[] previous) {
		String[] prefClasspath = StringUtil.cutString(NavajoScriptPluginPlugin.getDefault().getJvmClasspath(), ";");
		return StringUtil.concatUniq(previous, prefClasspath);
	}




    private String[] addPreferenceJvmToBootClasspath(String[] previous) {
		String[] prefBootClasspath = StringUtil.cutString(NavajoScriptPluginPlugin.getDefault().getJvmBootClasspath(), ";");
		return StringUtil.concatUniq(previous, prefBootClasspath);
	}



	
	public String getJvmParamaters() {
		IPreferenceStore pref =	NavajoScriptPluginPlugin.getDefault().getPreferenceStore();
		return pref.getString(PREF_JVM_PARAMETERS_KEY);		
	}

	public String getJvmClasspath() {
		IPreferenceStore pref =	NavajoScriptPluginPlugin.getDefault().getPreferenceStore();
		return pref.getString(PREF_JVM_CLASSPATH_KEY);		
	}

	public String getJvmBootClasspath() {
		IPreferenceStore pref =	NavajoScriptPluginPlugin.getDefault().getPreferenceStore();
		return pref.getString(PREF_JVM_BOOTCLASSPATH_KEY);		
	}


	public void setProjectsInCP(List projectsInCP) {
		this.saveProjectsToPreferenceStore(projectsInCP, NAVAJO_PREF_PROJECTSINCP_KEY);
	}

	public List getProjectsInCP() {
		return this.readProjectsFromPreferenceStore(NAVAJO_PREF_PROJECTSINCP_KEY);	
	}	
	
	public void setProjectsInSourcePath(List projectsInCP) {
		this.saveProjectsToPreferenceStore(projectsInCP, NAVAJO_PREF_PROJECTSINSOURCEPATH_KEY);
	}

	public List getProjectsInSourcePath() {
		IPreferenceStore pref =	NavajoScriptPluginPlugin.getDefault().getPreferenceStore();
		if(!(pref.contains(NAVAJO_PREF_PROJECTSINSOURCEPATH_KEY))) {
			// Project list in source path should be filled to make migration from v2.1 to v2.1.1 easier
			initProjectsInSourcePath();
		}		
		return this.readProjectsFromPreferenceStore(NAVAJO_PREF_PROJECTSINSOURCEPATH_KEY);	
	}

	static List readProjectsFromPreferenceStore(String keyInPreferenceStore) {
		IPreferenceStore pref =	NavajoScriptPluginPlugin.getDefault().getPreferenceStore();
		String stringList =  pref.getString(keyInPreferenceStore);

		List projectsIdList = new ArrayList();	
		StringTokenizer tokenizer = new StringTokenizer(stringList, ";");
		while (tokenizer.hasMoreElements()) {
			projectsIdList.add(tokenizer.nextToken());
		}
		
		return ProjectListElement.stringsToProjectsList(projectsIdList);
		
	}

	private void initProjectsInSourcePath() {
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IProject[] allProjects = root.getProjects();
		
		ArrayList tempList = new ArrayList(allProjects.length);
		for (int i = 0; i < allProjects.length; i++) {
			try {
				if((allProjects[i].isOpen()) && allProjects[i].hasNature(JavaCore.NATURE_ID)) {
					tempList.add(new ProjectListElement(allProjects[i].getNature(JavaCore.NATURE_ID).getProject()));
				}
			} catch (CoreException e) {
			    NavajoScriptPluginPlugin.getDefault().log(e);
			}
		}
		this.setProjectsInSourcePath(tempList);		
	}
	
	static void saveProjectsToPreferenceStore(List projectList, String keyInPreferenceStore) {
		IPreferenceStore pref =	NavajoScriptPluginPlugin.getDefault().getPreferenceStore();
		StringBuffer buf = new StringBuffer();
		Iterator it = projectList.iterator();
		while(it.hasNext()) {
			ProjectListElement each = (ProjectListElement)it.next();
			buf.append(each.getID());
			buf.append(';');	
		}
		pref.setValue(keyInPreferenceStore, buf.toString());
	}
	
    
    
    /**
     * @return Returns the compilePath.
     */
    public String getCompilePath() {
        return compilePath;
    }
    /**
     * @return Returns the scriptPath.
     */
    public String getScriptPath() {
        return scriptPath;
    }
    
    public String getTmlPath() {
        return tmlPath;
    }
    
    public IFolder getTmlFolder(IProject p) {
      return p.getFolder(tmlPath);
    }
    public IFolder getScriptFolder(IProject p) {
        return p.getFolder(getScriptPath());
      }
 
    public IFile getScriptFile(IProject p, String path) {
        System.err.println("Reported scriptPath: "+getScriptPath());
        System.err.println("ScriptFILE: "+path);
        IFolder iff = p.getFolder(getScriptPath());
        System.err.println("Folder: "+iff.getFullPath());
        IFile ifff =  iff.getFile(path+".xml");
        System.err.println("FILE: "+ifff.getFullPath());
        return ifff;
    }
    
    public String getScriptName(IFile script, IProject project) {
        ArrayList al = new ArrayList();
        StringBuffer sb = new StringBuffer();
        IFolder scriptDir  = project.getFolder(getScriptPath());
        IResource ir  = script;

        while (!ir.equals(scriptDir)) {
            if (ir==null) {
                return "Straaange.. I have not seen this script before...";
            }
            al.add(ir.getName());
            ir = ir.getParent();
//            if (!ir.equals(scriptDir)) {
//            }
        }
        for (int i = al.size()-1; i >= 0 ; i--) {
            String current = (String)al.get(i);
            if (i!=al.size()-1) {
                sb.append("/");
            }
            sb.append(current);
        }
        String buffer = sb.toString();
        if (buffer.endsWith(".xml")) {
            return buffer.substring(0,buffer.length()-4);
        }
        return buffer;
    }

    public String getScriptNameFromTml(IFile tml, IProject project) {
        ArrayList al = new ArrayList();
        StringBuffer sb = new StringBuffer();
        IFolder tmlDir  = project.getFolder(getTmlPath());
        IResource ir  = tml;

        while (!ir.equals(tmlDir)) {
            if (ir==null) {
                return "Straaange.. I have not seen this script before...";
            }
            al.add(ir.getName());
            ir = ir.getParent();
//            if (!ir.equals(scriptDir)) {
//            }
        }
        for (int i = al.size()-1; i >= 0 ; i--) {
            String current = (String)al.get(i);
            if (i!=al.size()-1) {
                sb.append("/");
            }
            sb.append(current);
        }
        String buffer = sb.toString();
        if (buffer.endsWith(".xml")) {
            return buffer.substring(0,buffer.length()-4);
        }
        return buffer;
    }



    /**
     * @param browser
     */
    public void setNavajoView(NavajoBrowser browser) {
        navajoBrowser = browser;
    }

    public NavajoBrowser getNavajoView() {
        return navajoBrowser;
    }
    

    public void refreshResource(final IResource ir, IProgressMonitor monitor) throws CoreException {
//        Job job = new Job("Refreshing resource") {
//            protected IStatus run(IProgressMonitor monitor) {
//                try {
                    ir.refreshLocal(IResource.DEPTH_INFINITE, monitor);
//                   } catch (CoreException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//
//                return Status.OK_STATUS;
//            }
//        };
//        job.setPriority(Job.SHORT);
//        job.schedule();
    
    }

    /**
     * @return
     */
    public IFolder getCompileFolder(IProject ipp) {
           return ipp.getFolder(getCompilePath());
    }

}
