package com.dexels.navajo.util;


/**
 * Title:        Navajo
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Dexels
 * @author Arjen Schoneveld en Martin Bergman
 * @version 1.0
 */

import java.lang.reflect.*;
import java.util.ArrayList;


public class Cast {

    @SuppressWarnings("unchecked")
	public static void down(Object in, Object out) {

        Class inClass = null;

        try {
            inClass = in.getClass();
            Field[] fields = inClass.getDeclaredFields();

            for (int i = 0; i < fields.length; i++) {
                // System.out.println("field " + i + ": " + fields[i].getName() + "/" + fields[i].getType());
                Object value = fields[i].get(in);

                fields[i].set(out, value);
            }
        } catch (IllegalAccessException e) {
            //System.err.println("error in Cast.down(): " + e.getMessage());
            //System.err.println("Object: " + in);
            //if (inClass != null)
            //    System.err.println("inClass: " + inClass.getName());
            //else
            //    System.err.println("Could not determine inClass");
        }
    }

    @SuppressWarnings("unchecked")
	public static Object up(Object in) {

        Object out = null;

        // Return null if object is not instantiated
        if (in == null)
            return null;

        Class superClass = null;

        try {
            superClass = in.getClass().getSuperclass();

            // System.out.println("Superclass: " + superClass.getName());

            // Return object if it does not have a super class.
            if (superClass.getName().equals("java.lang.Object")) {
                return in;
            }

            out = superClass.newInstance();
            Field[] fields = superClass.getDeclaredFields();

            for (int i = 0; i < fields.length; i++) {
                // System.out.println("field " + i + ": " + fields[i].getName() + "/" + fields[i].getType());
                Object value = fields[i].get(in);

                fields[i].set(out, value);
            }
        } catch (IllegalAccessException e) {
            //System.err.println("error in Cast.up(): " + e.getMessage());
            //System.err.println("Object: " + in);
            //if (superClass != null)
            ////    System.err.println("Superclass: " + superClass.getName());
            //else
            //    System.err.println("Could not determine superclass");
        
    } catch (InstantiationException e) {
        //System.err.println("error in Cast.up(): " + e.getMessage());
        //System.err.println("Object: " + in);
        //if (superClass != null)
        ////    System.err.println("Superclass: " + superClass.getName());
        //else
        //    System.err.println("Could not determine superclass");
    }

        return out;
    }

    @SuppressWarnings("unchecked")
	public static ArrayList upAll(Object[] in) {
        if (in == null)
            return null;
        ArrayList out = new ArrayList();

        for (int i = 0; i < in.length; i++) {
            Object input = in[i];
            Object output = up(input);
            out.add(output);
        }

        return out;
    }

    @SuppressWarnings("unchecked")
	public static ArrayList upAll(ArrayList in) {

        if (in == null)
            return null;

        ArrayList out = new ArrayList();

        for (int i = 0; i < in.size(); i++) {
            Object input = in.get(i);
            Object output = up(input);
            out.add(output);
        }

        return out;
    }
}
