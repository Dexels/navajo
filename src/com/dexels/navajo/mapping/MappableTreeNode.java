package com.dexels.navajo.mapping;

/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author Arjen Schoneveld
 * @version 1.0
 */

import java.util.HashMap;
import java.lang.reflect.*;
import com.dexels.navajo.server.Parameters;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.NavajoConfig;
import com.dexels.navajo.server.UserException;

public class MappableTreeNode implements Mappable {

        public MappableTreeNode parent = null;
        public Mappable myMap = null;
        public Object myObject = null;
        public String name = "";
        public String ref = "";
        public long starttime;
        public long endtime = -1;
        public int totaltime;

        // HashMap to cache method references.
        private HashMap methods;

        public MappableTreeNode(MappableTreeNode parent, Object o) {
            this.parent = parent;
            this.myObject = o;
            methods = new HashMap();
            starttime = System.currentTimeMillis();
        }

        public Mappable getMyMap() {
          if (myObject != null) {
            return (Mappable) myObject;
          } else {
            return null;
          }
        }

        public void setEndtime() {
          endtime = System.currentTimeMillis();
        }

        public int getTotaltime() {
          if (endtime == -1) {
            return (int) (System.currentTimeMillis() - starttime);
          } else {
            return (int) (endtime - starttime);
          }
        }

        public String getMapName() {
          if (myObject != null) {
            return myObject.getClass().getName();
          } else {
            return null;
          }
        }

       public Method getMethodReference(String name, Object [] arguments) throws MappingException {

          StringBuffer key = new StringBuffer();
          key.append(name);

          // Determine method unique method key:
          Class [] classArray = null;

          if (arguments != null) {
              // Get method with arguments.
              classArray = new Class[arguments.length];
              for (int i = 0; i < arguments.length; i++) {
                classArray[i] = arguments[i].getClass();
                key.append(arguments[i].getClass().getName());
              }
          }

          Method m = (Method) methods.get(key.toString());

          if (m == null) {

              StringBuffer methodNameBuffer = new StringBuffer();
              methodNameBuffer.append("get").append( (name.charAt(0) + "").toUpperCase()).
                append(name.substring(1, name.length()));

              String methodName = methodNameBuffer.toString();

              Class c = myObject.getClass();

              try {
                  m = c.getMethod(methodName, classArray);
                  methods.put(key.toString(), m);
                } catch (NoSuchMethodException nsme) {
                      throw new MappingException("Could not find method in Mappable object: " + methodName);
              }
          }

           return m;

       }

       public Method setMethodReference(String name, Class [] parameters) throws MappingException {

               java.lang.reflect.Method m = (Method) methods.get(name+parameters.hashCode());

               if (m == null) {
                     String methodName = "set" + (name.charAt(0) + "").toUpperCase()
                          + name.substring(1, name.length());

                      Class c = this.myObject.getClass();

                      java.lang.reflect.Method[] all = c.getMethods();
                      for (int i = 0; i < all.length; i++) {
                        if (all[i].getName().equals(methodName)) {
                          m = all[i];
                          Class[] inputParameters = m.getParameterTypes();
                          if (inputParameters.length == parameters.length) {
                            for (int j = 0; j < inputParameters.length; j++) {
                              Class myParm = parameters[j];
                              if (inputParameters[j].isAssignableFrom(myParm)) {
                                i = all.length + 1;
                                j = inputParameters.length + 1;
                                break;
                              }
                            }
                          }
                        }
                      }
                  if (m == null) {
                      throw new MappingException("Could not find method in Mappable object: " + methodName);
                  }
                  methods.put(name+parameters.hashCode(), m);
             }

            return m;
        }
  public void load(Parameters parms, Navajo inMessage, Access access, NavajoConfig config) throws MappableException, UserException {
  }

  public void store() throws MappableException, UserException {
  }

  public void kill() {
  }

}
