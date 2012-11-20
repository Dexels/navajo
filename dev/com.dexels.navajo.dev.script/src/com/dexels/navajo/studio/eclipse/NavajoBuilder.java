/*
 * Created on Feb 24, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.studio.eclipse;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;

import com.dexels.navajo.studio.script.plugin.NavajoPluginException;
import com.dexels.navajo.studio.script.plugin.NavajoScriptPluginPlugin;

/**
 * @author Administrator
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class NavajoBuilder extends org.eclipse.core.resources.IncrementalProjectBuilder {

    public static final int LARGE_COMPILE_THRESHOLD = 100;
    public static final int VERY_LARGE_COMPILE_THRESHOLD = 500;
      private boolean isOkToCompile;
//    private TslMetaDataHandler metaDataHandler;
//    private int compileCount;

    /**
     * 
     */

    @Override
	protected void startupOnInitialize() {
        if (getProject() == null) {
            System.err.println("Builder project null.");
        } else {
            System.err.println("Builder created for project: " + getProject().getName());
        }
        NavajoScriptPluginPlugin.getDefault().setNavajoBuilder(this);
   }

    
    

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.core.resources.IncrementalProjectBuilder#build(int,
     *      java.util.Map, org.eclipse.core.runtime.IProgressMonitor)
     */
    @Override
	protected IProject[] build(final int kind, Map args, IProgressMonitor monitor) throws CoreException {
//                ClassProvider provider = NavajoScriptPluginPlugin.getDefault().getClassProvider(getProject(),false);
                buildScripts(kind, monitor);
        return null;
    }
//
//    public TslMetaDataHandler getMetaDataHandler() {
//        return metaDataHandler;
//    }

    private void buildScripts(final int kind, final IProgressMonitor monitor) throws CoreException {
         final IFolder script;
        final IFolder compiled;
        IProject ip = getProject();
            try {
                script = getProject().getFolder(NavajoScriptPluginPlugin.getDefault().getScriptPath(getProject()));
                compiled = getProject().getFolder(NavajoScriptPluginPlugin.getDefault().getCompilePath(getProject()));
            } catch (NavajoPluginException e2) {
                System.err.println("Not a navajo project.");
                return;
            }
        switch (kind) {
        case INCREMENTAL_BUILD:
        case AUTO_BUILD:
            if (!ip.hasNature(NavajoScriptPluginPlugin.NAVAJO_NATURE)) {
                System.err.println("No nature?");
                break;
            }
            IResourceDelta ird = getDelta(ip);
            try {
                buildScriptDelta(monitor, ird, script, compiled);
            } catch (NavajoPluginException e) {
                NavajoScriptPluginPlugin.getDefault().log("Error building script delta for project: "+ip.getName(), e);
            }
            
            break;
        case CLEAN_BUILD:
            System.err.println("Cleaning...");
            if (!ip.hasNature(NavajoScriptPluginPlugin.NAVAJO_NATURE)) {
                break;
            }
            try {
                cleanFolder(NavajoScriptPluginPlugin.getDefault().getCompileFolder(getProject()));
            } catch (NavajoPluginException e) {
                NavajoScriptPluginPlugin.getDefault().log(e);
            }

            break;

        case FULL_BUILD:
            List<NavajoScriptCompilation> compileList = new ArrayList<NavajoScriptCompilation>();
            System.err.println("Fullbuild would compile: " + compileList.size());
            compileScript(compileList);
            break;

        default:
            break;
        }
    }



    public void clean(final IFolder compileDir) throws CoreException {
        System.err.println("Cleaning folder....");
        NavajoScriptPluginPlugin.getDefault().getMetaDataHandler().flushAll();
        cleanFolder(compileDir);
    }

    private void cleanFolder(IResource r) throws CoreException {
        if (r instanceof IFolder) {
            IResource[] ir = ((IFolder) r).members();
            for (int i = 0; i < ir.length; i++) {
                cleanFolder(ir[i]);
                ir[i].delete(false, null);
            }
        }
    }

    public void buildScriptDelta(final IProgressMonitor monitor, IResourceDelta ird, final IFolder scriptDir, final IFolder compileDir)
            throws CoreException, NavajoPluginException {
        final List<IResource> changed = new ArrayList<IResource>();
        final List<IResource> removed = new ArrayList<IResource>();
//        final IFolder configFolder = NavajoScriptPluginPlugin.getDefault().getNavajoConfigFolder(getProject());
        final IFolder adapterFolder = NavajoScriptPluginPlugin.getDefault().getAdaptersFolder(getProject());
                
        IResourceDeltaVisitor visitor = new IResourceDeltaVisitor() {
            public boolean visit(IResourceDelta delta) {
                // only interested in changed resources (not added or removed)
                IResource resource = delta.getResource();
                monitor.setTaskName("Checking navajo script resource : " + resource.getFullPath());
                String ext = resource.getFileExtension();
                if (resource instanceof IProject) {
                     //TODO: CHeck if its the RIGHT project
                    System.err.println("Script dir: "+scriptDir.getFullPath()+" compile: "+compileDir.getFullPath());
                    System.err.println("Prject..: "+resource.getName());
                    return true;
                }
                
                /**
                 * REAEALY Beware when refactoring the project structure of a navajo project,
                 * as the following code closely depends on that
                 */
                
                if (resource instanceof IFolder) {
                    IFolder fold = (IFolder) resource;
                    if (fold.equals(scriptDir)) {
                        // isn't this one redundant?
                        return true;
                    }
                    if (fold.equals(adapterFolder)) {
                          return true;
                    }

                    if (scriptDir.getFullPath().isPrefixOf(fold.getFullPath())) {
                        return true;
                    }
                    if (fold.getFullPath().isPrefixOf(scriptDir.getFullPath())) {
                        return true;
                    }
//                    if (fold.getFullPath().equals(configFolder)) {
//                       return true;
//                    }
                    return false;
                }
                if (!(resource instanceof IFile)) {
                    // System.err.println("Not a file?! " + resource);
                    return false;
                }
                IFile current = (IFile) resource;
                if ("xml".equalsIgnoreCase(ext)) {
                    switch (delta.getKind()) {
                    case IResourceDelta.REMOVED:
                        removed.add(resource);
                        break;
                    case IResourceDelta.ADDED:
                    case IResourceDelta.REPLACED:
                    case IResourceDelta.CHANGED:
                        
                        changed.add(resource);
                        break;
                    }
                }
                if (current.getName().equals(".classpath")) {
                    System.err.println("Detected classpath change, project: "+current.getProject().getName());
//                    NavajoScriptPluginPlugin.getDefault().logMessage("Reloading compiler, classpath changed!");
                    // Do this later; Avoid doing it multiple times
                    // NavajoScriptPluginPlugin.getDefault().refreshCompilerClassLoader(current.getProject());

                }
                
                try {
                    if (current.equals(NavajoScriptPluginPlugin.getDefault().getServerXml(current.getProject()))) {
                        NavajoScriptPluginPlugin.getDefault().refreshServerXmlNavajo(current.getProject());
                        NavajoScriptPluginPlugin.getDefault().logMessage("Reloading server.xml, it changed.");
                    }
                } catch (NavajoPluginException e) {
                    NavajoScriptPluginPlugin.getDefault().log("Error accessing config server.xml: "+current.getFullPath().toOSString(), e);
                }

                if ("jar".equalsIgnoreCase(ext) || "zip".equalsIgnoreCase(ext) && adapterFolder.getFullPath().isPrefixOf(current.getFullPath()) ) {
                    System.err.println("Adapters changed!");
                }
                
                if ("java".equalsIgnoreCase(ext)) {
                    // System.err.println("Changed java detected");
                    switch (delta.getKind()) {
                    case IResourceDelta.REMOVED:
                        if (NavajoScriptPluginPlugin.getDefault().recompileOnJavaDelete()) {
                            if (compileDir.getFullPath().isPrefixOf(current.getFullPath())) {
                            }

                        }
                        break;
                    case IResourceDelta.ADDED:
                        break;
                    case IResourceDelta.REPLACED:
                        break;
                    case IResourceDelta.CHANGED:
                        break;
                    }
                }
                return false;
            }
        };
        if (ird!=null) {
            ird.accept(visitor);
            try {
                monitor.beginTask("Compiling TSL", changed.size());
                List<NavajoScriptCompilation> compilation = new ArrayList<NavajoScriptCompilation>();
                Set<IPath> compilationSet = new HashSet<IPath>();
                for (Iterator iter = changed.iterator(); iter.hasNext();) {
                    if (monitor.isCanceled()) {
                        System.err.println("CANCELLED!");
                        break;
                    }
                    IFile element = (IFile) iter.next();
                    if (compilationSet.contains(element.getFullPath())) {
                        System.err.println("Already parsed. Skipping");
                        continue;
                    }
                    compilationSet.add(element.getFullPath());
                    IContainer scriptPackage = element.getParent();
                    IPath scriptDirPath = scriptDir.getFullPath();
                    IPath packPath = scriptPackage.getFullPath().removeFirstSegments(scriptDirPath.segmentCount());
                    String script = null;
                    String packageName = null;
                    packageName = packPath.toString();

                    
                    if (element.getParent().equals(NavajoScriptPluginPlugin.getDefault().getScriptFolder(getProject()))) {
                        script = element.getName();
                    } else {
                        script = packageName + "/" + element.getName();
                    }
                    if (script.endsWith(".xml")) {
                        script = script.substring(0, script.length() - 4);
                    } else {
                        // Experimental
                        continue;
                    }
//                    final String scriptString = script;
                    // if (scriptPackage.getName().equals("include")) {
                    // System.err.println("Skipping include dir...");
                    // continue;
                    // }
                    if (script.endsWith(".sample")) {
                        System.err.println("Skipping sample file...");
                        continue;
                    }

                    // TODO Create a better way to filter out non-xml files.

//                    String scriptName = NavajoScriptPluginPlugin.getDefault().getScriptName(element, element.getProject());
//                    IFile tmlFile = NavajoScriptPluginPlugin.getDefault().getTmlFile(element.getProject(), scriptName);
                    long modstamp = element.getModificationStamp();
                    IFile compiledFile = NavajoScriptPluginPlugin.getDefault().getCompiledScriptFile(getProject(), script);
                    if (compiledFile != null) {
                        if (compiledFile.exists()) {
                            long compStamp = compiledFile.getModificationStamp();
                            if (compStamp > modstamp) {
//                                System.err.println("<Disabled> Skipping because of modification stamp");
                                // continue;
                            }
                        }
                    }
                    addCompilation(compilation, scriptDir, compileDir, packageName, script);
                }
                if (monitor.isCanceled()) {
                    return;
                }
                if (compilation.size() > 0) {
                     compileScript(compilation);
                }
                for (Iterator it = removed.iterator(); it.hasNext();) {
                    IFile element = (IFile) it.next();
                    if (element==null) {
                        continue;
                    }
                }
            } catch (Throwable e1) {
                NavajoScriptPluginPlugin.getDefault().log("General error compiling script ",e1);
            }
             } else {
                 System.err.println("No resource delta!");
             }
   }

    private void compileScript(final List<NavajoScriptCompilation> compilationList)  {
//        System.err.println("Compilelist: " + compilationList.size());
        if (compilationList.size() > VERY_LARGE_COMPILE_THRESHOLD) {
            NavajoScriptPluginPlugin.getDefault().getWorkbench().getDisplay().syncExec(new Runnable() {

                public void run() {
                    isOkToCompile = MessageDialog.openQuestion(NavajoScriptPluginPlugin.getDefault().getWorkbench().getDisplay().getActiveShell(), "Oh dear", "About to build: "
                            + compilationList.size() + " scripts. You are a very, very, VERY brave Navajo.");
                }
            });

        } else if (compilationList.size() > LARGE_COMPILE_THRESHOLD) {
            NavajoScriptPluginPlugin.getDefault().getWorkbench().getDisplay().syncExec(new Runnable() {

                public void run() {
                    isOkToCompile = MessageDialog.openQuestion(NavajoScriptPluginPlugin.getDefault().getWorkbench().getDisplay().getActiveShell(), "Oh dear", "About to build: "
                            + compilationList.size() + " scripts. You are a brave Navajo.");
                }
            });

        }
        if (!isOkToCompile) {
            isOkToCompile = true;
            return;
        }
        
    }

	private void addCompilation(List<NavajoScriptCompilation> compilationList, final IFolder scriptDir, final IFolder compileDir, final String scriptPackageName,
            final String script) {
        NavajoScriptCompilation nsc = new NavajoScriptCompilation(script, scriptPackageName, scriptDir.getLocation().toString(), compileDir
                .getLocation().toString());
        compilationList.add(nsc);
    }

 


    /**
     * @return
     */
    public String getOutputPath() throws NavajoPluginException {
        IFolder ff = NavajoScriptPluginPlugin.getDefault().getCompiledScriptFolder(getProject());
        return ff.getRawLocation().toOSString();
    }

    class NavajoScriptCompilation {
        private final String script;

        private final String scriptPackage;

        private final String scriptDir;

        private final String compileDir;

        public NavajoScriptCompilation(String script, String scriptPackage, String scriptDir, String compileDir) {
            this.script = script;
            this.scriptPackage = scriptPackage;
            this.scriptDir = scriptDir;
            this.compileDir = compileDir;
        }

   

		@Override
		public String toString() {
            return "Script: " + script + " package: " + scriptPackage + " compileDir: " + compileDir + " scriptDir: " + scriptDir;
        }

        /**
         * @return Returns the compileDir.
         */
        public String getCompileDir() {
            return compileDir;
        }

        /**
         * @return Returns the script.
         */
        public String getScript() {
            return script;
        }

        /**
         * @return Returns the scriptDir.
         */
        public String getScriptDir() {
            return scriptDir;
        }

        /**
         * @return Returns the scriptPackage.
         */
        public String getScriptPackage() {
            return scriptPackage;
        }
    }
}