
/**
 * Title:        Navajo (version 2)<p>
 * Description:  <p>
 * Copyright:    Copyright (c) Arjen Schoneveld<p>
 * Company:      Dexels<p>
 * @author Arjen Schoneveld
 * @version 1.0
 */
package com.dexels.navajo.mapping;
import java.lang.reflect.*;

public class Reflectie {

  private double aap;

  public void setAap(double d) {
    aap = d;
  }

  public double getAap() {
    return aap;
  }

  public Reflectie() {
  }

  public static void main(String[] args) {
    Reflectie reflectie1 = new Reflectie();

    Class c = reflectie1.getClass();
    Method [] methods = c.getMethods();

    for (int i=0;i < methods.length;i++) {
      System.out.println(methods[i].toString());
      Method m = methods[i];
      Class [] returns = m.getParameterTypes();
      System.out.println(m.getName());
      System.out.println("parameters:");
      for (int j=0;j<returns.length;j++){
        Class c1 = returns[j];
        System.out.println(c1.getName());
        System.out.println("isPrimitive: " + c1.isPrimitive());
        Constructor [] cs = c1.getConstructors();
        System.out.println("aantal constructors: " + cs.length);
        Field [] fields = c1.getDeclaredFields();
        System.out.println("aantal fields: " + cs.length);
      }
    }

    try {
      Class c3 = String.class;
      Class [] parameters = new Class[] {Double.TYPE};
      Method m = c.getMethod("setAap", parameters);
      Object [] arguments = new Object[] {new Double(4.0)};
      m.invoke(reflectie1, arguments);


      m = c.getMethod("getAap", null);
      Object o = m.invoke(reflectie1, null);

      System.out.println("getAap(): " + o.toString());
    } catch (Exception e) {
      System.out.println(e);
    }
  }
}