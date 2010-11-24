
package com.dexels.navajo.dsl.expression;

import com.google.inject.Injector;

/**
 * Initialization support for running Xtext languages 
 * without equinox extension registry
 */
public class NavajoExpressionStandaloneSetup extends NavajoExpressionStandaloneSetupGenerated{

	public static void doSetup() {
		Injector i = new NavajoExpressionStandaloneSetup().createInjectorAndDoEMFRegistration();
	}
	
	public static void main(String args[]) {
		doSetup();
		
	}
}

