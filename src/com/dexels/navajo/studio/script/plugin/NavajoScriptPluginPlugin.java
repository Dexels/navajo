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
import org.eclipse.jdt.internal.compiler.batch.*;
import org.eclipse.jdt.internal.core.*;
import org.eclipse.jdt.internal.ui.*;
import org.eclipse.jdt.internal.ui.javaeditor.*;
import org.eclipse.jdt.launching.*;

import org.eclipse.jface.action.*;
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
import com.dexels.navajo.document.nanoimpl.*;
import com.dexels.navajo.functions.*;
import com.dexels.navajo.mapping.compiler.*;
import com.dexels.navajo.studio.eclipse.*;
import com.dexels.navajo.studio.eclipse.prefs.*;
import com.dexels.navajo.studio.script.plugin.editors.*;
import com.dexels.navajo.studio.script.plugin.navajobrowser.*;
import com.dexels.navajo.studio.script.plugin.navajobrowser.preferences.*;
import com.dexels.navajo.studio.script.plugin.views.*;
import com.dexels.navajo.util.*;

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
    public static final String JAVA_NATURE = "org.eclipse.jdt.core.javanature";
    
    public static final String NAVAJO_REPOSITORY_INTERFACE = "com.dexels.navajo.server.Repository";

    public static final String NAVAJO_REPOSITORY_BASECLASS = "com.dexels.navajo.server.SimpleRepository";

    public static final String NAVAJO_ADAPTER_INTERFACE = "com.dexels.navajo.mapping.Mappable";

    public static final String NAVAJO_FUNCTION_CLASS = "com.dexels.navajo.parser.FunctionInterface";

    public static final String NAVAJO_ROOT_PATH = "navajo";

    public static final String RELATIVE_COMPILED_PATH = "/compiled";

    public static final String RELATIVE_SCRIPT_PATH = "/scripts";

    public static final String RELATIVE_ADAPTERS_PATH = "/adapters";

    public static final String RELATIVE_TML_PATH = "/tml";

    public static final String NAVAJO_CONFIG_PATH = "/config";

    public static final String NAVAJO_AUXILARY = "/auxilary";

    public static final String RELATIVE_METADATA_PATH = "/meta";

    public static final String COMPILED_SCRIPT_PATH = "/classes";

    public static final String SCRIPT_META_NAME = "scriptMeta.xml";

    //	    public static final String NAVAJO_SERVER_PATH =
    // "navajo-tester/auxilary/config";
    public static final String SERVER_FILE_NAME = "server.xml";

    public static final String SQL_FILE_NAME = "sqlmap.xml";

    private static final String PERSISTENCE_FILE_NAME = "persistence-manager.xml";

    public static final String APPLICATION_SETTINGS_PATH = "src/application.properties";
    
    public static final String P_NAVAJO_PATH = "navajoPathPreference";

    public static final String NAVAJO_APPLICATION_SERVERS = "navajoServers";
    
    public static final String NATURE_ID = "navajoNature";

    public static final String NAVAJO_RUNNER_CLASS = "com.dexels.navajo.client.impl.NavajoRunner";

    public static final String REMOTE_PORT = "remotePort";
    public static final String REMOTE_USERNAME = "remoteUsername";
    public static final String REMOTE_SERVERURL = "remoteServerUrl";
    public static final String REMOTE_PASSWORD = "remotePassword";

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

    private static final String NAVAJO_PLUGIN_SCRIPT_INVOCATIONS = "navajoPluginScriptInvocations";

//    public static final String NAVAJO_DEFAULT_PROJECT = "SportlinkClubStudio";

    public static final String NAVAJO_DEFAULT_PROJECT_KEY = "navajoDefaultProject";

    //    private NavajoBrowser navajoBrowser = null;
    private Launch currentFunctionLaunch = null;

    private Launch currentSocketLaunch = null;
    
    private Launch currentScriptLaunch = null;

    private NavajoNature myNature = null;

    private NavajoBuilder myBuilder = null;

    private ClientInterface localClient = null;

    private TmlViewer currentTmlViewer;

    private TmlBrowser currentTmlBrowser;

    private MetaDataViewer currentMetaDataViewer;

    private ArrayList myServerEntries = null;
    //    private String getPreferenceStore().;
    private final ArrayList myServerEntryListeners = new ArrayList();
    
    private IPreferenceStore myPreferences  = null;
    
    private ArrayList scriptInvocations = null;
    
    
    private final Map classProviderMap = new HashMap();

    private final Map compilerProviderMap = new HashMap();
    
    
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
//        myBuilder = new NavajoBuilder();
        plugin = this;
        
//        ResourcesPlugin.getWorkspace().addResourceChangeListener(
//                new IResourceChangeListener(){
//                    public void resourceChanged(IResourceChangeEvent event) {
//                        System.err.println("Res CHANGED!!!"+event.getResource());
//                        IResource res = event.getResource();
//                        try {
//                            switch (event.getType()) {
//                               case IResourceChangeEvent.PRE_CLOSE:
//                                  System.out.print("Project ");
//                                  System.out.print(res.getFullPath());
//                                  System.out.println(" is about to close.");
//                                  break;
//                               case IResourceChangeEvent.PRE_DELETE:
//                                  System.out.print("Project ");
//                                  System.out.print(res.getFullPath());
//                                  System.out.println(" is about to be deleted.");
//                                  break;
//                               case IResourceChangeEvent.POST_CHANGE:
//                                  System.out.println("Resources have changed.");
//                                  event.getDelta().accept(new DeltaPrinter());
//                                  break;
//                               case IResourceChangeEvent.PRE_AUTO_BUILD:
//                                  System.out.println("Auto build about to run.");
//                                  event.getDelta().accept(new DeltaPrinter());
//                                  break;
//                               case IResourceChangeEvent.POST_AUTO_BUILD:
//                                  System.out.println("Auto build complete.");
//                                  event.getDelta().accept(new DeltaPrinter());
//                                  break;
//                            }
//                        } catch (CoreException e) {
//                            e.printStackTrace();
//                        }
//                     }                        
//                    }, IResourceChangeEvent.POST_CHANGE);
//    
    }
//    class DeltaPrinter implements IResourceDeltaVisitor {
//        public boolean visit(IResourceDelta delta) {
//           IResource res = delta.getResource();
//           switch (delta.getKind()) {
//              case IResourceDelta.ADDED:
//                 System.out.print("Resource ");
//                 System.out.print(res.getFullPath());
//                 System.out.println(" was added.");
//                 break;
//              case IResourceDelta.REMOVED:
//                 System.out.print("Resource ");
//                 System.out.print(res.getFullPath());
//                 System.out.println(" was removed.");
//                 break;
//              case IResourceDelta.CHANGED:
//                 System.out.print("Resource ");
//                 System.out.print(res.getFullPath());
//                 System.out.println(" has changed.");
//                 break;
//           }
//           return true; // visit the children
//        }
//     }    



    public Launch runNavajo(String className, IFile scriptFile) throws CoreException, NavajoPluginException {
        return runNavajo(className, scriptFile, null);
    }

    public Launch runNavajo(String classRunner, IFile scriptFile, IFile sourceTml) throws CoreException, NavajoPluginException {
//        System.err.println("Running file with path: " + scriptFile.getFullPath());
        String name = null;
        if ("tml".equals(scriptFile.getFileExtension())) {
//            System.err.println("Tml file found.");
            name = getScriptNameFromTml(sourceTml, scriptFile.getProject());
            scriptFile = getScriptFolder(scriptFile.getProject()).getFile(name);
//            System.err.println("Resolved to script: " + scriptFile.getLocation().toString());
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
//            System.err.println("Source TML FOUND: " + location);
//            System.err.println("ResolvedPAth: " + relTmlLocation);
        }
 
        final IProject project = tml.getProject();
        final String scriptName = name;

        Job job = new Job("Waiting for process to end..") {
            protected IStatus run(IProgressMonitor monitor) {
                while (!currentScriptLaunch.isTerminated()) {
                    try {
                        // TODO YUCK!
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                    }
                    if (monitor.isCanceled()) {
                        System.err.println("Run cancelled!");
                        return Status.CANCEL_STATUS;
                    }
                }
//                System.err.println("Cool. It finished");
                final IFile f = tml.getFile(scriptName + ".tml");
                //                try {
                //                    NavajoScriptPluginPlugin.getDefault().getTmlFolder(project).refreshLocal(IResource.DEPTH_INFINITE,
                // monitor);
                //                } catch (CoreException e1) {
                //                     e1.printStackTrace();
                //                }
                System.err.println("check this...");
                //                f.refreshLocal(0, monitor);
                //                NavajoScriptPluginPlugin.getDefault().openInEditor(f);
                if (!f.exists()) {
                    try {
                        System.err.println("TML file does not seem to exist. Refreshing.");
                        f.refreshLocal(0, null);
                    } catch (CoreException e) {
                        e.printStackTrace();
                        return Status.CANCEL_STATUS;
                    }
                    if (!f.exists()) {
                        return Status.CANCEL_STATUS;
                    } else {
//                        System.err.println("Ah, there it is.");
                        
                    }
                }

                //                     if (currentTmlViewer != null) {
                //                          showTml(f);
                //                    } else {
                //                        System.err.println("Opening new viewer");
                //                        openTmlViewer();
                try {
                    showTml(f,scriptName);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                //                    }
                return Status.OK_STATUS;
            }

        };

        return runNavajoBootStrap(classRunner, true, scriptFile.getProject(), name, location, job, relTmlLocation, null);
    }

    public void openTmlViewer() {
        openViewer("com.dexels.TmlViewer");
    }
    
    public void openMetaDataViewer() {
        openViewer("com.dexels.MetaDataViewer");
    }
    
    public void openViewer(final String id) {
        getWorkbench().getDisplay().syncExec(new Runnable() {

            public void run() {
                try {
                    IViewPart iv = getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(id);
                    getWorkbench().getActiveWorkbenchWindow().getActivePage().bringToTop(iv);
                } catch (PartInitException e) {
                    e.printStackTrace();
                }
            }
        });
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
        if(!jp.isOpen()) {
//            try {
//                reportProblem("Project: "+jp.getProject().getName()+" refers to a closed project.", jp.getProject(), 0, 1, null);
//            } catch (CoreException e) {
//                e.printStackTrace();
//            }
//            System.err.println("Ignoring closed project: "+jp.getProject().getName());
//            return;
        }
        IClasspathEntry[] ice = jp.getResolvedClasspath(true);
        current.add(jp.getOutputLocation());
//        System.err.println("Resolving project: "+jp.getElementName());
//        System.err.println("cp size: "+ice.length);
        for (int i = 0; i < ice.length; i++) {
            if (ice[i].getEntryKind() == IClasspathEntry.CPE_PROJECT) {
                IProject prj = ResourcesPlugin.getWorkspace().getRoot().getProject(ice[i].getPath().toString());
                IJavaProject project = JavaCore.create(prj);
                resolveProject(outputPaths, current, project);
                continue;
            }
//            System.err.println("Adding path: "+ice[i].getPath());
            if (!current.contains(ice[i].getPath())) {
             current.add(ice[i].getPath());
         
            }
      
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

    public IFile getServerXml(IProject prj) throws NavajoPluginException {
        return prj.getFile(new Path(getNavajoConfigPath(prj) + "/" + SERVER_FILE_NAME));
    }

    public IFile getSqlXml(IProject prj) throws NavajoPluginException {
        return prj.getFile(new Path(getNavajoConfigPath(prj) + "/" + SQL_FILE_NAME));
    }

    public IFolder getNavajoConfigFolder(IProject prj) throws NavajoPluginException {
        return prj.getFolder(new Path(getNavajoConfigPath(prj)));
    }

    public String getNavajoConfigPath(IProject prj) throws NavajoPluginException {
        return getNavajoRootPath(prj) + NAVAJO_AUXILARY + NAVAJO_CONFIG_PATH;
    }

    public Launch runNavajoBootStrap(String runClassName, boolean showInDebugger, IProject project, String scriptName, String sourceTmlPath, Job job,
            String relativeTmlLocation, String[] programArgs) throws CoreException, NavajoPluginException {
//        IProject myProject = script.getProject();
        final IFolder tml = NavajoScriptPluginPlugin.getDefault().getTmlFolder(project);
        IFolder scriptPath = project.getFolder(NavajoScriptPluginPlugin.getDefault().getScriptPath(project));
        IProjectNature ipn = project.getNature("org.eclipse.jdt.core.javanature");
        IFile file = getServerXml(project);
        System.err.println("Raw location: " + project.getRawLocation());

        if (ipn != null) {
            System.err.println("Java nature found: " + ipn.getClass());

        }
        String serverXml = file.getRawLocation().toString();

        String[] prgArgs;
        if (programArgs!=null) {
            prgArgs = programArgs;
        } else {
            if (sourceTmlPath == null) {
                prgArgs = new String[] { serverXml, scriptName, tml.getRawLocation().toString() };
            } else {
                prgArgs = new String[] { serverXml, scriptName, tml.getRawLocation().toString(), sourceTmlPath, relativeTmlLocation };
            }
        }
        String[] classpath = new String[0];
        if (ipn instanceof IJavaProject) {
            IJavaProject jp = (IJavaProject) ipn;
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
            for (int i = 0; i < classpath.length; i++) {
                System.err.println("Classpath: "+classpath[i]);
            }
        } else {
            System.err.println("Not a java project?!");
            //		    System.err.println(" affe >>>"+myProject.getClass());
        }
        String[] vmArgs = this.getVmArgs(project);
//        vmArgs = addPreferenceParameters(vmArgs);

        for (int i = 0; i < vmArgs.length; i++) {
            System.err.println("vmARG: " + vmArgs[i]);
        }
        String[] bootClasspath = new String[0];

        StringBuffer programArguments = new StringBuffer();
        for (int i = 0; i < prgArgs.length; i++) {
            programArguments.append(" " + prgArgs[i]);
        }

        StringBuffer jvmArguments = new StringBuffer();
        for (int i = 0; i < vmArgs.length; i++) {
            jvmArguments.append(" " + vmArgs[i]);
        }
        currentScriptLaunch = VMLauncherUtility.runVM("Navajo inline", runClassName, classpath, bootClasspath, jvmArguments.toString(),
                programArguments.toString(), getSourceLocator(), isDebugMode(), showInDebugger, job);
        return currentScriptLaunch;
    }

    public String[] getClasspath() {
        return new String[] {};
    }

    public String[] getVmArgs(IProject myProject) {
        return new String[] { "-Duser.dir=" + myProject.getLocation().toOSString(), "-Dnavajo.user=" + getDefaultNavajoUser(myProject),
                "-Dnavajo.password=" + getDefaultNavajoPassword(myProject),"-Dnavajo.project="+myProject.getName() };
    }

    /**
     * @return
     */
//    private String getPassword() {
//        return "ik";
//    }

    /**
     * @return
     */
//    private String getUsername() {
//        return "ik";
//    }

    /**
     * @return
     */
    private ISourceLocator getSourceLocator() {
        return null;
    }

    public void openInEditor(final IFile f) {
        openInEditor(f, null);
    }

//    public void closeEditorsWithExtension(IEditorPart exclude, String extension) {
//        IEditorPart[] iii = Workbench.getInstance().getActiveWorkbenchWindow().getActivePage().getEditors();
//        for (int i = 0; i < iii.length; i++) {
//            IResource res = (IResource) iii[i].getEditorInput().getAdapter(IResource.class);
//            if (res == null) {
//                continue;
//            }
//            if (iii[i] == exclude) {
//                continue;
//            }
//            if (res.getFileExtension() == null) {
//                continue;
//            }
//            if (res.getFileExtension().equals(extension)) {
//                System.err.println("FOUND AN EDITOR WITH THE SAME EXTENSION!");
//                Workbench.getInstance().getActiveWorkbenchWindow().getActivePage().closeEditor(iii[i], false);
//                return;
//            }
//
//        }
//
//    }

    public void openInEditor(final IFile f, final ISourceRange range) {
        if (f == null || !f.exists()) {
            System.err.println("Can not open: Null file or non existent.");
            return;
        }
        getWorkbench().getDisplay().syncExec(new Runnable() {
            public void run() {
                boolean exclusiveForExtension = false;
                System.err.println("AAP, exclusive: " + f.getFileExtension());

                if (f.getFileExtension().equals(".tml")) {
                    System.err.println("Yes, exclusive: " + f.getFileExtension());
                    exclusiveForExtension = true;
                }
               IEditorDescriptor edId = getWorkbench().getEditorRegistry().getDefaultEditor(f.getName());
                if (edId == null) {
                    edId = getWorkbench().getEditorRegistry().findEditor(IEditorRegistry.SYSTEM_EXTERNAL_EDITOR_ID);
                    System.err.println("No descriptor found for: " + f.getName());
                    if (edId == null) {
                        System.err.println("STILL No descriptor found for: " + f.getName() + ">> " + IEditorRegistry.SYSTEM_EXTERNAL_EDITOR_ID);
                    }
                }
                try {
                    getWorkbench().getActiveWorkbenchWindow().getActivePage().openEditor(new FileEditorInput(f), edId.getId());
                    if (range != null) {
                        IEditorPart ied = getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
                        if (ied != null && ied instanceof AbstractTextEditor) {
                            AbstractTextEditor cue = (AbstractTextEditor) ied;
                            //                                cue.
                            cue.selectAndReveal(range.getOffset(), range.getLength());
                        }
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
    
    public static IWorkbench getDefaultWorkbench() {
        return plugin.getWorkbench();
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

//    private String[] addPreferenceParameters(String[] previous) {
//        String[] prefParams = StringUtil.cutString(NavajoScriptPluginPlugin.getDefault().getJvmParamaters(), System.getProperty("path.separator"));
//        return StringUtil.concat(previous, prefParams);
//    }

//    private String[] addPreferenceJvmToClasspath(String[] previous) {
//        String[] prefClasspath = StringUtil.cutString(NavajoScriptPluginPlugin.getDefault().getJvmClasspath(), System.getProperty("path.separator"));
//        return StringUtil.concatUniq(previous, prefClasspath);
//    }

//    private String[] addPreferenceJvmToBootClasspath(String[] previous) {
//        String[] prefBootClasspath = StringUtil.cutString(NavajoScriptPluginPlugin.getDefault().getJvmBootClasspath(), System
//                .getProperty("path.separator"));
//        return StringUtil.concatUniq(previous, prefBootClasspath);
//    }

//    public String getJvmParamaters() {
//        IPreferenceStore pref = NavajoScriptPluginPlugin.getDefault().getPreferenceStore();
//        return pref.getString(PREF_JVM_PARAMETERS_KEY);
//    }
//
//    public String getJvmClasspath() {
//        IPreferenceStore pref = NavajoScriptPluginPlugin.getDefault().getPreferenceStore();
//        return pref.getString(PREF_JVM_CLASSPATH_KEY);
//    }
//
//    public String getJvmBootClasspath() {
//        IPreferenceStore pref = NavajoScriptPluginPlugin.getDefault().getPreferenceStore();
//        return pref.getString(PREF_JVM_BOOTCLASSPATH_KEY);
//    }

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
    public String getCompilePath(IProject prj) throws NavajoPluginException {
        return getAuxilaryPath(prj)  + RELATIVE_COMPILED_PATH;
    }
    
    public String getAuxilaryPath(IProject prj) throws NavajoPluginException {
        return getNavajoRootPath(prj) + NAVAJO_AUXILARY;
    }
    
    public String getAdaptersPath(IProject prj) throws NavajoPluginException {
        return getAuxilaryPath(prj)  + RELATIVE_ADAPTERS_PATH;
    }

    public String getNavajoRootPath(IProject prj) throws NavajoPluginException {
        if (!prj.isOpen()) {
            throw new NavajoPluginException("Current navajo project is not open!");
        }
        try {
            if (!prj.hasNature(NAVAJO_NATURE)) {
               System.err.println("Warning: No navajo project!");
            }
        } catch (CoreException e) {
            throw new NavajoPluginException("Error getting project state");
        }
//        System.err.println("Getting nrp from: "+prj.getName());
        IFile iff = prj.getFile(".navajoroot");
        InputStream iss = null;
        BufferedReader isr = null;
        if (iff == null ) {
            return NAVAJO_ROOT_PATH;
        }
        
        try {
            iff.refreshLocal(0, null);
            if (!iff.exists()) {
                return NAVAJO_ROOT_PATH;
            }
            iss = iff.getContents();
            isr = new BufferedReader(new InputStreamReader(iss));
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
                if (iss!=null) {
                    iss.close();
                }
                if (isr!=null) {
                    isr.close();
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

    }

    /**
     * @return Returns the scriptPath.
     */
    public String getScriptPath(IProject prj)  throws NavajoPluginException{
        return getNavajoRootPath(prj) + NAVAJO_AUXILARY + RELATIVE_SCRIPT_PATH;
    }

    public String getTmlPath(IProject prj)  throws NavajoPluginException{
        return getNavajoRootPath(prj) + NAVAJO_AUXILARY + RELATIVE_TML_PATH;
    }

    public String getMetadataPath(IProject prj)  throws NavajoPluginException{
        return getNavajoRootPath(prj) + NAVAJO_AUXILARY + RELATIVE_METADATA_PATH;
    }

    public String getCompiledScriptPath(IProject prj)  throws NavajoPluginException{
        return getNavajoRootPath(prj) + NAVAJO_AUXILARY + COMPILED_SCRIPT_PATH;
    }

    
    public IFolder getTmlFolder(IProject p)  throws NavajoPluginException{
        return p.getFolder(getTmlPath(p));
    }

    public IFolder getScriptFolder(IProject p)  throws NavajoPluginException{
        return p.getFolder(getScriptPath(p));
    }

    public IFolder getCompiledScriptFolder(IProject project)  throws NavajoPluginException{
        return project.getFolder(getCompiledScriptPath(project));
    }
    
   
    public IFolder getAuxilaryFolder(IProject p)  throws NavajoPluginException{
        return p.getFolder(getAuxilaryPath(p));
    }

    public IFolder getAdaptersFolder(IProject p)  throws NavajoPluginException{
        return p.getFolder(getAdaptersPath(p));
    }

    public IFolder getMetadataFolder(IProject p)  throws NavajoPluginException{
        return p.getFolder(getMetadataPath(p));
    }

    public IFile getScriptMetadataFile(IProject p)  throws NavajoPluginException{
        return getMetadataFolder(p).getFile(SCRIPT_META_NAME);
    }
    
    
    public IFile getScriptFile(IProject p, String path)  throws NavajoPluginException{
        IFolder iff = p.getFolder(getScriptPath(p));
        IFile ifff = iff.getFile(path + ".xml");
        return ifff;
    }

    public IFile getCompiledScriptFile(IProject p, String path)  throws NavajoPluginException{
        IFolder iff = p.getFolder(getCompilePath(p));
        IFile ifff = iff.getFile(path + ".java");
        return ifff;
    }
    public IFile getClassFile(IProject p, String path)  throws NavajoPluginException{
        IFolder iff = p.getFolder(getCompiledScriptPath(p));
        IFile ifff = iff.getFile(path + ".class");
        return ifff;
    }
    public IFile getTmlFile(IProject p, String path)  throws NavajoPluginException{
        IFolder iff = p.getFolder(getTmlPath(p));
        if (!path.endsWith(".tml")) {
            IFile ifff = iff.getFile(path + ".tml");
            return ifff;
        }
        return iff.getFile(path);

    }

    /** Looks for a tml file, parses it and returns it */
    public Navajo getNavajo(IProject p , String scriptName) throws NavajoPluginException {
        IFile iff = getTmlFile(p, scriptName);
        if (iff==null || !iff.exists()) {
            return null;
        }
        InputStream iss;
        try {
            iss = iff.getContents();
        } catch (CoreException e) {
            e.printStackTrace();
            throw new NavajoPluginException("Error loading TML file: "+scriptName);
        }
        Navajo n = NavajoFactory.getInstance().createNavajo(iss);
        return n;
    }

    public String getScriptName(IFile script, IProject project)  throws NavajoPluginException{
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
    public String getScriptNameFromTml(IFile tml, IProject project)  throws NavajoPluginException{
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

    public String getScriptNameFromResource(IFile ff)  throws NavajoPluginException{
        return getScriptNameFromResource(null,ff);
    }
    
    /** Will determine the name of the script. If possible from the IFile, otherwise from the navajo header */
    public String getScriptNameFromResource(Navajo n, IFile ff)  throws NavajoPluginException{
        if (ff==null) {
            Header j = n.getHeader();
            return j.getRPCName();
        }
        
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


    public void deleteFile(final IFile ir, final IProgressMonitor monitor) throws CoreException {
        System.err.println("Ignoring delete");
        if (true) {
            return;
        }
        getWorkbench().getDisplay().syncExec(new Runnable() {

            public void run() {
                //                ResourcesPlugin.getWorkspace().get
                IEditorPart edd = getWorkbench().getActiveWorkbenchWindow().getActivePage().findEditor(new FileEditorInput(ir));
                if (edd != null) {
                    getWorkbench().getActiveWorkbenchWindow().getActivePage().closeEditor(edd, false);
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

    public void showTml(IFile tmlFile,String scriptName) throws Exception {
        InputStream is = null;
       if (tmlFile == null) {
            return;
        }
        Navajo n = null;
        try {
            tmlFile.refreshLocal(0, null);
            if (!tmlFile.exists()) {
                return;
            }
            is = tmlFile.getContents();
            if (is==null) {
                return;
            }
            
            n = NavajoFactory.getInstance().createNavajo(is);
            is.close();
        } catch (CoreException e) {
             e.printStackTrace();
             return;
        } catch (IOException e) {
            e.printStackTrace();
            return;
        } finally {
            try {
                if (is!=null) {
                    is.close();
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

        showTml(tmlFile, n, scriptName);
   }
    
    
    public void showTml(IFile tmlFile,Navajo n,String scriptName) {
logMessage("tmlFile: "+tmlFile.getName());
        if (currentTmlViewer == null) {
            System.err.println("Opening new viewer");
            logMessage("Opening new viewer: "+tmlFile.getName());
           openTmlViewer();
            if (currentTmlViewer == null) {
                logMessage("STILL NO VIEWER?!");
                System.err.println("STILL NO VIEWER?!");
                return;
            }
        }
             currentTmlViewer.setNavajo(n, tmlFile,scriptName);
            currentTmlViewer.setFocus();
            getWorkbench().getDisplay().asyncExec(new Runnable() {

                public void run() {
                    getWorkbench().getActiveWorkbenchWindow().getActivePage().bringToTop(currentTmlViewer);
                }
            });

    }

    public Navajo getNavajoFromViewer() {
        if (currentTmlViewer==null) {
            return null;
        }
        return currentTmlViewer.getNavajo();
    }

    public String getServiceFromViewer() {
        if (currentTmlViewer==null) {
            return null;
        }
        return currentTmlViewer.getService();
    }

    
    public Navajo getNavajoFromBrowser() {
        if (currentTmlBrowser==null) {
            return null;
        }
        return currentTmlBrowser.getNavajo();
    }

    public String getServiceFromBrowser() {
        if (currentTmlBrowser==null) {
            return null;
        }
        return currentTmlBrowser.getService();
    }

    
    
    public IFolder getCompileFolder(IProject ipp)  throws NavajoPluginException{
        IFolder iff = ipp.getFolder(getCompilePath(ipp));
        return iff;
    }

    public void logMessage(String msg) {
        super.getLog().log(new Status(Status.OK,"navajo",0,msg,null));
    }
    public ArrayList searchForExtendingClasses(IProject ipp, String interfaceName, IProgressMonitor monitor) throws CoreException {
        return searchForClasses(ipp, interfaceName, monitor, IJavaSearchConstants.REFERENCES);
    }

    public ArrayList searchForImplementingClasses(IProject ipp, String interfaceName, IProgressMonitor monitor) throws CoreException {
        return searchForClasses(ipp, interfaceName, monitor, IJavaSearchConstants.IMPLEMENTORS);
    }

    private ArrayList searchForClasses(IProject ipp, String interfaceName, IProgressMonitor monitor, int type) throws CoreException {
        IProjectNature ipn = ipp.getNature("org.eclipse.jdt.core.javanature");
        if (ipn instanceof IJavaProject) {
            final ArrayList matches = new ArrayList();
            IJavaProject jp = (IJavaProject) ipn;
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
                            System.err.println(">>> "+match.toString());
                            matches.add(match);
                        }
                    }, monitor);
            return matches;
        }
        return null;
    }

//    public IProject getCurrentProject() {
//
//        // oh, come on:
//        try {
//            ArrayList arr = getNavajoProjects();
//            if (arr.size() > 0) {
//                return (IProject) arr.get(0);
//            }
//            System.err.println("# of projects found: " + arr.size());
//        } catch (CoreException e) {
//            e.printStackTrace();
//        }
//        return ResourcesPlugin.getWorkspace().getRoot().getProject("TestNavajo");
//        //		return null;
//    }

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

    public Launch getCurrentSocketLaunch() {
        return currentSocketLaunch;
    }

    public void setCurrentSocketLaunch(Launch currentSocketLaunch) {
        this.currentSocketLaunch = currentSocketLaunch;
    }
    
    
    public void insertIntoCurrentTextEditor(String s) {
        IEditorPart activeEditor = getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
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
    public IFile getPersistenceXml(IProject ip)  throws NavajoPluginException{
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

    public void addNavajoNature(IProject project, boolean suppressDialogs, String root) throws CoreException, NavajoPluginException {
        System.err.println("Entering... " + project.getName());
        IProjectDescription description = project.getDescription();
        System.err.println("Found open project. Desc: " + description.getName());
        // Toggle the nature.
        List newIds = new ArrayList();
        newIds.addAll(Arrays.asList(description.getNatureIds()));
        int index = newIds.indexOf(NavajoScriptPluginPlugin.NAVAJO_NATURE);
        if (index == -1) {
            System.err.println("Adding,,,");
            newIds.add(0,NavajoScriptPluginPlugin.NAVAJO_NATURE);
            createDefaultServerConfigFiles(project, suppressDialogs,root);
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
        //        boolean res2 = showQuestion("Folder: "+name+" exists in folder:
        // "+parent.getFullPath()+"\nOverwrite?");
        //        if (res2) {
        //            aux.delete(true, null);
        //            aux.create(true, true, null);
        //        }
        System.err.println("Folder: " + name + " exists in folder: " + parent.getFullPath());
        return aux;
    }

    public void createDefaultServerConfigFiles(IProject ipp, boolean suppressDialogs,String navajoRoot) throws CoreException, NavajoPluginException {
        IFolder navajotester = checkAndCreateFolder(ipp, navajoRoot);
        IFolder auxilary = checkAndCreateFolder(navajotester, NAVAJO_AUXILARY);
        IFolder config = checkAndCreateFolder(auxilary, NAVAJO_CONFIG_PATH);
        IFolder scripts = checkAndCreateFolder(auxilary, RELATIVE_SCRIPT_PATH);
        IFolder compiled = checkAndCreateFolder(auxilary, RELATIVE_COMPILED_PATH);
        IFolder src = checkAndCreateFolder(ipp, "src");
        IFolder bin = checkAndCreateFolder(ipp, "bin");
        IFile testFile = scripts.getFile("InitTestScript.xml");
        createFile(testFile, "/com/dexels/navajo/studio/defaultres/InitTestScript.xml", suppressDialogs);
        IFile navajoStatusFile = scripts.getFile("InitNavajoStatus.xml");
        createFile(navajoStatusFile, "/com/dexels/navajo/studio/defaultres/InitNavajoStatus.xml", suppressDialogs);

        IFile iff = getServerXml(ipp);
        boolean bb = createFile(iff, "/com/dexels/navajo/studio/defaultres/server.xml", suppressDialogs);
        iff = getNavajoConfigFolder(ipp).getFile("sqlmap.xml");
        createFile(iff, "/com/dexels/navajo/studio/defaultres/sqlmap.xml", suppressDialogs);
        iff = ipp.getFile("src/application.properties");
        createFile(iff, "/com/dexels/navajo/studio/defaultres/application.properties", suppressDialogs);

        //        IPackageFragmentRoot[] ipfr = jp.getPackageFragmentRoots();
        //        for (int i = 0; i < ipfr.length; i++) {
        //            System.err.println("Packagefragment: "+ipfr[i].getPath());
        //        }
        ipp.getFolder("src").refreshLocal(IResource.DEPTH_INFINITE, null);
        getNavajoConfigFolder(ipp).refreshLocal(IResource.DEPTH_INFINITE, null);
        //        jp.setOutputLocation(bin.getFullPath(), null);

        IClasspathEntry srcEntry = JavaCore.newSourceEntry(src.getFullPath());
//        IClasspathEntry compiledEntry = JavaCore.newSourceEntry(compiled.getFullPath());

        IProjectNature ipn = ipp.getNature("org.eclipse.jdt.core.javanature");
        JavaProject jp = (JavaProject) ipn;

        IClasspathEntry self = jp.getClasspathEntryFor(ipp.getFullPath());
        IClasspathEntry srcc = jp.getClasspathEntryFor(src.getFullPath());
        IClasspathEntry compp = jp.getClasspathEntryFor(compiled.getFullPath());

        int extraEntries = (srcc == null ? 1 : 0) + (compp == null ? 1 : 0);

        IClasspathEntry[] current = jp.getRawClasspath();

        showWarning("I stopped with messing around in the Java build settings.\nIt's up to you to make sure that it has been set up correctly.\nImportant:\n- The project itself should not be a sourcedirectory.\n- src/ should be a source directory.\n- The project itself should not be an output directory\n-The 'classes' folder should be appended as a library path if and only if you want to do a local run.\n- For remote runs, it will mess up the navajo classloader if you do.");
        // Project itself is in classpath. Evil. Bad. Must seek and destroy
//        if (self != null) {
//            int outCount = 0;
//            IClasspathEntry[] current2 = new IClasspathEntry[current.length - 1];
//            for (int i = 0; i < current.length; i++) {
//                System.err.println(":: " + i + " null? " + (current[i] == null) + " len: " + current.length);
//                System.err.println("AAP: " + current[i]);
//                if (current[i].getPath().equals(ipp.getFullPath())) {
//                } else {
//                    current2[outCount++] = current[i];
//                }
//
//            }
//            current = current2;
//        }
//        ArrayList al = new ArrayList();
//
//        IClasspathEntry[] newCPE = null; //new
//        IClasspathEntry[] newCPE2 = null; //new
//        jp.setRawClasspath(newCPE2, bin.getFullPath(), null);
//        jp.save(null, false);
    }

    private boolean createFile(IFile iff, String resourceName, boolean suppressDialogs) throws CoreException {
        if (iff.exists()) {
            boolean res;
            if (suppressDialogs) {
                res = false;
            } else {
                res = showQuestion("File: " + iff.getFullPath() + " exists. \nOverwrite?");

            }
            if (!res) {
                System.err.println("Skipping existing config: " + iff.getFullPath());
                return false;
            } else {
                iff.delete(true, null);
            }
            //            showI "Creating config files", "File exists. Skipping.");
        }
        InputStream in = getClass().getResourceAsStream(resourceName);
        if (in == null) {
            showWarning("Resource: " + resourceName + " is not found!");
            return false;
        }
        iff.create(in, true, null);
        try {
            in.close();
        } catch (IOException e) {
           e.printStackTrace();
        }
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
        getWorkbench().getDisplay().syncExec(new Runnable() {
            public void run() {
                MessageDialog.openInformation(getWorkbench().getDisplay().getActiveShell(), title, message);
            }
        });

    }

    public boolean showQuestion(String title, String message) {
        return MessageDialog.openQuestion(getWorkbench().getDisplay().getActiveShell(), title, message);
    }

    public boolean showConfirm(final String title, final String message) {
        getWorkbench().getDisplay().syncExec(new Runnable(){
            public void run() {
                MessageDialog.openConfirm(getWorkbench().getDisplay().getActiveShell(), title, message);
            }});
        return true;
    }

    public void showError(final String title, final String message) {
        getWorkbench().getDisplay().syncExec(new Runnable() {
            public void run() {
                MessageDialog.openError(getWorkbench().getDisplay().getActiveShell(), title, message);
            }
        });
    }

    public void showWarning(final String title, final String message) {
        getWorkbench().getDisplay().syncExec(new Runnable() {
            public void run() {
                MessageDialog.openWarning(getWorkbench().getDisplay().getActiveShell(), title, message);
            }
        });
    }

    public boolean isScriptExisting(IProject ipp, String scriptName) throws NavajoPluginException {
        IFile dd = getScriptFile(ipp, scriptName);
        return dd.exists();
    }

    public void reportProblem(final String msg, final IResource loc, final int start, final int end, final TslCompileException tce) throws CoreException {
//            loc.refreshLocal(IResource.DEPTH_INFINITE, null);
//            loc.touch(null);
//        	Map m = new HashMap();
//        	m.put(IMarker.CHAR_START, new Integer(start));
//        	m.put(IMarker.CHAR_END, new Integer(end));
//        	m.put(IMarker.SEVERITY,  new Integer(IMarker.SEVERITY_ERROR));
//        	m.put(IMarker.PRIORITY, new Integer(IMarker.PRIORITY_HIGH));
//        	m.put("key", ""+start+":"+"end");
//         	m.put("violation", "Niet de apen voeren!");
//    
//        loc.refreshLocal(IResource.DEPTH_INFINITE, null);
        Map m = new HashMap();
        MarkerUtilities.setCharStart(m, start);
        MarkerUtilities.setCharEnd(m, end);
        MarkerUtilities.setMessage(m,  msg + ": ");
        m.put(IMarker.SEVERITY,  IMarker.SEVERITY_ERROR );
        if (tce!=null) {
            m.put("code", tce.getCode());
        }

        MarkerUtilities.createMarker(loc, m, "com.dexels.plugin.tslproblemmarker");
//	    IMarker marker = loc.createMarker("com.dexels.plugin.tslproblemmarker");
//	    System.err.println("Marker id: "+marker.getType());
//        marker.setAttribute(IMarker.MESSAGE, msg + ": ");
//        marker.setAttribute(IMarker.CHAR_START, start);
//        marker.setAttribute(IMarker.CHAR_END, end);
//        marker.setAttribute(IMarker.SEVERITY,  IMarker.SEVERITY_ERROR );

        
//        
//        getWorkbench().getDisplay().syncExec(new Runnable(){
//
//            public void run() {
//                if (loc instanceof IFile) {
//                    IFile iff = (IFile)loc;
//                    FileEditorInput fei = new FileEditorInput(iff);
//                    IEditorPart ied = getWorkbench().getActiveWorkbenchWindow().getActivePage().findEditor(fei);
//                    if (ied!=null) {
//                        if (ied instanceof ITextEditor) {
//                            ITextEditor ite = (ITextEditor)ied;
//                            if (!ite.isDirty()) {
//                                ite.doRevertToSaved();
//                                ite.
//                                System.err.println("REVERTED!!!!");
//                            }
//                        }
//                    }
//                }
//            }});
//         
//        IEditorInput o;
//        
//        getActiveEditor();
        
//        IEditorPart part = page.getActiveEditor();
//        if (!(part instanceof AbstractTextEditor))
//           return;
//        ITextEditor editor = (ITextEditor)part;
//        IDocumentProvider dp = editor.getDocumentProvider();
//        IDocument doc = dp.getDocument(editor.getEditorInput());
//        int offset = doc.getLineOffset(doc.getNumberOfLines()-4);
//        doc.replace(offset, 0, pasteText+"\n");

      }

    public void setTmlViewer(TmlViewer tv) {
        currentTmlViewer = tv;
    }
    
    public void setTmlBrowser(TmlBrowser tv) {
        currentTmlBrowser = tv;
    }

    /**
     * @return
     */
    public boolean recompileOnJavaDelete() {
         return false;
    }

    /**
     * @param scriptName
     * @return
     */
    public void showMetaData(IFile file, String scriptName) throws NavajoPluginException {
        if (currentMetaDataViewer == null) {
             openMetaDataViewer();
            if (currentMetaDataViewer == null) {
                System.err.println("STILL NO VIEWER?!");
                return;
            }
            getWorkbench().getDisplay().asyncExec(new Runnable() {

                public void run() {
                    getWorkbench().getActiveWorkbenchWindow().getActivePage().bringToTop(currentMetaDataViewer);
                }
            });
 
            
        }
        if (currentMetaDataViewer!= null) {
            if (myBuilder==null) {
                showConfirm("There is a problem building metadata.\nI know exactly what the problem is: There is no builder yet.\n So, just edit a script, and the builder will be initialized, and the fun can begin.");
            } else {
                currentMetaDataViewer.showScript(file, scriptName, myBuilder.getMetaDataHandler());
            }
            
        }
    }

    /**
     * @param viewer
     */
    public void setMetaDataViewer(MetaDataViewer viewer) {
        currentMetaDataViewer = viewer;
        
    }

    /**
     * @param builder
     */
    public void setNavajoBuilder(NavajoBuilder builder) {
        myBuilder = builder;
    }

    public boolean hasNavajoBuilder() {
        return myBuilder!=null;
    }
    
    public String getDefaultNavajoUser(IProject p)  {
        System.err.println("Loading server.xml----- OPTIMIZE ME");
        Navajo n = null;
        try {
            n = loadNavajo(getServerXml(p));
        } catch (NavajoPluginException e) {
            e.printStackTrace();
        }
       if (n==null) {
            System.err.println("No serverxml?!");
            return null;
        }
        Property prop = n.getProperty("server-configuration/plugin/defaultUser");
        if (p==null) {
            return null;
        } else {
            return prop.getValue();
        }
        
    }
    
    public String getDefaultNavajoPassword(IProject p) {
        System.err.println("Loading server.xml----- OPTIMIZE ME");
        Navajo n = null;
        try {
            n = loadNavajo(getServerXml(p));
        } catch (NavajoPluginException e) {
            e.printStackTrace();
        }
        if (n==null) {
            System.err.println("No serverxml?!");
            return null;
        }
        Property prop = n.getProperty("server-configuration/plugin/defaultPassword");
        if (p==null) {
            return null;
        } else {
            return prop.getValue();
        }
    }

    
    public Navajo loadNavajo(IFile f) {
        if (f==null || !f.exists()) {
            return null;
        }
        InputStream s = null;
        try {
            s = f.getContents();
            Navajo n = NavajoFactory.getInstance().createNavajo(s);
            return n;
        } catch (CoreException e) {
            e.printStackTrace();
        } finally {
            try {
                if (s!=null) {
                    s.close();
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
         }
        return null;
    }
    
    public IPreferenceStore getPreferenceStore() {
        if (myPreferences==null) {
            myPreferences = super.getPreferenceStore();
        }
        return myPreferences;
    }

    public String getRemoteUsername() {
        return getPreferenceStore().getString(REMOTE_USERNAME);
//        return "ROOT";
    }

    public String getRemotePassword() {
        return getPreferenceStore().getString(REMOTE_PASSWORD);
//        return "";
    }
    public String getRemoteServer() {
        return getPreferenceStore().getString(REMOTE_SERVERURL);
//        return "localhost:10000";
    }
    
    public int getRemotePort() {
        return getPreferenceStore().getInt(REMOTE_PORT);
//        return 10000;
    }
    public void runRemoteNavajo(IProject ipp, String scriptName, IFile sourceTml, String sourceName) {
            try {
//                NavajoScriptPluginPlugin.getDefault().runNavajo(NavajoScriptPluginPlugin.NAVAJO_RUNNER_CLASS, file);
                NavajoClientFactory.resetClient();
                NavajoClientFactory.createClient("com.dexels.navajo.client.NavajoSocketClient", null);
                NavajoClientFactory.getClient().setServerUrl(getRemoteServer());
                NavajoClientFactory.getClient().setUsername(getRemoteUsername());
                NavajoClientFactory.getClient().setPassword(getRemotePassword());
                Navajo in = null;
                if (sourceTml!=null && sourceTml.exists()) {
                    sourceTml.refreshLocal(0, null);
                    in = NavajoFactory.getInstance().createNavajo(sourceTml.getContents());
                } else {
                    System.err.println("Running init script with empty navajo...");
                    in = NavajoFactory.getInstance().createNavajo();
                }
                System.err.println("Client class: "+NavajoClientFactory.getClient().getClass());
                Navajo result = NavajoClientFactory.getClient().doSimpleSend(in, getRemoteServer(),scriptName,getRemoteUsername(),getRemotePassword(),-1,false);
//                if (ipp==null) {
//                    System.err.println("Project null?!");
//                }
                if (sourceName!=null) {
                    result.getHeader().setAttribute("sourceScript", sourceName);
                                
                }
                
//                System.err.println("PRINTING HEADER FOR result, in runRemoteNavajo:");
//                result.write(System.err);
                
                IFile tml = getTmlFile(ipp, scriptName);
                if (tml==null) {
                    System.err.println("TmlFile not locatable for script: "+scriptName);
                }
                String path = tml.getLocation().toOSString();
                File fff =  new File(path).getParentFile();
                if (fff!=null) {
                    fff.mkdirs();
                }
                System.err.println("Path: "+path);
                FileWriter fw = new FileWriter(path);
                result.write(fw);
                fw.flush();
                fw.close();
//                System.err.println("Connection finished, and written. About to show tml");
                showTml(tml, result,scriptName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        
    }
    
    public ArrayList getServerEntries() {
        if (myServerEntries==null) {
            myServerEntries = parseServerEntries();
        }
        return myServerEntries;
    }
  
    private ArrayList parseServerEntries() {
        String entries = getPreferenceStore().getString(NAVAJO_APPLICATION_SERVERS);
        StringTokenizer st = new StringTokenizer(entries,"\n");
        ArrayList entryList = new ArrayList();
        while (st.hasMoreTokens()) {
            String current = st.nextToken();
            ServerEntry se = new ServerEntry(current);
            entryList.add(se);
        }
        return entryList;
    }
    
    private String serializeServerEntries() {
            if (myServerEntries==null) {
                return null;
            }
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < myServerEntries.size(); i++) {
                ServerEntry current = (ServerEntry)myServerEntries.get(i);
                sb.append(current.toDataString());
                sb.append("\n");
            }
            return sb.toString();
            
    }
    
    private String serializeServerInvocations() {
        if (scriptInvocations==null) {
            scriptInvocations = parseScriptInvocations();
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < scriptInvocations.size(); i++) {
            String current = (String)scriptInvocations.get(i);
            sb.append(current);
            sb.append("\n");
        }
        return sb.toString();
        
}
    
    
    
       protected void initializeDefaultPreferences(IPreferenceStore store) {
           super.initializeDefaultPreferences(store);
           //        IPreferenceStore store = getPreferenceStore();
        if (store == null) {
            return;
        }
        myPreferences = store;
        store.setDefault(P_NAVAJO_PATH, "navajo-tester");
        store.setDefault(NAVAJO_APPLICATION_SERVERS,"Ficus|http|10.0.0.3:3000/sportlink/knvb/servlet/Postman|ROOT|\nLocal socket|socket|localhost:10000|ROOT|");
        store.setDefault(NAVAJO_PLUGIN_SCRIPT_INVOCATIONS, "InitNavajoStatus");

        store.setDefault(REMOTE_SERVERURL, "localhost:10000");
        store.setDefault(REMOTE_USERNAME, "ROOT");
        store.setDefault(REMOTE_PASSWORD, "");
        store.setDefault(REMOTE_PORT, "10000");

        //        return new ServerEntry[]{new ServerEntry("Ficus","http","10.0.0.3:3000/sportlink/knvb/servlet/Postman","ROOT",""),new ServerEntry("Local socket","socket","localhost:10000","ROOT","")};

        //      store.setDefault(P_NAVAJO_SERVERURL,
        // "193.172.187.148:3000/sportlink/knvb/servlet/Postman");
        //      store.setDefault(P_NAVAJO_USERNAME, "ROOT");
        //      store.setDefault(P_NAVAJO_PASSWORD, "");
     }

       protected void savePreferenceStore() {
           String ss = serializeServerEntries();
           myPreferences.setValue(NAVAJO_APPLICATION_SERVERS, ss);
           String sss = serializeServerInvocations();
           myPreferences.setValue(NAVAJO_PLUGIN_SCRIPT_INVOCATIONS, sss);
           
           if (myBuilder!=null) {
                        try {
                        myBuilder.updateMetaData(null);
                    } catch (NavajoPluginException e) {
                        e.printStackTrace();
                    } catch (CoreException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
             }            
           super.savePluginPreferences();
        }
       

       public void addServerEntry(String name, String protocol, String server, String username, String password) {
           ServerEntry se = new ServerEntry(name,protocol,server,username,password);
           if (myServerEntries==null) {
            myServerEntries = parseServerEntries();
        }
           myServerEntries.add(se);
           serverEntriyChanged(myServerEntries.size()-1);
       }

       public void deleteServerEntry(int index) {
           if (myServerEntries==null) {
            return;
           }
           if (index>=myServerEntries.size()) {
               return;
           }
           myServerEntries.remove(index);
           serverEntriyChanged(index);
       }

       public void updateServerEntry(int index, String name, String protocol, String server, String username, String password) {
           ServerEntry se = new ServerEntry(name,protocol,server,username,password);
           if (myServerEntries==null) {
            myServerEntries = parseServerEntries();
        }
           myServerEntries.remove(index);
           myServerEntries.add(se);
           serverEntriyChanged(myServerEntries.size()-1);
       }

       private void serverEntriyChanged(int index) {
           for (int i = 0; i < myServerEntryListeners.size(); i++) {
               IServerEntryListener current = (IServerEntryListener)myServerEntryListeners.get(i);
               current.serverEntryChanged(index);
              }
       }

       public void addServerEntryListener(IServerEntryListener ise) {
           myServerEntryListeners.add(ise);
       }
       public void removeServerEntryListener(IServerEntryListener ise) {
           myServerEntryListeners.remove(ise);
       }

       public ArrayList getScriptsStartingWith(String prefix) {
        if (scriptInvocations == null) {
            scriptInvocations = parseScriptInvocations();
        }
        ArrayList result = new ArrayList();
        for (int i = 0; i < scriptInvocations.size(); i++) {
            String current = (String) scriptInvocations.get(i);
            if (current.startsWith(prefix)) {
                result.add(current);
            }
        }
        return result;
    }

    private ArrayList parseScriptInvocations() {
        String invocations = myPreferences.getString(NAVAJO_PLUGIN_SCRIPT_INVOCATIONS);
        ArrayList res = new ArrayList();
        StringTokenizer st = new StringTokenizer(invocations,"\n");
        while (st.hasMoreTokens()) {
            res.add(st.nextToken());
        }
        return res;
    }
    
    public void addToScriptInvocations(String scriptName) {
        System.err.println("Adding to invocations: "+scriptName);
        if (scriptInvocations == null) {
            scriptInvocations = parseScriptInvocations();
        }
        if (!scriptInvocations.contains(scriptName)) {
            scriptInvocations.add(scriptName);
        }
        System.err.println("Size now: "+scriptName);
    }
    
    public IProject getDefaultNavajoProject() {
        return ResourcesPlugin.getWorkspace().getRoot().getProject(getPreferenceStore().getString(NAVAJO_DEFAULT_PROJECT_KEY));
    }

    
//    public void refreshCompilerClassLoader() {
//        if (myBuilder!=null) {
//            myBuilder.refreshCompilerClassLoader();
//        } else {
//            System.err.println("NO BUILDER FOUND!!!");
//        }
//    }

    public ClassProvider getClassProvider(IProject project) {
        ClassProvider p = (ClassProvider)classProviderMap.get(project);
        if (p!=null) {
            return p;
        }
        p = refreshCompilerClassLoader(project);
        classProviderMap.put(project, p);
        return p;
    }

    public NanoTslCompiler getNavajoCompiler(IProject project) {
        NanoTslCompiler p = (NanoTslCompiler)compilerProviderMap.get(project);
        if (p!=null) {
            return p;
        }
        System.err.println("No compiler found for project: "+project.getName());
        ClassProvider provider = getClassProvider(project);
        System.err.println("Created provider: "+provider.toString());
        provider.initializeJarResources();
        p = new NanoTslCompiler(provider);
        try {
            Class.forName("com.dexels.sportlink.adapters.FormConstructionMap",true,provider);
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        }
        
        System.err.println("Created compiler");
        ArrayList cpe = new ArrayList();
        Set classPathSet = new HashSet();
        IWorkspaceRoot ir = ResourcesPlugin.getWorkspace().getRoot();
        for (Iterator iter = provider.getClassPathEntries().iterator(); iter.hasNext();) {
            IPath element = (IPath) iter.next();
            if (classPathSet.contains(element.toOSString())) {
                continue;
            }
            classPathSet.add(element.toOSString());
            //                    IResource irr = ir.get
            IFile iff = ir.getFile(element);
            if (iff != null && iff.exists()) {
                cpe.add(iff.getRawLocation().toOSString());
            } else {
                IFolder fold = ir.getFolder(element);
                if (fold != null && fold.exists()) {
                    cpe.add(fold.getRawLocation().toOSString());
                } else {
                    System.err.println("HMMM: WHAT IS THIS: " + element);
                }
            }
        }
        
        try {
            IFolder adapters = NavajoScriptPluginPlugin.getDefault().getAdaptersFolder(project);
            IResource[] rr = adapters.members();
            for (int i = 0; i < rr.length; i++) {
                if (rr[i] instanceof IFile) {
                    IFile f = (IFile)rr[i];
                    cpe.add(f.getLocation().toOSString());
                }
            }
        } catch (NavajoPluginException e1) {
            e1.printStackTrace();
        } catch (CoreException e1) {
            e1.printStackTrace();
        }
        
//        p = new NanoTslCompiler(getClassProvider(project));
//        IFolder ff = NavajoScriptPluginPlugin.getDefault().getCompiledScriptFolder(project);
//        return ff.getRawLocation().toOSString();

        try {
            p.initJavaCompiler(NavajoScriptPluginPlugin.getDefault().getCompiledScriptFolder(project).getRawLocation().toOSString(), cpe,Main.class);
        } catch (NavajoPluginException e) {
            e.printStackTrace();
        }

        compilerProviderMap.put(project, p);
        return p;
    }

    
    public ClassProvider refreshCompilerClassLoader(IProject p) {
        try {
            if ( p.hasNature(NavajoScriptPluginPlugin.NAVAJO_NATURE)) {
                IFolder adapters = NavajoScriptPluginPlugin.getDefault().getAdaptersFolder( p);
                IFolder classes = NavajoScriptPluginPlugin.getDefault().getCompiledScriptFolder( p);
                ClassProvider classp = new ClassProvider( adapters.getLocation().toString(), classes.getLocation().toString(), false, (IProject) p);
                return classp;
            }
        } catch (CoreException e) {
            e.printStackTrace();
        } catch (NavajoPluginException e) {
            e.printStackTrace();
        }
        throw new IllegalStateException("Nature error while rebuilding classfiles!");
    }
    
//    public NanoTslCompiler refreshNanoCompiler(IProject p) {
//        ClassProvider classp = refreshCompilerClassLoader(p);
//        return new NanoTslCompiler(classp);
//    }
        
    
}
