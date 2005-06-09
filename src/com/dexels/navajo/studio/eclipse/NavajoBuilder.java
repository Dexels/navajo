/*
 * Created on Feb 24, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.studio.eclipse;

import java.util.*;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.eclipse.core.runtime.jobs.*;
import org.eclipse.jdt.core.*;
import org.eclipse.jdt.internal.formatter.*;
import org.eclipse.jface.dialogs.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.internal.*;
//import org.eclipse.osgi.service.datalocation.*;

import com.dexels.navajo.client.*;
import com.dexels.navajo.document.*;
import com.dexels.navajo.document.nanoimpl.*;
import com.dexels.navajo.functions.*;
import com.dexels.navajo.loader.*;
import com.dexels.navajo.mapping.compiler.*;
import com.dexels.navajo.server.*;
import com.dexels.navajo.studio.script.plugin.*;
import com.dexels.navajo.studio.script.plugin.navajobrowser.*;

/**
 * @author Administrator
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class NavajoBuilder extends org.eclipse.core.resources.IncrementalProjectBuilder {

    private boolean isOkToCompile;

    /**
     *  
     */
    public NavajoBuilder() {
        super();
        System.err.println("Created a navajo builder...");

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.core.resources.IncrementalProjectBuilder#build(int,
     *      java.util.Map, org.eclipse.core.runtime.IProgressMonitor)
     */
    protected IProject[] build(final int kind, Map args, IProgressMonitor monitor) throws CoreException {
        //        ResourcesPlugin.getWorkspace().
        System.err.println("BUILDING SCRIPTS!");
        ResourcesPlugin.getWorkspace().run(new IWorkspaceRunnable() {
            public void run(IProgressMonitor monitor) throws CoreException {
                buildScripts(kind, monitor);
            }
        }, monitor);
        return null;
    }

    private void buildScripts(final int kind, final IProgressMonitor monitor) throws CoreException {
        System.err.println("\n\nBuild: kind: " + kind);
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
                break;
            }
            IResourceDelta ird = getDelta(ip);
            buildScriptDelta(monitor, ird, script, compiled);
            break;
        case CLEAN_BUILD:
            System.err.println("Cleaning...");
            if (!ip.hasNature(NavajoScriptPluginPlugin.NAVAJO_NATURE)) {
                break;
            }
           Workbench.getInstance().getDisplay().syncExec(new Runnable() {

                public void run() {

                    boolean res = MessageDialog.openQuestion(Workbench.getInstance().getActiveWorkbenchWindow().getShell(), "Navajo Studio Plug-in",
                            "Do you want to remove all the TML files?");
                    if (res) {
                        try {
                            try {
                                cleanFolder(NavajoScriptPluginPlugin.getDefault().getTmlFolder(getProject()));
                            } catch (NavajoPluginException e1) {
                                 e1.printStackTrace();
                            }
                            
                        } catch (CoreException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }); // NO BREAK?
            try {
                    cleanFolder(NavajoScriptPluginPlugin.getDefault().getCompileFolder(getProject()));
                } catch (NavajoPluginException e) {
                     e.printStackTrace();
                }
            
            break;

        case FULL_BUILD:
            ArrayList compileList = new ArrayList();
//            try {
//                clean(kind, compiled, monitor);
//            } catch (CoreException e) {
//                e.printStackTrace();
//            }
             
            fullBuild(compileList, script, compiled, monitor, script, "");
            System.err.println("Fullbuild would compile: "+compileList.size());
            try {
                    compileScript(compileList,monitor);
                } catch (CoreException e1) {
                      e1.printStackTrace();
                } catch (NavajoPluginException e1) {
                     e1.printStackTrace();
                }
            break;

        default:
            break;
        }
    }

    /**
     * @param script
     * @param compiled
     * @param monitor
     */
    private void fullBuild(ArrayList compilationList, IFolder scriptDir, IFolder compiled, IProgressMonitor monitor, IFolder currentDir,
            String currentPrefix) throws CoreException {
   
//        scriptDir.deleteMarkers(IMarker.PROBLEM, true, IResource.DEPTH_INFINITE);
        IResource[] ir = ((IFolder) currentDir).members();
        for (int i = 0; i < ir.length; i++) {
            String name = ir[i].getName();
            if (ir[i] instanceof IFolder) {
                IFolder cc = (IFolder) ir[i];
                if (cc.getName().equals("CVS")) {
                    // uglyish hack
                    continue;
                }
                if (cc.getName().equals("include")) {
                    // uglyish hack
                    continue;
                }

                if (currentPrefix.equals("")) {
                    String prefix = name;
                    fullBuild(compilationList, scriptDir, compiled, monitor, cc, prefix);
                } else {
                    String prefix = currentPrefix + "/" + name;
                    fullBuild(compilationList, scriptDir, compiled, monitor, cc, prefix);
                }
            } else {
                if (ir[i] instanceof IFile) {
                    IFile ffile = (IFile) ir[i];
                    String script = null;
                    if (currentPrefix.equals("")) {
                        script = name;
                    } else {
                        script = currentPrefix + "/" + name;
                    }
                    if (name.endsWith(".sample")) {
                        continue;
                    }
                    long modstamp = ffile.getModificationStamp();
                    IFile compiledFile;
                    try {
                        compiledFile = NavajoScriptPluginPlugin.getDefault().getCompiledScriptFile(getProject(), script);
                    } catch (NavajoPluginException e) {
                        System.err.println("No compiled scriptdir?!");
                        continue;
                    }
                    if (compiledFile != null) {
                        if (compiledFile.exists()) {
                            long compStamp = compiledFile.getModificationStamp();
                            long localStamp = compiledFile.getLocalTimeStamp();
                            if (localStamp > compStamp) {
                                MessageDialog.openInformation(Display.getCurrent().getActiveShell(), "You edited the java source form:", name
                                        + ", 0uw3");
                            }
                            if (compStamp > modstamp) {
                                System.err.println("SHOULD SKIP:: " + name);
                                continue;
                            }
                        }
                    } else {
                        System.err.println("NO COMPILED FILE FOUND");
                    }

                    script = script.endsWith(".xml") ? script.substring(0, script.length() - 4) : script;
                    addCompilation(compilationList, scriptDir, compiled, currentPrefix, script);
                    //                    System.err.println("Script: " + ir[i].getName());

                } else {
                    MessageDialog.openInformation(Display.getCurrent().getActiveShell(), "Geen idee wat voor script dingetje dit is:", name);
                }
            }
        }
    }

    public void clean(int kind, final IFolder compileDir, IProgressMonitor ipm) throws CoreException {
        System.err.println("Cleaning folder....");
        Workbench.getInstance().getDisplay().syncExec(new Runnable(){

            public void run() {
                MessageDialog.openWarning(Workbench.getInstance().getDisplay().getActiveShell(), "Oh dear", "Do you have popcorn with you? This will take a while.");
                
            }});

        cleanFolder(compileDir);
        
    }

    private void cleanFolder(IResource r) throws CoreException {
        if (r instanceof IFolder) {
            IResource[] ir = ((IFolder) r).members();
            for (int i = 0; i < ir.length; i++) {
                cleanFolder(ir[i]);
                //                System.err.println("DELETING: " +
                // ir[i].getFullPath().toOSString());
                ir[i].delete(false, null);
            }
        }
        //        System.err.println("DELETING: " + r.getFullPath().toOSString());

    }

    public void buildScriptDelta(final IProgressMonitor monitor, IResourceDelta ird, final IFolder scriptDir, final IFolder compileDir)
            throws CoreException {
        final ArrayList changed = new ArrayList();
        final ArrayList removed = new ArrayList();
//        final IFolder tslDir = NavajoScriptPluginPlugin.getDefault().getScriptFolder(getProject());
      IResourceDeltaVisitor visitor = new IResourceDeltaVisitor() {
            public boolean visit(IResourceDelta delta) {
                //only interested in changed resources (not added or removed)
                monitor.setTaskName("Checking navajo script resources...");
                IResource resource = delta.getResource();
//                delta.getKind();
                //                System.err.println("Visiting: "+resource.getFullPath());
                String ext = resource.getFileExtension();
                if (resource instanceof IProject) {
                    //                    System.err.println("Prject..");
                    return true;
                }
                if (resource instanceof IFolder) {
                    //                    System.err.println("Folder.."+ resource.getFullPath()+"
                    // compile: "+compileDir.getFullPath()+" scriptdir:
                    // "+scriptDir.getFullPath());
                    IFolder fold = (IFolder) resource;
                     if (fold.equals(scriptDir)) {
                        // isn't this one redundant?
                        return true;
                    }
                    if (scriptDir.getFullPath().isPrefixOf(fold.getFullPath())) {
                        return true;
                    }
                    if (fold.getFullPath().isPrefixOf(scriptDir.getFullPath())) {
                        return true;
                    }
//                    if (compileDir.getFullPath().isPrefixOf(fold.getFullPath())) {
//                        return true;
//                    }

                    //                    System.err.println("Folder: " + fold.getFullPath() + "
                    // did not qualify");
                    return false;
                }
                if (!(resource instanceof IFile)) {
                    //                    System.err.println("Not a file?! " + resource);
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
                if ("java".equalsIgnoreCase(ext)) {
//                    System.err.println("Changed java detected");
                    switch (delta.getKind()) {
                    case IResourceDelta.REMOVED:
                        if(NavajoScriptPluginPlugin.getDefault().recompileOnJavaDelete()) {
                            if (compileDir.getFullPath().isPrefixOf(current.getFullPath())) {
//                                String scriptName = NavajoScriptPluginPlugin.getDefault().getScriptNameFromResource(current);
//                                if (scriptName != null && !"".equals(scriptName)) {
//                                    final IFile script = NavajoScriptPluginPlugin.getDefault().getScriptFile(current.getProject(), scriptName);
//                                    // Kick the script to trigger a recompile
//                                    new Job("Recompiling script") {
//                                        protected IStatus run(IProgressMonitor monitor) {
//                                            try {
//                                                script.touch(monitor);
//                                            } catch (CoreException e) {
//                                                // Whatever
//                                                e.printStackTrace();
//                                            }
//                                            return Status.OK_STATUS;
//                                        }
//                                    }.schedule();
//                                   }
//                                return true;
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
        ird.accept(visitor);
        try {
            String name = ird.getFullPath().toString();
            ArrayList compilation = new ArrayList();
            for (Iterator iter = changed.iterator(); iter.hasNext();) {
                if (monitor.isCanceled()) {
                    System.err.println("CANCELLED!");
                    break;
                }
                IFile element = (IFile) iter.next();
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
                final String scriptString = script;
                if (scriptPackage.getName().equals("include")) {
                    System.err.println("Skipping include dir...");
                    continue;
                }
                if (script.endsWith(".sample")) {
                    System.err.println("Skipping sample file...");
                    continue;
                }
                
                // TODO Create a better way to filter out non-xml files.
                
                String scriptName = NavajoScriptPluginPlugin.getDefault().getScriptName(element, element.getProject());
                IFile tmlFile = NavajoScriptPluginPlugin.getDefault().getTmlFile(element.getProject(), scriptName);
                long modstamp = element.getModificationStamp();
                IFile compiledFile = NavajoScriptPluginPlugin.getDefault().getCompiledScriptFile(getProject(), script);
                if (compiledFile != null) {
                    if (compiledFile.exists()) {
                        long compStamp = compiledFile.getModificationStamp();
                         if (compStamp > modstamp) {
                            continue;
                        }
                    }
                }                 
                addCompilation(compilation, scriptDir, compileDir, packageName, script);
             }
            if (monitor.isCanceled()) {
                return;
            }
            if (compilation.size()>0) {
                compileScript(compilation,monitor);
            }
            for (Iterator it = removed.iterator(); it.hasNext();) {
                IFile element = (IFile) it.next();
                String scriptName = NavajoScriptPluginPlugin.getDefault().getScriptName(element, element.getProject());
                IFile tmlFile = NavajoScriptPluginPlugin.getDefault().getTmlFile(element.getProject(), scriptName);
                if (tmlFile.exists()) {
                    NavajoScriptPluginPlugin.getDefault().deleteFile(tmlFile, monitor);
                  }                 IFile compiledFile = NavajoScriptPluginPlugin.getDefault().getCompiledScriptFile(element.getProject(), scriptName);
                if (compiledFile.exists()) {
                    NavajoScriptPluginPlugin.getDefault().deleteFile(compiledFile, monitor);
                } 
            }
        } catch (Throwable e1) {
            e1.printStackTrace();
        }
    }

    private void compileScript(final ArrayList compilationList, IProgressMonitor monitor) throws CoreException, NavajoPluginException {
        if (compilationList.size()>50) {
            Workbench.getInstance().getDisplay().syncExec(new Runnable(){

                public void run() {
                    isOkToCompile = MessageDialog.openQuestion(Workbench.getInstance().getDisplay().getActiveShell(), "Oh dear", "About to build: "+compilationList.size()+" scripts. You are brave.");
                    }});
           
        }
        if (!isOkToCompile) {
            isOkToCompile = true;
            return;
        }
        
        Job job = new Job("Compiling scripts: ") {
            protected IStatus run(IProgressMonitor monitor) {
                long totalOld = 0;
                long totalNew = 0;
//                ClassProvider provider = new ClassProvider("aap", "aap", false, (IProject) getProject());
                ClassProvider provider2 = new ClassProvider("aap", "aap", false, (IProject) getProject());
                long time = System.currentTimeMillis();
                monitor.beginTask("Compiling TSL", compilationList.size());
                for (int i = 0; i < compilationList.size(); i++) {
                    if (monitor.isCanceled()) {
                        break;
                    }
                  NavajoScriptCompilation current = (NavajoScriptCompilation) compilationList.get(i);
                    try {
                        IFile ifi = NavajoScriptPluginPlugin.getDefault().getScriptFile(getProject(), current.getScript());
                        IFile output = NavajoScriptPluginPlugin.getDefault().getCompiledScriptFile(getProject(), current.getScript());
                          try {
                              monitor.setTaskName(" Compiling: " + i + "/" + compilationList.size() + " "
                                    + current.getScript());
                            monitor.worked(1);
                            if (!ifi.exists()) {
                                continue;
                            }
                          ifi.deleteMarkers(IMarker.PROBLEM, true, 0);//                        monitor.worked(1);
                          long cc = System.currentTimeMillis(); 
//                      TslCompiler.compileToJava(current.getScript(), current.getScriptDir(), current.getCompileDir()+"Old", current.getScriptPackage(),    provider);
//                          System.err.println("Old Compiling: "+current.getScript()+" took: "+(System.currentTimeMillis()-cc)+" millis.");
                          totalOld += (System.currentTimeMillis()-cc);
                          cc = System.currentTimeMillis(); 
                          
//                      NanoTslCompiler.compileToJava(current.getScript(), current.getScriptDir(), current.getCompileDir()+"New", current.getScriptPackage(), provider2);
                         
                          NanoTslCompiler.compileToJava(current.getScript(), current.getScriptDir(), current.getCompileDir(), current.getScriptPackage(), provider2);
//                          System.err.println("New Compiling: "+current.getScript()+" took: "+(System.currentTimeMillis()-cc)+" millis.");

                          totalNew += (System.currentTimeMillis()-cc);
//                      output.refreshLocal(0, monitor);
//                      ifi.refreshLocal(0, monitor);
                          } catch (Throwable e) {
                              int start = 0;
                              int end = 5;
                              if (e instanceof TslCompileException) {
                                  System.err.println("TCE detected!");
                                  start = ((TslCompileException)e).getStartOffset();
                                  end = ((TslCompileException)e).getEndOffset();
try {
                                    //                              XMLElement elt = ((TslCompileException)e).getSource();
                                       NavajoScriptPluginPlugin.getDefault().reportProblem(e.getMessage(),ifi, start, end+1,(TslCompileException)e);
                                } catch (CoreException e1) {
                                      e1.printStackTrace();
                                }
                                   continue;
                              }
                            if (ifi != null) {
                                if (!ifi.exists()) {
                                } else {
                                    System.err.println("REPORTING PROBLEM!");
                                    try {
                                        NavajoScriptPluginPlugin.getDefault().reportProblem(e.getMessage(),ifi, start, end+1,null);
                                    } catch (CoreException e1) {
                                        e1.printStackTrace();
                                    }
                                }
                            } 
                        }
                    } catch (NavajoPluginException e) {
                        e.printStackTrace();
                    }
                  }
                monitor.done();
               System.err.println("TSL COMPILE TOOK: "+totalNew);
                long cc = System.currentTimeMillis();
                try {
                    IFolder ifff = NavajoScriptPluginPlugin.getDefault().getCompileFolder(getProject());
                    ifff.refreshLocal(IFolder.DEPTH_INFINITE, monitor);
                } catch (NavajoPluginException e) {
                    e.printStackTrace();
                } catch (CoreException e) {
                    e.printStackTrace();
                }
                long diff = System.currentTimeMillis() - cc;
//                System.err.println("Refresh took: "+diff);
                //                try {
//                    NavajoScriptPluginPlugin.getDefault().refreshResource(NavajoScriptPluginPlugin.getDefault().getCompileFolder(getProject()),
//                            monitor);
//                } catch (CoreException e) {
//                    System.err.println("Trouble refreshing compile directory!");
//                    e.printStackTrace();
//
//                }
//                long time2 = System.currentTimeMillis();
//                System.err.println("Compiled: " + compilationList.size() + " in: " + (time2 - time) + " millis! Cache size: "
//                        + provider.getCacheSize() + " and " + provider.getAmountOfLoads() + " bytecode load attempts");
//                System.gc();
                return Status.OK_STATUS;
            }
        };
        job.setUser(true);
        job.setRule(getProject());
        job.setPriority(Job.SHORT);
        job.schedule();
    }

 
 
    private void addCompilation(ArrayList compilationList, final IFolder scriptDir, final IFolder compileDir, final String scriptPackageName,
            final String script) throws CoreException {
         NavajoScriptCompilation nsc = new NavajoScriptCompilation(script, scriptPackageName, scriptDir.getLocation().toString(), compileDir.getLocation().toString());
        compilationList.add(nsc);
    }

    protected void startupOnInitialize() {
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