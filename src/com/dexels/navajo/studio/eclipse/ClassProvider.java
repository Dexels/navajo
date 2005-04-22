/*
 * Created on Apr 13, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.studio.eclipse;

import java.io.*;
import java.util.zip.*;

import org.dexels.utils.*;
import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.eclipse.jdt.core.*;
import org.eclipse.jdt.core.search.*;
import org.eclipse.jdt.core.util.*;
import org.eclipse.jdt.internal.core.*;

import com.dexels.navajo.loader.*;

/**
 * @author Administrator
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ClassProvider extends NavajoClassLoader {

    private String adapterPath;

    private boolean beta;

    private String compiledScriptPath;

    private IJavaProject project;

    private int loads = 0;

    //    IJavaProject project =
    // JavaCore.getJavaCore().create(root.getProject(projectName));

    public ClassProvider(String adapterPath, String compiledScriptPath, boolean beta, IProject project) {
        super(adapterPath, compiledScriptPath, beta);
        this.project = JavaCore.getJavaCore().create(project);
        //    this.project = project;
        this.adapterPath = adapterPath;
        this.beta = beta;
        this.compiledScriptPath = compiledScriptPath;
    }

    protected final byte[] loadClassBytes(String className) {
        try {
            loads++;
            IType type = project.findType(className);
            IClassFile classfile = null;
            System.err.println("Type: " + className);
            //            type.getClassFile();
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
                        System.err.println("Present in jar... "+ifff.getFullPath());
                        return extractClassFromJar(ifff, className);
                    }
                }
            }
            ICompilationUnit icu = type.getCompilationUnit();
            //          System.err.println("COMP UNIT: "+icu);

            byte[] result = null;
            if (icu == null) {
                return result = super.loadClassBytes(className);
            }

            //            type.getCompilationUnit() getClassFile());
            IJavaProject jp = icu.getJavaProject();

            IResource resource = icu.getUnderlyingResource();
            if (resource instanceof IFile) {
                IFile ifff = (IFile) resource;
                if (ifff.getFileExtension().equals("class")) {
                    System.err.println("Loading class as binary: " + ifff.getFullPath());
                    loadClassFile(ifff);
                }
            } else {
                System.err.println("Resource: " + resource + " is not a file");
            }
            IPackageFragmentRoot sourceRoot = null;
            IPackageFragmentRoot[] roots = jp.getPackageFragmentRoots();
            for (int i = 0; i < roots.length; i++) {
                //                System.err.println("ROOT: "+roots[i].getElementName());
                if (!roots[i].isArchive()) {
                    sourceRoot = roots[i];
                    if (sourceRoot == null) {
                        System.err.println("Could not find source path?!");
                        return null;
                    }
                    //            IClassFileReader rr =
                    // org.eclipse.jdt.core.ToolFactory.createDefaultClassFileReader(classfile,
                    // org.eclipse.jdt.core.util.IClassFileReader.ALL);
                    IFile res = resolveSourceType(jp, icu, sourceRoot);
                    if (res == null) {
                        System.err.println("Could not resolve Type: " + type);
                        System.err.println(" 'source' file: " + resource);
                    }
                    if (res.exists()) {
                        return loadClassFile(res);
                    }
                }
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
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
        String osPath = ifff.getLocation().toOSString();
        File f = new File(osPath);
        System.err.println("F: "+f.toString());
        System.err.println("Classname: "+className);
        JarResources jr = new JarResources(f);
       System.err.println("looking for: "+className.replace('.', '/')+".class");
        return jr.getResource(className.replace('.', '/')+".class");
        //        ZipInputStream zis = new ZipInputStream(ifff.getContents());
        //        String path = className.replace(".", "/")+".class";
        //        System.err.println("Extracting from: "+ifff.getFullPath()+" looking
        // for: "+path);
        //        return null;
    }

    private byte[] loadClassFile(IFile iff) throws CoreException {
        //          System.err.println("LOADING CLASSBYTES FROM FILE:
        // "+iff.getFullPath().toString());
        InputStream is = iff.getContents();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        copyResource(baos, is);
        return baos.toByteArray();
    }

    private IType resolveBinaryType(IClassFile classFile) throws JavaModelException {
        IPath path = classFile.getType().getPackageFragment().getPath();
        IPath outputLoc = project.getOutputLocation();

        if (!outputLoc.isPrefixOf(path)) {
            return null;
        }

        int commonSegments = outputLoc.matchingFirstSegments(path);
        IPath relative = path.removeFirstSegments(commonSegments);
        String packageName = relative.toString().replace('/', '.');
        String className = classFile.getType().getTypeQualifiedName();
        if (!packageName.equals("")) {
            className = packageName + "." + className;
        }
        return project.findType(className);
    }

    private IFile resolveSourceType(IJavaProject project, ICompilationUnit sourceCu, IPackageFragmentRoot root) throws JavaModelException {
        // Dirty hack
        IPath path = sourceCu.getAllTypes()[0].getPackageFragment().getPath();

        IPath outputLoc = sourceCu.getJavaProject().getOutputLocation();
        //        sourceCu.getJavaProject().g
        String name = sourceCu.getElementName();
        if (name.endsWith(".java")) {
            name = name.substring(0, name.length() - 5);
        }
        //        System.err.println("path: "+path+" name: "+name);
        if (!root.getPath().isPrefixOf(path)) {
            System.err.println("path: " + path + " is not a prefix of: " + root.getPath());
            return null;
        }

        int commonSegments = root.getPath().matchingFirstSegments(path);
        IPath relative = path.removeFirstSegments(commonSegments);
        IPath classThing = outputLoc.append(relative);
        IPath classFilePath = classThing.append(name + ".class");
        //        System.err.println("output location: "+classThing);
        //        System.err.println("output location: "+classFilePath);
        return ResourcesPlugin.getWorkspace().getRoot().getFile(classFilePath);
        //        String packageName = relative.toString().replace('/','.');
        //        System.err.println("PACKAGENAME: "+packageName);
        //        String className = classFile.getType().getTypeQualifiedName();
        //        if (!packageName.equals("")) {
        //            className = packageName + "." + className;
        //        }
        //        return project.findType(className);
    }

    private void copyResource(OutputStream out, InputStream in) throws CoreException {
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

    public final Class getClass(String className) throws ClassNotFoundException {
        System.err.println("CLASS REQUESTED: *********** " + className);
        return super.loadClass(loadClassBytes(className), className, false, false);
        //        return super.getClass(className);
    }

    public Class loadClass(String className, boolean resolveIt) throws ClassNotFoundException {
        System.err.println("CLASS LOAD REQUESTED: *********** " + className + " resolve? " + resolveIt);
        return super.loadClass(loadClassBytes(className), className, false, false);
        //       return super.loadClass(className,resolveIt);
    }

    public final Class loadClass(String className) throws ClassNotFoundException {
        System.err.println("CLASS LOAD REQUESTED: *********** " + className);
        byte[] bb = loadClassBytes(className);
        if (bb!=null) {
            System.err.println("Bytes found: " + bb.length);
        } else {
            System.err.println("Classname: "+className+" not present in the ClassProvider");
        }
        return super.loadClass(bb, className, false, false);
        //      return super.loadClass(className);

    }

    /**
     * @return
     */
    public int getCacheSize() {
        return classes.size();
    }

    public int getAmountOfLoads() {
        return loads = 0;
    }

}
