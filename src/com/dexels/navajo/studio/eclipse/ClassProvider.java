/*
 * Created on Apr 13, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.studio.eclipse;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.dexels.utils.JarResources;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.IClassFile;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;

import com.dexels.navajo.studio.script.plugin.NavajoScriptPluginPlugin;

/**
 * @author Administrator
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ClassProvider extends DynamicClassLoader {

  
    private IJavaProject javaproject;

    private int loads = 0;

    private final Map jarResourceMap = new HashMap();

    private ArrayList classPathRes = new ArrayList();
    public ClassProvider(String adapterPath, String compiledScriptPath, boolean beta, IProject project,ClassLoader cl) {
        super(adapterPath, compiledScriptPath, beta,cl);
        if (project != null) {
            setProject(project);
        }
        // System.err.println("Provider ready.");
    }

    public void setProject(IProject p) {
        javaproject = JavaCore.create(p);

        try {
            classPathRes.clear();
            NavajoScriptPluginPlugin.getDefault().resolveProject(new ArrayList(), classPathRes, javaproject);
        } catch (JavaModelException e) {
            NavajoScriptPluginPlugin.getDefault().log("Setting project of ClassProvider. Weird.", e);
        }

    }

    protected final byte[] loadClassBytes(String className) {
        try {
            loads++;
            IType type = javaproject.findType(className);
            if (type == null) {
                return super.loadClassBytes(className);
            }
            IClassFile classfile = null;

            classfile = type.getClassFile();
            if (classfile != null) {
                IResource irr = classfile.getResource();
                if (irr instanceof IFile) {
                    IFile ifff = (IFile) irr;
                    if (ifff.getFileExtension().equals("class")) {
                        System.err.println("Loading class as binary: " + ifff.getFullPath());
                        loadClassFile(ifff);
                    }
                    if (ifff.getFileExtension().equals("jar") || ifff.getFileExtension().equals("jar")) {
                        System.err.println("Present in jar... " + ifff.getFullPath());
                        return extractClassFromJar(ifff, className);
                    }
                }
                // } else {
                // System.err.println("Classfile not found!");
            }
            ICompilationUnit icu = type.getCompilationUnit();
            byte[] result = null;
            if (icu == null) {
                return result = super.loadClassBytes(className);
            }
            IJavaProject jp = icu.getJavaProject();
            IResource resource = icu.getUnderlyingResource();
            if (resource instanceof IFile) {
                IFile ifff = (IFile) resource;
                if (ifff.getFileExtension().equals("class")) {
                    loadClassFile(ifff);
                }
            } else {
            }
            IPackageFragmentRoot sourceRoot = null;
            IPackageFragmentRoot[] roots = jp.getPackageFragmentRoots();
            for (int i = 0; i < roots.length; i++) {
                if (!roots[i].isArchive()) {
                    sourceRoot = roots[i];
                    if (sourceRoot == null) {
                        System.err.println("Could not find source path?!");
                        return null;
                    }
                    IFile res = resolveSourceType(jp, icu, sourceRoot);
                    if (res.exists()) {
                        return loadClassFile(res);
                    }
                }
            }

        } catch (Exception e) {
            NavajoScriptPluginPlugin.getDefault().log("Unclear error reading class file:", e);
        }
        return super.loadClassBytes(className);
    }

    /**
     * @param ifff
     * @param className
     * @return
     * @throws CoreException
     */
    private byte[] extractClassFromJar(IFile ifff, String className) throws Exception {
        JarResources jr = null;
        String path = ifff.getFullPath().toString();
        jr = (JarResources) jarResourceMap.get(path);
        if (jr == null) {
            String osPath = ifff.getLocation().toOSString();
            File f = new File(osPath);
            jr = new JarResources(f);
            jarResourceMap.put(path, jr);
        }
        return jr.getResource(className.replace('.', '/') + ".class");
    }

    private byte[] loadClassFile(IFile iff) throws CoreException {
        try {
            InputStream is = iff.getContents();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            NavajoScriptPluginPlugin.getDefault().copyResource(baos, is);
            is.close();
            return baos.toByteArray();
        } catch (IOException e) {
            NavajoScriptPluginPlugin.getDefault().log("IO error while loading classFile",e);
             return null;
        }
    }

    private IFile resolveSourceType(IJavaProject project, ICompilationUnit sourceCu, IPackageFragmentRoot root) throws JavaModelException {
        // Dirty hack
        IPath path = sourceCu.getAllTypes()[0].getPackageFragment().getPath();

        IPath outputLoc = sourceCu.getJavaProject().getOutputLocation();
        String name = sourceCu.getElementName();
        if (name.endsWith(".java")) {
            name = name.substring(0, name.length() - 5);
        }
        if (!root.getPath().isPrefixOf(path)) {
            System.err.println("path: " + path + " is not a prefix of: " + root.getPath());
            return null;
        }

        int commonSegments = root.getPath().matchingFirstSegments(path);
        IPath relative = path.removeFirstSegments(commonSegments);
        IPath classThing = outputLoc.append(relative);
        IPath classFilePath = classThing.append(name + ".class");
        return ResourcesPlugin.getWorkspace().getRoot().getFile(classFilePath);
    }

    public final Class getClass(String className) throws ClassNotFoundException {
        try {
            System.err.println("Looking for class: "+className);
            return super.loadClass(loadClassBytes(className), className, false, false);
        } catch (ClassNotFoundException e) {
            System.err.println("NOT FOUND!");
            throw e;
        }
    }

    public Class loadClass(String className, boolean resolveIt) throws ClassNotFoundException {
        return super.loadClass(className, resolveIt);
    }

    public final Class loadClass(String className) throws ClassNotFoundException {
        byte[] bb = loadClassBytes(className);
        return super.loadClass(bb, className, false, false);
    }

    /**
     * @return
     */
    public int getCacheSize() {
        return -1;
    }

    public int getAmountOfLoads() {
        return loads;
    }

    public ArrayList getClassPathEntries() {
        return classPathRes;
    }

    /**
     * @return
     */
    public String getOutputPath() throws JavaModelException {
        IFolder ip = ResourcesPlugin.getWorkspace().getRoot().getFolder(javaproject.getOutputLocation());
        return ip.getLocation().toOSString();
    }
}
