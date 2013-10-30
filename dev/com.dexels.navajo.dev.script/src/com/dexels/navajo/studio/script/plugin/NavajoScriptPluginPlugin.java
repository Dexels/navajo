package com.dexels.navajo.studio.script.plugin;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.Launch;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Header;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.mapping.compiler.meta.TslMetaDataHandler;
import com.dexels.navajo.parser.DefaultExpressionEvaluator;
import com.dexels.navajo.studio.eclipse.INavajoActivityListener;
import com.dexels.navajo.studio.eclipse.IServerEntryListener;
import com.dexels.navajo.studio.eclipse.NavajoBuilder;
import com.dexels.navajo.studio.eclipse.ServerEntry;
import com.dexels.navajo.studio.script.plugin.views.TmlBrowser;

/**
 * The main plugin class to be used in the desktop.
 */
public class NavajoScriptPluginPlugin extends AbstractUIPlugin {
    //The shared instance.
    private static NavajoScriptPluginPlugin plugin;

    
	private final static Logger logger = LoggerFactory
			.getLogger(NavajoScriptPluginPlugin.class);
    
    //Resource bundle.
    private ResourceBundle resourceBundle = null;

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

//    public static final String NAVAJO_AUXILARY = "/auxilary";
    public static final String NAVAJO_AUXILARY = "";

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
   

    private NavajoBuilder myBuilder = null;


    private TmlBrowser currentTmlBrowser;


    private ArrayList<ServerEntry> myServerEntries = null;
     private final List<IServerEntryListener> myServerEntryListeners = new ArrayList<IServerEntryListener>();
    
    private IPreferenceStore myPreferences  = null;
    
    private List<String> scriptInvocations = null;
    
    
//    private final Map classProviderMap = new HashMap();

//    private final Map compilerProviderMap = new HashMap();

//    private boolean questionResult;

    private int count = 0;

    private final Map<IProject,String> navajoProjectRootMap = new HashMap<IProject,String>();

    private final Map<IProject,Navajo> serverXmlNavajoMap = new HashMap<IProject,Navajo>();

	private String selectedLocale;
    
    public  static final String DOC_IMPL = "com.dexels.navajo.DocumentImplementation";
    public static final String NANO = "com.dexels.navajo.document.nanoimpl.NavajoFactoryImpl";
    public static final String JAXP = "com.dexels.navajo.document.jaxpimpl.NavajoFactoryImpl";
    public static final String QDSAX = "com.dexels.navajo.document.base.BaseNavajoFactoryImpl";

	private final List<INavajoActivityListener> navajoActivityListeners = new ArrayList<INavajoActivityListener>();
    
    
    
    
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

       public void openTmlViewer() {
        openViewer("com.dexels.TmlViewer");
    }
    
    public void openMetaDataViewer() {
        openViewer("com.dexels.MetaDataViewer");
    }
    
    public void openTmlClientViewer() {
        openViewer("com.dexels.TmlClientView");
    }

    public void injectNavajoResponse(Navajo n, String scriptName, IProject currentProject) {
    	for (INavajoActivityListener nn : navajoActivityListeners) {
			nn.navajoResponse(n,scriptName,currentProject);
		}
    }
    
    public void addNavajoActivityListener(INavajoActivityListener il) {
    	navajoActivityListeners.add(il);
    }

    public void removeNavajoActivityListener(INavajoActivityListener il) {
    	navajoActivityListeners.remove(il);
    }

    
    public void openViewer(final String id) {
        getWorkbench().getDisplay().syncExec(new Runnable() {

            @Override
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

//    /**
//     * @return
//     */
//    private ISourceLocator getSourceLocator() {
//        return null;
//    }

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

//    /**
//     * @return
//     */
//    private boolean isDebugMode() {
//        return false;
//    }

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
    @Override
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
//        System.err.println("Setting up...");
//        IPreferenceStore ips = NavajoScriptPluginPlugin.getDefault().getPreferenceStore();
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
    	if(plugin==null) {
    		plugin = new NavajoScriptPluginPlugin();
    	}
        return plugin;
    }
    
    public static IWorkbench getDefaultWorkbench() {
        return PlatformUI.getWorkbench();
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
//                throw new NavajoPluginException("Error accessing navajo project: Navajo nature present, but no .navajoroot file. Project: "+prj.getName());
            	System.err.println("No .navajoroot found assuming project dir.");
            	return;
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
            	logger.error("Error: ", e1);
            }
        }
    }
    
    public String getNavajoRootPath(IProject prj) throws NavajoPluginException {
        String root = navajoProjectRootMap.get(prj);
        if (root==null) {
            refreshNavajoRootPath(prj);
            root = navajoProjectRootMap.get(prj);
            if(root==null) {
            	return "";
            }
            return root;
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
        return getNavajoRootPath(prj) + NAVAJO_AUXILARY + RELATIVE_COMPILED_PATH;
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
                	logger.error("Error: ", e);
                }
            }
        }
        return n;
    }

    public String getScriptName(IFile script, IProject project)  throws NavajoPluginException{
        List<String> al = new ArrayList<String>();
        StringBuffer sb = new StringBuffer();
        IFolder scriptDir = project.getFolder(getScriptPath(project));
        IResource ir = script;

        while (!ir.equals(scriptDir)) {
            al.add(ir.getName());
            ir = ir.getParent();
        }
        for (int i = al.size() - 1; i >= 0; i--) {
            String current = al.get(i);
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

    /** @param project 
     * @deprecated */
    public String getScriptNameFromTml(IFile tml, IProject project) {
        List<String> al = new ArrayList<String>();
        StringBuffer sb = new StringBuffer();
        IResource ir = tml;
        while (ir != null ) {
            al.add(ir.getName());
            ir = ir.getParent();
        }
        for (int i = al.size() - 1; i >= 0; i--) {
            String current = al.get(i);
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
        List<String> al = new ArrayList<String>();
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
            String current = al.get(i);
            if (i != al.size() - 1) {
                sb.append("/");
            }
            sb.append(current);
        }
        String buffer = sb.toString();
        if (buffer.endsWith(".xml") || buffer.endsWith(".tml")|| buffer.endsWith(".tsl")) {
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
            boolean isXml = ff.getFileExtension().equals("xml") || ff.getFileExtension().equals("tsl");
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
    



    public static List<IProject> getProjectsByNature(String nature) throws CoreException {
        List<IProject> al = new ArrayList<IProject>();
        IProject[] pp = ResourcesPlugin.getWorkspace().getRoot().getProjects();
        for (int i = 0; i < pp.length; i++) {
            logger.info("Checking project: " + pp[i].getName() + " for nature: " + nature + " class: " + pp[i].getClass());
            if (pp[i].isOpen()) {
                IProjectDescription ipd = pp[i].getDescription();
                String[] aaa = ipd.getNatureIds();
                if (aaa.length == 0) {
                    System.err.println("No ids found");
                }
                for (int j = 0; j < aaa.length; j++) {
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

    public static List<IProject> getNavajoProjects() throws CoreException {
        return getProjectsByNature(NAVAJO_NATURE);
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
                throw new CoreException(Status.CANCEL_STATUS);
            }

        }
    }

    public void addNavajoNature(IProject project) throws CoreException {
        IProjectDescription description = project.getDescription();
        List<String> newIds = new ArrayList<String>();
        newIds.addAll(Arrays.asList(description.getNatureIds()));
        int index = newIds.indexOf(NavajoScriptPluginPlugin.NAVAJO_NATURE);
        if (index == -1) {
             newIds.add(0,NavajoScriptPluginPlugin.NAVAJO_NATURE);
        } else {
             return;
        }
        project.refreshLocal(IResource.DEPTH_INFINITE, null);
        description.setNatureIds(newIds.toArray(new String[newIds.size()]));
        try {
            project.setDescription(description, null);
        } catch (CoreException e) {
            log("Error adding navajo nature",e);
        }
    }

    public void removeNavajoNature(IProject project) throws CoreException {
         IProjectDescription description = project.getDescription();
         // Toggle the nature.
        List<String> newIds = new ArrayList<String>();
        newIds.addAll(Arrays.asList(description.getNatureIds()));
        int index = newIds.indexOf(NavajoScriptPluginPlugin.NAVAJO_NATURE);
        if (index == -1) {
           } else {
            newIds.remove(index);
        }
        description.setNatureIds(newIds.toArray(new String[newIds.size()]));
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
            @Override
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
            @Override
			public void run() {
                MessageDialog.openConfirm(getWorkbench().getDisplay().getActiveShell(), title, message);
            }});
        return true;
    }

    public void showError(final String title, final String message) {
        getWorkbench().getDisplay().syncExec(new Runnable() {
            @Override
			public void run() {
                MessageDialog.openError(getWorkbench().getDisplay().getActiveShell(), title, message);
            }
        });
    }

    public void showWarning(final String title, final String message) {
        getWorkbench().getDisplay().syncExec(new Runnable() {
            @Override
			public void run() {
                MessageDialog.openWarning(getWorkbench().getDisplay().getActiveShell(), title, message);
            }
        });
    }

    public boolean isScriptExisting(IProject ipp, String scriptName) throws NavajoPluginException {
        IFile dd = getScriptFile(ipp, scriptName);
        return dd.exists();
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
        Navajo n = serverXmlNavajoMap.get(p);
        if (n!=null) {
            return n;
        }
        refreshServerXmlNavajo(p);
        n = serverXmlNavajoMap.get(p);
        return n;
    }
    
    public String getDefaultNavajoUser(IProject p)  {
        Navajo n = getServerXmlNavajo(p);
       if (n==null) {
           return null;
         }
        Property prop = n.getProperty("server-configuration/plugin/defaultUser");
        if (prop==null) {
            log("Missing username in the server.xml. (Property server-configuration/plugin/defaultUser is missing!)");
            return "Unknown";
//            return null;
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
        if (prop==null) {
             log("Missing password in the server.xml. (Property server-configuration/plugin/defaultPassword is missing!)");
             return "Unknown";
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
        	logger.error("Error: ", e);
        } finally {
            try {
                if (s!=null) {
                    s.close();
                }
            } catch (IOException e1) {
            	logger.error("Error: ", e1);
            }
         }
        return null;
    }
    
    @Override
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
    

    public List<ServerEntry> getServerEntries() {
        if (myServerEntries==null) {
            myServerEntries = parseServerEntries();
        }
        return myServerEntries;
    }
  
    private ArrayList<ServerEntry> parseServerEntries() {
        String entries = getPreferenceStore().getString(NAVAJO_APPLICATION_SERVERS);
        StringTokenizer st = new StringTokenizer(entries,"\n");
        ArrayList<ServerEntry> entryList = new ArrayList<ServerEntry>();
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
                ServerEntry current = myServerEntries.get(i);
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
            String current = scriptInvocations.get(i);
            sb.append(current);
            sb.append("\n");
        }
        return sb.toString();
        
}
    
    
    
       @Override
	@SuppressWarnings("deprecation")
	protected void initializeDefaultPreferences(IPreferenceStore store) {
           super.initializeDefaultPreferences(store);
         if (store == null) {
            return;
        }
        myPreferences = store;
        store.setDefault(P_NAVAJO_PATH, "navajo-tester");
        store.setDefault(NAVAJO_APPLICATION_SERVERS,"LocalJetty|http|localhost:8888/Postman||\n");
        store.setDefault(NAVAJO_PLUGIN_SCRIPT_INVOCATIONS, "InitNavajoStatus");

        store.setDefault(REMOTE_SERVERURL, "localhost:10000");
        store.setDefault(REMOTE_USERNAME, "ROOT");
        store.setDefault(REMOTE_PASSWORD, "");
        store.setDefault(REMOTE_PORT, "10000");
     }

       @Override
	@SuppressWarnings("deprecation")
	protected void savePreferenceStore() {
           String ss = serializeServerEntries();
           if(ss!=null) {
               myPreferences.setValue(NAVAJO_APPLICATION_SERVERS, ss);
        	   
           }
           
           String sss = serializeServerInvocations();
           if(sss!=null) {
               myPreferences.setValue(NAVAJO_PLUGIN_SCRIPT_INVOCATIONS, sss);
           }
           
           if (myBuilder!=null) {
//                        try {
                            
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
       public void updateMetaData(IProgressMonitor ipm, IProject ip) throws NavajoPluginException, CoreException {
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
               IServerEntryListener current = myServerEntryListeners.get(i);
               current.serverEntryChanged(index);
              }
       }

       public void addServerEntryListener(IServerEntryListener ise) {
           myServerEntryListeners.add(ise);
       }
       public void removeServerEntryListener(IServerEntryListener ise) {
           myServerEntryListeners.remove(ise);
       }

       public List<String> getScriptsStartingWith(String prefix) {
        if (scriptInvocations == null) {
            scriptInvocations = parseScriptInvocations();
        }
        List<String> result = new ArrayList<String>();
        for (int i = 0; i < scriptInvocations.size(); i++) {
            String current = scriptInvocations.get(i);
            if (current.startsWith(prefix)) {
                result.add(current);
            }
        }
        return result;
    }

    private List<String> parseScriptInvocations() {
        String invocations = myPreferences.getString(NAVAJO_PLUGIN_SCRIPT_INVOCATIONS);
        List<String> res = new ArrayList<String>();
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
    


    	
//	public void createReport(IProject p, String name, Navajo n, File sourceFile) throws NavajoPluginException, IOException, NavajoException, PartInitException {
//		 IFolder iff = p.getFolder(getReportPath(p));
//		 
//			System.err.println("CREATING REPORT...");
//		BirtUtils b = new BirtUtils();
//		System.err.println("BIRT_UTILS_FOUND");
//		
////		b.createEmptyReport(n, iff.getLocation().toFile(), name,n.getHeader().getHeaderAttribute("sourceScript"));
//		try {
//			iff.refreshLocal(IResource.DEPTH_INFINITE, null);
//		} catch (CoreException e) {
//			e.printStackTrace();
//		}
//		IFile rep = getReportFile(p, name+".rptdesign");
//
//		IDE.openEditor(getWorkbench().getActiveWorkbenchWindow().getActivePage(), rep);
//		
//	}

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
