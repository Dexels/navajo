/*
 * Created on Apr 13, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.studio.eclipse;

import java.io.*;
import java.util.*;
import java.util.zip.*;

import org.dexels.utils.*;
import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.eclipse.jdt.core.*;
import org.eclipse.jdt.core.search.*;
import org.eclipse.jdt.core.util.*;
import org.eclipse.jdt.internal.core.*;

import com.dexels.navajo.loader.*;
import com.dexels.navajo.studio.script.plugin.*;

/**
 * @author Administrator
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ClassProvider extends NavajoClassLoader {

//    private String adapterPath;

//    private boolean beta;

//    private String compiledScriptPath;

    private IJavaProject javaproject;

    private int loads = 0;

    private final Map jarResourceMap = new HashMap();

    private ArrayList classPathRes = new ArrayList();

    //    IJavaProject project =
    // JavaCore.getJavaCore().create(root.getProject(projectName));

    public ClassProvider(String adapterPath, String compiledScriptPath, boolean beta, IProject project) {
        super(adapterPath, compiledScriptPath, beta);
        //    this.project = project;
//        this.adapterPath = adapterPath;
//        this.beta = beta;
//        this.compiledScriptPath = compiledScriptPath;
 
//        System.err.println("Class provider created for project: "+project.getName());
//        System.err.println("Adapterpath: "+adapterPath+" comp. scriptpath: " +compiledScriptPath);
        if (project!=null) {
            setProject(project);
        }
//        System.err.println("Provider ready.");
    }
    
    public void setProject(IProject p) {
//        System.err.println("IN setProject");
        javaproject = JavaCore.getJavaCore().create(p);
 
        try {
          classPathRes.clear();
            NavajoScriptPluginPlugin.getDefault().resolveProject(new ArrayList(),classPathRes, javaproject);
             
            //           IClasspathEntry[] ice = javaproject.getResolvedClasspath(true);
//            System.err.println("Classpath size: "+classPathRes.size());
            //            for (int i = 0; i < classPathRes.size(); i++) {
//                IPath pp = (IPath)classPathRes.get(i);
//                if (pp.segmentCount()==1) {
//                    System.err.println("Not trusting: "+pp);
//                } else {
//                    IFile entry = ResourcesPlugin.getWorkspace().getRoot().getFile(pp);
//                    
//                    if (entry.exists()) {
//                        classPathRes.add(entry.getLocation().toOSString());
//                    } else {
//                        classPathRes.add(pp.toOSString());
//                    }
//                    
//                }
//            }
        } catch (JavaModelException e) {
            e.printStackTrace();
        }
//        System.err.println("CREATED PROVIDER FOR PROJECT: "+p.getName());
        
    }

    protected final byte[] loadClassBytes(String className) {
//        System.err.println("INVALIDATED CLASS PROVIDER FUNCTION! REVERTING TO SUPER");
//        if (true) {
//            return super.loadClassBytes(className);
//        }
//        System.err.println("In ClassProvider.loadClassBytes "+className);
        try {
            loads++;
            IType type = javaproject.findType(className);
            if (type==null) {
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
//            } else {
//                System.err.println("Classfile not found!");
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
        JarResources jr = null;
        String path = ifff.getFullPath().toString();
        jr = (JarResources) jarResourceMap.get(path);
        if (jr == null) {
            String osPath = ifff.getLocation().toOSString();
            File f = new File(osPath);
//            System.err.println("F: " + f.toString());
//            System.err.println("Classname: " + className);
            jr = new JarResources(f);
            jarResourceMap.put(path, jr);
        }
//        System.err.println("looking for: " + className.replace('.', '/') + ".class");
        return jr.getResource(className.replace('.', '/') + ".class");
        //        ZipInputStream zis = new ZipInputStream(ifff.getContents());
        //        String path = className.replace(".", "/")+".class";
        //        System.err.println("Extracting from: "+ifff.getFullPath()+" looking
        // for: "+path);
        //        return null;
    }

    private byte[] loadClassFile(IFile iff) throws CoreException {
                  try {
//                    System.err.println("LOADING CLASSBYTES FROM FILE:"+iff.getFullPath().toString());
      InputStream is = iff.getContents();
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      NavajoScriptPluginPlugin.getDefault().copyResource(baos, is);
      is.close();
//      System.err.println("# of bytes: "+baos.size());
      return baos.toByteArray();
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
    }

//    private IType resolveBinaryType(IClassFile classFile) throws JavaModelException {
//        IPath path = classFile.getType().getPackageFragment().getPath();
//        IPath outputLoc = javaproject.getOutputLocation();
//
//        if (!outputLoc.isPrefixOf(path)) {
//            return null;
//        }
//
//        int commonSegments = outputLoc.matchingFirstSegments(path);
//        IPath relative = path.removeFirstSegments(commonSegments);
//        String packageName = relative.toString().replace('/', '.');
//        String className = classFile.getType().getTypeQualifiedName();
//        if (!packageName.equals("")) {
//            className = packageName + "." + className;
//        }
//        return javaproject.findType(className);
//    }

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

//    private void copyResource(OutputStream out, InputStream in) throws CoreException {
//        BufferedInputStream bin = null;
//        BufferedOutputStream bout = null;
//        try {
//            bin = new BufferedInputStream(in);
//            bout = new BufferedOutputStream(out);
//            byte[] buffer = new byte[1024];
//            int read;
//            while ((read = bin.read(buffer)) > -1) {
//                bout.write(buffer, 0, read);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//            throw new CoreException(Status.CANCEL_STATUS);
//        } finally {
//            try {
//                if (bin != null) {
//                    bin.close();
//                }
//                if (bout != null) {
//                    bout.flush();
//                    bout.close();
//
//                }
//            } catch (IOException e1) {
//                e1.printStackTrace();
//                throw new CoreException(Status.CANCEL_STATUS);
//            }
//
//        }
//    }

    
    // TODO CHECK IF IT STILL WORKS
    public final Class getClass(String className) throws ClassNotFoundException {
//        System.err.println("CLASS REQUESTED: *********** " + className);
        try {
            return super.loadClass(loadClassBytes(className), className, false, false);
        } catch (ClassNotFoundException e) {
            System.err.println("NOT FOUND!");
            throw e;
        }

        //        return super.getClass(className);
    }

    
    public Class loadClass(String className, boolean resolveIt) throws ClassNotFoundException {
//        System.err.println("CLASS loadClass REQUESTED: *********** " + className + " resolve? " + resolveIt);
//        return super.loadClass(loadClassBytes(className), className, false, false);
        return super.loadClass(className,resolveIt);
    }

    public final Class loadClass(String className) throws ClassNotFoundException {
//        System.err.println("CLASS loadClass REQUESTED: *********** " + className);
        byte[] bb = loadClassBytes(className);
        return super.loadClass(bb, className, false, false);
//              return super.loadClass(className);
    }

    /**
     * @return
     */
    public int getCacheSize() {
        return classes.size();
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
