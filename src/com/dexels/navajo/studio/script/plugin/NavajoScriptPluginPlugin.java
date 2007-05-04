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

import com.dexels.navajo.birt.BirtUtils;
import com.dexels.navajo.client.*;
import com.dexels.navajo.document.*;
import com.dexels.navajo.document.nanoimpl.*;
import com.dexels.navajo.functions.*;
import com.dexels.navajo.mapping.compiler.*;
import com.dexels.navajo.mapping.compiler.meta.*;
import com.dexels.navajo.parser.DefaultExpressionEvaluator;
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
    
    public static final String RELATIVE_REPORT_PATH = "/reports";    

    public static final String NAVAJO_CONFIG_PATH = "/config";

    public static final String NAVAJO_AUXILARY = "/auxilary";

    public static final String RELATIVE_METADATA_PATH = "/meta";

    public static final String COMPILED_SCRIPT_PATH = "/classes";

    public static final String SCRIPT_META_NAME = "scriptMeta.xml";

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

    public static final String PREF_JVM_PARAMETERS_KEY = "jvmParam";

    public static final String PREF_JVM_CLASSPATH_KEY = "jvmClasspath";

    public static final String PREF_JVM_BOOTCLASSPATH_KEY = "jvmBootClasspath";

    // These are related with Markers. Don't remember what they mean.
    public static final String KEY = "key";

    public static final String VIOLATION = "violation";

    private static final String NAVAJO_PLUGIN_SCRIPT_INVOCATIONS = "navajoPluginScriptInvocations";

    public static final String NAVAJO_DEFAULT_PROJECT_KEY = "navajoDefaultProject";

     private Launch currentFunctionLaunch = null;

    private Launch currentSocketLaunch = null;
    private TslMetaDataHandler metaDataHandler = new TslMetaDataHandler();
   
    /** @deprecated */
    private Launch currentScriptLaunch = null;

    private NavajoNature myNature = null;

    private NavajoBuilder myBuilder = null;

    private ClientInterface localClient = null;

    private TmlViewer currentTmlViewer;

    private TmlBrowser currentTmlBrowser;

    private MetaDataViewer currentMetaDataViewer;

    private ArrayList myServerEntries = null;
     private final ArrayList myServerEntryListeners = new ArrayList();
    
    private IPreferenceStore myPreferences  = null;
    
    private ArrayList scriptInvocations = null;
    
    
    private final Map classProviderMap = new HashMap();

    private final Map compilerProviderMap = new HashMap();

    private boolean questionResult;

    private int count = 0;

    private final Map navajoProjectRootMap = new HashMap();

    private final Map serverXmlNavajoMap = new HashMap();

	private String selectedLocale;
    
    public  static final String DOC_IMPL = "com.dexels.navajo.DocumentImplementation";
    public static final String NANO = "com.dexels.navajo.document.nanoimpl.NavajoFactoryImpl";
    public static final String JAXP = "com.dexels.navajo.document.jaxpimpl.NavajoFactoryImpl";
    public static final String QDSAX = "com.dexels.navajo.document.base.BaseNavajoFactoryImpl";

	
    
    
    
    
    /**
     * The constructor.
     */
    public NavajoScriptPluginPlugin() {
        super();
         System.err.println("Started NavajoScriptPlugin at: " + new Date());
        System.setProperty("com.dexels.navajo.DocumentImplementation", "com.dexels.navajo.document.base.BaseNavajoFactoryImpl");
//        System.setProperty("com.dexels.navajo.DocumentImplementation", "com.dexels.navajo.document.nanoimpl.NavajoFactoryImpl");
        NavajoFactory.getInstance().setExpressionEvaluator(new DefaultExpressionEvaluator());

        System.setProperty("com.dexels.navajo.propertyMap", "com.dexels.navajo.studio.script.plugin.propertymap");
        plugin = this;
        }

    /** @deprecated */
    public Launch runNavajo(String className, IFile scriptFile) throws CoreException, NavajoPluginException {
        return runNavajo(className, scriptFile, null);
    }

    /** @deprecated */
    public Launch runNavajo(String classRunner, IFile scriptFile, IFile sourceTml) throws CoreException, NavajoPluginException {
        String name = null;
        if ("tml".equals(scriptFile.getFileExtension())) {
            name = getScriptNameFromTml(sourceTml, scriptFile.getProject());
            scriptFile = getScriptFolder(scriptFile.getProject()).getFile(name);
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
                final IFile f = tml.getFile(scriptName + ".tml");
                System.err.println("check this...");
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
                        
                    }
                }
                try {
                    showTml(f,scriptName);
                } catch (Exception e) {
                     e.printStackTrace();
                }
                 return Status.OK_STATUS;
            }

        };

        currentScriptLaunch = runNavajoBootStrap(classRunner, true, scriptFile.getProject(), name, location, job, relTmlLocation, null);
        return currentScriptLaunch;
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
                    log("Error showing viewer: "+id,e);
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
        }
        IClasspathEntry[] ice = jp.getResolvedClasspath(true);
        current.add(jp.getOutputLocation());
        for (int i = 0; i < ice.length; i++) {
            if (ice[i].getEntryKind() == IClasspathEntry.CPE_PROJECT) {
                IProject prj = ResourcesPlugin.getWorkspace().getRoot().getProject(ice[i].getPath().toString());
                IJavaProject project = JavaCore.create(prj);
                resolveProject(outputPaths, current, project);
                continue;
            }
            if (!current.contains(ice[i].getPath())) {
             current.add(ice[i].getPath());
         
            }
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
        Launch scriptLaunch = VMLauncherUtility.runVM("Navajo inline", runClassName, classpath, bootClasspath, jvmArguments.toString(),
                programArguments.toString(), getSourceLocator(), isDebugMode(), showInDebugger, job);
        return scriptLaunch;
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
                    log("Error opening file in editor: "+f.getFullPath().toString(),e);
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

    public void log(String msg) {
        super.getLog().log(new Status(Status.OK,"navajo",0,msg,null));
    }

    public void log(Throwable s) {
        System.err.println("Error logged.");
        super.getLog().log(new Status(Status.ERROR,"navajo",0,"No message",s));
    }
    public void log(String message, Throwable s) {
        System.err.println("Error logged.");
        super.getLog().log(new Status(Status.ERROR,"navajo",0,message,s));
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
//    public void stop(BundleContext context) throws Exception {
//        super.stop(context);
//    }

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

    public void refreshNavajoRootPath(IProject prj) throws NavajoPluginException {
        count++;
        System.err.println("refreshNavajoRoot called: "+count);
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
            throw new NavajoPluginException("Error accessing navajo project: Navajo nature present, but no .navajoroot file!");
        }
        
        try {
//            iff.refreshLocal(0, null);
            if (!iff.exists()) {
                throw new NavajoPluginException("Error accessing navajo project: Navajo nature present, but no .navajoroot file. Project: "+prj.getName());
            }
            iss = iff.getContents();
            isr = new BufferedReader(new InputStreamReader(iss));
            String rr = isr.readLine();
            navajoProjectRootMap.put(prj, rr);
//            return rr;
        } catch (CoreException e) {
            throw new NavajoPluginException("Error accessing navajo project: Navajo nature present, but no .navajoroot file!",e);
        } catch (IOException e) {
            throw new NavajoPluginException("Error accessing navajo project: Navajo nature present, but no .navajoroot file!",e);
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
    
    public String getNavajoRootPath(IProject prj) throws NavajoPluginException {
        String root = (String)navajoProjectRootMap.get(prj);
        if (root==null) {
            refreshNavajoRootPath(prj);
            return (String)navajoProjectRootMap.get(prj);
        }
        return root;
    }

    /**
     * @return Returns the scriptPath.
     */
    public String getScriptPath(IProject prj)  throws NavajoPluginException{
        return getNavajoRootPath(prj) + NAVAJO_AUXILARY + RELATIVE_SCRIPT_PATH;
    }    
 
    public String getReportPath(IProject prj)  throws NavajoPluginException{
        return getNavajoRootPath(prj) + NAVAJO_AUXILARY + RELATIVE_REPORT_PATH;
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
        IFolder iff = p.getFolder(getAdaptersPath(p));
        if (!iff.exists()) {
            try {
                iff.create(true,false, null);
            } catch (CoreException e) {
                throw new NavajoPluginException("Error creating adapters folder: "+iff,e);
            }
        }
        return iff;
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

    
    public IFile getReportFile(IProject p, String path)  throws NavajoPluginException{
        IFolder iff = p.getFolder(getReportPath(p));
        IFile ifff = iff.getFile(path + ".rptdesign");
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
        InputStream iss = null;
        Navajo n = null;
        try {
            iss = iff.getContents();
            n = NavajoFactory.getInstance().createNavajo(iss);
        } catch (CoreException e) {
            throw new NavajoPluginException("Error loading TML file: "+scriptName);
        } finally {
            if (iss!=null) {
                try {
                    iss.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
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
                    log("Error deleting resource: "+ir,e);
                    return new Status(IStatus.ERROR, "com.dexels.plugin", -1, "Error deleting resource", e);
                }
                return Status.OK_STATUS;
            }
        };
        job.setRule(ir.getProject());
        job.schedule();

    }

    public void showTml(IFile tmlFile,String scriptName) throws Exception {
    	System.err.println("in showtml");
    	InputStream is = null;
       if (tmlFile == null) {
            return;
        }
        Navajo n = null;
        try {
            if (!tmlFile.isSynchronized(0)) {
                tmlFile.refreshLocal(0, null);
            }
            if (!tmlFile.exists()) {
                return;
            }
            is = tmlFile.getContents();
            if (is==null) {
                return;
            }
            System.err.println("Opening tml: "+tmlFile.getLocation());
            n = NavajoFactory.getInstance().createNavajo(is);
            is.close();
        } catch (CoreException e) {
            log("Error showing tml: "+scriptName,e);
             return;
        } catch (IOException e) {
            log("Error showing tml: "+scriptName,e);
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
    	System.err.println("ShowTml: "+tmlFile.getLocation().toString());
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
    
    public void logErrorMessage(String msg, Throwable t) {
        super.getLog().log(new Status(Status.ERROR,"navajo",0,msg,t));
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
        IProjectDescription description = project.getDescription();
        List newIds = new ArrayList();
        newIds.addAll(Arrays.asList(description.getNatureIds()));
        int index = newIds.indexOf(NavajoScriptPluginPlugin.NAVAJO_NATURE);
        if (index == -1) {
             newIds.add(0,NavajoScriptPluginPlugin.NAVAJO_NATURE);
            createDefaultServerConfigFiles(project, suppressDialogs,root);
        } else {
             return;
        }
        project.refreshLocal(IResource.DEPTH_INFINITE, null);
        description.setNatureIds((String[]) newIds.toArray(new String[newIds.size()]));
        try {
            project.setDescription(description, null);
        } catch (CoreException e) {
            log("Error adding navajo nature",e);
        }
    }

    public void removeNavajoNature(IProject project) throws CoreException {
         IProjectDescription description = project.getDescription();
         // Toggle the nature.
        List newIds = new ArrayList();
        newIds.addAll(Arrays.asList(description.getNatureIds()));
        int index = newIds.indexOf(NavajoScriptPluginPlugin.NAVAJO_NATURE);
        if (index == -1) {
           } else {
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
             log("Error removeing navajo nature: "+project.getName(),e);

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
    public void showError(String message, Throwable t) {
        showError("Trouble in the Tipi:", message+"\n(stacktrace logged.)");
        log(t);
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
        Map m = new HashMap();
        if (tce!=null) {
            m.put("tslCompileException", tce);
        }
//        m.put("aap", "noot");
        MarkerUtilities.setMessage(m,  msg + ": ");
        m.put(IMarker.SEVERITY, new Integer( IMarker.SEVERITY_ERROR ));
        if (tce!=null) {
            MarkerUtilities.setCharStart(m, tce.getStartOffset());
            MarkerUtilities.setCharEnd(m, tce.getEndOffset());
            
            m.put("code", new Integer(tce.getCode()));
        }
//        MarkerUtilities.createMarker(loc, m, "com.dexels.plugin.tslproblemmarker");
        MarkerUtilities.createMarker(loc, m, "com.dexels.plugin.TslMarker");

//        IMarker[] imm =  loc.findMarkers("com.dexels.plugin.tslproblemmarker", false, IResource.DEPTH_INFINITE);
//        System.err.println("immsize: "+imm.length+"imm::: "+imm[0].getAttributes());
             }

    public void setTmlViewer(TmlViewer tv) {
        currentTmlViewer = tv;
    }
    
    public TmlViewer getTmlViewer() {
        return currentTmlViewer;
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
           currentMetaDataViewer.showScript(file, scriptName, metaDataHandler);
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
    
    public void refreshServerXmlNavajo(IProject p) {
        Navajo n = null;
        serverXmlNavajoMap.remove(p);
       try {
            n = loadNavajo(getServerXml(p));
        } catch (NavajoPluginException e) {
            log("Error reading server.xml for project: "+p.getName(), e);
            return;
        }
        if (n==null) {
            log("Error reading server.xml for project: "+p.getName());
            return;
        }
        serverXmlNavajoMap.put(p, n);
    }
    
    public Navajo getServerXmlNavajo(IProject p) {
        Navajo n = (Navajo)serverXmlNavajoMap.get(p);
        if (n!=null) {
            return n;
        }
        refreshServerXmlNavajo(p);
        n = (Navajo)serverXmlNavajoMap.get(p);
        return n;
    }
    
    public String getDefaultNavajoUser(IProject p)  {
        Navajo n = getServerXmlNavajo(p);
       if (n==null) {
           return null;
         }
        Property prop = n.getProperty("server-configuration/plugin/defaultUser");
        if (p==null) {
            log("Missing username in the server.xml. (Property server-configuration/plugin/defaultUser is missing!)");
            return null;
        } else {
            String user = prop.getValue();
            if (user==null) {
                log("Missing username in the server.xml. (Property server-configuration/plugin/defaultUser has null value!)");
            }
            return user;
        }
        
    }
    
    
    public String getDefaultNavajoPassword(IProject p) {
        Navajo n = getServerXmlNavajo(p);
        if (n==null) {
            return null;
          }
        Property prop = n.getProperty("server-configuration/plugin/defaultPassword");
        if (p==null) {
             log("Missing password in the server.xml. (Property server-configuration/plugin/defaultPassword is missing!)");
             return null;
         } else {
             String user = prop.getValue();
             if (user==null) {
                 log("Missing password in the server.xml. (Property server-configuration/plugin/defaultPassword has null value!)");
             }
             return user;
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
    
    public void runRemoteNavajo(final IFile file, final String scriptName) throws CoreException {
        boolean ok = true;
        if (file==null) {
            ok = false;
        }
        if (ok && !file.exists()) {
            ok = false;
        }
        IProject ipp = file.getProject();
        if (ok && !ipp.hasNature(NavajoScriptPluginPlugin.NAVAJO_NATURE)) {
            ok = false;
        }
        if (ok) {
            Job job = new Job("Running "+scriptName+"...") {
                protected IStatus run(IProgressMonitor monitor) {
                    Navajo res = runRemoteNavajo(file.getProject(), scriptName, null,null);
//                        NavajoScriptPluginPlugin.getDefault().showTml(file, res,scriptName);
                    return Status.OK_STATUS;
                }
            };
            job.schedule();
           
        } else {
            NavajoScriptPluginPlugin.getDefault().showError("I don't know which project you mean. Select a file in the navigator,\nwhich is a child of a navajo-project.\n\nThen start the socket runner again.");
        }
   }    
    public Navajo runRemoteNavajo(IProject ipp, String scriptName, IFile sourceTml, String sourceName) {
            try {
                if (currentSocketLaunch==null || currentSocketLaunch.isTerminated()) {
                    questionResult = false;
                    getWorkbench().getDisplay().syncExec(new Runnable(){
                        public void run() {
                            questionResult  = showQuestion("No running instance found!", "Start socket runner?");
                        }});
                    if (questionResult) {
                                    startSocketRunner(ipp);
                                    Thread.sleep(4000);
                    }
                 }  
                
                NavajoClientFactory.resetClient();
                System.setProperty(DOC_IMPL,QDSAX);
                NavajoFactory.resetImplementation();
                NavajoFactory.getInstance().setExpressionEvaluator(new DefaultExpressionEvaluator());
                NavajoClientFactory.createClient("com.dexels.navajo.client.NavajoSocketClient", null);
                NavajoClientFactory.getClient().setServerUrl(getRemoteServer());
                NavajoClientFactory.getClient().setUsername(getRemoteUsername());
                NavajoClientFactory.getClient().setPassword(getRemotePassword());
                Navajo in = null;
                if (sourceTml!=null && sourceTml.exists()) {
                    in = loadNavajo(sourceTml);
//                    in = NavajoFactory.getInstance().createNavajo(sourceTml.getContents());
                } else {
//                    System.err.println("Running init script with empty navajo...");
                    in = NavajoFactory.getInstance().createNavajo();
                }
                Navajo result = NavajoClientFactory.getClient().doSimpleSend(in, getRemoteServer(),scriptName,getRemoteUsername(),getRemotePassword(),-1,false);
                if (sourceName!=null&& !"".equals(sourceName)) {
                    result.getHeader().setAttribute("sourceScript", sourceName);
                }
                if (currentTmlViewer != null) {
                    currentTmlViewer.setListeningForResourceChanges(false);
                }
                
                IFile tml = getTmlFile(ipp, scriptName);
                if (tml==null) {
                    System.err.println("TmlFile not locatable for script: "+scriptName);
                }
                String path = tml.getLocation().toOSString();
                File fff =  new File(path).getParentFile();
                if (fff!=null) {
                    fff.mkdirs();
                }
                if (currentTmlViewer != null) {
                    currentTmlViewer.setListeningForResourceChanges(false);
                }
                FileWriter fw = new FileWriter(path);
                result.write(fw);
                fw.flush();
                fw.close();
                showTml(tml, result,scriptName);
                return result;
            } catch (Exception e) {
                log("Problem while doing a runRemoteNavajo. ",e);
            }
        return null;
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
     }

       protected void savePreferenceStore() {
           String ss = serializeServerEntries();
           myPreferences.setValue(NAVAJO_APPLICATION_SERVERS, ss);
           String sss = serializeServerInvocations();
           myPreferences.setValue(NAVAJO_PLUGIN_SCRIPT_INVOCATIONS, sss);
           
           if (myBuilder!=null) {
//                        try {
                            // TODO REINSTATE saving metadata, loop through project compiler map
                            
//                        myBuilder.updateMetaData(null);
//                    } catch (NavajoPluginException e) {
//                        log("Error saving preferences. ",e);
//                    } catch (CoreException e) {
//                        log("Error saving preferences. ",e);
//                    } catch (IOException e) {
//                        log("Error saving preferences. ",e);
//                    }
             }            
           super.savePluginPreferences();
        }
       
       public TslMetaDataHandler getMetaDataHandler() {
           return metaDataHandler;
       }
       public void updateMetaData(IProgressMonitor ipm, IProject ip) throws NavajoPluginException, CoreException, IOException {
           IFolder iff = NavajoScriptPluginPlugin.getDefault().getAuxilaryFolder(ip);
           IFolder meta = iff.getFolder("meta");
           if (!meta.exists()) {
               meta.create(false, true, ipm);
           }
           IFile adapterUses = meta.getFile("adapters.xml");
           storeXML(metaDataHandler.getAdaptersUsedByScript(), adapterUses);
           IFile metatotal = meta.getFile("scriptMeta.xml");
           XMLElement xe = metaDataHandler.createTotalXML();
           storeXML(xe, metatotal);
           meta.refreshLocal(1, ipm);
       }
       private void storeXML(XMLElement xe, IFile dest) throws NavajoPluginException {
           File ff = dest.getLocation().toFile();
           if (ff == null) {
               throw new NavajoPluginException("Metadata error.");
           }
           FileWriter fw = null;
           try {
               fw = new FileWriter(ff);
               xe.write(fw);
               fw.flush();
           } catch (IOException e) {
               NavajoScriptPluginPlugin.getDefault().log("Error writing metadata",e);
           } finally {
               try {
                   if (fw != null) {
                       fw.close();
                   }
               } catch (IOException e1) {
                   NavajoScriptPluginPlugin.getDefault().log("Error writing metadata",e1);
               }
           }

       }
       public int addServerEntry(String name, String protocol, String server, String username, String password) {
           ServerEntry se = new ServerEntry(name,protocol,server,username,password);
           if (myServerEntries==null) {
            myServerEntries = parseServerEntries();
        }
           myServerEntries.add(se);
           serverEntriyChanged(myServerEntries.size()-1);
           return myServerEntries.size();
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
    
    /** @deprecated */
    public IProject getDefaultNavajoProject() {
        String name = getPreferenceStore().getString(NAVAJO_DEFAULT_PROJECT_KEY);
        try {
            IProject p = ResourcesPlugin.getWorkspace().getRoot().getProject(name);
            if (p!=null && p.exists() && p.isOpen()) {
                return p;
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        try {
            ArrayList al = getProjectsByNature(NAVAJO_NATURE);
            if (al.size()==1) {
                IProject p =(IProject)al.get(0);
                return p;
            }
        } catch (CoreException e) {
            log(e);
            showError("Can not open default project. Select the default project in the preferences");
            e.printStackTrace();
        }
//        showError("Can not open default project. Select the default project in the preferences");
        return null;
        }

    
//    public void refreshCompilerClassLoader() {
//        if (myBuilder!=null) {
//            myBuilder.refreshCompilerClassLoader();
//        } else {
//            System.err.println("NO BUILDER FOUND!!!");
//        }
//    }

    public ClassProvider getClassProvider(IProject project, boolean forceRefresh) {
        ClassProvider p = (ClassProvider)classProviderMap.get(project);
        if (p!=null && !forceRefresh) {
            return p;
        }
        p = refreshCompilerClassLoader(project);
        classProviderMap.put(project, p);
        return p;
    }

    public void refreshNavajoCompiler(IProject project) {
        compilerProviderMap.remove(project);
    }
    
    public NanoTslCompiler getNavajoCompiler(IProject project) {
        NanoTslCompiler p = (NanoTslCompiler)compilerProviderMap.get(project);
        if (p!=null) {
//            log("Using existing compiler.");
            return p;
        }
//        log("Create new navajo compiler.");
        // TODO Check
        ClassProvider provider = getClassProvider(project,false);
         provider.initializeJarResources();
//         try {
//            Class c = Class.forName("com.dexels.navajo.mapping.compiler.NanoTslCompiler");
//            Object o = c.newInstance();
//            p = (NanoTslCompiler) o;
//        } catch (Exception e) {
//            e.printStackTrace();
//            log("Error instantiating compiler",e);
//        }

         p = new NanoTslCompiler(provider);
         
        // ======================= SETUP METADATA HANDLER
        InputStream metaIn = null;
        try {
            IFile iff = getScriptMetadataFile(project);
            if (iff != null && iff.exists()) {
                iff.refreshLocal(0, null);
                metaIn = iff.getContents();
                metaDataHandler.loadScriptData(metaIn);
            }
        } catch (Exception e) {
            NavajoScriptPluginPlugin.getDefault().log(e);
        } finally {
            if (metaIn != null) {
                try {
                    metaIn.close();
                } catch (IOException e1) {
                    NavajoScriptPluginPlugin.getDefault().log(e1);
                }
            }
        }
        // =====================
        
        p.addMetaDataListener(metaDataHandler);
        
//         p = new NanoTslCompiler(provider);
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
            log("Error reading adapter folder: ",e1);
        } catch (CoreException e1) {
            log("Error saving preferences. ",e1);
        }
        try {
            p.initJavaCompiler(NavajoScriptPluginPlugin.getDefault().getCompiledScriptFolder(project).getRawLocation().toOSString(), cpe,Main.class);
        } catch (NavajoPluginException e) {
            log("Error initializing java compiler. ",e);
        }
        compilerProviderMap.put(project, p);
        return p;
    }

    
    public ClassProvider refreshCompilerClassLoader(IProject p) {
        try {
            if ( p.hasNature(NavajoScriptPluginPlugin.NAVAJO_NATURE)) {
                IFolder adapters = NavajoScriptPluginPlugin.getDefault().getAdaptersFolder( p);
                IFolder classes = NavajoScriptPluginPlugin.getDefault().getCompiledScriptFolder( p);
                ClassProvider classp = new ClassProvider( adapters.getLocation().toString(), classes.getLocation().toString(), false, (IProject) p,getClass().getClassLoader());
                return classp;
            }
        } catch (CoreException e) {
            log("Error rebuilding compiler classloader. ",e);
        } catch (NavajoPluginException e) {
            log("Error rebuilding compiler classloader. ",e);
        }
        throw new IllegalStateException("Nature error while rebuilding classfiles!");
    }
    
    public void startSocketRunner(IProject p) throws DebugException {
        if (getCurrentSocketLaunch() != null) {
            getCurrentSocketLaunch().terminate();
            setCurrentSocketLaunch(null);
        }
        String n = p.getName();
        getPreferenceStore().setValue(NAVAJO_DEFAULT_PROJECT_KEY,n);
        Launch lll = null;
        
        try {
        	System.err.println("Starting navajo runner. Port: "+getRemotePort()+" server.xml : "+getServerXml(p).getLocation().toOSString());
//             logMessage("Navajo Integrator Launched at: "+new Date());
            lll = runNavajoBootStrap("com.dexels.navajo.client.socket.NavajoSocketLauncher", true, p,
                    "", "", null,null, new String[]{""+getRemotePort(),getServerXml(p).getLocation().toOSString()});
        } catch (Throwable e) {
            log("Error starting socket runner. ",e);
        }
        setCurrentSocketLaunch(lll);
    }

//    public void createEmptyReport(Navajo n, File reportFolder, String reportName, String serviceName ) throws IOException, NavajoException {
    	
	public void createReport(IProject p, String name, Navajo n, File sourceFile) throws NavajoPluginException, IOException, NavajoException, PartInitException {
		 IFolder iff = p.getFolder(getReportPath(p));
		 
			System.err.println("CREATING REPORT...");
		BirtUtils b = new BirtUtils();
		System.err.println("BIRT_UTILS_FOUND");
		b.createEmptyReport(n, iff.getLocation().toFile(), name);
		try {
			iff.refreshLocal(IResource.DEPTH_INFINITE, null);
		} catch (CoreException e) {
			e.printStackTrace();
		}
		IFile rep = getReportFile(p, name+".rptdesign");
//		if(rep==null || !rep.exists()) {
//			showError("Dat ging even mis...");
//		} else {
//		}
		
		IDE.openEditor(getWorkbench().getActiveWorkbenchWindow().getActivePage(), rep);
		
	}

	public String getSelectedLocale() {
		return selectedLocale;
	}
	
	public void setSelectedLocale(String l) {
		selectedLocale = l;
	}

	public String[] getLocales() {
		return new String[]{"nl","en","fr","es","pt","ar"};
	}    
    
}
