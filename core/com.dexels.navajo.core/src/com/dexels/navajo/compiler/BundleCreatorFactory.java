package com.dexels.navajo.compiler;


public class BundleCreatorFactory {

    public static  BundleCreator instance = null;

    public static void setInstance(BundleCreator instance) {
        BundleCreatorFactory.instance = instance;
    }
    
    public static BundleCreator getInstance() {
        return instance;
    }
}
