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
import org.eclipse.jdt.internal.formatter.*;
import org.eclipse.jface.dialogs.*;
import org.eclipse.ui.internal.*;
//import org.eclipse.osgi.service.datalocation.*;

import com.dexels.navajo.client.*;
import com.dexels.navajo.document.*;
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

    public void buildScripts(int kind, IProgressMonitor monitor) throws CoreException {
        System.err.println("Build: kind: " + kind);
        IFolder script = getProject().getFolder(NavajoScriptPluginPlugin.getDefault().getScriptPath());
        IFolder compiled = getProject().getFolder(NavajoScriptPluginPlugin.getDefault().getCompilePath());
        switch (kind) {
        case INCREMENTAL_BUILD:
        case AUTO_BUILD:
            IResourceDelta ird = getDelta(getProject());
            buildScriptDelta(monitor, ird, script, compiled);
            break;
        case CLEAN_BUILD:
            Workbench.getInstance().getDisplay().syncExec(new Runnable(){

                public void run() {
                    
                    boolean res = MessageDialog.openQuestion(
                            Workbench.getInstance().getActiveWorkbenchWindow().getShell(),
    						"Navajo Studio Plug-in",
    						"Do you want to remove all the TML files?");
    	    				if (res) {
    	    				    try {
                                    cleanFolder(NavajoScriptPluginPlugin.getDefault().getTmlFolder(getProject()));
                                } catch (CoreException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
    	    				}
                    
                }});
            
        case FULL_BUILD:
            clean(kind, compiled, monitor);
            ArrayList compileList = new ArrayList();
            fullBuild(compileList, script, compiled, monitor, script, "");
            //             fullBuild(script, compiled, monitor);
            compileScript(compileList);
            //            NavajoScriptPluginPlugin.getDefault().refreshResource(compiled);
            //            refreshResource(compiled);
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
                String script = null;
                if (currentPrefix.equals("")) {
                    script = name;
                } else {
                    script = currentPrefix + "/" + name;
                }
                if (name.endsWith(".sample")) {
                    continue;
                }
                script = script.endsWith(".xml") ? script.substring(0, script.length() - 4) : script;
                addCompilation(compilationList, scriptDir, compiled, currentPrefix, script);
                System.err.println("Script: " + ir[i].getName());
            }
        }
    }

    public void clean(int kind, final IFolder compileDir, IProgressMonitor ipm) throws CoreException {
        System.err.println("Cleaning folder....");
        cleanFolder(compileDir);
        //        Job job = new Job("Refreshing output folder") {
        //            protected IStatus run(IProgressMonitor monitor) {
        try {
            compileDir.refreshLocal(IResource.DEPTH_INFINITE, ipm);
        } catch (CoreException e) {
            e.printStackTrace();
        }

        //                return Status.OK_STATUS;
        //            }
        //        };
        //        job.setUser(true);
        //        job.schedule();
    }

    private void cleanFolder(IResource r) throws CoreException {
        if (r instanceof IFolder) {
            IResource[] ir = ((IFolder) r).members();
            for (int i = 0; i < ir.length; i++) {
                cleanFolder(ir[i]);
                System.err.println("DELETING: " + ir[i].getFullPath().toOSString());
                ir[i].delete(false, null);
            }
        }
        //        System.err.println("DELETING: " + r.getFullPath().toOSString());

    }

    public void buildScriptDelta(IProgressMonitor monitor, IResourceDelta ird, final IFolder scriptDir, final IFolder compileDir)
            throws CoreException {
        final ArrayList changed = new ArrayList();
        final ArrayList removed = new ArrayList();
        IResourceDeltaVisitor visitor = new IResourceDeltaVisitor() {
            public boolean visit(IResourceDelta delta) {
                //only interested in changed resources (not added or removed)
                IResource resource = delta.getResource();
                System.err.println("Visiting: "+resource.getFullPath());
                String ext = resource.getFileExtension();
                if (resource instanceof IProject) {
                    System.err.println("Prject..");
                    return true;
                }
                if (resource instanceof IFolder) {
                    System.err.println("Folder..");
                    return true;
                }
                if (!(resource instanceof IFile)) {
                    System.err.println("Not a file?! "+resource);
                    return false;
                }
                if (!"xml".equalsIgnoreCase(ext)) {
                    System.err.println("Ignoring extention: "+ext );
                    return false;
                }

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
//                if (delta.getKind() != IResourceDelta.CHANGED) {
//                    if ("xml".equalsIgnoreCase(resource.getFileExtension()) || delta.getKind() == IResourceDelta.REMOVED) {
//                        System.err.println("Script removed: " + resource.getFullPath().toString());
//                        return true;
//                    }
//                }
                //only interested in content changes
                //                if ((delta.getFlags() & IResourceDelta.CONTENT) == 0)
                //                    return true;
                //only interested in files with the "xml" extension
//                if (resource.getType() == IResource.FILE && "xml".equalsIgnoreCase(resource.getFileExtension())) {
//                    if (delta.getKind() == IResourceDelta.CHANGED || delta.getKind() == IResourceDelta.REPLACED) {
//                        changed.add(resource);
//                        return true;
//                    }
//                }
                return false;
            }
        };
        ird.accept(visitor);
        try {
            String name = ird.getFullPath().toString();
            ArrayList compilation = new ArrayList();
            for (Iterator iter = changed.iterator(); iter.hasNext();) {
                IFile element = (IFile) iter.next();
                IContainer scriptPackage = element.getParent();
                String script = null;
                if (element.getParent().equals(NavajoScriptPluginPlugin.getDefault().getScriptFolder(getProject()))) {
                    script = element.getName();
                } else {
                    script = scriptPackage.getName() + "/" + element.getName();
                }
                if (script.endsWith(".xml")) {
                    script = script.substring(0, script.length() - 4);
                }
                  final String scriptString = script;
                String packageName = null;
                System.err.println("ScriptDir: " + scriptDir.getName());
                System.err.println("PackageDir: " + scriptPackage.getName());
                if (scriptPackage.getName().equals("include")) {
                    System.err.println("Skipping include dir...");
                    continue;
                }
                if (script.endsWith(".sample")) {
                    System.err.println("Skipping sample file...");
                    continue;
                }
                if (scriptDir.getName().trim().equals(scriptPackage.getName().trim())) {
                    System.err.println("yiupee!");
                    packageName = "";
                } else {
                    packageName = scriptPackage.getName();
                }
                String scriptName = NavajoScriptPluginPlugin.getDefault().getScriptName(element, element.getProject());
                IFile tmlFile = NavajoScriptPluginPlugin.getDefault().getTmlFile(element.getProject(), scriptName);
               if (tmlFile.exists()) {
                    System.err.println("Script changed. Should delete tmlFile file: "+tmlFile);
                    NavajoScriptPluginPlugin.getDefault().deleteFile(tmlFile, monitor);
               } else {
                    System.err.println("Script changed, but did not delete tmlFile file, it did not exist: "+tmlFile);
                }
                
                addCompilation(compilation, scriptDir, compileDir, packageName, script);
                //                compileScript(scriptDir, compileDir,packageName, script,
                // false);
            }
            compileScript(compilation);
            for (Iterator it = removed.iterator(); it.hasNext();) {
                IFile element = (IFile) it.next();
                String scriptName = NavajoScriptPluginPlugin.getDefault().getScriptName(element, element.getProject());
                IFile tmlFile = NavajoScriptPluginPlugin.getDefault().getTmlFile(element.getProject(), scriptName);
                if (tmlFile.exists()) {
                    System.err.println("Should delete tmlFile file: "+tmlFile);
                    NavajoScriptPluginPlugin.getDefault().deleteFile(tmlFile, monitor);
//                    tmlFile.delete(true, false, monitor);
               } else {
                    System.err.println("Did not delete tmlFile file, it did not exist: "+tmlFile);
                }

                
                IFile compiledFile = NavajoScriptPluginPlugin.getDefault().getCompiledScriptFile(element.getProject(), scriptName);
                if (compiledFile.exists()) {
                    System.err.println("Should delete compiled file: "+compiledFile);
                    NavajoScriptPluginPlugin.getDefault().deleteFile(compiledFile, monitor);
                } else {
                    System.err.println("Did not delete compiled file, it did not exist: "+compiledFile);
                }
                
            }
            
        } catch (Throwable e1) {
            e1.printStackTrace();
        }
    }

    //    private void compileScript(final IFolder scriptDir, final IFolder
    // compileDir, final String scriptPackageName, final String script, final
    // boolean refreshScript) throws CoreException {
    //        Job job = new Job("Compiling: "+script) {
    //            protected IStatus run(IProgressMonitor monitor) {
    //                     try {
    //                        TslCompiler.compileToJava(script, scriptDir.getLocation().toString(),
    // compileDir.getLocation().toString(), scriptPackageName,
    //                                null);
    //                        
    //                    } catch (SystemException e) {
    //                        e.printStackTrace();
    //                    }
    //    
    //                return Status.OK_STATUS;
    //            }
    //        };
    //        job.setPriority(Job.SHORT);
    //        job.schedule();
    //     }
    //    
    private void compileScript(final ArrayList compilationList) throws CoreException {
        Job job = new Job("Compiling scripts: ") {
            protected IStatus run(IProgressMonitor monitor) {
                for (int i = 0; i < compilationList.size(); i++) {
                    NavajoScriptCompilation current = (NavajoScriptCompilation) compilationList.get(i);
                    IFile ifi = NavajoScriptPluginPlugin.getDefault().getScriptFile(getProject(), current.getScript());
                    System.err.println("Looking for resource: " + ifi.getFullPath().toOSString());
                    try {
                        ifi.deleteMarkers(IMarker.PROBLEM, false, 1);//                        monitor.worked(1);
                        monitor.setTaskName("Compiling: " + i + "/" + compilationList.size() + " " + current.getScript());
                        System.err.println("Compiling: " + i + "/" + compilationList.size());
                        System.err.println("Current: " + current.getScript() + " dir: " + current.getScriptDir() + " compiledir: "
                                + current.getCompileDir() + " pack: " + current.getScriptPackage());
                        TslCompiler.compileToJava(current.getScript(), current.getScriptDir(), current.getCompileDir(), current.getScriptPackage(),
                                null);
                    } catch (Throwable e) {
                        //                        e.printStackTrace();
                        System.err.println("%%%%%%%%%%%\n%%%%%%%%%%\n%%%%%%%%%%");
                        System.err.println("Error: " + e.getMessage());
                        if (ifi != null) {
                            if (!ifi.exists()) {
                                System.err.println("This is a bit weird,,,");
                            } else {
                                System.err.println("REPORTING PROBLEM!");
                                reportProblem("TSL compiler problem: " + e.getMessage(), ifi, i, true, IMarker.PROBLEM, "aap", 1, 10);

                            }
                            //                            try {
                            //                                IMarker im = ifi.createMarker(IMarker.PROBLEM);
                            //                            } catch (CoreException e1) {
                            //                                // TODO Auto-generated catch block
                            //                                e1.printStackTrace();
                            //                            }
                        } else {
                            System.err.println("What the F@#$?");
                        }

                    }

                }
                try {
                    NavajoScriptPluginPlugin.getDefault().refreshResource(NavajoScriptPluginPlugin.getDefault().getCompileFolder(getProject()),
                            monitor);
                } catch (CoreException e) {
                    // TODO Auto-generated catch block
                    System.err.println("Trouble refreshing compile directory!");
                    e.printStackTrace();

                }

                return Status.OK_STATUS;
            }
        };
        job.setUser(true);
        job.setRule(ResourcesPlugin.getWorkspace().getRoot());
        job.setPriority(Job.SHORT);
        job.schedule();
    }

    public static final String KEY = "key";

    public static final String VIOLATION = "violation";

    private void reportProblem(String msg, IResource loc, int violation, boolean isError, String mid, String key, int start, int end) {
        try {
            IMarker marker = loc.createMarker(mid);
            marker.setAttribute(IMarker.MESSAGE, msg + ": ");
            marker.setAttribute(IMarker.CHAR_START, start);
            marker.setAttribute(IMarker.CHAR_END, end);
            marker.setAttribute(IMarker.SEVERITY, isError ? IMarker.SEVERITY_ERROR : IMarker.SEVERITY_WARNING);
            marker.setAttribute(KEY, key);
            //          marker.setAttribute(IMarker.TRANSIENT, true);
            marker.setAttribute(VIOLATION, violation);
        } catch (CoreException e) {
            e.printStackTrace();
            return;
        }
    }

    private void addCompilation(ArrayList compilationList, final IFolder scriptDir, final IFolder compileDir, final String scriptPackageName,
            final String script) throws CoreException {
        //                         TslCompiler.compileToJava(script, scriptDir.getLocation().toString(),
        // compileDir.getLocation().toString(), scriptPackageName,null);
        NavajoScriptCompilation nsc = new NavajoScriptCompilation(script, scriptPackageName, scriptDir.getLocation().toString(), compileDir
                .getLocation().toString());
        System.err.println("NSC: " + nsc);
        compilationList.add(nsc);
    }

    /**
     * @param scriptDir
     * @param compiled
     * @param monitor
     * @param script
     */
    //    private void compileScript(IFolder scriptDir, IFolder compiled,
    // IProgressMonitor monitor, String script) {
    //        // TODO Auto-generated method stub
    //        
    //    }
    //    public void refreshResource(final IResource ir) throws CoreException {
    //        Job job = new Job("Refreshing resource") {
    //            protected IStatus run(IProgressMonitor monitor) {
    //                try {
    //                    ir.refreshLocal(IResource.DEPTH_INFINITE, monitor);
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
    //    
    //    }
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