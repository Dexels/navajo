package com.dexels.navajo.studio.script.plugin;

import org.eclipse.core.internal.jobs.*;
import org.eclipse.core.internal.resources.*;
import org.eclipse.core.internal.runtime.*;
import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.eclipse.core.runtime.jobs.*;
import org.eclipse.debug.core.*;
import org.eclipse.debug.core.model.*;
import org.eclipse.jdt.core.*;
import org.eclipse.jdt.core.search.*;
import org.eclipse.jdt.internal.core.*;
import org.eclipse.jdt.internal.ui.*;
import org.eclipse.jdt.internal.ui.javaeditor.*;
import org.eclipse.jdt.launching.*;

import org.eclipse.jface.dialogs.*;
import org.eclipse.jface.preference.*;
import org.eclipse.jface.text.*;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.viewers.*;
import org.eclipse.text.edits.*;
import org.eclipse.ui.*;
import org.eclipse.ui.internal.*;
import org.eclipse.ui.part.*;
import org.eclipse.ui.plugin.*;
import org.eclipse.ui.texteditor.*;
import org.osgi.framework.BundleContext;

import com.dexels.navajo.client.*;
import com.dexels.navajo.document.*;
import com.dexels.navajo.functions.*;
import com.dexels.navajo.studio.eclipse.*;
import com.dexels.navajo.studio.eclipse.prefs.*;
import com.dexels.navajo.studio.script.plugin.navajobrowser.*;
import com.dexels.navajo.studio.script.plugin.navajobrowser.preferences.*;


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

//	public static final String NAVAJO_PREF_JRE_KEY = "navajoJRE";
//	public static final String NAVAJO_PREF_JVM_PARAMETERS_KEY = "jvmParameters";
//	public static final String NAVAJO_PREF_JVM_CLASSPATH_KEY = "jvmClasspath";
//	public static final String NAVAJO_PREF_JVM_BOOTCLASSPATH_KEY = "jvmBootClasspath";
//	public static final String NAVAJO_PREF_PROJECTSINCP_KEY = "projectsInCp";
//	public static final String NAVAJO_PREF_PROJECTSINSOURCEPATH_KEY = "projectsInSourcePath";
//	public static final String NAVAJO_PREF_DEBUGMODE_KEY = "navajoDebugMode";
	
	public static final String NAVAJO_NATURE = "NavajoNature";
	
	public static final String NAVAJO_REPOSITORY_INTERFACE = "com.dexels.navajo.server.Repository";
	public static final String NAVAJO_ADAPTER_INTERFACE = "com.dexels.navajo.mapping.Mappable";
	public static final String NAVAJO_FUNCTION_CLASS = "com.dexels.navajo.parser.FunctionInterface";
	
		   public static final String NAVAJO_ROOT_PATH = "navajo-tester";   
	    public static final String RELATIVE_COMPILED_PATH = "/auxilary/compiled";
	    public static final String RELATIVE_SCRIPT_PATH = "/auxilary/scripts";
	    public static final String RELATIVE_TML_PATH = "/auxilary/tml";
//	    public static final String NAVAJO_SERVER_PATH = "navajo-tester/auxilary/config";
	    public static final String SERVER_FILE_NAME = "server.xml";
	    public static final String NAVAJO_CONFIG_PATH = "/auxilary/config";
	    
	    public static final String APPLICATION_SETTINGS_PATH = "src/application.properties";
	
	public static final String NATURE_ID = "navajoNature";

//    private String scriptPath = "navajo-tester/auxilary/scripts";
//
//    private String compilePath = "navajo-tester/auxilary/compiled";
//    private String tmlPath = "navajo-tester/auxilary/tml";

	public static final String PREF_JVM_PARAMETERS_KEY = "jvmParam";
	public static final String PREF_JVM_CLASSPATH_KEY = "jvmClasspath";
	public static final String PREF_JVM_BOOTCLASSPATH_KEY = "jvmBootClasspath";
//    private NavajoBrowser navajoBrowser = null;

		
	private NavajoNature myNature = null;
	private NavajoBuilder myBuilder = null;
	
	private ClientInterface localClient = null;
//    private String getPreferenceStore().;
	/**
	 * The constructor.
	 */
	public NavajoScriptPluginPlugin() {
		super();
//		JavaCore jc = JavaCore.getJavaCore();
		System.err.println("Started NavajoScriptPlugin at: "+new Date());
	    System.setProperty("com.dexels.navajo.DocumentImplementation","com.dexels.navajo.document.nanoimpl.NavajoFactoryImpl");
	    System.setProperty("com.dexels.navajo.propertyMap", "com.dexels.navajo.studio.script.plugin.propertymap");
//	    System.setProperty("javax.xml.parsers.DocumentBuilderFactory", "org.apache.xerces.jaxp.DocumentBuilderFactoryImpl");
	    
	    plugin = this;
	}

	   public Launch runNavajo(String className, IFile scriptFile) throws CoreException {
	       return runNavajo(className, scriptFile,null);
	   }	

	   private Launch currentFunctionLaunch = null;
	   private Launch currentScriptLaunch = null;
	   

    public Launch runNavajo(String classRunner, IFile scriptFile, IFile sourceTml) throws CoreException {
        System.err.println("Running file with path: "+scriptFile.getFullPath());
        String name = null;
        if ("tml".equals(scriptFile.getFileExtension())) {
            System.err.println("Tml file found.");
            name = getScriptNameFromTml(sourceTml, scriptFile.getProject());
            scriptFile = getScriptFolder(scriptFile.getProject()).getFile(name);
            System.err.println("Resolved to script: "+scriptFile.getLocation().toString());
        } else {
            name = getScriptName(scriptFile, scriptFile.getProject());
        }
        
        final IFolder tml = getTmlFolder(scriptFile.getProject());
        IFile ii = tml.getFile(name+".tml");
        String location = null;
        if (sourceTml!=null) {
            location = sourceTml.getRawLocation().toString();
            System.err.println("Source TML FOUND: "+location);
        }
        
        final IProject project = tml.getProject();
        final String scriptName = name;
        
		   Job job = new Job("Waiting for process to end..") {
	            protected IStatus run(IProgressMonitor monitor) {
	                while (!currentScriptLaunch.isTerminated()) {
                     try {
                         Thread.sleep(1000);
                     } catch (InterruptedException e) {
                     }
                     if (monitor.isCanceled()) {
                         System.err.println("Run cancelled!");
                         return Status.CANCEL_STATUS;
                     }
	                }
	                System.err.println("Cool. It finished");
	                try {
                     NavajoScriptPluginPlugin.getDefault().getTmlFolder(project).refreshLocal(IResource.DEPTH_INFINITE, monitor);
                 } catch (CoreException e1) {
                     // TODO Auto-generated catch block
                     e1.printStackTrace();
                 }
	                final IFile f = tml.getFile(scriptName+".tml");
	                
	                NavajoScriptPluginPlugin.getDefault().openInEditor(f);
	                
	                if (f.exists()) {
		                System.err.println("And the tmlfile exists");
		                try {
			                f.refreshLocal(IResource.DEPTH_INFINITE, monitor);
		                    InputStream fis = f.getContents();
                         Navajo n = NavajoFactory.getInstance().createNavajo(fis);
//                         if (NavajoScriptPluginPlugin.getDefault().getNavajoView()!=null) {
//                             NavajoScriptPluginPlugin.getDefault().getNavajoView().setNavajo(n,f);
//                         }
                         
                         final IEditorDescriptor edId = Workbench.getInstance().getEditorRegistry().getDefaultEditor(f.getName());
                         final IEditorInput iei = new FileEditorInput(f);
                         
                         if (iei!=null) {
                             System.err.println("Ja lekker: "+iei.getName());
                         }
                           System.err.println("Krijg nou wat: ");                                
                                  Workbench.getInstance().getDisplay().syncExec(new Runnable(){
                                     public void run() {
                                         try {
                                             IWorkbenchWindow ww = Workbench.getInstance().getActiveWorkbenchWindow();
                                                 IWorkbenchPage wp = ww.getActivePage();
                                                 String is = edId.getId();
                                                 System.err.println("is: "+is);
                                                 wp.openEditor(iei,is);
                                             } catch (PartInitException e2) {
                                                 e2.printStackTrace();
                                             }
                                     }});
    		                } catch (CoreException e) {
  		                e.printStackTrace();
                     }
	                return Status.OK_STATUS;
	            } else {
	                return Status.CANCEL_STATUS;

	            }
	        }
		   };
        
        
        return runNavajoBootStrap(classRunner,true,scriptFile,name, location,job);
    }


//	private String[] addPreferenceProjectListToClasspath(String[] previouscp) {
//	    List projectsList = NavajoScriptPluginPlugin.getDefault().getProjectsInCP();
//    	System.err.println("Adding projectLIST: "+projectsList.size());
//		String[] result = previouscp;
//		Iterator it = projectsList.iterator();
//		while (it.hasNext()) {
//			try {
//				ProjectListElement ple = (ProjectListElement) it.next();
//				System.err.println("Found element: "+ple.getID());
//				result = this.addProjectToClasspath(result, ple.getID());
//			} catch (Exception e) {
//			    e.printStackTrace();
//				// nothing will be added to classpath
//			}
//		}
//
//		return result;
//	 	}

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
	
	public void resolveProject(final ArrayList outputPaths, final ArrayList current, IJavaProject jp) throws JavaModelException {
        IClasspathEntry[] ice = jp.getResolvedClasspath(true);
        current.add(jp.getOutputLocation());
               
        for (int i = 0; i < ice.length; i++) {
            if (ice[i].getEntryKind()==IClasspathEntry.CPE_PROJECT) {
//                System.err.println("Project entry found: >>"+ice[i].getPath().toString()+"<<");
//                IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(ice[i].getPath().toString());
                IProject prj = JavaPlugin.getWorkspace().getRoot().getProject(ice[i].getPath().toString());

                //TODO Check project nature first
                IJavaProject project = JavaCore.create(prj);
                resolveProject(outputPaths,current, project);
                continue;
            }
            
            current.add(ice[i].getPath());
            
//            if (ice[i].getEntryKind()==IClasspathEntry.CPE_LIBRARY) {
//                System.err.println("Adding library: "+ice[i].getPath().toString());
//                current.add(ice[i].getPath());
//                continue;
//            }
//            if (ice[i].getEntryKind()==IClasspathEntry.CPE_CONTAINER) {
//                System.err.println("Adding container: "+ice[i].getPath().toString());
//                current.add(ice[i].getPath());
//                continue;
//            }
//            if (ice[i].getEntryKind()==IClasspathEntry.CPE_SOURCE) {
//                System.err.println("Adding source: "+ice[i].getPath().toString());
//                current.add(ice[i].getPath());
//                continue;
//            }
//            
        }
	}
	
	public IFile getServerXml(IProject prj) {
		return prj.getFile(new Path(getNavajoConfigPath()+"/"+SERVER_FILE_NAME));
	    
	}
	
	public String getNavajoConfigPath() {
	    return getNavajoRootPath()+NAVAJO_CONFIG_PATH;
	}
	
	public Launch runNavajoBootStrap(String runClassName, boolean showInDebugger,IFile script, String scriptName, String sourceTmlPath, Job job) throws CoreException {
		IProject myProject = script.getProject();
	    final IFolder tml = NavajoScriptPluginPlugin.getDefault().getTmlFolder(myProject);
		IFolder scriptPath = myProject.getFolder(NavajoScriptPluginPlugin.getDefault().getScriptPath());
		IProjectNature ipn = myProject.getNature("org.eclipse.jdt.core.javanature");
		IFile file = getServerXml(myProject);
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
            JavaProject jp = (JavaProject)ipn;
            classpath = new String[] {};
            ArrayList lll = new ArrayList();
            
            ArrayList resolvedClasspath = new ArrayList();
            Set classSet = new TreeSet();
            classSet.addAll(resolvedClasspath);
            resolvedClasspath.retainAll(classSet);
            ArrayList outputPaths = new ArrayList();
            resolveProject(outputPaths,resolvedClasspath, jp);
            for (int i = 0; i < resolvedClasspath.size(); i++) {
                IPath ic = (IPath)resolvedClasspath.get(i);
                    IFile iff = ResourcesPlugin.getWorkspace().getRoot().getFile(ic);
                    if (iff!=null && iff.getRawLocation()!=null) {
                        lll.add(iff.getRawLocation().toString());
                    }
            }
            Object[] cp = lll.toArray();
            classpath = new String[cp.length];
            System.arraycopy(cp, 0, classpath, 0, cp.length);
		} else {
		    System.err.println("Not a java project?!");
//		    System.err.println(" affe >>>"+myProject.getClass());
		}
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
		currentScriptLaunch = VMLauncherUtility.runVM("Navajo inline",runClassName, classpath, bootClasspath, jvmArguments.toString(), programArguments.toString(), getSourceLocator(), isDebugMode(), showInDebugger,scriptName,myProject,job);
		return currentScriptLaunch;
	}
	public String[] getClasspath() {
	    return new String[]{};
	}

	public String[] getVmArgs(IProject myProject) {
	    return new String[]{"-Duser.dir="+myProject.getLocation().toOSString(),"-Dnavajo.user="+getUsername(),"-Dnavajo.password="+getPassword()};
	}
	/**
     * @return
     */
    private String getPassword() {
        return "ik";
    }

    /**
     * @return
     */
    private String getUsername() {
        return "ik";
    }

    /**
     * @return
     */
    private ISourceLocator getSourceLocator() {
        // TODO Auto-generated method stub
        return null;
    }
    public void openInEditor(final IFile f) {
        openInEditor(f,null);
    }
    
    public void openInEditor(final IFile f, final ISourceRange range) {
        Workbench.getInstance().getDisplay().syncExec(new Runnable(){
            public void run() {
                    IEditorPart[] iii = Workbench.getInstance().getActiveWorkbenchWindow().getActivePage().getEditors();
                    for (int i = 0; i < iii.length; i++) {
                        IResource res = (IResource)iii[i].getEditorInput().getAdapter(IResource.class);
                        if (res!=null) {
                            System.err.println("RESOURCE FOUND!!!!!: "+res.getFullPath().toString());
                        }
                        if (f.equals(res)) {
                            System.err.println("FOUND THE EDITOR... WILL ATTEMPT TO CLOSE IT");
                            Workbench.getInstance().getActiveWorkbenchWindow().getActivePage().closeEditor(iii[i], false);
                        }
                    }
                    IEditorDescriptor edId = Workbench.getInstance().getEditorRegistry().getDefaultEditor(f.getName());
                    if (edId==null) {
                        edId = Workbench.getInstance().getEditorRegistry().findEditor(IEditorRegistry.SYSTEM_EXTERNAL_EDITOR_ID);
                        System.err.println("No descriptor found for: "+f.getName());
                        if (edId==null) {
                            System.err.println("STILL No descriptor found for: "+f.getName()+">> "+IEditorRegistry.SYSTEM_EXTERNAL_EDITOR_ID);
                        }
                    }
                    try {
                        Workbench.getInstance().getActiveWorkbenchWindow().getActivePage().openEditor(new FileEditorInput(f), edId.getId());
                        if (range!=null) {
                            IEditorPart ied = Workbench.getInstance().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
                            if (ied!=null && ied instanceof AbstractTextEditor) {
                                AbstractTextEditor cue = (AbstractTextEditor)ied;
//                                cue.
                                cue.selectAndReveal(range.getOffset(), range.getLength());
                            }
                            
//                            System.err.println("CLASSSSSS: "+ccc);
                            
                        }
                        
                    } catch (PartInitException e) {
                        // oh dear
                        e.printStackTrace();
                    }
             }});
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
//	public String getNavajoJRE() {
//		IPreferenceStore pref =	NavajoScriptPluginPlugin.getDefault().getPreferenceStore();
//		String result = pref.getString(NAVAJO_PREF_JRE_KEY);
//		if (result.equals("")) {
//	      result = JavaRuntime.getDefaultVMInstall().getId();
//		}
//		return result;
//	}
	public void setup() {
	    System.err.println("Setting up...");
	    IPreferenceStore ips = NavajoScriptPluginPlugin.getDefault().getPreferenceStore();
//	    NavajoClientFactory.createDefaultClient().setUsername(ips.getString(NavajoPreferencePage.P_NAVAJO_USERNAME));
//		NavajoClientFactory.createDefaultClient().setPassword(ips.getString(NavajoPreferencePage.P_NAVAJO_PASSWORD));
//		NavajoClientFactory.createDefaultClient().setServerUrl(ips.getString(NavajoPreferencePage.P_NAVAJO_SERVERURL));
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
		String[] prefParams = StringUtil.cutString(NavajoScriptPluginPlugin.getDefault().getJvmParamaters(), System.getProperty("path.separator"));
		return StringUtil.concat(previous, prefParams);
	}



    private String[] addPreferenceJvmToClasspath(String[] previous) {
		String[] prefClasspath = StringUtil.cutString(NavajoScriptPluginPlugin.getDefault().getJvmClasspath(),System.getProperty("path.separator"));
		return StringUtil.concatUniq(previous, prefClasspath);
	}




    private String[] addPreferenceJvmToBootClasspath(String[] previous) {
		String[] prefBootClasspath = StringUtil.cutString(NavajoScriptPluginPlugin.getDefault().getJvmBootClasspath(),System.getProperty("path.separator"));
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

//
//	public void setProjectsInCP(List projectsInCP) {
//		this.saveProjectsToPreferenceStore(projectsInCP, NAVAJO_PREF_PROJECTSINCP_KEY);
//	}
//
//	public List getProjectsInCP() {
//		return this.readProjectsFromPreferenceStore(NAVAJO_PREF_PROJECTSINCP_KEY);	
//	}	
//	
//	public void setProjectsInSourcePath(List projectsInCP) {
//		this.saveProjectsToPreferenceStore(projectsInCP, NAVAJO_PREF_PROJECTSINSOURCEPATH_KEY);
//	}
//
//	public List getProjectsInSourcePath() {
//		IPreferenceStore pref =	NavajoScriptPluginPlugin.getDefault().getPreferenceStore();
//		if(!(pref.contains(NAVAJO_PREF_PROJECTSINSOURCEPATH_KEY))) {
//			// Project list in source path should be filled to make migration from v2.1 to v2.1.1 easier
//			initProjectsInSourcePath();
//		}		
//		return this.readProjectsFromPreferenceStore(NAVAJO_PREF_PROJECTSINSOURCEPATH_KEY);	
//	}

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

//	private void initProjectsInSourcePath() {
//		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
//		IProject[] allProjects = root.getProjects();
//		
//		ArrayList tempList = new ArrayList(allProjects.length);
//		for (int i = 0; i < allProjects.length; i++) {
//			try {
//				if((allProjects[i].isOpen()) && allProjects[i].hasNature(JavaCore.NATURE_ID)) {
//					tempList.add(new ProjectListElement(allProjects[i].getNature(JavaCore.NATURE_ID).getProject()));
//				}
//			} catch (CoreException e) {
//			    NavajoScriptPluginPlugin.getDefault().log(e);
//			}
//		}
//		this.setProjectsInSourcePath(tempList);		
//	}
	
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
	
	
    public String getApplicationSetting() {
        return APPLICATION_SETTINGS_PATH;
    }

	
    
    /**
     * @return Returns the compilePath.
     */
    public String getCompilePath() {
        return getNavajoRootPath()+RELATIVE_COMPILED_PATH;
    }

    
    public String getNavajoRootPath() {
//        System.err.println("Gettin root path:   "+getPreferenceStore().getString(NavajoPreferencePage.P_NAVAJO_PATH));
        return NAVAJO_ROOT_PATH;
    }
    
    /**
     * @return Returns the scriptPath.
     */
    public String getScriptPath() {
        return getNavajoRootPath()+RELATIVE_SCRIPT_PATH;
    }
    
   
    public String getTmlPath() {
        return getNavajoRootPath()+RELATIVE_TML_PATH;
    }
    
    public IFolder getTmlFolder(IProject p) {
      return p.getFolder(getTmlPath());
    }
    public IFolder getScriptFolder(IProject p) {
        return p.getFolder(getScriptPath());
      }
 
    public IFile getScriptFile(IProject p, String path) {
        IFolder iff = p.getFolder(getScriptPath());
        IFile ifff =  iff.getFile(path+".xml");
        return ifff;
    }
 
    public IFile getCompiledScriptFile(IProject p, String path) {
         IFolder iff = p.getFolder(getCompilePath());
        IFile ifff =  iff.getFile(path+".java");
        return ifff;
    }
    public IFile getTmlFile(IProject p, String path) {
        IFolder iff = p.getFolder(getTmlPath());
        IFile ifff =  iff.getFile(path+".tml");
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

    /** @deprecated */
    public String getScriptNameFromTml(IFile tml, IProject project) {
        ArrayList al = new ArrayList();
        StringBuffer sb = new StringBuffer();
        IFolder tmlDir  = project.getFolder(getTmlPath());
        IResource ir  = tml;
        while (ir!=null || !ir.getFullPath().equals(tmlDir.getFullPath())) {
            if (ir==null) {
                return "Straaange.. I have not seen this script before...";
            }
            al.add(ir.getName());
            ir = ir.getParent();
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

    public String getScriptNameFromFolder(IFile tml, IFolder folder) {
        ArrayList al = new ArrayList();
        StringBuffer sb = new StringBuffer();
//        IFolder tmlDir  = project.getFolder(getTmlPath());
        IResource ir  = tml;
        if (ir==null) {
            return "Straaange.. I have not seen this script before...";
        }
       while (!ir.getFullPath().equals(folder.getFullPath())) {
              al.add(ir.getName());
            ir = ir.getParent();
            if (ir==null) {
                break;
            }
       }
        for (int i = al.size()-1; i >= 0 ; i--) {
            String current = (String)al.get(i);
            if (i!=al.size()-1) {
                sb.append("/");
            }
            sb.append(current);
        }
        String buffer = sb.toString();
        if (buffer.endsWith(".xml") || buffer.endsWith(".tml") ) {
            return buffer.substring(0,buffer.length()-4);
        }
        if (buffer.endsWith(".java")) {
            return buffer.substring(0,buffer.length()-5);
        }
        return buffer;
    }

    
    public String getScriptNameFromResource(IFile ff) {
        IFolder parentFold = null;
        boolean isTml = ff.getFileExtension().equals("tml");
        if (isTml) {
            parentFold = getTmlFolder(ff.getProject());
        } else {
//            boolean isXml = isParentOf(ff,getScriptFolder(ff.getProject()));
              boolean isXml = ff.getFileExtension().equals("xml");
            if(isXml) {
                parentFold = getScriptFolder(ff.getProject());
            }else {
//                boolean isJava = isParentOf(ff,getCompileFolder(ff.getProject()));
                boolean isJava = ff.getFileExtension().equals("java");
                if(isJava) {
                    parentFold = getCompileFolder(ff.getProject());
                }
            }
        }
        if (parentFold==null) {
            System.err.println("Not a suitable resource: "+ff.getFullPath());
            return null;
        }
        return getScriptNameFromFolder(ff,parentFold);
    }

    public boolean isParentOf(IResource rr, IFolder ff) {
        return true;
    }
    

//    /**
//     * @param browser
//     */
//    public void setNavajoView(NavajoBrowser browser) {
//        navajoBrowser = browser;
//    }

//    public NavajoBrowser getNavajoView() {
//        return navajoBrowser;
//    }
    

    public void refreshResource(final IResource ir, IProgressMonitor monitor) throws CoreException {
       ir.refreshLocal(IResource.DEPTH_INFINITE, monitor);
    }
    
    public void deleteFile(final IFile ir, final IProgressMonitor monitor) throws CoreException {
        System.err.println("Ignoring delete");
        if (true) {
            return;
        }
        Workbench.getInstance().getDisplay().syncExec(new Runnable(){

            public void run() {
//                ResourcesPlugin.getWorkspace().get
                IEditorPart edd =  Workbench.getInstance().getActiveWorkbenchWindow().getActivePage().findEditor(new FileEditorInput(ir));
                if (edd!=null) {
                    Workbench.getInstance().getActiveWorkbenchWindow().getActivePage().closeEditor(edd, false);
                } else {
                    System.err.println("No open editor found");
                }
            }});
        
        Job job = new Job("Deleting resource..") {
            protected IStatus run(IProgressMonitor monitor) {
                try {
                    ir.delete(true, false, monitor);
                    
                } catch (CoreException e) {
                     e.printStackTrace();
                     return new Status(IStatus.ERROR,"com.dexels.plugin",-1,"Error deleting resource",e);
                }
                return Status.OK_STATUS;
            }
        };
        job.setRule(ir.getProject());
        job.schedule();

    }

    /**
     * @return
     */
    public IFolder getCompileFolder(IProject ipp) {
        IFolder iff = ipp.getFolder(getCompilePath());
        return iff;
    }

    public ArrayList searchForExtendingClasses(IProject ipp, String interfaceName, IProgressMonitor monitor) throws CoreException {
	    return searchForClasses(ipp, interfaceName, monitor, IJavaSearchConstants.REFERENCES);
	}
    
    public ArrayList searchForImplementingClasses(IProject ipp, String interfaceName, IProgressMonitor monitor) throws CoreException {
	    return searchForClasses(ipp, interfaceName, monitor, IJavaSearchConstants.IMPLEMENTORS);
	}

    private ArrayList searchForClasses(IProject ipp, String interfaceName, IProgressMonitor monitor, int type) throws CoreException {
       	IProjectNature ipn = ipp.getNature("org.eclipse.jdt.core.javanature");
    	if (ipn instanceof JavaProject) {
           	final ArrayList matches = new ArrayList();
           JavaProject jp = (JavaProject)ipn;
            SearchEngine s = new SearchEngine();
            IType itt = jp.findType(interfaceName);
            if (itt==null) {
                return matches;
            }
            SearchPattern pat = SearchPattern.createPattern(itt.getPrimaryElement(),type);
            String[] names  = jp.getRequiredProjectNames();
           // REFACTOR TO RECURSIVELY GET ALL PROJECTS:::\
          // Also, will not return indirectly implemented classes :-/
            IJavaElement[] prjs = new IJavaElement[names.length+1]; 
            for (int i = 0; i < names.length; i++) {
                IProject currentPrj = ResourcesPlugin.getWorkspace().getRoot().getProject(names[i]);
               	IProjectNature rrr = currentPrj.getNature("org.eclipse.jdt.core.javanature");
               	prjs[i] = (IJavaElement)rrr;
            }
           	prjs[names.length] = jp;
            s.search(pat, new SearchParticipant[]{SearchEngine.getDefaultSearchParticipant()}, s.createJavaSearchScope(prjs),  new SearchRequestor(){
    		     public void acceptSearchMatch(SearchMatch match) throws CoreException {
                    matches.add(match);
    		     }}, monitor);
            return matches;
        }
    	return null;
	}

    public IProject getCurrentProject() {
//		ISelection is = Workbench.getInstance().getActiveWorkbenchWindow().getActivePage(). getActivePart().;
//		if (is==null) {
//            System.err.println("Null SELECTION! OH DEAR!");
//        } else {
//    		System.err.println("Selection CLASS: "+is.getClass());
//
//        }
//		if (!(is instanceof IStructuredSelection)) {
//		    System.err.println("oh dear");
//		    IFile iff = (IFile)Workbench.getInstance().getActiveWorkbenchWindow().getActivePage().getActiveEditor().getEditorInput().getAdapter(IFile.class);
//		    if (iff!=null) {
//		        System.err.println("Resource found");
//                return iff.getProject();
//            }
//		} else {
//      		
//      		IStructuredSelection iss = (IStructuredSelection)is;
//      		Object first = iss.getFirstElement();
//      		if (first instanceof IResource) {
//                  IResource irr = (IResource)first;
//                  if (irr instanceof IProject) {
//                      
//                      return (IProject)irr;
//                  }
//                  return irr.getProject();
//              }
//              
//          }
		// oh, come on:
		return ResourcesPlugin.getWorkspace().getRoot().getProject("TestNavajo");
//		return null;
    }
    
    public static ArrayList getProjectsByNature(String nature) throws CoreException {
		ArrayList al = new ArrayList();
        IProject[] pp = ResourcesPlugin.getWorkspace().getRoot().getProjects();
        for (int i = 0; i < pp.length; i++) {
            if (pp[i].isOpen() && pp[i].hasNature(nature)) {
                al.add(pp[i]);
            }
        }
        return al;
    }

    public static ArrayList getNavajoProjects() throws CoreException {
        return getProjectsByNature(NAVAJO_NATURE);
    }
    
    public static ArrayList getJavaProjects() throws CoreException {
        ArrayList l =  getProjectsByNature("org.eclipse.jdt.core.javanature");
        System.err.println("L: "+l);
        return l;
    }
    
    
    public Launch getCurrentFunctionLaunch() {
        return currentFunctionLaunch;
    }
    public void setCurrentFunctionLaunch(Launch currentFunctionLaunch) {
        this.currentFunctionLaunch = currentFunctionLaunch;
    }
    
    public void insertIntoCurrentTextEditor(String s) {
        IEditorPart activeEditor = Workbench.getInstance().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
        if (activeEditor instanceof ITextEditor) {
            ITextEditor ite = (ITextEditor)activeEditor;
            IEditorInput ie = activeEditor.getEditorInput();
            IDocumentProvider idp = ite.getDocumentProvider();
            IDocument id = idp.getDocument(ie);
            IRegion ir = ite.getHighlightRange();
//            ite.g
            TextEdit ied;
            if (ir!=null) {
                ied = new ReplaceEdit(ir.getOffset(),ir.getLength(),s);
            } else {
                ied = new InsertEdit(0,s);
            }
            try {
                ied.apply(id);
            } catch (MalformedTreeException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            } catch (BadLocationException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
    }

}
