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
import org.eclipse.ui.ide.*;
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
import com.dexels.navajo.studio.script.plugin.views.*;

import java.io.*;
import java.io.File;
import java.util.*;
import java.util.Date;

import javax.xml.transform.dom.*;

/**
 * The main plugin class to be used in the desktop.
 */
public class NavajoScriptPluginPlugin extends AbstractUIPlugin {
    //The shared instance.
    private static NavajoScriptPluginPlugin plugin;

    //Resource bundle.
    private ResourceBundle resourceBundle;

    //	public static final String NAVAJO_PREF_JRE_KEY = "navajoJRE";
    //	public static final String NAVAJO_PREF_JVM_PARAMETERS_KEY =
    // "jvmParameters";
    //	public static final String NAVAJO_PREF_JVM_CLASSPATH_KEY =
    // "jvmClasspath";
    //	public static final String NAVAJO_PREF_JVM_BOOTCLASSPATH_KEY =
    // "jvmBootClasspath";
    //	public static final String NAVAJO_PREF_PROJECTSINCP_KEY = "projectsInCp";
    //	public static final String NAVAJO_PREF_PROJECTSINSOURCEPATH_KEY =
    // "projectsInSourcePath";
    //	public static final String NAVAJO_PREF_DEBUGMODE_KEY = "navajoDebugMode";

    public static final String NAVAJO_NATURE = "com.dexels.plugin.NavajoNature";

    public static final String NAVAJO_REPOSITORY_INTERFACE = "com.dexels.navajo.server.Repository";

    public static final String NAVAJO_ADAPTER_INTERFACE = "com.dexels.navajo.mapping.Mappable";

    public static final String NAVAJO_FUNCTION_CLASS = "com.dexels.navajo.parser.FunctionInterface";

    public static final String NAVAJO_ROOT_PATH = "navajo";


    public static final String RELATIVE_COMPILED_PATH = "/compiled";

    public static final String RELATIVE_SCRIPT_PATH = "/scripts";

    public static final String RELATIVE_TML_PATH = "/tml";

    public static final String NAVAJO_CONFIG_PATH = "/config";

    public static final String NAVAJO_AUXILARY = "/auxilary";

    
    //	    public static final String NAVAJO_SERVER_PATH =
    // "navajo-tester/auxilary/config";
    public static final String SERVER_FILE_NAME = "server.xml";

    public static final String SQL_FILE_NAME = "sqlmap.xml";


    private static final String PERSISTENCE_FILE_NAME = "persistence-manager.xml";

    public static final String APPLICATION_SETTINGS_PATH = "src/application.properties";

    public static final String NATURE_ID = "navajoNature";

    public static final String NAVAJO_RUNNER_CLASS = "com.dexels.navajo.client.impl.NavajoRunner";

    //    private String scriptPath = "navajo-tester/auxilary/scripts";
    //
    //    private String compilePath = "navajo-tester/auxilary/compiled";
    //    private String tmlPath = "navajo-tester/auxilary/tml";

    public static final String PREF_JVM_PARAMETERS_KEY = "jvmParam";

    public static final String PREF_JVM_CLASSPATH_KEY = "jvmClasspath";

    public static final String PREF_JVM_BOOTCLASSPATH_KEY = "jvmBootClasspath";
    

    // These are related with Markers. Don't remember what they mean.
    public static final String KEY = "key";
    public static final String VIOLATION = "violation";
    

    //    private NavajoBrowser navajoBrowser = null;
    private Launch currentFunctionLaunch = null;

    private Launch currentScriptLaunch = null;

    private NavajoNature myNature = null;

    private NavajoBuilder myBuilder = null;

    private ClientInterface localClient = null;

    private TmlViewer currentTmlViewer;

    //    private String getPreferenceStore().;
    /**
     * The constructor.
     */
    public NavajoScriptPluginPlugin() {
        super();
        //		JavaCore jc = JavaCore.getJavaCore();
        System.err.println("Started NavajoScriptPlugin at: " + new Date());
        System.setProperty("com.dexels.navajo.DocumentImplementation", "com.dexels.navajo.document.nanoimpl.NavajoFactoryImpl");
        System.setProperty("com.dexels.navajo.propertyMap", "com.dexels.navajo.studio.script.plugin.propertymap");
        //	    System.setProperty("javax.xml.parsers.DocumentBuilderFactory",
        // "org.apache.xerces.jaxp.DocumentBuilderFactoryImpl");

        plugin = this;
    }

    public Launch runNavajo(String className, IFile scriptFile) throws CoreException {
        return runNavajo(className, scriptFile, null);
    }

    public Launch runNavajo(String classRunner, IFile scriptFile, IFile sourceTml) throws CoreException {
        System.err.println("Running file with path: " + scriptFile.getFullPath());
        String name = null;
        if ("tml".equals(scriptFile.getFileExtension())) {
            System.err.println("Tml file found.");
            name = getScriptNameFromTml(sourceTml, scriptFile.getProject());
            scriptFile = getScriptFolder(scriptFile.getProject()).getFile(name);
            System.err.println("Resolved to script: " + scriptFile.getLocation().toString());
        } else {
            name = getScriptName(scriptFile, scriptFile.getProject());
        }

        final IFolder tml = getTmlFolder(scriptFile.getProject());
        IFile ii = tml.getFile(name + ".tml");
        String location = null;
        String relTmlLocation = null;
        if (sourceTml != null) {
            location = sourceTml.getRawLocation().toString();
            IPath sourceTmlPath = sourceTml.getFullPath();
            IPath relPath = sourceTmlPath.removeFirstSegments(tml.getFullPath().segmentCount());
            relTmlLocation = relPath.toString();
            System.err.println("Source TML FOUND: " + location);
            System.err.println("ResolvedPAth: "+relTmlLocation);
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
                final IFile f = tml.getFile(scriptName + ".tml");
//                try {
//                    NavajoScriptPluginPlugin.getDefault().getTmlFolder(project).refreshLocal(IResource.DEPTH_INFINITE, monitor);
//                } catch (CoreException e1) {
//                     e1.printStackTrace();
//                }
                System.err.println("check this..."); 
//                f.refreshLocal(0, monitor);
//                NavajoScriptPluginPlugin.getDefault().openInEditor(f);

                if (f.exists()) {
//                    System.err.println("And the tmlfile exists");
                    try {
                        f.refreshLocal(IResource.DEPTH_INFINITE, monitor);
                        
                        InputStream contents = f.getContents();

                        if (currentTmlViewer!=null) {
//	                          Navajo n = NavajoFactory.getInstance().createNavajo(contents);
//	                          try {
//	                              if (contents!=null) {
//	                                  contents.close();
//	                               
//	                           }
//	                          } catch (IOException e) {
//	                                e.printStackTrace();
//	                                throw new CoreException(Status.CANCEL_STATUS);
//	                          }
                            showTml(f);
                        } else {
                            openTmlViewer();
                            showTml(f);
                               }
//                        
//                        InputStream fis = f.getContents();
//                        final IEditorDescriptor edId = Workbench.getInstance().getEditorRegistry().getDefaultEditor(f.getName());
//                        final IEditorInput iei = new FileEditorInput(f);
//
//                        if (iei != null) {
//                            System.err.println("Ja lekker: " + iei.getName());
//                        }
//                        System.err.println("Krijg nou wat: ");
//                        Workbench.getInstance().getDisplay().syncExec(new Runnable() {
//                            public void run() {
//                                try {
//                                    IWorkbenchWindow ww = Workbench.getInstance().getActiveWorkbenchWindow();
//                                    IWorkbenchPage wp = ww.getActivePage();
//                                    String is = edId.getId();
//                                    System.err.println("is: " + is);
//                                    wp.openEditor(iei, is);
//                                } catch (PartInitException e2) {
//                                    e2.printStackTrace();
//                                }
//                            }
//                        });
                    } catch (CoreException e) {
                        e.printStackTrace();
                    }
                    return Status.OK_STATUS;
                } else {
                    return Status.CANCEL_STATUS;

                }
            }
        };

        return runNavajoBootStrap(classRunner, true, scriptFile, name, location, job, relTmlLocation);
    }

    //	private String[] addPreferenceProjectListToClasspath(String[] previouscp)
    // {
    //	    List projectsList =
    // NavajoScriptPluginPlugin.getDefault().getProjectsInCP();
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

    public void openTmlViewer() {
        IViewPart p =  Workbench.getInstance().getActiveWorkbenchWindow().getActivePage().findView("com.dexels.TmlViewer");
        System.err.println("p? "+p);
    }
    
    private String[] addProjectToClasspath(String[] previouscp, String projectName) throws CoreException {
        IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
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
     * Launch a new JVM running Tomcat Main class Set classpath, bootclasspath
     * and environment variable
     */

    public void resolveProject(final ArrayList outputPaths, final ArrayList current, IJavaProject jp) throws JavaModelException {
        IClasspathEntry[] ice = jp.getResolvedClasspath(true);
        current.add(jp.getOutputLocation());

        for (int i = 0; i < ice.length; i++) {
            if (ice[i].getEntryKind() == IClasspathEntry.CPE_PROJECT) {
                //                System.err.println("Project entry found:
                // >>"+ice[i].getPath().toString()+"<<");
                //                IProject project =
                // ResourcesPlugin.getWorkspace().getRoot().getProject(ice[i].getPath().toString());
                IProject prj = JavaPlugin.getWorkspace().getRoot().getProject(ice[i].getPath().toString());

                //TODO Check project nature first
                IJavaProject project = JavaCore.create(prj);
                resolveProject(outputPaths, current, project);
                continue;
            }

            current.add(ice[i].getPath());

            //            if (ice[i].getEntryKind()==IClasspathEntry.CPE_LIBRARY) {
            //                System.err.println("Adding library:
            // "+ice[i].getPath().toString());
            //                current.add(ice[i].getPath());
            //                continue;
            //            }
            //            if (ice[i].getEntryKind()==IClasspathEntry.CPE_CONTAINER) {
            //                System.err.println("Adding container:
            // "+ice[i].getPath().toString());
            //                current.add(ice[i].getPath());
            //                continue;
            //            }
            //            if (ice[i].getEntryKind()==IClasspathEntry.CPE_SOURCE) {
            //                System.err.println("Adding source:
            // "+ice[i].getPath().toString());
            //                current.add(ice[i].getPath());
            //                continue;
            //            }
            //            
        }
    }

    public IFile getServerXml(IProject prj) {
        return prj.getFile(new Path(getNavajoConfigPath(prj) + "/" + SERVER_FILE_NAME));
    }

    public IFile getSqlXml(IProject prj) {
        return prj.getFile(new Path(getNavajoConfigPath(prj) + "/" + SQL_FILE_NAME));
    }

    public IFolder getNavajoConfigFolder(IProject prj) {
        return prj.getFolder(new Path(getNavajoConfigPath(prj)));
    }

    public String getNavajoConfigPath(IProject prj) {
        return getNavajoRootPath(prj) + NAVAJO_AUXILARY + NAVAJO_CONFIG_PATH;
    }

    public Launch runNavajoBootStrap(String runClassName, boolean showInDebugger, IFile script, String scriptName, String sourceTmlPath, Job job, String relativeTmlLocation)
            throws CoreException {
        IProject myProject = script.getProject();
        final IFolder tml = NavajoScriptPluginPlugin.getDefault().getTmlFolder(myProject);
        IFolder scriptPath = myProject.getFolder(NavajoScriptPluginPlugin.getDefault().getScriptPath(myProject));
        IProjectNature ipn = myProject.getNature("org.eclipse.jdt.core.javanature");
        IFile file = getServerXml(myProject);
        System.err.println("Raw location: " + myProject.getRawLocation());

        if (ipn != null) {
            System.err.println("Java nature found: " + ipn.getClass());

        }
        String serverXml = file.getRawLocation().toString();

        String[] prgArgs;
        if (sourceTmlPath == null) {
            prgArgs = new String[] { serverXml, scriptName, tml.getRawLocation().toString() };
        } else {
            prgArgs = new String[] { serverXml, scriptName, tml.getRawLocation().toString(), sourceTmlPath, relativeTmlLocation };
        }
        String[] classpath = new String[0];
        if (ipn instanceof JavaProject) {
            JavaProject jp = (JavaProject) ipn;
            classpath = new String[] {};
            ArrayList lll = new ArrayList();

            ArrayList resolvedClasspath = new ArrayList();
            Set classSet = new TreeSet();
            classSet.addAll(resolvedClasspath);
            resolvedClasspath.retainAll(classSet);
            ArrayList outputPaths = new ArrayList();
            resolveProject(outputPaths, resolvedClasspath, jp);
            for (int i = 0; i < resolvedClasspath.size(); i++) {
                IPath ic = (IPath) resolvedClasspath.get(i);
                try {
                    IFile iff = ResourcesPlugin.getWorkspace().getRoot().getFile(ic);
                    if (iff != null && iff.getRawLocation() != null) {
                        String cloc = iff.getRawLocation().toString();
                        if (!lll.contains(cloc)) {
                            lll.add(cloc);
                        }
                    }
                } catch (RuntimeException e) {
                    e.printStackTrace();
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
            System.err.println("vmARG: " + vmArgs[i]);
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
        currentScriptLaunch = VMLauncherUtility.runVM("Navajo inline", runClassName, classpath, bootClasspath, jvmArguments.toString(),
                programArguments.toString(), getSourceLocator(), isDebugMode(), showInDebugger, scriptName, myProject, job);
        return currentScriptLaunch;
    }

    public String[] getClasspath() {
        return new String[] {};
    }

    public String[] getVmArgs(IProject myProject) {
        return new String[] { "-Duser.dir=" + myProject.getLocation().toOSString(), "-Dnavajo.user=" + getUsername(),
                "-Dnavajo.password=" + getPassword() };
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
        return null;
    }

    public void openInEditor(final IFile f) {
        openInEditor(f, null);
    }
    
    public void closeEditorsWithExtension(IEditorPart exclude, String extension) {
        IEditorPart[] iii = Workbench.getInstance().getActiveWorkbenchWindow().getActivePage().getEditors();
        for (int i = 0; i < iii.length; i++) {
            IResource res = (IResource) iii[i].getEditorInput().getAdapter(IResource.class);
              	if (res==null) {
              	    continue;
                }
              	if (iii[i]==exclude) {
                    continue;
                }
              	if (res.getFileExtension()==null) {
              	    continue;
              	}
                if (res.getFileExtension().equals(extension)) {
                    System.err.println("FOUND AN EDITOR WITH THE SAME EXTENSION!");
                    Workbench.getInstance().getActiveWorkbenchWindow().getActivePage().closeEditor(iii[i], false);
                    return;
                }
            
              
        }
      
    }
    
     public void openInEditor(final IFile f, final ISourceRange range) {
        if (f==null || !f.exists()) {
            System.err.println("Can not open: Null file or non existent.");
            return;
        }
         Workbench.getInstance().getDisplay().syncExec(new Runnable() {
            public void run() {
                boolean exclusiveForExtension = false;
                System.err.println("AAP, exclusive: "+f.getFileExtension());
                
                if (f.getFileExtension().equals(".tml")) {
                    System.err.println("Yes, exclusive: "+f.getFileExtension());
                    exclusiveForExtension = true;
                }
//                IEditorPart[] iii = Workbench.getInstance().getActiveWorkbenchWindow().getActivePage().getEditors();
//                for (int i = 0; i < iii.length; i++) {
//                    IResource res = (IResource) iii[i].getEditorInput().getAdapter(IResource.class);
//                      if (f.equals(res)) {
//                        System.err.println("FOUND THE EDITOR... WILL ATTEMPT TO CLOSE IT");
//                        Workbench.getInstance().getActiveWorkbenchWindow().getActivePage().closeEditor(iii[i], false);
//                    } else {
//                        if (exclusiveForExtension && res.getFileExtension().equals(f.getFileExtension())) {
//                            System.err.println("FOUND AN EDITOR WITH THE SAME EXTENSION!");
//                            Workbench.getInstance().getActiveWorkbenchWindow().getActivePage().closeEditor(iii[i], false);
//                            
//                        }
//                    }
//                      
//                }
                IEditorDescriptor edId = Workbench.getInstance().getEditorRegistry().getDefaultEditor(f.getName());
                if (edId == null) {
                    edId = Workbench.getInstance().getEditorRegistry().findEditor(IEditorRegistry.SYSTEM_EXTERNAL_EDITOR_ID);
                    System.err.println("No descriptor found for: " + f.getName());
                    if (edId == null) {
                        System.err.println("STILL No descriptor found for: " + f.getName() + ">> " + IEditorRegistry.SYSTEM_EXTERNAL_EDITOR_ID);
                    }
                }
                try {
                    Workbench.getInstance().getActiveWorkbenchWindow().getActivePage().openEditor(new FileEditorInput(f), edId.getId());
                    if (range != null) {
                        IEditorPart ied = Workbench.getInstance().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
                        if (ied != null && ied instanceof AbstractTextEditor) {
                            AbstractTextEditor cue = (AbstractTextEditor) ied;
                            //                                cue.
                            cue.selectAndReveal(range.getOffset(), range.getLength());
                        }

                        //                            System.err.println("CLASSSSSS: "+ccc);

                    }

                } catch (PartInitException e) {
                    // oh dear
                    e.printStackTrace();
                }
            }
        });
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
    //		IPreferenceStore pref =
    // NavajoScriptPluginPlugin.getDefault().getPreferenceStore();
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
     * Returns the string from the plugin's resource bundle, or 'key' if not
     * found.
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
        String[] prefClasspath = StringUtil.cutString(NavajoScriptPluginPlugin.getDefault().getJvmClasspath(), System.getProperty("path.separator"));
        return StringUtil.concatUniq(previous, prefClasspath);
    }

    private String[] addPreferenceJvmToBootClasspath(String[] previous) {
        String[] prefBootClasspath = StringUtil.cutString(NavajoScriptPluginPlugin.getDefault().getJvmBootClasspath(), System
                .getProperty("path.separator"));
        return StringUtil.concatUniq(previous, prefBootClasspath);
    }

    public String getJvmParamaters() {
        IPreferenceStore pref = NavajoScriptPluginPlugin.getDefault().getPreferenceStore();
        return pref.getString(PREF_JVM_PARAMETERS_KEY);
    }

    public String getJvmClasspath() {
        IPreferenceStore pref = NavajoScriptPluginPlugin.getDefault().getPreferenceStore();
        return pref.getString(PREF_JVM_CLASSPATH_KEY);
    }

    public String getJvmBootClasspath() {
        IPreferenceStore pref = NavajoScriptPluginPlugin.getDefault().getPreferenceStore();
        return pref.getString(PREF_JVM_BOOTCLASSPATH_KEY);
    }

    //
    //	public void setProjectsInCP(List projectsInCP) {
    //		this.saveProjectsToPreferenceStore(projectsInCP,
    // NAVAJO_PREF_PROJECTSINCP_KEY);
    //	}
    //
    //	public List getProjectsInCP() {
    //		return
    // this.readProjectsFromPreferenceStore(NAVAJO_PREF_PROJECTSINCP_KEY);
    //	}
    //	
    //	public void setProjectsInSourcePath(List projectsInCP) {
    //		this.saveProjectsToPreferenceStore(projectsInCP,
    // NAVAJO_PREF_PROJECTSINSOURCEPATH_KEY);
    //	}
    //
    //	public List getProjectsInSourcePath() {
    //		IPreferenceStore pref =
    // NavajoScriptPluginPlugin.getDefault().getPreferenceStore();
    //		if(!(pref.contains(NAVAJO_PREF_PROJECTSINSOURCEPATH_KEY))) {
    //			// Project list in source path should be filled to make migration from
    // v2.1 to v2.1.1 easier
    //			initProjectsInSourcePath();
    //		}
    //		return
    // this.readProjectsFromPreferenceStore(NAVAJO_PREF_PROJECTSINSOURCEPATH_KEY);
    //	}

    static List readProjectsFromPreferenceStore(String keyInPreferenceStore) {
        IPreferenceStore pref = NavajoScriptPluginPlugin.getDefault().getPreferenceStore();
        String stringList = pref.getString(keyInPreferenceStore);

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
    //				if((allProjects[i].isOpen()) &&
    // allProjects[i].hasNature(JavaCore.NATURE_ID)) {
    //					tempList.add(new
    // ProjectListElement(allProjects[i].getNature(JavaCore.NATURE_ID).getProject()));
    //				}
    //			} catch (CoreException e) {
    //			    NavajoScriptPluginPlugin.getDefault().log(e);
    //			}
    //		}
    //		this.setProjectsInSourcePath(tempList);
    //	}

    static void saveProjectsToPreferenceStore(List projectList, String keyInPreferenceStore) {
        IPreferenceStore pref = NavajoScriptPluginPlugin.getDefault().getPreferenceStore();
        StringBuffer buf = new StringBuffer();
        Iterator it = projectList.iterator();
        while (it.hasNext()) {
            ProjectListElement each = (ProjectListElement) it.next();
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
    public String getCompilePath(IProject prj) {
        return getNavajoRootPath(prj) + NAVAJO_AUXILARY + RELATIVE_COMPILED_PATH;
    }

    
    public String getNavajoRootPath(IProject prj) {
        IFile iff = prj.getFile(".navajoroot");
        InputStream iss = null;
        if (iff==null || !iff.exists()) {
            return NAVAJO_ROOT_PATH;
        }
        try {
            iss = iff.getContents();
            BufferedReader isr = new BufferedReader(new InputStreamReader(iss));
            String rr = isr.readLine();
              return rr;
        } catch (CoreException e) {
               e.printStackTrace();
               return NAVAJO_ROOT_PATH;
       } catch (IOException e) {
            e.printStackTrace();
            return NAVAJO_ROOT_PATH;
      } finally {
          try {
            iss.close();
        } catch (IOException e1) {
             e1.printStackTrace();
        }
      }

    }

    /**
     * @return Returns the scriptPath.
     */
    public String getScriptPath(IProject prj) {
        return getNavajoRootPath(prj) + NAVAJO_AUXILARY + RELATIVE_SCRIPT_PATH;
    }

    public String getTmlPath(IProject prj) {
        return getNavajoRootPath(prj) + NAVAJO_AUXILARY + RELATIVE_TML_PATH;
    }

    public IFolder getTmlFolder(IProject p) {
        return p.getFolder(getTmlPath(p));
    }

    public IFolder getScriptFolder(IProject p) {
        return p.getFolder(getScriptPath(p));
    }

    public IFile getScriptFile(IProject p, String path) {
        IFolder iff = p.getFolder(getScriptPath(p));
        IFile ifff = iff.getFile(path + ".xml");
        return ifff;
    }

    public IFile getCompiledScriptFile(IProject p, String path) {
        IFolder iff = p.getFolder(getCompilePath(p));
        IFile ifff = iff.getFile(path + ".java");
        return ifff;
    }

    public IFile getTmlFile(IProject p, String path) {
        IFolder iff = p.getFolder(getTmlPath(p));
        if (!path.endsWith(".tml")) {
            IFile ifff = iff.getFile(path + ".tml");
            return ifff;
        }
        return iff.getFile(path);

    }

    public String getScriptName(IFile script, IProject project) {
        ArrayList al = new ArrayList();
        StringBuffer sb = new StringBuffer();
        IFolder scriptDir = project.getFolder(getScriptPath(project));
        IResource ir = script;

        while (!ir.equals(scriptDir)) {
            if (ir == null) {
                return "Straaange.. I have not seen this script before...";
            }
            al.add(ir.getName());
            ir = ir.getParent();
        }
        for (int i = al.size() - 1; i >= 0; i--) {
            String current = (String) al.get(i);
            if (i != al.size() - 1) {
                sb.append("/");
            }
            sb.append(current);
        }
        String buffer = sb.toString();
        if (buffer.endsWith(".xml")) {
            return buffer.substring(0, buffer.length() - 4);
        }
        return buffer;
    }

    /** @deprecated */
    public String getScriptNameFromTml(IFile tml, IProject project) {
        ArrayList al = new ArrayList();
        StringBuffer sb = new StringBuffer();
        IFolder tmlDir = project.getFolder(getTmlPath(project));
        IResource ir = tml;
        while (ir != null || !ir.getFullPath().equals(tmlDir.getFullPath())) {
            if (ir == null) {
                return "Straaange.. I have not seen this script before...";
            }
            al.add(ir.getName());
            ir = ir.getParent();
        }
        for (int i = al.size() - 1; i >= 0; i--) {
            String current = (String) al.get(i);
            if (i != al.size() - 1) {
                sb.append("/");
            }
            sb.append(current);
        }
        String buffer = sb.toString();
        if (buffer.endsWith(".xml")) {
            return buffer.substring(0, buffer.length() - 4);
        }
        return buffer;
    }

    public String getScriptNameFromFolder(IFile tml, IFolder folder) {
        ArrayList al = new ArrayList();
        StringBuffer sb = new StringBuffer();
        //        IFolder tmlDir = project.getFolder(getTmlPath());
        IResource ir = tml;
        if (ir == null) {
            return "Straaange.. I have not seen this script before...";
        }
        while (!ir.getFullPath().equals(folder.getFullPath())) {
            al.add(ir.getName());
            ir = ir.getParent();
            if (ir == null) {
                break;
            }
        }
        for (int i = al.size() - 1; i >= 0; i--) {
            String current = (String) al.get(i);
            if (i != al.size() - 1) {
                sb.append("/");
            }
            sb.append(current);
        }
        String buffer = sb.toString();
        if (buffer.endsWith(".xml") || buffer.endsWith(".tml")) {
            return buffer.substring(0, buffer.length() - 4);
        }
        if (buffer.endsWith(".java")) {
            return buffer.substring(0, buffer.length() - 5);
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
            if (isXml) {
                parentFold = getScriptFolder(ff.getProject());
            } else {
                //                boolean isJava =
                // isParentOf(ff,getCompileFolder(ff.getProject()));
                boolean isJava = ff.getFileExtension().equals("java");
                if (isJava) {
                    parentFold = getCompileFolder(ff.getProject());
                }
            }
        }
        if (parentFold == null) {
            System.err.println("Not a suitable resource: " + ff.getFullPath());
            return null;
        }
        return getScriptNameFromFolder(ff, parentFold);
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
        Workbench.getInstance().getDisplay().syncExec(new Runnable() {

            public void run() {
                //                ResourcesPlugin.getWorkspace().get
                IEditorPart edd = Workbench.getInstance().getActiveWorkbenchWindow().getActivePage().findEditor(new FileEditorInput(ir));
                if (edd != null) {
                    Workbench.getInstance().getActiveWorkbenchWindow().getActivePage().closeEditor(edd, false);
                } else {
                    System.err.println("No open editor found");
                }
            }
        });

        Job job = new Job("Deleting resource..") {
            protected IStatus run(IProgressMonitor monitor) {
                try {
                    ir.delete(true, false, monitor);

                } catch (CoreException e) {
                    e.printStackTrace();
                    return new Status(IStatus.ERROR, "com.dexels.plugin", -1, "Error deleting resource", e);
                }
                return Status.OK_STATUS;
            }
        };
        job.setRule(ir.getProject());
        job.schedule();

    }
    
    public void showTml(IFile tmlFile) {
        if (currentTmlViewer==null) {
            return;
        }
        try {
            InputStream is = tmlFile.getContents();
            Navajo n = NavajoFactory.getInstance().createNavajo(is);
            is.close();
            currentTmlViewer.setNavajo(n, tmlFile);
            currentTmlViewer.setFocus();
            Workbench.getInstance().getDisplay().asyncExec(new Runnable(){

                public void run() {
                    // TODO Auto-generated method stub
                    Workbench.getInstance().getActiveWorkbenchWindow().getActivePage().bringToTop(currentTmlViewer);
                }});
        } catch (CoreException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }

    /**
     * @return
     */
    public IFolder getCompileFolder(IProject ipp) {
        IFolder iff = ipp.getFolder(getCompilePath(ipp));
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
            JavaProject jp = (JavaProject) ipn;
            SearchEngine s = new SearchEngine();
            IType itt = jp.findType(interfaceName);
            if (itt == null) {
                return matches;
            }
            SearchPattern pat = SearchPattern.createPattern(itt.getPrimaryElement(), type);
            String[] names = jp.getRequiredProjectNames();
            // REFACTOR TO RECURSIVELY GET ALL PROJECTS:::\
            // Also, will not return indirectly implemented classes :-/
            IJavaElement[] prjs = new IJavaElement[names.length + 1];
            for (int i = 0; i < names.length; i++) {
                IProject currentPrj = ResourcesPlugin.getWorkspace().getRoot().getProject(names[i]);
                IProjectNature rrr = currentPrj.getNature("org.eclipse.jdt.core.javanature");
                prjs[i] = (IJavaElement) rrr;
            }
            prjs[names.length] = jp;
            s.search(pat, new SearchParticipant[] { SearchEngine.getDefaultSearchParticipant() }, s.createJavaSearchScope(prjs),
                    new SearchRequestor() {
                        public void acceptSearchMatch(SearchMatch match) throws CoreException {
                            matches.add(match);
                        }
                    }, monitor);
            return matches;
        }
        return null;
    }

    public IProject getCurrentProject() {
        //		ISelection is =
        // Workbench.getInstance().getActiveWorkbenchWindow().getActivePage().
        // getActivePart().;
        //		if (is==null) {
        //            System.err.println("Null SELECTION! OH DEAR!");
        //        } else {
        //    		System.err.println("Selection CLASS: "+is.getClass());
        //
        //        }
        //		if (!(is instanceof IStructuredSelection)) {
        //		    System.err.println("oh dear");
        //		    IFile iff =
        // (IFile)Workbench.getInstance().getActiveWorkbenchWindow().getActivePage().getActiveEditor().getEditorInput().getAdapter(IFile.class);
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
        try {
            ArrayList arr = getNavajoProjects();
            if (arr.size() > 0) {
                return (IProject) arr.get(0);
            }
            System.err.println("# of projects found: " + arr.size());
        } catch (CoreException e) {
            e.printStackTrace();
        }
        return ResourcesPlugin.getWorkspace().getRoot().getProject("TestNavajo");
        //		return null;
    }

    public static ArrayList getProjectsByNature(String nature) throws CoreException {
        ArrayList al = new ArrayList();
        IProject[] pp = ResourcesPlugin.getWorkspace().getRoot().getProjects();
        for (int i = 0; i < pp.length; i++) {
            System.err.println("Checking project: " + pp[i].getName() + " for nature: " + nature + " class: " + pp[i].getClass());
            if (pp[i].isOpen()) {
                IProjectDescription ipd = pp[i].getDescription();
                String[] aaa = ipd.getNatureIds();
                if (aaa.length == 0) {
                    System.err.println("No ids found");
                }
                for (int j = 0; j < aaa.length; j++) {
                    System.err.println("NATURE: " + aaa[j]);
                    if (aaa[j].equals(nature)) {
                        al.add(pp[i]);
                    }
                }
                //                if (ipd.hasNature(nature)) {
                //                    al.add(pp[i]);
                //                } else {
                //                    System.err.println("Did not qualify");
                //
                //                }
            } else {
                System.err.println("Not open");
            }
        }
        System.err.println("Size: " + al.size());
        return al;
    }

    public static ArrayList getNavajoProjects() throws CoreException {
        return getProjectsByNature(NAVAJO_NATURE);
    }

    public static ArrayList getJavaProjects() throws CoreException {
        ArrayList l = getProjectsByNature("org.eclipse.jdt.core.javanature");
        System.err.println("L: " + l);
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
            ITextEditor ite = (ITextEditor) activeEditor;
            IEditorInput ie = activeEditor.getEditorInput();
            IDocumentProvider idp = ite.getDocumentProvider();
            IDocument id = idp.getDocument(ie);
            IRegion ir = ite.getHighlightRange();
            //            ite.g
            TextEdit ied;
            if (ir != null) {
                ied = new ReplaceEdit(ir.getOffset(), ir.getLength(), s);
            } else {
                ied = new InsertEdit(0, s);
            }
            try {
                ied.apply(id);
            } catch (MalformedTreeException e1) {
                e1.printStackTrace();
            } catch (BadLocationException e1) {
                e1.printStackTrace();
            }
        }
    }

    /**
     * @param ip
     * @return
     */
    public IFile getPersistenceXml(IProject ip) {
        return ip.getFile(new Path(getNavajoConfigPath(ip) + "/" + PERSISTENCE_FILE_NAME));
    }

    public void copyResource(OutputStream out, InputStream in) throws CoreException {
        BufferedInputStream bin = null;
        BufferedOutputStream bout = null;
        try {
            bin = new BufferedInputStream(in);
            bout = new BufferedOutputStream(out);
            byte[] buffer = new byte[1024];
            int read;
            while ((read = bin.read(buffer)) > -1) {
                bout.write(buffer, 0, read);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new CoreException(Status.CANCEL_STATUS);
        } finally {
            try {
                if (bin != null) {
                    bin.close();
                }
                if (bout != null) {
                    bout.flush();
                    bout.close();

                }
            } catch (IOException e1) {
                e1.printStackTrace();
                throw new CoreException(Status.CANCEL_STATUS);
            }

        }
    }

    public void addNavajoNature(IProject project, boolean suppressDialogs) throws CoreException {
        System.err.println("Entering... " + project.getName());
        IProjectDescription description = project.getDescription();
        System.err.println("Found open project. Desc: " + description.getName());
        // Toggle the nature.
        List newIds = new ArrayList();
        newIds.addAll(Arrays.asList(description.getNatureIds()));
        int index = newIds.indexOf(NavajoScriptPluginPlugin.NAVAJO_NATURE);
        if (index == -1) {
            System.err.println("Adding,,,");
            newIds.add(NavajoScriptPluginPlugin.NAVAJO_NATURE);
            createDefaultServerConfigFiles(project,suppressDialogs);
        } else {
            System.err.println("Already present. Ignoring.");
            return;
        }
        project.refreshLocal(IResource.DEPTH_INFINITE, null);
        description.setNatureIds((String[]) newIds.toArray(new String[newIds.size()]));
        try {
            project.setDescription(description, null);
        } catch (CoreException e) {
            e.printStackTrace();
        }
    }
    public void removeNavajoNature(IProject project) throws CoreException {
        System.err.println("Entering REMOVE... " + project.getName());
        IProjectDescription description = project.getDescription();
        System.err.println("Found open project. Desc: " + description.getName());
        // Toggle the nature.
        List newIds = new ArrayList();
        newIds.addAll(Arrays.asList(description.getNatureIds()));
        int index = newIds.indexOf(NavajoScriptPluginPlugin.NAVAJO_NATURE);
        if (index == -1) {
            System.err.println("No navajo nature present. Ignoring.");
        } else {
            System.err.println("Removing...");
            newIds.remove(index);
        }
        description.setNatureIds((String[]) newIds.toArray(new String[newIds.size()]));
        // Save the description.
        try {
            project.setDescription(description, null);
            IFile ff = project.getFile(".navajoroot");
            if (ff.exists()) {
                ff.delete(false, false, null);
            }
        } catch (CoreException e) {
            e.printStackTrace();
        }
        project.refreshLocal(IResource.DEPTH_INFINITE, null);
    }

    private IFolder checkAndCreateFolder(IContainer parent, String name) throws CoreException {
        IFolder aux = parent.getFolder(new Path(name));
        if (!aux.exists()) {
            aux.create(true, true, null);
            return aux;
        }
//        boolean res2 = showQuestion("Folder: "+name+" exists in folder: "+parent.getFullPath()+"\nOverwrite?");
//        if (res2) {
//            aux.delete(true, null);
//            aux.create(true, true, null);
//        }
        System.err.println("Folder: "+name+" exists in folder: "+parent.getFullPath());
        return aux;
    }

    public void createDefaultServerConfigFiles(IProject ipp, boolean suppressDialogs) throws CoreException {
         IFolder navajotester = checkAndCreateFolder(ipp, getNavajoRootPath(ipp));
        IFolder auxilary = checkAndCreateFolder(navajotester, NAVAJO_AUXILARY);
        IFolder config = checkAndCreateFolder(auxilary, NAVAJO_CONFIG_PATH);
        IFolder scripts = checkAndCreateFolder(auxilary, RELATIVE_SCRIPT_PATH);
        IFolder compiled = checkAndCreateFolder(auxilary, RELATIVE_COMPILED_PATH);
        IFolder src = checkAndCreateFolder(ipp, "src");
        IFolder bin = checkAndCreateFolder(ipp, "bin");
        IFile testFile = scripts.getFile("InitTestScript.xml");
        createFile(testFile, "/com/dexels/navajo/studio/defaultres/InitTestScript.xml",suppressDialogs);
        IFile navajoStatusFile = scripts.getFile("InitNavajoStatus.xml");
        createFile(navajoStatusFile, "/com/dexels/navajo/studio/defaultres/InitNavajoStatus.xml",suppressDialogs);

        IFile iff = getServerXml(ipp);
        boolean bb = createFile(iff, "/com/dexels/navajo/studio/defaultres/server.xml",suppressDialogs);
         iff = getNavajoConfigFolder(ipp).getFile("sqlmap.xml");
        createFile(iff, "/com/dexels/navajo/studio/defaultres/sqlmap.xml",suppressDialogs);
        iff = ipp.getFile("src/application.properties");
        createFile(iff, "/com/dexels/navajo/studio/defaultres/application.properties",suppressDialogs);

        //        IPackageFragmentRoot[] ipfr = jp.getPackageFragmentRoots();
        //        for (int i = 0; i < ipfr.length; i++) {
        //            System.err.println("Packagefragment: "+ipfr[i].getPath());
        //        }
        refreshResource(ipp.getFolder("src"), null);
        refreshResource(getNavajoConfigFolder(ipp), null);
        //        jp.setOutputLocation(bin.getFullPath(), null);

        IClasspathEntry srcEntry = JavaCore.newSourceEntry(src.getFullPath());
        IClasspathEntry compiledEntry = JavaCore.newSourceEntry(compiled.getFullPath());
	    
        IProjectNature ipn = ipp.getNature("org.eclipse.jdt.core.javanature");
	    JavaProject jp = (JavaProject) ipn;

        IClasspathEntry self = jp.getClasspathEntryFor(ipp.getFullPath());
        IClasspathEntry srcc = jp.getClasspathEntryFor(src.getFullPath());
        IClasspathEntry compp = jp.getClasspathEntryFor(compiled.getFullPath());

        int extraEntries = (srcc == null ? 1 : 0) + (compp == null ? 1 : 0);

        IClasspathEntry[] current = jp.getRawClasspath();

        //        System.err.println("ORIG: "+current.length);

        // Project itself is in classpath. Evil. Bad. Must seek and destroy
        if (self != null) {
            int outCount = 0;
            IClasspathEntry[] current2 = new IClasspathEntry[current.length - 1];
            for (int i = 0; i < current.length; i++) {
                System.err.println(":: " + i + " null? " + (current[i] == null) + " len: " + current.length);
                System.err.println("AAP: " + current[i]);
                if (current[i].getPath().equals(ipp.getFullPath())) {
                } else {
                    current2[outCount++] = current[i];
                }

            }
            current = current2;
        }
        ArrayList al = new ArrayList();

        IClasspathEntry[] newCPE = null; //new
        // IClasspathEntry[current.length+extraEntries];
        IClasspathEntry[] newCPE2 = null; //new
        // IClasspathEntry[current.length+extraEntries];

        if (srcc == null) {
            newCPE = new IClasspathEntry[current.length + 1];
            System.arraycopy(current, 0, newCPE, 0, current.length);
            newCPE[current.length] = srcEntry;
        } else {
            newCPE = current;
        }
        //        System.err.println("NewCPE length: "+newCPE.length);
        if (compp == null) {
            newCPE2 = new IClasspathEntry[newCPE.length + 1];
            System.arraycopy(newCPE, 0, newCPE2, 0, newCPE.length);
            newCPE2[newCPE.length] = compiledEntry;
        } else {
            newCPE2 = newCPE;
        }
        //
        //        System.err.println("LENG: "+newCPE2.length);
        //        for (int i = 0; i < newCPE2.length; i++) {
        //            System.err.println(":: "+i+" null? "+(newCPE2[i]==null)+" len:
        // "+newCPE2.length);
        //            System.err.println("AAP: "+newCPE2[i]);
        //        }
        //        
        jp.setRawClasspath(newCPE2, bin.getFullPath(), null);
        jp.save(null, false);
        //        ipp.close(null);
        //        ipp.open(null);
        //        runNavajo(NAVAJO_RUNNER_CLASS, testFile);
    }

    private boolean createFile(IFile iff, String resourceName, boolean suppressDialogs) throws CoreException {
        if (iff.exists()) {
            boolean res;
            if(suppressDialogs) {
                res = false;
            } else {
                res = showQuestion("File: "+iff.getFullPath()+" exists. \nOverwrite?");
                
            }
            if (!res) {
                System.err.println("Skipping existing config: " + iff.getFullPath());
                return false;
            } else {
                iff.delete(true,null);
            }
            //            showI "Creating config files", "File exists. Skipping.");
        }
        InputStream in = getClass().getResourceAsStream(resourceName);
        if (in == null) {
            showWarning("Resource: " + resourceName + " is not found!");
            return false;
        }
        iff.create(in, true, null);
        return true;
    }

    public void showInfo(String message) {
        showInfo("Info from the Navajo Chief", message);
    }
    public void showWarning(String message) {
        showWarning("Beware of the Buffalo:", message);
    }
    public boolean showQuestion(String message) {
        return showQuestion("The Manitou asks you:", message);
    }
    public void showError(String message) {
        showError("Trouble in the Tipi:", message);
    }

    public boolean showConfirm(String message) {
        return showConfirm("Hmmm, are you sure?", message);
    }
    
    public void showInfo(final String title, final String message) {
        Workbench.getInstance().getDisplay().syncExec(new Runnable() {
          public void run() {
              MessageDialog.openInformation(Workbench.getInstance().getDisplay().getActiveShell(), title, message);
           }});
        
     }

    public boolean showQuestion(String title, String message) {
        return MessageDialog.openQuestion(Workbench.getInstance().getDisplay().getActiveShell(), title, message);
    }
    public boolean showConfirm(String title, String message) {
        return MessageDialog.openConfirm(Workbench.getInstance().getDisplay().getActiveShell(), title, message);
    }
   public void showError(final String title, final String message) {
       Workbench.getInstance().getDisplay().syncExec(new Runnable() {
           public void run() {
               MessageDialog.openError(Workbench.getInstance().getDisplay().getActiveShell(), title, message);
           }});
   }
   
    public void showWarning(final String title, final String message) {
        Workbench.getInstance().getDisplay().syncExec(new Runnable() {
            public void run() {
                MessageDialog.openWarning(Workbench.getInstance().getDisplay().getActiveShell(), title, message);
             }});
      }
    
    
    public boolean isScriptExisting(IProject ipp, String scriptName) {
        IFile dd = getScriptFile(ipp, scriptName);
        return dd.exists();
    }
    
    public void reportProblem(String msg, IResource loc, int violation, boolean isError, String mid, String key, int start, int end) {
        try {
            DOMSource s;
            IMarker marker = loc.createMarker(mid);
            marker.setAttribute(IMarker.MESSAGE, msg + ": ");
            marker.setAttribute(IMarker.CHAR_START, start);
            marker.setAttribute(IMarker.CHAR_END, end);
            marker.setAttribute(IMarker.SEVERITY, isError ? IMarker.SEVERITY_ERROR : IMarker.SEVERITY_WARNING);
            marker.setAttribute(KEY, key);
            //          marker.setAttribute(IMarker.TRANSIENT, true);
            marker.setAttribute(VIOLATION, violation);
            loc.refreshLocal(IResource.DEPTH_INFINITE, null);
        } catch (CoreException e) {
            e.printStackTrace();
            return;
        }
    }

    public void setTmlViewer(TmlViewer tv) {
        currentTmlViewer = tv;
    }
}
