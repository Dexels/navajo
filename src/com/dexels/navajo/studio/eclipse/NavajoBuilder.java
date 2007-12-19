/*
 * Created on Feb 24, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.studio.eclipse;

import java.io.*;
import java.util.*;

import javax.sound.midi.*;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.eclipse.core.runtime.jobs.*;
import org.eclipse.jdt.core.*;
import org.eclipse.jdt.internal.compiler.batch.*;
import org.eclipse.jdt.internal.formatter.*;
import org.eclipse.jface.dialogs.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.internal.*;
// import org.eclipse.osgi.service.datalocation.*;

import com.dexels.navajo.client.*;
import com.dexels.navajo.document.*;
import com.dexels.navajo.document.nanoimpl.*;
import com.dexels.navajo.loader.*;
import com.dexels.navajo.mapping.compiler.*;
import com.dexels.navajo.mapping.compiler.meta.*;
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

    public static final int LARGE_COMPILE_THRESHOLD = 100;
    private boolean isOkToCompile;
//    private TslMetaDataHandler metaDataHandler;
    private int compileCount;
    private boolean classPathChanged;

    /**
     * 
     */
    public NavajoBuilder() {
        super();
        // Force dependency to JDT Compiler. Not pretty, but used to fool 
        // the dependency optimizer. It will kick out the JDT dependency otherwise
        // because it can not see the dependency
        Main m;
    }

    protected void startupOnInitialize() {
        if (getProject() == null) {
            System.err.println("Builder project null.");
        } else {
            System.err.println("Builder created for project: " + getProject().getName());
        }
        NavajoScriptPluginPlugin.getDefault().setNavajoBuilder(this);
//
//        InputStream metaIn = null;
//        try {
//            IFile iff = NavajoScriptPluginPlugin.getDefault().getScriptMetadataFile((IProject) getProject());
//            if (iff != null && iff.exists()) {
//                iff.refreshLocal(0, null);
//                metaIn = iff.getContents();
//                metaDataHandler.loadScriptData(metaIn);
//            }
//        } catch (Exception e) {
//            NavajoScriptPluginPlugin.getDefault().log(e);
//        } finally {
//            if (metaIn != null) {
//                try {
//                    metaIn.close();
//                } catch (IOException e1) {
//                    NavajoScriptPluginPlugin.getDefault().log(e1);
//                }
//            }
//        }
//        NavajoScriptPluginPlugin.getDefault().getNavajoCompiler(getProject()).addMetaDataListener(metaDataHandler);
    }

    
    

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.core.resources.IncrementalProjectBuilder#build(int,
     *      java.util.Map, org.eclipse.core.runtime.IProgressMonitor)
     */
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
            ArrayList compileList = new ArrayList();
              fullBuild(compileList, script, compiled, monitor, script, "");
            System.err.println("Fullbuild would compile: " + compileList.size());
            try {
                compileScript(compileList, monitor);
            } catch (CoreException e1) {
                NavajoScriptPluginPlugin.getDefault().log(e1);
            } catch (NavajoPluginException e1) {
                NavajoScriptPluginPlugin.getDefault().log(e1);
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
                    // continue;
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
                    // System.err.println("Script: " + ir[i].getName());

                } else {
                    MessageDialog.openInformation(Display.getCurrent().getActiveShell(), "Geen idee wat voor script dingetje dit is:", name);
                }
            }
        }
    }

    public void clean(int kind, final IFolder compileDir, IProgressMonitor ipm) throws CoreException {
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
        final ArrayList changed = new ArrayList();
        final ArrayList removed = new ArrayList();
        compileCount = 0;
        final IFolder configFolder = NavajoScriptPluginPlugin.getDefault().getNavajoConfigFolder(getProject());
        final IFolder adapterFolder = NavajoScriptPluginPlugin.getDefault().getAdaptersFolder(getProject());
                
        classPathChanged = false;
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
                    if (fold.getFullPath().equals(configFolder)) {
                       return true;
                    }
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

                    classPathChanged = true;
                }
                if (current.getName().equals(".navajoroot")) {
                    System.err.println("Detected navajoroot change, project: "+current.getProject().getName());
                    try {
                        NavajoScriptPluginPlugin.getDefault().logMessage("Navajoroot changed, refreshing both root path and server.xml");
                        NavajoScriptPluginPlugin.getDefault().refreshNavajoRootPath(current.getProject());
                        NavajoScriptPluginPlugin.getDefault().refreshServerXmlNavajo(current.getProject());
                        classPathChanged = true;
                    } catch (NavajoPluginException e) {
                        NavajoScriptPluginPlugin.getDefault().showError("Error refreshing navajo root?!",e);
                        e.printStackTrace();
                    }
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
                    classPathChanged = true;
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
            if (classPathChanged) {
                System.err.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Rebuilding stuff");
                NavajoScriptPluginPlugin.getDefault().refreshNavajoCompiler(getProject());
                NavajoScriptPluginPlugin.getDefault().getClassProvider(getProject(), true);
                NavajoScriptPluginPlugin.getDefault().showInfo("A classpath change occurred!");
                NavajoScriptPluginPlugin.getDefault().refreshCompilerClassLoader(getProject());
            }
            try {
                monitor.beginTask("Compiling TSL", changed.size());
                String name = ird.getFullPath().toString();
                ArrayList compilation = new ArrayList();
                Set compilationSet = new HashSet();
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
                    final String scriptString = script;
                    // if (scriptPackage.getName().equals("include")) {
                    // System.err.println("Skipping include dir...");
                    // continue;
                    // }
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
                     compileScript(compilation, monitor);
                }
                for (Iterator it = removed.iterator(); it.hasNext();) {
                    IFile element = (IFile) it.next();
                    if (element==null) {
                        continue;
                    }
                    String scriptName = NavajoScriptPluginPlugin.getDefault().getScriptName(element, element.getProject());
                    IFile tmlFile = NavajoScriptPluginPlugin.getDefault().getTmlFile(element.getProject(), scriptName);
                    if (tmlFile.exists()) {
                        NavajoScriptPluginPlugin.getDefault().deleteFile(tmlFile, monitor);
                    }
                    IFile compiledFile = NavajoScriptPluginPlugin.getDefault().getCompiledScriptFile(element.getProject(), scriptName);
                    if (compiledFile.exists()) {
                        NavajoScriptPluginPlugin.getDefault().deleteFile(compiledFile, monitor);
                    }
                }
            } catch (Throwable e1) {
                NavajoScriptPluginPlugin.getDefault().log("General error compiling script ",e1);
            }
             } else {
                 System.err.println("No resource delta!");
             }
   }

    private void compileScript(final ArrayList compilationList, IProgressMonitor monitor) throws CoreException, NavajoPluginException {
//        System.err.println("Compilelist: " + compilationList.size());
        if (compilationList.size() > LARGE_COMPILE_THRESHOLD) {
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
        final ArrayList javaCompilations = new ArrayList();
//        Job job = new Job("Compiling scripts: ") {
//            protected IStatus run(IProgressMonitor monitor) {
                long totalOld = 0;
                long totalNew = 0;

                long totalJava = 0;
                int totalJavaScript = 0;
                int totalLines = 0;
                double javaAvg = 0;
                long time = System.currentTimeMillis();
                
//                Set classPathSet = new HashSet();
                monitor.beginTask("Compiling TSL", compilationList.size());
                IFolder fbin = null;
                try {
                    fbin = NavajoScriptPluginPlugin.getDefault().getCompiledScriptFolder(getProject());
                    if (!fbin.exists()) {
                        fbin.create(false, true, null);
                    }
                } catch (NavajoPluginException e2) {
                    NavajoScriptPluginPlugin.getDefault().log("Error compliling script ",e2);
                } catch (CoreException e2) {
                    NavajoScriptPluginPlugin.getDefault().log("Error compliling script ",e2);
                }
//                
                
                
                
                
                ArrayList compiledFiles = new ArrayList();
                NanoTslCompiler navajoCompiler = NavajoScriptPluginPlugin.getDefault().getNavajoCompiler(getProject());
                ClassProvider cp = NavajoScriptPluginPlugin.getDefault().getClassProvider(getProject(), false);
//                System.err.println("ClassPro: "+cp.toString());
//                System.err.println("navavcomp: "+navajoCompiler.toString());
//                System.err.println("Navacomp cl: "+navajoCompiler.getClass().getClassLoader());
                
                for (int i = 0; i < compilationList.size(); i++) {
                    if (monitor.isCanceled()) {
                        break;
                    }
                    NavajoScriptCompilation current = (NavajoScriptCompilation) compilationList.get(i);
                    String javaSrc = current.getCompileDir() + "/" + current.getScript() + ".java";
                       try {
                        IFile ifi = NavajoScriptPluginPlugin.getDefault().getScriptFile(getProject(), current.getScript());
                        IFile output = NavajoScriptPluginPlugin.getDefault().getCompiledScriptFile(getProject(), current.getScript());
                        IFile classfile = NavajoScriptPluginPlugin.getDefault().getClassFile(getProject(), current.getScript());
                        try {
                            monitor.setTaskName(" Compiling: " + i + "/" + compilationList.size() + " " + current.getScript());
                            monitor.worked(1);
                            if (!ifi.exists()) {
                                continue;
                            }
                            ifi.deleteMarkers(IMarker.PROBLEM, true, 0);// monitor.worked(1);
                            long cc = System.currentTimeMillis();
                            int lines = navajoCompiler.compileTsl(current.getScript(), current.getScriptDir(), current.getCompileDir(), current
                                    .getScriptPackage(), true);
                            totalLines += lines;
                            totalNew += (System.currentTimeMillis() - cc);
                                    totalJava += (System.currentTimeMillis() - cc);
                            totalJavaScript++;
                            cc = System.currentTimeMillis();
                            javaCompilations.add(javaSrc);
                            compiledFiles.add(output);
                        } catch (Throwable e) {
                            NavajoScriptPluginPlugin.getDefault().log("Compile exception:", e);
                            int start = 0;
                            int end = 5;
                            if (e instanceof TslCompileException) {
                                TslCompileException tce = (TslCompileException)e;
                                System.err.println("TCE detected!");
                                if (tce.isAttributeProblem()) {
                                    System.err.println("ATTRIBUTEPROBLEM::: "+tce.getOffendingAttribute());
                                    System.err.println("ALTERNATIVES: "+tce.getSolutions());
//                                    start = tce.getStartOffset();
                                    start = 10;
                                    end = 20;
                                } else {
                                    System.err.println("TagPROBLEM!");
                                    System.err.println("ALTERNATIVES: "+tce.getSolutions());
                                    start = ((TslCompileException) e).getStartOffset();
                                    end = ((TslCompileException) e).getEndOffset();
                               }
                                
                                try {
                                    NavajoScriptPluginPlugin.getDefault().reportProblem(e.getMessage(), ifi, start, end + 1, (TslCompileException) e);
                                    
                                } catch (CoreException e1) {
                                    NavajoScriptPluginPlugin.getDefault().log("Error reporting error. This is a classic one.",e1);
                                }
                                if (classfile.exists()) {
                                    if (!classfile.isSynchronized(0)) {
                                        classfile.refreshLocal(0, monitor);
                                    }
                                    classfile.delete(false, null);
                                }
                                if (output.exists()) {
                                    if (!output.isSynchronized(0)) {
                                        output.refreshLocal(0, monitor);
                                    }
                                    output.delete(false, null);
                                }
                                continue;
                            }
                            if (ifi != null) {
                                if (!ifi.exists()) {
                                } else {
                                    System.err.println("REPORTING PROBLEM!");
                                    try {
                                        NavajoScriptPluginPlugin.getDefault().reportProblem(e.getMessage(), ifi, start, end + 1, null);
                                    } catch (CoreException e1) {
                                        NavajoScriptPluginPlugin.getDefault().log("Oh great, an error displaying an error. ",e1);
                                    }
                                }
                            }
                        }
                    } catch (NavajoPluginException e) {
                        e.printStackTrace();
                        NavajoScriptPluginPlugin.getDefault().log(e);
                    }
                }
                monitor.worked(1);
                monitor.setTaskName("Compiling "+javaCompilations.size()+" java files...");
              if (javaCompilations.size()>0) {
                    try {
                        Class cc = Class.forName("org.eclipse.jdt.internal.compiler.batch.Main",true,NavajoScriptPluginPlugin.class.getClassLoader());
                        if (cc!=null) {
                            navajoCompiler.setCompileClassLoader(cc.getClass().getClassLoader());
                        } else {
                            navajoCompiler.setCompileClassLoader(getClass().getClassLoader());
                        }
                        navajoCompiler.compileAllTslToJava(javaCompilations);
                    } catch (NavajoPluginException e3) {
                        NavajoScriptPluginPlugin.getDefault().log("Error compiling java files.",e3);
                    } catch (Exception e3) {
                        NavajoScriptPluginPlugin.getDefault().log("Error compiling java files.",e3);
                    }
                }
                monitor.done();
                System.err.println("Total compiled lines: " + totalLines);
                System.err.println("TSL COMPILE TOOK: " + totalNew);
                System.err.println("Performance: " + ((1000 * totalLines) / (totalNew + 1)) + " lines/sec.");
                System.err.println("JAVA COMPILE TOOK: " + totalJavaScript + " scripts in " + totalJava + " millis!");
                long cc = System.currentTimeMillis();
                try {
              if (compiledFiles.size() > LARGE_COMPILE_THRESHOLD) {
                        IFolder ifff = NavajoScriptPluginPlugin.getDefault().getCompileFolder(getProject());
                        ifff.refreshLocal(IResource.DEPTH_INFINITE, null);
                    } else {
                        for (int i = 0; i < compiledFiles.size(); i++) {
                            IFile c = (IFile) compiledFiles.get(i);
                            c.refreshLocal(0, null);
                        }
                    }
                } catch (NavajoPluginException e) {
                    NavajoScriptPluginPlugin.getDefault().log("Error refreshing .java files after build!",e);
                } catch (CoreException e) {
                    NavajoScriptPluginPlugin.getDefault().log("Error refreshing .java files after build!",e);
                }

                long diff = System.currentTimeMillis() - cc;
    }

    private void addCompilation(ArrayList compilationList, final IFolder scriptDir, final IFolder compileDir, final String scriptPackageName,
            final String script) throws CoreException {
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