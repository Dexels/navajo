package org.dexels.utils;

import com.dexels.navajo.loader.NavajoClassSupplier;


/**
 * A simple test class loader capable of loading from
 * multiple sources, such as local files or a URL.
 *
 * This class is derived from an article by Chuck McManis
 * http://www.javaworld.com/javaworld/jw-10-1996/indepth.src.html
 * with large modifications.
 *
 * Note that this has been updated to use the non-deprecated version of
 * defineClass() -- JDM.
 *
 * @author Jack Harich - 8/18/97
 * @author John D. Mitchell - 99.03.04
 * @author Arjen Schoneveld - 17.06.01
 *
 */

public abstract class MultiClassLoader extends NavajoClassSupplier {



    // ---------- Fields --------------------------------------

    //public Hashtable classes = new Hashtable();
    private char      classNameReplacementChar;
    protected boolean   monitorOn = false;
    protected boolean   sourceMonitorOn = true;
    // ---------- Initialization ------------------------------

    public MultiClassLoader(ClassLoader parent) {
    	super(parent);
    }

    public void clearCache() {
//        if (classes != null) {
//            classes.clear();
//            classes = new Hashtable();
//        }
    }

    // ---------- Superclass Overrides ------------------------
    /**
     * This is a simple version for external clients since they
     * will always want the class resolved before it is returned
     * to them.
     */



    public Class<?> loadClass(String className) throws ClassNotFoundException {
        return (loadClass(className, true, false));
    }

    public Class<?> loadClass(String className, boolean resolveIt) throws ClassNotFoundException {
        return (loadClass(className, resolveIt, false));
    }

    // ---------- Abstract Implementation ---------------------

    public synchronized Class<?> loadClass(String className, boolean resolveIt, boolean useCache) throws ClassNotFoundException {
       byte[]  classBytes;
       classBytes = loadClassBytes(className);
       return loadClass(classBytes, className, resolveIt, useCache);
    }

    public synchronized Class<?> loadClass(byte [] classBytes, String className,
            boolean resolveIt, boolean useCache) throws ClassNotFoundException {
        Class<?>   result;
 
        //System.err.println(this.hashCode() + ": in loadClass(), className " + className);
       
//        result = (Class) classes.get(className);
//        if (result != null) {
//        	System.err.println(this.hashCode() + ": Found " + className + " in cache!");
//          return result;
//        }

        if (classBytes == null) {

            // --- Try with Class.forName
//            try {
//            	System.err.println(this.hashCode() + ": Try with Class.forname: " + getParent().hashCode());
//                result = Class.forName(className);
//                //classes.put(className, result);
//                return result;
//            } catch (ClassNotFoundException e) {
//                //System.out.println("Not found with Class.forName");
//            }

            // ----- Check with the primordial class loader

            try {
                //System.err.println(this.hashCode() + ":Attempting to load class "+className+" from parent classloader");
                result = Class.forName(className, true, getParent()); //super.findSystemClass(className);
                //classes.put(className, result);
                //monitor(">> returning system class (in CLASSPATH).");

                return result;

            } catch (ClassNotFoundException e) {
              //System.err.println("Did not succeed");
                //monitor(">> Not a system class.");
            }
            throw new ClassNotFoundException();
        } else {
            //monitor("Found class in jar");
        }

        // ----- Define it (parse the class file)

        result = defineClass(className, classBytes, 0, classBytes.length);

        if (result == null) {
            //System.out.println("ClassFormatError");
            throw new ClassFormatError();
        }

        //monitor("defined class, result = " + result);
        // ----- Resolve if necessary
        if (resolveIt) resolveClass(result);
        //monitor("resolved class");

        // Done
//        classes.put(className, result);
//        System.err.println("Putting " + className + " in cache!!!");
        //monitor(">> Returning newly loaded class.");

        return result;

    }


//    protected Class getCachedClass(String name) {
//    	return (Class)classes.get(name);
//    }
    

    // ---------- Public Methods ------------------------------

    /**
     * This optional call allows a class name such as
     * "COM.test.Hello" to be changed to "COM_test_Hello",
     * which is useful for storing classes from different
     * packages in the same retrival directory.
     * In the above example the char would be '_'.
     */

    public void setClassNameReplacementChar(char replacement) {
        classNameReplacementChar = replacement;
    }

    // ---------- Protected Methods ---------------------------

    protected abstract byte[] loadClassBytes(String className);

    protected String formatClassName(String className) {

        if (classNameReplacementChar == '\u0000') {
            // '/' is used to map the package to the path
            return className.replace('.', '/') + ".class";
        } else {
            // Replace '.' with custom char, such as '_'
            return className.replace('.',
                    classNameReplacementChar) + ".class";
        }
    }

    protected void monitor(String text) {
        if (monitorOn) print(text);
    }

    // --- Std
    protected static void print(String text) {
        System.out.println(text);
    }


} // End class
