package com.dexels.navajo.document;

/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author Arjen Schoneveld
 * @version 1.0
 */

public abstract class NavajoFactory {

    protected static NavajoFactory impl = null;

    /**
     * Get the default NavajoFactory implementation.
     *
     * @return
     */
    public static NavajoFactory getInstance() {
        if (impl == null) {
           String name = System.getProperty("com.dexels.navajo.DocumentImplementation");
           System.out.println("com.dexels.navajo.DocumentImplementation = " + name);
           if (name == null)
              name = "com.dexels.navajo.document.jaxpimpl.NavajoFactoryImpl";
           try {
             System.out.println("USING DOCUMENT IMPLEMENTATION: " + name);
             impl = (NavajoFactory) Class.forName(name).newInstance();
           } catch (Exception e) {
              e.printStackTrace();
           }
        }
        return impl;
    }

    /**
     * Get a specific NavajoFactory implementation.
     *
     * @param className
     * @return
     */
    public static NavajoFactory getInstance(String className) {
      try {
        return (NavajoFactory) Class.forName(className).newInstance();
      } catch (Exception e) {
        e.printStackTrace();
        return null;
      }
    }

    public abstract NavajoException createNavajoException(String message);
    public abstract NavajoException createNavajoException(Exception e);
    public abstract Navajo createNavajo(java.io.InputStream stream);
    public abstract Navajo createNavajo(Object representation);
    public abstract Navajo createNavajo();
    public abstract Header createHeader(Navajo n, String rpcName, String rpcUser, String rpcPassword, long expiration_interval);
    public abstract Message createMessage(Object representation);
    public abstract Message createMessage(Navajo n, String name);
    public abstract Message createMessage(Navajo tb, String name, String type);
    public abstract Property createProperty(Object representation);
    public abstract Property createProperty(Navajo tb, String name, String cardinality, String description, String direction) throws NavajoException;
    public abstract Property createProperty(Navajo tb, String name, String type, String value, int length,
            String description, String direction) throws NavajoException;
    public abstract Selection createSelection(Navajo tb, String name, String value, boolean selected);
    public abstract Selection createDummySelection();
    public abstract Method createMethod(Navajo tb, String name, String server);
    public abstract Point createPoint(Property p) throws NavajoException;
}