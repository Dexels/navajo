package com.dexels.navajo.compiler;


public class BundleCreatorFactory {

    private static  BundleCreator instance = null;

    private BundleCreatorFactory() {
    	// no instances
    }
    public static void setInstance(BundleCreator instance) {
        BundleCreatorFactory.instance = instance;
    }
    
//    public static BundleCreator getInstance() {
//        return instance;
//    }
}
